package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.MapGraph;
import model.Observer;

import java.io.File;
import java.util.ArrayList;

public class MapController{

    private MapGraph mapGraph;
    private ArrayList<Color> colorPicker;

    final private int maxContinentsNum = 8;
    private int continentsCount = 0;

    @FXML
    private MenuItem loadMapMenuItem;

    @FXML
    private MenuItem saveMapMenuItem;

    @FXML
    public AnchorPane mapPane;

    @FXML
    public AnchorPane actionPane;

    @FXML
    void loadMap(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(loadMapMenuItem.getParentPopup());
        if (file != null) {
            try {
                // rendering the map here

                // TODO: readMapFile(File file);
                // read map.txt file, return a MapGraph mapGraph
                // this.mapGraph = mapGraph;
                this.mapGraph = new MapGraph();

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

                // TODO: mapGraph.getAllConnections();
                // return a List of all Edges in the mapGraph

                // TODO: edge.getVertices()
                // for each Edge edge, edge.getVertices() returns a 2-element array Country [] countries
                // representing the two countries on each end of the edge

                // TODO: country.getPosition()
                // return a Point2D(x, y) position of the country

                // load fake data

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveMap(ActionEvent event) {

    }

    @FXML
    void addContinent(ActionEvent event) {
        if (continentsCount < maxContinentsNum) {
            Rectangle continentRectangle = new Rectangle(60, 20, colorPicker.get(continentsCount));
            continentsCount++;
            continentRectangle.setX(mapPane.getLayoutBounds().getMaxX() - 100);
            continentRectangle.setY(continentsCount * 50);
            mapPane.getChildren().addAll(continentRectangle);

            // TODO: mapGraph.addContinent(String continentName, double controlValue)
        }
    }

    @FXML
    void addCountry(ActionEvent event) {
        Circle circle = new Circle(100, 100, 15, Color.YELLOWGREEN);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseDragged((t) -> {
            Circle c = (Circle) (t.getSource());
            circle.setCenterX(t.getX());
            circle.setCenterY(t.getY());

            // TODO: mapGraph.updateCountryPosition(String countryName, double x, double y);
        });

        // TODO: mapGraph.addCountry(String countryName, String continentName, double x, double y);
        mapPane.getChildren().addAll(circle);
    }

    @FXML
    void addConnection(ActionEvent event){
        mapGraph.addCountryLocation(300, 300);
    }

    public MapController(){
        ColorController colorController = new ColorController();
        this.colorPicker = colorController.getPalette();

        // Fake Map Data here:
        this.mapGraph = new MapGraph();
        new MapGraphObserver(this.mapGraph);
    }

    public void initModel(){
        Circle c1;
        c1 = new Circle(200, 200, 20, Color.rgb(186, 222, 213));

        Circle c2;
        c2 = new Circle(300, 200, 20, Color.rgb(222, 195, 186));

        Line line = new Line();
        line.setStartX(c1.getCenterX());
        line.setStartY(c1.getCenterY());
        line.setEndX(c2.getCenterX());
        line.setEndY(c2.getCenterY());
        line.setStroke(Color.GRAY);

        mapPane.getChildren().add(line);
        mapPane.getChildren().add(c1);
        mapPane.getChildren().add(c2);
    }

    public class MapGraphObserver extends Observer{

        public MapGraphObserver(MapGraph mapGraph){
            this.mapGraph = mapGraph;
            this.mapGraph.attach(this);
        }

        @Override
        public void update(){
            System.out.println("update called");
        }
    }

}