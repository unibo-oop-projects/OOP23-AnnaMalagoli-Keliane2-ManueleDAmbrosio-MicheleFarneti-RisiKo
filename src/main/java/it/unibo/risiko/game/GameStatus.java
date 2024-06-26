package it.unibo.risiko.game;

/**
 * @author Farneti Michele
 * Enum used to rappresent the differente sta
 */
public enum GameStatus {
    TERRITORY_OCCUPATION,ATTACK,ARMIES_PLACEMENT;

    /**
     * 
     * @return The correct gamestatus for the next stage of the game
     */
    public GameStatus next() {
        switch (this) {
            case TERRITORY_OCCUPATION:
                return ARMIES_PLACEMENT;
            case ARMIES_PLACEMENT:
                return ATTACK;
            case ATTACK:
                return ARMIES_PLACEMENT;
            default:
                return ATTACK;
        }
    }
}
