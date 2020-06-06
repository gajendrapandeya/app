package com.copy.lms.activity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.copy.lms.BaseAppClass;
import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSingleLession;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.adapter.LessionListAdapter;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityLessionlistBinding;
import com.copy.lms.model.CourseDetailModel;
import com.copy.lms.model.LessionListModel;
import com.copy.lms.model.LessionModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AlertDialogAndIntents;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import java.net.MalformedURLException;
import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LessionListActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, View.OnClickListener {


    public static final String VALUE = "value";
    private static final String TAG = "LessionListActivity";
    private LessionListModel model;
    private ActivityLessionlistBinding binding;
    SimpleExoPlayer player;
    public static final String LESSION_ID = "lessionId";
    public static final String LESSIONTIMELINE = "lessiontimeline";

    String lessionId;
    YouTubePlayer youTubePlayer;
    boolean fullscreen = false;
    String videoUrl;
    String pdfFileName;
    String audioFile;
    Integer pageNumber = 0;

    MediaPlayer mp;
    String courseId;

    boolean isPlaying = false;
    CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessionlist);
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, LessionListActivity.this).createProgressBar();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_lessionlist);
        model = new LessionListModel();
        model.setArrayList(new ArrayList<LessionModel>());
        binding.setLessionListModel(model);

        try {
            // Initializing video player with developer key
            // AIzaSyAsgtOvy1dr8jcVSUFqy63wB2X8KW4TFT0
            binding.youtubeView.initialize(Config.DEVELOPER_KEY, LessionListActivity.this);
        } catch (Exception e) {

        }
        setToolBar();
        initViews();


    }


    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());

        super.onResume();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void initViews() {
        mp = new MediaPlayer();
        lessionId = getIntent().getStringExtra(LESSION_ID);
        courseId = getIntent().getStringExtra(TestActivity.COURSEID);
        binding.included.txtTitle.setText(getString(R.string.app_name));
        binding.included.imgBack.setOnClickListener(this);
        ArrayList<CourseDetailModel> models = getIntent().getParcelableArrayListExtra(LESSIONTIMELINE);
        if (models != null) {
            model.setCourseDetailModelArrayList(models);
        } else {
            model.setCourseDetailModelArrayList(new ArrayList<CourseDetailModel>());
        }
        singleLessionDetail();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(LessionListActivity.this, trackSelector);
        binding.exoplayer.setPlayer(player);
        binding.exoplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent buyIntent = new Intent(LessionListActivity.this, FullscreenVideoActivity.class);
                buyIntent.putExtra("url", videoUrl);
                startActivity(buyIntent);


            }
        });

        binding.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url="
                        + pdfFileName), "text/html");
                startActivity(intent);
            }
        });
        binding.txtAudioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    mp.pause();
                    binding.txtAudioPlay.setText("Play");

                } else {
                    try {
                        mp.setDataSource(audioFile);
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binding.txtAudioPlay.setText("Pause");

                }

            }
        });

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LessionListAdapter(LessionListActivity.this,
                model.getCourseDetailModelArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                if (model.getCourseDetailModelArrayList().get(position).getType().equalsIgnoreCase("test")) {
//                    if (!model.getCourseDetailModelArrayList().get(position).getCompleted()) {
                    Intent intent = new Intent(LessionListActivity.this, TestActivity.class);
                    intent.putExtra(TestActivity.TITLE, model.getCourseDetailModelArrayList().get(position).getTitle() + "  ");
                    intent.putExtra(TestActivity.COURSEID, courseId);
                    intent.putExtra(TestActivity.ISCOMPLATED, true);
                    intent.putExtra(TestActivity.TESTID, model.getCourseDetailModelArrayList().get(position).getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);
//                    }
//                    else {
//                        Intent intent = new Intent(LessionListActivity.this, TestActivity.class);
//                        intent.putExtra(TestActivity.TITLE, model.getCourseDetailModelArrayList().get(position).getTitle() + "  ");
//                        intent.putExtra(TestActivity.COURSEID, courseId);
//                        intent.putExtra(TestActivity.TESTID, model.getCourseDetailModelArrayList().get(position).getId());
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.animation, R.anim.animation2);
//
//                    }
                } else {
                    saveProgress();
                    Intent intent = new Intent(LessionListActivity.this, LessionListActivity.class);
                    intent.putExtra(LessionListActivity.LESSION_ID, CourseDetailActivity.model.getArrayList().get(position).getId() + "  ");
                    intent.putExtra(TestActivity.COURSEID, courseId);
                    intent.putParcelableArrayListExtra(LessionListActivity.LESSIONTIMELINE, CourseDetailActivity.model.getArrayList());
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);
                    finish();
                }


            }
        }));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((recyclerView.getLayoutManager().getChildCount()
                        + ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                        >= recyclerView.getLayoutManager().getItemCount()) {

                    if (/*model.getCount() > model.getArrayList().size() &&*/ !model.isApiCallActive()) {

                    }
                }
            }
        });

    }

    public void singleLessionDetail() {
        dialog.setCancelable(false);
        dialog.show();

        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getSingleLession(lessionId,
                            courseCallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback courseCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSingleLession netSingleLession = (NetSingleLession) object;
            if (netSingleLession.getStatus().equalsIgnoreCase("success")) {
                initRecycler();
                binding.txtTitle.setText(netSingleLession.getResult().getLesson().getTitle());
                binding.txtDes.setText(netSingleLession.getResult().getLesson().getFull_text());
//                fillArrayList(netSingleLession.getResult().getLesson());

                // Produces DataSource instances through which media data is loaded.

                if (netSingleLession.getResult().getLesson().getMedia_video() != null) {
                    binding.llWeb.setVisibility(View.GONE);
                    if (netSingleLession.getResult().getLesson().getMedia_video().getType().equalsIgnoreCase("youtube")) {

                        try {
                            youTubePlayer.loadVideo(AlertDialogAndIntents.extractYoutubeId(netSingleLession.getResult().getLesson().getMedia_video().getUrl()));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        binding.youtubeView.setVisibility(View.VISIBLE);
                        binding.exoplayer.setVisibility(View.GONE);
                    } else {

                        videoUrl = netSingleLession.getResult().getLesson().getMedia_video().getUrl();
                        binding.youtubeView.setVisibility(View.GONE);
                        binding.exoplayer.setVisibility(View.VISIBLE);
                        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(LessionListActivity.this,
                                Util.getUserAgent(LessionListActivity.this, getResources().getString(R.string.app_name)));
                        // This is the MediaSource representing the media to be played.

                        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(Uri.parse(netSingleLession.getResult().getLesson().getMedia_video().getUrl()));
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
                }
                if (netSingleLession.getResult().getLesson().getMedia_p_d_f() != null) {
                    binding.llWeb.setVisibility(View.VISIBLE);

                    pdfFileName = netSingleLession.getResult().getLesson().getMedia_p_d_f().getUrl();
                    binding.textName.setText(netSingleLession.getResult().getLesson().getMedia_p_d_f().getName());
                    binding.txtdate.setText(netSingleLession.getResult().getLesson().getMedia_p_d_f().getCreated_at());

                    displayFromUri(Uri.parse(netSingleLession.getResult().getLesson().getMedia_p_d_f().getUrl()));
                }
                if (netSingleLession.getResult().getLesson().getMedia_audio() != null) {

                    binding.llAudio.setVisibility(View.VISIBLE);
                    audioFile = netSingleLession.getResult().getLesson().getMedia_audio().getUrl();
                    binding.txtAudioName.setText(netSingleLession.getResult().getLesson().getMedia_audio().getName());
                    binding.txtAudioDate.setText(netSingleLession.getResult().getLesson().getMedia_audio().getCreated_at());
                } else {

                    dialog.hide();

//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();

            model.setApiCallActive(false);

        }
    };

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);
//        binding.pdfView.setBackgroundColor(Color.LTGRAY);
//        binding.pdfView.fromUri(uri)
//                .defaultPage(pageNumber)
//                .onPageChange(LessionListActivity.this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError(this)
//                .load();
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.course));
            binding.included.toolbar.setVisibility(View.VISIBLE);
//            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }

    public void closeActivity() {

        AppConstant.hideKeyboard(this, binding.recyclerView);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
            }
        }
        saveProgress();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG)
                .show();


    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
            }
        }
        closeActivity();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
            }
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
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        youTubePlayer = player;

        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/


    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }


    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onVideoStarted() {
        }
    };

    public void saveProgress() {
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    saveProgress(
                            "lesson",
                            lessionId,
                            freecallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback freecallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

}
