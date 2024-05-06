package it.unibo.risiko;

import java.util.List;
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
     * Method used to shuffle the deck.
     */
    void shuffle();

    /**
     * Method used to get the list of the cards that are actually in the deck.
     * @return the list of the cards in the deck
     */
    List<Card> getListCards();
}
