package it.unibo.risiko.game;

import it.unibo.risiko.player.Player;

public interface GameFactory {
    /**
     * @param player Adds a new player to the game.
     * @return True if the limit of the number of players isn't already reached.
     */
    boolean addNewPlayer(Player player);

    /**
     * @return A new Game ready to be played.
     */
    Game initializeGame();

    /**
     * Allows to specify, within the chosen map limits, the number of players for the game.
     * @return True if the number of players is within the map limits
     */
    boolean setPlayersNumber(int nPLayers);

    /**
     * @return The count of the players already setted
     */
    int getPlayersCount();
}
