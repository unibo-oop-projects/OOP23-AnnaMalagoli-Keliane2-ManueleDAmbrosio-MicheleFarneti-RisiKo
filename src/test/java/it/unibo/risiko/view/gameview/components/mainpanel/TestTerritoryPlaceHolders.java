package it.unibo.risiko.view.gameview.components.mainpanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.model.map.TerritoryImpl;
import it.unibo.risiko.view.gameview.components.Position;

/**
 * A test for territory place hodlers
 * 
 * @author Michele Farneti
 */
public class TestTerritoryPlaceHolders {
    private static final String FILE_SEPARATOR = File.separator;

    @Test
    void TestTerritoryPlaceHolder() {
        final String coordinatesPath = "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "java" + FILE_SEPARATOR + "it"
                + FILE_SEPARATOR + "unibo" + FILE_SEPARATOR + "risiko" + FILE_SEPARATOR + "view"
                + FILE_SEPARATOR + "gameview" + FILE_SEPARATOR + "components" + FILE_SEPARATOR + "mainpanel" + FILE_SEPARATOR + "coordinates.txt";
        final TerritoryPlaceHolderFactory factory = new TerritoryPlaceHolderFactory(coordinatesPath);
        final Territory territory = new TerritoryImpl("China", "Asia", List.of());
        final Function<Position, Position> coordinatesFunction = new Function<>() {

            @Override
            public Position apply(Position p) {
                return new Position(1, 1);
            }

        };
        final var tank = factory.generateTank(territory, coordinatesFunction, "dummy.txt", e -> {
        });
        assertTrue(tank.isPresent());
        assertEquals("China", tank.get().getTerritoryName());
    }

}