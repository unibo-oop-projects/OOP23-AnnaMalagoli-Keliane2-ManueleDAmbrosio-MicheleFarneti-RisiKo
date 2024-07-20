package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.player.Player;

public class GameLoopManagerImpl2 implements GameLoopManager2{
    private GameStatus gameStatus;
    private Integer activePlayerIndex;

    public GameLoopManagerImpl2(){
        this.gameStatus = GameStatus.TERRITORY_OCCUPATION;
        this.activePlayerIndex = 0;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public Integer getActivePlayer() {
        return activePlayerIndex;
    }

    @Override
    public boolean doActionIfPossible(List<Player> players, GameActionType gameAction) {
        switch(gameAction):
    }
}
