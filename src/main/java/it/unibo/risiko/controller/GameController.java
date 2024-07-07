package it.unibo.risiko.controller;

import java.io.File;
import java.util.List;

import it.unibo.risiko.model.game.GameImpl;
import it.unibo.risiko.model.game.GameManager;
import it.unibo.risiko.model.game.GameManagerImpl;
import it.unibo.risiko.model.map.GameMapImpl;
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

        // PlayerFactory pf = new SimplePlayerFactory();
        // var provaplayer = pf.createStandardPlayer("red", 0);
        // var provaplayer2 = pf.createAIPlayer("blue", 0);
        // this.view.showTurnIcons(List.of(provaplayer,provaplayer2));
        // this.view.setCurrentPlayer(provaplayer2);
        //Game provagame = new GameImpl(new GameMapImpl(0, 0, null, null, saveGamesFilePath));
        view.showTanks(null);
    }

    @Override
    public void skikpTurn() {
        if(gameManager.getCurrentGame().get().nextTurn()){
            view.setCurrentPlayer(gameManager.getCurrentGame().get().getCurrentPlayer());
        }
    }
}
