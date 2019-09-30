package model;

import java.util.List;

public class Country {

    Integer id;

    String countryName;

    List<Country> neighbours;

    Continent parentContinent;

    Player player;

    Integer armyValue;

}
