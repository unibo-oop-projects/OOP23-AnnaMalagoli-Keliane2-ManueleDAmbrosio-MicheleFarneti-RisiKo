package it.unibo.risiko;

import it.unibo.risiko.controller.GameController;

public class Program {

    private Program() {
        //This constructor is intentionally private.
    }
    public static void main(final String[] args) {
        new GameController();
    }
}
