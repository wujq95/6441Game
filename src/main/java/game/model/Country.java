package model;

import java.util.List;

public class Country {

    Integer id;

    String countryName;

    Integer positionX;

    Integer positionY;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Country> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Country> neighbours) {
        this.neighbours = neighbours;
    }

    public Continent getParentContinent() {
        return parentContinent;
    }

    public void setParentContinent(Continent parentContinent) {
        this.parentContinent = parentContinent;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getArmyValue() {
        return armyValue;
    }

    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    List<Country> neighbours;

    Continent parentContinent;

    Player player;

    Integer armyValue;

    public Country(Integer id, String countryName, Continent parentContinent, int positionX, int positionY) {
        this.id = id;
        this.countryName = countryName;
        this.parentContinent = parentContinent;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
