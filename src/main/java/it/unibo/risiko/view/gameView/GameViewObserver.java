package it.unibo.risiko.view.gameView;

/**
 * Observer used by the view to interact with the controller by calling it's
 * void methods
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Anna Malagoli
 */
public interface GameViewObserver {
    /**
     * Tells the controller that the user is triyng to skip to the next game phase.
     * 
     * @author Michele Farneti
     */
    void skipTurn();

    /**
     * Method used to start a new game.
     * 
     * @param mapName                 - name of the map.
     * @param numberOfStandardPlayers - number of players controlled by humans.
     * @param numberOfAIPlayers       - number of players controlled by the
     *                                computer.
     * @author Manuele D'Ambrosio.
     */
    public void startNewGame(final String mapName, final int numberOfStandardPlayers, final int numberOfAIPlayers);

    /**
     * Method that compute the attack after setting the number of
     * attacking armies and updates the view
     * 
     * @param numberOfAttackingAmies - Number of armies that are attacking.
     * @author Manuele D'Ambrosio.
     */
    void setAttackingArmies(int numberOfAttackingAmies);

    /**
     * If a territory has been conquered opens the conquer panel,
     * otherwise closes the attack panel.
     * 
     * @author Manuele D'Ambrosio
     */
    void conquerIfPossible();

    /**
     * Method used to set the number of armies to move in a
     * conquered territory.
     * 
     * @param numberOfMovingArmies - Number of armies to move.
     * @author Manuele D'Ambrosio.
     */
    void setMovingArmies(int numberOfMovingArmies);

    /**
     * Tells the controller that the user clicked on a territory
     * 
     * @author Michele Farneti
     */
    void territorySelected(String territory);

    /**
     * Tells the controller that the player wants to enter the attack phase.
     * 
     * @author Michele Farneti
     */
    void setAttacking();

    /**
     * Used to
     * 
     * @param srcTerritory
     * @param dstTerritory
     * @param numArmies
     * 
     * @author Anna Malagoli
     */
    void moveArmies(String srcTerritory, String dstTerritory, int numArmies);

    /**
     * 
     * @param card1
     * @param card2
     * @param card3
     * 
     * @author Anna Malagoli
     */
    void playCards(String card1, String card2, String card3);

    /**
     * Alerts the controller that the move armies button has been clicked
     * 
     * @author Michele Farneti
     */
    void moveClicked();

    /**
     * Alerts the controller that the armies movement phase has concluded
     * 
     * @author Michele Farneti
     */
    void closeMovementPhase();

    /**
     * Alerts the controller that the cards managing phase has concluded
     * 
     * @author Michele Farneti
     */
    public void exitCardsManagingPhase();

}
