package it.unibo.risiko.model.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of @PlayerFactory inteface.
 * 
 * @author Manuele D'Ambrosio
 */

public class SimplePlayerFactory implements PlayerFactory {
    private int FIRST_COLOR_INDEX = 0;
    private List<String> colorList = new ArrayList<>();
    private int colorIndex;

    public SimplePlayerFactory() {
        this.colorList = List.of("cyan", "blue", "green", "red", "pink", "yellow");
        this.colorIndex = FIRST_COLOR_INDEX;
    }

    @Override
    public StdPlayer createStandardPlayer() {
        return new StdPlayer(nextColor());
    }

    @Override
    public EasyModePlayer createAIPlayer() {
        return new EasyModePlayerImpl(nextColor());
    }

    private String nextColor() {
        int nextColor = colorIndex;
        colorIndex++;
        if (nextColor < colorList.size()) {
            return colorList.get(nextColor);
        }
        return colorList.get(nextColor) + Integer.toString(nextColor - colorList.size());
    }

}
