package it.unibo.risiko;

/**
 * compare the results of the multiple dice throws.
 * @author Manuele D'Ambrosio
 */


public class TripleDiceImpl extends Dice{
    private static final int MAX_THROWS = 3;
    private static final int MIN_THROWS = 1;
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    private int[] results;
    private int[] temporaryResults;
    private int numberOfThrows;


    public TripleDiceImpl(final int numberOfThrows) {
        this.numberOfThrows = numberOfThrows;
        if (isLegitNumberOfThrows(numberOfThrows)) {
            this.results = new int[3];
            this.temporaryResults = new int[3];
            this.results[MAX_VAL] = diceThrow(); 
            this.results[MID_VAL] = diceThrow(); 
            this.results[MIN_VAL] = diceThrow(); 
            orderResults(numberOfThrows);
        }
        else {
            throw new IllegalArgumentException("Number of Throws out of range");
        }
    }

    /*
     * This method is only used for tests.
     */
    public void setDummyThrows(final int first, final int second, final int third) {
        this.results[MAX_VAL] = first;
        this.results[MID_VAL] = second; 
        this.results[MIN_VAL] = third; 
        orderResults(this.numberOfThrows);
    }

    public int getNumberOfThrows() {
        return this.numberOfThrows;
    }

    public int[] getResults() {
        return this.results;
    }

    private boolean isLegitNumberOfThrows(final int numberOfThrows) {
        return numberOfThrows <= MAX_THROWS && numberOfThrows >= MIN_THROWS;
    }

    private void orderResults(final int numberOfThrows) {
        if (numberOfThrows == MAX_THROWS) {
            int val_sum = results[MAX_VAL] + results[MID_VAL] + results[MIN_VAL];
            temporaryResults[MAX_VAL] = results[MAX_VAL];
            temporaryResults[MIN_VAL] = results[MIN_VAL];
            for (int i : results) {
                if (results[i] > temporaryResults[MAX_VAL]) {
                    temporaryResults[MAX_VAL] = results[i];
                }
                if (results[i] < temporaryResults[MIN_VAL]) {
                    temporaryResults[MIN_VAL] = results[i];
                }
            }
            temporaryResults[MID_VAL] = val_sum - temporaryResults[MAX_VAL] - temporaryResults[MIN_VAL];
            results = temporaryResults;
        }
        else if (numberOfThrows == MIN_THROWS + 1) {
            if (results[MIN_VAL] > results[MAX_VAL]) {
                temporaryResults[MAX_VAL] = results[MIN_VAL];
                results[MIN_VAL] = results[MAX_VAL];
                results[MAX_VAL] = results[MIN_VAL];
            }
        }
        else {}
    }

    public static int attackerLostArmies(final TripleDiceImpl attackerThrows, final TripleDice defenderThrows) {
        int maxLostArmies = Math.min(attackerThrows.getNumberOfThrows(), defenderThrows.getNumberOfThrows());
        int lostArmies = 0;
        if (maxLostArmies == MAX_THROWS) {
            if (attackerThrows.getResults()[MAX_VAL] >= defenderThrows.getResults()[MAX_VAL]) {
                lostArmies++;
            }
            if (attackerThrows.getResults()[MIN_VAL] >= defenderThrows.getResults()[MIN_VAL]) {
                lostArmies++;
            }
            if (attackerThrows.getResults()[MID_VAL] >= defenderThrows.getResults()[MID_VAL]) {
                lostArmies++;
            }
        }
        else if (maxLostArmies == MIN_THROWS) {
            if (attackerThrows.getResults()[MAX_VAL] >= defenderThrows.getResults()[MAX_VAL]) {
                lostArmies++;
            }
        }
        else {
            if (attackerThrows.getResults()[MAX_VAL] >= defenderThrows.getResults()[MAX_VAL]) {
                lostArmies++;
            }
            if (attackerThrows.getResults()[MIN_VAL] >= defenderThrows.getResults()[MIN_VAL]) {
                lostArmies++;
            }
        }
        return lostArmies;
    }
}
