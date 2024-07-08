package it.unibo.risiko.model.map;

import java.io.File;
import java.util.List;

import it.unibo.risiko.model.cards.Deck;

public class GameMapImpl implements GameMap {

    /**Parameters that match the orginal's game map initial armies distribution.
     * They are wrote this way in order to be re-used to calculate the number of armies for each
     * player even with MAPS with different limits for the number of players.
    */
    private static final String FILE_SEPARATOR = File.separator;
    private static final String resourcesPackageString = "build" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    
    private static final int MINIMUM_ARMIES = 20;
    private static final int ARMIES_STEP = 5;

    final Territories territories;
    final int maxPlayers;
    final int minPlayers;
    final String name;
    
    public GameMapImpl(final int maxPlayers,final int minPlayers, final Territories territories, final Deck deck, String name){
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.territories = territories;
        this.name = name;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getStratingArmies(int nplayers) {
        return (MINIMUM_ARMIES + ARMIES_STEP*(nplayers-minPlayers));
    }

    @Override
    public List<Territory> getTerritories() {
        return territories.getListTerritories();
    }

    @Override
    public List<Continent> getContinents() {
        return territories.getListContinents();
    }

    @Override
    public String getName() {
        return name;
    }
}
