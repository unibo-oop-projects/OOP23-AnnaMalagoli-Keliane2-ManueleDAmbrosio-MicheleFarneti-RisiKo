package it.unibo.risiko.game;

import it.unibo.risiko.TripleDice;

/**
 * @author Manuele D'Ambrosio
 */

public interface AttackPhase {

    public TripleDice getAttackerDiceThrows();

    public TripleDice getDefenderDiceThrows();

    public int getAttackerLostArmies();

    public int getDefenderLostArmies();
}
