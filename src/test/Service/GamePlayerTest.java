package Service;

import com.sun.javafx.collections.MappingChange;
import model.Country;
import model.GamePlayer;
import model.MapGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.ArrayList;
import java.util.List;

public class GamePlayerTest {

    MapEditorService mapEditorService;
    private GamePlayerService gamePlayerService;

    @Before
    public void initial(){

        gamePlayerService = new GamePlayerService();
        mapEditorService = new MapEditorService();
    }

    @Test
    public void addPlayerTest(){

        String commandMsg01 = "gameplayer -add test01 -add test02";
        String[] arguments01 = commandMsg01.split( " ");
        String msg01 = gamePlayerService.gamePlayerAction(arguments01);
        Assert.assertEquals("gameplayer action success",msg01);

        String commandMsg02 = "gameplayer -add test02";
        String[] arguments02 = commandMsg02.split( " ");
        String msg02 = gamePlayerService.gamePlayerAction(arguments02);
        Assert.assertEquals("player name duplicate",msg02);

    }

    @Test
    public void removePlayerTest(){
        String commandMsg01 = "gameplayer -add test01 -add test02 -add test03";
        String[] arguments01 = commandMsg01.split( " ");
        gamePlayerService.gamePlayerAction(arguments01);

        String commandMsg02 = "gameplayer -remove test02";
        String[] arguments02 = commandMsg02.split( " ");
        String msg02 = gamePlayerService.gamePlayerAction(arguments02);
        Assert.assertEquals("gameplayer action success",msg02);

        String commandMsg03 = "gameplayer -add test03 -add test04 -remove test01";
        String[] arguments03 = commandMsg03.split( " ");
        String msg03 = gamePlayerService.gamePlayerAction(arguments03);
        Assert.assertEquals("gameplayer action success",msg02);

        String commandMsg04  = "gameplayer -remove test06";
        String[] arguments04 = commandMsg04.split(" ");
        String msg04 = gamePlayerService.gamePlayerAction(arguments04);
        Assert.assertEquals("player name can not be found",msg04);
    }

    @Test
    public void calReinNumTest(){
        List<GamePlayer>  playerList = new ArrayList<GamePlayer>();
        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

        player1.setArmyValue(0);
        player2.setArmyValue(1);
        player3.setArmyValue(5);

        mapEditorService.editMap("/Users/wujiaqi/soen6441/risk.map");
        List<Country> countryList1 = new ArrayList<Country>();

        countryList1.add( MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add( MapEditorService.mapGraph.getCountryList().get(1));
        player1.setCountryList(countryList1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(3));
        player2.setCountryList(countryList2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);

        MapEditorService.mapGraph.getCountryList().get(0).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(1).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(2).setPlayer(player2);
        MapEditorService.mapGraph.getCountryList().get(3).setPlayer(player3);
        MapEditorService.mapGraph.getCountryList().get(4).setPlayer(player3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        gamePlayerService.calReinArmyNum();

        Assert.assertEquals("4",GamePlayerService.playerList.get(0).getArmyValue().toString());
        Assert.assertEquals("4",GamePlayerService.playerList.get(1).getArmyValue().toString());
        Assert.assertEquals("8",GamePlayerService.playerList.get(2).getArmyValue().toString());

    }
}
