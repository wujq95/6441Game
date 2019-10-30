package controller;

import model.Country;
import model.MapGraph;
import service.AttackService;
import service.CardService;
import service.GamePlayerService;

/**
 * Abstract Observer class
 */
public abstract class Observer {

    protected MapGraph mapGraph;

    protected GamePlayerService gamePlayerService;

    protected AttackService attackService;

    protected CardService cardService;

    public abstract void update();
}
