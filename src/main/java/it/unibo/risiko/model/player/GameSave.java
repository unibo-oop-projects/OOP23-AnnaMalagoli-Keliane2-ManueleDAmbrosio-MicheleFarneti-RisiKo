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

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.CardImpl;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * @author Manuele D'Ambrosio
 */

public final class GameSave implements ActualGame {
    private static final int OWNER = 2;
    private static final String DEFAULT_TYPE = "NaT";
    private static final String SEP = File.separator;
    private static final String NEW_LINE = System.lineSeparator();
    private static final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "save" + SEP + "save.txt";
    private List<Player> playerList;
    private List<Territory> territoryList;
    private String mapName;
    private int turnIndex;

    /**
     * This contructor saves the actual game.
     * 
     * @param playerList    - list of players.
     * @param territoryList - list of territories.
     * @param mapName       - name of the map.
     * @param turnIndex     - index of the actual player in the player list.
     */
    public GameSave(final List<Player> playerList, final List<Territory> territoryList, final String mapName,
            final int turnIndex) {
        this.playerList = List.copyOf(playerList);
        this.territoryList = List.copyOf(territoryList);
        this.mapName = mapName;
        this.turnIndex = turnIndex;
        saveWriter(PATH);
    }

    /**
     * This contructor build this class from the previous save.
     */
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

            // Initialize turn Index
            this.turnIndex = Integer.parseInt(reader.readLine());

            numberOfPlayers = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numberOfPlayers; i++) {
                row = Optional.ofNullable(reader.readLine());
                line = Arrays.asList(row.get().substring(0, row.get().length() - 1).split(" "));
                pList.add(new StdPlayer(line.get(0), Boolean.parseBoolean(line.get(1))));
                // Initializing player owned cards.
                reader.readLine();
                row = Optional.ofNullable(reader.readLine());
                while (!"$".equals(row.get())) {
                    pList.get(pList.size() - 1).addCard(new CardImpl(row.get(), DEFAULT_TYPE));
                    row = Optional.ofNullable(reader.readLine());
                }
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

    @Override
    public String getMapName() {
        return this.mapName;
    }

    @Override
    public List<Player> getPlayerList() {
        return List.copyOf(this.playerList);
    }

    @Override
    public List<Territory> getTerritoryList() {
        return List.copyOf(this.territoryList);
    }

    @Override
    public int getTurnIndex() {
        return this.turnIndex;
    }

    private boolean saveWriter(final String savePath) {
        try (OutputStreamWriter saveFile = new OutputStreamWriter(new FileOutputStream(savePath),
                StandardCharsets.UTF_8)) {
            // In the fisrt line there is the map name.
            saveFile.write(this.mapName + NEW_LINE);
            // In the second line there is the turn index;
            saveFile.write(this.turnIndex + NEW_LINE);
            // In the third line there is the number of players.
            saveFile.write(playerList.size() + NEW_LINE);
            // Players names.
            for (final Player player : playerList) {
                saveFile.write(player.getColorID() + " " + player.isAI() + " " + NEW_LINE);
                saveFile.write("$" + NEW_LINE);
                for (final Card card : player.getOwnedCards()) {
                    saveFile.write(card.getTerritoryName() + NEW_LINE);
                }
                saveFile.write("$" + NEW_LINE);
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

    @Override
    public void reassignCards(final Deck deck) {
        playerList.stream().forEach(p -> {
            final List<Card> newList = new ArrayList<>();
            p.getOwnedCards().stream().forEach(e -> newList.add(deck.getListCards().stream()
                    .filter(c -> c.getTerritoryName().equals(e.getTerritoryName())).findFirst().get()));
            p.setOwnedCards(newList);
        });
        adjustDeck(deck);
    }

    private void adjustDeck(final Deck deck) {
        final List<Card> drawnCards = new ArrayList<>();
        final List<Card> totalCards = deck.getListCards();
        for (final Player p : playerList) {
            drawnCards.addAll(p.getOwnedCards());
        }
        while (deck.getListCards().size() > 0) {
            deck.pullCard();
        }
        for (final Card card : totalCards) {
            if (!drawnCards.contains(card)) {
                deck.addCard(card);
            }
        }
    }

}
