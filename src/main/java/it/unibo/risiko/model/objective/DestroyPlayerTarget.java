package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

/**
 * DestroyPlayerTarget, a BaseTarget extension
 * @author Keliane Nana
 */
public class DestroyPlayerTarget extends BaseTarget {
    private final Player playerToDestroy;
    
    public DestroyPlayerTarget(final Player player,final Player playerToDestroy) {
        super(player);
        this.playerToDestroy=playerToDestroy.clone();
    }

    @Override
    public int remainingActions() {
        return Math.max(0,playerToDestroy.getNumberOfTerritores());
    }

    @Override
    public String remainingActionsToString() {
        return this.remainingActions()==0? 
        "Remainnig territories to conquer = 0. You won!":
        "You have to conquer "+this.remainingActions()+
        " territory(ies) of "+this.playerToDestroy.getColorID()+
        " to win the game";
    }

    @Override
    public String showTargetDescription() {
        return "Destroy the player "+this.playerToDestroy.getColorID()+" to win the game";
    }
}
