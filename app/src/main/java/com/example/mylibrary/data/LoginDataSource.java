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

    public Result<LoggedInUser> login(Context postContext, String username, String password) {

        LoginPOST loginPOST = new LoginPOST(postContext);
        String response;
        final LoggedInUser[] loggedInUser = new LoggedInUser[1];

        try {
            // TODO: handle loggedInUser authentication

            loginPOST.loginPOST(username, password,
                    new LoginPOST.VolleyResponseListener() {
                @Override
                public void OnError(String message) {
                    try {
                        throw new Throwable(message);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();

                    }
                }

                @Override
                public void OnResponse(String userId, String userInfo) {
                    Toast.makeText(postContext, "Success " + userInfo, Toast.LENGTH_SHORT).show();
                    loggedInUser[0] = new LoggedInUser(userId, userInfo);
                }
            });
            int i = 0;
            while (loggedInUser[0] == null) {
                i++;
            }



            return new Result.Success<>(loggedInUser[0]);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}