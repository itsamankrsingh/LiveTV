package com.itsamankrsingh.livetvapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.itsamankrsingh.livetvapp.models.Channel;

public class Details extends AppCompatActivity {

    PlayerView playerView;
    TextView description;
    ImageView facebookLink, youtubeLink, twitterLink, webLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(channel.getName());

        playerView = findViewById(R.id.player_view);

        description = findViewById(R.id.channel_desc);

        facebookLink = findViewById(R.id.facebook_link);
        youtubeLink = findViewById(R.id.youtube_link);
        twitterLink = findViewById(R.id.twitter_link);
        webLink = findViewById(R.id.web_link);

        description.setText(channel.getDescription());

        facebookLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getFacebook());
            }
        });

        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getWebsite());
            }
        });

        youtubeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getYoutube());
            }
        });

        twitterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getTwitter());
            }
        });
    }

    public void openLink(String url) {
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}