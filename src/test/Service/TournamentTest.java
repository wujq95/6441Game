package Service;

import controller.MapController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AttackService;
import service.MapEditorService;
import service.TournamentService;

import java.util.List;

public class TournamentTest {

    private MapEditorService mapEditorService;
    private TournamentService tournamentService;

    /**
     * test initial action method
     */
    @Before
    public void initial(){
        tournamentService = new TournamentService();
        mapEditorService = new MapEditorService();
        mapEditorService.editMap("ameroki.map");

    }

    @Test
    public void tournamentTest(){
        String input = "tournament -M /Users/wujiaqi/soen6441/ameroki.map -P Aggressive Benevolent Aggressive Aggressive Random Random -G 5 -D 100";
        String results = tournamentService.tournament(input.split(" "));
       // String[] resultList = results.split("\n");
       // Assert.assertEquals(resultList.length,500);
        String aggResult = "";
    }

}
