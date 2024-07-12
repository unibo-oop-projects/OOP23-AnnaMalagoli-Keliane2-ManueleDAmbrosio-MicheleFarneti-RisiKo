package it.unibo.risiko.Event_Register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.event.EventImpl;
import it.unibo.risiko.model.event.EventType;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;


/*public class TestEvent0 {
    private PlayerFactory pf=new SimplePlayerFactory();
    private Player eventLeader=pf.createStandardPlayer();
    private Player eventLeaderAdversary=pf.createStandardPlayer();
    private Territory attacker=new TerritoryImpl("Belgia", "Europe", new ArrayList<String>());
    private Territory defender=new TerritoryImpl("France", "Europe", new ArrayList<String>());
    private Event e=new EventImpl(EventType.ATTACK, attacker, defender, eventLeader, eventLeaderAdversary);

    @Test
    void testGetEventType(){
        assertEquals(EventType.ATTACK, EventType.ATTACK);
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
}*/
