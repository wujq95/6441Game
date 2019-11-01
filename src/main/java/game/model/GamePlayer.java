package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Game player class stored player's attributes and related methods
 */
public class GamePlayer {
    /**
     * Default Constructor
     */
    public GamePlayer(){
    }

    /**
     * Constructor with player name , Number of Armies and country list
     * @param playerName 　player name
     * @param armyValue number of army
     * @param countryList country list
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
     * @param playerName player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get Number of Army
     * @return number of army
     */
    public Integer getArmyValue() {
        return armyValue;
    }

    /**
     * Set Number of Army
     * @param armyValue number of army
     */
    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    /**
     * Get CountryList
     * @return country country list
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * Set CountryList
     * @param countryList country list
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    /**
     * Initial variables
     */
    String playerName;
    Integer armyValue;
    List<Country> countryList = new ArrayList<Country>();
    List<Card> cardList;
    Integer num;


}
