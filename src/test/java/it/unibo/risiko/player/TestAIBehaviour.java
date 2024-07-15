package it.unibo.risiko.player;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.AIBehaviour;
import it.unibo.risiko.model.player.AIBehaviourImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */

public class TestAIBehaviour {

    private Player player;
    private AIBehaviour ai;
    private Deck deck;

    @BeforeEach
    void setUp() {
        final String SEP = File.separator;
        final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "maps" + SEP + "standard" + SEP + "cards.txt";
        deck = new DeckImpl(PATH);
        PlayerFactory factory = new SimplePlayerFactory();
        player = factory.createAIPlayer();
        Territory t1 = new TerritoryImpl("t1", "cont", List.of("t2"));
        Territory t2 = new TerritoryImpl("t2", "cont", List.of("t1"));
        
        player.addTerritory(t2);
        player.addTerritory(t1);

        ai = new AIBehaviourImpl(player);
    }

}
