package it.unibo.risiko.controller;

import java.io.File;

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
    private static final String FILE_SEPARATOR = File.separator;
    private static final String saveGamesFilePath = FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "savegames" + FILE_SEPARATOR +"savegames.json";
    private static final String mapImagePath = FILE_SEPARATOR + "maps" + FILE_SEPARATOR + "standardMap.png";
    private static final String resourcesPackageString = "build" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    

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
