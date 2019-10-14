package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import java.io.File;
import static javafx.scene.Cursor.HAND;

public class MapController{

    private MapGraph mapGraph;
    private ColorController colorPicker;

    @FXML
    private MenuItem loadMapMenuItem;

    @FXML
    private MenuItem saveMapMenuItem;

    @FXML
    public AnchorPane mapPane;

    @FXML
    public AnchorPane actionPane;

    @FXML
    public TextField continentNameR1;

    @FXML
    public TextField continentValueR1;

    @FXML
    public TextField countryNameR2;

    @FXML
    public TextField continentNameR2;

    @FXML
    public TextField countryNameR3;

    @FXML
    public TextField neighborCountryNameR3;

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

                // TODO: edge.getVertices();
                // for each Edge edge, edge.getVertices() returns a 2-element array Country [] countries
                // representing the two countries on each end of the edge

                // TODO: country.getPosition();
                // return a Point2D(x, y) position of the country

                // load fake data

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveMap(ActionEvent event) {
        // TODO: mapGraph.writeToFile();
        // save the map to file
    }

    @FXML
    void addContinent(ActionEvent event) {
        String continentName = continentNameR1.getText();
        String continentValueStr = continentValueR1.getText();
        Color color = colorPicker.pickOneColor();
        mapGraph.addContinent(continentName, continentValueStr, color);
    }

    @FXML
    void deleteContinent(ActionEvent event) {
        String continentName = continentNameR1.getText();
        mapGraph.deleteContinent(continentName);
    }

    @FXML
    void addCountry(ActionEvent event) {
        String countryName = countryNameR2.getText();
        String continentName = continentNameR2.getText();
        mapGraph.addCountry(countryName, continentName);
    }

    @FXML
    void deleteCountry(ActionEvent event) {
        String countryName = countryNameR2.getText();
        mapGraph.deleteCountry(countryName);
    }

    @FXML
    void addConnection(ActionEvent event){
        String cName1 = countryNameR3.getText();
        String cName2 = neighborCountryNameR3.getText();
        mapGraph.addConnection(cName1, cName2);
    }

    @FXML
    void deleteConnection(ActionEvent event){
        String cName1 = countryNameR3.getText();
        String cName2 = neighborCountryNameR3.getText();
        mapGraph.deleteConnection(cName1, cName2);
    }

    public MapController(){
        colorPicker = new ColorController();

        // default empty graph before loaded
        this.mapGraph = new MapGraph();
        MapGraphObserver mapGraphObserver = new MapGraphObserver(mapGraph);
    }

    /**
     * MapGraphObserver
     */
    public class MapGraphObserver extends Observer{

        public MapGraphObserver(MapGraph mapGraph){
            this.mapGraph = mapGraph;
            this.mapGraph.attach(this);
        }

        @Override
        public void updateContinentList(String action, Continent continent){
            if(action == "add"){
                System.out.println("Continent Add");

                /**
                 * Update the GUI
                 */
                //create continent rectangle and text
                Rectangle continentRectangle = new Rectangle(60, 20, continent.getColor());
                continentRectangle.setId(continent.getContinentName());
                Text text = new Text(continent.getContinentName() + ": " + continent.getArmyValue());
                text.setId("continentNameText");

                //set rectangle and text position
                double x = mapPane.getLayoutBounds().getMaxX() - 100;
                double y = mapGraph.getContinentList().size() * 50;
                continentRectangle.setX(x);
                continentRectangle.setY(y);
                text.setX(x);
                text.setY(y-5);

                mapPane.getChildren().addAll(continentRectangle, text);

            }else if(action == "delete"){
                System.out.println("Continent Delete");

                /**
                 * Update the GUI
                 */
                //remove the deleted continent rectangle on mapPane
                mapPane.getChildren().remove(mapPane.lookup("#" + continent.getContinentName()));
                mapPane.getChildren().remove(mapPane.lookup("#continentNameText"));
                //remove all the remaining continent rectangles & texts on mapPane
                for (Continent c: mapGraph.getContinentList()) {
                    mapPane.getChildren().remove(mapPane.lookup("#" + c.getContinentName()));
                    mapPane.getChildren().remove(mapPane.lookup("#continentNameText"));
                }

                //reload the continent List
                int i = 1;
                for (Continent c: mapGraph.getContinentList()) {
                    //create continent rectangle and text
                    Rectangle continentRectangle = new Rectangle(60, 20, c.getColor());
                    continentRectangle.setId(c.getContinentName());
                    Text text = new Text(c.getContinentName() + ": " + c.getArmyValue());
                    text.setId("continentNameText");

                    //set rectangle and text position
                    double x = mapPane.getLayoutBounds().getMaxX() - 100;
                    double y = i * 50;
                    continentRectangle.setX(x);
                    continentRectangle.setY(y);
                    text.setX(x);
                    text.setY(y-5);

                    mapPane.getChildren().addAll(continentRectangle, text);
                    i++;
                }


            }

        }

        @Override
        public void updateCountry(String action, Country country){
            if(action == "add"){
                System.out.println("The Country Add");

                /**
                 * TODO: country.getContinent().getColor();
                 */
                Color countryColor = country.getContinent().getColor();
                //Fake Data
                countryColor = Color.TAN;
                Point2D center = new Point2D(250, 250); //default position on scene
                Circle circle = new Circle(center.getX(), center.getY(), 15, countryColor);
                circle.setCursor(HAND);

                Label label = new Label(country.getCountryName() + ":" + "Fake Player Name");
                label.setLayoutX(250 - 20);
                label.setLayoutY(250 - 30);

                circle.setOnMouseDragged((t) -> {
                    // update Circle View's location
                    circle.setCenterX(t.getX());
                    circle.setCenterY(t.getY());
                    label.setLayoutX(t.getX() - 20);
                    label.setLayoutY(t.getY() - 30);
                });

                circle.setOnMouseClicked((t) -> {
                    // update the dragged country's location info
                    System.out.println("Country's new location set.");
                    country.setCoordinator(t.getX(), t.getY());
                });

                mapPane.getChildren().addAll(circle, label);

            }else if(action == "delete"){
                System.out.println("The Country Delete");
            }
        }

        @Override
        public void updateConnection(String action, Connection connection) {
            if(action == "add"){
                System.out.println("The Connection Add");
            } else if(action == "delete"){
                System.out.println("The Connection Delete");
            }

        }
    }


    /**
     * ConnectionObserver
     */
//    public class ConnectionObserver extends Observer{
//
//        /**
//         * Construct the ConnectionObserver for the Connection object
//         * @param connection
//         */
//        public ConnectionObserver(Connection connection){
//            this.connection = connection;
//            this.connection.attach(this);
//        }
//
//        /**
//         * Update GUI When there are changes of Connection
//         */
//        @Override
//        public void update(){
//            System.out.println("Connection Modified");
//
//            Country country1 = this.connection.getCountry1();
//            Country country2 = this.connection.getCountry2();
//            // TODO: Point2D pt1 = country1.getCoordinator();
//            // TODO: Point2D pt2 = country2.getCoordinator();
//            // Two Fake Point2D data here
//            Point2D pt1 = new Point2D(300, 500);
//            Point2D pt2 = new Point2D(350, 450);
//
//            // draw a connected line for the two countries
//            Line line = new Line();
//            line.setStartX(pt1.getX());
//            line.setStartY(pt1.getY());
//            line.setEndX(pt2.getX());
//            line.setEndY(pt2.getY());
//            line.setStroke(Color.GRAY);
//            line.toBack();
//            mapPane.getChildren().add(line);
//        }
//    }
}