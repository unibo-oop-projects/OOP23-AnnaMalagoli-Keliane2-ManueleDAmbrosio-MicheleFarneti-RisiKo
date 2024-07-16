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
 * memorized in a file text that contains the name of a territory, its coordinates and
 * the list of the neighboring territories after the name of the continent. 
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
            List<String> continentInfo;
            int bonusArmies;
            String nameTerritory;
            List<String> listNearTerritory;

            stringRow = bufferedReader.readLine();

            do {
                if (stringRow.contains(":")) { 
                    continentInfo = Arrays.asList(stringRow.substring(0, stringRow.length() - 1).split(" "));
                    continentName = continentInfo.get(0);
                    if (!this.isInList(continentName)) {
                        bonusArmies = Integer.valueOf(continentInfo.get(1));
                        this.listContinents.add(new ContinentImpl(continentName, bonusArmies));
                    }
                } else {
                    final List<String> nations = Arrays.asList(stringRow.split(" "));
                    nameTerritory = nations.get(0);
                    listNearTerritory = new ArrayList<>(nations);
                    listNearTerritory.remove(nameTerritory);
                    final Territory territory = new TerritoryImpl(nameTerritory, continentName, listNearTerritory);
                    this.listTerritories.add(territory);

                    this.getContinentFromName(continentName).get().addTerritory(territory);
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
        return List.copyOf(listContinents); 
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
     * @return an optional that containes the continent searched or an empty
     * optional. The optional should always contain the continent.
     */
    private Optional<Continent> getContinentFromName(final String name) {
        for (var elem : this.listContinents) {
            if (elem.getName().equals(name)) {
                return Optional.of(elem);
            }
        }
        return Optional.empty();
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
}
