package it.unibo.risiko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.test;
import org.junit.jupiter.api.BeforeEach;


/**
 * @author Manuele D'Ambrosio
 */


 class TestTripleDiceImpl {
    private static final int MAX_VAL = 0;
    private static final int MID_VAL = 1;
    private static final int MIN_VAL = 2;

    @Test 
    void TestBuilderException() {
        boolean throwFlag = false;

        try {
            final Dice dice1 = new TripleDiceImpl();
        } catch(IllegalArgumentException e) {
            throwFlag = true;
        }

        assertTrue(throwFlag);
    }
 }