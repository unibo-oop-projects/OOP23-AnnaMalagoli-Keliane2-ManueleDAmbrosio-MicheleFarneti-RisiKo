package it.unibo.risiko.controller;

import it.unibo.risiko.game.GameManager;
import it.unibo.risiko.game.GameManagerImpl;
import it.unibo.risiko.view.GameView;
import it.unibo.risiko.view.GameViewImpl;
import it.unibo.risiko.view.GameViewObserver;

/**
 * Controller class for the risiko game, its function is to coordinate view and model.
 * @author Michele Farneti
 */
public class GameController implements GameViewObserver{
    private final GameManager model;
    private final GameView view;

    /**
    * Initialization of the Game controller with a GameManager as model field and a Java Swing view 
    *@author Michele Farneti
    */
    public GameController(){
        model = new GameManagerImpl();
        view = new GameViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }
    
}
