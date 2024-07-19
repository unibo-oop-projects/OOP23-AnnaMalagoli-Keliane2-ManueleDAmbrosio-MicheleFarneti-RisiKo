package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;

import it.unibo.risiko.view.gameview.components.Position;

/**
 * Class used to sum up all of the aspects
 * @author Michele Farneti
 */
public final class TankIcon implements TerritoryPlaceHolder {
    private static final int ARMIES_LABEL_HEIGHT = 17;
    private static final int ARMIES_LABEL_WIDTH = 17;
    private static final int ARMIES_LABEL_FONT_SIZE = 14;
    private static final String FONT_NAME = "Arial";
    private static final Integer TANKS_WIDTH = 45;
    private static final Integer TANKS_HEIGTH = 45;
    private final static String sep = File.separator;
    private final String territoryName;
    private final JButton tankIcon;
    private final JLabel armiesLabel;
    private final Position coordinates;

    /**
     * Constructor for a TankIcon, sets up the bounds for both its button and its
     * armies label and initialize it's basic appearence aspects with values got
     * from constants
     * 
     * @param x
     * @param y
     * @param territoryName
     * @param resourcesPackageString
     */
    public TankIcon(final Integer x, final Integer y, final String territoryName, String resourcesPackageString) {
        this.territoryName = territoryName;
        this.coordinates = new Position(x, y);
        this.armiesLabel = new JLabel();
        this.tankIcon = new ColoredImageButton(resourcesPackageString,
                sep + "tanks" + sep + "tank_", x, y, TANKS_WIDTH, TANKS_HEIGTH);
        armiesLabel.setBounds(
                (int) tankIcon.getBounds().getLocation().getX() + TANKS_WIDTH
                        - (ARMIES_LABEL_WIDTH / 2),
                (int) tankIcon.getBounds().getLocation().getY() + TANKS_HEIGTH
                        - ARMIES_LABEL_HEIGHT,
                ARMIES_LABEL_WIDTH, ARMIES_LABEL_HEIGHT);
        armiesLabel.setBackground(Color.white);
        armiesLabel.setForeground(Color.black);
        armiesLabel.setOpaque(true);
        armiesLabel.setFont(new Font(FONT_NAME, Font.BOLD, ARMIES_LABEL_FONT_SIZE));
    }

}
