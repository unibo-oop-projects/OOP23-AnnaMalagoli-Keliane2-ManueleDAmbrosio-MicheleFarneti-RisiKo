package it.unibo.risiko.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.event.EventImpl;
import it.unibo.risiko.model.event.EventType;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.event_register.RegisterImpl;
import it.unibo.risiko.model.game.AttackPhase;
import it.unibo.risiko.model.game.AttackPhaseImpl;
import it.unibo.risiko.model.game.Game;
import it.unibo.risiko.model.game.GameManager;
import it.unibo.risiko.model.game.GameManagerImpl;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.model.game.GameFactory;
import it.unibo.risiko.model.game.GameFactoryImpl;
import it.unibo.risiko.model.game.GameLoopManager;
import it.unibo.risiko.model.game.GameLoopManagerImpl;
import it.unibo.risiko.view.InitialViewObserver;
import it.unibo.risiko.view.InitialView.GameFrame;
import it.unibo.risiko.view.gameView.GameView;
import it.unibo.risiko.view.gameView.GameViewImpl;
import it.unibo.risiko.view.gameView.GameViewObserver;

/**
 * Controller class for the risiko game, its function is to coordinate view and
 * model.
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Anna Malagoli
 * @author Keliane2
 */
public class GameController implements GameViewObserver, InitialViewObserver {
    private final GameManager gameManager;
    private GameView view;
    private static final String FILE_SEPARATOR = File.separator;
    private static final String saveGamesFilePath = FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "savegames"
            + FILE_SEPARATOR + "savegames.json";
    private static final String resourcesPackageString = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    private Optional<String> attackerTerritory = Optional.empty();
    private Optional<String> defenderTerritory = Optional.empty();
    private AttackPhase attackPhase;
    private Register register;

    private Territories territories;
    private List<Player> players;
    private Deck deck;
    private GameStatus gameStatus;
    private int activePlayerIndex;
    private GameLoopManager gameLoopManager;

    /**
     * Initialization of the Game controller with a GameManager as model field and a
     * Java Swing view
     * 
     * @author Michele Farneti
     */
    public GameController() {
        gameManager = new GameManagerImpl(resourcesPackageString + saveGamesFilePath,
                resourcesPackageString + FILE_SEPARATOR);
        this.register = new RegisterImpl();
        new GameFrame(this);
    }

    @Override
    public void startGameWindow(Integer width, Integer height) {
        this.view = new GameViewImpl(width, height, resourcesPackageString, this);
        this.view.start();
    }

    @Override
    public void initializeNewGame() {
        view.showInitializationWindow(gameManager.getAvailableMaps());
    }

    @Override
    public void setupGameView() {
        if (gameManager.getCurrentGame().isPresent()) {
            this.game = gameManager.getCurrentGame().get();
            view.showGameWindow(game.getMapName(), game.getPlayersList().size());
            view.showTanks(
                    game.getTerritoriesList().stream().map(t -> t.getTerritoryName()).collect(Collectors.toList()));
            showTurnIcons();
            view.createLog(this.register, game.getPlayersList());
            view.createTablePanel(game.getTerritoriesList(),
                    game.getPlayersList());
            redrawView();
        }
    }

    /**
     * Updates the view in such a way to show the players icons
     * 
     * @author Michele Farneti
     */
    private void showTurnIcons() {
        for (int index = 0; index < game.getPlayersList().size(); index++) {
            var player = game.getPlayersList().get(index);
            this.view.showTurnIcon(player.getColor_id(), index, player.isAI());
        }
    }

    @Override
    public void skipTurn() {
        if (game.skipTurn()) {
            resetAttack();
            redrawView();
            view.setCurrentPlayer(game.getCurrentPlayer().getColor_id(), game.getCurrentPlayer().getArmiesToPlace(),
                    game.getCurrentPlayer().getTarget().showTargetDescription());
            showCards();
            redrawView();
            view.enableMovements(false);
            view.enableAttack(false);
            view.enableSkip(false);
        }
    }

    /**
     * Check if the current player has cards to be played and eventually shows him
     * the menu
     * 
     * @Author Michele Farneti
     */
    private void showCards() {
        if (game.getCurrentPlayer().isAI() && game.getCurrentPlayer().getOwnedCards().isEmpty()) {
            view.createChoiceCards(game.getCurrentPlayer().getOwnedCards().stream().toList());
        }
    }

    /**
     * Resets the current territories set as attacker and defender
     * 
     * @author Michele Farneti
     */
    private void resetAttack() {
        attackerTerritory.ifPresent(t -> view.resetFightingTerritory(t));
        defenderTerritory.ifPresent(t -> view.resetFightingTerritory(t));
        attackerTerritory = Optional.empty();
        defenderTerritory = Optional.empty();
        redrawView();
    }

