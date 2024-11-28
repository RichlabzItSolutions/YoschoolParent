package com.credoapp.parent.events;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.credoapp.parent.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubeVideos extends YouTubeBaseActivity {
    private String youTubeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_videos);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                youTubeUrl = null;
            } else {
                youTubeUrl = extras.getString("value");
            }
        } else {
            youTubeUrl = (String) savedInstanceState.getSerializable("value");
        }


        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoViewYoutube);
        YouTubePlayer.OnInitializedListener onInitializedListener;
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("", "onInitializationSuccess: youtube player");
                youTubePlayer.loadVideo(youTubeUrl);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("", "onInitializationFailure: youtube player");
            }
        };
        youTubePlayerView.initialize("", onInitializedListener);
    }
}
