package service;

import model.Country;
import model.GamePlayer;
import observer.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * service class for fortify phase
 */
public class FortifyService extends Observable {

    /**
     * Fortify Action
     *
     * @param fromCountry From Country Name
     * @param toCountry   To Country Name
     * @param num         Number
     * @return Message
     */
    public String fortify(String fromCountry, String toCountry, String num) {
        Integer fortifyArmyValue = Integer.parseInt(num);
        if (fortifyArmyValue < 0) {
            return "fortify num can be negative";
        } else {
            boolean flag1 = checkPlayer(fromCountry);
            boolean flag2 = checkPlayer(toCountry);

            List<Country> countryList = MapEditorService.mapGraph.getCountryList();

            int flag = 0;
            if (flag1) {
                flag = 1;
                if (flag2) {
                    flag = 2;
                    boolean flag3 = checkConnected(fromCountry, toCountry);
                    if (flag3) {
                        for (int i = 0; i < countryList.size(); i++) {
                            if (toCountry.equals(countryList.get(i).getCountryName())) {
                                for (int j = 0; j < countryList.size(); j++) {
                                    if (fromCountry.equals(countryList.get(j).getCountryName())) {
                                        if (countryList.get(j).getArmyValue() < fortifyArmyValue + 1) {
                                            flag = 3;
                                        } else {
                                            Integer newFromCountry = countryList.get(j).getArmyValue() - fortifyArmyValue;
                                            Integer newToCountry = countryList.get(i).getArmyValue() + fortifyArmyValue;
                                            MapEditorService.mapGraph.getCountryList().get(j).setArmyValue(newFromCountry);
                                            MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newToCountry);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        flag = 4;
                    }
                }
            }
            if (flag == 0) {
                return "from Country name can not be found";
            } else if (flag == 1) {
                return "to Country name can not be found";
            } else if (flag == 3) {
                return "the army value of the form country is not enough";
            } else if (flag == 4) {
                return "two countries are not connected";
            } else {
                boolean flag3 = checkStop();
                if (flag3) {
                    GamePlayerService.checkPhase = 2;
                    GamePlayerService.choosePlayer=0;
                    notifyObservers(this);
                    return "fortify success and enter into the reinforcement phase for the next player";
                } else {
                    GamePlayerService.checkPhase = 2;
                    GamePlayerService.choosePlayer++;
                    notifyObservers(this);
                    return "fortify success and enter into the reinforcement phase for the next player";
                }
            }
        }
    }

    /**
     * No fortify
     *
     * @return message
     */
    public String fortifyNone() {

        boolean flag = checkStop();
        if (flag) {
            GamePlayerService.checkPhase = 2;
            GamePlayerService.choosePlayer=0;
            notifyObservers(this);
            return "fortify none success and enter into the reinforcement phase for the next player";
        } else {
            GamePlayerService.checkPhase = 2;
            GamePlayerService.choosePlayer++;
            notifyObservers(this);
            return "fortify none success and enter into the reinforcement phase for the next player";
        }
    }

    /**
     * Check player's turn
     *
     * @return true or false
     */
    public boolean checkStop() {
        boolean flag = false;
        if (GamePlayerService.choosePlayer >= GamePlayerService.playerList.size() - 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * check the country if from which player
     *
     * @param countryName country name
     * @return True of False
     */
    public boolean checkPlayer(String countryName) {
        boolean flag = false;
        for (int j = 0; j < GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().size(); j++) {
            if (countryName.equals(GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList().get(j).getCountryName())) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * check if fortify countries are connected
     *
     * @param fromCountry Initial Army Moving Country Name
     * @param toCountry   Goal Army Moving Country Name
     * @return True or False
     */
    public boolean checkConnected(String fromCountry, String toCountry) {
        boolean flag = false;
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                Set<Country> countryList = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it = countryList.iterator();
                while (it.hasNext()) {
                    Country cc = (Country) it.next();
                    if (cc.getCountryName().equals(toCountry)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * get the name of the current player
     * @return current palyer name
     */
    public String getCurrentPlayerName() {
        GamePlayer currentGamePlayer = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
        String currentPlayerName = currentGamePlayer.getPlayerName();
        if (GamePlayerService.choosePlayer.equals(0))
            currentPlayerName += " (Me)";
        return currentPlayerName;
    }

    /**
     * stop game
     * @return message
     */
    public String stop(){
        GamePlayerService.checkPhase=5;
        notifyObservers(this);
        return "game stop";
    }
}
