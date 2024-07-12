package it.unibo.risiko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.model.player.StdPlayer;//DA FARE MODIFICA NEI DUE TEST
import it.unibo.risiko.view.gameView.gameViewComponents.Position;
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
        /*final CardImpl firstCardRemoved;
        final CardImpl card = new CardImpl(ITALIA, "Fante");
        final CardImpl cardAdded1 = new CardImpl("Spagna", "Cavalleria");
        final CardImpl cardAdded2 = new CardImpl("Inghilterra", "Fante");
        final CardImpl cardAdded3 = new CardImpl(FRANCIA, "Cavalleria");*/
        //final List<CardImpl> deckList = deck.getListCards();
        final Card firstCardRemoved;
        final Card card = new CardImpl(ITALIA, "Infantry");
        final Card cardAdded1 = new CardImpl("Spagna", "Cavalry");
        final Card cardAdded2 = new CardImpl("Inghilterra", "Infantry");
        final Card cardAdded3 = new CardImpl(FRANCIA, "Cavalry");
        final List<Card> deckList = deck.getListCards();
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
    
    @Test
    void testPlaysOfCards() {
        final String path = "src/test/java/it/unibo/risiko/DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        final Card card1 = new CardImpl(ITALIA, "Cavalry");
        final Card card2 = new CardImpl("Spagna", "Cavalry");
        final Card card3 = new CardImpl("Inghilterra", "Infantry");
        final Card card4 = new CardImpl(FRANCIA, "Cavalry");
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        Set<Territory> playerTerritories = new HashSet<Territory>();
        playerTerritories.add(new TerritoryImpl("Spagna", "Europa", List.of(ITALIA, FRANCIA), 1, 1));
        //Player player = new SimplePlayerFactory().createStandardPlayer("Blu", 0);
        Player player = new SimplePlayerFactory().createStandardPlayer();
        player.setOwnedTerritories(playerTerritories);
        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card4);

        deck.playCards(card1, card2, card4, player);
        //System.out.println(player.getArmiesToPlace());
        assertEquals( 10, player.getArmiesToPlace());
    }

    /**Test per verificare funzionamento del metodo getCardFromNameTerritory */
    @Test
    void testGetCardFromNameTerritory() {
        /*Player player = new SimplePlayerFactory().createStandardPlayer("Blu", 0 )*/
        Player player = new SimplePlayerFactory().createStandardPlayer();
        final String path = "src/test/java/it/unibo/risiko/DeckCards.txt";
        final Deck deck = new DeckImpl(path);
        final Card card1 = new CardImpl(ITALIA, "Cavalry");
        final Card card2 = new CardImpl("Spagna", "Cavalry");
        final Card card3 = new CardImpl("Inghilterra", "Infantry");
        final Card card4 = new CardImpl(FRANCIA, "Cavalry");
        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card3);
        player.addCard(card4);
        assertEquals(card3, deck.getCardByTerritoryName("Inghilterra", player).get());
        assertEquals(card3.getTerritoryName(), deck.getCardByTerritoryName(card3.getTerritoryName(), player).get().getTerritoryName());
    }

    /**
     * Test of the method of the class TerritoryImpl.
     */
    @Test
    void testTerritoryImpl() {
        final int numberArmies = 25;
        final Territory territory = new TerritoryImpl(ITALIA, "Europa", List.of("Francia", "Austria", "Slovenia", "Svizzera"), 2, 2);
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
        final Continent continent;
        final List<Territory> territoriesList = territories.getListTerritories();
        final List<Continent> continentList = territories.getListContinents();
        assertEquals(territoriesList.get(0).getTerritoryName(), ITALIA);
        assertEquals(territoriesList.get(1).getTerritoryName(), FRANCIA);
        assertEquals(territoriesList.get(0).getPosition(), new Position(1, 1));
        //System.out.println(continentList.get(0).getName());
        assertEquals(continentList.get(0).getName(), "Europa");
        assertEquals(continentList.get(0).getBonusArmies(), 5);
        assertEquals(continentList.size(), 1);
        continent = continentList.get(0);
        assertEquals(continent.getListTerritories().get(0).getTerritoryName(), ITALIA);
        //System.out.println(continent.getListTerritories().get(0).getTerritoryName());
        assertEquals(continent.getListTerritories().get(1).getTerritoryName(), FRANCIA);
        //aggiunta di 3 armate in Italia
        territories.addArmiesInTerritory(ITALIA, 3);
        for (var elem : territoriesList) {
            if (elem.getTerritoryName().equals(ITALIA)) {
                assertEquals(elem.getNumberOfArmies(), 3);
            }
        }
    }

    @Test
    public void testMovementOfArmiesBetweenTwoTerritory() {
        final String path = "src/test/java/it/unibo/risiko/Territories.txt";
        final Territories territories = new Territories(path);
        /*Verify if the movement of 3 armies between ITALY and SPAIN is permetted
         * and does as expected.
        */
        territories.addArmiesInTerritory(ITALIA, 4);
        Territory France = territories.getListTerritories().get(1);
        Territory Italy = territories.getListTerritories().get(0);
        int movedArmies = Italy.getNumberOfArmies() - 1;
        territories.moveArmiesFromPlaceToPlace(ITALIA, FRANCIA, movedArmies);
        assertEquals(1, Italy.getNumberOfArmies());
        assertEquals(movedArmies, France.getNumberOfArmies());
    }

}