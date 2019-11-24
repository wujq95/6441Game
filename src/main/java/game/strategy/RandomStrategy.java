package strategy;

import model.Country;
import model.GamePlayer;
import service.AttackService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RandomStrategy implements Strategy{
    @Override
    public void attack() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        AttackService attackService = new AttackService();
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy();
        List<Country> countryList = player.getCountryList();
        while(countryList.size()>0) {
            Integer randomIndex = (int) (Math.random() * (countryList.size())) + 1;
            Country country=null;
            for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                if (countryList.get(randomIndex).getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                    country = MapEditorService.mapGraph.getCountryList().get(i);
                }
            }
            Set<Country> countryNeighborsList = country.getNeighbours();
            Iterator it = countryNeighborsList.iterator();
            boolean flag  =true;
            while (it.hasNext()) {
                if(flag){
                    Integer index=0;
                    Integer neighborIndex = 0;
                    Country countryNeighbor = (Country)it.next();
                    if(player.getPlayerName().equals(countryNeighbor.getPlayer().getPlayerName())){
                        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                            if (country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                                index = i;
                            }
                        }
                        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                            if (countryNeighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                                neighborIndex = i;
                            }
                        }
                        Integer attackTimes = (int)(Math.random()*(MapEditorService.mapGraph.getCountryList().get(index).getArmyValue()-1))+1;
                        for(int i=0;i<attackTimes;i++){
                            if(MapEditorService.mapGraph.getCountryList().get(neighborIndex).getArmyValue()>0){
                                AttackService.fromDiceNum = attackService.fromCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(index).getArmyValue());
                                AttackService.toDiceNum = attackService.toCountryMaxdice(MapEditorService.mapGraph.getCountryList().get(neighborIndex).getArmyValue());
                                AttackService.fromCountry = country.getCountryName();
                                AttackService.toCountry = countryNeighbor.getCountryName();
                                attackService.attackProcess();
                            }
                        }
                        if(MapEditorService.mapGraph.getCountryList().get(neighborIndex).getArmyValue()==0){
                               aggressiveStrategy.moveArmy(1,country.getCountryName(),countryNeighbor.getCountryName());
                        }
                        flag =false;
                    }
                }
            }
            if(flag){
                countryList.remove(randomIndex);
            }else{
                countryList.clear();
            }
        }
        System.out.println("Attacking randomly...");
    }

    @Override
    public void reinforce() {
        GamePlayerService gamePlayerService = new GamePlayerService();
        gamePlayerService.calReinArmyNum();
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Integer randomIndex = (int)(Math.random()*(player.getCountryList().size()));
        Country country  = player.getCountryList().get(randomIndex);
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Integer oldArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(oldArmyValue+player.getArmyValue());
            }
        }
        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(0);
        System.out.println("Reinforcing randomly...");
    }

    @Override
    public void fortify() {
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Integer fromRandomIndex = (int)(Math.random()*(player.getCountryList().size()))+1;
        Integer toRandomIndex = (int)(Math.random()*(player.getCountryList().size()))+1;
        Country fromCountry  = player.getCountryList().get(fromRandomIndex);
        Country toCountry  = player.getCountryList().get(toRandomIndex);
        Integer fromCountryArmyValue = fromCountry.getArmyValue();
        Integer armyValueMovement = (int)(Math.random()*(fromCountryArmyValue-1))+1;
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(fromCountry.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(fromCountry.getArmyValue()-armyValueMovement);
            }
        }
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(toCountry.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(toCountry.getArmyValue()+armyValueMovement);
            }
        }
        System.out.println("Fortifying randomly...");
    }


}
