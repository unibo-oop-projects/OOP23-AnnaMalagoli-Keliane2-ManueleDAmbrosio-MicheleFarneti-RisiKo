package it.unibo.risiko.view.initview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A panel representing the principal menu's sub menu.
 * 
 * @author Keliane Nana
 */
public class OptionSubMenu extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int PREFERRED_WIDTH = 700;
    private static final int PREFERRED_HEIGHT = 700;
    private static final int DEFAULT_WIDTH_SPACE = 0;
    private static final int DEFAULT_HEIGHT_SPACE = 5;
    private static final int DEFAULT_EMPTY_BORDER = 0;
    private static final int OSM_TOP_EMPTY_BORDER = 350;
    private static final int RISPANE_LEFT_EMPTY_BORDER = 25;
    private static final int RISPANE_RIGHT_EMPTY_BORDER = 21;
    private static final int BUTTON_LATTERAL_EMPTY_BORDER = 12;
    private static final int BUTTON_TRANVERSAL_EMPTY_BORDER = 69;
    private final ButtonGroup grp;
    private final ImageIcon backgroundImage = new ImageIcon(
            "src\\main\\resources\\it\\unibo\\risiko\\images\\background.jpg");

    /**
     * Option's Sub menu innizialisation.
     * 
     * @param p the principal menu of the game
     * @param g the initial view of the game
     */
    public OptionSubMenu(final PrincipalMenu p, final InitialView g) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(OSM_TOP_EMPTY_BORDER, DEFAULT_EMPTY_BORDER, DEFAULT_EMPTY_BORDER,
                DEFAULT_EMPTY_BORDER));
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        // creating risolution button
        final JButton risolution = p.addButtonToMenu("Resolution", this);
        // creating risolution option panel
        final JPanel risPane = p.addPanelToMenu(this);
        this.add(Box.createRigidArea(new Dimension(DEFAULT_WIDTH_SPACE, DEFAULT_HEIGHT_SPACE)));
        grp = createRisolutions(risPane);
        risPane.setLayout(new BoxLayout(risPane, BoxLayout.PAGE_AXIS));
        risPane.setBorder(BorderFactory.createEmptyBorder(DEFAULT_EMPTY_BORDER, RISPANE_LEFT_EMPTY_BORDER,
                DEFAULT_EMPTY_BORDER, RISPANE_RIGHT_EMPTY_BORDER));
        // laying out rules button
        final JButton regolamento = p.addButtonToMenu("Game Rules", this);
        // creation of the panel containing the buttons back and save.
        final JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(0, 0, 0, 0));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        // laying out the buttons back and save
        final JButton back = p.addButtonToMenu("Back", buttonPane);
        buttonPane.add(Box.createRigidArea(new Dimension(DEFAULT_HEIGHT_SPACE * 10, DEFAULT_WIDTH_SPACE)));
        final JButton save = p.addButtonToMenu("Save", buttonPane);
        // adding buttonPane to the OptionMenu
        this.add(Box.createRigidArea(new Dimension(DEFAULT_WIDTH_SPACE, DEFAULT_HEIGHT_SPACE * 3)));
        this.add(buttonPane);
        // button lay out ajustments
        risolution.setBorder(BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER,
                BUTTON_TRANVERSAL_EMPTY_BORDER + 2, BUTTON_LATTERAL_EMPTY_BORDER,
                BUTTON_TRANVERSAL_EMPTY_BORDER + DEFAULT_HEIGHT_SPACE));
        regolamento.setBorder(BorderFactory.createEmptyBorder(BUTTON_LATTERAL_EMPTY_BORDER,
                BUTTON_TRANVERSAL_EMPTY_BORDER, BUTTON_LATTERAL_EMPTY_BORDER, BUTTON_TRANVERSAL_EMPTY_BORDER));
        // adding ActionListeners to buttons
        risolution.addActionListener(e -> risPane.setVisible(true));
        regolamento.addActionListener(e -> showRules());
        save.addActionListener(e -> saveOption(risPane, g));
        back.addActionListener(e -> g.updatePanel(p));
    }

    /**
     * This method shows the game's rules.
     */
    private void showRules() {
        final String rules = getGameRules();
        if (!rules.isEmpty()) {
            // creating a JPanel which will contains the game rules
            final JPanel rulePane = new JPanel();
            rulePane.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
            rulePane.setLayout(new BorderLayout());
            final JTextArea ruleText = new JTextArea(rules);
            final JScrollPane logTextScroller = new JScrollPane(ruleText);
            rulePane.add(logTextScroller);
            // adding rulePane to a messageDialog JOptionPane
            JOptionPane.showMessageDialog(this, rulePane, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
        }
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
     * This method helps to get the game's rules text.
     * 
     * @return a string that contains the rules of the game
     */
    private String getGameRules() {
        final StringBuilder rules = new StringBuilder();
        final String fileName = "src\\main\\resources\\it\\unibo\\risiko\\gameRules\\rules.txt";

        try {
            final InputStream inputStream = new FileInputStream(fileName);
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferReader = new BufferedReader(inputStreamReader);) {
                String line = bufferReader.readLine();
                while (line != null) {
                    rules.append(line).append(System.lineSeparator());
                    line = bufferReader.readLine();
                }
                bufferReader.close();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error while trying to read the file", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error while trying to read the file", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return rules.toString();
    }

    /**
     * Set the GameFrame's risolution with the selected risolution.
     * 
     * @param pane the risolution panel of the OptionSubMenu
     * @param g    the initial view
     */
    private void saveOption(final JPanel pane, final InitialView g) {
        final String screenResolution = grp.getSelection().getActionCommand();
        final String[] ris = screenResolution.split("x");
        g.setResolution(Integer.parseInt(ris[0]), Integer.parseInt(ris[1]));
        pane.setVisible(false);
    }

    /**
     * Creates a radio button and add it to a specific ButtonGroup and panel.
     * 
     * @param g    the ButtonGroup
     * @param p    the JPanel
     * @param text the radioButton's actionComand
     */
    private void createRadioButton(final ButtonGroup g, final JPanel p, final String text) {
        final JRadioButton r = new JRadioButton(text);
        r.setActionCommand(text);
        g.add(r);
        p.add(r);
    }

    /**
     * Creates sub panels that help in laying radio in the rigth way.
     * 
     * @return a panel with a LINE_AXIS BoxLayout
     */
    private JPanel createResolutionSubPanels() {
        final JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        return p1;
    }

    /**
     * This method creates a complete risolution panel.
     * 
     * @param p the panel in which we should add the missing elements
     * @return the ButtonGroup containing all the radio that represent
     *         the possible risolutions
     */
    private ButtonGroup createRisolutions(final JPanel p) {
        final ButtonGroup g = new ButtonGroup();
        final JPanel p1 = createResolutionSubPanels();
        final JPanel p2 = createResolutionSubPanels();
        createRadioButton(g, p1, "1600x900");
        createRadioButton(g, p1, "1500x840");
        createRadioButton(g, p2, "1350x760");
        createRadioButton(g, p2, "1280x720");
        p.add(p1);
        p.add(p2);
        return g;
    }
}
