package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.view.gameview.components.Position;

/**
 * Simple factory used to safely create territory placeholders for every
 * tarritory present in a map.
 * 
 * @author Michele Farneti
 */

public final class TerritoryPlaceHolderFactory {
    private final Map<String, Position> tanksCoordinates;

    /**
     * Constructor of the factory gets initialized by filling up a map with the
     * coorrdinates of each territory in order to check while generating new
     * Placeholders if the territory which we want to genrate a placholder for
     * actually is supported by the view.
     * 
     * @param coordinatesPath The path for the coordinates.txt file of the map
     */
    public TerritoryPlaceHolderFactory(final String coordinatesPath) {
        tanksCoordinates = new HashMap<>();
        try (BufferedReader coordinateReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(coordinatesPath), StandardCharsets.UTF_8));) {
            coordinateReader.lines().map(s -> s.split(" ")).forEach(
                    t -> tanksCoordinates.put(t[0], new Position(Integer.parseInt(t[1]), Integer.parseInt(t[2]))));
        } catch (IOException e) {
            tanksCoordinates.put("error", new Position(0, 0));
        }
    }

    /**
     * Generates a new territoryPlacehoder for a given territory if it's present in
     * the coordinates list known by the factory, setting already its relative
     * coordinates in the game map,and adding it's actionListner.
     * 
     * @param territory
     * @param coordinatesGenerator   A function used for generating coordinates that
     *                               are relative to the actuale game map proportion
     *                               from the standard coordinates know from the map
     *                               folder.
     * @param resourcesPackageString
     * @param al                     ActionListener for when te tankIcon gets
     *                               clicked.
     * @return An optional of a tank Icon if it was possible to find the coordinates
     *         of the territory, an empty optional otherwiswe.
     */
    public Optional<TankIcon> generateTank(final Territory territory,
            final Function<Position, Position> coordinatesGenerator, final String resourcesPackageString,
            final ActionListener al) {
        if (tanksCoordinates.containsKey(territory.getTerritoryName())) {
            final Position newPosition = coordinatesGenerator.apply(tanksCoordinates.get(territory.getTerritoryName()));
            return Optional.of(new TankIcon(newPosition.x(), newPosition.y(), territory.getTerritoryName(),
                    resourcesPackageString, al));
        }
        return Optional.empty();
    }
}
