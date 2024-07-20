package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.player.Player;

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

    public boolean doActionIfPossible(List<Player> players, GameActionType placeArmies);
}
