package com.copy.lms.activity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.copy.lms.R;
import com.copy.lms.databinding.ActivityFullscreenVideoBinding;
import com.copy.lms.databinding.ActivityLessionlistBinding;
import com.copy.lms.model.MainActivityModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

// Fullscreen related code taken from Android Studio blueprint
public class FullscreenVideoActivity extends AppCompatActivity {

    private MainActivityModel model;
    private ActivityFullscreenVideoBinding binding;
    SimpleExoPlayer player;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen_video);
        model = new MainActivityModel();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(FullscreenVideoActivity.this, trackSelector);
        binding.exoplayer.setPlayer(player);
        String fullScreen = getIntent().getStringExtra("fullScreenInd");
        if ("y".equals(fullScreen)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }

        Uri videoUri = Uri.parse(getIntent().getStringExtra("url"));

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(FullscreenVideoActivity.this,
                Util.getUserAgent(FullscreenVideoActivity.this, getResources().getString(R.string.app_name)));
        // This is the MediaSource representing the media to be played.

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
//                         Prepare the exoplayer with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady) {
//                    progressBar.setVisibility(View.GONE);
                }
                super.onPlayerStateChanged(playWhenReady, playbackState);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
