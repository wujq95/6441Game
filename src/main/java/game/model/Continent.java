package model;

import java.util.List;

public class Continent {
    Integer id;

    String continentName;

    Integer countryNumber;

    int armyValue;

    String color;

    List<Country> countries;

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

    public Continent(Integer id, String continentName, int armyValue, String color) {
        this.id = id;
        this.continentName = continentName;
        this.armyValue = armyValue;
        this.color = color;
    }
}
