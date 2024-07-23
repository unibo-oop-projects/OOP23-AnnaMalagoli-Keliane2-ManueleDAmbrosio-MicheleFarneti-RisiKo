package it.unibo.risiko.view.gameview.imagereaders;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Simpleimagereader class is used to load an image from the game's resources.
 * system with a pre detrminate format.
 * 
 * @author Michele Farneti
 */
public final class SimpleImageReader implements StandradImageReader {
    private static final String FILE_FORMAT = ".png";

    @Override
    public Optional<Image> loadImage(final String imagePath) {
        Optional<Image> image;
        try {
            image = Optional.of(ImageIO.read(new File(imagePath + FILE_FORMAT)));
        } catch (IOException e) {
            image = Optional.empty();
        }
        return image;
    }
}
