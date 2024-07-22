package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

/**
 * An action handler specialization for armies placement actions, computing
 * effects for the players, the territories and alos updating currentplayer and
 * gamestatus.
 * 
 * @author Michele Farneti
 */
public abstract class PlaceArmiesActionHandler extends ActionHandlerImpl {
    /** Number of armies placeable at a time. */
    protected static final Integer PLACEABLE_ARMIES_AT_A_TIME = 1;

    /**
     * Checks if the player is allowed to carry out the placment action by
     * controlling it is owing the territory and his armies left to place and
     * delegates the computing of the updates to the abstract method.
     * 
     * @param activePlayerIndex The active player.
     * @param players           The players left in the game.
     * @param territory         The terrritory where the armies are going to be
     *                          placed.
     * @param territories       The territories of the game.
     * @return True if it wass possible to execute the action
     */
    public boolean checkActionAndExceute(final Integer activePlayerIndex, final List<Player> players,
            final String territory,
            final Territories territories) {
        if (players.get(activePlayerIndex).getArmiesToPlace() > 0) {
            return doAction(activePlayerIndex, players, territory, territories);
        }
        return false;
    }

    /**
     * Every specialization wil carry on the task to compute updates on the game
     * enviroment based on their nature.
     * 
     * @param activePlayerIndex The active player.
     * @param players           The players left in the game.
     * @param territory         The terrritory where the armies are going to be
     *                          placed.
     * @param territories       The territories of the game.
     * @return True if it wass possible to execute the action
     */
    abstract boolean doAction(Integer activePlayerIndex, List<Player> players, String territory,
            Territories territories);

    /**
     * Deloploys armies of agiven player on a given territory.
     * 
     * @param player      Player who is placing the armies
     * @param territory   name of the territory where the armies are getting placed.
     * @param territories The territories in the game.
     */
    protected void addArmies(final Player player, final String territory, final Territories territories) {
        player.decrementArmiesToPlace();
        territories.addArmiesInTerritory(territory, PLACEABLE_ARMIES_AT_A_TIME);
    }
}
