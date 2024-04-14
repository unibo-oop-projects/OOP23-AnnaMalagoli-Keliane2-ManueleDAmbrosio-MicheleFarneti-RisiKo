package it.unibo.risiko;

public class Card {
    
    private String typeName;
    private String territoryName;

    public Card(String territoryName, String typeName){
        this.typeName = typeName;
        this.territoryName = territoryName;
    }

    public String getTerritoryName(){
        return this.territoryName;
    }

    public String getTypeName(){
        return this.typeName;
    }
}
