package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import model.MapGraph;

import java.io.File;

public class MapController {
    private MapGraph mapGraph;

    @FXML
    private MenuItem loadMapMenuItem;

    @FXML
    private MenuItem saveMapMenuItem;

    @FXML
    private Pane mapPane;

    @FXML
    void loadMap(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(loadMapMenuItem.getParentPopup());
        if(file != null){
            try{
                // rendering the map here

                // TODO: readMapFile(File file);
                // read map.txt file, return a MapGraph mapGraph
                // this.mapGraph = mapGraph;

                // TODO: mapGraph.getAllContinents();
                // return a List of Continents

                // TODO: continent.getColor();
                // return the color representing the continent

                // TODO: mapGraph.getAllCountries();
                // return a List of Countries

                // TODO: country.getOwner();
                // return the country's owner player, if not assigned, return null

                // TODO: player.getId();
                // return the Id representing the player

                // TODO: mapGraph.getAllAdjacencies();
                // return a List of all Edges in the mapGraph

                // TODO: edge.getVertices()
                // for each Edge edge, edge.getVertices() returns a 2-element array Country [] countries
                // representing the two countries on each end of the edge

                // TODO: country.getPosition()
                // return a Point2D(x, y) position of the country

                Circle c1;
                c1 = new Circle(200, 200, 20, Color.rgb(186, 222, 213));
                mapPane.getChildren().add(c1);

                Circle c2;
                c2 = new Circle(300, 200, 20, Color.rgb(222, 195, 186));
                mapPane.getChildren().add(c2);

                Line line = new Line();
                line.setStartX(c1.getCenterX());
                line.setStartY(c1.getCenterY());
                line.setEndX(c2.getCenterX());
                line.setEndY(c2.getCenterY());
                line.setStroke(Color.GRAY);
                mapPane.getChildren().add(line);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveMap(ActionEvent event) {

    }

    public MapController(){

    }
}
