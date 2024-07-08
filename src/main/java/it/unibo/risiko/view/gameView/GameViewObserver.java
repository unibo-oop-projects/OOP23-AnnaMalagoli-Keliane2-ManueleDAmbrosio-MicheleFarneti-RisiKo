package it.unibo.risiko.view.gameView;

import it.unibo.risiko.model.map.Territory;

/**
 * Observer used by the view to interact with the controller by calling it's
 * void methods
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 */
public interface GameViewObserver {
    /**
     * Tells the controller that the user is triyng to skip to the next game phase.
     * 
     * @author Michele Farneti
     */
    void skipTurn();

    /**
     * Method used to set the name of the map.
     * 
     * @param mapName - name of the map.
     * @author Manuele D'Ambrosio
     */
    void setMapName(String mapName);

    /**
     * Method used to set the number of players controlled by humans.
     * 
     * @param numberOfStandardPlayers - number of players controlled by humans.
     * @author Manuele D'Ambrosio
     */
    void setStandardPlayers(int numberOfStandardPlayers);

    /**
     * Method used to set the number of players controlled by the computer.
     * 
     * @param numberOfAIPlayers - number of players controlled by the computer.
     * @author Manuele D'Ambrosio
     */
    void setAIPlayers(int numberOfAIPlayers);

    /**
     * Tells the controller that the user clicked on a territory
     * 
     * @author Michele Farneti
     */
    void territorySelected(String territory);

}
