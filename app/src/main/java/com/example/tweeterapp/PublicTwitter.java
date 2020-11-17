package com.example.tweeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tweeterapp.data.Login;
import com.example.tweeterapp.data.ServiceTweeter;
import com.example.tweeterapp.data.ServiceUser;
import com.example.tweeterapp.data.Tweet;
import com.example.tweeterapp.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class PublicTwitter extends AppCompatActivity {
    int id;
    EditText twtContent;
    ServiceTweeter serviceTweeter;
    Button Cancel, Delete, Publish, Update;
    JSONObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_twitter);
        getSupportActionBar().hide();
        Cancel = findViewById(R.id.btnCancel);
        Publish = findViewById(R.id.btnPublicarTwitter);
        Delete = findViewById(R.id.btnDelete);
        Update = findViewById(R.id.btnUpdate);
        twtContent = findViewById(R.id.txtContentTwitter);
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", 0);

        if(id > 0) {
            String texto = intent.getStringExtra("text");
            twtContent.setText(texto);
            Cancel.setVisibility(View.GONE);
            Publish.setVisibility(View.GONE);
            Delete.setVisibility(View.VISIBLE);
            Update.setVisibility(View.VISIBLE);
        }else {
            Delete.setVisibility(View.GONE);
            Update.setVisibility(View.GONE);
            Cancel.setVisibility(View.VISIBLE);
            Publish.setVisibility(View.VISIBLE);
        }
        serviceTweeter = new ServiceTweeter();
        SharedPreferences sharedPref = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String userData = sharedPref.getString("jsonObj", "{}");
        try {
            object = new JSONObject(userData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void goBack(View view) {
        finish();
    }

    public void goPublication(View view) throws JSONException {
        serviceTweeter = new ServiceTweeter();
        User user = new User();
        Tweet tweet = new Tweet();
        user.mapJson(object);
        Boolean result = tweet.Tweet(twtContent.getText().toString(), "", user);
        if (result) {
            serviceTweeter.publishTweet(tweet, this);
            twtContent.setText("");
        } else {
            Toast.makeText(this, "Los campos deben estar llenos", Toast.LENGTH_SHORT).show();
        }
    }

    public void goUpdate(View view) throws JSONException {
        serviceTweeter = new ServiceTweeter();
        User user = new User();
        Tweet tweet = new Tweet();
        tweet.Id = id;
        user.mapJson(object);
        Boolean result = tweet.Tweet(twtContent.getText().toString(), "", user);
        if (result) {
            int resp = serviceTweeter.updateTweet(tweet, this);
            if(resp >= 200 && resp < 300) {
                Toast.makeText(this, "Twitt actualizado.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ocurrio un fallo.", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            Toast.makeText(this, "Los campos deben estar llenos", Toast.LENGTH_SHORT).show();
        }
    }

    public void goDelete(View view) {
        serviceTweeter = new ServiceTweeter();
        int result = serviceTweeter.DeleteTweets(id);
        if(result >= 200 && result < 300) {
            Toast.makeText(this, "Este Tweet se ha eliminado.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ocurrio un fallo.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
