package it.unibo.risiko.view.initview;

import java.awt.Component;
import java.awt.Dimension;

/**
 * An interface for the principal game view
 * 
 * @author Keliane Nana
 */
public interface InitialView {

    /**
     * This method updates the view that should be shown by the GameFrame
     * 
     * @param c the component to show
     */
    void updatePanel(final Component c);

    /**
     * Method used to get the dimensios of the GameFrame
     * 
     * @return GameFrame's risolutions
     */
    Dimension getFrameRisolution();

    /**
     * method used to remove the initial frame
     */
    public void unshow();

    /**
     * This method helps to set the GameFrame resolution
     * 
     * @param width  the width of the GameFrame
     * @param height the height of the GameFrame
     */
    void setResolution(final int int1, final int int2);
}
