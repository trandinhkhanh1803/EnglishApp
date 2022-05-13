package com.example.aoneenglish.Listening;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Media_Player {
    public static final String URL_MEDIA_SAMPLE  = "https://github.com/quanghuyK20/Listening_MP3/blob/master/Listening/ve-nghe-me-ru.mp3";

    public static final String LOG_TAG= "MediaPlayerTutorial";

    // String videoURL = "https://raw.githubusercontent.com/o7planning/webexamples/master/_testdatas_/yodel.mp3";
    public static void playURLMedia(Context context, MediaPlayer mediaPlayer, String videoURL)  {
        try {
            Log.i(LOG_TAG, "Media URL: "+ videoURL);

            Uri uri= Uri.parse( videoURL );
            Toast.makeText(context,"Select source: "+ uri,Toast.LENGTH_SHORT).show();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync();

        } catch(Exception e) {
            Log.e(LOG_TAG, "Error Play URL Media: "+ e.getMessage());
            Toast.makeText(context,"Error Play URL Media: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
