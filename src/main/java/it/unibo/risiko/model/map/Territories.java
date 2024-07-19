package it.unibo.risiko.model.map;

import java.util.List;

/**
 * The interface Territories contains all the methods
 * that have to be callable in the game to change and set information about
 * the territories and continents.
 * 
 * @author Anna Malagoli
 */
public interface Territories {

    /**
     * Method used to get the list of all the territories extracted from the text file.
     * @return a copy of the list of territories
     */
    List<Territory> getListTerritories();

    /**
     * Method used to get the list of continents extracted from the text file.
     * @return a copy of the list of territories
     */
    List<Continent> getListContinents();

    /**
     * Method to shuffle the list of territories.
     */
    void shuffle();

    /**
     * Method to add a specified number of armies in a territory.
     * @param territoryName is the name of the territory in which we
     * want to add armies
     * @param numArmies is the number of armies that we want to add in the territory
     */
    void addArmiesInTerritory(String territoryName, int numArmies);

    /**
     * Method to remove a specified number of armies from a territory.
     * @param territoryName is the name of the territory in which we
     * want to remove armies
     * @param numArmies is the number of armies that we want to remove from the territory
     */
    void removeArmiesInTerritory(String territoryName, int numArmies);

    /**
     * Method to verify if the two territories passed in input are adjacent.
     * @param name1 is the name of the first territory
     * @param name2 is the name of the second territory
     * @return true if they are adjacent, or false if they are not adjacent
    */
    boolean territoriesAreNear(String name1, String name2);

    /**
     * Method used to set the owner of a territory.
     * @param terrName is the name of the territory
     * @param playerId is the id of the player
     */
    void setOwner(String terrName, String playerId);

}