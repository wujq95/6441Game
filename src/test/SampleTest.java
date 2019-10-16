import model.Continent;
import model.Country;
import model.GamePlayer;
import model.MapGraph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import service.GamePlayerService;
import service.MapEditorService;
import service.ReinforceService;

import java.util.ArrayList;
import java.util.List;


public class SampleTest {

    @Before
    public void startUp(){
        String filename="";
        MapEditorService mapEditorService = new MapEditorService();
        mapEditorService.editMap(filename);
    }

    @Test
    public void TestMapValidate(){
        assertEquals(true,MapValidation());
    }

    public boolean MapValidation(){
        MapEditorService mapEditorService = new MapEditorService();
        /*Continent continent = new Continent();
        MapEditorService.mapGraph = new MapGraph();
        List<Continent> continentList = new ArrayList<>();
        continentList.add(continent.setContinentName(Aisa));
        Country country1 = new Country();
        Country country2 = new Country();ß
        MapEditorService.mapGraph.setCountryList();*/
        boolean Msg;
        Msg= mapEditorService.validateMap();
        return Msg;
    }

    @Test
    public void TestSaveMap(){
        assertEquals(true,SaveMapValidation());
    }

    public boolean SaveMapValidation(){
        MapEditorService mapEditorService = new MapEditorService();
        boolean result=false;
        String fileName="";//
        String Msg;
        Msg=mapEditorService.saveMap(fileName);
        if(Msg.equals("saveMap success")){
            result=true;
        }
        return result;
    }

    @Test
    public void TestShowMap(){
        assertEquals(true,ShowMapValidation());
    }

    public boolean ShowMapValidation(){
        MapEditorService mapEditorService = new MapEditorService();
        boolean result = false;
        String fileName="",Msg;
        Msg= mapEditorService.saveMap(fileName);
        if (Msg.equals(fileName.startsWith("The"))){
            result=true;
        }

        return  result; }

    @Test
    public void TestEditMap(){
        assertEquals(true,EditMapValidation());
    }

    public boolean EditMapValidation(){
        MapEditorService mapEditorService = new MapEditorService();
        boolean result = false;
        String fileName="",Msg;
        Msg= mapEditorService.saveMap(fileName);
        if (Msg.equals(fileName.endsWith("The"))){
            result=true;
        }

        return  result;
    }

    /*@Test
    public void TestContinentValidate(){
        assertEquals("Continent Validate successfully",true,ContinentValidation());
    }

    public boolean ContinentValidation(){
        MapEditorService mapEditorService = new MapEditorService();
        Country country = new Country();
        List<Country> country1;
        List<Country> country2;
        country1=country.();
        country2=country.getNeighbours();
        boolean Msg=false;
        Msg= mapEditorService.checkIfConnected(country1,country2);
        return Msg;
    }*/

    @Test
    public void TestArmiesNum(){
        assertEquals(true,ArmiesNumValidation());
    }

    public boolean ArmiesNumValidation(){
        boolean ArmiesNum=false;
        GamePlayerService gamePlayer = new GamePlayerService();
        ArmiesNum=gamePlayer.checkPlayerNum();
        return ArmiesNum;
    }

    @Test
    public void TestArmiesNum2(){ assertEquals(true,PutAllArmies());

    }

    public boolean PutAllArmies(){
        GamePlayer gamePlayer = new GamePlayer();
        GamePlayerService gamePlayerService = new GamePlayerService();
        boolean result = gamePlayerService.checkPutAll(gamePlayer);
        return result;

    }

    @Test
    public void TestPlaceArmy(){assertEquals(true,PlaceArmyValidation());}

    public boolean PlaceArmyValidation(){
        GamePlayerService gamePlayerService = new GamePlayerService();
        Country country = new Country();
        boolean result = false;
        String CountryName="",Msg;
        CountryName = country.getCountryName();
        Msg = gamePlayerService.placeOneArmy(CountryName);
        if (Msg.equals(CountryName.endsWith("success"))){
            result=true;
        }

        return  result;
    }

    @Test
    public void TestPlaceAll(){
        assertEquals(true,PlaceAllValidation());
    }

    public boolean PlaceAllValidation(){
        GamePlayerService gamePlayerService = new GamePlayerService();
        String Msg;
        boolean result =false;
        Msg=gamePlayerService.placeAll();
        if(Msg.endsWith("success")){
            result = true;
        }
        return result;
    }

    @Test
    public void TestReinforce(){
        assertEquals(true,ReinforceValidation());
    }

    public boolean ReinforceValidation()
    {   String countryName,Msg;
        ReinforceService reinforceService = new ReinforceService();
        Country country = new Country();
        countryName= country.getCountryName();
       Msg = reinforceService.reinforce(countryName,"3");
       boolean result=false;
       if (Msg.endsWith("success")){
           result = true;
       }
       return result;
    }
}

