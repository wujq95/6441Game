package Service;

import model.Country;
import model.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AttackService;
import service.GamePlayerService;
import service.MapEditorService;

import java.util.ArrayList;
import java.util.List;

public class AttackServiceTest {

    private AttackService attackService;
    private MapEditorService mapEditorService;

    /**
     * test initial action method
     */
    @Before
    public void initial(){
        attackService = new AttackService();
        mapEditorService = new MapEditorService();
        mapEditorService.editMap("/Users/siming/Desktop/soen6441/Domination/maps/risk.map");
    }

    /**
     * attack move test
     */
    @Test
    public void attackMoveTest(){

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

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
        MapEditorService.mapGraph.getCountryList().get(0).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(1).setPlayer(player1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);
        MapEditorService.mapGraph.getCountryList().get(2).setPlayer(player2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);
        MapEditorService.mapGraph.getCountryList().get(3).setPlayer(player3);
        MapEditorService.mapGraph.getCountryList().get(4).setPlayer(player3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        AttackService.fromCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();
        AttackService.toCountry= MapEditorService.mapGraph.getCountryList().get(2).getCountryName();
        AttackService.fromDiceResultList.add(3);
        AttackService.fromDiceResultList.add(3);

        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(2).setArmyValue(0);

        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString(),"5");
        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(2).getArmyValue().toString(),"0");
        attackService.attackMove("3");
        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(1).getArmyValue().toString(),"2");
        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(2).getArmyValue().toString(),"3");
    }

    /**
     * attack test
     */
    @Test
    public void attackTest(){

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

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
        MapEditorService.mapGraph.getCountryList().get(0).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(1).setPlayer(player1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);
        MapEditorService.mapGraph.getCountryList().get(2).setPlayer(player2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);
        MapEditorService.mapGraph.getCountryList().get(3).setPlayer(player3);
        MapEditorService.mapGraph.getCountryList().get(4).setPlayer(player3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        AttackService.fromDiceNum=1;
        AttackService.toDiceNum = 1;

        AttackService.fromCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();
        AttackService.toCountry= MapEditorService.mapGraph.getCountryList().get(2).getCountryName();
        MapEditorService.mapGraph.getCountryList().get(1).setArmyValue(5);
        MapEditorService.mapGraph.getCountryList().get(2).setArmyValue(4);

        Integer firstCountryArmyValue  = MapEditorService.mapGraph.getCountryList().get(1).getArmyValue();
        Integer secondCountryArmyValue = MapEditorService.mapGraph.getCountryList().get(2).getArmyValue();
        Integer sum = firstCountryArmyValue+secondCountryArmyValue;
        Assert.assertEquals(sum.toString(),"9");
        attackService.attackProcess();

        firstCountryArmyValue  = MapEditorService.mapGraph.getCountryList().get(1).getArmyValue();
        secondCountryArmyValue = MapEditorService.mapGraph.getCountryList().get(2).getArmyValue();
        sum = firstCountryArmyValue+secondCountryArmyValue;
        Assert.assertEquals(sum.toString(),"8");
    }

    /**
     * no attack test
     */
    @Test
    public void noAttackTest(){
        attackService.noAttack();
        Assert.assertEquals(GamePlayerService.checkPhase,3);
    }

    /**
     * change player test
     */
    @Test
    public void changePlayerTest(){

        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

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
        MapEditorService.mapGraph.getCountryList().get(0).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(1).setPlayer(player1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);
        MapEditorService.mapGraph.getCountryList().get(2).setPlayer(player2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);
        MapEditorService.mapGraph.getCountryList().get(3).setPlayer(player3);
        MapEditorService.mapGraph.getCountryList().get(4).setPlayer(player3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        AttackService.fromCountry = MapEditorService.mapGraph.getCountryList().get(1).getCountryName();
        AttackService.toCountry= MapEditorService.mapGraph.getCountryList().get(2).getCountryName();

        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(2).getPlayer().getPlayerName(),"player02");
        attackService.changPlayer();
        Assert.assertEquals(MapEditorService.mapGraph.getCountryList().get(2).getPlayer().getPlayerName(),"player01");
    }

    /**
     * control continent test
     */
    @Test
    public void controlContinentTest(){


        GamePlayer player1 = new GamePlayer();
        GamePlayer player2 = new GamePlayer();
        GamePlayer player3 = new GamePlayer();

        player1.setPlayerName("player01");
        player2.setPlayerName("player02");
        player3.setPlayerName("player03");

        player1.setArmyValue(0);
        player2.setArmyValue(0);
        player3.setArmyValue(0);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        List<Country> countryList1 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(0));
        player1.setCountryList(countryList1);
        MapEditorService.mapGraph.getCountryList().get(0).setPlayer(player1);

        List<Country> countryList2 = new ArrayList<Country>();
        countryList1.add(MapEditorService.mapGraph.getCountryList().get(1));
        countryList2.add(MapEditorService.mapGraph.getCountryList().get(2));
        player2.setCountryList(countryList2);
        MapEditorService.mapGraph.getCountryList().get(1).setPlayer(player1);
        MapEditorService.mapGraph.getCountryList().get(2).setPlayer(player2);

        List<Country> countryList3 = new ArrayList<Country>();
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(3));
        countryList3.add(MapEditorService.mapGraph.getCountryList().get(4));
        player3.setCountryList(countryList3);
        MapEditorService.mapGraph.getCountryList().get(3).setPlayer(player3);
        MapEditorService.mapGraph.getCountryList().get(4).setPlayer(player3);

        GamePlayerService.playerList.add(player1);
        GamePlayerService.playerList.add(player2);
        GamePlayerService.playerList.add(player3);

        AttackService.fromCountry = MapEditorService.mapGraph.getCountryList().get(0).getCountryName();
        AttackService.toCountry= MapEditorService.mapGraph.getCountryList().get(1).getCountryName();

        GamePlayerService.choosePlayer=0;
        Assert.assertEquals(GamePlayerService.playerList.get(0).getControlledContinent().size(),0);
        attackService.dealControllContinent();
        Assert.assertEquals(GamePlayerService.playerList.get(0).getControlledContinent().size(),1);

    }

}


