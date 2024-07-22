package it.unibo.risiko.model.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.objective.Target;

/**
 * Implementation of Player interface.
 * 
 * @author Manuele D'Ambrosio
 */
public final class StdPlayer implements Player {
    private static final int REINFORCEMENT_FACTOR = 3;
    private static final int INITIAL_ARMIES = 0;
    private final String colorID;
    private final Set<String> ownedTerritories = new HashSet<>();
    private final Set<Card> ownedCards = new HashSet<>();
    private int armiesToPlace;
    private Optional<Target> target;
    private boolean isAI;
    private boolean hasDrawnNewCard;

    /**
     * @param color         - color name of the player.
     * @param armiesToPlace - initial armies.
     * @param isAI          - true if the player is AI, false otherwise.
     */
    protected StdPlayer(final String color, final int armiesToPlace, final boolean isAI) {
        this.colorID = color;
        this.armiesToPlace = armiesToPlace;
        this.isAI = isAI;
    }

    /**
     * @param color - color name of the player.
     * @param isAI  - true if the player is AI, false otherwise.
     */
    protected StdPlayer(final String color, final boolean isAI) {
        this(color, INITIAL_ARMIES, isAI);
    }

    @Override
    public void setArmiesToPlace(final int armiesToPlace) {
        this.armiesToPlace = armiesToPlace;
    }

    @Override
    public void setOwnedTerritories(final Set<String> newTerritories) {
        this.ownedTerritories.addAll(newTerritories);
    }

    @Override
    public void setTarget(final Target target) {
        this.target = Optional.of(target);
    }

    @Override
    public void addTerritory(final String newTerritory) {
        this.ownedTerritories.add(newTerritory);
    }

    @Override
    public void addCard(final Card newCard) {
        this.ownedCards.add(newCard);
    }

    @Override
    public String getColorID() {
        return this.colorID;
    }

    @Override
    public int getArmiesToPlace() {
        return this.armiesToPlace;
    }

    @Override
    public Collection<String> getOwnedTerritories() {
        return Collections.unmodifiableCollection(this.ownedTerritories);
    }

    @Override
    public Collection<Card> getOwnedCards() {
        return Collections.unmodifiableCollection(this.ownedCards);
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
    public void computeReinforcements(final Collection<Continent> continentsList) {
        int bonusArmies = this.ownedTerritories.size() / REINFORCEMENT_FACTOR;
        List<String> territoriesNames;
        for (final Continent continent : continentsList) {
            territoriesNames = continent.getListTerritories().stream().map(t -> t.getTerritoryName())
                    .collect(Collectors.toList());
            if (this.ownedTerritories.containsAll(territoriesNames)) {
                bonusArmies += continent.getBonusArmies();
            }
        }
        this.armiesToPlace = bonusArmies;
        resetDraw();
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
    public boolean removeTerritory(final String territory) {
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

    @Override
    public String toString() {
        return "Color = " + this.colorID
                + "\nTarget = " + this.target
                + "\nNumber of cards = " + getNumberOfCards()
                + "\nOwned cards = " + ownedCards.toString()
                + "\nNumber of territories = " + getNumberOfTerritores()
                + "\nOwned territories = " + ownedTerritories.toString()
                + "\nArmies to place = " + this.armiesToPlace;
    }

    @Override
    public boolean isOwnedCard(final Card card) {
        return this.ownedCards.contains(card);
    }

    @Override
    public boolean isOwnedTerritory(final String territory) {
        return this.ownedTerritories.contains(territory);
    }

    @Override
    public boolean isAI() {
        return this.isAI;
    }

    @Override
    public boolean drawNewCardIfPossible(final Card card) {
        if (!hasDrawnNewCard) {
            this.addCard(card);
            this.hasDrawnNewCard = true;
            return true;
        }
        return false;
    }

    private void resetDraw() {
        this.hasDrawnNewCard = false;
    }

    @Override
    public Player clonePlayer() {
        return this;
    }
}
