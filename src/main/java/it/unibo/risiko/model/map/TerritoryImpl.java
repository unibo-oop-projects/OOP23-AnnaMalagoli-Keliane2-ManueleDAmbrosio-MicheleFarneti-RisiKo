package it.unibo.risiko.model.map;

import java.util.List;


/**
 * Implementation of the interface Territory.
 * 
 * @author Anna Malagoli
 */
public class TerritoryImpl implements Territory {
    private final String name;
    private int numberOfArmies;
    private final List<String> listNearTerritories;
    private final String continent;
    private String playerId;

    /**
     * Constructor to set the parameters of the territory with the input parameters
     * and to set the initial number of armies to zero.
     * 
     * @param name is the name of the territory
     * @param continent is the name of the continent
     * @param listNearTerritories is the list of the neighboring territories
     */
    public TerritoryImpl(final String name, final String continent, final List<String> listNearTerritories) {
        this.name = name;
        this.continent = continent;
        this.listNearTerritories = List.copyOf(listNearTerritories);
        this.numberOfArmies = 0;
    }

    /**
     * Method to extract the name of the territory.
     * 
     * @return the name of the territory
     */
    @Override
    public String getTerritoryName() {
        return this.name;
    }

    /**
     * Method to get the number positioned in the territory.
     * 
     * @return the number of armies
     */
    @Override
    public int getNumberOfArmies() {
        return this.numberOfArmies;
    }

    /**
     * Method to get the player that owns the territory.
     * 
     * @return the id of the player
     */
    public String getPlayer() {
        return this.playerId;
    }

    /**
     * Method to set the owner of the territory.
     * 
     * @param player is the player id
     */
    public void setPlayer(final String player) {
        this.playerId = player;
    }

    /**
     * Method used to add a specified number of armies in the territory.
     * 
     * @param number is the number of armies that has to be added
     */
    @Override
    public void addArmies(final int number) {
        this.numberOfArmies += number;
    }

    /**
     * Method used to remove a specified number of armies in the territory.
     * 
     * @param number is the number of armies that has to be removed
     */
    @Override
    public void removeArmies(final int number) {
        this.numberOfArmies -= number;
    }

    /**
     * Method to get the list of the territories that are near the territory.
     * 
     * @return the list of the neighboring territories of a territory
     */
    @Override
    public List<String> getListOfNearTerritories() {
        return List.copyOf(this.listNearTerritories);
    }

    /**
     * Method to get the name of the continent to whom the territory belongs.
     * 
     * @return the name of the continent
     */
    @Override
    public String getContinentName() {
        return this.continent;
    }

}
