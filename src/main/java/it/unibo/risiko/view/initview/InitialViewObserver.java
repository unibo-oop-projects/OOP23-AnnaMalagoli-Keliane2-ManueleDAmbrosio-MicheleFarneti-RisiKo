package it.unibo.risiko.view.initview;

/**
 * Observer for @InitialView.
 * 
 * @author Michele Farneti
 * @author Keliane Nana
 * 
 */

public interface InitialViewObserver {
    /**
     * Tells the controller to show the game initiliaziton window in the gameView.
     * 
     * @author Michele Farneti
     */
    void initializeNewGame();

    /**
     * Continues from the last saved game.
     * 
     * @author Manuele D'Ambrosio
     */
    void continueGame();

    /**
     * Start a new window used for the game playing phase and eventually for game
     * initialization.
     * 
     * @param width  Window's width
     * @param height Window's height
     * @author Keliane Nana
     */
    void startGameWindow(Integer width, Integer height);
}
