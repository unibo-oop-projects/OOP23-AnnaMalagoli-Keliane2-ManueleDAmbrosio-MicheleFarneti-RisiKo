package it.unibo.risiko;

import it.unibo.risiko.controller.GameController;

/**
 * The main class of the game.
 */
public final class Program {

    private Program() {
        // This constructor is intentionally private.
    }

    /**
     * The main for starting the game.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        new GameController();
    }
}
