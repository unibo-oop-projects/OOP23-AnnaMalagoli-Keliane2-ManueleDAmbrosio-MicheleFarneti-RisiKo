package it.unibo.risiko.view.gameview.imagereaders;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A proxy class for StandardImageReader allowing to store an already read image
 * in
 * a cache.
 * 
 * @author Michele Farneti
 */

public class ImageReaderWithCache implements StandradImageReader {
    private static final Map<String, Image> IMAGES_CACHE = new HashMap<>();
    private static final StandradImageReader IMAGE_READER = new SimpleImageReader();

    /**
     * This loadImage method uses the loadImage method of a simpleImageReader adding
     * caching.
     */
    @Override
    public Optional<Image> loadImage(final String imagePath) {
        Optional<Image> image = getImageFromCache(imagePath);
        if (!image.isPresent()) {
            image = IMAGE_READER.loadImage(imagePath);
            IMAGES_CACHE.put(imagePath, image.get());
        }
        return image;
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
        if (IMAGES_CACHE.containsKey(imagePath)) {
            return Optional.of(IMAGES_CACHE.get(imagePath));
        }
        return Optional.empty();
    }
}
