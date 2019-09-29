package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CommandController {
    @FXML
    TextField continentNameAdd;


    public String getCommandInput(){
        continentNameAdd.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    System.out.println(continentNameAdd.getText());
                }
            }
        });
        return continentNameAdd.getText();
    }
}
