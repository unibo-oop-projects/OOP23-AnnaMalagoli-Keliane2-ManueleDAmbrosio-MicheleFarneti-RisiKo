package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class DestroyPlayer implements Target {
    private Player player;
    private Player playerToDestroy;
    
    public DestroyPlayer(Player player, Player playerToDestroy) {
        this.player = player;
        this.playerToDestroy=playerToDestroy;
    }


    @Override
    public Boolean isAchieved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAchieved'");
    }
    
}
