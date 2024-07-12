package it.unibo.risiko.view.gameView;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import it.unibo.risiko.model.player.Player;
import it.unibo.risiko.model.map.Territory;

/**
 * Creation of a model for the table of territories.
 * This model organizes datas that have to be displayed
 * in the table.
 * In the first column are displayed the names of the territories,
 * in the second the names of the continents where are placed the territories,
 * in the third the number of armies prentent in the territories and
 * in the fourth the players that own the territories.
 * @author Anna Malagoli 
 */
public class TableModelTerritories extends AbstractTableModel{

    private List<Territory> listTerritories;
    private List<Player> listPlayers;
    private int NUM_COLUMN = 4;
    /*the following list is used to contain the name of every column in the table*/
    private String[] columnsName = {"Territory", "Continet", "NumArmies", "Player"};
    /**
     * Method to return the number of rows displayed in the table that corresponds
     * to the number of territories of the map.
     * @return the number of rows that are displayed in the table.
     */
    public int getRowCount() {
        return listTerritories.size();
    }

    /**
     * Method that returns the number of columns of the table that corresponds to
     * the properties that must be displayed for each territory in the table.
     * @return the number of columns of the table.
     */
    public int getColumnCount() {
        return NUM_COLUMN;
    }

    /**
     * Method to return the value for the cell at columnIndex and rowIndex.
     * @param rowIndex is the row whose value is to be queried
     * @param columnIndex is the column whose value is to be queried
     * @return the value for the cell at columnIndex and rowIndex
     */
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        Territory territory = this.listTerritories.get(rowIndex);

        switch(columnIndex) {
            case 0:
                return territory.getTerritoryName();
            case 1:
                return territory.getContinentName();
            case 2:
                return territory.getNumberOfArmies();
            case 3:
                for(var player : this.listPlayers) {
                    if(player.isOwnedTerritory(territory)) {
                        return player.getColor_id();
                    }
                }
            default:
                return "Error";
        }
    }

    /**
     * Method to set datas in the table model.
     * @param terr is the list of territories
     * @param players is the list of players
     */
    public void setData(final List<Territory> terr, final List<Player> players) {
        this.listPlayers = players;
        this.listTerritories = terr;
    }

    /**
     * Method to set the names of the columns in the table.
     * @param columnIndex is the number of the column
     * @return the name of the column
     */
    public String getColumnName(final int columnIndex) {
        return this.columnsName[columnIndex];
    }

}
