package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Graphics;
import javax.swing.JPanel;

import it.unibo.risiko.view.gameview.imagereaders.SimpleImageReader;
import it.unibo.risiko.view.gameview.imagereaders.StandradImageReader;

/**
 * Realizes a JPanel with an image as background.
 * 
 * @author Michele Farneti
 */
public final class BackgroundImagePanel extends JPanel {
    private static final long serialVersionUID = 1;
    private final transient StandradImageReader imageReader = new SimpleImageReader();
    private final String imageName;
    private final String resourcesPackagePath;

    /**
     * Initialization with the path for the resource.
     * 
     * @param resourcesPackage
     * @param imageName
     */
    public BackgroundImagePanel(final String resourcesPackage, final String imageName) {
        this.imageName = imageName;
        this.resourcesPackagePath = resourcesPackage;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        imageReader.loadImage(resourcesPackagePath + imageName)
                .ifPresent(image -> g.drawImage(image, 0, 0, getWidth(), getHeight(), null));
    }
}
