package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

/**
 * GameLoopManager interface rappresents a class keeping track of the status of
 * the game, calculating and updating it in relation to the players actions. It
 * will be possibile to get infos about the updates in the game status from
 * getters.
 * 
 * @author Michele Farneti
 */
public interface GameLoopManager {

    /**
     * Index of the active player of the game, who is the player who is going to
     * have the opportunity to make actions.
     * 
     * @return An index for the game's player list.
     */
    Integer getActivePlayerIndex();

    /**
     * 
     * @return Status of the game saved in the GameLoopManager.
     */
    GameStatus getGameStatus();

    /**
     * If the status of the game allows it (risiko rules), the gameloopmanager will
     * compute the placement of the armies.
     * 
     * @param players     The players left in the game.
     * @param territory   The territory the activePlayer wants to place the armies
     *                    in.
     * @param territories The territories of the map.
     */
    void placeArmiesIfPossible(List<Player> players, String territory, Territories territories);

    /**
     * The turn is skipped by updating the index of the active player and the
     * gamestatus for the next player.
     * 
     * @param players     The players left in the game.
     * @param territories The territories of the map.
     */
    void skipTurn(List<Player> players, Territories territories);

    /**
     * Calculates the index of the next player.
     * 
     * @param activePlayerIndex The index of the current player.
     * @param playersCount      The number of players present in the game.
     * @return An index for the players list,
     */
    Integer nextPlayer(Integer activePlayerIndex, Integer playersCount);

    /**
     * Getter for a turn count wich gets updated every time the game loop goes back
     * to the first player.
     * 
     * @return An integer rappresenting the turns count
     */
    Integer getTurnsCount();

    /**
     * 
     * @return True if int the last game Action the activplayer went from a standard
     *         player to an ai player.
     */
    Boolean skippedToAi();

    /**
     * Manually sets the gameStatus of a gameloop manager. This operation is used
     * for game stages that are out of the game loop and can only be triggered by
     * specific actions such as the attack or the cads managing.
     * 
     * @param status The game status the gameloopmanager it's going to be set with.
     */
    void setLoopPhase(GameStatus status);

    /**
     * Chechk if in the players list there is someone who reached his target. In the
     * particular case where a player has to destroy someone whose last nation is
     * conquired by somebody else, a new target gets generated for him.
     * 
     * @param players     The players left in the game.
     * @param territories The territories of the map.
     * @return True if a player has reached is target,
     */
    boolean isGameOver(List<Player> players, Territories territories);
}
