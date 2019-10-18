package model;

import controller.Observer;
import javafx.scene.paint.Color;
import service.MapEditorService;

import java.util.*;

public class MapGraph {
    String name;

    Integer height, width;

    List<Continent> continentList;

    LinkedHashMap<Country, Set<Country>> adjacentCountries;

    List<Country> countryList;

    List<Connection> connectionList;

    // observers list
    private List<controller.Observer> mapObservers = new ArrayList<>();

    /**
     * attach the observer
     *
     * @param observer the observer to be attached
     */
    public void attach(controller.Observer observer) {
        mapObservers.add(observer);
    }

    /**
     * notify the map observer
     *
     * @param action via different actions, the observer will call different update
     * @param object pass different types of object the corresponding update() method needed
     */
    public void notifyObservers(String action, Object object) {
        for (Observer observer : mapObservers) {
            switch (action) {
                case "add continent":
                    observer.updateMapGraph();
                    break;
                case "delete continent":
                    observer.updateMapGraph();
                    //observer.updateContinentList("add", (Continent) object);
                    break;
                case "add country":
                    //FORCE update
                    observer.updateCountry("add", (Country) object);
                    break;
                case "delete country":
                    //observer.updateCountry("delete", (Country) object);
                    observer.updateMapGraph();
                    break;
                case "add connection":
                    //observer.updateConnection("add", (Connection) object);
                    observer.updateMapGraph();
                    break;
                case "delete connection":
                    //observer.updateConnection("delete", (Connection) object);
                    //observer.updateCountry("delete", (Country) object);
                    observer.updateMapGraph();
                    break;
            }
        }
    }

    /**
     * Initial continent List
     */
    public MapGraph() {
        continentList = new LinkedList<>();
    }

    /**
     * Set Continent Name
     *
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Continent Height
     *
     * @param height int
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Set Width
     *
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Add new Continent
     *
     * @param continentName
     * @param armyValue
     * @param color
     */
    public void addContinent(String continentName, Integer armyValue, Color color) {
        Continent continent = new Continent(continentName, armyValue, color);
        continentList.add(continent);
        notifyObservers("add continent", this);
    }

    /**
     * validate the continent and add it to map
     *
     * @param continentName
     * @param continentValue
     * @param color
     */
    public void addContinent(String continentName, String continentValue, Color color) {

        Continent continent = new Continent(continentName, 99, color);
        MapEditorService.mapGraph.continentList.add(continent);
        notifyObservers("add continent", continent);
    }

    /**
     * @param continentName string
     */
    public void deleteContinent(String continentName) {
        for (int i = 0; i < MapEditorService.mapGraph.continentList.size(); i++) {
            if (continentName.equals(continentList.get(i).getContinentName())) {
                List<Country> continentCountries = continentList.get(i).getCountries();
                MapEditorService.mapGraph.continentList.remove(i);

                for (int m = 0; m < continentCountries.size(); m++) {
                    deleteCountryfromAdjacentCountries(continentCountries.get(m));
                    for (int j = 0; j < MapEditorService.mapGraph.countryList.size(); j++) {
                        if (MapEditorService.mapGraph.countryList.get(j) == continentCountries.get(m))
                            MapEditorService.mapGraph.countryList.remove(j);
                    }

                }
            }
        }

        notifyObservers("delete continent", continentName);
    }

    /**
     * Check whether add country correctly
     *
     * @param countryName   string
     * @param continentName string
     */
    public boolean addCountry(String countryName, String continentName) {
        List<Continent> continentList = MapEditorService.mapGraph.getContinentList();
        boolean flag = false;
        for (int i = 0; i < continentList.size(); i++) {
            if (continentName.equals(continentList.get(i).getContinentName())) {
                flag = true;
            }
        }
        if (flag) {
            List<Country> countryList = MapEditorService.mapGraph.countryList;
            Country country = new Country(countryName, continentName, countryList.get(countryList.size() - 1).getId() + 1);
            notifyObservers("add country", country);
            MapEditorService.mapGraph.countryList.add(country);
            MapEditorService.mapGraph.adjacentCountries.put(country, new HashSet<>());
            for (int i = 0; i < continentList.size(); i++) {
                if (continentName.equals(continentList.get(i).getContinentName())) {
                    List<Country> countryList1 = MapEditorService.mapGraph.getContinentList().get(i).countries;
                    if (countryList1 == null) {
                        countryList1 = new LinkedList<>();
                        countryList1.add(country);
                    }
                    {
                        countryList1.add(country);
                    }
                    MapEditorService.mapGraph.getContinentList().get(i).setCountries(countryList1);
                }
            }
            return true;
        } else {
            return false;
        }


    }


