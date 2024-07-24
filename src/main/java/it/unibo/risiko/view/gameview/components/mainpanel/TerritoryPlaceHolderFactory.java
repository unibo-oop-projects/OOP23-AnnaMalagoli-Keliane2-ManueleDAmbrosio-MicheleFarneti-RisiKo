package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.event.ActionListener;
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

public interface TerritoryPlaceHolderFactory {

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
     * @return An optional of a territory place holder if it was possible to find the coordinates
     *         of the territory, an empty optional otherwiswe.
     */
    Optional<TerritoryPlaceHolder> generateTank(Territory territory,
            Function<Position, Position> coordinatesGenerator, String resourcesPackageString,
            ActionListener al);
}
