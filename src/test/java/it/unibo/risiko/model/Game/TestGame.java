package it.unibo.risiko.model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/*public class TestGame {

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
    void TestGameTurnsStandrdPlayers(){
        initializeGame();
        Player player1 = playerFactory.createStandardPlayer();
        Player player2 = playerFactory.createStandardPlayer();

        factory.addNewPlayer(player1);
        factory.addNewPlayer(player2);
        var game = factory.initializeGame();

        assertEquals(GameStatus.TERRITORY_OCCUPATION,game.getGameStatus());
        assertEquals(game.getTurnsCount(),0);
        var firstPlayer = game.getCurrentPlayer();
        var territory1 =  firstPlayer.getOwnedTerritories().iterator().next();
        assertEquals(3, firstPlayer.getOwnedTerritories().size());
        assertFalse(game.skipTurn());
        while(game.placeArmies(territory1, 1));
        assertEquals(firstPlayer.getNumberOfTerritores() + 3,firstPlayer.getOwnedTerritories().stream().mapToInt(t -> t.getNumberOfArmies()).sum());

        var secondPlayer = game.getCurrentPlayer();
        var territory2 =  secondPlayer.getOwnedTerritories().iterator().next();
        while(game.placeArmies(territory2, 1));
        assertEquals(secondPlayer.getNumberOfTerritores() + 3,secondPlayer.getOwnedTerritories().stream().mapToInt(t -> t.getNumberOfArmies()).sum());

        while(game.placeArmies(game.getCurrentPlayer() == firstPlayer ? territory1 : territory2, 1) && game.getGameStatus() == GameStatus.TERRITORY_OCCUPATION);
        assertEquals(GameStatus.ARMIES_PLACEMENT,game.getGameStatus());

        while(game.placeArmies(territory1, firstPlayer.getArmiesToPlace()));
        assertEquals(GameStatus.READY_TO_ATTACK,game.getGameStatus());

        game.setAttacking();
        assertEquals(GameStatus.ATTACKING,game.getGameStatus());

        game.skipTurn();
        assertEquals(secondPlayer,game.getCurrentPlayer());
        assertTrue(game.getGameStatus() == GameStatus.ARMIES_PLACEMENT || game.getGameStatus() == GameStatus.READY_TO_ATTACK|| game.getGameStatus()==GameStatus.CARDS_MANAGING);
        assertFalse(game.getTurnsCount() == 0);
    }

    @Test
    void TestGameTurnsWithAi(){
        initializeGame();
        Player player1 = playerFactory.createStandardPlayer();
        Player player2 = playerFactory.createAIPlayer();
        factory.addNewPlayer(player1);
        factory.addNewPlayer(player2);
        var game = factory.initializeGame();

        assertEquals(GameStatus.TERRITORY_OCCUPATION,game.getGameStatus());
        var standardPlayer = game.getCurrentPlayer();
        var AIPlayer = game.getPlayersList().stream().filter(p -> p.isAI()).findAny().get();
        assertEquals(standardPlayer.getNumberOfTerritores(),3);
        assertEquals(AIPlayer.getNumberOfTerritores(),3);
        var territory1 =  standardPlayer.getOwnedTerritories().iterator().next();
        assertFalse(game.skipTurn());
        while(game.placeArmies(territory1, 1));
        if(AIPlayer != game.getPlayersList().get(0)){
            assertEquals(20,AIPlayer.getOwnedTerritories().stream().mapToInt(t -> t.getNumberOfArmies()).sum());
            assertEquals(21,standardPlayer.getOwnedTerritories().stream().mapToInt(t -> t.getNumberOfArmies()).sum());
        }
        assertTrue(game.getGameStatus() == GameStatus.ARMIES_PLACEMENT || game.getGameStatus() == GameStatus.READY_TO_ATTACK|| game.getGameStatus()==GameStatus.CARDS_MANAGING);
        assertFalse(game.getTurnsCount() == 0);
    }
}*/
