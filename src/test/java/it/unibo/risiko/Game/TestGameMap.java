package it.unibo.risiko.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.GameMap;
import it.unibo.risiko.model.map.GameMapImpl;
import it.unibo.risiko.model.map.TerritoryImpl;

public class TestGameMap {
    private static final String SMALL_MAP_NAME = "smallMap";
    private static final Integer SMAL_MAP_MAX_PLAYERS = 2;
    private static final Integer BIG_MAP_MAX_PLAYERS = 6;
    private static final String BIG_MAP_NAME = "bigMap";
    private static String TEST_RESOURCES_PATH;
    private static final String FILE_SEPARATOR = File.separator;

    private GameMap testMap;

    @BeforeAll
    static void setResoucesString() {
        TEST_RESOURCES_PATH = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "Game" + FILE_SEPARATOR
                + "testResources";
    }

    @Test
    void testGetMaxPlayers() {
        assertEquals(SMAL_MAP_MAX_PLAYERS,
                GameMap.getMaxPlayers(TEST_RESOURCES_PATH + FILE_SEPARATOR + SMALL_MAP_NAME));
        assertEquals(BIG_MAP_MAX_PLAYERS,
                GameMap.getMaxPlayers(TEST_RESOURCES_PATH + FILE_SEPARATOR + "maps" + FILE_SEPARATOR + BIG_MAP_NAME));
    }

    /*@Test
    void TestMap() {
        testMap = new GameMapImpl("smallMap", TEST_RESOURCES_PATH);

        assertEquals(SMALL_MAP_NAME, testMap.getName());
        assertEquals(testMap.getMaxPlayers(), 2);
        var territories = testMap.getTerritories();
        assertEquals(2, territories.size());
        List<String> territoriesNames = territories.stream().map(t -> t.getTerritoryName())
                .collect(Collectors.toList());
        assertEquals(List.of("Italia", "Francia"), territoriesNames);

        var continents = testMap.getContinents();
        assertEquals(1, continents.size());
        List<String> continentsNames = continents.stream().map(t -> t.getName()).collect(Collectors.toList());
        assertEquals(List.of("Europa"), continentsNames);

        assertEquals(15, testMap.getStratingArmies(3));
        assertEquals(20, testMap.getStratingArmies(2));

        var territory1 = new TerritoryImpl("Italia", "Europa", List.of("Francia", "Austria", "Slovenia", "Svizzera"));
        var territory2 = new TerritoryImpl("Francia", "Europa" ,List.of("Belgio", "Lussemburgo", "Germania" ,"Svizzera" ,"Italia" ,"Monaco" ,"Spagna" ,"Andorra"));

        assertTrue(testMap.areTerritoriesNear(territory1,territory2));
    }*/
}
