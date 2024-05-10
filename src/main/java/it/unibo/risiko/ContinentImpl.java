package it.unibo.risiko;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of the interface Continent.
 * @author Anna Malagoli 
 */
public class ContinentImpl implements Continent {

    private final List<Territory> listTerritories = new ArrayList<>();
    private final String name;

    /**
     * Constructor that is used to set the name of the continent created.
     * @param name is the name of the continent
     */
    ContinentImpl(final String name) {
        this.name = name;
    }

    /**
     * Method that returns the continent's name.
     * @return the name of the continent
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the list of territories of a continent.
     * @return the list of territories
     */
    @Override
    public List<Territory> getListTerritories() {
        return List.copyOf(this.listTerritories);
    }

    /**
     * Method used to add a territory in the list of territories of the continent.
     * @param terr is a territory
     */
    @Override
    public void addTerritory(final Territory terr) {
        listTerritories.add(terr);
    } 
}
