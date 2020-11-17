package com.example.tweeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.tweeterapp.data.ListAdapter;
import com.example.tweeterapp.data.ServiceTweeter;
import com.example.tweeterapp.data.Tweet;

import java.util.LinkedList;

public class TweetList extends AppCompatActivity {
    LinkedList<Tweet> ListTwitts;
    ServiceTweeter serviceTweeter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
        getSupportActionBar().hide();
        serviceTweeter = new ServiceTweeter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListTwitts = new LinkedList<>();
        ListTwitts = serviceTweeter.getTweets();
        ListView listView = (ListView) findViewById(R.id.ListTweets);
        listView.setAdapter(new ListAdapter(this, ListTwitts));
    }

    public void goPerfil(View view) {
        startActivity(new Intent(this, PerfilActivity.class));
    }

    public void goPublish(View view) { startActivity(new Intent(this, PublicTwitter.class)); }

    public void goUpdate(View view) {
        ListTwitts = serviceTweeter.getTweets();
        ListView listView = (ListView) findViewById(R.id.ListTweets);
        listView.setAdapter(new ListAdapter(this, ListTwitts));
    }
}
