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
        Assert.assertEquals(returnMsg, "the map is not valid");

        fileName = "/Applications/Domination/maps/risk.map";
        returnMsg = mapEditorService.editMap(fileName);
        Assert.assertEquals(returnMsg, "load map from file /Applications/Domination/maps/risk.map success");

        fileName = "/Applications/Domination/maps/empty.map";
        returnMsg = mapEditorService.editMap(fileName);
        File mapFile = new File(fileName);
        Assert.assertEquals(mapFile.isFile(), true);
        Assert.assertEquals(returnMsg, "create new file success");
        mapFile.delete();
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
