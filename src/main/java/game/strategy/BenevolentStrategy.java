package strategy;

import model.Country;
import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.List;

public class BenevolentStrategy implements Strategy {

    Integer countryFlag = 0;
    RandomStrategy randomStrategy = new RandomStrategy();
    Integer fromIndex = 0;
    Integer toIndex = 0;

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
        Integer flag =-1;
        Integer index =-1;
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        List<Country> countryList = player.getCountryList();
        for(int i=0;i<countryList.size();i++){
            Country country = countryList.get(i);
            flag = randomStrategy.findFriend(country);
            if(flag!=-1){
                fromIndex = flag;
                index = i;
                break;
            }
        }
        if(flag!=-1){
            Country country  =player.getCountryList().get(index);
            for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                    toIndex = i;
                }
            }
            Integer formArmyValue = MapEditorService.mapGraph.getCountryList().get(fromIndex).getArmyValue();
            Integer toArmyValue = MapEditorService.mapGraph.getCountryList().get(toIndex).getArmyValue();
            Integer armyMovement = formArmyValue-(toArmyValue+formArmyValue)/2;
            MapEditorService.mapGraph.getCountryList().get(fromIndex).setArmyValue(formArmyValue-armyMovement);
            MapEditorService.mapGraph.getCountryList().get(toIndex).setArmyValue(toArmyValue+armyMovement);
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

