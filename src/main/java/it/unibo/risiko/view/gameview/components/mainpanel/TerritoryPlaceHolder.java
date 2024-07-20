package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;

import javax.swing.JLayeredPane;

import it.unibo.risiko.model.map.Territory;

/**
 * Interface rappresenting a territory entity for what concerns it's appearence
 * in the java swing game view. It's composed by a button rappresenting the
 * image of a tunk wich is going to change color based on on its owner and a
 * label rappresenting the armies count in the territory.
 * It also gives the opportunity for the territory to be higlighted by setting
 * up a colored border around it.
 * 
 * @author Michele Farneti.
 */
public interface TerritoryPlaceHolder {

    /**
     * Adds the tank icon and the armies label to a layerdpane at a given layer.
     * 
     * @param layerdPane
     * @param layer
     */
    void addToLayoutPane(JLayeredPane layerdPane, Integer layer);

    /**
     * Updates the appearence of a territory place holder by taking infos from a
     * given territory. Changes its color in relation to its owner and updates it's
     * armies label.
     * 
     * @param territory
     */
    void redrawTank(Territory territory);

    /**
     * 
     * @return The name of the territory associated to the placeholder/
     */
    String getTerritoryName();

    /**
     * Removes highlightings from the placeholder by disabling the painting of its
     * border.
     */
    void resetBorder();

    /**
     * Changes the enabled option of the tank icon by setting it as clickable or
     * not.
     * 
     * @param enabled True if the buttonm has to be enabled, false otherwise.
     */
    void setEnabled(Boolean enabled);

    /**
     * Higligths the placeholder by drawing it's border of a given color.
     * 
     * @param color
     */
    void setFighting(Color color);
}
