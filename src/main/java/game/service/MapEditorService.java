package service;

import controller.ColorController;
import javafx.scene.paint.Color;
import model.Continent;
import model.Country;
import model.MapGraph;

import java.io.File;
import java.util.*;

/**
 * provide service for map editor
 */
public class MapEditorService {
    /**
     * initial color Controller
     */
    public MapEditorService() {
        colorPicker = new ColorController();
    }

    private static ColorController colorPicker;
    public static MapGraph mapGraph = new MapGraph();

    /**
     * Edit Continent
     *
     * @param continentNameList Continent name list
     * @return message
     */
    String editContinent(String[] continentNameList) {
        // editcontinent -add name value -add name2 value2 -add name3 value3
        for (int i = 1; i < continentNameList.length; i++) {
            if (continentNameList[i].equals("-add")) {
                String continentName = continentNameList[i + 1];
                Integer continentValue = Integer.parseInt(continentNameList[i + 2]);
                Color continentColor = colorPicker.pickOneColor();

                if (continentValue <= 0) {
                    return "continentvalue is not valid";
                }

                mapGraph.addContinent(continentName, continentValue, continentColor);
            } else if (continentNameList[i].equals("-remove")) {
                String continentName = continentNameList[i + 1];
                mapGraph.deleteContinent(continentName);
            }
        }
        return "map edit success";
    }

    /**
     * Edit Country
     *
     * @param countryName country name
     * @return message
     */
    String editCountry(String[] countryName) {
        String Msg = "";
        for (int i = 1; i < countryName.length; i++) {
            if (countryName[i].equals("-add")) {
                if (countryName.length <= i + 2 || countryName[i + 2] == null) {
                    return "invalid command";
                }
                boolean flag = mapGraph.addCountry(countryName[i + 1], countryName[i + 2]);
                if (flag) {
                    Msg = "add success";
                } else {
                    Msg = "continent name not available";
                }
            } else if (countryName[i].equals("-remove")) {
                mapGraph.deleteCountry(countryName[i + 1]);
                Msg = "remove country success";
            }
        }
        return Msg;
    }

    /**
     * Edit Neighbor
     *
     * @param countryName country name
     * @return message
     */
    String editNeighbor(String[] countryName) {
        String Msg = "";
        for (int i = 1; i < countryName.length; i++) {
            if (countryName[i].equals("-add")) {
                mapGraph.addConnection(countryName[i + 1], countryName[i + 2]);

                Country country1 = findCountryByName(countryName[i + 1]);
                Country country2 = findCountryByName(countryName[i + 2]);

                if (country1 == null || country2 == null) {
                    return "one of the countries doesn't exist";
                }
                country1.addNeighbor(country2);
                country2.addNeighbor(country1);

                Set<Country> country1List = MapEditorService.mapGraph.getAdjacentCountries().get(country1);
                country1List.add(country2);
                MapEditorService.mapGraph.getAdjacentCountries().put(country1, country1List);
                Set<Country> country2List = MapEditorService.mapGraph.getAdjacentCountries().get(country2);
                country2List.add(country1);
                MapEditorService.mapGraph.getAdjacentCountries().put(country2, country2List);

                Msg = "edit success";
            }
            if (countryName[i].equals("-remove")) {
                mapGraph.deleteConnection(countryName[i + 1], countryName[i + 2]);
                Country country1 = findCountryByName(countryName[i + 1]);
                Country country2 = findCountryByName(countryName[i + 2]);

                if (country1 == null || country2 == null) {
                    return "one of the countries doesn't exist";
                }
                country1.removeNeighbor(country2);
                country2.removeNeighbor(country1);
                Set<Country> country1List = mapGraph.getAdjacentCountries().get(country1);
                country1List.remove(country2);
                MapEditorService.mapGraph.getAdjacentCountries().put(country1, country1List);

                Set<Country> country2List = mapGraph.getAdjacentCountries().get(country2);
                country2List.remove(country1);
                MapEditorService.mapGraph.getAdjacentCountries().put(country2, country2List);

                Msg = "remove neighbor success";
            }
        }
        return Msg;
    }

