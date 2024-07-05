package it.unibo.risiko.view.gameView;

import it.unibo.risiko.view.InitialView.GameFrame;
import it.unibo.risiko.view.gameView.gameViewComponents.BackgroundImagePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameViewImpl implements GameView{

    private final static Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private final static Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;
    private final static int MAP_LAYER = 1;
    private final Integer GAME_FRAME_WIDTH;
    private final Integer GAME_FRAME_HEIGHT;
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

        baseLayoutPane.setBounds(0, 0,GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT);
        baseLayoutPane.add(gamePanel,0,0);
        gamePanel.setBounds(0,0,(int)(gameFrame.getWidth()*GAME_PANEL_WIDTH_PERCENTAGE),gameFrame.getHeight());
        gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));
        gamePanel.setBackground(Color.black);

        Dimension mapLayoutPaneSize = new Dimension((int)gamePanel.getWidth(),(int)(gamePanel.getHeight()*MAP_PANEL_HEIGHT_PERCENTAGE));
        mapLayoutPane.setBounds(0,0,(int)mapLayoutPaneSize.getWidth(),(int)mapLayoutPaneSize.getHeight());
        mapLayoutPane.setPreferredSize(new Dimension((int)(gamePanel.getWidth()),(int)(gamePanel.getHeight()*MAP_PANEL_HEIGHT_PERCENTAGE)));
        paintMap(mapImagePath);
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
    
}
