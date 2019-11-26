package service;

import model.Card;
import model.Country;
import strategy.Strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public abstract class SaveLoadGame {

    public static MapEditorService mapEditorService;

    public static GamePlayerService gamePlayerService;

    public static ReinforceService reinforceService;

    public static FortifyService fortifyService;

    public static AttackService attackService;

    public static CardService cardService;

    public SaveLoadGame() {
        mapEditorService = new MapEditorService();
        gamePlayerService = new GamePlayerService();
    }

    abstract String saveGame(String fileName);

    abstract String loadGame(String fileName);

    public List<Integer> StringArrayToIntList(String[] strs) {
        List<Integer> intList = new LinkedList<>();
        for (String str : strs) {
            intList.add(Integer.parseInt(str));
        }

        return intList;
    }

    public List<Card> StringArrayToCardList(String[] strs) {
        List<Card> intList = new LinkedList<>();
        for (String str : strs) {
            intList.add(Card.valueOf(str));
        }

        return intList;
    }

    public List<Country> findCountryNames(String[] strs) {
        List<Country> intList = new LinkedList<>();
        for (String str : strs) {
            MapEditorService.findCountryByName(str);
        }

        return intList;
    }

    public Strategy findStrategyByName(String name) {
        ServiceLoader<Strategy> loader = ServiceLoader.load(Strategy.class);
        for (Strategy implClass : loader) {
            if (implClass.getClass().getSimpleName().equals(name)) {
                return implClass;
            }
        }

        return null;
    }
}