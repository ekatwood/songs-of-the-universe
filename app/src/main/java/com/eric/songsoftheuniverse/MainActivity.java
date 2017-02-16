package com.eric.songsoftheuniverse;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity{

    ScrollView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //set Background
        int orientation = this.getResources().getConfiguration().orientation;
        layout = (ScrollView) findViewById(R.id.scroll_layout);
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            layout.setBackgroundResource(R.drawable.background);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            layout.setBackgroundResource(R.drawable.background_320_landscape);
        }

        verifyStoragePermissions(this);

    }

    //an int to be used as a code, and a String[] to hold the permissions required
    protected static final int REQUEST_EXTERNAL_STORAGE = 123;
    protected static String[] PERMISSIONS_STORAGE = { Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {

        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void playbackWindow(View view){
        Intent intent = new Intent(this, PlaybackWindow.class);

        switch(view.getId()){
            case R.id.sunButton:
                intent.putExtra("key", "Sun");
                break;
            case R.id.earthButton:
                intent.putExtra("key", "Earth");
                break;
            case R.id.jupiterButton:
                intent.putExtra("key", "Jupiter");
                break;
            case R.id.neptuneButton:
                intent.putExtra("key", "Neptune");
                break;
            case R.id.saturnButton:
                intent.putExtra("key", "Saturn");
                break;
            case R.id.uranusButton:
                intent.putExtra("key", "Uranus");
                break;
        }
        startActivity(intent);
    }

    //display information in dialog popup window
    public void displayAbout(View view){

        DialogFragment newFragment = new AboutDialog();
        newFragment.show(getSupportFragmentManager(), "about");

    }
}
