package it.unibo.risiko.view.gameView;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.util.List;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.map.Territory;

/**
 * TablePanel is the class for the creation of a panel that contains
 * the table of territories.
 * @author Anna Malagoli 
 */
public class TablePanel extends JPanel {

    private JTable table;
    private TableModelTerritories tableModelTerritories;
    /**
     * Into the constructor is created the table. 
     */
    public TablePanel() {

        tableModelTerritories = new TableModelTerritories();
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
    }

    /**
     * Method used to set the data in the model of the table.
     * @param terr is the list of territories owned by the player
     * @param players is the list of players
     */
    public void setData(List<Territory> terr, List<Player> players) {
        tableModelTerritories.setData(terr, players);
    }
    
    /**
     * Method to update the model of the table.
     */
    public void update() {
        tableModelTerritories.fireTableDataChanged();
    }

}

