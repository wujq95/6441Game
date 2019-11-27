package strategy;

import model.Continent;
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
 * Cheater Strategy class
 */
public class CheaterStrategy implements Strategy{
    /**
     * Initial attack service object
     */
    AttackService attackService = new AttackService();

    /**
     * Attack Method
     */
    @Override
    public void attack() {
        GamePlayer player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        List<Country> countryList = player.getCountryList();

        List<Country> a = new LinkedList<>();
        for(int i=0;i<countryList.size();i++){
            a.add(countryList.get(i));
        }
        for (int i=0;i<a.size();i++){
            Country country  = countryList.get(i);
            List<Integer> indexList = findEnemy(country);
            for(int j=0;j<indexList.size();j++){
                removeCountryFromPlayer(MapEditorService.mapGraph.getCountryList().get(indexList.get(j)));
                MapEditorService.mapGraph.getCountryList().get(indexList.get(j)).setPlayer(player);
                GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().add(MapEditorService.mapGraph.getCountryList().get(indexList.get(j)));
            }
        }
        attackService.dealControllContinent();
        attackService.deletePlayer();
    }

    /**
     * Reinforce Method
     */
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

    /**
     * Fortify method
     */
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
    }

    /**
     * check if the country has a neighbor which is from another player
     * @param country country object
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


    /**
     * find neighbor index list
     * @param country country object
     * @return index list
     */
    public List<Integer> findEnemy(Country country){
        List<Integer> indexList = new LinkedList<>();
        for (int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Set<Country> countryNeighbors = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it  = countryNeighbors.iterator();
                while (it.hasNext()){
                    Country countryNeighbor = (Country) it.next();
                    for (int j=0;j<MapEditorService.mapGraph.getCountryList().size();j++){
                        if(countryNeighbor.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())){
                            if(!MapEditorService.mapGraph.getCountryList().get(j).getPlayer().getPlayerName().equals(MapEditorService.mapGraph.getCountryList().get(i).getPlayer().getPlayerName())){
                                indexList.add(j);
                            }
                        }
                    }
                }
            }
        }
        return indexList;
    }

    /**
     * remove the country from his old owner
     * @param country country object
     */
    public void removeCountryFromPlayer(Country country){
        Integer index=-1;
        GamePlayer player = country.getPlayer();
        for(int i=0;i<GamePlayerService.playerList.size();i++){
            if(player.getPlayerName().equals(GamePlayerService.playerList.get(i).getPlayerName())){
                index = i;
            }
        }
        for(int i=0;i<GamePlayerService.playerList.get(index).getCountryList().size();i++){
            if(country.getCountryName().equals(GamePlayerService.playerList.get(index).getCountryList().get(i).getCountryName())){
                GamePlayerService.playerList.get(index).getCountryList().remove(i);
            }
        }
    }
}
