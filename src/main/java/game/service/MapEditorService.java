package service;

import controller.ColorController;
import javafx.scene.paint.Color;
import model.Connection;
import model.Continent;
import model.Country;
import model.MapGraph;

import java.io.*;
import java.util.*;

public class MapEditorService {
    /**
     * initial color Controller
     */
    public MapEditorService() {
        colorPicker = new ColorController();
    }

    private static ColorController colorPicker;
    public static MapGraph mapGraph;

    /**
     * Edit Continent
     *
     * @param continentNameList
     * @return Message
     */
    String editContinent(String[] continentNameList) {

        // edit continent -add name value -add name2 value2 -add name3 value3
        for (int i = 1; i < continentNameList.length; i = i + 3) {
            if (continentNameList[i].equals("-add")) {

                String continentName = continentNameList[i + 1];
                Integer continentValue = Integer.parseInt(continentNameList[i + 2]);
                Color continentColor = colorPicker.pickOneColor();

                mapGraph.addContinent(continentName, continentValue, continentColor);
            } else if (continentNameList[i].equals("-remove")) {
                String continentName = continentNameList[i + 1];
                mapGraph.deleteContinent(continentName);
            } else {
                return "wrong syntax";
            }
        }
        return "map edit success";
    }

    /**
     * Edit Country
     *
     * @param countryName
     * @return Message
     */
    String editCountry(String[] countryName) {
        String Msg = "";
        for (int i = 1; i < countryName.length; i = i + 3) {
            if (countryName[i].equals("-add")) {
                boolean flag = mapGraph.addCountry(countryName[i + 1], countryName[i + 2]);
                if (flag) {
                    Msg = "add success";
                } else {
                    Msg = "continent name not available";
                }
            } else if (countryName[i].equals("-remove")) {
                mapGraph.deleteCountry(countryName[i + 1]);
                Msg = "remove country success";
            } else {
                return "wrong syntax";
            }
        }
        return Msg;
    }

    /**
     * Edit Neighbor
     *
     * @param countryName
     * @return message
     */
    String editNeighbor(String[] countryName) {
        String Msg = "";
        for (int i = 1; i < countryName.length; i = i + 3) {
            if (countryName[i].equals("-add")) {
                mapGraph.addConnection(countryName[i + 1], countryName[i + 2]);

                Country country1 = findCountryByName(countryName[i + 1]);
                Country country2 = findCountryByName(countryName[i + 2]);

                if (country1 == null || country2 == null) {
                    return "one of the countries doesn't exist";
                }
                List<Country> country1List = MapEditorService.mapGraph.getAdjacentCountries().get(country1);
                country1.addNeighbor(country2);
                country2.addNeighbor(country1);
                country1List.add(country2);
                MapEditorService.mapGraph.getAdjacentCountries().put(country1, country1List);
                List<Country> country2List = MapEditorService.mapGraph.getAdjacentCountries().get(country2);
                country2List.add(country1);
                MapEditorService.mapGraph.getAdjacentCountries().put(country2, country2List);

                Msg = "edit success";
            }
            if (countryName[i].equals("-remove")) {
                boolean flag = mapGraph.deleteConnection(countryName[i + 1], countryName[i + 2]);
                if (flag)
                    Msg = "edit success";
                else
                    Msg = "Connection is not available";
            }
        }
        return Msg;
    }

