package com.example.pristineseed.ui.dashboard.newTheam;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class GogogleMapShowingFragment  extends FragmentActivity implements OnMapReadyCallback, com.google.android.gms.location.LocationListener{

private GoogleMap mMap;
private SessionManagement sessionManagement;
private String lat_long_string="";
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0;
    Location mLastLocation;
    private static final String TAG = "Location_change";
    private Context context;
    private Location currentLocation;
    private ImageButton back_btn;

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    private LocationManager mLocationManager = null;
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getApplicationContext();
        setContentView(R.layout.map_activity_fragment);
        back_btn=findViewById(R.id.back_btn);
        sessionManagement=new SessionManagement(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        back_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        callLocation();
        if(sessionManagement.getCurrentLocation()!=null || sessionManagement.getAddress()!=null) {
            String location_lag_long = sessionManagement.getCurrentLocation();
            String address = sessionManagement.getAddress();
            String[] parts = location_lag_long.split(",");
            String lat = parts[0];
            String log = parts[1];

            double latitude = Double.parseDouble(lat);
            double longnitude = Double.parseDouble(log);

            LatLng TutorialsPoint = new LatLng(latitude, longnitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(TutorialsPoint)      // Sets the center of the map to location user
                    .zoom(20)                   // Sets the zoom
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();
            mMap.addMarker(new MarkerOptions().position(TutorialsPoint).title(address));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {


    }


    private void callLocation() {
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);

            Log.e("network", "network");
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        //for gps provider
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
            Log.e("gps_provider", "gps");
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }


    private class LocationListener implements android.location.LocationListener {

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            Log.e("location", mLastLocation.getLatitude() + "");
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            currentLocation=location;
            mLastLocation.set(location);
            String location_str = location.getLatitude() + "," + location.getLongitude();
            Log.e("location->", location_str + "");
            try {
                sessionManagement.setCurrentLocation(location_str);
            }catch (Exception e){
                e.printStackTrace();
            }

            Geocoder gcd = new Geocoder(context);
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),  location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses!=null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                try {
                    sessionManagement.setCurrentCity(city);
                    sessionManagement.setCurrentState(state);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "getAddress:  address" + address);
                Log.d(TAG, "getAddress:  city" + city);
                Log.d(TAG, "getAddress:  state" + state);
                Log.d(TAG, "getAddress:  postalCode" + postalCode);
                Log.d(TAG, "getAddress:  knownName" + knownName);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled:" + provider);
            if (provider.equals("gps")) {
                Log.e("Disabled: ", provider);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
            if (provider.equals("gps")) {
                Log.e("Disabled: ", provider);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    private void initializeLocationManager() {
        Log.e("initialzeLocation", "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

}
