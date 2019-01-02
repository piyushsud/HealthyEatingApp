package com.example.piyush.healthyeating;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.facebook.login.LoginManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class preferencesScreen extends AppCompatActivity {

    private static final String FILE_NAME = "userFile.txt";

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SCD_STRING = "scdPrefs";
    private static final String VEGAN_STRING = "veganPrefs";
    private static final String GLUTEN_FREE_STRING = "glutenFreePrefs";

    private CheckBox scd;
    private CheckBox glutenFree;
    private CheckBox vegan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_screen);
        Intent intent = getIntent();
        scd = (CheckBox) findViewById(R.id.scd_id);
        glutenFree = (CheckBox) findViewById(R.id.glutenFree_id);
        vegan = (CheckBox) findViewById(R.id.vegan_id);
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        scd.setChecked(sharedPreferences.getBoolean(SCD_STRING, false));
        glutenFree.setChecked(sharedPreferences.getBoolean(GLUTEN_FREE_STRING, false));
        vegan.setChecked(sharedPreferences.getBoolean(VEGAN_STRING, false));
    }

    public void homeButtonClicked2(View view) {
        Intent intent = new Intent(this, googleMapsScreen.class);
        startActivity(intent);
    }

    public void preferencesButtonClicked2(View view) {
        Intent intent = new Intent(this, preferencesScreen.class);
        startActivity(intent);
    }

    public void logOutButtonClicked2(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginManager.getInstance().logOut();
    }

    public void savePreferences(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SCD_STRING, scd.isChecked());
        editor.putBoolean(VEGAN_STRING, vegan.isChecked());
        editor.putBoolean(GLUTEN_FREE_STRING, glutenFree.isChecked());
        editor.commit();
    }


}
