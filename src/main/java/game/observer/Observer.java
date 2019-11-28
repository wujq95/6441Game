package observer;

import model.MapGraph;
import service.*;

/**
 * Observer class used to Observer Pattern
 */
public abstract class Observer {
    /**
     * Initial required model
     */
    protected MapGraph mapGraph;

    protected CommandService commandService;

    protected GamePlayerService gamePlayerService;

    protected ReinforceService reinforceService;

    protected FortifyService fortifyService;

    protected AttackService attackService;

    protected CardService cardService;

    protected TournamentService tournamentService;

    /**
     * Update info once changed
     * @param o Observable
     */
    public abstract void update(Observable o);
}