package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

/**
 * This interface contains some methods that could be applied to a Target
 * @author Keliane Tchoumkeu
 */
public interface Target {
    /**
     * This method helps to know the player to whom a target has been assigned
     * @return the player who has to achieve the target in order to win the game
     */
    Player getPlayer();

    /**
     * This method shows the number of remaining actions a players has to
     * do in other to complete his target
     * @return the number of specifics territories the player should conquer to win
     */
    int remainingActions();

    /**
     * This method shows a description about the remaining actions a players has to
     * do in other to complete his target
     * @return a string that informs on the number of specifics territories the player 
     * should conquer to win
     */
    String remainingActionsToString();

    /**
     * This method tells us if the goal of a specific player has been achieved
     * @return true if the objective is achieved, false if not
     */
    Boolean isAchieved();

    /**
     * This method shows the target's description 
     * @return a string that describe the objective to achieve
     */
    String targetDescription();
}
