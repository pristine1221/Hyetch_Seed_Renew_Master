package com.example.pristineseed.ui.adapter.LeaveApplicationAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveAppliedSubmitModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveCreateModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveCreateViewModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveDaysModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveTypeSpinnerModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveDaysAdapter extends RecyclerView.Adapter<LeaveDaysAdapter.CustomViewHolder> {
    private final List<LeaveDaysModel> dataList;
    private final Context context;
    private final SessionManagement sessionManagement;
    List<LeaveTypeSpinnerModel> empList = new ArrayList<>();
    List<LeaveCreateModel> createAppliedList = new ArrayList<>();
    List<LeaveCreateViewModel> createAppliedViewList = new ArrayList<>();
    private List<String> items;
    private AutoCompleteTextView tvLeaveTypeSpinner;
    private String strName1;
    private LeaveAppliedCreateAdapter leaveAppliedCreateAdapter;
    private RecyclerView recycler_view_applied_create;

    public LeaveDaysAdapter(Context context, List<LeaveDaysModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        sessionManagement = new SessionManagement((Activity) context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_leave_days, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        LeaveDaysModel leaveListModel = dataList.get(position);
        holder.tvEmpName.setText(leaveListModel.emp_name);
        holder.tvReason.setText(leaveListModel.reason);
        holder.tvStartDate.setText(DateTimeUtilsCustome.getDateOnlyChange(leaveListModel.start_datetime));
        holder.tvEndDate.setText(DateTimeUtilsCustome.getDateOnlyChange(leaveListModel.end_datetime));
        holder.tvNoDayys.setText(leaveListModel.no_of_days);

        holder.tvEditField.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_assigned_leave_type, null);
                Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
                dialog.setContentView(popupView);
                dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
                ImageView assigned_type_cross = dialog.findViewById(R.id.assigned_type_cross);
                TextView assigned_type_submit = dialog.findViewById(R.id.assigned_type_submit);
                TextInputEditText et_leave_from_date = dialog.findViewById(R.id.et_leave_from_date);
                TextInputEditText et_leave_to_date = dialog.findViewById(R.id.et_leave_to_date);
                TextInputEditText et_days = dialog.findViewById(R.id.et_days);
                recycler_view_applied_create = dialog.findViewById(R.id.recycler_view_applied_create);
                Chip add_item = dialog.findViewById(R.id.add);

                assigned_type_cross.setOnClickListener(v1 -> dialog.dismiss());

                String fromDate = DateTimeUtilsCustome.getDateOnlyChange(leaveListModel.start_datetime);
                String toDtae = DateTimeUtilsCustome.getDateOnlyChange(leaveListModel.end_datetime);

                String Datefrom = DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate(fromDate);
                String DateTo = DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate(toDtae);
                et_leave_from_date.setText(Datefrom);

                if (leaveListModel.type.equalsIgnoreCase("single")) {
                    et_leave_to_date.setText(DateTo);
                } else if (leaveListModel.type.equalsIgnoreCase("Multi")) {
                }

                et_leave_from_date.setOnTouchListener((view1, motionEvent) -> {
                    new CustomDatePicker((Activity) context).showDatePickerDialogByRange(et_leave_from_date, Datefrom, DateTo);
                    return true;
                });

                listOfAllDates(Datefrom, DateTo);

                et_leave_to_date.setOnTouchListener((view, motionEvent) -> {
                    new CustomDatePicker((Activity) context).showDatePickerDialogByRange(et_leave_to_date, Datefrom, DateTo);
                    return true;
                });

                et_leave_to_date.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        calculateNoOfDays(Datefrom, DateTo, et_days);
                    }
                });

                String leave_id = leaveListModel.id;
                assigned_type_submit.setOnClickListener(v12 -> {
                    if (createAppliedList.size() > 0) {
                        assigned_type_submit.setEnabled(true);
                        leaveAppliedSubmit(leave_id);
                        dialog.dismiss();
                    } else {
                        assigned_type_submit.setEnabled(false);
                    }
                });

                tvLeaveTypeSpinner = dialog.findViewById(R.id.type_leave);
                leaveTypeSpinnerApi();

                add_item.setOnClickListener(v13 -> {
                    if (tvLeaveTypeSpinner.getText().toString().equalsIgnoreCase("")) {
                        tvLeaveTypeSpinner.setError("Please Select the Leave Type");
                        tvLeaveTypeSpinner.requestFocus();
                    } else if (et_days.getText().toString().equalsIgnoreCase("")) {
                        et_days.setError("Please Calculate No Of days");
                        et_days.requestFocus();
                    } else {
                        System.out.println("toleave_date :" + et_leave_to_date.getText().toString().trim());
                       String date_f = DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate1(et_leave_from_date.getText().toString());
                        String date_t = DateTimeUtilsCustome.get_yyyy_mm_dd_to_dd_mm_yyyy_ToDateFormate1(et_leave_to_date.getText().toString());
                        onLeaveAppliedCreateApi(date_f,date_t, leave_id,et_days.getText().toString());
                    }
                });

                tvLeaveTypeSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        tvLeaveTypeSpinner.setError(null);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    //spinner_for_leave_type
    public void leaveTypeSpinnerApi() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<LeaveTypeSpinnerModel>> call = mAPIService.getLeaveTypeSpiner();
        call.enqueue(new Callback<List<LeaveTypeSpinnerModel>>() {

            @Override
            public void onResponse(Call<List<LeaveTypeSpinnerModel>> call, Response<List<LeaveTypeSpinnerModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        empList = response.body();
                        for (int i = 0; i < empList.size(); i++) {
                            showListinShpinner(empList);
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Employee list", context);
                }
            }

            @Override
            public void onFailure(Call<List<LeaveTypeSpinnerModel>> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    //spinner selection
    private void showListinShpinner(List<LeaveTypeSpinnerModel> employeeNameList) {
        items = new ArrayList<>();
        for (int i = 0; i < employeeNameList.size(); i++) {
            if (!TextUtils.isEmpty(employeeNameList.get(i).description)) {
                items.add(employeeNameList.get(i).description);
            }
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
        tvLeaveTypeSpinner.setAdapter(adp);
        try {

            tvLeaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                    strName1 = employeeNameList.get(position).description;
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        //BattleRun.dismiss();
        Log.d("Item", "Clicked");
    }

    public void onLeaveAppliedCreateApi(String fDate, String tDate, String leaveIdCode,String days_diff) {
        String name = tvLeaveTypeSpinner.getText().toString();
        String[] val = name.split(" ");
        char cFirstname = val[0].charAt(0);
        char cLastname = val[1].charAt(0);
        String sFirstname = String.valueOf(cFirstname);
        String sLastname = String.valueOf(cLastname);
        String fullLeaveType = sFirstname + sLastname;

        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject postedJson = new JsonObject();
        postedJson.addProperty("created_by", sessionManagement.getUserEmail());
        postedJson.addProperty("days_diff", days_diff);
        postedJson.addProperty("from_date", fDate); //yyyy-mm-dd
        postedJson.addProperty("leave_id", leaveIdCode);
        postedJson.addProperty("to_date", tDate); //yyyy-mm-dd
        postedJson.addProperty("type", fullLeaveType);

        Call<List<LeaveCreateModel>> call = mAPIService.getLeaveAppliedCreate(postedJson);
        call.enqueue(new Callback<List<LeaveCreateModel>>() {
            @Override
            public void onResponse(Call<List<LeaveCreateModel>> call, Response<List<LeaveCreateModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        createAppliedList = response.body();
                        if (createAppliedList.size() > 0 && createAppliedList.get(0).condition) {
                            leaveCreateViewApi(leaveIdCode);
                        } else {
                            Toast.makeText(context, "Error:" + createAppliedList.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Applied Create list", context);
                } finally {
                }
            }

            @Override
            public void onFailure(Call<List<LeaveCreateModel>> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Employee list", context);
            }
        });
    }

    public void leaveCreateViewApi(String leave_id) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<LeaveCreateViewModel>> call = mAPIService.getLeaveAppliedCreateView(leave_id);
        call.enqueue(new Callback<List<LeaveCreateViewModel>>() {

            @Override
            public void onResponse(Call<List<LeaveCreateViewModel>> call, Response<List<LeaveCreateViewModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        createAppliedViewList = response.body();
                        if (createAppliedViewList.size() > 0 && createAppliedViewList.get(0).condition) {
                            generateDataList(createAppliedViewList);
                        } else {
                            Toast.makeText(context, createAppliedViewList.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), " list", context);
                }
            }

            @Override
            public void onFailure(Call<List<LeaveCreateViewModel>> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG
                ).show();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), " list", context);

            }
        });
    }

    private void generateDataList(List<LeaveCreateViewModel> photoList) {
        leaveAppliedCreateAdapter = new LeaveAppliedCreateAdapter(context, photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recycler_view_applied_create.setLayoutManager(layoutManager);
        recycler_view_applied_create.setAdapter(leaveAppliedCreateAdapter);
    }

    public void leaveAppliedSubmit(String leave_id) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<LeaveAppliedSubmitModel>> call = mAPIService.getLeaveAppliedSubmit(leave_id);
        call.enqueue(new Callback<List<LeaveAppliedSubmitModel>>() {

            @Override
            public void onResponse(Call<List<LeaveAppliedSubmitModel>> call, Response<List<LeaveAppliedSubmitModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<LeaveAppliedSubmitModel> leaveAppliedSubmit = response.body();
                        if (leaveAppliedSubmit.size() > 0 && leaveAppliedSubmit.get(0).condition) {
                            Toast.makeText(context, leaveAppliedSubmit.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, leaveAppliedSubmit.get(0).message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "list", context);
                }
            }

            @Override
            public void onFailure(Call<List<LeaveAppliedSubmitModel>> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG
                ).show();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "list", context);

            }
        });
    }

    void calculateNoOfDays(String LeaveDateFrom, String LeaveDateTo, TextInputEditText et_calulate_days) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = myFormat.parse(LeaveDateFrom);
            Date date2 = myFormat.parse(LeaveDateTo);
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = (hours / 24) + 1;

            et_calulate_days.setText(String.valueOf(days));
            Log.e("days", String.valueOf(days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView tvEmpName, tvReason, tvStartDate, tvEndDate, tvNoDayys;
        ImageView tvEditField;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvEmpName = mView.findViewById(R.id.tv_emp_name);
            tvReason = mView.findViewById(R.id.tv_reason);
            tvStartDate = mView.findViewById(R.id.tv_start_date);
            tvEndDate = mView.findViewById(R.id.tv_end_date);
            tvNoDayys = mView.findViewById(R.id.tv_days);
            tvEditField = mView.findViewById(R.id.approve_edit);
        }
    }

    //list of all days from  between two dates
    void listOfAllDates(String dTrom, String dTo) {
        List<Date> dates = new ArrayList<Date>();
        String str_date = dTrom;
        String end_date = dTo;

        DateFormat formatter;

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = (Date) formatter.parse(end_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);
            System.out.println(" Date is ..." + ds);
           // Log.e("date is...", ds);
        }
    }
}