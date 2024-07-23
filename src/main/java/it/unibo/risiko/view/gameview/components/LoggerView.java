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
 * The panel used as events' log.
 * 
 * @author Keliane Nana
 */
public class LoggerView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private static final int WIDTH_SPACE = 0;
    private static final int HEIGHT_SPACE = 2;
    private static final int ROWS_COL = 20;
    private final JButton allEvent;
    private String eventList = "";
    private String[] list;
    private final JTextArea logText;

    /**
     * @param register   the register of the party
     * @param playerList the list containing the players of the party
     */
    public LoggerView(final Register register, final List<Player> playerList) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // creating a textArea, the event container
        logText = new JTextArea(ROWS_COL, ROWS_COL);
        logText.setEditable(false);
        logText.setFont(new Font("Arial", Font.CENTER_BASELINE, FONT_SIZE));
        // creating a scroller containing the event container
        final JScrollPane logTextScroller = new JScrollPane(logText);
        // the list that will contain the color_id of the players
        final List<String> playerNameList = new ArrayList<>();
        list = new String[playerList.size()];
        playerList.stream().map(i -> i.getColorID())
                .forEach(j -> playerNameList.add(j));
        this.setList(playerNameList);
        // creating a JComboBox for the selection of the player we want to visualize the
        // events
        final JComboBox<String> playerOptions = new JComboBox<>(list);
        playerOptions.setRenderer(new MyComboBoxRenderer("Show events of:"));
        playerOptions.setSelectedIndex(-1);
        // the button used to show all the events
        allEvent = new JButton("Show all events");
        allEvent.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        allEvent.setAlignmentX(Component.CENTER_ALIGNMENT);
        // adding the button and the JComboBox to the panel options
        this.add(allEvent);
        this.add(Box.createRigidArea(new Dimension(WIDTH_SPACE, HEIGHT_SPACE)));
        this.add(playerOptions);
        this.add(Box.createRigidArea(new Dimension(WIDTH_SPACE, HEIGHT_SPACE)));
        // adding the options and logTextScroller to the loggerView
        this.add(logTextScroller, BorderLayout.CENTER);
        // adding actionListerner to the button and the JComboBox
        allEvent.addActionListener(e -> showAllEvents(logText, register));
        playerOptions.addActionListener(e -> showEventByPlayerName(logText, playerOptions, playerList, register));
    }

    private void setList(final List<String> playerNameList) {
        for (int i = 0; i < list.length; i++) {
            list[i] = playerNameList.get(i);
        }
    }

    /**
     * Method that shows all the events of a specific player.
     * 
     * @param logText       the textArea that will contain the events
     * @param playerOptions the element from which was selected the player whose
     *                      events
     *                      should be visualize
     * @param playerList
     * @param register
     */
    private void showEventByPlayerName(final JTextArea logText, final JComboBox<String> playerOptions,
            final List<Player> playerList, final Register register) {
        this.eventList = "";
        Optional<Player> selectedPlayer = Optional.empty();
        final String effectiveSelectedColor = (String) playerOptions.getSelectedItem();
        for (int j = 0; j < playerList.size() && selectedPlayer.equals(Optional.empty()); j++) {
            if (playerList.get(j).getColorID().equals(effectiveSelectedColor)) {
                selectedPlayer = Optional.of(playerList.get(j));
            }
        }
        eventList += selectedPlayer.get().getTarget().remainingActionsToString() + "\n\n";
        register.getAllEventsPlayer(selectedPlayer.get()).forEach(i -> eventList += i.getDescription() + "\n");
        updateLogText(logText, eventList);
    }

    /**
     * Method that shows all the events of the game.
     * 
     * @param logText  the textArea that will contain the events
     * @param register the game register
     */
    public void showAllEvents(final JTextArea logText, final Register register) {
        this.eventList = "";
        register.getAllEvents().forEach(i -> {
            eventList += i.getDescription() + "\n";
        });
        updateLogText(logText, eventList);
    }

    /**
     * Used to implicitly click the button allEvent.
     */
    public void pressButtonAllEvent() {
        this.allEvent.doClick();
    }

    /**
     * Update the textArea which contains the events to visualize.
     * 
     * @param logText the textArea which has to contain the events
     * @param s       string representation of the events to visualize
     */
    private void updateLogText(final JTextArea logText, final String s) {
        logText.setText(s);
    }

    /**
     * Inner static Class which help to set a title in the JComboBox
     * used to select the player whose events we want to see.
     */
    static class MyComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
        private static final long serialVersionUID = 1L;
        private final String title0;

        MyComboBoxRenderer(final String title) {
            title0 = title;
        }

        @Override
        public Component getListCellRendererComponent(final JList<? extends String> list, final String value,
                final int index, final boolean isSelected, final boolean cellHasFocus) {
            if (index == -1 && value == null) {
                setText(title0);
                setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
            } else {
                setText(value);
            }
            return this;
        }

    }
}
