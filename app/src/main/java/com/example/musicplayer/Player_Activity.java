package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class Player_Activity extends AppCompatActivity {
    ImageView play, next, prev , fl, fr;
    TextView name, start, stop;
    SeekBar seekBar;
    BarVisualizer barVisualizer;

    String sname;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> my_songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        play= findViewById(R.id.play_btn_id);
        next= findViewById(R.id.btn_next_id);
        prev= findViewById(R.id.btn_prev_id);
        fl= findViewById(R.id.btn_fl_id);
        fr= findViewById(R.id.btn_fr_id);
        name= findViewById(R.id.txt_song_id);
        start= findViewById(R.id.text_start_id);
        stop= findViewById(R.id.text_stop_id);
        seekBar= findViewById(R.id.seekbar_id);
        barVisualizer= findViewById(R.id.blast);

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i= getIntent();
        Bundle bundle = i.getExtras();

        my_songs=(ArrayList) bundle.getParcelableArrayList("songs");
        String song_name= i.getStringExtra("songname");
        position = bundle.getInt("pos", 0);
        name.setSelected(true);
        Uri uri = Uri.parse(my_songs.get(position).toString());
        sname= my_songs.get(position).getName();
        name.setText(sname);

        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.middle);
                    mediaPlayer.pause();
                }else
                {
                    play.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });
    }
}