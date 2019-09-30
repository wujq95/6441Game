package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ContinentController{

    @FXML
    private AnchorPane mapAnchorPane;

    @FXML
    private TextField continentNameDel;

    @FXML
    private TextField continentVal;

    @FXML
    private TextField continentNameAdd;

    @FXML
    void addContinent(ActionEvent event) {
        //continentNameAdd.setText("clicked");
        Circle testCircle = new Circle(50, 50, 20, Color.TAN);
        mapAnchorPane.getChildren().add(testCircle);
    }

}

