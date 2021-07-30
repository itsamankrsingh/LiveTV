package com.itsamankrsingh.livetvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itsamankrsingh.livetvapp.adapters.ChannelAdapter;
import com.itsamankrsingh.livetvapp.models.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    RecyclerView bigSliderList;
    ChannelAdapter bigSliderAdapter;
    List<Channel> channelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelList = new ArrayList<>();

        bigSliderList = findViewById(R.id.big_slider_list);
        bigSliderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        bigSliderAdapter = new ChannelAdapter(channelList, "slider");
        bigSliderList.setAdapter(bigSliderAdapter);

        getChannelData("http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&channels=all");
    }

    public void getChannelData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject channelData = response.getJSONObject(String.valueOf(i));
                                Channel c = new Channel();

                                c.setId(channelData.getInt("id"));
                                c.setName(channelData.getString("name"));
                                c.setDescription(channelData.getString("description"));
                                c.setThumbnail(channelData.getString("thumbnail"));
                                c.setLive_url(channelData.getString("live_url"));
                                c.setFacebook(channelData.getString("facebook"));
                                c.setTwitter(channelData.getString("twitter"));
                                c.setYoutube(channelData.getString("youtube"));
                                c.setWebsite(channelData.getString("website"));
                                c.setCategory(channelData.getString("category"));

                              channelList.add(c);
                              bigSliderAdapter.notifyDataSetChanged();

                                Log.d(TAG,"onResponse: " +c.toString());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(objectRequest);
    }
}