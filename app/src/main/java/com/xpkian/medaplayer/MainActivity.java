package com.xpkian.medaplayer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String PLAYBACK_TIME = "play_time";
    final String URL = "https://cld2098fls.audiovideoweb.com/str/_definst_/mp4:8c3fls5502/ComplianceSeries/AAIMdonnaAntiHarassment.mp4/playlist.m3u8";
    VideoView videoView;
    ProgressDialog progressDialog;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Connecting server");
        progressDialog.setMessage("Please Wait... ");
        progressDialog.setCancelable(false);

        videoView = findViewById(R.id.videoView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    private void initializePlayer() {
        progressDialog.show();
        videoView.setVideoURI(Uri.parse(URL));
        videoView.requestFocus();
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {

                progressDialog.dismiss();
                /*if (currentPosition > 0) {
                videoView.seekTo(currentPosition);
                } else {

                    videoView.seekTo(1);
                }

                currentPosition = videoView.getCurrentPosition();*/
                videoView.seekTo(1);
                videoView.start();
            }
        });


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                //TODO Video Completed
                /////////////
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Video Completed");
                builder.setCancelable(false);
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                //////////////
            }
        });


    }
}