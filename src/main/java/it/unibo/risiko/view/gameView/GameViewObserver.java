package it.unibo.risiko.view.gameView;

import it.unibo.risiko.model.map.Territory;

/**
 * Observer used by the view to interact with the controller by calling it's void methods
 * @author Michele Farneti
 */
public interface GameViewObserver {
    /**
     * Tells the controller that the user is triyng to skip to the next game phase.
     * @author Michele Farneti
     */
    void skipTurn();

    /**
     * Tells the controller that the user clicked on a territory
     * @author Michele Farneti
     */
    void territorySelected(String territory);

}
