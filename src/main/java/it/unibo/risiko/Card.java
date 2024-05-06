package it.unibo.risiko;
/**
 * The class Card is used to implement the cards of the game. 
 * They can show the player which continents they have at the beginning 
 * of the match or theycan be played to have and addictional number of armies.
 * 
 * @author Anna Malagoli 
 */
public class Card {
    
    private final String typeName;
    private final String territoryName;

    /**
     * Constructor is used to set the information a the card.
     * 
     * @param territoryName is the name of the territory in the card
     * @param typeName is the name of the type of the card that can be of 
     * three different types: Fante, Cavaliere, Cannone 
     */
    public Card(final String territoryName, final String typeName) {
        this.typeName = typeName;
        this.territoryName = territoryName;
    }

    /**
     * Method used to get the territory's name that is on the card.
     * @return the name of the territory
     */
    public String getTerritoryName() {
        return this.territoryName;
    }

    /**
     * Method used to get the type of card.
     * @return the name of the type
     */
    public String getTypeName() {
        return this.typeName;
    }
}
