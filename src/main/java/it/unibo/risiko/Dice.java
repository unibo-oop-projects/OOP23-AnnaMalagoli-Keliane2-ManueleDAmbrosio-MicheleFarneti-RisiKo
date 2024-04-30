package it.unibo.risiko;

import java.util.Random;

/**
 * @author Manuele D'Ambrosio
 */

public class Dice {
    private static final int DICE_MINIMUM_VALUE = 1;
    private static final int DICE_MAXIMUM_VALUE = 6;
    
    private static Random random = new Random();

    public static int diceThrow() {
        return random.nextInt(DICE_MAXIMUM_VALUE - DICE_MINIMUM_VALUE + 1) + DICE_MINIMUM_VALUE;
    }
}
