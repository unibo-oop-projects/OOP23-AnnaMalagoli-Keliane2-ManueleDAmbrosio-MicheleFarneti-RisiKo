package it.unibo.risiko.view.InitialView;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *A panel which represents the principal menu
 @author Keliane Nana
 */
public class PrincipalMenu extends JPanel {
    //private final GameFrame gameFrame;
    private final ImageIcon backgroundImage = new ImageIcon("src\\main\\resources\\it\\unibo\\risiko\\images\\background.jpg");

    public PrincipalMenu(final InitialView f, final InitialViewObserver controller){
        //this.gameFrame = f;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(350,0,0,0));
        this.setPreferredSize(new Dimension(1600, 900));
        //adding JButton to the PrincipalMenu
        JButton newGame=addButtonToMenu("New Game", this);
        JButton continueSavedGame=addButtonToMenu("Continue", this);
        JButton option=addButtonToMenu("Option", this);
        JButton quit=addButtonToMenu("Quit", this);
        //setting the border of the JButton
        newGame.setBorder(BorderFactory.createEmptyBorder(12,50,12,50));
        continueSavedGame.setBorder(BorderFactory.createEmptyBorder(12,54,12,54));
        option.setBorder(BorderFactory.createEmptyBorder(12,61,12,61));
        quit.setBorder(BorderFactory.createEmptyBorder(12,69,12,69));
        //adding actionListeners to the JButton
        newGame.addActionListener(a->startNewGame(f, controller));
        continueSavedGame.addActionListener(a->continueSavedGame(f, controller));
        option.addActionListener(a->showOptionPanel(f));
        quit.addActionListener(e->{int answer=JOptionPane.showConfirmDialog(this,
        "Are you sure you want to quit?", "Impostazioni Risoluzione",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); if(answer==0){ f.unshow();}});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * This method helps to update the gameFrame by showing
     * the OptionSubMenu panel
     */
    private void showOptionPanel(InitialView gameFrame) {
        gameFrame.updatePanel(new OptionSubMenu(this, gameFrame));
    }

    /**
     * this method organise everything so that a saved game
     * can continue
     */
    private void continueSavedGame(InitialView gameFrame, InitialViewObserver controller) {
        var dim=gameFrame.getFrameRisolution();
        gameFrame.unshow();
        controller.startGameWindow(dim.width, dim.height);
        controller.setupGameView();
    }

    /**
     * This method starts a new game
     */
    private void startNewGame(InitialView gameFrame, InitialViewObserver controller) {
        var dim=gameFrame.getFrameRisolution();
        gameFrame.unshow();
        controller.startGameWindow(dim.width, dim.height);
       controller.initializeNewGame();
    }

    /**
     * Method used to add a JButton in a Container
     * @param name the JButton text
     * @param container the Container in which we want to make 
     * the insertion
     * @return the added JButton
     */
    protected final JButton addButtonToMenu(String name, Container container) {
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        return button;
    }

    /**
     * Used to add a JPanel in a Container
     * @param c the Container in which we want to make 
     * the insertion
     * @return the added JPanel 
     */
    protected final JPanel addPanelToMenu(Container c) {
        JPanel pane = new JPanel();
        pane.setVisible(false);
        c.add(pane);
        return pane;
    }

    /**
     * getter, used to get the gameFrame associated to this panel
     * @return the gameFrame of this PrincipalMenu
     */
    /*public GameFrame getGameFrame() {
        return this.gameFrame;
    } */

}
