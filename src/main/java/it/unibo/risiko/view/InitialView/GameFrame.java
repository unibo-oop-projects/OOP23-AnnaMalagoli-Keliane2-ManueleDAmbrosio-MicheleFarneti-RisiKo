package it.unibo.risiko.view.InitialView;

import java.awt.*;
import javax.swing.*;

import it.unibo.risiko.view.InitialViewObserver;

public class GameFrame{
    private JFrame frame;
    private PrincipalMenu menuPanel;
    private OptionSubMenu optionMenu;
    private InitialViewObserver controller;

    public GameFrame(InitialViewObserver controller) { 
        this.controller = controller;
        this.menuPanel = new PrincipalMenu(this);
        this.optionMenu = new OptionSubMenu(this.menuPanel);
        this.frame = new JFrame("***Risiko***");
        this.frame.setLayout(new BorderLayout());
        this.updatePanel(menuPanel);
        this.frame.setSize(80,80);
        System.out.println("hellllloo");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.show();
    }

    /**
     * This method updates the view that should be shown by the GameFrame
     * @param c the component to show
     */
    public void updatePanel(final Component  c) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().add(c);
    }

    /**
     * This method helps to set the GameFrame resolution
     * @param width the width of the GameFrame
     * @param height the height of the GameFrame
     */
    protected void setResolution(int width, int height){
        this.frame.setSize(width,height);
    }

    /**
     * This method is used to make sure the GameFrame will be visible
     */
    private void show() {
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    /**
     * This method is used to get the option menu panel
     * @return the OptionSubMenu of active GameFrame
     */
    protected OptionSubMenu getOptionSubMenu() {
        return this.optionMenu;
    }

    public Dimension getFrameRisolution(){
        return this.frame.getSize();
    }

    /**
     * @return the controller associated to the game view
     */
    public InitialViewObserver getController() {
        return this.controller;
    }

    //method used to remove the initial frame
    public void unshow() {
        this.frame.dispose();
    }
}
