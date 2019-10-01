package model;

import java.util.List;

public class Country {

    String countryName;

    List<Country> neighbours;

    Continent parentContinent;

    Player player;

    Integer armyValue;

    /**
     * initialise class member
     * @param countryName
     */
    public Country(String countryName)
    {
        this.countryName=countryName;
    }

    /**
     * add neighbor
     * @param country
     */
    public void addNeighbor(Country country)
    {
        this.neighbours.add(country);
    }

    /**
     * remove neighbor
     * @param country
     */
    public void removeNeighbor(Country country)
    {
        this.neighbours.remove(country);
    }

    /**
     * check if countries are connected
     * @param country
     * @return
     */
    public boolean connected(Country country)
    {
        return this.neighbours.contains(country);
    }

    /**
     * get country name
     * @return
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * get all neighbours of a country
     * @return
     */
    public List<Country> getNeighbours()
    {
        return neighbours;
    }
}
