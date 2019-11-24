package strategy;

import model.Country;
import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.Set;


public class CheaterStrategy implements Strategy{
    @Override
    public void attack() {
        GamePlayer player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(player.getPlayerName()==MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName()){
                Set<Country> countryList = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it = countryList.iterator();
                while(it.hasNext()){
                    Country country = (Country) it.next();
                    //MapEditorService.mapGraph.getCountryList().get(i).getNeighbours().remove(country);
                    //country.setPlayer(player);
                    //MapEditorService.mapGraph.getCountryList().get(i).getNeighbours().add(country);
                    String countryName = country.getCountryName();
                    for(int j=0;j< MapEditorService.mapGraph.getCountryList().size();j++){
                        if(countryName.equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())){
                            GamePlayer playerOwner = MapEditorService.mapGraph.getCountryList().get(j).getPlayer();
                            if(!playerOwner.getPlayerName().equals(player.getPlayerName())){
                                for(int t=0;t<GamePlayerService.playerList.size();t++){
                                    if(GamePlayerService.playerList.get(t).getPlayerName().equals(playerOwner.getPlayerName())){
                                        for(int p=0;p<GamePlayerService.playerList.get(t).getCountryList().size();p++){
                                            if(GamePlayerService.playerList.get(t).getCountryList().get(p).getCountryName().equals(countryName)){
                                                GamePlayerService.playerList.get(t).getCountryList().remove(p);
                                            }
                                        }
                                    }
                                }
                                GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().add(country);
                            }
                        }
                    }

                }
            }
        }

        System.out.println("Attacking in cheater mode...");
    }

    @Override
    public void reinforce() {
        GamePlayer  player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
            if(player.getPlayerName()==MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName()){
                Integer oldArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                Integer newArmyValue = oldArmyValue*2;
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newArmyValue);
            }
        }
    }

    @Override
    public void fortify() {
        GamePlayer  player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        for(int i=0;i<player.getCountryList().size();i++){
            Country country = player.getCountryList().get(i);
            boolean flag = checkNeighbor(country);
            if(flag){
               Integer oldArmyValue =  player.getCountryList().get(i).getArmyValue();
               Integer newArmyValue = oldArmyValue*2;
               player.getCountryList().get(i).setArmyValue(newArmyValue);
            }
        }
        System.out.println("Fortifying in cheater mode...");
    }

    /**
     * check if the country has a neighbor which is from another player
     * @param country
     * @return boolean
     */
    public boolean checkNeighbor(Country country){
        GamePlayer player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        boolean flag = false;
        Set<Country> countrySet =country.getNeighbours();
        Iterator it = countrySet.iterator();
        while (it.hasNext()){
            Country countryNext = (Country)it.next();
            if(!player.getPlayerName().equals(countryNext.getPlayer().getPlayerName())){
                flag=true;
            }
        }
        return flag;
    }
}
