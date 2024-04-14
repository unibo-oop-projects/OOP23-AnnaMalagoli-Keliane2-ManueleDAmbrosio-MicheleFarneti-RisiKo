package it.unibo.risiko;

/*import static org.junit.Assert.*;
import java.util.*;

public class TestDeck {

    private Deck deck;
    
    @org.junit.Before
	public void init() {
		this.deck = new Deck();
	}

    @org.junit.Test
    public void testGenerateDeck(){
        int cont = 0;
        String path = "./DeckCards.txt";
        deck.generate(path);
        assertEquals(List.of(new Card("Italia", "Soldato")), deck.getListCards().get(0));
        for(var elem : deck.getListCards()){
            assertTrue(!elem.getTerritoryName().isEmpty());
            assertTrue(!elem.getTypeName().isEmpty());
        }
    }

}
*/