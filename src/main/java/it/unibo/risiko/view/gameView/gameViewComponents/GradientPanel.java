package it.unibo.risiko.view.gameView.gameViewComponents;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/**
 * A basic JPanel with a gradient set as background.
 * @author Michele Farneti
 */
public class GradientPanel extends JPanel{
    private Color topColor;
    private Color bottomColor;
    private int gradientLevel;

    /**
     * @param topColor Color of the upper part of the background
     * @param bottomColor Color of the lower part of the background
     * @param gradientLevel Height of the color change
     */
    public GradientPanel(Color topColor, Color bottomColor, int gradientLevel)
    {
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.gradientLevel = gradientLevel;
    }

    /**
     * @param topColor A new color to be set as topcolor
     */
    public void setTopColor(Color topColor){
        this.topColor = topColor;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    
        int width = getWidth();
        int height = getHeight();
        Color color1 = topColor;
        Color color2 = bottomColor;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height/gradientLevel, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
    
}
