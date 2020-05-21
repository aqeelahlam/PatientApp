package com.example.cholesterol.Objects;

public class Individual {

    private String id;
    private String name;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public Individual(String id, String name, String addressLine, String city, String state, String postalCode, String country) {
        setId(id);
        setName(name);
        setAddressLine(addressLine);
        setCity(city);
        setState(state);
        setPostalCode(postalCode);
        setCountry(country);
    }

    public Individual() {
    }

    public Individual(String patientID, String name) {
        setId(patientID);
        setName(name);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
