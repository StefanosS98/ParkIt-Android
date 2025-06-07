package com.example.parkit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingLocations extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {
    private MapView mapView;
    private GoogleMap gmap;
    SharedPreferences preferences;
    SQLiteDatabase db;
    String markertitle;
    String parkingslot;
    float stepsvalue;
    float proximityvalue;
    String date;
    private SensorManager sensorManager;
    private Sensor steps;
    private Sensor proximity;
    private TextView textview3,textview4;
    private Button parkherebtn,leaveparkingbtn;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_locations);
        ActionBar actionBar = getSupportActionBar();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        db = openOrCreateDatabase("SensorsRecords",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Records( steps FLOAT, proximity FLOAT, date TEXT );");
        parkingslot = preferences.getString("markertitlepref","");
        textview3 = (TextView) findViewById(R.id.textView3);
        textview4 = (TextView) findViewById(R.id.textView4);
        parkherebtn = (Button) findViewById(R.id.button4);
        leaveparkingbtn = (Button) findViewById(R.id.button5);
        markertitle = getIntent().getExtras().getString("markertitle");
        if(parkingslot.equals(markertitle)){
            parkherebtn.setEnabled(false);
            leaveparkingbtn.setEnabled(true);
            SecondMessage();
        }
        else {
            parkherebtn.setEnabled(true);
            leaveparkingbtn.setEnabled(false);
        }
        parkherebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveparkingbtn.setEnabled(true);
                parkherebtn.setEnabled(false);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("markertitlepref",markertitle);
                editor.commit();
                Message();
                SelectData();
            }
        });

        leaveparkingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parkingslot2 = preferences.getString("markertitlepref","");
                Toast.makeText(getApplicationContext(),getString(R.string.toast_leaveparking) + "\n" + parkingslot2,Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("markertitlepref","");
                editor.commit();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        setTitle(R.string.parking_locations_title);
        actionBar.setSubtitle(markertitle);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    //Alert Dialog 1
    public void Message(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.setTitle("ParkIt");
        builder.setMessage(R.string.dialog_message2);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(247,242,242)));
    }

    //Alert Dialog
    public void SecondMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.setTitle("ParkIt");
        builder.setMessage(R.string.dialog_message3);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(247,242,242)));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        sensorManager.registerListener(this, steps, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        sensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng markerposition = getIntent().getExtras().getParcelable("markerposition");
        markertitle = getIntent().getExtras().getString("markertitle");
        gmap = googleMap;
        gmap.addMarker(new MarkerOptions()
                .position(markerposition)
                .title(markertitle)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(markerposition));
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerposition, 15));
    }

    //Αισθητήρες
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            stepsvalue = event.values[0];
            textview3.setText(getString(R.string.textview3) + "\n" + String.valueOf(stepsvalue));
        }
        else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            proximityvalue = event.values[0];
            textview4.setText(getString(R.string.textview4) + "\n" + String.valueOf(proximityvalue) + "cm");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        date = sdf.format(new Date(System.currentTimeMillis() + (event.timestamp - SystemClock.elapsedRealtimeNanos()) / 1000000));
        db.execSQL("INSERT INTO Records values" + "('"+ stepsvalue +"','"+ proximityvalue +"','"+ date +"');");
    }

    //Προσθήκη των δεδομένων στο Records
    public void SelectData(){
        Cursor cursor = db.rawQuery("SELECT * FROM Records",null);
        if (cursor.getCount()==0)
            Toast.makeText(this,"No values in sensors",Toast.LENGTH_LONG).show();
        else {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                buffer.append(cursor.getDouble(0)+ getString(R.string.steps));
                buffer.append(",");
                buffer.append(cursor.getDouble(1)+ "cm");
                buffer.append(",");
                buffer.append(cursor.getString(2));
                buffer.append("\n");
            }
            String data = buffer.toString();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("data1",data);
            editor.commit();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
