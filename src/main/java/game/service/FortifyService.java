package service;

import model.Country;
import model.GamePlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static controller.MapController.gamePlayerService;

/**
 * service class for fortify phase
 */
public class FortifyService {

    public static Integer playerNum = 0;

    /**
     * Fortify Action
     * @param fromCountry From Country Name
     * @param toCountry To Country Name
     * @param num Number
     * @return Message
     */
    public String fortify(String fromCountry, String toCountry, String num){
        Integer fortifyArmyValue = Integer.parseInt(num);
        if(fortifyArmyValue<0){
            return "fortify num can be negative";
        }else{
            boolean flag1 = checkPlayer(fromCountry);
            boolean flag2 = checkPlayer(toCountry);

            List<Country> countryList = MapEditorService.mapGraph.getCountryList();

            int flag = 0;
            if(flag1){
                flag = 1;
                if(flag2){
                    flag=2;
                    boolean flag3= checkConnected(fromCountry,toCountry);
                    if(flag3){
                        for(int i=0;i<countryList.size();i++){
                            if(toCountry.equals(countryList.get(i).getCountryName())) {
                                for (int j = 0; j < countryList.size(); j++) {
                                    if (fromCountry.equals(countryList.get(j).getCountryName())) {
                                        if (countryList.get(j).getArmyValue() < fortifyArmyValue+1) {
                                            flag = 3;
                                        }else{
                                            Integer newFromCountry = countryList.get(j).getArmyValue() - fortifyArmyValue;
                                            Integer newToCountry =countryList.get(i).getArmyValue() + fortifyArmyValue;
                                            MapEditorService.mapGraph.getCountryList().get(j).setArmyValue(newFromCountry);
                                            MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newToCountry);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        flag = 4;
                    }
                }
            }
            if (flag==0){
                return "from Country name can not be found";
            }else if(flag==1){
                return "to Country name can not be found";
            }else if(flag==3){
                return "the army value of the form country is not enough";
            }else if(flag==4){
                return "two countries are not connected";
            }else {
                playerNum++;
                boolean flag3  =checkStop();
                if(flag3){
                    GamePlayerService.checkPhase++;
                }
                return "fortify success";
            }
        }
    }

    /**
     * No fortify
     * @return message
     */
    public String fortifyNone(){
        playerNum++;
        boolean flag  =checkStop();
        if(flag){
            GamePlayerService.checkPhase++;
        }
        return "fortify none success";
    }

    /**
     * Check player's turn
     * @return true or false
     */
    public boolean checkStop(){
        boolean flag=false;
        if(playerNum>=GamePlayerService.playerList.size()){
            flag=true;
        }
        return flag;
    }

    /**
     * check the country if from which player
     * @param countryName country name
     * @return True of False
     */
    public boolean checkPlayer(String countryName){

        boolean flag = false;
        for(int j=0;j<GamePlayerService.playerList.get(playerNum).getCountryList().size();j++){
            if(countryName.equals(GamePlayerService.playerList.get(playerNum).getCountryList().get(j).getCountryName())){
                flag=true;
            }
        }
        return flag;
    }

    /**
     * check if fortify countries are connected
     * @param fromCountry Initial Army Moving Country Name
     * @param toCountry Goal Army Moving Country Name
     * @return True or False
     */
    public boolean checkConnected(String fromCountry,String toCountry){
        boolean flag =false;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Set<Country> countryList = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it = countryList.iterator();
                while(it.hasNext()){
                    Country cc= (Country) it.next();
                    if(cc.getCountryName().equals(toCountry)){
                        flag=true;
                    }
                }
            }
        }
        return flag;
    }

    public String getCurrentPlayerName(){
        GamePlayer currentGamePlayer = gamePlayerService.playerList.get(gamePlayerService.choosePlayer);
        String currentPlayerName = currentGamePlayer.getPlayerName();
        if(gamePlayerService.choosePlayer.equals(0))
            currentPlayerName += " (Me)";
        return currentPlayerName;
    }
}
