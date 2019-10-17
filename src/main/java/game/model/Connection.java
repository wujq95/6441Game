package model;

import controller.Observer;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    private Country country1;    // Country on one end of the edge

    private Country country2;    // Another Country on the other side

    /**
     * TODO:
     * @param countryName1 User typed country name
     * @param countryName2 User typed country name
     *
     * @return the Connection constructed or null
     */
    public Connection(String countryName1, String countryName2) {

    }

    /**
     * Get Country1
     * @return
     */
    public Country getCountry1() {
        return country1;
    }

    /**
     * Set Country1
     * @param country1 User typed country name
     */
    public void setCountry1(Country country1) {
        this.country1 = country1;
    }

    /**
     * Get Country2
     * @return User typed country name
     */
    public Country getCountry2() {
        return country2;
    }

    /**
     * Set Country
     * @param country2 User typed country name
     */
    public void setCountry2(Country country2) {
        this.country2 = country2;
    }

}
