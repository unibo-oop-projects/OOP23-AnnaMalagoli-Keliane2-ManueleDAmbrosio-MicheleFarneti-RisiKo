package it.unibo.risiko.model.territories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * Class used to execute the tests on the classes for the territories.
 * 
 * @author Anna Malagoli
 */
class MainTest {

    private static final String ITALIA = "Italia";
    private static final String FRANCIA = "Francia";

    /**
     * Test of the method of the class TerritoryImpl.
     */
    @Test
    void testTerritoryImpl() {
        final int numberArmies = 25;
        /*creation of a new territory by the constructor of the class TerritoryImpl */
        final Territory territory = new TerritoryImpl("Italy", "Europe", List.of("France", "Austria", "Slovenia", "Swiss"));
        /*check that when created the territory does not have armies*/
        assertEquals(territory.getNumberOfArmies(), 0);
        /*added armies in the territory*/
        territory.addArmies(numberArmies);
        assertEquals(territory.getNumberOfArmies(), numberArmies);
        /*check of the methods callable from/in the territory */
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
        final String path = "src/test/java/it/unibo/risiko/territories/Territories.txt";
        final Territories territories = new Territories(path);
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
        //Added three armies in Italia
        territories.addArmiesInTerritory(ITALIA, 3);
        for (var elem : territoriesList) {
            if (elem.getTerritoryName().equals(ITALIA)) {
                assertEquals(elem.getNumberOfArmies(), 3);
            }
        }
    }

    @Test
    void testTwoTerritoriesAreNear() {
        final String path = "src/test/java/it/unibo/risiko/territories/Territories.txt";
        final Territories territories = new Territories(path);
        assertTrue(territories.territoriesAreNear("Francia", "Italia"));
        assertFalse(territories.territoriesAreNear("Italia", "Spagna"));
    }

    /*@Test
    public void testMovementOfArmiesBetweenTwoTerritory() {
        final String path = "src/test/java/it/unibo/risiko/Territories.txt";
        final Territories territories = new Territories(path);
        Verify if the movement of 3 armies between ITALY and SPAIN is permetted
         * and does as expected.

        territories.addArmiesInTerritory(ITALIA, 4);
        Territory France = territories.getListTerritories().get(1);
        Territory Italy = territories.getListTerritories().get(0);
        int movedArmies = Italy.getNumberOfArmies() - 1;
        territories.moveArmiesFromPlaceToPlace(ITALIA, FRANCIA, movedArmies);
        assertEquals(1, Italy.getNumberOfArmies());
        assertEquals(movedArmies, France.getNumberOfArmies());
    }*/

    /*@Test
    public void testMovementOfArmiesBetweenTwoTerritory() {
        extracion of two territories that are in the territories example file
        GameController controller = new GameController();
        final String path = "src/test/java/it/unibo/risiko/Territories.txt";
        final Territories territories = new Territories(path);
        territories.addArmiesInTerritory(ITALIA, 4);
        Territory France = territories.getListTerritories().get(1);
        Territory Italy = territories.getListTerritories().get(0);
        int movedArmies = Italy.getNumberOfArmies() - 1;
        controller.moveArmies(ITALIA, FRANCIA, movedArmies);
        assertEquals(1, Italy.getNumberOfArmies());
        assertEquals(movedArmies, France.getNumberOfArmies());
    }*/

}
