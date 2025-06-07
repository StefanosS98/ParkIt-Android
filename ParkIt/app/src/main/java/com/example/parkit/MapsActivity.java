package com.example.parkit;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences preferences;
    Locale myLocale;
    String preftitle;
    private static final LatLng PARKINGLOCATION1 = new LatLng(37.898846, 23.753844);
    private static final LatLng PARKINGLOCATION2 = new LatLng(37.903079, 23.754316);
    private static final LatLng PARKINGLOCATION3 = new LatLng(37.900692, 23.752798);
    private static final LatLng PARKINGLOCATION4 = new LatLng(37.900484, 23.754643);
    private static final LatLng PARKINGLOCATION5 = new LatLng(37.912200, 23.714038);
    private static final LatLng PARKINGLOCATION6 = new LatLng(37.916821, 23.710143);
    private static final LatLng PARKINGLOCATION7 = new LatLng(37.942120, 23.653133);
    private static final LatLng PARKINGLOCATION8 = new LatLng(37.942019, 23.652076);
    private static final LatLng PARKINGLOCATION9 = new LatLng(37.940530, 23.650209);
    private static final LatLng PARKINGLOCATION10 = new LatLng(37.943762, 23.651244);

    private Marker mParkinglocation1;
    private Marker mParkinglocation2;
    private Marker mParkinglocation3;
    private Marker mParkinglocation4;
    private Marker mParkinglocation5;
    private Marker mParkinglocation6;
    private Marker mParkinglocation7;
    private Marker mParkinglocation8;
    private Marker mParkinglocation9;
    private Marker mParkinglocation10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Η κάμερα του Χάρτη μόλις ανοίγει η εφαρμογή
        LatLng defaultcamera = new LatLng(37.983810, 23.727539);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultcamera));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultcamera, 10));

        //Τα σημεία παρκινγκ
        preftitle = preferences.getString("markertitlepref","");
        mParkinglocation1 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION1)
                .title(getString(R.string.parkl1)));

        mParkinglocation2 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION2)
                .title(getString(R.string.parkl2)));

        mParkinglocation3 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION3)
                .title(getString(R.string.parkl3)));

        mParkinglocation4 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION4)
                .title(getString(R.string.parkl4)));

        mParkinglocation5 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION5)
                .title(getString(R.string.parkl5)));

        mParkinglocation6 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION6)
                .title(getString(R.string.parkl6)));

        mParkinglocation7 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION7)
                .title(getString(R.string.parkl7)));

        mParkinglocation8 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION8)
                .title(getString(R.string.parkl8)));

        mParkinglocation9 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION9)
                .title(getString(R.string.parkl9)));

        mParkinglocation10 = mMap.addMarker(new MarkerOptions()
                .position(PARKINGLOCATION10)
                .title(getString(R.string.parkl10)));

        //Έλεγχος εάν έχει παρκάρει χρήστης σε κάποιο σημείο
        if(preftitle.equals(mParkinglocation1.getTitle()) || preftitle.equals(mParkinglocation2.getTitle()) || preftitle.equals(mParkinglocation3.getTitle()) || preftitle.equals(mParkinglocation4.getTitle()) || preftitle.equals(mParkinglocation5.getTitle()) || preftitle.equals(mParkinglocation6.getTitle()) || preftitle.equals(mParkinglocation7.getTitle()) || preftitle.equals(mParkinglocation8.getTitle()) || preftitle.equals(mParkinglocation9.getTitle()) || preftitle.equals(mParkinglocation10.getTitle())){
            if(preftitle.equals(mParkinglocation1.getTitle())){
                mParkinglocation1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation2.getTitle())){
                mParkinglocation2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation3.getTitle())){
                mParkinglocation3.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation4.getTitle())){
                mParkinglocation4.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation5.getTitle())){
                mParkinglocation5.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation6.getTitle())){
                mParkinglocation6.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation7.getTitle())){
                mParkinglocation7.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation8.getTitle())){
                mParkinglocation8.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation9.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation9.getTitle())){
                mParkinglocation9.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation10.setVisible(false);
            }
            else if(preftitle.equals(mParkinglocation10.getTitle())){
                mParkinglocation10.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mParkinglocation1.setVisible(false);
                mParkinglocation2.setVisible(false);
                mParkinglocation3.setVisible(false);
                mParkinglocation4.setVisible(false);
                mParkinglocation5.setVisible(false);
                mParkinglocation6.setVisible(false);
                mParkinglocation7.setVisible(false);
                mParkinglocation8.setVisible(false);
                mParkinglocation9.setVisible(false);
            }

        }
        else{
            //Μπλε σημάδια και να είναι Visible
            mParkinglocation1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation3.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation4.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation5.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation6.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation7.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation8.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation9.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation10.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mParkinglocation1.setVisible(true);
            mParkinglocation2.setVisible(true);
            mParkinglocation3.setVisible(true);
            mParkinglocation4.setVisible(true);
            mParkinglocation5.setVisible(true);
            mParkinglocation6.setVisible(true);
            mParkinglocation7.setVisible(true);
            mParkinglocation8.setVisible(true);
            mParkinglocation9.setVisible(true);
            mParkinglocation10.setVisible(true);
        }



        //Κουμπί για την τοποθεσία του χρήστη
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),ParkingLocations.class);
                intent.putExtra("markerposition",marker.getPosition());
                intent.putExtra("markertitle",marker.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, getString(R.string.toast_locationbtn), Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
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
}
