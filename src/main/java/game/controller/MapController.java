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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import model.*;
import service.*;

import java.io.File;
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
     * GamePlayerService class contains all GamePlayer related methods
     */
    private static GamePlayerService gamePlayerService;

    private static ReinforceService reinforceService;

    private static FortifyService fortifyService;

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
     * anchor pane containing all user interface actions to the map graph
     */
    @FXML
    public AnchorPane actionPane;

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

    @FXML
    public Label phaseLabel;

    @FXML
    public Label currentPlayerLabel;

    private void loadGameInfo(GamePlayerService gamePlayerService){
        int phaseNum = gamePlayerService.checkPhase;
        switch (phaseNum){
            case 0:
                phaseLabel.setText("Map Editor");
                currentPlayerLabel.setText("None");
                break;
            case 1:
                phaseLabel.setText("Startup");
                if(gamePlayerService.playerList.size() > 0){
                    GamePlayer currentPlayer = gamePlayerService.playerList.get(gamePlayerService.choosePlayer);
                    currentPlayerLabel.setText(currentPlayer.getPlayerName());
                }
                break;
            case 2:
                phaseLabel.setText("Reinforcement");
                if(gamePlayerService.playerList.size() > 0){
                    GamePlayer currentPlayer = gamePlayerService.playerList.get(reinforceService.playerNum);
                    currentPlayerLabel.setText(currentPlayer.getPlayerName());
                }
                break;
            case 3:
                phaseLabel.setText("Fortification");
                if(gamePlayerService.playerList.size() > 0){
                    GamePlayer currentPlayer = gamePlayerService.playerList.get(fortifyService.playerNum);
                    currentPlayerLabel.setText(currentPlayer.getPlayerName());
                }
                break;
            case 4:
                phaseLabel.setText("Game Stop");
                currentPlayerLabel.setText("None");
                break;
        }

    }

    /**
     * load the mapGraph on mapPane
     * @param mGraph user interface loading map
     */
    private void loadMapGraph(MapGraph mGraph){
        mapPane.getChildren().clear();

        // location for game player list title and continent list title
        double x = mapPane.getLayoutBounds().getMaxX() - 300;
        double y = 20;

        // Load all game players
        List<GamePlayer> gamePlayerList = GamePlayerService.playerList;

        // set Game Player List Title
        Text title = new Text("Game Players");
        title.setX(x);
        title.setY(y);
        mapPane.getChildren().addAll(title);

        // render all game players info
        int i = 0;
        for (GamePlayer gamePlayer : gamePlayerList) {
            // set game player name location
            y = i * 50 + 50;
            Text text = new Text((gamePlayer.getPlayerName() + ": " + gamePlayer.getArmyValue()));
            text.setX(x);
            text.setY(y);

            mapPane.getChildren().addAll(text);
            i++;
        }

        // Load all continents
        List<Continent> continentList = mGraph.getContinentList();

        // set Continent List Title
        title = new Text("Continent List");
        x = mapPane.getLayoutBounds().getMaxX() - 150;
        y = 20;
        title.setX(x);
        title.setY(y);
        mapPane.getChildren().addAll(title);

        i = 0;
        for (Continent continent:continentList) {
            Rectangle continentRectangle = new Rectangle(60, 20, continent.getColor());
            continentRectangle.setId("continent" + continent.getContinentName());
            Text text = new Text(continent.getContinentName() + ": " + continent.getArmyValue());
            text.setId("continentLabel" + continent.getContinentName());

            //set rectangle and text position
            x = mapPane.getLayoutBounds().getMaxX() - 150;
            y = i * 50 + 50;
            continentRectangle.setX(x);
            continentRectangle.setY(y);
            text.setX(x);
            text.setY(y - 5);

            mapPane.getChildren().addAll(continentRectangle, text);
            i++;
        }

        // Load all countries
        List<Country> countryList = mGraph.getCountryList();
        for (Country country: countryList) {
            x = country.getX();
            y = country.getY();
            x = x * 1.3;
            y = y * 1.3;
            Color countryColor = country.getParentContinent().getColor();
            Circle circle = new Circle(x, y, 15, countryColor);

            GamePlayer player = country.getPlayer();
            String playerName = (player == null)? "": player.getPlayerName();
            Label label = new Label(country.getCountryName() + "\n" + country.getArmyValue() + "\n" + playerName);
            label.setFont(new Font(10));
            label.toFront();
            circle.toBack();
            //label.setId(country.getCountryName());
            label.setLayoutX(x - 20);
            label.setLayoutY(y - 20);

            mapPane.getChildren().addAll(circle, label);
        }

        // Load all connections
        List<Connection> connectionList = mGraph.getConnections();
        for(Connection connection: connectionList){
            Country country1 = connection.getCountry1();
            Country country2 = connection.getCountry2();

            Point2D pt1 = country1.getCoordinator();
            Point2D pt2 = country2.getCoordinator();
            String lineId = country1.getCountryName() + country2.getCountryName();

            Line line = new Line();
            line.setId(lineId);
            line.setStartX(pt1.getX() * 1.3);
            line.setStartY(pt1.getY() * 1.3);
            line.setEndX(pt2.getX() * 1.3);
            line.setEndY(pt2.getY() * 1.3);
            line.setStroke(Color.rgb(95,103,105));
            line.toBack();

            mapPane.getChildren().add(line);
        }
    }

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

                //load map graph
                loadMapGraph(mapGraph);

                //load game info
                loadGameInfo(gamePlayerService);

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
            mapEditorService.saveMap(file.getName());
        }
    }

    /**
     * send the command line, and display the return info
     * @param event mouse click event
     */
    @FXML
    void detectEnter(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            System.out.println("Enter Key Pressed");
            String commandStr = commandLine.getText();
            System.out.println("Your command: " + commandStr);
            commandLine.clear();
            CommandService commandService = new CommandService();
            String returnMsg = commandService.processCommand(commandStr);
            infoTextView.setText(returnMsg);
            loadMapGraph(MapEditorService.mapGraph);
            loadGameInfo(gamePlayerService);
        }
    }

    /**
     * MapController constructor
     */
    public MapController(){
        colorPicker = new ColorController();
        mapEditorService = new MapEditorService();
        gamePlayerService = new GamePlayerService();
        reinforceService = new ReinforceService();
        fortifyService = new FortifyService();

        // default empty graph before loaded
        this.mapGraph = new MapGraph();
        // this.mapGraphObserver = new MapGraphObserver(mapGraph);
        this.mapGraphObserver = new MapGraphObserver(MapEditorService.mapGraph);
    }

    /**
     * MapGraphObserver
     */
    public class MapGraphObserver extends Observer{
        /**
         * mapGraphObserver constructor
         * @param mapGraph map graph
         */
        public MapGraphObserver(MapGraph mapGraph){
            this.mapGraph = mapGraph;
            this.mapGraph.attach(this);
        }

        /**
         * reload the mapGraph
         */
        @Override
        public void updateMapGraph(){
            System.out.println("MapGraph Reloaded.");
            loadMapGraph(MapEditorService.mapGraph);
        }

        /**
         * add the newly added country to mapPane
         * @param action "add"
         * @param country contains the information of the newly added country
         */
        @Override
        public void updateCountry(String action, Country country){
            if(action == "add"){
                System.out.println("The Country Add");
                /**
                 * TODO: country.getContinent().getColor();
                 */
                // Color countryColor = country.getParentContinent().getColor();
                Color countryColor = Color.TAN;
                double x = 500; //country default x coordinate
                double y = 500; //country default y coordinate

                //Point2D center = new Point2D(x, y); //default position on scene
                Circle circle = new Circle(x, y, 15, countryColor);
                // TODO: circle.setId(country.getCountryName());
                circle.setCursor(HAND);

                Label label = new Label(country.getCountryName() + "\n" + "Player Name\n" + country.getArmyValue());
                //label.setId(country.getCountryName());
                label.setLayoutX(x - 20);
                label.setLayoutY(y - 60);

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
                    //MapEditorService.mapGraph.setCountryCoordinates(String countryName, double x, double y);
                });

                mapPane.getChildren().addAll(circle, label);
            }
        }
    }
}