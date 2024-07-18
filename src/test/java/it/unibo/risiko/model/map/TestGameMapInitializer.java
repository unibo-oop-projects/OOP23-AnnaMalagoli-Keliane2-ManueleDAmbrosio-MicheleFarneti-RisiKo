package it.unibo.risiko.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

class TestGameMapInitializer {
        private static final String SMALL_MAP_NAME = "smallMap";
        private static final Integer SMAL_MAP_MAX_PLAYERS = 2;
        private static final Integer BIG_MAP_MAX_PLAYERS = 6;
        private static final String BIG_MAP_NAME = "bigMap";
        private static String testResourcePath;
        private static final String FILE_SEPARATOR = File.separator;

        private static PlayerFactory playerFactory;

        @BeforeAll
        static void setResoucesString() {
                testResourcePath = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "Game"
                                + FILE_SEPARATOR
                                + "testResources";
                playerFactory = new SimplePlayerFactory();
        }

        @Test
        void testGetMaxPlayers() {
                assertEquals(SMAL_MAP_MAX_PLAYERS,
                                GameMapInitializer.getMaxPlayers(testResourcePath + FILE_SEPARATOR + SMALL_MAP_NAME));
                assertEquals(BIG_MAP_MAX_PLAYERS,
                                GameMapInitializer
                                                .getMaxPlayers(testResourcePath + FILE_SEPARATOR + "maps"
                                                                + FILE_SEPARATOR + BIG_MAP_NAME));
        }

        @Test
        void testMarp() {
                final GameMapInitializer testMap = new GameMapInitializerImpl("smallMap", testResourcePath);

                assertEquals(SMALL_MAP_NAME, testMap.getMapName());
                assertEquals(testMap.getMaxPlayers(), 2);
                final var territories = new Territories(testMap.getTerritoriesPath());
                assertEquals(6, territories.getListTerritories().size());
                final List<String> territoriesNames = territories.getListTerritories().stream()
                                .map(t -> t.getTerritoryName())
                                .collect(Collectors.toList());
                assertEquals(List.of("Italia", "Francia", "Lichtestein", "Belgio", "San-Marino", "Monaco"),
                                territoriesNames);

                final List<Continent> continentNames = territories.getListContinents();
                assertEquals("Europa", continentNames.get(0).getName());

                assertEquals(5, testMap.getStartingArmies(3));
                assertEquals(10, testMap.getStartingArmies(2));

                final var deck = new DeckImpl(testMap.getDeckPath());
                assertEquals(deck.getListCards().size(), 1);

                final Player player1 = playerFactory.createStandardPlayer();
                final Player player2 = playerFactory.createAIPlayer();
                final var target = testMap.generateTarget(0, List.of(player1, player2), territories);
                assertEquals(target.getPlayer(), player1);
        }
}
