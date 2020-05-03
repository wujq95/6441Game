package service;

import model.Card;
import model.Country;
import model.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Save and Load Game Test
 */
public class SaveLoadGameTest {
    private MapEditorService mapEditorService;

    /**
     * Initial map editor service
     */
    @Before
    public void setUp() {
        mapEditorService = new MapEditorService();
    }

    /**
     * Test - Save Game function
     */
    @Test
    public void testSaveLoadGame() {
        for(int i = GamePlayerService.playerList.size()-1;i>=0;i--){
            GamePlayerService.playerList.remove(i);
        }
        String fileName = "risk.map";
        mapEditorService.editMap(fileName);

        SaveLoadGame saveLoadGame = new SaveLoadGameAttackPhase();

        GamePlayer a = new GamePlayer();
        a.setPlayerName("a");
        a.setArmyValue(15);
        List<Country> countryList = new LinkedList<>();
        Country country = new Country();
        country.setCountryName("cou");
        country.setArmyValue(5);
        countryList.add(country);
        Country country2 = new Country();
        country2.setCountryName("try");
        country2.setArmyValue(4);
        countryList.add(country2);
        a.setCountryList(countryList);
        List<Card> cardList = new ArrayList<>();
        cardList.add(Card.infantry);
        cardList.add(Card.cavalry);
        a.setCardList(cardList);
        GamePlayerService.playerList.add(a);


        GamePlayer b = new GamePlayer();
        b.setPlayerName("a");
        b.setArmyValue(15);
        List<Country> countryList2 = new LinkedList<>();
        Country country3 = new Country();
        country3.setCountryName("cou");
        country3.setArmyValue(5);
        countryList2.add(country);
        Country country4 = new Country();
        country4.setCountryName("try");
        country4.setArmyValue(4);
        countryList2.add(country2);
        a.setCountryList(countryList);
        List<Card> cardList2 = new ArrayList<>();
        cardList2.add(Card.infantry);
        cardList2.add(Card.cavalry);
        b.setCardList(cardList2);
        GamePlayerService.playerList.add(b);

        GamePlayerService.checkPhase = 3;
        GamePlayerService.choosePlayer = 0;

        CardService.cardDeckList = cardList;
        CardService.notExchangeCards = true;
        AttackService.fromDiceNum = 3;
        AttackService.toDiceNum = 2;
        AttackService.fromCountry = "blue";
        AttackService.toCountry = "red";
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(1);
        arrayList1.add(2);
        arrayList1.add(3);
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(4);
        arrayList2.add(5);
        AttackService.fromDiceResultList = arrayList1;
        AttackService.toDiceResultList = arrayList2;
        HashMap<GamePlayer, Card> hashMap1 = new HashMap<>();
        hashMap1.put(a, Card.infantry);
        CardService.lastRewardedCard = hashMap1;

        HashMap<GamePlayer, List<Card>> hashMap2 = new HashMap<>();
        hashMap2.put(a, cardList);
        CardService.rewardedCardsAfterDefeatAnotherPlayer = hashMap2;

        String returnMsg = saveLoadGame.saveGame("testsavegame3.map");
        returnMsg.equals("saveGame success");
    }

    /**
     * Test - Load Game Function
     */
    @Test
    public void testLoadGame() {

        SaveLoadGame saveLoadGame = new SaveLoadGameAttackPhase();

        saveLoadGame.loadGame("testsavegame3.map");
        Assert.assertEquals(GamePlayerService.checkPhase, 3);
        Assert.assertEquals(GamePlayerService.choosePlayer.longValue(), 0L);

        Assert.assertEquals(CardService.notExchangeCards, true);
        Assert.assertEquals(AttackService.fromDiceNum.longValue(), 3L);
        Assert.assertEquals(AttackService.toDiceNum.longValue(), 2L);
        Assert.assertEquals(AttackService.fromCountry, "blue");
        Assert.assertEquals(AttackService.toCountry, "red");
    }
}
