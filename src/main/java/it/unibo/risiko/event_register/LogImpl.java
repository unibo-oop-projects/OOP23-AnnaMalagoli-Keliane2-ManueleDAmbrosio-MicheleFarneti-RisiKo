package it.unibo.risiko.event_register;

import java.util.ArrayList;
import java.util.List;

import it.unibo.risiko.event.Event;

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
    
}
