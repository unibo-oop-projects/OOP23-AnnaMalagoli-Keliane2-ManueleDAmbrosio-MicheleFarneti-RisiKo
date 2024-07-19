package it.unibo.risiko.model.dice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of @TripleDice.
 * 
 * @author Manuele D'Ambrosio
 */

public final class TripleDiceImpl implements TripleDice {
    private static final int DICE_MINIMUM_VALUE = 1;
    private static final int DICE_MAXIMUM_VALUE = 6;
    private static final int NOT_A_THROW = -1;
    private static final int MAX_THROWS = 3;
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    private static Random dice = new Random();

    private List<Integer> results = new ArrayList<>();

    /**
     * Creates dices throws for a maximum of three dices.
     * 
     * @param numberOfThrows - number of dice throws.
     */
    public TripleDiceImpl(final int numberOfThrows) {
        int diceThrows = numberOfThrows;
        if (diceThrows > MAX_THROWS) {
            diceThrows = MAX_THROWS;
        }

        for (int i = 0; i < MAX_THROWS; i++) {
            if (i < diceThrows) {
                results.add(i, dice.nextInt(DICE_MAXIMUM_VALUE) + DICE_MINIMUM_VALUE);
            } else {
                results.add(i, NOT_A_THROW);
            }
        }
        orderResults();
    }

    @Override
    public List<Integer> getResults() {
        return List.copyOf(this.results);
    }

    @Override
    public String toString() {
        final String max = Integer.toString(results.get(MAX_VAL));
        final String mid = results.get(MID_VAL) == NOT_A_THROW ? "/" : Integer.toString(results.get(MID_VAL));
        final String min = results.get(MIN_VAL) == NOT_A_THROW ? "/" : Integer.toString(results.get(MIN_VAL));
        return "Results: " + max + ", " + mid + ", " + min;

    }

    private void orderResults() {
        Collections.sort(results);
        Collections.reverse(results);
    }

    /**
     * This method is only used for tests.
     * 
     * @param d1
     * @param d2
     * @param d3
     */
    public void setDummyResults(final int d1, final int d2, final int d3) {
        this.results = List.of(d1, d2, d3);
    }

}
