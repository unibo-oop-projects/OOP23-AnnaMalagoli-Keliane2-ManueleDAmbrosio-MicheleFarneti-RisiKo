package it.unibo.risiko.view.gameView;

import java.util.List;
import it.unibo.risiko.model.map.Territory;

/**
 * The view interface for the game phase of the application
 * @author Michele Farneti
 */
public interface GameView {

    /**
     * Sets the observer for the view
     * @param gameController Observer 
     */
    void setObserver(GameViewObserver gameController);

    /**
     * Starts the view  by setting up all of the main components
     */
    void start();

    /**
     * Activates the placing and the viewing of the tanks buttons over their respective territories
     * @param territories Territories list of the game map
     */
    void showTanks(List<Territory> territories);
    
}
