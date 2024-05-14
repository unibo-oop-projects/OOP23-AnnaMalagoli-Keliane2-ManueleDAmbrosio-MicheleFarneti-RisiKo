package it.unibo.risiko.InitialView;

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

public class PrincipalMenu extends JPanel {
    private GameFrame gameFrame;
    private JLabel picture;
    private ImageIcon backgroundImage = new ImageIcon("src\\main\\java\\it\\unibo\\risiko\\resources\\images\\background.jpg");

    public PrincipalMenu(GameFrame f){
        this.gameFrame=f;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(350,0,0,0));
        this.setPreferredSize(new Dimension(700, 700));
        
        JButton newGame=addButtonToMenu("New Game", this);
        JButton continueSavedGame=addButtonToMenu("Continue", this);
        JButton option=addButtonToMenu("Option", this);
        JButton quit=addButtonToMenu("Quit", this);

        newGame.setBorder(BorderFactory.createEmptyBorder(12,50,12,50));
        continueSavedGame.setBorder(BorderFactory.createEmptyBorder(12,54,12,54));
        option.setBorder(BorderFactory.createEmptyBorder(12,61,12,61));
        quit.setBorder(BorderFactory.createEmptyBorder(12,69,12,69));

        newGame.addActionListener(a->startNewGame());
        continueSavedGame.addActionListener(a->continueSavedGame());
        option.addActionListener(a->showOptionPanel(this));
        quit.addActionListener(e->{int answer=JOptionPane.showConfirmDialog(this,
        "Are you sure you want to quit?", "Impostazioni Risoluzione",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); if(answer==0){ System.exit(0);}});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    protected void updateLabel(String name) {
        ImageIcon icon = createImageIcon(name + ".gif");
        picture.setIcon(icon);
        picture.setToolTipText("A drawing of a " + name.toLowerCase());
        if (icon != null) {
            picture.setText(null);
        } else {
            picture.setText("Image not found");
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        System.out.println(path);
        java.net.URL imgURL = PrincipalMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void showOptionPanel(PrincipalMenu p) {
        this.gameFrame.updatePanel(this.gameFrame.getOptionSubMenu());;
    }

    private Object continueSavedGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'continueSavedGame'");
    }

    private Object startNewGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startNewGame'");
    }

    protected JButton addButtonToMenu(String name, Container container) {
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        return button;
    }

    public JPanel addPanelToMenu(Container c) {
        JPanel pane = new JPanel();
        pane.setVisible(false);
        c.add(pane);
        return pane;
    }

    public GameFrame getGameFrame() {
        return this.gameFrame;
    }

}
