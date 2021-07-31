package com.itsamankrsingh.livetvapp.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itsamankrsingh.livetvapp.models.Channel;

import org.json.JSONException;
import org.json.JSONObject;

public class ChannelDataService {
    Context context;

    public ChannelDataService(Context context) {
        this.context = context;
    }

    public interface OnDataResponse {
        void onResponse(JSONObject response);

        void onError(String error);
    }

    public void getChannelData(String url, OnDataResponse onDataResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onDataResponse.onResponse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onDataResponse.onError(error.getLocalizedMessage());
            }
        });

        queue.add(objectRequest);
    }
}
