package model;

import controller.Observer;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapGraph {
    String name;

    Integer height, width;

    List<Continent> continentList;

    LinkedHashMap<Country, List<Country>> adjacentCountries;

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
                    observer.updateContinentList("add", (Continent) object);
                    break;
                case "delete continent":
                    observer.updateContinentList("delete", (Continent) object);
                    break;
                case "add country":
                    observer.updateCountry("add", (Country) object);
                    break;
                case "delete country":
                    observer.updateCountry("delete", (Country) object);
                    break;
                case "add connection":
                    observer.updateConnection("add", (Connection) object);
                    break;
                case "delete connection":
                    observer.updateConnection("delete", (Connection) object);
                    break;
            }
        }
    }

    public MapGraph() {
        continentList = new LinkedList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void addContinent(String continentName, Integer armyValue, Color color) {
        /**
         * TODO:
         * validate the continent
         * add it to map
         */

        Continent continent = new Continent(continentName, armyValue, color);
        continentList.add(continent);
        notifyObservers("add continent", this);
    }

    /**
     * @param continentName
     * @param continentValue
     * @param color
     */
    public void addContinent(String continentName, String continentValue, Color color) {
        /**
         * TODO:
         * validate the continent
         * add it to map
         */
        Continent continent = new Continent(continentName, 99, color);
        continentList.add(continent);
        notifyObservers("add continent", continent);
    }

    /**
     * @param continentName
     */
    public void deleteContinent(String continentName) {
        /**
         * TODO:
         * get the continent to be deleted by name
         * delete it from map
         */

        // Now Fake continent
        Continent continent = new Continent();
        notifyObservers("delete continent", continent);
    }

    /**
     * @param countryName
     * @param continentName
     */
    public void addCountry(String countryName, String continentName) {
        /**
         * TODO:
         * validate the country
         * add it to map
         */

        // Now Fake country
        Country country = new Country(countryName, continentName);
        notifyObservers("add country", country);
    }

    /**
     * @param countryName
     */
    public void deleteCountry(String countryName) {
        /**
         * TODO:
         * get the country to be deleted by name
         * delete it from map
         */

        // Now fake Country
        Country country = new Country(countryName);
        notifyObservers("delete country", country);
    }

    /**
     * @param countryName1
     * @param countryName2
     */
    public void addConnection(String countryName1, String countryName2) {
        /**
         * TODO:
         * validate the connection
         * add it to map
         */

        // Now fake Connection
        Connection connection = new Connection(countryName1, countryName2);
        notifyObservers("add connection", connection);
    }

    /**
     * @param countryName1
     * @param countryName2
     */
    public void deleteConnection(String countryName1, String countryName2) {
        /**
         * TODO:
         * get the Connection to be deleted by name
         * delete it from map
         */

        // Now fake Connection
        Connection connection = new Connection(countryName1, countryName2);
        notifyObservers("delete connection", connection);
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
        List<Connection> connectionList = new ArrayList<>();
        return connectionList;
    }

    public void removeContinent(Continent continent) {
        continentList.remove(continent);
    }

    public String getName() {
        return this.name;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public LinkedHashMap<Country, List<Country>> getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(LinkedHashMap<Country, List<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }
}
