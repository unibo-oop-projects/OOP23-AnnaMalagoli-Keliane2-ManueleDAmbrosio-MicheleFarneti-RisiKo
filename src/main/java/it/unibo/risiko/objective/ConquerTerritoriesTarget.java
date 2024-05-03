package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class ConquerTerritoriesTarget extends BaseTarget{
    private int territoryWantedNumber;

    public ConquerTerritoriesTarget(Player player, int territoryWantedNumber) {
        super(player);
        this.territoryWantedNumber=territoryWantedNumber;
    }

    @Override
    public Boolean isAchieved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAchieved'");
    }
    
}