    /**
     * @param fileName
     * @return message
     */
    public String editMap(String fileName) {
        if (fileName.endsWith("\n")) {
            fileName = fileName.trim();
        }
        String returnMsg = "";
        File mapFile = new File(fileName);
        //if the map file exists
        if (mapFile.isFile()) {
            //TODO:SET CONTINENT NAMES/COUNTRY NAMES
            HashMap<Integer, Continent> continentMap = new HashMap<>();
            HashMap<Integer, Country> countryHashMap = new HashMap<>();

            List<Country> countryList = new LinkedList<>();
            LinkedHashMap<Country, List<Country>> adjacentCountries = new LinkedHashMap<>();
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

                            //List<Connection> connectionList=new LinkedList<>();
                            List<Country> neighbourList = new LinkedList<>();
                            for (int i = 1; i < borderInfos.length; i++) {
                                Integer neighbourId = Integer.valueOf(borderInfos[i]);

                                Connection connection = new Connection(country.getCountryName(), countryHashMap.get(neighbourId).getCountryName());
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
            mapGraph = new MapGraph();
            mapGraph.setContinentList(new LinkedList<>(continentMap.values()));
            mapGraph.setCountryList(countryList);
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

    /**
     * show Map
     *
     * @return Message
     */
    public String showMap() {
        if (mapGraph.getCountryList().get(0).getPlayer() != null) {
            StringBuilder showMap = new StringBuilder();
            showMap.append("The continents are");
            for (Continent continent : mapGraph.getContinentList()) {
                showMap.append(" ").append(continent.getContinentName()).append(",");
            }

            showMap.append("\ncountries include");
            for (Country country : mapGraph.getCountryList()) {
                showMap.append("\ncountry");
                showMap.append(" ").append(country.getCountryName()).append(",")
                        .append(" the owner player of the country is ").append(country.getPlayer().getPlayerName()).append(",")
                        .append(" the army of this country is ").append(country.getArmyValue()).append(",");

                showMap.append("\n and ").append(country.getCountryName()).append("'s neighbours are");
                for (Country neighour : country.getNeighbours()) {
                    showMap.append(" ").append(neighour.getCountryName()).append(",");
                }
            }

            return showMap.toString();
        } else {
            StringBuilder showMap = new StringBuilder();
            showMap.append("The continents are");
            for (Continent continent : mapGraph.getContinentList()) {
                showMap.append(" ").append(continent.getContinentName()).append(",");
            }

            showMap.append("\ncountries include");
            for (Country country : mapGraph.getCountryList()) {
                showMap.append("\ncountry");
                showMap.append(" ").append(country.getCountryName()).append(",");

                showMap.append("\n and ").append(country.getCountryName()).append("'s neighbours are");
                for (Country neighour : country.getNeighbours()) {
                    showMap.append(" ").append(neighour.getCountryName()).append(",");
                }
            }

            return showMap.toString();
        }
    }

    /**
     * Map Validation
     *
     * @return true or false
     */
    public boolean validateMap() {
        //1. duplicate country names
        Set<String> countryNames = new HashSet<>();
        for (Country country : mapGraph.getCountryList()) {
            //7. check if the country has parent continent
            if (country.getParentContinent() == null) {
                return false;
            }
            countryNames.add(country.getCountryName());
        }
        if (countryNames.size() < mapGraph.getCountryList().size()) {
            return false;
        }
        Set<String> continentNames = new HashSet<>();
        for (Continent continent : mapGraph.getContinentList()) {
            continentNames.add(continent.getContinentName());

            //2. check if one continent has at least one country
            if (continent.getCountries() == null || continent.getCountries().size() == 0) {
                return false;
            }
        }
        //3. duplicate continent names
        if (continentNames.size() < mapGraph.getContinentList().size()) {
            return false;
        }
        //4.no countries or continents
        if (mapGraph.getAdjacentCountries() == null || (mapGraph.getAdjacentCountries().size()) == 0) {
            return false;
        }
        if (mapGraph.getCountryList() == null || mapGraph.getCountryList().size() == 0) {
            return false;
        }
        if (mapGraph.getContinentList() == null || mapGraph.getContinentList().size() == 0) {
            return false;
        }
        //5. check if the graph is connected
        if (!checkIfConnected(mapGraph.getAdjacentCountries())) {
            return false;
        }
        //6. check if the continents are connected subgraph

        return true;
    }

    /**
     * Check adjacent country with each other
     *
     * @param adjacentCountries
     * @return true or false
     */
    public boolean checkIfConnected(LinkedHashMap<Country, List<Country>> adjacentCountries) {
        Integer start = 1;

        LinkedHashMap<Integer, List<Country>> adj = new LinkedHashMap<>();

        for (Map.Entry<Country, List<Country>> entry : adjacentCountries.entrySet()) {
            adj.put(entry.getKey().getId(), entry.getValue());
        }
        boolean[] visited = new boolean[adj.size() + 1];
        LinkedList<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while (queue.size() != 0) {
            start = queue.poll();
            Iterator<Country> i = adj.get(start).iterator();
            while (i.hasNext()) {
                Country n = i.next();
                Integer nId = n.getId();
                if (!visited[nId]) {
                    visited[nId] = true;
                    queue.add(nId);
                }
            }
        }

        boolean connected = false;
        for (Integer vertex : adj.keySet()) {
            if (vertex == null) {
                connected = false;
                break;
            } else if (visited[vertex]) {
                connected = true;
            } else {
                connected = false;
                break;
            }
        }

        return connected;
    }

    /**
     * Save Map
     *
     * @param fileName
     * @return Message
     */
    public String saveMap(String fileName) {
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

        for (Continent continent : mapGraph.getContinentList()) {
            String continentDesc = continent.getContinentName() + " " + continent.getArmyValue() + " " + continent.getColor();
            lines.add(continentDesc);
        }

        lines.add("\n[countries]");
        int index = 0;
        for (Country country : mapGraph.getCountryList()) {
            String countryDesc = "";
            index++;
            if (country.getId() == null) {
                countryDesc = index + " " + country.getCountryName() + " "
                        + country.getParentContinent().getId() + " " + country.getX() + " " + country.getY();
            } else {
                countryDesc = country.getId() + " " + country.getCountryName() + " "
                        + country.getParentContinent().getId() + " " + country.getX() + " " + country.getY();
            }

            lines.add(countryDesc);
        }

        lines.add("\n[borders]");
        for (Map.Entry<Country, List<Country>> entry : mapGraph.getAdjacentCountries().entrySet()) {
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

    /**
     * Find required Country by searching name
     * @param countryName
     * @return null
     */
    private Country findCountryByName(String countryName) {
        for (Country country : mapGraph.getCountryList()) {
            if (countryName.equals(country.getCountryName())) {
                return country;
            }
        }
        return null;
    }
}
