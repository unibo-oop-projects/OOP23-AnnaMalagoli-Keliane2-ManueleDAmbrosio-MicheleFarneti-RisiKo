package it.unibo.risiko.model.objective;

import java.util.Random;

/**
 * @author Farneti Michele
 *         Each value of these enum describes a different typ of target.
 */
public enum TargetType {
    /**
     * Destroy a player target.
     */
    PLAYER,
    /**
     * Conquire a continent.
     */
    CONTINENT,
    /**
     * Conquire a territory target.
     */
    TERRITORY;

    private static final Random TARGET_GENERATOR = new Random();

    /**
     * @return A random target type
     */
    public static TargetType randomTargetType() {
        final TargetType[] targetTypes = values();
        return targetTypes[TARGET_GENERATOR.nextInt(targetTypes.length)];
    }
}
