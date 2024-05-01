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
     * @return the event's type
     */
    EventType getEventType();
    
    /**
     * @return the player carrying the event
     */
    Player getPlayer();

    /**
     * @return the attacking territory
     */
    Territory getAttackingTerritory();

    /**
     * @return the defender territory
     */
    Territory getDefenderTerritory();

}
