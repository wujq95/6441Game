package service;

import javafx.scene.paint.Color;
import model.Connection;
import model.Continent;
import model.Country;

import java.io.*;
import java.util.*;

import static service.MapEditorService.*;

public class SaveLoadGameMapEditorPhase extends SaveLoadGame {
    @Override
    public String loadGame(String fileName) {
        if (fileName.endsWith("\n")) {
            fileName = fileName.trim();
        }
        String returnMsg = "";
        File mapFile = new File(fileName);

        //if the map file exists
        if (mapFile.isFile()) {
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

                            mapGraph.setContinentList(new LinkedList<>(continentMap.values()));

                            countryList.add(country);
                            mapGraph.setCountryList(countryList);
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

            mapGraph.setConnectionList(connectionList);
            mapGraph.setAdjacentCountries(adjacentCountries);

            if (!validateMap()) {
                return "the map is not valid";
            }


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

    @Override
    public String saveGame(String fileName) {
        fileName = fileName.trim();
        if (!validateMap()) {
            return "the map is not valid";
        }

        String returnMsg = "";

        List<String> lines = new LinkedList<>();

        String[] filepaths = fileName.split("/");
        String mapName = "name " + filepaths[filepaths.length - 1] + " Map";
        lines.add(mapName);

        String continents = "\n[continents]";
        lines.add(continents);

        List<Continent> updatedContinentList = new LinkedList<>();
        //continentindex for continent id
        int continentIndex = 1;
        for (Continent continent : MapEditorService.mapGraph.getContinentList()) {
            String continentDesc = continent.getContinentName() + " " + continent.getArmyValue() + " " + continent.getColor();
            lines.add(continentDesc);

            continent.setId(continentIndex);
            updatedContinentList.add(continent);
            continentIndex++;
        }

        lines.add("\n[countries]");
        int index = 0;
        for (Country country : MapEditorService.mapGraph.getCountryList()) {
            String countryDesc = "";

            //index for the countries id
            index++;
            if (country.getId() == null) {
                countryDesc = index + " " + country.getCountryName() + " "
                        + findContinentIdByName(country.getParentContinent().getContinentName(), updatedContinentList) + " " + country.getX() + " " + country.getY();
            } else {
                countryDesc = country.getId() + " " + country.getCountryName() + " "
                        + findContinentIdByName(country.getParentContinent().getContinentName(), updatedContinentList) + " " + country.getX() + " " + country.getY();
            }

            lines.add(countryDesc);
        }

        lines.add("\n[borders]");
        for (Map.Entry<Country, Set<Country>> entry : MapEditorService.mapGraph.getAdjacentCountries().entrySet()) {
            StringBuilder borderDesc = new StringBuilder();
            borderDesc.append(entry.getKey().getId());

            for (Country neighbour : entry.getValue()) {
                borderDesc.append(" ").append(neighbour.getId());
            }

            lines.add(borderDesc.toString());
        }

        File mapFile = new File(fileName);

        if (mapFile.isFile()) {
            mapFile.delete();
            File newFile = new File(fileName);

            try {
                FileWriter fileWriter = new FileWriter(newFile, false);
                for (String str : lines) {
                    fileWriter.write(str + System.lineSeparator());
                }
                fileWriter.close();

                returnMsg = "saveMap success";
            } catch (IOException e) {
                returnMsg = "saveMap failed";
                return returnMsg;
            }
        } else {
            File newFile = new File(fileName);

            try {
                FileWriter fileWriter = new FileWriter(newFile, false);
                for (String str : lines) {
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
