package it.unibo.risiko.view.gameview;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.view.gameview.components.BackgroundImagePanel;
import it.unibo.risiko.view.gameview.components.ColoredImageButton;
import it.unibo.risiko.view.gameview.components.CustomButton;
import it.unibo.risiko.view.gameview.components.GradientPanel;
import it.unibo.risiko.view.gameview.components.LoggerView;
import it.unibo.risiko.view.gameview.components.Position;
import it.unibo.risiko.view.gameview.components.StandardTextField;
import it.unibo.risiko.view.gameview.components.TerritoryPlaceHolder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementattion of GameView interface.
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 * @author Anna Malagoli
 * @author Keliane Nana
 */

public final class GameViewImpl implements GameView {

    private static final int MAX_COLOR_VALUE = 255;

    private static final String FONT_NAME = "Arial";

    private static final int ARMIES_COUNT_THICKNESS = 3;

    private static final Integer WINNER_FONT_SIZE = 30;

    private static final String FILE_SEPARATOR = File.separator;

    private static final Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private static final Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;

    private static final Integer MAP_GRADIENT_LEVEL = 1;
    private static final Integer ATTACK_BAR_GRADIENT_LEVEL = 7;
    private static final Integer TURNS_BAR_GRADIENT_LEVEL = 1;

    private static final int MAP_LAYER = 1;
    private static final int TANK_LAYER = 2;
    private static final int TURN_ICON_LAYER = 3;
    private static final int TURNSBAR_LAYER = 2;
    private static final int BACKGROUND_LAYER = 0;
    private static final Integer TOP_LAYER = 5;

    private static final Color ATTACK_BAR_BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color ATTACK_BAR_FOREGROUND_COLOR = new Color(255, 204, 0);

    private static final Integer TANKS_WIDTH = 45;
    private static final Integer TANKS_HEIGTH = 45;

    private final Integer turnIconWidth;
    private final Integer turnIconHeight;
    private static final Integer TURNBAR_START_X = 10;
    private static final Integer TURNBAR_START_Y = 10;

    private static final Integer COUNTRYBAR_START_Y = 80;

    private static final Integer ATTACKBAR_BUTTONS_WIDTH = 150;
    private static final Integer ATTACKBAR_BUTTONS_HEIGHT = 50;
    private static final Integer ATTACKBAR_BUTTONS_DISTANCE = 20;

    private static final Integer TURN_TANK_WIDTH = 100;
    private static final Integer TURN_TANK_HEIGHT = 100;
    private static final Integer TURN_TANK_LAYER = 5;

    private static final int ARMIES_LABEL_HEIGHT = 17;
    private static final int ARMIES_LABEL_WIDTH = 17;
    private static final int ARMIES_LABEL_FONT_SIZE = 14;

    private static final int PLAYER_ARMIES_LABEL_HEIGHT = 23;
    private static final int PLAYER_ARMIES_LABEL_WIDTH = 23;
    private static final int PLAYER_ARMIES_LABEL_FONT_SIZE = 20;

    private final Integer frameWidth;
    private final Integer frameHeigth;
    private String mapName;

    private final Map<String, Position> tanksCoordinates = new HashMap<>();
    private final Map<String, TerritoryPlaceHolder> tanksMap = new HashMap<>();
    private final Map<String, ColoredImageButton> iconsMap = new HashMap<>();
    @SuppressFBWarnings(value = "EI2", justification = "GameViewObserver is an inteface intentionally mutable and safe to store.")
    private final GameViewObserver gameViewObserver;
    private final JFrame mainFrame = new JFrame();
    private final JLayeredPane baseLayoutPane = new JLayeredPane();
    private final JLayeredPane mapLayoutPane = new JLayeredPane();
    private final JLayeredPane attackBarLayoutPane = new JLayeredPane();
    private final JPanel gamePanel = new JPanel();
    private JPanel mapPanel;
    private final JLabel playerArmiesLabel = new JLabel();
    private JTextArea countryBarPanel;
    private JTextArea gameStatusPanel;
    private AttackPanel attackPanel;
    private JPanel moveArmiesPanel;
    private TablePanel tablePanel;
    private final JTextField targetTextField = new StandardTextField("Target");
    private final JPanel logPanel = new JPanel();
    private final JPanel territoriesTablePanel = new JPanel();
    private JPanel choiceCardsPanel;
    private LoggerView log;

