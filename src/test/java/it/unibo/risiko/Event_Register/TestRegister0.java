package it.unibo.risiko.Event_Register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.event.EventImpl;
import it.unibo.risiko.model.event.EventType;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.event_register.RegisterImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

public class TestRegister0 {
    private PlayerFactory pf=new SimplePlayerFactory();
    private Player eventLeader=pf.createStandardPlayer();
    private Target elTarget= new ConquerTerritoriesTarget(null, 15);
    private Player eventLeaderAdversary=pf.createStandardPlayer();
    private Target elaTarget=new ConquerTerritoriesTarget(null, 0) ;
    private Territory attacker=new TerritoryImpl("Belgia", "Europe", new ArrayList<String>());
    private Territory defender=new TerritoryImpl("France", "Europe", new ArrayList<String>());
    private Event e1=new EventImpl(EventType.ATTACK, attacker, defender, eventLeader, eventLeaderAdversary);
    private Event e2=new EventImpl(EventType.TROOP_MOVEMENT, attacker, defender, eventLeader, eventLeaderAdversary);
    private Event e3=new EventImpl(EventType.TERRITORY_CONQUEST, attacker, defender, eventLeader, eventLeaderAdversary);
    private Event e4=new EventImpl(EventType.ATTACK, attacker, defender, eventLeaderAdversary, eventLeader);
    private Event e5=new EventImpl(EventType.TROOP_MOVEMENT, attacker, defender, eventLeaderAdversary, eventLeader);
    private Event e6=new EventImpl(EventType.ATTACK, attacker, defender, eventLeader, eventLeaderAdversary);

    @Test
    void testAddEvents(){
        this.eventLeader.setTarget(elTarget);
        this.eventLeaderAdversary.setTarget(elaTarget);
        Register register=new RegisterImpl();
        register.addEvent(e1);
        register.addEvent(e2);
        assertEquals(2, register.getAllEvents().size());
        register.addEvent(e3);
        assertEquals(List.of(e1, e2,e3), register.getAllEvents());
    }

    @Test
    void testGetLastEvent(){
        Register register=new RegisterImpl();
        assertEquals(null, register.getLastEvent());
        register.addEvent(e1);
        register.addEvent(e3);
        assertEquals(e3, register.getLastEvent());
    }

    @Test
    void testGetAllEventPlayer(){
        Register register=new RegisterImpl();
        register.addEvent(e1);
        register.addEvent(e2);
        register.addEvent(e4);
        register.addEvent(e5);
        register.addEvent(e6);
        assertEquals(List.of(e1, e2, e6), register.getAllEventsPlayer(this.eventLeader));
    }

}
