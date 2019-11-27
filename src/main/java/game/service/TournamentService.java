package service;

import model.GamePlayer;
import observer.Observable;
import observer.Observer;
import strategy.*;

import java.util.LinkedList;
import java.util.List;

/**
 * tournament service
 */
public class TournamentService extends Observable {
    //check if command is right
    //check String and number are right
    //检查策略种类个数符合要求,策略拼写正确


    private Observer observer;

    /**
     * set Observer
     * @param obs observer object
     */
    public void setObserver(Observer obs) {
        observer = obs;
    }

    /**
     * initial map editor service object
     */
    MapEditorService mapEditorService = new MapEditorService();

    /**
     * tournament mode
     * @param arguments player list
     * @return result
     */
    public String tournament(String[] arguments){
        List<String> mapFileList = new LinkedList<>();
        List<String> playerStrategyList = new LinkedList<>();
        GamePlayerService gamePlayerService = new GamePlayerService();
        gamePlayerService.setObserver(observer);
        Integer gameNumber =0;
        Integer maxTurnNumber = 0;
        Integer indexP =0;
        Integer indexG = 0;
        boolean flag = true;
        String result = "game result:\n";
        for(int i=0;i<arguments.length;i++){
            if(arguments[i].equals("-P")){
                indexP = i;
            }
            if(arguments[i].equals("-G")){
                indexG = i;
            }
        }
        for(int i=2;i<indexP;i++){
            mapFileList.add(arguments[i]);
        }
        for(int i=indexP+1;i<indexG;i++){
            playerStrategyList.add(arguments[i]);
        }
        gameNumber = Integer.parseInt(arguments[indexG+1]);
        maxTurnNumber = Integer.parseInt(arguments[indexG+3]);

        Integer index = 1;
        for(int i=0;i<gameNumber;i++){
            for(int j=0;j<mapFileList.size();j++){
                mapEditorService.editMap(mapFileList.get(j));
                for(int t=0;t<playerStrategyList.size();t++){
                    String playerName = "test"+(t+1);
                    if(playerStrategyList.get(t).equals("Aggressive")){
                        Strategy strategy = new AggressiveStrategy();
                        gamePlayerService.addPlayer(playerName,strategy);
                    }else if(playerStrategyList.get(t).equals("Random")){
                        Strategy strategy = new RandomStrategy();
                        gamePlayerService.addPlayer(playerName,strategy);
                    }else if(playerStrategyList.get(t).equals("Benevolent")){
                        Strategy strategy = new BenevolentStrategy();
                        gamePlayerService.addPlayer(playerName,strategy);
                    }else if(playerStrategyList.get(t).equals("Cheater")){
                        Strategy strategy = new CheaterStrategy();
                        gamePlayerService.addPlayer(playerName,strategy);
                    }else{
                        flag = false;
                    }
                }
                if(flag){
                    gamePlayerService.populateCountries();
                    gamePlayerService.alloInitialArmy();
                    gamePlayerService.placeAll();

                    for(int p=0;p<maxTurnNumber;p++){
                        boolean flag3 = true;
                        while(flag3){
                            GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
                            player.reinforce();
                            player.attack();
                            player.fortify();
                            boolean flag2 = false;
                            if (GamePlayerService.choosePlayer >= GamePlayerService.playerList.size() - 1) {
                                flag2 = true;
                            }
                            if(flag2){
                                GamePlayerService.choosePlayer=0;
                                flag3 = false;
                            }else{
                                GamePlayerService.choosePlayer++;
                            }
                        }
                        if(GamePlayerService.playerList.size()==1){
                            break;
                        }
                    }
                    if(GamePlayerService.playerList.size()==1){
                        for(int p=0;p<GamePlayerService.playerList.size();p++){
                            result = result+"game"+index+" result:"+GamePlayerService.playerList.get(p).getStrategyName()+"\n";
                        }
                    }else {
                        result = result+"game"+index+" result:Draw\n";
                    }

                    for(int p=GamePlayerService.playerList.size()-1;p>=0;p--){
                        GamePlayerService.playerList.remove(p);
                    }
                }
                index++;
            }
        }
        return result;
    }
}
