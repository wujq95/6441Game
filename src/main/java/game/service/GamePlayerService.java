package service;

import model.Continent;
import model.Country;
import model.GamePlayer;
import observer.Observable;
import observer.Observer;
import strategy.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * service class to deal with player
 */
public class GamePlayerService extends Observable {

    FortifyService fortifyService = new FortifyService();
    public static List<GamePlayer> playerList = new ArrayList<GamePlayer>();
    public static Integer choosePlayer = 0;
    public static int checkPhase = 0;
    private Observer observer;

    public void setObserver(Observer obs) {
        observer = obs;
    }

    /**
     * Get random number for dice toll result
     *
     * @return Dice number
     */
    public Integer orderTurn() {
        double num;
        int result;
        num = Math.floor(Math.random() * 6 + 1);
        result = (int) num;
        return result;
    }

    /**
     * Assign dice number to each player
     *
     * @param player player instance
     */
    public void decideTurn(GamePlayer player) {
        for (GamePlayer players : playerList) {
            player.setNum(orderTurn());
        }
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i <= playerList.size(); i++) {
            order.add(player.getNum());
        }
        // Descending sort
        Collections.sort(order);
        Collections.reverse(order);
        for (int i = 0; i <= order.size(); i++) {
            System.out.println("Player " + playerList.get(i) + "'s result for dice toll is " + order.get(i));
        }
    }


    /**
     * check if player name is suitable for the game
     *
     * @return True or False
     */
    public boolean checkPlayerNum() {
        if (playerList.size() >= 2 && playerList.size() <= 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * add players to the playerlist and remove players from the playerlist
     *
     * @param arguments string
     * @return Message
     */
    public String gamePlayerAction(String[] arguments) {

        checkPhase = 1;

        List<String> addPlayerNameList = new ArrayList<String>();
        List<String> removePlayerNameList = new ArrayList<String>();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].startsWith("-add")) {
                addPlayerNameList.add(arguments[i + 1]);
            }
        }

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].startsWith("-remove")) {
                removePlayerNameList.add(arguments[i + 1]);
            }
        }

        boolean checkDuplicate = checkDuplicatePlayerName(addPlayerNameList);
        boolean checkIncluded = checkPlayerNameIncluded(removePlayerNameList);

        if (checkDuplicate) {
            return "player name duplicate";
        } else if (!checkIncluded) {
            return "player name can not be found";
        } else {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i].startsWith("-add")) {
                    if (arguments[i + 2].startsWith("human")) {
                        addPlayer(arguments[i + 1], null);
                    } else if (arguments[i + 2].startsWith("aggressive")) {
                        Strategy strategy = new AggressiveStrategy();
                        addPlayer(arguments[i + 1], strategy);
                    } else if (arguments[i + 2].startsWith("benevolent")) {
                        Strategy strategy = new BenevolentStrategy();
                        addPlayer(arguments[i + 1], strategy);
                    } else if (arguments[i + 2].startsWith("cheater")) {
                        Strategy strategy = new CheaterStrategy();
                        addPlayer(arguments[i + 1], strategy);
                    } else if (arguments[i + 2].startsWith("random")) {
                        Strategy strategy = new RandomStrategy();
                        addPlayer(arguments[i + 1], strategy);
                    }
                } else if (arguments[i].startsWith("-remove")) {
                    removePlayer(arguments[i + 1]);
                }
            }
            notifyObservers(this);
            return "gameplayer action success";
        }
    }

    /**
     * check if the player names that are added are duplicated
     *
     * @param playerNameList list
     * @return boolean
     */
    public boolean checkDuplicatePlayerName(List<String> playerNameList) {
        if (playerNameList.size() > 0) {
            boolean flagAll = false;

            for (int i = 0; i < playerNameList.size(); i++) {
                boolean flag = false;
                for (GamePlayer player : playerList) {
                    if (playerNameList.get(i).equals(player.getPlayerName())) {
                        flag = true;
                    }
                }
                if (flag) {
                    flagAll = true;
                }
            }
            return flagAll;
        } else {
            return false;
        }
    }

    /**
     * check player names that are removed can be found
     *
     * @param playerNameList list
     * @return boolean
     */
    public boolean checkPlayerNameIncluded(List<String> playerNameList) {

        if (playerNameList.size() > 0) {
            boolean flagAll = true;
            for (int i = 0; i < playerNameList.size(); i++) {
                boolean flag = false;
                for (GamePlayer player : playerList) {
                    if (playerNameList.get(i).equals(player.getPlayerName())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    flagAll = false;
                }
            }
            return flagAll;
        } else {
            return true;
        }
    }

    /**
     * add one player to the player list by player name
     *
     * @param playerName string
     */
    public void addPlayer(String playerName, Strategy strategy) {
        GamePlayer player = new GamePlayer();
        player.attach(observer);
        player.setStrategy(strategy);
        player.setPlayerName(playerName);
        player.setArmyValue(0);
        playerList.add(player);
        notifyObservers(this);
    }

    /**
     * remove one player from the player list by name
     *
     * @param playerName string
     */
    public void removePlayer(String playerName) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getPlayerName().equals(playerName)) {
                playerList.remove(i);
            }
        }
        notifyObservers(this);
    }

    /**
     * populate all countries to players
     *
     * @return string
     */
    public String populateCountries() {

        List<Country> countryList = MapEditorService.mapGraph.getCountryList();

        Integer playerNum = playerList.size();
        for (int i = 0; i < countryList.size(); i++) {
            List<Country> playerCountryList = playerList.get(i % playerNum).getCountryList();
            playerCountryList.add(countryList.get(i));
            playerList.get(i % playerNum).setCountryList(playerCountryList);
            MapEditorService.mapGraph.getCountryList().get(i).setPlayer(playerList.get(i % playerNum));
        }
        notifyObservers(this);
        return "populatecountries success and ";
    }

    /**
     * allocate initial armies for all players
     *
     * @return string
     */
    public String alloInitialArmy() {

        boolean flag = false;

        Integer playerNum = playerList.size();
        Integer initialArmies = 0;
        if (playerNum == 2) {
            initialArmies = 40;
        } else if (playerNum == 3) {
            initialArmies = 35;
        } else if (playerNum == 4) {
            initialArmies = 30;
        } else if (playerNum == 5) {
            initialArmies = 25;
        } else if (playerNum == 6) {
            initialArmies = 20;
        } else {
            initialArmies = 0;
            flag = true;
        }

        for (GamePlayer player : playerList) {
            player.setArmyValue(initialArmies);
        }
        for (GamePlayer player : playerList) {
            if (player.getArmyValue() > player.getCountryList().size()) {
                player.setArmyValue((player.getArmyValue() - player.getCountryList().size()));
                for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                    for (int j = 0; j < player.getCountryList().size(); j++) {
                        if ((player.getCountryList().get(j).getCountryName()).equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                            MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(1);
                        }
                    }
                }
            }

        }
        if (flag) {
            return "player number wrong!";
        } else {
            choosePlayer = 0;
            notifyObservers(this);
            return "allocate initial army success";
        }
    }

    /**
     * place one army from the player to a country
     *
     * @param countryName Country Name
     * @return Message
     */
    public String placeOneArmy(String countryName) {
        int flag = 0;
        for (int j = 0; j < playerList.get(choosePlayer).getCountryList().size(); j++) {
            if (countryName.equals(playerList.get(choosePlayer).getCountryList().get(j).getCountryName())) {
                flag = 1;
                Integer PlayerArmyValue = playerList.get(choosePlayer).getArmyValue();
                if (PlayerArmyValue == 0) {
                    flag = 2;
                } else {
                    for (Country country : MapEditorService.mapGraph.getCountryList()) {
                        if (country.getCountryName().equals(countryName)) {
                            Integer newCountryArmyValue = country.getArmyValue() + 1;
                            country.setArmyValue(newCountryArmyValue);
                        }
                    }
                    Integer newPlayerArmyValue = playerList.get(choosePlayer).getArmyValue() - 1;
                    playerList.get(choosePlayer).setArmyValue(newPlayerArmyValue);

                }
            }
        }
        if (flag == 0) {
            return "country name can not be found";
        } else if (flag == 2) {
            return "the army value of the player is not enough";
        } else {
            String result = changeIndexPlayer();
            ;
            notifyObservers(this);
            return result;
        }
    }

    /**
     * automatically place all armies to countries
     *
     * @return Message
     */
    public String placeAll() {
        for (GamePlayer player : playerList) {
            Integer remainPlayerArmyValue = player.getArmyValue();
            List<Country> playerCountryList = player.getCountryList();
            List<Country> mapCountryList = MapEditorService.mapGraph.getCountryList();
            if (remainPlayerArmyValue > 0) {
                for (int i = 0; i < remainPlayerArmyValue; i++) {
                    Integer index = (int) (Math.random() * playerCountryList.size());
                    for (int j = 0; j < mapCountryList.size(); j++) {
                        if ((playerCountryList.get(index).getCountryName()).equals(mapCountryList.get(j).getCountryName())) {
                            Integer newCountryArmyValue = mapCountryList.get(j).getArmyValue() + 1;
                            MapEditorService.mapGraph.getCountryList().get(j).setArmyValue(newCountryArmyValue);
                            player.setArmyValue(player.getArmyValue() - 1);
                        }
                    }
                }
            }
        }
        boolean flag = nextPhase();
        String result;
        if (flag) {
            checkPhase = 2;
            choosePlayer = 0;
            notifyObservers(this);
            result = "enter into the reinforcement phase";
        } else {
            result = "place all success!";
        }
        return result;
    }

    /**
     * calculate the army number at the beginning of the reinforce phase
     *
     * @return Message
     */
    public String calReinArmyNum() {

        GamePlayer player = playerList.get(choosePlayer);
        if (player.getArmyValue() == 0) {
            List<Country> countryList = player.getCountryList();
            Integer countryNum = (int) Math.floor(countryList.size() / 3);

            List<Continent> continentList = MapEditorService.mapGraph.getContinentList();
            Integer continentNum = 0;
            for (Continent continent : continentList) {
                boolean flag = true;
                for (Country country : continent.getCountries()) {
                    if (!country.getPlayer().getPlayerName().equals(player.getPlayerName())) {
                        flag = false;
                    }
                }
                if (flag) {
                    continentNum = continentNum + continent.getArmyValue();
                }
            }
            Integer newPlayerArmyValue = 0;
            if (continentNum > 0) {
                newPlayerArmyValue = player.getArmyValue() + continentNum;
                player.setArmyValue(newPlayerArmyValue);
                notifyObservers(this);
                return "calculate reinforce number success: " + newPlayerArmyValue + "\n"
                        + "continent value:" + continentNum + "\n";
            } else {
                newPlayerArmyValue = player.getArmyValue() + Math.max(countryNum, 3);
                player.setArmyValue(newPlayerArmyValue);
                notifyObservers(this);
                return "calculate reinforce number success: " + newPlayerArmyValue + "\n"
                        + "no continent value!" + "\n"
                        + "country number: round down(" + countryList.size() + "\\3)=" + countryNum + "\n"
                        + "normal addition:3\n";
            }
        } else {
            return "wrong syntax";
        }
    }

    /*
     * check if all armies have been put on the country
     * @return boolean

    public boolean checkPutAll(){

        boolean flag = true;

        for(GamePlayer player :playerList){
            if(player.getArmyValue()>0){
                flag =  false;
            }
        }
        return flag;
    }*/

    /**
     * change the index of the player
     *
     * @return message
     */
    public String changeIndexPlayer() {
        String result = "place one army success";
        choosePlayer++;
        if (choosePlayer == playerList.size()) {
            choosePlayer = 0;
        }
        if (playerList.get(choosePlayer).getArmyValue() == 0) {
            boolean flag = true;
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i).getArmyValue() > 0) {
                    flag = false;
                }
            }
            if (!flag) {
                changeIndexPlayer();
            } else {
                result = "enter into the reinforcement phase";
                choosePlayer = 0;
                checkPhase = 2;
            }
        }
        return result;
    }

    /**
     * check if player should enter next phase
     *
     * @return true or false
     */
    public boolean nextPhase() {
        boolean flag = false;
        if (playerList.get(GamePlayerService.choosePlayer).getArmyValue() == 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * Get player name in current phase
     *
     * @return player name
     */
    public String getCurrentPlayerName() {
        GamePlayer currentGamePlayer = playerList.get(choosePlayer);
        String currentPlayerName = currentGamePlayer.getPlayerName();
        if (choosePlayer.equals(0))
            currentPlayerName += " (Me)";
        currentPlayerName += " " + currentGamePlayer.getStrategyName();
        return currentPlayerName;
    }

    /**
     * Gey current player
     *
     * @return current player from player list
     */
    public GamePlayer getCurrentPlayer() {
        return GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
    }

    /**
     * update card information for player
     *
     * @param player player instance
     */
    public void updateGamePlayerCard(GamePlayer player) {
        GamePlayer gamePlayer = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        GamePlayerService.playerList.remove(gamePlayer);
        GamePlayerService.playerList.add(player);
    }

    public static GamePlayer findPlayerFromName(String name) {
        for (GamePlayer player : playerList) {
            if (name.equals(player.getPlayerName())) {
                return player;
            }
        }

        return null;
    }

    /**
     * change player and phase
     */
    public void changPlayer(){
        boolean flag = fortifyService.checkStop();
        if(flag){
            GamePlayerService.checkPhase = 2;
            GamePlayerService.choosePlayer=0;
        }else {
            GamePlayerService.checkPhase = 2;
            GamePlayerService.choosePlayer++;
        }
        notifyObservers(this);
    }
}
