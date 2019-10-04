package model;

import java.util.List;
import java.util.Map;

public class MapGraph {

    private Map<Country, List<Country>> adjacentCountries;

    public MapGraph(Map<Country, List<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }
}
