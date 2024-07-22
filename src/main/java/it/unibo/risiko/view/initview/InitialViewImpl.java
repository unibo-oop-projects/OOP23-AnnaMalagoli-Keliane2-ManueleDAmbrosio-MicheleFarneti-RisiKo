package it.unibo.risiko.view.initview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The gameFrame implementation
 * 
 * @author Keliane Nana
 */
public class InitialViewImpl implements InitialView {
    private static final int INITIAL_FRAME_WIDTH = 80;
    private static final int INITIAL_FRAME_HEIGHT = 80;
    private final JFrame frame;
    private final PrincipalMenu menuPanel;

    public InitialViewImpl(final InitialViewObserver controller) {
        this.menuPanel = new PrincipalMenu(this, controller);
        this.frame = new JFrame("***Risiko***");
        this.frame.setLayout(new BorderLayout());
        this.updatePanel(menuPanel);
        this.frame.setSize(INITIAL_FRAME_WIDTH, INITIAL_FRAME_HEIGHT);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.show();
    }

    @Override
    public void updatePanel(final Component c) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
        this.frame.getContentPane().add(c);
    }

    @Override
    public void setResolution(final int width, final int height) {
        this.frame.setSize(width, height);
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
    public Dimension getFrameRisolution() {
        return this.frame.getSize();
    }

    @Override
    public void unshow() {
        this.frame.dispose();
    }
}
