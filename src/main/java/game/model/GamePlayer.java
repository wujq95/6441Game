package model;

import java.util.ArrayList;
import java.util.List;

public class GamePlayer {
    /**
     * Default Constructor
     */
    public GamePlayer(){
    }

    /**
     * Constructor with player name , Number of Armies and country list
     * @param playerName
     * @param armyValue
     * @param countryList
     */
    public GamePlayer(String playerName,Integer armyValue,List<Country> countryList){
        this.armyValue = armyValue;
        this.countryList = countryList;
        this.playerName = playerName;
    }

    /**
     * Get PlayerName
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Set PlayerName
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get Number of Army
     * @return army number
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * Set Number of Army
     * @param armyValue
     */
    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * Get CountryList
     * @return country list
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * Set CountryList
     * @param countryList
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    String playerName;
    Integer armyValue;
    List<Country> countryList = new ArrayList<Country>();

}
