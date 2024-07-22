package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

/**
 * A specific actionHandler which is going to handle the placement of armies in
 * the armies placement phase, computing the next gamestatuts the player should
 * go to.
 * 
 * @author Michele Farneti
 */
public final class PlacementPhaseActionHandler extends PlaceArmiesActionHandler {

    @Override
    protected boolean updateStatus(final Integer actionPlayerIndex, final List<Player> players, final String territory,
            final Territories territories) {
        this.addArmies(players.get(actionPlayerIndex), territory, territories);
        if (players.get(actionPlayerIndex).getArmiesToPlace() == 0) {
            this.setGameStatus(GameStatus.READY_TO_ATTACK);
        } else {
            this.setGameStatus(GameStatus.ARMIES_PLACEMENT);
        }
        this.setActivePlayerIndex(actionPlayerIndex);
        return true;
    }

}
