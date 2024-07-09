package it.unibo.risiko.view.gameView;

import it.unibo.risiko.view.gameView.gameViewComponents.BackgroundImagePanel;
import it.unibo.risiko.view.gameView.gameViewComponents.ColoredImageButton;
import it.unibo.risiko.view.gameView.gameViewComponents.CustomButton;
import it.unibo.risiko.view.gameView.gameViewComponents.GradientPanel;
import it.unibo.risiko.view.gameView.gameViewComponents.Position;
import it.unibo.risiko.view.gameView.gameViewComponents.TerritoryPlaceHolder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Implementattion of GameView interface.
 * 
 * @author Michele Farneti
 * @author Manuele D'Ambrosio
 */

public class GameViewImpl implements GameView {

    private static final Integer WINNER_FONT_SIZE = 30;

    private static final String FILE_SEPARATOR = File.separator;

    private final static Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private final static Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;

    private final static Integer MAP_GRADIENT_LEVEL = 1;
    private final static Integer ATTACK_BAR_GRADIENT_LEVEL = 7;
    private final static Integer TURNS_BAR_GRADIENT_LEVEL = 1;

    private final static int MAP_LAYER = 1;
    private final static int TANK_LAYER = 2;
    private final static int TURN_ICON_LAYER = 3;
    private final static int TURNSBAR_LAYER = 2;
    private final static int BACKGROUND_LAYER = 0;
    private static final Integer TOP_LAYER = 5;

    private final static Color ATTACK_BAR_BACKGROUND_COLOR = new Color(63, 58, 20);
    private final static Color ATTACK_BAR_FOREGROUND_COLOR = new Color(255, 204, 0);

    private static final Integer TANKS_WIDTH = 45;
    private static final Integer TANKS_HEIGTH = 45;

    private static final Integer TURN_ICON_WIDTH = 60;
    private static final Integer TURN_ICON_HEIGHT = 60;
    private static final Integer TURNBAR_START_X = 10;
    private static final Integer TURNBAR_START_Y = 10;

    private static final Integer COUNTRYBAR_START_Y = 80;

    private static final Integer ATTACKBAR_BUTTONS_WIDTH = 150;
    private static final Integer ATTACKBAR_BUTTONS_HEIGHT = 50;
    private static final Integer ATTACKBAR_BUTTONS_DISTANCE = 20;

    private static final Integer TURN_TANK_WIDTH = 100;
    private static final Integer TURN_TANK_HEIGHT = 100;
    private static final Integer TURN_TANK_LAYER = 5;

    private static final int ARMIES_LABEL_HEIGHT = 15;
    private static final int ARMIES_LABEL_WIDTH = 15;
    private static final int ARMIES_LABEL_FONT_SIZE = 14;

    private static final int PLAYER_ARMIES_LABEL_HEIGHT = 23;
    private static final int PLAYER_ARMIES_LABEL_WIDTH = 23;
    private static final int PLAYER_ARMIES_LABEL_FONT_SIZE = 20;

    private final Integer GAME_FRAME_WIDTH;
    private final Integer GAME_FRAME_HEIGHT;
    private static final Map<String, Position> tanksCoordinates = new HashMap<String, Position>() {
        {
            put("Great-Britain", new Position(530, 95));
            put("Iceland", new Position(530, 45));
            put("Scandinavia", new Position(620, 55));
            put("North-Europe", new Position(620, 110));
            put("West-Europe", new Position(550, 150));
            put("South-Europe", new Position(640, 150));
            put("Ukraine", new Position(710, 90));
            put("North-Africa", new Position(550, 250));
            put("Egypt", new Position(650, 220));
            put("Congo", new Position(650, 340));
            put("East-Africa", new Position(730, 310));
            put("Southern-Africa", new Position(655, 440));
            put("Madagascar", new Position(770, 430));
            put("East-Australia", new Position(1140, 440));
            put("New-Guinea", new Position(1180, 360));
            put("Western-Australia", new Position(1070, 460));
            put("Indonesia", new Position(1050, 360));
            put("Venezuela", new Position(250, 310));
            put("Peru", new Position(260, 420));
            put("Argentina", new Position(290, 490));
            put("Brazil", new Position(340, 390));
            put("Alaska", new Position(60, 50));
            put("Alberta", new Position(130, 95));
            put("Central-America", new Position(170, 270));
            put("Eastern-U.S.", new Position(210, 170));
            put("Greenland", new Position(450, 15));
            put("Northwest-Territories", new Position(160, 50));
            put("Ontario", new Position(220, 100));
            put("Quebec", new Position(300, 100));
            put("West-United-States", new Position(100, 160));
            put("Urals", new Position(810, 70));
            put("Siberia", new Position(880, 50));
            put("Yakutia", new Position(990, 50));
            put("Chita", new Position(970, 95));
            put("Kamchatka", new Position(1110, 60));
            put("Japan", new Position(1130, 170));
            put("Mongolia", new Position(980, 140));
            put("Afghanistan", new Position(810, 130));
            put("Middle-East", new Position(750, 200));
            put("India", new Position(880, 230));
            put("China", new Position(995, 200));
            put("Siam", new Position(995, 270));
        }
    };

