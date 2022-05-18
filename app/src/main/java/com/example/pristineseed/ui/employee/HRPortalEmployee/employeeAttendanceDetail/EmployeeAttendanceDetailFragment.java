package com.example.pristineseed.ui.employee.HRPortalEmployee.employeeAttendanceDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.MaterialDatePicker;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.EmployeeModel.EmployeeAttendanceDetailModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveDaysModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.EmployeeAttendanceDetailAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAttendanceDetailFragment extends Fragment {

    private TextInputEditText et_start_date, et_end_date;
    private Button btn_submit;
    private SessionManagement sessionManagement;
    private RecyclerView rv_emp_attendance_list;
    private LinearLayoutManager layoutManager;
    private String start_date = "", end_date = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // todo Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_attendance_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
    }

    private void initView(View view) {
        et_start_date = view.findViewById(R.id.et_start_date);
        et_end_date = view.findViewById(R.id.et_end_date);
        btn_submit = view.findViewById(R.id.btn_submit);
        rv_emp_attendance_list = view.findViewById(R.id.rv_emp_attendance_list);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_emp_attendance_list.setLayoutManager(layoutManager);

        //todo edit start date...
        et_start_date.setText(getSevenDaysBeforeDate());

        et_start_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new CustomDatePicker(getActivity()).showDatePickerDialog(et_start_date);
                }
                return true;
            }
        });

        //todo converting mm-dd-yyyy into yyyy-mm-dd....
        Calendar c = Calendar.getInstance();
        int d = 0, m = 0, y = 0;
        start_date = String.valueOf(et_start_date.getText().toString());
        String[] split = start_date.split("-");
        d = Integer.valueOf(split[0]);
        m = Integer.valueOf(split[1]);
        y = Integer.valueOf(split[2]);
        String pass_start_date = String.valueOf(y + "-" + m + "-" + d);
        start_date = pass_start_date;

        //todo edit end date...
        et_end_date.setText(getCurrentDate());

        et_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                    materialDatePicker.disableAllDatesUnableOneMonthDates(et_start_date, et_end_date, et_start_date.getText().toString());
                }
                return true;
            }
        });

        //todo converting mm-dd-yyyy into yyyy-mm-dd....
        end_date = String.valueOf(et_end_date.getText().toString());
        String[] split2 = end_date.split("-");
        d = Integer.valueOf(split2[0]);
        m = Integer.valueOf(split2[1]);
        y = Integer.valueOf(split2[2]);
        String pass_end_date = String.valueOf(y + "-" + m + "-" + d);
        end_date = pass_end_date;

        getEmployeeAttendanceDetail(start_date, end_date);

    }

    //todo show details.;
    public void getEmployeeAttendanceDetail(String start_date, String end_date){
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("EmployeeID",sessionManagement.getemp_id());
            jsonObject.addProperty("StartDate", start_date);
            jsonObject.addProperty("EndDate", end_date);
            Call<List<EmployeeAttendanceDetailModel>> call = mAPIService.getEmployeeAttendanceDetails(jsonObject);
            call.enqueue(new Callback<List<EmployeeAttendanceDetailModel>>() {
                @Override
                public void onResponse(Call<List<EmployeeAttendanceDetailModel>> call, Response<List<EmployeeAttendanceDetailModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<EmployeeAttendanceDetailModel> respoinseList = response.body();
                            if (respoinseList.size() > 0 && respoinseList !=  null && respoinseList.get(0).condition) {
                                List<EmployeeAttendanceDetailModel> detailModelList = respoinseList;
                                EmployeeAttendanceDetailAdapter adapter = new EmployeeAttendanceDetailAdapter(getActivity(), detailModelList);
                                rv_emp_attendance_list.setAdapter(adapter);
                            } else {
                                Toast.makeText(getActivity(), respoinseList.size() > 0 ? "Result not found" : "", Toast.LENGTH_LONG).show();
                                progressDialogLoading.hideDialog();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "list", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<EmployeeAttendanceDetailModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "leave approval_list", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    //todo getting 7 days before date from current date in input edit text...
    public String getSevenDaysBeforeDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date sevenDaysBeforeDate = cal.getTime();
        String date = dateFormat.format(sevenDaysBeforeDate);
        return date;
    }

    //todo getting today date for set in text input edit text...
    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

}