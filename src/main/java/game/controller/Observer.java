package controller;

import model.Country;
import model.MapGraph;

/**
 * Abstract Observer class
 */
public abstract class Observer {

    protected MapGraph mapGraph;

    /**
     * reload the mapGraph GUI
     */
    public abstract void updateMapGraph();


    /**
     * update the newly added country
     * @param action "add"
     * @param country contains info of the newly added country
     */
    public abstract void updateCountry(String action, Country country);
}
