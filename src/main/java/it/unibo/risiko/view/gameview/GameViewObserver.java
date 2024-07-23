package it.unibo.risiko.view.gameview;

/**
 * Observer used by the view to interact with the controller by calling it's
 * void methods.
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
     * @param mapName - name of the map.
     * @param players - number of players controlled by humans.
     * @param bots    - number of players controlled by the
     *                computer.
     * @author Manuele D'Ambrosio.
     */
    void startNewGame(String mapName, int players, int bots);

    /**
     * Method that compute the attack after setting the number of
     * attacking armies and updates the view.
     * 
     * @param armies - Number of armies that are attacking.
     * @author Manuele D'Ambrosio.
     */
    void setAttackingArmies(int armies);

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
     * @param armies - Number of armies to move.
     * @author Manuele D'Ambrosio.
     */
    void setMovingArmies(int armies);

    /**
     * Tells the controller that the user clicked on a territory.
     * 
     * @author Michele Farneti
     * @param territory The selected territory'sname
     */
    void territorySelected(String territory);

    /**
     * Tells the controller that the player wants to enter the attack phase.
     * 
     * @author Michele Farneti
     */
    void setAttacking();

    /**
     * Method used to move a certain amount of armies between two
     * adjacent territories.
     * 
     * @param src    is the source territory
     * @param dst    is the destination territory
     * @param armies is the number of armies that the player
     *               wants to move
     * 
     * @author Anna Malagoli
     */
    void moveArmies(String src, String dst, int armies);

    /**
     * Method used to play the three cards selected by a player.
     * 
     * @param card1 is the first card selected
     * @param card2 is the second card selected
     * @param card3 is the third card selected
     * 
     * @author Anna Malagoli
     */
    void playCards(String card1, String card2, String card3);

    /**
     * Alerts the controller that the move armies button has been clicked.
     * 
     * @author Michele Farneti
     */
    void moveClicked();

    /**
     * Alerts the controller that the armies movement phase has concluded.
     * 
     * @author Michele Farneti
     */
    void closeMovementPhase();

    /**
     * Alert the controller that the card managing phase has concluded.
     * 
     * @author Michele Farneti
     */
    void exitCardsManagingPhase();

    /**
     * Saves the actual game.
     * 
     * @author Manuele D'Ambrosio.
     */
    void saveGame();
}
