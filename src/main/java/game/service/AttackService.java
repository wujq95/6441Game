package service;

import controller.Observer;
import model.Country;

import java.util.*;

public class AttackService {

    public static String fromCountry;
    public static String toCountry;
    public static Integer fromDiceNum;
    public static Integer toDiceNum;
    public static List<Integer> fromDiceResultList=new LinkedList<>();
    public static List<Integer> toDiceResultList=new LinkedList<>();

    //observers list
    private List<controller.Observer> attackObservers = new ArrayList<>();

    public void attach(controller.Observer observer){
        attackObservers.add(observer);
    }

    public void notifyObservers(){
        for (Observer observer : attackObservers) {
            observer.update();
        }
    }

    /**
     * attack information input
     * @param arguments
     * @return
     */
    public String attack(String[] arguments){

        String countryFrom  = arguments[1];
        String countryTo = arguments[2];
        Double dNumFromDice = Double.parseDouble(arguments[3]);

        if(dNumFromDice%1!=0){
            return "Dice Number must be an integer";
        }else{
            Integer numFromDice = Integer.parseInt(arguments[3]);
            if(numFromDice<=0){
                return "Dice Number can not be negative or zero";
            }else if(numFromDice>3){
                return "Dice Number can not be more than three";
            }else{
                boolean checkFromName = checkCountryName(countryFrom);
                boolean checkToName = checkCountryName(countryTo);
                boolean checkFromPlayer = checkFromPlayer(countryFrom);
                boolean checkToPlayer = checkFromPlayer(countryTo);
                boolean checkConnected = checkConnected(countryFrom,countryTo);
                boolean checkDiceNum = checkDiceNum(countryFrom,numFromDice);
                if(!checkFromName){
                    return "from country name can not be found";
                }else if(!checkToName){
                    return "to country name can not be found";
                }else if(!checkFromPlayer){
                    return "from country must be from the real time player";
                }else if(checkToPlayer){
                    return "from country and to country can not from the same player";
                }else if(!checkConnected){
                    return "from country and to country must be connected";
                }else if(!checkDiceNum){
                    return "incorrect attack dice number";
                }else {
                    fromCountry = countryFrom;
                    toCountry = countryTo;
                    fromDiceNum = numFromDice;
                    return "please enter the defender dive information";
                }
            }
        }
    }

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
                fromCountry = countryFrom;
                toCountry = countryTo;
                //get maximal dice
                fromDiceNum = fromCountryMaxdice(fromCountryArmyValue);
                toDiceNum = toCountryMaxdice(toCountryArmyValue);

