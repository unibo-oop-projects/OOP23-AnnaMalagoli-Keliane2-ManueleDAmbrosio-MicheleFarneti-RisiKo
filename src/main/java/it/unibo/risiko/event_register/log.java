package it.unibo.risiko.event_register;

import java.util.List;

import it.unibo.risiko.event.Event;

/**
 * This interface contains methods for keeping track of in-game events
 * @author Keliane Tchoumkeu
 */
public interface Log {
    /**
     * This method adds a an event in the log
     */
    void addEvent(Event e);

    /**
     * @return a list containing all the events that have been registered in the log
     */
    List<Event> getAllEvents();

    /**
     * @return the last event added to the log
     */
    Event getLastEvent();
}
