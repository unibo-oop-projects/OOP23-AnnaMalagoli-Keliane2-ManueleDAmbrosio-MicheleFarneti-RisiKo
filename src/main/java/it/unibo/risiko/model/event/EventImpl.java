package it.unibo.risiko.model.event;

import java.util.Optional;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * An implementation of the Event interface
 * @author Keliane Nana
 */
public final class EventImpl implements Event {
    private final EventType type;
    private final String attackerTerritoryName;
    private final int attackerNumArmies;
    private final String defenderTerritoryName;
    private final int defenderNumArmies;
    private final String eventLeaderId;
    private final Optional<String> eventLeaderAdversaryId;
    private final Optional<Integer> numArmies;
    private String description;


    public EventImpl(final EventType type,final Territory attacker,final Territory defender,final Player eventLeader,final Optional<Player> eventLeaderAdversary,final Optional<Integer> numArmies) {
        this.type = type;
        this.attackerTerritoryName = attacker.getTerritoryName();
        this.attackerNumArmies = attacker.getNumberOfArmies();
        this.defenderTerritoryName = defender.getTerritoryName();
        this.defenderNumArmies = defender.getNumberOfArmies();
        this.eventLeaderId = eventLeader.getColor_id();
        if (eventLeaderAdversary.isPresent()) {
            this.eventLeaderAdversaryId = Optional.of(eventLeaderAdversary.get().getColor_id());  
        }else{
            this.eventLeaderAdversaryId = Optional.empty();
        }  
        this.numArmies = numArmies;
        this.setDescription();
    }

    @Override
    public String getEventLeaderId() {
        return this.eventLeaderId;
    }

    @Override
    public void setDescription() {
        if (this.type.equals(EventType.ATTACK) && eventLeaderAdversaryId.isPresent()) {
            this.description= "--> ATTACK of "+eventLeaderId+
                        "\nFrom "+attackerTerritoryName+
                        "( number of armies: "+attackerNumArmies+
                        " )\nTo "+defenderTerritoryName+
                        " ( number of armies: "+defenderNumArmies+
                        " ), territory of "+eventLeaderAdversaryId.get();
        }else if (this.type.equals(EventType.TERRITORY_CONQUEST) && eventLeaderAdversaryId.isPresent()) {
            this.description= "--> "+eventLeaderId+
                        " has conquered "+defenderTerritoryName+
                        " which was the territory of "+eventLeaderAdversaryId.get();
        }else if (this.type.equals(EventType.TROOP_MOVEMENT) && numArmies.isPresent()) {
            this.description= "--> Deployment of "+numArmies.get()+
                        " armies of "+eventLeaderId+
                        " from "+attackerTerritoryName+
                        " to "+defenderTerritoryName;
        }else{
            this.description= "Invalid Event"; 
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    
}
