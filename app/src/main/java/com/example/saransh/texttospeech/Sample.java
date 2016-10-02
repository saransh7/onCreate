package com.example.saransh.texttospeech;

/**
 * Created by dell pc on 02-10-2016.
 */

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Sample extends ActionBarActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private String line = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        tts = new TextToSpeech(getApplicationContext(), this);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            private String[] arr;

            @Override
            public void onClick(View v) {
                File sdcard = Environment.getExternalStorageDirectory();
                File dir = new File(sdcard.getAbsolutePath() + "/Download");
                dir.mkdirs();
                // Get the text file

                File file = new File(dir,"COM_200_Assignment_7.txt");

                // ob.pathh
                // Read text from file

                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    // int i=0;
                    List<String> lines = new ArrayList<String>();

                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                        // arr[i]=line;
                        // i++;
                        text.append(line);
                        text.append('\n');
                    }
                    for (String string : lines) {
                        tts.speak(string, TextToSpeech.SUCCESS, null);
                    }
                    arr = lines.toArray(new String[lines.size()]);
                    //System.out.println(arr.length);
                    speakOut(text.toString());
//                    text1.setText(text);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    private void speakOut(String text) {
        //String page = text1.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}