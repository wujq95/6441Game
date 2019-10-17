package model;

import java.util.ArrayList;
import java.util.List;

/**
 * save gamePlayer
 */
public class GamePlayer {
    /**
     * Default Constructor
     */
    public GamePlayer(){
    }

    /**
     * Default Constructor
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
     * @return playerName
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
     * @return armyValue
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
     * @return countryList
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
