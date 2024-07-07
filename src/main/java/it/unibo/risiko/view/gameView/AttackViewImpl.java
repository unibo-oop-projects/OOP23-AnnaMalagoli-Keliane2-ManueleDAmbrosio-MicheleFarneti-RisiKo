package it.unibo.risiko.view.gameView;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuele D'Ambrosio
 */

 /*TODO aggiusta i magic numbers */

public class AttackViewImpl {
    private static final String SEP = File.separator;
    private static final String PATH = "build" + SEP + "resources" + SEP + "main" + SEP + "it" + SEP + "unibo" + SEP
            + "risiko" + SEP + "dice";
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final int DEFAULT_ATTACKING_ARMIES = 1;
    private static final int DEFAULT_MOVING_ARMIES = 1;
    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color SECONDARY_COLOR = new Color(255, 204, 0);
    private static final Color TEXT_COLOR = new Color(200, 200, 200);
    private static final Color BLACK_COLOR = new Color(0, 0, 0);

    final int height;
    final int width;

    private JPanel attackPanel;
    private List<Integer> attDice;
    private List<Integer> defDice;
    private String attacking;
    private String defending;
    private int attackingTerritoryArmies;
    private int attackersNumber;
    private int armiesToMove;
    private int attackerLostArmies;
    private boolean territoryConquered;

    public AttackViewImpl(final int height, final int width, final String attacking, final String defending,
            final int attackingTerritoryArmies) {
        this.height = height;
        this.width = width;

        this.attackPanel = new JPanel();
        this.attDice = new ArrayList<>();
        this.defDice = new ArrayList<>();
        this.attacking = attacking;
        this.defending = defending;
        this.attackingTerritoryArmies = attackingTerritoryArmies;
        this.attackersNumber = DEFAULT_ATTACKING_ARMIES;
        this.armiesToMove = DEFAULT_MOVING_ARMIES;
        this.territoryConquered = false;

        attackPanel.setLayout(new BorderLayout());
        attackPanel.setSize(height, width);
        attackPanel.setBackground(BACKGROUND_COLOR);
        attackPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        attackPanel.add(topPanel(), BorderLayout.NORTH);
        attackPanel.add(bottomPanel("THROW!", e -> drawDicePanels()), BorderLayout.SOUTH);
        attackPanel.add(sidePanel("Attacker"), BorderLayout.WEST);
        attackPanel.add(sidePanel("Defender"), BorderLayout.EAST);

    }

    private void increase(final JTextField textValue, final int max) {
        if (attackersNumber < max && attackersNumber < attackingTerritoryArmies) {
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
        return new Font("Copperplate", Font.BOLD, fontSize);
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

    private JTextField standardTextField(final String text) {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(changeFontSize(DEFAULT_FONT_SIZE));
        textField.setForeground(TEXT_COLOR);
        textField.setBackground(BLACK_COLOR);
        textField.setEditable(false);
        textField.setText(text);
        textField.setBorder(BorderFactory.createEmptyBorder());
        return textField;
    }

    private JPanel selectorPanel(String selectorText) {
        final int MIN_ATTACKING_ARMIES = 1;
        final int MAX_ATTACKING_ARMIES = 3;
        JPanel selectorPanel = new JPanel();
        JTextField textField = standardTextField(selectorText);
        JTextField textValue = standardTextField(Integer.toString(attackersNumber));
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
            if (attackersNumber >= MAX_ATTACKING_ARMIES) {
                increaser.setEnabled(false);
            }
            decreaser.setEnabled(true);
        });

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
        JTextField textField = standardTextField(attacking + " is attacking " + defending);

        titlePanel.setBackground(BLACK_COLOR);
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(textField);

        return titlePanel;
    }

    private JPanel topPanel() {
        final int HEIGHT_FACTOR = 6;
        JPanel topPanel = new JPanel();

        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(BLACK_COLOR);
        topPanel.setPreferredSize(new Dimension(width, height / HEIGHT_FACTOR));
        topPanel.add(titlePanel());
        topPanel.add(selectorPanel("CHOSE THE NUMBER OF ATTACKING ARMIES: "));

        return topPanel;
    }

