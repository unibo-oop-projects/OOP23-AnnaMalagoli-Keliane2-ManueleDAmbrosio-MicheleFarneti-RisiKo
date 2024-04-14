package it.unibo.risiko;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

public class TerritoryImpl implements Territory {
    private String name;
    private int numberOfArmies;
    private List<String> listNearTerritories = new ArrayList<>();
    private String continent;

    public TerritoryImpl(String name, String continent, List<String> listNearTerritories){
        this.name = name;
        this.continent = continent;
        this.listNearTerritories = listNearTerritories;
        this.numberOfArmies = 0;
    }

    @Override
    public String getTerritoryName() {
        return this.name;
    }

    @Override
    public int getNumberOfArmies() {
        return this.numberOfArmies;
    }
    @Override
    public void addArmies(int number) {
        this.numberOfArmies += number;
    }

    @Override
    public void removeArmies(int number) {
        this.numberOfArmies -= number;
    }

    @Override
    public List<String> getListOfNearTerritories() {
        List<String> copyListNearTerritories;
        copyListNearTerritories = new ArrayList<>(this.getNumberOfArmies());
        return copyListNearTerritories;
    }

    @Override
    public String getContinetName() {
        return this.continent;
    }
    
}
