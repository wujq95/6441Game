package model;

import controller.Observer;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    private Country country1;    // Country on one end of the edge

    private Country country2;    // Another Country on the other side

    /**
     * TODO:
     * @param countryName1
     * @param countryName2
     *
     * @return the Connection constructed or null
     */
    public Connection(String countryName1, String countryName2) {

    }

    public Country getCountry1() {
        return country1;
    }

    public void setCountry1(Country country1) {
        this.country1 = country1;
    }

    public Country getCountry2() {
        return country2;
    }

    public void setCountry2(Country country2) {
        this.country2 = country2;
    }

    /**
     *
     * @param country
     */
    public void updateCountryInfo(Country country){
        // TODO: setup the Country country's coordinates of this connection
        // find
    }
}
