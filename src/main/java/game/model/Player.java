package model;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private String playerName;

    private int armyNum;    //num of armies owned by the player

    private List<Country> countryList;  //countries owned by the player

    public Player(String name){
        playerName = name;
        countryList = new LinkedList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getArmyNum() {
        return armyNum;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void addCountry(Country country){
        countryList.add(country);
    }
}
