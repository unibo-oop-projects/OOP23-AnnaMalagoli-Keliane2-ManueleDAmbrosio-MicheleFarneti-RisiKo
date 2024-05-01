package it.unibo.risiko.event;

import it.unibo.risiko.Territory;
import it.unibo.risiko.player.Player;

public class EventImpl implements Event {
    private EventType type;
    private Territory attacker;
    private Territory defender;
    private Player eventLeader;

    public EventImpl(EventType type, Territory attacker, Territory defender, Player eventLeader) {
        this.type = type;
        this.attacker = attacker;
        this.defender = defender;
        this.eventLeader = eventLeader;
    }

    @Override
    public EventType getEventType() {
        return this.type;
    }

    @Override
    public Player getPlayer() {
        return this.eventLeader;
    }

    @Override
    public Territory getAttackingTerritory() {
        return this.attacker;
    }

    @Override
    public Territory getDefenderTerritory() {
        return this.defender;
    }
    
}
