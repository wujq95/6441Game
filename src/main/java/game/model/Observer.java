package model;

import model.MapGraph;

public abstract class Observer {

    protected MapGraph mapGraph;

    public abstract void update();
}
