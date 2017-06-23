package com.example.karthik.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";
    public static final String PREFS_NAME = "MySettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


    public void saveData(View view) {


        // To store contents to the shared preferences

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        editor.putString(getString(R.string.key_data), message);
        editor.commit();

        // To store contents to the internal storage

        final String filename = "settings";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(message.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        View parentLayout = findViewById(R.id.root_view);
        final Context context = this;

        Snackbar.make(parentLayout, "Saving Completed", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Clear shared preferences
                        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.commit();

                        // Delete file
                        context.deleteFile("settings");

                        Toast.makeText(context,"Cleared data ...",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }


}
