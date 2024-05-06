package it.unibo.risiko.game;

public class GameManagerImpl implements GameManager{
    
    //private static final int MAX_PLAYERS_PER_GAME = 7;
    private static final int MAX_SAVEGAMES= 3;
    
    private void initializeNewGame(GameMap map){
        GameFactory gameGenerator = new GameFactoryImpl(map);
    }
    
}
