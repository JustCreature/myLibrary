package com.example.mylibrary.data;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibrary.data.model.LoggedInUser;
import com.example.mylibrary.registration.LoginPOST;
import com.example.mylibrary.registration.RegistrPOST;
import com.example.mylibrary.registration.RegistrUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    LoggedInUser loggedInUser;

    public LoginDataSource() {
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public Result<LoggedInUser> login(String userId, String userInfo) {

        try {
            loggedInUser = new LoggedInUser(userId, userInfo);
            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void setUser (String userId, String userInfo) {
        loggedInUser = new LoggedInUser(userId, userInfo);
    }

    public void logout() {
        // TODO: revoke authentication
    }

}