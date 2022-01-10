package com.example.pristineseed.ui.splashScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.pristineseed.LoginActivity;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.example.pristineseed.ui.slider_carousel.SliderCarouselActivity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
   private ImageView pristineImage;
   private Animation animTogether;
   private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
           if(getSupportActionBar()!=null) {   //will hide the title
             getSupportActionBar().hide();
             // hide the title bar
             }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        }
        else {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        }
        //enable full screen
        setContentView(R.layout.activity_splash_screen);
        sessionManagement=new SessionManagement(this);
        //todo rotate image
        pristineImage = findViewById(R.id.pristineImage);
        // todo end rotate
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkPermission()) {
                    gotoLogin();
                } else {
                    requestPermission();
                }
            }
        }, 200);
    }

    void gotoLogin() {
            if (sessionManagement.getLastSession().equalsIgnoreCase("1")) {
            Intent intent = new Intent(SplashScreenActivity.this, BottomMainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(SplashScreenActivity.this, SliderCarouselActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    //todo when request permision hit
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (readAccepted && writeAccepted && cameraAccepted) {
//                        Toast.makeText(LogoPopupActivity.this, "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_LONG).show();
                        gotoLogin();
                    } else {
//                        Toast.makeText(LogoPopupActivity.this, "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }

    //todo show message yes or no after disallow
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}
