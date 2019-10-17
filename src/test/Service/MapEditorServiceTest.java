package Service;

import model.Continent;
import model.Country;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.MapEditorService;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class MapEditorServiceTest {


    private MapEditorService mapEditorService;

    /**
     * Initial map editor service
     */
    @Before
    public void setUp() {
        mapEditorService = new MapEditorService();
    }

    /**
     * test editMap
     */
    @Test
    public void testEditMap() {
        String fileName = "/Applications/Domination/maps/ameroki.map";
        String returnMsg = mapEditorService.editMap(fileName);
        Assert.assertEquals(returnMsg, "load map from file /Applications/Domination/maps/ameroki.map success");

        fileName = "/Applications/Domination/maps/empty.map";
        returnMsg = mapEditorService.editMap(fileName);
        File mapFile = new File(fileName);
        Assert.assertEquals(mapFile.isFile(), true);
        Assert.assertEquals(returnMsg, "create new file success");
        mapFile.delete();
    }

    /**
     * test showMap
     */
    @Test
    public void testShowMap() {
        testEditMap();
        String result = mapEditorService.showMap();
        Assert.assertEquals(result,
                "The continents are azio, ameroki, utropa, amerpoll, afrori, ulstrailia,\n" +
                        "countries include country vagnagale,\n" +
                        " and vagnagale's neighbours are argentina, pero, heaurt, \n" +
                        "country heal,\n" +
                        " and heal's neighbours are china, Quebeck, \n" +
                        "country south_afrori,\n" +
                        " and south_afrori's neighbours are tungu, jacuncail, japan, \n" +
                        "country vinenlant,\n" +
                        " and vinenlant's neighbours are alberta, duiestie, \n" +
                        "country yazteck,\n" +
                        " and yazteck's neighbours are worrick, kongrolo, middle_east, czeck, \n" +
                        "country western_ulstrailia,\n" +
                        " and western_ulstrailia's neighbours are jacuncail, eastern_ulstarilia, new_guinia, \n" +
                        "country ireland,\n" +
                        " and ireland's neighbours are ihesia, senadlavin, \n" +
                        "country egypt,\n" +
                        " and egypt's neighbours are heaurt, siberia, north_afrori, \n" +
                        "country maganar,\n" +
                        " and maganar's neighbours are east_afrori, ihesia, \n" +
                        "country heaurt,\n" +
                        " and heaurt's neighbours are pero, vagnagale, egypt, \n" +
                        "country middle_east,\n" +
                        " and middle_east's neighbours are yazteck, china, kancheria, kongrolo, \n" +
                        "country jacuncail,\n" +
                        " and jacuncail's neighbours are south_afrori, new_guinia, western_ulstrailia, eastern_ulstarilia, \n" +
                        "country eastern_ulstarilia,\n" +
                        " and eastern_ulstarilia's neighbours are jacuncail, western_ulstrailia, \n" +
                        "country sluci,\n" +
                        " and sluci's neighbours are china, kancheria, afganistan, \n" +
                        "country alberta,\n" +
                        " and alberta's neighbours are western_united_states, central_ameroki, vinenlant, teramar, duiestie, \n" +
                        "country north_afrori,\n" +
                        " and north_afrori's neighbours are egypt, east_afrori, tungu, \n" +
                        "country argentina,\n" +
                        " and argentina's neighbours are northern_utropa, vagnagale, \n" +
                        "country ihesia,\n" +
                        " and ihesia's neighbours are ireland, western_utropa, maganar, senadlavin, \n" +
                        "country japan,\n" +
                        " and japan's neighbours are south_afrori, india, \n" +
                        "country great_britain,\n" +
                        " and great_britain's neighbours are western_utropa, siberia, western_united_states, souther_utropa, senadlavin, \n" +
                        "country siberia,\n" +
                        " and siberia's neighbours are egypt, great_britain, worrick, \n" +
                        "country pero,\n" +
                        " and pero's neighbours are vagnagale, heaurt, \n" +
                        "country western_utropa,\n" +
                        " and western_utropa's neighbours are ihesia, senadlavin, souther_utropa, great_britain, northern_utropa, \n" +
                        "country central_ameroki,\n" +
                        " and central_ameroki's neighbours are Quebeck, albania, western_united_states, alberta, duiestie, \n" +
                        "country afganistan,\n" +
                        " and afganistan's neighbours are kancheria, sluci, \n" +
                        "country teramar,\n" +
                        " and teramar's neighbours are western_united_states, alberta, \n" +
                        "country duiestie,\n" +
                        " and duiestie's neighbours are Quebeck, central_ameroki, vinenlant, alberta, \n" +
                        "country northern_utropa,\n" +
                        " and northern_utropa's neighbours are western_utropa, argentina, souther_utropa, \n" +
                        "country albania,\n" +
                        " and albania's neighbours are Quebeck, czeck, western_united_states, central_ameroki, \n" +
                        "country souther_utropa,\n" +
                        " and souther_utropa's neighbours are western_utropa, great_britain, northern_utropa, \n" +
                        "country india,\n" +
                        " and india's neighbours are japan, worrick, kancheria, \n" +
                        "country western_united_states,\n" +
                        " and western_united_states's neighbours are albania, czeck, teramar, alberta, central_ameroki, senadlavin, great_britain, \n" +
                        "country kancheria,\n" +
                        " and kancheria's neighbours are china, afganistan, sluci, middle_east, india, \n" +
                        "country worrick,\n" +
                        " and worrick's neighbours are siberia, india, czeck, yazteck, \n" +
                        "country senadlavin,\n" +
                        " and senadlavin's neighbours are western_utropa, western_united_states, great_britain, ireland, ihesia, \n" +
                        "country czeck,\n" +
                        " and czeck's neighbours are worrick, kongrolo, albania, western_united_states, yazteck, \n" +
                        "country tungu,\n" +
                        " and tungu's neighbours are north_afrori, south_afrori, east_afrori, \n" +
                        "country new_guinia,\n" +
                        " and new_guinia's neighbours are jacuncail, western_ulstrailia, \n" +
                        "country east_afrori,\n" +
                        " and east_afrori's neighbours are north_afrori, maganar, tungu, \n" +
                        "country china,\n" +
                        " and china's neighbours are kongrolo, middle_east, heal, sluci, kancheria, \n" +
                        "country Quebeck,\n" +
                        " and Quebeck's neighbours are heal, duiestie, central_ameroki, albania, \n" +
                        "country kongrolo,\n" +
                        " and kongrolo's neighbours are yazteck, czeck, china, middle_east, \n" +
                        "country");
    }

    /**
     * Test Save Map Function
     * @throws NoSuchFieldException
     */
    @Test
    public void testSaveMap() throws NoSuchFieldException {
        String fileName = "/Applications/Domination/maps/risk.map";
        String returnMsg = mapEditorService.editMap(fileName);

        Assert.assertEquals(returnMsg, "load map from file /Applications/Domination/maps/risk.map success");
        LinkedHashMap<Country, List<Country>> countryMap = MapEditorService.mapGraph.getAdjacentCountries();
        List<Map.Entry<Country, List<Country>>> entryList = new LinkedList<>();
        entryList.addAll(countryMap.entrySet());
        //make country#1's neighbours the same as country#2's
        countryMap.put(entryList.get(0).getKey(), entryList.get(1).getValue());
        MapEditorService.mapGraph.setAdjacentCountries(countryMap);
        countryMap = MapEditorService.mapGraph.getAdjacentCountries();
        mapEditorService.saveMap(fileName);
    }

    /**
     * Test whether Graph 1 is connected
     */
    @Test
    public void testConnectedGraph1(){
        mapEditorService.editMap("/Applications/Domination/maps/ameroki.map");
        LinkedHashMap<Country, List<Country>> adjacentCountries = MapEditorService.mapGraph.getAdjacentCountries();
        Assert.assertTrue(mapEditorService.checkIfConnected(adjacentCountries));
    }

    /**
     * Test whether Graph 2 is connected
     */
    @Test
    public void testConnectedGraph2(){
        mapEditorService.editMap("/Applications/Domination/maps/risk2.map");
        LinkedHashMap<Country, List<Country>> adjacentCountries = MapEditorService.mapGraph.getAdjacentCountries();
        Assert.assertFalse(mapEditorService.checkIfConnected(adjacentCountries));
    }

    /**
     * check continent is connected subgraph
     */
    @Test
    public void testConnectedContinentGraph1(){
        LinkedHashMap<Country, List<Country>> adjacentCountries = new LinkedHashMap<Country, List<Country>>();
        mapEditorService.editMap("/Applications/Domination/maps/risk2t.map");
        Continent continent = MapEditorService.mapGraph.getContinentList().get(0);
        List<Country> countryList = continent.getCountries().get(3).getNeighbours();
        countryList.remove(1);
        continent.getCountries().get(3).setNeighbours(countryList);
        for(Country country:continent.getCountries()){
            adjacentCountries.put(country,country.getNeighbours());
        }
        Assert.assertTrue(mapEditorService.checkIfConnected(adjacentCountries));
    }

    /**
     * check continent is connected sub graph
     */
    @Test
    public void testConnectedContinentGraph2(){
        LinkedHashMap<Country, List<Country>> adjacentCountries = new LinkedHashMap<Country, List<Country>>();
        mapEditorService.editMap("/Applications/Domination/maps/risk2t.map");
        Continent continent = MapEditorService.mapGraph.getContinentList().get(0);
        List<Country> countryList = continent.getCountries().get(3).getNeighbours();
        countryList.remove(1);
        countryList.remove(0);
        continent.getCountries().get(3).setNeighbours(countryList);
        List<Country> countryList2 = continent.getCountries().get(2).getNeighbours();
        countryList2.remove(1);
        continent.getCountries().get(2).setNeighbours(countryList);
        for(Country country:continent.getCountries()){
            adjacentCountries.put(country,country.getNeighbours());
        }
        Assert.assertFalse(mapEditorService.checkIfConnected(adjacentCountries));
    }

    /**
     * test valid map
     */
    @Test
    public void testValidMap(){
        String Result = "";
        try{
            Result= mapEditorService.editMap("/Applications/Domination/maps/ameroki.map");

        }catch (Exception e){
            Assert.assertEquals("load map from file ameroki success",Result);
        }
    }

    /**
     * Test Invalid map
     */
    @Test
    public void testInvalidMap(){
        String Result = "";
        try{
             Result= mapEditorService.editMap("/Applications/Domination/maps/risk.map");

        }catch (Exception e){
            Assert.assertEquals("the map is not valid",Result);
        }
    }


}
