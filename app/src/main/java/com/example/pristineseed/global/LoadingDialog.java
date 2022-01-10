package com.example.pristineseed.global;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.pristineseed.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog {
    private AlertDialog alertDialog;
    private AppCompatImageView progressBar;
    private boolean loadingstate = false;
    private Animation myRotation;
    Runnable animateView;
    Handler mHandler;
    Thread animationThread;
    AVLoadingIndicatorView avLoadingIndicatorView;
    public void showLoadingDialog(Activity activity) {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.loading_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);//,R.style.loading_dialog)
        builder.setView(dialogView);
       // progressBar = dialogView.findViewById(R.id.loading_progress_bar);
        String indicator=activity.getIntent().getStringExtra("indicator");
        avLoadingIndicatorView=dialogView.findViewById(R.id.avi);
        avLoadingIndicatorView.setIndicator(indicator);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(null);
        animateView = new Runnable(){
            @Override public void run(){
                loadingstate = true;
                avLoadingIndicatorView.show();
               // myRotation = AnimationUtils.loadAnimation(activity, R.anim.rotate_image_anim);
               // progressBar.startAnimation(myRotation);
            }
        };

        mHandler = new Handler();
        animationThread = new Thread(new Runnable(){
            @Override public void run(){
                mHandler.post(animateView);
                Log.e("anim","start");
            }
        });
        animationThread.start();

     /*   myRotation = AnimationUtils.loadAnimation(activity, R.anim.rotate_image_anim);
        progressBar.startAnimation(myRotation);*/

    }
    public boolean getLoadingState() {
        return loadingstate;
    }

    public void hideDialog() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                loadingstate = false;
               // progressBar.setVisibility(View.GONE);
                //progressBar.clearAnimation();
                avLoadingIndicatorView.hide();
                mHandler.removeCallbacks(animateView);
                animationThread.interrupt();
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
