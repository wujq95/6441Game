package service;

import controller.ColorController;
import javafx.scene.paint.Color;
import model.Continent;
import model.Country;
import model.MapGraph;

import java.io.*;
import java.util.*;

public class MapEditorService {
    public MapEditorService() {
        colorPicker = new ColorController();
    }

    private static ColorController colorPicker;
    public static MapGraph mapGraph;

    String editContinent(String[] continentName) {
        for (int i = 1; i < continentName.length; i++) {
            if (continentName[i].equals("-add")) {
                Color continentColor = colorPicker.pickOneColor();
                int continentValue = Integer.parseInt(continentName[i + 2]);

                Continent newContinent = new Continent(continentName[i + 1], continentValue, continentColor);
                mapGraph.addContinent(continentName[i + 1], continentValue, continentColor);
                List<Continent> continentList = mapGraph.getContinentList();
                continentList.add(newContinent);
                mapGraph.setContinentList(continentList);
            }
            if (continentName[i].equals("-remove")) {
                mapGraph.deleteContinent(continentName[i + 1]);

                List<Continent> continentList = mapGraph.getContinentList();
                for (Continent continent : continentList) {
                    if ((continent.getContinentName()).equals(continentName[i + 1])) {
                        continentList.remove(continent);
                    }
                }
                mapGraph.setContinentList(continentList);

            }
        }
        return "";
    }


    String editCountry(String[] countryName) {
        for (int i = 1; i < countryName.length; i++) {
            if (countryName[i].equals("-add")) {
                mapGraph.addCountry(countryName[i + 1], countryName[i + 2]);
            }
            if (countryName[i].equals("-remove")) {
                mapGraph.deleteCountry(countryName[i + 1]);
            }
        }
        return "";
    }

    String editNeighbor(String[] countryName) {
        for (int i = 1; i < countryName.length; i++) {
            if (countryName[i].equals("-add")) {
                mapGraph.addConnection(countryName[i + 1], countryName[i + 2]);
            }
            if (countryName[i].equals("-remove")) {
                mapGraph.deleteConnection(countryName[i + 1], countryName[i + 2]);
            }
        }
        return "";
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

                            //TODO:continent.setcountries
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
            mapGraph.setContinentList(new LinkedList<Continent>(continentMap.values()));
            mapGraph.setCountryList(countryList);
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

    public String showMap() {
        StringBuilder showMap = new StringBuilder();
        showMap.append("The continents are");
        for (Continent continent : mapGraph.getContinentList()) {
            showMap.append(" ").append(continent.getContinentName()).append(",");
        }

        showMap.append("\ncountries include country");
        for (Country country : mapGraph.getAdjacentCountries().keySet()) {
            showMap.append(" ").append(country.getCountryName()).append(",");

            showMap.append("\n and ").append(country.getCountryName()).append("'s neighbours are");
            for (Country neighour : country.getNeighbours()) {
                showMap.append(" ").append(neighour.getCountryName()).append(",");
            }
            showMap.append(" \ncountry");
        }

        return showMap.toString();
    }

    boolean validateMap() {
        //1. duplicate country names
        Set<String> countryNames = new HashSet<>();
        for (Country country : mapGraph.getCountryList()) {
            countryNames.add(country.getCountryName());
        }
        if (countryNames.size() < mapGraph.getCountryList().size()) {
            //"duplicate country names"
            return false;
        }
        //2. duplicate continent names
        Set<String> continentNames = new HashSet<>();
        for (Continent continent : mapGraph.getContinentList()) {
            continentNames.add(continent.getContinentName());
        }
        if (continentNames.size() < mapGraph.getContinentList().size()) {
            return false;
        }
        //3. check if the graph is connected
        if (!checkIfConnected(mapGraph.getAdjacentCountries())) {
            // "the map graph is not connected"
            return false;
        }

        return true;
    }

    private boolean checkIfConnected(LinkedHashMap<Country, List<Country>> adjacentCountries) {
        Integer start = 0;

        LinkedHashMap<Integer, List<Country>> adj = new LinkedHashMap<>();
        for (Map.Entry<Country, List<Country>> entry : adjacentCountries.entrySet()) {
            adj.put(entry.getKey().getId(), entry.getValue());
            start = entry.getKey().getId();
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
            if (visited[vertex]) {
                connected = true;
            } else {
                connected = false;
                break;
            }
        }

        return connected;
    }

    public String saveMap(String fileName) {
        fileName = fileName.trim();
        if (!validateMap()) {
            return "the map is not valid";
        }

        String returnMsg = "";
        File mapFile = new File(fileName);

        if (mapFile.isFile()) {
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
                    borderDesc.append(" ").append(neighbour.getId());
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
