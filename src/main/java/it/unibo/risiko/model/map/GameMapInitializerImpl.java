package it.unibo.risiko.model.map;

import java.io.File;
import java.util.List;
import java.util.Random;

import it.unibo.risiko.model.objective.ConquerContinentTarget;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.DestroyPlayerTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.objective.TargetType;
import it.unibo.risiko.model.player.Player;

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

    public GameMapInitializerImpl(final String mapName, final String resourcesPackageString) {
        this.mapName = mapName;
        this.resourcesPackageString = resourcesPackageString;
        maxPlayers = GameMapInitializer.getMaxPlayers(buildResourceLocator());
    }

    private String buildResourceLocator(final String resourceName) {
        return resourcesPackageString + FILE_SEPARATOR + "maps" + FILE_SEPARATOR + mapName + FILE_SEPARATOR
                + resourceName;
    }

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
}
