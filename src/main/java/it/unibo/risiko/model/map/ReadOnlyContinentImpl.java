package it.unibo.risiko.model.map;

import java.util.List;

/**
 * ReadOnlyContinent implementation.
 * 
 * @author Keliane Nana
 */
public class ReadOnlyContinentImpl implements ReadOnlyContinent {
    private final String name;
    private final List<Territory> listTerritories;

    /**
     * ReadOnlyContinentImpl constructor.
     * 
     * @param continent the continent to read
     */
    public ReadOnlyContinentImpl(final Continent continent) {
        this.name = continent.getName();
        this.listTerritories = List.copyOf(continent.getListTerritories());
    }

    /**
     * Reads the continent's name.
     * 
     * @return the name of the continent
     */
    @Override
    public final String getName() {
        return this.name;
    }

    /**
     * Helps to get the list containing all the territories of a continent.
     * 
     * @return the continent Territory's list
     */
    @Override
    public final List<Territory> getListTerritories() {
        return this.listTerritories;
    }

}
