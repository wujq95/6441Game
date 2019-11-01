package service;

import controller.MapController;
import model.GamePlayer;

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

        inputCommand = inputCommand.trim();
        String commandReturnMsg = "";

        if (inputCommand.startsWith("editmap")) {
            GamePlayerService.checkPhase = 0;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
        } else if (inputCommand.startsWith("loadmap")) {
            GamePlayerService.checkPhase = 1;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
        } else if (inputCommand.startsWith("showmap")) {
            GamePlayerService.checkPhase = 0;
            commandReturnMsg = mapEditorService.showMap();
        } else if (inputCommand.startsWith("savemap")) {
            GamePlayerService.checkPhase = 0;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.saveMap(arguments[1]);
        } else if (inputCommand.startsWith("validatemap")) {
            GamePlayerService.checkPhase = 0;
            if (!mapEditorService.validateMap()) {
                return "the map is not valid";
            } else {
                return "the map is valid";
            }
        } else if (inputCommand.startsWith("editcontinent")) {
            GamePlayerService.checkPhase = 0;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editContinent(arguments);
        } else if (inputCommand.startsWith("editcountry")) {
            GamePlayerService.checkPhase = 0;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editCountry(arguments);
        } else if (inputCommand.startsWith("editneighbor")) {
            GamePlayerService.checkPhase = 0;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editNeighbor(arguments);
        } else if (inputCommand.startsWith("gameplayer")) {
            GamePlayerService.checkPhase = 1;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.gamePlayerAction(arguments);
        } else if (inputCommand.startsWith("populatecountries")) {
            GamePlayerService.checkPhase = 1;
            boolean flag = gamePlayerService.checkPlayerNum();
            if (flag) {
                String populateReturnMsg = gamePlayerService.populateCountries();
                String alloReturnMsg = gamePlayerService.alloInitialArmy();
                commandReturnMsg = populateReturnMsg + " " + alloReturnMsg;
            } else {
                commandReturnMsg = "Inappropriate player number";
            }
        } else if (inputCommand.startsWith("placearmy")) {
            GamePlayerService.checkPhase = 1;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeOneArmy(arguments[1]);
        } else if (inputCommand.startsWith("placeall")) {
            GamePlayerService.checkPhase = 1;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeAll();
        } else if (inputCommand.startsWith("start")) {
            GamePlayerService.checkPhase = 2;
            commandReturnMsg = gamePlayerService.calReinArmyNum();
        } else if (inputCommand.startsWith("reinforce")) {
            GamePlayerService.checkPhase = 2;
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = reinforceService.reinforce(arguments[1], arguments[2]);
        } else if (inputCommand.startsWith("fortify")) {
            GamePlayerService.checkPhase = 3;
            String[] arguments = inputCommand.split(" ");
            if (arguments[1].startsWith("none")) {
                commandReturnMsg = fortifyService.fortifyNone();
            } else {
                commandReturnMsg = fortifyService.fortify(arguments[1], arguments[2], arguments[3]);
            }
        } else if (inputCommand.startsWith("attack")) {
            GamePlayerService.checkPhase = 4;
            String[] arguments = inputCommand.split(" ");
            if (arguments[1].startsWith("-noattack")){
                commandReturnMsg = attackService.noattack();
            }else if(arguments[3].startsWith("-allout")){
                commandReturnMsg = attackService.allout(arguments[1], arguments[2]);
            }else {
                commandReturnMsg = attackService.attack(arguments);
            }
<<<<<<< Updated upstream
        } else if (inputCommand.startsWith("defend")) {
            GamePlayerService.checkPhase = 4;
=======
        } else if (inputCommand.startsWith("defend") && GamePlayerService.checkPhase == 4) {
>>>>>>> Stashed changes
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = attackService.defend(arguments[1]);
        } else if (inputCommand.startsWith("exchangecards") && GamePlayerService.checkPhase == 2) {
            String[] arguments = inputCommand.split(" ");
            //TODO:right now game player
            GamePlayer gamePlayer = new GamePlayer();
            if (cardService.mustExchange(gamePlayer)) {
                if (arguments[1].equals("-none")) {
                    return "you must exchange cards";
                } else {
                    cardService.exchangeCards(
                            Integer.parseInt(arguments[1]),
                            Integer.parseInt(arguments[2]),
                            Integer.parseInt(arguments[3]),
                            gamePlayer);
                }
            } else {
                if (!arguments[1].equals("-none")) {
                    cardService.exchangeCards(
                            Integer.parseInt(arguments[1]),
                            Integer.parseInt(arguments[2]),
                            Integer.parseInt(arguments[3]),
                            gamePlayer);
                }
            }
        } else {
            return "wrong syntax";
        }

        return commandReturnMsg;
    }
}
