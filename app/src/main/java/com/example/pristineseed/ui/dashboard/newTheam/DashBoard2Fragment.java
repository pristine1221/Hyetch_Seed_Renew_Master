package com.example.pristineseed.ui.dashboard.newTheam;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pristineseed.DataSyncingBackgraundProcess.BackgruandSyncing_process;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.login.LoginModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.dashboard_grid_adapter.DashImageModel;
import com.example.pristineseed.ui.adapter.dashboard_grid_adapter.Dash_board_grid_adapter;
import com.example.pristineseed.ui.adapter.dashboard_grid_adapter.ItemOffsetDecoration;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashBoard2Fragment extends Fragment {
    Chip current_location_view;
    private SessionManagement sessionManagement;
    private TextView user_name;
    private TextView tv_temp_infer,tv_humdity,rainfall,tv_wind_speed;
    private  String current_location_lat_log;
    private  String icon_url="";
    private  String text="";
    private ImageView rain_day_img;
    private String rain_image_url="https:"+icon_url;
    private TextView tv_condition;
    private static OkHttpClient okHttpClient;
    private static int REQUEST_TIMEOUT = 2;
    private  String temp_f="",humidity="",rain_fall_="",wind_speed="";
    public static Intent backgroundServiceintent;
    public static String location_string;
    public FrameLayout fm_notification;
    private TextView notification_count;
    private RecyclerView grid_image_list;
    private Integer[] image_array={R.drawable.slide_das_img1,R.drawable.slide_dash_img2,R.drawable.slide_dash_img3,
            R.drawable.musterd_img,R.drawable.hytech_img,
            R.drawable.face_book_and_linkedin,R.drawable.gallery_slide_das_0612,R.drawable.gallery_award_pic};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement=new SessionManagement(getActivity());
        current_location_view=view.findViewById(R.id.current_location_view);
        user_name=view.findViewById(R.id.user_name);
        tv_temp_infer=view.findViewById(R.id.tv_temp_infer);
        tv_humdity=view.findViewById(R.id.tv_humdity);
        tv_wind_speed=view.findViewById(R.id.wind_speed);
        rain_day_img=view.findViewById(R.id.rain_day_img);
        tv_condition=view.findViewById(R.id.tv_condition);
        rainfall=view.findViewById(R.id.rainfall);
        fm_notification=view.findViewById(R.id.fm_notification);
        grid_image_list=view.findViewById(R.id.grid_image);
        notification_count=view.findViewById(R.id.notification_count);
        user_name.setText(sessionManagement.getUserName());
        ObjectAnimator a = ObjectAnimator.ofInt(tv_condition, "textColor", getResources().getColor(R.color.pending_color), Color.WHITE);
        a.setInterpolator(new LinearInterpolator());
        a.setDuration(300);
        a.setRepeatCount(ValueAnimator.INFINITE);
        a.setRepeatMode(ValueAnimator.REVERSE);
        a.setEvaluator(new ArgbEvaluator());
        AnimatorSet t = new AnimatorSet();
        t.play(a);
        t.start();
        getLoadGridImgae();
        sendReceivePushNotification();
        sentFcmTokenToServer();
        handleLocationUpdates();

        if(sessionManagement.getCurrentCity()!=null) {
            current_location_view.setText(sessionManagement.getCurrentCity());
        }
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
                  try {
                      Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CommonData.WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            NetworkInterface service = retrofit.create(NetworkInterface.class);
            current_location_lat_log=sessionManagement.getCurrentLocation();
            if(current_location_lat_log!=null && !current_location_lat_log.equalsIgnoreCase("")) {
                Call<WeatherResponse> call = service.getCurrentWeatherData(CommonData.weather_key, current_location_lat_log);
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            WeatherResponse weatherResponse = response.body();
                            if (weatherResponse != null) {
                                WeatherResponse.Current current = weatherResponse.current;
                                if (current != null) {
                                    temp_f = current.temp_c;
                                    rain_fall_ = current.precip_in;
                                    humidity = current.humidity;
                                    wind_speed = current.wind_mph;
                                    WeatherResponse.Current.Condition condition = current.condition;
                                      if (condition != null) {
                                        icon_url = condition.icon;
                                        text = condition.text;
                                       }
                                    try {
                                        sessionManagement.setTempF(temp_f);
                                        sessionManagement.setRainFall(rain_fall_);
                                        sessionManagement.sethumidity(humidity);
                                        sessionManagement.setwindSpeed(wind_speed);
                                        sessionManagement.setweatherCondition(text);
                                        tv_temp_infer.setText(sessionManagement.getTempF() + "\u00B0"+"C");
                                        rainfall.setText(sessionManagement.getRainFall() + " in");
                                        tv_humdity.setText(sessionManagement.gethumidity() + "%");
                                        tv_wind_speed.setText(sessionManagement.getwindSpeed() + "mph");
                                        tv_condition.setText(sessionManagement.getweatherCondition());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                        Log.e("weather_api", t.getMessage());
                    }
                });
            }
            else {
                Toast.makeText(getActivity(),"Enable to fetch current location.",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        current_location_view.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), GogogleMapShowingFragment.class));
        });

   /*  fm_notification.setOnTouchListener((v, event) -> {
        InspectionsNotificationFragment inspectionNotificationFragment=new InspectionsNotificationFragment();
        StaticMethods.loadFragments(getActivity(),inspectionNotificationFragment,"Notification_fragment");
         return true;
     });*/

    }

    private void handleLocationUpdates() {
            backgroundServiceintent = new Intent(getActivity(), ServiceBg.class);
            getActivity().startService(backgroundServiceintent);
    }

    private void stophandleLocationUpdates(){
            getActivity().stopService(backgroundServiceintent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sentFcmTokenToServer(){
        Log.e("main_token",sessionManagement.getFcmToken());
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject postedJson = new JsonObject();
        postedJson.addProperty("Email",sessionManagement.getUserEmail());
        postedJson.addProperty("password",sessionManagement.getPasword());
        postedJson.addProperty("token",sessionManagement.getFcmToken());
        Call<LoginModel> call = mAPIService.Login(postedJson);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {
                    if (response.isSuccessful()) {
                        LoginModel getResponse = response.body();
                        if (getResponse!=null && getResponse.condition) {
                        Log.e("Token_fetech","Fetech_Token Successful");
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "login", getActivity());
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call,Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "login",  getActivity());
            }
        });
    }

    private void sendReceivePushNotification(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>(){
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        try {
                            sessionManagement.setFcmToken(token);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("token",token);
                    }
                });
    }

    private void getLoadGridImgae(){
        RecyclerView.LayoutManager layoutmanager = new GridLayoutManager(getActivity(), 3);
        grid_image_list.setLayoutManager(layoutmanager);
        grid_image_list.setItemAnimator(new DefaultItemAnimator());
        List<Integer> img_list = Arrays.asList(image_array);
        List<DashImageModel> dashImage_list=new ArrayList<>();

        for(int i=0;i<img_list.size();i++){
            DashImageModel dashImageModel=new DashImageModel();
            dashImageModel.img_url=img_list.get(i);
            dashImage_list.add(dashImageModel);

        }

        Dash_board_grid_adapter dash_board_grid_adapter=new Dash_board_grid_adapter(getActivity(),dashImage_list);
        grid_image_list.setAdapter(dash_board_grid_adapter);

    }


}