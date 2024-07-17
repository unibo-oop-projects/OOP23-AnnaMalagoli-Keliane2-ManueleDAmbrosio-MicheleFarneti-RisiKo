package it.unibo.risiko.model.map;

import java.io.File;
import java.util.List;

public class GameMapInitializerImpl implements GameMapInitializer {

    /**
     * Parameters that match the orginal's game map initial armies distribution.
     * They are wrote this way in order to be re-used to calculate the number of
     * armies for each
     * player even with MAPS with different limits for the number of players.
     */
    private static final String FILE_SEPARATOR = File.separator;
    private final String resourcesPackageString;

    private static final int MINIMUM_ARMIES = 20;
    private static final int ARMIES_STEP = 5;

    private Territories territories;
    private final String name;
    private final int maxPlayers;

    public GameMapInitializerImpl(String mapName, String resourcesPackageString) {
        this.name = mapName;
        this.resourcesPackageString = resourcesPackageString;
        this.territories = new Territories(buildResourceLocator(FILE_SEPARATOR + "territories.txt"));
        territories.shuffle();
        maxPlayers = GameMapInitializer.getMaxPlayers(buildResourceLocator());
    }

    private String buildResourceLocator(String resourceName) {
        return resourcesPackageString + FILE_SEPARATOR + "maps" + FILE_SEPARATOR + name + FILE_SEPARATOR + resourceName;
    }

    private String buildResourceLocator() {
        return buildResourceLocator("");
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getStratingArmies(int nplayers) {
        return (MINIMUM_ARMIES + ARMIES_STEP * (maxPlayers - nplayers));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDeckPath() {
        return buildResourceLocator("cards.txt");
    }

    @Override
    public String getTerritoriesPath(){
        return buildResourceLocator("territories.txt");
    }
}
