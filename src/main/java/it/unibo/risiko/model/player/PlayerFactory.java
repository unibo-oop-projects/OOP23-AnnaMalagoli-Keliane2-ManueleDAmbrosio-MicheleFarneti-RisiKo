package it.unibo.risiko.model.player;

/**
 * Factory that creates istances of new players.
 * 
 * @author Manuele D'Ambrosio
 */

public interface PlayerFactory {

    /**
     * This method is used to create a Standard Player.
     * 
     * @return a new istance of a manually controlled player.
     */
    public StdPlayer createStandardPlayer();

    /**
     * This method is used to create an AI Player.
     * 
     * @return a new istance of a computer controlled player.
     */
    public EasyModePlayer createAIPlayer();
}
