package it.unibo.risiko.view.gameview.components;

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
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }
}
