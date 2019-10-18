package model;

import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Continent {
    Integer id;

    String continentName;

    Integer countryNumber;

    int armyValue;

    Color continentColor;

    List<Country> countries;

    /**
     * Default Constructor
     */
    public Continent() {
    }

    /**
     * Constructor with continent Name, Number of Armies and color
     * @param continentName continent name
     * @param armyValue number of army
     * @param color color
     */
    public Continent(String continentName, int armyValue, Color color) {
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.continentColor = color;
        this.countries = new LinkedList<>();
    }

    /**
     * Constructor with Continent Name and Number of Armies only
     * @param continentName continent name
     * @param armyValue number of army
     */
    public Continent(String continentName, int armyValue) {
        this.continentName = continentName;
        this.armyValue = armyValue;
    }

    /**
     * edit continent name
     *
     * @param continentName continent name
     */
    public void editContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * edit continent Army value
     *
     * @param armyValue number of army
     */
    public void editContinentValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * add country to continent
     *
     * @param country country name
     */
    public void addCountry(Country country) {
        if (!this.countries.contains(country)) {
            countryNumber++;
            this.countries.add(country);
        }
    }

    /**
     * remove country from a continent
     * @param country country name
     */
    public void removeCountry(Country country) {
        if (this.countries.contains(country)) {
            countryNumber--;
            this.countries.remove(country);
        }
    }

    /**
     * check if a country is belong to a continent
     * @param country country name
     * @return country name
     */
    public boolean includeCountry(Country country) {
        return this.countries.contains(country);
    }

    /**
     * get continent name
     * @return continent name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * TODO: return the representing Color of the continent
     * @return return color
     */
    public Color getColor(){
        return continentColor;
    }

    /**
     * get army value
     * @return number of army
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * get country number
     * @return country number
     */
    public Integer getCountryNumber() {
        return countryNumber;
    }

    /**
     * get country list of a continent
     * @return country list inside specified continent
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Get Id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set Id
     * @param id continent id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set ContinentName
     * @param continentName continent name
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Set Country
     * @param countries countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Default Constructor for Continent
     * @param id id
     * @param continentName continent name
     * @param armyValue number of army
     * @param color color
     * @param countryList country list
     */
    public Continent(Integer id, String continentName, int armyValue, Color color, List<Country> countryList) {
        this.id = id;
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.continentColor = color;
        this.setCountries(countryList);
    }
}
