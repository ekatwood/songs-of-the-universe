package com.eric.songsoftheuniverse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PlaybackWindow extends AppCompatActivity {

    ScrollView layout;
    ImageButton play_pause;
    ImageView bigImage;
    Intent intent;
    TextView title;
    static MediaPlayer m = new MediaPlayer();

    //1 = fifteen mintues, 2 = 30 minutes, 3 = 60 minute selection on button.
    public int timeStatus = 1;
    int pos = 0;
    Boolean wasPlaying = true;
    long firstSchedule,secondSchedule,thirdSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_window);

        play_pause = (ImageButton)findViewById(R.id.playPause);
        Timer timer = new Timer();

        //if the orientation has been changed
        if(savedInstanceState != null){

            timeStatus = savedInstanceState.getInt(TIME);
            pos = savedInstanceState.getInt(POS);

            if(wasPlaying = savedInstanceState.getBoolean(PLAYING)){
                play_pause.setImageResource(R.drawable.pause_35);}
            else {
                play_pause.setImageResource(R.drawable.play_35);
            }

            firstSchedule = savedInstanceState.getLong(FIRST_SCHEDULE);
            secondSchedule = savedInstanceState.getLong(SECOND_SCHEDULE);
            thirdSchedule = savedInstanceState.getLong(THIRD_SCHEDULE);

            timer.schedule(new fifteenButton(), new Date(firstSchedule));
            timer.schedule(new thirtyButton(), new Date(secondSchedule));
            timer.schedule(new sixtyButton(), new Date(thirdSchedule));
        }
        else{
            play_pause.setImageResource(R.drawable.pause_35);

            firstSchedule = System.currentTimeMillis() + 900000;
            secondSchedule = System.currentTimeMillis() + 1800000;
            thirdSchedule = System.currentTimeMillis() + 3600000;

            timer.schedule(new fifteenButton(), new Date(firstSchedule));
            timer.schedule(new thirtyButton(), new Date(secondSchedule));
            timer.schedule(new sixtyButton(), new Date(thirdSchedule));
        }

        //set Background
        int orientation = this.getResources().getConfiguration().orientation;
        layout = (ScrollView) findViewById(R.id.scroll_layout_playback);
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            layout.setBackgroundResource(R.drawable.background);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            layout.setBackgroundResource(R.drawable.background_320_landscape);
        }

        bigImage = (ImageView)findViewById(R.id.bigImage);
        title = (TextView)findViewById(R.id.playbackTitle);
        intent = getIntent();

        switch(intent.getStringExtra("key")){
            case "Sun":
                bigImage.setImageResource(R.drawable.sun_transparent_big);
                title.setText("Sun");
                playSound("Sun");
                break;
            case "Earth":
                bigImage.setImageResource(R.drawable.earth_transparent_big);
                title.setText("Earth");
                playSound("Earth");
                break;
            case "Jupiter":
                bigImage.setImageResource(R.drawable.jupiter_transparent_big);
                title.setText("Jupiter");
                playSound("Jupiter");
                break;
            case "Neptune":
                bigImage.setImageResource(R.drawable.neptune_transparent_big);
                title.setText("Neptune");
                playSound("Neptune");
                break;
            case "Saturn":
                bigImage.setImageResource(R.drawable.saturn_transparent_big);
                title.setText("Saturn");
                playSound("Saturn");
                break;
            case "Uranus":
                bigImage.setImageResource(R.drawable.uranus_transparent_big);
                title.setText("Uranus");
                playSound("Uranus");
                break;
        }

        if(wasPlaying)
            m.start();
    }

    private void playSound(String id){

        m.reset();

        //check permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            //Open the sound file in the expansion
            try {
                ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(this, 22, 0);

                AssetFileDescriptor afd;

                switch (id) {
                    case "Sun":
                        afd = expansionFile.getAssetFileDescriptor("sun_sound.mp3");
                        break;
                    case "Earth":
                        afd = expansionFile.getAssetFileDescriptor("earth_sound.mp3");
                        break;
                    case "Jupiter":
                        afd = expansionFile.getAssetFileDescriptor("jupiter_sound.mp3");
                        break;
                    case "Neptune":
                        afd = expansionFile.getAssetFileDescriptor("neptune_sound.mp3");
                        break;
                    case "Saturn":
                        afd = expansionFile.getAssetFileDescriptor("saturn_sound.mp3");
                        break;
                    case "Uranus":
                        afd = expansionFile.getAssetFileDescriptor("uranus_sound.mp3");
                        break;
                    default:
                        afd = expansionFile.getAssetFileDescriptor("");
                        break;
                }

                try {
                    m.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    m.prepare();
                } catch (java.io.IOException e) {  }

                afd.close();

            } catch (IOException e) {  }

        }
        else { /* Permission denied, fall through play no sound */  }

        m.seekTo(pos);
        m.setLooping(true);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        m.stop();
    }

    @Override
    public void onUserLeaveHint(){
        super.onUserLeaveHint();

        m.stop();
    }

    class fifteenButton extends TimerTask {
        @Override
        public void run(){
            if(timeStatus == 1){
                finish();
            }
        }
    };
    class thirtyButton extends TimerTask{
        @Override
        public void run(){
            if(timeStatus == 2 || timeStatus == 1){
                finish();
            }
        }
    };
    class sixtyButton extends TimerTask{
        @Override
        public void run(){
            if(timeStatus == 3 || timeStatus == 1 || timeStatus == 2){
                finish();
            }
        }
    };

    public void fifteenClicked(View view){ timeStatus = 1; }
    public void thirtyClicked(View view){ timeStatus = 2; }
    public void sixtyClicked(View view){ timeStatus = 3; }

    public void playPause(View view){

        if(m.isPlaying()){
            play_pause.setImageResource(R.drawable.play_35);
            wasPlaying = false;
            m.pause();
        }
        else{
            play_pause.setImageResource(R.drawable.pause_35);
            wasPlaying = true;
            m.start();
        }
    }

    static final String POS = "position";
    static final String TIME = "time";
    static final String PLAYING = "playing";
    static final String FIRST_SCHEDULE = "first";
    static final String SECOND_SCHEDULE = "second";
    static final String THIRD_SCHEDULE = "third";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(POS, m.getCurrentPosition());
        savedInstanceState.putInt(TIME, timeStatus);
        savedInstanceState.putBoolean(PLAYING, wasPlaying);
        savedInstanceState.putLong(FIRST_SCHEDULE, firstSchedule);
        savedInstanceState.putLong(SECOND_SCHEDULE, secondSchedule);
        savedInstanceState.putLong(THIRD_SCHEDULE, thirdSchedule);

        super.onSaveInstanceState(savedInstanceState);
    }
}