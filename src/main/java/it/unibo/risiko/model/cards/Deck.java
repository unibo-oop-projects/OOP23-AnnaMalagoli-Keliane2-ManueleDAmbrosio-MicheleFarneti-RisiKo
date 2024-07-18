package it.unibo.risiko.model.cards;

import java.util.List;

import it.unibo.risiko.model.player.Player;
/** 
 * Interface Deck is used to establish the method that a deck of cards must have.
 * @author Anna Malagoli 
*/
public interface Deck {

    /**
     * Method used to add a card in the deck.
     * @param card is the card that has to be added in the deck
     */
    void addCard(Card card);

    /**
     * Method used to get a card from the deck.
     * @return the card that was pulled out from the deck
     */
    Card pullCard();

    /**
     * Method used to get the list of the cards that are actually in the deck.
     * @return the list of the cards in the deck
     */
    List<Card> getListCards();

    /**
     * Method to play three cards during a player turn.
     * @param card1 is the first card to be played
     * @param card2 is the second card to be played
     * @param card3 is the third card to be played
     * @param player is the one who plays the three cards during his turn
     */
    void playCards(String card1, String card2, String card3, Player player);

}
