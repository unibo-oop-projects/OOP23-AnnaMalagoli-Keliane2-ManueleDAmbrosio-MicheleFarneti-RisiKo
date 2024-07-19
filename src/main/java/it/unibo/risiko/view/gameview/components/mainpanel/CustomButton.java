package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JButton;

/**
 * JButton customization used for having a standrad button to be used within the
 * context of the attack bar in the game frame.
 * 
 * @author Michele Farneti
 */
public final class CustomButton extends JButton {
    private static final long serialVersionUID = 1;
    private static final Color FOREGROUND_COLOR = new Color(63, 58, 20);
    private static final Color BACKGROUND_COLOR_PRESSED = new Color(235, 184, 0);
    private static final Color BACKGROUND_COLOR = new Color(255, 204, 0);
    private static final int FONT_SIZE = 26;

    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    /**
     * Creates a JButton with a pre-customised style.
     * 
     * @param text The text to be set for the button
     */
    public CustomButton(final String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        setForeground(FOREGROUND_COLOR);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        if (getModel().isPressed()) {
            g2d.setColor(BACKGROUND_COLOR_PRESSED);
        } else {
            g2d.setColor(BACKGROUND_COLOR);
        }
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
        super.paintComponent(g);
        g2d.dispose();
    }
}
