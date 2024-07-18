package it.unibo.risiko.model.dice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Manuele D'Ambrosio
 */

class TestTripleDiceImpl {
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    @Test
    void testBuilder() {
        final TripleDice dice1 = new TripleDiceImpl(3);
        final TripleDice dice2 = new TripleDiceImpl(2);
        final TripleDice dice3 = new TripleDiceImpl(1);
        final TripleDice dice4 = new TripleDiceImpl(4);

        assertTrue(dice1.getResults().get(MAX_VAL) >= dice1.getResults().get(MID_VAL) &&
                dice1.getResults().get(MID_VAL) >= dice1.getResults().get(MIN_VAL));
        assertTrue(dice2.getResults().get(MAX_VAL) >= dice2.getResults().get(MID_VAL) &&
                dice2.getResults().get(MID_VAL) >= dice2.getResults().get(MIN_VAL));
        assertTrue(dice3.getResults().get(MAX_VAL) >= dice3.getResults().get(MID_VAL) &&
                dice3.getResults().get(MID_VAL) >= dice3.getResults().get(MIN_VAL));
        assertTrue(dice4.getResults().get(MAX_VAL) >= dice4.getResults().get(MID_VAL) &&
                dice4.getResults().get(MID_VAL) >= dice4.getResults().get(MIN_VAL));

    }

    @Test
    void testAttackerLostArmies() {
        TripleDiceImpl attDice = new TripleDiceImpl(3);
        TripleDiceImpl defDice = new TripleDiceImpl(3);

        attDice.setDummyResults(6, 4, 2);
        defDice.setDummyResults(3, 3, 3);
        assertTrue(TripleDice.attackerLostArmies(attDice, defDice) == 1);
        attDice.setDummyResults(6, 6, 6);
        defDice.setDummyResults(6, 6, 5);
        assertTrue(TripleDice.attackerLostArmies(attDice, defDice) == 2);
        attDice.setDummyResults(1, 1, 1);
        defDice.setDummyResults(1, 1, 1);
        assertTrue(TripleDice.attackerLostArmies(attDice, defDice) == 3);

    }
}