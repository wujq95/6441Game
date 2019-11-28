package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class is used to create , notify and remove observers
 */
public class Observable {
    /**
     * Initial observer list
     */
    private List<Observer> observersList = new ArrayList<Observer>();

    /**
     * Add Observer
     * @param o Observer
     */
    public void attach(Observer o){
        this.observersList.add(o);
    }

    /**
     * Remove observer
     * @param o Observer
     */
    public void detach(Observer o){
        if(!observersList.isEmpty()){
            observersList.remove(o);
        }
    }

    /**
     * Notify observers once changed
     * @param observable observable
     */
    public void notifyObservers(Observable observable){
        try{
            for(Observer observer: observersList){
                observer.update(observable);
            }
        } catch (Exception e){

        }

    }
}
