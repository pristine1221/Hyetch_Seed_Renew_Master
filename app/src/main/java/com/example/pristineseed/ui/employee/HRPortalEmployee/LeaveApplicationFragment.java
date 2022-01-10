package com.example.pristineseed.ui.employee.HRPortalEmployee;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomCurrentDatePicker;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.MaterialDatePicker;
import com.example.pristineseed.model.EmployeeModel.EmployeeLeaveMasterModel;
import com.example.pristineseed.model.LeaveApplicationModel.ApplyLeaveModel;
import com.example.pristineseed.model.LeaveApplicationModel.AvailableLeaveListModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveListModel;
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.AvailableLeaveAdapter;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeaveAdapter;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeaveTypeAdapter;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.SubLeaveTypeAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApplicationFragment extends Fragment {
    private RecyclerView leave_list_recycler;
     private SessionManagement sessionManagement;
    private AvailableLeaveAdapter adapterAvailableLeave;
    private Chip chip_add_new_applied_list;
    private AutoCompleteTextView mbs_select_leave_type,ac_sub_leave_type;
    private TextInputEditText et_leave_from_date, et_leave_to_date, et_desc;
    private RadioGroup rg_leave_type;
    private RadioButton  rb_short_leave;
    private Button btn_ok;
    private TextInputLayout leave_to_date;
    private List<AvailableLeaveListModel> list = new ArrayList<>();
    private String postedString="false";
    private  List<EmployeeLeaveMasterModel.Data> leave_type_list=null;
    String flag = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leave_application, container, false);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        leave_list_recycler = view.findViewById(R.id.leave_list_recycler);
        chip_add_new_applied_list = view.findViewById(R.id.chip_add_new_applied_list);
        chip_add_new_applied_list.setOnClickListener(v -> AddNewItemPopup());
      //  onAppliedLeaveList();
    }

    @Override
    public void onResume() {
        onAppliedLeaveList();
        super.onResume();
    }

    /**
     * Leave List which is newly Applied
     */

    public void onAppliedLeaveList() {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject postedJson = new JsonObject();
            postedJson.addProperty("EmployeeID",sessionManagement.getemp_id());
            Call<List<AvailableLeaveListModel>> call = mAPIService.availableLeaveList(postedJson);
            call.enqueue(new Callback<List<AvailableLeaveListModel>>() {
                @Override
                public void onResponse(Call<List<AvailableLeaveListModel>> call, Response<List<AvailableLeaveListModel>> response) {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            list = response.body();
                                if (list!=null && list.size() > 0 && list.get(0).condition) {
                                       generateDataList();
                                    } else {
                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                        progressDialogLoading.hideDialog();
                                    }
                                } else {
                                    Log.e("message", "No List");
                                    progressDialogLoading.hideDialog();
                                }
                }

                @Override
                public void onFailure(Call<List<AvailableLeaveListModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Available List", getActivity());
                }
            });
        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection.",Toast.LENGTH_SHORT).show();
        }
    }

    private void generateDataList() {
        adapterAvailableLeave = new AvailableLeaveAdapter(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        leave_list_recycler.setLayoutManager(layoutManager);
        leave_list_recycler.setAdapter(adapterAvailableLeave);
    }

    private int leave_days=0;

    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_applied_leave_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        mbs_select_leave_type = popupView.findViewById(R.id.select_leave_type);
        leave_to_date = popupView.findViewById(R.id.leave_to_date);
        et_leave_from_date = popupView.findViewById(R.id.et_leave_from_date);
        et_leave_to_date = popupView.findViewById(R.id.et_leave_to_date);
        rg_leave_type = popupView.findViewById(R.id.rg_leave_type);
        rb_short_leave = popupView.findViewById(R.id.rb_short_leave);
        btn_ok = popupView.findViewById(R.id.btn_ok);
        et_desc = popupView.findViewById(R.id.et_desc);
        ac_sub_leave_type=popupView.findViewById(R.id.ac_sub_leave_type);
        MaterialProgressBar content_loading=popupView.findViewById(R.id.leave_content_loading);

       // LinearLayout sub_levae_type=popupView.findViewById(R.id.sub_levae_type);

        List<String> parent_leave_type= Arrays.asList(CommonData.leaveTypeList);
        //getSubLeaveType(ac_sub_leave_type);
        ac_sub_leave_type.setOnItemClickListener((parent, view, position, id) -> {
            switch (leave_type_list.get(position).Leave_Type){
                case "Maternity":
                    /*et_leave_to_date.setEnabled(false);
                    et_leave_to_date.setSelected(false);*/
                    et_leave_from_date.setText("");
                    et_leave_to_date.setText("");
                    leave_days=Integer.parseInt(leave_type_list.get(position).Leave_Days);
                    break;
                case "Paternity":
                    /*et_leave_to_date.setEnabled(false);
                    et_leave_to_date.setSelected(false);*/
                    et_leave_from_date.setText("");
                    et_leave_to_date.setText("");
                    leave_days=Integer.parseInt(leave_type_list.get(position).Leave_Days);
                    break;

                    case "Casual":
                       /* et_leave_to_date.setEnabled(true);
                        et_leave_to_date.setSelected(true);*/
                        et_leave_from_date.setText("");
                        et_leave_to_date.setText("");
                        leave_days = Integer.parseInt(leave_type_list.get(position).Leave_Days);
                        break;

            }
        });


       /* et_leave_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                materialDatePicker.showWeekDaysDialog(et_leave_to_date);
            }
        });*/

        et_leave_to_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if(mbs_select_leave_type.getText().toString().equalsIgnoreCase("Single Leave")) {
                        MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                        materialDatePicker.showWeekDaysDialog(et_leave_to_date, et_leave_from_date, "to_date_set_text", "");
                    }
                    else if(mbs_select_leave_type.getText().toString().equalsIgnoreCase("Multi Leave")) {
                        MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                        materialDatePicker.showWeekDaysDialog(et_leave_to_date, et_leave_from_date, "not_set_text", et_leave_from_date.getText().toString());
                    }
                }
                return false;
            }
        });

        et_leave_from_date.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if(mbs_select_leave_type.getText().toString().equalsIgnoreCase("Single Leave")){
                    MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                    materialDatePicker.showWeekDaysDialog(et_leave_from_date, et_leave_to_date,"to_date_set_text","");
                }
                else if(mbs_select_leave_type.getText().toString().equalsIgnoreCase("Multi Leave")){
                    MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                    materialDatePicker.showWeekDaysDialog(et_leave_from_date, et_leave_to_date,"not_set_text","");
                }

            }
            return true;
        });


     /*   et_leave_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = new MaterialDatePicker(getActivity());
                materialDatePicker.showWeekDaysDialog(et_leave_from_date);
            }
        });*/

       /* et_leave_from_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
//                    et_leave_to_date.setText(et_leave_from_date.getText());
                    if(ac_sub_leave_type.getText().toString().equalsIgnoreCase("Casual")) {
                        et_leave_to_date.setText("");
                    }
                    else {
                        et_leave_to_date.setText(getLevaeDateDaysWise(et_leave_to_date.getText().toString().trim(), leave_days));
                    }
            }
        });*/

      /*  et_leave_to_date.setOnTouchListener((v, event) -> {
            new CustomCurrentDatePicker(getActivity()).showDatePickerDialog_min(et_leave_to_date, et_leave_from_date != null && !et_leave_from_date.getText().toString().equalsIgnoreCase("") ?
                    et_leave_from_date.getText().toString() : "");

            return true;
        });*/

        btn_ok.setOnClickListener(v -> {
            onApplyLeaveList(dialog);
        });

        mbs_select_leave_type.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                getLeaveTypeMaster(content_loading,"Single Leave");
                ac_sub_leave_type.setText("");
                et_leave_from_date.setText("");
                et_leave_to_date.setText("");
                et_leave_to_date.setEnabled(false);
                et_leave_to_date.setSelected(false);
