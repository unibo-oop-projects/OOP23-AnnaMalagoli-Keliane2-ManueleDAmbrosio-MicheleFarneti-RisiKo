package it.unibo.risiko.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.cards.Card;

/**
 * Implementation of @AIBehaviour interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class AIBehaviourImpl implements AIBehaviour {
    private static final int ONE_CARD = 1;
    private static final int TWO_CARDS = 2;
    private static final int THREE_CARDS = 3;
    private static final int MINIMUM_ARMIES = 1;
    private static final int INITIAL_INDEX = 0;
    private static final int MAX_ATTACKING_ARMIES = 3;
    private List<Territory> playerTerritoryList;
    private List<Card> playerCardList;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;
    private int territoryIndex;

    public AIBehaviourImpl(final List<Territory> playerOwnedTerritories, final List<Card> playerOwnedCards) {
        this.playerTerritoryList = new ArrayList<Territory>(playerOwnedTerritories);
        this.playerCardList = new ArrayList<Card>(playerOwnedCards);
        this.nextAttackedTerritory = Optional.empty();
        this.nextAttackingTerritory = Optional.empty();
        this.territoryIndex = INITIAL_INDEX;
    }

    @Override
    public String getNextAttackingTerritory() {
        return this.nextAttackingTerritory.get().getTerritoryName();
    }

    @Override
    public String getNextAttackedTerritory() {
        return this.nextAttackedTerritory.get().getTerritoryName();
    }

    @Override
    public int getArmiesToMove() {
        return nextAttackingTerritory.get().getNumberOfArmies() - MINIMUM_ARMIES;
    }

    @Override
    public Territory decidePositioning() {
        territoryIndex++;
        if (territoryIndex >= playerTerritoryList.size()) {
            territoryIndex = INITIAL_INDEX;
        } 
        return playerTerritoryList.stream().collect(Collectors.toList()).get(territoryIndex);
    }

    @Override
    public boolean decideAttack(final List<Territory> territoryList) {
        Territory attackingTerritory;
        Iterator<Territory> it = playerTerritoryList.iterator();
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
        List<String> adjacentNames = territory.getListOfNearTerritories();
        List<Territory> adjacentTerritories = new ArrayList<>();
        for (Territory t : territoryList) {
            if (adjacentNames.contains(t.getTerritoryName()) && !playerTerritoryList.contains(t)) {
                adjacentTerritories.add(t);
            }
        }
        if (adjacentTerritories.isEmpty()) {
            return false;
        }
        nextAttackedTerritory = Optional.of(adjacentTerritories.get(INITIAL_INDEX));
        return true;
    }

    @Override
    public int decideAttackingArmies() {
        return nextAttackingTerritory.get().getNumberOfArmies() < MAX_ATTACKING_ARMIES
                ? nextAttackingTerritory.get().getNumberOfArmies()
                : MAX_ATTACKING_ARMIES;
    }

    @Override
    public List<Card> checkCardCombo() {
        return findCombo(playerCardList.stream().toList());
    }

    private List<Card> findCombo(List<Card> playerCardList) {
        List<Card> tris = new ArrayList<>();
        List<Card> jollyList = listOfType(playerCardList, "Jolly");
        List<Card> cannonList = listOfType(playerCardList, "Cannon");
        List<Card> infantryList = listOfType(playerCardList, "Infantry");
        List<Card> calvalryList = listOfType(playerCardList, "Cavalry");
        if (jollyList.size() >= ONE_CARD) {
            if (cannonList.size() >= TWO_CARDS) {
                addFromListToList(jollyList, tris, ONE_CARD);
                addFromListToList(cannonList, tris, TWO_CARDS);
                return tris;
            } else if (infantryList.size() >= TWO_CARDS) {
                addFromListToList(jollyList, tris, ONE_CARD);
                addFromListToList(infantryList, tris, TWO_CARDS);
                return tris;
            } else if (calvalryList.size() >= TWO_CARDS) {
                addFromListToList(jollyList, tris, ONE_CARD);
                addFromListToList(calvalryList, tris, TWO_CARDS);
                return tris;
            }
        } else if (calvalryList.size() >= THREE_CARDS) {
            addFromListToList(calvalryList, tris, THREE_CARDS);
            return tris;
        } else if (infantryList.size() >= THREE_CARDS) {
            addFromListToList(infantryList, tris, THREE_CARDS);
            return tris;
        } else if (cannonList.size() >= THREE_CARDS) {
            addFromListToList(cannonList, tris, THREE_CARDS);
            return tris;
        }
        return List.of();
    }

    private List<Card> listOfType(List<Card> playerCardList, String typeName) {
        return playerCardList.stream()
                .filter(t -> t.getTypeName().equals(typeName))
                .collect(Collectors.toList());
    }

    private void addFromListToList(List<Card> srcList, List<Card> destList, final int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            destList.add(srcList.get(i));
        }
    }
}
