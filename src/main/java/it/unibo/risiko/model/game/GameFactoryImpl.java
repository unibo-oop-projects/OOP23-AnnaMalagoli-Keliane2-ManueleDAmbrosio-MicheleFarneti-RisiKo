package it.unibo.risiko.model.game;

import java.util.LinkedList;
import java.util.List;

import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.GameMapInitializerImpl;
import it.unibo.risiko.model.player.Player;

public class GameFactoryImpl implements GameFactory {

    private GameMapInitializer map;
    private List<Player> players;

    /**
     * Creates a gamefctory instance by giving it the name of the map where the game
     * is oging to be played and the string used to reach the maps resources
     */
    public GameFactoryImpl(String mapName, String resourcePackageString) {
        this.map = new GameMapInitializerImpl(mapName, resourcePackageString);
        players = new LinkedList<>();
    }

    @Override
    public boolean addNewPlayer(Player player) {
        if (players.size() < map.getMaxPlayers()) {
            players.add(player);
            return true;
        }
        return false;
    }

    @Override
    public Game initializeGame() {
        Game newGame = new GameImpl(map, players);
        newGame.startGame();
        return newGame;
    }
}
