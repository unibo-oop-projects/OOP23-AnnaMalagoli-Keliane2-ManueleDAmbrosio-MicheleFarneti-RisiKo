package it.unibo.risiko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Territories {

    private List<Territory> listTerritories = new ArrayList<>();

    public Territories(String filePath){
        File file = new File(filePath);
        String absoluteFilePath = file.getAbsolutePath();
        try(
            final InputStream inputStream = new FileInputStream(absoluteFilePath);
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ){
            String stringRow;
            String continentName = " ";
            String nameTerritory;
            List<String> listNearTerritory;

            while((stringRow = bufferedReader.readLine()) != null){
                if(stringRow.contains(":")){
                    continentName = stringRow.substring(0, stringRow.length() - 1);                
                } else{
                    String[] nations = stringRow.split(" ");
                    nameTerritory = nations[0];
                    listNearTerritory = Arrays.asList(nations);
                    listNearTerritory.remove(0);
                    Territory territory = new TerritoryImpl(nameTerritory, continentName, listNearTerritory);
                    this.listTerritories.add(territory);

                }
            }



        } catch(Exception e){
            //gestione eccezzione
        }
    }

    public List<Territory> getList(){
        List<Territory> copyListTerritories = new ArrayList<>(this.listTerritories);
        return copyListTerritories; 
    }
}
