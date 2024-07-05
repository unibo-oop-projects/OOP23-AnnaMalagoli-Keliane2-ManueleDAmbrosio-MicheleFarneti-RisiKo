package it.unibo.risiko.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.player.StdPlayer;

/**
 * @author Manuele D'Ambrosio
 */

public class TestStdPlayer {

    @Test
    void testRemoveCard() {
        StdPlayer player = new StdPlayer("Green", 0);
        CardImpl card = new CardImpl("Cannon", "Madagascar");
        player.addCard(card);

        assertTrue(player.isOwnedCard(card));
    }
}
