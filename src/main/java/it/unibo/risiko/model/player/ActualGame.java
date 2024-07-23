package it.unibo.risiko.model.player;

import java.util.List;
import java.util.Map;

import it.unibo.risiko.model.cards.Deck;
import it.unibo.risiko.model.map.Territory;

/**
 * Interface uset to get information about a game.
 * 
 * @author Manuele D'Ambrosio
 */

public interface ActualGame {

    /**
     * @return the name of the map
     */
    String getMapName();

    /**
     * @return the list of Players without 
     */
    List<Player> getPlayerList();

    /**
     * @return the list of territories.
     */
    List<Territory> getTerritoryList();

    /**
     * @return the index of the actual player in the player list.
     */
    int getTurnIndex();

    /**
     * Reassigns the card owned by the players with the actual 
     * cards in the deck.
     * 
     * @param deck - deck used in the game
     */
    void reassignCards(Deck deck);

    /**
     * @return a map with player names as keys and a string representing their target as values.
     */
    Map<String, String> getTargetMap();
}
