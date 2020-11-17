package com.example.tweeterapp.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
    public int Id;
    public String text;
    public String date;
    public User user;

    public Tweet(){
        Id = 0;
        text = null;
        date = null;
        user = new User();
    }

    public boolean Tweet(String text, String date, User user) {
        if (text.isEmpty())
            return false;
        this.date = "";
        this.text = text;
        this.user = user;
        return true;
    }

    public JSONObject getJson() throws JSONException {
        /**
         * {
         *   "Id": "juan",
         *   "text": "juan",
         *   "date": "Sat Nov 07 2020",
         *   "user": {
         *     "Id": 1,
         *     "image": "",
         *     "name": "Jorsh",
         *     "lastname": "williams"
         *   }
         * }
         * */

        JSONObject Tweet = new JSONObject();
        if (Id == 0) {
            Tweet.put("Id", null);
        } else {
            Tweet.put("Id", Id);
        }
        Tweet.put("text", this.text);
        Tweet.put("date", this.date);
        JSONObject User = new JSONObject();
        User.put("Id", this.user.Id);
        User.put("image", this.user.image);
        User.put("name", this.user.name);
        User.put("lastname", this.user.lastname);
        User.put("date", this.user.date);
        Tweet.put("user",User);
        return Tweet;
    }

}
