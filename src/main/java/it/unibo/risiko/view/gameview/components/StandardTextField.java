package it.unibo.risiko.view.gameview.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * A standard text field.
 * 
 * @author Manuele D'Ambrosio.
 */

public class StandardTextField extends JTextField {
    public static final long serialVersionUID = 1L;
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final Color TEXT_COLOR = new Color(200, 200, 200);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);

    /**
     * @param text - text of the text field.
     * @param fontSize - text font size.
     */
    public StandardTextField(final String text, final int fontSize) {
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setFont(new Font("Copperplate", Font.BOLD, fontSize));
        this.setForeground(TEXT_COLOR);
        this.setBackground(BLACK_COLOR);
        this.setEditable(false);
        this.setText(text);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Font size is set by default.
     * 
     * @param text text of the text field
     */
    public StandardTextField(final String text) {
        this(text, DEFAULT_FONT_SIZE);
    }
}
