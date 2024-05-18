package it.unibo.risiko.gameView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import it.unibo.risiko.event_register.Register;
import it.unibo.risiko.game.Game;
import it.unibo.risiko.player.Player;

public class LoggerView extends JPanel{
    private Register register;
    private String eventList="";
    private List<Player> playerList;
    
    @SuppressWarnings("unchecked")
    public LoggerView(Game game, Register register){
        this.register=register;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000, 1000));
        //creating a textArea, the event container
        JTextArea logText=new JTextArea("");
        logText.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
        //creating a scroller containing the event container
        JScrollPane logTextScroller=new JScrollPane(logText);
        //creating a new panel 
        JPanel options=new JPanel();
        options.setLayout(new BoxLayout(options,BoxLayout.LINE_AXIS));
        //getting the list of the players
        List<Player> playerList=game.getListOfPlayers();
        //the list that will contain the color_id of the players
        List<String> playerNameList=new ArrayList<>();
        String[] list=new String[playerList.size()];
        playerList.stream().map(i->i.getColor_id())
                           .forEach(j->playerNameList.add(j));
        //creating a JComboBox for the selection of the player we want to visualize the events
        JComboBox<String> playerOptions = new JComboBox<>(list);
        playerOptions.setRenderer(new MyComboBoxRenderer("Select the player you want the events to be displayed"));
        playerOptions.setSelectedIndex(-1);
        //the button used to show all the events
        JButton allEvent=new JButton("Show all events");
        allEvent.setBorder(BorderFactory.createEmptyBorder(8,195,8,195));
        allEvent.setFont(new Font("Arial", Font.BOLD, 14));
        //adding the button and the JComboBox to the panel options
        options.add(allEvent);
        options.add(playerOptions);
        //adding the options and logTextScroller to the loggerView
        this.add(options,BorderLayout.PAGE_START);
        this.add(logTextScroller,BorderLayout.CENTER);
        //adding actionListerner to the button and the JComboBox
        allEvent.addActionListener(e->showAllEvents(logText));
        playerOptions.addActionListener(e->showEventByPlayerName(logText,playerOptions));
    }

    /**
     * Method that shows all the events of a specific player
     * @param logText the textArea that will contain the events
     * @param playerOptions the element from which was selected the player whose events 
     * should be visualize 
     */
    private void showEventByPlayerName(JTextArea logText, JComboBox<String> playerOptions) {
        this.eventList="";
        Optional<Player> selectedPlayer=Optional.empty();
        String effectiveSelectedColor=(String)playerOptions.getSelectedItem();
        for (int j = 0; j < playerList.size() && selectedPlayer.equals(Optional.empty()); j++) {
            if (playerList.get(j).getColor_id().equals(effectiveSelectedColor)) {
                selectedPlayer=Optional.of(playerList.get(j));
            }
        }
        register.getAllEventsPlayer(selectedPlayer.get()).forEach(i->eventList+=i.toString()+"\n");
        updateLogText(logText, eventList);
    }

    /**
     * Method that shows all the events of the game
     * @param logText the textArea that will contain the events
     */
    private void showAllEvents(JTextArea logText) {
        this.eventList="";
        register.getAllEvents().forEach(i->eventList+=i.toString()+"\n");
        updateLogText(logText, eventList);
    }

    /**
     * Update the textArea which contains the events to visualize
     * @param logText the textArea which has to contain the events
     * @param s string representation of the events to visualize
     */
    private void updateLogText(JTextArea logText, String s) {
        logText.setText(s);
    }

    /**
     * Inner Class which help to set a title in the JComboBox 
     * used to select the player whose events we want to see
     */
    @SuppressWarnings("rawtypes")
    class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
        private String _title;

        public MyComboBoxRenderer(String title) {
            _title = title;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean hasFocus) {
            if (index == -1 && value == null){
            setText(_title);
            setFont(new Font("Arial", Font.BOLD, 14));
            }else{
            setText(value.toString());
            }
            return this;
        }
    }
}
