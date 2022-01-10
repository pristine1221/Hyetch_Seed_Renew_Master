package com.example.pristineseed.ui.travel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelHeaderTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelInsertDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.travel.ta_da_model.TADAInsertModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.travel.addExpanse.AddTravelExpanseFragment;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTravelFragment extends Fragment {

    private Button submitPage;
    private TextInputEditText et_start_date, et_end_date, et_reason, et_expense_budget;
    private SessionManagement sessionManagement;
    private TextInputEditText to_city, from_city;
    private LinearLayout main_layout;
    private TextInputEditText ed_advance_tour_plan;
    private AutoCompleteTextView ac_type;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_travel_fragment, container, false);
    }

    private int mYear, mMonth, mDay;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        /*ItemAdapter type_adapter=new ItemAdapter(getActivity(),R.layout.item_view, Arrays.asList(CommonData.type));
        ac_type.setAdapter(type_adapter);*/
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        et_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                et_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        et_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                et_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        submitPage.setOnClickListener(view1 -> {
            getTravelHeader();
        });

    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        from_city = view.findViewById(R.id.from_city);
        et_reason = view.findViewById(R.id.et_reason);
//        et_expense_budget = view.findViewById(R.id.et_expense_budget);
        et_start_date = view.findViewById(R.id.et_start_date);
        et_end_date = view.findViewById(R.id.et_end_date);
        to_city = view.findViewById(R.id.to_city);
        main_layout = view.findViewById(R.id.main_layout);
        submitPage = view.findViewById(R.id.submitPage);
//         ed_advance_tour_plan=view.findViewById(R.id.ed_advance_tour_plan);
//         ac_type=view.findViewById(R.id.ac_type);
    }

    private boolean dateCompare() {
        String date = et_start_date.getText().toString().trim();
        String date1 = et_end_date.getText().toString().trim();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date datec = formatter.parse(date);
            Date date2 = formatter.parse(date1);

            if (datec.compareTo(date2) > 0) {
                Toast.makeText(getActivity(), "From Date Should Be Less Than To Date", Toast.LENGTH_LONG).show();
                return false;
            } else if (datec.compareTo(date2) < 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void getTravelHeader() {
        if (!dateCompare()) {
            Toast.makeText(getActivity(), "From Date Should Be Less Than To Date", Toast.LENGTH_LONG).show();
        } else if (et_start_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select  from date", Toast.LENGTH_LONG).show();
        } else if (et_end_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select to date", Toast.LENGTH_LONG).show();
        } else if (et_reason.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please fill the travel reason", Toast.LENGTH_LONG).show();
        } else if (to_city.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select tocity", Toast.LENGTH_LONG).show();
        } else if (from_city.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select fromcity ", Toast.LENGTH_LONG).show();
        } else {
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
            if (isNetwork) {
                NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                JsonObject postedJson = new JsonObject();
                postedJson.addProperty("email", sessionManagement.getUserEmail());
                postedJson.addProperty("from_loc", from_city.getText().toString().trim());
                postedJson.addProperty("to_loc", to_city.getText().toString().trim());
                postedJson.addProperty("start_date", DateTimeUtilsCustome.getDateYYMMDD(et_start_date.getText().toString()));
                postedJson.addProperty("end_date", DateTimeUtilsCustome.getDateYYMMDD(et_end_date.getText().toString()));
                postedJson.addProperty("travel_reson", et_reason.getText().toString());
                postedJson.addProperty("expense_budget", 0);
                postedJson.addProperty("approver_id", sessionManagement.getApprover_id());
                postedJson.addProperty("user_type", sessionManagement.getUser_type());
                postedJson.addProperty("adavanced_tour_plan", "");
                postedJson.addProperty("type", "");

                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                Call<List<TADAInsertModel>> call = mApiService.insertTADA(postedJson);
                Log.e("posted_json", new Gson().toJson(postedJson));
                call.enqueue(new Callback<List<TADAInsertModel>>() {
                    @Override
                    public void onResponse(Call<List<TADAInsertModel>> call, Response<List<TADAInsertModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<TADAInsertModel> tada_response_list = response.body();
                                if (tada_response_list.size() > 0 && tada_response_list.get(0).condition) {
                                    bindOfflineDataInsert(tada_response_list.get(0).travelcode, tada_response_list.get(0).created_on);
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), tada_response_list.size() > 0 ? tada_response_list.get(0).message : "Api not respoding.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_TA/DA", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TADAInsertModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_TA/DA", getActivity());
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void bindOfflineDataInsert(String event_code, String created_on) {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = successmessaePopupView.findViewById(R.id.order_no);
            order_no.setText(event_code);

            ImageView succesessImg = successmessaePopupView.findViewById(R.id.succesessImg);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.success_animation);
            succesessImg.startAnimation(animation);

            dialog.setContentView(successmessaePopupView);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.show();
            Button goBackFromItem = successmessaePopupView.findViewById(R.id.goBackFromItem);
            goBackFromItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAllField();
                    dialog.dismiss();
                }
            });
            successmessaePopupView.setFocusableInTouchMode(true);
            successmessaePopupView.requestFocus();
            successmessaePopupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearAllField() {
        et_end_date.setText("");
        et_end_date.clearFocus();
//        et_expense_budget.setText("");
//        et_expense_budget.clearFocus();
        et_start_date.setText("");
        et_start_date.clearFocus();
        et_reason.setText("");
        et_reason.clearFocus();
        from_city.setText("");
        from_city.clearFocus();
        to_city.setText("");
        to_city.clearFocus();
//        ac_type.setText("");
//        ed_advance_tour_plan.setText("");
        main_layout.requestFocus(View.SCROLL_INDICATOR_TOP);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("fetch next fragment", "true");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

