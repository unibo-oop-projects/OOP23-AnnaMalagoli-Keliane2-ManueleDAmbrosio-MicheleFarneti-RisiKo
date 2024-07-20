package it.unibo.risiko.model.event_register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.risiko.model.event.Event;
import it.unibo.risiko.model.player.Player;

/**
 * The Register's implementation
 * @author Keliane Nana
 */
public class RegisterImpl implements Register{
    private List<Event> register;

    public RegisterImpl() {
        this.register = new ArrayList<>();
    }

    public RegisterImpl(Register reg){
        this.register = reg.getAllEvents();
    }

    @Override
    public void addEvent(final Event e) {
        this.register.add(e);
    }

    @Override
    public List<Event> getAllEvents() {
        return Collections.unmodifiableList(this.register);
    }

    @Override
    public Event getLastEvent() {
        if (this.register.isEmpty()) {
            return null;
        }
        return this.register.get(this.register.size()-1);
    }

    @Override
    public List<Event> getAllEventsPlayer(final Player player) {
        List<Event> l=new ArrayList<>();
        for (Event e : this.register) {
            if (e.getEventLeaderId().equals(player.getColorID())) {
                l.add(e);
            }
        }
        return l;
    }
    
}
