package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    Integer playerId=0;
    String playerName="";
    Integer numOfArmies=0;
    String countryName="";
    List<Player> players ;

    public Player(){

    }

    public Player(String PlayerName, Integer NumOfArmies, Integer playerId){
        this.playerName = PlayerName;
        this.numOfArmies = NumOfArmies;
        this.playerId= playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getNumOfArmies() {
        return numOfArmies;
    }

    public void setNumOfArmies(Integer numOfArmies) {
        this.numOfArmies = numOfArmies;
    }

    public void setCountryName(String CountryName) {
        countryName = CountryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setPlayerId(Integer PlayerId) {
        playerId = PlayerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    /**
     * Add player name into player list
     * @param PlayerNames
     */
    public void AddPlayer(Player PlayerNames){
        if(!this.players.contains(PlayerNames))
        { this.players.add(PlayerNames); playerId++;}}
    /**
     * Remove player
     * @param PlayerName
     */
    public void RemovePlayer(Player PlayerName){
        this.players.remove(PlayerName); playerId--;
    }

    /**
     *  Identify the total number of armies according to the player number
     * @param playerId
     * @return
     */
    public Integer populateCountries(int playerId){
        int initialArmies=0;

        if(playerId == 2){
            initialArmies=40;
        } else if(playerId==3){
            initialArmies=35;
        } else if(playerId==4) {
            initialArmies = 30;
        } else if(playerId==5){
            initialArmies =25;
        }   else  if(playerId==6){
            initialArmies=20;
        }
        numOfArmies=initialArmies;
        return numOfArmies;

    }

    /**
     * Place armies for each players
     * @param countryName
     * @param AmountOfArmies
     */
    public void placeArmy(String countryName, int AmountOfArmies){
        int Balance;
        Balance = this.numOfArmies - AmountOfArmies;
        do{
            if(players.contains(countryName)){
                this.numOfArmies+= AmountOfArmies;
            }} while(Balance!=0);


    }

    /**
     * Divided all number of armies into each player
     * @param AmountOfArmies
     * @param playerId
     */
    public static void n_random(int AmountOfArmies, int playerId) {
        Random r = new Random();
        List<Integer> load = new ArrayList<>();

        //random numbers
        int sum = 0;
        for (int i = 0; i < playerId; i++) {
            int next = r.nextInt(AmountOfArmies) + 1;
            load.add(next);
            sum += next;
        }

        //scale to the desired target sum
        double scale = 1d * AmountOfArmies / sum;
        sum = 0;
        for (int i = 0; i < playerId; i++) {
            load.set(i, (int) (load.get(i) * scale));
            sum += load.get(i);
        }

        //take rounding issues into account
        while (sum++ < AmountOfArmies) {
            int i = r.nextInt(playerId);
            load.set(i, load.get(i) + 1);
        }

    }}