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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.risiko.controller.GameController;

public class PrincipalMenu extends JPanel {
    private GameFrame gameFrame;
    //private JLabel picture;
    private ImageIcon backgroundImage = new ImageIcon("src\\main\\resources\\it\\unibo\\risiko\\images\\background.jpg");

    public PrincipalMenu(GameFrame f){
        this.gameFrame=f;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(350,0,0,0));
        this.setPreferredSize(new Dimension(700, 700));
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
        newGame.addActionListener(a->startNewGame());
        continueSavedGame.addActionListener(a->continueSavedGame());
        option.addActionListener(a->showOptionPanel());
        quit.addActionListener(e->{int answer=JOptionPane.showConfirmDialog(this,
        "Are you sure you want to quit?", "Impostazioni Risoluzione",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); if(answer==0){ System.exit(0);}});
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
    private void showOptionPanel() {
        this.gameFrame.updatePanel(this.gameFrame.getOptionSubMenu());
    }

    /**
     * this method organise everything so that a saved game
     * can continue
     */
    private void continueSavedGame() {
        var dim=this.gameFrame.getFrameRisolution();
        this.gameFrame.unshow();
        this.gameFrame.getController().startGameWindow(dim.width, dim.height);
        this.gameFrame.getController().setupGameView();
    }

    /**
     * This method starts a new game
     */
    private void startNewGame() {
        var dim=this.gameFrame.getFrameRisolution();
        this.gameFrame.unshow();
        this.gameFrame.getController().startGameWindow(dim.width, dim.height);
        this.gameFrame.getController().initializeNewGame();
    }

    /**
     * Method used to add a JButton in a Container
     * @param name the JButton text
     * @param container the Container in which we want to make 
     * the insertion
     * @return the added JButton
     */
    protected JButton addButtonToMenu(String name, Container container) {
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
    public JPanel addPanelToMenu(Container c) {
        JPanel pane = new JPanel();
        pane.setVisible(false);
        c.add(pane);
        return pane;
    }

    /**
     * getter, used to get the gameFrame associated to this panel
     * @return the gameFrame of this PrincipalMenu
     */
    public GameFrame getGameFrame() {
        return this.gameFrame;
    }

}
