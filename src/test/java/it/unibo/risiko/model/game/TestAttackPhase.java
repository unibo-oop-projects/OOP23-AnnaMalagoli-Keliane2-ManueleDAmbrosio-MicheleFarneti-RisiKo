package it.unibo.risiko.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Manuele D'Ambrosio
 */
class TestAttackPhase {
    private static final int INITIAL_ARMIES = 10;
    private static final int MAX_ARMIES = 3;

    @Test
    void testAttackPhase() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player attPlayer = factory.createStandardPlayer();
        final Player defPlayer = factory.createStandardPlayer();
        final Territory attTerritory = new TerritoryImpl("attTerritory", "cont1", List.of("defTerritory"));
        final Territory defTerritory = new TerritoryImpl("defTerritory", "cont1", List.of("attTerritory"));
        attTerritory.addArmies(INITIAL_ARMIES);
        defTerritory.addArmies(MAX_ARMIES);
        attPlayer.addTerritory(attTerritory.getTerritoryName());
        defPlayer.addTerritory(defTerritory.getTerritoryName());

        final AttackPhase attackPhase = new AttackPhaseImpl(3, defTerritory.getNumberOfArmies());
        final int initAttTerritoryArmies = attTerritory.getNumberOfArmies();
        final int initDefTerritoryArmies = defTerritory.getNumberOfArmies();
        attTerritory.removeArmies(attackPhase.getAttackerLostArmies());
        defTerritory.removeArmies(attackPhase.getDefenderLostArmies());
        assertEquals(attackPhase.getAttackerLostArmies(),
                initAttTerritoryArmies - attTerritory.getNumberOfArmies());
        assertEquals(attackPhase.getDefenderLostArmies(),
                initDefTerritoryArmies - defTerritory.getNumberOfArmies());
    }
}
