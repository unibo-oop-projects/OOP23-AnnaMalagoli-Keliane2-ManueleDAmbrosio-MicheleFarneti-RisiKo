package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

public class PlacementPhaseActionHandler extends PlaceArmiesActionHandler {

    @Override
    boolean doAction(final Integer actionPlayerIndex, List<Player> players, final String territory,
            Territories territories) {
        this.addArmies(players.get(actionPlayerIndex), territory, territories);
        if (players.get(actionPlayerIndex).getArmiesToPlace() == 0) {
            this.gameStatus = GameStatus.READY_TO_ATTACK;
        }else{
            this.gameStatus = GameStatus.ARMIES_PLACEMENT;
        }
        this.activePlayerIndex = actionPlayerIndex;
        return true;
    }

}
