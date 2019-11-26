package strategy;

import model.Country;
import model.GamePlayer;
import service.AttackService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.*;

public class RandomStrategy implements Strategy{
    @Override
    public void attack() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        AttackService attackService = new AttackService();
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy();
        Integer enemyIndex = 0 ;
        Integer index = 0;
        Integer index2 = 0;

        List<Integer> a = new LinkedList<>();
        for(int i=0;i<player.getCountryList().size();i++){
            a.add(i);
        }

        Collections.shuffle(a);
        List<Country> countryList = player.getCountryList();
        for(int i=0;i<a.size();i++){
            Country country = countryList.get(a.get(i));
            Integer flag = findEnemy(country);
            if(flag!=-1){
                index = a.get(i);
                enemyIndex = flag;
                break;
            }
        }

        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(player.getCountryList().get(index).getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                index2 = i;
            }
        }

        Integer attackTimes = (int)(Math.random()*(MapEditorService.mapGraph.getCountryList().get(index2).getArmyValue()-1))+1;
        for(int i=0;i<attackTimes;i++){
            if(MapEditorService.mapGraph.getCountryList().get(enemyIndex).getArmyValue()>0){
                AttackService.fromDiceNum = attackService.fromCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(index2).getArmyValue());
                AttackService.toDiceNum = attackService.toCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(enemyIndex).getArmyValue());
                AttackService.fromCountry = MapEditorService.mapGraph.getCountryList().get(index2).getCountryName();
                AttackService.toCountry = MapEditorService.mapGraph.getCountryList().get(enemyIndex).getCountryName();
                attackService.attackProcess();
                boolean flag = attackService.checkConquered();
                if(flag){
                    aggressiveStrategy.moveArmy(1,MapEditorService.mapGraph.getCountryList().get(index2).getCountryName(),MapEditorService.mapGraph.getCountryList().get(enemyIndex).getCountryName());
                    attackService.dealControllContinent();
                    attackService.deletePlayer();
                    break;
                }
            }
        }
    }

    @Override
    public void reinforce() {
        GamePlayerService gamePlayerService = new GamePlayerService();
        gamePlayerService.calReinArmyNum();
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Integer  p =player.getCountryList().size();
        Integer randomIndex = (int)(Math.random()*(p));
        Country country  = player.getCountryList().get(randomIndex);
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Integer oldArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(oldArmyValue+player.getArmyValue());
            }
        }
        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(0);
    }

    @Override
    public void fortify() {
        Integer flag = -1;
        Integer index = 0;
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        List<Integer> a = new LinkedList<>();
        for(int i=0;i<player.getCountryList().size();i++){
            a.add(i);
        }
        Collections.shuffle(a);
        for(int i=0;i<a.size();i++){
            Country fromCountry = player.getCountryList().get(a.get(i));
            flag  = findFriend(fromCountry);
            if(flag!=-1){
                index = a.get(i);
                break;
            }
        }
        Integer fromIndex=0;

        Country fromCountry = player.getCountryList().get(index);
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(fromCountry.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                fromIndex=i;
            }
        }
        if(flag!=-1){
            Country toCountry  =MapEditorService.mapGraph.getCountryList().get(flag);
            Integer fromCountryArmyValue = MapEditorService.mapGraph.getCountryList().get(fromIndex).getArmyValue();
            Integer armyValueMovement = (int)(Math.random()*(fromCountryArmyValue-1))+1;
            MapEditorService.mapGraph.getCountryList().get(fromIndex).setArmyValue(fromCountry.getArmyValue()-armyValueMovement);
            MapEditorService.mapGraph.getCountryList().get(flag).setArmyValue(toCountry.getArmyValue()+armyValueMovement);

        }




    }

    /**
     * find the index of enemy country
     * @param country
     * @return
     */
    public Integer findFriend(Country country){
        Integer flag = -1;
        Integer index=0;
        String fromPlayerName =null;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                index = i;
                fromPlayerName = MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName();
            }
        }
        Set<Country> countryNeighborList = MapEditorService.mapGraph.getCountryList().get(index).getNeighbours();
        Iterator it = countryNeighborList.iterator();
        while (it.hasNext()){
            Country neighbor = (Country) it.next();
            if(fromPlayerName.equals(neighbor.getPlayer().getPlayerName())){
                for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                    if(neighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                        flag  =i;
                    }
                }
            }
        }
        return  flag;
    }

    /**
     * find the index of enemy country
     * @param country
     * @return
     */
    public Integer findEnemy(Country country){
        Integer flag = -1;
        Integer index=0;
        String fromPlayerName =null;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                index = i;
                fromPlayerName = MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName();
            }
        }
        Set<Country> countryNeighborList = MapEditorService.mapGraph.getCountryList().get(index).getNeighbours();
        Iterator it = countryNeighborList.iterator();
        while (it.hasNext()){
            Country neighbor = (Country) it.next();
            if(!fromPlayerName.equals(neighbor.getPlayer().getPlayerName())){
                for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                    if(neighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                        flag  =i;
                    }
                }
            }
        }
        return  flag;
    }

}
