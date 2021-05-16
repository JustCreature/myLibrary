package com.example.mylibrary.registration;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginPOST {

    Context context;
    String userInfo;
    String userId;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";

    public LoginPOST(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void OnError(String message);

        void OnResponse(String userId, String userInfo);
    }

    public void startParsingTask() {
        Thread threadA = new Thread() {
            public void run() {
                ThreadB threadB = new ThreadB(context);
                JSONObject jsonObject = null;
                try {
                    jsonObject = threadB.execute().get(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                final JSONObject receivedJSONObject = jsonObject;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mTextView.setText("Response is: " + receivedJSONObject);
                        if (receivedJSONObject != null) {
                            try {
//                                mTextView.setText(mTextView.getText() + "\n\n" +
//                                        receivedJSONObject.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        threadA.start();
    }
    private class ThreadB extends AsyncTask<Void, Void, JSONObject> {
        private Context mContext;
        public ThreadB(Context ctx) {
            mContext = ctx;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            final RequestFuture<JSONObject> futureRequest = RequestFuture.newFuture();
            mQueue = CustomVolleyRequestQueue.getInstance(mContext.getApplicationContext())
                    .getRequestQueue();
            String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk";
            final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method
                    .GET, url,
                    new JSONObject(), futureRequest, futureRequest);
            jsonRequest.setTag(REQUEST_TAG);
            mQueue.add(jsonRequest);
            try {
                return futureRequest.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}




//    public void loginPOST(String email, String password, VolleyResponseListener volleyResponseListener) {
//
//
//        try {
////            String URL = "http://10.55.200.196:8080/api/v1/login";
//            String URL = "http://10.51.64.108:8080/api/v1/login";
//            JSONObject jsonBody = new JSONObject();
//
//            jsonBody.put("email", email);
//            jsonBody.put("password", password);
//
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//            RequestFuture<JSONObject> future = RequestFuture.newFuture();
//
//            JsonObjectRequest requestJsonObj = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, future, future);
//
////            AppRequestQueue.getInstance(context).addToRequestQueue(requestJsonObj);
//            requestQueue.add(requestJsonObj);
//
//            try {
//                JSONObject response = future.get(); // this will block
//
//                userId = response.get("id").toString();
//                userInfo = response.get("firstName").toString() + response.get("lastName").toString();
//                volleyResponseListener.OnResponse(userId, userInfo);
//
//            } catch (InterruptedException e) {
//                // exception handling
//            } catch (ExecutionException e) {
//                // exception handling
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
}


//        new Response.Listener<JSONObject>() {
//@Override
//public void onResponse(JSONObject response) {
//
//
//        try {
//        userId = response.get("id").toString();
//        userInfo = response.get("firstName").toString() + response.get("lastName").toString();
//        volleyResponseListener.OnResponse(userId, userInfo);
//        } catch (JSONException e) {
//        volleyResponseListener.OnError(e.toString());
//        e.printStackTrace();
//        }
//        }
//        }, new Response.ErrorListener() {
//@Override
//public void onErrorResponse(VolleyError error) {
//        volleyResponseListener.OnError("Request Failed!");
//        }