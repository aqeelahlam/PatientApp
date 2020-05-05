package com.example.cholesterol;

public class Patient {

    private String cholesterol;

    public Patient(String cholesterol){
        this.cholesterol = cholesterol;

    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol() {
        return cholesterol;
    }
}
