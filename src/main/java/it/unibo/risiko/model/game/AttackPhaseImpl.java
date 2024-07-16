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
    private static final int NO_ARMIES = 0;

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
    public AttackPhaseImpl(Player attackingPlayer, Territory attackerTerritory, final int attackingArmies,
            Player defendingPlayer, Territory defenderTerritory) {

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
        if (defenderTerritory.getNumberOfArmies() <= NO_ARMIES) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroyArmies() {
        defenderTerritory.removeArmies(defenderLostArmies);
        attackerTerritory.removeArmies(attackerLostArmies);
    }

    @Override
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

    @Override
    public Player getAttacker() {
        return this.attackingPlayer;
    }

    @Override
    public Player getDefender() {
        return this.defendingPlayer;
    }

    @Override
    public Territory getAttackingTerritory() {
        return this.attackerTerritory;
    }

    @Override
    public Territory getDefendingTerritory() {
        return this.defenderTerritory;
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

    public static boolean isLegitOwner(final Player player, final Territory territory) {
        return player.isOwnedTerritory(territory);
    }

    public static boolean isLegitAttackingArmies(final Territory territory, final int attackingArmies) {
        return (territory.getNumberOfArmies() > attackingArmies) && (attackingArmies <= MAX_DEFENDING_ARMIES);
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
