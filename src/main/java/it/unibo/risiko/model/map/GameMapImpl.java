package it.unibo.risiko.model.map;

import java.io.File;
import java.util.List;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;

public class GameMapImpl implements GameMap {

    /**Parameters that match the orginal's game map initial armies distribution.
     * They are wrote this way in order to be re-used to calculate the number of armies for each
     * player even with MAPS with different limits for the number of players.
    */
    private static final String FILE_SEPARATOR = File.separator;
    private static final String resourcesPackageString = "build" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "it" + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko";
    
    private static final Integer minPlayers = 2;

    private static final Integer MAX_PLAYERS_SMALL_MAPS = 2;
    private static final Integer MAX_PLAYERS_BIG_MAPS = 6;

    private static final int MINIMUM_ARMIES = 20;
    private static final int ARMIES_STEP = 5;

    private Territories territories;
    private Deck deck;
    private final String name;
    private final int maxPlayers;
    

    public GameMapImpl(String mapName){
        this.territories = new Territories(buildResourceLocator(mapName)); 
        this.deck = new DeckImpl(buildResourceLocator(mapName)); 
        this.name = mapName;

        if(this.territories.getListContinents().size()<= 3){
            maxPlayers =2 ;
        }else{
            maxPlayers = 6;
        }
    }

    private String buildResourceLocator(String resourceName){
        return resourcesPackageString + FILE_SEPARATOR + "maps"+ FILE_SEPARATOR + name + FILE_SEPARATOR + resourceName + "s" + FILE_SEPARATOR + resourceName;
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

    @Override
    public Deck getDeck() {
        return deck;
    }
}
