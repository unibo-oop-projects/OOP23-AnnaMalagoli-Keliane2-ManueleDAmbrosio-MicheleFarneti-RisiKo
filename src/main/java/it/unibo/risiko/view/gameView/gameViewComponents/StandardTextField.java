package it.unibo.risiko.view.gameView.gameViewComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * A standard text field.
 * 
 * @author Manuele D'Ambrosio.
 */

public class StandardTextField extends JTextField{
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final Color TEXT_COLOR = new Color(200, 200, 200);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);

    public StandardTextField(final String text, final int fontSize) {
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setFont(new Font("Copperplate", Font.BOLD, fontSize));
        this.setForeground(TEXT_COLOR);
        this.setBackground(BLACK_COLOR);
        this.setEditable(false);
        this.setText(text);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public StandardTextField(final String text) {
        this(text, DEFAULT_FONT_SIZE);
    }
}
