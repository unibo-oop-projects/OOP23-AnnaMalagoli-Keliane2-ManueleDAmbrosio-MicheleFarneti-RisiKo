package it.unibo.risiko;

import java.util.List;

/**
 * The interface Territory contains all the methods
 * that have to be callable in a territory
 * 
 * @author Anna Malagoli
 */
public interface Territory {
    /**
     * This method is used to get the name of
     * the territory
     * 
     * @return the name of the territory in which the method is called
     */
    String getTerritoryName();

    /**
     * This method is used to get the name of
     * the continent of the territory
     * 
     * @return the name of the continent
     */
    String getContinetName();

    /**
     * This method is used to get the actual number of 
     * armies that are in a territory
     * 
     * @return the number of armies that are in the territory
     */
    int getNumberOfArmies();

    /**
     * This method is used to set the new number of
     * the positioned armies in the territory by removing 
     * the specified number that is passed as argument
     * 
     * @param number - int value that is the actual 
     * number of armies in the territory 
     */
    void removeArmies(int number);

    void addArmies(int number);

    /**
     * This method is used to get, in a list, all the names of the
     * territories that are close to the one in which the method is called
     *  
     * @return the list of the neighboring territories of the territory
     */
    List<String> getListOfNearTerritories();
}
