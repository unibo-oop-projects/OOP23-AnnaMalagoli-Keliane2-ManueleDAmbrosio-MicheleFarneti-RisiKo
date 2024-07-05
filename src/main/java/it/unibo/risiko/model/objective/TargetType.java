package it.unibo.risiko.model.objective;

import java.util.Random;

/**
 * @author Farneti Michele
 * Each value of these enum describes a different typ of target
 */
public enum TargetType {
    PLAYER,CONTINENT,TERRITORY;
    
    private static final Random TargetGenerator = new Random();

    /** 
     * @return A random target type
     */
    public static TargetType randomTargetType()  {
        TargetType[] targetTypes = values();
        return targetTypes[TargetGenerator.nextInt(targetTypes.length)];
    }
}
