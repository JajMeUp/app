package com.teamjaj.jajmeup.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
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
    private String voter;
    private String message_voter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // attaching layout xml
        setContentView(R.layout.activity_youtube);

        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);
        VIDEO_ID = getIntent().getStringExtra("link");
        voter = getIntent().getStringExtra("voter");
        message_voter = getIntent().getStringExtra("message");

        TextView text_voter = (TextView)findViewById(R.id.voter);
        text_voter.setText(Html.fromHtml("<b>"+voter+"</b>"+" t'as réveillé !"), TextView.BufferType.EDITABLE);
        TextView text_message = (TextView)findViewById(R.id.voter_message);
        text_message.setText(message_voter, TextView.BufferType.EDITABLE);

        if (VIDEO_ID == "")
        {
            VIDEO_ID = "dQw4w9WgXcQ";
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        player.loadVideo(VIDEO_ID);

    }

    public void stopButtonClick(View view){
        /*player.
        DialogFragmentMessage fragParamReveil = new DialogFragmentMessage();
        // fragParamReveil.setArguments(target);
        fragParamReveil.show(getSupportFragmentManager(), "YouTube Link");*/
        finish();
       /*Intent intent = new Intent();
        intent.setAction("jajmeup.messagereveil");
        intent.putExtra("voter",voter);
        intent.putExtra("message_voter", message_voter);
        sendBroadcast(intent);*/
    }

}
