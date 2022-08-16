package com.example.pristineseed.ui.bootmMainScreen;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.pristineseed.DataSyncingBackgraundProcess.BackgruandSyncing_process;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.LoginActivity;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.firebase_notification_service.MyNotification;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.signalr.NotificationSignalRService;
import com.example.pristineseed.model.exceptions.ExceptionModel;
import com.example.pristineseed.model.login.LoginModel;
import com.example.pristineseed.model.menu.MenuData;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.sql_lite_process.dao.ExceptionDao;
import com.example.pristineseed.sql_lite_process.model.ExceptionTableModel;
import com.example.pristineseed.ui.bootmMainScreen.ui.bottomMenu.BottomDialogFragmentNew;
import com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler.MenuMainPageFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.notifications.NotificationsFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.profile.ProfileFragment;
import com.example.pristineseed.ui.dashboard.DashboardFragment;
import com.example.pristineseed.ui.dashboard.newTheam.DashBoard2Fragment;
import com.example.pristineseed.ui.dashboard.newTheam.InspectionsNotificationFragment;
import com.example.pristineseed.ui.dashboard.newTheam.ServiceBg;
import com.example.pristineseed.ui.employee.HRPortalEmployee.EmployeeAttendanceFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pristineseed.RoomDataBase.PristineDatabase.databaseWriteExecutor;
import static com.example.pristineseed.ui.dashboard.newTheam.DashBoard2Fragment.backgroundServiceintent;
public class BottomMainActivity extends AppCompatActivity implements BottomDialogFragmentNew.ItemClickListener {
    private SessionManagement sessionManagement;
    private NotificationSignalRService notificationSignalRService;
    private BottomNavigationView navView;
    public static WorkManager mWorkManager;
    public static OneTimeWorkRequest oneTimeWorkRequest;
    private LinearLayout ly_internet_section;
    private TextView tv_internet_speed_text;
    public static boolean backgraund_syncing_is_running;
    private Handler brodcast_internetConnectivity = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        //createNotification("syncing",getApplicationContext());
        sessionManagement = new SessionManagement(this);


