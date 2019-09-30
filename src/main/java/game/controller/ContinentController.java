package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.CommandService;

public class ContinentController {

    @FXML
    private TextField continentNameDel;

    @FXML
    private TextField continentVal;

    @FXML
    private TextField continentNameAdd;

    @FXML
    void addContinent(ActionEvent event) {
        CommandService commandService = new CommandService();
        String output = commandService.processCommand(continentNameAdd.getText());
        continentNameAdd.setText(output + "output");
    }

}

