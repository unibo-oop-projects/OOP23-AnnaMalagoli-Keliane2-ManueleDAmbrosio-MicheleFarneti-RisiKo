package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Image;
import java.util.Optional;

/**
 * Class used to carry on tasks about reading images from the file system by
 * follwing a predeterminated image name structure also indicating it's color.
 * The immage name should appear like /namecolor.png.
 */
public interface ColoredImageReader {
    /**
     * Read an image from a given path specifying it's color.
     * 
     * @param imagePath The path.
     * @param color     A string rappresenting the color of the image
     * @return Optional of the image if the image is present, Empty optional
     *         otherwise.
     */
    Optional<Image> loadColoredImage(String imagePath, String color);
}
