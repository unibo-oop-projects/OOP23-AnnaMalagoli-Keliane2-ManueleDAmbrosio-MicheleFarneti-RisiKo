package it.unibo.risiko.model.game;

import java.util.List;

/**
 * This class contains all the functions of the attack phase.
 * 
 * @author Manuele D'Ambrosio
 */

public interface AttackPhase {

    /**
     * This method is used to get the dice throws of the attacker.
     * @return a list representing the attacker's dice throws.
     */
    public List<Integer> getAttackerDiceThrows();

    /**
     * This method is used to get the dice throws of the defender.
     * @return a list representing the defender's dice throws.
     */
    public List<Integer> getDefenderDiceThrows();

    /**
     * This method is used to get the number of armies lost by
     * the attacking player.
     * @return the attacking player's lost armies.
     */
    public int getAttackerLostArmies();

    /**
     * This method is used to get the number of armies lost by
     * the defending player.
     * @return the defending player's lost armies.
     */
    public int getDefenderLostArmies();

    /**
     * This method is used to know if the attacked territory is
     * conquered by the attacking player.
     * @return true if the territory has no more armies,
     * false otherwise.
     */
    public boolean isTerritoryConquered();
}

