package model;

import java.util.List;

public class Country {

    String countryName;

    Integer x, y;

    List<Country> neighbours;

    Continent parentContinent;

    Player player;

    Integer armyValue;

    public Country(){}

    public Country(String countryName)
    {
        this.countryName=countryName;
    }

    public void editCountryName(String countryName){ this.countryName=countryName; }

    /**
     * set coordinators
     * @param x
     * @param y
     */
    public void setCoordinator(Integer x, Integer y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     * add neighbor
     * @param country
     */
    public void addNeighbor(Country country)
    {
        if(!this.neighbours.contains(country))
            this.neighbours.add(country);
    }

    /**
     * remove neighbor
     * @param country
     */
    public void removeNeighbor(Country country)
    {
        if(this.neighbours.contains(country))
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