    // private HashMap<Territory,ColoredImageButton> tanksMap = new
    // HashMap<Territory,ColoredImageButton>();
    private HashMap<String, TerritoryPlaceHolder> tanksMap = new HashMap<>();
    private HashMap<ColoredImageButton, String> iconsMap = new HashMap<>();
    private GameViewObserver gameViewObserver;
    private JFrame mainFrame = new JFrame();
    private JLayeredPane baseLayoutPane = new JLayeredPane();
    private JLayeredPane mapLayoutPane = new JLayeredPane();
    private JLayeredPane attackBarLayoutPane = new JLayeredPane();
    private JPanel gamePanel = new JPanel();
    private JPanel mapPanel;
    private JPanel turnsBarPanel;
    private JLabel playerArmiesLabel = new JLabel();
    private JTextArea countryBarPanel;
    private AttackPanel attackPanel;

    private ColoredImageButton turnTank;
    private GradientPanel attackBarBackgroundPanel;

    private final String resourcesLocator;

    /**
     * Initialzizes the GUI for the game creating all of its main components.
     * 
     * @param frameWidth
     * @param frameHeight
     */
    public GameViewImpl(Integer frameWidth, Integer frameHeight, String resourcesLocator) {
        GAME_FRAME_WIDTH = frameWidth;
        GAME_FRAME_HEIGHT = frameHeight;
        this.resourcesLocator = resourcesLocator;

        mainFrame.setSize(new Dimension(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT));
        mainFrame.setTitle("Risiko!");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void showGameWindow(String mapName){
        mainFrame.add(baseLayoutPane, BorderLayout.CENTER);

        // GamePanel and Basic layout initialization
        baseLayoutPane.setBounds(0, 0, GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT);
        baseLayoutPane.add(gamePanel, 0, 0);
        gamePanel.setBounds(0, 0, (int) (mainFrame.getWidth() * GAME_PANEL_WIDTH_PERCENTAGE), mainFrame.getHeight());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Painting of the map
        Dimension mapLayoutPaneSize = new Dimension((int) gamePanel.getWidth(),
                (int) (gamePanel.getHeight() * MAP_PANEL_HEIGHT_PERCENTAGE));
        mapLayoutPane.setBounds(0, 0, (int) mapLayoutPaneSize.getWidth(), (int) mapLayoutPaneSize.getHeight());
        mapLayoutPane.setPreferredSize(new Dimension((int) (gamePanel.getWidth()),
                (int) (gamePanel.getHeight() * MAP_PANEL_HEIGHT_PERCENTAGE)));
        paintMap(resourcesLocator + "maps" + FILE_SEPARATOR + mapName+ FILE_SEPARATOR + mapName + ".png" );

        // Map background setting
        GradientPanel mapBackgroundPanel = new GradientPanel(Color.GRAY, Color.WHITE, MAP_GRADIENT_LEVEL);
        mapBackgroundPanel.setBounds(0, 0, mapLayoutPane.getWidth(), mapLayoutPane.getHeight());
        mapBackgroundPanel.setOpaque(true);
        setLayerdPaneBackground(mapLayoutPane, mapBackgroundPanel);
        setupAttackBar();
    }


    /**
     * Sets up the attackbar part of the view
     */
    private void setupAttackBar() {
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
        turnsBarPanel = new GradientPanel(Color.WHITE, ATTACK_BAR_FOREGROUND_COLOR, TURNS_BAR_GRADIENT_LEVEL);
        turnsBarPanel.setBounds(TURNBAR_START_X, TURNBAR_START_Y, TURN_ICON_WIDTH * 6, TURN_ICON_HEIGHT);
        attackBarLayoutPane.add(turnsBarPanel, TURNSBAR_LAYER, 0);
        // Country bar
        countryBarPanel = new JTextArea();
        countryBarPanel.setBounds(TURNBAR_START_X, TURNBAR_START_Y + COUNTRYBAR_START_Y, TURN_ICON_WIDTH * 6,
                TURN_ICON_HEIGHT / 2);
        attackBarLayoutPane.add(countryBarPanel, TURNSBAR_LAYER, 0);
        countryBarPanel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        countryBarPanel.setEditable(false);

        var attackButton = new CustomButton("ATTACK");
        attackButton.setBounds(gamePanel.getWidth() / 2 - ATTACKBAR_BUTTONS_WIDTH - ATTACKBAR_BUTTONS_DISTANCE, 100,
                ATTACKBAR_BUTTONS_WIDTH, ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(attackButton, 5, 0);

        var skipButton = new CustomButton("SKIP");
        skipButton.setBounds(gamePanel.getWidth() / 2 + ATTACKBAR_BUTTONS_DISTANCE, 100, ATTACKBAR_BUTTONS_WIDTH,
                ATTACKBAR_BUTTONS_HEIGHT);
        attackBarLayoutPane.add(skipButton, 5, 0);
        skipButton.addActionListener(e -> gameViewObserver.skipTurn());

        turnTank = new ColoredImageButton(FILE_SEPARATOR + "tanks" + FILE_SEPARATOR + "tank_",
                gamePanel.getWidth() / 2 - (TURN_TANK_WIDTH) / 2, 0, TURN_TANK_WIDTH, TURN_TANK_HEIGHT);
        turnTank.setBorderPainted(false);
        turnTank.setEnabled(false);

        playerArmiesLabel.setBounds(
                turnTank.getBounds().x + TURN_TANK_WIDTH, turnTank.getBounds().y + TURN_TANK_HEIGHT / 3,
                PLAYER_ARMIES_LABEL_WIDTH, PLAYER_ARMIES_LABEL_HEIGHT);
        playerArmiesLabel.setFont(new Font("Arial", Font.BOLD, PLAYER_ARMIES_LABEL_FONT_SIZE));
        playerArmiesLabel.setText("20");
        playerArmiesLabel.setBackground(ATTACK_BAR_FOREGROUND_COLOR);
        playerArmiesLabel.setForeground(ATTACK_BAR_BACKGROUND_COLOR);
        playerArmiesLabel.setOpaque(true);

        attackBarLayoutPane.add(turnTank, TURN_TANK_LAYER, 0);
        attackBarLayoutPane.add(playerArmiesLabel, TURN_TANK_LAYER + 1, 0);
    }

    /**
     * Sets a Jpanel as a JlayerdPane background
     * 
     * @param layeredPane
     * @param background
     * @author Michele Farneti
     */
    private void setLayerdPaneBackground(JLayeredPane layeredPane, JPanel background) {
        setLayerdPaneLayer(layeredPane, background, BACKGROUND_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerde pane top level
     * 
     * @param layeredPane
     * @param overlay
     * @author Michele Farneti
     */
    private void setLayerdPaneOverlay(JLayeredPane layeredPane, JPanel overlay) {
        setLayerdPaneLayer(layeredPane, overlay, TOP_LAYER);
    }

    /**
     * Sets a Jpanel at a Jlayerde given layer
     * 
     * @param layeredPane
     * @param jpanel
     * @author Michele Farneti
     */
    private void setLayerdPaneLayer(JLayeredPane layeredPane, JPanel jpanel, Integer layer) {
        layeredPane.add(jpanel, layer, 0);
    }

    /**
     * This funcion manages whenever a tank rapresenting a tank gets clicked
     * 
     * @param territory The territory that got clicked
     */
    private void tankClicked(String territory) {
        countryBarPanel.setText(territory.toUpperCase());
        countryBarPanel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        countryBarPanel.setFont(new Font("Arial", Font.BOLD, 25));
        gameViewObserver.territorySelected(territory);
    }

    /**
     * Sets the background of mapPanel as the mapImage, if the loading of the image
     * fails,
     * a pannel reporting the error appears
     * 
     * @param mapPath The path of the image of the selected map
     */
    private void paintMap(String mapPath) {
        System.out.println(mapPath);
        Optional<Image> mapImage = readImage(mapPath);
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
    public void setObserver(GameViewObserver gameController) {
        this.gameViewObserver = gameController;
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
     */
    private Optional<Image> readImage(String imagePath) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            return Optional.of(image);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void showTanks(List<String> territories) {

        territories.stream().forEach(territory -> tanksMap.put(territory, new TerritoryPlaceHolder(
                new ColoredImageButton(FILE_SEPARATOR + "tanks" + FILE_SEPARATOR + "tank_"), new JLabel("0"))));
        
        for (var tank : tanksMap.entrySet()) {
            tank.getValue().button().setBounds(tanksCoordinates.get(tank.getKey()).x(),
                    tanksCoordinates.get(tank.getKey()).y(), TANKS_WIDTH, TANKS_HEIGTH);
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
            tank.getValue().armiesCount().setForeground(Color.white);
            tank.getValue().armiesCount().setFont(new Font("Arial", Font.BOLD, ARMIES_LABEL_FONT_SIZE));
            mapLayoutPane.add(tank.getValue().button(), TANK_LAYER, 0);
            mapLayoutPane.add(tank.getValue().armiesCount(), TANK_LAYER, 0);
        }
    }

    @Override
    public void showTurnIcon(String player, int playerIndex, boolean isAI) {
        if (isAI) {
            iconsMap.put(
                    new ColoredImageButton(FILE_SEPARATOR + "aiplayers" + FILE_SEPARATOR + "aiplayer_",
                            computeIconStartingX(playerIndex), TURNBAR_START_Y, TURN_ICON_WIDTH, TURN_ICON_HEIGHT),
                    player);
        } else {
            iconsMap.put(
                    new ColoredImageButton(FILE_SEPARATOR + "standardplayers" + FILE_SEPARATOR + "standardplayer_",
                            computeIconStartingX(playerIndex), TURNBAR_START_Y, TURN_ICON_WIDTH, TURN_ICON_HEIGHT),
                    player);
        }

        for (var icon : iconsMap.entrySet()) {
            icon.getKey().setColor(icon.getValue());
            icon.getKey().setEnabled(false);
            icon.getKey().setBorder(BorderFactory.createLineBorder(stringToColor(icon.getValue().toLowerCase()), 3));
            attackBarLayoutPane.add(icon.getKey(), TURN_ICON_LAYER, 0);
        }
    }

    /**
     * Calculates the starting pixel for a turnIcon of a player at a given index
     * 
     * @param playerIndex
     */
    private int computeIconStartingX(final int playerIndex) {
        return (playerIndex * TURN_ICON_WIDTH) + TURNBAR_START_X;
    }

    @Override
    public void setCurrentPlayer(String playerColor, Integer nArmies) {
        turnTank.setColor(playerColor);
        iconsMap.entrySet().stream()
                .forEach(e -> e.getKey().setBorderPainted((e.getValue().equals(playerColor)) ? true : false));
        attackBarBackgroundPanel.setTopColor(stringToColor(playerColor));
        playerArmiesLabel.setText(nArmies.toString());
        mainFrame.validate();
        mainFrame.repaint();
    }

    private Color stringToColor(String color_id) {
        switch (color_id) {
            case "cyan":
                return new Color(0, 255, 255);
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
    public void showFightingTerritory(String territory, boolean isAttacker) {
        tanksMap.get(territory).button().setCustomBorder(isAttacker ? Color.RED : Color.BLUE);
        tanksMap.get(territory).button().setBorderPainted(true);
    }

    @Override
    public void redrawTank(String countryName, String playerColor, Integer armiesCount) {
        tanksMap.get(countryName).button().setColor(playerColor);
        tanksMap.get(countryName).armiesCount().setText(armiesCount.toString());
    }

    @Override
    public void resetFightingTerritories(String attackerTerritory, String defenderTerritory) {
        tanksMap.get(attackerTerritory).button().setBorderPainted(false);
        tanksMap.get(defenderTerritory).button().setBorderPainted(false);
    }

    @Override
    public void gameOver(String winnerColor) {
        var winnerPanel = new GradientPanel(Color.BLACK, stringToColor(winnerColor), MAP_GRADIENT_LEVEL);
        winnerPanel.setBackground(Color.RED);
        winnerPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        winnerPanel.setOpaque(true);

        var winnerWriting = new JTextField(winnerColor.toUpperCase() + " player has won the Game!");
        winnerWriting.setFont(new Font("Arial", Font.BOLD, WINNER_FONT_SIZE));
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
        attackPanel = new AttackPanel(GAME_FRAME_HEIGHT / 3, GAME_FRAME_WIDTH / 3, attacking, defending,
                attackingTerritoryArmies, gameViewObserver);
        attackPanel.setLocation(GAME_FRAME_WIDTH/3, GAME_FRAME_HEIGHT/3);
        attackPanel.setVisible(true);
        setLayerdPaneOverlay(baseLayoutPane, attackPanel);
    }

    @Override
    public void closeAttackPanel() {
        attackPanel.setVisible(false); //NEED TO CHECK IF THE PANEL CLOSES
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
    public void showInitializationWindow(List<String> mapNames) {
        var initializationPanel = new NewGameInitViewImpl(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT, mapNames, gameViewObserver);
        mainFrame.add(initializationPanel,BorderLayout.CENTER);
        mainFrame.validate();
    }

}
