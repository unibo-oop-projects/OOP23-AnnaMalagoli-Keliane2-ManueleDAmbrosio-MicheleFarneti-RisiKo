package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

/**
 * An abstract class implementing Target.
 * 
 * @author Keliane Nana
 */
public abstract class BaseTarget implements Target {
    private final Player player;

    /**
     * Constructor that set the field Player with the refering player.
     * 
     * @param player the player whose terget is the current target
     */
    public BaseTarget(final Player player) {
        this.player = player.clonePlayer();
    }

    /**
     * This method helps to know the player to whom a target has been assigned.
     * 
     * @return the player who has to achieve the target in order to win the game
     */
    @Override
    public Player getPlayer() {
        return this.player.clonePlayer();
    }

    /**
     * This method tells us if the goal of a specific player has been achieved.
     * 
     * @return true if the objective is achieved, false if not
     */
    @Override
    public Boolean isAchieved() {
        return this.remainingActions() == 0;
    }
}
