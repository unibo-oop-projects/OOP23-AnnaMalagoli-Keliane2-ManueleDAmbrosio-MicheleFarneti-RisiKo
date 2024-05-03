package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public abstract class BaseTarget implements Target{
    private Player player;

    public BaseTarget(Player player) {
        this.player = player;
    }
    
    @Override
    public Player getPlayer(){
        return this.player;
    }
}
