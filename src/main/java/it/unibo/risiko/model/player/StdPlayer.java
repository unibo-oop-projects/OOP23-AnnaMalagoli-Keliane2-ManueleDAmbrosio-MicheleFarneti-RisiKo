package it.unibo.risiko.model.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.objective.Target;

/**
 * Implementation of Player interface.
 * 
 * @author Manuele D'Ambrosio
 */
public class StdPlayer implements Player {
    private static final int REINFORCEMENT_FACTOR = 3;
    private static final int INITIAL_ARMIES = 0;
    private final String color_id;
    private Set<Territory> ownedTerritories = new HashSet<>();
    private Set<Card> ownedCards = new HashSet<>();
    private int armiesToPlace;
    private Optional<Target> target;
    private boolean isAI;

    protected StdPlayer(final String color, final int armiesToPlace, final boolean isAI) {
        this.color_id = color;
        this.armiesToPlace = armiesToPlace;
        this.target = Optional.empty();
        this.isAI = isAI;
    }

    protected StdPlayer(final String color, final boolean isAI) {
        this(color, INITIAL_ARMIES, isAI);
    }

    @Override
    public void setArmiesToPlace(final int armiesToPlace) {
        this.armiesToPlace = armiesToPlace;
    }

    @Override
    public void setOwnedTerritories(final Set<Territory> newTerritories) {
        this.ownedTerritories.addAll(newTerritories);
    }

    @Override
    public void setTarget(final Target target) {
        this.target = Optional.of(target);
    }

    @Override
    public void addTerritory(final Territory newTerritory) {
        this.ownedTerritories.add(newTerritory);
    }

    @Override
    public void addCard(final Card newCard) {
        this.ownedCards.add(newCard);
    }

    @Override
    public String getColor_id() {
        return this.color_id;
    }

    @Override
    public int getArmiesToPlace() {
        return this.armiesToPlace;
    }

    @Override
    public Collection<Territory> getOwnedTerritories() {
        return this.ownedTerritories;
    }

    @Override
    public Collection<Card> getOwnedCards() {
        return this.ownedCards;
    }

    @Override
    public int getNumberOfCards() {
        return this.ownedCards.size();
    }

    @Override
    public int getNumberOfTerritores() {
        return this.ownedTerritories.size();
    }

    @Override
    public Target getTarget() {
        return this.target.get();
    }

    @Override
    public void computeReinforcements() {
        this.armiesToPlace = this.ownedTerritories.size() / REINFORCEMENT_FACTOR;
    }

    @Override
    public void decrementArmiesToPlace() {
        this.armiesToPlace--;
    }

    @Override
    public boolean removeCard(final Card card) {
        if (isOwnedCard(card)) {
            this.ownedCards.remove(card);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeTerritory(final Territory territory) {
        if (isOwnedTerritory(territory)) {
            this.ownedTerritories.remove(territory);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDefeated() {
        return this.ownedTerritories.isEmpty();
    }

    public String toString() {
        return "Color = " + this.color_id +
                "\nTarget = " + this.target +
                "\nNumber of cards = " + getNumberOfCards() +
                "\nOwned cards = " + ownedCards.toString() +
                "\nNumber of territories = " + getNumberOfTerritores() +
                "\nOwned territories = " + ownedTerritories.toString() +
                "\nArmies to place = " + this.armiesToPlace;
    }

    @Override
    public boolean isOwnedCard(final Card card) {
        return this.ownedCards.contains(card);
    }

    @Override
    public boolean isOwnedTerritory(final Territory territory) {
        return this.ownedTerritories.contains(territory);
    }

    @Override
    public boolean isAI() {
        return this.isAI;
    }
}
