package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

/**
 * This interface manages all of the basic functions that are offered during the
 * actual risiko games!
 * It is going to manage all of the players actions and the game loop itself.
 * 
 * @author Michele Farneti
 */

public interface GameLoopManager {

    /**
     * @param activePlayer The index of the current player
     * @param playersCount The amount of player currentlyt in the game
     * @return The index of the next player
     */
    Integer nextPlayer(Integer activePlayer, Integer playersCount);

    /**
     * Allows the game to go on by updating the active players, but only after
     * checking if he made all the actions needed to skip the turn.
     * 
     * @return True if the turn was succesfuly skipped
     */
    boolean skipTurn(final Integer player, final List<Player> players, final Territories territories,
            final GameStatus gameStatus);

    /**
     * @return The current Stage of the game
     */
    GameStatus getGameStatus();

    /**
     * @param
     * @return True if one of the players has reached is target
     */
    boolean isGameOver(Integer playerIndex, final List<Player> players, final Territories territories);

    /**
     * 
     * @return The index of the active player
     */
    Integer getActivePlayer();

    /**
     * 
     * @return a number indicating how many times the game loop has reached the
     *         first player
     */
    Long getTurnsCount();

}
