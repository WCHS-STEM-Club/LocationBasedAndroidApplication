package com.example.kofu.locationbasedandroidapplication;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assert locationManager != null;

                if (ActivityCompat.checkSelfPermission(getBaseContext(), permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getBaseContext(), permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }


                Snackbar.make(view, "You are located: right here", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        LocationListener locationListener = new LocationListener() {

            @Override public void onLocationChanged(Location l) {
                Log.d("LOCATION ALERT", "LOCATION CHANGED: " + l.getLatitude() + ", " + l.getLongitude());
                ((TextView) findViewById(R.id.text)).setText("uR L0c4t1on: \nLatitude: " + l.getLatitude() + "\nLongitude: " + l.getLongitude());
            }
            @Override public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override public void onProviderEnabled(String s) {}
            @Override public void onProviderDisabled(String s) {}
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

  // nate keep in mind the location listener won't be called until you move >5 meters
  // with wifi it may be less accurate
  private class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
      // i feel like this would be a better use for toast
      Log.d("LOCATION ALERT", "LOCATION CHANGED: " + loc.getLatitude() + ", " + loc.getLatitude());
      Snackbar.make(findViewById(R.id.fab), "Location changed: Lat: " + loc.getLatitude() + " Lng: "
              + loc.getLongitude(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    // ignore
    @Override public void onProviderDisabled(String provider) {}
    @Override public void onProviderEnabled(String provider) {}
    @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
  }
}
