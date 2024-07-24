package it.unibo.risiko.model.target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.ContinentImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.objective.ConquerContinentTarget;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.DestroyPlayerTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Keliane Nana
 */
class TestTarget {
    private final static String AFRICA = "Afica";
    private final Territory nigeria = new TerritoryImpl("Nigeria", AFRICA, List.of("Cameroon", "Tchad", "Niger"));
    private final Territory cameroon = new TerritoryImpl("Cameroon", AFRICA, List.of("Nigeria", "Tchad", "Niger"));
    private final Territory tchad = new TerritoryImpl("Tchad", AFRICA, List.of("Cameroon", "Nigeria", "Niger"));
    private final Territory italy = new TerritoryImpl("Italy", "Europe", List.of("Belgia", "Francia"));

    @Test
    void testPlayerDestroyTarget() {
        // testing remainingActions and isAchieved of DestroyPlayerTarget
        final PlayerFactory pf = new SimplePlayerFactory();
        final Player p1 = pf.createStandardPlayer();
        p1.addTerritory(cameroon.getTerritoryName());
        p1.addTerritory(italy.getTerritoryName());
        final Player p3 = pf.createStandardPlayer();
        final Target playerDestroyTarget = new DestroyPlayerTarget(p3, p1);
        p3.setTarget(playerDestroyTarget);
        assertEquals(2, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.addTerritory(nigeria.getTerritoryName());
        assertEquals(3, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.removeTerritory(cameroon.getTerritoryName());
        p1.removeTerritory(italy.getTerritoryName());
        assertEquals(1, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.removeTerritory(nigeria.getTerritoryName());
        assertEquals(0, playerDestroyTarget.remainingActions());
        assertTrue(playerDestroyTarget.isAchieved());
    }

    @Test
    void testRemainingActionsContinentTarget() {
        // testing remainingActions and isAchieved of ConquerContinentTarget
        final PlayerFactory pf = new SimplePlayerFactory();
        final Player p2 = pf.createStandardPlayer();
        final Continent africa = new ContinentImpl(AFRICA, 2);
        africa.addTerritory(nigeria);
        africa.addTerritory(cameroon);
        africa.addTerritory(tchad);
        final Target continentTarget = new ConquerContinentTarget(p2, africa);
        p2.setTarget(continentTarget);
        assertEquals(3, continentTarget.remainingActions());
        assertFalse(continentTarget.isAchieved());
        p2.addTerritory(cameroon.getTerritoryName());
        p2.addTerritory(italy.getTerritoryName());
        assertEquals(2, continentTarget.remainingActions());
        assertFalse(continentTarget.isAchieved());
        p2.addTerritory(tchad.getTerritoryName());
        p2.addTerritory(nigeria.getTerritoryName());
        assertEquals(0, continentTarget.remainingActions());
        assertTrue(continentTarget.isAchieved());
    }

    @Test
    void testRemainingActionsTerritoryTarget() {
        // testing remainingActions and isAchieved of ConquerTerritoryTarget
        final PlayerFactory pf = new SimplePlayerFactory();
        final Player p3 = pf.createStandardPlayer();
        final Target territoryTarget = new ConquerTerritoriesTarget(p3, 3);
        p3.setTarget(territoryTarget);
        assertEquals(3, territoryTarget.remainingActions());
        assertFalse(territoryTarget.isAchieved());
        p3.addTerritory(cameroon.getTerritoryName());
        assertEquals(2, territoryTarget.remainingActions());
        assertFalse(territoryTarget.isAchieved());
        p3.addTerritory(italy.getTerritoryName());
        p3.addTerritory(nigeria.getTerritoryName());
        assertEquals(0, territoryTarget.remainingActions());
        assertTrue(territoryTarget.isAchieved());
    }

}
