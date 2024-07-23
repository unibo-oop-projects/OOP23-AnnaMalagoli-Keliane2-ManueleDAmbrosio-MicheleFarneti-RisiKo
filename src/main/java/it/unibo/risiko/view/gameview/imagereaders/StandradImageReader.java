package it.unibo.risiko.view.gameview.imagereaders;

import java.awt.Image;
import java.util.Optional;

/**
 * A simple image reader to carry on tasks about reading images from the game
 * resources automatically handling exceptions.
 * @author Michele Farneti
 */
public interface StandradImageReader {
    /**
     * Read an image from a given path.
     * 
     * @param imagePath The path
     * @return Optional of the image if the image is present, Empty optional
     *         otherwise.
     */
    Optional<Image> loadImage(String imagePath);
}
