package it.unibo.risiko.model.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.GameMapInitializer;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.objective.ConquerContinentTarget;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.objective.DestroyPlayerTarget;
import it.unibo.risiko.model.objective.Target;
import it.unibo.risiko.model.objective.TargetType;
import it.unibo.risiko.model.player.AIBehaviourImpl;
import it.unibo.risiko.model.player.Player;

/**
 * Implementation of the game interface
 * 
 * @author Michele Farneti
 */

public class GameImpl implements Game {

    private static final double MIN_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.6;
    private static final double MAX_TERRITORIES_TO_CONQUER_PERCENTAGE = 0.8;
    private static final int PLACEABLE_ARMIES_PER_TURN = 3;
    private static final int MIN_CARDS_PLAYABLE = 3;
    private static final Random randomNumberGenerator = new Random();

    private final GameMapInitializer map;

    private int activePlayer = 0;
    private int armiesPlaced = 0;
    private long turnsCount = 1;
    private List<Player> players = new LinkedList<Player>();
    private GameStatus status = GameStatus.TERRITORY_OCCUPATION;
    private Deck deck;

    protected GameImpl(final GameMapInitializer map, final List<Player> players) {
        this.map = map;
        this.players.addAll(players);
        this.deck = new DeckImpl(map.getDeckPath());
    }

    @Override
    public void startGame() {
        Collections.shuffle(players);
        players.forEach(p -> p.setArmiesToPlace(map.getStratingArmies(players.size())));
        assignTargets();
        assignTerritories();
        activePlayer = 0;
        handleAIBehaviour();
    }

    /**
     * Assigns a target to every player
     */
    private void assignTargets() {
        players.stream().forEach(p -> p.setTarget(generateRandomTarget(p)));
    }

    /**
     * Gennerates a random target for a given player
     * 
     * @param player The player who is getting the target
     * @return The random target
     */
    private Target generateRandomTarget(Player player) {
        switch (TargetType.randomTargetType()) {
            case PLAYER:
                return new DestroyPlayerTarget(player, players.get(
                        (players.indexOf(player) + randomNumberGenerator.nextInt(1, players.size())) % players.size()));
            case TERRITORY:
                return new ConquerTerritoriesTarget(player, randomNumberGenerator.nextInt(
                        Math.toIntExact(
                                Math.round(map.getTerritories().size() * MIN_TERRITORIES_TO_CONQUER_PERCENTAGE)),
                        Math.toIntExact(
                                Math.round(map.getTerritories().size() * MAX_TERRITORIES_TO_CONQUER_PERCENTAGE))));
            case CONTINENT:
                return new ConquerContinentTarget(player,
                        map.getContinents().get(randomNumberGenerator.nextInt(map.getContinents().size())));
            default:
                return new ConquerTerritoriesTarget(player, map.getTerritories().size());
        }
    }

    /**
     * Private function used to split the map territories between the players in the
     * game, it also
     * places one army per territory for each player
     */
    private void assignTerritories() {
        var territoriesToAssign = map.getTerritories();

        for (Territory territory : territoriesToAssign) {
            players.get(activePlayer).addTerritory(territory.getTerritoryName());
            territory.addArmies(1);
            players.get(activePlayer).decrementArmiesToPlace();
            activePlayer = nextPlayer();
        }
    }

    @Override
    public boolean skipTurn() {
        if (skipTurnPossible()) {
            switch (status) {
                // Current player gets updated, if no player has armies left to place, the next
                // player is going to enter the classic game loop.
                case TERRITORY_OCCUPATION:
                    if (getTotalArmiesLeftToPlace() == 0) {
                        players.get(nextPlayerIfNotDefeated()).computeReinforcements(map.getContinents());
                        nextGamePhase();
                    } else {
                        armiesPlaced = 0;
                    }
                    break;
                // Current player gets updated and the next player gets reinforcements
                case CARDS_MANAGING:
                case ATTACKING:
                case READY_TO_ATTACK:
                    players.get(nextPlayerIfNotDefeated()).computeReinforcements(map.getContinents());
                    nextGamePhase();
                break;
                default:
                    break;
            }
            updateCurrentPlayer();
            return true;
        }
        return false;
    }

