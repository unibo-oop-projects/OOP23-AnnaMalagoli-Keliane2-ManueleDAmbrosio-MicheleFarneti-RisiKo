package it.unibo.risiko.event;

import it.unibo.risiko.Territory;
import it.unibo.risiko.player.Player;

public class EventImpl implements Event {
    private EventType type;
    private Territory attacker;
    private Territory defender;
    private Player eventLeader;
    private Player eventLeaderAdversary;

    public EventImpl(EventType type, Territory attacker, Territory defender, Player eventLeader, Player eventLeaderAdversary) {
        this.type = type;
        this.attacker = attacker;
        this.defender = defender;
        this.eventLeader = eventLeader;
        this.eventLeaderAdversary=eventLeaderAdversary;
    }

    @Override
    public EventType getEventType() {
        return this.type;
    }

    @Override
    public Player getEventLeader() {
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

    @Override
    public String toString() {
        switch (type) {
            case ATTACK:
                return "Attack of player "+eventLeader.getColor_id()+
                        " from "+attacker.getTerritoryName()+
                        "( number of armies: "+attacker.getNumberOfArmies()+
                        " )\n to "+defender.getTerritoryName()+
                        " ( number of armies: "+defender.getNumberOfArmies()+
                        " ), territory of "+eventLeaderAdversary.getColor_id();
            case TERRITORY_CONQUEST:
                return "The player "+eventLeader.getColor_id()+
                        " has conquered "+defender.getTerritoryName()+
                        "which was the territory of"+eventLeaderAdversary.getColor_id();
            case TROOP_MOVEMENT:
                return "deployment of "+(attacker.getNumberOfArmies()-1)+
                        " armies of "+eventLeader.getColor_id()+
                        " from "+attacker.getTerritoryName()+
                        " to "+defender.getTerritoryName();
            default:
                return "Invalid Event";
        }
    }
    
}
