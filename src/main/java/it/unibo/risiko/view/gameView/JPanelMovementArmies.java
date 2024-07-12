package it.unibo.risiko.view.gameView;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Creation of a panel JPanelMovementArmies which shows three selection cells:
 * the first to choose the territory from which the player wants to move
 * some armies to a near territory that is the one selected in the second cell;
 * the third cell is used by the player to choose the number of armies that
 * he wants to move. The player can move a maximum of 100 armies, leaving at
 * least one army in the first selected territory.
 * @author Anna Malagoli 
 */
public class JPanelMovementArmies extends JPanel {

    private String srcTerritory = "";
    private String dstTerritory = "";
    private int numArmies;
    private int MAX_NUM_ARMIES = 100;
    /*input viene passato al costruttore la lista dei territori(jolly esclusi)*/
    /**
     * Through the constructor the JPanelMovementArmies is set.
     * @param territories is the list of territories of a player
     */
    public JPanelMovementArmies(final List<String> territories) {
        this.setLayout(new BorderLayout());
        /*creation of buttonPanel that is a panel that contains the button that shows 
         *the operations that have to be done by the player to move armies
         *between two near territories.
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(createInfoButton(), BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.NORTH);
        /*Creation of three Choice. The first two for the selection of the two
         * territories and the last one for the setting of the number of armies that 
         * the player wants to move between the two territories selected.
        */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new GridLayout(1, 2));
        Choice srcTerrChoice = new Choice();
        Choice dstTerrChoice = new Choice();
        Choice choiceNumArmies = new Choice();
        for (var terr : territories) {
            srcTerrChoice.add(terr);
            dstTerrChoice.add(terr);
        }
        for (int i = 1; i <= MAX_NUM_ARMIES; i++) {
            choiceNumArmies.add(String.valueOf(i));
        }
        choicePanel.add(srcTerrChoice);
        choicePanel.add(dstTerrChoice);
        centerPanel.add(choicePanel, BorderLayout.CENTER);
        centerPanel.add(choiceNumArmies, BorderLayout.LINE_END);
        this.add(centerPanel, BorderLayout.CENTER);

        /*creation of a button that if pressed permits to move
        * the selected amount of armies between the two territories.
        */
        JButton playButton = new JButton("Move armies");
        this.add(playButton, BorderLayout.SOUTH);
        playButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                srcTerritory = srcTerrChoice.getSelectedItem();
                dstTerritory = dstTerrChoice.getSelectedItem();
                numArmies = Integer.valueOf(choiceNumArmies.getSelectedItem());
                //METODO PER RITORNARE al controller i nomi dei territori e numero armate selezionate dal giocatore.
            }
        });
    }

    /**
     * Method to create a button that if clicked opens a JDialog
     * in which are defined the operations that the player has to
     * do to move a certain amount of armies between two adjacent territories.
     * @return the info button created
     */
    private JButton createInfoButton() {
        Icon infoIcon = new ImageIcon("src\\main\\java\\it\\unibo\\risiko\\resources\\images\\infoImage.png");
        JButton infoButton = new JButton(infoIcon);
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
                JOptionPane.showMessageDialog(infoButton, message, "How to move armies", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return infoButton;
    }
}
