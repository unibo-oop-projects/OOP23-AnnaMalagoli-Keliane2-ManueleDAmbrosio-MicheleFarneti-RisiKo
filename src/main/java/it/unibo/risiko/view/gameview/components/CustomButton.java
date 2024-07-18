package it.unibo.risiko.view.gameview.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JButton;

public class CustomButton extends JButton{
    private static final Color foregroundColor = new Color(63,58,20); 
    private static final Color backgroundColor_pressed = new Color(235,184,0); 
    private static final Color backgroundColor = new Color(255,204,0);
    private static final int FONT_SIZE = 26;

    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        setForeground(foregroundColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
       
        // Set gradient background
        if (getModel().isPressed()) {
            g2d.setColor(backgroundColor_pressed);
        } else {
            g2d.setColor(backgroundColor);
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Draw text
        super.paintComponent(g);
        g2d.dispose();
    }
}
