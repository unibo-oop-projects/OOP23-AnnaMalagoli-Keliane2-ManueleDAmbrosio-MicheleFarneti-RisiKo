package it.unibo.risiko.model.event;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

public class EventImpl implements Event {
    private EventType type;
    private Territory attacker;
    private Territory defender;
    private Player eventLeader;
    private Player eventLeaderAdversary;
    private String description;
    private int numArmies;


    public EventImpl(EventType type, Territory attacker, Territory defender, Player eventLeader, int numArmies) {
        this.type = type;
        this.attacker = attacker;
        this.defender = defender;
        this.eventLeader = eventLeader;
        this.numArmies = numArmies;
        this.setDescription();
    }

    public EventImpl(EventType type, Territory attacker, Territory defender, Player eventLeader, Player eventLeaderAdversary) {
        this.type = type;
        this.attacker = attacker;
        this.defender = defender;
        this.eventLeader = eventLeader;
        this.eventLeaderAdversary = eventLeaderAdversary;
        this.setDescription();
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
    public void setDescription() {
        if (this.type.equals(EventType.ATTACK)) {
            this.description= "--> ATTACK of "+eventLeader.getColor_id()+
                        "\nFrom "+attacker.getTerritoryName()+
                        "( number of armies: "+attacker.getNumberOfArmies()+
                        " )\nTo "+defender.getTerritoryName()+
                        " ( number of armies: "+defender.getNumberOfArmies()+
                        " ), territory of "+eventLeaderAdversary.getColor_id();
        }else if (this.type.equals(EventType.TERRITORY_CONQUEST)) {
            this.description= "--> "+eventLeader.getColor_id()+
                        " has conquered "+defender.getTerritoryName()+
                        " which was the territory of "+eventLeaderAdversary.getColor_id();
        }else if (this.type.equals(EventType.TROOP_MOVEMENT)) {
            this.description= "--> Deployment of "+numArmies+
                        " armies of "+eventLeader.getColor_id()+
                        " from "+attacker.getTerritoryName()+
                        " to "+defender.getTerritoryName();
        }else{
            this.description= "Invalid Event"; 
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    
}
