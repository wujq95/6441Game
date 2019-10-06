package model;

import java.util.List;
import java.util.Map;

public class MapGraph {
    String name;

    Integer height, width;

    List<Continent> continents;

    private Map<Country, List<Country>> adjacentCountries;

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


    public MapGraph(Map<Country, List<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }
}
