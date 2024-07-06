package it.unibo.risiko.model.dice;

import java.util.Random;

/**
 * A simple dice.
 * @author Manuele D'Ambrosio
 */

public class Dice {
    private static final int DICE_MINIMUM_VALUE = 1;
    private static final int DICE_MAXIMUM_VALUE = 6;
    
    private static Random random = new Random();

    protected Dice() {}

    /**
     * This method is used to get a random 
     * dice number.
     * 
     * @return a random number from 1 to 6.
     */
    public static int diceThrow() {
        return random.nextInt(DICE_MAXIMUM_VALUE - DICE_MINIMUM_VALUE + 1) + DICE_MINIMUM_VALUE;
    }
}
