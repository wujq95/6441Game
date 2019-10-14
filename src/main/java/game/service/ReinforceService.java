package service;

import model.Country;
import model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReinforceService {

    /**
     * Reinforce Phase Action
     * @param player
     * @param countryName
     * @param num
     * @return
     */
    public String reinforce(Player player,String countryName, String num) {

        Integer reinArmyVlaue = Integer.parseInt(num);
        Integer countryArmyValue = player.getNumOfArmies();

        //check the army value of the player is enough to reinforce
        if (countryArmyValue < reinArmyVlaue) {
            return "the army value of the player is not enough!";
        } else {
            List<Country> countryList = MapEditorService.mapGraph.getCountryList();
            boolean flag = false;
            for (int i = 0; i < countryList.size(); i++) {
                if (countryList.get(i).equals(countryName)) {
                    Integer newCountryArmyValue = countryList.get(i).getArmyValue()+reinArmyVlaue;
                    MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newCountryArmyValue);
                    player.setNumOfArmies(countryArmyValue - reinArmyVlaue);
                    flag = true;
                }
            }
            if (flag) {
                return "reinforce success";
            } else {
                return "country name can not be found";
            }
        }
    }
}

