package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * ColoredImageButton enables to create a button with a given image as
 * background,
 * it also allows to change the image color by generating the right path to find
 * it in the resources folder.
 * 
 * @author Michele Farneti
 */
public final class ColoredImageButton extends JButton {
    private final String resourcesPackagePath;
    private static final String FILE_FORMAT = ".png";
    private static final int BORDER_THICKNESS = 2;
    private static final long serialVersionUID = 1;

    private final String imageUri;
    private String imageColor = "white";

    /**
     * @param imageUri             The url of the image to be set as backgroud of
     *                             the button
     * @param resourcesPackagePath The path needed to reach the button image
     */
    public ColoredImageButton(final String resourcesPackagePath, final String imageUri) {
        this.imageUri = imageUri;
        this.resourcesPackagePath = resourcesPackagePath;
    }

    /**
     * Constructor wich sets the button background image and also sets its bounds
     * 
     * @param resourcesPackagePath The path needed to reach the button image
     * @param imageUrl
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public ColoredImageButton(final String resourcesPackagePath, final String imageUrl, final int x, final int y,
            final int width, final int height) {
        this.resourcesPackagePath = resourcesPackagePath;
        this.imageUri = imageUrl;
        this.setBounds(x, y, width, height);
        this.setOpaque(false);
    }

    /**
     * @param imageColor A string raprresenting the color in rgb format.
     */
    public void setColor(final String imageColor) {
        this.imageColor = imageColor;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final String coloredImageUrl = resourcesPackagePath + imageUri + imageColor + FILE_FORMAT;
        try {
            final Image image = ImageIO.read(new File(coloredImageUrl));
            g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
        } catch (IOException e) {
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Sets a prederminated border of a given color
     */
    public void setCustomBorder(final Color borderColor) {
        this.setBorder(BorderFactory.createLineBorder(borderColor, BORDER_THICKNESS));
    }
}
