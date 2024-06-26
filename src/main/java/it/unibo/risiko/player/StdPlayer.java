package it.unibo.risiko.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.risiko.Card;
import it.unibo.risiko.Territory;
import it.unibo.risiko.objective.Target;

/**
 * Implementation of Player interface.
 * 
 * @author Manuele D'Ambrosio
 */
public class StdPlayer implements Player {

    private final String color_id;
    private Set<Territory> ownedTerritories = new HashSet<>();
    private Set<Card> ownedCards = new HashSet<>();
    private int armiesToPlace;
    private Optional<Target> target;

    public StdPlayer(final String color, final int armiesToPlace) {
        this.color_id = color;
        this.armiesToPlace = armiesToPlace;
        this.target = Optional.empty();
    }

    public void setArmiesToPlace(final int armiesToPlace) {
        this.armiesToPlace = armiesToPlace;
    }

    public void setOwnedTerritories(final Set<Territory> newTerritories) {
        this.ownedTerritories.addAll(newTerritories);
    }

    public void setTarget(final Target target) {
        this.target = Optional.of(target);
    }

    public void addTerritory(final Territory newTerritory) {
        this.ownedTerritories.add(newTerritory);
    }

    public void addCard(final Card newCard) {
        this.ownedCards.add(newCard);
    }

    public String getColor_id() {
        return this.color_id;
    }

    public int getArmiesToPlace() {
        return this.armiesToPlace;
    }

    public Collection<Territory> getOwnedTerritories() {
        return this.ownedTerritories;
    }

    public Collection<Card> getOwnedCards() {
        return this.ownedCards;
    }

    public int getNumberOfCards() {
        return this.ownedCards.size();
    }

    public int getNumberOfTerritores() {
        return this.ownedTerritories.size();
    }

    public Optional<Target> getTarget() {
        return this.target;
    }

    @Override
    public void computeReinforcements() {
        this.armiesToPlace = this.ownedTerritories.size()/3;
    }

    public void decrementArmiesToPlace() {
        this.armiesToPlace--;
    }

    public boolean removeCard(final Card card) {
        if (isOwnedCard(card)) {
            this.ownedCards.remove(card);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeTerritory(final Territory territory) {
        if (isOwnedTerritory(territory)) {
            this.ownedTerritories.remove(territory);
            return true;
        } else {
            return false;
        }
    }

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

    public boolean isOwnedCard(final Card card) {
        return this.ownedCards.contains(card);
    }

    public boolean isOwnedTerritory(final Territory territory) {
        return this.ownedTerritories.contains(territory);
    }

    public boolean isAI() {
        return false;
    }
}
