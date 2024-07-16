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
    

    private static final int MINIMUM_ARMIES = 20;
    private static final int ARMIES_STEP = 5;

    private Territories territories;
    private final String name;
    private final int maxPlayers;
    

    public GameMapImpl(String mapName, String resourcesPackageString){
        this.name = mapName;
        this.resourcesPackageString = resourcesPackageString;
        this.territories = new Territories(buildResourceLocator(FILE_SEPARATOR +"territories.txt")); 
        territories.shuffle();
        maxPlayers = GameMap.getMaxPlayers(buildResourceLocator());
    }

    private String buildResourceLocator(String resourceName){
        return resourcesPackageString + FILE_SEPARATOR + "maps"+ FILE_SEPARATOR + name + FILE_SEPARATOR + resourceName;
    }

    private String buildResourceLocator(){
        return buildResourceLocator("");
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getStratingArmies(int nplayers) {
        return (MINIMUM_ARMIES + ARMIES_STEP*(maxPlayers-nplayers));
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
    public String getDeckPath() {
        return buildResourceLocator("cards.txt");
    }

    @Override
    public boolean areTerritoriesNear(String territory1, String territory2) {
        ///DA SISTEMARE
        throw new UnsupportedOperationException("Unimplemented method 'areTerritoriesNear'");
    }

    @Override
    public void addArmies(String territory, int nArmies) {
        territories.addArmiesInTerritory(territory, nArmies);
    }

    @Override
    public void removeArmies(String territory, int numberOfMovingArmies) {
        territories.removeArmiesInTerritory(territory, numberOfMovingArmies);
    }

    @Override
    public void setOwner(String territory, String color_id) {
        ///DA SISTEMARE
        throw new UnsupportedOperationException("Unimplemented method 'setOwner'");
    }
}
