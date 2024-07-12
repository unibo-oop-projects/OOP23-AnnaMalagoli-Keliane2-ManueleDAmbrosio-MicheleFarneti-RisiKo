package it.unibo.risiko.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */

public class TestPlayer {

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
        player.addTerritory(new TerritoryImpl("ter1", "cont1", List.of("ter2", "ter3")));
        player.addTerritory(new TerritoryImpl("ter2", "cont2", List.of("ter3", "ter1")));
        player.computeReinforcements();
        assertEquals(player.getArmiesToPlace(), 0);
    }
}
