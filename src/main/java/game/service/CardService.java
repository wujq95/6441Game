package service;

import model.Card;
import model.GamePlayer;
import observer.Observable;

import java.util.*;

/**
 * Card function for reinforcement phase
 */
public class CardService extends Observable {
    private int armyRewarded = 5;
    private static List<Card> cardDeckList;
    private GamePlayerService gamePlayerService = new GamePlayerService();

    public static HashMap<GamePlayer, Card> lastRewardedCard = new HashMap<>();
    public static HashMap<GamePlayer, List<Card>> rewardedCardsAfterDefeatAnotherPlayer = new HashMap<>();
    public static boolean notExchangeCards = false;

    /**
     * Get reward card once attack conquered one country
     *
     * @return card name
     */
    public String rewardCardAfterConquerOneCountry() {
        GamePlayer gamePlayer = gamePlayerService.getCurrentPlayer();

        List<Card> previousCards = gamePlayer.getCardList();
        if (previousCards == null) {
            previousCards = new LinkedList<>();
        }

        Card randomCard = getRandomCardFromDeck();
        previousCards.add(randomCard);
        gamePlayer.setCardList(previousCards);

        lastRewardedCard.put(gamePlayer, randomCard);
        return randomCard.name();
    }


    /**
     * Get reward card once all countries have been conquered
     *
     * @param conquered conquered country
     */
    public void rewardCardAfterConquerLastCountry(GamePlayer conquered) {
        GamePlayer attacker = gamePlayerService.getCurrentPlayer();
        List<Card> previousCards = attacker.getCardList();
        if (previousCards == null) {
            previousCards = new LinkedList<>();
        }
        if (conquered.getCardList() == null) {
            conquered.setCardList(new LinkedList<Card>());
        }
        previousCards.addAll(conquered.getCardList());
        attacker.setCardList(previousCards);
        rewardedCardsAfterDefeatAnotherPlayer.put(attacker, previousCards);
        conquered.setCardList(new LinkedList<Card>());
        notifyObservers(this);
    }

    /**
     * Exchange cards
     *
     * @param no1        card no1
     * @param no2        card no2
     * @param no3        card no3
     * @param gamePlayer game player instance
     * @return message
     */
    public String exchangeCards(int no1, int no2, int no3, GamePlayer gamePlayer) {
        if (gamePlayer.getCardList() == null || gamePlayer.getCardList().size() < 3) {
            return "you don't have enough cards";
        }
        if (gamePlayer.getCardList().size() <= no1 ||
                gamePlayer.getCardList().size() <= no2 ||
                gamePlayer.getCardList().size() <= no3) {
            return "invalid command";
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
            notifyObservers(this);
        }
        return "exchange success";
    }

    /**
     * check whether player should exchange card
     *
     * @param gamePlayer player instance
     * @return true or false
     */
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

    /**
     * Create three type of cards
     */
    public void createCardDeck() {
        int number = 42;
        cardDeckList = new ArrayList<Card>(42);

        for (int i = 0; i < number / 3; i++) {
            cardDeckList.add(Card.artillery);
            cardDeckList.add(Card.cavalry);
            cardDeckList.add(Card.infantry);
        }

        notifyObservers(this);
    }

    /**
     * Get card
     *
     * @return card type number
     */
    public Card getRandomCardFromDeck() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(cardDeckList.size());
        return cardDeckList.remove(randomNumber);
    }
}
