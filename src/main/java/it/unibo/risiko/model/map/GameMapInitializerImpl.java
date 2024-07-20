package it.unibo.risiko.model.map;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.risiko.model.objective.ConquerContinentTarget;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.DestroyPlayerTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.objective.TargetType;
import it.unibo.risiko.model.player.Player;

/**
 * Implementation of a gameMapInitializer.
 * 
 * @author Michele Farneti
 */
public class GameMapInitializerImpl implements GameMapInitializer {

    private static final double MIN_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.6;
    private static final double MAX_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.8;
    private static final String FILE_SEPARATOR = File.separator;
    private final String resourcesPackageString;

    private static final int MINIMUM_ARMIES = 10;
    private static final int MINIMUM_ARMIES_PER_TERRITORY = 1;
    private static final int ARMIES_STEP = 5;

    private final Random randomNumberGenerator = new Random();

    private final String mapName;
    private final int maxPlayers;

    /**
     * Basic constructor for the game map Initializer for a given map.
     * 
     * @param mapName                The name of the map.
     * @param resourcesPackageString The path to reach the rources folder.
     */
    public GameMapInitializerImpl(final String mapName, final String resourcesPackageString) {
        this.mapName = mapName;
        this.resourcesPackageString = resourcesPackageString;
        maxPlayers = getMaxPlayers(buildResourceLocator());
    }

    /**
     * Builds a path string for a specific resource for specific map.
     * 
     * @param resourceName
     * @return A Path string.
     */
    private String buildResourceLocator(final String resourceName) {
        return resourcesPackageString + FILE_SEPARATOR + "maps" + FILE_SEPARATOR + mapName + FILE_SEPARATOR
                + resourceName;
    }

    /**
     * 
     * @return A Path string for the map resources folder
     */
    private String buildResourceLocator() {
        return buildResourceLocator("");
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getStartingArmies(final int nplayers) {
        return MINIMUM_ARMIES + ARMIES_STEP * (maxPlayers - nplayers);
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    @Override
    public String getDeckPath() {
        return buildResourceLocator("cards.txt");
    }

    @Override
    public String getTerritoriesPath() {
        return buildResourceLocator("territories.txt");
    }

    @Override
    public int minimumArmiesPerTerritory() {
        return MINIMUM_ARMIES_PER_TERRITORY;
    }

    @Override
    public Target generateTarget(final Integer player, final List<Player> players, final Territories territories) {
        switch (TargetType.randomTargetType()) {
            case PLAYER:
                return new DestroyPlayerTarget(players.get(player), players.get(
                        (player + randomNumberGenerator.nextInt(1, players.size())) % players.size()));
            case TERRITORY:
                return new ConquerTerritoriesTarget(players.get(player), randomNumberGenerator.nextInt(
                        Math.toIntExact(
                                Math.round(territories.getListTerritories().size()
                                        * MIN_TERRITORIES_TO_CONQUER_PERCENTAGE)),
                        Math.toIntExact(
                                Math.round(territories.getListTerritories().size()
                                        * MAX_TERRITORIES_TO_CONQUER_PERCENTAGE))));
            case CONTINENT:
                return new ConquerContinentTarget(players.get(player),
                        territories.getListContinents()
                                .get(randomNumberGenerator.nextInt(territories.getListContinents().size())));
            default:
                return new ConquerTerritoriesTarget(players.get(player), territories.getListTerritories().size());
        }
    }

    /**
     * Given a path for a map in the file system, returns it's max players value by
     * checking inside its territories file if the are more ore less territories
     * than a certain limit.
     * 
     * @param mapPath The path for the map's folder in the file system.
     * @return The maxNumberOfPLayers for the map
     */
    public static Integer getMaxPlayers(final String mapPath) {
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

    /**
     * Reads from file thw available maps to be played by checking their related
     * directory.
     * 
     * @param resourcesPath
     * @return A map for the the maps and their max number of players.
     */
    public static Map<String, Integer> getAvailableMaps(final String resourcesPath) {
        final Map<String, Integer> availableMaps = new HashMap<>();
        final var mapsFoldersLocation = resourcesPath + "maps";
        final var mapsFolderPath = Paths.get(mapsFoldersLocation);
        if (Files.exists(mapsFolderPath) && Files.isDirectory(mapsFolderPath)) {
            try {
                for (final var path : Files.list(mapsFolderPath).collect(Collectors.toList())) {
                    final var key = Optional.ofNullable(path.getFileName()).map(Path::toString);
                    final Optional<Integer> value = key.map(k -> getMaxPlayers(mapsFolderPath.resolve(k).toString()));
                    if (key.isPresent() && value.isPresent()) {
                        availableMaps.put(key.get(), value.get());
                    }
                }
            } catch (IOException e) {
                return Collections.emptyMap();
            }
        }
        return availableMaps;
    }
}
