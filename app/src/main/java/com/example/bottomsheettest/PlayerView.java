package com.example.bottomsheettest;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;

public class PlayerView extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;
    private String TAG = "PlayerView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        String videoPath = getIntent().getStringExtra("videoPath");

        VideoView videoView = findViewById(R.id.vv_video);
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        View view = findViewById(R.id.video_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e(TAG,"[onStateChanged] new state= "+newState);
                if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    bottomSheetBehavior.setPeekHeight(200);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e(TAG,"[onSlide] offset= "+slideOffset);
            }
        });
    }

}
