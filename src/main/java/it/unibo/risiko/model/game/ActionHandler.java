package it.unibo.risiko.model.game;

public abstract class ActionHandler {
    protected GameStatus gameStatus = GameStatus.TERRITORY_OCCUPATION;
    protected Integer activePlayerIndex = 0;

    public Integer getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    protected Integer nextPlayer(final Integer activePlayer, final Integer playersCount) {
        return (activePlayer + 1) % playersCount;
    }
}
