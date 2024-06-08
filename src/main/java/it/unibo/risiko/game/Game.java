package it.unibo.risiko.game;

/**
 * This interface manages all of the basic functions that are offered during the actual risiko games!
 * It is going to manage all of the players actions and the game loop itself.
 * 
 * @author Michele Farneti
 */

public interface Game {

    /**
     * @return True if the currently active player is allowed to end is turn.
     */
    boolean NextTurn();

}
