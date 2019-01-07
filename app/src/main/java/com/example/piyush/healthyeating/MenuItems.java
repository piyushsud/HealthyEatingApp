package com.example.piyush.healthyeating;

public class MenuItems {
    private String item_name;
    private Boolean glutenFree;
    private Boolean vegan;
    private Boolean scd;
    private Boolean nuts;
    private Boolean lactose;

    public MenuItems() {
        item_name = " ";
        glutenFree = false;
        vegan = false;
        scd = false;
        nuts = false;
        lactose = false;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public String getItemName(){
        return item_name;
    }

    public void setGlutenFreeState(Boolean gluten_free) {
        this.glutenFree = gluten_free;
    }

    public Boolean getGlutenFreeState(){
        return glutenFree;
    }

    public void setVeganState(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getVeganState(){
        return vegan;
    }

    public void setScdState(Boolean scd) {
        this.scd = scd;
    }

    public Boolean getScdState(){
        return scd;
    }

    public void setNuts(Boolean nuts) {
        this.nuts = nuts;
    }

    public Boolean getNutsState(){
        return nuts;
    }

    public void setLactose(Boolean lactose) {
        this.lactose = lactose;
    }

    public Boolean getLactoseState(){
        return lactose;
    }

}
