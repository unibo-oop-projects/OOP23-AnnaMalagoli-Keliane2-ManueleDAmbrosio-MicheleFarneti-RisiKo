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

/**
 * An implementation of the interface Deck.
 * @author Anna Malagoli 
 */
public class DeckImpl implements Deck {

    private final List<CardImpl> listCards = new ArrayList<>();

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
                CardImpl card;
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
    public void addCard(final CardImpl card) {
        listCards.add(card);
    }

    /**
     * Method to remove a card from the deck.
     * @return firstCard is the card pulled out from the deck
     */
    @Override
    public CardImpl pullCard() {
        CardImpl firstCard;
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
    public List<CardImpl> getListCards() {
        return List.copyOf(this.listCards);
    }
}
