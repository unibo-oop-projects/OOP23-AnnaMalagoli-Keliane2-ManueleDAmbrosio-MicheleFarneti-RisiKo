package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import it.unibo.risiko.model.map.Territory;
import it.unibo.risiko.view.gameview.components.Position;

/**
 * Class used to sum up all of the aspects
 * 
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
        private final ColoredImageButton tankIcon;
        private final JLabel armiesLabel;

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
        public TankIcon(final Integer x, final Integer y, final String territoryName,
                        final String resourcesPackageString, ActionListener al) {
                this.territoryName = territoryName;
                this.armiesLabel = new JLabel();
                this.tankIcon = new ColoredImageButton(resourcesPackageString,
                                sep + "tanks" + sep + "tank_", x, y, TANKS_WIDTH, TANKS_HEIGTH);
                armiesLabel.setBounds(
                                (int) tankIcon.getBounds().getLocation().getX() + TANKS_WIDTH
                                                - (ARMIES_LABEL_WIDTH / 2),
                                (int) tankIcon.getBounds().getLocation().getY() + TANKS_HEIGTH
                                                - ARMIES_LABEL_HEIGHT,
                                ARMIES_LABEL_WIDTH, ARMIES_LABEL_HEIGHT);
                tankIcon.addActionListener(al);
                tankIcon.setBorderPainted(false);
                tankIcon.setContentAreaFilled(false);
                armiesLabel.setBackground(Color.white);
                armiesLabel.setForeground(Color.black);
                armiesLabel.setOpaque(true);
                armiesLabel.setFont(new Font(FONT_NAME, Font.BOLD, ARMIES_LABEL_FONT_SIZE));
                tankIcon.setEnabled(true);
        }

        @Override
        public void addToLayoutPane(final JLayeredPane layerdPane,final Integer layer) {
                layerdPane.add(tankIcon, layer,0);
                layerdPane.add(armiesLabel, layer+1,0);
        }

        @Override
        public void redrawTank(Territory territory) {
                tankIcon.setColor(territory.getPlayer());
                armiesLabel.setText(String.valueOf(territory.getNumberOfArmies()));
        }

        @Override
        public String getTerritoryName() {
                return territoryName;
        }

        @Override
        public void resetBorder() {
                this.tankIcon.setBorderPainted(false);
        }

        @Override
        public void setEnabled(Boolean enabled) {
                tankIcon.setEnabled(enabled);
        }

        @Override
        public void setFighting(Color color) {
                tankIcon.setCustomBorder(color);
                tankIcon.setBorderPainted(true);
        }

}
