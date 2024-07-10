package it.unibo.risiko.model.player;

import java.util.List;

import it.unibo.risiko.model.map.Territory;

/**
 * This Class is a easy mode AI player that can make simple
 * decisions about the game and play on its own.
 * 
 * @author Manuele D'Ambrosio
 */

public interface AIBehaviour {

    /**
     * This method is used to get a territory owned
     * by the AI player and which the player wants
     * to attack from. 
     * 
     * @param territoryList - List of the territories in the current map.
     * @return the next territory the AI wants to attack from.
     */
    Territory getNextAttackingTerritory(List<Territory> territoryList);

    /**
     * This method is used to get a territory the AI player
     * can attack from the adjacent attacking territory.
     * 
     * @return the next territory the AI wants to attack.
     */
    Territory getNextAttackedTerritory();

    /**
     * This methos is used to get the number of armies
     * that are attacking.
     * 
     * @return the number of attacking armies
     */
    int decideAttackingArmies();

    /**
     * This method is used to get the number of armies
     * to move from the attacking territory to the 
     * conquered territory.
     * 
     * @return the number of armies to move.
     */
    int getArmiesToMove();

    /**
     * This method is used for position the new armies
     * received at the start of each turn.
     * 
     * @return the territory where the armies will be
     * positioned.
     */
    Territory decidePositioning();

    /**
     * This method is used to decide the next attacking territory and
     * the next adjacent defending territory the AI wants to attack. 
     * 
     * @param listOfTerritories - List of the territories in the current map.
     * @return true if the AI player can declare an attack, false
     * otherwise.
     */
    boolean decideAttack(final List<Territory> territoryList);

}