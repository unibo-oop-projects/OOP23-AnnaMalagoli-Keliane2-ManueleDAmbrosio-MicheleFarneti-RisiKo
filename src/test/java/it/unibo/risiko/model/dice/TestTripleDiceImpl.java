package it.unibo.risiko.model.dice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Manuele D'Ambrosio
 */

class TestTripleDiceImpl {
        private static final int MAX_VAL = 0;
        private static final int MID_VAL = 1;
        private static final int MIN_VAL = 2;
        private static final int ONE_ARMY = 1;
        private static final int TWO_ARMIES = 2;
        private static final int THREE_ARMIES = 3;
        private static final int DICE_ONE = 1;
        private static final int DICE_TWO = 2;
        private static final int DICE_THREE = 3;
        private static final int DICE_FOUR = 4;
        private static final int DICE_FIVE = 5;
        private static final int DICE_SIX = 6;

        @Test
        void testBuilder() {
                final TripleDice dice1 = new TripleDiceImpl(3);
                final TripleDice dice2 = new TripleDiceImpl(2);
                final TripleDice dice3 = new TripleDiceImpl(1);
                final TripleDice dice4 = new TripleDiceImpl(4);

                assertTrue(dice1.getResults().get(MAX_VAL) >= dice1.getResults().get(MID_VAL)
                                && dice1.getResults().get(MID_VAL) >= dice1.getResults().get(MIN_VAL));
                assertTrue(dice2.getResults().get(MAX_VAL) >= dice2.getResults().get(MID_VAL)
                                && dice2.getResults().get(MID_VAL) >= dice2.getResults().get(MIN_VAL));
                assertTrue(dice3.getResults().get(MAX_VAL) >= dice3.getResults().get(MID_VAL)
                                && dice3.getResults().get(MID_VAL) >= dice3.getResults().get(MIN_VAL));
                assertTrue(dice4.getResults().get(MAX_VAL) >= dice4.getResults().get(MID_VAL)
                                && dice4.getResults().get(MID_VAL) >= dice4.getResults().get(MIN_VAL));

        }

        @Test
        void testAttackerLostArmies() {
                final TripleDiceImpl attDice = new TripleDiceImpl(3);
                final TripleDiceImpl defDice = new TripleDiceImpl(3);

                attDice.setDummyResults(DICE_SIX, DICE_FOUR, DICE_TWO);
                defDice.setDummyResults(DICE_THREE, DICE_THREE, DICE_THREE);
                assertEquals(ONE_ARMY, TripleDice.attackerLostArmies(attDice, defDice));
                attDice.setDummyResults(DICE_SIX, DICE_SIX, DICE_SIX);
                defDice.setDummyResults(DICE_SIX, DICE_SIX, DICE_FIVE);
                assertEquals(TWO_ARMIES, TripleDice.attackerLostArmies(attDice, defDice));
                attDice.setDummyResults(DICE_ONE, DICE_ONE, DICE_ONE);
                defDice.setDummyResults(DICE_ONE, DICE_ONE, DICE_ONE);
                assertEquals(THREE_ARMIES, TripleDice.attackerLostArmies(attDice, defDice));
        }
}
