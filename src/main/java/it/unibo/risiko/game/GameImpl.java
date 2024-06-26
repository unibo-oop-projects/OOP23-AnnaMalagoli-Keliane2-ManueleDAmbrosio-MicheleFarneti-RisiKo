package it.unibo.risiko.game;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibo.risiko.DeckImpl;
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
            if(status == GameStatus.TERRITORY_OCCUPATION){

                armiesPlaced = 0;
                if(getTotalArmiesLeftToPlace() == 0){
                    status = status.next();
                    players.get(nextPlayer()).computeReinforcements();
                }
                updateCurrentPlayer();

            } else if(status == GameStatus.ARMIES_PLACEMENT){
                status = status.next();
            } else if (status == GameStatus.ATTACK){
                status = status.next();
                players.get(nextPlayer()).computeReinforcements();
                updateCurrentPlayer();
            }
            return true;
        }
        return false;
    }

    /**
     * @return False if the player is not allowed to skip his turn, true otherwise.
     */
    private boolean skipTurnPossible(){
        /* Territory occupation stage, It's only possibile to skip the turn if the player has no armies 
         * left to place or if he already placed 3 of them. */
        if(status == GameStatus.TERRITORY_OCCUPATION){
            return(armiesPlaced == PLACEABLE_ARMIES_PER_TURN || players.get(activePlayer).getArmiesToPlace() == 0);
        } if (status == GameStatus.ARMIES_PLACEMENT){
            return players.get(activePlayer).getArmiesToPlace()==0;
        } else if (status == GameStatus.ATTACK){
            return true;
        }
        /* */
        return players.get(activePlayer).getArmiesToPlace() == 0; 
    }

    /**
     * 
     * @return The totale amount of armies that are still left to be placed among all the players.
     */
    private int getTotalArmiesLeftToPlace(){
        return players.stream().mapToInt(p -> p.getArmiesToPlace()).sum();
    }

    public void placeArmies(final Territory territory, final int nArmies){
        if(status == GameStatus.TERRITORY_OCCUPATION){
            if(armiesPlaced < 3){
                if(players.get(activePlayer).isOwnedTerritory(territory)){
                    territory.addArmies(nArmies);
                    armiesPlaced ++;
                }
            }
        }
    }

    @Override
    public List<Player> getPlayersList() {
        return List.copyOf(players);
    }

    @Override
    public GameStatus getGameStatus() {
        return this.status;
    }

    /**
     * @return The index of the next active player
     */
    private int nextPlayer(){
        return (activePlayer+1)%players.size();
    }

    private void updateCurrentPlayer(){
        activePlayer = nextPlayer();
    }

}
