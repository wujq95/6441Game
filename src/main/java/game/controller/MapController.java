package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;

public class MapController {

    @FXML
    private MenuItem loadMapMenuItem;

    @FXML
    private MenuBar mapMenuBar;

    @FXML
    private MenuItem saveMapMenuItem;

    @FXML
    void loadMap(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(loadMapMenuItem.getParentPopup());
        if(file != null){
            try{
                //process loaded file data here
                System.out.println("process loaded file data here");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveMap(ActionEvent event) {

    }

}
