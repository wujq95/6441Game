package model;

import javafx.scene.paint.Color;
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
     * @param continentName
     * @param armyValue
     * @param color
     */
    public Continent(String continentName, int armyValue, Color color) {
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.continentColor = color;
    }

    /**
     * Constructor with Continent Name and Number of Armies only
     * @param continentName
     * @param armyValue
     */
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
     * edit continent Army value
     *
     * @param armyValue
     */
    public void editContinentValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * add country to continent
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
     * @param country
     * @return Country
     */
    public boolean includeCountry(Country country) {
        return this.countries.contains(country);
    }

    /**
     * get continent name
     * @return Continent Name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * TODO: return the representing Color of the continent
     * @return Color
     */
    public Color getColor(){
        return continentColor;
    }

    /**
     * get army value
     * @return Number of Army
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * get country number
     * @return Country Number
     */
    public Integer getCountryNumber() {
        return countryNumber;
    }

    /**
     * get country list of a continent
     * @return country
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Get Id
     * @return Country id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set Id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set ContinentName
     * @param continentName
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Set Country
     * @param countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Default Constructor for Continent
     * @param id
     * @param continentName
     * @param armyValue
     * @param color
     * @param countryList
     */
    public Continent(Integer id, String continentName, int armyValue, Color color, List<Country> countryList) {
        this.id = id;
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.continentColor = color;
        this.setCountries(countryList);
    }
}
