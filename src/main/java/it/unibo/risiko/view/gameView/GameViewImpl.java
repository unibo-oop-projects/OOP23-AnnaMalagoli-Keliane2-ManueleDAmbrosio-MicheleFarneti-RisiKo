package it.unibo.risiko.view.gameView;

import it.unibo.risiko.view.InitialView.GameFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameViewImpl implements GameView{

    private final static Double GAME_PANEL_WIDTH_PERCENTAGE = 0.8;
    private final static Double MAP_PANEL_HEIGHT_PERCENTAGE = 0.7;
    private final Integer GAME_FRAME_WIDTH;
    private final Integer GAME_FRAME_HEIGHT;
    private GameViewObserver gameViewObserver;
    private JFrame gameFrame = new JFrame();
    private JLayeredPane baseLayoutPane = new JLayeredPane();
    private JLayeredPane mapLayoutPane = new JLayeredPane();
    private JLayeredPane attackBarLayoutPane = new JLayeredPane();
    private JPanel gamePanel = new JPanel();

    /**
     * Initialzize the GUI for the game creating all of its main components.
     * @param frameWidth
     * @param frameHeight
     */
    public GameViewImpl(Integer frameWidth, Integer frameHeight){
        GAME_FRAME_WIDTH = frameWidth;
        GAME_FRAME_HEIGHT = frameHeight;

        gameFrame.setSize(new Dimension(GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT));
        gameFrame.add(baseLayoutPane, BorderLayout.CENTER);
        baseLayoutPane.setBounds(0, 0,GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT);

        baseLayoutPane.add(gamePanel,0,0);
        gamePanel.setBounds(0,0,(int)(gameFrame.getWidth()*GAME_PANEL_WIDTH_PERCENTAGE),gameFrame.getHeight());
        gamePanel.setOpaque(true);
        gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));

        Dimension mapLayoutPaneSize = new Dimension((int)gamePanel.getWidth(),(int)(gamePanel.getHeight()*MAP_PANEL_HEIGHT_PERCENTAGE));
        mapLayoutPane.setBounds(0,0,(int)mapLayoutPaneSize.getWidth(),(int)mapLayoutPaneSize.getHeight());
        //mapLayoutPane.setMaximumSize(new Dimension((int)(gamePanel.getWidth()),(int)(gamePanel.getHeight()*0.7)));
        //mapLayoutPane.setMinimumSize(new Dimension((int)(gamePanel.getWidth()),(int)(gamePanel.getHeight()*0.7)));
        mapLayoutPane.setPreferredSize(mapLayoutPaneSize);

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
    
}
