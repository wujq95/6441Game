package controller;

import model.Connection;
import model.Continent;
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

    //public abstract void updateContinentList(String action, Continent continent);

    /**
     * update the newly added country
     * @param action "add"
     * @param country contains info of the newly added country
     */
    public abstract void updateCountry(String action, Country country);

    //public abstract void updateConnection(String action, Connection connection);
}
