package com.example.myapplication.models;

public class FromAndToLocations
{

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    String locationName ,charge;
    public FromAndToLocations(String locationName, String charge)
    {
        this.locationName=locationName;
        this.charge = charge;
    }
}
