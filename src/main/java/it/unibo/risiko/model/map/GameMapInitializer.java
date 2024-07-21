package it.unibo.risiko.model.map;

import java.util.List;

import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.player.Player;

/**
 * The gameMap class manages all of the enviroment features of the game map,
 * such us keeping track of the territories'list and other settings.
 * 
 * @author Michele Farneti
 */

public interface GameMapInitializer {

    /**
     * @return The max number of players allowed to play a game in the map at the
     *         same time.
     */
    int getMaxPlayers();

    /**
     * @return The minimum number of armies every territory has to be occupied by.
     */
    int minimumArmiesPerTerritory();

    /**
     * 
     * @return The name of the map set for the initializer.
     */
    String getMapName();

    /**
     * @param nplayers Number of players playing in the map
     * @return The number of armies every player should start with.
     */
    int getStartingArmies(int nplayers);

    /**
     * @return The path to create the Game deck for the given map.
     */
    String getDeckPath();

    /**
     * @return Returns the path where to get the territories for the given map from.
     */
    String getTerritoriesPath();

    /**
     * Generates a random target for the given activePlayer based on the context of
     * the game.
     * 
     * @param activePlayer
     * @param players
     * @param territories
     * @return A random target for a given player
     */
    Target generateTarget(Integer activePlayer, List<Player> players, Territories territories);
}
