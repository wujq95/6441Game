package strategy;

import model.Country;
import model.GamePlayer;
import service.AttackService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.Set;

public class AggressiveStrategy implements Strategy {

    static Integer countryFlag = 0;
    GamePlayerService gamePlayerService = new GamePlayerService();
    AttackService attackService =new AttackService();
    @Override
    public void attack() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Country country  = MapEditorService.mapGraph.getCountryList().get(countryFlag);
        Iterator it = country.getNeighbours().iterator();
        while (it.hasNext()){
            Country countryNeighbor = (Country)it.next();
            if(!countryNeighbor.getPlayer().getPlayerName().equals(player.getPlayerName())){
                while(country.getArmyValue()>1&&countryNeighbor.getArmyValue()>0){
                    AttackService.fromCountry = country.getCountryName();
                    AttackService.toCountry = countryNeighbor.getCountryName();
                    AttackService.fromDiceNum = attackService.fromCountryMaxdice(country.getArmyValue());
                    AttackService.toDiceNum = attackService.toCountryMaxdice(countryNeighbor.getArmyValue());
                    attackService.attackProcess();
                    for (int i = 0, n = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++, n++) {
                        if (MapEditorService.mapGraph.getCountryList().get(i).getCountryName().equals(country.getCountryName())) {
                            country.setArmyValue(MapEditorService.mapGraph.getCountryList().get(i).getArmyValue());
                        }
                        if (MapEditorService.mapGraph.getCountryList().get(n).getCountryName().equals(countryNeighbor.getCountryName())) {
                            countryNeighbor.setArmyValue(MapEditorService.mapGraph.getCountryList().get(n).getArmyValue());
                        }
                    }
                }
                boolean flag = attackService.checkConquered();
                if(flag){
                    moveArmy(1,country.getCountryName(),countryNeighbor.getCountryName());
                    attackService.changPlayer();
                    attackService.dealControllContinent();
                    attackService.deletePlayer();
                    attackService.checkStop();
                    /**
                     * notifyObservers();
                     *
                     *if (conqueredAll) {
                     *    conqueredAll = false;
                     *    return "you have get the other player's cards, please choose the number of moving army value";
                     *} else {
                     *     return "please choose the number of moving army value";
                     *}
                     */
                }
            }
        }

        System.out.println("Attacking aggressively...");
    }

    @Override
    public void reinforce() {
        gamePlayerService.calReinArmyNum();
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
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

        System.out.println("Reinforcing aggressively...");
    }

    @Override
    public void fortify() {
        Integer fromCountryIndex = 0;
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
        fortifyArmy(fromCountryIndex,toCountryIndex);
        System.out.println("Fortifying aggressively...");
    }

    /**
     * move one army to new country if having conquered the country
     * @param armyValue
     * @param fromCountry
     * @param toCountry
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
     * @param fromCountryIndex
     * @param toCountryIndex
     */
    public void fortifyArmy(Integer fromCountryIndex,Integer toCountryIndex){
        Integer fromArmyValue = MapEditorService.mapGraph.getCountryList().get(fromCountryIndex).getArmyValue();
        MapEditorService.mapGraph.getCountryList().get(fromCountryIndex).setArmyValue(1);
        Integer toArmyValue = MapEditorService.mapGraph.getCountryList().get(toCountryIndex).getArmyValue();
        MapEditorService.mapGraph.getCountryList().get(fromCountryIndex).setArmyValue(toArmyValue+fromArmyValue-1);
    }
}
