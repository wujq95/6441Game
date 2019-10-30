package service;

import model.Card;
import model.GamePlayer;

import java.util.List;

public class CardService {
    private int armyRewarded = 5;

    public void rewardCardAfterConquerOneCountry(GamePlayer gamePlayer) {
        List<Card> previousCards = gamePlayer.getCardList();
        previousCards.add(Card.getRandomCard());
        gamePlayer.setCardList(previousCards);
    }

    public void rewardCardAfterConquerLastCountry(GamePlayer attacker, GamePlayer conquered) {
        List<Card> previousCards = attacker.getCardList();
        previousCards.addAll(conquered.getCardList());
        attacker.setCardList(previousCards);
    }

    public void exchangeCards(int no1, int no2, int no3, GamePlayer gamePlayer) {
        Card card1 = gamePlayer.getCardList().get(no1);
        Card card2 = gamePlayer.getCardList().get(no2);
        Card card3 = gamePlayer.getCardList().get(no3);

        if (card1.equals(card2) && card2.equals(card3)) {
            gamePlayer.setArmyValue(gamePlayer.getArmyValue() + armyRewarded);
        } else if (!card1.equals(card2) && !card2.equals(card3) && !card3.equals(card1)) {
            gamePlayer.setArmyValue(gamePlayer.getArmyValue() + armyRewarded);
        }

        armyRewarded = armyRewarded + 5;
    }

    //TODO:How to know who is the player right now?
    public boolean mustExchange(GamePlayer gamePlayer) {
        for (GamePlayer player : GamePlayerService.playerList) {
            if (gamePlayer.equals(player)) {
                if (player.getCardList().size() >= 5) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

}
