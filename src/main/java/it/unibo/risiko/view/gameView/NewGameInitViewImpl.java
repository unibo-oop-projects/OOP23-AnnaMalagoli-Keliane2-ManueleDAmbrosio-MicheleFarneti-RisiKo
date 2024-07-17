package it.unibo.risiko.view.gameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.risiko.view.gameView.gameViewComponents.ContinuePanel;
import it.unibo.risiko.view.gameView.gameViewComponents.DefaultButton;
import it.unibo.risiko.view.gameView.gameViewComponents.StandardTextField;

/**
 * Panel used to initialize a new game.
 * 
 * @author Manuele D'Ambrosio
 */

public class NewGameInitViewImpl extends JPanel {
    private static final int NO_PLAYERS = 0;
    private static final int MIN_PLAYERS = 2;
    private static final int FIRST_MAP_INDEX = 0;
    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color MAP_TEXT_COLOR = new Color(54, 8, 14);
    private int width;
    private int height;
    private boolean startable;
    private int humanPlayers;
    private int aiPlayers;
    private int totalPlayers;
    private int maxPlayers;
    private int mapIndex;
    private String mapName;
    private List<String> mapList = new ArrayList<>();
    private List<Integer> maxPlayersList = new ArrayList<>();
    private GameViewObserver observer;

    public NewGameInitViewImpl(final int width, final int height, final Map<String, Integer> maps,
            GameViewObserver observer) {
        this.width = width;
        this.height = height;
        this.observer = observer;
        this.mapIndex = FIRST_MAP_INDEX;
        this.startable = false;
        resetPlayers();

        this.mapList = maps.keySet().stream().collect(Collectors.toList());
        for (int i = FIRST_MAP_INDEX; i < mapList.size(); i++) {
            this.maxPlayersList.add(maps.get(mapList.get(i)));
        }
        mapName = mapList.get(mapIndex);
        updateMaxPlayers();

        this.setBackground(BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());

        this.add(titlePanel(), BorderLayout.NORTH);
        this.add(centralPanel(), BorderLayout.CENTER);
        this.add(new ContinuePanel("START", width, e -> {
            if (startable && humanPlayers != NO_PLAYERS) {
                observer.startNewGame(mapName, humanPlayers, aiPlayers);
            }
        }), BorderLayout.SOUTH);
        this.setVisible(true);

    }

