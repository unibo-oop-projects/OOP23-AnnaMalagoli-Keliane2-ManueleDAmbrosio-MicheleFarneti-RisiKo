package it.unibo.risiko;

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

import it.unibo.risiko.player.Player;

/**
 * An implementation of the interface Deck.
 * @author Anna Malagoli 
 */
public class DeckImpl implements Deck {

    private final List<Card> listCards = new ArrayList<>();

    /**
     * Constructor used to initialize the starting deck at the beginning of the game
     * by reading a text file that contains all the informations of a card:
     * the name of the territory and the type name of the card.
     * If a problem does happend (because the file is not find or the program is not able
     * to read the text) an exception is thrown and the list of cards is set empty
     * @param filePath is the relative path of the text file that contains
     *        all the information of the cards
     */
    public DeckImpl(final String filePath) {
        String territory;
        String typeCard;
        final File file = new File(filePath);
        final String absoluteFilePath = file.getAbsolutePath();
        try {
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            try {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
    }

    /**
     * Method to add a card into the deck.
     * @param card is the card that has to be added in the deck
     */
    @Override
    public void addCard(final Card card) {
        listCards.add(card);
    }

    /**
     * Method to remove a card from the deck.
     * @return firstCard is the card pulled out from the deck
     */
    @Override
    public Card pullCard() {
        Card firstCard;
        firstCard = listCards.get(0);
        listCards.remove(0);
        return firstCard;
    }

    /**
     * Method to shuffle the card in the deck.
     */
    @Override
    public void shuffle() {
        Collections.shuffle(listCards, new Random());
    }

    /**
     * Method to get the list of the cards in the deck.
     * @return the list of cards
     */
    @Override
    public List<Card> getListCards() {
        return List.copyOf(this.listCards);
    }

    /**
     * Method used to get a card from the name of the 
     * territory in the list of cards of the player.
     * @param territoryName is the name of the territory
     * @param player is the one we need to check the possession of a card
     * @return an empty optional if case the player does not
     * have the card or an optional that contains the required card 
     */
    public Optional<Card> getCardByTerritoryName(String territoryName, Player player) {
        for(var card : player.getOwnedCards()) {
            if(card.getTerritoryName().equals(territoryName)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    /**
     * Method to play three cards during a player turn.
     * If the three cards generate a combo then the method computes
     * the number of armies that has to be added in the armies that
     * the player can place. Otherwise no armies are added for the player
     * and an error message is returned.
     * @param card1 is the first card to be played
     * @param card2 is the second card to be played
     * @param card3 is the third card to be played
     * @param player is the one who plays the three cards during his turn.
     * @return an empty string if the operation succeeded or 
     * an message that shows the error that incurred
     */
    public String playCards(Card card1, Card card2, Card card3, Player player) {
        int numberOfArmies = 0;
        String outputMessage = "";
        List<Card> cards = List.of(card1, card2, card3);
        if(checkCardsPlayer(cards, player)) {
            if(checkCardsAreDifferent(cards)) {
                if(playedThreeCannons(cards)) {
                    numberOfArmies = 4;
                }
                if(playedThreeInfantry(cards)) {
                    numberOfArmies = 6;
                }
                if(playedThreeCavalry(cards)) {
                    numberOfArmies = 8;
                }
                if(oneCardOfAllTypes(cards)) {
                    numberOfArmies = 10;
                }
                if(jollyAndTwoEqualCards(cards)){
                    numberOfArmies = 12;
                }
                /*If the three cards generate a combo that has been specified before
                then they are removed from the player's list of cards, they are inserted 
                in the deck and the number of armies to place by the player is increased.*/
                if(numberOfArmies != 0) {
                    player.removeCard(card1);
                    player.removeCard(card2);
                    player.removeCard(card3);
                    this.addCard(card1);
                    this.addCard(card2);
                    this.addCard(card3);
                /*For each played card if the name of the territory corresponds
                 * to a territory of the player two extra armies are added to 
                 * the placeable armies.
                */
                    numberOfArmies = numberOfArmies + extraArmies(cards, player);
                /*Update the number of armies of the player.*/
                    player.setArmiesToPlace(player.getArmiesToPlace() + numberOfArmies);
                } else {
                    outputMessage = "The played cards do not generate an acceptable combo";
                }
            }
            else {
                outputMessage = "The three cards are not different";
            }
        } else {
            outputMessage = "The player does not have the chosen cards";
        }
        return outputMessage;
    }

    /**
     * Method used to check if there is a combo with a jolly card.
     * @param cards is the list of cards that the player wants to play 
     * @return true if a jolly is present and if the other two cards 
     * are of the same type, false otherwise
     */
    private boolean jollyAndTwoEqualCards(List<Card> cards) {
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
        if(contJolly == 1){
            if(contCannon == 2 || contCavalry == 2 || contInfantry == 2){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to verify if the three cards are different.
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are different or false if at least two cards
     * are equal
     */
    private boolean checkCardsAreDifferent(List<Card> cards) {
        if(cards.get(0).getTerritoryName().equals(cards.get(1).getTerritoryName()) ||
        cards.get(0).getTerritoryName().equals(cards.get(2).getTerritoryName()) ||
        cards.get(1).getTerritoryName().equals(cards.get(2).getTerritoryName())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to calculate the number of extra armies that has to be added to 
     * the number of placeable armies. 
     * @param cards is the list of cards that the player wants to play
     * @param player is the player who played the cards
     * @return the number of extra armies
     */
    private int extraArmies(List<Card> cards, Player player) {
        int numExtraArmies = 0;
        for(var card : cards) {
            for(var elem : player.getOwnedTerritories()) {
                if(elem.getTerritoryName().equals(card.getTerritoryName())) {
                    numExtraArmies = numExtraArmies + 2;
                }
            }
        }
        return numExtraArmies;
    }

    /**
     * Method to verify if the player has played a card of each type.
     * @param cards is the list of cards that the player wants to play
     * @return true if the player played three cards of three different types
     * or false if not
     */
    private boolean oneCardOfAllTypes(List<Card> cards) {
        int numCannons = 0;
        int numInfantry = 0;
        int numCavalry = 0;
        for(var card : cards) {
            if(card.getTypeName().equals("Cannone")) {
                numCannons++;
            }
            if(card.getTypeName().equals("Fante")) {
                numInfantry++;
            }
            if(card.getTypeName().equals("Cavaliere")) {
                numCavalry++;
            }
        }
        if(numCannons == 1 && numCavalry == 1 && numInfantry == 1){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to verify if the player ownes the cards he wants to play
     * @param cards is the list of cards that the player wants to play
     * @param player is the player who played the cards
     * @return true if the cards are owned by the player, false if not
     */
    private boolean checkCardsPlayer(List<Card> cards, Player player) {
        int numCardsOwnedByPlayer = 0;
        for (var card : cards){
            if(player.isOwnedCard(card)) {
                numCardsOwnedByPlayer++;
            }
        }
        if(numCardsOwnedByPlayer == 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to verify if the three played cards are of the "cannon" type.
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "cannon" type, false if not
     */
    private boolean playedThreeCannons(List<Card> cards) {
        int numberOfCannons = 0;
        for(var card : cards){
            if(card.getTypeName().equals("Cannone")) {
                numberOfCannons++;
            }
        }
        if(numberOfCannons == 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to verify if the three played cards are of the "infantry" type.
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "infantry" type, false if not
     */
    private boolean playedThreeInfantry(List<Card> cards) {
        int numberOfInfantry = 0;
        for(var card : cards){
            if(card.getTypeName().equals("Fante")) {
                numberOfInfantry++;
            }
        }
        if(numberOfInfantry == 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to verify if the three played cards are of the "cavalry" type.
     * @param cards is the list of cards that the player wants to play
     * @return true if the cards are all of the "cavalry" type, false if not
     */
    private boolean playedThreeCavalry(List<Card> cards) {
        int numberOfCavalry = 0;
        for(var card : cards){
            if(card.getTypeName().equals("Cavaliere")) {
                numberOfCavalry++;
            }
        }
        if(numberOfCavalry == 3) {
            return true;
        } else {
            return false;
        }
    }

}
