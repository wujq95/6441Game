package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import service.CommandService;
import service.MapEditorService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.Cursor.HAND;

/**
 * MapController class
 * Linking the user input with backend model data
 */
public class MapController{

    /**
     * data structure storing the loaded map
     */
    private MapGraph mapGraph;

    /**
     * observer of mapGraph
     */
    private MapGraphObserver mapGraphObserver;

    /**
     * color palette for continents
     */
    private ColorController colorPicker;

    /**
     * mapEditorService class contains all edit mapGraph methods
     */
    private static MapEditorService mapEditorService;

    /**
     * load map option on menu bar
     */
    @FXML
    private MenuItem loadMapMenuItem;

    /**
     * save map option on menu bar
     */
    @FXML
    private MenuItem saveMapMenuItem;

    /**
     * anchor pane displaying the map
     */
    @FXML
    public AnchorPane mapPane;

    /**
     * continent list title label
     */
    @FXML
    public Label continentListLabel;

    /**
     * anchor pane containing all user interface actions to the map graph
     */
    @FXML
    public AnchorPane actionPane;

    /**
     * continent name textfield for add/delete continent
     */
    @FXML
    public TextField continentNameR1;

    /**
     * continent value textfield for add continent
     */
    @FXML
    public TextField continentValueR1;

    /**
     * country name textfield for add/delete country
     */
    @FXML
    public TextField countryNameR2;

    /**
     * continent name textfield for add/delete country
     */
    @FXML
    public TextField continentNameR2;

    /**
     * country name textfield for add/delete connection
     */
    @FXML
    public TextField countryNameR3;

    /**
     * neighbour country name textfield for add/delete connection
     */
    @FXML
    public TextField neighborCountryNameR3;

    /**
     * player name textfield for add player
     */
    @FXML
    public TextField playerNameInput;

    /**
     * country name textfield for reinforcement
     */
    @FXML
    public TextField reinforceCountryName;

    /**
     * reinforce number textfield
     */
    @FXML
    public TextField reinforceNum;

    /**
     * fortify from country name textfield
     */
    @FXML
    public TextField fortifyFrom;

    /**
     * fortify to country name textfield
     */
    @FXML
    public TextField fortifyTo;

    /**
     * number of armies textfield to fortify
     */
    @FXML
    public TextField fortifyNum;

    /**
     * textarea for the input command line
     */
    @FXML
    public TextArea commandLine;

    /**
     * textarea displaying the information
     */
    @FXML
    public TextArea infoTextView;

