package service;

import controller.MapController;
import model.MapGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tournament Mode Test
 */
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

    /**
     *tournament mode test
     */
    @Test
    public void tournamentTest(){
        for(int i = GamePlayerService.playerList.size()-1;i>=0;i--){
            GamePlayerService.playerList.remove(i);
        }
        MapEditorService.mapGraph = new MapGraph();
        String input = "tournament -M /Applications/Domination/maps/ameroki.map /Users/siming/Desktop/soen6441/Domination/maps/eurasien.map -P Aggressive Benevolent Aggressive Random -G 5 -D 30";
        String results = tournamentService.tournament(input.split(" "));
        String[] resultList = results.split("\n");
        Assert.assertEquals(resultList.length,11);
        for(int j=1;j<resultList.length;j++){
           String result = resultList[j];
           String[] oneResultList = result.split(" ");
           String oneResult  = oneResultList[3];
           boolean flag = true;
           if(!(oneResult.equals("Draw")||oneResult.equals("AggressiveStrategy")||oneResult.equals("RandomStrategy")||oneResult.equals("BenevolentStrategy")||oneResult.equals("CheaterStrategy"))){
               flag = false;
           }
           Assert.assertEquals(flag,true);
        }
    }
}
