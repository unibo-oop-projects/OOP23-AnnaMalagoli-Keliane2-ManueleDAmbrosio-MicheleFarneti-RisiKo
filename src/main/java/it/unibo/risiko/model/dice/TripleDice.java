package it.unibo.risiko.model.dice;

import java.util.List;

/**
 * A triple dice with a method for computing results.
 * 
 * @author Manuele D'Ambrosio
 */

public interface TripleDice {
    /**
     *Initial value.
     */
    int INITIAL_VALUE = 0;
    /**
     * Value of an invalid or unused throw. 
     */
    int NOT_A_THROW = -1;

    /**
     * This method is used to get the list of throws,
     * but not always all the throws are valid.
     * 
     * @return a list of lenght 3 containing the values
     *         of the 3 dice throws.
     */
    List<Integer> getResults();

    /**
     * This method is used to comupute the number of attacker's lost armies.
     * 
     * @param attackerThrows - attacker's throws.
     * @param defenderThrows - defender'S throws.
     * @return the number of armies lost by the attacker.
     */
    static int attackerLostArmies(final TripleDice attackerThrows, final TripleDice defenderThrows) {
        int attackerArmies = INITIAL_VALUE;
        int defenderArmies = INITIAL_VALUE;
        int lostArmies = INITIAL_VALUE;
        int maxLostArmies;
        for (final int result : attackerThrows.getResults()) {
            if (result != NOT_A_THROW) {
                attackerArmies++;
            }
        }
        for (final int result : defenderThrows.getResults()) {
            if (result != NOT_A_THROW) {
                defenderArmies++;
            }
        }
        maxLostArmies = Math.min(attackerArmies, defenderArmies);
        for (int i = 0; i < maxLostArmies; i++) {
            if (attackerThrows.getResults().get(i) <= defenderThrows.getResults().get(i)) {
                lostArmies++;
            }
        }

        return lostArmies;
    }
}
