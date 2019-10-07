package model;

import model.MapGraph;

public abstract class Observer {

    protected MapGraph mapGraph;

    protected Country country;

    public abstract void update();
}
