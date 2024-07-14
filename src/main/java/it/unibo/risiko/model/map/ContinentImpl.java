package it.unibo.risiko.model.map;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of the interface Continent.
 * @author Anna Malagoli 
 */
public class ContinentImpl implements Continent {

    private final List<Territory> listTerritories = new ArrayList<>();
    private final String name;
    private final int bonusArmies; 

    /**
     * Constructor that is used to set the name of the continent created
     * and the number of bonus armies associated with a continent.
     * @param name is the name of the continent
     * @param bonusArmies the number of bonus armies of the continent
     */
    public ContinentImpl(final String name, final int bonusArmies) {
        this.name = name;
        this.bonusArmies = bonusArmies;
    }

    /**
     * Method that returns the continent's name.
     * @return the name of the continent
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the list of territories of a continent.
     * @return the list of territories
     */
    public List<Territory> getListTerritories() {
        return List.copyOf(this.listTerritories);
    }

    /**
     * Method used to add a territory in the list of territories of the continent.
     * @param terr is a territory
     */
    public void addTerritory(final Territory terr) {
        listTerritories.add(terr);
    }

    /**
     * Method to get the number of bonus armies that the player
     * gaines if he has all the territory of the continent.
     * @return the number of bonus armies
     */
    public int getBonusArmies() {
        return this.bonusArmies;
    } 
}
