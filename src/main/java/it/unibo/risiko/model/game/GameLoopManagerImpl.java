package it.unibo.risiko.model.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.player.Player;

/**
 * Implementation of a gameLoopManger: It will keep track of the status of a
 * game by updating its gameStatus and activePlayerIndex in relation to the
 * player's actions.
 * 
 * @author Michele Farneti
 */
public final class GameLoopManagerImpl extends ActionHandlerImpl implements GameLoopManager {
    private static final double NEW_TARGET_PERCENTAGE = 0.7;
    private static final int MIN_CARDS_PLAYABLE = 1;
    private static final Random RANDOM = new Random();
    private final PlaceArmiesActionHandler occupationPhaseActionHandler = new OccupationPhaseActionHandler();
    private final PlaceArmiesActionHandler placementPhaseActionHandler = new PlacementPhaseActionHandler();
    private Boolean wasAI = false;
    private Boolean skippedToAI = false;
    private Integer turnsCount = 0;

    /**
     * Constructor for the gameLoopManager setting to TERRITORY_OCCUPATION the
     * status of the game and to 0 the index of the activePlayer.
     */
    public GameLoopManagerImpl() {
        this.setGameStatus(GameStatus.TERRITORY_OCCUPATION);
        this.setActivePlayerIndex(0);
    }

    @Override
    public void placeArmiesIfPossible(final List<Player> players, final String territory,
            final Territories territories) {
        wasAI = players.get(this.getActivePlayerIndex()).isAI();
        GameStatus newStatus = GameStatus.ARMIES_PLACEMENT;
        switch (this.getGameStatus()) {
            case TERRITORY_OCCUPATION:
                if (occupationPhaseActionHandler.checkActionAndExceute(this.getActivePlayerIndex(), players, territory,
                        territories)) {
                    newStatus = occupationPhaseActionHandler.getGameStatus();
                    this.setActivePlayerIndex(occupationPhaseActionHandler.getActivePlayerIndex());
                }
                break;
            case ARMIES_PLACEMENT:
                if (placementPhaseActionHandler.checkActionAndExceute(this.getActivePlayerIndex(), players, territory,
                        territories)) {
                    newStatus = placementPhaseActionHandler.getGameStatus();
                    this.setActivePlayerIndex(placementPhaseActionHandler.getActivePlayerIndex());
                }
                break;
            default:
                break;
        }
        this.setGameStatus(newStatus);
        checkSkipToAI(players);
    }

    private void checkSkipToAI(final List<Player> players) {
        if (!wasAI && players.get(this.getActivePlayerIndex()).isAI()) {
            skippedToAI = true;
        } else {
            skippedToAI = false;
        }
    }

    @Override
    public void skipTurn(final List<Player> players, final Territories territories) {
        wasAI = players.get(this.getActivePlayerIndex()).isAI();
        GameStatus newStatus;
        switch (this.getGameStatus()) {
            case ATTACKING:
            case READY_TO_ATTACK:
                players.get(nextPlayer(this.getActivePlayerIndex(), players.size()))
                        .computeReinforcements(territories.getListContinents());
                if (players.get(nextPlayer(this.getActivePlayerIndex(), players.size()))
                        .getNumberOfCards() >= MIN_CARDS_PLAYABLE) {
                    newStatus = GameStatus.CARDS_MANAGING;
                } else if (players.get(nextPlayer(this.getActivePlayerIndex(), players.size()))
                        .getArmiesToPlace() > 0) {
                    newStatus = GameStatus.ARMIES_PLACEMENT;
                } else {
                    newStatus = GameStatus.READY_TO_ATTACK;
                }
                this.setActivePlayerIndex(nextPlayer(this.getActivePlayerIndex(), players.size()));
                this.setGameStatus(newStatus);
                break;
            default:
                break;
        }
        checkSkipToAI(players);
        if (this.getActivePlayerIndex() == 0) {
            turnsCount++;
        }
    }

    @Override
    public Integer nextPlayer(final Integer activePlayer, final Integer playersCount) {
        return super.nextPlayer(activePlayer, playersCount);
    }

    @Override
    public Integer getTurnsCount() {
        return turnsCount;
    }

    @Override
    public Boolean skippedToAi() {
        return skippedToAI;
    }

    @Override
    public void setLoopPhase(final GameStatus status) {
        this.setGameStatus(status);
    }

    @Override
    public boolean isGameOver(final List<Player> players, final Territories territories) {
        final Optional<Player> winner = players.stream().filter(p -> p.getTarget().isAchieved()).findAny();
        if (winner.isPresent()) {
            if (winner.get().equals(players.get(this.getActivePlayerIndex()))) {
                return true;
            } else {
                players.get(this.getActivePlayerIndex())
                        .setTarget(new ConquerTerritoriesTarget(players.get(this.getActivePlayerIndex()),
                                RANDOM
                                        .nextInt(Math.toIntExact(
                                                Math.round(territories.getListTerritories().size()
                                                        * NEW_TARGET_PERCENTAGE)))));
            }
        }
        return false;
    }
}
