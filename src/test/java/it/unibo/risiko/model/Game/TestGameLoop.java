package it.unibo.risiko.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.map.TerritoriesImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

/**
 * Test for the gameLoopManagement objects. Done by simulating players actions
 * on a game.
 */
class TestGameLoop {

    private static final String SMALL_MAP_NAME = "smallMap";
    private static final String FILE_SEPARATOR = File.separator;
    private final PlayerFactory playerFactory = new SimplePlayerFactory();
    private List<Player> players = new LinkedList<>();
    private GameMapInitializer testMap;
    private String resourcesPath;
    private Deck deck;
    private Territories territories;

    private void initializePath() {
        resourcesPath = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "model"
                + FILE_SEPARATOR + "testresources";
    }

    TestGameLoop() {
        initializePath();
        testMap = new GameMapInitializerImpl(SMALL_MAP_NAME, resourcesPath);
        this.deck = new DeckImpl(testMap.getDeckPath());
        this.territories = new TerritoriesImpl(testMap.getTerritoriesPath());
    }

    @BeforeEach
    void testGameInitialization() {
        initializePath();
        final Player player1 = playerFactory.createStandardPlayer();
        final Player player2 = playerFactory.createAIPlayer();
        players = List.of(player1, player2);
        testMap = new GameMapInitializerImpl(SMALL_MAP_NAME, resourcesPath);
        this.deck = new DeckImpl(testMap.getDeckPath());
        this.territories = new TerritoriesImpl(testMap.getTerritoriesPath());
    }

    @Test
    void testPlacementPhaseActionHandlers() {
        final var occupationActionHandler = new OccupationPhaseActionHandler();
        final var armiesPlacementHandler = new PlacementPhaseActionHandler();
        assertEquals(0, occupationActionHandler.getActivePlayerIndex());
        assertEquals(GameStatus.TERRITORY_OCCUPATION, occupationActionHandler.getGameStatus());

        final var territoryPlayer1 = territories.getListTerritories().get(0).getTerritoryName();
        territories.setOwner(territoryPlayer1, players.get(0).getColorID());
        players.get(0).addTerritory(territoryPlayer1);

        final var territoryPlayer2 = territories.getListTerritories().get(1).getTerritoryName();
        territories.setOwner(territoryPlayer2, players.get(1).getColorID());
        players.get(1).addTerritory(territoryPlayer1);

        occupationActionHandler.addArmies(players.get(0), territoryPlayer1, territories);
        assertEquals(1, territories.getListTerritories().stream()
                .filter(t -> t.getTerritoryName().equals(territoryPlayer1)).findAny().get().getNumberOfArmies());

        occupationActionHandler.addArmies(players.get(0), territoryPlayer2, territories);
        assertEquals(1, territories.getListTerritories().stream()
                .filter(t -> t.getTerritoryName().equals(territoryPlayer2)).findAny().get().getNumberOfArmies());

        occupationActionHandler.addArmies(players.get(1), territoryPlayer2, territories);
        assertEquals(2, territories.getListTerritories().stream()
                .filter(t -> t.getTerritoryName().equals(territoryPlayer2)).findAny().get().getNumberOfArmies());

        players.get(0).setArmiesToPlace(3);
        players.get(1).setArmiesToPlace(3);
        for (Integer i = 0; i < 3; i++) {
            occupationActionHandler.checkActionAndExceute(0, players, territoryPlayer1, territories);
        }
        assertEquals(1, occupationActionHandler.getActivePlayerIndex());
        assertEquals(GameStatus.TERRITORY_OCCUPATION, occupationActionHandler.getGameStatus());

        for (Integer i = 0; i < 3; i++) {
            occupationActionHandler.checkActionAndExceute(1, players, territoryPlayer1, territories);
        }

        assertEquals(0, occupationActionHandler.getActivePlayerIndex());
        assertEquals(GameStatus.ARMIES_PLACEMENT, occupationActionHandler.getGameStatus());

        armiesPlacementHandler.checkActionAndExceute(0, players, territoryPlayer1, territories);
        assertEquals(0, occupationActionHandler.getActivePlayerIndex());
        assertEquals(GameStatus.ARMIES_PLACEMENT, occupationActionHandler.getGameStatus());
    }

    @Test
    void testGameLoopManagerImpl() {
        final var gameLoopManager = new GameLoopManagerImpl();

        assertEquals(0, gameLoopManager.getActivePlayerIndex());
        assertEquals(GameStatus.TERRITORY_OCCUPATION, gameLoopManager.getGameStatus());

        final var territoryPlayer1 = territories.getListTerritories().get(0).getTerritoryName();
        territories.setOwner(territoryPlayer1, players.get(0).getColorID());
        players.get(0).addTerritory(territoryPlayer1);

        final var territoryPlayer2 = territories.getListTerritories().get(1).getTerritoryName();
        territories.setOwner(territoryPlayer2, players.get(1).getColorID());
        players.get(1).addTerritory(territoryPlayer1);

        players.get(0).setArmiesToPlace(1);
        players.get(1).setArmiesToPlace(2);

        gameLoopManager.placeArmiesIfPossible(players, territoryPlayer1, territories);
        gameLoopManager.placeArmiesIfPossible(players, territoryPlayer2, territories);
        gameLoopManager.placeArmiesIfPossible(players, territoryPlayer2, territories);

        assertEquals(0, gameLoopManager.getActivePlayerIndex());
        assertEquals(GameStatus.ARMIES_PLACEMENT, gameLoopManager.getGameStatus());

        players.get(0).setArmiesToPlace(1);
        gameLoopManager.placeArmiesIfPossible(players, territoryPlayer1, territories);
        assertEquals(0, gameLoopManager.getActivePlayerIndex());
        assertEquals(GameStatus.READY_TO_ATTACK, gameLoopManager.getGameStatus());

        gameLoopManager.skipTurn(players, territories);
        assertEquals(1, gameLoopManager.getActivePlayerIndex());
        assertEquals(GameStatus.READY_TO_ATTACK, gameLoopManager.getGameStatus());

        players.get(1).addCard(deck.pullCard());
        gameLoopManager.skipTurn(players, territories);
        gameLoopManager.skipTurn(players, territories);
        assertEquals(1, gameLoopManager.getActivePlayerIndex());
        assertEquals(GameStatus.CARDS_MANAGING, gameLoopManager.getGameStatus());
    }
}
