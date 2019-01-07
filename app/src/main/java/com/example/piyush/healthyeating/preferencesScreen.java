package com.example.piyush.healthyeating;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import static com.example.piyush.healthyeating.MainActivity.booleanToString;

public class preferencesScreen extends AppCompatActivity {

    private static final String FILE_NAME = "userFile.txt";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SCD_STRING = "scdPrefs";
    private static final String VEGAN_STRING = "veganPrefs";
    private static final String GLUTEN_FREE_STRING = "glutenFreePrefs";
    private static final String NUTS_STRING = "nutsPrefs";
    private static final String LACTOSE_STRING = "lactosePrefs";
    private static final String GET_PREFERENCES_URL = "http://apptesting.getsandbox.com/preferences";
    private static final String UPDATE_PREFERENCES_URL = "http://apptesting.getsandbox.com/updatepreference";

    private CheckBox scd;
    private CheckBox glutenFree;
    private CheckBox vegan;
    private CheckBox allergicToNuts;
    private CheckBox lactoseIntolerant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_screen);
        Intent intent = getIntent();
        scd = (CheckBox) findViewById(R.id.scd_id);
        glutenFree = (CheckBox) findViewById(R.id.glutenFree_id);
        vegan = (CheckBox) findViewById(R.id.vegan_id);
        allergicToNuts = (CheckBox) findViewById(R.id.allergicToNuts_id);
        lactoseIntolerant = (CheckBox) findViewById(R.id.lactoseIntolerant_id);
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        scd.setChecked(sharedPreferences.getBoolean(SCD_STRING, false));
        glutenFree.setChecked(sharedPreferences.getBoolean(GLUTEN_FREE_STRING, false));
        vegan.setChecked(sharedPreferences.getBoolean(VEGAN_STRING, false));
        allergicToNuts.setChecked(sharedPreferences.getBoolean(NUTS_STRING, false));
        lactoseIntolerant.setChecked(sharedPreferences.getBoolean(LACTOSE_STRING, false));
    }

    public void homeButtonClicked2(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
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
        //save to local storage
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SCD_STRING, scd.isChecked());
        editor.putBoolean(VEGAN_STRING, vegan.isChecked());
        editor.putBoolean(GLUTEN_FREE_STRING, glutenFree.isChecked());
        editor.putBoolean(NUTS_STRING, allergicToNuts.isChecked());
        editor.putBoolean(LACTOSE_STRING, lactoseIntolerant.isChecked());
        editor.commit();

        //save to database
        JSONObject data = null;
        try {
            String preferences = "{ 'scd': " + booleanToString(scd.isChecked()) + "," +
                    " 'vegan': " + booleanToString(vegan.isChecked()) + "," +
                    " 'glutenFree': " + booleanToString(glutenFree.isChecked())+ "," +
                    " 'nuts': " + booleanToString(allergicToNuts.isChecked()) + "," +
                    " 'lactose': " + booleanToString(lactoseIntolerant.isChecked()) + "}";
            data = new JSONObject(preferences);
        } catch (JSONException e){
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                UPDATE_PREFERENCES_URL,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        UserPreferences userPreferences = gson.fromJson(response.toString(),UserPreferences.class);
                        Log.e("vegan state", booleanToString(userPreferences.getVeganState()));
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }

                }
        );
        requestQueue.add(objectRequest);
        Toast.makeText(this, "Preferences Saved", Toast.LENGTH_SHORT).show();
    }
}
