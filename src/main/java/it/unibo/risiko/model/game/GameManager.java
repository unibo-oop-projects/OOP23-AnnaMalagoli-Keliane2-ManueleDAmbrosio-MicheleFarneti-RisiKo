package it.unibo.risiko.model.game;

import java.util.Optional;

/**
 * This interface manages all of the basic functions that are offered by the application 
 * at its start. It gives the opportunity to keep progress of multiple games during the use of the application
 * aswell as allowing to load informations of past games stored in a file
 * 
 * @author Michele Farneti
 */

public interface GameManager {

    /**
     * 
     * @return True if a currentGame is already set, false otherwise
     */
    boolean isThereCurrentGame();

    /**
     * 
     * @param game A new game to be set as current
     * @return True if the game was succesfuly set as a new currentGame, False if 
     * there is no more space to save the current game.
     */
    boolean AddNewCurrentGame(Game game);

    /**
     * @param game The game to delete
     */
    void deleteSavegame(Game game);

    /**
     * 
     * @return True if the game is succesfuly saved
     */
    boolean saveCurrentGame();

    /**
     * 
     * @return True if there is still space to save a game.
     */
    boolean isThereSpaceToSave();

    /**
     * 
     * @return The game currently being played
     */
    Optional<Game> getCurrentGame();

    /**
     * Saves all of the games saved in the gameManager as a Json file
     * @return True if the Games where saved correctly on file, false otherwise.
     */
    boolean saveGameOnFile(String saveGamesFilePath);
}
