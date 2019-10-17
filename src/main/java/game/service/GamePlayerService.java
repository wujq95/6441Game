package service;

import model.Continent;
import model.Country;
import model.GamePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * service class to deal with player
 */
public class GamePlayerService {

    public static List<GamePlayer> playerList = new ArrayList<GamePlayer>();

    /**
     * check if player name is suitable for the game
     * @return boolean
     */
    public boolean checkPlayerNum(){
        if(playerList.size()>=2&&playerList.size()<=6){
            return true;
        }else{
            return false;
        }
    }

    /**
     * add players to the playerlist and remove players from the playerlist
     * @param arguments
     * @return message
     */
    public String gamePlayerAction(String[] arguments){

        List<String> addPlayerNameList = new ArrayList<String>();
        List<String> removePlayerNameList = new ArrayList<String>();

        for (int i=0;i<arguments.length;i++){
            if (arguments[i].startsWith("-add")){
                addPlayerNameList.add(arguments[i+1]);
            }
        }

        for (int i=0;i<arguments.length;i++){
            if (arguments[i].startsWith("-remove")){
                removePlayerNameList.add(arguments[i+1]);
            }
        }

        boolean checkDuplicate = checkDuplicatePlayerName(addPlayerNameList);
        boolean checkIncluded = checkPlayerNameIncluded(removePlayerNameList);

        if(checkDuplicate){
            return "player name duplicate";
        }else if(!checkIncluded){
            return "player name can not be found";
        }else{
            for(int i =0;i<arguments.length;i++){
                if(arguments[i].startsWith("-add")){
                    addPlayer(arguments[i+1]);
                }else if(arguments[i].startsWith("-remove")){
                    removePlayer(arguments[i+1]);
                }
            }
            return "gameplayer action success";
        }
    }

    /**
     * check if the playernames that are added are duplicated
     * @param playerNameList
     * @return boolean
     */
    public boolean checkDuplicatePlayerName(List<String> playerNameList){

        if(playerNameList.size()>0) {
            boolean flagAll = false;

            for (int i = 0; i < playerNameList.size(); i++) {
                boolean flag = false;
                for (GamePlayer player : playerList) {
                    if (playerNameList.get(i).equals(player.getPlayerName())) {
                        flag = true;
                    }
                }
                if (flag) {
                    flagAll = true;
                }
            }
            return flagAll;
        }else{
            return false;
        }
    }

    /**
     * check playernames that are removed can be found
     * @param playerNameList
     * @return boolean
     */
    public boolean checkPlayerNameIncluded(List<String> playerNameList){

        if(playerNameList.size()>0) {
            boolean flagAll = true;

            for (int i = 0; i < playerNameList.size(); i++) {
                boolean flag = false;
                for (GamePlayer player : playerList) {
                    if (playerNameList.get(i).equals(player.getPlayerName())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    flagAll = false;
                }
            }

            return flagAll;
        }else{
            return true;
        }
    }

    /**
     * add one player to the playerlist by playername
     * @param playerName
     */
    public void addPlayer(String playerName){
            GamePlayer player = new GamePlayer();
            player.setPlayerName(playerName);
            playerList.add(player);
    }


    /**
     * remove one player from the playerlist by name
     * @param playerName
     */
    public void removePlayer(String playerName){
        for(int i=0;i<playerList.size();i++){
            if(playerList.get(i).getPlayerName().equals(playerName)){
                playerList.remove(i);
            }
        }
    }

    /**
     * populate all countries to players
     * @return message
     */
    public String populateCountries(){

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
        return "popilatecountries success and ";
    }

    /**
     * allocate initial armies for all players
     * @return message
     */
    public String alloInitialArmy(){

        boolean flag = false;

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
        }else{
            initialArmies =0;
            flag =true;
        }

        for(GamePlayer player:playerList){
            player.setArmyValue(initialArmies);
        }
        if(flag){
            return "player number wrong!";
        }else{
            return "allocate initial army success";
        }
    }

    /**
     * place one army from the player to a country
     * @param countryName
     * @return message
     */
    public String placeOneArmy(String countryName){

        List<Country> countryList =  MapEditorService.mapGraph.getCountryList();

        int flag = 0;
        for(int i=0;i<playerList.size();i++){
            for (int j=0;j<playerList.get(i).getCountryList().size();j++){
                if (countryName.equals(playerList.get(i).getCountryList().get(j).getCountryName())){
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
     * @return message
     */
    public String placeAll(){
        for (GamePlayer player:playerList){
            Integer remainPlayerArmyValue = player.getArmyValue();
            List<Country> countryList = MapEditorService.mapGraph.getCountryList();
            if(remainPlayerArmyValue>0){
                for(int i=0;i<remainPlayerArmyValue;i++){
                    Integer index = (int)(Math.random()*countryList.size());
                    Integer newCountryArmyValue = countryList.get(index).getArmyValue()+1;
                    MapEditorService.mapGraph.getCountryList().get(index).setArmyValue(newCountryArmyValue);
                    player.setArmyValue(player.getArmyValue()-1);
                }
            }
        }
        return "place all success!";
    }

    /**
     * calculate the army number at the beginning of the reinforce phase
     * @return message
     */
    public String calReinArmyNum(){
        for(GamePlayer player:playerList){
            List<Country> countryList = player.getCountryList();
            Integer countryNum = Math.round(countryList.size()/3);

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
            Integer newPlayerArmyValue = player.getArmyValue()+countryNum+continentNum+3;
            player.setArmyValue(newPlayerArmyValue);
        }

        return "calculate reinforce number success";
    }

    /**
     * check if all armies have been put on the country
     * @return boolean
     */
    public boolean checkPutAll(GamePlayer player){
        if(player.getArmyValue()==0){
            return true;
        }else{
            return false;
        }
    }
}