    private void redrawPlayerPanels() {
        this.removeAll();
        this.add(titlePanel(), BorderLayout.NORTH);
        this.add(centralPanel(), BorderLayout.CENTER);
        this.add(new ContinuePanel("START", width, e -> {
            if (startable && humanPlayers != NO_PLAYERS) {
                observer.startNewGame(mapName, humanPlayers, aiPlayers);
            }
        }), BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }

    private void updateTotalPlayers() {
        this.totalPlayers = humanPlayers + aiPlayers;
    }

    private void resetPlayers() {
        this.humanPlayers = NO_PLAYERS;
        this.aiPlayers = NO_PLAYERS;
        updateTotalPlayers();
        setStartable(false);
    }

    private void increasePlayerNumber(final boolean isHuman) {
        if (isHuman) {
            humanPlayers++;
        } else {
            aiPlayers++;
        }
        updateTotalPlayers();
    }

    private void decreasePlayerNumber(final boolean isHuman) {
        if (isHuman) {
            humanPlayers--;
        } else {
            aiPlayers--;
        }
        updateTotalPlayers();
    }

    private int getPlayerType(final boolean isHuman) {
        if (isHuman) {
            return humanPlayers;
        } else {
            return aiPlayers;
        }
    }

    private void updateMaxPlayers() {
        this.maxPlayers = maxPlayersList.get(mapIndex);
    }

    private void setStartable(boolean isStartable) {
        this.startable = isStartable;
    }

    private JTextField titlePanel() {
        final int TITLE_HEIGHT = height / 6;
        JTextField title = new StandardTextField("NEW GAME");
        title.setPreferredSize(new Dimension(width, TITLE_HEIGHT));
        title.setFont(new Font("Arial", Font.BOLD, 26));

        return title;
    }

    private JPanel centralPanel() {
        final int CENTRAL_PANEL_HEIGHT = width / 3;
        final int ROWS = 3;
        final int COLS = 1;
        JPanel central = new JPanel();
        central.setLayout(new GridLayout(ROWS, COLS));
        central.setPreferredSize(new Dimension(width, CENTRAL_PANEL_HEIGHT));
        central.add(mapSelectorPanel(mapList));
        central.add(playersSelectorPanel("PLAYERS: ", true));
        central.add(playersSelectorPanel("BOTS: ", false));

        return central;
    }

    private JPanel mapSelectorPanel(final List<String> mapList) {
        final int TEXT_WIDTH = width / 6;
        final int HEIGHT_SIZE = 4;
        final Font selectorFont = new Font("Arial", Font.BOLD, 26);
        int numberOfMaps = mapList.size();
        JPanel mapSelectorPanel = new JPanel();
        JTextField mapSelectionText = new StandardTextField("SELECT THE MAP YOU WANT TO PLAY: ");
        JTextField nameTextField = new StandardTextField(mapName.toUpperCase(Locale.ROOT));
        JButton nextName = new DefaultButton("NEXT");

        mapSelectionText.setBackground(BACKGROUND_COLOR);
        mapSelectionText.setFont(selectorFont);
        nameTextField.setForeground(MAP_TEXT_COLOR);
        nameTextField.setBackground(BACKGROUND_COLOR);
        nameTextField.setFont(selectorFont);
        nameTextField.setPreferredSize(new Dimension(TEXT_WIDTH, mapSelectionText.getPreferredSize().height));

        nextName.setPreferredSize(new Dimension(TEXT_WIDTH, mapSelectionText.getPreferredSize().height));
        nextName.addActionListener(e -> {
            mapIndex++;
            if (mapIndex >= numberOfMaps) {
                mapIndex = FIRST_MAP_INDEX;
            }
            resetPlayers();
            updateMaxPlayers();
            mapName = mapList.get(mapIndex);
            nameTextField.setText(mapName.toUpperCase(Locale.ROOT));
            redrawPlayerPanels();
        });

        mapSelectorPanel.setLayout(new FlowLayout());
        mapSelectorPanel.setBackground(BACKGROUND_COLOR);
        mapSelectorPanel.setPreferredSize(new Dimension(width, height / HEIGHT_SIZE));
        mapSelectorPanel.add(mapSelectionText);
        mapSelectorPanel.add(nameTextField);
        mapSelectorPanel.add(nextName);

        return mapSelectorPanel;
    }

    private JPanel playersSelectorPanel(final String playerType, final boolean isHuman) {
        final int PLAYER_SELECTOR_HEIGHT = height / 5;
        JPanel playerSelectorPanel = new JPanel();
        JTextField typeText = new StandardTextField(playerType);
        JTextField value = new StandardTextField(Integer.toString(NO_PLAYERS));
        JButton increaser = new DefaultButton("+");
        JButton decreaser = new DefaultButton("-");

        decreaser.setEnabled(false);
        decreaser.addActionListener(e -> {
            if (getPlayerType(isHuman) > NO_PLAYERS) {
                decreasePlayerNumber(isHuman);
                value.setText(Integer.toString(getPlayerType(isHuman)));
            }
            if (getPlayerType(isHuman) <= NO_PLAYERS) {
                decreaser.setEnabled(false);
                setStartable(false);
            }
            increaser.setEnabled(true);
            if (totalPlayers <= maxPlayers && totalPlayers >= MIN_PLAYERS) {
                setStartable(true);
            }
        });
        increaser.addActionListener(e -> {
            if (totalPlayers < maxPlayers) {
                increasePlayerNumber(isHuman);
                value.setText(Integer.toString(getPlayerType(isHuman)));
            }
            if (totalPlayers >= maxPlayers) {
                increaser.setEnabled(false);
            }
            decreaser.setEnabled(true);
            if (totalPlayers <= maxPlayers && totalPlayers >= MIN_PLAYERS) {
                setStartable(true);
            }
        });

        typeText.setBackground(BACKGROUND_COLOR);
        value.setBackground(BACKGROUND_COLOR);
        playerSelectorPanel.setPreferredSize(new Dimension(width, PLAYER_SELECTOR_HEIGHT));
        playerSelectorPanel.setLayout(new FlowLayout());
        playerSelectorPanel.setBackground(BACKGROUND_COLOR);
        playerSelectorPanel.add(typeText);
        playerSelectorPanel.add(decreaser);
        playerSelectorPanel.add(value);
        playerSelectorPanel.add(increaser);

        return playerSelectorPanel;
    }
}