    /**
     * edit map
     *
     * @param fileName filename for stored map
     * @return message
     */
    public String editMap(String fileName) {
        if (fileName.endsWith("\n")) {
            fileName = fileName.trim();
        }
        String returnMsg = "";
        File mapFile = new File(fileName);
        ReadFileFromEmptyImplement readFileFromEmptyImplement = new ReadFileFromEmptyImplement();
        ReadFileFromExistedFileImplement readFileFromExistedFileImplement = new ReadFileFromExistedFileImplement();

        //if the map file exists
        if (mapFile.isFile()) {
            ReadFileFromEmpty readFileFromEmpty = new ReadFileAdapter(readFileFromExistedFileImplement);
            readFileFromEmpty.readFileFromEmpty(fileName);
        } else {
            ReadFileFromExistedFile readFileFromExistedFile = new ReadFileAdapter(readFileFromEmptyImplement);
            readFileFromExistedFile.readFileFromExistedFile(fileName);
        }

        return returnMsg;
    }

    /**
     * show Map
     *
     * @return message
     */
    public String showMap() {
        if (mapGraph.getCountryList().size() == 0 && mapGraph.getCountryList().get(0).getPlayer() != null) {
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
            for (Map.Entry<Country, Set<Country>> entry : mapGraph.getAdjacentCountries().entrySet()) {
                showMap.append("\ncountry");
                showMap.append(" ").append(entry.getKey().getCountryName()).append(",");

                showMap.append("\n and ").append(entry.getKey().getCountryName()).append("'s neighbours are");
                for (Country neighour : entry.getValue()) {
                    showMap.append(" ").append(neighour.getCountryName()).append(",");
                }
            }

            return showMap.toString();
        }
    }

    /**
     * Map Validation
     *
     * @return True or False
     */
    public static boolean validateMap() {
        //1. duplicate country names
        Set<String> countryNames = new HashSet<>();
        for (Country country : mapGraph.getCountryList()) {
            // check if the country has parent continent
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

        return true;
    }

    /**
     * Check adjacent country with each other
     *
     * @param adjacentCountries neighbor of countries
     * @return true of false
     */
    public static boolean checkIfConnected(LinkedHashMap<Country, Set<Country>> adjacentCountries) {
        if (adjacentCountries.keySet().size() == 1) {
            return false;
        }
        Integer start = 0;
        for (Country country : adjacentCountries.keySet()) {
            start = country.getId();
        }

        LinkedHashMap<Integer, Set<Country>> adj = new LinkedHashMap<>();
        for (Map.Entry<Country, Set<Country>> entry : adjacentCountries.entrySet()) {
            adj.put(entry.getKey().getId(), entry.getValue());
        }
        boolean[] visited = new boolean[start + 1];
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
     * @param fileName Saved map filename
     * @return message
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

        List<Continent> updatedContinentList = new LinkedList<>();
        //continentindex for continent id
        int continentIndex = 1;
        for (Continent continent : mapGraph.getContinentList()) {
            String continentDesc = continent.getContinentName() + " " + continent.getArmyValue() + " " + continent.getColor();
            lines.add(continentDesc);

            continent.setId(continentIndex);
            updatedContinentList.add(continent);
            continentIndex++;
        }

        lines.add("\n[countries]");
        int index = 0;
        for (Country country : mapGraph.getCountryList()) {
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
        for (Map.Entry<Country, Set<Country>> entry : mapGraph.getAdjacentCountries().entrySet()) {
            StringBuilder borderDesc = new StringBuilder();
            borderDesc.append(entry.getKey().getId());

            for (Country neighbour : entry.getValue()) {
                borderDesc.append(" ").append(neighbour.getId());
            }

            lines.add(borderDesc.toString());
        }

        File mapFile = new File(fileName);

        if (mapFile.isFile()) {
            WriteFileToNewFileImplement writeFileToNewFileImplement = new WriteFileToNewFileImplement();
            WriteFileToExistedFile writeFileAdapter = new WriteFileAdapter(writeFileToNewFileImplement);
            writeFileAdapter.writeFileToExistedFile(fileName, lines);
        } else {
            WriteFileToExistedFileImplement writeFileToExistedFile = new WriteFileToExistedFileImplement();
            WriteFileToNewFile writeFileAdapter = new WriteFileAdapter(writeFileToExistedFile);
            writeFileAdapter.writeFileToNewFile(fileName, lines);
        }

        return returnMsg;
    }

    /**
     * Find required Country by searching name
     *
     * @param countryName country name
     * @return country object
     */
    public static Country findCountryByName(String countryName) {
        for (Country country : mapGraph.getCountryList()) {
            if (countryName.equals(country.getCountryName())) {
                return country;
            }
        }
        return null;
    }

    /**
     * Find continent by name
     * @param name continent name
     * @param continentList continent list
     * @return continent id
     */
    public static int findContinentIdByName(String name, List<Continent> continentList) {
        for (Continent continent : continentList) {
            if (name.equals(continent.getContinentName())) {
                return continent.getId();
            }
        }

        return 0;
    }
}
