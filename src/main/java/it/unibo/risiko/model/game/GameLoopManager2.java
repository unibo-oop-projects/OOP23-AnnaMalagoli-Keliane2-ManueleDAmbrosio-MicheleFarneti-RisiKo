package it.unibo.risiko.model.game;

public interface GameLoopManager2 {
    /**
     * 
     * @return The current gameStatus saved in the game loop manager
     */
    public GameStatus getGameStatus();

    /**
     * 
     * @return The active player index saved in the game loop manager
     */
    public Integer getActivePlayer();
}
