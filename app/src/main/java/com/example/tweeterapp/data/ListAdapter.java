package com.example.tweeterapp.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tweeterapp.R;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;

public class ListAdapter extends ArrayAdapter<Tweet> {

    public ListAdapter(@NonNull Context context, @NonNull LinkedList<Tweet> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tweet user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.twitter_item, parent, false);
        }
        // Lookup view for data population
        TextView tvContent = (TextView) convertView.findViewById(R.id.twtTweetContent);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.twtUserName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.datePublish);
        // Populate the data into the template view using the data object
        tvContent.setText(user.text);
        tvUserName.setText(user.user.name + " " + user.user.lastname);
        tvDate.setText(user.date);
        // Return the completed view to render on screen
        return convertView;
    }
}
