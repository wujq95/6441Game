package service;

import controller.Observer;
import model.Card;
import model.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardService {
    private int armyRewarded = 5;
    private static List<Card> cardDeckList;
    private List<controller.Observer> cardObservers = new ArrayList<>();

    public void attach(controller.Observer observer) {
        cardObservers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : cardObservers) {
            observer.update();
        }
    }

    public void rewardCardAfterConquerOneCountry(GamePlayer gamePlayer) {
        List<Card> previousCards = gamePlayer.getCardList();
        previousCards.add(getRandomCardFromDeck());
        gamePlayer.setCardList(previousCards);

        notifyObservers();
    }

    public void rewardCardAfterConquerLastCountry(GamePlayer attacker, GamePlayer conquered) {
        List<Card> previousCards = attacker.getCardList();
        previousCards.addAll(conquered.getCardList());
        attacker.setCardList(previousCards);

        notifyObservers();
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
        notifyObservers();
    }

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

    public void createCardDeck() {
        int number = MapEditorService.mapGraph.getCountryList().size();
        cardDeckList = new ArrayList<Card>(number);

        if (number % 3 == 0) {
            for (int i = 0; i < number / 3; i++) {
                cardDeckList.add(Card.artillery);
                cardDeckList.add(Card.cavalry);
                cardDeckList.add(Card.infantry);
            }
        } else if (number % 3 == 1) {
            for (int i = 0; i < number / 3; i++) {
                cardDeckList.add(Card.artillery);
                cardDeckList.add(Card.cavalry);
                cardDeckList.add(Card.infantry);
            }
            cardDeckList.add(Card.getRandomCard());
        } else {
            for (int i = 0; i < number / 3; i++) {
                cardDeckList.add(Card.artillery);
                cardDeckList.add(Card.cavalry);
                cardDeckList.add(Card.infantry);
            }
            cardDeckList.add(Card.getRandomCard());
            cardDeckList.add(Card.getRandomCard());
        }

        notifyObservers();
    }

    public Card getRandomCardFromDeck() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(cardDeckList.size());
        return cardDeckList.remove(randomNumber);
    }
}
