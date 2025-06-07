package com.example.parkit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    //Μετρητές για τα results από το SpeechRecognition
    int r=0;
    int k=0;
    int m=0;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    Locale myLocale;
    SharedPreferences prefs,spreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        spreferences = getSharedPreferences("com.example.ParkIt", MODE_PRIVATE);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("language_preference")) {
                    String language = prefs.getString("language_preference","");
                    if(language.equals("Greek")){
                        changeLang("el");
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else if (language.equals("English")){
                        changeLang("en");
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }
                else if(key.equals("signature")){
                    String signature = prefs.getString("signature","");
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_welcome) + "\n" + signature,Toast.LENGTH_SHORT).show();
                }
                else if(key.equals("info")){
                    Boolean instructions = prefs.getBoolean("info",false);
                    if(instructions.equals(true)){
                        Toast.makeText(getApplicationContext(),getString(R.string.toast_instructions),Toast.LENGTH_SHORT).show();
                        showMessage();
                    }
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    //Κουμπί Search for parking
    public void Searchbtn(View view){
        if (spreferences.getBoolean("firstrun", true)) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            Toast.makeText(this,getString(R.string.permission_settings),Toast.LENGTH_SHORT).show();
            spreferences.edit().putBoolean("firstrun", false).commit();
        }
        else {
            startActivity(new Intent(this,MapsActivity.class));
        }
    }

    //Alert Dialog
    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(247,242,242)));
    }

    //Αλλαγή γλώσσας
    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Resources res = getResources();
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        res.updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language2 = prefs.getString(langPref, "");
        changeLang(language2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_records){
            startActivity(new Intent(MainActivity.this,Records.class));
        }
        else if(id == R.id.action_map){
            startActivity(new Intent(MainActivity.this,MapsActivity.class));
        }
        else if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //Κουμπί SpeechRecognition
    public void go(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please give me an order!");
        startActivityForResult(intent,742);
    }

    //SpeechRecognition results
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==742 && resultCode==RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            StringBuffer buffer = new StringBuffer();
            for (String s :
                    results) {
                buffer.append(s.toLowerCase() + "\n");
                if(s.contains("records") || s.contains("recordings") || s.contains("open records") || s.contains("open recordings")){
                    r=r+1;
                    if(r==1){
                        Toast.makeText(this,"Opening records",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(this,Records.class);
                        startActivity(intent6);
                    }
                }
                else if(s.contains("maps") || s.contains("google maps") || s.contains("map") || s.contains("open maps") || s.contains("open google maps") || s.contains("open map") || s.contains("search for parking")){
                    m=m+1;
                    if(m==1){
                        Toast.makeText(this,"Opening google maps",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(this,MapsActivity.class);
                        startActivity(intent4);
                    }
                }
                else if(s.contains("settings") || s.contains("open settings")){
                    k=k+1;
                    if(k==1){
                        Toast.makeText(this,"Opening settings",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(this,SettingsActivity.class);
                        startActivity(intent5);
                    }
                }
            }
            r=0;
            m=0;
            k=0;
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
