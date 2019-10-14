package service;

import model.Country;

import java.util.List;

public class FortifyService {

    /**
     * Fortify Action
     * @param fromCountry
     * @param toCountry
     * @param num
     * @return
     */
    public String fortify(String fromCountry, String toCountry, String num){

        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        Integer fortifyArmyValue = Integer.parseInt(num);

        int flag = 0;
        for(int i=0;i<countryList.size();i++){
            if(countryList.get(i).getCountryName().equals(toCountry)) {
                flag = 1;
                for (int j = 0; j < countryList.size(); j++) {
                    if (countryList.get(j).getCountryName().equals(fromCountry)) {
                        flag = 2;
                        if (countryList.get(j).getArmyValue() < fortifyArmyValue) {
                            flag = 3;
                        } else {
                            Integer newFromCountry = countryList.get(j).getArmyValue() - fortifyArmyValue;
                            Integer newToCountry =countryList.get(i).getArmyValue() + fortifyArmyValue;
                            MapEditorService.mapGraph.getCountryList().get(j).setArmyValue(newFromCountry);
                            MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newToCountry);
                        }
                    }
                }
            }
        }

        if (flag==0){
            return "to tountry name can not be found";
        }else if(flag==1){
            return "from Country name can not be found";
        }else if(flag==3){
            return "the army value of the form country is not enough";
        }else{
            return "fortify success";
        }
    }
}
