package com.example.saransh.texttospeech;

/**
 * Created by dell pc on 02-10-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import static com.example.saransh.texttospeech.R.id.button1;

public class TextSpeech extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final int FILE_SELECT_CODE = 0;
    private static final int SUCCESS = 0;
    private TextToSpeech tts;
    private Button button;
    private Button button1;
    private Button button2;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        tts = new TextToSpeech(this, this);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.editText);
        // button on click event
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent t = new Intent(TextSpeech.this, Sample.class);
                startActivity(t);
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_attachment)), FILE_SELECT_CODE);
                //Intent t = new Intent(TextSpeech.this, Browse.class);
                //startActivity(t);
            }

        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri returnUri = data.getData();
                    Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                    if (returnCursor != null) {
                        returnCursor.moveToFirst();
                        String fPath = returnUri.getPath();
                        returnCursor.moveToFirst();
                        toast(fPath);
                    }}}}
    public void toast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Unknown Language");
                } else {
                    button.setEnabled(true);
                    speakOut();
                }


            }else {
            Log.e("TTS", "Failed!");
        }

    }

    private void speakOut() {
        String text = editText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}

