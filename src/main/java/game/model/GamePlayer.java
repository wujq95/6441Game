package model;

import java.util.ArrayList;
import java.util.List;

public class GamePlayer {

    public GamePlayer(){
    }

    public GamePlayer(String playerName,Integer armyValue,List<Country> countryList){
        this.armyValue = armyValue;
        this.countryList = countryList;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getArmyValue() {
        return armyValue;
    }

    public void setArmyValue(Integer armyValue) {
        this.armyValue = armyValue;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    String playerName;
    Integer armyValue;
    List<Country> countryList = new ArrayList<Country>();

}