    /**
     * Private function used to manage the alternation of game phases in the game
     * loop by
     * updating the gameStatus following the gmae flow.
     */
    private void nextGamePhase() {
        switch (status) {
            // Once territory occupation phase is over begins armiesPlacement phase
            case TERRITORY_OCCUPATION:
                status = GameStatus.ARMIES_PLACEMENT;
                break;
            // If the player has armies to place status goes to armies placement, otherwise
            // directly to Ready to attack
            case CARDS_MANAGING:
                if (players.get(activePlayer).getArmiesToPlace() > 0) {
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
                if (players.get(nextPlayerIfNotDefeated()).getNumberOfCards() >= MIN_CARDS_PLAYABLE) {
                    status = GameStatus.CARDS_MANAGING;
                } else if (players.get(nextPlayerIfNotDefeated()).getArmiesToPlace() > 0) {
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
    private boolean skipTurnPossible() {
        switch (status) {
            case TERRITORY_OCCUPATION:
                if (armiesPlaced == PLACEABLE_ARMIES_PER_TURN || getCurrentPlayer().getArmiesToPlace() == 0) {
                    return true;
                }
                return false;
            case ARMIES_PLACEMENT:
                return players.get(activePlayer).getArmiesToPlace() == 0;
            case CARDS_MANAGING:
            case READY_TO_ATTACK:
            case ATTACKING:
                return true;
            default:
                return false;
        }
    }

    /**
     * @return The totale amount of armies that are still left to be placed among
     *         all the players.
     */
    private int getTotalArmiesLeftToPlace() {
        return players.stream().mapToInt(p -> p.getArmiesToPlace()).sum();
    }

    @Override
    public boolean placeArmies(final String territory, final int nArmies) {
        if (getCurrentPlayer().getArmiesToPlace() > 0) {
            switch (status) {
                case TERRITORY_OCCUPATION:
                    if (armiesPlaced < PLACEABLE_ARMIES_PER_TURN
                            && getCurrentPlayer().isOwnedTerritory(territory)) {
                        map.addArmies(territory,nArmies);
                        armiesPlaced++;
                        getCurrentPlayer().decrementArmiesToPlace();
                        if (armiesPlaced == PLACEABLE_ARMIES_PER_TURN
                                || getCurrentPlayer().getArmiesToPlace() == 0) {
                            skipTurn();
                        }
                        return true;
                    }
                    break;
                case ARMIES_PLACEMENT:
                    if (getCurrentPlayer().isOwnedTerritory(territory)) {
                        map.addArmies(territory,nArmies);
                        getCurrentPlayer().decrementArmiesToPlace();
                        if (getCurrentPlayer().getArmiesToPlace() == 0) {
                            nextGamePhase();
                        }
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
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
     * @return The index of the next active player, avoiding all of the elliminated
     *         players.
     */
    private int nextPlayerIfNotDefeated() {
        if (!(players.get((activePlayer + 1) % players.size()).isDefeated())) {
            return nextPlayer();
        } else {
            activePlayer = nextPlayer();
            return nextPlayer();
        }

    }

    /**
     * 
     * @return The index of the next player, independently if it is Defeated or not
     */
    private int nextPlayer() {
        return (activePlayer + 1) % players.size();
    }

    /**
     * Sets as new activePlayer the next player in line, eventually handles the AI
     * moves if the next player is AI
     */
    private void updateCurrentPlayer() {
        activePlayer = nextPlayerIfNotDefeated();
        if (activePlayer == 0) {
            turnsCount++;
        }
        handleAIBehaviour();
    }

    /**
     * 
     * Based on the game status, handles AI's behavior
     * 
     */
    private void handleAIBehaviour() {
        // if (getCurrentPlayer().isAI()) {
        //     var aiBehaviour = new AIBehaviourImpl(getCurrentPlayer());
        //     switch (status) {
        //         case TERRITORY_OCCUPATION:
        //             while(getCurrentPlayer().isAI()){
        //                 this.placeArmies(aiBehaviour.decidePositioning(), 1);
        //             }
        //             break;
        //         case CARDS_MANAGING:
        //             var cardCombo = aiBehaviour.checkCardCombo();
        //             //this.getDeck().playCards(cardCombo.get(0), cardCombo.get(1), cardCombo.get(2), getCurrentPlayer());
        //         case ARMIES_PLACEMENT:
        //             while (this.placeArmies(aiBehaviour.decidePositioning(), 1))
        //                 ;
        //             if (aiBehaviour.decideAttack(getTerritoriesList())) {
        //                 AttackPhase attackPhase = new AttackPhaseImpl(
        //                         getCurrentPlayer(),
        //                         aiBehaviour.getNextAttackingTerritory(),
        //                         aiBehaviour.decideAttackingArmies(),
        //                         getOwner(aiBehaviour.getNextAttackedTerritory()),
        //                         aiBehaviour.getNextAttackedTerritory());
        //                 attackPhase.destroyArmies();
        //                 attackPhase.conquerTerritory(aiBehaviour.getArmiesToMove());
        //             }
        //             this.skipTurn();
        //             break;
        //         default:
        //             break;
        //     }
        // }
    }

    @Override
    public boolean gameOver() {
        Optional<Player> winner = players.stream().filter(p -> p.getTarget().isAchieved() == true).findAny();
        if (winner.isPresent()) {
            if (winner.get().equals(players.get(activePlayer))) {
                return true;
            } else {
                players.get(activePlayer).setTarget(new ConquerTerritoriesTarget(players.get(activePlayer),
                        randomNumberGenerator.nextInt(
                                Math.toIntExact(Math
                                        .round(map.getTerritories().size() * MIN_TERRITORIES_TO_CONQUER_PERCENTAGE)),
                                Math.toIntExact(Math
                                        .round(map.getTerritories().size() * MAX_TERRITORIES_TO_CONQUER_PERCENTAGE)))));
            }
        }
        return false;
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(activePlayer);
    }

    @Override
    public List<Territory> getTerritoriesList() {
        return map.getTerritories();
    }

    @Override
    public String getOwner(String territory) {
        return players.stream().filter(p -> p.getOwnedTerritories().stream().anyMatch(t -> t.equals(territory)))
                .findFirst().get().getColor_id();
    }

    @Override
    public String getMapName() {
        return this.map.getName();
    }

    @Override
    public void setAttacking() {
        if (status == GameStatus.READY_TO_ATTACK) {
            status = GameStatus.ATTACKING;
        }
    }

    @Override
    public void endAttack() {
        if (status == GameStatus.ATTACKING) {
            status = GameStatus.READY_TO_ATTACK;
        }
    }

    @Override
    public boolean areTerritoriesNear(String territory1, String territory2) {
        return map.areTerritoriesNear(territory1, territory2);
    }

    @Override
    public Card pullCard() {
        return deck.pullCard();
    }

    @Override
    public void playCards(final String card1, final String card2,final String card3, final Player player){
        deck.playCards(card1, card2, card3, player);
    }

    @Override
    public void endCardsPhase() {
        if (status == GameStatus.CARDS_MANAGING) {
            nextGamePhase();
        }
    }

    @Override
    public Long getTurnsCount() {
        return turnsCount;
    }

    @Override
    public int getNumberOfArmies(String territory) {
        return map.getTerritories().stream().filter(t -> t.getTerritoryName().equals(territory)).mapToInt(t -> t.getNumberOfArmies()).sum();
    }

    @Override
    public void removeArmies(String territory, int numberOfMovingArmies) {
        map.removeArmies(territory, numberOfMovingArmies);
    }

    @Override
    public void setOwner(String territory, String color_id) {
        map.setOwner(territory,color_id);
    }
}
