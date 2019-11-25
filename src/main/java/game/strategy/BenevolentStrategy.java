package strategy;

import model.Country;
import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BenevolentStrategy implements Strategy {

    Integer countryFlag = 0;

    @Override
    public void attack() {

    }

    @Override
    public void reinforce() {
        GamePlayerService gamePlayerService = new GamePlayerService();
        gamePlayerService.calReinArmyNum();
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);

        Integer armyValue = player.getArmyValue();
        Integer countryFlag = checkWeakestCountry(MapEditorService.mapGraph.getCountryList());
        Integer armyFlag = MapEditorService.mapGraph.getCountryList().get(countryFlag).getArmyValue();

        MapEditorService.mapGraph.getCountryList().get(countryFlag).setArmyValue(armyFlag+armyValue);
        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(0);
        System.out.println("Reinforcing benevolently...");
    }

    @Override
    public void fortify() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        List<Country> countryList = player.getCountryList();
        List<Country> countryCopy = countryList;
        while(countryList.size()>0){
            countryFlag  = checkWeakestCountry(countryList);
            Country country = countryList.get(countryFlag);
            Set<Country> countryNeighborSet = country.getNeighbours();
            Iterator it = countryNeighborSet.iterator();
            boolean flag  =true;
            while (it.hasNext()){
                if(flag){
                    Country countryNeighbor = (Country)it.next();
                    if(player.getPlayerName().equals(countryNeighbor.getPlayer().getPlayerName())){
                        Integer toArmyValue = 0;
                        Integer formArmyValue = 0;
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                toArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                            }
                        }
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(countryNeighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                formArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                            }
                        }
                        Integer armyMovement = (toArmyValue+formArmyValue)/2;
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(toArmyValue+armyMovement);
                            }
                        }
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(countryNeighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(formArmyValue-armyMovement);
                            }
                        }
                        flag = false;
                    }
                }
            }
            if(flag){
                countryCopy.remove(0);
                countryList = countryCopy;
            }else{
                for(int i=countryList.size()-1;i>=0;i--){
                    countryList.remove(i);
                }
            }
        }
    }


    /**
     * look for the country which has the least army value
     * @return
     */
    public Integer checkWeakestCountry(List<Country> countryList){
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Integer armyFlag = countryList.get(0).getArmyValue();
        Integer countryFlag = 0;
        for(int i=0;i< countryList.size();i++){
            if(countryList.get(i).getPlayer().getPlayerName().equals(player.getPlayerName())){
                if(countryList.get(i).getArmyValue()<armyFlag){
                    armyFlag = countryList.get(i).getArmyValue();
                    countryFlag = i;
                }
            }
        }
        return countryFlag;
    }
}

