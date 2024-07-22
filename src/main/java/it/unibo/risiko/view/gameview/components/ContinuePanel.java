package it.unibo.risiko.view.gameview.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A simple panel with a single button at the centre.
 * 
 * @author Manuele D'Ambrosio
 */

public class ContinuePanel extends JPanel {
    public static final long serialVersionUID = 1L;
    private static final Color BLACK_COLOR = new Color(0, 0, 0);
    private static final int BUTTON_WIDTH_FACTOR = 3;
    private static final int BUTTON_HEIGHT = 40;
    private static final int DEFAULT_HEIGHT = 60;

    /** 
     * @param buttonText - text of the central button.
     * @param width - width of the panel.
     * @param e - action listener of the button.
     */
    public ContinuePanel(final String buttonText, final int width, final ActionListener e) {
        final JButton continueButton = new DefaultButton(buttonText);
        continueButton.setPreferredSize(new Dimension(width / BUTTON_WIDTH_FACTOR, BUTTON_HEIGHT));
        continueButton.addActionListener(e);
        this.setPreferredSize(new Dimension(width, DEFAULT_HEIGHT));
        this.setBackground(BLACK_COLOR);
        this.add(continueButton);
    }
}
