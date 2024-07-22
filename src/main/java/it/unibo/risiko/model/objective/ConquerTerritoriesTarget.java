package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

/**
 * ConquerTerritoriesTarget, a BaseTarget extension.
 * 
 * @author Keliane Nana
 */
public class ConquerTerritoriesTarget extends BaseTarget {
    private final int territoryWantedNumber;

    /**
     * Constructor to set the field of the target.
     * 
     * @param player                the player having this target
     * @param territoryWantedNumber the number of territoties to conquer
     */
    public ConquerTerritoriesTarget(final Player player, final int territoryWantedNumber) {
        super(player);
        this.territoryWantedNumber = territoryWantedNumber;
    }

    /**
     * This method shows the number of remaining actions a players has to do in
     * other to complete his target.
     * 
     * @return the number of specifics territories the player should conquer to win
     */
    @Override
    public int remainingActions() {
        return Math.max(0, this.territoryWantedNumber - this.getPlayer().getNumberOfTerritores());
    }

    /**
     * This method shows a description about the remaining actions a players has to
     * do in other to complete his target.
     * 
     * @return a string that informs on the number of specifics territories the
     *         player
     *         should conquer to win
     */
    @Override
    public String remainingActionsToString() {
        return this.remainingActions() == 0 ? "Remainnig territories to conquer = 0. You won!"
                : "You have to conquer "
                        + this.remainingActions()
                        + " territory(ies) to win the game";
    }

    /**
     * This method shows the target's description.
     * 
     * @return a string that describe the objective to achieve
     */
    @Override
    public String showTargetDescription() {
        return "Conquer "
                + this.territoryWantedNumber
                + " territories to win the game";
    }
}
