package com.example.cholesterol;

public class Patient {

    private String cholesterol;
    private String name;
    private String patientID;

//    public Patient(String cholesterol){
//        this.cholesterol = cholesterol;
//    }

//  Empty constructor if ever we need to initialize
    public Patient() {
    }

    public Patient(String patientID, String name){
        this.patientID = patientID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol() {
        return cholesterol;
    }
}
