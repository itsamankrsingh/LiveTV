package com.itsamankrsingh.livetvapp;

import static com.itsamankrsingh.livetvapp.Constants.CATEGORIES_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsamankrsingh.livetvapp.adapters.CategoryAdapter;
import com.itsamankrsingh.livetvapp.models.Category;
import com.itsamankrsingh.livetvapp.models.Channel;
import com.itsamankrsingh.livetvapp.services.ChannelDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoriesActivity extends AppCompatActivity {

    public static final String TAG = "CategoriesActivity";

    //private static final String URL = "http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&categories=all";

    RecyclerView categoryLists;
    CategoryAdapter categoryAdapter;
    List<Category> categories;
    ChannelDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dataService = new ChannelDataService(this);

        categories = new ArrayList<>();
        categoryLists = findViewById(R.id.category_lists);
        categoryLists.setLayoutManager(new GridLayoutManager(this, 2));
        categoryAdapter = new CategoryAdapter(categories);

        categoryLists.setAdapter(categoryAdapter);

        dataService.getChannelData(CATEGORIES_URL, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, "onResponse: " + response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));

                        Category category = new Category(
                                channelData.getInt("id"),
                                channelData.getString("name"),
                                channelData.getString("image_url")
                        );
                        categories.add(category);
                        categoryAdapter.notifyDataSetChanged();

                        Log.d(TAG, "onResponse: " + category.toString());


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