package it.unibo.risiko.model.player;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;

/**
 * Implementation of @EasyModePlayer interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class EasyModePlayerImpl extends StdPlayer implements EasyModePlayer {
    private static final int NUMBER_OF_ATTACKS = 3; 
    private static final int INITIAL_ARMIES = 0;

    private int NumberOfAttacks;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;

    protected EasyModePlayerImpl(final String color, final int armiesToPlace) {
        super(color, armiesToPlace);
        NumberOfAttacks = NUMBER_OF_ATTACKS;
        nextAttackingTerritory = Optional.empty();
        nextAttackedTerritory = Optional.empty();
    }

    protected EasyModePlayerImpl(final String color) {
        this(color, INITIAL_ARMIES);
    }

    @Override
    public Territory getNextAttackingTerritory() {
        return this.nextAttackingTerritory.get();
    }

    @Override
    public Territory getNextAttackedTerritory() {
        return this.nextAttackedTerritory.get();
    }

    @Override
    public int getArmiesToMove() {
        return nextAttackingTerritory.get().getNumberOfArmies() - 1;
    }

    @Override
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

    @Override
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
