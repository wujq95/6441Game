package model;

import model.MapGraph;

public abstract class Observer {

    protected MapGraph mapGraph;

    protected Continent continent;

    protected Country country;

    protected Connection connection;

    public abstract void update();
}
