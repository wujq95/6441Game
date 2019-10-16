package controller;

import model.Connection;
import model.Continent;
import model.Country;
import model.MapGraph;

public abstract class Observer {

    protected MapGraph mapGraph;

    public abstract void updateMapGraph();

    public abstract void updateContinentList(String action, Continent continent);

    public abstract void updateCountry(String action, Country country);

    public abstract void updateConnection(String action, Connection connection);
}
