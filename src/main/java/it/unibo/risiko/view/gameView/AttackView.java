package it.unibo.risiko.view.gameView;

import java.util.List;

/**
 * Interface used by the controlled to set the variables of the
 * attack view.
 * 
 * @author Manuele D'Ambrosio
 */

public interface AttackView {

    /**
     * Method used to set the attacker's dice throws.
     * 
     * @param attDice - results list of the attacker dices.
     */
    public void setAtt(final List<Integer> attDice);

    /**
     * Method used to set the defender's dice throws. 
     * 
     * @param defDice - Results list of the defender dices.
     */
     public void setDef(final List<Integer> defDice);

    /**
     * This method is used to know if the attacked territory has
     * been conquered.
     * 
     * @param territoryConquered - True if the territory has been
     * conquered, false otherwise.
     */
    public void isTerritoryConquered(final boolean territoryConquered);

    /**
     * Method used to set the number of armies lost by the
     * attacker.
     * 
     * @param attackerLostArmies - Number of armies lost by the attacker.
     */
    public void setAttackerLostArmies(final int attackerLostArmies);

    /**
     * Method used to set the number of armies lost by the 
     * defender.
     * 
     * @param defenderLostArmies - Number of armies lost by the defender.
     */
    public void setDefenderLostArmies(final int defenderLostArmies);
}
