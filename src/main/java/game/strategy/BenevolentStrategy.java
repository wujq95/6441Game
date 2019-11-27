package strategy;

import model.Country;
import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Benevolent Strategy
 */
public class BenevolentStrategy implements Strategy {
    /**
     * initial object and variable
     */
    RandomStrategy randomStrategy = new RandomStrategy();
    Integer fromIndex = 0;
    Integer toIndex = 0;

    /**
     * attack method
     */
    @Override
    public void attack() {
    }

    /**
     * reinforce method
     */
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
    }

    /**
     * fortification method
     */
    @Override
    public void fortify() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);

        boolean flag = true;
        List<Country> indexList = new LinkedList<>();
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName().equals(player.getPlayerName())){
                indexList.add(MapEditorService.mapGraph.getCountryList().get(i));
            }
        }

        Integer enemyIndex =-1;
        Integer fromIndex = -1;
        Integer enemyArmyValue = 0;
        while (flag){
            Integer index=0;
            Integer armyFlag = indexList.get(0).getArmyValue();
            for(int i=0;i<indexList.size();i++){
                if(indexList.get(i).getArmyValue()<armyFlag){
                    index = i;
                    armyFlag =indexList.get(i).getArmyValue();
                }
            }
            Set<Country> countryNeighbors = indexList.get(index).getNeighbours();
            Iterator it = countryNeighbors.iterator();

            while (it.hasNext()){
                Country neighbor = (Country) it.next();
                if(neighbor.getPlayer().getPlayerName().equals(indexList.get(index).getPlayer().getPlayerName())){
                    if(neighbor.getArmyValue()>enemyArmyValue){
                        enemyArmyValue=neighbor.getArmyValue();
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(neighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                enemyIndex =i;
                            }
                        }
                        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                            if(indexList.get(index).getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                                fromIndex =i;
                            }
                        }
                    }

                }
            }
            if(enemyArmyValue==0){
                indexList= removeList(indexList,index);
                if(indexList.size()==0){
                    flag=false;
                }
            }else{
                flag=false;
            }
        }

        if(enemyIndex!=-1){
            Integer oldFromArmyValue = MapEditorService.mapGraph.getCountryList().get(fromIndex).getArmyValue();
            Integer oldToArmyValue = MapEditorService.mapGraph.getCountryList().get(enemyIndex).getArmyValue();
            MapEditorService.mapGraph.getCountryList().get(fromIndex).setArmyValue(1);
            MapEditorService.mapGraph.getCountryList().get(enemyIndex).setArmyValue(oldFromArmyValue-1+oldToArmyValue);
        }
        /*Integer flag =-1;
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
        }*/
    }


    /**
     * look for the country which has the least army value
     * @return country element of index
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

    /**
     * remove country list
     * @param countryList country list
     * @param index integer number
     * @return new country list
     */
    public List<Country> removeList(List<Country> countryList,Integer index){
        List<Country> countryList1 = new LinkedList<>();
        for(int i=0;i<countryList.size();i++){
            if(i!=index){
                countryList1.add(countryList.get(i));
            }
        }
        return countryList1;
    }
}

