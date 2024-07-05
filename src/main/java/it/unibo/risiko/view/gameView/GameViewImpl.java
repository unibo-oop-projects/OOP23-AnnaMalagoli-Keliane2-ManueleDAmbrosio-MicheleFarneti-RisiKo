package it.unibo.risiko.view.gameView;

import it.unibo.risiko.view.InitialView.GameFrame;

public class GameViewImpl implements GameView{
    
    private GameViewObserver gameViewObserver;

    @Override
    public void setObserver(GameViewObserver gameController) {
        this.gameViewObserver = gameController;
    }

    @Override
    public void start() {
        new GameFrame();
    }
    
}