    /**
     * load map the map into MapGraph mapGraph data structure
     * @param event mouse click event
     */
    @FXML
    void loadMap(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(loadMapMenuItem.getParentPopup());
        if (file != null) {
            try {
                // rendering the map here

                String fileName = file.getName();
                mapEditorService.editMap(fileName);
                this.mapGraph = MapEditorService.mapGraph;
                //add observer
                MapGraphObserver MapEditorMapGraphObserver = new MapGraphObserver(MapEditorService.mapGraph);

                // Load all continents
                List<Continent> continentList = mapGraph.getContinentList();
                int i = 0;
                for (Continent continent:continentList) {
                    Rectangle continentRectangle = new Rectangle(60, 20, continent.getColor());
                    continentRectangle.setId("Continent" + continent.getContinentName());
                    Text text = new Text(continent.getContinentName() + ": " + continent.getArmyValue());
                    text.setId("ContinentLabel" + continent.getContinentName());

                    //set rectangle and text position
                    double x = mapPane.lookup("#continentListLabel").getLayoutX();
                    double y = i * 50 + 50;
                    continentRectangle.setX(x);
                    continentRectangle.setY(y);
                    text.setX(x);
                    text.setY(y - 5);

                    mapPane.getChildren().addAll(continentRectangle, text);
                    i++;
                }

                // Load all countries
                List<Country> countryList = mapGraph.getCountryList();
                for (Country country: countryList) {
                    double x = country.getX();
                    double y = country.getY();
                    Color countryColor = country.getParentContinent().getColor();
                    Circle circle = new Circle(x, y, 15, countryColor);
                    mapPane.getChildren().add(circle);
                }

                // Load all connections
                List<Connection> connectionList = mapGraph.getConnections();
                for(Connection connection: connectionList){
                    Country country1 = connection.getCountry1();
                    Country country2 = connection.getCountry2();

                    Point2D pt1 = country1.getCoordinator();
                    Point2D pt2 = country2.getCoordinator();
                    String lineId = country1.getCountryName() + country2.getCountryName();

                    Line line = new Line();
                    line.setId(lineId);
                    line.setStartX(pt1.getX());
                    line.setStartY(pt1.getY());
                    line.setEndX(pt2.getX());
                    line.setEndY(pt2.getY());
                    line.setStroke(Color.rgb(95,103,105));
                    line.toBack();

                    mapPane.getChildren().add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * save the map to file
     * @param event mouse click event
     */
    @FXML
    void saveMap(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(saveMapMenuItem.getParentPopup());
        if(file != null){
            MapEditorService.mapGraph = this.mapGraph;
            System.out.println(file.getName());
            //mapEditorService.saveMap("ameroki_save.map");
            //TODO: saveMap only works for saving to an existing file
            //TODO: save to a new file not work
            mapEditorService.saveMap(file.getName());
        }
    }

    /**
     * add continent
     * @param event mouse click event
     */
    @FXML
    void addContinent(ActionEvent event) {
        String continentName = continentNameR1.getText();
        String continentValueStr = continentValueR1.getText();
        Color color = colorPicker.pickOneColor();
        mapGraph.addContinent(continentName, continentValueStr, color);
    }

    /**
     * delete continent
     * @param event mouse click event
     */
    @FXML
    void deleteContinent(ActionEvent event) {
        String continentName = continentNameR1.getText();
        mapGraph.deleteContinent(continentName);
    }

    /**
     * add country
     * @param event mouse click event
     */
    @FXML
    void addCountry(ActionEvent event) {
        String countryName = countryNameR2.getText();
        String continentName = continentNameR2.getText();
        mapGraph.addCountry(countryName, continentName);
    }

    /**
     * delete country
     * @param event mouse click event
     */
    @FXML
    void deleteCountry(ActionEvent event) {
        String countryName = countryNameR2.getText();
        mapGraph.deleteCountry(countryName);
    }

    /**
     * add connection
     * @param event mouse click event
     */
    @FXML
    void addConnection(ActionEvent event){
        String cName1 = countryNameR3.getText();
        String cName2 = neighborCountryNameR3.getText();
        mapGraph.addConnection(cName1, cName2);
    }

    /**
     * delete connection
     * @param event mouse click event
     */
    @FXML
    void deleteConnection(ActionEvent event){
        String cName1 = countryNameR3.getText();
        String cName2 = neighborCountryNameR3.getText();
        mapGraph.deleteConnection(cName1, cName2);
    }

    /**
     * add player
     * @param event mouse click event
     */
    @FXML
    void addPlayer(ActionEvent event){
        String playerName = playerNameInput.getText();
        System.out.println(playerName + " add called.");
        /**
         * TODO: add player to game
         * Player player = game.addPlayer(String playerName);
         * return the player added.
         * I need game.getPlayerList.size()
         */
        Text playerNameText = new Text(playerName);
        double x = mapPane.getLayoutBounds().getMaxX() - 200;
        double y = 1 * 50;
        playerNameText.setX(x);
        playerNameText.setY(y);
        playerNameText.setId(playerName + "Player");
        mapPane.getChildren().add(playerNameText);
    }

    /**
     * delete player
     * @param event mouse click event
     */
    @FXML
    void deletePlayer(ActionEvent event){
        String playerName = playerNameInput.getText();
        System.out.println(playerName + " delete called.");
        /**
         * TODO: delete player to game
         * return the player deleted.
         * Player player = game.deletePlayer(String playerName);
         * I need game.getPlayerList.size()
         */
        mapPane.getChildren().remove(mapPane.lookup("#" + playerName + "Player"));
    }

    /**
     * randomly assign countries to all players
     * @param event mouse click event
     */
    @FXML
    void populateCountries(ActionEvent event){
        System.out.println("Populate countries called");
        /**
         * TODO: assign the countries in mapGraph to different players
         * game.populateCountries(MapGraph mapGraph);
         * game.getPopulatedCountriesList();
         * All the countries in the getPopulatedCountriesList already have player assigned
         */
    }

    /**
     * players place army to a choosen country in round-robin fashion
     * @param event mouse click event
     */
    @FXML
    void placeArmy(ActionEvent event){
        System.out.println("Place Army called");

        /**
         * TODO: round_robin
         * Player player = game.getCurrentPlayer();
         * player.placeArmy(String countryName);
         */
    }

    /**
     * assign armies to all countries
     * @param event mouse click event
     */
    @FXML
    void placeAll(ActionEvent event){
        System.out.println("Place All called");

        /**
         * TODO: round_robin
         */
    }

    /**
     * r
     * @param event
     */
    @FXML
    void reinforce(ActionEvent event){
        System.out.println("reinforce called");
        String countryName = reinforceCountryName.getText();
        String num = reinforceNum.getText();
        /**
         * TODO:
         */
    }

    @FXML
    void fortify(ActionEvent event){
        System.out.println("fortify called");
        String fromCountryName = fortifyFrom.getText();
        String toCountryName = fortifyTo.getText();
        String num = fortifyNum.getText();
        /**
         * TODO:
         */
    }

    @FXML
    void fortifyNone(ActionEvent event){
        System.out.println("fortifyNone called");

        /**
         * TODO:
         */
    }

    @FXML
    void detectEnter(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            // do what is to do
            System.out.println("Enter Key Pressed");
            String commandStr = commandLine.getText();
            System.out.println("Your command: " + commandStr);
            commandLine.clear();
            CommandService commandService = new CommandService();
            String returnMsg = commandService.processCommand(commandStr);
            infoTextView.setText(returnMsg);
        }
    }

    public MapController(){
        colorPicker = new ColorController();
        mapEditorService = new MapEditorService();
        // default empty graph before loaded
        this.mapGraph = new MapGraph();
        this.mapGraphObserver = new MapGraphObserver(mapGraph);
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
                double x = mapPane.lookup("#continentListLabel").getLayoutX();
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
                mapPane.getChildren().remove(mapPane.lookup("#Continent" + continent.getContinentName()));
                mapPane.getChildren().remove(mapPane.lookup("#ContinentLabel" + continent.getContinentName()));
                //remove all the remaining continent rectangles & texts on mapPane
                for (Continent c: mapGraph.getContinentList()) {
                    mapPane.getChildren().remove(mapPane.lookup("#Continent" + c.getContinentName()));
                    mapPane.getChildren().remove(mapPane.lookup("#ContinentLabel" + c.getContinentName()));
                }

                //reload the continent List
                int i = 1;
                for (Continent c: mapGraph.getContinentList()) {
                    //create continent rectangle and text
                    Rectangle continentRectangle = new Rectangle(60, 20, c.getColor());
                    continentRectangle.setId("Continent" + c.getContinentName());
                    Text text = new Text(c.getContinentName() + ": " + c.getArmyValue());
                    text.setId("ContinentLabel" + c.getContinentName());

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
                // TODO: circle.setId(country.getCountryName());
                //Now Fake
                circle.setId("FakeId");
                circle.setCursor(HAND);

                // TODO: Label label = new Label(country.getCountryName() + ":" + "Fake Player Name");
                Label label = new Label("Country Name\n" + "Player Name\n" + "Army #");
                // TODO: label.setId(country.getCountryName() + "Text");
                label.setId("FakeId" + "Text");
                label.setLayoutX(250 - 20);
                label.setLayoutY(250 - 60);

                circle.setOnMouseDragged((t) -> {
                    // update Circle View's location
                    circle.setCenterX(t.getX());
                    circle.setCenterY(t.getY());
                    label.setLayoutX(t.getX() - 20);
                    label.setLayoutY(t.getY() - 60);
                });

                circle.setOnMouseClicked((t) -> {
                    // update the dragged country's location info
                    System.out.println("Country's new location set.");
                    country.setCoordinator(t.getX(), t.getY());
                });

                mapPane.getChildren().addAll(circle, label);

            }else if(action == "delete"){
                System.out.println("The Country Delete");
                mapPane.getChildren().remove(mapPane.lookup("#FakeId"));
                mapPane.getChildren().remove(mapPane.lookup("#FakeId" + "Text"));
            }
        }

        @Override
        public void updateConnection(String action, Connection connection) {
            if(action == "add"){
                System.out.println("The Connection Add");
                /**
                 * TODO:
                 * Country country1 = connection.getCountry1();
                 * Country country2 = connection.getCountry2();
                 * Point2D pt1 = country1.getCoordinator();
                 * Point2D pt2 = country2.getCoordinator();
                 * String lineId = country1.getCountryName() + country2.getCountryName();
                 */

                // Two Fake Point2D data here
                Point2D pt1 = new Point2D(300, 500);
                Point2D pt2 = new Point2D(350, 450);
                // Fake lineId
                String lineId = "FakeLineId";

                Line line = new Line();
                line.setId(lineId);
                line.setStartX(pt1.getX());
                line.setStartY(pt1.getY());
                line.setEndX(pt2.getX());
                line.setEndY(pt2.getY());
                line.setStroke(Color.rgb(95,103,105));
                line.toBack();

                mapPane.getChildren().add(line);

            } else if(action == "delete"){
                System.out.println("The Connection Delete");

                /**
                 * TODO:
                 * Country country1 = connection.getCountry1();
                 * Country country2 = connection.getCountry2();
                 * String lineId = country1.getCountryName() +  country2.getCountryName();
                 */

                // Now remove the connection by a fake Id
                mapPane.getChildren().remove(mapPane.lookup("#FakeLineId"));
            }
        }
    }
}