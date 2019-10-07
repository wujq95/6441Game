package model;

import javafx.geometry.Point2D;

import java.util.*;

public class MapGraph {
    String name;

    Integer height, width;

    List<Continent> continents;

    //*******************************************************************************
    //************Don't Delete, Zelan's Testing Observer Pattern*********************

    private Map<Country, List<Country>> adjacentCountries;

    private List<Observer> observers = new ArrayList<Observer>();

    private List<Point2D> countryLocationList = new ArrayList<Point2D>();

    public MapGraph(){

    }

    public List<Point2D> getCountryLocationList() {
        return countryLocationList;
    }

    public void setCountryLocationList(List<Point2D> countryLocationList) {
        this.countryLocationList = countryLocationList;
        notifyAllObservers();
    }

    public void addCountryLocation(double x, double y){
        Point2D pt = new Point2D(x, y);
        this.countryLocationList.add(pt);
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    //************************************Finish*************************************

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

    public void addContinent(String continentName, Integer armyValue)
    {
        Continent continent=new Continent(continentName, armyValue);
        continents.add(continent);
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
