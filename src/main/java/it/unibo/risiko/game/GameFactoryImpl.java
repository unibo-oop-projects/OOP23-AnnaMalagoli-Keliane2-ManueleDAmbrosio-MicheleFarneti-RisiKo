package it.unibo.risiko.game;

import java.util.LinkedList;
import java.util.List;

import it.unibo.risiko.map.GameMap;
import it.unibo.risiko.player.Player;

public class GameFactoryImpl implements GameFactory{

    private int nPlayers;
    GameMap map;
    List<Player> players;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeGame'");
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
