package it.unibo.risiko.view.gameView;

import java.util.List;
import java.util.Map;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * The view interface for the game phase of the application
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Keliane Nana
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
     * 
     * @author Michele Farneti
     * @param mapNames A map with the name of the GameMap names and associated
     *                 maxPlayers
     */
    void showInitializationWindow(Map<String, Integer> mapNames);

    /**
     * Shows the game window used for displaying the events happening in the
     * 
     * @param mapName       The name of the map Used for the game
     * @param playersNumber The number of players playing the game
     * @author Michele Farneti
     */
    void showGameWindow(final String mapName,final Integer playersNumber);

    /**
     * Activates the placing and the viewing of the tanks buttons over their
     * respective territories
     * 
     * @param territories Territories list of the game map
     * @author Michele Farneti
     */
    void showTanks(final List<Territory> territories);

    /**
     * Setups one turn icon bar with the players' info
     * 
     * @param Player
     * @param playersIndex
     * @author Michele Farneti
     */
    void showTurnIcon(final Player player,final int playerIndex);

    /**
     * Edits the view in order to show wich player is the current player.
     * 
     * @param player
     * @author Michele Farneti
     */
    void setCurrentPlayer(final Player player);

    /**
     * Highlights a territory in a different way either if it is attacking ord
     * defending
     * 
     * @param territory  The territory wich is going to be higlighted as attacker
     * @param isAttacker
     * @author Michele Farneti
     */
    void showFightingTerritory(final Territory territory,final boolean isAttacker);

    /**
     * Deletes higlightings for a fighting territory
     * 
     * @param fightingTerritory
     * @author Michele Farneti
     */
    void resetFightingTerritory(final Territory fightingTerritory);

    /**
     * Updates the game view, changing a tank based on territory
     * occuaption and updating the armies count label
     * 
     * @param territory
     * @param playerColor
     * @author Michele Farneti
     */
    void redrawTank(final Territory territory, final String playerColor);

    /**
     * Updates the gameView making it show who is the winner of the game
     * 
     * @param winnerColor
     * @author Michele Farneti
     */
    void gameOver(final String winnerColor);

    /**
     * Updates the @GameView making it show the attack panel.
     * 
     * @param attacking                - Name of the attacking territory.
     * @param defending                - Name of the the defending territory
     * @param attackingTerritoryArmies - Number of armies in the attacking
     *                                 territory.
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
     * Closes the attack panel at the end of the attack.
     * 
     * @author Manuele D'Ambrosio
     */
    void closeAttackPanel();

    /**
     * Handles MoveArmiesPanel closure if it's closed without completing a move
     *
     * @author Michele Farneti
     */
    void exitMoveArmiesPanel();

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

    /**
     * Method used to create a panel that allows to move armies between two
     * adjacent territories.
     * @param listTerritories is the list of territories of a player
     * @author Anna Malagoli
     */
    public void createMoveArmies(final List<Territory> listTerritories);

    /**
     * Method used to create a panel that allows the player to play three cards.
     * @param playerCards is the list of cards of the player
     * @author Anna Malagoli
     */
    public void createChoiceCards(List<Card> playerCards);

    /**
     * 
     * Enables or deactivates the button used for armies movement.
     * 
     * @author Michele Farneti
     * @param enabled True if the button has to be enabled
     */
    public void enableMovements(final boolean enabled);

    /**
     * 
     * Enables or deactivates the button used for skipping
     * 
     * @author Michele Farneti
     * @param enabled True if the button has to be enabled
     */
    public void enableSkip(final boolean enabled);

    /**
     * 
     * Enables or deactivates the button used for attacks.
     * 
     * @author Michele Farneti
     * @param enabled True if the button has to be enabled
     */
    public void enableAttack(final boolean enabled);

    /**
     * Creates the logPanel used to display the events of the register
     * @param reg the register
     * @param l the list of the players 
     * @author Keliane Nana
     */
    public void createLog(Register reg, List<Player> l);

    /**
     * Updates the log with the events that should be visualized
     * @author Keliane Nana
     */
    public void updateLog();
    /**
     * Creates the territory table panel.
     * 
     * @param terr    - list of territories.
     * @param players - list of players.
     */
    public void createTablePanel(List<Territory> terr, List<Player> players);

    /**
     * Updates the table panel.
     */
    public void updateTablePanel();

    /**
     * Handles Cards panel closure
     * 
     * @author Michele Farneti
     */
    void exitCardsPanel();

    /**
     * Updates the view with infos about the gamestatus and current turn.
     * @param gameStatus
     * @param turnsCount
     */
    void showStatus(GameStatus gameStatus, Long turnsCount);
}
