package com.example.piyush.healthyeating;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView textView;

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
        textView = (TextView)findViewById(R.id.textView2);
        callbackManager = CallbackManager.Factory.create();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        try {
            if (AccessToken.getCurrentAccessToken().getToken() != null) {
                Intent intent = new Intent(MainActivity.this, googleMapsScreen.class);
                startActivity(intent);
            }
        }
        catch (NullPointerException exception) {
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
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
                            Log.e("birthday", birthday);
                            Log.e("gender", gender);

                            Intent main = new Intent(MainActivity.this,googleMapsScreen.class);
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

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
/*
    public void goToGoogleMapsScreen(View view) {
        Intent intent = new Intent(this, googleMapsScreen.class);
        startActivity(intent);
    }*/

}
