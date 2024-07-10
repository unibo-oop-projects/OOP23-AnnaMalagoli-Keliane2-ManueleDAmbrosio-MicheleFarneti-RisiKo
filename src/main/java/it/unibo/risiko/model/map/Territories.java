package it.unibo.risiko.model.map;

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
import java.util.Optional;
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
    private final List<Continent> listContinents = new ArrayList<>();
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
                    if (!this.isInList(continentName)) {
                        this.listContinents.add(new ContinentImpl(continentName));
                    }
                } else {
                    final List<String> nations = Arrays.asList(stringRow.split(" "));
                    nameTerritory = nations.get(0);
                    listNearTerritory = new ArrayList<>(nations);
                    listNearTerritory.remove(nameTerritory);
                    final Territory territory = new TerritoryImpl(nameTerritory, continentName, listNearTerritory);
                    this.listTerritories.add(territory);

                    this.getContinentFromName(continentName).addTerritory(territory);
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
    public List<Territory> getListTerritories() {
        return listTerritories; 
    }

    /**
     * Method used to get the list of continents extracted from the text file.
     * @return the list of territories
     */
    public List<Continent> getListContinents() {
        return listContinents; 
    }

    /**
     * Method to verify if a continent is already present in the list of continents.
     * @param continent is the name of the continent
     * @return -true if the continent is in the list of continents
     *         -false if the continet has to be inserted into the list of continents
     */
    private boolean isInList(final String continent) {
        for (var elem : this.listContinents) {
            if (elem.getName().equals(continent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to extract from the list of continents the continent with
     * a specified name.
     * @param name is the name of a continent
     * @return a continent object
     */
    private Continent getContinentFromName(final String name) {
        for (var elem : this.listContinents) {
            if (elem.getName().equals(name)) {
                return elem;
            }
        }
        return new ContinentImpl("");
    }

    /**
     * Method to add a specified number of armies in a territory.
     * @param territoryName is the name of the territory in which we
     * want to add armies
     * @param numArmies is the number of armies that we want to add in the territory
     */
    public void addArmiesInTerritory(final String territoryName, final int numArmies) {
        for (var terr : this.listTerritories) {
            if (terr.getTerritoryName().equals(territoryName)) {
                terr.addArmies(numArmies);
            }
        }
    }

    /**
     * Method to remove a specified number of armies from a territory.
     * @param territoryName is the name of the territory in which we
     * want to remove armies
     * @param numArmies is the number of armies that we want to remove from the territory
     */
    public void removeArmiesInTerritory(final String territoryName, final int numArmies) {
        for (var terr : this.listTerritories) {
            if (terr.getTerritoryName().equals(territoryName)) {
                terr.removeArmies(numArmies);
            }
        }
    }

    /**
     * Method to move a certain amount of armies from a territory to a 
     * neightbor territory.
     * @param srcName is the name of the territory that initially contains the armies
     * the player wants to move
     * @param dstName is the name of the destination territory of the armies
     * @param numArmies is the number of armies that the player wants to move
     * @return a string that explain the error that occured if the name of a territory 
     * is invalid, if the number of armies the player wants to move
     * is greater or equal of the number of armies in the source territory (srcName) 
     * and if the two territories are not adjacent. If the operation succedes the method
     * return an empty string.
     */
    public String moveArmiesFromPlaceToPlace(final String srcName, final String dstName, final int numArmies) {
        String outputMessage = "";
        Optional<Territory> opt;
        Territory srcTerritory;
        Territory dstTerritory;

        /*Is necessary to have the two territories object by calling a private method 
        contained in the class and verify that the optional returned by the method is
        not empty.*/
        opt = getTerritoryFromName(srcName);
        if (opt.isPresent()) {
            srcTerritory = opt.get();
            opt = getTerritoryFromName(dstName);
            if (opt.isPresent()) {
                dstTerritory = opt.get();
                /*Before moving the armies between the two territories
                 * we have to make sure that the operation is possible.
                 */
                if (territoriesAreNear(srcTerritory, dstTerritory)) {
                    if (numArmies > 0 && numArmies < srcTerritory.getNumberOfArmies()) {
                        this.removeArmiesInTerritory(srcName, numArmies);
                        this.addArmiesInTerritory(dstName, numArmies);
                    } else {
                        outputMessage = "The number of armies selected for the operation is not valid.";
                    }
                } else {
                    outputMessage = "The two territories inserted are not near.";
                }
            } else {
                outputMessage = "Errore, il territorio destinazione cercato non è presente.";
            }
        } else {
            outputMessage = "Errore, il territorio sorgente cercato non è presente.";
        }
        return outputMessage;
    }

    /**
     * Method to verify if the two territories passed in input are adjacent.
     * @param terr1 is the first territory
     * @param terr2 is the second territory
     * @return true if they are adjacent, or false if they are not adjacent
     */
    public boolean territoriesAreNear(final Territory terr1, final Territory terr2) {
        for (var elem : terr1.getListOfNearTerritories()) {
            if (elem.equals(terr2.getTerritoryName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get a territory by his name.
     * @param name is the name of the territory
     * @return an optional that is empty if the territory is not present or
     * that contains the territory searched if present
     */
    private Optional<Territory> getTerritoryFromName(final String name) {
        for (var terr : this.listTerritories) {
            if (terr.getTerritoryName().equals(name)) {
                return Optional.of(terr);
            }
        }
        return Optional.empty();
    }
}
