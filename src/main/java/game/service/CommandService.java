package service;

public class CommandService {

    /**
     * Prompt Command into console
     *
     * @param inputCommand Commands
     * @return Message
     */
    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = new MapEditorService();
        ReinforceService reinforceService = new ReinforceService();
        FortifyService fortifyService = new FortifyService();
        GamePlayerService gamePlayerService = new GamePlayerService();

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
            GamePlayerService.checkPhase = 1;
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
        } else {
            return "wrong syntax";
        }

        return commandReturnMsg;
    }
}
