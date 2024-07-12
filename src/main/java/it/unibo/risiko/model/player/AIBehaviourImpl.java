package it.unibo.risiko.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.cards.Card;

/**
 * Implementation of @EasyModePlayer interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class AIBehaviourImpl implements AIBehaviour {
    private static final int MINIMUM_ARMIES = 1;
    private static final int INITIAL_INDEX = 0;
    private static final int MAX_ATTACKING_ARMIES = 3;
    private Player player;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;
    private int territoryIndex;

    public AIBehaviourImpl(final Player player) {
        this.player = player;
        this.territoryIndex = INITIAL_INDEX;
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
        Iterator<Territory> it = player.getOwnedTerritories().iterator();
        attackingTerritory = it.next();
        while (!findAdjacentEnemy(attackingTerritory, territoryList)) {
            attackingTerritory = it.next();
            if (!it.hasNext()) {
                return false;
            }
        }
        if (attackingTerritory.getNumberOfArmies() <= MINIMUM_ARMIES) {
            return false;
        }
        nextAttackingTerritory = Optional.of(attackingTerritory);
        return true;
    }

    private boolean findAdjacentEnemy(final Territory territory, final List<Territory> territoryList) {
        final int FIRST_INDEX = 0;
        List<String> adjacentNames = territory.getListOfNearTerritories();
        List<Territory> adjacentTerritories = new ArrayList<>();
        for (Territory t : territoryList) {
            if (adjacentNames.contains(t.getTerritoryName()) && !player.isOwnedTerritory(t)) {
                adjacentTerritories.add(t);
            }
        }
        if (adjacentTerritories.isEmpty()) {
            return false;
        }
        nextAttackedTerritory = Optional.of(adjacentTerritories.get(FIRST_INDEX));
        return true;
    }

    @Override
    public int decideAttackingArmies() {
        return nextAttackingTerritory.get().getNumberOfArmies() < MAX_ATTACKING_ARMIES
                ? nextAttackedTerritory.get().getNumberOfArmies()
                : MAX_ATTACKING_ARMIES;
    }

    @Override
    public List<Card> checkCardCombo() {
        List<Card> combo = new ArrayList<>();

        return combo;
    }

    private List<Card> checkTris(List<Card> playerCardList, String typeName) {
        final int TRIS = 3;
        List<Card> tris = new ArrayList<>();
        for (Card card : playerCardList) {
            if (card.getTypeName().equals(typeName)) {
                tris.add(card);
                if (tris.size() == TRIS) {
                    return tris;
                }
            }
        }
        return List.of();
    }

    private List<Card> check(List<Card> playerCardList) {
        List<Card> tris = new ArrayList<>();
        List<Card> jollyList = listOfType(playerCardList, "Jolly");
        List<Card> cannonList = listOfType(playerCardList, "Cannon");
        List<Card> infantryList = listOfType(playerCardList, "Infantry");
        List<Card> calvalryList = listOfType(playerCardList, "Cavalry");

        return tris;
    }

    private List<Card> listOfType(List<Card> playerCardList, String typeName) {
        return playerCardList.stream()
                .filter(t -> t.getTypeName().equals(typeName))
                .collect(Collectors.toList());
    }
}
