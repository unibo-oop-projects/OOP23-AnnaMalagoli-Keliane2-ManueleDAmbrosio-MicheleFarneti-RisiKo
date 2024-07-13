package it.unibo.risiko.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.game.AttackPhase;
import it.unibo.risiko.model.game.AttackPhaseImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */
public class TestAttackPhase {

    @Test
    void testAttackPhase() {
        PlayerFactory factory = new SimplePlayerFactory();
        Player attPlayer = factory.createStandardPlayer();
        Player defPlayer = factory.createStandardPlayer();
        Territory attTerritory = new TerritoryImpl("attTerritory", "cont1", List.of("defTerritory"), 1, 1);
        Territory defTerritory = new TerritoryImpl("defTerritory", "cont1", List.of("attTerritory"), 2, 2);
        attTerritory.addArmies(10);
        defTerritory.addArmies(3);
        attPlayer.addTerritory(attTerritory);
        defPlayer.addTerritory(defTerritory);

        AttackPhase attackPhase = new AttackPhaseImpl(attPlayer, attTerritory, 3, defPlayer, defTerritory);
        int initAttTerritoryArmies = attTerritory.getNumberOfArmies();
        int initDefTerritoryArmies = defTerritory.getNumberOfArmies();
        attackPhase.destroyArmies();
        assertEquals(attackPhase.getAttackerLostArmies(),
                initAttTerritoryArmies - attTerritory.getNumberOfArmies());
        assertEquals(attackPhase.getDefenderLostArmies(),
                initDefTerritoryArmies - defTerritory.getNumberOfArmies());

        attTerritory.addArmies(100);
        while (!attackPhase.isTerritoryConquered()) {
            attackPhase = new AttackPhaseImpl(attPlayer, attTerritory, 3, defPlayer, defTerritory);
        }
        attackPhase.conquerTerritory(attTerritory.getNumberOfArmies() - 1);
        assertEquals(attPlayer.getNumberOfTerritores(), 2);
        assertTrue(defPlayer.isDefeated());
    }
}
