package model;

import javafx.geometry.Point2D;
import service.MapEditorService;

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

    Integer armyValue = 0;

    /**
     * Get Id
     * @return
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
     * @return
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
     * @return
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

        this.player = new GamePlayer();
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

    /**
     * Get Country Position X
     * @return
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
     * @return
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
     * @return null
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
