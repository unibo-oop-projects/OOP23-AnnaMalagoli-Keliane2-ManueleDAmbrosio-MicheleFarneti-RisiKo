package it.unibo.risiko.model.game;

/**
 * A base class created to be extended for creating action handlers for the
 * game.
 * Keeps tracks of a gamestatus and an index of the active player.
 * 
 * @author Michele Farneti.
 */
public class ActionHandlerImpl implements ActionHandler {
    private GameStatus gameStatus = GameStatus.TERRITORY_OCCUPATION;
    private Integer activePlayerIndex = 0;

    /**
     * Setter for the gameStatus.
     * 
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     * @param gameStatus
     */
    public void setGameStatus(final GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Setter for the player index.
     * 
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     * @param activePlayerIndex
     */
    public void setActivePlayerIndex(final Integer activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }

    /**
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     */
    @Override
    public Integer getActivePlayerIndex() {
        return activePlayerIndex;
    }

    /**
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     */
    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * @param activePlayer the index of the current player.
     * @param playersCount the number of players in the game.
     * @return The index of the player coming up next.
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     */
    protected Integer nextPlayer(final Integer activePlayer, final Integer playersCount) {
        return (activePlayer + 1) % playersCount;
    }
}
