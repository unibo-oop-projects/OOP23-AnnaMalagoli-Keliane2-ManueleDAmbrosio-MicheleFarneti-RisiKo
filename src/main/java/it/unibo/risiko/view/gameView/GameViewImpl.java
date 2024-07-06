package it.unibo.risiko.view.gameView;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.view.InitialView.GameFrame;
import it.unibo.risiko.view.gameView.gameViewComponents.BackgroundImagePanel;
import it.unibo.risiko.view.gameView.gameViewComponents.ColoredImageButton;
import it.unibo.risiko.view.gameView.gameViewComponents.GradientPanel;
import it.unibo.risiko.view.gameView.gameViewComponents.Position;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameViewImpl implements GameView{

    private final static Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private final static Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;
    private final static Integer MAP_GRADIENT_LEVEL = 1;
    private final static Integer ATTACK_BAR_GRADIENT_LEVEL = 7;
    private final static int MAP_LAYER = 1;
    private final static int BACKGROUND_LAYER = 0;
    private final static Color ATTACK_BAR_BACKGROUND_COLOR = new Color(63,58,20);
    private final static Color ATTACK_BAR_FOREGROUND_COLOR = new Color(255,204,0);
    private static final Integer tanksWidth = 45;
    private static final Integer tanksHeight = 45;
    private final Integer GAME_FRAME_WIDTH;
    private final Integer GAME_FRAME_HEIGHT;
    private static final Map<String,Position> tanksCoordinates = new HashMap<String,Position>(){{
        put("Alaska",new Position(10,10));
        put("North West Territory",new Position(20,30));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(10,10));
        put("Alaska",new Position(120,120));

    }};
    private HashMap<JButton,Territory> tankMap = new HashMap<JButton,Territory>();
    private GameViewObserver gameViewObserver;
    private JFrame gameFrame = new JFrame();
    private JLayeredPane baseLayoutPane = new JLayeredPane();
    private JLayeredPane mapLayoutPane = new JLayeredPane();
    private JLayeredPane attackBarLayoutPane = new JLayeredPane();
    private JPanel mapPanel;
    private JPanel gamePanel = new JPanel();

    /**
     * Initialzize the GUI for the game creating all of its main components.
     * @param frameWidth
     * @param frameHeight
     */
    public GameViewImpl(Integer frameWidth, Integer frameHeight,String mapImagePath){
        GAME_FRAME_WIDTH = frameWidth;
        GAME_FRAME_HEIGHT = frameHeight;

        gameFrame.setSize(new Dimension(GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT));
        gameFrame.setTitle("Risiko!");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(baseLayoutPane, BorderLayout.CENTER);

        //GamePanel and Basic layout initialization
        baseLayoutPane.setBounds(0, 0,GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT);
        baseLayoutPane.add(gamePanel,0,0);
        gamePanel.setBounds(0,0,(int)(gameFrame.getWidth()*GAME_PANEL_WIDTH_PERCENTAGE),gameFrame.getHeight());
        gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));

        //Painting of the map
        Dimension mapLayoutPaneSize = new Dimension((int)gamePanel.getWidth(),(int)(gamePanel.getHeight()*MAP_PANEL_HEIGHT_PERCENTAGE));
        mapLayoutPane.setBounds(0,0,(int)mapLayoutPaneSize.getWidth(),(int)mapLayoutPaneSize.getHeight());
        mapLayoutPane.setPreferredSize(new Dimension((int)(gamePanel.getWidth()),(int)(gamePanel.getHeight()*MAP_PANEL_HEIGHT_PERCENTAGE)));
        paintMap(mapImagePath);

        //AttackBAr initiialization
        attackBarLayoutPane.setPreferredSize(new Dimension((int)(gamePanel.getWidth()),(int)(gamePanel.getHeight()*0.3)));
        gamePanel.add(attackBarLayoutPane);
        attackBarLayoutPane.setBackground(Color.pink);
        attackBarLayoutPane.setOpaque(true);

        //Map background setting
        GradientPanel mapBackgroundPanel = new GradientPanel(Color.GRAY,Color.WHITE,MAP_GRADIENT_LEVEL);
        mapBackgroundPanel.setBounds(0,0,mapLayoutPane.getWidth(),mapLayoutPane.getHeight());
        mapBackgroundPanel.setOpaque(true);
        setLayerdPaneBackground(mapLayoutPane,mapBackgroundPanel);

        //AttackBar backgorund setting
        GradientPanel attackBarBackgroundPanel = new GradientPanel(ATTACK_BAR_FOREGROUND_COLOR,ATTACK_BAR_BACKGROUND_COLOR,ATTACK_BAR_GRADIENT_LEVEL);
        attackBarBackgroundPanel.setBounds(0,0,(int)attackBarLayoutPane.getPreferredSize().getWidth(),(int)attackBarLayoutPane.getPreferredSize().getHeight());
        attackBarBackgroundPanel.setOpaque(true);
        setLayerdPaneBackground(attackBarLayoutPane,attackBarBackgroundPanel);
    }

    private void setLayerdPaneBackground(JLayeredPane layeredPane, JPanel background){
        layeredPane.add(background,BACKGROUND_LAYER,0);
    }

    /**
     * Sets the background of mapPanel as the mapImage, if the loading of the image fails,
     * a pannel reporting the error appears
     * @param mapPath  The path of the image of the selected map
     */
    private void paintMap(String mapPath) {
        Optional<Image> mapImage = readImage(mapPath);
        if(mapImage.isPresent()){
            mapPanel = new BackgroundImagePanel(mapImage.get());
            mapPanel.setOpaque(false);
        }else{
            mapPanel = new JPanel();
            mapPanel.add(new JLabel("Failed to load Map image"));
        }
        mapPanel.setBounds(0, 0, mapLayoutPane.getWidth(), mapLayoutPane.getHeight());
        mapLayoutPane.add(mapPanel,MAP_LAYER,0);
        gamePanel.add(mapLayoutPane);
    }

    @Override
    public void setObserver(GameViewObserver gameController) {
        this.gameViewObserver = gameController;
    }

    @Override
    public void start() {
        this.gameFrame.setVisible(true);
    }

    /**
     * 
     * @param imagePath The image path
     * @return An empty optional it failed toread the image, An optional of the image otherwise.
     */
    private Optional<Image> readImage(String imagePath){
        try {
            Image image = ImageIO.read(new File(imagePath));
            return Optional.of(image);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void showTanks(List<Territory> territories) {
        territories.stream().forEach(territory -> tankMap.put(new ColoredImageButton("/tanks/tank"), territory));
        tankMap.entrySet().stream().forEach(e -> e.getKey().setBounds(tanksCoordinates.get(e.getValue().getTerritoryName()).x(), tanksCoordinates.get(e.getValue().getTerritoryName()).y(), tanksWidth, tanksHeight));
    }
    
}
