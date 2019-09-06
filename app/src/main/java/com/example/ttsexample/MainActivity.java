package com.example.ttsexample;

import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

/*

project based on tts tutorial
https://javapapers.com/android/android-text-to-speech-tutorial/#targetText=It%20has%20a%20LinearLayout%20with,convert%20the%20text%20to%20speech.

TODO: implement setVoice method for changing speed
TODO: experiment with volume control
TODO: implement checkbox for changing language
radio button in radio group -
 */

public class MainActivity extends AppCompatActivity {

    // declare xml layout objects
    private TextToSpeech textToSpeech;
    private Button button;
    private Button button1;
    private EditText editText;
    private RadioGroup radioLanguageGroup;
    private Button selectButton;
    private RadioButton radioLanguageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        editText = (EditText) findViewById(R.id.et);
        radioLanguageGroup = (RadioGroup) findViewById(R.id.chooseLanguage);
        selectButton = (Button) findViewById(R.id.btn);

        /*
        When button is clicked, speak the string in the editText field
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                Log.i("BTN","button clicked: " + data);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        // when button is clicked, speak the button text field
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = button1.getText().toString();
                Log.i("BTN","button clicked: " + data);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // hitting the select button gets the selected language and changes the language to that
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioLanguageGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioLanguageButton = (RadioButton) findViewById(selectedId);

                // say the name of selected radio button
                String data = radioLanguageButton.getText().toString();

                /*

                English: en_CA
                French Canadian: fr_CA
                Italian: it

                */
                Locale.setDefault(new Locale (data, ""));
                textToSpeech.setLanguage( Locale.getDefault());

                Log.i("SELECT","button clicked: " + Locale.getDefault().toString());
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){

                    // from radio group
                    int selectedId = radioLanguageGroup.getCheckedRadioButtonId();
                    radioLanguageButton = (RadioButton) findViewById(selectedId);
                    String data = radioLanguageButton.getText().toString();
                    Log.i("TTS","button clicked: " + data);

                    int ttsLang = textToSpeech.setLanguage(new Locale (data, ""));

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED){
                        // log.e is an error message
                        Log.e("TTS","The language is not supported");
                    }
                    else {
                        // log.i is an info message
                        Log.i("TTS","Language supported");
                    }
                    Log.i("TTS", "Initialization success");
                }
                else{
                    Toast.makeText(getApplicationContext(), "TTS init failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
