package com.example.cholesterol;

public class Patient {

    private String cholesterol;
    private String name;
    private String patientID;
    private String effectiveDate;

//  Empty constructor if ever we need to initialize
    public Patient() {
    }

//  Constructor to pass in ID and Name of Patient
    public Patient(String patientID, String name){
        this.patientID = patientID;
        this.name = name;
    }

    public Patient(String patientID, String name, String cholesterol, String effectiveDate){
        this.patientID = patientID;
        this.name = name;
        this.cholesterol = cholesterol;
        this.effectiveDate = effectiveDate;
    }

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

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
