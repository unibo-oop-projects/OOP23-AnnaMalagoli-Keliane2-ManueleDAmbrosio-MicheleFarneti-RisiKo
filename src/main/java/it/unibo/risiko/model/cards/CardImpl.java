package it.unibo.risiko.model.cards;

/**
 * An implementation of the interface Card.
 * 
 * @author Anna Malagoli 
 */
public class CardImpl implements Card {

    private final String typeName;
    private final String territoryName;

    /**
     * Constructor is used to set the information a the card.
     * 
     * @param territoryName is the name of the territory in the card
     * @param typeName is the name of the type of the card that can be of 
     * three different types: Fante, Cavaliere, Cannone 
     */
    public CardImpl(final String territoryName, final String typeName) {
        this.typeName = typeName;
        this.territoryName = territoryName;
    }

    /**
     * Method used to get the territory's name that is on the card.
     * @return the name of the territory
     */
    @Override
    public String getTerritoryName() {
        return this.territoryName;
    }

    /**
     * Method used to get the type of card.
     * @return the name of the type
     */
    @Override
    public String getTypeName() {
        return this.typeName;
    }
}
