package it.unibo.risiko.model.map;

import java.util.List;

/**
 * The interface Territory contains all the methods
 * that have to be callable in a territory.
 * 
 * @author Anna Malagoli
 */
public interface Territory {
    /**
     * This method is used to get the name of
     * the territory.
     * 
     * @return the name of the territory
     */
    String getTerritoryName();

    /**
     * This method is used to get the name of
     * the continent of the territory.
     * 
     * @return the name of the continent
     */
    String getContinentName();

    /**
     * This method is used to get the actual number of 
     * armies that are in a territory.
     * 
     * @return the number of armies that are in the territory
     */
    int getNumberOfArmies();

    /**
     * This method is used to remove a specified number of
     * the positioned armies in the territory.
     * 
     * @param number - int value that is the number of armies
     * tha have to be removed
     */
    void removeArmies(int number);

    /**
     * This method is used to add a specified number of
     * the positioned armies in the territory.
     * 
     * @param number - int value that is the number of armies
     * tha have to be added
     */
    void addArmies(int number);

    /**
     * This method is used to get, in a list, all the names of the
     * territories that are close to the one in which the method is called.
     * @return the list of the neighboring territories of the territory
     */
    List<String> getListOfNearTerritories();

    String getPlayer();

    void setPlayer(final String player);
}
