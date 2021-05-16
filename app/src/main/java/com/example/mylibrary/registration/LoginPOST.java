package com.example.mylibrary.registration;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPOST {

    Context context;
    String userInfo;
    String userId;

    public LoginPOST(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void OnError(String message);

        void OnResponse(String userId, String userInfo);
    }

    public void loginPOST(String email, String password, VolleyResponseListener volleyResponseListener) {


        try {
//            String URL = "http://10.55.200.196:8080/api/v1/login";
            String URL = "http://10.51.64.108:8080/api/v1/login";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest requestJsonObj = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        userId = response.get("id").toString();
                        userInfo = response.get("firstName").toString() + " " + response.get("lastName").toString();
                        volleyResponseListener.OnResponse(userId, userInfo);
                    } catch (JSONException e) {
                        volleyResponseListener.OnError(e.toString());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.OnError("Request Failed!");
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                    return headers;
                }
            };

            AppRequestQueue.getInstance(context).addToRequestQueue(requestJsonObj);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