    private JPanel sidePanel(String diceType) {
        final int SIZE_FACTOR = 3;
        final int WIDTH_FACTOR = 2;
        JPanel sidePanel = new JPanel();
        int size = height / SIZE_FACTOR;
        String path = PATH + "Standard" + diceType + "Dice.png";

        sidePanel.setBackground(BACKGROUND_COLOR);
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(width / WIDTH_FACTOR, height));

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(path)));
            Image resizedIcon = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            sidePanel.add(new JLabel(new ImageIcon(resizedIcon)), BorderLayout.CENTER);
        } catch (IOException e) {
            System.out.println(new File(path).getAbsolutePath());
            e.printStackTrace();
        }

        return sidePanel;
    }

    private JPanel bottomPanel(String buttonText, ActionListener e) {
        final int SIZE_FACTOR = 3;
        final int HEIGHT_FACTOR = 2;
        JPanel bottomPanel = new JPanel();
        JButton continueButton = selectorButton(buttonText);

        continueButton.setPreferredSize(new Dimension(width / SIZE_FACTOR, DEFAULT_FONT_SIZE * HEIGHT_FACTOR));
        bottomPanel.setPreferredSize(new Dimension(width, DEFAULT_FONT_SIZE * SIZE_FACTOR));
        bottomPanel.setBackground(BLACK_COLOR);
        bottomPanel.add(continueButton);
        continueButton.addActionListener(e);

        return bottomPanel;
    }

    private JPanel resultsPanel() {
        final int WIDTH_FACTOR = 2;
        final int HEIGHT_FACTOR = 10;
        JPanel resultsPanel = new JPanel();
        JTextField attackerResult = standardTextField("LOST: " + attackerLostArmies);
        JTextField defenderResult = standardTextField("LOST: " + (attackersNumber - attackerLostArmies));
        attackerResult.setPreferredSize(new Dimension(width / WIDTH_FACTOR, height / HEIGHT_FACTOR));
        defenderResult.setPreferredSize(new Dimension(width / WIDTH_FACTOR, height / HEIGHT_FACTOR));
        resultsPanel.setBackground(BACKGROUND_COLOR);
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.add(attackerResult, BorderLayout.WEST);
        resultsPanel.add(defenderResult, BorderLayout.EAST);
        return resultsPanel;
    }

    private JPanel dicePanel(String diceColor) {
        final int WIDTH_FACTOR = 2;
        String diceType = diceColor + "Dice" + "_";
        JPanel dicePanel = new JPanel();
        List<Integer> dices = new ArrayList<>();
        int diceSize = height / 5;

        dicePanel.setLayout(new GridLayout(3, 1));
        dicePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        dicePanel.setBackground(BACKGROUND_COLOR);
        dicePanel.setPreferredSize(new Dimension(width / WIDTH_FACTOR, height));

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

    private boolean drawDicePanels() {
        final int ROWS = 2;
        final int COLS = 1;
        if (attDice.isEmpty() || defDice.isEmpty()) {
            return false;
        } else {
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new GridLayout(ROWS, COLS));
            attackPanel.removeAll();
            attackPanel.add(titlePanel(), BorderLayout.NORTH);
            southPanel.add(resultsPanel());
            southPanel.add(bottomPanel("CONTINUE!", e -> drawConquerPanel()));
            attackPanel.add(southPanel, BorderLayout.SOUTH);
            attackPanel.add(dicePanel("Red"), BorderLayout.WEST);
            attackPanel.add(dicePanel("Blue"), BorderLayout.EAST);
            attackPanel.revalidate();
            attackPanel.repaint();
            return true;
        }
    }

    private boolean drawConquerPanel() {
        if (territoryConquered) {
            attackPanel.removeAll();
            attackPanel.add(conquerPanel(attackersNumber - attackerLostArmies,
                    attackingTerritoryArmies - attackerLostArmies - DEFAULT_ATTACKING_ARMIES), BorderLayout.CENTER);
            attackPanel.revalidate();
            attackPanel.repaint();
            return true;
        }
        return false;
    }

    private JPanel conquerPanel(int minArmies, int maxArmies) {
        final int MID_HEIGHT_FACTOR = 2;
        final int TEXT_HEIGHT_FACTOR = 5;
        JPanel conquerPanel = new JPanel();
        JPanel midPanel = new JPanel();
        JTextField conquerText = standardTextField("CONQUERED: " + defending);
        JTextField movingArmies = standardTextField(Integer.toString(minArmies));
        JTextField adviceText = standardTextField("Select the number of armies to move: ");
        JButton decreaser = selectorButton("-");
        JButton increaser = selectorButton("+");

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
        conquerPanel.add(bottomPanel("CLOSE", null), BorderLayout.SOUTH); // Da sistemare con il controller

        return conquerPanel;
    }

    public void setAtt(int d1, int d2, int d3) {
        attDice = List.of(d1, d2, d3);
    }

    public void setDef(int d1, int d2, int d3) {
        defDice = List.of(d1, d2, d3);
    }

    public int getNumberOfAttackers() {
        return this.attackersNumber;
    }

    public void setAttackersLostArmies(final int attackerLostArmies) {
        this.attackerLostArmies = attackerLostArmies;
    }

    public int getArmiesToMove() {
        return this.armiesToMove;
    }

    public void territoryConquered(final boolean isTerritoryConquered) {
        this.territoryConquered = isTerritoryConquered;
    }

    public JPanel getAttackPanel() {
        return this.attackPanel;
    }
}
