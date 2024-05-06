package it.unibo.risiko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
/**
 * Class used to execute the tests on the classes.
 */
class MainTest {

    private static final String ITALIA = "Italia";
    private static final String FRANCIA = "Francia";
    /**
     * Trial test.
     */
    @Test
    void testTry() {
        assertEquals("Ciao", "Ciao");
    }

    /**
     * Test used to verify the correction of the class DeckImpl.
     */
    @Test
    void testGenerateDeck() {
        final String path = "src/test/java/it/unibo/risiko/DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        final CardImpl firstCardRemoved;
        final CardImpl card = new CardImpl(ITALIA, "Fante");
        final CardImpl cardAdded1 = new CardImpl("Spagna", "Cavalleria");
        final CardImpl cardAdded2 = new CardImpl("Inghilterra", "Fante");
        final CardImpl cardAdded3 = new CardImpl(FRANCIA, "Cavalleria");
        final List<CardImpl> deckList = deck.getListCards();
        assertEquals(List.of(card).getClass(), deckList.getClass());
        for (final var elem : deck.getListCards()) {
            assertFalse(elem.getTerritoryName().isEmpty());
            assertFalse(elem.getTypeName().isEmpty());
        }
        assertEquals(deckList.get(0).getTerritoryName(), card.getTerritoryName());
        assertEquals(deckList.get(0).getTypeName(), card.getTypeName());
        firstCardRemoved = deck.pullCard();
        assertTrue(deck.getListCards().isEmpty());
        assertEquals(firstCardRemoved.getTerritoryName(), card.getTerritoryName());
        assertEquals(firstCardRemoved.getTypeName(), card.getTypeName());
        deck.addCard(card);
        assertFalse(deck.getListCards().isEmpty());
        deck.addCard(cardAdded1);
        deck.addCard(cardAdded2);
        deck.addCard(cardAdded3);
        deck.shuffle();
        /*Verify that the shuffle method is all right by showing the content of the list
         * deck which card's order it's not predictable
         */
        /*deck.getListCards().stream().forEach(x -> System.out.println(x.getTerritoryName() + " " + x.getTypeName()));*/
    }

    /**
     * Test of the method of the class TerritoryImpl.
     */
    @Test
    void testTerritoryImpl() {
        final int numberArmies = 25;
        final Territory territory = new TerritoryImpl(ITALIA, "Europa", List.of("Francia", "Austria", "Slovenia", "Svizzera"));
        assertEquals(territory.getNumberOfArmies(), 0);
        territory.addArmies(numberArmies);
        assertEquals(territory.getNumberOfArmies(), numberArmies);
        assertEquals(ITALIA, territory.getTerritoryName());
        assertEquals("Europa", territory.getContinentName());
        territory.removeArmies(numberArmies);
        assertEquals(territory.getNumberOfArmies(), 0);
        assertEquals(territory.getListOfNearTerritories(), List.of(FRANCIA, "Austria", "Slovenia", "Svizzera"));
    }

    /**
     * Test of the method of the class Territories that has to read the 
     * information for the initialization of the territories.
     */
    @Test
    void testTerritories() {
        final String path = "src/test/java/it/unibo/risiko/Territories.txt";
        final Territories territories = new Territories(path);
        final List<Territory> territoriesList = territories.getList();
        assertEquals(territoriesList.get(0).getTerritoryName(), ITALIA);
        assertEquals(territoriesList.get(1).getTerritoryName(), FRANCIA);
    }

}
