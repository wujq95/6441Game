package observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observersList = new ArrayList<Observer>();
    public void attach(Observer o){
        this.observersList.add(o);
    }

    public void detach(Observer o){
        if(!observersList.isEmpty()){
            observersList.remove(o);
        }
    }

    public void notifyObservers(Observable observable){
        try{
            for(Observer observer: observersList){
                observer.update(observable);
            }
        } catch (Exception e){
            System.out.println("No observer");
        }

    }
}
