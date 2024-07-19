package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.dice.TripleDice;
import it.unibo.risiko.model.dice.TripleDiceImpl;

/**
 * Implementation of @AttackPhase interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class AttackPhaseImpl implements AttackPhase {
    private static final int MAX_DEFENDING_ARMIES = 3;

    private final int attackingArmies;
    private final int defendingArmies;
    private final TripleDiceImpl attackerDiceThrows;
    private final TripleDiceImpl defenderDiceThrows;
    private final int attackerLostArmies;
    private final int defenderLostArmies;

    /**
     * @param numberOfAttackingArmis   - number of attacking armies.
     * @param defendingTerritoryArmies - number of armies in the attacked territory.
     */
    public AttackPhaseImpl(final int numberOfAttackingArmis, final int defendingTerritoryArmies) {
        this.attackingArmies = numberOfAttackingArmis;
        this.defendingArmies = assignDefendingArmies(defendingTerritoryArmies);
        this.attackerDiceThrows = new TripleDiceImpl(this.attackingArmies);
        this.defenderDiceThrows = new TripleDiceImpl(this.defendingArmies);
        this.attackerLostArmies = computeAttackerLostArmies();
        this.defenderLostArmies = computeDefenderLostArmies();
    }

    @Override
    public List<Integer> getAttackerDiceThrows() {
        return this.attackerDiceThrows.getResults();
    }

    @Override
    public List<Integer> getDefenderDiceThrows() {
        return this.defenderDiceThrows.getResults();
    }

    @Override
    public int getAttackerLostArmies() {
        return attackerLostArmies;
    }

    @Override
    public int getDefenderLostArmies() {
        return defenderLostArmies;
    }

    @Override
    public boolean isTerritoryConquered() {
        return defendingArmies <= defenderLostArmies;
    }

    private int assignDefendingArmies(final int defendingTerritoryArmies) {
        return defendingTerritoryArmies >= MAX_DEFENDING_ARMIES
                ? MAX_DEFENDING_ARMIES
                : defendingTerritoryArmies;
    }

    private int computeAttackerLostArmies() {
        return TripleDice.attackerLostArmies(attackerDiceThrows, defenderDiceThrows);
    }

    private int computeDefenderLostArmies() {
        return Math.min(attackingArmies, defendingArmies) - attackerLostArmies;
    }
}
