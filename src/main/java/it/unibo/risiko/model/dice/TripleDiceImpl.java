package it.unibo.risiko.model.dice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * compare the results of the multiple dice throws.
 * 
 * @author Manuele D'Ambrosio
 */

public class TripleDiceImpl extends Dice implements TripleDice {
    private static final int NOT_A_THROW = -1;
    private static final int MAX_THROWS = 3;
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    private List<Integer> results = new ArrayList<>();

    public TripleDiceImpl(final int numberOfThrows) {
        for (int i = 0; i < MAX_THROWS; i++) {
            if (i < numberOfThrows) {
                results.add(i, diceThrow());
            } else {
                results.add(i, NOT_A_THROW);
            }
        }
        orderResults();
    }

    public List<Integer> getResults() {
        return this.results;
    }

    public String toString() {
        String max = Integer.toString(results.get(MAX_VAL));
        String mid = results.get(MID_VAL) == NOT_A_THROW? "/" : Integer.toString(results.get(MID_VAL));
        String min = results.get(MIN_VAL) == NOT_A_THROW? "/" : Integer.toString(results.get(MIN_VAL));
        return "Results: " + max + ", " + mid + ", " + min;

    }

    private void orderResults() {
        Collections.sort(results);
        Collections.reverse(results);
    }

}
