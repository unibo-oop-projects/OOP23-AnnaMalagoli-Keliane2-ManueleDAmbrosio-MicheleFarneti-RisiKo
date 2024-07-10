package it.unibo.risiko.model.game;

import java.util.List;
import java.util.Optional;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * This interface manages all of the basic functions that are offered during the actual risiko games!
 * It is going to manage all of the players actions and the game loop itself.
 * 
 * @author Michele Farneti
 */

public interface Game {

    /**
     * @return Allows the game to go on by updating the active players, but only after checking if he made all the actions needed to skip the turn.
     */
    boolean nextTurn();

    /**
     * @return the name of the Map being played.
     */
    String getMapName();

    /**
     * Initializises the game following the basic Risiko rules, Every player
     * gets a randomly generated target, the set of owned territories and the right amount of armies.
     */
    void startGame();

    /**
     * @return The List of the players currently playing the game.
     */
    List<Player> getPlayersList();

    /**
     * @return The List of the territories in the map
     */
    List<Territory> getTerritoriesList();

    /**
     * @return The current Stage of the game
     */
    GameStatus getGameStatus();

    /**
     * 
     * @return True if one of the players has reached is target
     */
    boolean gameOver();

    /**
     * 
     * @return The player whose turn it is
     */
    Player getCurrentPlayer();

    /**
     * The amount of armies selected is added to the player's territory if the current player 
     * owns it
     * @param territory
     * @param nArmies
     */
    public void placeArmies(final Territory territory, final int nArmies);

    /**
     * 
     * @param territory
     * @return The owner of the given territory
     */
    public Player getOwner(final Territory territory);

    /**
     * 
     * If possible, sets the Game status to ATTACKING, allowing the player to attack next
     */
    void setAttacking();

    /**
     * 
     * Ends the attacking phase by setting the gameStatus back to READY_TO_ATTACK allowing the gameLoop to restart
     */
    void endAttack();

}
