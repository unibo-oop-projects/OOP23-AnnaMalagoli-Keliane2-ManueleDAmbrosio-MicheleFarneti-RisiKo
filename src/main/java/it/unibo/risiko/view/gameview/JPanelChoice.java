package it.unibo.risiko.view.gameview;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Optional;
import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.view.gameview.components.ContinuePanel;
import it.unibo.risiko.view.gameview.components.DefaultButton;

/**
 * Creation of the class JPanelChoice which is a panel that shows three
 * different selection cells that contain the territories of the cards
 * owned by the player.
 * This panel is used to play three cards to gain new armies.
 * 
 * @author Anna Malagoli
 */
public class JPanelChoice extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(63, 58, 20);
    private static final Color BLACK = new Color(0, 0, 0);
    private static final int INFO_BOTTON_DIMENSION = 80;
    private static final int BOTTON_DIMENSION = 600;
    private static final int CHOICE_SIZE = 20;
    private String firstChoice = "";
    private String secondChoice = "";
    private String thirdChoice = "";
    private final List<Card> listCards;

    /**
     * Through the constructor the JPanelChoice is set.
     * 
     * @param playerCards is the list of cards owned by the player
     * @param observer    is the game observer
     */
    JPanelChoice(final List<Card> playerCards, final GameViewObserver observer) {
        this.setLayout(new BorderLayout());
        this.listCards = playerCards;
        /*
         * creation of buttonPanel that is a panel that contains the button that shows
         * the operations that have to be done by the player to play the three cards.
         */
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BLACK);
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(createInfoButton(), BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.NORTH);
        /*
         * Creation of a panel that contains a grid of three colums. In every column
         * there is a choice selection that shows the name of every territories of the
         * cards owned by the player.
         */
        final JPanel choicePanel = new JPanel();
        choicePanel.setBackground(BACKGROUND_COLOR);
        choicePanel.setLayout(new GridLayout(1, 3));
        final Choice firstChoiceTerritories = new Choice();
        final Choice secondChoiceTerritories = new Choice();
        final Choice thirdChoiceTerritories = new Choice();
        /*setting the size and font of the three Choice */
        firstChoiceTerritories.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        secondChoiceTerritories.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        thirdChoiceTerritories.setFont(new Font("Verdana", Font.PLAIN, CHOICE_SIZE));
        /*
         * setting of the item in the Choice menu with the name of the territories
         * of the cards owned by the player and the type of the card.
         */
        String type;
        for (final var card : playerCards) {
            type = "";
            if ("Cannon".equals(card.getTypeName())) {
                type = "CAN";
            } else {
                if ("Cavalry".equals(card.getTypeName())) {
                    type = "CAV";
                } else {
                    if ("Infantry".equals(card.getTypeName())) {
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

        /*
         * creation of one button that if pressed permits to play the three
         * cards selected by the player, and of another button used to exit from the
         * current panel displayed.
         */
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        final JPanel exitPanel = new ContinuePanel("Exit", BOTTON_DIMENSION, e -> observer.exitCardsManagingPhase());
        final JPanel executePanel = new ContinuePanel("Play selected cards", BOTTON_DIMENSION,
                e -> {
                    final String[] firstTerritoryName = firstChoiceTerritories.getSelectedItem().split(" ");
                    final String[] secondTerritoryName = secondChoiceTerritories.getSelectedItem().split(" ");
                    final String[] thirdTerritoryName = thirdChoiceTerritories.getSelectedItem().split(" ");
                    firstChoice = firstTerritoryName[0];
                    secondChoice = secondTerritoryName[0];
                    thirdChoice = thirdTerritoryName[0];
                    final List<Card> selectedCards = List.of(getSelectedItem(firstChoice).get(),
                            getSelectedItem(secondChoice).get(), getSelectedItem(thirdChoice).get());
                    if (checkSelectedCards(selectedCards)) {
                        observer.playCards(firstChoice, secondChoice, thirdChoice);
                        this.setVisible(false);
                    } else {
                        final String message = "Error of the item selected.\n Retry!";
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
     * do to play his cards.
     * 
     * @return the button created
     */
    private JButton createInfoButton() {
        final JButton infoButton = new DefaultButton("INFO");
        final String message = "Choose three territories whose cards\n"
                + "you already own to play those cards."
                + "There are 5 possible combos:\n"
                + "-cannon + infantry + cavalry -> +10 armies\n"
                + "-cannon + cannon + cannon -> +4 armies\n"
                + "-infantry + infantry + infantry -> +6 armies\n"
                + "-cavalry + cavalry + cavalry -> +8 armies\n"
                + "-jolly + two cards of the same type -> +12 armies";
        infoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(infoButton, message, "How to play the cards",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infoButton.setPreferredSize(new Dimension(INFO_BOTTON_DIMENSION, infoButton.getPreferredSize().height));
        return infoButton;
    }

    /**
     * Method used to get a card from the name of the
     * territory in the list of cards of the player.
     * 
     * @param cardName is the name of the territory
     * @return an empty optional if case the player does not
     *         have the card or an optional that contains the required card.
     *         In this case the card is always found because in the choice cells
     *         are only present the cards of the player.
     */
    private Optional<Card> getSelectedItem(final String cardName) {
        for (final var card : this.listCards) {
            if (card.getTerritoryName().equals(cardName)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    /**
     * Method used to verify if the three cards selected are playable.
     * 
     * @param cards is the list of cards selected by the player
     * @return true if the cards can be played, false otherwise
     */
    private boolean checkSelectedCards(final List<Card> cards) {
        return cardsAreDifferent(cards) && checkCombo(cards);
    }

    /**
     * Method to verify if the three cards are different.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are different or false if at least two cards
     *              * are equal
     */
    private boolean cardsAreDifferent(final List<Card> cards) {

        return !cards.get(0).getTerritoryName().equals(cards.get(1).getTerritoryName())
            || !cards.get(0).getTerritoryName().equals(cards.get(2).getTerritoryName())
            || !cards.get(1).getTerritoryName().equals(cards.get(2).getTerritoryName());
    }

    /**
     * Method used to verify if the three cards selected by the player are a combo.
     * 
     * @param cards is the list of cards
     * @return true if the cards are a combo, false if not
     */
    private boolean checkCombo(final List<Card> cards) {
        int contCav = 0;
        int contJolly = 0;
        int contInf = 0;
        int contCan = 0;

        for (final var card : cards) {
            if ("Cannon".equals(card.getTypeName())) {
                contCan++;
            }
            if ("Cavalry".equals(card.getTypeName())) {
                contCav++;
            }
            if ("Infantry".equals(card.getTypeName())) {
                contInf++;
            }
            if ("Jolly".equals(card.getTypeName())) {
                contJolly++;
            }
        }

        if (contCan == 3 || contCav == 3 || contInf == 3) {
            return true;
        }
        if (contJolly == 1 && (contCan == 2 || contCav == 2 || contInf == 2)) {
                return true;
        }

        return contCan == 1 && contInf == 1 && contCav == 1;
    }

}
