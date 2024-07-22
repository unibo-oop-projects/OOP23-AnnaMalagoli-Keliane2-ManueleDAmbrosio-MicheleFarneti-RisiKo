package it.unibo.risiko.model.territories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.TerritoriesImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * Class used to execute the tests on the classes for the territories.
 * 
 * @author Anna Malagoli
 */
class TestTerritories {

    private static final String ITALIA = "Italia";
    private static final String FRANCIA = "Francia";
    private final String separator = File.separator;

    /**
     * Test of the method of the class TerritoryImpl.
     */
    @Test
    void testTerritoryImpl() {
        final int numberArmies = 25;
        /* creation of a new territory by the constructor of the class TerritoryImpl */
        final Territory territory = new TerritoryImpl("Italy", "Europe",
                List.of("France", "Austria", "Slovenia", "Swiss"));
        /* check that when created the territory does not have armies */
        assertEquals(territory.getNumberOfArmies(), 0);
        /* added armies in the territory */
        territory.addArmies(numberArmies);
        assertEquals(territory.getNumberOfArmies(), numberArmies);
        /* check of the methods callable from/in the territory */
        assertEquals("Italy", territory.getTerritoryName());
        assertEquals("Europe", territory.getContinentName());
        territory.removeArmies(numberArmies);
        assertEquals(territory.getNumberOfArmies(), 0);
        assertEquals(territory.getListOfNearTerritories(), List.of("France", "Austria", "Slovenia", "Swiss"));
    }

    /**
     * Test of the method of the class Territories that has to read the
     * information for the initialization of the territories.
     */
    @Test
    void testTerritories() {
        final String path = "src" + separator + "test" + separator + "java" + separator + "it" + separator + "unibo"
                + separator + "risiko" + separator + "model" + separator + "territories" + separator
                + "Territories.txt";
        final Territories territories = new TerritoriesImpl(path);
        final Continent continent;
        final int bonusArmyEurope = 5;
        final List<Territory> territoriesList = territories.getListTerritories();
        final List<Continent> continentList = territories.getListContinents();
        assertEquals(territoriesList.get(0).getTerritoryName(), ITALIA);
        assertEquals(territoriesList.get(0).getListOfNearTerritories().get(0), FRANCIA);
        assertEquals(territoriesList.get(1).getTerritoryName(), FRANCIA);
        assertEquals(continentList.get(0).getName(), "Europa");
        assertEquals(continentList.get(0).getBonusArmies(), bonusArmyEurope);
        assertEquals(continentList.size(), 1);
        continent = continentList.get(0);
        assertEquals(continent.getListTerritories().get(0).getTerritoryName(), ITALIA);
        assertEquals(continent.getListTerritories().get(1).getTerritoryName(), FRANCIA);
        // Added three armies in Italia
        territories.addArmiesInTerritory(ITALIA, 3);
        for (final var elem : territoriesList) {
            if (ITALIA.equals(elem.getTerritoryName())) {
                assertEquals(elem.getNumberOfArmies(), 3);
            }
        }
    }

    /**
     * Test the method used to verify if two territories are adjacent.
     */
    @Test
    void testTwoTerritoriesAreNear() {
        final String path = "src" + separator + "test" + separator + "java" + separator + "it" + separator + "unibo"
                + separator + "risiko" + separator + "model" + separator + "territories" + separator
                + "Territories.txt";
        final Territories territories = new TerritoriesImpl(path);

        assertTrue(territories.territoriesAreNear("Francia", "Italia"));
        assertFalse(territories.territoriesAreNear("Italia", "Spagna"));
    }

}
