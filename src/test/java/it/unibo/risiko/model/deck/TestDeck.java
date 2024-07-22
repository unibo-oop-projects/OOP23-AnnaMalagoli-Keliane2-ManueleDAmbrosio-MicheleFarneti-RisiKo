package it.unibo.risiko.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Set;
import java.util.HashSet;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import java.io.File;

/**
 * Test class used to test the deck.
 * @author Anna Malagoli
 */
class TestDeck {

    private final String separator = File.separator;
    private static final String CAVALRY = "Cavalry";

    /**
     * Test used to verify the correction of the class DeckImpl.
     */
    @Test
    void testGenerateDeck() {
        final String path = "src" + separator + "test" + separator + "java" + separator + "it" + separator + "unibo"
                + separator + "risiko" + separator + "model" + separator + "deck" + separator + "DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        final Card firstCardRemoved;
        final Card card = new CardImpl("Italy", "Infantry");
        /* definition of three cards that will be added to the deck */
        final Card cardAdded1 = new CardImpl("Spain", CAVALRY);
        final Card cardAdded2 = new CardImpl("Great-Britain", CAVALRY);
        final Card cardAdded3 = new CardImpl("France", CAVALRY);
        /* extraction of the list of cards in the deck */
        final List<Card> deckList = deck.getListCards();

        /* verification that the deck does not contains empty cards */
        for (final var elem : deck.getListCards()) {
            assertFalse(elem.getTerritoryName().isEmpty());
            assertFalse(elem.getTypeName().isEmpty());
        }
        /* check that the deck is created correctly from the deck */
        assertEquals(deckList.get(0).getTerritoryName(), card.getTerritoryName());
        assertEquals(deckList.get(0).getTypeName(), card.getTypeName());
        /* extraction of a card from the deck */
        firstCardRemoved = deck.pullCard();
        /*
         * after the only card of the deck is extracted is verified that the deck is now
         * empty
         */
        assertTrue(deck.getListCards().isEmpty());
        /* verification that the card extracted from the deck is the right one */
        assertEquals(firstCardRemoved.getTerritoryName(), card.getTerritoryName());
        assertEquals(firstCardRemoved.getTypeName(), card.getTypeName());
        /* added three cards in the deck */
        deck.addCard(card);
        assertFalse(deck.getListCards().isEmpty());
        deck.addCard(cardAdded1);
        deck.addCard(cardAdded2);
        deck.addCard(cardAdded3);
    }

    /**
     * Test created to verify the play of three cards by a player.
     */
    @Test
    void testPlaysOfCards() {
        final String path = "src/test/java/it/unibo/risiko/deck/DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        /* cards that will be added to the deck */
        final Card card1 = new CardImpl("Italy", CAVALRY);
        final Card card2 = new CardImpl("Spain", CAVALRY);
        final Card card3 = new CardImpl("Great-Britain", "Infantry");
        final Card card4 = new CardImpl("France", CAVALRY);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        /* creation of the set of territories owned by the player */
        final Set<String> playerTerritories = new HashSet<>();
        playerTerritories.add("Spain");
        final Player player = new SimplePlayerFactory().createStandardPlayer();
        player.setOwnedTerritories(playerTerritories);
        /* added cards to the list of cards of the player */
        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card4);

        /*
         * play of three cards of the player that create a cavalry combo (8 armies
         * added).
         * One of the cards is of Spain which is a territory that the player owns,
         * so he gain 2 extra armies. After the player play he has to set 10 armies.
         */
        assertEquals(3, player.getOwnedCards().size());
        deck.playCards(card1.getTerritoryName(), card2.getTerritoryName(), card4.getTerritoryName(), player);
        assertEquals(10, player.getArmiesToPlace());
        assertEquals(0, player.getOwnedCards().size());
    }

}
