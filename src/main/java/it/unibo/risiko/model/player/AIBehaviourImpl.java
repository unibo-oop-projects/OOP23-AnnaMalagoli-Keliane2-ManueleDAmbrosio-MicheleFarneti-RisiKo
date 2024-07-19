package it.unibo.risiko.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
    private final Random rnd;
    private final List<Territory> playerTerritoryList;
    private final List<Card> playerCardList;
    private Optional<Territory> nextAttackingTerritory;
    private Optional<Territory> nextAttackedTerritory;

    public AIBehaviourImpl(final List<Territory> playerOwnedTerritories, final List<Card> playerOwnedCards) {
        this.playerTerritoryList = new ArrayList<>(playerOwnedTerritories);
        this.playerCardList = new ArrayList<>(playerOwnedCards);
        this.nextAttackedTerritory = Optional.empty();
        this.nextAttackingTerritory = Optional.empty();
        rnd = new Random();
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
        return playerTerritoryList.get(rnd.nextInt(playerTerritoryList.size()));
    }

    @Override
    public boolean decideAttack(final List<Territory> territoryList) {
        final Iterator<Territory> it = playerTerritoryList.iterator();
        Territory attackingTerritory;
        attackingTerritory = it.next();
        while (!findAdjacentEnemy(attackingTerritory, territoryList)
                || attackingTerritory.getNumberOfArmies() <= MINIMUM_ARMIES) {
            attackingTerritory = it.next();
            if (!it.hasNext()) {
                return false;
            }
        }
        nextAttackingTerritory = Optional.of(attackingTerritory);
        return true;
    }

    private boolean findAdjacentEnemy(final Territory territory, final List<Territory> territoryList) {
        final List<String> adjacentNames = territory.getListOfNearTerritories();
        final List<Territory> adjacentTerritories = new ArrayList<>();
        for (final Territory t : territoryList) {
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
        return findCombo();
    }

    private List<Card> findCombo() {
        final List<Card> tris = new ArrayList<>();
        final List<Card> jollyList = listOfType(playerCardList, "Jolly");
        final List<Card> cannonList = listOfType(playerCardList, "Cannon");
        final List<Card> infantryList = listOfType(playerCardList, "Infantry");
        final List<Card> calvalryList = listOfType(playerCardList, "Cavalry");
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

    private List<Card> listOfType(final List<Card> playerCardList, final String typeName) {
        return playerCardList.stream()
                .filter(t -> t.getTypeName().equals(typeName))
                .collect(Collectors.toList());
    }

    private void addFromListToList(final List<Card> srcList, final List<Card> destList, final int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            destList.add(srcList.get(i));
        }
    }
}
