package it.unibo.risiko.game;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Michele Farneti
 * Implementation of the gameManager
 */
public class GameManagerImpl implements GameManager{
    
    private static final int MAX_SAVEGAMES= 3;

    private Optional<Game> currentGame;
    private final Set<Game> saveGames = new HashSet<Game>();
    
    @Override
    public boolean isThereCurrentGame() {
        return currentGame.isPresent();
    }

    @Override
    public boolean AddNewCurrentGame(Game game){
        if (isThereCurrentGame()) {
            if(!isThereSpaceToSave()){
                return false;
            }else{
                saveGames.add(currentGame.get());
                currentGame = Optional.of(game);
            }
        };
        currentGame = Optional.of(game);
        return true;
    }

    @Override
    public void deleteSavegame(Game game) {
        saveGames.remove(game);
    }

    @Override
    public boolean saveCurrentGame() {
        if(isThereSpaceToSave()){
            saveGames.add(currentGame.get());
            return true;
        };
        return false;
    }

    @Override
    public boolean isThereSpaceToSave() {
        return !(MAX_SAVEGAMES==saveGames.size());
    }
}
