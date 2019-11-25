package strategy;

import model.Country;
import model.GamePlayer;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class CheaterStrategy implements Strategy{
    @Override
    public void attack() {
        GamePlayer player=GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        /*for(int i=0;i< MapEditorService.mapGraph.getCountryList().size();i++){
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
        }*/
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


    /**
     * find neighbor index list
     * @param country
     * @return
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
     * @param country
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