    private ColoredImageButton turnTank;
    private GradientPanel attackBarBackgroundPanel;

    private final String resourcesLocator;

    private JButton skipButton;
    private JButton attackButton;
    private JButton moveArmiesButton;

    private int mapWidth;
    private int mapHeight;

    /**
     * Initializes the GUI for the game by creating the frame.
     * 
     * @param frameWidth
     * @param frameHeight
     */
    public GameViewImpl(final Integer frameWidth, final Integer frameHeight, final String resourcesLocator,
            final GameViewObserver observer) {
        this.gameViewObserver = observer;
        this.frameWidth = frameWidth;
        this.frameHeigth = frameHeight;
        this.resourcesLocator = resourcesLocator;
        turnIconWidth = frameWidth / 20;
        turnIconHeight = frameWidth / 20;

        mainFrame.setResizable(false);
        mainFrame.setSize(new Dimension(frameWidth, frameHeigth));
        mainFrame.setTitle("Risiko!");
        readImage(createPath(resourcesLocator, List.of("icon.png"))).ifPresent(image -> mainFrame.setIconImage(image));
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function used to create paths withou having to manually specify the file
     * separator every time
     * 
     * @return A path for a given resource
     * @author Michele Farneti
     * @param resourcePackage the path leading to the resources folder
     * @param directories     The pattern leading to the desired resource
     */
    private String createPath(final String resourcePackage, final List<String> directories) {
        return resourcePackage + directories.stream().map(d -> FILE_SEPARATOR + d).collect(Collectors.joining());
    }

    @Override
    public void showGameWindow(final String mapName, final Integer nPlayers) {
        this.mapName = mapName;
        mainFrame.getContentPane().removeAll();
        mainFrame.add(baseLayoutPane, BorderLayout.CENTER);

        // GamePanel and Basic layout initialization
        baseLayoutPane.setBounds(0, 0, frameWidth, frameHeigth);
        baseLayoutPane.add(gamePanel, 0, 0);
        gamePanel.setBounds(0, 0, (int) (mainFrame.getWidth() * GAME_PANEL_WIDTH_PERCENTAGE), mainFrame.getHeight());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Painting of the map
        mapLayoutPane.setBounds(0, 0, (int) gamePanel.getWidth(),
                (int) (gamePanel.getHeight() * MAP_PANEL_HEIGHT_PERCENTAGE));
        mapLayoutPane.setPreferredSize(new Dimension((int) (gamePanel.getWidth()),
                (int) (gamePanel.getHeight() * MAP_PANEL_HEIGHT_PERCENTAGE)));
        paintMap(createPath(resourcesLocator, List.of("maps", mapName, mapName + ".png")));
        mapWidth = mapLayoutPane.getSize().width;
        mapHeight = mapLayoutPane.getSize().height;

        // Map background setting
        final GradientPanel mapBackgroundPanel = new GradientPanel(Color.GRAY, Color.WHITE, MAP_GRADIENT_LEVEL);
        mapBackgroundPanel.setBounds(0, 0, mapLayoutPane.getWidth(), mapLayoutPane.getHeight());
        mapBackgroundPanel.setOpaque(true);
        setLayerdPaneBackground(mapLayoutPane, mapBackgroundPanel);

        logPanel.setBounds(gamePanel.getWidth(), 0, mainFrame.getWidth() - gamePanel.getWidth(),
                mainFrame.getHeight() / 2);
        logPanel.setBackground(ATTACK_BAR_BACKGROUND_COLOR);
        baseLayoutPane.add(logPanel, MAP_LAYER, 0);

        territoriesTablePanel.setBounds(gamePanel.getWidth(), mainFrame.getHeight() / 2,
                mainFrame.getWidth() - gamePanel.getWidth(), mainFrame.getHeight() / 2);
        territoriesTablePanel.setBackground(ATTACK_BAR_FOREGROUND_COLOR);
        baseLayoutPane.add(territoriesTablePanel, MAP_LAYER, 0);
        setupAttackBar(nPlayers);
    }

    /**
     * 
     * Private function used to deactivate gameView's buttons, used whenever the
     * attack panel or movement panel pops up
     * so that the user can't mess around with the rest of the game.
     * 
     * @param enabled True if the buttons are going to get enabled, false if it's
     *                going to deactivate them
     * @author Michele Farneti
     */
    private void setGameViewButtonsEnabled(final Boolean enabled) {
        tanksMap.entrySet().forEach(e -> e.getValue().button().setEnabled(enabled));
        skipButton.setEnabled(enabled);
        attackButton.setEnabled(enabled);
        moveArmiesButton.setEnabled(enabled);
    }

    /**
     * Sets up the attackbar part of the view
     * 
     * @param nPlayers the bumber of players playint the game, used to setup an cion
     *                 bar of the right dimension
     * @author Michele Farneti
     */
    private void setupAttackBar(final int nPlayers) {
        // AttackBar initiialization
        attackBarLayoutPane
                .setPreferredSize(new Dimension((int) (gamePanel.getWidth()), (int) (gamePanel.getHeight() * 0.3)));
        gamePanel.add(attackBarLayoutPane);
        attackBarLayoutPane.setOpaque(true);

        // AttackBar backgorund setting
        attackBarBackgroundPanel = new GradientPanel(ATTACK_BAR_FOREGROUND_COLOR, ATTACK_BAR_BACKGROUND_COLOR,
                ATTACK_BAR_GRADIENT_LEVEL);
        attackBarBackgroundPanel.setBounds(0, 0, (int) attackBarLayoutPane.getPreferredSize().getWidth(),
                (int) attackBarLayoutPane.getPreferredSize().getHeight());
        attackBarBackgroundPanel.setOpaque(true);
        setLayerdPaneBackground(attackBarLayoutPane, attackBarBackgroundPanel);

        // Turns bar
        final JPanel turnsBarPanel = new GradientPanel(Color.WHITE, ATTACK_BAR_FOREGROUND_COLOR,
                TURNS_BAR_GRADIENT_LEVEL);
        turnsBarPanel.setBounds(TURNBAR_START_X, TURNBAR_START_Y, turnIconWidth * nPlayers, turnIconHeight);
        attackBarLayoutPane.add(turnsBarPanel, TURNSBAR_LAYER, 0);
        // Country bar
        countryBarPanel = new JTextArea();
        countryBarPanel.setBounds(TURNBAR_START_X, TURNBAR_START_Y + COUNTRYBAR_START_Y, gamePanel.getWidth() / 6,
                turnIconHeight / 2);
        attackBarLayoutPane.add(countryBarPanel, TURNSBAR_LAYER, 0);
        countryBarPanel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        countryBarPanel.setEditable(false);
        countryBarPanel.setLineWrap(true);

        attackButton = new CustomButton("ATTACK");
        attackButton.setBounds(gamePanel.getWidth() / 2 - ATTACKBAR_BUTTONS_WIDTH - ATTACKBAR_BUTTONS_DISTANCE, 100,
                ATTACKBAR_BUTTONS_WIDTH, ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(attackButton, 5, 0);
        attackButton.addActionListener(e -> gameViewObserver.setAttacking());
        attackButton.setEnabled(false);

        skipButton = new CustomButton("SKIP");
        skipButton.setBounds((int) (gamePanel.getWidth() * 0.8), 100, ATTACKBAR_BUTTONS_WIDTH,
                ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(skipButton, 5, 0);
        skipButton.addActionListener(e -> gameViewObserver.skipTurn());
        skipButton.setEnabled(false);

        moveArmiesButton = new CustomButton("MOVE");
        moveArmiesButton.setBounds(gamePanel.getWidth() / 2 + ATTACKBAR_BUTTONS_DISTANCE, 100, ATTACKBAR_BUTTONS_WIDTH,
                ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(moveArmiesButton, 5, 0);
        moveArmiesButton.addActionListener(e -> gameViewObserver.moveClicked());
        moveArmiesButton.setEnabled(false);

        turnTank = new ColoredImageButton(resourcesLocator + FILE_SEPARATOR,
                FILE_SEPARATOR + "tanks" + FILE_SEPARATOR + "tank_",
                gamePanel.getWidth() / 2 - (TURN_TANK_WIDTH) / 2, 0, TURN_TANK_WIDTH, TURN_TANK_HEIGHT);
        turnTank.setBorderPainted(false);
        turnTank.setEnabled(false);

        playerArmiesLabel.setBounds(
                turnTank.getBounds().x + TURN_TANK_WIDTH, turnTank.getBounds().y + TURN_TANK_HEIGHT / 3,
                PLAYER_ARMIES_LABEL_WIDTH, PLAYER_ARMIES_LABEL_HEIGHT);
        playerArmiesLabel.setFont(new Font(FONT_NAME, Font.BOLD, PLAYER_ARMIES_LABEL_FONT_SIZE));
        playerArmiesLabel.setText("20");
        playerArmiesLabel.setBackground(ATTACK_BAR_FOREGROUND_COLOR);
        playerArmiesLabel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        playerArmiesLabel.setOpaque(true);

        attackBarLayoutPane.add(turnTank, TURN_TANK_LAYER, 0);
        attackBarLayoutPane.add(playerArmiesLabel, TURN_TANK_LAYER + 1, 0);

        targetTextField.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        targetTextField.setBackground(ATTACK_BAR_FOREGROUND_COLOR);
        targetTextField.setBounds((int) (gamePanel.getWidth() * 0.75), (int) (attackBarLayoutPane.getHeight() * 0.2),
                (int) (gamePanel.getWidth() * 0.25), 50);
        targetTextField.setFont(new Font(FONT_NAME, Font.ITALIC, 13));
        attackBarLayoutPane.add(targetTextField, TURN_ICON_LAYER, 0);

        // Game Status Bar
        gameStatusPanel = new JTextArea();
        gameStatusPanel.setBounds((int) (gamePanel.getWidth() * 0.75),
                (int) targetTextField.getLocation().getY() + targetTextField.getHeight(), targetTextField.getWidth(),
                turnIconHeight / 2);
        attackBarLayoutPane.add(gameStatusPanel, TURNSBAR_LAYER, 0);
        gameStatusPanel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        gameStatusPanel.setEditable(false);
        gameStatusPanel.setLineWrap(true);
    }

    /**
     * Sets a Jpanel as a JlayerdPane background
     * 
     * @param layeredPane
     * @param background
     * @author Michele Farneti
     */
    private void setLayerdPaneBackground(final JLayeredPane layeredPane, final JPanel background) {
        setLayerdPaneLayer(layeredPane, background, BACKGROUND_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerde pane top level
     * 
     * @param layeredPane
     * @param overlay
     * @author Michele Farneti
     */
    private void setLayerdPaneOverlay(final JLayeredPane layeredPane, final JPanel overlay) {
        setLayerdPaneLayer(layeredPane, overlay, TOP_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerde given layer
     * 
     * @param layeredPane
     * @param jpanel
     * @author Michele Farneti
     */
    private void setLayerdPaneLayer(final JLayeredPane layeredPane, final JPanel jpanel, final Integer layer) {
        layeredPane.add(jpanel, layer, 0);
    }

    /**
     * This funcion manages whenever a tank rapresenting a tank gets clicked
     * 
     * @param territory The territory that got clicked
     * @author Michele Farneti
     */
    private void tankClicked(final String territory) {
        countryBarPanel.setText(territory.toUpperCase(Locale.ROOT));
        countryBarPanel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        gameViewObserver.territorySelected(territory);
    }

    /**
     * Sets the background of mapPanel as the mapImage, if the loading of the image
     * fails,
     * a pannel reporting the error appears
     * 
     * @param mapPath The path of the image of the selected map
     * @author Michele Farneti
     */
    private void paintMap(final String mapPath) {
        final Optional<Image> mapImage = readImage(mapPath);
        if (mapImage.isPresent()) {
            mapPanel = new BackgroundImagePanel(mapImage.get());
            mapPanel.setOpaque(false);
        } else {
            mapPanel = new JPanel();
            mapPanel.add(new JLabel("Failed to load Map image"));
        }
        mapPanel.setBounds(0, 0, mapLayoutPane.getWidth(), mapLayoutPane.getHeight());
        mapLayoutPane.add(mapPanel, MAP_LAYER, 0);
        gamePanel.add(mapLayoutPane);
    }

    @Override
    public void start() {
        this.mainFrame.setVisible(true);
    }

    /**
     * 
     * @param imagePath The image path
     * @return An empty optional it failed toread the image, An optional of the
     *         image otherwise.
     * @author Michele Farneti
     */
    private Optional<Image> readImage(final String imagePath) {
        try {
            final Image image = ImageIO.read(new File(imagePath));
            return Optional.of(image);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * 
     * @return The coordinates of a tank rapresenting a territory on the map,
     *         relatively to the map dimension.
     * @author Michele Farneti
     */
    private Position getRelativePoition(final Position position) {
        return new Position(Math.round(position.x() * (mapWidth / 1280f)),
                Math.round(position.y() * (mapHeight / 630f)));
    }

    @Override
    public void showTanks(final List<Territory> territories) {
        territories.stream().forEach(territory -> tanksMap.put(territory.getTerritoryName(), new TerritoryPlaceHolder(
                new ColoredImageButton(resourcesLocator + FILE_SEPARATOR,
                        FILE_SEPARATOR + "tanks" + FILE_SEPARATOR + "tank_"),
                new JLabel("0"))));

        readTanksCoordinates();
        for (final var tank : tanksMap.entrySet()) {
            final var relativePosition = getRelativePoition(tanksCoordinates.get(tank.getKey()));
            tank.getValue().button().setBounds(relativePosition.x(),
                    relativePosition.y(), TANKS_WIDTH, TANKS_HEIGTH);
            mapLayoutPane.add(tank.getValue().button(), TANK_LAYER, 0);
            tank.getValue().button().addActionListener(e -> tankClicked(tank.getKey()));
            tank.getValue().button().setBorderPainted(false);
            tank.getValue().button().setContentAreaFilled(false);

            tank.getValue().armiesCount().setBounds(
                    (int) tank.getValue().button().getBounds().getLocation().getX() + TANKS_WIDTH
                            - (ARMIES_LABEL_WIDTH / 2),
                    (int) tank.getValue().button().getBounds().getLocation().getY() + TANKS_HEIGTH
                            - ARMIES_LABEL_HEIGHT,
                    ARMIES_LABEL_WIDTH, ARMIES_LABEL_HEIGHT);
            tank.getValue().armiesCount().setBackground(Color.white);
            tank.getValue().armiesCount().setForeground(Color.black);
            tank.getValue().armiesCount().setOpaque(true);
            tank.getValue().armiesCount().setFont(new Font(FONT_NAME, Font.BOLD, ARMIES_LABEL_FONT_SIZE));
            mapLayoutPane.add(tank.getValue().button(), TANK_LAYER, 0);
            mapLayoutPane.add(tank.getValue().armiesCount(), TANK_LAYER, 0);
        }
    }

    /**
     * Reads from file the coordinates needed for displaying tanks in the right
     * position.
     * 
     * @author Michele Farneti
     */
    private void readTanksCoordinates() {
        final var filePath = createPath(resourcesLocator, List.of("maps", mapName, "coordinates.txt"));
        try (BufferedReader coordinateReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));) {
            coordinateReader.lines().map(s -> s.split(" ")).forEach(
                    t -> tanksCoordinates.put(t[0], new Position(Integer.parseInt(t[1]), Integer.parseInt(t[2]))));
        } catch (IOException e) {
            tanksCoordinates.put("error", new Position(0, 0));
        }
    }

    @Override
    public void showTurnIcon(final Player player, final int playerIndex) {
        if (player.isAI()) {
            iconsMap.put(
                    player.getColorID(),
                    new ColoredImageButton(resourcesLocator + FILE_SEPARATOR,
                            FILE_SEPARATOR + "aiplayers" + FILE_SEPARATOR + "aiplayer_",
                            computeIconStartingX(playerIndex), TURNBAR_START_Y, turnIconWidth, turnIconHeight));
        } else {
            iconsMap.put(player.getColorID(),
                    new ColoredImageButton(resourcesLocator + FILE_SEPARATOR,
                            FILE_SEPARATOR + "standardplayers" + FILE_SEPARATOR + "standardplayer_",
                            computeIconStartingX(playerIndex), TURNBAR_START_Y, turnIconWidth, turnIconHeight));
        }

        for (final var icon : iconsMap.entrySet()) {
            icon.getValue().setColor(icon.getKey());
            icon.getValue().setEnabled(false);
            icon.getValue().setBorder(
                    BorderFactory.createLineBorder(stringToColor(icon.getKey().toLowerCase(Locale.ROOT)),
                            ARMIES_COUNT_THICKNESS));
            attackBarLayoutPane.add(icon.getValue(), TURN_ICON_LAYER, 0);
        }
    }

    /**
     * Calculates the starting pixel for a turnIcon of a player at a given index
     * 
     * @param playerIndex
     * @author Michele Farneti
     */
    private int computeIconStartingX(final int playerIndex) {
        return playerIndex * turnIconWidth + TURNBAR_START_X;
    }

    @Override
    public void setCurrentPlayer(final Player player) {
        turnTank.setColor(player.getColorID());
        iconsMap.entrySet().stream()
                .forEach(e -> e.getValue()
                        .setBorderPainted(e.getKey().equals(player.getColorID())));
        attackBarBackgroundPanel.setTopColor(stringToColor(player.getColorID()));
        playerArmiesLabel.setText(String.valueOf(player.getArmiesToPlace()));
        showTarget(player.getTarget().showTargetDescription());
        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * Converts a player's colo_id into a Color object
     * 
     * @param color_id a player's color id
     * @return The curresponding color object
     * @author Michele Farneti
     */
    private Color stringToColor(final String color_id) {
        switch (color_id) {
            case "cyan":
                return new Color(0, MAX_COLOR_VALUE, 255);
            case "blue":
                return new Color(0, 0, 255);
            case "green":
                return new Color(0, 255, 0);
            case "red":
                return new Color(255, 0, 0);
            case "pink":
                return new Color(255, 0, 255);
            case "yellow":
                return new Color(255, 255, 0);
            default:
                return new Color(255, 255, 255);
        }
    }

    @Override
    public void showFightingTerritory(final String territory, final boolean isAttacker) {
        tanksMap.get(territory).button().setCustomBorder(isAttacker ? Color.RED : Color.BLUE);
        tanksMap.get(territory).button().setBorderPainted(true);
    }

    @Override
    public void redrawTank(final Territory territory) {
        tanksMap.get(territory.getTerritoryName()).button().setColor(territory.getPlayer());
        tanksMap.get(territory.getTerritoryName()).armiesCount().setText(String.valueOf(territory.getNumberOfArmies()));
    }

    @Override
    public void resetFightingTerritory(final String fightingTerritory) {
        tanksMap.get(fightingTerritory).button().setBorderPainted(false);
    }

    @Override
    public void gameOver(final String winnerColor) {
        var winnerPanel = new GradientPanel(Color.BLACK, stringToColor(winnerColor), MAP_GRADIENT_LEVEL);
        winnerPanel.setBackground(Color.RED);
        winnerPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        winnerPanel.setOpaque(true);

        var winnerWriting = new JTextField(winnerColor.toUpperCase(Locale.ROOT) + " player has won the Game!");
        winnerWriting.setFont(new Font(FONT_NAME, Font.BOLD, WINNER_FONT_SIZE));
        winnerWriting.setForeground(stringToColor(winnerColor));
        winnerWriting.setOpaque(false);
        winnerWriting.setBorder(BorderFactory.createEmptyBorder());
        winnerPanel.add(winnerWriting);
        setLayerdPaneOverlay(baseLayoutPane, winnerPanel);
    }

    @Override
    public void setAtt(List<Integer> attDice) {
        attackPanel.setAtt(attDice);
    }

    @Override
    public void setDef(List<Integer> defDice) {
        attackPanel.setDef(defDice);
    }

    @Override
    public void setAttackerLostArmies(int attackerLostArmies) {
        attackPanel.setAttackerLostArmies(attackerLostArmies);
    }

    @Override
    public void setDefenderLostArmies(int defenderLostArmies) {
        attackPanel.setDefenderLostArmies(defenderLostArmies);
    }

    @Override
    public void createAttack(String attacking, String defending, int attackingTerritoryArmies) {
        setGameViewButtonsEnabled(false);
        final int PANEL_WIDTH = frameWidth / 2;
        final int PANEL_HEIGHT = frameHeigth / 2;
        final int PANEL_LOCATION_X = frameWidth / 6;
        final int PANEL_LOCATION_Y = frameHeigth / 6;
        attackPanel = new AttackPanel(
                PANEL_HEIGHT,
                PANEL_WIDTH,
                attacking,
                defending,
                attackingTerritoryArmies,
                gameViewObserver);
        attackPanel.setLocation(PANEL_LOCATION_X, PANEL_LOCATION_Y);
        attackPanel.setVisible(true);
        setLayerdPaneOverlay(baseLayoutPane, attackPanel);
    }

    @Override
    public void closeAttackPanel() {
        setGameViewButtonsEnabled(true);
        attackPanel.setVisible(false);
    }

    @Override
    public void exitMoveArmiesPanel() {
        setGameViewButtonsEnabled(true);
        moveArmiesPanel.setVisible(false);
    }

    @Override
    public void drawDicePanels() {
        attackPanel.drawDicePanels();
    }

    @Override
    public void drawConquerPanel() {
        attackPanel.drawConquerPanel();
    }

    @Override
    public void showInitializationWindow(Map<String, Integer> mapNames) {
        var initializationPanel = new NewGameInitViewImpl(frameWidth, frameHeigth, mapNames,
                gameViewObserver);
        mainFrame.add(initializationPanel, BorderLayout.CENTER);
        mainFrame.validate();
    }

    /**
     * Method used to create a panel to move armies between two adjacent
     * territories.
     * After the panel creation it is set visible and added to the game frame.
     * 
     * @param listTerritories is the list of territories owned by the player
     */
    public void createMoveArmies(List<Territory> listTerritories) {
        final int LOCATION_FACTOR = 6;
        final int SIZE_FACTOR = 2;
        moveArmiesPanel = new JPanelMovementArmies(listTerritories, gameViewObserver);
        moveArmiesPanel.setBounds(frameWidth / LOCATION_FACTOR, frameHeigth / LOCATION_FACTOR,
                frameWidth / SIZE_FACTOR,
                frameHeigth / SIZE_FACTOR);
        moveArmiesPanel.setVisible(true);
        setGameViewButtonsEnabled(false);
        setLayerdPaneOverlay(baseLayoutPane, moveArmiesPanel);
    }

    /**
     * Updates the target text area
     * 
     * @param targetText
     */
    private void showTarget(String targetText) {
        targetTextField.setText(targetText);
    }

    /**
     * Method used to create the panel to play the three cards.
     * After the panel creation it is set visible and added to the game frame.
     * 
     * @param playerCards is the list of cards of the player
     */
    public void createChoiceCards(List<Card> playerCards) {
        final int LOCATION_FACTOR = 6;
        final int SIZE_FACTOR = 2;
        choiceCardsPanel = new JPanelChoice(playerCards, gameViewObserver);
        choiceCardsPanel.setBounds(frameWidth / LOCATION_FACTOR, frameHeigth / LOCATION_FACTOR,
                frameWidth / SIZE_FACTOR,
                frameHeigth / SIZE_FACTOR);
        choiceCardsPanel.setVisible(true);
        setLayerdPaneOverlay(baseLayoutPane, choiceCardsPanel);
    }

    @Override
    public void enableMovements(boolean enabled) {
        moveArmiesButton.setEnabled(enabled);
    }

    @Override
    public void enableSkip(boolean enabled) {
        skipButton.setEnabled(enabled);
    }

    @Override
    public void enableAttack(boolean enabled) {
        attackButton.setEnabled(enabled);
    }

    /**
     * @author Keliane Nana
     */
    @Override
    public void createLog(Register reg, List<Player> l) {
        log = new LoggerView(reg, l);
        log.setPreferredSize(new Dimension(mainFrame.getWidth() - gamePanel.getWidth(),
                mainFrame.getHeight() / 2));
        logPanel.add(log);
    }

    /**
     * @author Keliane Nana
     */
    @Override
    public void updateLog() {
        log.pressButtonAllEvent();
    }

    /**
     * Method used to create a panel that contains the table of the territories.
     * 
     * @param terr    is the list of territories
     * @param players is the list of players
     * 
     * @author Anna Malagoli
     */
    public void createTablePanel(List<Territory> terr, List<Player> players) {
        this.tablePanel = new TablePanel(terr, players);
        tablePanel.update();
        territoriesTablePanel.add(tablePanel);
    }

    @Override
    public void updateTablePanel() {
        this.tablePanel.update();
    }

    @Override
    public void exitCardsPanel() {
        setGameViewButtonsEnabled(true);
        choiceCardsPanel.setVisible(false);
    }

    @Override
    public void showStatus(GameStatus gameStatus, Long turnsCount) {
        String statusString = "[" + turnsCount + "] -";
        switch (gameStatus) {
            case ARMIES_PLACEMENT:
            case TERRITORY_OCCUPATION:
                statusString += "Click the map territory where you want to add armies";
                break;
            case READY_TO_ATTACK:
            case ATTACKING:
                statusString += "Feel free to attack!";
                break;
            case CARDS_MANAGING:
                statusString += "Cards Managing!";
                break;
            default:
                break;
        }
        gameStatusPanel.setText(statusString);
    }
}
