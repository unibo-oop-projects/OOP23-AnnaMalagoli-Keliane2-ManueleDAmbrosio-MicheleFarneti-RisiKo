package it.unibo.risiko.view.gameView.gameViewComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 * ColoredImageButton enables to create a button with a given image as background, 
 * it also allows to change the image color by generating the right path to find it in the resources folder.
 * @author Michele Farneti
 */
public class ColoredImageButton extends JButton{
    private final String STANDARD_COLOR = "255255255";
    private static final String resourcesPackageString = "build/resources/main/it/unibo/risiko";
    private static final String fileFormat = ".png";

    private String imageUrl;
    private String imageColor = STANDARD_COLOR;
    
    public ColoredImageButton(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    /**
     * @param rgbImageColor A string raprresenting the color in rgb format.
     */
    public void setColor(String rgbImageColor){
        imageColor = rgbImageColor;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        String coloredImageUrl = resourcesPackageString + imageUrl + imageColor+fileFormat;
        try {
            Image image = ImageIO.read(new File(coloredImageUrl));
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        } catch (IOException e) {
            System.out.println("Failed to load image " + coloredImageUrl);
        }
    }
}
