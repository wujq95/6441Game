package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private MapGraph mapGraph;

    private ArrayList<Player> playerList;

    private ArrayList<Country> populatedCountryList;

    private int currentPlayer;

    public Game(MapGraph mapGraph){
        this.mapGraph = mapGraph;
        playerList = new ArrayList<>();
        populatedCountryList = new ArrayList<>();
    }

    public Player addGamePlayer(String playerName){
        Player player = new Player(playerName);
        playerList.add(player);
        return player;
    }

    public Player deletePlayer(String playerName){
        for(int i=0; i<playerList.size(); ++i){
            if(playerList.get(i).getPlayerName().equals(playerName)){
                Player removedPlayer = playerList.remove(i);
                return removedPlayer;
            }
            ++i;
        }
        return null;
    }

    public void populateCountries(){
        List<Country> countryList = mapGraph.getCountryList();
        int playerCount = playerList.size();

        //loop through countrylist, assign the country to a random player in playerList
        for (Country country:countryList) {
            // rand [0, playerCount)
            int randomNum = ThreadLocalRandom.current().nextInt(0, playerCount);
            Player player = playerList.get(randomNum);  //get a random player
            country.setPlayer(player);  //set the player as the country owner
            playerList.get(randomNum).addCountry(country);  //also assign the country to the player
            populatedCountryList.add(country);
        }
    }
}
