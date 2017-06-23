package com.example.karthik.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.interface_message);
        textView.setText(message);

    }

    public void loadData(View view) {

        SharedPreferences sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String sharedkey = sharedPref.getString(getString(R.string.key_data),"Unable to read contents");
        TextView spfile = (TextView) findViewById(R.id.saved_text);
        spfile.setText("Saved text in shared preference: \n\n"
                .concat("Key: ")
                .concat(getString(R.string.key_data))
                .concat("\nValue: ")
                .concat(sharedkey));

        StringBuilder filecontents = new StringBuilder();

        try {

            FileInputStream fis = this.openFileInput("settings");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                filecontents.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            filecontents.append("file not found");
        }

        TextView isfile = (TextView) findViewById(R.id.saved_file);
        isfile.setText("Saved text in internal storage: \n\n"
                .concat(filecontents.toString()));

    }

}
