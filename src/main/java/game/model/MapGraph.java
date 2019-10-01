package model;

import java.util.List;
import java.util.Map;

public class MapGraph {
    String name;

    Integer height, width;

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

}
