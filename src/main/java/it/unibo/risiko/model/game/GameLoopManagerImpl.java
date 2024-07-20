package it.unibo.risiko.model.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.objective.ConquerTerritoriesTarget;
import it.unibo.risiko.model.player.Player;

public class GameLoopManagerImpl extends ActionHandler implements GameLoopManager {
    private static final double NEW_TARGET_PERCENTAGE = 0.7;
    private static final int MIN_CARDS_PLAYABLE = 1;
    private PlaceArmiesActionHandler occupationPhaseActionHandler = new OccupationPhaseActionHandler();
    private PlaceArmiesActionHandler placementPhaseActionHandler = new PlacementPhaseActionHandler();
    private Boolean wasAI = false;
    private Boolean skippedToAI = false;
    private Long turnsCount;

    public GameLoopManagerImpl() {
        this.gameStatus = GameStatus.TERRITORY_OCCUPATION;
        this.activePlayerIndex = 0;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public Integer getActivePlayerIndex() {
        return activePlayerIndex;
    }

    @Override
    public void placeArmiesIfPossible(List<Player> players, String territory, Territories territories) {
        wasAI = players.get(this.activePlayerIndex).isAI();
        GameStatus newStatus = GameStatus.ARMIES_PLACEMENT;
        switch (this.gameStatus) {
            case TERRITORY_OCCUPATION:
                if (occupationPhaseActionHandler.checkActionAndExceute(activePlayerIndex, players, territory,
                        territories)) {
                    newStatus = occupationPhaseActionHandler.getGameStatus();
                    this.activePlayerIndex = occupationPhaseActionHandler.getActivePlayerIndex();
                }
                break;
            case ARMIES_PLACEMENT:
                if (placementPhaseActionHandler.checkActionAndExceute(activePlayerIndex, players, territory,
                        territories)) {
                    newStatus = placementPhaseActionHandler.getGameStatus();
                    this.activePlayerIndex = placementPhaseActionHandler.getActivePlayerIndex();
                }
                break;
            default:
                break;
        }
        this.gameStatus = newStatus;
        checkSkipToAI(players);
    }

    private void checkSkipToAI(List<Player> players) {
        if (!wasAI && players.get(activePlayerIndex).isAI()) {
            skippedToAI = true;
        } else {
            skippedToAI = false;
        }
    }

    @Override
    public void skipTurn(List<Player> players, Territories territories) {
        wasAI = players.get(this.activePlayerIndex).isAI();
        GameStatus newStatus = GameStatus.ARMIES_PLACEMENT;
        switch (this.gameStatus) {
            case ATTACKING:
            case READY_TO_ATTACK:
                players.get(nextPlayer(this.activePlayerIndex, players.size()))
                        .computeReinforcements(territories.getListContinents());
                if (players.get(nextPlayer(this.activePlayerIndex, players.size()))
                        .getNumberOfCards() >= MIN_CARDS_PLAYABLE) {
                    newStatus = GameStatus.CARDS_MANAGING;
                } else if (players.get(nextPlayer(this.activePlayerIndex, players.size())).getArmiesToPlace() > 0) {
                    newStatus = GameStatus.ARMIES_PLACEMENT;
                } else {
                    newStatus = GameStatus.READY_TO_ATTACK;
                }
                activePlayerIndex = nextPlayer(activePlayerIndex, players.size());  
                this.gameStatus = newStatus;
                break;
            default:
                break;
        }
        checkSkipToAI(players);
    }

    @Override
    public Integer nextPlayer(Integer activePlayer, Integer playersCount) {
        return super.nextPlayer(activePlayer, playersCount);
    }

    @Override
    public Long getTurnsCount() {
        return turnsCount;
    }

    @Override
    public Boolean skippedToAi() {
        return skippedToAI;
    }

    @Override
    public void setLoopPhase(GameStatus status) {
        this.gameStatus = status;
    }

    @Override
    public boolean isGameOver(final List<Player> players, final Territories territories) {
        var randomNumberGenerator = new Random();
        final Optional<Player> winner = players.stream().filter(p -> p.getTarget().isAchieved()).findAny();
        if (winner.isPresent()) {
            if (winner.get().equals(players.get(this.activePlayerIndex))) {
                return true;
            } else {
                players.get(this.activePlayerIndex)
                        .setTarget(new ConquerTerritoriesTarget(players.get(this.activePlayerIndex),
                                randomNumberGenerator
                                        .nextInt(Math.toIntExact(
                                                Math.round(territories.getListTerritories().size()
                                                        * NEW_TARGET_PERCENTAGE)))));
            }
        }
        return false;
    }
}
