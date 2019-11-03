package service;

import controller.Observer;
import model.Card;
import model.GamePlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardService {
    private int armyRewarded = 5;
    private static List<Card> cardDeckList;
    private List<controller.Observer> cardObservers = new ArrayList<>();
    private GamePlayerService gamePlayerService = new GamePlayerService();

    public void attach(controller.Observer observer) {
        cardObservers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : cardObservers) {
            observer.update();
        }
    }

    public void rewardCardAfterConquerOneCountry() {
        GamePlayer gamePlayer = gamePlayerService.getCurrentPlayer();

        List<Card> previousCards = gamePlayer.getCardList();
        if (previousCards == null) {
            previousCards = new LinkedList<>();
        }
        previousCards.add(getRandomCardFromDeck());
        gamePlayer.setCardList(previousCards);

        gamePlayerService.updateGamePlayerCard(gamePlayer);
    }

    public void rewardCardAfterConquerLastCountry(GamePlayer conquered) {
        GamePlayer attacker = gamePlayerService.getCurrentPlayer();
        List<Card> previousCards = attacker.getCardList();
        previousCards.addAll(conquered.getCardList());
        attacker.setCardList(previousCards);
        conquered.setCardList(new LinkedList<Card>());

        gamePlayerService.updateGamePlayerCard(attacker);
    }

    String exchangeCards(int no1, int no2, int no3, GamePlayer gamePlayer) {
        if (gamePlayer.getCardList() == null || gamePlayer.getCardList().size() < 3) {
            return "you don't have enough cards";
        }
        Card card1 = gamePlayer.getCardList().get(no1);
        Card card2 = gamePlayer.getCardList().get(no2);
        Card card3 = gamePlayer.getCardList().get(no3);

        if (card1.equals(card2) && card2.equals(card3)) {
            gamePlayer.setArmyValue(gamePlayer.getArmyValue() + armyRewarded);
            gamePlayer.getCardList().remove(card1);
            gamePlayer.getCardList().remove(card2);
            gamePlayer.getCardList().remove(card3);
        } else if (!card1.equals(card2) && !card2.equals(card3) && !card3.equals(card1)) {
            gamePlayer.setArmyValue(gamePlayer.getArmyValue() + armyRewarded);
            gamePlayer.getCardList().remove(card1);
            gamePlayer.getCardList().remove(card2);
            gamePlayer.getCardList().remove(card3);
        } else {
            return "invalid command";
        }

        armyRewarded = armyRewarded + 5;

        if (GamePlayerService.checkPhase == 2) {
            notifyObservers();
        }
        return "exchange success";
    }

    public boolean mustExchange(GamePlayer gamePlayer) {
        for (GamePlayer player : GamePlayerService.playerList) {
            if (gamePlayer.equals(player)) {

                if (player.getCardList() != null && player.getCardList().size() >= 5) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public void createCardDeck() {
        int number = 42;
        cardDeckList = new ArrayList<Card>(42);

        for (int i = 0; i < number / 3; i++) {
            cardDeckList.add(Card.artillery);
            cardDeckList.add(Card.cavalry);
            cardDeckList.add(Card.infantry);
        }

        notifyObservers();
    }

    public Card getRandomCardFromDeck() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(cardDeckList.size());
        return cardDeckList.remove(randomNumber);
    }
}
