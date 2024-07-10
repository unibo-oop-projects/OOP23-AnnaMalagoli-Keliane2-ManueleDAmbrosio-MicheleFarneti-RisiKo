package it.unibo.risiko.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;
import it.unibo.risiko.model.player.StdPlayer;

/**
 * @author Manuele D'Ambrosio
 */

public class TestStdPlayer {

    @Test
    void testRemoveCard() {
        PlayerFactory factory = new SimplePlayerFactory();
        Player player = factory.createStandardPlayer();
        player.addCard(new CardImpl("Territory1", "A"));
    }
}
