package com.example.cholesterol;

public class Patient {

    private Double cholesterol;

    public Patient(Double cholesterol){
        this.cholesterol = cholesterol;

    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getCholesterol() {
        return cholesterol;
    }
}
