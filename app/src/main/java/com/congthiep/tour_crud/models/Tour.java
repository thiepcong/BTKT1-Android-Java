package com.congthiep.tour_crud.models;

public class Tour {
    private String itinerary;
    private String duration;
    private Vehicle vehicle;

    // Constructor
    public Tour(String itinerary, String duration, Vehicle vehicle) {
        this.itinerary = itinerary;
        this.duration = duration;
        this.vehicle = vehicle;
    }

    public Tour() {}


    // Getters and setters
    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

