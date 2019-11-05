package service;

import controller.Observer;
import model.Country;
import model.GamePlayer;

import java.util.*;

/**
 * Attack Phase
 */
public class AttackService {

    public static String fromCountry;
    public static String toCountry;
    public static Integer fromDiceNum;
    public static Integer toDiceNum;
    public static List<Integer> fromDiceResultList = new LinkedList<>();
    public static List<Integer> toDiceResultList = new LinkedList<>();
    private CardService cardService = new CardService();
    static boolean ConqueredAtleastOneIntheturn = false;

    //observers list
    private List<controller.Observer> attackObservers = new ArrayList<>();

    /**
     * Add observer
     *
     * @param observer observer
     */
    public void attach(controller.Observer observer) {
        attackObservers.add(observer);
    }

    /**
     * Notify observers once changed
     */
    public void notifyObservers() {
        for (Observer observer : attackObservers) {
            observer.update();
        }
    }

    /**
     * attack information input
     *
     * @param arguments input command
     * @return Message
     */
    public String attack(String[] arguments) {

        String countryFrom = arguments[1];
        String countryTo = arguments[2];
        Double dNumFromDice = Double.parseDouble(arguments[3]);

        if (dNumFromDice % 1 != 0) {
            return "Dice Number must be an integer";
        } else {
            Integer numFromDice = Integer.parseInt(arguments[3]);
            if (numFromDice <= 0) {
                return "Dice Number can not be negative or zero";
            } else if (numFromDice > 3) {
                return "Dice Number can not be more than three";
            } else {
                boolean checkFromName = checkCountryName(countryFrom);
                boolean checkToName = checkCountryName(countryTo);
                boolean checkFromPlayer = checkFromPlayer(countryFrom);
                boolean checkToPlayer = checkFromPlayer(countryTo);
                boolean checkConnected = checkConnected(countryFrom, countryTo);
                boolean checkDiceNum = checkDiceNum(countryFrom, numFromDice);
                if (!checkFromName) {
                    return "from country name can not be found";
                } else if (!checkToName) {
                    return "to country name can not be found";
                } else if (!checkFromPlayer) {
                    return "from country must be from the real time player";
                } else if (checkToPlayer) {
                    return "from country and to country can not from the same player";
                } else if (!checkConnected) {
                    return "from country and to country must be connected";
                } else if (!checkDiceNum) {
                    return "incorrect attack dice number";
                } else {
                    fromCountry = countryFrom;
                    toCountry = countryTo;
                    fromDiceNum = numFromDice;
                    return "please enter the defender dive information";
                }
            }
        }
    }

    /**
     * attack until no attack is possible using maximum number of dice to attack/defend
     *
     * @param countryFrom input command
     * @param countryTo   input command
     * @return Message
     */
    public String allout(String countryFrom, String countryTo) {

        //check
        boolean checkFromName = checkCountryName(countryFrom);
        boolean checkToName = checkCountryName(countryTo);
        boolean checkFromPlayer = checkFromPlayer(countryFrom);
        boolean checkToPlayer = checkFromPlayer(countryTo);
        boolean checkConnected = checkConnected(countryFrom, countryTo);
        if (!checkFromName) {
            return "from country name can not be found";
        } else if (!checkToName) {
            return "to country name can not be found";
        } else if (!checkFromPlayer) {
            return "from country must be from the real time player";
        } else if (checkToPlayer) {
            return "from country and to country can not from the same player";
        } else if (!checkConnected) {
            return "from country and to country must be connected";
        } else {

            fromCountry = countryFrom;
            toCountry = countryTo;
            //get army value
            Integer fromCountryArmyValue = 1, toCountryArmyValue = 1;
            List<Country> countryList = MapEditorService.mapGraph.getCountryList();
            for (int i = 0, n = 0; i < countryList.size(); i++, n++) {
                if (countryList.get(i).getCountryName().equals(countryFrom)) {
                    fromCountryArmyValue = countryList.get(i).getArmyValue();
                }
                if (countryList.get(n).getCountryName().equals(countryTo)) {
                    toCountryArmyValue = countryList.get(n).getArmyValue();
                }
            }

            if (fromCountryArmyValue == 1) {
                return "cannot attack";
            } else {
                while (fromCountryArmyValue > 1 && toCountryArmyValue > 0) {
                    fromDiceNum = fromCountryMaxdice(fromCountryArmyValue);
                    toDiceNum = toCountryMaxdice(toCountryArmyValue);
                    attackProcess();
                    for (int i = 0, n = 0; i < countryList.size(); i++, n++) {
                        if (countryList.get(i).getCountryName().equals(countryFrom)) {
                            fromCountryArmyValue = countryList.get(i).getArmyValue();
                        }
                        if (countryList.get(n).getCountryName().equals(countryTo)) {
                            toCountryArmyValue = countryList.get(n).getArmyValue();
                        }
                    }
                }
                boolean flag = checkConquered();
                if (flag) {
                    Integer numOfDice = fromDiceResultList.size();
                    Integer fromArmyValue = checkArmyValueFromName(fromCountry);
                    if (fromArmyValue >= numOfDice + 2) {
                        changPlayer();
                        dealControllContinent();
                        notifyObservers();
                        return "please choose the number of moving army value";
                    } else {
                        moveArmy(fromArmyValue - 1);
                        changPlayer();
                        dealControllContinent();
                        notifyObservers();
                        return "attack and conquer success";
                    }
                } else {
                    notifyObservers();
                    return "allout process finished";
                }
            }
        }
    }

