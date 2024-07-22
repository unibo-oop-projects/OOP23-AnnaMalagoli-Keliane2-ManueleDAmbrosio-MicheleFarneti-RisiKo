package it.unibo.risiko.model.event_register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.player.Player;

/**
 * The Register's implementation.
 * 
 * @author Keliane Nana
 */
public class RegisterImpl implements Register {
    private final List<Event> register;

    /**
     * Initialization of the register field.
     */
    public RegisterImpl() {
        this.register = new ArrayList<>();
    }

    /**
     * Initialization of the register field with elements of an existing register.
     * 
     * @param reg
     */
    public RegisterImpl(final Register reg) {
        this.register = reg.getAllEvents();
    }

    /**
     * This method adds an event in the log.
     * 
     * @param e the event to be added
     */
    @Override
    public void addEvent(final Event e) {
        this.register.add(e);
    }

    /**
     * Method used to get all the events of the register.
     * 
     * @return a list containing all the events that have been registered in the log
     */
    @Override
    public List<Event> getAllEvents() {
        return Collections.unmodifiableList(this.register);
    }

    /**
     * Method used to get the last event from the register.
     * 
     * @return the last event added to the log
     */
    @Override
    public Event getLastEvent() {
        if (this.register.isEmpty()) {
            return null;
        }
        return this.register.get(this.register.size() - 1);
    }

    /**
     * This method helps to get all the events of a specific player.
     * 
     * @return a list containing all the events of a particular player that have
     *         been registered in the log
     * @param player the player that carried out the events we want to get
     */
    @Override
    public List<Event> getAllEventsPlayer(final Player player) {
        final List<Event> l = new ArrayList<>();
        for (final Event e : this.register) {
            if (e.getEventLeaderId().equals(player.getColorID())) {
                l.add(e);
            }
        }
        return l;
    }

}
