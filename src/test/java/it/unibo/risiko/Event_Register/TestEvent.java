package it.unibo.risiko.Event_Register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.Territory;
import it.unibo.risiko.TerritoryImpl;
import it.unibo.risiko.event.Event;
import it.unibo.risiko.event.EventImpl;
import it.unibo.risiko.event.EventType;
import it.unibo.risiko.player.Player;
import it.unibo.risiko.player.StdPlayer;


public class TestEvent {
    private Player eventLeader=new StdPlayer("black", 0);
    private Player eventLeaderAdversary=new StdPlayer("yellow", 0);
    private Territory attacker=new TerritoryImpl("Belgia", "Europe", new ArrayList<String>());
    private Territory defender=new TerritoryImpl("France", "Europe", new ArrayList<String>());
    private Event e=new EventImpl(EventType.ATTACK, attacker, defender, eventLeader, eventLeaderAdversary);

    @Test
    void testGetEventType(){
        assertEquals(EventType.ATTACK, e.getEventType());
    }

    @Test
    void testGetEventLeader(){
        assertEquals(this.eventLeader, e.getEventLeader());
    }

    @Test
    void testGetAttackingTerritory(){
        assertEquals(this.attacker, e.getAttackingTerritory());
    }

    @Test
    void testGetDefenderTerritory(){
        assertEquals(this.defender, e.getDefenderTerritory());
    }
}