    /**
     * calculate maximal dice
     *
     * @param armyvalue number of armies
     * @return from country maximal dice
     */
    public Integer fromCountryMaxdice(Integer armyvalue) {
        if (armyvalue >= 4) {
            fromDiceNum = 3;
        } else if (armyvalue == 3) {
            fromDiceNum = 2;
        } else {
            fromDiceNum = 1;
        }
        return fromDiceNum;
    }

    /**
     * calculate maximal dice
     *
     * @param armyvalue number of armies
     * @return to country maximal dice
     */
    public int toCountryMaxdice(int armyvalue) {
        if (armyvalue >= 2) {
            toDiceNum = 2;
        } else {
            toDiceNum = 1;
        }
        return toDiceNum;
    }

    /**
     * check if input country name can be found
     *
     * @param countryName country name
     * @return boolean
     */
    public boolean checkCountryName(String countryName) {

        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        boolean flag = false;
        for (int i = 0; i < countryList.size(); i++) {
            if (countryName.equals(countryList.get(i).getCountryName())) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * check if the country is from the real time player
     *
     * @param countryName country name
     * @return true or false
     */
    public boolean checkFromPlayer(String countryName) {
        List<Country> countryList = GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList();
        System.out.println(GamePlayerService.choosePlayer);
        boolean flag = false;
        for (int i = 0; i < countryList.size(); i++) {
            if (countryName.equals(countryList.get(i).getCountryName())) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * check if fortify countries are connected
     *
     * @param countryFrom Initial Army Moving Country Name
     * @param countryTo   Goal Army Moving Country Name
     * @return True or False
     */
    public boolean checkConnected(String countryFrom, String countryTo) {
        boolean flag = false;
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (countryFrom.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                Set<Country> countryList = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it = countryList.iterator();
                while (it.hasNext()) {
                    Country cc = (Country) it.next();
                    if (cc.getCountryName().equals(countryTo)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * check if attack dice number is correct
     *
     * @param fromCountryName country name
     * @param num             dice number
     * @return true or false
     */
    public boolean checkDiceNum(String fromCountryName, Integer num) {

        Integer remainArmyNum = 0;
        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        for (int i = 0; i < countryList.size(); i++) {
            if (fromCountryName.equals(countryList.get(i).getCountryName())) {
                remainArmyNum = countryList.get(i).getArmyValue();
            }
        }
        if (remainArmyNum == 1) {
            return false;
        } else if (remainArmyNum == 2) {
            if (num == 1) {
                return true;
            } else {
                return false;
            }
        } else if (remainArmyNum == 3) {
            if (num == 1 || num == 2) {
                return true;
            } else {
                return false;
            }
        } else if (remainArmyNum >= 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * defend dive information input
     *
     * @param defendNum dice number
     * @return message
     */
    public String defend(String defendNum) {
        String countryName = toCountry;
        Double dNumDefend = Double.parseDouble(defendNum);
        if (dNumDefend % 1 != 0) {
            return "Dice Number must be an integer";
        } else {
            Integer numDefend = Integer.parseInt(defendNum);
            if (numDefend <= 0) {
                return "Dice Number can not be negative or zero";
            } else if (numDefend > 2) {
                return "Dice Number can not more than 2";
            } else {
                boolean checkDice = checkDefendDice(countryName, numDefend);
                if (checkDice) {
                    toDiceNum = numDefend;
                    attackProcess();
                    boolean flag = checkConquered();
                    if (flag) {
                        Integer numOfDice = fromDiceResultList.size();
                        Integer fromArmyValue = checkArmyValueFromName(fromCountry);
                        if (fromArmyValue >= numOfDice + 2) {
                            changPlayer();
                            dealControllContinent();
                            notifyObservers();
                            return "please choose the number of moving army value";
                        } else {
                            moveArmy(fromArmyValue - 1);
                            changPlayer();
                            dealControllContinent();
                            notifyObservers();
                            return "attack and conquer success";
                        }
                    } else {
                        notifyObservers();
                        return "attack process finished";
                    }
                } else {
                    return "incorrect defend dice number";
                }
            }
        }

    }

    /**
     * check defend dice number
     *
     * @param countryName country name
     * @param numDefend   dice number
     * @return true or false
     */
    public boolean checkDefendDice(String countryName, Integer numDefend) {
        Integer remainArmyNum = 0;
        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        for (int i = 0; i < countryList.size(); i++) {
            if (countryName.equals(countryList.get(i).getCountryName())) {
                remainArmyNum = countryList.get(i).getArmyValue();
            }
        }
        if (remainArmyNum == 1) {
            if (numDefend == 1) {
                return true;
            } else {
                return false;
            }
        } else if (remainArmyNum >= 2) {
            if (numDefend == 1 || numDefend == 2) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * attack process
     */
    public void attackProcess() {

        Integer[] fromDiceArray = new Integer[fromDiceNum];
        Integer[] toDiceArray = new Integer[toDiceNum];

        for (int i = 0; i < fromDiceNum; i++) {
            fromDiceArray[i] = ((int) (Math.random() * 6 + 1));
        }

        for (int i = 0; i < toDiceNum; i++) {
            toDiceArray[i] = (int) (Math.random() * 6 + 1);
        }

        Arrays.sort(fromDiceArray, Collections.reverseOrder());
        Arrays.sort(toDiceArray, Collections.reverseOrder());

        ArrayList<Integer> fromDiceList = new ArrayList<Integer>(Arrays.asList(fromDiceArray));
        ArrayList<Integer> toDiceList = new ArrayList<Integer>(Arrays.asList(toDiceArray));

        if (fromDiceList.size() == 1) {
            if (toDiceList.size() == 1) {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            } else {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            }
        } else if (fromDiceList.size() == 2) {
            if (toDiceList.size() == 1) {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            } else {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
                if (fromDiceList.get(1) > toDiceList.get(1)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            }
        } else {
            if (toDiceList.size() == 1) {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            } else {
                if (fromDiceList.get(0) > toDiceList.get(0)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
                if (fromDiceList.get(1) > toDiceList.get(1)) {
                    deleteOneArmy(toCountry);
                } else {
                    deleteOneArmy(fromCountry);
                }
            }
        }
        fromDiceResultList = fromDiceList;
        toDiceResultList = toDiceList;
    }

    /**
     * change the owner of the country after conquering
     */
    public void changPlayer() {
        GamePlayer oldFromPlayer = null;
        GamePlayer oldToPlayer = null;
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                oldFromPlayer = MapEditorService.mapGraph.getCountryList().get(i).getPlayer();
            }
        }
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                oldToPlayer = MapEditorService.mapGraph.getCountryList().get(i).getPlayer();
                MapEditorService.mapGraph.getCountryList().get(i).setPlayer(oldFromPlayer);
            }
        }

        Country country = null;
        for (int i = 0; i < GamePlayerService.playerList.size(); i++) {
            if (oldToPlayer.getPlayerName().equals(GamePlayerService.playerList.get(i).getPlayerName())) {
                for (int j = 0; j < GamePlayerService.playerList.get(i).getCountryList().size(); j++) {
                    if (toCountry.equals(GamePlayerService.playerList.get(i).getCountryList().get(j).getCountryName())) {
                        country = GamePlayerService.playerList.get(i).getCountryList().get(j);
                        GamePlayerService.playerList.get(i).getCountryList().remove(j);
                    }
                }
            }
        }

        for (int i = 0; i < GamePlayerService.playerList.size(); i++) {
            if (oldFromPlayer.getPlayerName().equals(GamePlayerService.playerList.get(i).getPlayerName())) {
                GamePlayerService.playerList.get(i).getCountryList().add(country);
            }
        }

    }

    /**
     * Move army
     *
     * @param armyNum number of armies player want to move
     */
    public void moveArmy(Integer armyNum) {
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(1);
            }
        }
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(armyNum);
            }
        }
    }

    /**
     * get the army value of the country according to the country name
     *
     * @param name player name
     * @return number of army
     */
    public Integer checkArmyValueFromName(String name) {
        Integer result = 0;
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (name.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                result = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
            }
        }
        return result;
    }


    /**
     * check if the country has been conquered
     *
     * @return true or false
     */
    public boolean checkConquered() {
        boolean flag2 = false;
        boolean flag = false;
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                if (MapEditorService.mapGraph.getCountryList().get(i).getArmyValue() == 0) {
                    flag = true;
                }
            }
        }
        if (flag) {
            flag2 = checkConquerAll();
            ConqueredAtleastOneIntheturn = true;
        }

        return flag;
    }

    /**
     * check if all countries of a player have been conquered
     *
     * @return true or false
     */
    public boolean checkConquerAll() {
        boolean flag = false;
        for (int i = 0; i < GamePlayerService.playerList.size(); i++) {
            for (int j = 0; j < GamePlayerService.playerList.get(i).getCountryList().size(); j++) {
                if (toCountry.equals(GamePlayerService.playerList.get(i).getCountryList().get(j).getCountryName())) {
                    if (GamePlayerService.playerList.get(i).getCountryList().size() == 1) {
                        flag = true;
                        cardService.rewardCardAfterConquerLastCountry(GamePlayerService.playerList.get(i));
                    }
                }
            }
        }

        return flag;
    }

    /**
     * delete one army if loosing the dice competition
     *
     * @param countryName country name
     */
    public void deleteOneArmy(String countryName) {
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (countryName.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                Integer newArmy = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue() - 1;
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newArmy);
            }
        }
    }

    /**
     * choose to stop attacking
     *
     * @return message
     */
    public String noAttack() {
        GamePlayerService.checkPhase = 3;
        notifyObservers();
        return "enter into fortification phase";
    }

    /**
     * if conquering one country,player could move some armies to that country
     *
     * @param num number of armies
     * @return message
     */
    public String attackMove(String num) {

        Double dArmyNum = Double.valueOf(num);
        Integer ArmyNum = Integer.parseInt(num);
        Integer numOfDice = fromDiceResultList.size();
        boolean flag = checkMoveArmy(ArmyNum);

        if (dArmyNum % 1 != 0) {
            return "Dice Number must be an integer";
        } else if (ArmyNum < 0) {
            return "attack move number can be negative";
        } else if (ArmyNum < numOfDice) {
            return "attack move number can not be less than the number of dice";
        } else if (!flag) {
            return "incorrect army number";
        } else {
            for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                    MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(ArmyNum);
                }
            }
            for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
                if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                    Integer remainArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
                    MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(remainArmyValue - ArmyNum);
                }
            }
            changPlayer();
            notifyObservers();
            return "attack move success";
        }

    }

    /**
     * check if army of moving is suitable
     *
     * @return true or false
     */
    public boolean checkMoveArmy(Integer num) {

        Integer remainArmyValue = 0;
        boolean flag = true;

        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (fromCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                remainArmyValue = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue();
            }
        }
        if (num > remainArmyValue - 1) {
            flag = false;
        }
        return flag;
    }

    /**
     * check if the player control all countries in a continent
     */
    public void dealControllContinent() {
        String continentName = "";
        for (int i = 0; i < MapEditorService.mapGraph.getCountryList().size(); i++) {
            if (toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())) {
                continentName = MapEditorService.mapGraph.getCountryList().get(i).getParentContinent().getContinentName();
            }
        }

        for (int i = 0; i < MapEditorService.mapGraph.getContinentList().size(); i++) {
            if (continentName.equals(MapEditorService.mapGraph.getContinentList().get(i).getContinentName())) {
                List<Country> countryList = MapEditorService.mapGraph.getContinentList().get(i).getCountries();
                boolean flag = true;
                for (Country country : countryList) {
                    for (int j = 0; j < MapEditorService.mapGraph.getCountryList().size(); j++) {
                        if (country.getCountryName().equals(MapEditorService.mapGraph.getCountryList().get(j).getCountryName())) {
                            if (!MapEditorService.mapGraph.getCountryList().get(j).getPlayer().getPlayerName().equals(GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getPlayerName())) {
                                flag = false;
                            }
                        }
                    }
                }
                if (flag) {
                    List<String> continentNameList = GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getControlledContinent();
                    continentNameList.add(continentName);
                    GamePlayerService.playerList.get(GamePlayerService.choosePlayer).setControlledContinent(continentNameList);
                }
            }
        }
    }

    /**
     * Get dice toll result
     *
     * @return dice number
     */
    public String getFromDice() {
        return fromDiceResultList.toString();
    }

    /**
     * Get dice toll result
     *
     * @return dice number
     */
    public String getToDice() {
        return toDiceResultList.toString();
    }
}
