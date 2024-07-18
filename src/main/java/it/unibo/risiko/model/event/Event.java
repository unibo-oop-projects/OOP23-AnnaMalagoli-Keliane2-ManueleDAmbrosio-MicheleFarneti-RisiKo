package it.unibo.risiko.model.event;

/**
 * This interface contains methods that 
 * help to know more about an event
 * @author Keliane Tchoumkeu
 */
public interface Event {
   
    /**
     * This method is used to know the player initiating an event
     * @return the player carrying the event
     */
    String getEventLeaderId();

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