        try {
            sessionManagement.setSelectedSubGroupMenuName("");
            sessionManagement.setSelectedGroupMenuName("");

        } catch (Exception e) {
            e.printStackTrace();
        }
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);
        ly_internet_section=findViewById(R.id.ly_internet_section);
        tv_internet_speed_text=findViewById(R.id.tv_internet_speed_text);
        loadFragments(new DashBoard2Fragment(), "Dashboard");
        bottomDialogFragment = new BottomDialogFragmentNew().newInstance();

        //sendReceivePushNotification();
     //   sentFcmTokenToServer();
        AppExceptionSendToTheServer();
        gpsLocationPermission();

        requestPemission();

        //todo recevice all message from server
        this.registerReceiver(broadcastReceiver, new IntentFilter("checkInterNetBackground"));
        syncAppWithServerAsyTask = new BackgruandSyncing_process(BottomMainActivity.this);

        statusCheck();
        Intent i = new Intent("checkInterNetBackground");
        i.putExtra("message", true);
        getApplicationContext().sendBroadcast(i);

    }


    //todo receiver create for internate access
    static BackgruandSyncing_process syncAppWithServerAsyTask;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle b = intent.getExtras();
                boolean message = b.getBoolean("message", false);
                if (message) {
                    if (syncAppWithServerAsyTask != null && syncAppWithServerAsyTask.getStatus() != AsyncTask.Status.RUNNING) {
                        backgraund_syncing_is_running=true;

                        syncAppWithServerAsyTask = new BackgruandSyncing_process(BottomMainActivity.this);
                        syncAppWithServerAsyTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    ly_internet_section.setBackgroundResource(R.color.colorPrimary);
                    tv_internet_speed_text.setText("You Are Online...");
                    Log.e("network_status","You are online");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           ly_internet_section.setVisibility(View.GONE);
                        }
                    }, 500);

                } else {
                    if (syncAppWithServerAsyTask != null && syncAppWithServerAsyTask.getStatus() == AsyncTask.Status.RUNNING) {
                        backgraund_syncing_is_running=false;
                        syncAppWithServerAsyTask.cancel(true);
                    }
                    ly_internet_section.setBackgroundResource(R.color.my_app_error_color);
                    tv_internet_speed_text.setText("You Are Offline...");
                    ly_internet_section.setVisibility(View.VISIBLE);
                    Log.e("network_status","You are offline");
                }

            } catch (Exception e) {
            }
        }
    };

    SignalRReceiveModel signalRReceiverdata;

    void ConnectTosignalR() throws Exception {
        notificationSignalRService = new NotificationSignalRService(sessionManagement);
        if (!notificationSignalRService.isConnectionState()) {
            notificationSignalRService.Connect();
        }
        NotificationSignalRService.hubConnection.on("notification", (message) -> {
             NotificationCall(message);
            this.signalRReceiverdata = message;
          // SignalRActionHandler("notification", message, "");
        }, SignalRReceiveModel.class);

        NotificationSignalRService.hubConnection.on("logoutAllDevices", (message) -> {
            NotificationCall(message);
            this.signalRReceiverdata = message;
           //SignalRActionHandler("logoutAllDevices", message, "");
        }, SignalRReceiveModel.class);
    }

    void NotificationCall(SignalRReceiveModel receiveMessage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "logout")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(receiveMessage.action)
                .setContentText(receiveMessage.action)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(receiveMessage.message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onItemClick(String child_title, MenuData menuData) {
        MenuMainPageFragment menuMainPageFragment = new MenuMainPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selected_Group", new Gson().toJson(menuData));
        bundle.putString("selected_Group_child", child_title);
        menuMainPageFragment.setArguments(bundle);
        loadFragments(menuMainPageFragment, "MenuMainFragment");
    }

    public class SignalRReceiveModel {
        public String connectionID;
        public String action;
        public String message;
    }

    public void AppExceptionSendToTheServer() {
        databaseWriteExecutor.execute(() -> {
            try {
                PristineDatabase db = PristineDatabase.getAppDatabase(this);
                ExceptionDao exceptionTable = db.exceptionDao();
                List<ExceptionTableModel> exception_list = exceptionTable.getAllException();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                for (int i = 0; i < exception_list.size(); i++) {
                    JsonObject hashMap = new JsonObject();
                    hashMap.addProperty("Exception", exception_list.get(i).getMyException());
                    hashMap.addProperty("ExceptionType", exception_list.get(i).getExceptionType());
                    hashMap.addProperty("lineNo", exception_list.get(i).getLineNo());
                    hashMap.addProperty("fragment", exception_list.get(i).getFragment());
                    hashMap.addProperty("method", exception_list.get(i).getMethod());
                    Call<List<ExceptionModel>> call = mAPIService.Exception(hashMap);
                    Response<List<ExceptionModel>> response = call.execute();
                    Log.e("Result", response.body().get(0).message);
                }
                if (exception_list.size() > 0) {
                    int a = exceptionTable.deleteAllRecord();
                }
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    BottomDialogFragmentNew bottomDialogFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;
        String fragment_tag = "";
        switch (item.getItemId()) {
            case R.id.navigation_profile: {
                selectedFragment = new ProfileFragment();
                fragment_tag = "Profile";
                break;
            }
            case R.id.navigation_dashboard: {
                selectedFragment = new DashBoard2Fragment();
                fragment_tag = "Dashboard";
                break;
            }
            case R.id.navigation_notifications: {
                selectedFragment = new InspectionsNotificationFragment();
                fragment_tag = "Notification";
                break;
            }
            case R.id.navigation_menu: {
                try {
                    if (bottomDialogFragment != null && !bottomDialogFragment.isVisible())
                        bottomDialogFragment.show(getSupportFragmentManager(), BottomDialogFragmentNew.TAG);
                    fragment_tag = "Menu";
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.graph:
                selectedFragment=new DashboardFragment();
                fragment_tag = "Graph";
                break;

        }
        if (selectedFragment != null) {
                loadFragments(selectedFragment, fragment_tag);

        }
        return true;
    };

    private void loadFragments(Fragment selectedFragment, String fragment_tag)  {
        try {
            boolean check_verify = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isVisible()) {
                        if (fragment.getTag() != null && fragment.getTag().equalsIgnoreCase(fragment_tag)) {
                            check_verify = true;
                            break;
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), "calling...........", Toast.LENGTH_SHORT).show();
            }
            if (check_verify) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag).commit();

            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag)
                        .addToBackStack(null).commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
        if (notificationSignalRService != null && notificationSignalRService.isConnectionState()){
            notificationSignalRService.disconectHub();
    }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this.registerReceiver(broadcastReceiver, new IntentFilter("checkInterNetBackground"));
        if (notificationSignalRService != null && !notificationSignalRService.isConnectionState()){
            notificationSignalRService.Connect();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        try {

            if (fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isVisible()) {
                        PerformActionFragment(fragment.getTag() == null ? "" : fragment.getTag());
                    }
                }
            }
            else {

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    static int counter = 0;
    void PerformActionFragment(String Fragment_Tag) {
        try {
            switch (Fragment_Tag) {
                case "Dashboard": {
                    if (counter >= 2) {
                        new MaterialAlertDialogBuilder(this)
                                .setTitle("Exit Confirm...")
                                .setMessage("Do you really want to exit?")
                                .setIcon(R.drawable.ic_baseline_info_24)
                                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                                    counter = 0;
                                    finish();
                                })
                                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                                    counter = 0;
                                })
                                .show();
                    } else {
                        navView.setSelectedItemId(R.id.navigation_dashboard);
                        counter++;
                    }
                    sessionManagement.setSelectedSubGroupMenuName("");
                    break;
                }
                case "Profile": {
                    navView.setSelectedItemId(R.id.navigation_profile);
                    sessionManagement.setSelectedSubGroupMenuName("");
                    break;
                }
                case "Notification": {
                    navView.setSelectedItemId(R.id.navigation_notifications);
                    sessionManagement.setSelectedSubGroupMenuName("");
                    break;
                }
                case "Menu": {
                    //    navView.setSelectedItemId(R.id.navigation_menu);
                    break;
                }
                case "MenuMainFragment": {
                    // navView.setSelectedItemId(R.id.navigation_menu);
                    break;
                }
                default: {
                    // navView.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                }
            }
        }catch (Exception e){

        }
    }

    public void gpsLocationPermission() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}
        if(!gps_enabled && !network_enabled) {
            if (materialAlertDialogBuilder == null)
                buildAlertMessageNoGps();
        }else{
            String attendance_start_stop = sessionManagement.getattendance();
            if (attendance_start_stop != null && attendance_start_stop.equalsIgnoreCase("1")) {
                if (EmployeeAttendanceFragment.backgroundServiceintent == null) {
                    EmployeeAttendanceFragment.backgroundServiceintent = new Intent(this, ServiceBg.class);
            if(backgroundServiceintent!=null) {
                startService(backgroundServiceintent);
            }
                }
            }
        }
    }

    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private void buildAlertMessageNoGps() {
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Confirm ...")
                .setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setIcon(R.drawable.approve_order_icon)
                .setCancelable(false)
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    dialogInterface.dismiss();
                    materialAlertDialogBuilder = null;
                })
                .show();
    }


    private void requestPemission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BottomMainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }else{
            ActivityCompat.requestPermissions(BottomMainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(BottomMainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(BottomMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                            return;//
                            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}
        if(!gps_enabled && !network_enabled) {
            if (materialAlertDialogBuilder == null)
                buildAlertMessageNoGps();
        }else{
            String attendance_start_stop = sessionManagement.getattendance();
            if (attendance_start_stop != null && attendance_start_stop.equalsIgnoreCase("1")){
                if (EmployeeAttendanceFragment.backgroundServiceintent == null) {
                    EmployeeAttendanceFragment.backgroundServiceintent = new Intent(this, ServiceBg.class);
                    startService(EmployeeAttendanceFragment.backgroundServiceintent);
                }
            }
        }

    }
    //todo for checking syncing notification.....................................
    private NotificationManager notifManager;
    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        //String id = context.getString("kkkk"); // default_channel_id
        //String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel("channel1");
            if (mChannel == null) {
                mChannel = new NotificationChannel("channel1", "MyCannel", importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(), "channel1");
            intent = new Intent(getApplicationContext(), BottomMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(getApplicationContext(), "channel1");
            intent = new Intent(getApplicationContext(), BottomMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}