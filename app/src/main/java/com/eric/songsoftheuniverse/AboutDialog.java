package com.eric.songsoftheuniverse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by eric on 1/7/2017.
 */
public class AboutDialog extends DialogFragment {

    String about = "To use: choose the sun or a planet, and audio will playback for 15, 30," +
            " or 60 minutes.\n\nAll audio courtesy of NASA's Voyager missions. Recordings" +
            " were taken of the planets as the Voyager zoomed along through our solar" +
            " system. It recorded electromagnetic vibrations within and around each planet, " +
            "and translated it to be within the range of human hearing.";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(about)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
