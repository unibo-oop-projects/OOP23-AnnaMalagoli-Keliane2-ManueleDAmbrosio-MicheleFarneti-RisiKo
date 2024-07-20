package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

public interface GameLoopManager {

    Integer getActivePlayerIndex();
    GameStatus getGameStatus();
    void placeArmiesIfPossible(List<Player> players, String territory, Territories territories);
    void skipTurn(List<Player> players, Territories territories);
    Integer nextPlayer(Integer activePlayerIndex,Integer playersCount);
    Long getTurnsCount();
    Boolean skippedToAi();
    void setLoopPhase(GameStatus status);
    boolean isGameOver(List<Player> players, Territories territories);
}
