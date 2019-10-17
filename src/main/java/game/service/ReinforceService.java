package service;

import model.Country;


/**
 * service class for reinforce phase
 */
public class ReinforceService {

    /**
     * Reinforce Phase Action
     * @param countryName
     * @param num
     * @return
     */
    public String reinforce(String countryName, String num) {

        int flag = 0;

        for(int i=0;i<GamePlayerService.playerList.size();i++){
            for(int j=0;j<GamePlayerService.playerList.get(i).getCountryList().size();j++){
                if((countryName).equals(GamePlayerService.playerList.get(i).getCountryList().get(j).getCountryName())){
                    flag =1;
                    Integer reinArmyValue = Integer.parseInt(num);
                    Integer playerArmyValue = GamePlayerService.playerList.get(i).getArmyValue();
                    if (playerArmyValue < reinArmyValue) {
                        flag =2;
                    }else{
                        for(Country country:MapEditorService.mapGraph.getCountryList()){
                            if((countryName).equals(country.getCountryName())){
                                Integer newCountryArmyValue = country.getArmyValue()+reinArmyValue;
                                country.setArmyValue(newCountryArmyValue);
                            }
                        }
                        Integer newPlayerArmyValue = playerArmyValue-reinArmyValue;
                        GamePlayerService.playerList.get(i).setArmyValue(newPlayerArmyValue);
                        //Integer newCountryArmyValue = GamePlayerService.playerList.get(i).getCountryList().get(j).getArmyValue()+reinArmyValue;
                        //GamePlayerService.playerList.get(i).getCountryList().get(j).setArmyValue(newCountryArmyValue);
                    }
                }
            }
        }

        if(flag==0){
            return "country name can not be found";
        }else if(flag==2){
            return "the army value of the player is not enough";
        }else{
            return "reinforce success";
        }

    }
}

