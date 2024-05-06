package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class DestroyPlayerTarget extends BaseTarget {
    private Player playerToDestroy;
    
    public DestroyPlayerTarget(Player player, Player playerToDestroy) {
        super(player);
        this.playerToDestroy=playerToDestroy;
    }

    @Override
    public int remainingActions() {
        return Math.max(0,playerToDestroy.getNumberOfTerritories());
    }
}
