package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class DestroyPlayerTarget extends BaseTarget {
    private Player playerToDestroy;
    
    public DestroyPlayerTarget(Player player, Player playerToDestroy) {
        super(player);
        this.playerToDestroy=playerToDestroy;
    }


    @Override
    public Boolean isAchieved() {
        return this.playerToDestroy.isDefeated();
    }
    
}
