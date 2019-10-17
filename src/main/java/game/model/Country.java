package model;

import javafx.geometry.Point2D;
import service.MapEditorService;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * save Country
 */
public class Country {

    Integer id;

    String countryName;

    double x;

    double y;

    List<Country> neighbours;

    Continent parentContinent;

    GamePlayer player;

    Integer armyValue = 0;

    /**
     * Get Id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set Id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set Country Name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Set Neighbours
     * @param neighbours
     */
    public void setNeighbours(List<Country> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Get ParentContinent
     * @return parentContinent
     */
    public Continent getParentContinent() {
        return parentContinent;
    }

    /**
     * Set ParentContinent
     * @param parentContinent
     */
    public void setParentContinent(Continent parentContinent) {
        this.parentContinent = parentContinent;
    }

    /**
     * Get Player
     * @return player
     */
    public GamePlayer getPlayer() {
        return player;
    }

    /**
     * Set Player
     * @param player
     */
    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    /**
     * Get Army Number
     * @return armyValue
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * Set Army Number
     * @param armyValue
     */
    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * Default Constructor
     */
    public Country() {
    }

    /**
     * Constructor
     * @param countryName
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Country Constructor
     *
     * @param countryName
     * @param continentName
     */
    public Country(String countryName, String continentName, int countryId) {
        this.id = countryId;
        this.countryName = countryName;
        this.parentContinent = findContinentByName(continentName);
        this.neighbours = new LinkedList<>();

        Random r = new Random();
        this.x = 500 * r.nextDouble();
        this.y = 500 * r.nextDouble();
    }

    /**
     * edit country name
     * @param countryName
     */
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
     * return Point2D location of the country
     *
     * @return Point2D (x,y)
     */
    public Point2D getCoordinator() {
        return new Point2D(x, y);
    }

    /**
     * add neighbor
     *
     * @param country
     */
    public void addNeighbor(Country country) {
        if (!this.neighbours.contains(country))
            this.neighbours.add(country);
    }

    /**
     * remove neighbor
     *
     * @param country
     */
    public void removeNeighbor(Country country) {
        this.neighbours.remove(country);
    }

    /**
     * check if countries are connected
     *
     * @param country
     * @return boolean
     */
    public boolean connected(Country country) {
        return this.neighbours.contains(country);
    }

    /**
     * get country name
     *
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * get all neighbours of a country
     *
     * @return neighbours
     */
    public List<Country> getNeighbours() {
        return neighbours;
    }

    /**
     * get the continent that the country belongs to
     *
     * @return continent
     */
    public Continent getContinent() {
        Continent continent = new Continent();
        return continent;
    }

    /**
     * Get Country Position X
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Set Country Position X
     * @param x
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Get Country Position Y
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Get Country Position Y
     * @param y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Country Constructor
     * @param id
     * @param countryName
     * @param parentContinent
     * @param positionX
     * @param positionY
     * @param armyValue
     */
    public Country(Integer id, String countryName, Continent parentContinent, double positionX, double positionY, int armyValue) {
        this.id = id;
        this.countryName = countryName;
        this.parentContinent = parentContinent;
        this.x = positionX;
        this.y = positionY;
        this.armyValue = armyValue;
    }

    /**
     * find continent by name
     * @param continentName
     * @return continent
     */
    private Continent findContinentByName(String continentName) {
        for (Continent continent : MapEditorService.mapGraph.getContinentList()) {
            if (continentName.equals(continent.getContinentName())) {
                return continent;
            }
        }
        return null;
    }
}
