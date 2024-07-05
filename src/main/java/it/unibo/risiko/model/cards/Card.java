package it.unibo.risiko.model.cards;
/**
 * The class Card is used to implement the cards of the game. 
 * They can show the player which continents they have at the beginning 
 * of the match or theycan be played to have and addictional number of armies.
 * 
 * @author Anna Malagoli 
 */
public interface Card {

    /**
     * Method used to get the type of card.
     * @return the type of the card
     */
    public String getTypeName();

    /**
     * Method used to get the territory's name that is on the card.
     * @return the name of the territory
     */
    public String getTerritoryName();
    
}
