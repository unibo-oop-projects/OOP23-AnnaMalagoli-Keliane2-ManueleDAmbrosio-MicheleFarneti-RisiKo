package it.unibo.risiko.view;

import it.unibo.risiko.controller.GameController;

public interface GameView {

    void setObserver(GameViewObserver gameController);

    void start();
    
}
