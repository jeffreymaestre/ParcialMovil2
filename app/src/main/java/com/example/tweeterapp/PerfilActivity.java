package com.example.tweeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tweeterapp.data.ListAdapter;
import com.example.tweeterapp.data.ServiceTweeter;
import com.example.tweeterapp.data.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class PerfilActivity extends AppCompatActivity {
    ListView listView;
    JSONObject object;
    TextView fileName, dateUser;
    LinkedList<Tweet> ListTwitts;
    LinkedList<Tweet> ListTwittsUser;
    ServiceTweeter serviceTweeter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide();
        fileName = findViewById(R.id.PefilName);
        dateUser = findViewById(R.id.PerfilDate);
        ListTwittsUser = new LinkedList<>();
        serviceTweeter = new ServiceTweeter();
        listView = findViewById(R.id.listPerfilPublications);
        SharedPreferences sharedPref = getSharedPreferences("userdata",Context.MODE_PRIVATE);
        String userData = sharedPref.getString("jsonObj", "{}");
        try {
            object = new JSONObject(userData);
            fileName.setText(object.getString("name") + " " +
                    object.getString("lastname"));
            dateUser.setText(object.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getBaseContext(), PublicTwitter.class );
                intent.putExtra("Id", ListTwittsUser.get(position).Id);
                intent.putExtra("text", ListTwittsUser.get(position).text);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListTwitts = new LinkedList<>();
        ListTwittsUser = new LinkedList<>();
        ListTwitts = serviceTweeter.getTweets();
        for (Tweet item: ListTwitts) {
            try {
                if(item.user.Id == object.getInt("Id")) {
                    ListTwittsUser.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listView.setAdapter(new ListAdapter(this, ListTwittsUser));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListTwitts = new LinkedList<>();
        ListTwittsUser = new LinkedList<>();
        ListTwitts = serviceTweeter.getTweets();
        for (Tweet item: ListTwitts) {
            try {
                if(item.user.Id == object.getInt("Id")) {
                    ListTwittsUser.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listView.setAdapter(new ListAdapter(this, ListTwittsUser));
    }

    public void goBack(View view) {
        this.finish();
    }
}
