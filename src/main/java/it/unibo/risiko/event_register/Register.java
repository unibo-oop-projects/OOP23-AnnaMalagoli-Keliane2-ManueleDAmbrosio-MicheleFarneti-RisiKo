package it.unibo.risiko.event_register;

import java.util.List;

import it.unibo.risiko.event.Event;
import it.unibo.risiko.player.Player;

/**
 * This interface contains methods for keeping track of in-game events
 * @author Keliane Tchoumkeu
 */
public interface Register {
    /**
     * This method adds an event in the log
     * @param e the event to be added
     */
    void addEvent(final Event e);

    /**
     * @return a list containing all the events that have been registered in the log
     */
    List<Event> getAllEvents();

    /**
     * @return a list containing all the events of a particular player that have been 
     * registered in the log
     * @param player the player that carried out the events we want to get
     */
    List<Event> getAllEventsPlayer(final Player player);

    /**
     * @return the last event added to the log
     */
    Event getLastEvent();
}
