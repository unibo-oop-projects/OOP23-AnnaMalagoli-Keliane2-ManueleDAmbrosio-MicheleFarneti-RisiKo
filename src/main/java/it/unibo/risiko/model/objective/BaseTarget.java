package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

public abstract class BaseTarget implements Target{
    private Player player;

    public BaseTarget(Player player) {
        this.player = player;
    }
    
    @Override
    public Player getPlayer(){
        return this.player;
    }

    @Override
    public Boolean isAchieved() {
        return this.remainingActions()==0;
    }
}
