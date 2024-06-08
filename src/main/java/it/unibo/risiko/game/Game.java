package it.unibo.risiko.game;

import java.util.List;

import it.unibo.risiko.player.Player;

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

    /**
     * @return The List of the players currently playing the game.
     */
    List<Player> getPlayersList();

}
