package it.unibo.risiko.model.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.risiko.model.map.GameMapInitializer;

/**
 * Implmentation of @GameManager interface
 * 
 * @author Michele Farneti
 */
public class GameManagerImpl implements GameManager {

    private Optional<Game> currentGame;
    private final String resourcesPath;

    /**
     * Constructor for GameManagerImpl from a filepath, containing a json file with
     * the previously saved game. If the file doesn't exist, the current game is set
     * as empty.
     * 
     * @param saveGamesFilePath filePath of the savegame file.
     */
    public GameManagerImpl(String saveGamesPath, String resourcesPath) {
        try (JsonReader reader = new JsonReader(new FileReader(resourcesPath + saveGamesPath,StandardCharsets.UTF_8))) {
            Gson gson = new Gson();
            currentGame = gson.fromJson(reader, Game.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            currentGame = Optional.empty();
        }
        this.resourcesPath = resourcesPath;
    }

    @Override
    public boolean isThereCurrentGame() {
        return currentGame.isPresent();
    }

    @Override
    public void setCurrentGame(Game game) {
        currentGame = Optional.of(game);
    }

    @Override
    public Optional<Game> getCurrentGame() {
        return currentGame;
    }

    @Override
    public boolean saveGameOnFile(String saveGamesFilePath) {
        try (Writer writer = new FileWriter(saveGamesFilePath,StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(currentGame, writer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressFBWarnings
    public Map<String, Integer> getAvailableMaps() {
        Map<String, Integer> availableMaps = new HashMap<>();
        var mapsFoldersLocations = resourcesPath + "maps";
        try {
            for (Path p : Files.list(Path.of(mapsFoldersLocations)).collect(Collectors.toList())) {
                var key = Optional.ofNullable(p.getFileName().toString());
                var value = Optional.ofNullable(GameMapInitializer.getMaxPlayers(resourcesPath + "maps" + File.separator + p.getFileName().toString()));
                key.ifPresent(k -> value.ifPresent( v-> availableMaps.put(k,v)));
            }
        } catch (IOException e) {}
        return availableMaps;
    }

    @Override
    public String getResourcesPath() {
        return resourcesPath;
    }
}
