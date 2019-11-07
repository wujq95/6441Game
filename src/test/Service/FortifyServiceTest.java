package Service;

import model.Country;
import model.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.FortifyService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.ArrayList;
import java.util.List;

public class FortifyServiceTest {

    private MapEditorService mapEditorService;
    private FortifyService fortifyService;

    /**
     * test initial action method
     */
    @Before
    public void initial(){
        fortifyService = new FortifyService();
        mapEditorService = new MapEditorService();
        mapEditorService.editMap("/Users/siming/Desktop/soen6441/Domination/maps/risk.map");
    }

    /**
     * fortify action test
     */
    @Test
    public void fortifyTest() {

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

        player1.setArmyValue(0);
        player2.setArmyValue(0);
        player3.setArmyValue(0);

        for(int i=GamePlayerService.playerList.size()-1;i>=0;i--) {
            GamePlayerService.playerList.remove(i);
        }

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));
        player1.setCountryList(countryList1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        String fromCountry = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        String toCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        Assert.assertEquals("5",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        Assert.assertEquals("0",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString());

        GamePlayerService.choosePlayer=0;

        fortifyService.fortify(fromCountry,toCountry,"2");
        Assert.assertEquals("3",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        Assert.assertEquals("2",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString());

    }

    /**
     * fortify none test
     */
    @Test
    public void fortifyNoneTest(){

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        GamePlayerService.choosePlayer=0;

        Assert.assertEquals("5",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        Assert.assertEquals("0",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString());


        fortifyService.fortifyNone();
        Assert.assertEquals("5",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        Assert.assertEquals("0",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString());


    }

    /**
     * fortify phase change test
     */
    @Test
    public void fortifyPhaseTest() {

        for(int i=GamePlayerService.playerList.size()-1;i>=0;i--) {
            GamePlayerService.playerList.remove(i);
        }

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setArmyValue(0);
        player2.setArmyValue(0);
        player3.setArmyValue(0);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));
        player1.setCountryList(countryList1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        String fromCountry = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        String toCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();

        fortifyService.fortify(fromCountry,toCountry,"2");
        Assert.assertEquals(GamePlayerService.checkPhase,2);
    }

    /**
     * fortify phase change test
     */
    @Test
    public void fortifyNonePhaseTest() {

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        fortifyService.fortifyNone();
        Assert.assertEquals(GamePlayerService.checkPhase,2);
    }

    /**
     * player test
     */
    @Test
    public void playerTest(){

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setArmyValue(0);
        player2.setArmyValue(0);
        player3.setArmyValue(0);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));
        player1.setCountryList(countryList1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        String fromCountry = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        String toCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();


        GamePlayerService.choosePlayer=0;

        fortifyService.fortify(fromCountry,toCountry,"2");
        Assert.assertEquals(GamePlayerService.choosePlayer.toString(),"1");
    }

    /**
     * stop test
     */
    @Test
    public void stopTest(){

        fortifyService.stop();
        Assert.assertEquals(GamePlayerService.checkPhase,5);
    }
}
