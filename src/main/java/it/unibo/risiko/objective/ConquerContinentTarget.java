package it.unibo.risiko.objective;

import it.unibo.risiko.player.Player;

public class ConquerContinentTarget implements Target {
    private Player player;
    //private Continent continent;
    
    public ConquerContinentTarget(Player player /*,Continent continent*/) {
        this.player = player;
        /*this.continent=continent; */
    } 
    
    @Override
    public Boolean isAchieved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAchieved'");
    }
}
