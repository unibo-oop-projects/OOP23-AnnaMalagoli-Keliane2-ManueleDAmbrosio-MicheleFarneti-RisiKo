package it.unibo.risiko.player;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.risiko.model.player.AIBehaviour;
import it.unibo.risiko.model.player.AIBehaviourImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

public class TestAIBehaviour {

    private Player player;
    private AIBehaviour ai;

    @BeforeEach
    void setUp() {
        PlayerFactory factory = new SimplePlayerFactory();
        player = factory.createAIPlayer();
        ai = new AIBehaviourImpl(player);
    }

}
