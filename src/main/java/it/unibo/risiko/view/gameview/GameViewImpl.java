package it.unibo.risiko.view.gameview;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.game.GameStatus;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.view.gameview.components.DefaultButton;
import it.unibo.risiko.view.gameview.components.LoggerView;
import it.unibo.risiko.view.gameview.components.Position;
import it.unibo.risiko.view.gameview.components.StandardTextField;
import it.unibo.risiko.view.gameview.components.mainpanel.BackgroundImagePanel;
import it.unibo.risiko.view.gameview.components.mainpanel.ColoredImageButton;
import it.unibo.risiko.view.gameview.components.mainpanel.CustomButton;
import it.unibo.risiko.view.gameview.components.mainpanel.GradientPanel;
import it.unibo.risiko.view.gameview.components.mainpanel.TerritoryPlaceHolder;
import it.unibo.risiko.view.gameview.components.mainpanel.TerritoryPlaceHolderFactory;
import it.unibo.risiko.view.gameview.components.mainpanel.TerritoryPlaceHolderFactoryImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    private static final float MAP_HEIGHT_SCALE = 630f;
    private static final float MAP_WIDTH_SCALE = 1280f;
    private static final int TARGET_FONT_SIZE = 13;
    private static final int TARGET_HEIGHT = 50;
    private static final double TARGET_WIDTH_PERCENTAGE = 0.25;
    private static final double TARGET_Y_PERCENTAGE = 0.2;
    private static final double TARGET_X_PERCENTAGE = 0.75;
    private static final double GAMESTATUS_WIDTH_PERCENTAGE = 0.75;
    private static final double SKIP_BUTTON_X_PERCENTAGE = 0.8;
    private static final int BUTTONS_LAYER = 5;
    private static final int ATTACK_BUTTON_Y_DISTANCE = 100;
    private static final int GAMEPANEL_WIDTH_DIVIDER = 6;
    private static final double ATTACK_BAR_HEIGHT_PERCENTAGE = 0.3;
    private static final int TURN_ICON_DIMENSION_DIVIDER = 20;
    private static final int MAX_COLOR_VALUE = 255;

    private static final String FONT_NAME = "Arial";

    private static final int ARMIES_COUNT_THICKNESS = 3;

    private static final Integer WINNER_FONT_SIZE = 30;

    private static final String FILE_SEPARATOR = File.separator;

    private static final Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private static final Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;

    private static final Integer MAP_GRADIENT_LEVEL = 2;
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

    private static final int PLAYER_ARMIES_LABEL_HEIGHT = 23;
    private static final int PLAYER_ARMIES_LABEL_WIDTH = 23;
    private static final int PLAYER_ARMIES_LABEL_FONT_SIZE = 20;

    private static final int ATTACK_PANEL_POSITIONING = 2;
    private static final int ATTACK_PANEL_LOCATION = 6;

    private final Integer frameWidth;
    private final Integer frameHeigth;
    private String mapName;

    private final Set<TerritoryPlaceHolder> tanks = new HashSet<>();
    private final Map<String, ColoredImageButton> iconsMap = new HashMap<>();
    @SuppressFBWarnings(value = "EI2", justification = "GameViewObserver is an inteface intentionally mutable and safe to store.")
    private final GameViewObserver gameViewObserver;
    private final JFrame mainFrame = new JFrame();
    private final JLayeredPane baseLayoutPane = new JLayeredPane();
    private final JLayeredPane mapLayoutPane = new JLayeredPane();
    private final JLayeredPane attackBarLayoutPane = new JLayeredPane();
    private final JPanel gamePanel = new JPanel();
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
    private JButton saveButton;

    private int mapWidth;
    private int mapHeight;

    /**
     * Initializes the GUI for the game by creating the frame of a given dimension.
     * 
     * @param frameWidth
     * @param frameHeight
     * @param resourcesLocator The string used for locating the game view resources.
     * @param observer         The observer for the game controller.
     */
    public GameViewImpl(final Integer frameWidth, final Integer frameHeight, final String resourcesLocator,
            final GameViewObserver observer) {
        this.gameViewObserver = observer;
        this.frameWidth = frameWidth;
        this.frameHeigth = frameHeight;
        this.resourcesLocator = resourcesLocator;
        turnIconWidth = frameWidth / TURN_ICON_DIMENSION_DIVIDER;
        turnIconHeight = frameWidth / TURN_ICON_DIMENSION_DIVIDER;

        mainFrame.setResizable(false);
        mainFrame.setSize(new Dimension(frameWidth, frameHeigth));
        mainFrame.setTitle("Risiko!");
        readImage(createPath(resourcesLocator, List.of("icon.png"))).ifPresent(image -> mainFrame.setIconImage(image));
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function used to create paths withou having to manually specify the file
     * separator every time.
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
        paintMap(resourcesLocator, FILE_SEPARATOR + "maps" + FILE_SEPARATOR + mapName + FILE_SEPARATOR + mapName);
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
        tanks.forEach(e -> e.setEnabled(enabled));
        skipButton.setEnabled(enabled);
        attackButton.setEnabled(enabled);
        moveArmiesButton.setEnabled(enabled);
    }

    /**
     * Sets up the attackbar part of the view.
     * 
     * @param nPlayers the bumber of players playint the game, used to setup an cion
     *                 bar of the right dimension
     * @author Michele Farneti
     */
    private void setupAttackBar(final int nPlayers) {
        // AttackBar initiialization
        attackBarLayoutPane
                .setPreferredSize(new Dimension((int) (gamePanel.getWidth()),
                        (int) (gamePanel.getHeight() * ATTACK_BAR_HEIGHT_PERCENTAGE)));
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
        countryBarPanel.setBounds(TURNBAR_START_X, TURNBAR_START_Y + COUNTRYBAR_START_Y,
                gamePanel.getWidth() / GAMEPANEL_WIDTH_DIVIDER,
                turnIconHeight / 2);
        attackBarLayoutPane.add(countryBarPanel, TURNSBAR_LAYER, 0);
        countryBarPanel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        countryBarPanel.setEditable(false);
        countryBarPanel.setLineWrap(true);

        attackButton = new CustomButton("ATTACK");
        attackButton.setBounds(gamePanel.getWidth() / 2 - ATTACKBAR_BUTTONS_WIDTH - ATTACKBAR_BUTTONS_DISTANCE,
                ATTACK_BUTTON_Y_DISTANCE,
                ATTACKBAR_BUTTONS_WIDTH, ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(attackButton, BUTTONS_LAYER, 0);
        attackButton.addActionListener(e -> gameViewObserver.setAttacking());
        attackButton.setEnabled(false);

        skipButton = new CustomButton("SKIP");
        skipButton.setBounds((int) (gamePanel.getWidth() * SKIP_BUTTON_X_PERCENTAGE), 100, ATTACKBAR_BUTTONS_WIDTH,
                ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(skipButton, BUTTONS_LAYER, 0);
        skipButton.addActionListener(e -> gameViewObserver.skipTurn());
        skipButton.setEnabled(false);

        saveButton = new DefaultButton("SAVE");
        saveButton.setBounds(TURNBAR_START_X, TURNBAR_START_Y * 2 + COUNTRYBAR_START_Y + countryBarPanel.getHeight(),
                ATTACKBAR_BUTTONS_WIDTH / ATTACK_PANEL_POSITIONING,
                ATTACKBAR_BUTTONS_HEIGHT / ATTACK_PANEL_POSITIONING);
        saveButton.addActionListener(e -> gameViewObserver.saveGame());
        attackBarLayoutPane.add(saveButton, BUTTONS_LAYER, 0);

        moveArmiesButton = new CustomButton("MOVE");
        moveArmiesButton.setBounds(gamePanel.getWidth() / 2 + ATTACKBAR_BUTTONS_DISTANCE, 100, ATTACKBAR_BUTTONS_WIDTH,
                ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(moveArmiesButton, BUTTONS_LAYER, 0);
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
        targetTextField.setBounds((int) (gamePanel.getWidth() * TARGET_X_PERCENTAGE),
                (int) (attackBarLayoutPane.getHeight() * TARGET_Y_PERCENTAGE),
                (int) (gamePanel.getWidth() * TARGET_WIDTH_PERCENTAGE), TARGET_HEIGHT);
        targetTextField.setFont(new Font(FONT_NAME, Font.ITALIC, TARGET_FONT_SIZE));
        attackBarLayoutPane.add(targetTextField, TURN_ICON_LAYER, 0);

        // Game Status Bar
        gameStatusPanel = new JTextArea();
        gameStatusPanel.setBounds((int) (gamePanel.getWidth() * GAMESTATUS_WIDTH_PERCENTAGE),
                (int) targetTextField.getLocation().getY() + targetTextField.getHeight(), targetTextField.getWidth(),
                turnIconHeight / 2);
        attackBarLayoutPane.add(gameStatusPanel, TURNSBAR_LAYER, 0);
        gameStatusPanel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        gameStatusPanel.setEditable(false);
        gameStatusPanel.setLineWrap(true);
    }

    /**
     * Sets a Jpanel as a JlayerdPane background.
     * 
     * @param layeredPane
     * @param background
     * @author Michele Farneti
     */
    private void setLayerdPaneBackground(final JLayeredPane layeredPane, final JPanel background) {
        setLayerdPaneLayer(layeredPane, background, BACKGROUND_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerdpane pane top level.
     * 
     * @param layeredPane
     * @param overlay
     * @author Michele Farneti
     */
    private void setLayerdPaneOverlay(final JLayeredPane layeredPane, final JPanel overlay) {
        setLayerdPaneLayer(layeredPane, overlay, TOP_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerdpane given layer.
     * 
     * @param layeredPane
     * @param jpanel
     * @param layer
     * @author Michele Farneti
     */
    private void setLayerdPaneLayer(final JLayeredPane layeredPane, final JPanel jpanel, final Integer layer) {
        layeredPane.add(jpanel, layer, 0);
    }

    /**
     * This funcion manages whenever a tank rappresenting a territory gets clicked.
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
     * Sets the background of mapPanel as the mapImage, if the loading of the image.
     * 
     * @param resourcePackagePath
     * @param imageName           The name of the map image.
     * @author Michele Farneti
     */
    private void paintMap(final String resourcePackagePath, final String imageName) {
        final JPanel mapPanel = new BackgroundImagePanel(resourcePackagePath, imageName);
        mapPanel.setBounds(0, 0, mapLayoutPane.getWidth(), mapLayoutPane.getHeight());
        mapLayoutPane.add(mapPanel, MAP_LAYER, 0);
        gamePanel.add(mapLayoutPane);
        mapPanel.setOpaque(false);
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

    @Override
    public void showTanks(final List<Territory> territories) {
        final TerritoryPlaceHolderFactory tanksFactory = new TerritoryPlaceHolderFactoryImpl(
                createPath(resourcesLocator, List.of("maps", mapName, "coordinates.txt")));
        territories.stream().map(territory -> tanksFactory.generateTank(territory,
                position -> new Position(Math.round(position.x() * (mapWidth / MAP_WIDTH_SCALE)),
                        Math.round(position.y() * (mapHeight / MAP_HEIGHT_SCALE))),
                resourcesLocator, e -> tankClicked(territory.getTerritoryName())))
                .filter(optional -> optional.isPresent()).map(placeholder -> placeholder.get())
                .forEach(placeholder -> tanks.add(placeholder));
        tanks.forEach(t -> t.addToLayoutPane(mapLayoutPane, TANK_LAYER));
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
     * Calculates the starting pixel for a turnIcon of a player at a given index.
     * 
     * @param playerIndex
     * @return The distance in pixels from the left side of the frame the turn icon
     *         of a player at a given index should appear.
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
     * Converts a player's color_id into a Color object.
     * 
     * @param colorID a player's color id
     * @return The curresponding color object
     * @author Michele Farneti
     */
    private Color stringToColor(final String colorID) {
        switch (colorID) {
            case "cyan":
                return new Color(0, MAX_COLOR_VALUE, MAX_COLOR_VALUE);
            case "blue":
                return new Color(0, 0, MAX_COLOR_VALUE);
            case "green":
                return new Color(0, MAX_COLOR_VALUE, 0);
            case "red":
                return new Color(MAX_COLOR_VALUE, 0, 0);
            case "pink":
                return new Color(MAX_COLOR_VALUE, 0, MAX_COLOR_VALUE);
            case "yellow":
                return new Color(MAX_COLOR_VALUE, MAX_COLOR_VALUE, 0);
            default:
                return new Color(MAX_COLOR_VALUE, MAX_COLOR_VALUE, MAX_COLOR_VALUE);
        }
    }

    @Override
    public void showFightingTerritory(final String territory, final boolean isAttacker) {
        tanks.stream().filter(t -> t.getTerritoryName().equals(territory))
                .forEach(t -> t.setFighting(isAttacker ? Color.RED : Color.BLUE));
    }

    @Override
    public void redrawTank(final Territory territory) {
        tanks.stream().filter(t -> t.getTerritoryName().equals(territory.getTerritoryName()))
                .forEach(t -> t.redrawTank(territory));
    }

    @Override
    public void resetFightingTerritories() {
        tanks.stream().forEach(t -> t.resetBorder());
    }

    @Override
    public void gameOver(final String winnerColor) {
        final var winnerPanel = new GradientPanel(Color.BLACK, stringToColor(winnerColor), MAP_GRADIENT_LEVEL);
        winnerPanel.setBackground(Color.RED);
        winnerPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        winnerPanel.setOpaque(true);

        final var winnerWriting = new JTextField(winnerColor.toUpperCase(Locale.ROOT) + " player has won the Game!");
        winnerWriting.setFont(new Font(FONT_NAME, Font.BOLD, WINNER_FONT_SIZE));
        winnerWriting.setForeground(stringToColor(winnerColor));
        winnerWriting.setOpaque(false);
        winnerWriting.setBorder(BorderFactory.createEmptyBorder());
        winnerPanel.add(winnerWriting);
        setLayerdPaneOverlay(baseLayoutPane, winnerPanel);
    }

    @Override
    public void setAtt(final List<Integer> attDice) {
        attackPanel.setAtt(attDice);
    }

    @Override
    public void setDef(final List<Integer> defDice) {
        attackPanel.setDef(defDice);
    }

    @Override
    public void setAttackerLostArmies(final int attackerLostArmies) {
        attackPanel.setAttackerLostArmies(attackerLostArmies);
    }

    @Override
    public void setDefenderLostArmies(final int defenderLostArmies) {
        attackPanel.setDefenderLostArmies(defenderLostArmies);
    }

    @Override
    public void createAttack(final String attacking, final String defending, final int attackingTerritoryArmies) {
        final int panelWidth = frameWidth / ATTACK_PANEL_POSITIONING;
        final int panelHeight = frameHeigth / ATTACK_PANEL_POSITIONING;
        final int panelLocationX = frameWidth / ATTACK_PANEL_LOCATION;
        final int panelLocationY = frameHeigth / ATTACK_PANEL_LOCATION;
        attackPanel = new AttackPanel(
                panelHeight,
                panelWidth,
                attacking,
                defending,
                attackingTerritoryArmies,
                gameViewObserver);
        attackPanel.setLocation(panelLocationX, panelLocationY);
        attackPanel.setVisible(true);
        setGameViewButtonsEnabled(false);
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
    public void showInitializationWindow(final Map<String, Integer> mapNames) {
        final var initializationPanel = new NewGameInitViewImpl(frameWidth, frameHeigth, mapNames,
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
    @Override
    public void createMoveArmies(final List<Territory> listTerritories) {
        final int locationFactor = ATTACK_PANEL_LOCATION;
        final int sizeFactor = ATTACK_PANEL_POSITIONING;
        moveArmiesPanel = new JPanelMovementArmies(listTerritories, gameViewObserver);
        moveArmiesPanel.setBounds(frameWidth / locationFactor, frameHeigth / locationFactor,
                frameWidth / sizeFactor,
                frameHeigth / sizeFactor);
        moveArmiesPanel.setVisible(true);
        setGameViewButtonsEnabled(false);
        setLayerdPaneOverlay(baseLayoutPane, moveArmiesPanel);
    }

    /**
     * Updates the target text area.
     * 
     * @param targetText Target description.
     * @autho Michele Farneti.
     */
    private void showTarget(final String targetText) {
        targetTextField.setText(targetText);
    }

    /**
     * Method used to create the panel to play the three cards.
     * After the panel creation it is set visible and added to the game frame.
     * 
     * @param playerCards is the list of cards of the player
     */
    @Override
    public void createChoiceCards(final List<Card> playerCards) {
        final int locationFactor = ATTACK_PANEL_LOCATION;
        final int sizeFactor = ATTACK_PANEL_POSITIONING;
        choiceCardsPanel = new JPanelChoice(playerCards, gameViewObserver);
        choiceCardsPanel.setBounds(frameWidth / locationFactor, frameHeigth / locationFactor,
                frameWidth / sizeFactor,
                frameHeigth / sizeFactor);
        choiceCardsPanel.setVisible(true);
        setLayerdPaneOverlay(baseLayoutPane, choiceCardsPanel);
    }

    @Override
    public void enableMovements(final boolean enabled) {
        moveArmiesButton.setEnabled(enabled);
    }

    @Override
    public void enableSkip(final boolean enabled) {
        skipButton.setEnabled(enabled);
    }

    @Override
    public void enableAttack(final boolean enabled) {
        attackButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
    }

    /**
     * @author Keliane Nana
     */
    @Override
    public void createLog(final Register reg, final List<Player> l) {
        log = new LoggerView(reg, l);
        log.setPreferredSize(new Dimension(mainFrame.getWidth() - gamePanel.getWidth(),
                mainFrame.getHeight() / ATTACK_PANEL_POSITIONING));
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
    @Override
    public void createTablePanel(final List<Territory> terr, final List<Player> players) {
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
        if (choiceCardsPanel != null) {
            choiceCardsPanel.setVisible(false);
            baseLayoutPane.remove(choiceCardsPanel);
        }
    }

    @Override
    public void showStatus(final GameStatus gameStatus, final Integer turnsCount) {
        final int stringSize = 40;
        final StringBuilder stringBuilder = new StringBuilder(stringSize);
        stringBuilder.append("[" + turnsCount + "] -");
        switch (gameStatus) {
            case ARMIES_PLACEMENT:
            case TERRITORY_OCCUPATION:
                stringBuilder.append("Click the map territory where you want to add armies");
                break;
            case READY_TO_ATTACK:
            case ATTACKING:
                stringBuilder.append("Feel free to attack!");
                break;
            case CARDS_MANAGING:
                stringBuilder.append("Cards Managing!");
                break;
            default:
                break;
        }
        gameStatusPanel.setText(stringBuilder.toString());
    }

    @Override
    public void enableTanks(final boolean enabled) {
        tanks.forEach(t -> t.setEnabled(enabled));
    }
}
