package it.unibo.risiko;

import java.util.List;

public interface Deck {
    void addCard(Card card);

    Card pullCard();

    void shuffle();

    List<Card> getListCards();
}
