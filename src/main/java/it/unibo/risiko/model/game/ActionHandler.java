package it.unibo.risiko.model.game;

/**
 * The interface actionHnadler rappresents all of the classes operating in the
 * game loop and updating thier internal fields gamestatus and activeplayer
 * index after every action in order to keep going the game loop.
 * 
 * @author Michele Farneti
 */
public interface ActionHandler {

    /**
     * 
     * @return The index of the active player saved in the actionHandler.
     * @implSpec Sublcasses should be careful voerriding in order to
     *           not mess up new calls for methods of this class.
     */
    Integer getActivePlayerIndex();

    /**
     * 
     * @return The game status saved in the action handler.
     * @implSpec Sublcasses should be careful voerriding in order to
     *           not mess up new calls for methods of this class.
     */
    GameStatus getGameStatus();

    /**
     * Setter for the gameStatus.
     * 
     * @implSpec Sublcasses should be careful overriding in order to
     *           not mess up new calls for methods of this class.
     * @param gameStatus
     */
    void setGameStatus(final GameStatus gameStatus);

}
