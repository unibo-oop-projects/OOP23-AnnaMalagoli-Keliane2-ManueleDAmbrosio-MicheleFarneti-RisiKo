package it.unibo.risiko.controller;

import java.awt.Dimension;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.Card;
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
 */
public class GameController implements GameViewObserver , InitialViewObserver{
    private final GameManager gameManager;
    private GameView view;
    private static final String FILE_SEPARATOR = File.separator;
    private static final String saveGamesFilePath = "resources" + FILE_SEPARATOR + "savegames"
            + FILE_SEPARATOR + "savegames.json";
    private static final String mapImagePath = FILE_SEPARATOR + "maps" + FILE_SEPARATOR + "standardMap.png";
    private static final String resourcesPackageString = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR;
    private Optional<Territory> attackerTerritory = Optional.empty();
    private Optional<Territory> defenderTerritory = Optional.empty();
    private AttackPhase attackPhase;
    private GameFrame gameFrame;

    /**
     * Initialization of the Game controller with a GameManager as model field and a
     * Java Swing view
     * 
     * @author Michele Farneti
     */
    public GameController() {
        this.gameFrame = new GameFrame(this);
        gameManager = new GameManagerImpl(resourcesPackageString + saveGamesFilePath, resourcesPackageString);
    }

    
    @Override
    public void startGameWindow(Integer width, Integer height) {
        this.view = new GameViewImpl(width,height,resourcesPackageString);
        this.view.start();
        this.view.setObserver(this); 
    }

    @Override
    public void initializeNewGame() {
        view.showInitializationWindow(gameManager.getAvailableMaps());
    }

   @Override
    public void setupGameView() {
        view.showGameWindow(gameManager.getCurrentGame().get().getMapName());
        view.showTanks(gameManager.getCurrentGame().get().getTerritoriesList().stream().map(t -> t.getTerritoryName())
                .toList());
        showTurnIcons();
        redrawView();
    }

    /**
     * Updates the view in such a way to show the players icons
     * 
     * @author Michele Farneti
     */
    private void showTurnIcons() {
        for (int i = 0; i < gameManager.getCurrentGame().get().getPlayersList().size(); i++) {
            var player = gameManager.getCurrentGame().get().getPlayersList().get(i);
            this.view.showTurnIcon(player.getColor_id(), i, player.isAI());
        }
    }

    @Override
    public void skipTurn() {
        if (gameManager.getCurrentGame().get().nextTurn()) {
            resetAttack();
            view.enableMovements(false);
            view.setCurrentPlayer(currentPlayer().get().getColor_id(), currentPlayer().get().getArmiesToPlace());
            redrawView();
            view.enableAttack(false);
            view.enableSkip(false);
        }
    }

    /**
     * Resets the current territories set as attacker and defender
     * 
     * @author Michele Farneti
     */
    private void resetAttack() {
        attackerTerritory.ifPresent( t -> view.resetFightingTerritory(t.getTerritoryName()));
        defenderTerritory.ifPresent( t -> view.resetFightingTerritory(t.getTerritoryName()));
        attackerTerritory = Optional.empty();
        defenderTerritory = Optional.empty();
        redrawView();
    }

    @Override
    public void territorySelected(String territoryName) {
        var territory = getTerritoryFromString(territoryName);
        switch (gameManager.getCurrentGame().get().getGameStatus()) {
            case TERRITORY_OCCUPATION:
                gameManager.getCurrentGame().get().placeArmies(territory, 1);       
                break;
            case ARMIES_PLACEMENT:
                gameManager.getCurrentGame().get().placeArmies(territory, 1);
                if(gameManager.getCurrentGame().get().getGameStatus()==GameStatus.READY_TO_ATTACK){
                    view.enableMovements(true);
                };
                break;
            case ATTACKING:
                if(currentPlayerOwns(territory) && getTerritoryFromString(territoryName).getNumberOfArmies() > 1){
                    setFighter(territoryName, true);
                }
                else if (defenderTerritory.isEmpty() && attackerTerritory.isPresent()){
                    if(gameManager.getCurrentGame().get().areTerritoriesNear(attackerTerritory.get(),getTerritoryFromString(territoryName))){
                        setFighter(territoryName, false);
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
     * @author Michele Farneti
     *  */
    private void checkWinner(){
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
     * @param territoryName
     * @author Michele Farneti
     */
    private void setFighter(String territoryName, boolean isAttacker) {
        if (isAttacker) {
            resetAttack();
            attackerTerritory = Optional.of(getTerritoryFromString(territoryName));
            view.showFightingTerritory(territoryName, true);
        } else {
            defenderTerritory = Optional.of(getTerritoryFromString(territoryName));
            view.showFightingTerritory(territoryName, false);
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
                        .forEach(t -> view.redrawTank(t.getTerritoryName(),p.getColor_id(), t.getNumberOfArmies())));
        view.setCurrentPlayer(currentPlayer().get().getColor_id(), currentPlayer().get().getArmiesToPlace());
        view.showTarget(currentPlayer().get().getTarget().showTargetDescription());

        switch (gameManager.getCurrentGame().get().getGameStatus()) {
            case READY_TO_ATTACK:
                view.enableAttack(true);
                view.enableSkip(true);
                break;
            case ARMIES_PLACEMENT:
                view.enableAttack(false);
                view.enableSkip(false);
            default:
                break;
        }
    }

    @Override
    public void startNewGame(String mapName, int numberOfStandardPlayers, int numberOfAIPlayers) {
        final GameFactory gameFactory = new GameFactoryImpl(new GameMapImpl(mapName,resourcesPackageString));
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
        this.skipTurn();
    }


    @Override
    public void playCards(String card1, String card2, String card3) {
        Deck deck = gameManager.getCurrentGame().get().getDeck();
        Card firstCard = deck.getCardByTerritoryName(card1, currentPlayer().get()).get();
        Card secondCard = deck.getCardByTerritoryName(card2, currentPlayer().get()).get();
        Card thirdCard = deck.getCardByTerritoryName(card3, currentPlayer().get()).get();
        /*modifica del metodo playCards per cui non viene passato il player */
        deck.playCards(firstCard, secondCard, thirdCard, currentPlayer().get());
    }


    @Override
    public void moveClicked() {
        view.createMoveArmies(currentPlayer().get().getOwnedTerritories().stream().toList());
    }
}