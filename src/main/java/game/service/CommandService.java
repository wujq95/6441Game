package service;

public class CommandService {

    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = new MapEditorService();

        String commandReturnMsg = "";
        if (inputCommand.startsWith("editmap")) {
            String[] arguments = inputCommand.split(" ");
            commandReturnMsg = mapEditorService.editMap(arguments[1]);
        }
        return commandReturnMsg;
    }
}
