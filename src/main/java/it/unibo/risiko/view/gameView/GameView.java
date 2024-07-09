package it.unibo.risiko.view.gameView;

import java.util.List;

/**
 * The view interface for the game phase of the application
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 */
public interface GameView {

    /**
     * Sets the observer for the view
     * 
     * @param gameController Observer
     * @author Michele Farneti
     */
    void setObserver(GameViewObserver gameController);

    /**
     * Starts the view by setting up all of the main components
     * 
     * @author Michele Farneti
     */
    void start();

    /**
     * Activates the placing and the viewing of the tanks buttons over their
     * respective territories
     * 
     * @param territories Territories list of the game map
     * @author Michele Farneti
     */
    void showTanks(List<String> territories);

    /**
     * Setups one turn icon bar with the players' info
     * 
     * @param playersColor
     * @param playersIndex
     * @param isAI
     * @author Michele Farneti
     */
    void showTurnIcon(String playerColor, int playerIndex, boolean isAI);

    /**
     * Edits the view in order to show wich player is the current player.
     * 
     * @param player
     * @author Michele Farneti
     */
    void setCurrentPlayer(String playerColor, Integer nArmies);

    /**
     * Highlights a territory in a different way either if it is attacking ord
     * defending
     * 
     * @param territory  The territory wich is going to be higlighted as attacker
     * @param isAttacker
     * @author Michele Farneti
     */
    void showFightingTerritory(String territory, boolean isAttacker);

    /**
     * Deletes higlights of the territories that just fighted
     * 
     * @param attackerTerritory
     * @param defenderTerritory
     * @author Michele Farneti
     */
    void resetFightingTerritories(String attackerTerritory, String defenderTerritory);

    /**
     * Update the game view, changing a tank based on territory
     * occuaption and updating the armies count label
     * 
     * @param territoryName
     * @param playerColor
     * @param armiesCount
     * @author Michele Farneti
     */
    void redrawTank(String territroyName, String playerColor, Integer armiesCount);

    /**
     * Updates the gameView making it show who is the winner of the game
     * 
     * @param winnerColor
     * @author Michele Farneti
     */
    void gameOver(String winnerColor);

    /**
     * Updates the @GameView making it show the attack panel.
     * 
     * @param attacking - Name of the attacking territory.
     * @param defending - Name of the the defending territory
     * @param attackingTerritoryArmies - Number of armies in the attacking territory.
     * @author Manuele D'Ambrosio
     */
    void createAttack(final String attacking, final String defending,
            final int attackingTerritoryArmies);

    /**
     * Updates the attack panel after the attacking player has selected
     * the number of attacking armies.
     * 
     * @author Manuele D'Ambrosio
     */
    void drawDicePanels();

    /**
     * Updates the attack panel to make the player select the
     * armies to move in the conquered territory.
     * 
     * @author Manuele D'Ambrosio
     */
    void drawConquerPanel();

    /**
     * Method used to set the attacker's dice throws.
     * 
     * @param attDice - results list of the attacker dices.
     * @author Manuele D'Ambrosio
     */
    public void setAtt(final List<Integer> attDice);

    /**
     * Method used to set the defender's dice throws.
     * 
     * @param defDice - Results list of the defender dices.
     * @author Manuele D'Ambrosio
     */
    public void setDef(final List<Integer> defDice);

    /**
     * Method used to set the number of armies lost by the
     * attacker.
     * 
     * @param attackerLostArmies - Number of armies lost by the attacker.
     * @author Manuele D'Ambrosio
     */
    public void setAttackerLostArmies(final int attackerLostArmies);

    /**
     * Method used to set the number of armies lost by the
     * defender.
     * 
     * @param defenderLostArmies - Number of armies lost by the defender.
     * @author Manuele D'Ambrosio
     */
    public void setDefenderLostArmies(final int defenderLostArmies);
}
