package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

public class OccupationPhaseActionHandler extends PlaceArmiesActionHandler {

    private static final int PLACEABLE_ARMIES_PER_OCCUPATION_TURN = 3;
    private int armiesPlaced = 0;

    @Override
    boolean doAction(final Integer actionPlayerIndex, List<Player> players, final String territory,
            Territories territories) {
        if (armiesPlaced < PLACEABLE_ARMIES_PER_OCCUPATION_TURN) {
            armiesPlaced++;
            this.addArmies(players.get(actionPlayerIndex), territory, territories);
            if (armiesPlaced == PLACEABLE_ARMIES_PER_OCCUPATION_TURN
                    || players.get(actionPlayerIndex).getArmiesToPlace() == 0) {
                if (getTotalArmiesLeftToPlace(players) <= 0) {
                    this.activePlayerIndex = nextPlayer(actionPlayerIndex, players.size());
                    players.get(activePlayerIndex).computeReinforcements(territories.getListContinents());
                    this.gameStatus = GameStatus.ARMIES_PLACEMENT;
                } else {
                    this.activePlayerIndex = nextPlayerWithArmies(actionPlayerIndex, players);
                }
                armiesPlaced = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Return the totale number of armies left to place within all the players in
     * the game.
     * 
     * @param players
     * @return
     */
    private Integer getTotalArmiesLeftToPlace(List<Player> players) {
        return players.stream().mapToInt(p -> p.getArmiesToPlace()).sum();
    }

    /**
     * Function used to get the index of the player coming up next after the active
     * player having at least one army.
     * 
     * @param playerIndex
     * @param players     The list of players in the game
     * @return Index of the next player with armies.
     */
    private Integer nextPlayerWithArmies(final Integer playerIndex, final List<Player> players) {
        if (players.get(nextPlayer(playerIndex, players.size())).getArmiesToPlace() != 0) {
            return nextPlayer(playerIndex, players.size());
        } else {
            return nextPlayerWithArmies(nextPlayer(playerIndex, players.size()), players);
        }
    }

}
