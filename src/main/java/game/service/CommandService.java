package service;

public class CommandService {

    public String processCommand(String inputCommand) {
        MapEditorService mapEditorService = new MapEditorService();
        if (inputCommand.startsWith("editmap")) {
            String[] arguments = inputCommand.split(" ");
            mapEditorService.editMap(arguments[1]);
        }
        return inputCommand;
    }
}
