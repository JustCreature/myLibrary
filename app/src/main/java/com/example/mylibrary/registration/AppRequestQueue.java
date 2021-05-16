package com.example.mylibrary.registration;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppRequestQueue {
    private static AppRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private AppRequestQueue(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }

    public static synchronized AppRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new AppRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
