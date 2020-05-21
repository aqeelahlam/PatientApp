package com.example.cholesterol.Objects;

public class Patient {

    private String patientID;
    private String name;
    private String cholesterol;
    private String effectiveDate;
    private String birthDate;
    private String gender;
    private String addressLine;
    private String city;
    private String postalCode;
    private String state;
    private String country;


    /**
     * Constructor for Patient Object
     * @param patientID Patient Identification
     * @param name Patient Name
     */
    public Patient(String patientID, String name){
        this.patientID = patientID;
        this.name = name;
    }

    /**
     * Constructor for Patient Object
     * @param patientID Patient Identification
     * @param name Patient Name
     * @param cholesterol Patient Cholesterol
     * @param effectiveDate Patient Effective Date
     */
    public Patient(String patientID, String name, String cholesterol, String effectiveDate){
        this.patientID = patientID;
        this.name = name;
        this.cholesterol = cholesterol;
        this.effectiveDate = effectiveDate;
    }

/*
Below are the Accessors and Mutators required to update or get an item from a Patient Object
 */

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public void setExtraDetails(String birthDate, String gender, String addressLine, String city,
                                String postalCode, String state, String country) {
        this.birthDate = birthDate;
        this.gender = gender;
        this.addressLine = addressLine;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
    }


}



