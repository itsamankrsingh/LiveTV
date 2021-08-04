package com.itsamankrsingh.livetvapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.itsamankrsingh.livetvapp.models.Channel;

public class Details extends AppCompatActivity {

    PlayerView playerView;
    TextView description;
    ImageView facebookLink, youtubeLink, twitterLink, webLink;
    ImageView fullScreen;
    boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(channel.getName());

        playerView = findViewById(R.id.player_view);
        fullScreen = playerView.findViewById(R.id.exo_fullscreen_icon);

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    Toast.makeText(Details.this, "We are now going back to normal mode", Toast.LENGTH_SHORT).show();

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width=params.MATCH_PARENT;
                    params.height=(int)(200*getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    isFullScreen = false;
                } else {
                    //Toast.makeText(Details.this, "We are going to full screen mode", Toast.LENGTH_SHORT).show();

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width=params.MATCH_PARENT;
                    params.height=params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    isFullScreen = true;
                }

            }
        });

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

        playChannel(channel.getLive_url());
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

    public void playChannel(String liveUrl) {
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(liveUrl);

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }
}