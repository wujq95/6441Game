package model;

import javafx.geometry.Point2D;

import java.util.*;

public class MapGraph {
    String name;

    Integer height, width;

    List<Continent> continents;

    private Map<Country, List<Country>> adjacentCountries;

    // observers list
    private List<Observer> mapObservers = new ArrayList<>();

    public void attach(Observer observer){
        mapObservers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : mapObservers) {
            observer.update();
        }
    }

    public MapGraph(){

    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setHeight(Integer height)
    {
        this.height=height;
    }

    public void setWidth(Integer width)
    {
        this.width=width;
    }


    /**
     *
     * @param continent
     */
    public void addContinent(Continent continent){
        // TODO: validate the continent
        // if not valid, do something
        // else if valid, notify the Continent Observer

        continent.notifyAllObservers();
    }

    /**
     *
     * @param country
     */
    public void addCountry(Country country){
        // TODO: add country backend code here
        // if not valid, do something
        // else if valid, notify the Country Observer

        country.notifyAllObservers();
    }

    /**
     *
     * @param connection
     */
    public void addConnection(Connection connection){
        // TODO: validate the connnection
        // if not valid, do something
        // else if valid, notify the ConnectionObserver

        connection.notifyAllObservers();
    }

    public void removeContinent(Continent continent)
    {
        continents.remove(continent);
    }



    public String getName()
    {
        return this.name;
    }

    public Integer getHeight()
    {
        return this.height;
    }

    public Integer getWidth()
    {
        return this.width;
    }

    public Map<Country, List<Country>> getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(Map<Country, List<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public MapGraph(Map<Country, List<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }


}
