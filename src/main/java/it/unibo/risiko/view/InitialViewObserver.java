package it.unibo.risiko.view;

public interface InitialViewObserver {
    /**
     * Tells the controller to show the game initiliaziton window in the gameView.
     * 
     * @author Michele Farneti
     */
    void initializeNewGame();

    /**
     * Tells the controller to setUp the game phase window in the gameView.
     * 
     * @author Michele Farneti
     */
    void setupGameView();

    /**
     * Start a new window used for the game playing phase and eventually for game
     * initialization
     * 
     * @param width  Window's width
     * @param height Window's height
     */
    void startGameWindow(Integer width, Integer height);
}
