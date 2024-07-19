package it.unibo.risiko.model.player;

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

/**
 * @author Manuele D'Ambrosio
 */

class TestPlayer {
    private static final int BONUS_ARMIES = 3;
    private static final int NO_ARMIES = 0;
    private static final int ARMIES_COMPUTED = 4;
    private static final int ONE_CARD = 1;
    private static final int ZERO_CARDS = 0;

    @Test
    void testRemoveCard() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createStandardPlayer();
        final Card card = new CardImpl("Territory1", "A");
        player.addCard(card);
        assertEquals(player.getNumberOfCards(), ONE_CARD);
        assertTrue(player.removeCard(card));
        assertEquals(player.getNumberOfCards(), ZERO_CARDS);
    }

    @Test
    void testComputeReinforcement() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createStandardPlayer();
        final Continent cont = new ContinentImpl("cont", BONUS_ARMIES);
        final Territory t1 = new TerritoryImpl("ter1", cont.getName(), List.of("ter2", "ter3"));
        final Territory t2 = new TerritoryImpl("ter2", cont.getName(), List.of("ter3", "ter1"));
        final Territory t3 = new TerritoryImpl("ter5", cont.getName(), List.of("ter4"));
        cont.addTerritory(t3);
        cont.addTerritory(t2);
        cont.addTerritory(t1);
        player.addTerritory(t1.getTerritoryName());
        player.addTerritory(t2.getTerritoryName());
        player.computeReinforcements(List.of(cont));
        assertEquals(NO_ARMIES, player.getArmiesToPlace());
        player.addTerritory(t3.getTerritoryName());
        player.computeReinforcements(List.of(cont));
        assertEquals(ARMIES_COMPUTED, player.getArmiesToPlace());
    }

    @Test
    void testDrawNewCardIfPossible() {
        final String sep = File.separator;
        final String path = "src" + sep + "main" + sep + "resources" + sep + "it" + sep + "unibo" + sep
            + "risiko" + sep + "maps" + sep + "standard" + sep + "cards.txt";
        final Deck deck = new DeckImpl(path);
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createStandardPlayer();
        assertTrue(player.drawNewCardIfPossible(deck.pullCard()));
        assertEquals(player.getNumberOfCards(), ONE_CARD);
        assertFalse(player.drawNewCardIfPossible(deck.pullCard()));
        player.computeReinforcements(List.of(new ContinentImpl("no", NO_ARMIES)));
        assertTrue(player.drawNewCardIfPossible(deck.pullCard()));
    }

}
