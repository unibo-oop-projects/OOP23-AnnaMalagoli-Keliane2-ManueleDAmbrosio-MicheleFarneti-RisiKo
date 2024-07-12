package it.unibo.risiko.model.game;

import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the gameManager, whose task is to handle games. Saving or
 * reading them from files if needed.
 * 
 * @author Michele Farneti
 */

public interface GameManager {

    /**
     * 
     * @return True if a currentGame is present, flase otherwise
     */
    boolean isThereCurrentGame();

    /**
     * Sets a new game as the gameManager current game
     * 
     * @param Game
     */
    void setCurrentGame(Game game);

    /**
     * 
     * @return The game currently being played
     */
    Optional<Game> getCurrentGame();

    /**
     * Saves the current game as a Json file, eventually overriding old savegames
     * 
     * @return True if the Games where saved correctly on file, false otherwise.
     */
    boolean saveGameOnFile(String saveGamesFilePath);

    /**
     * 
     * @return The list of map names currently avilable to play and the maximum
     *         number of players for each of them
     */
    Map<String, Integer> getAvailableMaps();

    /**
     * 
     * @return The string rappresenting the path to reach the game resources.
     */
    String getResourcesPath();
}
