package service;

import model.Country;

import java.util.List;

/**
 * service class for fortify phase
 */
public class FortifyService {

    static Integer playerNum = 0;
    /**
     * Fortify Action
     * @param fromCountry string
     * @param toCountry string
     * @param num string
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
                }
            }
            if (flag==0){
                return "from tountry name can not be found";
            }else if(flag==1){
                return "to Country name can not be found";
            }else if(flag==3){
                return "the army value of the form country is not enough";
            }else {
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
        checkStop();
        return "fortify none success";
    }

    public boolean checkStop(){
        boolean flag=false;
        if(playerNum>=GamePlayerService.playerList.size()){
            flag=true;
        }
        return flag;
    }

    /**
     * check the country if from which player
     * @param countryName
     * @return Integer
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
}
