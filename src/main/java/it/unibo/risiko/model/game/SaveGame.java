package it.unibo.risiko.model.game;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.map.Territories;
import it.unibo.risiko.model.player.Player;

public record SaveGame(List<Player> players,Deck deck ,Territories territories ,GameStatus gamestatus,Integer playerIndex,GameLoopManager gameLoopManager) {
    public static Optional<SaveGame> readFromFile(String saveGamesPath){
        Optional<SaveGame> save;
         try (JsonReader reader = new JsonReader(new FileReader(saveGamesPath,StandardCharsets.UTF_8))) {
            Gson gson = new Gson();
            save = gson.fromJson(reader, SaveGame.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            save = Optional.empty();
        }
        return save;
    }

    public boolean saveOnFile(String saveGamesFilePath){
        try (Writer writer = new FileWriter(saveGamesFilePath,StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(this, writer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
