package com.example.pristineseed.ui.employee.HRPortalEmployee;

import static com.example.pristineseed.ui.dashboard.newTheam.DashBoard2Fragment.backgroundServiceintent;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.EmployeeModel.EmployeeAttendModel;
import com.example.pristineseed.model.EmployeeModel.LiveViewModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.model.EmployeeModel.CurrentAttendanceModel;
import com.example.pristineseed.model.EmployeeModel.EndClockAttendanceModel;
import com.example.pristineseed.model.EmployeeModel.StartClockAttendanceModel;
import com.example.pristineseed.ui.dashboard.newTheam.ServiceBg;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAttendanceFragment extends Fragment {
   private TextInputEditText editTextWorkIn, editTextWorkFrom;
   private TextInputLayout ti_workIn, ti_workFrom;
   private Button bt_submitattandance, button_ok;
   private TextView tv_timere_textview, timer;
   private LinearLayout stopwatch_timer, init_layout_section;
   private int leave_id;
   private ImageView attendance_delete;
   private TextInputEditText et_attendance_date;
    private TextView work_time, work_in, work_from, work_admin,work_end;
    private SessionManagement sessionManagement;
    private int houres, minute, sec;
    public static Intent backgroundServiceintent;
    private TableLayout table_layout_text;
    public static   LiveViewModel employee_Attan_location;

    public EmployeeAttendanceFragment() {
        houres = 0;
        minute = 0;
        sec = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_employee_attendance_new, container, false);
        sessionManagement = new SessionManagement(getActivity());
        Log.e("emp_id", sessionManagement.getemp_id()) ;
        employee_Attan_location= new ViewModelProvider(this).get(LiveViewModel.class);
        return v;
    }

    //fragment load while to check currently their is any working clock
    private void onCurrentAttendance() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject postedJson = new JsonObject();
        postedJson.addProperty("EmployeeID", sessionManagement.getemp_id());
        Call<List<CurrentAttendanceModel>> call = mAPIService.Current(postedJson);
        call.enqueue(new Callback<List<CurrentAttendanceModel>>() {
            @Override
            public void onResponse(Call<List<CurrentAttendanceModel>> call, Response<List<CurrentAttendanceModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<CurrentAttendanceModel> list = response.body();
                        if (list!=null && list.size() > 0 && list.get(0).condition) {
                            leave_id = list.get(0).id;
                            stopwatch_timer.setVisibility(View.VISIBLE);
                            init_layout_section.setVisibility(View.GONE);
                            table_layout_text.setVisibility(View.VISIBLE);
                            bt_submitattandance.setText("Stop work");
                            bt_submitattandance.setBackgroundColor(Color.RED);

                            int input = Integer.parseInt(list.get(0).running_time);
                            int p1 = input % 60;  //seconds
                            int p2 = input / 60;  //hrs
                            int p3 = p2 % 60; //minutes
                            p2 = p2 / 60;

                            System.out.print("HH:MM:SS - " + p2 + ":" + p3 + ":" + p1);
                            tv_timere_textview.setText(p2 + ":" + p3 + ":" + p1);

                            houres = p2;
                            minute = p3;
                            sec = p1;

                            handler.postDelayed(timerThread, 1000);

                            work_admin.setText(sessionManagement.getUserEmail());
                            work_in.setText(list.get(0).work_type);
                            work_from.setText(list.get(0).work_location);
                            timer.setText(list.get(0).start_datetime);

                        } else {
                            stopwatch_timer.setVisibility(View.GONE);
                            init_layout_section.setVisibility(View.VISIBLE);
                            Log.e("message", "No data");
                        }
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void onFailure(Call<List<CurrentAttendanceModel>> call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "End_clock", getActivity());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //handler.postDelayed(timerThread,1000);
        handleLocationUpdates();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextWorkIn = view.findViewById(R.id.editTextworking);
        editTextWorkFrom = view.findViewById(R.id.editTextworkfrom);
        et_attendance_date = view.findViewById(R.id.et_attendance_date);
        timer = view.findViewById(R.id.timer);
        bt_submitattandance = view.findViewById(R.id.bt_submitattandance);
        stopwatch_timer = view.findViewById(R.id.stopwatch_timer);
        init_layout_section = view.findViewById(R.id.init_layout_section);
        tv_timere_textview = view.findViewById(R.id.tv_timere_textview);
        ti_workIn = view.findViewById(R.id.ti_workin_layout);
        ti_workFrom = view.findViewById(R.id.ti_workfrom_layout);
        button_ok = view.findViewById(R.id.btn_ok);
        attendance_delete = view.findViewById(R.id.attendance_delete);
        table_layout_text=view.findViewById(R.id.table_layout_hh);
        work_admin=view.findViewById(R.id.work_admin);
        work_in=view.findViewById(R.id.work_in);
        work_from=view.findViewById(R.id.work_from);
        onCurrentAttendance();


        employee_Attan_location.getLiveData().observe(getActivity(),employeeAttendModel -> {
            if(employeeAttendModel!=null){
                String my_city=employeeAttendModel.city!=null?employeeAttendModel.city:"";
                String state=employeeAttendModel.from_area!=null?employeeAttendModel.from_area:"";
                String postal_code=employeeAttendModel.from_postal_code!=null?employeeAttendModel.from_postal_code:"";
                String to_postal_code=employeeAttendModel.to_postal_code!=null?employeeAttendModel.to_postal_code:"";
                city=my_city+","+state+","+postal_code+","+to_postal_code;
            }
        });
        bt_submitattandance.setOnClickListener(v -> {
            if(bt_submitattandance.getText().toString().equalsIgnoreCase("Start Work")){
            if(editTextWorkIn.getText().toString().trim().equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "Please enter work input !", Toast.LENGTH_SHORT).show();
            }
            else if(editTextWorkFrom.getText().toString().trim().equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "Please enter from work location !", Toast.LENGTH_SHORT).show();
            }
            else {
                if (bt_submitattandance.getText().toString().equalsIgnoreCase("Start Work")) {
                    onStartAttendance();
                }
               }
            }
           if (bt_submitattandance.getText().toString().equalsIgnoreCase("Stop Work")) {
                onEndAttendance();
                bt_submitattandance.setText("Start Work");
                init_layout_section.setVisibility(View.VISIBLE);
                bt_submitattandance.setBackgroundColor(Color.parseColor("#009D48"));
                stopwatch_timer.setVisibility(View.GONE);
                attendance_delete.setVisibility(View.GONE);
                handler.removeCallbacks(timerThread);
                houres = 0;
                minute = 0;
                sec = 0;
                tv_timere_textview.setText(intToString(houres, 2) + ":" + intToString(minute, 2) + ":" + intToString(sec, 2));
            }

        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void onStartAttendance() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject postedJson = new JsonObject();
            postedJson.addProperty("EmployeeID", sessionManagement.getemp_id());
            postedJson.addProperty("WorkType", editTextWorkIn.getText().toString());
            postedJson.addProperty("WorkLocation", editTextWorkFrom.getText().toString());
            postedJson.addProperty("StartLocation",city);

            Call<List<StartClockAttendanceModel>> call = mAPIService.Start(postedJson);
            call.enqueue(new Callback<List<StartClockAttendanceModel>>() {
                @Override
                public void onResponse(Call<List<StartClockAttendanceModel>> call, Response<List<StartClockAttendanceModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<StartClockAttendanceModel> list = response.body();
                            if (list!=null && list.size() > 0 && list.get(0).condition) {
                                leave_id = list.get(0).id;
                                if (bt_submitattandance.getText().toString().equalsIgnoreCase("Start Work")) {
                                    sessionManagement.setattendance(String.valueOf(list.get(0).is_running));
                                    sessionManagement.setattendance(String.valueOf(list.get(0).is_running));
                                    bt_submitattandance.setText("Stop Work");
                                    init_layout_section.setVisibility(View.GONE);
                                    bt_submitattandance.setBackgroundColor(Color.RED);
                                    stopwatch_timer.setVisibility(View.VISIBLE);
                                    attendance_delete.setVisibility(View.GONE);
                                    handler.postDelayed(timerThread, 1000);
                                    table_layout_text.setVisibility(View.VISIBLE);
                                    work_admin.setText(sessionManagement.getUserEmail());
                                    work_in.setText(editTextWorkIn.getText().toString());
                                    work_from.setText(editTextWorkFrom.getText().toString());
                                    timer.setText(list.get(0).start_datetime);
                                    handleLocationUpdates();
                                   }
                                /* else {
                                    onEndAttendance();
                                    bt_submitattandance.setText("Start Work");
                                    init_layout_section.setVisibility(View.VISIBLE);
                                    bt_submitattandance.setBackgroundColor(Color.parseColor("#009D48"));
                                    stopwatch_timer.setVisibility(View.GONE);
                                    attendance_delete.setVisibility(View.GONE);
                                    handler.removeCallbacks(timerThread);

                                    houres = 0;
                                    minute = 0;
                                    sec = 0;
                                    tv_timere_textview.setText(intToString(houres, 2) + ":" + intToString(minute, 2) + ":" + intToString(sec, 2));
                                    progressDialogLoading.hideDialog();
                                }*/
                            } else {
                                Toast.makeText(getContext(), "Error" + list.get(0).message, Toast.LENGTH_LONG).show();
                                progressDialogLoading.hideDialog();
                            }
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "start_clock", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<StartClockAttendanceModel>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "start_clock", getActivity());
                }
            });
        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }


  private String city="";
  private void onEndAttendance() {
           if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
         /*   employee_Attan_location.getLiveData().observe(getActivity(),employeeAttendModel -> {
                if(employeeAttendModel!=null){
                    String my_city=employeeAttendModel.city!=null?employeeAttendModel.city:"";
                    String state=employeeAttendModel.from_area!=null?employeeAttendModel.from_area:"";
                    String postal_code=employeeAttendModel.from_postal_code!=null?employeeAttendModel.from_postal_code:"";
                    String to_postal_code=employeeAttendModel.to_postal_code!=null?employeeAttendModel.to_postal_code:"";
                    city=my_city+","+state+","+postal_code+","+to_postal_code;
                }
            });*/

            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject postedJson = new JsonObject();
            postedJson.addProperty("EmployeeID", sessionManagement.getemp_id());
            postedJson.addProperty("ID", leave_id);
            postedJson.addProperty("EndLocation",city);

            Call<List<EndClockAttendanceModel>> call = mAPIService.End(postedJson);
            call.enqueue(new Callback<List<EndClockAttendanceModel>>() {
                @Override
                public void onResponse(Call<List<EndClockAttendanceModel>> call, Response<List<EndClockAttendanceModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<EndClockAttendanceModel> list = response.body();
                            if (list!=null && list.size() > 0 && list.get(0).condition) {
                                editTextWorkIn.setText("");
                                editTextWorkFrom.setText("");
                                onCurrentAttendance();
                            } else {
                                Toast.makeText(getContext(), "Error:" + list.get(0).message, Toast.LENGTH_LONG).show();
                                progressDialogLoading.hideDialog();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<EndClockAttendanceModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "End_clock", getActivity());
                }
            });
        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    Handler handler = new Handler(Looper.myLooper());
    Runnable timerThread = new Runnable() {
        @Override
        public void run() {
            sec++;
            if (sec > 60) {
                sec = 0;
                minute++;
            }
            if (minute > 60) {
                minute = 0;
                houres++;
            }
            tv_timere_textview.setText(intToString(houres, 2) + ":" + intToString(minute, 2) + ":" + intToString(sec, 2));
            handler.postDelayed(this, 1000);
        }
    };


    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerThread);
    }

    private void handleLocationUpdates(){
        if(backgroundServiceintent==null){
          if(!isMyServiceRunning(ServiceBg.class)) {
              backgroundServiceintent = new Intent(getActivity(), ServiceBg.class);
              if(getActivity()!=null) {
                  getActivity().startService(backgroundServiceintent);
              }
          } else {
                 Log.e("service_","service is running....");
              }
          }
    }

    private void stophandleLocationUpdates(){
        if(backgroundServiceintent!=null){
            if(   getActivity()!=null) {
                getActivity().stopService(backgroundServiceintent);
            }
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    private void getlocationMaster() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(getActivity())
                    .setMessage("Gps network not enabled")
                    .setPositiveButton("Open location Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }
}