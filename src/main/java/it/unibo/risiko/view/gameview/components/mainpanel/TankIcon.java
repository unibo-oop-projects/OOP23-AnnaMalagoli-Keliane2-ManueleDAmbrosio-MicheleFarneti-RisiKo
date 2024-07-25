package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import it.unibo.risiko.model.map.Territory;

/**
 * Class used to sum up all of the aspects.
 * 
 * @author Michele Farneti
 */
public final class TankIcon implements TerritoryPlaceHolder {
        private static final int ARMIES_LABEL_HEIGHT = 17;
        private static final int ARMIES_LABEL_WIDTH = 17;
        private static final int ARMIES_LABEL_FONT_SIZE = 12;
        private static final String FONT_NAME = "Dialog";
        private static final Integer TANKS_WIDTH = 45;
        private static final Integer TANKS_HEIGTH = 45;
        private static final String SEP = File.separator;
        private final String territoryName;
        private final ColoredImageButton tankButton;
        private final JLabel armiesLabel;

        /**
         * Constructor for a TankIcon, sets up the bounds for both its button and its
         * armies label and initialize it's basic appearence aspects with values got
         * from constants.
         * 
         * @param x
         * @param y
         * @param territoryName
         * @param resourcesPackageString
         * @param al                     The action listener for clicks over the tanks.
         */
        public TankIcon(final Integer x, final Integer y, final String territoryName,
                        final String resourcesPackageString, final ActionListener al) {
                this.territoryName = territoryName;
                this.armiesLabel = new JLabel();
                this.tankButton = new ColoredImageButton(resourcesPackageString,
                                SEP + "tanks" + SEP + "tank_", x, y, TANKS_WIDTH, TANKS_HEIGTH);
                armiesLabel.setBounds(
                                (int) tankButton.getBounds().getLocation().getX() + TANKS_WIDTH
                                                - (ARMIES_LABEL_WIDTH / 2),
                                (int) tankButton.getBounds().getLocation().getY() + TANKS_HEIGTH
                                                - ARMIES_LABEL_HEIGHT,
                                ARMIES_LABEL_WIDTH, ARMIES_LABEL_HEIGHT);
                tankButton.addActionListener(al);
                tankButton.setBorderPainted(false);
                tankButton.setContentAreaFilled(false);
                armiesLabel.setBackground(Color.white);
                armiesLabel.setForeground(Color.black);
                armiesLabel.setOpaque(true);
                armiesLabel.setFont(new Font(FONT_NAME, Font.BOLD, ARMIES_LABEL_FONT_SIZE));
                tankButton.setEnabled(true);
        }

        @Override
        public void addToLayoutPane(final JLayeredPane layerdPane, final Integer layer) {
                layerdPane.add(tankButton, layer, 0);
                layerdPane.add(armiesLabel, layer + 1, 0);
        }

        @Override
        public void redrawTank(final Territory territory) {
                if (!tankButton.getColor().equals(territory.getPlayer())) {
                        tankButton.setColor(territory.getPlayer());
                }
                if (!armiesLabel.getText().equals(String.valueOf(territory.getNumberOfArmies()))) {
                        armiesLabel.setText(String.valueOf(territory.getNumberOfArmies()));
                }
        }

        @Override
        public String getTerritoryName() {
                return territoryName;
        }

        @Override
        public void resetBorder() {
                this.tankButton.setBorderPainted(false);
        }

        @Override
        public void setEnabled(final Boolean enabled) {
                tankButton.setEnabled(enabled);
        }

        @Override
        public void setFighting(final Color color) {
                tankButton.setCustomBorder(color);
                tankButton.setBorderPainted(true);
        }

}
