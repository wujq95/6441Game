package model;

import controller.Observer;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Continent {
    Integer id;

    String continentName;

    Integer countryNumber;

    int armyValue;

    String color;

    List<Country> countries;

    // observers list
    private List<Observer> continentObservers = new ArrayList<>();

    public void attach(Observer observer){
        continentObservers.add(observer);
    }

//    public void notifyAllObservers(){
//        for(Observer observer : continentObservers)
//            observer.update();
//    }

    public Continent() {
    }

    public Continent(String continentName, int armyValue) {
        this.continentName = continentName;
        this.armyValue = armyValue;
    }

    /**
     * edit continent name
     *
     * @param continentName
     */
    public void editContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * edit continent value
     *
     * @param armyValue
     */
    public void editContinentValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * add country to a continent
     *
     * @param country
     */
    public void addCountry(Country country) {
        if (!this.countries.contains(country)) {
            countryNumber++;
            this.countries.add(country);
        }
    }

    /**
     * remove country from a continent
     *
     * @param country
     */
    public void removeCountry(Country country) {
        if (this.countries.contains(country)) {
            countryNumber--;
            this.countries.remove(country);
        }
    }

    /**
     * check if a country is belong to a continent
     *
     * @param country
     * @return
     */
    public boolean includeCountry(Country country) {
        return this.countries.contains(country);
    }

    /**
     * get continent name
     *
     * @return
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * TODO: return the representing Color of the continet
     * TODO:MUZIQIU ???
     * @return
     */
    public Color getColor(){
        Color color = Color.GRAY;
        return color;
    }

    /**
     * get army value
     *
     * @return
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * get country number
     *
     * @return
     */
    public Integer getCountryNumber() {
        return countryNumber;
    }

    /**
     * get country list of a continent
     *
     * @return
     */
    public List<Country> getCountries() {
        return countries;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Continent(Integer id, String continentName, int armyValue, String color) {
        this.id = id;
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.color = color;
    }
}
