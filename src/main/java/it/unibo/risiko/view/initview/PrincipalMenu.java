package it.unibo.risiko.view.initview;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A panel which represents the principal menu.
 * 
 * @author Keliane Nana
 */
public class PrincipalMenu extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String SEP = File.separator;
    private static final int PREFERRED_WIDTH = 1600;
    private static final int PREFERRED_HEIGHT = 900;
    private static final int DEFAULT_EMPTY_BORDER = 0;
    private static final int PM_TOP_EMPTY_BORDER = 350;
    private static final int BUTTON_LATTERAL_EMPTY_BORDER = 12;
    private static final int BUTTON_TRANVERSAL_EMPTY_BORDER = 69;
    private static final int DEFAULT_WIDTH_SPACE = 0;
    private static final int DEFAULT_HEIGHT_SPACE = 5;
    private final ImageIcon backgroundImage = new ImageIcon(
            "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP + "risiko" + SEP + "images"
                    + SEP + "background.jpg");

    /**
     * Inizializing the principal menu.
     * 
     * @param f          the initial view
     * @param controller the game controller
     */
    public PrincipalMenu(final InitialView f, final InitialViewObserver controller) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(PM_TOP_EMPTY_BORDER, DEFAULT_EMPTY_BORDER, DEFAULT_EMPTY_BORDER,
                DEFAULT_EMPTY_BORDER));
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        // adding JButton to the PrincipalMenu
        final JButton newGame = addButtonToMenu("New Game", this);
        final JButton continueSavedGame = addButtonToMenu("Continue", this);
        final JButton option = addButtonToMenu("Option", this);
        final JButton quit = addButtonToMenu("Quit", this);
        // setting the border of the JButton
        newGame.setBorder(
                BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER,
                        BUTTON_TRANVERSAL_EMPTY_BORDER - (DEFAULT_HEIGHT_SPACE * 4 - 1),
                        BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER - (DEFAULT_HEIGHT_SPACE * 4 - 1)));
        continueSavedGame.setBorder(
                BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER,
                        BUTTON_TRANVERSAL_EMPTY_BORDER - DEFAULT_HEIGHT_SPACE * 3,
                        BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER - DEFAULT_HEIGHT_SPACE * 3));
        option.setBorder(BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER,
                BUTTON_TRANVERSAL_EMPTY_BORDER - 8, BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER - 8));
        quit.setBorder(BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER,
                BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER));
        // adding actionListeners to the JButton
        newGame.addActionListener(a -> startNewGame(f, controller));
        continueSavedGame.addActionListener(a -> continueSavedGame(f, controller));
        option.addActionListener(a -> showOptionPanel(f));
        quit.addActionListener(e -> {
            final int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit?", "Impostazioni Risoluzione",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == 0) {
                f.unshow();
            }
        });
    }

    /**
     * Helps to set the background image of the panel.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * This method helps to update the gameFrame by showing
     * the OptionSubMenu panel.
     * 
     * @param gameFrame the initial view
     */
    private void showOptionPanel(final InitialView gameFrame) {
        gameFrame.updatePanel(new OptionSubMenu(this, gameFrame));
    }

    /**
     * this method organise everything so that a saved game
     * can continue.
     * 
     * @param gameFrame
     * @param controller
     */
    private void continueSavedGame(final InitialView gameFrame, final InitialViewObserver controller) {
        final var dim = gameFrame.getFrameRisolution();
        gameFrame.unshow();
        controller.startGameWindow(dim.width, dim.height);
        controller.continueGame();
    }

    /**
     * This method starts a new game.
     * 
     * @param gameFrame
     * @param controller
     */
    private void startNewGame(final InitialView gameFrame, final InitialViewObserver controller) {
        final var dim = gameFrame.getFrameRisolution();
        gameFrame.unshow();
        controller.startGameWindow(dim.width, dim.height);
        controller.initializeNewGame();
    }

    /**
     * Method used to add a JButton in a Container.
     * 
     * @param name      the JButton text
     * @param container the Container in which we want to make
     *                  the insertion
     * @return the added JButton
     */
    protected final JButton addButtonToMenu(final String name, final Container container) {
        final JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(DEFAULT_WIDTH_SPACE, DEFAULT_HEIGHT_SPACE)));
        return button;
    }

    /**
     * Used to add a JPanel in a Container.
     * 
     * @param c the Container in which we want to make
     *          the insertion
     * @return the added JPanel
     */
    protected final JPanel addPanelToMenu(final Container c) {
        final JPanel pane = new JPanel();
        pane.setVisible(false);
        c.add(pane);
        return pane;
    }

}
