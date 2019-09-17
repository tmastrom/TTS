package com.example.ttsexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.speech.tts.TextToSpeech;

import com.example.ttsexample.MainActivity;

/*
TODO: Narrate dropdown list options
*
*/

public class SpinnerActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerLanguage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_activity);
        spinnerLanguage = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using a string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // How to pass selected item to TTS to be narrated?

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void MainMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
