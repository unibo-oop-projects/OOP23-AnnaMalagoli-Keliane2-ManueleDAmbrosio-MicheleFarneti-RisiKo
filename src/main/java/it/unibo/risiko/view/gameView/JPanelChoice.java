package it.unibo.risiko.view.gameView;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.view.gameView.gameViewComponents.ContinuePanel;
import it.unibo.risiko.view.gameView.gameViewComponents.DefaultButton;
/**
 * Creation of the class JPanelChoice which is a panel that shows three different
 * selection cells that contain the territories of the cards owned by the player.
 * This panel is used to play three cards to gain new armies.
 * @author Anna Malagoli 
 */
public class JPanelChoice extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color BLACK = new Color(0, 0, 0);
    private final static int INFO_BOTTON_DIMENSION = 80;
    private final static int BOTTON_DIMENSION = 600;
    private String firstChoice = "";
    private String secondChoice = "";
    private String thirdChoice = "";

    /**
     * Through the constructor the JPanelChoice is set.
     * @param playerCards is the list of cards owned by the player
     */
    JPanelChoice(final List<Card> playerCards, final GameViewObserver observer) {
        this.setLayout(new BorderLayout());
        /*creation of buttonPanel that is a panel that contains the button that shows 
         *the operations that have to be done by the player to play the three cards.
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BLACK);
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(createInfoButton(), BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.NORTH);
        /*Creation of a panel that contains a grid of three colums. In every column 
         *there is a choice selection that shows the name of every territories of the
         *cards owned by the player.
        */
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(BACKGROUND_COLOR);
        choicePanel.setLayout(new GridLayout(1, 3));
        Choice firstChoiceTerritories = new Choice();
        Choice secondChoiceTerritories = new Choice();
        Choice thirdChoiceTerritories = new Choice();
        /*setting of the item in the Choice menu with the name of the territories 
         *of the cards owned by the player and the type of the card.
         */
        String type;
        for (var card : playerCards) {
            type = "";
            if (card.getTypeName().equals("Cannon")) {
                type = "CAN";
            } else {
                if (card.getTypeName().equals("Cavalry")) {
                    type = "CAV";
                } else {
                    if (card.getTypeName().equals("Infantry")) {
                        type = "INF";
                    }
                }
            }
            firstChoiceTerritories.add(card.getTerritoryName() + " " + type);
            secondChoiceTerritories.add(card.getTerritoryName() + " " + type);
            thirdChoiceTerritories.add(card.getTerritoryName() + " " + type);
        }
        choicePanel.add(firstChoiceTerritories);
        choicePanel.add(secondChoiceTerritories);
        choicePanel.add(thirdChoiceTerritories);
        this.add(choicePanel, BorderLayout.CENTER);

        /*creation of a button that if pressed permits to play the three 
        *cards selected by the player.
        */
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        JPanel exitPanel = new ContinuePanel("Exit", BOTTON_DIMENSION, e -> this.setVisible(false));
        exitPanel.setPreferredSize(
                new Dimension(this.getPreferredSize().width / 2, exitPanel.getPreferredSize().height));
        JPanel executePanel = new ContinuePanel("Play selected cards", BOTTON_DIMENSION,
                e -> {
                    final String[] firstTerritoryName = firstChoiceTerritories.getSelectedItem().split(" ");
                    final String[] secondTerritoryName = secondChoiceTerritories.getSelectedItem().split(" ");
                    final String[] thirdTerritoryName = thirdChoiceTerritories.getSelectedItem().split(" ");
                    firstChoice = firstTerritoryName[0];
                    secondChoice = secondTerritoryName[0];
                    thirdChoice = thirdTerritoryName[0];
                    if(true/*checkSelectedItem(srcTerrChoice.getSelectedItem(), dstTerrChoice.getSelectedItem(), Integer.valueOf(choiceNumArmies.getSelectedItem()))*/) {
                        observer.playCards(firstChoice, secondChoice, thirdChoice);
                        this.setVisible(false);
                    }
                    else{
                        String message = "Error of the item selected.\n Retry!";
                        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    });
        executePanel.setPreferredSize(
                new Dimension(this.getPreferredSize().width / 2, executePanel.getPreferredSize().height));
        southPanel.add(exitPanel);
        southPanel.add(executePanel);
        this.add(southPanel, BorderLayout.SOUTH);
        /*JButton but = new JButton("Play selected cards");
        this.add(but, BorderLayout.SOUTH);
        but.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                //dopo che sono stati selezionati i territori delle carte da giocare si clicca sul bottone per 
                //chiamare la funzione nel controller che si occupa, dati i nomi dei territori, di restituire le carte
                //e chiamare metodo deck per effettuare l'operazione se possibile verificando risultato 
                //(se != "" -> errore stampa sul frame messaggio JDialog)
                gestione della scelta 
                final String[] firstTerritoryName = firstChoiceTerritories.getSelectedItem().split(" ");
                final String[] secondTerritoryName = secondChoiceTerritories.getSelectedItem().split(" ");
                final String[] thirdTerritoryName = thirdChoiceTerritories.getSelectedItem().split(" ");
                firstChoice = firstTerritoryName[0];
                secondChoice = secondTerritoryName[0];
                thirdChoice = thirdTerritoryName[0];
                //CHIAMATA DI METODO AL MODEL PER RESTITUIRE LE STRINGHE contenenti i nomi territori selezionati
            }
        });*/
    }

    /**
     * Method to create a button that if clicked opens a JDialog
     * in which are defined the operations that the player has to
     * do to play his cards.
     * @return the button created
     */
    private JButton createInfoButton() {
        JButton infoButton = new DefaultButton("INFO");
        String message = "Choose three territories whose cards\n"
        + "you already own to play those cards."
        + "There are 5 possible combos:\n"
        + "-cannon + infantry + cavalry -> +10 armies\n"
        + "-cannon + cannon + cannon -> +4 armies\n"
        + "-infantry + infantry + infantry -> +6 armies\n"
        + "-cavalry + cavalry + cavalry -> +8 armies\n"
        + "-jolly + two cards of the same type -> +12 armies";
        infoButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(infoButton, message, "How to play the cards", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infoButton.setPreferredSize(new Dimension(INFO_BOTTON_DIMENSION, infoButton.getPreferredSize().height));
        return infoButton;
    }

}
