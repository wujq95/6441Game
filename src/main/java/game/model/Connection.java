package model;

import controller.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Connection class is used to initiate the connection between two countries' variables
 */
public class Connection {

    private Country country1;    // Country on one end of the edge

    private Country country2;    // Another Country on the other side

    public Connection(Country country1, Country country2) {
        this.country1 = country1;
        this.country2 = country2;
    }

    /**
     * Get Country1
     * @return country1 name from user
     */
    public Country getCountry1() {
        return country1;
    }

    /**
     * Set Country1
     * @param country1 User typed country1 name
     */
    public void setCountry1(Country country1) {
        this.country1 = country1;
    }

    /**
     * Get Country2
     * @return User typed country2 name
     */
    public Country getCountry2() {
        return country2;
    }

    /**
     * Set Country
     * @param country2 User typed country2 name
     */
    public void setCountry2(Country country2) {
        this.country2 = country2;
    }

}
