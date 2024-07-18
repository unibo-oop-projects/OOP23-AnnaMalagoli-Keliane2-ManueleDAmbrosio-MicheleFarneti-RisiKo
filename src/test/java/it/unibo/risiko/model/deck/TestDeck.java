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
 * @author Anna Malagoli
 */
public class TestDeck {

    private String separator = File.separator;

    /**
     * Test used to verify the correction of the class DeckImpl.
     */
    @Test
    void testGenerateDeck() {
        final String path = "src" + separator + "test" + separator + "java" + separator + "it" + separator + "unibo"
                + separator + "risiko" + separator + "deck" + separator + "DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        final Card firstCardRemoved;
        final Card card = new CardImpl("Italy", "Infantry");
        /* definition of three cards that will be added to the deck */
        final Card cardAdded1 = new CardImpl("Spain", "Cavalry");
        final Card cardAdded2 = new CardImpl("Great-Britain", "Infantry");
        final Card cardAdded3 = new CardImpl("France", "Cavalry");
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

    @Test
    void testPlaysOfCards() {
        final String path = "src/test/java/it/unibo/risiko/deck/DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        /* cards that will be added to the deck */
        final Card card1 = new CardImpl("Italy", "Cavalry");
        final Card card2 = new CardImpl("Spain", "Cavalry");
        final Card card3 = new CardImpl("Great-Britain", "Infantry");
        final Card card4 = new CardImpl("France", "Cavalry");
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        /* creation of the set of territories owned by the player */
        Set<String> playerTerritories = new HashSet<String>();
        playerTerritories.add("Spain");
        Player player = new SimplePlayerFactory().createStandardPlayer();
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

    /** DA RIMUOVERE PERCHè metodo privato
     * Test per verificare funzionamento del metodo getCardFromNameTerritory 
    @Test
    void testGetCardFromNameTerritory() {
    *creation of a player*
    Player player = new SimplePlayerFactory().createStandardPlayer();
    final String path = "src/test/java/it/unibo/risiko/deck/DeckCards.txt";
    final Deck deck = new DeckImpl(path);
    *definition of four cards*
    final Card card1 = new CardImpl("Italy", "Cavalry");
    final Card card2 = new CardImpl("Spain", "Cavalry");
    final Card card3 = new CardImpl("Great-Britain", "Infantry");
    final Card card4 = new CardImpl("France", "Cavalry");
    * added the four cards to the player *
    player.addCard(card1);
    player.addCard(card2);
    player.addCard(card3);
    player.addCard(card4);
    *verification that the card extracted from the list of cards of the player is
    corrected *
    assertEquals(card3, deck.getCardByTerritoryName("Great-Britain",
    player).get());
    assertEquals(card3.getTerritoryName(),
    deck.getCardByTerritoryName(card3.getTerritoryName(),
    player).get().getTerritoryName());
    }*/

}
