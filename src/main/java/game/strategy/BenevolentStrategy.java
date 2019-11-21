package strategy;

import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

public class BenevolentStrategy implements Strategy {
    @Override
    public void attack() {
        System.out.println("Attacking benevolently...");
    }

    @Override
    public void reinforce() {
        GamePlayerService gamePlayerService = new GamePlayerService();
        gamePlayerService.calReinArmyNum();
        GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        Integer armyValue = player.getArmyValue();
        Integer armyFlag = MapEditorService.mapGraph.getCountryList().get(0).getArmyValue();
        Integer countryFlag = 0;
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName().equals(player.getPlayerName())){
                if(MapEditorService.mapGraph.getCountryList().get(i).getArmyValue()<armyFlag){
                    armyFlag = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                    countryFlag = i;
                }
            }
        }

        MapEditorService.mapGraph.getCountryList().get(countryFlag).setArmyValue(armyFlag+armyValue);
        GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setArmyValue(0);
        System.out.println("Reinforcing benevolently...");
    }

    @Override
    public void fortify() {

        System.out.println("Fortifying benevolently...");
    }
}

