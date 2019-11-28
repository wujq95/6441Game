package service;

import model.GamePlayer;
import observer.Observable;
import observer.Observer;
import strategy.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * tournament service
 */
public class TournamentService extends Observable {


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
        String result = "game result:\n";
        boolean flagT = true;
        try{
            List<String> mapFileList = new LinkedList<>();
            List<String> playerStrategyList = new LinkedList<>();
            GamePlayerService gamePlayerService = new GamePlayerService();
            gamePlayerService.setObserver(observer);
            Integer gameNumber =0;
            Integer maxTurnNumber = 0;
            Integer indexP =0;
            Integer indexG = 0;
            boolean flag = true;
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

            flagT = checkStrategyNumber(playerStrategyList);

            Integer mapNumber  = indexP-2;
            if(mapNumber<=0||mapNumber>5||mapNumber % 1 != 0){
                flagT = false;
            }
            if(gameNumber<=0||gameNumber>5||gameNumber % 1 != 0){
                flagT = false;
            }
            if(maxTurnNumber<10||maxTurnNumber>50||maxTurnNumber % 1 != 0){
                flagT = false;
            }
            if(flagT){
                for(int i=0;i<gameNumber;i++) {
                    for (int j = 0; j < mapFileList.size(); j++) {
                        result = result + "Game" + (i + 1);
                        result = result + " Map" + (j + 1);
                        mapEditorService.editMap(mapFileList.get(j));
                        for (int t = 0; t < playerStrategyList.size(); t++) {
                            String playerName = "test" + (t + 1);
                            if (playerStrategyList.get(t).equals("Aggressive")) {
                                Strategy strategy = new AggressiveStrategy();
                                gamePlayerService.addPlayer(playerName, strategy);
                            } else if (playerStrategyList.get(t).equals("Random")) {
                                Strategy strategy = new RandomStrategy();
                                gamePlayerService.addPlayer(playerName, strategy);
                            } else if (playerStrategyList.get(t).equals("Benevolent")) {
                                Strategy strategy = new BenevolentStrategy();
                                gamePlayerService.addPlayer(playerName, strategy);
                            } else if (playerStrategyList.get(t).equals("Cheater")) {
                                Strategy strategy = new CheaterStrategy();
                                gamePlayerService.addPlayer(playerName, strategy);
                            } else {
                                flag = false;
                            }
                        }
                        if (flag) {
                            gamePlayerService.populateCountries();
                            gamePlayerService.alloInitialArmy();
                            gamePlayerService.placeAll();

                            for (int p = 0; p < maxTurnNumber; p++) {
                                boolean flag3 = true;
                                while (flag3) {
                                    GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
                                    player.reinforce();
                                    Thread.sleep(25);
                                    player.attack();
                                    Thread.sleep(25);
                                    player.fortify();
                                    Thread.sleep(25);
                                    boolean flag2 = false;
                                    if (GamePlayerService.choosePlayer >= GamePlayerService.playerList.size() - 1) {
                                        flag2 = true;
                                    }
                                    if (flag2) {
                                        GamePlayerService.choosePlayer = 0;
                                        flag3 = false;
                                    } else {
                                        GamePlayerService.choosePlayer++;
                                    }
                                }
                                if (GamePlayerService.playerList.size() == 1) {
                                    break;
                                }
                            }
                            if (GamePlayerService.playerList.size() == 1) {
                                for (int p = 0; p < GamePlayerService.playerList.size(); p++) {
                                    result = result + " result: " + GamePlayerService.playerList.get(p).getStrategyName() + "\n";
                                }
                            } else {
                                result = result + " result: Draw\n";
                            }

                            for (int p = GamePlayerService.playerList.size() - 1; p >= 0; p--) {
                                GamePlayerService.playerList.remove(p);
                            }
                        }

                    }
                }
            }else{
                result = "parameter setting error";
            }
        }catch(Exception e){
            result = "wrong syntax";
        }
        return result;
    }

    /**
     * check if the number of strategy meets the requirement
     * @param aList Strategy List
     * @return boolean
     */
    public boolean checkStrategyNumber(List<String> aList){
        boolean flag = true;
        boolean aggressive = false;
        boolean cheater = false;
        boolean benevolent = false;
        boolean random = false;
        Integer result = 0;
        for(String s:aList){
            if(s.equals("Aggressive")){
                aggressive = true;
            }else if(s.equals("Random")){
                cheater = true;
            }else if(s.equals("Benevolent")){
                benevolent = true;
            }else if(s.equals("Cheater")){
                random = true;
            }
        }
        if(aggressive==true){
            result++;
        }
        if(random==true){
            result++;
        }
        if(benevolent==true){
            result++;
        }
        if(cheater==true){
            result++;
        }
        if(result<2){
            flag=false;
        }


        return  flag;
    }
}
