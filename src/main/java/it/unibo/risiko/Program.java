package it.unibo.risiko;

import it.unibo.risiko.controller.GameController;

/**
 * Class containing main.
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Anna Malagoli
 * @author Keliane Nana
 */

public final class Program {

    private Program() {
        //This constructor is intentionally private.
    }

    /**
     * Main.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        new GameController();
    }
}
