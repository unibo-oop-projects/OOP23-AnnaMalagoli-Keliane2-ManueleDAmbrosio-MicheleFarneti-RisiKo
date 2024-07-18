package it.unibo.risiko.model.cards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import it.unibo.risiko.model.player.Player;

/**
 * An implementation of the interface Deck.
 * 
 * @author Anna Malagoli
 */
public class DeckImpl implements Deck {

    private static final int COMBO_THREE_CANNON = 4;
    private static final int COMBO_THREE_INFANTRY = 6;
    private static final int COMBO_THREE_CAVALRY = 8;
    private static final int COMBO_ONE_OF_EACH = 10;
    private static final int COMBO_JOLLY = 12;
    private static final int BONUS_ARMIES = 2;
    private final List<Card> listCards = new ArrayList<>();

    /**
     * Constructor used to initialize the starting deck at the beginning of the game
     * by reading a text file that contains all the informations of a card:
     * the name of the territory and the type name of the card.
     * If a problem does happend (because the file is not find or the program is not
     * able
     * to read the text) an exception is thrown and the list of cards is set empty
     * 
     * @param filePath is the relative path of the text file that contains
     *                 all the information of the cards
     */
    public DeckImpl(final String filePath) {
        String territory;
        String typeCard;
        final File file = new File(filePath);
        final String absoluteFilePath = file.getAbsolutePath();
        try {
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
                String stringRow = bufferedReader.readLine();
                while (stringRow != null) {
                    Card card;
                    final String[] cardData = stringRow.split(" ");
                    territory = cardData[0];
                    typeCard = cardData[1];
                    card = new CardImpl(territory, typeCard);
                    this.listCards.add(card);
                    stringRow = bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (IOException e) {
                this.listCards.clear();
            }
        } catch (FileNotFoundException e) {
            this.listCards.clear();
        }
        this.shuffle();
    }

    /**
     * Method to add a card into the deck.
     * 
     * @param card is the card that has to be added in the deck
     */
    public void addCard(final Card card) {
        listCards.add(card);
    }

    /**
     * Method to remove a card from the deck.
     * 
     * @return firstCard is the card pulled out from the deck
     */
    public Card pullCard() {
        Card firstCard;
        firstCard = listCards.get(0);
        listCards.remove(0);
        return firstCard;
    }

    /**
     * Method to shuffle the card in the deck.
     */
    private void shuffle() {
        Collections.shuffle(listCards, new Random());
    }

    /**
     * Method to get the list of the cards in the deck.
     * 
     * @return the list of cards
     */
    public List<Card> getListCards() {
        return List.copyOf(this.listCards);
    }

    /**
     * Method used to get a card from the name of the
     * territory in the list of cards of the player.
     * 
     * @param territoryName is the name of the territory
     * @param player        is the one we need to check the possession of a card
     * @return an empty optional if case the player does not
     *         have the card or an optional that contains the required card
     */
    private Optional<Card> getCardByTerritoryName(final String territoryName, final Player player) {
        for (var card : player.getOwnedCards()) {
            if (card.getTerritoryName().equals(territoryName)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    /**
     * Method to play three cards during a player turn.
     * If the three cards generate a combo then the method computes
     * the number of armies that has to be added in the armies that
     * the player can place.
     * 
     * @param name1 is the name of the territory in the first card to be played
     * @param name2 is the name of the territory in the second card to be played
     * @param name3 is the name of the territory in the third card to be played
     * @param player is the one who plays the three cards during his turn
     */
    public void playCards(final String name1, final String name2, final String name3, final  Player player) {
        int numberOfArmies = 0;
        Card card1 = getCardByTerritoryName(name1, player).get();
        Card card2 = getCardByTerritoryName(name2, player).get();
        Card card3 = getCardByTerritoryName(name3, player).get();
        List<Card> cards = List.of(card1, card2, card3);
        if (playedThreeCannons(cards)) {
            numberOfArmies = COMBO_THREE_CANNON;
        }
        if (playedThreeInfantry(cards)) {
            numberOfArmies = COMBO_THREE_INFANTRY;
        }
        if (playedThreeCavalry(cards)) {
            numberOfArmies = COMBO_THREE_CAVALRY;
        }
        if (oneCardOfAllTypes(cards)) {
            numberOfArmies = COMBO_ONE_OF_EACH;
        }
        if (jollyAndTwoEqualCards(cards)) {
            numberOfArmies = COMBO_JOLLY;
        }
        /*
         * If the three cards generate a combo that has been specified before
         * then they are removed from the player's list of cards, they are inserted
         * in the deck and the number of armies to place by the player is increased.
         */
        if (numberOfArmies != 0) {
            player.removeCard(card1);
            player.removeCard(card2);
            player.removeCard(card3);
            this.addCard(card1);
            this.addCard(card2);
            this.addCard(card3);
            /*
             * For each played card if the name of the territory corresponds
             * to a territory of the player two extra armies are added to
             * the placeable armies.
             */
            numberOfArmies = numberOfArmies + extraArmies(cards, player);
            /* Update the number of armies of the player. */
            player.setArmiesToPlace(player.getArmiesToPlace() + numberOfArmies);
        }
    }

    /**
     * Method used to check if there is a combo with a jolly card.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if a jolly is present and if the other two cards
     *         are of the same type, false otherwise
     */
    private boolean jollyAndTwoEqualCards(final List<Card> cards) {
        int contJolly = 0;
        int contCannon = 0;
        int contInfantry = 0;
        int contCavalry = 0;
        for (var card : cards) {
            if (card.getTypeName().equals("Jolly")) {
                contJolly++;
            } else {
                if (card.getTypeName().equals("Cannon")) {
                    contCannon++;
                } else {
                    if (card.getTypeName().equals("Cavalry")) {
                        contCavalry++;
                    } else {
                        contInfantry++;
                    }
                }
            }
        }
        if (contJolly == 1) {
            if (contCannon == 2 || contCavalry == 2 || contInfantry == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to calculate the number of extra armies that has to be added to
     * the number of placeable armies.
     * 
     * @param cards  is the list of cards that the player wants to play
     * @param player is the player who played the cards
     * @return the number of extra armies
     */
    private int extraArmies(final List<Card> cards, final Player player) {
        int numExtraArmies = 0;
        for (var card : cards) {
            for (var elem : player.getOwnedTerritories()) {
                if (elem.equals(card.getTerritoryName())) {
                    numExtraArmies = numExtraArmies + BONUS_ARMIES;
                }
            }
        }
        return numExtraArmies;
    }

    /**
     * Method to verify if the player has played a card of each type.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if the player played three cards of three different types
     *         or false if not
     */
    private boolean oneCardOfAllTypes(final List<Card> cards) {
        int numCannons = 0;
        int numInfantry = 0;
        int numCavalry = 0;
        for (var card : cards) {
            if (card.getTypeName().equals("Cannon")) {
                numCannons++;
            }
            if (card.getTypeName().equals("Infantry")) {
                numInfantry++;
            }
            if (card.getTypeName().equals("Cavalry")) {
                numCavalry++;
            }
        }
        if (numCannons == 1 && numCavalry == 1 && numInfantry == 1) {
            return true;
        }

        return false;
    }

    /**
     * Method to verify if the three played cards are of the "cannon" type.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "cannon" type, false if not
     */
    private boolean playedThreeCannons(final List<Card> cards) {
        int numberOfCannons = 0;
        for (var card : cards) {
            if (card.getTypeName().equals("Cannon")) {
                numberOfCannons++;
            }
        }
        if (numberOfCannons == 3) {
            return true;
        }

        return false;
    }

    /**
     * Method to verify if the three played cards are of the "infantry" type.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "infantry" type, false if not
     */
    private boolean playedThreeInfantry(final List<Card> cards) {
        int numberOfInfantry = 0;
        for (var card : cards) {
            if (card.getTypeName().equals("Infantry")) {
                numberOfInfantry++;
            }
        }
        if (numberOfInfantry == 3) {
            return true;
        }

        return false;
    }

    /**
     * Method to verify if the three played cards are of the "cavalry" type.
     * 
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "cavalry" type, false if not
     */
    private boolean playedThreeCavalry(final List<Card> cards) {
        int numberOfCavalry = 0;
        for (var card : cards) {
            if (card.getTypeName().equals("Cavalry")) {
                numberOfCavalry++;
            }
        }
        if (numberOfCavalry == 3) {
            return true;
        }
        return false;
    }

}
