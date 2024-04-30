package it.unibo.risiko.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import it.unibo.risiko.Card;
import it.unibo.risiko.Territory;

/*TO DO:
Aggiungere campi che contano il numero di territori e carte possedute.
Aggiungere ai metodi l'aggiornamento dei campi sopracitati.
Aggiungere un Set di continenti controllati con relativi metodi.
 */

public class RealPlayer implements Player{

    private final String color_id;
    private Set<Territory> ownedTerritories = new HashSet<>();
    private Set<Card> ownedCards = new HashSet<>();
    private int armiesToPlace;

    public RealPlayer (final String color, final int armiesToPlace) {
        this.color_id = color;
        this.armiesToPlace = armiesToPlace;
        this.ownedCards = Collections.emptySet();
        this.ownedTerritories = Collections.emptySet();
    }

    public void setArmiesToPlace(final int armiesToPlace) {
        this.armiesToPlace = armiesToPlace;
    }

    public void setOwnedTerritories(final Set<Territory> ownedTerritories) {
        this.ownedTerritories.addAll(ownedTerritories);
    }

    public void addTerritory(final Territory newTerritory) {
        this.ownedTerritories.add(newTerritory);
    }

    public void addCard (final Card newCard) {
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

    public void decrementArmiesToPlace() {
        this.armiesToPlace--;
    }

    public void removeCard(final Card card) {
        if (isOwnedCard(card)) {
            this.ownedCards.remove(card);
        }
        else {
            System.err.println("Carta " + card.getTerritoryName() + " non posseduta");
        }
    }

    public void removeTerritory(final Territory territory) {
        if (isOwnedTerritory(territory)) {
            this.ownedTerritories.remove(territory);
        }
        else {
            System.err.println("Territorio " + territory.getTerritoryName() + " non posseduta");
        }
    }

    public boolean isDefeated() {
        return this.ownedTerritories.isEmpty();
    }

    private boolean isOwnedCard(final Card card) {
        return this.ownedCards.contains(card);
    }

    private boolean isOwnedTerritory(final Territory territory) {
        return this.ownedTerritories.contains(territory);
    }
}
