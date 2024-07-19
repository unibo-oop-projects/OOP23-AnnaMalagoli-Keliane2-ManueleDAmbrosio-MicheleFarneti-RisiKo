package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Image read class is used to load an image from the game's resources
 * 
 * @author Michele Farneti
 */
public class SimpleImageReader implements ColoredImageReader,StandradImageReader{
    private final static String fileFormat = File.separator + ".png";

    @Override
    public Optional<Image> loadImage(final String imagePath) {
        Optional<Image> image = Optional.empty();
        try {
            image = Optional.of(ImageIO.read(new File(imagePath + fileFormat)));
            return image;
        } catch (IOException e) {
            return image;
        }
    }

    @Override
    public Optional<Image> loadColoredImage(final String imagePath, final String color) {
        return loadImage(imagePath + color);
    }
}