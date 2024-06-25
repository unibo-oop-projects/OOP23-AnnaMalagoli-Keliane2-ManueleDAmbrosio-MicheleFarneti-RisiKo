package it.unibo.risiko.game;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibo.risiko.Territory;
import it.unibo.risiko.map.GameMap;
import it.unibo.risiko.objective.ConquerContinentTarget;
import it.unibo.risiko.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.objective.DestroyPlayerTarget;
import it.unibo.risiko.objective.Target;
import it.unibo.risiko.objective.TargetType;
import it.unibo.risiko.player.Player;

/**
 * Implementation of the game interface
 * 
 * @author Michele Farneti
 */

public class GameImpl implements Game {

    private static final double MIN_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.6;
    private static final double MAX_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.8;
    private static final int PLACEABLE_ARMIES_PER_TURN = 3;

 
    private GameMap map;
    private int activePlayer = 0;
    private int armiesPlaced = 0 ;
    private final List<Player> players = new LinkedList<Player>();
    private static final Random randomNumberGenerator = new Random();
    GameStatus status = GameStatus.TERRITORY_OCCUPATION;
   
    @Override
    public void startGame(){
        Collections.shuffle(players);
        /*Attribuzione armate */
        players.forEach( p -> p.setArmiesToPlace(map.getStratingArmies(players.size())));
        /*Attribuzione territori */
        assignTerritories();
        /*Assegnamento degli obiettivi*/
        assignTargets();
    }

    /**
     * Assigns a target to every player
     */
    private void assignTargets() {
        players.stream().forEach(p -> p.setTarget(generateRandomTarget(p)));  
    }

    /**
     * Gennerates a random target for a given player
     * @param player The player who is getting the target 
     * @return The random target 
     */
    private Target  generateRandomTarget(Player player){
        switch(TargetType.randomTargetType()){
            case PLAYER:
                return new DestroyPlayerTarget(player, players.get((players.indexOf(player) + randomNumberGenerator.nextInt(1,players.size()))%players.size()));
            case TERRITORY:
                return new ConquerTerritoriesTarget(player,randomNumberGenerator.nextInt(Math.toIntExact(Math.round(map.getTerritories().size()*MIN_TERRITORIES_TO_CONQUER_PERCENTAGE)),Math.toIntExact(Math.round(map.getTerritories().size()*MAX_TERRITORIES_TO_CONQUER_PERCENTAGE))));
            case CONTINENT:
                return new ConquerContinentTarget(player, map.getContinents().get(randomNumberGenerator.nextInt(map.getContinents().size())));
            default:
                return new ConquerTerritoriesTarget(player, map.getTerritories().size());
        }
    }

    /**
     * Private function used to split the map territories between the players in the game 
     */
    private void assignTerritories() {
        var territoriesToAssign = map.getTerritories();
        Collections.shuffle(territoriesToAssign);

        while (territoriesToAssign.size()>0){
            players.get(activePlayer).addTerritory(territoriesToAssign.remove(0));
            nextTurn();
        }
        activePlayer = 0;
    }

    @Override
    public boolean nextTurn(){
        if(skipTurnPossible()){
            activePlayer = (activePlayer+1)%players.size();
            if(status == GameStatus.TERRITORY_OCCUPATION){
                armiesPlaced = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * @return False if the player is not allowed to skip his turn, true otherwise.
     */
    private boolean skipTurnPossible(){
        if(status == GameStatus.TERRITORY_OCCUPATION){
            return(armiesPlaced == 3 || players.get(activePlayer).getArmiesToPlace() == 0);
        }
        return players.get(activePlayer).getArmiesToPlace() == 0; 
    }

    public boolean placeArmies(final Territory territory){
        if(status == GameStatus.TERRITORY_OCCUPATION){
            if(armiesPlaced < 3){
                if(players.get(activePlayer).isOwnedTerritory(territory)){
                    map.setArmies(territory,1);
                    armiesPlaced ++;
                }
            }
        }
    }

    @Override
    public List<Player> getPlayersList() {
        return List.copyOf(players);
    }
}
