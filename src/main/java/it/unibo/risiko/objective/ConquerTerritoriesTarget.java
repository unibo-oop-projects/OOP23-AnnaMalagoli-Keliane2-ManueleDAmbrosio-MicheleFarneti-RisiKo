package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class ConquerTerritoriesTarget extends BaseTarget{
    private int territoryWantedNumber;

    public ConquerTerritoriesTarget(Player player, int territoryWantedNumber) {
        super(player);
        this.territoryWantedNumber=territoryWantedNumber;
    }

    @Override
    public int remainingActions() {
        return Math.max(0,this.territoryWantedNumber-this.getPlayer().getNumberOfTerritories());
    }

    @Override
    public String remainingActionsToString() {
        return this.remainingActions()==0? 
        "Remainnig territories to conquer = 0. You won!":
        "You have to conquer "+this.remainingActions()+
        " territory(ies) to win the game";
    }

    @Override
    public String targetDescription() {
        return "Conquer "+this.territoryWantedNumber+" territories to win the game";
    }
}
