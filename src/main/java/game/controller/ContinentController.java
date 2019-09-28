package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ContinentController {

    @FXML
    private TextField continentNameDel;

    @FXML
    private TextField continentVal;

    @FXML
    private TextField continentNameAdd;

    @FXML
    void addContinent(ActionEvent event) {
        continentNameAdd.setText("clicked");
    }

}

