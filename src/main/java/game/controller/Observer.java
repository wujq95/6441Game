package controller;

import model.Country;
import model.MapGraph;
import service.GamePlayerService;

/**
 * Abstract Observer class
 */
public abstract class Observer {

    protected MapGraph mapGraph;

    protected GamePlayerService gamePlayerService;

    public abstract void update();
}
