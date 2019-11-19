package model;

import strategy.Strategy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Game player class stored player's attributes and related methods
 */
public class GamePlayer {

    private Strategy strategy;

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void attack(){
        this.strategy.attack();
    }

    public void reinforce(){
        this.strategy.reinforce();
    }

    public void fortify(){
        this.strategy.fortify();
    }

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
        this.cardList = new LinkedList<>();
        this.controlledContinent = new LinkedList<>();
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

    /**
     * Get card list
     * @return card list
     */
    public List<Card> getCardList() {
        return cardList;
    }

    /**
     * set card list
     * @param cardList card list
     */
    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    /**
     * set num
     * @param num dice number
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * get num
     * @return number
     */
    public Integer getNum() {
        return num;
    }

    /**
     * set controlled continent
     * @param controlledContinent controlled continent list
     */
    public void setControlledContinent(List<String> controlledContinent){this.controlledContinent = controlledContinent;}

    /**
     * get controlled continent
     * @return controlled continent
     */
    public List<String> getControlledContinent (){return controlledContinent;}

    /**
     * Initial variables
     */
    String playerName;
    Integer armyValue;
    List<Country> countryList = new ArrayList<Country>();
    List<Card> cardList;
    Integer num;
    List<String> controlledContinent = new LinkedList<>();


}
