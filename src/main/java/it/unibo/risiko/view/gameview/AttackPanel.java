package it.unibo.risiko.view.gameview;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.risiko.view.gameview.components.ContinuePanel;
import it.unibo.risiko.view.gameview.components.StandardTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel containing the view of the attack phase.
 * 
 * @author Manuele D'Ambrosio
 */

public class AttackPanel extends JPanel {
    public static final long serialVersionUID = 1L;
    private static final String SEP = File.separator;
    private static final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "dice" + SEP;
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final int DEFAULT_ATTACKING_ARMIES = 1;
    private static final int DEFAULT_MOVING_ARMIES = 1;
    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color SECONDARY_COLOR = new Color(255, 204, 0);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);
    private static final int THICKNESS = 3;
    private static final int DOUBLE = 2;
    private static final int MIN_ATTACKING_ARMIES = 1;
    private static final int MAX_ATTACKING_ARMIES = 3;
    private static final int QUARTER_SIZE_FACTOR = 4;
    private static final int TEN = 10;
    private static final int DICE_ROWS = 3;
    private static final int DICE_COLS = 1;
    private static final int NO_THROWS = 0;

    private final int height;
    private final int width;

    @SuppressFBWarnings(value = "EI2", justification = "observer is intentionally mutable")
    private final transient GameViewObserver observer;
    private List<Integer> attDice;
    private List<Integer> defDice;
    private final String attacking;
    private final String defending;
    private final int attackingTerritoryArmies;
    private int attackersNumber;
    private int armiesToMove;
    private int attackerLostArmies;
    private int defenderLostArmies;

    /**
     * @param height - height of the panel
     * @param width - width of the panel
     * @param attacking - name of the attacking territory
     * @param defending - name of the defender territory
     * @param attackingTerritoryArmies - number of armies in attacking territory 
     * @param observer - observer of the controller
     */
    public AttackPanel(final int height, final int width, final String attacking, final String defending,
            final int attackingTerritoryArmies, final GameViewObserver observer) {
        this.height = height;
        this.width = width;
        this.observer = observer;

        this.attDice = new ArrayList<>();
        this.defDice = new ArrayList<>();
        this.attacking = attacking;
        this.defending = defending;
        this.attackingTerritoryArmies = attackingTerritoryArmies;
        this.attackersNumber = DEFAULT_ATTACKING_ARMIES;
        this.armiesToMove = DEFAULT_MOVING_ARMIES;

        this.setLayout(new BorderLayout());
        this.setSize(width, height);
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        this.add(topPanel(), BorderLayout.NORTH);
        this.add(new ContinuePanel("THROW!", width, e -> {
            observer.setAttackingArmies(attackersNumber);
        }), BorderLayout.SOUTH);
        this.add(sidePanel("Attacker"), BorderLayout.WEST);
        this.add(sidePanel("Defender"), BorderLayout.EAST);

        this.setVisible(false);

    }

    private void increase(final JTextField textValue, final int max) {
        if (attackersNumber < max && attackersNumber < attackingTerritoryArmies - DEFAULT_MOVING_ARMIES) {
            attackersNumber++;
            textValue.setText(Integer.toString(attackersNumber));
        }
    }

    private void decrease(final JTextField textValue, final int min) {
        if (attackersNumber > min) {
            attackersNumber--;
            textValue.setText(Integer.toString(attackersNumber));
        }
    }

    private Font changeFontSize(final int fontSize) {
        return new Font("Arial", Font.BOLD, fontSize);
    }

    private JButton selectorButton(final String text) {
        final JButton button = new JButton();
        button.setFont(changeFontSize(DEFAULT_FONT_SIZE));
        button.setForeground(BLACK_COLOR);
        button.setText(text);
        button.setBackground(SECONDARY_COLOR);
        button.setBorder(BorderFactory.createLineBorder(BLACK_COLOR, THICKNESS));
        button.setPreferredSize(new Dimension(DEFAULT_FONT_SIZE * DOUBLE, DEFAULT_FONT_SIZE * DOUBLE));

        return button;
    }

    private JPanel selectorPanel(final String selectorText) {
        final JPanel selectorPanel = new JPanel();
        final JTextField textField = new StandardTextField(selectorText);
        final JTextField textValue = new StandardTextField(Integer.toString(attackersNumber));
        final JButton decreaser = selectorButton("-");
        final JButton increaser = selectorButton("+");

        decreaser.setEnabled(false);
        decreaser.addActionListener(e -> {
            decrease(textValue, MIN_ATTACKING_ARMIES);
            if (attackersNumber <= MIN_ATTACKING_ARMIES) {
                decreaser.setEnabled(false);
            }
            increaser.setEnabled(true);
        });

        increaser.addActionListener(e -> {
            increase(textValue, MAX_ATTACKING_ARMIES);
            if (attackersNumber >= MAX_ATTACKING_ARMIES || attackersNumber >= attackingTerritoryArmies) {
                increaser.setEnabled(false);
            }
            decreaser.setEnabled(true);
        });

        textValue.setPreferredSize(new Dimension(textValue.getPreferredSize().width * DOUBLE,
                textValue.getPreferredSize().height));
        selectorPanel.setLayout(new FlowLayout());
        selectorPanel.setBackground(BLACK_COLOR);
        selectorPanel.add(textField);
        selectorPanel.add(decreaser);
        selectorPanel.add(textValue);
        selectorPanel.add(increaser);

        return selectorPanel;
    }

    private JPanel titlePanel() {
        final JPanel titlePanel = new JPanel();
        final JTextField textField = new StandardTextField(attacking + " is attacking " + defending);

        titlePanel.setBackground(BLACK_COLOR);
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(textField);

        return titlePanel;
    }

    private JPanel topPanel() {
        final JPanel topPanel = new JPanel();

        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(BLACK_COLOR);
        topPanel.setPreferredSize(new Dimension(width, height / QUARTER_SIZE_FACTOR));
        topPanel.add(titlePanel(), BorderLayout.NORTH);
        topPanel.add(selectorPanel("CHOSE THE NUMBER OF ATTACKING ARMIES: "), BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel sidePanel(final String diceType) {
        final int size = width / QUARTER_SIZE_FACTOR;
        final JPanel sidePanel = new JPanel();
        final String path = PATH + "Standard" + diceType + "Dice.png";

        sidePanel.setBackground(BACKGROUND_COLOR);
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(width / DOUBLE, height));

        try {
            final ImageIcon icon = new ImageIcon(ImageIO.read(new File(path)));
            final Image resizedIcon = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            sidePanel.add(new JLabel(new ImageIcon(resizedIcon)), BorderLayout.CENTER);
        } catch (IOException e) {
            return sidePanel;
        }

        return sidePanel;
    }

    private JPanel resultsPanel() {
        final int panelWidth = width / DOUBLE;
        final int resultHeight = height / TEN;
        final JPanel resultsPanel = new JPanel();
        final JTextField attackerResult = new StandardTextField("LOST: " + attackerLostArmies);
        final JTextField defenderResult = new StandardTextField("LOST: " + defenderLostArmies);
        attackerResult.setPreferredSize(new Dimension(panelWidth, resultHeight));
        defenderResult.setPreferredSize(new Dimension(panelWidth, resultHeight));
        resultsPanel.setBackground(BACKGROUND_COLOR);
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.add(attackerResult, BorderLayout.WEST);
        resultsPanel.add(defenderResult, BorderLayout.EAST);
        return resultsPanel;
    }

    private JPanel dicePanel(final String diceColor) {
        final int panelWidth = width / DOUBLE;
        final int panelHeight = height / DOUBLE;
        final int diceSize = panelHeight / THICKNESS;
        final String diceType = diceColor + "Dice" + "_";
        final JPanel dicePanel = new JPanel();
        List<Integer> dices = new ArrayList<>();

        dicePanel.setLayout(new GridLayout(DICE_ROWS, DICE_COLS));
        dicePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        dicePanel.setBackground(BACKGROUND_COLOR);
        dicePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        if ("Blue".equals(diceColor)) {
            dices = defDice;
        }
        if ("Red".equals(diceColor)) {
            dices = attDice;
        }

        for (final int result : dices) {
            if (result > NO_THROWS) { 
                try {
                    final ImageIcon icon = new ImageIcon(ImageIO
                            .read(new File(PATH + diceType + Integer.toString(result) + ".png")));
                    final Image resizedIcon = icon.getImage().getScaledInstance(diceSize, diceSize, Image.SCALE_SMOOTH);
                    dicePanel.add(new JLabel(new ImageIcon(resizedIcon)), JComponent.CENTER_ALIGNMENT);
                } catch (IOException e) {
                    return dicePanel;
                }
            }
        }

        return dicePanel;
    }

    /**
     * Redraws the panel and shows dice results.
     */
    public void drawDicePanels() {
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(DOUBLE, DICE_COLS));
        this.removeAll();
        this.add(titlePanel(), BorderLayout.NORTH);
        southPanel.add(resultsPanel());
        southPanel.add(new ContinuePanel("CONTINUE!", width, e -> observer.conquerIfPossible()));
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(dicePanel("Red"), BorderLayout.WEST);
        this.add(dicePanel("Blue"), BorderLayout.EAST);
        this.revalidate();
        this.repaint();

    }

    /**
     * Redraws the panel changed in conquer phase.
     */
    public void drawConquerPanel() {
        this.removeAll();
        this.add(conquerPanel(attackersNumber - attackerLostArmies,
                attackingTerritoryArmies - attackerLostArmies - DEFAULT_ATTACKING_ARMIES), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private JPanel conquerPanel(final int minArmies, final int maxArmies) {
        final int midHeightFactor = DOUBLE;
        final int textHeightFactor = TEN / DOUBLE;
        final JPanel conquerPanel = new JPanel();
        final JPanel midPanel = new JPanel();
        final JTextField conquerText = new StandardTextField("CONQUERED: " + defending);
        final JTextField movingArmies = new StandardTextField(Integer.toString(minArmies));
        final JTextField adviceText = new StandardTextField("Select the number of armies to move: ");
        final JButton decreaser = selectorButton("-");
        final JButton increaser = selectorButton("+");

        movingArmies.setPreferredSize(
                new Dimension(movingArmies.getPreferredSize().width * THICKNESS,
                        movingArmies.getPreferredSize().height));
        armiesToMove = minArmies;
        decreaser.setEnabled(false);
        decreaser.addActionListener(e -> {
            if (armiesToMove > minArmies) {
                armiesToMove--;
                movingArmies.setText(Integer.toString(armiesToMove));
            }
            increaser.setEnabled(true);
        });
        increaser.addActionListener(e -> {
            if (armiesToMove < maxArmies) {
                armiesToMove++;
                movingArmies.setText(Integer.toString(armiesToMove));
            }
            decreaser.setEnabled(true);
        });
        midPanel.setLayout(new FlowLayout());
        midPanel.setBackground(BLACK_COLOR);
        midPanel.setPreferredSize(new Dimension(width, height / midHeightFactor));
        midPanel.add(adviceText);
        midPanel.add(decreaser);
        midPanel.add(movingArmies);
        midPanel.add(increaser);

        conquerPanel.setLayout(new BorderLayout());
        conquerPanel.setBackground(BLACK_COLOR);
        conquerText.setPreferredSize(new Dimension(width, height / textHeightFactor));
        conquerPanel.add(conquerText, BorderLayout.NORTH);
        conquerPanel.add(midPanel, BorderLayout.CENTER);
        conquerPanel.add(new ContinuePanel("CLOSE", width, e -> {
            observer.setMovingArmies(armiesToMove);
        }), BorderLayout.SOUTH);

        return conquerPanel;
    }

    /**
     * Sets the result of the attacker dice.
     * 
     * @param attDice - results of attacker dice.
     */
    public void setAtt(final List<Integer> attDice) {
        this.attDice = List.copyOf(attDice);
    }

    /**
     * Sets the result of the defender dice.
     * 
     * @param defDice - results of defender dice.
     */
    public void setDef(final List<Integer> defDice) {
        this.defDice = List.copyOf(defDice);
    }

    /**
     * Sets the number of armies lost by the defender player.
     * 
     * @param defenderLostArmies - number of armies lost
     */
    public void setDefenderLostArmies(final int defenderLostArmies) {
        this.defenderLostArmies = defenderLostArmies;
    }

    /**
     * Sets the number of armies lost by the attacker player.
     * 
     * @param attackerLostArmies - number of armies lost
     */
    public void setAttackerLostArmies(final int attackerLostArmies) {
        this.attackerLostArmies = attackerLostArmies;
    }

}
