package it.unibo.risiko.model.game;

import java.util.List;

import it.unibo.risiko.model.dice.TripleDice;
import it.unibo.risiko.model.dice.TripleDiceImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.player.Player;

/**
 * Implementation of AttackPhase interface.
 * 
 * @author Manuele D'Ambrosio
 */

public class AttackPhaseImpl implements AttackPhase {
    private static final int MAX_DEFENDING_ARMIES = 3;

    private Player attackingPlayer;
    private Player defendingPlayer;
    private Territory attackerTerritory;
    private Territory defenderTerritory;
    private int attackingArmies;
    private int defendingArmies;
    private TripleDiceImpl attackerDiceThrows;
    private TripleDiceImpl defenderDiceThrows;
    private int attackerLostArmies;
    private int defenderLostArmies;

    /**
     * Builder method for AttackPhaseImpl class.
     * 
     * @param attackingPlayer   - The attacking player.
     * @param attackerTerritory - The territory the attacking player is attacking
     *                          from.
     * @param attackingArmies   - The number of armies the attacking player is
     *                          attacking with.
     * @param defendingPlayer   - The defending player.
     * @param defenderTerritory - The territory the defending player is defending.
     */
    public AttackPhaseImpl(final Player attackingPlayer, final Territory attackerTerritory, final int attackingArmies,
            final Player defendingPlayer, final Territory defenderTerritory) {

        if (isLegitAttackingArmies(attackerTerritory, attackingArmies)
                && isLegitOwner(defendingPlayer, defenderTerritory)
                && isLegitOwner(attackingPlayer, attackerTerritory)) {
            this.attackingPlayer = attackingPlayer;
            this.defendingPlayer = defendingPlayer;
            this.defenderTerritory = defenderTerritory;
            this.attackerTerritory = attackerTerritory;
            this.attackingArmies = attackingArmies;
            this.defendingArmies = assignDefendingArmies();
            this.attackerDiceThrows = new TripleDiceImpl(attackingArmies);
            this.defenderDiceThrows = new TripleDiceImpl(defendingArmies);
            this.attackerLostArmies = computeAttackerLostArmies();
            this.defenderLostArmies = computeDefenderLostArmies();
        } else {
            throw new IllegalArgumentException(
                    "Number of attacking armies not allowed or players are not the owners of the territories: " +
                        "attacking player: " + attackingPlayer.getColor_id() + 
                        "; defending player: " + defendingPlayer.getColor_id() + 
                        "; attacking armies: " + attackingArmies + 
                        "; attacking territory: " + attackerTerritory.getTerritoryName() + 
                        "; defending territory: " + defenderTerritory.getTerritoryName());
        }
    }

    public List<Integer> getAttackerDiceThrows() {
        return this.attackerDiceThrows.getResults();
    }

    public List<Integer> getDefenderDiceThrows() {
        return this.defenderDiceThrows.getResults();
    }

    public int getAttackerLostArmies() {
        return attackerLostArmies;
    }

    public int getDefenderLostArmies() {
        return defenderLostArmies;
    }

    public boolean isTerritoryConquered() {
        if (defenderLostArmies >= defenderTerritory.getNumberOfArmies()) {
            return true;
        } else {
            return false;
        }
    }

    public void destroyArmies() {
        defenderTerritory.removeArmies(defenderLostArmies);
        attackerTerritory.removeArmies(attackerLostArmies);
    }

    public boolean conquerTerritory(final int armiesToMove) {
        if (isTerritoryConquered() && isLegitArmiesToMove(armiesToMove)) {
            defendingPlayer.removeTerritory(defenderTerritory);
            attackingPlayer.addTerritory(defenderTerritory);
            attackerTerritory.removeArmies(armiesToMove);
            defenderTerritory.addArmies(armiesToMove);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Player " + attackingPlayer.getColor_id() + " is attacking from" +
                attackerTerritory.getTerritoryName() + " with " +
                attackingArmies + " armies." +
                "\nPlayer " + defendingPlayer.getColor_id() + " is defending " +
                defenderTerritory.getTerritoryName() + " with " +
                defendingArmies + " armies.";

    }

    private boolean isLegitArmiesToMove(final int armiesToMove) {
        return (armiesToMove >= attackingArmies && armiesToMove < attackerTerritory.getNumberOfArmies());
    }

    private boolean isLegitOwner(final Player player, final Territory territory) {
        return player.isOwnedTerritory(territory);
    }

    private boolean isLegitAttackingArmies(final Territory territory, final int attackingArmies) {
        return (territory.getNumberOfArmies() > attackingArmies);
    }

    private int assignDefendingArmies() {
        return defenderTerritory.getNumberOfArmies() >= MAX_DEFENDING_ARMIES
                ? MAX_DEFENDING_ARMIES
                : defenderTerritory.getNumberOfArmies();
    }

    private int computeAttackerLostArmies() {
        return TripleDice.attackerLostArmies(attackerDiceThrows, defenderDiceThrows);
    }

    private int computeDefenderLostArmies() {
        return Math.min(attackingArmies, defendingArmies) - attackerLostArmies;
    }
}
