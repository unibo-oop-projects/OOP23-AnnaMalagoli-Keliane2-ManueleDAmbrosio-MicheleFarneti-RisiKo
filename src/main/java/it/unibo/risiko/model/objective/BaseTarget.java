package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

/**
 * An abstract class implementing Target
 * @author Keliane Nana
 */
public abstract class BaseTarget implements Target{
    private final Player player;

    public BaseTarget(final Player player) {
        this.player = player.clone();
    }
    
    @Override
    public Player getPlayer(){
        return this.player.clone();
    }

    @Override
    public Boolean isAchieved() {
        return this.remainingActions()==0;
    }
}
