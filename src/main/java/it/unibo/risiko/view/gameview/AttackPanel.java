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
 * @author Manuele D'Ambrosio
 */

public class AttackPanel extends JPanel {
    private static final String SEP = File.separator;
    private static final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "dice" + SEP;
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final int DEFAULT_ATTACKING_ARMIES = 1;
    private static final int DEFAULT_MOVING_ARMIES = 1;
    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color SECONDARY_COLOR = new Color(255, 204, 0);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);

    final int height;
    final int width;

    @SuppressFBWarnings(value = "EI2", justification = "observer is intentionally mutable")
    private final GameViewObserver observer;
    private List<Integer> attDice;
    private List<Integer> defDice;
    private String attacking;
    private String defending;
    private int attackingTerritoryArmies;
    private int attackersNumber;
    private int armiesToMove;
    private int attackerLostArmies;
    private int defenderLostArmies;

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

    private Font changeFontSize(int fontSize) {
        return new Font("Arial", Font.BOLD, fontSize);
    }

    private JButton selectorButton(final String text) {
        final int THICKNESS = 3;
        final int SIZE_FACTOR = 2;
        JButton button = new JButton();
        button.setFont(changeFontSize(DEFAULT_FONT_SIZE));
        button.setForeground(BLACK_COLOR);
        button.setText(text);
        button.setBackground(SECONDARY_COLOR);
        button.setBorder(BorderFactory.createLineBorder(BLACK_COLOR, THICKNESS));
        button.setPreferredSize(new Dimension(DEFAULT_FONT_SIZE * SIZE_FACTOR, DEFAULT_FONT_SIZE * SIZE_FACTOR));

        return button;
    }

    private JPanel selectorPanel(String selectorText) {
        final int TEXT_VALUE_WIDTH = 2;
        final int MIN_ATTACKING_ARMIES = 1;
        final int MAX_ATTACKING_ARMIES = 3;
        JPanel selectorPanel = new JPanel();
        JTextField textField = new StandardTextField(selectorText);
        JTextField textValue = new StandardTextField(Integer.toString(attackersNumber));
        JButton decreaser = selectorButton("-");
        JButton increaser = selectorButton("+");

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

        textValue.setPreferredSize(new Dimension(textValue.getPreferredSize().width * TEXT_VALUE_WIDTH,
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
        JPanel titlePanel = new JPanel();
        JTextField textField = new StandardTextField(attacking + " is attacking " + defending);

        titlePanel.setBackground(BLACK_COLOR);
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(textField);

        return titlePanel;
    }

    private JPanel topPanel() {
        final int HEIGHT = height / 4;
        JPanel topPanel = new JPanel();

        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(BLACK_COLOR);
        topPanel.setPreferredSize(new Dimension(width, HEIGHT));
        topPanel.add(titlePanel(), BorderLayout.NORTH);
        topPanel.add(selectorPanel("CHOSE THE NUMBER OF ATTACKING ARMIES: "), BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel sidePanel(String diceType) {
        final int SIZE = width / 4;
        final int WIDTH = width / 2;
        JPanel sidePanel = new JPanel();
        String path = PATH + "Standard" + diceType + "Dice.png";

        sidePanel.setBackground(BACKGROUND_COLOR);
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(WIDTH, height));

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(path)));
            Image resizedIcon = icon.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
            sidePanel.add(new JLabel(new ImageIcon(resizedIcon)), BorderLayout.CENTER);
        } catch (IOException e) {
            System.out.println(new File(path).getAbsolutePath());
            e.printStackTrace();
        }

        return sidePanel;
    }

    private JPanel resultsPanel() {
        final int WIDTH = width / 2;
        final int HEIGHT = height / 10;
        JPanel resultsPanel = new JPanel();
        JTextField attackerResult = new StandardTextField("LOST: " + attackerLostArmies);
        JTextField defenderResult = new StandardTextField("LOST: " + defenderLostArmies);
        attackerResult.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        defenderResult.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        resultsPanel.setBackground(BACKGROUND_COLOR);
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.add(attackerResult, BorderLayout.WEST);
        resultsPanel.add(defenderResult, BorderLayout.EAST);
        return resultsPanel;
    }

    private JPanel dicePanel(String diceColor) {
        final int WIDTH = width / 2;
        final int HEIGHT = height / 2;
        String diceType = diceColor + "Dice" + "_";
        JPanel dicePanel = new JPanel();
        List<Integer> dices = new ArrayList<>();
        final int diceSize = HEIGHT / 3;

        dicePanel.setLayout(new GridLayout(3, 1));
        dicePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        dicePanel.setBackground(BACKGROUND_COLOR);
        dicePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        if (diceColor.equals("Blue")) {
            dices = defDice;
        }
        if (diceColor.equals("Red")) {
            dices = attDice;
        }

        for (int result : dices) {
            if (result > 0) {
                try {
                    ImageIcon icon = new ImageIcon(ImageIO
                            .read(new File(PATH + diceType + Integer.toString(result) + ".png")));
                    Image resizedIcon = icon.getImage().getScaledInstance(diceSize, diceSize, Image.SCALE_SMOOTH);
                    dicePanel.add(new JLabel(new ImageIcon(resizedIcon)), JComponent.CENTER_ALIGNMENT);
                } catch (IOException e) {
                    System.out.println(new File(PATH + diceType + Integer.toString(result) + ".png")
                            .getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }

        return dicePanel;
    }

    public void drawDicePanels() {
        final int ROWS = 2;
        final int COLS = 1;
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(ROWS, COLS));
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

    public void drawConquerPanel() {
        this.removeAll();
        this.add(conquerPanel(attackersNumber - attackerLostArmies,
                attackingTerritoryArmies - attackerLostArmies - DEFAULT_ATTACKING_ARMIES), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private JPanel conquerPanel(int minArmies, int maxArmies) {
        final int TEXT_WIDTH_FACTOR = 3;
        final int MID_HEIGHT_FACTOR = 2;
        final int TEXT_HEIGHT_FACTOR = 5;
        JPanel conquerPanel = new JPanel();
        JPanel midPanel = new JPanel();
        JTextField conquerText = new StandardTextField("CONQUERED: " + defending);
        JTextField movingArmies = new StandardTextField(Integer.toString(minArmies));
        JTextField adviceText = new StandardTextField("Select the number of armies to move: ");
        JButton decreaser = selectorButton("-");
        JButton increaser = selectorButton("+");

        movingArmies.setPreferredSize(
                new Dimension(movingArmies.getPreferredSize().width * TEXT_WIDTH_FACTOR,
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
        midPanel.setPreferredSize(new Dimension(width, height / MID_HEIGHT_FACTOR));
        midPanel.add(adviceText);
        midPanel.add(decreaser);
        midPanel.add(movingArmies);
        midPanel.add(increaser);

        conquerPanel.setLayout(new BorderLayout());
        conquerPanel.setBackground(BLACK_COLOR);
        conquerText.setPreferredSize(new Dimension(width, height / TEXT_HEIGHT_FACTOR));
        conquerPanel.add(conquerText, BorderLayout.NORTH);
        conquerPanel.add(midPanel, BorderLayout.CENTER);
        conquerPanel.add(new ContinuePanel("CLOSE", width, e -> {
            observer.setMovingArmies(armiesToMove);
        }), BorderLayout.SOUTH);

        return conquerPanel;
    }

    public void setAtt(final List<Integer> attDice) {
        this.attDice = List.copyOf(attDice);
    }

    public void setDef(final List<Integer> defDice) {
        this.defDice = List.copyOf(defDice);
    }

    public void setDefenderLostArmies(final int defenderLostArmies) {
        this.defenderLostArmies = defenderLostArmies;
    }

    public void setAttackerLostArmies(final int attackerLostArmies) {
        this.attackerLostArmies = attackerLostArmies;
    }

}
