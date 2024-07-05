package it.unibo.risiko.model.objective;

import it.unibo.risiko.model.player.Player;

public class DestroyPlayerTarget extends BaseTarget {
    private Player playerToDestroy;
    
    public DestroyPlayerTarget(Player player, Player playerToDestroy) {
        super(player);
        this.playerToDestroy=playerToDestroy;
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
        " territory(ies) of "+this.playerToDestroy.getColor_id()+
        " to win the game";
    }

    @Override
    public String showTargetDescription() {
        return "Destroy the player "+this.playerToDestroy.getColor_id()+" to win the game";
    }
}
