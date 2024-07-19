package it.unibo.risiko.view.gameview.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * A Default button.
 * @author Manuele D'Ambrosio.
 */

public class DefaultButton extends JButton {
    public static final long serialVersionUID = 1L;
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final Color SECONDARY_COLOR = new Color(255, 204, 0);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);
    private static final int DEFAULT_DIMENSION_FACTOR = 2;

    /**
     * @param text - text of the button.
     * @param fontSize - font size of the text of the button.
     */
    public DefaultButton(final String text, final int fontSize) {
        this.setFont(new Font("Copperplate", Font.BOLD, fontSize));
        this.setForeground(BLACK_COLOR);
        this.setText(text);
        this.setBackground(SECONDARY_COLOR);
        this.setBorder(BorderFactory.createLineBorder(BLACK_COLOR, 3));
        this.setPreferredSize(new Dimension(DEFAULT_FONT_SIZE * DEFAULT_DIMENSION_FACTOR,
                DEFAULT_FONT_SIZE * DEFAULT_DIMENSION_FACTOR));
    }

    /**
     * Font size is selected by default.
     * 
     * @param text - text of the button.
     */
    public DefaultButton(final String text) {
        this(text, DEFAULT_FONT_SIZE);
    }
}
