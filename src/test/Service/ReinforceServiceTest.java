package Service;

import model.Country;
import model.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.GamePlayerService;
import service.MapEditorService;
import service.ReinforceService;

import java.util.ArrayList;
import java.util.List;

public class ReinforceServiceTest {

    private MapEditorService mapEditorService;
    private ReinforceService reinforceService;

    /**
     * test initial action method
     */
    @Before
    public void initial(){
        reinforceService = new ReinforceService();
        mapEditorService = new MapEditorService();
    }

    /**
     * reinforce action test
     */
    @Test
    public void reinforceTest(){
        mapEditorService.editMap("/Users/siming/Desktop/soen6441/Domination/maps/risk.map");

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

        String countryName = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        Assert.assertEquals("3",GamePlayerService.playerList.get(0).getArmyValue().toString());
        Assert.assertEquals("0",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        reinforceService.reinforce(countryName,"2");
        Assert.assertEquals("2",MapEditorService.mapGraph.getCountryList().get(0).getArmyValue().toString());
        Assert.assertEquals("1",GamePlayerService.playerList.get(0).getArmyValue().toString());

    }
}
