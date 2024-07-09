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
    private final String resourcesPackageString;
    
    private static final Integer minPlayers = 2;

    private static final Integer MAX_PLAYERS_SMALL_MAPS = 2;
    private static final Integer MAX_PLAYERS_BIG_MAPS = 6;

    private static final int MINIMUM_ARMIES = 25;
    private static final int ARMIES_STEP = 5;

    private Territories territories;
    private Deck deck;
    private final String name;
    private final int maxPlayers;
    

    public GameMapImpl(String mapName, String resourcesPackageString){
        this.name = mapName;
        this.resourcesPackageString = resourcesPackageString;
        this.territories = new Territories(buildResourceLocator("territories.txt")); 
        this.deck = new DeckImpl(buildResourceLocator("cards.txt")); 

        if(this.territories.getListContinents().size()<= 3){
            maxPlayers = MAX_PLAYERS_SMALL_MAPS ;
        }else{
            maxPlayers = MAX_PLAYERS_BIG_MAPS;
        }
    }

    private String buildResourceLocator(String resourceName){
        return resourcesPackageString + "maps"+ FILE_SEPARATOR + name + FILE_SEPARATOR + resourceName;
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
