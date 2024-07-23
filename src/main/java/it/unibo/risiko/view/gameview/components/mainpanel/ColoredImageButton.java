package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * ColoredImageButton enables to create a button with a given image as
 * background,it also allows to change the image color by generating the right
 * path to find it in the resources folder.
 * 
 * @author Michele Farneti
 */
public final class ColoredImageButton extends JButton {
    private final String resourcesPackagePath;
    private static final int BORDER_THICKNESS = 2;
    private static final long serialVersionUID = 1;
    private final transient ColoredImageReader imageReader = new SimpleImageReader();

    private final String imageName;
    private String imageColor = "white";

    /**
     * @param imageName            The name of the image to be set as backgroud of
     *                             the button
     * @param resourcesPackagePath The path needed to reach the button image
     */
    public ColoredImageButton(final String resourcesPackagePath, final String imageName) {
        this.imageName = imageName;
        this.resourcesPackagePath = resourcesPackagePath;
    }

    /**
     * Constructor wich sets the button background image and also its bounds.
     * 
     * @param resourcesPackagePath The path needed to reach the button image
     * @param imageName            The path reach the image file after the resources
     *                             package ruling out the la spart of the name
     *                             indicating the color
     * @param x                    Starting x for the bounds to be set with
     * @param y                    Starting y for the bounds to be set with
     * @param width
     * @param height
     */
    public ColoredImageButton(final String resourcesPackagePath, final String imageName, final int x, final int y,
            final int width, final int height) {
        this.resourcesPackagePath = resourcesPackagePath;
        this.imageName = imageName;
        this.setBounds(x, y, width, height);
        this.setOpaque(false);
    }

    /**
     * @param imageColor A string rappresenting the color in rgb format.
     */
    public void setColor(final String imageColor) {
        this.imageColor = imageColor;
    }

    /**
     * @return The color the button image is currently set.
     */
    public String getColor() {
        return this.imageColor;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        imageReader.loadColoredImage(resourcesPackagePath + imageName, imageColor)
                .ifPresent(image -> g.drawImage(image, 0, 0, getWidth(), getHeight(), null));
    }

    /**
     * Sets a prederminated border of a given color.
     * 
     * @param borderColor The color the border should be painted with.
     */
    public void setCustomBorder(final Color borderColor) {
        this.setBorder(BorderFactory.createLineBorder(borderColor, BORDER_THICKNESS));
    }
}
