package it.unibo.risiko.model.game;

import java.util.List;
import java.util.Random;
import java.util.Optional;

import it.unibo.risiko.model.map.Continent;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.player.Player;

/**
 * Implementation of the game interface
 * 
 * @author Michele Farneti
 */

public class GameLoopManagerImpl implements GameLoopManager {

    private static final Integer LAST_ARMY = 1;
    private static final double NEW_TARGET_PERCENTAGE = 0.7;
    private static final int PLACEABLE_ARMIES_PER_OCCUPATION_TURN = 3;
    private static final int MIN_CARDS_PLAYABLE = 3;

    private static final Random randomNumberGenerator = new Random();

    private int armiesPlaced = 0;
    private long turnsCount = 0;
    private GameStatus status;
    private Integer activePlayer = 0;
    private boolean skippedToAI = false;

    public GameLoopManagerImpl() {
        status = GameStatus.TERRITORY_OCCUPATION;
    }

    @Override
    public boolean skipTurn(Integer player, final List<Player> players, final Territories territories,
            final GameStatus gameStatus) {
        if (skipTurnPossible(players, territories, player, gameStatus)) {
            switch (status) {
                // Current player gets updated, if no player has armies left to place, the next
                // player is going to enter the classic game loop.
                case TERRITORY_OCCUPATION:
                    if (getTotalArmiesLeftToPlace(players) == LAST_ARMY) {
                        nextGamePhase(player, players, territories.getListContinents());
                    } else {
                        armiesPlaced = 0;
                    }
                    break;
                // Current player gets updated and the next player gets reinforcements
                case CARDS_MANAGING:
                case ATTACKING:
                case READY_TO_ATTACK:
                    nextGamePhase(player, players, territories.getListContinents());
                    break;
                default:
                    break;
            }
            if(!players.get(activePlayer).isAI() && players.get(nextPlayer(player, players.size())).isAI()){
                skippedToAI = true;
            }else{
                skippedToAI = false;
            }
            activePlayer = nextPlayer(player, players.size());
            return true;
        }
        return false;
    }

    /**
     * Private function used to manage the alternation of game phases in the game
     * loop by
     * updating the gameStatus following the gmae flow.
     */
    private void nextGamePhase(final Integer player, final List<Player> players, final List<Continent> continents) {
        switch (status) {
            // Once territory occupation phase is over begins armiesPlacement phase
            case TERRITORY_OCCUPATION:
                status = GameStatus.ARMIES_PLACEMENT;
                break;
            // If the player has armies to place status goes to armies placement, otherwise
            // directly to Ready to attack
            case CARDS_MANAGING:
                if (players.get(player).getArmiesToPlace() > 0) {
                    status = GameStatus.ARMIES_PLACEMENT;
                } else {
                    status = GameStatus.READY_TO_ATTACK;
                }
                break;
            // After armies placement the player can attack
            case ARMIES_PLACEMENT:
                status = GameStatus.READY_TO_ATTACK;
                break;
            // After attacking a new turn is going to begin, if the player has enough cards
            // to play them it will go CARDS MANAGING phase,
            // OtherWhise if he hasn't enough cards but enough armies to place the new game
            // status is going to armies Placement. If it can't
            // Do any of those actions it's going directly to the attack phase.
            case ATTACKING:
            case READY_TO_ATTACK:
                players.get(nextPlayer(player, players.size())).computeReinforcements(continents);
                if (players.get(nextPlayer(player, players.size())).getNumberOfCards() >= MIN_CARDS_PLAYABLE) {
                    status = GameStatus.CARDS_MANAGING;
                } else if (players.get(nextPlayer(player, players.size())).getArmiesToPlace() > 0) {
                    status = GameStatus.ARMIES_PLACEMENT;
                } else {
                    status = GameStatus.READY_TO_ATTACK;
                }
                break;
            default:
                break;
        }
    }

    /**
     * @return False if the player is not allowed to skip his turn, true otherwise.
     */
    private boolean skipTurnPossible(final List<Player> players, final Territories territories, final Integer player,
            final GameStatus gameStatus) {
        switch (status) {
            case TERRITORY_OCCUPATION:
                if (armiesPlaced == PLACEABLE_ARMIES_PER_OCCUPATION_TURN
                        || players.get(player).getArmiesToPlace() == LAST_ARMY) {
                    return true;
                }
                return false;
            case ARMIES_PLACEMENT:
                return players.get(player).getArmiesToPlace() == LAST_ARMY;
            case CARDS_MANAGING:
            case READY_TO_ATTACK:
            case ATTACKING:
                return true;
            default:
                return false;
        }
    }

    /**
     * @param players The list of players in the game
     * @return The totale amount of armies that are still left to be placed among
     *         all the players.
     */
    private int getTotalArmiesLeftToPlace(List<Player> players) {
        return players.stream().mapToInt(p -> p.getArmiesToPlace()).sum();
    }

    @Override
    public GameStatus getGameStatus() {
        return this.status;
    }

    @Override
    public Integer getActivePlayer() {
        return this.activePlayer;
    }

    @Override
    public Integer nextPlayer(Integer activePlayer, Integer playersCount) {
        return (activePlayer + 1) % playersCount;
    }

    @Override
    public boolean isGameOver(Integer playerIndex, final List<Player> players, final Territories territories) {
        Optional<Player> winner = players.stream().filter(p -> p.getTarget().isAchieved() == true).findAny();
        if (winner.isPresent()) {
            if (winner.get().equals(players.get(activePlayer))) {
                return true;
            } else {
                players.get(activePlayer)
                        .setTarget(new ConquerTerritoriesTarget(players.get(playerIndex), randomNumberGenerator
                                .nextInt(Math.toIntExact(
                                        Math.round(territories.getListTerritories().size() * NEW_TARGET_PERCENTAGE)))));
            }
        }
        return false;
    }

    @Override
    public Long getTurnsCount() {
        return turnsCount;
    }

    @Override
    public boolean placeArmiesIfPossibile(final Player player, final List<Player> players, final String territory,
            final GameStatus gameStatus, final Integer nArmies, final Territories territories) {
        if (player.getArmiesToPlace() > 0 && player.isOwnedTerritory(territory)) {
            switch (gameStatus) {
                case TERRITORY_OCCUPATION:
                    if (armiesPlaced < PLACEABLE_ARMIES_PER_OCCUPATION_TURN) {
                        armiesPlaced++;
                        if (armiesPlaced == PLACEABLE_ARMIES_PER_OCCUPATION_TURN
                                || player.getArmiesToPlace() == LAST_ARMY) {
                            skipTurn(players.indexOf(player), players, territories, gameStatus);
                        }
                        return true;
                    }
                    break;
                case ARMIES_PLACEMENT:
                    if (player.getArmiesToPlace() == LAST_ARMY) {
                        this.status = GameStatus.READY_TO_ATTACK;
                        this.skippedToAI = false;
                    }
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean skippedToAI() {
        if(skippedToAI){
            skippedToAI = false;
            return true;
        }
        return false;
    }

}
