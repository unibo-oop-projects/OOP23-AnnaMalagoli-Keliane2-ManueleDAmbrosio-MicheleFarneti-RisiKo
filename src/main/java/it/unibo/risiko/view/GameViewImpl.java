package it.unibo.risiko.view;

public class GameViewImpl implements GameView{
    
    private GameViewObserver gameViewObserver;

    @Override
    public void setObserver(GameViewObserver gameController) {
        this.gameViewObserver = gameController;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
    
}
