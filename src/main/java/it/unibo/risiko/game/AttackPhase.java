package it.unibo.risiko.game;

import it.unibo.risiko.TripleDice;

/**
 * @author Manuele D'Ambrosio
 */

public interface AttackPhase {

    /**
     * This method is used to get the dice throws of the attacker.
     * @return the attacker's dice throws.
     */
    public TripleDice getAttackerDiceThrows();

    /**
     * This method is used to get the dice throws of the defender.
     * @return the defender's dice throws.
     */
    public TripleDice getDefenderDiceThrows();

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
