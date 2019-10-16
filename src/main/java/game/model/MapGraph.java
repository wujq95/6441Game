package model;

import controller.Observer;
import javafx.scene.paint.Color;
import service.MapEditorService;

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

    /**
     * Default Constructor
     */
    public MapGraph() {
        continentList = new LinkedList<>();
    }

    /**
     * Set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Height
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Set Width
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Add Continent with name value and color
     * @param continentName
     * @param armyValue
     * @param color
     */
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
        MapEditorService.mapGraph.continentList.add(continent);
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

        for (int i = 0; i < MapEditorService.mapGraph.continentList.size(); i++) {
            if (continentName.equals(continentList.get(i).getContinentName())) {
                MapEditorService.mapGraph.continentList.remove(i);
                List<Country> countryList1=continentList.get(i).getCountries();
                for(int m=0;m<countryList.size();m++)
                {
                    for(int j=0;j<countryList1.size();j++)
                    {
                        if(countryList.get(m)==countryList1.get(j))
                            countryList.remove(m);
                    }

                }
            }
        }
        notifyObservers("delete continent", continentName);
    }

    /**
     * @param countryName
     * @param continentName
     */
    public boolean addCountry(String countryName, String continentName) {
        /**
         * TODO:
         * validate the country
         * add it to map
         */

        List<Continent> continentList = MapEditorService.mapGraph.getContinentList();
        boolean flag = false;
        for (int i = 0; i < continentList.size(); i++) {
            if (continentName.equals(continentList.get(i).getContinentName())) {
                flag = true;
            }
        }
        if (flag) {
            Country country = new Country(countryName, continentName);
            notifyObservers("add country", country);
            MapEditorService.mapGraph.countryList.add(country);
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
     * @param countryName
     */
    public void deleteCountry(String countryName) {
        /**
         * TODO:
         * get the country to be deleted by name
         * delete it from map
         */

        List<Country> countryList = MapEditorService.mapGraph.countryList;
        for (int i = 0; i < countryList.size(); i++) {
            if (countryName.equals(countryList.get(i).getCountryName())) {
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
     * @param countryName1
     * @param countryName2
     */
    public void addConnection(String countryName1, String countryName2) {
        /**
         * TODO:
         * validate the connection
         * add it to map
         */
        int count=0;
        Connection connection = new Connection(countryName1, countryName2);
        connectionList.add(connection);
        count++;
        if (connectionList.indexOf(connection) == count){
            System.out.println("New Connection Added");
            notifyObservers("add connection", connection);
        }

    }

    /**
     * @param countryName1
     * @param countryName2
     */
    public boolean deleteConnection(String countryName1, String countryName2) {
        /**
         * TODO:
         * get the Connection to be deleted by name
         * delete it from map
         */
        boolean flag = false;
        int count=connectionList.size();
        int i = 0;
        for (; i < connectionList.size(); i++) {
            if (connectionList.get(i).getCountry1().countryName == countryName1 && connectionList.get(i).getCountry2().countryName == countryName2) {
                Connection connection = connectionList.get(i);
                connectionList.remove(i);
                count--;
                if(connectionList.size()==count){
                notifyObservers("delete connection", connection);
                flag = true;}
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
