package it.unibo.risiko.model.game;

import java.util.LinkedList;
import java.util.List;

import it.unibo.risiko.model.map.GameMap;
import it.unibo.risiko.model.player.Player;

public class GameFactoryImpl implements GameFactory{

    private int nPlayers;
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

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    @Override
    public Game initializeGame() {
        Game newGame = new GameImpl(map, players);
        newGame.startGame();
        return newGame;
    }

    @Override
    public boolean setPlayersNumber(final int nPlayers) {
        if(nPlayers < map.getMaxPlayers()){
            this.nPlayers = nPlayers;
            return true;
        }
        return false;
    }

    private void fillRemainingSlots() {
        
    }

    @Override
    public int getPlayersCount() {
        return players.size();
    }

}
