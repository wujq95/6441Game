package controller;

import model.Connection;
import model.Country;
import model.MapGraph;

public abstract class Observer {

    protected MapGraph mapGraph;

    public abstract void updateContinetsList(String action, MapGraph mapGraph);

    public abstract void updateCountry(String action, Country country);

    public abstract void updateConnection(String action, Connection connection);
}
