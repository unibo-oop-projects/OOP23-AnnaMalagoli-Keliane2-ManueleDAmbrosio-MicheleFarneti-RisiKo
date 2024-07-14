package it.unibo.risiko.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.game.GameFactory;
import it.unibo.risiko.model.game.GameFactoryImpl;
import it.unibo.risiko.model.map.GameMap;
import it.unibo.risiko.model.map.GameMapImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

public class TestGame {

    private static final String SMALL_MAP_NAME = "smallMap";
    private static final String FILE_SEPARATOR = File.separator;
    private GameFactory factory;
    private PlayerFactory playerFactory;
    private GameMap map;

    private void initializeGame(){
        String resourcesPath = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "Game" + FILE_SEPARATOR
                + "testResources";
        map = new GameMapImpl(SMALL_MAP_NAME, resourcesPath);
        factory = new GameFactoryImpl(map);
        playerFactory = new SimplePlayerFactory();
    }

    @Test
    void testGameInitialization(){
        initializeGame();
        Player player1 = playerFactory.createStandardPlayer();
        Player player2 = playerFactory.createAIPlayer();
        Player player3 = playerFactory.createStandardPlayer();
        Player player4 = playerFactory.createStandardPlayer();

        factory.addNewPlayer(player1);
        factory.addNewPlayer(player2);
        factory.addNewPlayer(player3);
        factory.addNewPlayer(player4);
        var game = factory.initializeGame();

        assertEquals(List.of(player1,player2).stream().collect(Collectors.toSet()),game.getPlayersList().stream().collect(Collectors.toSet()));
        assertFalse(game.gameOver());
        assertEquals(map.getTerritories(),game.getTerritoriesList());
        assertEquals(SMALL_MAP_NAME,game.getMapName());
        for (Territory territoy : map.getTerritories()) {
            assertTrue(game.getOwner(territoy) == player1 || game.getOwner(territoy) == player2);
        }
    }
    
    @Test
    void TestGameTurns(){

    }
}
