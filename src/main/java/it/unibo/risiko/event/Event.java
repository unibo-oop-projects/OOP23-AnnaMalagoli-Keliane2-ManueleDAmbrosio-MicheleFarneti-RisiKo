package it.unibo.risiko.event;

import it.unibo.risiko.Territory;
import it.unibo.risiko.player.Player;

/**
 * This interface contains methods that 
 * help to know more about an event
 * @author Keliane Tchoumkeu
 */
public interface Event {
    /**
     * This method is used to know the type of a specific event
     * @return the event's type
     */
    EventType getEventType();
    
    /**
     * This method is used to know the player initiating an event
     * @return the player carrying the event
     */
    Player getEventLeader();

    /**
     * This method helps to know the territory used by the player carrying an event
     * @return the attacking territory
     */
    Territory getAttackingTerritory();

    /**
     * @return the defender territory
     */
    Territory getDefenderTerritory();

    void setDescription();

    String getDescription();
}
