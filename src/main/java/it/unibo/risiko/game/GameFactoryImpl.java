package it.unibo.risiko.game;

import java.util.List;

import it.unibo.risiko.player.Player;

public class GameFactoryImpl implements GameFactory{

    int maxPlayers;
    GameMap map;
    List<Player> players;

    public GameFactoryImpl(GameMap map) {
        this.map= map;
        maxPlayers = map.getMaxPlayers();
    }

    @Override
    public void addNewPlayer(Player player) {
        players.add(player);
    }

    @Override
    public Game initializeGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeGame'");

        //Comprendere anche l'assegnazione dei territori (e obbietivi?)
    }

    @Override
    public void setPlayersNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayersNumber'");
    }

    private void fillRemainingSlots() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillRemainingSlots'");
    }

    @Override
    public int getPlayersCount() {
        return players.size();
    }

}
