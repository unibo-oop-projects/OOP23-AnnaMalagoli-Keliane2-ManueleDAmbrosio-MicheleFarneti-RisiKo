package it.unibo.risiko.map;

import java.util.List;
import it.unibo.risiko.Territory;

/**
 * The gameMap class manages all of the enviroment features of the game map, such us
 * keeping track of the territories'list and other settings.
 * @author Michele Farneti
 */

public interface GameMap {

    /**
     * @return The max number of players allowed to play a game in the map at the same time.
     */
    int getMaxPlayers();

    /**
     * @param nplayers Number of players playing in the map
     * @return The number of armies every player should start with.
     */
    int getStratingArmies(int nplayers);

    /**
     * @return The list of territories for the Map
     */
    List<Territory> getTerritories();
}
