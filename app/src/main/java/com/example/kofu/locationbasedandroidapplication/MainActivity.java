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
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(
            Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        assert locationManager != null;
        if (ActivityCompat.checkSelfPermission(getBaseContext(), permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getBaseContext(), permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
          requestPermissions(new String[]{
              permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION},
              1);
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
          return;
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

//                Snackbar.make(view, "You are located: right here", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
  private class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
      Snackbar.make(findViewById(R.id.fab), "Location changed: Lat: " + loc.getLatitude() + " Lng: "
              + loc.getLongitude(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
  }
}
