package service;

import model.Card;
import model.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class CardServiceTest {

    private CardService cardService;

    /**
     * Initial map editor service
     */
    @Before
    public void setUp() {
        cardService = new CardService();
        cardService.createCardDeck();
    }

    @Test
    public void testExchangeCards() {
        GamePlayer gamePlayer = new GamePlayer();
        List<Card> cardList = new LinkedList<>();
        cardList.add(Card.artillery);
        gamePlayer.setCardList(cardList);
        gamePlayer.setArmyValue(0);

        String notenough = cardService.exchangeCards(0, 1, 2, gamePlayer);
        Assert.assertEquals("you don't have enough cards", notenough);

        cardList.add(Card.artillery);
        cardList.add(Card.infantry);
        gamePlayer.setCardList(cardList);
        String invalid = cardService.exchangeCards(0, 1, 2, gamePlayer);
        Assert.assertEquals("invalid command", invalid);

        cardList.add(Card.cavalry);
        gamePlayer.setCardList(cardList);
        String success = cardService.exchangeCards(0, 2, 3, gamePlayer);
        Assert.assertEquals("exchange success", success);
    }

    @Test
    public void testRewardCard() {
        GamePlayerService.choosePlayer = 0;
        GamePlayer gamePlayer = new GamePlayer();
        List<Card> cardList = new LinkedList<>();
        cardList.add(Card.artillery);
        gamePlayer.setCardList(cardList);
        gamePlayer.setArmyValue(0);

        for(int i=GamePlayerService.playerList.size()-1;i>=0;i--) {
            GamePlayerService.playerList.remove(i);
        }
        GamePlayerService.playerList.add(gamePlayer);


        cardService.rewardCardAfterConquerOneCountry();
        Assert.assertEquals(2, GamePlayerService.playerList.get(0).getCardList().size());

        GamePlayerService.choosePlayer = 1;
        GamePlayer attacker = new GamePlayer();
        List<Card> cardList2 = new LinkedList<>();
        cardList2.add(Card.artillery);
        cardList2.add(Card.infantry);
        attacker.setCardList(cardList2);
        attacker.setArmyValue(0);
        GamePlayerService.playerList.add(attacker);

        GamePlayer conquered = new GamePlayer();
        List<Card> cardList3 = new LinkedList<>();
        cardList3.add(Card.infantry);
        conquered.setCardList(cardList3);
        conquered.setArmyValue(0);

        cardService.rewardCardAfterConquerLastCountry(conquered);
        Assert.assertEquals(3, GamePlayerService.playerList.get(1).getCardList().size());
    }
}
