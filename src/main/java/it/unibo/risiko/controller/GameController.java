package it.unibo.risiko.controller;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.event.EventImpl;
import it.unibo.risiko.model.event.EventType;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.event_register.RegisterImpl;
import it.unibo.risiko.model.game.AttackPhase;
import it.unibo.risiko.model.game.AttackPhaseImpl;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.map.TerritoriesImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.AIBehaviour;
import it.unibo.risiko.model.player.AIBehaviourImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.model.game.GameLoopManager;
import it.unibo.risiko.model.game.GameLoopManagerImpl;
import it.unibo.risiko.view.gameview.GameView;
import it.unibo.risiko.view.gameview.GameViewImpl;
import it.unibo.risiko.view.gameview.GameViewObserver;
import it.unibo.risiko.view.initview.InitialViewImpl;
import it.unibo.risiko.view.initview.InitialViewObserver;

/**
 * Controller class for the risiko game, its function is to coordinate view and
 * model.
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Anna Malagoli
 * @author Keliane Nana
 */
public class GameController implements GameViewObserver, InitialViewObserver {
    private static final String FILE_SEPARATOR = File.separator;
    private static final String RESOURCES_PACKAGE_STRING = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    private static final int MAX_ATTACKING_ARMIES = 3;
    private static final int MIN_ARMIES = 1;
    private static final int FIRST_CARD = 0;
    private static final int SECOND_CARD = 1;
    private static final int THIRD_CARD = 2;
    private GameMapInitializer gameInitializer;
    private GameLoopManager gameLoopManager;
    private GameView view;
    private Optional<String> attackerTerritory = Optional.empty();
    private Optional<String> defenderTerritory = Optional.empty();
    private AttackPhase attackPhase;
    private Register register;

    private Territories territories;
    private List<Player> players;
    private Deck deck;
    private GameStatus gameStatus;
    private int activePlayerIndex;

    /**
     * Initialization of the Game controller with a GameManager as model field and a
     * Java Swing view
     * 
     * @author Michele Farneti
     */
    public GameController() {
        new InitialViewImpl(this);
    }

    @Override
    public void startGameWindow(final Integer width, final Integer height) {
        this.view = new GameViewImpl(width, height, RESOURCES_PACKAGE_STRING, this);
        this.view.start();
    }

    @Override
    public void initializeNewGame() {
        view.showInitializationWindow(GameMapInitializer.getAvailableMaps(RESOURCES_PACKAGE_STRING + FILE_SEPARATOR));
    }

    @Override
    public void setupGameView() {
        view.showGameWindow(gameInitializer.getMapName(), players.size());
        view.showTanks(territories.getListTerritories());
        showTurnIcons();
        view.createLog(this.register, players);
        view.createTablePanel(territories.getListTerritories(), players);
        redrawView();
    }

    /**
     * Updates the view in such a way to show the players icons
     * 
     * @author Michele Farneti
     */
    private void showTurnIcons() {
        for (int index = 0; index < players.size(); index++) {
            final var player = players.get(index);
            this.view.showTurnIcon(player, index);
        }
    }

    @Override
    public void skipTurn() {
        if (gameLoopManager.skipTurn(activePlayerIndex, players, territories)) {
            updateGameStatus();
            resetAttack();
            redrawView();
            view.setCurrentPlayer(currentPlayer());
            currentPlayer().computeReinforcements(territories.getListContinents());
            if (gameStatus == GameStatus.CARDS_MANAGING && currentPlayer().getOwnedCards().size() > 0
                    && !currentPlayer().isAI()) {
                showCards();
            }
            redrawView();
            view.enableMovements(false);
            view.enableAttack(false);
            view.enableSkip(false);
            if (gameLoopManager.skippedToAI()) {
                while (currentPlayer().isAI()) {
                    handleAIBehaviour();
                }
                redrawView();
            }
        }
    }

    private void updateGameStatus() {
        activePlayerIndex = gameLoopManager.getActivePlayer();
        gameStatus = gameLoopManager.getGameStatus();
    }

