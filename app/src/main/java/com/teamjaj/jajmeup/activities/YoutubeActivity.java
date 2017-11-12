package com.teamjaj.jajmeup.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.teamjaj.jajmeup.R;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyCKhpFxWKG0owZMwg0HIJeHSpop5WjzMBQ";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // attaching layout xml
        setContentView(R.layout.activity_youtube);

        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);
        VIDEO_ID = getIntent().getStringExtra("link");

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        //if(null== player) return;

        player.loadVideo(VIDEO_ID);

        // Start buffering
        /*if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }*/
    }

    public void stopButtonClick(View view){
        finish();
    }

}
