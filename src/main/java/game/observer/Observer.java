package observer;

import model.MapGraph;
import service.*;

public abstract class Observer {

    protected MapGraph mapGraph;

    protected GamePlayerService gamePlayerService;

    protected ReinforceService reinforceService;

    protected FortifyService fortifyService;

    protected AttackService attackService;

    protected CardService cardService;

    public abstract void update(Observable o);
}