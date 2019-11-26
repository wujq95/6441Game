package service;

import model.GamePlayer;
import observer.Observable;
import observer.Observer;
import strategy.*;

import java.util.LinkedList;
import java.util.List;

public class TournamentService extends Observable {
    //check if command is right
    //check String and number are right
    //检查策略种类个数符合要求,策略拼写正确

    private Observer observer;

    public void setObserver(Observer obs) {
        observer = obs;
    }

    MapEditorService mapEditorService = new MapEditorService();

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
        String result = "game result:";
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
                        for(GamePlayer player:GamePlayerService.playerList){
                            player.reinforce();
                            player.attack();
                            player.fortify();
                        }
                    }
                    for(int p=0;p<GamePlayerService.playerList.size();p++){
                        result = result+"  player"+(i+1)+":"+GamePlayerService.playerList.get(i).getStrategyName()+"\n";
                    }
                    for(int p=GamePlayerService.playerList.size()-1;i>=0;i--){
                        GamePlayerService.playerList.remove(p);
                    }
                }
            }
        }
        return result;
    }
}