    /**
     * AIBehaviour handler
     * 
     * @author Michele Farneti
     * @autho Manuele D'Ambrosio
     */
    private void handleAIBehaviour() {
        if (currentPlayer().isAI()) {
            final AIBehaviour aiBehaviour = new AIBehaviourImpl(
                    currentPlayer().getOwnedTerritories().stream().map(t -> getTerritoryFromString(t)).toList(),
                    currentPlayer().getOwnedCards().stream().toList());
            switch (this.gameStatus) {
                case ARMIES_PLACEMENT:
                case TERRITORY_OCCUPATION:
                    territorySelected(aiBehaviour.decidePositioning().getTerritoryName());
                    break;
                case ATTACKING:
                    if (aiBehaviour.decideAttack(territories.getListTerritories())) {
                        territorySelected(aiBehaviour.getNextAttackingTerritory());
                        territorySelected(aiBehaviour.getNextAttackedTerritory());
                    } else {
                        this.gameStatus = GameStatus.READY_TO_ATTACK;
                        skipTurn();
                    }
                    break;
                case CARDS_MANAGING:
                    final List<Card> combo = aiBehaviour.checkCardCombo();
                    if (!combo.isEmpty()) {
                        playCards(combo.get(FIRST_CARD).getTerritoryName(),
                                combo.get(SECOND_CARD).getTerritoryName(),
                                combo.get(THIRD_CARD).getTerritoryName());
                    } else {
                        exitCardsManagingPhase();
                    }
                    exitCardsManagingPhase();
                    break;
                case READY_TO_ATTACK:
                    setAttacking();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Check if the current player has cards to be played and eventually shows him
     * the menu
     * 
     * @Author Michele Farneti
     */
    private void showCards() {
        if (!currentPlayer().isAI()) {
            view.createChoiceCards(currentPlayer().getOwnedCards().stream().toList());
        }
    }

    /**
     * Resets the current territories set as attacker and defender
     * 
     * @author Michele Farneti
     */
    private void resetAttack() {
        view.resetFightingTerritories();
        attackerTerritory = Optional.empty();
        defenderTerritory = Optional.empty();
        redrawView();
    }

    @Override
    public void territorySelected(final String territory) {
        switch (gameStatus) {
            case TERRITORY_OCCUPATION:
                if (placeArmies(territory, MIN_ARMIES)) {
                    redrawView();
                    updateGameStatus();
                    if (gameStatus == GameStatus.ARMIES_PLACEMENT) {
                        currentPlayer().computeReinforcements(territories.getListContinents());
                    }
                    if (gameLoopManager.skippedToAI()) {
                        while (currentPlayer().isAI()) {
                            handleAIBehaviour();
                        }
                        redrawView();
                    }
                }
                break;
            case ARMIES_PLACEMENT:
                if (placeArmies(territory, 1)) {
                    if (gameStatus == GameStatus.READY_TO_ATTACK) {
                        view.enableMovements(true);
                    }
                    if (gameLoopManager.skippedToAI()) {
                        while (currentPlayer().isAI()) {
                            handleAIBehaviour();
                        }
                        redrawView();
                    }
                    redrawView();
                }
                break;
            case ATTACKING:
                if (currentPlayer().isOwnedTerritory(territory) && getArmiesInTerritory(territory) > 1) {
                    setFighter(territory, true);
                } else if (defenderTerritory.isEmpty() && attackerTerritory.isPresent()
                        && !currentPlayer().isOwnedTerritory(territory)) {
                    if (territories.territoriesAreNear(attackerTerritory.get(),
                            territory)) {
                        setFighter(territory, false);
                        startAttack();
                    }
                }
                break;
            default:
                break;
        }
        redrawView();
    }

    private boolean placeArmies(final String territory, final Integer nArmies) {
        if (gameStatus == GameStatus.TERRITORY_OCCUPATION) {
            if (gameLoopManager.placeArmiesIfPossible(players.get(activePlayerIndex), players, territory,
                    nArmies, territories)) {
                currentPlayer().decrementArmiesToPlace();
                territories.addArmiesInTerritory(territory, nArmies);
                return true;
            }
        } else {
            if (gameLoopManager.placeArmiesIfPossible(players.get(activePlayerIndex), players, territory,
                    nArmies, territories)) {
                currentPlayer().decrementArmiesToPlace();
                territories.addArmiesInTerritory(territory, nArmies);
                updateGameStatus();
                return true;
            }
        }
        return false;
    }

    private Integer getArmiesInTerritory(final String territory) {
        return territories.getListTerritories().stream()
                .filter(t -> t.getTerritoryName().equals(territory))
                .mapToInt(t -> t.getNumberOfArmies()).sum();
    }

    /**
     * 
     * Checks if the current player won the game, eventually displaying
     * a gameover window
     * 
     * @author Michele Farneti
     */
    private void checkWinner() {
        if (gameLoopManager.isGameOver(activePlayerIndex, players, territories)) {
            this.view.gameOver(currentPlayer().getColorID());
        }
    }

    /**
     * Creates a new attack once attacker territory and defender territory are
     * correctly set,
     * then updates the view.
     * 
     * @author Manuele D'Ambrosio
     */
    private void startAttack() {
        if (!currentPlayer().isAI()) {
            view.createAttack(attackerTerritory.get(), defenderTerritory.get(),
                    getArmiesInTerritory(attackerTerritory.get()));
        } else {
            attackPhase = new AttackPhaseImpl(
                    getTerritoryFromString(attackerTerritory.get()).getNumberOfArmies() > MAX_ATTACKING_ARMIES
                            ? MAX_ATTACKING_ARMIES
                            : getTerritoryFromString(attackerTerritory.get()).getNumberOfArmies(),
                    getArmiesInTerritory(defenderTerritory.get()));

            // Creation of attack event.
            createEvent(EventType.ATTACK, getTerritoryFromString(attackerTerritory.get()),
                    getTerritoryFromString(defenderTerritory.get()), currentPlayer(),
                    Optional.of(getOwner(getTerritoryFromString(defenderTerritory.get()))), Optional.empty());
            view.updateLog();

            territories.removeArmiesInTerritory(attackerTerritory.get(), attackPhase.getAttackerLostArmies());
            territories.removeArmiesInTerritory(defenderTerritory.get(), attackPhase.getDefenderLostArmies());

            // Conquer of the territory.
            if (attackPhase.isTerritoryConquered()) {
                final int armiesToMove = getTerritoryFromString(attackerTerritory.get()).getNumberOfArmies()
                        - MIN_ARMIES;
                conquerAndDraw(attackerTerritory.get(), defenderTerritory.get(), armiesToMove);

                // Conquer Event
                createEvent(EventType.TERRITORY_CONQUEST, getTerritoryFromString(attackerTerritory.get()),
                        getTerritoryFromString(defenderTerritory.get()), currentPlayer(),
                        Optional.of(getOwner(getTerritoryFromString(defenderTerritory.get()))),
                        Optional.empty());

                // Creating moovement event.
                createEvent(EventType.TROOP_MOVEMENT, getTerritoryFromString(attackerTerritory.get()),
                        getTerritoryFromString(defenderTerritory.get()),
                        getOwner(getTerritoryFromString(attackerTerritory.get())),
                        Optional.empty(), Optional.of(armiesToMove));
            }
            this.gameStatus = GameStatus.READY_TO_ATTACK; // prova
            skipTurn();
        }
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane Nana
     */
    @Override
    public void setAttackingArmies(final int numberOfAttackingAmies) {
        attackPhase = new AttackPhaseImpl(numberOfAttackingAmies, getArmiesInTerritory(defenderTerritory.get()));
        view.setAtt(attackPhase.getAttackerDiceThrows());
        view.setDef(attackPhase.getDefenderDiceThrows());
        view.setAttackerLostArmies(attackPhase.getAttackerLostArmies());
        view.setDefenderLostArmies(attackPhase.getDefenderLostArmies());

        // Creation of attack event.
        createEvent(EventType.ATTACK, getTerritoryFromString(attackerTerritory.get()),
                getTerritoryFromString(defenderTerritory.get()), currentPlayer(),
                Optional.of(getOwner(getTerritoryFromString(defenderTerritory.get()))), Optional.empty());
        view.updateLog();

        // Destroying armies.
        territories.removeArmiesInTerritory(attackerTerritory.get(), attackPhase.getAttackerLostArmies());
        territories.removeArmiesInTerritory(defenderTerritory.get(), attackPhase.getDefenderLostArmies());

        view.drawDicePanels();
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane Nana
     */
    @Override
    public void conquerIfPossible() {
        if (attackPhase.isTerritoryConquered()) {

            createEvent(EventType.TERRITORY_CONQUEST, getTerritoryFromString(attackerTerritory.get()),
                    getTerritoryFromString(defenderTerritory.get()), currentPlayer(),
                    Optional.of(getOwner(getTerritoryFromString(defenderTerritory.get()))),
                    Optional.empty());

            view.updateLog();
            view.drawConquerPanel();
        } else {
            view.closeAttackPanel();
            resetAttack();
            redrawView();
            checkWinner();
        }
        this.gameStatus = GameStatus.READY_TO_ATTACK;
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane Nana
     */
    @Override
    public void setMovingArmies(final int numberOfMovingArmies) {
        // Conquer of the territory.
        conquerAndDraw(attackerTerritory.get(), defenderTerritory.get(), numberOfMovingArmies);

        view.closeAttackPanel();

        // Creating moovement event.
        createEvent(EventType.TROOP_MOVEMENT, getTerritoryFromString(attackerTerritory.get()),
                getTerritoryFromString(defenderTerritory.get()),
                getOwner(getTerritoryFromString(attackerTerritory.get())),
                Optional.empty(), Optional.of(numberOfMovingArmies));

        resetAttack();
        view.updateLog();
        redrawView();
        checkWinner();
    }

    private void conquerAndDraw(final String attTerritory, final String conqueredTerritory, final int movingArmies) {
        getOwner(getTerritoryFromString(conqueredTerritory)).removeTerritory(conqueredTerritory);
        getOwner(getTerritoryFromString(attTerritory)).addTerritory(conqueredTerritory);
        territories.setOwner(conqueredTerritory, currentPlayer().getColorID());
        territories.removeArmiesInTerritory(attTerritory, movingArmies);
        territories.addArmiesInTerritory(conqueredTerritory, movingArmies);
        drawCard();
    }

    /**
     * @param reg
     * @param type
     * @param attacker
     * @param defender
     * @param eventLeader
     * @param eventLeaderAdversary
     * @author Keliane Nana
     */
    private void createEvent(final EventType type, final Territory attacker, final Territory defender,
            final Player eventLeader,
            final Optional<Player> eventLeaderAdversary, final Optional<Integer> numArmies) {
        register.addEvent(new EventImpl(type, attacker, defender, eventLeader, eventLeaderAdversary, numArmies));
    }

    /**
     * 
     * @return The current player
     * @author Michele Farneti
     */
    private Player currentPlayer() {
        return players.get(activePlayerIndex);
    }

    /**
     * The territory passed as argument is set as AttackerTerritory and is Higligted
     * by the GUI
     * 
     * @param territory
     * @author Michele Farneti
     */
    private void setFighter(final String territory, final boolean isAttacker) {
        if (isAttacker) {
            resetAttack();
            attackerTerritory = Optional.of(territory);
            view.showFightingTerritory(territory, true);
        } else {
            defenderTerritory = Optional.of(territory);
            view.showFightingTerritory(territory, false);
        }
    }

    /**
     * Updates the game view by changhing the map and the turns
     * 
     * @author Michele Farneti
     */
    private void redrawView() {
        territories.getListTerritories().stream()
                .forEach(t -> view.redrawTank(t));
        view.setCurrentPlayer(currentPlayer());
        view.updateTablePanel();
        view.showStatus(gameStatus,
                gameLoopManager.getTurnsCount());

        switch (gameStatus) {
            case READY_TO_ATTACK:
                view.enableAttack(true);
                view.enableSkip(true);
                break;
            case ARMIES_PLACEMENT:
                view.enableAttack(false);
                view.enableSkip(false);
                view.enableMovements(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void startNewGame(final String mapName, final int numberOfStandardPlayers, final int numberOfAIPlayers) {
        gameInitializer = new GameMapInitializerImpl(mapName, RESOURCES_PACKAGE_STRING);
        players = new LinkedList<>();
        final PlayerFactory playerFactory = new SimplePlayerFactory();

        for (int index = 0; index < numberOfStandardPlayers + numberOfAIPlayers; index++) {
            if (index < numberOfStandardPlayers) {
                players.add(playerFactory.createStandardPlayer());
            } else {
                players.add(playerFactory.createAIPlayer());
            }
        }
        Collections.shuffle(players);

        gameLoopManager = new GameLoopManagerImpl();
        this.deck = new DeckImpl(gameInitializer.getDeckPath());
        this.territories = new TerritoriesImpl(gameInitializer.getTerritoriesPath());
        this.gameStatus = GameStatus.TERRITORY_OCCUPATION;
        players.forEach(p -> p.setArmiesToPlace(gameInitializer.getStartingArmies(players.size())));
        players.forEach(p -> p.setTarget(gameInitializer.generateTarget(players.indexOf(p), players, territories)));
        assignTerritories(gameInitializer.minimumArmiesPerTerritory());
        this.register = new RegisterImpl();
        activePlayerIndex = 0;
        this.setupGameView();
        // Manages the case where the player selected at first is AI.
        while (currentPlayer().isAI()) {
            handleAIBehaviour();
        }
        redrawView();
    }

    /**
     * @param minimuArmiesPerTerritory The minimum number of armies every territory
     *                                 has to have when it's owned by someone
     * @author Michele Farneti
     */
    private void assignTerritories(final Integer minimumArmiesPerTerritory) {
        territories.shuffle();
        for (final Territory territory : this.territories.getListTerritories()) {
            currentPlayer().addTerritory(territory.getTerritoryName());
            territories.setOwner(territory.getTerritoryName(), currentPlayer().getColorID());
            territories.addArmiesInTerritory(territory.getTerritoryName(), minimumArmiesPerTerritory);
            currentPlayer().decrementArmiesToPlace();
            activePlayerIndex = gameLoopManager.nextPlayer(activePlayerIndex, players.size());
        }
    }

    @Override
    public void setAttacking() {
        gameStatus = GameStatus.ATTACKING;
    }

    /**
     * Method used to move a certain amount of armies between two
     * adjacent territories.
     * 
     * @param srcTerritory is the source territory
     * @param dstTerritory is the destination territory
     * @param numArmies    is the number of armies that the player
     *                     wants to move
     * @author Keliane Nana
     * @author Anna Malagoli
     */
    @Override
    public void moveArmies(final String srcTerritory, final String dstTerritory, final int numArmies) {
        territories.removeArmiesInTerritory(srcTerritory, numArmies);
        territories.addArmiesInTerritory(dstTerritory, numArmies);

        createEvent(EventType.TROOP_MOVEMENT, getTerritoryFromString(srcTerritory),
                getTerritoryFromString(dstTerritory), getOwner(getTerritoryFromString(dstTerritory)),
                Optional.empty(),
                Optional.of(numArmies));

        view.updateLog();
        view.exitMoveArmiesPanel();
        this.skipTurn();
    }

    /**
     * Method used to play the three cards selected by a player.
     * 
     * @param card1 is the first card selected
     * @param card2 is the second card selected
     * @param card3 is the third card selected
     * 
     * @author Anna Malagoli
     */
    @Override
    public void playCards(final String card1, final String card2, final String card3) {
        deck.playCards(card1, card2, card2, currentPlayer());
        exitCardsManagingPhase();
    }

    @Override
    public void moveClicked() {
        view.createMoveArmies(
                currentPlayer().getOwnedTerritories().stream().map(t -> getTerritoryFromString(t)).toList());
    }

    private Territory getTerritoryFromString(final String territoryName) {
        return territories.getListTerritories().stream().filter(t -> t.getTerritoryName().equals(territoryName))
                .findFirst().get();
    }

    private Player getOwner(final Territory territory) {
        final String playerColor = territory.getPlayer();
        return players.stream().filter(p -> p.getColorID().equals(playerColor)).findFirst().get();
    }

    private void drawCard() {
        final Card card = deck.pullCard();
        if (!currentPlayer().drawNewCardIfPossible(card)) {
            deck.addCard(card);
        }

    }

    @Override
    public void closeMovementPhase() {
        this.view.exitMoveArmiesPanel();
    }

    @Override
    public void exitCardsManagingPhase() {
        if (players.get(activePlayerIndex).getArmiesToPlace() == 0) {
            gameStatus = GameStatus.READY_TO_ATTACK;
        } else {
            gameStatus = GameStatus.ARMIES_PLACEMENT;
        }
        if (!currentPlayer().isAI()) {
            this.view.exitCardsPanel();
        }
        redrawView();
    }
}