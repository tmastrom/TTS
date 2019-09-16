package com.example.ttsexample;

import android.provider.MediaStore;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/*

project based on tts tutorial
https://javapapers.com/android/android-text-to-speech-tutorial/#targetText=It%20has%20a%20LinearLayout%20with,convert%20the%20text%20to%20speech.
TODO: add intent from main activity to spinner activity
TODO: dropdown list of languages

TODO: implement setVoice method for changing speed
TODO: experiment with volume control
TODO: add lifecycle features


TODO: implement checkbox for changing language -- DONE

 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // declare xml layout objects
    private TextToSpeech textToSpeech;
    private Button button;
    private EditText editText;
    private RadioGroup radioLanguageGroup;
    private RadioButton radioLanguageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.et);
        radioLanguageGroup = (RadioGroup) findViewById(R.id.chooseLanguage);




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


        radioLanguageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (rb != null) {
                    switch(checkedId) {
                        case R.id.rbEnglish:
                            textToSpeech.setLanguage(Locale.CANADA);
                            textToSpeech.speak("Canadian English",TextToSpeech.QUEUE_FLUSH, null);
                            break;

                        case R.id.rbFrench:
                            textToSpeech.setLanguage(Locale.CANADA_FRENCH);
                            textToSpeech.speak("French",TextToSpeech.QUEUE_FLUSH, null);
                            break;
                        case R.id.rbItalian:
                            textToSpeech.setLanguage(Locale.ITALIAN);
                            textToSpeech.speak("Italian",TextToSpeech.QUEUE_FLUSH, null);
                            break;
                    }
                }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
