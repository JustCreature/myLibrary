package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibrary.registration.RegisterFormValidator;
import com.example.mylibrary.registration.RegistrPOST;
import com.example.mylibrary.registration.RegistrUser;
import com.example.mylibrary.ui.login.LoginActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText firstName, lastName, email, password, rePassword;
    private TextView errorFirstName, errorLastName, errorEmail, errorPassword, errorRePassword;
    private Button btnRegister;
    private RegisterFormValidator validator;
    private RegistrPOST registrPOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstName = findViewById(R.id.et_firstName);
        lastName = findViewById(R.id.et_lastName);
        email = findViewById(R.id.et_regEmail);
        password = findViewById(R.id.et_regPassword);
        rePassword = findViewById(R.id.et_regRePassword);

        btnRegister = findViewById(R.id.btn_regRegister);

        errorFirstName = findViewById(R.id.tv_regFormErr_firstName);
        errorLastName = findViewById(R.id.tv_regFormErr_lastName);
        errorEmail = findViewById(R.id.tv_regFormErr_email);
        errorPassword = findViewById(R.id.tv_regFormErr_password);
        errorRePassword = findViewById(R.id.tv_regFormErr_rePassword);


        firstName.addTextChangedListener(RegisterFormValidator.
                validateName(this, errorFirstName, R.string.regFormError_firstName));
        lastName.addTextChangedListener(RegisterFormValidator.
                validateName(this, errorLastName, R.string.regFormError_lastName));
        email.addTextChangedListener(RegisterFormValidator.
                validateEmail(this, errorEmail));
        password.addTextChangedListener(RegisterFormValidator.
                validatePassword(this, errorPassword, rePassword));
        rePassword.addTextChangedListener(RegisterFormValidator.
                validateRePassword(this, errorRePassword, password));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isFormValid = RegisterFormValidator.validateAll(
                        firstName,
                        lastName,
                        email,
                        password,
                        rePassword
                );

                if (isFormValid) {

                    RegistrPOST.sendPostRegisterRequest(
                            getApplicationContext(),
                            new RegistrUser(
                                    firstName.getText().toString(),
                                    lastName.getText().toString(),
                                    email.getText().toString(),
                                    password.getText().toString()
                            )
                    );

                    onBackPressed();

                } else {
                    Toast.makeText(
                            RegisterActivity.this,
                            "Correct Registration form", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            onBackPressed();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}