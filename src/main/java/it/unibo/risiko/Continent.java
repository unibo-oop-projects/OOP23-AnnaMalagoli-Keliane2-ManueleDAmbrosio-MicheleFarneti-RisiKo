package it.unibo.risiko;

import java.util.List;
/**
 * Interface used to establish the methods that a continet has.
 * @author Anna Malagoli 
 */
public interface Continent {

    /**
     * Method used to extract the name of the continent.
     * @return the name of the continent
     */
    String getName();

    /**
     * Method that returns the list of the territories which are in the continent.
     * @return the list of the territories
     */
    List<String> getListTerritories();

    /**
     * Method used to add a territory in the list of the continent.
     * @param terr is the name of the territory that has to be added in the list
     */
    void addTerritory(String terr);
}
