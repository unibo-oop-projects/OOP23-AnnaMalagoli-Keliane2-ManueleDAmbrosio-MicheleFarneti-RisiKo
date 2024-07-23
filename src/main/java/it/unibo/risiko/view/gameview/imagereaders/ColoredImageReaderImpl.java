package it.unibo.risiko.view.gameview.imagereaders;

import java.awt.Image;
import java.util.Optional;

/**
 * Implementation of a iname reader specialized in reading images having
 * different versions for each color. Uses an ImageReaderWithCache to reduce the
 * number of acesses to the file system.
 * 
 * @author Michele Farneti
 */
public final class ColoredImageReaderImpl implements ColoredImageReader {
    private static final StandradImageReader IMAGE_READER = new ImageReaderWithCache();

    @Override
    public Optional<Image> loadColoredImage(final String imagePath, final String color) {
        return IMAGE_READER.loadImage(imagePath + color);
    }
}
