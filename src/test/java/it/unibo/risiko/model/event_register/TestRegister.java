package it.unibo.risiko.model.event_register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.event.EventImpl;
import it.unibo.risiko.model.event.EventType;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * @author Keliane Nana
 */
class TestRegister {
        private static final int DEFAULT_TERRITORY_WANTED_NUMBER = 15;
        private static final int DEFAULT_NUM_ARMIES = 3;
        private final PlayerFactory pf = new SimplePlayerFactory();
        private final Player eventLeader = pf.createStandardPlayer();
        private final Target elTarget = new ConquerTerritoriesTarget(eventLeader, DEFAULT_TERRITORY_WANTED_NUMBER);
        private final Player eventLeaderAdversary = pf.createStandardPlayer();
        private final Target elaTarget = new ConquerTerritoriesTarget(eventLeaderAdversary,
                        DEFAULT_TERRITORY_WANTED_NUMBER);
        private final Territory attacker = new TerritoryImpl("Belgia", "Europe", new ArrayList<String>());
        private final Territory defender = new TerritoryImpl("France", "Europe", new ArrayList<String>());
        private final Event e1 = new EventImpl(EventType.ATTACK, attacker, defender, eventLeader,
                        Optional.of(eventLeaderAdversary), Optional.of(DEFAULT_NUM_ARMIES));
        private final Event e2 = new EventImpl(EventType.TROOP_MOVEMENT, attacker, defender, eventLeader,
                        Optional.of(eventLeaderAdversary), Optional.of(DEFAULT_NUM_ARMIES));
        private final Event e3 = new EventImpl(EventType.TERRITORY_CONQUEST, attacker, defender, eventLeader,
                        Optional.of(eventLeaderAdversary), Optional.of(DEFAULT_NUM_ARMIES));
        private final Event e4 = new EventImpl(EventType.ATTACK, attacker, defender, eventLeaderAdversary,
                        Optional.of(eventLeader), Optional.of(DEFAULT_NUM_ARMIES));
        private final Event e5 = new EventImpl(EventType.TROOP_MOVEMENT, attacker, defender, eventLeaderAdversary,
                        Optional.of(eventLeader), Optional.of(DEFAULT_NUM_ARMIES));
        private final Event e6 = new EventImpl(EventType.ATTACK, attacker, defender, eventLeader,
                        Optional.of(eventLeaderAdversary), Optional.of(DEFAULT_NUM_ARMIES));

        @Test
        void testAddEvents() {
                this.eventLeader.setTarget(elTarget);
                this.eventLeaderAdversary.setTarget(elaTarget);
                final Register register = new RegisterImpl();
                register.addEvent(e1);
                register.addEvent(e2);
                assertEquals(2, register.getAllEvents().size());
                register.addEvent(e3);
                assertEquals(List.of(e1, e2, e3), register.getAllEvents());
        }

        @Test
        void testGetLastEvent() {
                final Register register = new RegisterImpl();
                assertEquals(null, register.getLastEvent());
                register.addEvent(e1);
                register.addEvent(e3);
                assertEquals(e3, register.getLastEvent());
        }

        @Test
        void testGetAllEventPlayer() {
                final Register register = new RegisterImpl();
                register.addEvent(e1);
                register.addEvent(e2);
                register.addEvent(e4);
                register.addEvent(e5);
                register.addEvent(e6);
                assertEquals(List.of(e1, e2, e6), register.getAllEventsPlayer(this.eventLeader));
        }

}
