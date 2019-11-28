package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
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
import observer.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


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
    public static CommandService commandService;

    public static MapEditorService mapEditorService;

    public static GamePlayerService gamePlayerService;

    public static ReinforceService reinforceService;

    public static FortifyService fortifyService;

    public static AttackService attackService;

    public static CardService cardService;

    public static TournamentService tournamentService;

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
    /**
     * Phase label displaying current phase name
     */
    @FXML
    public Label phaseLabel;
    /**
     * Current Player name will be demonstrated
     */
    @FXML
    public Label currentPlayerLabel;
    /**
     * Current Action label
     */
    @FXML
    public Label actionTakenLabel;
    /**
     * Dice result for attacker
     */
    @FXML
    public Pane cardExchangePane;

    @FXML
    public Label currentCardPlayerLabel;

    @FXML
    public Label cardListLabel;

    @FXML
    public Label attackerDice;
    /**
     * Dice result for defender
     */
    @FXML
    public Label defenderDice;

    /**
     * load the cards exchange view
     * @param gamePlayerService
     */
    private void loadCardsExchangeView(GamePlayerService gamePlayerService) {
        GamePlayer currentPlayer = gamePlayerService.getCurrentPlayer();
        List<Card> cardList = currentPlayer.getCardList();

        currentCardPlayerLabel.setText(currentPlayer.getPlayerName());

        if (cardList == null) {
            cardList = new LinkedList<>();
            cardListLabel.setText("zero cards");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Card card : cardList) {
                builder.append(card.name()).append(" ");
            }
            cardListLabel.setText(builder.toString());
        }

        if(CardService.notExchangeCards)
            cardExchangePane.setVisible(false);
        else
            cardExchangePane.setVisible(true);
    }

    private void hideCardsExchangeView() {
        cardExchangePane.setVisible(false);
    }

    /**
     * Load game information
     *
     * @param gamePlayerService gamerPlayerService instance initial
     */
    private void loadGameInformation(GamePlayerService gamePlayerService) {
        int phaseNum = gamePlayerService.checkPhase;

        String phaseName = "Map Editor";
        // String currentPlayerName = "None";
        GamePlayer currentPlayer = null;
        String currentPlayerInfo = "None";
        String actionTaken = "None";
        String attackerDiceOutcome = "None";
        String defenderDiceOutcome = "None";

        switch (phaseNum) {
            case 0:
                phaseName = "Map Editor";
                break;
            case 1:
                phaseName = "Start Up";
                //currentPlayerName = gamePlayerService.getCurrentPlayerName();
                currentPlayer = gamePlayerService.getCurrentPlayer();
                currentPlayerInfo = currentPlayer.getPlayerName() + "(" + currentPlayer.getStrategyName() + ")";
                break;
            case 2:
                phaseName = "Reinforcement";
                //currentPlayerName = reinforceService.getCurrentPlayerName();
                currentPlayer = gamePlayerService.getCurrentPlayer();
                currentPlayerInfo = currentPlayer.getPlayerName() + "(" + currentPlayer.getStrategyName() + ")";
                loadCardsExchangeView(gamePlayerService);
                break;
            case 3:
                phaseName = "Fortification";
                //currentPlayerName = fortifyService.getCurrentPlayerName();
                currentPlayer = gamePlayerService.getCurrentPlayer();
                currentPlayerInfo = currentPlayer.getPlayerName() + "(" + currentPlayer.getStrategyName() + ")";
                break;
            case 4:
                phaseName = "Attack";
                //currentPlayerName = fortifyService.getCurrentPlayerName();
                currentPlayer = gamePlayerService.getCurrentPlayer();
                currentPlayerInfo = currentPlayer.getPlayerName() + "(" + currentPlayer.getStrategyName() + ")";
                attackerDiceOutcome = attackService.getFromDice();
                defenderDiceOutcome = attackService.getToDice();
                hideCardsExchangeView();
                break;
            case 5:
                phaseName = "Game Stop";
                break;
        }

        phaseLabel.setText(phaseName);
        currentPlayerLabel.setText(currentPlayerInfo);
        actionTakenLabel.setText(actionTaken);
        attackerDice.setText(attackerDiceOutcome);
        defenderDice.setText(defenderDiceOutcome);


    }

    /**
     * load the mapGraph on mapPane
     *
     * @param mGraph user interface loading map
     */
    private void loadMapGraph(MapGraph mGraph) {
        mapPane.getChildren().clear();

        // location for game player list title and continent list title
        double x = mapPane.getLayoutBounds().getMaxX() - 550;
        double y = 20;

        // Load all game players
        List<GamePlayer> gamePlayerList = GamePlayerService.playerList;

        // set Game Player List Title
        Text title = new Text("Players World Domination View");
        title.setX(x);
        title.setY(y);
        mapPane.getChildren().addAll(title);

        Text hintTitle = new Text("Name, Strategy, #Army, OwnedContinents, #Country, %");
        hintTitle.setFont(new Font(10.0));
        hintTitle.setFill(Color.BURLYWOOD);
        hintTitle.setX(x);
        hintTitle.setY(y + 15);
        mapPane.getChildren().addAll(hintTitle);

        // render all game players info
        int i = 0;
        for (GamePlayer gamePlayer : gamePlayerList) {
            // set game player name location
            y = i * 50 + 50;

            String playerName = gamePlayer.getPlayerName();
            String strategyName = gamePlayer.getStrategyName();
            String armyValue = gamePlayer.getArmyValue().toString();
            String controlledContinents = gamePlayer.getControlledContinent().toString();
            controlledContinents = "\n" + controlledContinents;
            int countryNum = gamePlayer.getCountryList().size();
            String countryNumStr = Integer.toString(countryNum);
            int totalCountryNum = MapEditorService.mapGraph.getCountryList().size();
            double percentage = countryNum * 1.0 / totalCountryNum;
            DecimalFormat df = new DecimalFormat("##.##%");
            String percentageOnMap = df.format(percentage);

            List<String> line = Arrays.asList(playerName, strategyName, armyValue, controlledContinents, countryNumStr, percentageOnMap);
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
        for (Continent continent : continentList) {
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
        for (Country country : countryList) {
            x = country.getX();
            y = country.getY();
            x = x * 1.1;
            y = y * 1.1;
            Color countryColor = country.getParentContinent().getColor();
            Circle circle = new Circle(x, y, 15, countryColor);

            GamePlayer player = country.getPlayer();
            String playerName = (player == null) ? "" : player.getPlayerName();
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
        for (Connection connection : connectionList) {
            Country country1 = connection.getCountry1();
            Country country2 = connection.getCountry2();

            Point2D pt1 = country1.getCoordinator();
            Point2D pt2 = country2.getCoordinator();
            String lineId = country1.getCountryName() + country2.getCountryName();

            Line line = new Line();
            line.setId(lineId);
            line.setStartX(pt1.getX() * 1.1);
            line.setStartY(pt1.getY() * 1.1);
            line.setEndX(pt2.getX() * 1.1);
            line.setEndY(pt2.getY() * 1.1);
            line.setStroke(Color.rgb(95, 103, 105));
            line.toBack();

            mapPane.getChildren().add(line);
        }
    }

    /**
     * load map the map into MapGraph mapGraph data structure
     *
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
     *
     * @param event mouse click event
     */
    @FXML
    void saveMap(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(saveMapMenuItem.getParentPopup());
        if (file != null) {
            MapEditorService.mapGraph = this.mapGraph;
            System.out.println(file.getName());
            mapEditorService.saveMap(file.getName());
        }
    }

    /**
     * send the command line, and display the return info
     *
     * @param event mouse click event
     */
    @FXML
    void detectEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("Enter Key Pressed");
            String commandStr = commandLine.getText();
            System.out.println("Your command: " + commandStr);
            commandLine.clear();
            //commandService = new CommandService();
            String returnMsg = commandService.processCommand(commandStr);
            infoTextView.setText(returnMsg);
//            loadMapGraph(MapEditorService.mapGraph);
//            loadGameInfo(gamePlayerService);
        }
    }

    /**
     * MapController constructor
     */
    public MapController() {
        colorPicker = new ColorController();
        mapEditorService = new MapEditorService();
        gamePlayerService = new GamePlayerService();
        reinforceService = new ReinforceService();
        fortifyService = new FortifyService();
        attackService = new AttackService();
        cardService = new CardService();
        tournamentService = new TournamentService();
        commandService = new CommandService();

        // default empty graph before loaded
        this.mapGraph = new MapGraph();
        this.mapGraphObserver = new MapGraphObserver(MapEditorService.mapGraph);
        this.gameInfoObserver = new GameInfoObserver(commandService, gamePlayerService, attackService, cardService, reinforceService, fortifyService, tournamentService);
    }

    /**
     * sub-class of observer GameInObserver constructor
     */
    public class GameInfoObserver extends Observer {

        public GameInfoObserver(CommandService commandService, GamePlayerService gamePlayerService, AttackService attackService, CardService cardService,
                                ReinforceService reinforceService, FortifyService fortifyService, TournamentService tournamentService) {
            this.commandService = commandService;
            this.commandService.attach(this);

            this.gamePlayerService = gamePlayerService;
            this.gamePlayerService.attach(this);
            this.gamePlayerService.setObserver(this);

            this.attackService = attackService;
            this.attackService.attach(this);

            this.cardService = cardService;
            this.cardService.attach(this);

            this.reinforceService = reinforceService;
            this.reinforceService.attach(this);

            this.fortifyService = fortifyService;
            this.fortifyService.attach(this);

            this.tournamentService = tournamentService;
            this.tournamentService.setObserver(this);
            this.tournamentService.attach(this);

        }

        /**
         * View layer required update function with GameInformation and Map loading
         */
        @Override
        public void update(Observable o) {
            System.out.println("Game Information Reloaded");
            loadGameInformation(gamePlayerService);
            System.out.println("Map Pane Reloaded");
            loadMapGraph(MapEditorService.mapGraph);
        }
    }

    /**
     * MapGraphObserver
     */
    public class MapGraphObserver extends Observer {
        /**
         * mapGraphObserver constructor
         *
         * @param mapGraph map graph
         */
        public MapGraphObserver(MapGraph mapGraph) {
            this.mapGraph = mapGraph;
            this.mapGraph.attach(this);
        }

        /**
         * reload the mapGraph
         */
        @Override
        public void update(Observable o) {
            System.out.println("MapGraph Reloaded.");
            loadMapGraph(MapEditorService.mapGraph);
        }
    }
}