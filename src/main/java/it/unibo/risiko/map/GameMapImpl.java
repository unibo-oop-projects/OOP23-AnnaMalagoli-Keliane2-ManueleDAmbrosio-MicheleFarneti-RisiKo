package it.unibo.risiko.map;

import java.util.List;

import it.unibo.risiko.Territories;
import it.unibo.risiko.Territory;

public class GameMapImpl implements GameMap {

    /**Parameters that match the orginal's game map initial armies distribution.
     * They are wrote this way in order to be re-used to calculate the number of armies for each
     * player even with MAPS with different limits for the number of players.
    */
    private static final int MINIMUM_ARMIES = 20;
    private static final int ARMIES_STEP = 5;

    private final Territories territories;
    private final int maxPlayers;
    private final int minPlayers;
    
    public GameMapImpl(int maxPlayers, int minPlayers,Territories territories){
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.territories = territories;
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
        return territories.getList();
    }

    
    
    
}
