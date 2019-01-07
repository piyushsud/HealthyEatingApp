package com.example.piyush.healthyeating;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String GET_PREFERENCES_URL = "http://apptesting.getsandbox.com/preferences";
    private static final String UPDATE_PREFERENCES_URL = "http://apptesting.getsandbox.com/updatepreference";
    private static final String LOGIN_URL = "http://apptesting.getsandbox.com/login";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SCD_STRING = "scdPrefs";
    private static final String VEGAN_STRING = "veganPrefs";
    private static final String GLUTEN_FREE_STRING = "glutenFreePrefs";

    LoginButton loginButton;
    CallbackManager callbackManager;

    String userId;
    String firstName = " ";
    String lastName = " ";
    String email = " ";
    String birthday = " ";
    String gender = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent getIntent = getIntent();
        loginButton = (LoginButton)findViewById(R.id.fb_login_button);
        callbackManager = CallbackManager.Factory.create();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        try {
            if (AccessToken.getCurrentAccessToken().getToken() != null) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        }
        catch (NullPointerException exception) {
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                updatePreferencesOnLocalStorage();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            userId = object.getString("id");
                            if(object.has("first_name")) {
                                firstName = object.getString("first_name"); }
                            if(object.has("last_name")) {
                                lastName = object.getString("last_name"); }
                            if (object.has("email")) {
                                email = object.getString("email"); }
                            if (object.has("birthday")){
                                birthday = object.getString("birthday"); }
                            if (object.has("gender")){
                                gender = object.getString("gender");
                            }
                            Log.e("first name", firstName);
                            Log.e("last name", lastName);
                            Log.e("email", email);
                            Log.e("facebook id", userId);


                            sendFacebookInfoToDatabase(
                                    firstName,
                                    lastName,
                                    email,
                                    userId
                            );

                            Intent main = new Intent(MainActivity.this,MapsActivity.class);
                            main.putExtra("name",firstName);
                            main.putExtra("surname",lastName);
                            startActivity(main);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Facebook error", error.toString());
            }
        });
    }

    void sendFacebookInfoToDatabase(String firstName, String lastName, String email, String userId) {

        //send token, email address, facebook id, first name, last name to login API
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject data = null;
        try {
            String datas = "{ 'token': " + AccessToken.getCurrentAccessToken().getToken() + "," +
                    " 'userId': " + userId + "," +
                    " 'firstName': " + firstName + "," +
                    " 'lastName': " + lastName;
            if(email.equals(" ")) {
                datas = datas + "}";
            }
            else {
                datas = datas + "," + " 'email': " + email + "}";
            }
            data = new JSONObject(datas);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                LOGIN_URL,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //do nothing
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
    }

    private void updatePreferencesOnLocalStorage() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject data = null;
        try {
            String datas = "{ 'token': " + AccessToken.getCurrentAccessToken().getToken() + "}";
            data = new JSONObject(datas);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //call get preferences API to get user preferences, sending user token as parameter
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                GET_PREFERENCES_URL,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        //store preferences pulled from api in UserPreferences.class
                        UserPreferences userPreferences = gson.fromJson(response.toString(),UserPreferences.class);
                        //update local storage from UserPreferences.class
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(SCD_STRING, userPreferences.getScdState());
                        editor.putBoolean(VEGAN_STRING, userPreferences.getVeganState());
                        editor.putBoolean(GLUTEN_FREE_STRING, userPreferences.getGlutenFreeState());
                        editor.commit();
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
    }

    public static String booleanToString(boolean state) {
        if(state == true) {
            return "true";
        }
        else {
            return "false";
        }
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
