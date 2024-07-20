package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;

import javax.swing.JLayeredPane;

import it.unibo.risiko.model.map.Territory;

public interface TerritoryPlaceHolder {

    public void addToLayoutPane(JLayeredPane layerdPane, Integer layer);

    public void redrawTank(Territory territory);

    public String getTerritoryName();

    public void resetBorder();

    public void setEnabled(Boolean enabled);

    public void setFighting(Color color);
}
