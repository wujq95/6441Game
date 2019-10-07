package model;

import javafx.geometry.Point2D;

import java.util.*;

public class MapGraph {

//    private Map<Country, List<Country>> adjacentCountries;

    private List<Observer> observers = new ArrayList<Observer>();

    private List<Point2D> countryLocationList = new ArrayList<Point2D>();

    public List<Point2D> getCountryLocationList() {
        return countryLocationList;
    }

    public void setCountryLocationList(List<Point2D> countryLocationList) {
        this.countryLocationList = countryLocationList;
        notifyAllObservers();
    }

    public void addCountryLocation(double x, double y){
        Point2D pt = new Point2D(x, y);
        this.countryLocationList.add(pt);
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }
}
