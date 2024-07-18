package it.unibo.risiko.view.gameview.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import it.unibo.risiko.model.event_register.Register;
import it.unibo.risiko.model.player.Player;

/**
 * The panel used as events log
 * @author Keliane Nana
 */
public class LoggerView extends JPanel{
    private final List<Player> playerList;
    private final Register register; 
    private String eventList="";
    private String[] list;
    private JTextArea logText;
    
    @SuppressWarnings("unchecked")
    public LoggerView(final Register register, final List<Player> playerList){
        this.playerList=playerList;
        this.register=register;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        //creating a textArea, the event container
        logText=new JTextArea(20,20);
        logText.setEditable(false);
        logText.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
        //creating a scroller containing the event container
        JScrollPane logTextScroller=new JScrollPane(logText);
        logTextScroller.setSize(new Dimension(this.getWidth(),this.getHeight()));
        //the list that will contain the color_id of the players
        List<String> playerNameList=new ArrayList<>();
        list=new String[playerList.size()];
        playerList.stream().map(i->i.getColorID())
                           .forEach(j->playerNameList.add(j));
        this.setList(playerNameList);
        //creating a JComboBox for the selection of the player we want to visualize the events
        JComboBox<String> playerOptions = new JComboBox<>(list);
        playerOptions.setRenderer(new MyComboBoxRenderer("Show events of:"));
        playerOptions.setSelectedIndex(-1);
        //the button used to show all the events
        JButton allEvent=new JButton("Show all events");
        allEvent.setFont(new Font("Arial", Font.BOLD, 14));
        allEvent.setAlignmentX(Component.CENTER_ALIGNMENT);
        //adding the button and the JComboBox to the panel options
        this.add(allEvent);
        this.add(Box.createRigidArea(new Dimension(0,2)));
        this.add(playerOptions);
        this.add(Box.createRigidArea(new Dimension(0,2)));
        //adding the options and logTextScroller to the loggerView
        this.add(logTextScroller,BorderLayout.CENTER);
        //adding actionListerner to the button and the JComboBox
        allEvent.addActionListener(e->showAllEvents(logText));
        playerOptions.addActionListener(e->showEventByPlayerName(logText,playerOptions));
    }

    private void setList(List<String> playerNameList) {
        for (int i = 0; i < list.length; i++) {
            list[i]=playerNameList.get(i);
        }
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
            if (playerList.get(j).getColorID().equals(effectiveSelectedColor)) {
                selectedPlayer=Optional.of(playerList.get(j));
            }
        }
        register.getAllEventsPlayer(selectedPlayer.get()).forEach(i->eventList+=i.getDescription()+"\n");
        updateLogText(logText, eventList);
    }

    /**
     * Method that shows all the events of the game
     * @param logText the textArea that will contain the events
     */
    public void showAllEvents(JTextArea logText) {
        this.eventList="";
        register.getAllEvents().forEach(i->{eventList+=i.getDescription()+"\n"; System.out.println(i.getDescription());});
        updateLogText(logText, eventList);
    }

    /**
     * 
     * @return the textArea which should contains the events
     */
    public JTextArea getTextArea(){
        return this.logText;
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
