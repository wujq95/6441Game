package service;

public class CommandService {

    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = new MapEditorService();

        String commandReturnMsg = "";
        if (inputCommand.startsWith("editmap")) {
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
        }
        return commandReturnMsg;
    }
}
