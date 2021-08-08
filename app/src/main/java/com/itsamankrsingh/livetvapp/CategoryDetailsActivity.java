package com.itsamankrsingh.livetvapp;

import static com.itsamankrsingh.livetvapp.Constants.DETAILS_TYPE;
import static com.itsamankrsingh.livetvapp.Constants.getCategoriesUrl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.itsamankrsingh.livetvapp.adapters.ChannelAdapter;
import com.itsamankrsingh.livetvapp.models.Category;
import com.itsamankrsingh.livetvapp.models.Channel;
import com.itsamankrsingh.livetvapp.services.ChannelDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailsActivity extends AppCompatActivity {
    RecyclerView categoryDetailsList;
    ChannelAdapter channelAdapter;
    List<Channel> channels;
    ChannelDataService channelDataService;

    public static final String TAG = "CategoryDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        channels = new ArrayList<>();

        Category category = (Category) getIntent().getSerializableExtra("category");
        getSupportActionBar().setTitle(category.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryDetailsList = findViewById(R.id.category_details_list);
        channelAdapter = new ChannelAdapter(channels, DETAILS_TYPE);
        categoryDetailsList.setLayoutManager(new GridLayoutManager(this, 2));
        categoryDetailsList.setAdapter(channelAdapter);

        channelDataService = new ChannelDataService(this);

        String url = getCategoriesUrl(category.getName());

        channelDataService.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {


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

                        channels.add(c);
                        channelAdapter.notifyDataSetChanged();

                        Log.d(TAG, "onResponse: " + c.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}