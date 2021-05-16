package com.example.mylibrary.registration;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mylibrary.R;

public class RegisterFormValidator {

    private static final String TAG = "RegisterFormValidator";

    public static TextWatcher validateName(Context regContext, TextView errTextView, int errText) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                validate(regContext,
                        errTextView,
                        errText,
                        s.toString() == null || s.toString().trim().isEmpty() ||
                                s.toString().contains("@") || s.toString().contains(".") ||
                                s.toString().contains(",") || s.toString().contains("!") ||
                                s.toString().length() > 100);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        return textWatcher;
    }

    public static TextWatcher validateEmail(Context regContext, TextView errTextView) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                validate(regContext,
                        errTextView,
                        R.string.regFormError_email,
                        s.toString() == null || s.toString().trim().isEmpty() ||
                                s.toString().contains(",") || s.toString().contains("!") ||
                                !s.toString().contains("@") || s.toString().length() > 100);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        return textWatcher;
    }

    public static TextWatcher validatePassword(Context regContext,
                                                 TextView errTextView,
                                                 EditText rePass) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                validate(regContext,
                        errTextView,
                        R.string.regFormError_password,
                        s.toString() == null || s.toString().trim().isEmpty() ||
                                s.toString().length() < 5 || s.toString().length() > 100);

                rePass.setText("");



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        return textWatcher;
    }

    public static TextWatcher validateRePassword(Context regContext,
                                                 TextView errTextView,
                                                 EditText pass) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                validate(regContext,
                        errTextView,
                        R.string.regFormError_rePassword,
                        !s.toString().equals(pass.getText().toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        return textWatcher;
    }

    public static void validate(Context regContext,
                                TextView errTextView,
                                int errText,
                                Boolean isValid) {
        if (!isValid) {


            errTextView.setText(R.string.regFormValid);
            errTextView.setTextColor(ContextCompat.getColor(regContext, R.color.regFormValid));
            errTextView.setVisibility(View.VISIBLE);
        } else {
            errTextView.setText(errText);
            errTextView.setTextColor(ContextCompat.getColor(regContext, R.color.regFormError));
            errTextView.setVisibility(View.VISIBLE);
        }
    }

    public static Boolean validateAll(EditText firstName,
                                   EditText lastName,
                                   EditText email,
                                   EditText password,
                                   EditText rePassword) {
        return !(firstName.getText().toString() == null || firstName.getText().toString().trim().isEmpty() ||
                firstName.getText().toString().contains("@") || firstName.getText().toString().contains(".") ||
                firstName.getText().toString().contains(",") || firstName.getText().toString().contains("!") ||
                firstName.getText().toString().length() > 100)
                &&
                !(lastName.getText().toString() == null || lastName.getText().toString().trim().isEmpty() ||
                lastName.getText().toString().contains("@") || lastName.getText().toString().contains(".") ||
                lastName.getText().toString().contains(",") || lastName.getText().toString().contains("!") ||
                lastName.getText().toString().length() > 100)
                &&
                !(email.getText().toString() == null || email.getText().toString().trim().isEmpty() ||
                email.getText().toString().contains(",") || email.getText().toString().contains("!") ||
                !email.getText().toString().contains("@") || email.getText().toString().length() > 100)
                &&
                !(password.getText().toString() == null || password.getText().toString().trim().isEmpty() ||
                password.getText().toString().length() < 5 || password.getText().toString().length() > 100)
                &&
                (rePassword.getText().toString().equals(password.getText().toString()));
    }

}
