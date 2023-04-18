package com.example.proiect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlaybackActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE=1000;

    VideoView reelsVideoView;

    TextView textViewShowUsAPhotoOfYou;

    ImageView photoOfYou;

    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        textViewShowUsAPhotoOfYou=findViewById(R.id.textViewShowUsAPhotoOfYou);
        photoOfYou=findViewById(R.id.showUsAnImageOfYouImageView);
        VideoView reelsVideoView=findViewById(R.id.videoView);
        backButton=findViewById(R.id.videoPlaybackBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoPlaybackActivity.this,LoginActivity.class));
            }
        });

        String videoPath="android.resource://"+getPackageName()+"/"+R.raw.reels_video;
        Uri uri=Uri.parse(videoPath);
        reelsVideoView.setVideoURI(uri);

        MediaController mediaController=new MediaController(this);
        reelsVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(reelsVideoView);

        textViewShowUsAPhotoOfYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent(Intent.ACTION_PICK);
                galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,GALLERY_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==GALLERY_REQ_CODE){
                photoOfYou.setImageURI(data.getData());
            }
        }
    }
}