    // @Override
    public void territorySelected(String territory) {
        switch (game.getGameStatus()) {
            case TERRITORY_OCCUPATION:
                game.placeArmies(territory, 1);
                break;
            case ARMIES_PLACEMENT:
                game.placeArmies(territory, 1);
                if (game.getGameStatus() == GameStatus.READY_TO_ATTACK) {
                    view.enableMovements(true);
                }
                ;
                break;
            case ATTACKING:
                if (game.getCurrentPlayer().isOwnedTerritory(territory) && game.getNumberOfArmies(territory) > 1) {
                    setFighter(territory, true);
                } else if (defenderTerritory.isEmpty() && attackerTerritory.isPresent()
                        && !game.getCurrentPlayer().isOwnedTerritory(territory)) {
                    if (gameManager.getCurrentGame().get().areTerritoriesNear(attackerTerritory.get(),
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

    /**
     * 
     * Checks if the current player won the game, eventually displaying
     * a gameover window
     * 
     * @author Michele Farneti
     */
    private void checkWinner() {
        if (gameManager.getCurrentGame().get().gameOver()) {
            this.view.gameOver(currentPlayer().get().getColor_id());
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
        view.createAttack(attackerTerritory.get(), defenderTerritory.get(),
                game.getNumberOfArmies(attackerTerritory.get()));
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane2
     */
    @Override
    public void setAttackingArmies(int numberOfAttackingAmies) {
        attackPhase = new AttackPhaseImpl(numberOfAttackingAmies, game.getNumberOfArmies(defenderTerritory.get()));
        view.setAtt(attackPhase.getAttackerDiceThrows());
        view.setDef(attackPhase.getDefenderDiceThrows());
        view.setAttackerLostArmies(attackPhase.getAttackerLostArmies());
        view.setDefenderLostArmies(attackPhase.getDefenderLostArmies());

        // // Creation of attack event.
        // createEvent(EventType.ATTACK, attackerTerritory.get(),
        // defenderTerritory.get(), currentPlayer().get(),
        // Optional.of(getOwner(defenderTerritory.get())), Optional.empty());
        view.updateLog();

        // Destroying armies.
        game.removeArmies(attackerTerritory.get(), attackPhase.getAttackerLostArmies());
        game.removeArmies(defenderTerritory.get(), attackPhase.getDefenderLostArmies());

        view.drawDicePanels();
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane2
     */
    @Override
    public void conquerIfPossible() {
        if (attackPhase.isTerritoryConquered()) {
            // currentPlayer().get().drawNewCardIfPossible(gameManager.getCurrentGame().get().pullCard());

            // createEvent(EventType.TERRITORY_CONQUEST, attackerTerritory.get(),
            // defenderTerritory.get(), currentPlayer().get(),
            // Optional.of(game.getOwner(defenderTerritory.get().getTerritoryName())),
            // Optional.empty());

            view.updateLog();
            view.drawConquerPanel();
        } else {
            view.closeAttackPanel();
            redrawView();
            checkWinner();
        }

        resetAttack();
        game.endAttack();
    }

    /**
     * @author Manuele D'Ambrosio
     * @author Keliane2
     */
    @Override
    public void setMovingArmies(int numberOfMovingArmies) {
        // Conquer of the territory.
        game.setOwner(defenderTerritory.get(), currentPlayer().get().getColor_id());
        game.removeArmies(attackerTerritory.get(), numberOfMovingArmies);
        game.placeArmies(defenderTerritory.get(), numberOfMovingArmies);

        view.closeAttackPanel();
        // Creating moovement event.
        // createEvent(EventType.TROOP_MOVEMENT, attackerTerritory.get(),
        // defenderTerritory.get(),
        // game.getOwner(attackerTerritory.get().getTerritoryName()),
        // Optional.empty(), Optional.of(numberOfMovingArmies));

        view.updateLog();
        redrawView();
        checkWinner();
    }

    /**
     * @param reg
     * @param type
     * @param attacker
     * @param defender
     * @param eventLeader
     * @param eventLeaderAdversary
     * @author Keliane2
     */
    private void createEvent(EventType type, Territory attacker, Territory defender, Player eventLeader,
            Optional<Player> eventLeaderAdversary, Optional<Integer> numArmies) {
        if (type.equals(EventType.ATTACK) || type.equals(EventType.TERRITORY_CONQUEST)) {
            register.addEvent(new EventImpl(type, attacker, defender, eventLeader, eventLeaderAdversary.get()));
        } else {
            register.addEvent(new EventImpl(type, attacker, defender, eventLeader, numArmies.get()));
        }
    }

    /**
     * 
     * @return If present, the current player of the current game, otherwise an empy
     *         optional.
     * @author Michele Farneti
     */
    private Optional<Player> currentPlayer() {
        if (gameManager.getCurrentGame().isPresent()) {
            return Optional.of(gameManager.getCurrentGame().get().getCurrentPlayer());
        } else {
            return Optional.empty();
        }
    }

    /**
     * The territory passed as argument is set as AttackerTerritory and is Higligted
     * by the GUI
     * 
     * @param territory
     * @author Michele Farneti
     */
    private void setFighter(String territory, boolean isAttacker) {
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
     * Finds a terrritory in the currentGame territories list starting from its name
     * 
     * @param territoryName
     * @return The territory with the given name
     * @author Michele Farneti
     */
    private Territory getTerritoryFromString(String territoryName) {
        return game.getTerritoriesList().stream()
                .filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().get();
    }

    /**
     * Updates the game view by changhing the map and the turns
     * 
     * @author Michele Farneti
     */
    private void redrawView() {
        game.getTerritoriesList().stream()
                .forEach(t -> view.redrawTank(t.getTerritoryName(), t.getPlayer(), t.getNumberOfArmies()));
        view.setCurrentPlayer(game.getCurrentPlayer().getColor_id(), game.getCurrentPlayer().getArmiesToPlace(),
                game.getCurrentPlayer().getTarget().showTargetDescription());
        view.updateTablePanel();
        view.showStatus(game.getGameStatus(),
                game.getTurnsCount());

        switch (game.getGameStatus()) {
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
        final GameMapInitializer gameInitializer = new GameMapInitializerImpl(mapName, resourcesPackageString);
        PlayerFactory playerFactory = new SimplePlayerFactory();

        for (int index = 0; index < numberOfStandardPlayers + numberOfAIPlayers; index++) {
            if (index < numberOfStandardPlayers) {
                players.add(playerFactory.createStandardPlayer());
            } else {
                players.add(playerFactory.createStandardPlayer());
            }
        }

        gameLoopManager = new GameLoopManagerImpl();
        this.deck = new DeckImpl(gameInitializer.getDeckPath());
        this.territories = new Territories(gameInitializer.getTerritoriesPath());
        this.gameStatus = GameStatus.TERRITORY_OCCUPATION;
        players.forEach(p -> p.setArmiesToPlace(gameInitializer.getStratingArmies(players.size())));
        players.forEach(p-> p.setTarget(gameInitializer.generateTarget(players.indexOf(p),players,territories)));
        assignTerritories(gameInitializer.minimumArmiesPerTerritory());
        this.setupGameView();
    }

    /**
     * @param minimuArmiesPerTerritory The minimum number of armies every territory
     *                                 has to have when it's owned by someone
     * @author Michele Farneti
     */
    private void assignTerritories(Integer minimumArmiesPerTerritory) {
        for (Territory territory : this.territories.getListTerritories()) {
            players.get(activePlayerIndex).addTerritory(territory.getTerritoryName());
            territories.setOwner(territory.getTerritoryName(), players.get(activePlayerIndex).getColor_id());
            territories.addArmiesInTerritory(territory.getTerritoryName(), minimumArmiesPerTerritory);
            players.get(activePlayerIndex).decrementArmiesToPlace();
            activePlayerIndex = gameLoopManager.nextPlayer(activePlayerIndex, players.size());
        }
    }

    @Override
    public void setAttacking() {
        gameManager.getCurrentGame().get().setAttacking();
    }

    /**
     * Method used to move a certain amount of armies between two
     * adjacent territories.
     * 
     * @param srcTerritory is the source territory
     * @param dstTerritory is the destination territory
     * @param numArmies    is the number of armies that the player
     *                     wants to move
     * @author Keliane2
     * @author Anna Malagoli
     */
    public void moveArmies(final String srcTerritory, final String dstTerritory, final int numArmies) {
        getTerritoryFromString(srcTerritory).removeArmies(numArmies);
        getTerritoryFromString(dstTerritory).addArmies(numArmies);
        // createEvent(EventType.TROOP_MOVEMENT, getTerritoryFromString(srcTerritory),
        // getTerritoryFromString(dstTerritory),game.getOwner(srcTerritory),
        // Optional.empty(),
        // Optional.of(numArmies));
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
    public void playCards(final String card1, final String card2, final String card3) {

        gameManager.getCurrentGame().get().playCards(card1, card2, card2, currentPlayer().get());
        exitCardsManagingPhase();
    }

    @Override
    public void moveClicked() {
        view.createMoveArmies(
                game.getCurrentPlayer().getOwnedTerritories().stream().map(t -> getTerritoryFromString(t)).toList());
    }

    @Override
    public void closeMovementPhase() {
        this.view.exitMoveArmiesPanel();
    }

    @Override
    public void exitCardsManagingPhase() {
        gameManager.getCurrentGame().get().endCardsPhase();
        this.view.exitCardsPanel();
        redrawView();
    }
}