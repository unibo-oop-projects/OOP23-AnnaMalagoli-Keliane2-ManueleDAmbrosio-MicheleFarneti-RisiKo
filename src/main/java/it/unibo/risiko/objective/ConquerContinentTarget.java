package it.unibo.risiko.objective;

import it.unibo.risiko.Continent;
import it.unibo.risiko.Territory;
import it.unibo.risiko.player.Player;

public class ConquerContinentTarget extends BaseTarget {
    private Continent continent;
    
    public ConquerContinentTarget(Player player ,Continent continent) {
        super(player);
        this.continent=continent; 
    } 

    @Override
    public int remainingActions() {
        int i=0;
        for (Territory t : this.continent.getTerritories()) {
            if (!this.getPlayer().isOwnedTerritory(t)) {
                i++;
            }
        }
        return i;
    }
}
