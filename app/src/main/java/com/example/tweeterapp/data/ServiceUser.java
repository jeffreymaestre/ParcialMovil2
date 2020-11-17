package com.example.tweeterapp.data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import com.example.tweeterapp.TweetList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Permission;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class ServiceUser {
    private String host = "https://1c394563b2a7.ngrok.io";

    public void RegistrarUser(User user, Activity context) throws JSONException {
        try {
            String url = "/tweets/user";
            PostUser post = (PostUser) new PostUser().execute(host + url, user.getJson().toString());
            int result = post.get();
           if(result >= 200 && result < 300) {
               Toast.makeText(context, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
               context.finish();
           }
           if(result > 300 && result < 500) {
               Toast.makeText(context, "Ocurrio un error, intentelo de nuevo mas tarde.", Toast.LENGTH_SHORT).show();
           }
           if(result >= 500) {
               Toast.makeText(context, "Error del servidor.", Toast.LENGTH_SHORT).show();
           }

        }
        catch (JSONException | ExecutionException | InterruptedException e) {
            Log.e("ERRORR", "NO SE SOPORTO EL ENCONDED");
        }
    }


    public String LoginAuthentication(Login user, Activity context) throws JSONException {
        try {
            String url = "/tweets/login/authenticate";
            PostAuth post = (PostAuth) new PostAuth().execute(host + url, user.getJson().toString());
            return post.get();
        }
        catch (JSONException | ExecutionException | InterruptedException e) {
            Log.e("ERRORR", e.getMessage());
            Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private static class PostUser extends AsyncTask<String, Void, Integer> {

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

    private static class PostAuth extends AsyncTask<String, Void, String> {

        StringBuilder json = new StringBuilder();
        @Override
        protected String doInBackground(String... params) {
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
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    json.append(scanner.next());
                }

                // Response: 400
                return json.toString();
            } catch (Exception e) {
                Log.e(e.toString(), "Something with request");
            }
            return null;
        }
    }

    private class PutTweets extends AsyncTask<String, Void, Tweet> {

        @Override
        protected Tweet doInBackground(String... strings) {
            return null;
        }
    }
}
