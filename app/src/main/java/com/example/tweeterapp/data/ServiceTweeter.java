package com.example.tweeterapp.data;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ServiceTweeter {
    private String host = "https://1c394563b2a7.ngrok.io";

    public Integer publishTweet(Tweet user, Activity context) throws JSONException {
        try {
            String url = "/tweets";
            ServiceTweeter.PostTweets post = (PostTweets) new ServiceTweeter.PostTweets().execute(host + url, user.getJson().toString());
            int result = post.get();
            if (result >= 200 && result < 300) {
                Toast.makeText(context, "Su Twitt fue publicado.", Toast.LENGTH_SHORT).show();
                context.finish();
            }
            if (result > 300 && result < 500) {
                Toast.makeText(context, "Ocurrio un error, intentelo de nuevo mas tarde.", Toast.LENGTH_SHORT).show();
            }
            if (result >= 500) {
                Toast.makeText(context, "Error del servidor.", Toast.LENGTH_SHORT).show();
            }
            return result;
        } catch (JSONException | ExecutionException | InterruptedException e) {
            Log.e("ERRORR", e.getMessage());
            Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public LinkedList<Tweet> getTweets() {
        try {
            String url = "/tweets";
            ServiceTweeter.GetTweets getTweets = (GetTweets) new GetTweets().execute(host + url);
            return getTweets.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public Integer DeleteTweets(int Id) {
        try {
            String url = "/tweets/" + Id;
            ServiceTweeter.DeleteTweets deleteTweets = (DeleteTweets) new DeleteTweets().execute(host + url);
            return deleteTweets.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer updateTweet(Tweet user, Activity context) throws JSONException {
        try {
            String url = "/tweets";
            ServiceTweeter.PutTweets post = (PutTweets) new ServiceTweeter.PutTweets().execute(host + url, user.getJson().toString());
            return post.get();
        }
        catch (JSONException | ExecutionException | InterruptedException e) {
            Log.e("ERRORR", e.getMessage());
            Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private static class PostTweets extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "*/*");
                connection.setDoOutput(true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(params[1]);
                writer.close();
                connection.connect();
                // Response: 400
                Log.e("Response", connection.getResponseCode() + "");
                return connection.getResponseCode();
            } catch (Exception e) {
                Log.e(e.toString(), "Something with request");
            }
            return 500;
        }
    }

    private static class GetTweets extends AsyncTask<String, Void, LinkedList<Tweet>> {

        @Override
        protected LinkedList<Tweet> doInBackground(String... params) {
            LinkedList<Tweet> twitts = new LinkedList<>();
            StringBuilder List = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setDoOutput(false);
                connection.setConnectTimeout(5000);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    List.append(line+"\n");
                }
                br.close();

                JSONObject obj = new JSONObject(List.toString());
                JSONArray listTweets = obj.getJSONArray("data");
                for (int i = 0; i < listTweets.length(); i++) {
                    Tweet item = new Tweet();
                    User usitem = new User();
                    JSONObject twitt = listTweets.getJSONObject(i);
                    item.Id = twitt.getInt("Id");
                    item.date = twitt.getString("date");
                    item.text = twitt.getString("text");
                    JSONObject user = twitt.getJSONObject("user");
                    usitem.Id = user.getInt("Id");
                    usitem.name = user.getString("name");
                    usitem.lastname = user.getString("lastname");
                    usitem.date = user.getString("date");
                    usitem.image = user.getString("image");
                    item.user = usitem;
                    twitts.add(item);
                }
                return twitts;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return new LinkedList<>();
        }
    }

    private static  class DeleteTweets extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "*/*");
                connection.setDoOutput(false);
                connection.connect();
                // Response: 400
                Log.e("Response", connection.getResponseCode() + "");
                return connection.getResponseCode();
            } catch (Exception e) {
                Log.e(e.toString(), "Something with request");
            }
            return 500;
        }
    }

    private static class PutTweets extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "*/*");
                connection.setDoOutput(true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(params[1]);
                writer.close();
                connection.connect();
                // Response: 400
                Log.e("Response", connection.getResponseCode() + "");
                return connection.getResponseCode();
            } catch (Exception e) {
                Log.e(e.toString(), "Something with request");
            }
            return 500;
        }
    }
}
