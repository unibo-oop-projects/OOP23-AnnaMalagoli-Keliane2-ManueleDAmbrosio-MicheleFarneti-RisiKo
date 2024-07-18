package it.unibo.risiko.model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;

public class TestGameMap {
    private static final String SMALL_MAP_NAME = "smallMap";
    private static final Integer SMAL_MAP_MAX_PLAYERS = 2;
    private static final Integer BIG_MAP_MAX_PLAYERS = 6;
    private static final String BIG_MAP_NAME = "bigMap";
    private static String TEST_RESOURCES_PATH;
    private static final String FILE_SEPARATOR = File.separator;

    private GameMapInitializer testMap;
    private PlayerFactory playerFactory;

    @BeforeAll
    static void setResoucesString() {
        TEST_RESOURCES_PATH = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "Game" + FILE_SEPARATOR
                + "testResources";
    }

    @Test
    void testGetMaxPlayers() {
        assertEquals(SMAL_MAP_MAX_PLAYERS,
                GameMapInitializer.getMaxPlayers(TEST_RESOURCES_PATH + FILE_SEPARATOR + SMALL_MAP_NAME));
        assertEquals(BIG_MAP_MAX_PLAYERS,
                GameMapInitializer.getMaxPlayers(TEST_RESOURCES_PATH + FILE_SEPARATOR + "maps" + FILE_SEPARATOR + BIG_MAP_NAME));
    }

    @Test
    void TestMap() {
        testMap = new GameMapInitializerImpl("smallMap", TEST_RESOURCES_PATH);

        assertEquals(SMALL_MAP_NAME, testMap.getMapName());
        assertEquals(testMap.getMaxPlayers(), 2);
        var territories = new Territories(testMap.getTerritoriesPath());
        assertEquals(2, territories.getListTerritories().size());
        List<String> territoriesNames = territories.getListTerritories().stream().map(t -> t.getTerritoryName())
                .collect(Collectors.toList());
        assertEquals(List.of("Italia", "Francia"), territoriesNames);

        List<Continent> continentNames = territories.getListContinents();
        assertEquals(List.of("Italia", "Francia"), continentNames);

        assertEquals(15, testMap.getStartingArmies(3));
        assertEquals(20, testMap.getStartingArmies(2));

        var deck = new DeckImpl(testMap.getDeckPath());
        assertEquals(deck.getListCards(),new CardImpl("Italy", "Infantry"));

        Player player1 = playerFactory.createStandardPlayer();
        Player player2 = playerFactory.createAIPlayer();
        var target = testMap.generateTarget(0,List.of(player1,player2),territories);
        assertEquals(target.getPlayer(),player1);
        assertEquals(target.isAchieved(),false);
    }
}
