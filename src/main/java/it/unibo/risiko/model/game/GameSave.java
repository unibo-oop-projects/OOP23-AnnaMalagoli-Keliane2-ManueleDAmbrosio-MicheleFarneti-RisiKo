package it.unibo.risiko.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.player.PlayerFactory;
import it.unibo.risiko.model.player.SimplePlayerFactory;

public final class GameSave {
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
        try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(PATH));
                BufferedReader reader = new BufferedReader(streamReader);) {
            final PlayerFactory pFactory = new SimplePlayerFactory();
            String map;
            int numberOfPlayers;
            List<String> line;
            List<Player> pList = new ArrayList<>();
            List<Territory> tList = new ArrayList<>();
            Territory newTerritory;
            String row;

            map = reader.readLine();
            numberOfPlayers = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numberOfPlayers; i++) {
                row = reader.readLine();
                line = Arrays.asList(row.substring(0, row.length() - 1).split(" "));
                if ("true".equals(line.get(1))) {
                    pList.add(pFactory.createAIPlayer());
                } else {
                    pList.add(pFactory.createStandardPlayer());
                }
            }
            row = reader.readLine();
            while (row.length() > 1) {
                line = Arrays.asList(row.substring(0, row.length() - 1).split(" "));
                newTerritory = new TerritoryImpl(line.get(0), map, List.of());
                newTerritory.addArmies(Integer.parseInt(line.get(1)));
                tList.add(newTerritory);
                row = reader.readLine();
            }
            //Initializing game save.
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
        try (OutputStreamWriter saveFile = new OutputStreamWriter(new FileOutputStream(savePath))) {
            // In the fisrt line there is the map name.
            saveFile.write(this.mapName + NEW_LINE);
            // In the second line there is the number of players.
            saveFile.write(playerList.size() + NEW_LINE);
            // Players names.
            for (final Player player : playerList) {
                saveFile.write(player.getColorID() + player.isAI() + NEW_LINE);
            }
            // Territories with armies and owners.
            for (final Territory t : territoryList) {
                saveFile.write(t.getTerritoryName() + t.getNumberOfArmies() + t.getPlayer() + NEW_LINE);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