                String result=alloutProcess();
                return result;

                }
            }
        }


    public int fromCountryMaxdice(int armyvalue){
        if(armyvalue>=4) {
            fromDiceNum = 3;
        }else if(armyvalue==3){
            fromDiceNum = 2;
        }else{
            fromDiceNum = 1;
        }
        return fromDiceNum;
    }

    public int toCountryMaxdice(int armyvalue){
        if(armyvalue>=2) {
            toDiceNum = 2;
        }else{
            toDiceNum = 1;
        }
        return toDiceNum;
    }

    /**
     * check if input country name can be found
     * @param countryName
     * @return boolean
     */
    public boolean checkCountryName(String countryName){

        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        boolean flag = false;
        for(int i=0;i<countryList.size();i++){
            if(countryName.equals(countryList.get(i).getCountryName())){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * check if the country is from the real time player
     * @param countryName
     * @return
     */
    public boolean checkFromPlayer(String countryName){
        List<Country> countryList = GamePlayerService.playerList.get(GamePlayerService.choosePlayer).getCountryList();
        boolean flag = false;
        for(int i=0;i<countryList.size();i++){
            if(countryName.equals(countryList.get(i).getCountryName())){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * check if fortify countries are connected
     * @param countryFrom Initial Army Moving Country Name
     * @param countryTo Goal Army Moving Country Name
     * @return True or False
     */
    public boolean checkConnected(String countryFrom,String countryTo){
        boolean flag =false;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(countryFrom.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Set<Country> countryList = MapEditorService.mapGraph.getCountryList().get(i).getNeighbours();
                Iterator it = countryList.iterator();
                while(it.hasNext()){
                    Country cc= (Country) it.next();
                    if(cc.getCountryName().equals(countryTo)){
                        flag=true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * check if attack dice number is correct
     * @param fromCountryName
     * @param num
     * @return
     */
    public boolean checkDiceNum(String fromCountryName,Integer num){

        Integer remainArmyNum = 0;
        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        for(int i=0;i<countryList.size();i++){
            if(fromCountryName.equals(countryList.get(i).getCountryName())){
                remainArmyNum=countryList.get(i).getArmyValue();
            }
        }
        if(remainArmyNum==1){
            return false;
        }else if(remainArmyNum==2){
            if(num==1){
                return true;
            }else{
                return false;
            }
        }else if(remainArmyNum==3){
            if(num==1||num==2){
                return true;
            }else{
                return false;
            }
        }else if (remainArmyNum>=4){
            return true;
        }else{
            return false;
        }
    }

    /**
     * defend dive information input
     * @param defendNum
     * @return
     */
    public String defend(String defendNum){
        String countryName = toCountry;
        Double dNumDefend = Double.parseDouble(defendNum);
        if(dNumDefend%1!=0){
            return "Dice Number must be an integer";
        }else {
            Integer numDefend = Integer.parseInt(defendNum);
            if (numDefend <= 0) {
                return "Dice Number can not be negative or zero";
            } else if (numDefend > 2) {
                return "Dice Number can not more than 2";
            } else {
                boolean checkDice = checkDefendDice(countryName, numDefend);
                if (checkDice) {
                    toDiceNum = numDefend;
                    String result = attackProcess();
                    return result;
                } else {
                    return "incorrect defend dice number";
                }
            }
        }

    }

    /**
     *check defend dice number
     * @param countryName
     * @param numDefend
     * @return
     */
    public boolean checkDefendDice(String countryName,Integer numDefend){
        Integer remainArmyNum=0;
        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        for(int i=0;i<countryList.size();i++){
            if(countryName.equals(countryList.get(i).getCountryName())){
                remainArmyNum=countryList.get(i).getArmyValue();
            }
        }
        if(remainArmyNum==1){
            if(numDefend==1){
                return true;
            }else{
                return false;
            }
        }else if(remainArmyNum>=2){
            if(numDefend==1||numDefend==2){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String alloutProcess() {
        List<Country> countryList = MapEditorService.mapGraph.getCountryList();
        int i = 0, n = 0;
        for (; i < countryList.size(); i++, n++) {
            countryList.get(i).getCountryName().equals(fromCountry);
            countryList.get(n).getCountryName().equals(toCountry);
            }

        while (true) {
            attackProcess();
            if (countryList.get(i).getArmyValue() == 1||countryList.get(n).getArmyValue()==0) {
                return "allout process finished";
            }
        }
    }

    /**
     * attack process
     * @return
     */
    public String attackProcess(){

        Integer[] fromDiceArray = new Integer[fromDiceNum];
        Integer[] toDiceArray = new Integer[toDiceNum];

        for(int i=0;i<fromDiceNum;i++){
            fromDiceArray[i] = ((int)(Math.random()*6+1));
        }

        for(int i=0;i<toDiceNum;i++){
            toDiceArray[i] = (int)(Math.random()*6+1);
        }

        Arrays.sort(fromDiceArray,Collections.reverseOrder());
        Arrays.sort(toDiceArray,Collections.reverseOrder());

        ArrayList<Integer> fromDiceList = new ArrayList<Integer>(Arrays.asList(fromDiceArray));
        ArrayList<Integer> toDiceList = new ArrayList<Integer>(Arrays.asList(toDiceArray));

        if(fromDiceList.size()==1){
            if(toDiceList.size()==1){
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }else{
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }
        }else if(fromDiceList.size()==2){
            if(toDiceList.size()==1){
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }else{
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
                if(fromDiceList.get(1)>toDiceList.get(1)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }
        }else{
            if(toDiceList.size()==1){
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }else{
                if(fromDiceList.get(0)>toDiceList.get(0)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
                if(fromDiceList.get(1)>toDiceList.get(1)){
                    deleteOneArmy(toCountry);
                }else{
                    deleteOneArmy(fromCountry);
                }
            }
        }
        fromDiceResultList = fromDiceList;
        toDiceResultList = toDiceList;
        notifyObservers();
        return "attack process finished";
    }

    /**
     * check if the country has been conquered
     * @return
     */
    public boolean checkConquered(){
        boolean flag =false;
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i))){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * delete one army if loosing the dice competition
     * @param countryName
     */
    public void deleteOneArmy(String countryName){
        for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
            if(countryName.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                Integer newArmy = MapEditorService.mapGraph.getCountryList().get(i).getArmyValue()-1;
                MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(newArmy);
            }
        }
    }

    /**
     * choose to stop attacking
     * @return
     */
    public String noattack(){
        GamePlayerService.checkPhase=3;
        notifyObservers();
        return "enter into fortification phase";
    }

    /**
     * if conquering one country,player could move some armies to that country
     * @param num
     * @return
     */
    public String attackMove(String num){

        Double dArmyNum = Double.valueOf(num);
        Integer ArmyNum = Integer.parseInt(num);
        boolean flag = checkMoveArmy();

        if(dArmyNum%1!=0){
            return "Dice Number must be an integer";
        }else if(ArmyNum<=0){
                return "attack move number can be negative or zero";
        }else if(flag){
            return "incorrect army number";
        }else{
            for(int i=0;i<MapEditorService.mapGraph.getCountryList().size();i++){
                if(toCountry.equals(MapEditorService.mapGraph.getCountryList().get(i).getCountryName())){
                    MapEditorService.mapGraph.getCountryList().get(i).setArmyValue(ArmyNum);
                }
            }
            return "attack move success";
        }

    }

    /**
     * check if army of moving is suitable
     * @return
     */
    public boolean checkMoveArmy(){
        return true;
    }


    public String getFromDice(){
        return fromDiceResultList.toString();
    }

    public String getToDice(){
        return toDiceResultList.toString();
    }
}
