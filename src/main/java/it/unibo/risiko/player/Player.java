package it.unibo.risiko.player;

import java.util.Collection;
import java.util.Set;

import it.unibo.risiko.Card;
import it.unibo.risiko.Territory;

/**
 * Contains player status and informations as well as owned territories and cards.
 * @author Manuele D'Ambrosio
 */

public interface Player {

    /**
     * This method is used to set the number of armies
     * a player has to place.
     * 
     * @param armiesToPlace - int value representig the number
     * of armies that the player has to place.
     */
    public void setArmiesToPlace(final int armiesToPlace);

    /**
     * This method is used to set the territories owned
     * by the player.
     * 
     * @param ownedTerritories - Set containing the territories
     * owned by the player.
     */
    public void setOwnedTerritories(final Set<Territory> ownedTerritories);

    /**
     * This method is used to add a territory to the set of
     * territories owned by the player.
     * 
     * @param newTerritory - territory to add.
     */
    public void addTerritory(final Territory newTerritory);

    /**
     * This method is used to add a card to the set of cards
     * owned by the player.
     * 
     * @param newCard - card to add.
     */
    public void addCard (final Card newCard);

    /**
     * This method is used to get the color of the player.
     * 
     * @return the player color.
     */
    public String getColor_id();

    /**
     * This method is used to get the number of armies a player has yet
     * to place on its territories.
     * 
     * @return the number of armies to place.
     */
    public int getArmiesToPlace();

    /**
     * This method is used to get a collection containing 
     * the territories owned by the player.
     * 
     * @return the territories owned by the player.
     */
    public Collection<Territory> getOwnedTerritories();

    /**
     * This method is used to get a collection containing 
     * the cards owned by the player.
     * 
     * @return the cards owned by the player.
     */
    public Collection<Card> getOwnedCards();

    /**
     * This method is used to get the number of cards 
     * owned by the player.
     * 
     * @return the number of cards owned by the player.
     */
    public int getNumberOfCards();

    /**
     * This method is used to know the number of cards
     * owned by the player.
     * 
     * @return the number of cards owned by the players.
     */
    public int getNumberOfTerritores();

    /**
     * This metod is used to remove a card from the 
     * set of cards owned by the player.
     * 
     * @param card - card to be removed.
     * @return true if the card was possesed by the player,
     * false otherwise.
     */
    public boolean removeCard(final Card card);

    /**
     * This metod is used to remove a territory from the 
     * set of territories owned by the player.
     * 
     * @param territory - territory to be removed.
     * @return true if the territory was possesed by the
     * player, false otherwise.
     */
    public boolean removeTerritory(final Territory territory);

    /**
     * This method is used to know if the player has lost
     * all of his territories, which also means that the
     * player has also been defeated.
     * 
     * @return true if the player has lost all of his
     * territories, false otherwise.
     */
    public boolean isDefeated();

    /**
     * This method is used to know if a player owns a
     * certain card.
     * 
     * @param card - card to check.
     * @return true if the player owns the card, false
     * otherwise.
     */
    public boolean isOwnedCard(final Card card);

    /**
     * This method is used to know if a player owns a 
     * certain territory.
     * 
     * @param territory - territory to check.
     * @return True if the player owns the territory, false 
     * otherwise.
     */
    public boolean isOwnedTerritory(final Territory territory);

}
