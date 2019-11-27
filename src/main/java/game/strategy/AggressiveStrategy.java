package strategy;

import javafx.scene.shape.MoveToBuilder;
import model.Country;
import model.GamePlayer;
import service.AttackService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Aggressive Strategy class
 */
public class AggressiveStrategy implements Strategy {
    /**
     * initial object and variable
     */
    static Integer countryFlag = 0;
    GamePlayerService gamePlayerService = new GamePlayerService();
    AttackService attackService =new AttackService();
    CheaterStrategy cheaterStrategy = new CheaterStrategy();

    /**
     * attack method
     */
    @Override
    public void attack() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        List<Country> countryList =player.getCountryList();
        List<Country> indexList =new LinkedList<>();

        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(player.getPlayerName().equals(MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName())){
                indexList.add(MapEditorService.mapGraph.getCountryList().get(i));
            }
        }

        boolean flag = true;
        while(flag){
            boolean flag2 = true;
            Integer index = -1;
            for(int i=0;i<indexList.size();i++){
                Country country = indexList.get(i);
                if(country.getArmyValue()>1){
                    index = i;
                }
                /*for(int j=0;j<MapEditorService.mapGraph.getCountryList().size();j++){
                    if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())){
                        if(MapEditorService.mapGraph.getCountryList().get(j).getArmyValue()>1){
                            index = i;
                        }
                    }
                }*/
            }
            if(index==-1){
                flag=false;
            }else{
                indexList = attackNeighbor(indexList.get(index),indexList);
                for(int i=0;i<indexList.size();i++){
                    Country country = indexList.get(i);
                    if(country.getArmyValue()>1){
                        flag2=false;
                    }
                    /*for(int j=0;j<MapEditorService.mapGraph.getCountryList().size();j++){
                        if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())){
                            if(MapEditorService.mapGraph.getCountryList().get(j).getArmyValue()>1){
                                flag2 = false;
                            }
                        }
                    }*/
                }
                if(flag2){
                    flag = false;
                }
            }
        }
    }

    /**
     * reinforce method
     */
    @Override
    public void reinforce() {
        countryFlag=-1;
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        while(countryFlag==-1){
            Integer k = (int)(Math.random()*(MapEditorService.mapGraph.getCountryList().size()));
            if(MapEditorService.mapGraph.getCountryList().get(k).getPlayer().getPlayerName().equals(player.getPlayerName())){
                countryFlag = k;
            }
        }
        gamePlayerService.calReinArmyNum();

        Integer armyValue = player.getArmyValue();
        Integer armyFlag = MapEditorService.mapGraph.getCountryList().get(0).getArmyValue();

        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName().equals(player.getPlayerName())){
                if(MapEditorService.mapGraph.getCountryList().get(i).getArmyValue()>armyFlag){
                    armyFlag = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                    countryFlag = i;
                }
            }
        }
        MapEditorService.mapGraph.getCountryList().get(countryFlag).setArmyValue(armyFlag+armyValue);
        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(0);
    }

    /**
     * fortify method
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
                if(indexList.get(i).getArmyValue()>armyFlag){
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
        /*Integer fromCountryIndex = 0;
        Integer toCountryIndex = 0;
        Integer maxArmyValue = 0;
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        for(int i=0;i<player.getCountryList().size();i++){
            for(int j=0;j<MapEditorService.mapGraph.getCountryList().size();j++){
                if(player.getCountryList().get(i).getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())){
                    Set<Country> countrySet = MapEditorService.mapGraph.getCountryList().get(j).getNeighbours();
                    Iterator it  = countrySet.iterator();
                    while (it.hasNext()){
                        Country countryNeighbor = (Country) it.next();
                        if(countryNeighbor.getPlayer().getPlayerName().equals(player.getPlayerName())){
                            Integer fromCountryValue = MapEditorService.mapGraph.getCountryList().get(j).getArmyValue();
                            Integer toCountryValue = countryNeighbor.getArmyValue();
                            if((fromCountryValue+toCountryValue)>maxArmyValue){
                                maxArmyValue = fromCountryValue+toCountryValue;
                                fromCountryIndex = j;
                                for(int t=0;t<MapEditorService.mapGraph.getCountryList().size();t++){
                                    if(countryNeighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(t).getCountryName())){
                                        toCountryIndex = t;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        fortifyArmy(fromCountryIndex,toCountryIndex);*/
    }

    /**
     * move one army to new country if having conquered the country
     * @param armyValue number of army
     * @param fromCountry from country
     * @param toCountry to country
     */
    public void moveArmy(Integer armyValue, String fromCountry, String toCountry){
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                Integer oldArmy =  MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(oldArmy-armyValue);
            }
        }
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(armyValue);
            }
        }
    }

    /**
     * fortify armies in order to maximize aggregation
     * @param fromCountryIndex from country index of list
     * @param toCountryIndex to country index of list
     */
    public void fortifyArmy(Integer fromCountryIndex,Integer toCountryIndex){
        Integer fromArmyValue = MapEditorService.mapGraph.getCountryList().get(fromCountryIndex).getArmyValue();
        MapEditorService.mapGraph.getCountryList().get(fromCountryIndex).setArmyValue(1);
        Integer toArmyValue = MapEditorService.mapGraph.getCountryList().get(toCountryIndex).getArmyValue();
        MapEditorService.mapGraph.getCountryList().get(toCountryIndex).setArmyValue(toArmyValue+fromArmyValue-1);
    }


    /**
     * attack the neighbor of country
     * @param country country object
     * @param countryList country list
     * @return country list
     */
    public List<Country> attackNeighbor(Country country, List<Country> countryList){
        Integer index = -1;
        Integer enemyIndex = -1;
        boolean flag = false;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                index = i;
            }
        }
        Set<Country> countryNeighbor = MapEditorService.mapGraph.getCountryList().get(index).getNeighbours();
        Iterator it = countryNeighbor.iterator();
        while (it.hasNext()){
            Country neighbor = (Country)it.next();
            if(!neighbor.getPlayer().getPlayerName().equals(country.getPlayer().getPlayerName())){
                flag = true;
                for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                    if(neighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                        enemyIndex = i;
                    }
                }
            }
        }
        if(flag) {
            while (MapEditorService.mapGraph.getCountryList().get(index).getArmyValue() > 1 && MapEditorService.mapGraph.getCountryList().get(enemyIndex).getArmyValue() > 0) {
                AttackService.fromCountry = country.getCountryName();
                AttackService.toCountry = MapEditorService.mapGraph.getCountryList().get(enemyIndex).getCountryName();
                AttackService.fromDiceNum = attackService.fromCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(index).getArmyValue());
                AttackService.toDiceNum = attackService.toCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(enemyIndex).getArmyValue());
                attackService.attackProcess();
            }
            boolean flag2 = attackService.checkConquered();
            if (flag2) {
                cheaterStrategy.removeCountryFromPlayer(MapEditorService.mapGraph.getCountryList().get(enemyIndex));
                MapEditorService.mapGraph.getCountryList().get(enemyIndex).setPlayer(GamePlayerService.playerList.get(GamePlayerService.choosePlayer));
                GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().add(MapEditorService.mapGraph.getCountryList().get(enemyIndex));
                Integer armyLeft = MapEditorService.mapGraph.getCountryList().get(index).getArmyValue();
                moveArmy(armyLeft-1, MapEditorService.mapGraph.getCountryList().get(index).getCountryName(), MapEditorService.mapGraph.getCountryList().get(enemyIndex).getCountryName());
                attackService.dealControllContinent();
                attackService.deletePlayer();
                for(int i=0;i<countryList.size();i++){
                    if(country.getCountryName().equals(countryList.get(i).getCountryName())){
                        countryList.remove(i);
                        countryList.add(MapEditorService.mapGraph.getCountryList().get(enemyIndex));
                    }
                }
            }else{
                for(int i=0;i<countryList.size();i++){
                    if(country.getCountryName().equals(countryList.get(i).getCountryName())){
                        countryList.get(i).setArmyValue(1);
                    }
                }
            }
        }else {
            for(int i=0;i<countryList.size();i++){
                if(country.getCountryName().equals(countryList.get(i).getCountryName())){
                    countryList.get(i).setArmyValue(1);
                }
            }
        }
        return countryList;
    }

    /**
     * Remove country
     * @param countryList country list
     * @param index country element index
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

