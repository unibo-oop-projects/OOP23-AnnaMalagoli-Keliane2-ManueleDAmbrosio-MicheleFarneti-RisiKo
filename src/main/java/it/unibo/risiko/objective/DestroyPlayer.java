package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class DestroyPlayer extends BaseTarget {
    private Player playerToDestroy;
    
    public DestroyPlayer(Player player, Player playerToDestroy) {
        super(player);
        this.playerToDestroy=playerToDestroy;
    }


    @Override
    public Boolean isAchieved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAchieved'");
    }
    
}