//                et_leave_to_date.setVisibility(View.GONE);
//                leave_to_date.setVisibility(View.GONE);

            } else if (position == 1) {
                getLeaveTypeMaster(content_loading,"Multi Leave");
                ac_sub_leave_type.setText("");
                et_leave_from_date.setText("");
                et_leave_to_date.setText("");
                et_leave_to_date.setSelected(true);
                et_leave_to_date.setEnabled(true);
                et_leave_to_date.setVisibility(View.VISIBLE);
//                leave_to_date.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.android_item_view, parent_leave_type);
        mbs_select_leave_type.setAdapter(adapter);

    }

    /*private void getSubLeaveType(AutoCompleteTextView ac_sub_leave_type) {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();

           Call<List<LevaeTypeModel>> call = mAPIService.getLeaveType();
           call.enqueue(new Callback<List<LevaeTypeModel>>() {
            @Override
            public void onResponse(Call<List<LevaeTypeModel>> call, Response<List<LevaeTypeModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<LevaeTypeModel> list = response.body();
                        if (list!=null && list.size() > 0 && list.get(0).condition) {
                            SubLeaveTypeAdapter leaveTypeAdapter=new SubLeaveTypeAdapter(getActivity(),R.layout.android_item_view,list);
                            ac_sub_leave_type.setAdapter(leaveTypeAdapter);
                        } else {
                            Toast.makeText(getContext(), list.get(0).message, Toast.LENGTH_LONG).show();
                            progressDialogLoading.hideDialog();
                        }
                    }
                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "sub_leave_type_code", getActivity());
                } finally {
                    progressDialogLoading.hideDialog();
                }
            }
            @Override
            public void onFailure(Call<List<LevaeTypeModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "sub_leave_type_code", getActivity());
            }
        });
    }else {
        Toast.makeText(getActivity(),"Please wait for internet connection.",Toast.LENGTH_SHORT).show();
    }
    }*/

    public void onApplyLeaveList(Dialog dialog) {
        String dateformatStart = DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(et_leave_from_date.getText().toString());
        String dateformaEnd = DateTimeUtilsCustome.getDateRetrunIntoYYMMDD(et_leave_to_date.getText().toString());

        if(dateformatStart.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter start date", Toast.LENGTH_SHORT).show();
        }
        if(mbs_select_leave_type.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please select leave type",Toast.LENGTH_SHORT).show();
        }
        if(mbs_select_leave_type.getText().toString().trim().equalsIgnoreCase("Single Leave")){
            if(dateformatStart.equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select start_date", Toast.LENGTH_SHORT).show();}
            else {
                addLeaveApplyHit(dialog, dateformatStart, dateformaEnd); }
        }
        else {
            if(mbs_select_leave_type.getText().toString().trim().equalsIgnoreCase("Multi Leave")){
                if(dateformatStart.equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Please enter start date.",Toast.LENGTH_SHORT).show(); ;
                }
                if(dateformaEnd.equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Please enter end date",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(ac_sub_leave_type.getText().toString().trim().equalsIgnoreCase("Maternity")){
                        if (sessionManagement.getEmpGender().equalsIgnoreCase("Male")) {
                            Toast.makeText(getActivity(), "Male gender can't not apply for Maternity Leave", Toast.LENGTH_SHORT).show();
                        }
                        else {
                                addLeaveApplyHit(dialog, dateformatStart, dateformaEnd);
                             }
                         }
                         else if(ac_sub_leave_type.getText().toString().trim().equalsIgnoreCase("Paternity")){
                         if (sessionManagement.getEmpGender().equalsIgnoreCase("Female")) {
                            Toast.makeText(getActivity(), "Female gender can't not apply for Paternity Leave", Toast.LENGTH_SHORT).show();
                           }
                        else {
                            addLeaveApplyHit(dialog, dateformatStart, dateformaEnd);
                        }
                        }
                         else if(ac_sub_leave_type.getText().toString().trim().equalsIgnoreCase("Casual")){
                            addLeaveApplyHit(dialog, dateformatStart, dateformaEnd);
                          }
                      }
                }
            }
    }

    private void addLeaveApplyHit(Dialog dialog, String dateformatStart, String dateformalEnd) {
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject postedJson = new JsonObject();
            postedJson.addProperty("EmployeeID", sessionManagement.getemp_id());
            postedJson.addProperty("EndDate",dateformalEnd != null ? dateformalEnd : "null");
            postedJson.addProperty("HalfDay","");
            postedJson.addProperty("Reason",et_desc.getText().toString());
            postedJson.addProperty("StartDate",dateformatStart);
            postedJson.addProperty("Type", mbs_select_leave_type.getText().toString());
            postedJson.addProperty("ApprovalID", sessionManagement.getApprover_id());
            postedJson.addProperty("LeaveCode",ac_sub_leave_type.getText().toString().trim());

            Call<List<ApplyLeaveModel>> call = mAPIService.applyLeaveList(postedJson);
            call.enqueue(new Callback<List<ApplyLeaveModel>>() {
                @Override
                public void onResponse(Call<List<ApplyLeaveModel>> call, Response<List<ApplyLeaveModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<ApplyLeaveModel> list = response.body();
                            if (list!=null && list.size() > 0 && list.get(0).condition) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                onAppliedLeaveList();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), list.get(0).message, Toast.LENGTH_LONG).show();
                                progressDialogLoading.hideDialog();
                            }
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "submit_new_leave", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }
                @Override
                public void onFailure(Call<List<ApplyLeaveModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "submit_new_leave", getActivity());
                }
            });
        }else {
            Toast.makeText(getActivity(),"Please wait for internet connection.",Toast.LENGTH_SHORT).show();
        }
    }

    private void getLeaveTypeMaster(MaterialProgressBar materialProgressBar,String leave_type){
        if(NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            materialProgressBar.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<EmployeeLeaveMasterModel> call = mAPIService.getEmployeeLeaveMaster(leave_type);
            call.enqueue(new Callback<EmployeeLeaveMasterModel>() {
                @Override
                public void onResponse(Call<EmployeeLeaveMasterModel> call, Response<EmployeeLeaveMasterModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            materialProgressBar.setVisibility(View.GONE);
                            EmployeeLeaveMasterModel employeeLeaveMasterModel = response.body();
                            if (employeeLeaveMasterModel!=null && employeeLeaveMasterModel.condition) {
                               List<EmployeeLeaveMasterModel.Data> list_leave_type= employeeLeaveMasterModel.data;
                                if (list_leave_type!=null && list_leave_type.size() > 0) {
                                     leave_type_list= list_leave_type;
                                    SubLeaveTypeAdapter leaveTypeAdapter=new SubLeaveTypeAdapter(getActivity(),R.layout.android_item_view,leave_type_list);
                                    ac_sub_leave_type.setAdapter(leaveTypeAdapter);
                                } else {
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                materialProgressBar.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception e) {
                        materialProgressBar.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "leave_type_master", getActivity());
                    }
                }
                @Override
                public void onFailure(Call<EmployeeLeaveMasterModel> call, Throwable t) {
                    materialProgressBar.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "leave_type_master", getActivity());
                }
            });
        }
    }

    private String getLevaeDateDaysWise(String from_date, int leave_days ){
        if(from_date!=null && !from_date.equalsIgnoreCase("")) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date steepingdate = formatter.parse(from_date);
                Calendar ca = Calendar.getInstance();
                ca.setTime(steepingdate);
                ca.add(Calendar.DAY_OF_YEAR, leave_days);

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String newDate = dateFormat.format(ca.getTime());

                String[] parts = newDate.split("-");
                String dd = parts[0];
                String yy = parts[1];
                String mm=parts[2];
                Log.e("origional_date",yy+"-"+mm+"-"+dd);
                return newDate;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

}