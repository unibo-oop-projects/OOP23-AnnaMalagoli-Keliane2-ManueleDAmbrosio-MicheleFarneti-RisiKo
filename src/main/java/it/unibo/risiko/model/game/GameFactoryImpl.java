package it.unibo.risiko.model.game;

import java.util.LinkedList;
import java.util.List;

import it.unibo.risiko.model.map.GameMap;
import it.unibo.risiko.model.player.Player;

public class GameFactoryImpl implements GameFactory{

    private GameMap map;
    private List<Player> players;

    public GameFactoryImpl(GameMap map) {
        this.map= map;
        players = new LinkedList<>();
    }

    @Override
    public boolean addNewPlayer(Player player) {
        if(players.size()<map.getMaxPlayers()){
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
