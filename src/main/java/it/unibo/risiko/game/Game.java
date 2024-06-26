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
     * @return Allows the game to go on by updating the active players, but only after checking if he made all the actions needed to skip the turn.
     */
    boolean nextTurn();

    /**
     * Initializises the game following the basic Risiko rules, Every player
     * gets a randomly generated target, the set of owned territories and the right amount of armies.
     */
    void startGame();

    /**
     * @return The List of the players currently playing the game.
     */
    List<Player> getPlayersList();

    /**
     * @return The current Stage of the game
     */
    GameStatus getGameStatus();
}
