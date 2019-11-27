package service;

import model.Card;
import model.Country;
import strategy.Strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * abstract class for save and load game
 */
public abstract class SaveLoadGame {

    public static MapEditorService mapEditorService;

    public static GamePlayerService gamePlayerService;

    public static ReinforceService reinforceService;

    public static FortifyService fortifyService;

    public static AttackService attackService;

    public static CardService cardService;

    /**
     * default constructor
     */
    public SaveLoadGame() {
        mapEditorService = new MapEditorService();
        gamePlayerService = new GamePlayerService();
    }

    /**
     * abstract save game method
     * @param fileName file name
     * @return message
     */
    abstract String saveGame(String fileName);

    /**
     * abstract load game method
     * @param fileName file name
     * @return message
     */
    abstract String loadGame(String fileName);

    /**
     * Transfer String Array to Integer Array
     * @param strs String
     * @return Integer List
     */
    public List<Integer> StringArrayToIntList(String[] strs) {
        List<Integer> intList = new LinkedList<>();
        for (String str : strs) {
            intList.add(Integer.parseInt(str));
        }

        return intList;
    }

    /**
     * Transfer Card List to Integer List
     * @param strs String
     * @return Integer List
     */
    public List<Card> StringArrayToCardList(String[] strs) {
        List<Card> intList = new LinkedList<>();
        for (String str : strs) {
            intList.add(Card.valueOf(str));
        }

        return intList;
    }

    /**
     * Find country name
     * @param strs country name
     * @return Integer list of country
     */
    public List<Country> findCountryNames(String[] strs) {
        List<Country> intList = new LinkedList<>();
        for (String str : strs) {
            MapEditorService.findCountryByName(str);
        }

        return intList;
    }

    /**
     * Find strategy by name
     * @param name strategy name
     * @return strategy class by specified name
     */
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
