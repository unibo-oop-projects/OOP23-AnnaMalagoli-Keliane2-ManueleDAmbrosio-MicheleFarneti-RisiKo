package it.unibo.risiko.model.player;

public interface PlayerFactory {

    /**
     * This method is used to create a Standard Player.
     * 
     * @param color - Color that identifies the player.
     * @param armiesToPlace - Number of starting armies of the player
     * @return a new istance of a manually controlled player.
     */
    public StdPlayer createStandardPlayer(final String color, final int armiesToPlace);

    /**
     * This method is used to create an AI Player.
     * 
     * @param color - Color that identifies the player.
     * @param armiesToPlace - Number of starting armies of the player
     * @return a new istance of a computer controlled player.
     */
    public EasyModePlayer createAIPlayer(final String color, final int armiesToPlace);
}
