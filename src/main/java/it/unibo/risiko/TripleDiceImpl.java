package it.unibo.risiko;

/**
 * compare the results of the multiple dice throws.
 * 
 * @author Manuele D'Ambrosio
 */

public class TripleDiceImpl extends Dice implements TripleDice {
    private static final int MAX_THROWS = 3;
    private static final int MIN_THROWS = 1;
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    private int[] results;
    private int numberOfThrows;

    public TripleDiceImpl(final int numberOfThrows) {
        this.numberOfThrows = numberOfThrows;
        if (isLegitNumberOfThrows(numberOfThrows)) {

            this.results = new int[numberOfThrows];

            for (int i = 0; i < numberOfThrows; i++) {
                this.results[i] = diceThrow();
                orderResults(numberOfThrows);
            }
        } else {
            throw new IllegalArgumentException("Number of Throws out of range");
        }
    }

    public int getNumberOfThrows() {
        return this.numberOfThrows;
    }

    public int[] getResults() {
        return this.results;
    }

    public String toString() {
        if (numberOfThrows == MAX_THROWS) {
            return "Results: " + results[MAX_VAL] + ", " + results[MID_VAL] + ", " + results[MIN_VAL];
        } else if (numberOfThrows == MIN_THROWS) {
            return "Results: " + results[MAX_VAL];
        } else {
            return "Results: " + results[MAX_VAL] + ", " + results[MID_VAL];
        }
    }

    private boolean isLegitNumberOfThrows(final int numberOfThrows) {
        return numberOfThrows <= MAX_THROWS && numberOfThrows >= MIN_THROWS;
    }

    private void orderResults(final int numberOfThrows) {
        int temp = 0;
        if (numberOfThrows == MAX_THROWS) {
            // Looking for the max result.
            for (int i = 0; i < MAX_THROWS; i++) {
                if (results[i] > temp) {
                    temp = results[i];
                }
                results[MAX_VAL] = temp;
                // Oredering the other two results.
                if (results[MIN_VAL] > results[MID_VAL]) {
                    temp = results[MID_VAL];
                    results[MID_VAL] = results[MIN_VAL];
                    results[MIN_VAL] = temp;
                }
            }
        }
        // The two results are swapped if not already ordered.
        else if (numberOfThrows == MIN_THROWS + 1) {
            if (results[MAX_VAL] < results[MID_VAL]) {
                temp = results[MAX_VAL];
                results[MAX_VAL] = results[MID_VAL];
                results[MID_VAL] = temp;
            }
        } else {
        }
    }
}
