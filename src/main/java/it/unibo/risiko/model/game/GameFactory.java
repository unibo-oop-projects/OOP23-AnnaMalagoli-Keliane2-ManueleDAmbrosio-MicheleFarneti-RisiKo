package it.unibo.risiko.model.game;

import it.unibo.risiko.model.player.Player;

/**
 * This interface enables a dynamic creation of a new Game, allowing to add and remove players before initializating the game.
 * The instance of game created is also initialized and ready to be played.
 * @author Michele Farneti
 */
public interface GameFactory {
    /**
     * @param player Adds a new player to the game.
     * @return True if the limit of the number of players isn't already reached.
     */
    boolean addNewPlayer(Player player);

    /**
     * Returns new game instance already initializating targets for every player and assigning territories
     * @return A new Game ready to be played,.
     */
    Game initializeGame();
}
