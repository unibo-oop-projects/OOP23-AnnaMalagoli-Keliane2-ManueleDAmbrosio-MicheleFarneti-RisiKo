package it.unibo.risiko.model.map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @return The minimum number of armies every territory has to be occupied by
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
     * Given a path for a map in the file system, returns it's max players value by
     * checking inside its territories file if the are more ore less territories
     * than a certain limit
     * 
     * @param mapPath The path for the map's folder in the file system.
     * @return The maxNumberOfPLayers for the map
     */
    static Integer getMaxPlayers(final String mapPath) {
        final Integer maxPlayersSmallMaps = 2;
        final Integer maxPlayersBigMaps = 6;
        final Integer bigMapLimit = 30;
        try {
            final var territoriesNumber = Files.lines(Path.of(mapPath + File.separator + "territories.txt")).count()
                    / 2;
            if (territoriesNumber >= bigMapLimit) {
                return maxPlayersBigMaps;
            } else {
                return maxPlayersSmallMaps;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    static Map<String, Integer> getAvailableMaps(final String resourcesPath) {
        final Map<String, Integer> availableMaps = new HashMap<>();
        final var mapsFoldersLocations = resourcesPath + "maps";
        try {
            for (final Path path : Files.list(Path.of(mapsFoldersLocations)).collect(Collectors.toList())) {
                final var key = Optional.ofNullable(path.getFileName().toString());
                final var value = Optional.ofNullable(GameMapInitializer
                        .getMaxPlayers(resourcesPath + "maps" + File.separator + path.getFileName().toString()));
                key.ifPresent(k -> value.ifPresent(v -> availableMaps.put(k, v)));
            }
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        return availableMaps;
    }

    /**
     * @return The path to create the Game deck for the given map
     */
    String getDeckPath();

    /**
     * @return Returns the path where to get the territories for the given map from
     */
    String getTerritoriesPath();

    /**
     * Generates a random target for the given activePlayer based on the context of
     * the game
     * 
     * @param activePlayer
     * @param players
     * @param territories
     * @return
     */
    Target generateTarget(Integer activePlayer, List<Player> players, Territories territories);
}
