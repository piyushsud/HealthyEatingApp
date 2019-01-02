package com.example.piyush.healthyeating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.login.LoginManager;

public class googleMapsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_screen);
        Intent intent = getIntent();
    }

    public void homeButtonClicked(View view) {
        Intent intent = new Intent(this, googleMapsScreen.class);
        startActivity(intent);
    }

    public void preferencesButtonClicked(View view) {
        Intent intent = new Intent(this, preferencesScreen.class);
        startActivity(intent);
    }

    public void logOutButtonClicked(View view) {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
