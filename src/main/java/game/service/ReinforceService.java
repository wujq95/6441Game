package service;

import controller.Observer;
import model.Country;
import model.GamePlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * service class for reinforce phase
 */
public class ReinforceService {
    // Initiate object to be used later conveniently
    GamePlayerService gamePlayerService = new GamePlayerService();

    //observers list
    private List<Observer> reinforceInfoObservers= new ArrayList<>();

    /**
     * add observer
     * @param observer observer
     */
    public void attach(controller.Observer observer){
        reinforceInfoObservers.add(observer);
    }

    /**
     * Notify observers once changed
     */
    public void notifyObservers(){
        for (Observer observer : reinforceInfoObservers) {
            observer.update();
        }
    }

    /**
     * Reinforce Phase Action
     * @param countryName country name
     * @param num Reinforced Army Value
     * @return Message
     */
    public String reinforce(String countryName, String num) {
        Integer reinArmyValue = Integer.parseInt(num);
        if (reinArmyValue<0){
            return "reinforce number can be negative";
        }else {
            int flag = 0;
            for (int j = 0; j < GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().size(); j++) {
                if ((countryName).equals(GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().get(j).getCountryName())) {
                    flag = 1;
                    Integer playerArmyValue = GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getArmyValue();
                    if (playerArmyValue < reinArmyValue) {
                        flag = 2;
                    } else {
                        for (Country country : MapEditorService.mapGraph.getCountryList()) {
                            if ((countryName).equals(country.getCountryName())) {
                                Integer newCountryArmyValue = country.getArmyValue() + reinArmyValue;
                                country.setArmyValue(newCountryArmyValue);
                            }
                        }
                        Integer newPlayerArmyValue = playerArmyValue - reinArmyValue;
                        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(newPlayerArmyValue);
                    }
                }
            }

            if (flag == 0) {
                return "country name can not be found";
            } else if (flag == 2) {
                return "the army value of the player is not enough";
            } else {
                boolean flag2 = checkNextPhase();
                if(flag2){
                    GamePlayerService.checkPhase=4;
                    notifyObservers();
                    return "enter into the attack phase";
                }else{
                    notifyObservers();
                    return "reinforce success";
                }
            }
        }
    }

    /**
     * check if one player have put all his armies to the countries
     */
    public void checkPutAll(){
        int m = GamePlayerService.choosePlayer;
        int t= m;
        if(GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getArmyValue()==0){
            t=GamePlayerService.choosePlayer+1;
        }
        GamePlayerService.choosePlayer=t;
    }

    /**
     * check if player should enter next phase
     * @return true or false
     */
    public boolean checkNextPhase(){
        boolean flag = false;
        if(GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getArmyValue()==0){
            flag  =true;
        }
        return flag;
    }

    /**
     * Obtain player name in current phase
     * @return player name
     */
    public String getCurrentPlayerName(){
        GamePlayer currentGamePlayer = gamePlayerService.playerList.get(gamePlayerService.choosePlayer);
        String currentPlayerName = currentGamePlayer.getPlayerName();
        if(gamePlayerService.choosePlayer.equals(0))
            currentPlayerName += " (Me)";
        return currentPlayerName;
    }
}

