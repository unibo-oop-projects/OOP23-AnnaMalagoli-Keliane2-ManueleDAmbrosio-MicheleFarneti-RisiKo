package it.unibo.risiko.view.InitialView;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import it.unibo.risiko.view.InitialViewObserver;

/**
 * An interface for the principal game view
 * @author Keliane Nana
 */
public interface GameFrame {

    /**
     * This method updates the view that should be shown by the GameFrame
     * @param c the component to show
     */
    public void updatePanel(final Component  c);

    /**
     * Method used to get the dimensios of the GameFrame
     * @return GameFrame's risolutions
     */
    public Dimension getFrameRisolution();
    
    /**
     * Method used to get controller associated to the view
     * @return the controller associated to the game view
     */
    public InitialViewObserver getController();
    
    //method used to remove the initial frame
    public void unshow();

    /**
     * This method is used to get the option menu panel
     * @return the OptionSubMenu of active GameFrame
     */
    public JPanel getOptionSubMenu();

    /**
     * This method helps to set the GameFrame resolution
     * @param width the width of the GameFrame
     * @param height the height of the GameFrame
     */
    public void setResolution(int int1, int int2);
}
