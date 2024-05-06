package it.unibo.risiko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * The Territories class is used by the controller at the start of the game to generate 
 * the list of territories that are in the map. The informations about the territories are 
 * memorized in a file text that contains the name of a territory and the list of the 
 * neighboring territories after the name of the continent. 
 * 
 * @author Anna Malagoli 
 */
public class Territories {

    private final List<Territory> listTerritories = new ArrayList<>();
    /**
     * Contructor to set the list of territories by extracting informations 
     * from a file text. If an exeption is thrown the list of territories is empty.
     * 
     * @param filePath is the relative path of the text file
     */
    public Territories(final String filePath) {
        final File file = new File(filePath);
        final String absoluteFilePath = file.getAbsolutePath();
        try {
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            try {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String stringRow;
            String continentName = " ";
            String nameTerritory;
            List<String> listNearTerritory;

            stringRow = bufferedReader.readLine();
            do {
                if (stringRow.contains(":")) { 
                    continentName = stringRow.substring(0, stringRow.length() - 1);
                } else {
                    final List<String> nations = Arrays.asList(stringRow.split(" "));
                    nameTerritory = nations.get(0);
                    listNearTerritory = new ArrayList<>(nations);
                    listNearTerritory.remove(nameTerritory);
                    final Territory territory = new TerritoryImpl(nameTerritory, continentName, listNearTerritory);
                    this.listTerritories.add(territory);
                }
                stringRow = bufferedReader.readLine();
            } while (stringRow != null);
            bufferedReader.close();

        } catch (IOException e) {
            listTerritories.clear();
        }
    } catch (FileNotFoundException e) {
            listTerritories.clear();
        }
    }

    /**
     * Method used to get the list of all the territories extracted from the text file.
     * @return the list of territories
     */
    public List<Territory> getList() {
        return List.copyOf(listTerritories); 
    }
}
