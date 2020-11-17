package com.example.tweeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tweeterapp.data.Login;
import com.example.tweeterapp.data.ServiceTweeter;
import com.example.tweeterapp.data.ServiceUser;
import com.example.tweeterapp.data.User;

import org.json.JSONException;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ServiceUser serviceUser;
    DatePickerDialog datePickerDialog;
    EditText fechaNacimiento, Nombre, Apellidos, ImageUrl, txtUser, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Nombre = findViewById(R.id.txtNombre);
        Apellidos = findViewById(R.id.txtApellidos);
        ImageUrl = findViewById(R.id.txtImageUrl);
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        fechaNacimiento  = findViewById(R.id.txtFechaNacimiento);
        getSupportActionBar().hide();
        datePickerDialog = new DatePickerDialog(this, this, 1900, 1,1);
    }

    public void goSesion(View view) {
        this.finish();
    }

    public void goDatePicker(View view) {
        switch (view.getId()) {
            case R.id.txtFechaNacimiento:
                datePickerDialog.show();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        fechaNacimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    public void goRegister(View view) throws JSONException {
        serviceUser = new ServiceUser();
        Login login = new Login();
        Boolean resultLogin = login.login(txtUser.getText().toString(), txtPassword.getText().toString());
        User user = new User();
        Boolean resultUser = user.User(
                ImageUrl.getText().toString(),
                Nombre.getText().toString(),
                Apellidos.getText().toString(),
                fechaNacimiento.getText().toString(),
                login
        );
        if (resultUser && resultLogin) {
            serviceUser.RegistrarUser(user, this);
        } else {
            Toast.makeText(this, "Los campos deben estar llenos", Toast.LENGTH_SHORT).show();
        }
    }
}
