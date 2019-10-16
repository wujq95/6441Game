package model;

import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Country {

    Integer id;

    String countryName;

    double x;

    double y;

    List<Country> neighbours;

    Continent parentContinent;

    GamePlayer player;

    Integer armyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    public Integer getArmyValue() {
        return armyValue;
    }

    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    public Country() {
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Country Constructor
     *
     * @param countryName
     * @param continentName
     */
    public Country(String countryName, String continentName) {
        this.countryName = countryName;
        this.neighbours = new LinkedList<>();

        Random r = new Random();
        this.x = 500 * r.nextDouble();
        this.y = 500 * r.nextDouble();
    }

    public void editCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * set coordinators
     *
     * @param x
     * @param y
     */
    public void setCoordinator(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * TODO: return Point2D location of the country
     *
     * @return
     */
    public Point2D getCoordinator() {
        return new Point2D(x, y);
    }

    /**
     * TODO: add neighbor
     *
     * @param country
     */
    public void addNeighbor(Country country) {
        if (!this.neighbours.contains(country))
            this.neighbours.add(country);
    }

    /**
     * TODO: remove neighbor
     *
     * @param country
     */
    public void removeNeighbor(Country country) {
        this.neighbours.remove(country);
    }

    /**
     * TODO: check if countries are connected
     *
     * @param country
     * @return
     */
    public boolean connected(Country country) {
        return this.neighbours.contains(country);
    }

    /**
     * TODO: get country name
     *
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * TODO: get all neighbours of a country
     *
     * @return
     */
    public List<Country> getNeighbours() {
        return neighbours;
    }

    /**
     * TODO: get the continent that the country belongs to
     *
     * @return
     */
    public Continent getContinent() {
        Continent continent = new Continent();
        return continent;
    }

    public double getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Country(Integer id, String countryName, Continent parentContinent, double positionX, double positionY, int armyValue) {
        this.id = id;
        this.countryName = countryName;
        this.parentContinent = parentContinent;
        this.x = positionX;
        this.y = positionY;
        this.armyValue = armyValue;
    }
}
