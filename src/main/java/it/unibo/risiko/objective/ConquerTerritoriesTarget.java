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
}
