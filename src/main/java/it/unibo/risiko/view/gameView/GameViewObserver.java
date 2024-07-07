package it.unibo.risiko.view.gameView;

/**
 * Observer used by the view to interact with the controller by calling it's void methods
 * @author Michele Farneti
 */
public interface GameViewObserver {
    /**
     * Tells the controller that the user is triyng to skip to the next game phase.
     * @author Michele Farneti
     */
    void skikpTurn();
}
