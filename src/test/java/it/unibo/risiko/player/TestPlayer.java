package it.unibo.risiko.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.ContinentImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */

/*public class TestPlayer {

    @Test
    void testRemoveCard() {
        PlayerFactory factory = new SimplePlayerFactory();
        Player player = factory.createStandardPlayer();
        Card card = new CardImpl("Territory1", "A");
        player.addCard(card);
        assertEquals(player.getNumberOfCards(), 1);
        assertTrue(player.removeCard(card));
        assertEquals(player.getNumberOfCards(), 0);
    }

    @Test
    void testComputeReinforcement() {
        PlayerFactory factory = new SimplePlayerFactory();
        Player player = factory.createStandardPlayer();
        Continent cont = new ContinentImpl("cont", 3);
        Territory t1 = new TerritoryImpl("ter1", cont.getName(), List.of("ter2", "ter3"));
        Territory t2 = new TerritoryImpl("ter2", cont.getName(), List.of("ter3", "ter1"));
        Territory t3 = new TerritoryImpl("ter5", cont.getName(), List.of("ter4"));
        cont.addTerritory(t3);
        cont.addTerritory(t2);
        cont.addTerritory(t1);
        player.addTerritory(t1.getTerritoryName());
        player.addTerritory(t2.getTerritoryName());
        player.computeReinforcements(List.of(cont));
        assertEquals(0, player.getArmiesToPlace());
        player.addTerritory(t3.getTerritoryName());
        player.computeReinforcements(List.of(cont));
        assertEquals(1+3, player.getArmiesToPlace());
    }

    @Test
    void testDrawNewCardIfPossible() {
        final String SEP = File.separator;
        final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "maps" + SEP + "standard" + SEP + "cards.txt";
        Deck deck = new DeckImpl(PATH);
        PlayerFactory factory = new SimplePlayerFactory();
        Player player = factory.createStandardPlayer();
        assertTrue(player.drawNewCardIfPossible(deck.pullCard()));
        assertEquals(player.getNumberOfCards(), 1);
        assertFalse(player.drawNewCardIfPossible(deck.pullCard()));
        player.computeReinforcements(List.of(new ContinentImpl("no", 0)));
        assertTrue(player.drawNewCardIfPossible(deck.pullCard()));
    }

}*/
