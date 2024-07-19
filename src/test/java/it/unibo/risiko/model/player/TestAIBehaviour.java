package it.unibo.risiko.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.cards.Card;
import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.cards.DeckImpl;
import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * @author Manuele D'Ambrosio
 */

class TestAIBehaviour {
    private static final int INITIAL_10_ARMIES = 10;
    private static final int INITIAL_2_ARMIES = 2;
    private static final int MIN_ARMIES = 1;
    private static final int MAX_ARMIES = 3;
    private static final int PLAYABLE_CARDS = 3;
    private static final int NO_CARDS = 0;
    private static final int FIRST_CARD = 0;
    private static final int SECOND_CARD = 1;
    private static final int THIRD_CARD = 2;
    private static final String SEP = File.separator;
    private static final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "it" + SEP + "unibo" + SEP
                + "risiko" + SEP + "maps" + SEP + "standard" + SEP + "cards.txt";

    private AIBehaviour ai;
    private final Deck deck = new DeckImpl(PATH);
    private final Territory t1 = new TerritoryImpl("t1", "cont", List.of("t2", "t3"));
    private final Territory t2 = new TerritoryImpl("t2", "cont", List.of("t1"));
    private final Territory t3 = new TerritoryImpl("t3", "cont", List.of("t2", "t1"));

    @BeforeEach
    void setUp() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createAIPlayer();
        player.addTerritory(t2.getTerritoryName());
        player.addTerritory(t1.getTerritoryName());
        t1.addArmies(INITIAL_10_ARMIES);
        t2.addArmies(INITIAL_2_ARMIES);
        t3.addArmies(INITIAL_10_ARMIES);

        ai = new AIBehaviourImpl(List.of(t1, t2), player.getOwnedCards().stream().toList());
    }

    @Test
    void testDecideAttack() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createAIPlayer();
        player.addTerritory(t2.getTerritoryName());
        player.addTerritory(t1.getTerritoryName());
        ai = new AIBehaviourImpl(List.of(t1, t2), player.getOwnedCards().stream().toList());
        assertTrue(ai.decideAttack(List.of(t1, t2, t3)));
        assertEquals(t3.getTerritoryName(), ai.getNextAttackedTerritory());
        assertEquals(t1.getTerritoryName(), ai.getNextAttackingTerritory());
        assertEquals(MAX_ARMIES, ai.decideAttackingArmies());
        assertEquals(INITIAL_10_ARMIES - MIN_ARMIES, ai.getArmiesToMove());
    }

    @Test
    void testCheckCardCombo() {
        final PlayerFactory factory = new SimplePlayerFactory();
        final Player player = factory.createAIPlayer();
        player.addTerritory(t2.getTerritoryName());
        player.addTerritory(t1.getTerritoryName());
        assertFalse(deck.getListCards().isEmpty());
        List<Card> cardList;
        int numberOfCards = NO_CARDS;
        for (int i = NO_CARDS; i < INITIAL_10_ARMIES; i++) {
            do {
                player.addCard(deck.pullCard());
                ai = new AIBehaviourImpl(List.of(t1, t2), player.getOwnedCards().stream().toList());
                numberOfCards++;
            } while (ai.checkCardCombo().isEmpty());
            assertEquals(PLAYABLE_CARDS, ai.checkCardCombo().size());
            cardList = ai.checkCardCombo();
            deck.playCards(cardList.get(FIRST_CARD).getTerritoryName(), cardList.get(SECOND_CARD).getTerritoryName(),
                    cardList.get(THIRD_CARD).getTerritoryName(), player);
            assertEquals(numberOfCards - PLAYABLE_CARDS, player.getNumberOfCards());
            numberOfCards = numberOfCards - PLAYABLE_CARDS;
        }
    }
}
