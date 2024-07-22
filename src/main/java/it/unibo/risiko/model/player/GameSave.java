package it.unibo.risiko.model.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * @author Manuele D'Ambrosio
 */

public final class GameSave {
    private static final int OWNER = 2;
    private static final String SEP = File.separator;
    private static final String NEW_LINE = System.lineSeparator();
    private static final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "save" + SEP + "save.txt";
    private List<Player> playerList;
    private List<Territory> territoryList;
    private String mapName;

    public GameSave(final List<Player> playerList, final List<Territory> territoryList, final String mapName) {
        this.playerList = List.copyOf(playerList);
        this.territoryList = List.copyOf(territoryList);
        this.mapName = mapName;
        saveWriter(PATH);
    }

    public GameSave() {
        try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);) {
            String map;
            int numberOfPlayers;
            List<String> line;
            final List<Player> pList = new ArrayList<>();
            final List<Territory> tList = new ArrayList<>();
            Territory newTerritory;
            Optional<String> row;

            map = reader.readLine();
            numberOfPlayers = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numberOfPlayers; i++) {
                row = Optional.ofNullable(reader.readLine());
                line = Arrays.asList(row.get().substring(0, row.get().length() - 1).split(" "));
                pList.add(new StdPlayer(line.get(0), Boolean.parseBoolean(line.get(1))));
            }
            row = Optional.ofNullable(reader.readLine());
            while (row.isPresent()) {
                line = Arrays.asList(row.get().substring(0, row.get().length() - 1).split(" "));
                newTerritory = new TerritoryImpl(line.get(0), map, List.of());
                newTerritory.addArmies(Integer.parseInt(line.get(1)));
                newTerritory.setPlayer(line.get(OWNER));
                tList.add(newTerritory);
                row = Optional.ofNullable(reader.readLine());
            }
            // Initializing game save.
            this.mapName = map;
            this.playerList = pList;
            this.territoryList = tList;

        } catch (IOException e) {
            this.playerList = List.of();
            this.territoryList = List.of();
            this.mapName = "X";
        }
    }

    public String getMapName() {
        return this.mapName;
    }

    public List<Player> getPlayerList() {
        return List.copyOf(this.playerList);
    }

    public List<Territory> getTerritoryList() {
        return List.copyOf(this.territoryList);
    }

    private boolean saveWriter(final String savePath) {
        try (OutputStreamWriter saveFile = new OutputStreamWriter(new FileOutputStream(savePath), StandardCharsets.UTF_8)) {
            // In the fisrt line there is the map name.
            saveFile.write(this.mapName + NEW_LINE);
            // In the second line there is the number of players.
            saveFile.write(playerList.size() + NEW_LINE);
            // Players names.
            for (final Player player : playerList) {
                saveFile.write(player.getColorID() + " " + player.isAI() + " " + NEW_LINE);
            }
            // Territories with armies and owners.
            for (final Territory t : territoryList) {
                saveFile.write(
                        t.getTerritoryName() + " " + t.getNumberOfArmies() + " " + t.getPlayer() + " " + NEW_LINE);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
