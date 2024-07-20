package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

public abstract class PlaceArmiesActionHandler extends ActionHandler {
    protected static final Integer PLACEABLE_ARMIES_AT_A_TIME = 1;

    public boolean checkActionAndExceute(final Integer activePlayerIndex, List<Player> players, final String territory,
            Territories territories) {
        if (players.get(activePlayerIndex).getArmiesToPlace() > 0) {
            return doAction(activePlayerIndex, players, territory, territories);
        }
        return false;
    }

    abstract boolean doAction(final Integer activePlayerIndex, List<Player> players, String territory,
            Territories territories);

    protected void addArmies(Player player, String territory, Territories territories) {
        player.decrementArmiesToPlace();
        territories.addArmiesInTerritory(territory, PLACEABLE_ARMIES_AT_A_TIME);
    }
}
