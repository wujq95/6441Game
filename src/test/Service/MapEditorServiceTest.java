package Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.MapEditorService;

public class MapEditorServiceTest {


    private MapEditorService mapEditorService;

    @Before
    public void setuUp() {
        mapEditorService = new MapEditorService();
    }

    @Test
    public void testEditMap() {

        String fileName = "/Applications/Domination/maps/ameroki.map";
        String returnMsg = mapEditorService.editMap(fileName);
        Assert.assertEquals(returnMsg,"load map from file /Applications/Domination/maps/ameroki.map success");
    }

}
