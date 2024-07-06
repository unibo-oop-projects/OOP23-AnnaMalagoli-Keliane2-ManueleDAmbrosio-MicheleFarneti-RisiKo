package it.unibo.risiko.model.event;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

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

    /**
     * Method used to set the description of the Event
     */
    void setDescription();

    /**
     * Method used to get all the information of an Event
     * @return the Event's description
     */
    String getDescription();
}
