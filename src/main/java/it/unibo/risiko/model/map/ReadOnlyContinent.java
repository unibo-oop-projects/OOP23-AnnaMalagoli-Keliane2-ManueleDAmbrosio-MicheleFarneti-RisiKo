package it.unibo.risiko.model.map;

import java.util.List;

/**
 * An interface to avoid Continent external modifications
 * 
 * @author Keliane Nana
 */
public interface ReadOnlyContinent {

    /**
     * Reads the continent's name
     * 
     * @return the name of the continent
     */
    String getName();

    /**
     * Helps to get the list containing all the territories of a continent
     * 
     * @return the continent Territory's list
     */
    List<Territory> getListTerritories();

}
