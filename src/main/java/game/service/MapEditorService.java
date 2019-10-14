package service;

import model.Continent;
import model.Country;
import model.MapGraph;

import java.io.*;
import java.util.*;

public class MapEditorService {

    public MapGraph mapGraph;

    /**
     * @param fileName
     * @return message
     */
    public String editMap(String fileName) {
        String returnMsg = "";
        File mapFile = new File(fileName);
        //if the map file exists
        if (mapFile.isFile()) {
            //TODO:SET CONTINENT NAMES/COUNTRY NAMES
            HashMap<Integer, Continent> continentMap = new HashMap<>();
            HashMap<Integer, Country> countryHashMap = new HashMap<>();

            List<Continent> continentList = new LinkedList<>();
            List<Country> countryList = new LinkedList<>();
            LinkedHashMap<Country, List<Country>> adjacentCountries = new LinkedHashMap<>();

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
                            Continent continent = new Continent(continentIndex, continentInfos[0], armyValue, continentInfos[2]);
                            continentMap.put(continentIndex, continent);
                            continentList.add(continent);
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

                            countryList.add(country);
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


            mapGraph = new MapGraph();
            mapGraph.setAdjacentCountries(adjacentCountries);
            mapGraph.setContinentList(continentList);
            mapGraph.setCountryList(countryList);
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
                returnMsg = e.getMessage();
                return returnMsg;
            }
        }

        return returnMsg;
    }

    public String showMap() {
        StringBuilder showMap = new StringBuilder();
        showMap.append("The continents are");
        for (Continent continent : mapGraph.getContinentList()) {
            showMap.append(" ").append(continent.getContinentName()).append(",");
        }

        showMap.append("\ncountries include country");
        for (Country country : mapGraph.getAdjacentCountries().keySet()) {
            showMap.append(" ").append(country.getCountryName()).append(",");

            showMap.append("\n and " + country.getCountryName() + "'s neighbours are");
            for (Country neighour : country.getNeighbours()) {
                showMap.append(" ").append(neighour.getCountryName()).append(",");
            }
            showMap.append(" \ncountry");
        }

        return showMap.toString();
    }

    public String validateMap() {
        //1. a country is not connected to other countries
        if (mapGraph.getCountryList().size() != mapGraph.getAdjacentCountries().size()) {
            return "";
        }

        return "";
    }

    public String saveMap(String fileName) {
        String returnMsg = "";
        File mapFile = new File(fileName);

        if (mapFile.isFile()) {
            HashMap<Integer, Continent> continentMap = new HashMap<>();
            HashMap<Integer, Country> countryHashMap = new HashMap<>();
            List<Continent> continentList = new LinkedList<>();
            Map<Country, List<Country>> adjacentCountries = new HashMap<>();

            List<String> linesBeforeContinents = new LinkedList<>();
            List<String> linesAfterContinents = new LinkedList<>();
            List<String> allLines = new LinkedList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(mapFile));
                String line = "";
                while ((line = br.readLine()) != null && !line.contains("continents")) {
                    linesBeforeContinents.add(line);
                }
            } catch (IOException e) {
                returnMsg = e.getMessage();
                return returnMsg;
            }

            String continents = "[continents]";
            linesAfterContinents.add(continents);

            for (Continent continent : mapGraph.getContinentList()) {
                String continentDesc = continent.getContinentName() + " " + continent.getArmyValue() + " " + continent.getColor();
                linesAfterContinents.add(continentDesc);
            }

            linesAfterContinents.add("\n[countries]");
            for (Country country : mapGraph.getAdjacentCountries().keySet()) {
                String countryDesc = country.getId() + " " + country.getCountryName() + " "
                        + country.getParentContinent().getId() + " " + country.getX() + " " + country.getY();
                linesAfterContinents.add(countryDesc);
            }

            linesAfterContinents.add("\n[borders]");
            for (Map.Entry<Country, List<Country>> entry : mapGraph.getAdjacentCountries().entrySet()) {
                StringBuilder borderDesc = new StringBuilder();
                borderDesc.append(entry.getKey().getId());

                for (Country neighbour : entry.getValue()) {
                    borderDesc.append(" " + neighbour.getId());
                }

                linesAfterContinents.add(borderDesc.toString());
            }

            allLines.addAll(linesBeforeContinents);
            allLines.addAll(linesAfterContinents);

            mapFile.delete();
            File newFile = new File(fileName);

            try {
                FileWriter fileWriter = new FileWriter(newFile, false);
                for (String str : allLines) {
                    fileWriter.write(str + System.lineSeparator());
                }
                fileWriter.close();

                returnMsg = "saveMap success";
            } catch (IOException e) {
                returnMsg = "saveMap failed";
                return returnMsg;
            }
        }

        return returnMsg;
    }

}
