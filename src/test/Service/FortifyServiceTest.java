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
    }

    /**
     * fortify action test
     */
    @Test
    public void reinforceTest() {
        mapEditorService.editMap("/Users/wujiaqi/soen6441/risk.map");

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

        player1.setArmyValue(3);
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

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        String fromCountry = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        String toCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();

        MapEditorService.mapGraph.getCountryList().get(0).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(0);

        Assert.assertEquals("5",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue());
        Assert.assertEquals("0",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue());

        fortifyService.fortify(fromCountry,toCountry,"2");
        Assert.assertEquals("3",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue());
        Assert.assertEquals("2",MapEditorService.mapGraph.getCountryList().get(1).getArmyValue());

    }
}