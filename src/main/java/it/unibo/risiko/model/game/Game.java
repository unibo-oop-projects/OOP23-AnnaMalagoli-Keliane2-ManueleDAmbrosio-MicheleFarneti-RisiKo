package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * This interface manages all of the basic functions that are offered during the
 * actual risiko games!
 * It is going to manage all of the players actions and the game loop itself.
 * 
 * @author Michele Farneti
 */

public interface Game {

    /**
     * Allows the game to go on by updating the active players, but only after
     * checking if he made all the actions needed to skip the turn.
     * 
     * @return True if the turn was succesfuly skipped
     */
    boolean skipTurn();

    /**
     * @return the name of the Map being played.
     */
    String getMapName();

    /**
     * Initializises the game following the basic Risiko rules, Every player
     * gets a randomly generated target, the set of owned territories and the right
     * amount of armies, it also handles the case where.
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
     * The amount of armies selected is added to the player's territory if the
     * current player
     * owns it
     * 
     * @param territory
     * @param nArmies
     * @return True if it was possbile to place the armies, false otherwise.
     */
    public boolean placeArmies(final String territory, final int nArmies);

    /**
     * 
     * @param territory
     * @return The owner of the given territory
     */
    public String getOwner(final String territory);

    /**
     * 
     * If possible, sets the Game status to ATTACKING, allowing the player to attack
     * next
     */
    void setAttacking();

    /**
     * 
     * Ends the attacking phase by setting the gameStatus back to READY_TO_ATTACK
     * allowing the gameLoop to restart
     */
    void endAttack();

    /**
     * 
     * @return A card from the game's deck
     */
    Card pullCard();

    /**
     * 
     * @return A card from the game's deck
     */
    void playCards(String card1, String card2, String card3, final Player currentPlayer);

    /**
     * 
     * @param territory1
     * @param territory2
     * @return True if the two territories of the game's map are near, false
     *         otherwise.
     */
    boolean areTerritoriesNear(String territory1, String territory2);

    /**
     * If the game is in CARD_MANAGING phase, makes it go to the next phase.
     */
    void endCardsPhase();

    /**
     * 
     * @return a number indicating how many times the game loop has reached the
     *         first player
     */
    Long getTurnsCount();

    int getNumberOfArmies(String territory);

    void removeArmies(String Territory, int numberOfMovingArmies);

    void setOwner(String Territory, String color_id);

}
