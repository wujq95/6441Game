package service;

public class CommandService {

    private Phase phase;

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

        /*switch (phase) {
            case MAPEDITOR:
                // code block
                break;
            case GAMEPLAY:
                // code block
                break;
            case STARTUP:
                break;
            case REINFORCEMENT:
                break;
            case FORTIFICATION:
                break;
            default:
                // code block
        }*/
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
            boolean flag = gamePlayerService.checkPlayerNum();
            if(flag){
                String populateReturnMsg = gamePlayerService.populateCountries();
                String alloReturnMsg = gamePlayerService.alloInitialArmy();
                commandReturnMsg = populateReturnMsg + " " + alloReturnMsg;
            }else{
                commandReturnMsg = "Inappropriate player number";
            }
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
        } else {
            return "wrong syntax";
        }

        return commandReturnMsg;
    }
}
