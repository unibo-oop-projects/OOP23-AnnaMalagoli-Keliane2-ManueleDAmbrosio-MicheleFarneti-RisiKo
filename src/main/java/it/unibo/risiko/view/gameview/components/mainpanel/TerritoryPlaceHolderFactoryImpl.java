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
 * Simple factory implementation used to safely create territory placeholders
 * for every territory present in a map.
 * 
 * @author Michele Farneti
 */

public final class TerritoryPlaceHolderFactoryImpl implements TerritoryPlaceHolderFactory {
    private final Map<String, Position> tanksCoordinates;

    /**
     * Constructor of the factory gets initialized by filling up a map with the
     * coorrdinates of each territory in order to check while generating new
     * Placeholders if the territory which we want to genrate a placholder for
     * actually is supported by the view.
     * 
     * @param coordinatesPath The path for the coordinates.txt file of the map
     */
    public TerritoryPlaceHolderFactoryImpl(final String coordinatesPath) {
        tanksCoordinates = new HashMap<>();
        try (BufferedReader coordinateReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(coordinatesPath), StandardCharsets.UTF_8));) {
            coordinateReader.lines().map(s -> s.split(" ")).forEach(
                    t -> tanksCoordinates.put(t[0], new Position(Integer.parseInt(t[1]), Integer.parseInt(t[2]))));
        } catch (IOException e) {
            tanksCoordinates.put("error", new Position(0, 0));
        }
    }

    @Override
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
