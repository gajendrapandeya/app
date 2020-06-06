package com.copy.lms.util;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.HashMap;
import java.util.Map;

public class ExoPlayerViewManager {

  private static final String TAG = "ExoPlayerViewManager";

  public static final String EXTRA_VIDEO_URI = "video_uri";

  private static Map<String, ExoPlayerViewManager> instances = new HashMap<>();
  private Uri videoUri;

  public static ExoPlayerViewManager getInstance(String videoUri) {
    ExoPlayerViewManager instance = instances.get(videoUri);
    if (instance == null) {
      instance = new ExoPlayerViewManager(videoUri);
      instances.put(videoUri, instance);
    }
    return instance;
  }

  private SimpleExoPlayer player;
  private boolean isPlayerPlaying;

  private ExoPlayerViewManager(String videoUri) {
    this.videoUri = Uri.parse(videoUri);
  }

  public void prepareExoPlayer(Context context, PlayerView exoPlayerView) {
    if (context == null || exoPlayerView == null) {
      return;
    }
    if (player == null) {
      // Create a new player if the player is null or
      // we want to play a new video

      // Do all the standard ExoPlayer code here...

      // Prepare the player with the source.
//      player.prepare(videoSource);
    }
    player.clearVideoSurface();
    player.setVideoSurfaceView((SurfaceView) exoPlayerView.getVideoSurfaceView());
    player.seekTo(player.getCurrentPosition() + 1);
    exoPlayerView.setPlayer(player);
  }

  public void releaseVideoPlayer() {
    if (player != null) {
      player.release();
    }
    player = null;
  }

  public void goToBackground() {
    if (player != null) {
      isPlayerPlaying = player.getPlayWhenReady();
      player.setPlayWhenReady(false);
    }
  }

  public void goToForeground() {
    if (player != null) {
      player.setPlayWhenReady(isPlayerPlaying);
    }
  }
}