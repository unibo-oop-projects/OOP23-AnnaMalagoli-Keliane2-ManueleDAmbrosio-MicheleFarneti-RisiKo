package it.unibo.risiko.view.gameView.gameViewComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Realizes a JPanel with an image as background
 * @author Michele Farneti
 */
public class BackgroundImagePanel extends JPanel{
    private Image background;
    
    public BackgroundImagePanel(Image background)
    {
        this.background = background;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }
}
