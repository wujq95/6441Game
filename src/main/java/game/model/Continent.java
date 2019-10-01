package model;

import java.util.List;

public class Continent {

    String continentName;

    static Integer countryNo;

    Integer armyValue;

    List<Country> countries;

    /**
     * add continent
     * @param continentName
     * @param armyValue
     */
    public void addContinent(String continentName, int armyValue)
    {
        this.continentName=continentName;
        this.armyValue=armyValue;
    }

    /**
     * edit continent
     * @param continentName
     * @param armyValue
     */
    public void editContinent(String continentName, int armyValue)
    {
        this.continentName=continentName;
        this.armyValue=armyValue;
    }

    /**
     * add country to a continent
     * @param country
     */
    public void addCountry(Country country)
    {
        if(!this.countries.contains(country))
        {
            countryNo++;
            this.countries.add(country);
        }
    }

    /**
     * remove country from a continent
     * @param country
     */
    public void removeCountry(Country country)
    {
        if(this.countries.contains(country))
        {
            countryNo--;
            this.countries.remove(country);
        }
    }

    /**
     * edit country
     * @param countryName
     * @param parentContinent
     */
    public void editCountry(String countryName, Continent parentContinent)
    {

    }

    /**
     * check if a country is belong to a continent
     * @param country
     * @return
     */
    public boolean includeCountry(Country country)
    {
        return this.countries.contains(country);
    }

    /**
     * get continent name
     * @return
     */
    public String getContinentName()
    {
        return continentName;
    }

    /**
     * get army value
     * @return
     */
    public Integer getArmyValue()
    {
        return armyValue;
    }

    /**
     * get country number
     * @return
     */
    public Integer getCountryNo()
    {
        return countryNo;
    }

    /**
     * get country list of a continent
     * @return
     */
    public List<Country> getCountries()
    {
        return countries;
    }

}
