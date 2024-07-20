package it.unibo.risiko.model.map;

import java.util.List;

/**
 * ReadOnlyContinent implementation
 * @author Keliane Nana
 */
public class ContinentProxy implements ReadOnlyContinent {
    private final String name;
    private final List<Territory> listTerritories;

    public ContinentProxy(final Continent continent) {
        this.name = continent.getName();
        this.listTerritories = List.copyOf(continent.getListTerritories()); 
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Territory> getListTerritories() {
        return this.listTerritories;
    }

}
