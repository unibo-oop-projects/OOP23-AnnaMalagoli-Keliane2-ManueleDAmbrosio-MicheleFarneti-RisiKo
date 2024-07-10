package it.unibo.risiko.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.risiko.model.map.Territory;

/**
 * Implementation of @EasyModePlayer interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class AIBehaviourImpl implements AIBehaviour {
    private static final int MINIMUM_ARMIES = 1;
    private static final int INITIAL_INDEX = 0;
    private Player player;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;
    private int territoryIndex;

    public AIBehaviourImpl(final Player player) {
        this.player = player;
        this.territoryIndex = INITIAL_INDEX;
    }

    @Override
    public Territory getNextAttackingTerritory(List<Territory> territoryList) {
        Territory attackingTerritory;
        Iterator<Territory> it = player.getOwnedTerritories().iterator();
        attackingTerritory = it.next();
        while (!findAdjacentEnemy(attackingTerritory, territoryList)) {
            attackingTerritory = it.next();
        }
        return attackingTerritory;
    }

    @Override
    public Territory getNextAttackedTerritory() {
        return this.nextAttackedTerritory.get();
    }

    @Override
    public int getArmiesToMove() {
        return nextAttackingTerritory.get().getNumberOfArmies() - MINIMUM_ARMIES;
    }

    @Override
    public Territory decidePositioning() {
        int index = territoryIndex;
        territoryIndex++;
        return player.getOwnedTerritories().stream().collect(Collectors.toList()).get(index);
    }

    @Override
    public boolean decideAttack(final List<Territory> territoryList) {
        Territory attackingTerritory;
        int existenceCheker = 0;
        Iterator<Territory> it = player.getOwnedTerritories().iterator();
        attackingTerritory = it.next();
        while (!findAdjacentEnemy(attackingTerritory, territoryList)) {
            attackingTerritory = it.next();
            existenceCheker++;
            if (existenceCheker >= player.getOwnedTerritories().size()) {
                return false;
            }
        }
        nextAttackingTerritory = Optional.of(attackingTerritory);
        return true;
    }

    private boolean findAdjacentEnemy(final Territory territory, final List<Territory> territoryList) {
        final int FIRST_INDEX = 0;
        List<String> adjacentNames = territory.getListOfNearTerritories();
        List<Territory> adjacentTerritories = new ArrayList<>();
        for (Territory t : territoryList) {
            if (adjacentNames.contains(t.getTerritoryName())) {
                adjacentTerritories.add(t);
            }            
        }
        if (adjacentTerritories.isEmpty()) {
            return false;
        }
        nextAttackedTerritory = Optional.of(adjacentTerritories.get(FIRST_INDEX));
        return true;
    }
}
