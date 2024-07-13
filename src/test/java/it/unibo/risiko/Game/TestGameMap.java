package it.unibo.risiko.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.GameMap;
import it.unibo.risiko.model.map.GameMapImpl;

public class TestGameMap {
    private static final String SMALL_MAP_NAME = "smallMap";
    private static final Integer SMAL_MAP_MAX_PLAYERS = 2;
    private static final Integer BIG_MAP_MAX_PLAYERS = 6;
    private static final String BIG_MAP_NAME = "bigMap";
    private static String TEST_RESOURCES_PATH;
    private static final String FILE_SEPARATOR = File.separator;

    private GameMap testMap;

    /**
     * Trial test.
     */

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
        assertEquals(BIG_MAP_MAX_PLAYERS, GameMap.getMaxPlayers(TEST_RESOURCES_PATH + FILE_SEPARATOR + BIG_MAP_NAME));
    }

    @BeforeEach
    void initilizeMap(){
        testMap = new GameMapImpl("smallMap", TEST_RESOURCES_PATH);
    }

    @Test
    void TestMap() {
    }
}
