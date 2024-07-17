package it.unibo.risiko.view.gameView;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.map.Territory;

/**
 * TablePanel is the class for the creation of a panel that contains
 * the table of territories.
 * @author Anna Malagoli 
 */
public class TablePanel extends JPanel {

    private static final int HEIGHT = 383;
    private static final int WIDTH = 270;
    private static final int COLUMN_INDEX_PLAYER_ID = 3;
    private static final int COLUMN_INDEX_NUM_ARMIES = 2;
    private JTable table;
    private TableModelTerritories tableModelTerritories;

    /**
     * Into the constructor is created the table. 
     * @param terr is the list of territories
     * @param player is the list of players
     */
    public TablePanel(final List<Territory> terr, final List<Player> players) {

        tableModelTerritories = new TableModelTerritories(terr, players);
        /*the constructor of the JTable takes as input the table model 
         * previously created.
        */
        table = new JTable(tableModelTerritories);
        this.setLayout(new BorderLayout());
        /*When the table is inserted into the panel
        it must be inserted into a scroll pane (a panel
        which has a scroll bar on the side). 
        In this way if there are more rows in the table than can 
        be displayed you can scroll with the sidebar to view them all*/
        add(new JScrollPane(table), BorderLayout.CENTER);
        /*setting the dimension of the panel and of the columns*/
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        table.getColumnModel().getColumn(COLUMN_INDEX_NUM_ARMIES).setPreferredWidth(1);
        table.getColumnModel().getColumn(COLUMN_INDEX_PLAYER_ID).setPreferredWidth(1);
    }

    /**
     * Method to update the model of the table.
     */
    public void update() {
        tableModelTerritories.fireTableDataChanged();
    }

}

