package it.unibo.risiko.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */

public class TestStdPlayer {

    @Test
    void testRemoveCard() {
        PlayerFactory playerFactory = new SimplePlayerFactory();
        Player player = playerFactory.createStandardPlayer();
        CardImpl card = new CardImpl("Cannon", "Madagascar");
        player.addCard(card);

    }
}