    /**
     * Delete Country
     *
     * @param countryName string
     */
    public void deleteCountry(String countryName) {

        List<Country> countryList = MapEditorService.mapGraph.countryList;
        for (int i = 0; i < countryList.size(); i++) {
            if (countryName.equals(countryList.get(i).getCountryName())) {
                deleteCountryfromAdjacentCountries(countryList.get(i));

                MapEditorService.mapGraph.countryList.remove(i);
                List<Continent> continentList = MapEditorService.mapGraph.getContinentList();
                for (int j = 0; j < continentList.size(); j++) {
                    for (int t = 0; t < continentList.get(j).getCountries().size(); t++) {
                        if (countryName.equals(continentList.get(j).getCountries().get(t).getCountryName())) {
                            List<Country> countryList1 = MapEditorService.mapGraph.getContinentList().get(j).getCountries();
                            countryList1.remove(t);
                            MapEditorService.mapGraph.getContinentList().get(j).setCountries(countryList1);
                        }
                    }
                }
            }
        }

        Country country = new Country(countryName);
        notifyObservers("delete country", country);
    }

    /**
     * Add connection between country Name 1 and country Name 2
     *
     * @param countryName1 string
     * @param countryName2 string
     */
    public void addConnection(String countryName1, String countryName2) {
        Country country1 = findCountryByName(countryName1);
        Country country2 = findCountryByName(countryName2);
        Connection connection = new Connection(country1, country2);

        connectionList.add(connection);
        notifyObservers("add connection", connection);
    }

    /**
     * Check whether connection has been deleted properly
     *
     * @param countryName1 string
     * @param countryName2 string
     */
    public boolean deleteConnection(String countryName1, String countryName2) {
        /**
         * TODO:
         * get the Connection to be deleted by name
         * delete it from map
         */

        boolean flag = false;
        int i = 0;
        for (; i < connectionList.size(); i++) {
            if (connectionList.get(i).getCountry1().countryName.equals(countryName1) && connectionList.get(i).getCountry2().countryName.equals(countryName2)) {
                Connection connection = connectionList.get(i);
                connectionList.remove(i);
                notifyObservers("delete connection", connection);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Get all the connections associated with the country
     *
     * @return a list of Connection objects
     */
    public List<Connection> getConnections() {
        /**
         * TODO:
         * Get all the connections associated with the country
         */
        return connectionList;
    }

    /**
     * Remove Continent
     *
     * @param continent list
     */
    public void removeContinent(Continent continent) {
        continentList.remove(continent);
    }

    /**
     * Get Continent Name
     *
     * @return string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get Continent Height
     *
     * @return string
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     * Get Continent Width
     *
     * @return integer
     */
    public Integer getWidth() {
        return this.width;
    }

    /**
     * Get Adjacent Countries
     *
     * @return list
     */
    public LinkedHashMap<Country, Set<Country>> getAdjacentCountries() {
        return adjacentCountries;
    }

    /**
     * Set Adjacent Countries
     *
     * @param adjacentCountries list
     */
    public void setAdjacentCountries(LinkedHashMap<Country, Set<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    /**
     * Get Continent List
     *
     * @return list
     */
    public List<Continent> getContinentList() {
        return continentList;
    }

    /**
     * Set Continent List
     *
     * @param continentList list
     */
    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    /**
     * Get Country List
     *
     * @return list
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * Set Country List
     *
     * @param countryList list
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * Get Connection List
     *
     * @return list
     */
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * Set Connection List
     *
     * @param connectionList list
     */
    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }


    /**
     * delete country from adjacentcountryList
     *
     * @param country
     */
    private void deleteCountryfromAdjacentCountries(Country country) {
        MapEditorService.mapGraph.adjacentCountries.remove(country);
        for (Map.Entry<Country, Set<Country>> entry : MapEditorService.mapGraph.adjacentCountries.entrySet()) {
            if (entry.getValue().contains(country)) {
                Set<Country> updatedNeighborCountries = entry.getValue();
                updatedNeighborCountries.remove(country);
                MapEditorService.mapGraph.adjacentCountries.put(entry.getKey(), updatedNeighborCountries);

                entry.getKey().removeNeighbor(country);
            }
        }
    }

    /**
     * Find required Country by searching name
     *
     * @param countryName string
     * @return country
     */
    private Country findCountryByName(String countryName) {
        for (Country country : MapEditorService.mapGraph.getCountryList()) {
            if (countryName.equals(country.getCountryName())) {
                return country;
            }
        }
        return null;
    }
}
