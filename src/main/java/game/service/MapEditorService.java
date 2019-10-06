package service;

import model.Continent;
import model.Country;
import model.MapGraph;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapEditorService {

    private MapGraph mapGraph;

    public String editMap(String fileName) {
        String returnMsg = "";
        File mapFile = new File(fileName);
        //if the map file exists
        if (mapFile.isFile()) {
            HashMap<Integer, Continent> continentMap = new HashMap<>();
            HashMap<Integer, Country> countryHashMap = new HashMap<>();
            Map<Country, List<Country>> adjacentCountries = new HashMap<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(mapFile));
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (line.contains("continents")) {
                        String continentLine = "";
                        while (!(continentLine = br.readLine()).equals("")) {
                            int continentIndex = 1;
                            String[] continentInfos = continentLine.split(" ");
                            int armyValue = Integer.parseInt(continentInfos[1]);
                            Continent continent = new Continent(continentIndex, continentInfos[0], armyValue, continentInfos[2]);
                            continentMap.put(continentIndex, continent);
                        }
                    }

                    if (line.contains("countries")) {
                        String countryLine = "";
                        while (!(countryLine = br.readLine()).equals("")) {
                            String[] countryInfos = countryLine.split(" ");
                            int countryId = Integer.parseInt(countryInfos[0]);
                            int parentContinentId = Integer.parseInt(countryInfos[2]);
                            int positionX = Integer.parseInt(countryInfos[3]);
                            int positionY = Integer.parseInt(countryInfos[4]);
                            Country country = new Country(countryId, countryInfos[1], continentMap.get(parentContinentId), positionX, positionY);
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

                            List<Country> neighbourList = new LinkedList<>();
                            for (int i = 1; i < borderInfos.length; i++) {
                                Integer neighbourId = Integer.valueOf(borderInfos[i]);
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

            mapGraph = new MapGraph(adjacentCountries);
            returnMsg = "load map from file " + mapFile + " success";
        } else {
            File file = new File(fileName);
            try {
                //create an empty file
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    returnMsg = "create new file success";
                } else {
                    returnMsg = "create new file fail!";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return returnMsg;
    }


}
