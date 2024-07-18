package it.unibo.risiko.model.map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.player.Player;

/**
 * The gameMap class manages all of the enviroment features of the game map,
 * such us
 * keeping track of the territories'list and other settings.
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
     * @return The name of the map set fore the initializer.
     */
    String getMapName();

    /**
     * @param nplayers Number of players playing in the map
     * @return The number of armies every player should start with.
     */
    int getStartingArmies(int nplayers);

    /**
     * 
     * @param mapPath The path for the map's folder in the file system.
     * @return The maxNUmberOfPLayers for the map
     */
    public static Integer getMaxPlayers(String mapPath) {
        final Integer MAX_PLAYERS_SMALL_MAPS = 2;
        final Integer MAX_PLAYERS_BIG_MAPS = 6;
        final Integer BIG_MAP_LIMIT = 30;
        try {
            var territoriesNumber = Files.lines(Path.of(mapPath + File.separator + "territories.txt")).count() / 2;
            if (territoriesNumber >= BIG_MAP_LIMIT) {
                return MAX_PLAYERS_BIG_MAPS;
            } else {
                return MAX_PLAYERS_SMALL_MAPS;
            }
        } catch (IOException e) {
        }
        return MAX_PLAYERS_SMALL_MAPS;
    }

    @SuppressFBWarnings
    public static Map<String, Integer> getAvailableMaps(String resourcesPath) {
        Map<String, Integer> availableMaps = new HashMap<>();
        var mapsFoldersLocations = resourcesPath + "maps";
        try {
            for (Path p : Files.list(Path.of(mapsFoldersLocations)).collect(Collectors.toList())) {
                var key = Optional.ofNullable(p.getFileName().toString());
                var value = Optional.ofNullable(GameMapInitializer.getMaxPlayers(resourcesPath + "maps" + File.separator + p.getFileName().toString()));
                key.ifPresent(k -> value.ifPresent( v-> availableMaps.put(k,v)));
            }
        } catch (IOException e) {}
        return availableMaps;
    }

    /**
     * @return The path to create the Game deck for the given map
     */
    public String getDeckPath();

    /**
     * @return Returns the path where to get the territories for the given map from
     */
    public String getTerritoriesPath();

    /**
     * Generates a random target for the given activePlayer based on the context of the game
     * @param activePLayer
     * @param players
     * @param territories
     * @return
     */
    Target generateTarget(final Integer activePLayer,final List<Player> players, final Territories territories);
}
