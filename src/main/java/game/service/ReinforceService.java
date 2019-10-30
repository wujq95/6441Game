package service;

import model.Country;
import model.GamePlayer;


/**
 * service class for reinforce phase
 */
public class ReinforceService {
    // Initiate object to be used later conveniently
    GamePlayerService gamePlayerService = new GamePlayerService();
    public static Integer playerNum = 0;

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
            for (int j = 0; j < GamePlayerService.playerList.get(playerNum).getCountryList().size(); j++) {
                if ((countryName).equals(GamePlayerService.playerList.get(playerNum).getCountryList().get(j).getCountryName())) {
                    flag = 1;
                    Integer playerArmyValue = GamePlayerService.playerList.get(playerNum).getArmyValue();
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
                        GamePlayerService.playerList.get(playerNum).setArmyValue(newPlayerArmyValue);
                    }
                }
            }

            if (flag == 0) {
                return "country name can not be found";
            } else if (flag == 2) {
                return "the army value of the player is not enough";
            } else {
                checkPutAll();
                checkNextPhase();
                return "reinforce success";
            }
        }
    }

    /**
     * check if one player have put all his armies to the countries
     */
    public void checkPutAll(){
        int m = playerNum;
        int t= m;
        if(GamePlayerService.playerList.get(playerNum).getArmyValue()==0){
            t=playerNum+1;
        }
        playerNum=t;
    }

    /**
     * check if player should enter next phase
     *
     */
    public void checkNextPhase(){
        if(playerNum>=GamePlayerService.playerList.size()){
            GamePlayerService.checkPhase=3;
        }
    }

    public String getCurrentPlayerName(){
        GamePlayer currentGamePlayer = gamePlayerService.playerList.get(gamePlayerService.choosePlayer);
        String currentPlayerName = currentGamePlayer.getPlayerName();
        if(gamePlayerService.choosePlayer.equals(0))
            currentPlayerName += " (Me)";
        return currentPlayerName;
    }
}

