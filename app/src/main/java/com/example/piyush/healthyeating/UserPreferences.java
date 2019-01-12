package com.example.piyush.healthyeating;

public class UserPreferences {
    private Boolean vegan;
    private Boolean scd;
    private Boolean glutenFree;
    private Boolean lactose;
    private Boolean nuts;

    public UserPreferences() {
        vegan = false;
        scd = false;
        glutenFree = false;
        lactose = false;
        nuts = false;
    }

    public void setVeganState(Boolean vegan) {
        this.vegan = vegan;
    }

    public void setGlutenFreeState(Boolean glutenFree) {
        this.glutenFree = vegan;
    }

    public void setScdState(Boolean scd) {
        this.scd = scd;
    }

    public void setLactoseState(Boolean lactose) {
        this.lactose = lactose;
    }

    public void setNutsState(Boolean nuts) {
        this.nuts = nuts;
    }


    public boolean getVeganState() {
        return vegan;
    }

    public boolean getGlutenFreeState() {
        return glutenFree;
    }

    public boolean getScdState() {
        return scd;
    }

    public boolean getLactoseState() {
        return lactose;
    }

    public boolean getNutsState() {
        return nuts;
    }

}

