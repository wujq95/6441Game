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
import javafx.scene.layout.Pane;
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
import java.util.ArrayList;
import java.util.Arrays;
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
     * observer of current game information
     */
    private GameInfoObserver gameInfoObserver;

    /**
     * color palette for continents
     */
    private ColorController colorPicker;

    /**
     * mapEditorService class contains all edit mapGraph methods
     */
    public static MapEditorService mapEditorService;

    public static GamePlayerService gamePlayerService;

    public static ReinforceService reinforceService;

    public static FortifyService fortifyService;

    public static AttackService attackService;

    public static CardService cardService;

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

    @FXML
    public Label actionTakenLabel;

    @FXML
    public Pane cardExchangePane;

    @FXML
    public Label currentCardPlayerLabel;

    @FXML
    public Label cardListLabel;

    @FXML
    public Label attackerDice;

    @FXML
    public Label defenderDice;

    private void loadCardsExchangeView(GamePlayerService gamePlayerService){
        GamePlayer currentPlayer = gamePlayerService.getCurrentPlayer();
        currentPlayer.getCardList();

        currentCardPlayerLabel.setText(currentPlayer.getPlayerName());
        //cardListLabel.setText(cardsList);

        cardExchangePane.setVisible(true);
    }

    private void hideCardsExchangeView(){
        cardExchangePane.setVisible(false);
    }

    private void loadGameInformation(GamePlayerService gamePlayerService){
        int phaseNum  = gamePlayerService.checkPhase;

        String phaseName = "Map Editor";
        String currentPlayerName = "None";
        String actionTaken = "None";
        String attackerDiceOutcome = "None";
        String defenderDiceOutcome = "None";

        switch (phaseNum){
            case 0:
                phaseName = "Map Editor";
                break;
            case 1:
                phaseName = "Start Up";
                currentPlayerName = gamePlayerService.getCurrentPlayerName();
                break;
            case 2:
                phaseName = "Reinforcement";
                currentPlayerName = reinforceService.getCurrentPlayerName();
                loadCardsExchangeView(gamePlayerService);
                break;
            case 3:
                phaseName = "Fortification";
                currentPlayerName = fortifyService.getCurrentPlayerName();
                break;
            case 4:
                phaseName = "Attack";
                currentPlayerName = fortifyService.getCurrentPlayerName();
                attackerDiceOutcome = attackService.getFromDice();
                defenderDiceOutcome = attackService.getToDice();
                hideCardsExchangeView();
                break;
            case 5:
                phaseName = "Game Stop";
                break;
        }

        phaseLabel.setText(phaseName);
        currentPlayerLabel.setText(currentPlayerName);
        actionTakenLabel.setText(actionTaken);
        attackerDice.setText(attackerDiceOutcome);
        defenderDice.setText(defenderDiceOutcome);


    }

    /**
     * load the mapGraph on mapPane
     * @param mGraph user interface loading map
     */
    private void loadMapGraph(MapGraph mGraph){
        mapPane.getChildren().clear();

        // location for game player list title and continent list title
        double x = mapPane.getLayoutBounds().getMaxX() - 400;
        double y = 20;

        // Load all game players
        List<GamePlayer> gamePlayerList = GamePlayerService.playerList;

        // set Game Player List Title
        Text title = new Text("Players World Domination View");
        title.setX(x);
        title.setY(y);
        mapPane.getChildren().addAll(title);

        // render all game players info
        int i = 0;
        for (GamePlayer gamePlayer : gamePlayerList) {
            // set game player name location
            y = i * 50 + 50;

            String playerName = gamePlayer.getPlayerName();
            String armyValue = gamePlayer.getArmyValue().toString();
            String controlledContinentsNum = "0";
            String percentageOnMap = "0%";

            List<String> line = Arrays.asList(playerName, armyValue, controlledContinentsNum, percentageOnMap);
            // Text text = new Text((gamePlayer.getPlayerName() + ": " + gamePlayer.getArmyValue()));
            Text text = new Text(line.toString());
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

                //load map graph
                loadMapGraph(mapGraph);

                //load game info
                loadGameInformation(gamePlayerService);

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
//            loadMapGraph(MapEditorService.mapGraph);
//            loadGameInfo(gamePlayerService);
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
        attackService = new AttackService();
        cardService = new CardService();

        // default empty graph before loaded
        this.mapGraph = new MapGraph();
        this.mapGraphObserver = new MapGraphObserver(MapEditorService.mapGraph);
        this.gameInfoObserver = new GameInfoObserver(gamePlayerService, attackService, cardService,reinforceService,fortifyService);
    }

    public class GameInfoObserver extends Observer{

        public GameInfoObserver(GamePlayerService gamePlayerService, AttackService attackService, CardService cardService, ReinforceService reinforceService,FortifyService fortifyService){
            this.gamePlayerService = gamePlayerService;
            this.gamePlayerService.attach(this);

            this.attackService = attackService;
            this.attackService.attach(this);

            this.cardService = cardService;
            this.cardService.attach(this);

            this.reinforceService = reinforceService;
            this.reinforceService.attach(this);

            this.fortifyService =fortifyService;
            this.fortifyService.attach(this);
        }

        @Override
        public void update(){
            System.out.println("Game Information Reloaded");
            loadGameInformation(gamePlayerService);
            System.out.println("Map Pane Reloaded");
            loadMapGraph(MapEditorService.mapGraph);
        }
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
        public void update(){
            System.out.println("MapGraph Reloaded.");
            loadMapGraph(MapEditorService.mapGraph);
        }
    }
}