package it.unibo.risiko.view.gameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.risiko.view.gameView.gameViewComponents.ContinuePanel;
import it.unibo.risiko.view.gameView.gameViewComponents.DefaultButton;
import it.unibo.risiko.view.gameView.gameViewComponents.StandardTextField;

/**
 * Panel used to initialize a new game. 
 * @author Manuele D'Ambrosio
 */

public class NewGameInitViewImpl extends JPanel {
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 6;
    private static final int FIRST_MAP_INDEX = 0;
    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color MAP_TEXT_COLOR = new Color(54, 80, 140);
    private int width;
    private int height;
    private int humanPlayers;
    private int aiPlayers;
    private int totalPlayers;
    private int mapIndex;
    private String mapName;
    private GameViewObserver observer;

    public NewGameInitViewImpl(final int width, final int height, final List<String> mapList, GameViewObserver observer) {
        final int ROWS = 5;
        final int COLS = 1;
        this.observer = observer;
        this.width = width;
        this.height = height;
        this.humanPlayers = MIN_PLAYERS;
        this.aiPlayers = MIN_PLAYERS;
        this.totalPlayers = humanPlayers + aiPlayers;
        this.mapIndex = FIRST_MAP_INDEX;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(BACKGROUND_COLOR);
        this.setLayout(new GridLayout(ROWS, COLS));

        this.add(new StandardTextField("NEW GAME"));
        this.add(mapSelectorPanel(mapList));
        this.add(playersSelectorPanel("PLAYERS: "));
        this.add(playersSelectorPanel("BOTS: "));
        this.add(new ContinuePanel("START", width, e -> startGame()));

        this.setVisible(true);

    }

    private void startGame() {
        if (totalPlayers <= MAX_PLAYERS) {
            observer.startNewGame(mapName, humanPlayers, aiPlayers);
            this.setVisible(false);
        }
    }

    private JPanel mapSelectorPanel(final List<String> mapList) {
        final int HEIGHT_SIZE = 3;
        final int WIDTH_SIZE = 4;
        int numberOfMaps = mapList.size();
        JPanel mapSelectorPanel = new JPanel();
        JTextField mapSelectionText = new StandardTextField("SELECT THE MAP YOU WANT TO PLAY: ");
        JTextField nameTextField = new StandardTextField(mapName);
        JButton nextName = new DefaultButton("NEXT");

        mapSelectionText.setBackground(BACKGROUND_COLOR);
        nameTextField.setForeground(MAP_TEXT_COLOR);
        nameTextField.setBackground(BACKGROUND_COLOR);
        nameTextField.setText(mapList.get(mapIndex));
        nameTextField.setPreferredSize(new Dimension(width / WIDTH_SIZE, mapSelectionText.getPreferredSize().height));

        nextName.setPreferredSize(new Dimension(nameTextField.getPreferredSize()));
        nextName.addActionListener(e -> {
            mapIndex++;
            if (mapIndex >= numberOfMaps) {
                mapIndex = FIRST_MAP_INDEX;
            }
            mapName = mapList.get(mapIndex);
            nameTextField.setText(mapList.get(mapIndex));
        });

        mapSelectorPanel.setLayout(new FlowLayout());
        mapSelectorPanel.setBackground(BACKGROUND_COLOR);
        mapSelectorPanel.setPreferredSize(new Dimension(width, height / HEIGHT_SIZE));
        mapSelectorPanel.add(mapSelectionText);
        mapSelectorPanel.add(nameTextField);
        mapSelectorPanel.add(nextName);

        return mapSelectorPanel;
    }

    private void updateTotalPlayers() {
        this.totalPlayers = humanPlayers + aiPlayers;
    }

    private JPanel playersSelectorPanel(final String playerType) {
        final int MIN_PLAYERS = 1;
        JPanel playerSelectorPanel = new JPanel();
        JTextField typeText = new StandardTextField(playerType);
        JTextField value = new StandardTextField(Integer.toString(MIN_PLAYERS));
        JButton increaser = new DefaultButton("+");
        JButton decreaser = new DefaultButton("-");

        decreaser.setEnabled(false);
        if (playerType.equals("PLAYERS: ")) {
            decreaser.addActionListener(e -> {
                humanPlayers--;
                updateTotalPlayers();
                if (humanPlayers == MIN_PLAYERS) {
                    decreaser.setEnabled(false);
                }
                increaser.setEnabled(true);
                value.setText(Integer.toString(humanPlayers));
            });
            increaser.addActionListener(e -> {
                if (totalPlayers < MAX_PLAYERS) {
                    humanPlayers++;
                    updateTotalPlayers();
                    if (totalPlayers >= MAX_PLAYERS) {
                        increaser.setEnabled(false);
                    }
                    decreaser.setEnabled(true);
                    value.setText(Integer.toString(humanPlayers));
                } else {
                    increaser.setEnabled(false);
                }
            });
        }
        if (playerType.equals("BOTS: ")) {
            decreaser.addActionListener(e -> {
                aiPlayers--;
                updateTotalPlayers();
                if (aiPlayers == MIN_PLAYERS) {
                    decreaser.setEnabled(false);
                }
                increaser.setEnabled(true);
                value.setText(Integer.toString(aiPlayers));
            });
            increaser.addActionListener(e -> {
                if (totalPlayers < MAX_PLAYERS) {
                    aiPlayers++;
                    updateTotalPlayers();
                    if (totalPlayers >= MAX_PLAYERS) {
                        increaser.setEnabled(false);
                    }
                    decreaser.setEnabled(true);
                    value.setText(Integer.toString(aiPlayers));
                } else {
                    increaser.setEnabled(false);
                }
            });
        }
        typeText.setBackground(BACKGROUND_COLOR);
        value.setBackground(BACKGROUND_COLOR);
        playerSelectorPanel.setLayout(new FlowLayout());
        playerSelectorPanel.setBackground(BACKGROUND_COLOR);
        playerSelectorPanel.add(typeText);
        playerSelectorPanel.add(decreaser);
        playerSelectorPanel.add(value);
        playerSelectorPanel.add(increaser);

        return playerSelectorPanel;
    }
}
