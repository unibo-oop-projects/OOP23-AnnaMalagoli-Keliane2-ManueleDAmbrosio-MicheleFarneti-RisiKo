package it.unibo.risiko.model.player;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;

/**
 * This Class is a easy mode AI player that can make simple
 * decisions about the game and play on its own.
 * 
 * @author Manuele D'Ambrosio
 */

public class EasyModePlayer extends StdPlayer {
    private final static int NUMBER_OF_ATTACKS = 3; 

    private int NumberOfAttacks;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;

    EasyModePlayer(final String color, final int armiesToPlace) {
        super(color, armiesToPlace);
        NumberOfAttacks = NUMBER_OF_ATTACKS;
        nextAttackingTerritory = Optional.empty();
        nextAttackedTerritory = Optional.empty();
    }

    /**
     * This method is used to get a territory owned
     * by the AI player and which the player wants
     * to attack from. 
     * 
     * @return the next territory the AI wants to attack from.
     */
    public Territory getNextAttackingTerritory() {
        return this.nextAttackingTerritory.get();
    }

    /**
     * This method is used to get a territory the AI player
     * can attack from the adjacent attacking territory.
     * 
     * @return the next territory the AI wants to attack.
     */
    public Territory getNextAttackedTerritory() {
        return this.nextAttackedTerritory.get();
    }

    /**
     * This method is used to get the number of armies
     * to move from the attacking territory to the 
     * conquered territory.
     * 
     * @return the number of armies to move.
     */
    public int getArmiesToMove() {
        return nextAttackingTerritory.get().getNumberOfArmies() - 1;
    }

    /**
     * This method is used for position the new armies
     * received at the start of each turn.
     */
    public void decidePositioning() {
        int armiesToPlace = super.getArmiesToPlace();
        int armiesPartition = armiesToPlace/super.getNumberOfTerritores();
        for (Territory territory : super.getOwnedTerritories()) {
            if (armiesToPlace >= armiesPartition) {
                territory.addArmies(armiesPartition);
                armiesToPlace = armiesToPlace - armiesPartition;
            }
            else {
                territory.addArmies(armiesToPlace);
            }
        }
        super.setArmiesToPlace(armiesToPlace);
    }

    /**
     * This method is used to decide the next attacking territory and
     * the next adjacent defending territory the AI wants to attack. 
     * 
     * @param listOfTerritories - List of the territories in the current map.
     * @return true if the AI player can declare an attack, false
     * otherwise.
     */
    public boolean decideAttack(final Territories listOfTerritories) {
        for (Territory attackingTerritory : listOfTerritories.getListTerritories()) {
            if (isOwnedTerritory(attackingTerritory) && canAttack()) {
                if (attackingTerritory.getNumberOfArmies() > NUMBER_OF_ATTACKS) {
                    nextAttackingTerritory = Optional.of(attackingTerritory);
                    if (findAdjacentEnemy(attackingTerritory, listOfTerritories)) {
                        NumberOfAttacks--;
                        return true;
                    }
                }
            }
        }
        resetNumberOfAttacks();
        return false;
    }

    @Override
    public boolean isAI() {
        return true;
    }

    private void resetNumberOfAttacks() {
        NumberOfAttacks = NUMBER_OF_ATTACKS;
    }

    private boolean canAttack() {
        return NumberOfAttacks > 0;
    }

    private boolean findAdjacentEnemy(final Territory territory, final Territories listOfTerritories) {

        List<String> adjacentTerritoriesNames = territory.getListOfNearTerritories();
        Set<Territory> adjacentTerritories = new HashSet<>();
        for (String territoryName : adjacentTerritoriesNames) {
            for (Territory adjacentTerritory : listOfTerritories.getListTerritories()) { 
                if (adjacentTerritory.getTerritoryName() == territoryName) {
                    adjacentTerritories.add(adjacentTerritory);
                }
            }
        }
        for (Territory defendingTerritory : adjacentTerritories) {
            if (!isOwnedTerritory(defendingTerritory)) {
                nextAttackedTerritory = Optional.of(defendingTerritory);
                return true;
            }
        }
        return false;
    }

}
