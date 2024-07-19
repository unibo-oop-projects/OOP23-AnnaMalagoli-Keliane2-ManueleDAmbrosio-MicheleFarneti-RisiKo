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
import java.util.Random;
import java.util.Collections;
import java.nio.charset.StandardCharsets;

/**
 * The TerritoriesImpl class is used by the controller at the start of the game to generate 
 * the list of territories that are in the map. The informations about the territories are 
 * memorized in a file text that contains the name of a territory, its coordinates and
 * the list of the neighboring territories after the name of the continent. 
 * 
 * @author Anna Malagoli 
 */
public class TerritoriesImpl implements Territories {

    private final List<Territory> listTerritories = new ArrayList<>();
    private final List<Continent> listContinents = new ArrayList<>();
    /**
     * Contructor to set the list of territories by extracting informations 
     * from a file text. If an exeption is thrown the list of territories is empty.
     * 
     * @param filePath is the relative path of the text file
     */
    public TerritoriesImpl(final String filePath) {
        final File file = new File(filePath);
        final String absoluteFilePath = file.getAbsolutePath();
        try {
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            String stringRow;
            String continentName = " ";
            List<String> continentInfo;
            int bonusArmies;
            String nameTerritory;
            List<String> listNearTerritory;

            stringRow = bufferedReader.readLine();

            while (stringRow != null) {
                if (stringRow.contains(":")) { 
                    continentInfo = Arrays.asList(stringRow.substring(0, stringRow.length() - 1).split(" "));
                    continentName = continentInfo.get(0);
                    if (!this.isInList(continentName)) {
                        bonusArmies = Integer.parseInt(continentInfo.get(1));
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
            }
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
     * @return a copy of the list of territories
     */
    @Override
    public List<Territory> getListTerritories() {
        return List.copyOf(listTerritories); 
    }

    /**
     * Method used to get the list of continents extracted from the text file.
     * @return a copy of the list of territories
     */
    @Override
    public List<Continent> getListContinents() {
        return List.copyOf(listContinents); 
    }

    /**
     * Method to shuffle the list of territories.
     */
    @Override
    public void shuffle() {
        Collections.shuffle(listTerritories, new Random());
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
     * Method to extract from the list of territories the territory with
     * a specified name.
     * @param name is the name of a territory
     * @return an optional that containes the territory searched or an empty
     * optional. The optional should always contain the territory.
     */
    private Optional<Territory> getTerritoryFromName(final String name) {
        for (var elem : this.listTerritories) {
            if (elem.getTerritoryName().equals(name)) {
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
    @Override
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
    @Override
    public void removeArmiesInTerritory(final String territoryName, final int numArmies) {
        for (var terr : this.listTerritories) {
            if (terr.getTerritoryName().equals(territoryName)) {
                terr.removeArmies(numArmies);
            }
        }
    }
    /**
     * Method to verify if the two territories passed in input are adjacent.
     * @param name1 is the name of the first territory
     * @param name2 is the name of the second territory
     * @return true if they are adjacent, or false if they are not adjacent
    */
    @Override
    public boolean territoriesAreNear(final String name1, final String name2) {
        Territory terr1 = getTerritoryFromName(name1).get();
        for (var elem : terr1.getListOfNearTerritories()) {
            if (elem.equals(name2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to set the owner of a territory.
     * @param terrName is the name of the territory
     * @param playerId is the id of the player
     */
    @Override
    public void setOwner(final String terrName, final String playerId) {
        Territory terr = getTerritoryFromName(terrName).get();
        terr.setPlayer(playerId);
    }
}
