package com.example.demo.model;

public class Address {

    private String streetName;
    private int houseNumber;
    private String neighborhood;
    private String state;
    private String country;

    public Address() {
    }

    public Address(String streetName, int houseNumber, String neighborhood, String state, String country) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.neighborhood = neighborhood;
        this.state = state;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
