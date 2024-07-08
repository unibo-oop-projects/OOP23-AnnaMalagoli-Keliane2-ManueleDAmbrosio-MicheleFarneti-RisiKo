package it.unibo.risiko.controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.Optional;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.game.AttackPhase;
import it.unibo.risiko.model.game.AttackPhaseImpl;
import it.unibo.risiko.model.game.Game;
import it.unibo.risiko.model.game.GameFactoryImpl;
import it.unibo.risiko.model.game.GameImpl;
import it.unibo.risiko.model.game.GameManager;
import it.unibo.risiko.model.game.GameManagerImpl;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.GameMapImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.view.gameView.GameView;
import it.unibo.risiko.view.gameView.GameViewImpl;
import it.unibo.risiko.view.gameView.GameViewObserver;

/**
 * Controller class for the risiko game, its function is to coordinate view and model.
 * @author Michele Farneti
 */
public class GameController implements GameViewObserver{
    private final GameManager gameManager;
    private final GameView view;
    private static final String FILE_SEPARATOR = File.separator;
    private static final String saveGamesFilePath = FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "savegames" + FILE_SEPARATOR +"savegames.json";
    private static final String mapImagePath = FILE_SEPARATOR + "maps" + FILE_SEPARATOR + "standardMap.png";
    private static final String resourcesPackageString = "build" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    private Optional<Territory> attackerTerritory = Optional.empty();
    private Optional<Territory> defenderTerritory = Optional.empty();

    /**
    * Initialization of the Game controller with a GameManager as model field and a Java Swing view 
    *@author Michele Farneti
    */
    public GameController(){
        gameManager = new GameManagerImpl(resourcesPackageString+mapImagePath);
        view = new GameViewImpl(1600,900,resourcesPackageString+mapImagePath);
        this.view.setObserver(this);
        this.view.start();
        setupView();
    }
    
    /**
     * Setups the view with all of the infos from the gameManager's current game
     * @author Michele Farneti
     */
    private void setupView(){
        //this.view.showTanks(this.gameManager.getCurrentGame().get().);
        // if(this.gameManager.getCurrentGame().isPresent()){
        //     this.view.showTurnIcons(this.gameManager.getCurrentGame().get().getPlayersList());
        // }

        //PlayerFactory pf = new SimplePlayerFactory();
        //var provaplayer = pf.createStandardPlayer("red", 0);
        //var provaplayer2 = pf.createAIPlayer("blue", 0);
        // this.view.setCurrentPlayer(provaplayer2);
        
        //Territory provaTerritory = new TerritoryImpl("India", "South america", new LinkedList<>());
        // System.out.println(provaTerritory);
        //view.showTanks(List.of(provaTerritory).stream().map(t -> t.getTerritoryName()).toList());
        //showTurnIcons();
        view.gameOver("red");
    }

    /**
     * Updates the view in such a way to show the players icons
     * @author Michele Farneti
     */
    private void showTurnIcons() {
        for(int i = 0; i < gameManager.getCurrentGame().get().getPlayersList().size(); i++){
            var player = gameManager.getCurrentGame().get().getPlayersList().get(i);
            this.view.showTurnIcon(player.getColor_id(), i, player.isAI());
        }
    }

    @Override
    public void skipTurn() {
        if(gameManager.getCurrentGame().get().nextTurn()){
            view.setCurrentPlayer(currentPlayer().get().getColor_id(), currentPlayer().get().getArmiesToPlace());
        }
    }

    /**
     * Resets the current territories set as attacker and defender
     * @author Michele Farneti
     */
    private void resetAttack(){
        view.resetFightingTerritories(attackerTerritory.get().getTerritoryName(),defenderTerritory.get().getTerritoryName());
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
                break;
            case ATTACK:
                if(attackerTerritory.isEmpty() && currentPlayerOwns(territory)){
                    setFighter(territoryName,true);
                }else{
                    if(currentPlayerOwns(getTerritoryFromString(territoryName))){
                        setFighter(territoryName,true);
                    }else if(defenderTerritory.isEmpty() && !currentPlayerOwns(getTerritoryFromString(territoryName)) ){
                        setFighter(territoryName, false);
                        startAttack();
                        resetAttack();
                        if(gameManager.getCurrentGame().get().gameOver()){
                            this.view.gameOver(currentPlayer().get().getColor_id());
                        }
                    }
                }
                break;
            default: 
                break;
        }
    }

    /**
     * Creates a new attack once attacker territory and defender territory are correctly set,
     * then updates the view.
     * @author 
     */
    private void startAttack() {
        // Optional<Territory> attackerTerritory
        // Optional<Territory> defenderTerritory
        currentPlayer().get();
        
    }

    /**
     * 
     * @return If present, the current player of the current game, otherwise an empy optional.
     * @author Michele Farneti
     */
    private Optional<Player> currentPlayer(){
        if(gameManager.getCurrentGame().isPresent()){
            return Optional.of(gameManager.getCurrentGame().get().getCurrentPlayer());
        }
        else{
            return Optional.empty();
        }
    }

    /**
     * 
     * @param territory
     * @return The owner of the territory in the current game
     * @author Michele Farneti
     */
    private Player getOwner(Territory territory){
        return gameManager.getCurrentGame().get().getOwner(territory);
    }

    /**
     * The territory passed as argument is set as AttackerTerritory and is Higligted by the GUI
     * @param territoryName
     * @author Michele Farneti
     */
    private void setFighter(String territoryName, boolean isAttacker){
        if(isAttacker){
            attackerTerritory = Optional.of(getTerritoryFromString(territoryName));
            view.showFightingTerritory(territoryName, true);
        }else{
            defenderTerritory= Optional.of(getTerritoryFromString(territoryName));
            view.showFightingTerritory(territoryName, false);
        }
    }

    /**
     * Finds a terrritory in the currentGame territories list starting from its name
     * @param territoryName
     * @return The territory with the given name
     * @author Michele Farneti
     */
    private Territory getTerritoryFromString(String territoryName){
        return gameManager.getCurrentGame().get().getTerritoriesList().stream().filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().get();
    }

    /**
     * Checks if the current player of the current game owns a territory
     * @param territory
     * @return true if the player owns the territory
     * @author Michele Farneti
     */
    private boolean currentPlayerOwns(Territory territory){
        return currentPlayer().get().isOwnedTerritory(territory);
    }
    
    /**
     * Updates the game view by changhing the map and the turns
     * @author Michele Farneti
     */
    private void redrawView(){
        gameManager.getCurrentGame().get().getPlayersList().stream()
            .forEach(p -> p.getOwnedTerritories().stream()
                .forEach(t -> view.redrawTank(t.getTerritoryName(), p.getColor_id(), t.getNumberOfArmies())));
        view.setCurrentPlayer(currentPlayer().get().getColor_id(), currentPlayer().get().getArmiesToPlace());
    }

    @Override
    public void setMapName(String mapName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMapName'");
    }

    @Override
    public void setStandardPlayers(int numberOfStandardPlayers) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStandardPlayers'");
    }

    @Override
    public void setAIPlayers(int numberOfAIPlayers) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAIPlayers'");
    }
}
