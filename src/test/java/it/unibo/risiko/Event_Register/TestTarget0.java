package it.unibo.risiko.Event_Register;

import it.unibo.risiko.objective.Target;
import it.unibo.risiko.player.Player;
import it.unibo.risiko.player.StdPlayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.Continent;
import it.unibo.risiko.ContinentImpl;
import it.unibo.risiko.Territory;
import it.unibo.risiko.TerritoryImpl;
import it.unibo.risiko.objective.ConquerContinentTarget;
import it.unibo.risiko.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.objective.DestroyPlayerTarget;

public class TestTarget0 {
    private Territory nigeria=new TerritoryImpl("Nigeria", "Africa", List.of("Cameroon","Tchad","Niger"));
    private Territory cameroon =new TerritoryImpl("Cameroon", "Africa", List.of("Nigeria","Tchad","Niger"));
    private Territory tchad=new TerritoryImpl("Tchad", "Africa", List.of("Cameroon","Nigeria","Niger"));
    private Territory italy=new TerritoryImpl("Italy", "Europe", List.of("Belgia","Francia"));

    @Test
    void testPlayerDestroyTarget(){
        //testing remainingActions and isAchieved of DestroyPlayerTarget
        Player p1=new StdPlayer("black", 0);
        p1.addTerritory(cameroon);
        p1.addTerritory(italy);
        Player p3=new StdPlayer("green", 0);
        Target playerDestroyTarget=new DestroyPlayerTarget(p3, p1);
        p1.setTarget(Optional.of(playerDestroyTarget));
        assertEquals(2, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.addTerritory(nigeria);
        assertEquals(3, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.removeTerritory(cameroon);
        p1.removeTerritory(italy);
        assertEquals(1, playerDestroyTarget.remainingActions());
        assertFalse(playerDestroyTarget.isAchieved());
        p1.removeTerritory(nigeria);
        assertEquals(0, playerDestroyTarget.remainingActions());
        assertTrue(playerDestroyTarget.isAchieved());
    }

    @Test
    void testRemainingActions_ContinentTarget(){
        //testing remainingActions and isAchieved of ConquerContinentTarget
        Player p2=new StdPlayer("yellow", 0);
        Continent africa=new ContinentImpl("Africa");
        africa.addTerritory(nigeria);
        africa.addTerritory(cameroon);
        africa.addTerritory(tchad);
        Target continentTarget=new ConquerContinentTarget(p2, africa);
        p2.setTarget(Optional.of(continentTarget));
        assertEquals(3, continentTarget.remainingActions());
        assertFalse(continentTarget.isAchieved());
        p2.addTerritory(cameroon);
        p2.addTerritory(italy);
        assertEquals(2, continentTarget.remainingActions());
        assertFalse(continentTarget.isAchieved());
        p2.addTerritory(tchad);
        p2.addTerritory(nigeria);
        assertEquals(0, continentTarget.remainingActions());
        assertTrue(continentTarget.isAchieved());
    }
    
    @Test
    void testRemainingActions_TerritoryTarget(){
        //testing remainingActions and isAchieved of ConquerTerritoryTarget
        Player p3=new StdPlayer("green", 0);
        Target territoryTarget=new ConquerTerritoriesTarget(p3, 3);
        p3.setTarget(Optional.of(territoryTarget));
        assertEquals(3, territoryTarget.remainingActions());
        assertFalse(territoryTarget.isAchieved());
        p3.addTerritory(cameroon);
        assertEquals(2, territoryTarget.remainingActions());
        assertFalse(territoryTarget.isAchieved());
        p3.addTerritory(italy);
        p3.addTerritory(nigeria);
        assertEquals(0, territoryTarget.remainingActions());
        assertTrue(territoryTarget.isAchieved());
    }

}
