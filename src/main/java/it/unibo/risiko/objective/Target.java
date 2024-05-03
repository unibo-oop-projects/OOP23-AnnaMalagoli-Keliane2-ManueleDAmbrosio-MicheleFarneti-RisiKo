package it.unibo.risiko.objective;

/**
 * This interface contains some methods that could be applied to a Target
 * @author Keliane Tchoumkeu
 */
public interface Target {
    /**
     * This method tells us if the goal of a specific player has been achieved
     * @return true if the objective is achieved, false if not
     */
    Boolean isAchieved();
}
