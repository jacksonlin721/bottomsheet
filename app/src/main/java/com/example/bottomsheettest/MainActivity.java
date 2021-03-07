package com.example.bottomsheettest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    VideoView video1;
    VideoView video2;
    String videoPath1;
    String videoPath2;
    private BottomSheetBehavior bottomSheetBehavior;
    private VideoView bottomSheetVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPath1 = "android.resource://" + getPackageName() + "/" + R.raw.mp4test;
        videoPath2 = "android.resource://" + getPackageName() + "/" + R.raw.mkvtest;

        video1 = findViewById(R.id.iv_video_1);
        video1.setVideoURI(Uri.parse(videoPath1));
        video1.seekTo(5);
        video1.setOnClickListener(this);

        video2 = findViewById(R.id.iv_video_2);
        video2.setVideoURI(Uri.parse(videoPath2));
        video2.seekTo(5);
        video2.setOnClickListener(this);

        configBottomSheet();
    }

    private void configBottomSheet() {
        bottomSheetVideoView = findViewById(R.id.vv_video);

        View view = findViewById(R.id.video_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.2f);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e(TAG,"[onStateChanged] new state= "+newState);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED ||
                        newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetVideoView.stopPlayback();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e(TAG,"[onSlide] offset= "+slideOffset);
                ConstraintLayout.LayoutParams layoutParams =
                        new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (slideOffset > 0.2f) {
                    layoutParams.width *= slideOffset;
                }
                bottomSheetVideoView.setLayoutParams(layoutParams);
            }
        });

        bottomSheetVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    public void onClick(View view) {
        playWithNewPage(
                view.getId() == video1.getId() ?
                        videoPath1 : videoPath2);
/*        Intent intent = new Intent(this, PlayerView.class);
        if (view.getId() == video1.getId()) {
            intent.putExtra("videoPath", videoPath1);
        } else {
            intent.putExtra("videoPath", videoPath2);
        }
        startActivity(intent);
*/
    }

    private void playWithNewPage(String videoPath) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetVideoView.setVideoURI(Uri.parse(videoPath));
        bottomSheetVideoView.start();
    }
}