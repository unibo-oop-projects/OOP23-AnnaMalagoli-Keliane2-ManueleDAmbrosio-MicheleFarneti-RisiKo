package it.unibo.risiko.controller;

import it.unibo.risiko.model.game.GameManager;
import it.unibo.risiko.model.game.GameManagerImpl;
import it.unibo.risiko.view.gameView.GameView;
import it.unibo.risiko.view.gameView.GameViewImpl;
import it.unibo.risiko.view.gameView.GameViewObserver;

/**
 * Controller class for the risiko game, its function is to coordinate view and model.
 * @author Michele Farneti
 */
public class GameController implements GameViewObserver{
    private final GameManager model;
    private final GameView view;
    private static final String saveGamesFilePath = "resources/savegames/savegames.json";
    private static final String mapImagePath = "/maps/standardMap.png";
    private static final String resourcesPackageString = "build/resources/main/it/unibo/risiko";

    /**
    * Initialization of the Game controller with a GameManager as model field and a Java Swing view 
    *@author Michele Farneti
    */
    public GameController(){
        model = new GameManagerImpl(saveGamesFilePath);
        view = new GameViewImpl(1600,900,resourcesPackageString+mapImagePath);
        this.view.setObserver(this);
        this.view.start();
    }    
}
