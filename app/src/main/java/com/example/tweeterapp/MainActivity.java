package com.example.tweeterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tweeterapp.data.Login;
import com.example.tweeterapp.data.ServiceUser;

import org.json.JSONException;

import static com.example.tweeterapp.R.id.txtPassword;

public class MainActivity extends AppCompatActivity {

    ServiceUser serviceUser;
    EditText txtUser, txtPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        serviceUser = new ServiceUser();
        txtUser = findViewById(R.id.txtUser);
        txtPwd = findViewById(R.id.txtPwd);
    }

    public void goRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goSession(View view) throws JSONException {
            Login user = new Login();
            Boolean result = user.login(
                    txtUser.getText().toString(),
                    txtPwd.getText().toString()
            );
            if(result) {
                String json = serviceUser.LoginAuthentication(user, this);
                if(json!=null) {
                    SharedPreferences sharedPref = getSharedPreferences("userdata",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("jsonObj", json);
                    editor.commit();
                    Toast.makeText(this , "Bienvenido a twitter.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, TweetList.class));
                } else {
                    Toast.makeText(this , "Intentelo de nuevo mas tarde.", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Ingrese su usuario y contraseña.", Toast.LENGTH_SHORT).show();
            }
    }
}
