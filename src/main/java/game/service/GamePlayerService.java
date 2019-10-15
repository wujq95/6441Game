package service;

import model.Continent;
import model.Country;
import model.GamePlayer;
import model.MapGraph;

import java.util.ArrayList;
import java.util.List;

public class GamePlayerService {

    static List<GamePlayer> playerList = new ArrayList<GamePlayer>();


    public boolean checkPlayerNum(){
        if(playerList.size()>=2&&playerList.size()<=6){
            return true;
        }else{
            return false;
        }
    }

    /**
     * add player in the player list
     * @param playerName
     * @return
     */
    public String addPlayer(String playerName){
        GamePlayer player = new GamePlayer();
        player.setPlayerName(playerName);
        playerList.add(player);
        return "add player success";
    }

    /**
     * remove a player from the player list
     * @param playerName
     * @return
     */
    public String removePlayer(String playerName){
        boolean flag = false;
        for(int i=0;i<playerList.size();i++){
            if(playerList.get(i).getPlayerName().equals(playerName)){
                playerList.remove(i);
                flag = true;
            }
        }
        if(flag){
            return "remove player success";
        }else{
            return "player can not be found";
        }
    }

    /**
     * populate all countries to players
     * @return
     */
    public String populateCountries(){

        /*if(checkPlayerNum()){

        }*/

        List<Country> countryList =  MapEditorService.mapGraph.getCountryList();

        Integer playerNum = playerList.size();
        for(int i=0;i<countryList.size();i++){
            List<Country> playerCountryList = playerList.get(i%playerNum).getCountryList();
            playerCountryList.add(countryList.get(i));
            playerList.get(i%playerNum).setCountryList(playerCountryList);
            MapEditorService.mapGraph.getCountryList().get(i).setPlayer(playerList.get(i%playerNum));
        }

        /*for(Country country:countryList){
             Integer randomInt = (int)(Math.random()*playerList.size());
            GamePlayer gamePlayer = playerList.get(randomInt);
            List<Country> playerCountryList =  gamePlayer.getCountryList();
            playerCountryList.add(country);
            playerList.get(randomInt).setCountryList(playerCountryList);
        }*/
        return "popilatecountries success";
    }

    /**
     * allocate initial armies for all players
     * @return
     */
    public String alloInitialArmy(){

        /*if(checkPlayerNum()){

        }*/

        Integer playerNum = playerList.size();
        Integer initialArmies = 0;
        if(playerNum == 2){
            initialArmies=40;
        } else if(playerNum==3){
            initialArmies=35;
        } else if(playerNum==4) {
            initialArmies = 30;
        } else if(playerNum==5){
            initialArmies =25;
        } else if(playerNum==6){
            initialArmies=20;
        }
        for(GamePlayer player:playerList){
            player.setArmyValue(initialArmies);
        }

        //for(Country country:MapEditorService.mapGraph.getCountryList()){
          //  country.getPlayer().setArmyValue(initialArmies);
        //}

        return "allocate initial army success";
    }

    /**
     * place one army from the player to a country
     * @param countryName
     * @return
     */
    public String placeOneArmy(String countryName){

        List<Country> countryList =  MapEditorService.mapGraph.getCountryList();

        int flag = 0;
        for(int i=0;i<playerList.size();i++){
            for (int j=0;j<playerList.get(i).getCountryList().size();j++){
                if (playerList.get(i).getCountryList().get(j).equals(countryName)){
                    flag=1;
                    Integer PlayerArmyValue  = playerList.get(i).getArmyValue();
                    if(PlayerArmyValue==0){
                        flag = 2;
                    }else{
                        //Integer newCountryArmyValue = playerList.get(i).getCountryList().get(j).getArmyValue()+1;
                        //playerList.get(i).getCountryList().get(j).setArmyValue(newCountryArmyValue);
                        for(Country country:MapEditorService.mapGraph.getCountryList()){
                            if(country.getCountryName().equals(countryName)){
                                Integer newCountryArmyValue = country.getArmyValue()+1;
                                country.setArmyValue(newCountryArmyValue);
                            }
                        }

                        Integer newPlayerArmyValue=playerList.get(i).getArmyValue()-1;
                        playerList.get(i).setArmyValue(newPlayerArmyValue);
                    }
                }
            }
        }
        if(flag==0){
            return "country name can not be found";
        }else if(flag ==2){
            return "the army value of the player is not enough";
        }else{
            return "place one army success";
        }

    }

    /**
     * automically place all armies to countries
     * @return
     */
    public String placeAll(){

        for (GamePlayer player:playerList){
            Integer remainPlayerArmyValue = player.getArmyValue();
            List<Country> countryList = player.getCountryList();
            for(int i=0;i<remainPlayerArmyValue;i++){
                Integer index = (int)(Math.random()*countryList.size());
                Integer newCountryArmyValue = countryList.get(index).getArmyValue()+1;
                player.getCountryList().get(i).setArmyValue(newCountryArmyValue);
            }
            player.setArmyValue(0);
        }
        return "place all success!";
    }

    /**
     * calculate the army number at the beginning of the reinforce phase
     * @return
     */
    public String CalReinArmyNum(){

        for(GamePlayer player:playerList){
            List<Country> countryList = player.getCountryList();
            Integer CountryNum = Math.round(countryList.size()/3);

            List<Continent> continentList =  MapEditorService.mapGraph.getContinentList();
            Integer continentNum = 0;
            for(Continent continent:continentList){
                boolean flag = true;
                for(Country country:continent.getCountries()){
                    if (!country.getPlayer().getPlayerName().equals(player.getPlayerName())){
                        flag =false;
                    }
                }
                if(flag){
                    continentNum++;
                }
            }

            Integer newPlayerArmyValue = player.getArmyValue()+continentNum+CountryNum+3;
            player.setArmyValue(newPlayerArmyValue);
        }

        return "calculate reinforce number success";
    }

    /**
     * check if all armies have been put on the country
     * @return
     */
    public boolean checkPutAll(GamePlayer player){
        if(player.getArmyValue()==0){
            return true;
        }else{
            return false;
        }
    }
}