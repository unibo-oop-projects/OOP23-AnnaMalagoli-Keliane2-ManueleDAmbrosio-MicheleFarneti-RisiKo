package it.unibo.risiko.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;

/**
 * @author Manuele D'Ambrosio.
 */

class TestGameSave {
    private static final String STD = "noName";
    private static final String MAP = "map";
    private static final int ONE_ARMY = 1;
    private static final int TWO_ARMIES = 2;
    private static final int THREE_ARMIES = 3;
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;

    @Test
    void testSave() {
        final PlayerFactory factory = new SimplePlayerFactory();

        final Territory t1 = new TerritoryImpl("t1", STD, List.of());
        final Territory t2 = new TerritoryImpl("t2", STD, List.of());
        final Territory t3 = new TerritoryImpl("t3", STD, List.of());
        final Territory t4 = new TerritoryImpl("t4", STD, List.of());
        t1.addArmies(ONE_ARMY);
        t2.addArmies(THREE_ARMIES);
        t3.addArmies(THREE_ARMIES);
        t4.addArmies(TWO_ARMIES);

        final Player p1 = factory.createStandardPlayer();
        final Player p2 = factory.createAIPlayer();
        final Player p3 = factory.createAIPlayer();
        p1.addTerritory(t1.getTerritoryName());
        p2.addTerritory(t2.getTerritoryName());
        p3.addTerritory(t3.getTerritoryName());
        p3.addTerritory(t4.getTerritoryName());
        t1.setPlayer(p1.getColorID());
        t2.setPlayer(p2.getColorID());
        t3.setPlayer(p3.getColorID());

        final GameSave save = new GameSave(List.of(p1, p2, p3), List.of(t1, t2, t3, t4), MAP);
        final GameSave load = new GameSave();

        assertEquals(save.getPlayerList().get(FIRST_INDEX).getColorID(),
                load.getPlayerList().get(FIRST_INDEX).getColorID());
        assertEquals(MAP, save.getMapName());
        assertEquals(MAP, load.getMapName());
        assertEquals(THREE_ARMIES, save.getTerritoryList().get(SECOND_INDEX).getNumberOfArmies());
        assertEquals(THREE_ARMIES, load.getTerritoryList().get(SECOND_INDEX).getNumberOfArmies());
        assertEquals(ONE_ARMY, save.getTerritoryList().get(FIRST_INDEX).getNumberOfArmies());
        assertEquals(ONE_ARMY, load.getTerritoryList().get(FIRST_INDEX).getNumberOfArmies());
        assertEquals(p1.getColorID(), save.getTerritoryList().get(FIRST_INDEX).getPlayer());
        assertEquals(p1.getColorID(), load.getTerritoryList().get(FIRST_INDEX).getPlayer());
    }
}
