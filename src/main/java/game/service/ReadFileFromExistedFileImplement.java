package service;

import javafx.scene.paint.Color;
import model.Connection;
import model.Continent;
import model.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static service.MapEditorService.findCountryByName;
import static service.MapEditorService.validateMap;

public class ReadFileFromExistedFileImplement implements ReadFileFromExistedFile {

    @Override
    public String readFileFromExistedFile(String mapFile) {
        String returnMsg = "";
    //SET CONTINENT NAMES/COUNTRY NAMES
        HashMap<Integer, Continent> continentMap = new HashMap<>();
        HashMap<Integer, Country> countryHashMap = new HashMap<>();

        List<Country> countryList = new LinkedList<>();
        LinkedHashMap<Country, Set<Country>> adjacentCountries = new LinkedHashMap<>();
        List<Connection> connectionList = new LinkedList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("continents")) {
                    String continentLine = "";
                    int continentIndex = 1;
                    while (!(continentLine = br.readLine()).equals("")) {
                        String[] continentInfos = continentLine.split(" ");
                        int armyValue = Integer.parseInt(continentInfos[1]);
                        Continent continent = new Continent(continentIndex, continentInfos[0], armyValue, Color.web(continentInfos[2]), new LinkedList<Country>());
                        continentMap.put(continentIndex, continent);
                        continentIndex++;
                    }
                }

                if (line.contains("countries")) {
                    String countryLine = "";
                    while (!(countryLine = br.readLine()).equals("")) {
                        String[] countryInfos = countryLine.split(" ");
                        int countryId = Integer.parseInt(countryInfos[0]);
                        int parentContinentId = Integer.parseInt(countryInfos[2]);
                        double positionX = Double.parseDouble(countryInfos[3]);
                        double positionY = Double.parseDouble(countryInfos[4]);
                        Country country = new Country(countryId, countryInfos[1], continentMap.get(parentContinentId), positionX, positionY, 0);

                        Continent parent = continentMap.get(parentContinentId);
                        List<Country> continentCountries = parent.getCountries();
                        continentCountries.add(country);
                        parent.setCountries(continentCountries);
                        continentMap.put(parentContinentId, parent);

                        MapEditorService.mapGraph.setContinentList(new LinkedList<>(continentMap.values()));

                        countryList.add(country);
                        MapEditorService.mapGraph.setCountryList(countryList);
                        countryHashMap.put(countryId, country);
                    }
                }

                if (line.contains("borders")) {
                    String borderLine = "";
                    borderLine = br.readLine();
                    while (borderLine != null && !("".equals(borderLine))) {
                        String[] borderInfos = borderLine.split(" ");
                        int countryId = Integer.parseInt(borderInfos[0]);
                        Country country = countryHashMap.get(countryId);

                        //List<Connection> connectionList=new LinkedList<>();
                        Set<Country> neighbourList = new HashSet<>();
                        for (int i = 1; i < borderInfos.length; i++) {
                            Integer neighbourId = Integer.valueOf(borderInfos[i]);

                            Connection connection = new Connection(findCountryByName(country.getCountryName()), findCountryByName(countryHashMap.get(neighbourId).getCountryName()));
                            connection.setCountry1(country);
                            connection.setCountry2(countryHashMap.get(neighbourId));
                            connectionList.add(connection);

                            neighbourList.add(countryHashMap.get(neighbourId));
                        }
                        country.setNeighbours(neighbourList);
                        adjacentCountries.put(country, neighbourList);

                        borderLine = br.readLine();
                    }
                }
            }
        } catch (IOException e) {
            returnMsg = e.getMessage();
            return returnMsg;
        }

        MapEditorService.mapGraph.setConnectionList(connectionList);
        MapEditorService.mapGraph.setAdjacentCountries(adjacentCountries);

        if (!validateMap()) {
            return "the map is not valid";
        }
        returnMsg = "load map from file " + mapFile + " success";

        return returnMsg;
    }
}
