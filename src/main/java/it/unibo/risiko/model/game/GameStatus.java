package it.unibo.risiko.model.game;

/**
 * 
 * Enum used to rappresent the different stages of a game in order to coordinate
 * them during the game loop.
 * 
 * @author Michele Farneti
 */
public enum GameStatus {
    /**
     * The gamestatus for the first stage of the game, the player in this stage is
     * goint to place armies within a certain limit and than the turn is going to
     * automatically skip. Once every player will have played all of his armies the
     * game is going to get out of thi stage.
     */
    TERRITORY_OCCUPATION,
    /**
     * This gamestatus allows the player to place their reinforcements over the
     * battle filed.
     */
    ARMIES_PLACEMENT,
    /**
     * This gamestatus allows the player to skipp the turn, to attack and to move
     * armies between two adjacent territories it owns.
     */
    READY_TO_ATTACK,
    /**
     * This gamestatus will allow the player to do an attack.
     */
    ATTACKING,
    /**
     * During this gamestage the player will be able to play cards combos.
     */
    CARDS_MANAGING,
}
