package com.example.piyush.healthyeating;

public class UserPreferences {
    private Boolean vegan;
    private Boolean scd;
    private Boolean glutenFree;

    public UserPreferences() {
        vegan = false;
        scd = false;
        glutenFree = false;
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

    public boolean getVeganState() {
        return vegan;
    }

    public boolean getGlutenFreeState() {
        return glutenFree;
    }

    public boolean getScdState() {
        return scd;
    }
}
