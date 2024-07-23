package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.ReadOnlyContinent;
import it.unibo.risiko.model.map.ReadOnlyContinentImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * ConquerContinentTarget, a BaseTarget extension.
 * 
 * @author Keliane Nana
 */
public class ConquerContinentTarget extends BaseTarget {
    private final ReadOnlyContinent continent;

    /**
     * Constructor to set the field of the target.
     * 
     * @param player    the player having this target
     * @param continent the continent to conquer
     */
    public ConquerContinentTarget(final Player player, final Continent continent) {
        super(player);
        this.continent = new ReadOnlyContinentImpl(continent);
    }

    /**
     * This method shows the number of remaining actions a players has to do in
     * other to complete his target.
     * 
     * @return the number of specifics territories the player should conquer to win
     */
    @Override
    public int remainingActions() {
        int i = 0;
        for (final Territory t : this.continent.getListTerritories()) {
            if (!this.getPlayer().isOwnedTerritory(t.getTerritoryName())) {
                i++;
            }
        }
        return i;
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
                        + " territory(ies) of "
                        + this.continent.getName()
                        + " to win the game";
    }

    /**
     * This method shows the target's description.
     * 
     * @return a string that describe the objective to achieve
     */
    @Override
    public String showTargetDescription() {
        return "Conquer all the "
                + this.continent.getName()
                + " to win the game";
    }
}
