package it.unibo.risiko.model.game;

/**
 * @author Farneti Michele
 * Enum used to rappresent the differente sta
 */
public enum GameStatus {
    TERRITORY_OCCUPATION,CARDS_MANAGING,ATTACKING,READY_TO_ATTACK,ARMIES_PLACEMENT,GAME_OVER;

    /**
     * 
     * @return The correct gamestatus for the next stage of the game
     */
    public GameStatus next() {
        switch (this) {
            case ARMIES_PLACEMENT:
                return READY_TO_ATTACK;
            case READY_TO_ATTACK:
                return ARMIES_PLACEMENT;
            default:
                return READY_TO_ATTACK;
        }
    }
}
