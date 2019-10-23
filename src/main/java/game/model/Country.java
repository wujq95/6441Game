package model;

import javafx.geometry.Point2D;
import service.MapEditorService;

import java.util.*;

/**
 * Countries variables and basic methods
 */
public class Country {

    Integer id;

    String countryName;

    double x;

    double y;

    Set<Country> neighbours;

    Continent parentContinent;

    GamePlayer player;

    Integer armyValue = 0;

    /**
     * Get Id
     * @return integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set Id
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set Country Name
     * @param countryName string
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Set Neighbours
     * @param neighbours list
     */
    public void setNeighbours(Set<Country> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Get ParentContinent
     * @return list
     */
    public Continent getParentContinent() {
        return parentContinent;
    }

    /**
     * Set ParentContinent
     * @param parentContinent list
     */
    public void setParentContinent(Continent parentContinent) {
        this.parentContinent = parentContinent;
    }

    /**
     * Get Player
     * @return list
     */
    public GamePlayer getPlayer() {
        return player;
    }

    /**
     * Set Player
     * @param player player
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
     * @param armyValue number of army
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
     * @param countryName country name
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Country Constructor
     * @param countryId country Id
     * @param countryName country name
     * @param continentName continent name
     */
    public Country(String countryName, String continentName, int countryId) {
        this.id = countryId;
        this.countryName = countryName;
        this.parentContinent = findContinentByName(continentName);
        this.neighbours = new HashSet<>();

        Random r = new Random();
        this.x = 500 * r.nextDouble();
        this.y = 500 * r.nextDouble();

        this.player = new GamePlayer();
    }

    public void editCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * set coordinators
     *
     * @param x x
     * @param y y
     */
    public void setCoordinator(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * TODO: return Point2D location of the country
     *
     * @return node position info
     */
    public Point2D getCoordinator() {
        return new Point2D(x, y);
    }

    /**
     * TODO: add neighbor
     *
     * @param country country
     */
    public void addNeighbor(Country country) {
        if (!this.neighbours.contains(country))
            this.neighbours.add(country);
    }

    /**
     * TODO: remove neighbor
     *
     * @param country country
     */
    public void removeNeighbor(Country country) {
        this.neighbours.remove(country);
    }

    /**
     * TODO: check if countries are connected
     *
     * @param country country
     * @return boolean
     */
    public boolean connected(Country country) {
        return this.neighbours.contains(country);
    }

    /**
     * TODO: get country name
     *
     * @return string
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * TODO: get all neighbours of a country
     *
     * @return list
     */
    public Set<Country> getNeighbours() {
        return neighbours;
    }

    /**
     * TODO: get the continent that the country belongs to
     *
     * @return Get Neighbours
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
     * @param x y
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
     * @param y y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Country Constructor
     * @param id id
     * @param countryName country name
     * @param parentContinent parent continent
     * @param positionX x
     * @param positionY y
     * @param armyValue number of value
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
     * According to the continent Name to find required continent
     * @param continentName continent name
     * @return null or continent
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
