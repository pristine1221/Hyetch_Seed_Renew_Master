package com.example.pristineseed.ui.dashboard.newTheam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pristineseed.DataBaseRepository.EmployeeAttendance.EmployeeAttendanceDao;
import com.example.pristineseed.DataBaseRepository.EmployeeAttendance.EmployeeAttendanceTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.model.EmployeeModel.EmployeeAttendModel;
import com.example.pristineseed.model.EmployeeModel.LiveViewModel;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.example.pristineseed.ui.employee.HRPortalEmployee.EmployeeAttendanceFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBg extends Service implements com.google.android.gms.location.LocationListener {
    public static final int NOTIFICATION_ID_1 = 2;
    private static final String TAG = "Update_Service";
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0;
    Context context;
    Location mLastLocation;
    SessionManagement sessionManagement;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    private LocationManager mLocationManager = null;
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private LatLng latLng;
    private String final_longitude="", final_latitude="", locality="", from_area="", postal_code="", country="";
    //for fused
    public static final String ACTION_LOCATION_BROADCAST = ServiceBg.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    private int SchedulerTimerCount = 0;
    private int i=0;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onstart", "onStartCommand");
        callLocation();//new................................................................................
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e("service_start", "create");
        sessionManagement = new SessionManagement(getApplicationContext());
        sendBroadcast();
        context = getApplicationContext();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
           // showForegroundNotification();//uncomment....................................................
        } else {
            startForeground(1, new Notification());
        }
        callLocation();//add new...................................
        //////////////////////comment
        /*initializeLocationManager();
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
        try {
          mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }*///////////////////////////comment
    }


    @Override
    public void onDestroy() {
        //callBroadcastforrestartService();//uncomment
        if (mLocationManager != null) {
            for (int i=0;i< mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
        callBroadcastforrestartService();
        super.onDestroy();
    }


    private void callBroadcastforrestartService() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }


    private void callLocation() {
        initializeLocationManager();
        // get GPS status
        boolean checkGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // get network provider status
        boolean checkNetwork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(checkNetwork) {
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
        }

        //for gps provider
        if(checkGPS) {
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
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        sendBroadcast();
        Log.e("ClearFromService>camera", "END");
        callLocation();
        callBroadcastforrestartService();
    }

    private void initializeLocationManager() {
        Log.e("initialzeLocation", "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");
        if (location != null) {
            //Send result to activities
            sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            Log.e("bhv", String.valueOf(location.getLatitude()) + location.getLongitude());
        }
    }


    private class LocationListener implements android.location.LocationListener { //android.location.LocationListener
        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            Log.e("location", mLastLocation.getLatitude() + "");
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            String location_str = location.getLatitude() + "," + location.getLongitude();
            Log.e("location->", location_str + "");
                try {
                    sessionManagement.setCurrentLocation(location_str);

                    Geocoder gcd = new Geocoder(context);
                    ArrayList<EmployeeAttendModel> serverResponseList = new ArrayList<>();
                    List<Address> addresses = null;
                    addresses = gcd.getFromLocation(location.getLatitude(),  location.getLongitude(), 1);

                    if (addresses!=null && addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        for (int i = 0; i < addresses.size(); i++) {
                            final_latitude = String.valueOf(location.getLatitude());
                            final_longitude = String.valueOf(location.getLongitude());
                            String address_1=addresses.get(i).getAddressLine(i)!=null?addresses.get(i).getAddressLine(i):"";
                            String city_1=addresses.get(i).getLocality()!=null?addresses.get(i).getLocality():"";
                            locality = addresses.get(i).getSubLocality()!=null?addresses.get(i).getSubLocality():"";
                            from_area = addresses.get(i).getAdminArea()!=null?addresses.get(i).getAdminArea():"";
                            postal_code = addresses.get(i).getPostalCode()!=null?addresses.get(i).getPostalCode():"";
                            country = addresses.get(i).getCountryName()!=null?addresses.get(i).getCountryName():"";

                            try {
                                sessionManagement.setCurrentCity(city_1);
                                sessionManagement.setAddress(address_1);
                                sessionManagement.setCurrentState(from_area);
                                EmployeeAttendModel googleMapModel = new EmployeeAttendModel();
                                googleMapModel.from_country = country;
                                googleMapModel.city = city_1;
                                googleMapModel.from_area = from_area;
                                googleMapModel.from_latitude = final_latitude;
                                googleMapModel.from_longitude = final_longitude;
                                googleMapModel.from_locality = locality;
                                googleMapModel.from_postal_code = postal_code;
                                //serverResponseList.add(googleMapModel);
                                if (EmployeeAttendanceFragment.employee_Attan_location != null) {
                                    if(googleMapModel!=null &&  googleMapModel.city!=null) {
                                        EmployeeAttendanceFragment.employee_Attan_location.setLiveData(googleMapModel);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.d(TAG, "getAddress:  address" + address);
                            Log.d(TAG, "getAddress:  city" + city);
                            Log.d(TAG, "getAddress:  state" + state);
                            Log.d(TAG, "getAddress:  postalCode" + postalCode);
                            Log.d(TAG, "getAddress:  knownName" + knownName);
                        }

                        //bindGoogleMapResponse(serverResponseList);
                    }

                }catch (Exception e){
                    e.printStackTrace();
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

    private void sendBroadcast() {
        Intent intent = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 5);
        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    60000,
                    pendingIntent);
        }
    }

    private void sendMessageToUI(String lat, String lng) {
        Log.d(TAG, "Sending info...");
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        Log.e("data", lat + "\n" + lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



    //locally  insert the data
    void bindGoogleMapResponse(List<EmployeeAttendModel> serverResponseList) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(this);
        try {
            EmployeeAttendanceDao googleMapDao = pristineDatabase.employeeAttendanceDao();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String date = dateFormat.format(calendar.getTime());
            Log.e("aaaaa", date);

            for (EmployeeAttendModel responseobject : serverResponseList) {
                EmployeeAttendanceTable googleMapTable = new EmployeeAttendanceTable();
                googleMapTable.setEmp_code(sessionManagement.getemp_id());
                googleMapTable.setFrom_area(responseobject.from_area);
                googleMapTable.setFrom_postal_code(responseobject.from_postal_code);
                googleMapTable.setFrom_locality(responseobject.from_locality);
                googleMapTable.setFrom_country(responseobject.from_country);
                googleMapTable.setFrom_longitude(responseobject.from_longitude);
                googleMapTable.setFrom_latitude(responseobject.from_latitude);
                googleMapTable.setOnline_offline(responseobject.online_offline);
                googleMapTable.setCreated_by(sessionManagement.getemp_id());
                googleMapTable.setStart_time(date);
                googleMapTable.setSend_to_server(0);
                googleMapDao.insert(googleMapTable);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            PristineDatabase.destroyInstance();
        }
    }

    private void showForegroundNotification() {
        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher
        Intent showTaskIntent = new Intent(context, EmployeeAttendanceFragment.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-02";
        String channelName = "Channel Name_1";
        int importance = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("tracking")
                    .setContentText(" running")
                    .setSmallIcon(R.mipmap.hytech_base_logo)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setChannelId(channelId)
                    .build();
            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID_1, notification);
            }
            startForeground(NOTIFICATION_ID_1, notification);

        } else {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("tracking")
                    .setContentText("running")
                    .setSmallIcon(R.mipmap.hytech_base_logo)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .build();
            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID_1, notification);
            }
            startForeground(NOTIFICATION_ID_1, notification);
        }
    }

}
