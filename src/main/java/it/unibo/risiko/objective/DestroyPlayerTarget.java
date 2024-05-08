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

    @Override
    public String RemainingActionsToString() {
        return this.remainingActions()==0? 
        "Remainnig territories to conquer = 0. You won!":
        "You have to conquer "+this.remainingActions()+
        " territory(ies) of "+this.playerToDestroy.getColor_id()+
        " to win the game";
    }

    @Override
    public String targetDescription() {
        return "Destroy the player "+this.playerToDestroy.getColor_id()+" to win the game";
    }
}
