package it.unibo.risiko.view.gameview;

import java.util.Optional;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.view.gameview.components.ContinuePanel;
import it.unibo.risiko.view.gameview.components.DefaultButton;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Creation of a panel JPanelMovementArmies which shows three selection cells:
 * the first to choose the territory from which the player wants to move
 * some armies to a near territory that is the one selected in the second cell;
 * the third cell is used by the player to choose the number of armies that
 * he wants to move. The player can move a maximum of 100 armies, leaving at
 * least one army in the first selected territory.
 * 
 * @author Anna Malagoli
 */
public class JPanelMovementArmies extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color BLACK = new Color(0, 0, 0);
    private static final int BOTTON_DIMENSION = 600;
    private static final int INFO_BOTTON_DIMENSION = 80;
    private static final int MAX_NUM_ARMIES = 100;
    private static final int CHOICE_SIZE = 30;
    private final List<Territory> listTerritories;

    /**
     * Through the constructor the JPanelMovementArmies is set.
     * 
     * @param territories is the list of territories of a player
     * @param observer is the game observer
     */
    public JPanelMovementArmies(final List<Territory> territories, final GameViewObserver observer) {
        this.setLayout(new BorderLayout());
        this.listTerritories = List.copyOf(territories);
        /*
         * creation of buttonPanel that is a panel that contains the button that shows
         * the operations that have to be done by the player to move armies
         * between two near territories.
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BLACK);
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(createInfoButton(), BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.NORTH);
        /*
         * Creation of three Choice. The first two for the selection of the two
         * territories and the last one for the setting of the number of armies that
         * the player wants to move between the two territories selected.
         */
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setLayout(new BorderLayout());
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(BACKGROUND_COLOR);
        choicePanel.setLayout(new GridLayout(1, 2));
        Choice srcTerrChoice = new Choice();
        Choice dstTerrChoice = new Choice();
        Choice choiceNumArmies = new Choice();
        /*setting the size and font of the three Choice */
        srcTerrChoice.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        dstTerrChoice.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        choiceNumArmies.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        /*
         * setting of the item in the Choice menu with the name of the territories
         * owned by the player for the first two Choice. In the third Choice
         * there are numbers between 1 and 100
         */
        for (var terr : territories) {
            srcTerrChoice.add(terr.getTerritoryName());
            dstTerrChoice.add(terr.getTerritoryName());
        }
        for (int i = 1; i <= MAX_NUM_ARMIES; i++) {
            choiceNumArmies.add(String.valueOf(i));
        }
        choicePanel.add(srcTerrChoice);
        choicePanel.add(dstTerrChoice);
        centerPanel.add(choicePanel, BorderLayout.CENTER);
        centerPanel.add(choiceNumArmies, BorderLayout.LINE_END);
        this.add(centerPanel, BorderLayout.CENTER);

        /*
         * creation of one button that if pressed permits to move
         * the selected amount of armies between the two territories,
         * and of another botton used to exit from the current displayed panel.
         */
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        JPanel exitPanel = new ContinuePanel("Exit", BOTTON_DIMENSION, e -> observer.closeMovementPhase());
        JPanel executePanel = new ContinuePanel("Move armies", BOTTON_DIMENSION,
                e -> {
                    if (checkSelectedItem(srcTerrChoice.getSelectedItem(), dstTerrChoice.getSelectedItem(),
                            Integer.parseInt(choiceNumArmies.getSelectedItem()))) {
                        observer.moveArmies(srcTerrChoice.getSelectedItem(), dstTerrChoice.getSelectedItem(),
                                Integer.parseInt(choiceNumArmies.getSelectedItem()));
                        this.setVisible(false);
                    } else {
                        String message = "Error of the item selected.\n Retry!";
                        JOptionPane.showMessageDialog(this, message, "", JOptionPane.ERROR_MESSAGE);
                    }
                });
        southPanel.add(exitPanel);
        southPanel.add(executePanel);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Method to create a button that if clicked opens a JDialog
     * in which are defined the operations that the player has to
     * do to move a certain amount of armies between two adjacent territories.
     * 
     * @return the info button created
     */
    private JButton createInfoButton() {
        JButton infoButton = new DefaultButton("INFO");
        String message = "In the first selection cell choose the territory\n"
                + "whom armies you want to move.\n"
                + "In the second choose the territory\n"
                + "in which you want to place the armies.\n"
                + "In the third select the numeber of armies\n"
                + "you want to move from a minimum of 1\n"
                + "to a maximum of 100.\n"
                + "Remember to leave at least one army in\n"
                + "the first territory, otherwise the operation\n"
                + "will not succeed.";
        infoButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(infoButton, message, "How to move armies",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infoButton.setPreferredSize(new Dimension(INFO_BOTTON_DIMENSION, infoButton.getPreferredSize().height));
        return infoButton;
    }

    /**
     * Method used to verify if it is possible to move a certain amount of armies
     * between two territories of the player.
     * 
     * @param src is the name of the source territory
     * @param dst is the name of the destination territory
     * @param numArmies is the number of armies that the player wants to move
     * @return true if the operation is permitted, false otherwise
     */
    private boolean checkSelectedItem(final String src, final String dst, final int numArmies) {
        Territory srcTerritory = getTerritory(src).get();
        Territory dstTerritory = getTerritory(dst).get();
        if (territoriesAreNear(srcTerritory, dstTerritory) && checkNumberArmies(srcTerritory, numArmies)) {
            return true;
        }
        return false;
    }

    /**
     * Method to get the territory from the list of territories of the player
     * by its name.
     * 
     * @param terr is the name of the territory searched
     * @return an optional that always contains the territory searched
     */
    private Optional<Territory> getTerritory(final String terr) {
        for (var territory : this.listTerritories) {
            if (territory.getTerritoryName().equals(terr)) {
                return Optional.of(territory);
            }
        }
        return Optional.empty();
    }

    /**
     * Method used to verify if two territories owned by the player are adjacent.
     * 
     * @param terr1 is the first territory
     * @param terr2 is the second territory
     * @return true if the territories are adjacent, false otherwise
     */
    private boolean territoriesAreNear(final Territory terr1, final Territory terr2) {
        for (var elem : terr1.getListOfNearTerritories()) {
            if (elem.equals(terr2.getTerritoryName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to verify if there are enough armies in the source territory
     * to permit the movement of a certain amount of its armies to an adjacent
     * territory.
     * 
     * @param terr is the name of the territory which the player wants
     * to remove a certain amount of armies to place them in an
     * adjacent territory
     * @param numArmies is the number of armies that the player wants to move
     * @return true if the number of armies that the player wants to move is less
     * than the number of armies placed in the territory, false otherwise
     */
    private boolean checkNumberArmies(final Territory terr, final int numArmies) {
        if (terr.getNumberOfArmies() > numArmies) {
            return true;
        }
        return false;
    }
}
