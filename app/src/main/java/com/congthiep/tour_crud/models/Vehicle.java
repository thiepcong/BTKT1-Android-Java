package com.congthiep.tour_crud.models;

public class Vehicle {
    private String name;
    private int imageResourceId;

    public Vehicle(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
