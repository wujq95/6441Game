package model;

import java.util.List;

public class Continent {
    Integer id;

    String continentName;

    int controlValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getControlValue() {
        return controlValue;
    }

    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    List<Country> countries;

    public Continent(Integer id, String continentName, int controlValue) {
        this.id = id;
        this.continentName = continentName;
        this.controlValue = controlValue;
    }
}
