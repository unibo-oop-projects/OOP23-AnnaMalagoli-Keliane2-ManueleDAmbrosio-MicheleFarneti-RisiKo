package it.unibo.risiko.view.gameview.components.mainpanel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Simpleimagereader class is used to load an image from the game's resources.
 * It also stores already read Image in order to limit access to the file
 * system.
 * 
 * @author Michele Farneti
 */
public final class SimpleImageReader implements ColoredImageReader, StandradImageReader {
    private static final String FILE_FORMAT = ".png";
    private final Map<String, Image> imagesCache = new HashMap<>();

    @Override
    public Optional<Image> loadImage(final String imagePath) {
        Optional<Image> image = getImageFromCache(imagePath);
        if (!image.isPresent()) {
            try {
                image = Optional.of(ImageIO.read(new File(imagePath + FILE_FORMAT)));
                imagesCache.put(imagePath, image.get());
            } catch (IOException e) {
                image = Optional.empty();
            }
        }
        return image;
    }

    @Override
    public Optional<Image> loadColoredImage(final String imagePath, final String color) {
        return loadImage(imagePath + color);
    }

    /**
     * Searches if the image related to a given path has already been read and
     * eventually returns it.
     * 
     * @param imagePath The path for the image needing to be read.
     * @return An optional of the image if its present in the cache, an empty
     *         optional otherwise.
     */
    private Optional<Image> getImageFromCache(final String imagePath) {
        if (imagesCache.containsKey(imagePath)) {
            return Optional.of(imagesCache.get(imagePath));
        }
        return Optional.empty();
    }
}
