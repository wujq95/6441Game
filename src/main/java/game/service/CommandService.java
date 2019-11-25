package service;

import controller.MapController;
import model.GamePlayer;
import strategy.CheaterStrategy;

/**
 * Command Service is used to receive the input commands for player actions
 */
public class CommandService {

    /**
     * Prompt Command into console
     *
     * @param inputCommand Commands
     * @return Message
     */
    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = MapController.mapEditorService;
        ReinforceService reinforceService = MapController.reinforceService;
        FortifyService fortifyService = MapController.fortifyService;
        GamePlayerService gamePlayerService = MapController.gamePlayerService;
        AttackService attackService = MapController.attackService;
        CardService cardService = MapController.cardService;
        TournamentService tournamentService = new TournamentService();

        inputCommand = inputCommand.trim();
        String commandReturnMsg = "";

        if (inputCommand.startsWith("editmap") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
            cardService.createCardDeck();
        } else if (inputCommand.startsWith("loadmap") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
            cardService.createCardDeck();
        } else if (inputCommand.startsWith("showmap") && GamePlayerService.checkPhase == 0) {
            commandReturnMsg = mapEditorService.showMap();
        } else if (inputCommand.startsWith("savemap") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.saveMap(arguments[1]);
        } else if (inputCommand.startsWith("validatemap") && GamePlayerService.checkPhase == 0) {
            if (!mapEditorService.validateMap()) {
                return "the map is not valid";
            } else {
                return "the map is valid";
            }
        } else if (inputCommand.startsWith("editcontinent") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editContinent(arguments);
        } else if (inputCommand.startsWith("editcountry") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editCountry(arguments);
        } else if (inputCommand.startsWith("editneighbor") && GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editNeighbor(arguments);
        } else if (inputCommand.startsWith("gameplayer") && GamePlayerService.checkPhase == 1 || GamePlayerService.checkPhase == 0) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.gamePlayerAction(arguments);
        } else if (inputCommand.startsWith("populatecountries") && GamePlayerService.checkPhase == 1) {
            boolean flag = gamePlayerService.checkPlayerNum();
            if (flag) {
                String populateReturnMsg = gamePlayerService.populateCountries();
                String alloReturnMsg = gamePlayerService.alloInitialArmy();
                commandReturnMsg = populateReturnMsg + " " + alloReturnMsg;
            } else {
                commandReturnMsg = "Inappropriate player number";
            }
        } else if (inputCommand.startsWith("placearmy") && GamePlayerService.checkPhase == 1) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeOneArmy(arguments[1]);
        } else if (inputCommand.startsWith("placeall") && GamePlayerService.checkPhase == 1) {

            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeAll();
        } else if (inputCommand.startsWith("start") && GamePlayerService.checkPhase == 2) {
            GamePlayer player = GamePlayerService.playerList.get(GamePlayerService.choosePlayer);
            if(player.getStrategyName().equals("CheaterStrategy")){
                player.reinforce();
                player.attack();
                player.fortify();
                gamePlayerService.changPlayer();
                attackService.checkStop();
                if(GamePlayerService.checkPhase==5){
                    commandReturnMsg="game stop";
                }else{
                    commandReturnMsg = "cheater strategy execution success and enter into the reinforcement phase for the next player";
                }
            }else if(player.getStrategyName().equals("RandomStrategy")){
                player.reinforce();
                player.attack();
                player.fortify();
                gamePlayerService.changPlayer();
                attackService.checkStop();
                if(GamePlayerService.checkPhase==5){
                    commandReturnMsg="game stop";
                }else{
                    commandReturnMsg = "random strategy execution success and enter into the reinforcement phase for the next player";
                }
            }else if(player.getStrategyName().equals("BenevolentStrategy")){
                player.reinforce();
                player.attack();
                player.fortify();
                gamePlayerService.changPlayer();
                attackService.checkStop();
                if(GamePlayerService.checkPhase==5){
                    commandReturnMsg="game stop";
                }else{
                    commandReturnMsg = "benevolent strategy execution success and enter into the reinforcement phase for the next player";
                }
            } else if(player.getStrategyName().equals("AggressiveStrategy")){
                player.reinforce();
                player.attack();
                player.fortify();
                gamePlayerService.changPlayer();
                attackService.checkStop();
                if(GamePlayerService.checkPhase==5){
                    commandReturnMsg="game stop";
                }else{
                    commandReturnMsg = "aggressiveStrategy strategy execution success and enter into the reinforcement phase for the next player";
                }
            }else{
                commandReturnMsg = gamePlayerService.calReinArmyNum();
            }
        } else if (inputCommand.startsWith("reinforce") && GamePlayerService.checkPhase == 2) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = reinforceService.reinforce(arguments[1], arguments[2]);
        }else if(inputCommand.startsWith("tournament")&& GamePlayerService.checkPhase == 1){
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = tournamentService.tournament(arguments);
        } else if (inputCommand.startsWith("fortify") && GamePlayerService.checkPhase == 3) {
            String[] arguments = inputCommand.split(" ");
            if (arguments[1].startsWith("none")) {
                commandReturnMsg = fortifyService.fortifyNone();
            } else {
                commandReturnMsg = fortifyService.fortify(arguments[1], arguments[2], arguments[3]);
            }
        } else if (inputCommand.startsWith("attackmove") && GamePlayerService.checkPhase == 4) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = attackService.attackMove(arguments[1]);
        } else if (inputCommand.startsWith("attack") && GamePlayerService.checkPhase == 4) {
            String[] arguments = inputCommand.split(" ");
            if (arguments[1].startsWith("-noattack")) {
                if (AttackService.ConqueredAtleastOneIntheturn) {
                    String cardName = cardService.rewardCardAfterConquerOneCountry();
                    AttackService.ConqueredAtleastOneIntheturn = false;
                    commandReturnMsg = attackService.noAttack() + " , and you have been rewarded a card " + cardName;
                } else {
                    commandReturnMsg = attackService.noAttack();
                }
            } else if (arguments[3].startsWith("-allout")) {
                commandReturnMsg = attackService.allout(arguments[1], arguments[2]);
            } else {
                commandReturnMsg = attackService.attack(arguments);
            }
        } else if (inputCommand.startsWith("defend") && GamePlayerService.checkPhase == 4) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = attackService.defend(arguments[1]);
        } else if (inputCommand.startsWith("gamestop") && GamePlayerService.checkPhase == 3) {
            commandReturnMsg = fortifyService.stop();
        } else if (inputCommand.startsWith("exchangecards") && GamePlayerService.checkPhase == 2) {
            String[] arguments = inputCommand.split(" ");
            GamePlayer gamePlayer = gamePlayerService.getCurrentPlayer();
            if (cardService.mustExchange(gamePlayer)) {
                if (arguments[1].equals("-none")) {
                    return "you must exchange cards";
                } else {
                    return cardService.exchangeCards(
                            Integer.parseInt(arguments[1]) - 1,
                            Integer.parseInt(arguments[2]) - 1,
                            Integer.parseInt(arguments[3]) - 1,
                            gamePlayer);
                }
            } else {
                if (!arguments[1].equals("-none")) {
                    return cardService.exchangeCards(
                            Integer.parseInt(arguments[1]) - 1,
                            Integer.parseInt(arguments[2]) - 1,
                            Integer.parseInt(arguments[3]) - 1,
                            gamePlayer);
                } else {
                    CardService.notExchangeCards = true;
                    cardService.notifyObservers(cardService);
                    CardService.notExchangeCards = false;
                    return "you choose not to exchange cards";
                }
            }
        } else {
            return "wrong syntax";
        }

        return commandReturnMsg;
    }
}
