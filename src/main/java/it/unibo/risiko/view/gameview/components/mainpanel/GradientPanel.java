package it.unibo.risiko.view.gameview.components.mainpanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/**
 * A basic JPanel with a gradient set as background.
 * 
 * @author Michele Farneti
 */
public final class GradientPanel extends JPanel {
    private Color topColor;
    private final Color bottomColor;
    private final int gradientLevel;
    private static final long serialVersionUID = 1;

    /**
     * @param topColor      Color of the upper part of the background
     * @param bottomColor   Color of the lower part of the background
     * @param gradientLevel Height of the color change
     */
    public GradientPanel(final Color topColor, final Color bottomColor, final int gradientLevel) {
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.gradientLevel = gradientLevel;
    }

    /**
     * @param topColor A new color to be set as topcolor
     */
    public void setTopColor(final Color topColor) {
        this.topColor = topColor;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D graphics2D = (Graphics2D) g;
        final int width = getWidth();
        final int height = getHeight();
        final GradientPaint gradientPaint = new GradientPaint(0, 0, topColor, 0, (float) height / gradientLevel,
                bottomColor);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);
    }

}
