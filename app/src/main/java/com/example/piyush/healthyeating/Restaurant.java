package com.example.piyush.healthyeating;

import android.content.Intent;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class Restaurant{
    // Variable Declarations
    private String name;
    private double latitude;
    private double longitude;
    private String website;
    private String address;
    private List<MenuItems> menu_items;

    // Constructor
    public Restaurant() {
        name = "";
        latitude = 0;
        longitude = 0;
        website = "";
        address = "";
        menu_items = new ArrayList<MenuItems>();
    }

    // Set and Get Methods

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return (this.name);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return (this.latitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return (this.longitude);
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return (this.website);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return (this.address);
    }

    public void setMenu_items(List<MenuItems> menu_items) {
        this.menu_items = menu_items;
    }

    public List<MenuItems> getMenu_items() {
        return this.menu_items;
    }

}


