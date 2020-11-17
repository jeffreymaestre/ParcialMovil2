package com.example.tweeterapp.data;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public int Id;
    public String image;
    public String name;
    public String date;
    public String lastname;
    public Login user;

    public User() {
        Id = 0;
        image = null;
        name = null;
        lastname = null;
        date = null;
        user = new Login();
    }

    public boolean User(String image, String name, String lastname, String date, Login login) {
        if (image.isEmpty() || name.isEmpty() || lastname.isEmpty() || date.isEmpty())
            return false;
        this.image = image;
        this.name = name;
        this.lastname = lastname;
        this.date = date;
        user = login;
        return true;
    }

    public JSONObject getJson() throws JSONException {
        /**
         * {
         *   "Id": 1,
         *   "image": "",
         *   "name": "Jorsh",
         *   "lastname": "Washington",
         *   "user": {
         *     "Id": null,
         *     "password": "123",
         *     "usuario": "jorsh"
         *   }
         * }
         * */
        JSONObject User = new JSONObject();
        User.put("Id", null);
        User.put("image", this.image);
        User.put("name", this.name);
        User.put("date", this.date);
        User.put("lastname", this.lastname);
        JSONObject Login = new JSONObject();
        Login.put("Id", null);
        Login.put("password", this.user.password);
        Login.put("usuario", this.user.usuario);
        User.put("user", Login);
        return User;
    }

    public void mapJson(JSONObject User) throws JSONException {
        Id = User.getInt("Id");
        image = User.getString("image");
        name = User.getString("name");
        date = User.getString("date");
        lastname = User.getString("lastname");
    }
}
