package it.unibo.risiko.model.event_register;

import java.util.List;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.player.Player;

/**
 * This interface contains methods for keeping track of in-game events
 * @author Keliane Nana
 */
public interface Register {
    /**
     * This method adds an event in the log
     * @param e the event to be added
     */
    void addEvent(final Event e);

    /**
     * Method used to get all the events of the register
     * @return a list containing all the events that have been registered in the log
     */
    List<Event> getAllEvents();

    /**
     * This method helps to get all the events of a specific player
     * @return a list containing all the events of a particular player that have been 
     * registered in the log
     * @param player the player that carried out the events we want to get
     */
    List<Event> getAllEventsPlayer(final Player player);

    /**
     * Method used to get the last event from the register
     * @return the last event added to the log
     */
    Event getLastEvent();
}
