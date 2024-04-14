package it.unibo.risiko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeckImpl implements Deck {

    private final List<Card> listCards = new ArrayList<>();

    public DeckImpl(String filePath){
        String territory;
        String typeCard;
        File file = new File(filePath);
        String absoluteFilePath = file.getAbsolutePath();
        try(
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ) {
                String stringRow;
                while((stringRow = bufferedReader.readLine()) != null){
                    Card card;
                    String[] cardData = stringRow.split(" ");
                    territory = cardData[0];
                    typeCard = cardData[1];
                    card = new Card(territory, typeCard);
                    this.listCards.add(card);
                }
                bufferedReader.close();

        } catch (Exception e) {
            //gestione chiusura applicazione
            //non riuscito a trovare il file o non lo riesce a leggere
            System.out.println(e.getMessage());
        }        
    }

    public void addCard(Card card){
        listCards.add(card);
    }

    public Card pullCard(){
        Card firstCard;
        firstCard = listCards.get(0);
        listCards.remove(0);
        return firstCard;
    }

    public void shuffle(){
        Collections.shuffle(listCards, new Random());
    }

    public List<Card> getListCards(){
        List<Card> listCopyCards = new ArrayList<>(this.listCards);
        return listCopyCards;
    }
}
