package it.unibo.risiko.game;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.unibo.risiko.map.GameMap;
import it.unibo.risiko.player.Player;


public class GameImpl implements Game {

    private GameMap map;
    private int activePlayer = 0;
    private final List<Player> players = new LinkedList<Player>();
   
    public void startGame(){
        Collections.shuffle(players);
        /*Attribuzione armate */
        players.forEach( p -> p.setArmiesToPlace(map.getStratingArmies(players.size())));
        /*Attribuzione territori */
        AssignTerritories();
    }

    private void AssignTerritories() {
        var territoriesToAssign = map.getTerritories();
        Collections.shuffle(territoriesToAssign);

        while (territoriesToAssign.size()>0) {
            players.get(activePlayer).addTerritory(territoriesToAssign.remove(0));
            NextTurn();
        }
        activePlayer = 0;
    }

    public boolean NextTurn(){
        if(skipTurnPossible()){
            activePlayer = (activePlayer+1)%players.size();
            return true;
        }
        return false;
    }

    private boolean skipTurnPossible(){
        return false;
        
    }

    @Override
    public List<Player> getPlayersList() {
        return List.copyOf(players);
    }
}
