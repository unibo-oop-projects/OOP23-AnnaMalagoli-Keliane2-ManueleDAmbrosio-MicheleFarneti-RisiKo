package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class ConquestTerritoryTarget implements Target{
    private Player player;
    private int territoryWantedNumber;

    public ConquestTerritoryTarget(Player player, int territoryWantedNumber) {
        this.player=player;
        this.territoryWantedNumber=territoryWantedNumber;
    }

    @Override
    public Boolean isAchieved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAchieved'");
    }
    
}
