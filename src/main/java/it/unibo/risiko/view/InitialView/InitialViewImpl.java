package it.unibo.risiko.view.initialview;

import java.awt.*;
import javax.swing.*;

import it.unibo.risiko.view.InitialViewObserver;

/**The gameFrame implementation
 * @author Keliane Nana
 */
public class InitialViewImpl implements InitialView {
    private final JFrame frame;
    private final PrincipalMenu menuPanel;
    //private final InitialViewObserver controller;

    public InitialViewImpl(final InitialViewObserver controller){ 
        //this.controller = controller;
        this.menuPanel = new PrincipalMenu(this, controller);
        //this.optionMenu = new OptionSubMenu(this.menuPanel, this);
        this.frame = new JFrame("***Risiko***");
        this.frame.setLayout(new BorderLayout());
        this.updatePanel(menuPanel);
        this.frame.setSize(80,80);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.show();
    }

    @Override
    public void updatePanel(final Component  c) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().add(c);
    }

    @Override
    public void setResolution(int width, int height){
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

    @Override
    public Dimension getFrameRisolution(){
        return this.frame.getSize();
    }

    @Override
    public void unshow() {
        this.frame.dispose();
    }
}
