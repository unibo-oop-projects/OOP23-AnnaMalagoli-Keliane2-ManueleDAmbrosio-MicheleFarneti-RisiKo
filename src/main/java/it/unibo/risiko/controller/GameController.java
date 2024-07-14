package it.unibo.risiko.controller;

import java.io.File;
import java.util.Optional;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.event_register.RegisterImpl;
import it.unibo.risiko.model.game.AttackPhase;
import it.unibo.risiko.model.game.AttackPhaseImpl;
import it.unibo.risiko.model.game.GameManager;
import it.unibo.risiko.model.game.GameManagerImpl;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.GameMapImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.model.game.GameFactory;
import it.unibo.risiko.model.game.GameFactoryImpl;
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
    private Optional<Territory> attackerTerritory = Optional.empty();
    private Optional<Territory> defenderTerritory = Optional.empty();
    private AttackPhase attackPhase;
    private Register register;

    /**
     * Initialization of the Game controller with a GameManager as model field and a
     * Java Swing view
     * 
     * @author Michele Farneti
     */
    public GameController() {
        this.register = new RegisterImpl();
        gameManager = new GameManagerImpl(resourcesPackageString + saveGamesFilePath,
                resourcesPackageString + FILE_SEPARATOR);
        new GameFrame(this);
    }

    @Override
    public void startGameWindow(Integer width, Integer height) {
        this.view = new GameViewImpl(width, height, resourcesPackageString);
        this.view.start();
        this.view.setObserver(this);
    }

    @Override
    public void initializeNewGame() {
        view.showInitializationWindow(gameManager.getAvailableMaps());
    }

    @Override
    public void setupGameView() {
        view.showGameWindow(gameManager.getCurrentGame().get().getMapName(),
                gameManager.getCurrentGame().get().getPlayersList().size());
        view.showTanks(gameManager.getCurrentGame().get().getTerritoriesList());
        showTurnIcons();
        view.createLog(this.register, gameManager.getCurrentGame().get().getPlayersList());
        view.createTablePanel(gameManager.getCurrentGame().get().getTerritoriesList(),
                gameManager.getCurrentGame().get().getPlayersList());
        redrawView();
    }

    /**
     * Updates the view in such a way to show the players icons
     * 
     * @author Michele Farneti
     */
    private void showTurnIcons() {
        for (int index = 0; index < gameManager.getCurrentGame().get().getPlayersList().size(); index++) {
            var player = gameManager.getCurrentGame().get().getPlayersList().get(index);
            this.view.showTurnIcon(player, index);
        }
    }

    @Override
    public void skipTurn() {
        if (gameManager.getCurrentGame().get().skipTurn()) {
            resetAttack();
            redrawView();
            view.setCurrentPlayer(currentPlayer().get());
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
        if (!currentPlayer().get().isAI() && !currentPlayer().get().getOwnedCards().isEmpty()) {
            view.createChoiceCards(currentPlayer().get().getOwnedCards().stream().toList());
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

    @Override
    public void territorySelected(Territory territory) {
        switch (gameManager.getCurrentGame().get().getGameStatus()) {
            case TERRITORY_OCCUPATION:
                gameManager.getCurrentGame().get().placeArmies(territory, 1);
                break;
            case ARMIES_PLACEMENT:
                gameManager.getCurrentGame().get().placeArmies(territory, 1);
                if (gameManager.getCurrentGame().get().getGameStatus() == GameStatus.READY_TO_ATTACK) {
                    view.enableMovements(true);
                }
                ;
                break;
            case ATTACKING:
                if (currentPlayerOwns(territory) && territory.getNumberOfArmies() > 1) {
                    setFighter(territory, true);
                } else if (defenderTerritory.isEmpty() && attackerTerritory.isPresent()
                        && !currentPlayerOwns(territory)) {
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
        view.createAttack(attackerTerritory.get().getTerritoryName(), defenderTerritory.get().getTerritoryName(),
                attackerTerritory.get().getNumberOfArmies());
    }

    @Override
    public void setAttackingArmies(int numberOfAttackingAmies) {
        attackPhase = new AttackPhaseImpl(getOwner(attackerTerritory.get()), attackerTerritory.get(),
                numberOfAttackingAmies, getOwner(defenderTerritory.get()), defenderTerritory.get());
        view.setAtt(attackPhase.getAttackerDiceThrows());
        view.setDef(attackPhase.getDefenderDiceThrows());
        view.setAttackerLostArmies(attackPhase.getAttackerLostArmies());
        view.setDefenderLostArmies(attackPhase.getDefenderLostArmies());
        attackPhase.destroyArmies();

        view.drawDicePanels();
    }

    @Override
    public void conquerIfPossible() {
        if (attackPhase.isTerritoryConquered()) {
            currentPlayer().get().drawNewCardIfPossible(gameManager.getCurrentGame().get().getDeck());
            view.drawConquerPanel();
        } else {
            view.closeAttackPanel();
            redrawView();
            checkWinner();
        }
        resetAttack();
        gameManager.getCurrentGame().get().endAttack();
    }

    @Override
    public void setMovingArmies(int numberOfMovingArmies) {
        attackPhase.conquerTerritory(numberOfMovingArmies);
        view.closeAttackPanel();
        redrawView();
        checkWinner();
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
     * 
     * @param territory
     * @return The owner of the territory in the current game
     * @author Michele Farneti
     */
    private Player getOwner(Territory territory) {
        return gameManager.getCurrentGame().get().getOwner(territory);
    }

    /**
     * The territory passed as argument is set as AttackerTerritory and is Higligted
     * by the GUI
     * 
     * @param territory
     * @author Michele Farneti
     */
    private void setFighter(Territory territory, boolean isAttacker) {
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
        return gameManager.getCurrentGame().get().getTerritoriesList().stream()
                .filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().get();
    }

    /**
     * Checks if the current player of the current game owns a territory
     * 
     * @param territory
     * @return true if the player owns the territory
     * @author Michele Farneti
     */
    private boolean currentPlayerOwns(Territory territory) {
        return currentPlayer().get().isOwnedTerritory(territory);
    }

    /**
     * Updates the game view by changhing the map and the turns
     * 
     * @author Michele Farneti
     */
    private void redrawView() {
        gameManager.getCurrentGame().get().getPlayersList().stream()
                .forEach(p -> p.getOwnedTerritories().stream()
                        .forEach(t -> view.redrawTank(t, p.getColor_id())));
        view.setCurrentPlayer(currentPlayer().get());
        view.updateTablePanel();
        view.showStatus(gameManager.getCurrentGame().get().getGameStatus(),gameManager.getCurrentGame().get().getTurnsCount());

        switch (gameManager.getCurrentGame().get().getGameStatus()) {
            case READY_TO_ATTACK:
                view.enableAttack(true);
                view.enableSkip(true);
                break;
            case ARMIES_PLACEMENT:
                view.enableAttack(false);
                view.enableSkip(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void startNewGame(String mapName, int numberOfStandardPlayers, int numberOfAIPlayers) {
        final GameFactory gameFactory = new GameFactoryImpl(new GameMapImpl(mapName, resourcesPackageString));
        PlayerFactory playerFactory = new SimplePlayerFactory();

        for (int index = 0; index < numberOfStandardPlayers + numberOfAIPlayers; index++) {
            if (index < numberOfStandardPlayers) {
                gameFactory.addNewPlayer(playerFactory.createStandardPlayer());
            } else {
                gameFactory.addNewPlayer(playerFactory.createAIPlayer());
            }
        }
        gameManager.setCurrentGame(gameFactory.initializeGame());
        this.setupGameView();
    }

    @Override
    public void setAttacking() {
        gameManager.getCurrentGame().get().setAttacking();
    }

    @Override
    public void moveArmies(String srcTerritory, String dstTerritory, int numArmies) {
        getTerritoryFromString(srcTerritory).removeArmies(numArmies);
        getTerritoryFromString(dstTerritory).addArmies(numArmies);
        view.exitMoveArmiesPanel();
        this.skipTurn();
    }

    @Override
    public void playCards(String card1, String card2, String card3) {
        Deck deck = gameManager.getCurrentGame().get().getDeck();
        Card firstCard = deck.getCardByTerritoryName(card1, currentPlayer().get()).get();
        Card secondCard = deck.getCardByTerritoryName(card2, currentPlayer().get()).get();
        Card thirdCard = deck.getCardByTerritoryName(card3, currentPlayer().get()).get();
        /*
         * modifica del metodo playCards per cui non viene passato il player
         * e rimuovere le stringhe errore effettuando semplicemente l'operazione
         */
        deck.playCards(firstCard, secondCard, thirdCard, currentPlayer().get());    
        exitCardsManagingPhase();
        
    }

    @Override
    public void moveClicked() {
        view.createMoveArmies(currentPlayer().get().getOwnedTerritories().stream().toList());
    }

    @Override
    public void closeMovementPhase() {
        this.view.exitMoveArmiesPanel();
    }

    /**
     * Ends the card managing phase by alerting both mdoel and view.
     * @author Michele Farneti.
     */
    private void exitCardsManagingPhase() {
        gameManager.getCurrentGame().get().endCardsPhase();
        this.view.exitCardsPanel();
        redrawView();
    }
}