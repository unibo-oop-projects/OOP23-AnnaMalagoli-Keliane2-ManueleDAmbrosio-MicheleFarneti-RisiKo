package it.unibo.risiko.player;

public class SimplePlayerFactory implements PlayerFactory{

    public SimplePlayerFactory() {}

    public StdPlayer createStandardPlayer(String color, int armiesToPlace) {
        return new StdPlayer(color, armiesToPlace);
    }

    public EasyModePlayer createAIPlayer(String color, int armiesToPlace) {
        return new EasyModePlayer(color, armiesToPlace);
    };

}
