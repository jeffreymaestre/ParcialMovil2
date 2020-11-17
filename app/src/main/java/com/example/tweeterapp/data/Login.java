package com.example.tweeterapp.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {
    public int Id;
    public String usuario;
    public String password;

    public Login() {
        Id = 0;
        this.usuario = null;
        this.password = null;
    }

    public boolean login(String usuario, String password) {
        if(usuario.isEmpty() || password.isEmpty())
            return false;
        this.usuario = usuario;
        this.password = password;
        return true;
    }

    public JSONObject getJson() throws JSONException {
        /**
         * {
         *   "Id": null,
         *   "usuario": "kaka",
         *   "password": "1232"
         * }
         * */
        JSONObject Login = new JSONObject();
        Login.put("Id", null);
        Login.put("usuario", this.usuario);
        Login.put("password", this.password);
        return Login;
    }
}
