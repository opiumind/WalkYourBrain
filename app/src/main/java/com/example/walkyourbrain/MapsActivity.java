package com.example.walkyourbrain;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity
        implements
        OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        LocationSource.OnLocationChangedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;


    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(this.locationRequest);
        this.locationSettingsRequest = builder.build();

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult); // why? this. is. retarded. Android.
                Location currentLocation = locationResult.getLastLocation();

                Toast.makeText(MapsActivity.super.getApplicationContext(), "Current location:\n" + currentLocation.toString(), Toast.LENGTH_LONG).show();

                //current latitude:
                double curLat = currentLocation.getLatitude();
                double curLon = currentLocation.getLongitude();

                //starting position:
                double lat1 = 38.907665;
                double lon1 = -77.071507;
//                double clat1 =
//                double clon1 =
//
                //puzzle 1
                double lat2 = 38.907728;
                double lon2 = -77.067931;
//                double clat2 =
//                double clon2 =
//
                //puzzle 2
                double lat3 = 38.906034;
                double lon3 = -77.070431;
//                double clat3 =
//                double clon3 =
//
                //puzzle 3
                double lat4 = 38.905263;
                double lon4 = -77.066218;
//                double clat4 =
//                double clon4 =
//
                if (isInsideRegion(lat2, lon2, curLat, curLon, 3)) {
                    Toast.makeText(MapsActivity.super.getApplicationContext(), "YOU ARE CORRECT!!!!!", Toast.LENGTH_SHORT).show();

                }
            }
        };

        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback, Looper.myLooper());
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//    LatLng sydney = new LatLng(-34, 151);

//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//      mMap.setMyLocationEnabled(true);
//    }

//    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    // Check if a user came into a specific region. Radius in meters.
    public boolean isInsideRegion(double centerLatitude, double centerLongitude, double testLatitude, double testLongitude, double radius) {
//        double longDiff = currentLocation.getLongitude() - centerOfRegion.getLongitude();
//        double latDiff = currentLocation.getLatitude() - centerOfRegion.getLatitude();
//
//        if ((Math.pow(longDiff, 2) + Math.pow(latDiff, 2)) < Math.pow(radius, 2)) {
//            return true;
//        }
//        return false;
        float[] results = new float[1];
        Location.distanceBetween(centerLatitude, centerLongitude, testLatitude, testLongitude, results);
        float distanceInMeters = results[0];
        return distanceInMeters < radius;
    }

    public void onLocationChanged(Location location) {
        Toast.makeText(this, "MyLocation was changed", Toast.LENGTH_LONG).show();

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}