package service;

/**
 * provide service for dealing with comment line
 */
public class CommandService {
    /**
     * Prompt Command into console
     * @param inputCommand
     * @return commend result
     */
    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = new MapEditorService();
        ReinforceService reinforceService = new ReinforceService();
        FortifyService fortifyService = new FortifyService();
        GamePlayerService gamePlayerService = new GamePlayerService();

        inputCommand = inputCommand.trim();
        String commandReturnMsg = "";
        if (inputCommand.startsWith("editmap") || inputCommand.startsWith("loadmap")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
        } else if (inputCommand.startsWith("showmap")) {
            commandReturnMsg = mapEditorService.showMap();
        } else if (inputCommand.startsWith("savemap")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.saveMap(arguments[1]);
        } else if (inputCommand.startsWith("validatemap")) {
            if (!mapEditorService.validateMap()) {
                return "the map is not valid";
            } else {
                return "the map is valid";
            }
        } else if (inputCommand.startsWith("editcontinent")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editContinent(arguments);
        } else if (inputCommand.startsWith("editcountry")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editCountry(arguments);
        } else if (inputCommand.startsWith("editneighbor")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editNeighbor(arguments);
        } else if (inputCommand.startsWith("gameplayer")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.gamePlayerAction(arguments);
        } else if (inputCommand.startsWith("populatecountries")) {
            String populateReturnMsg = gamePlayerService.populateCountries();
            String alloReturnMsg = gamePlayerService.alloInitialArmy();
            commandReturnMsg = populateReturnMsg + " " + alloReturnMsg;
        } else if (inputCommand.startsWith("placearmy")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeOneArmy(arguments[1]);
        } else if (inputCommand.startsWith("placeall")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = gamePlayerService.placeAll();
        } else if (inputCommand.startsWith("start")) {
            commandReturnMsg = gamePlayerService.calReinArmyNum();
        } else if (inputCommand.startsWith("reinforce")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = reinforceService.reinforce(arguments[1], arguments[2]);
        } else if (inputCommand.startsWith("fortify")) {
            String[] arguments = inputCommand.split(" ");
            if (arguments[1].startsWith("none")) {
                commandReturnMsg = fortifyService.fortifyNone();
            } else {
                commandReturnMsg = fortifyService.fortify(arguments[1], arguments[2], arguments[3]);
            }
        }
        return commandReturnMsg;
    }
}
