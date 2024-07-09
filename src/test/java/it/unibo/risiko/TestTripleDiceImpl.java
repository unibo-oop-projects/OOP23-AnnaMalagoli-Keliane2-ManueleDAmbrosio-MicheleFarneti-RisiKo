package it.unibo.risiko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.dice.TripleDice;
import it.unibo.risiko.model.dice.TripleDiceImpl;

/**
 * @author Manuele D'Ambrosio
 */

class TestTripleDiceImpl {/*
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    @Test
    void TestBuilder() {
        final TripleDice dice = new TripleDiceImpl(3);
        assertEquals(dice.getNumberOfThrows(), 3);
    }

    @Test
    void TestBuilderOrder() {

        final TripleDice dice1 = new TripleDiceImpl(3);
        final TripleDice dice2 = new TripleDiceImpl(2);

        assertTrue(dice1.getResults()[MAX_VAL] >= dice1.getResults()[MID_VAL]
                && dice1.getResults()[MID_VAL] >= dice1.getResults()[MIN_VAL]);
        assertTrue(dice2.getResults()[MAX_VAL] >= dice2.getResults()[MID_VAL]);

    }

    @Test
    void TestBuilderException() {
        boolean throwFlag = false;

        try {
            final TripleDice dice = new TripleDiceImpl(4);
            dice.getResults();
        } catch (IllegalArgumentException e) {
            throwFlag = true;
        }

        assertTrue(throwFlag);
    }

    @Test
    void testGetNumberOfThrows() {
        final int number = 2;
        final TripleDice dice = new TripleDiceImpl(number);

        assertEquals(number, dice.getNumberOfThrows());
    }

    @Test
    void testToString() {
        final TripleDice dice1 = new TripleDiceImpl(3);
        final TripleDice dice2 = new TripleDiceImpl(2);
        final TripleDice dice3 = new TripleDiceImpl(1);

        final String results1 = "Results: " + dice1.getResults()[MAX_VAL] +
                ", " + dice1.getResults()[MID_VAL] +
                ", " + dice1.getResults()[MIN_VAL];
        final String results2 = "Results: " + dice2.getResults()[MAX_VAL] + ", " + dice2.getResults()[MID_VAL];
        final String results3 = "Results: " + dice3.getResults()[MAX_VAL];

        assertEquals(dice1.toString(), results1);
        assertEquals(dice2.toString(), results2);
        assertEquals(dice3.toString(), results3);
    }

    @Test
    void testAttackerLostArmies() {
        final TripleDiceImpl[] attacker = new TripleDiceImpl[100];
        final TripleDiceImpl[] defender = new TripleDiceImpl[100];

        for (int j = 1; j < 4; j++) {
            for (int i = 0; i < 50; i++) {
                attacker[i] = new TripleDiceImpl(j);
                defender[i] = new TripleDiceImpl(i%3 + 1);

                assertTrue(TripleDice.attackerLostArmies(attacker[i], defender[i]) <= Math.min(
                        attacker[i].getNumberOfThrows(),
                        defender[i].getNumberOfThrows()));
            }
        }
    }*/
}