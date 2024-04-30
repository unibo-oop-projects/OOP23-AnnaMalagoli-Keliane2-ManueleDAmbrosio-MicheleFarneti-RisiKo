package it.unibo.risiko.player;

import java.util.Collection;
import java.util.Set;

import it.unibo.risiko.Card;
import it.unibo.risiko.Territory;

/**
 * contains player status and informations as well as owned territories and cards.
 * @author Manuele D'Ambrosio
 */

public interface Player {

    public void setArmiesToPlace(final int armiesToPlace);

    public void setOwnedTerritories(final Set<Territory> ownedTerritories);

    public void addTerritory(final Territory newTerritory);

    public void addCard (final Card newCard);

    public String getColor_id();

    public int getArmiesToPlace();

    public Collection<Territory> getOwnedTerritories();

    public Collection<Card> getOwnedCards();

    public void removeCard(final Card card);

    public void removeTerritory(final Territory territory);

    public boolean isDefeated();


}
