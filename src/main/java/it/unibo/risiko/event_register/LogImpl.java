package it.unibo.risiko.event_register;

import java.util.ArrayList;
import java.util.List;

import it.unibo.risiko.event.Event;
import it.unibo.risiko.player.Player;

public class LogImpl implements Log {
    List<Event> register;

    public LogImpl() {
        this.register = new ArrayList<>();
    }

    @Override
    public void addEvent(Event e) {
        this.register.add(e);
    }

    @Override
    public List<Event> getAllEvents() {
        return this.register;
    }

    @Override
    public Event getLastEvent() {
        if (this.register.isEmpty()) {
            return null;
        }
        return this.register.get(this.register.size()-1);
    }

    @Override
    public List<Event> getAllEventsPlayer(Player player) {
        List<Event> l=new ArrayList<>();
        for (Event e : this.register) {
            if (e.getPlayer().equals(player)) {
                l.add(e);
            }
        }
        return l;
    }
    
}
