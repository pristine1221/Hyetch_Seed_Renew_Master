package com.example.pristineseed.ui.travel.addExpanse;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.City_master_Dao;
import com.example.pristineseed.DataBaseRepository.travel.ModeOfTravelMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.Mode_of_travel_master_Dao;
import com.example.pristineseed.DataBaseRepository.travel.TravelInsertDao;
import com.example.pristineseed.DataBaseRepository.travel.TravelLineExpenseTable;
import com.example.pristineseed.DataBaseRepository.travel.Travel_line_exapnce_Dao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomCurrentDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.GeoSetupModel.CountModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;
import com.example.pristineseed.model.travel.ta_da_model.ModeOfTravelModel;
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.example.pristineseed.model.travel.ta_da_model.TravelExpenseResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.TalukaAdapter;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.example.pristineseed.ui.adapter.item.CityAdapter;
import com.example.pristineseed.ui.travel.approveTravel.TDApproveFrament;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler.MenuMainPageFragment.viewPager;

public class AddTravelExpanseFragment extends Fragment implements TravelExpenseListAdapter.AddLineDeleteClickListner {
    private Button submitPage1;
    private RecyclerView expense_List;
    private Chip add_expenseButton;
    private TravelExpenseListAdapter travelExpenseListAdapter;
    private Chip add_expence_back_btn;
    private LinearLayout linear_vp_img;
    final static int SELECT_FILE = 1;
    private Chip clear_img;
    private TextView tv_from, tv_to_loc, tv_start_date, tv_end_date, tv_type, tv_expnse_budget, tv_addvance_tour_plan, tv_remrks, travel_header_no;
    Data.Builder data = new Data.Builder();

    public static AddTravelExpanseFragment newInstance() {
        return new AddTravelExpanseFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_travel_expanse_fragment, container, false);
    }

    boolean passBackFragmentData = false;
    private SessionManagement sessionManagement;
    private TravelHeaderModel submitTravelData = null;
    private List<SyncTravelDetailModel.Travel_line_Expense> line_expenseList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        intiView(view);
        if (NetworkUtil.getConnectivityStatusBoolean(getContext())) {

        }
        add_expence_back_btn.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });
        add_expenseButton.setOnClickListener(view1 -> {
            AddExpense();
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean passBackFragmentData = bundle.getBoolean("flag", false);
            submitTravelData = new Gson().fromJson(bundle.getString("passdata", ""), TravelHeaderModel.class);
            checkBackFragmentData(passBackFragmentData, submitTravelData);
        }
        /*submitPage.setOnClickListener(view1 -> {

        });*/

        if (submitTravelData.STATUS.equalsIgnoreCase("INSERT EXPENSE") || submitTravelData.STATUS.equalsIgnoreCase("APPROVED")) {
            add_expenseButton.setEnabled(false);
            submitPage1.setVisibility(View.GONE);
        } else if (submitTravelData.STATUS.equalsIgnoreCase("PENDING")) {
            submitPage1.setVisibility(View.GONE);
        } else if (submitTravelData.travel_line_expense != null && submitTravelData.travel_line_expense.size() > 0 && submitTravelData.travel_line_expense.get(0).line_no != null) {
            add_expenseButton.setEnabled(true);
            submitPage1.setVisibility(View.VISIBLE);
            finalSubmit(submitTravelData.travelcode);
        }

    }

    private void intiView(@NonNull View view) {
        sessionManagement = new SessionManagement(getActivity());
        add_expenseButton = view.findViewById(R.id.add_expenseButton);
        expense_List = view.findViewById(R.id.expense_List);
        add_expence_back_btn = view.findViewById(R.id.add_expence_back_btn);
        expense_List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        expense_List.setItemAnimator(new DefaultItemAnimator());
        expense_List.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        submitPage1 = view.findViewById(R.id.submitPage1);
        tv_from = view.findViewById(R.id.tv_from);
        tv_to_loc = view.findViewById(R.id.tv_to_loc);
        tv_start_date = view.findViewById(R.id.tv_start_date);
        tv_end_date = view.findViewById(R.id.tv_end_date);
        /*tv_type=view.findViewById(R.id.tv_type);
        tv_expnse_budget = view.findViewById(R.id.tv_expnse_budget);
        tv_addvance_tour_plan = view.findViewById(R.id.tv_addvance_tour_plan);*/
        tv_remrks = view.findViewById(R.id.tv_remrks);
        travel_header_no = view.findViewById(R.id.travel_header_no);

/*
        if(line_expenseList!=null && line_expenseList.size()>0 && line_expenseList.get(0).line_no!=null){
            submitPage.setVisibility(View.VISIBLE);
        }
        else {
            submitPage.setVisibility(View.GONE);
        }*/
    }

    boolean datedialog = false;
    private List<CityMasterModel> fromcityList = new ArrayList<>();
    private List<CityMasterModel> tocityList = new ArrayList<>();
    private CityMasterModel selectedFromCity = null;
    private CityMasterModel selectedToCity = null;
    private ModeOfTravelModel selectedModeOfTravelCost = null;

    private List<CityMasterModel.CityMaster> from_cityList = new ArrayList<>();
    private List<CityMasterModel.CityMaster> to_cityList = new ArrayList<>();
    private CityMasterModel.CityMaster selected_FromCity = null;
    private CityMasterModel selected_From_City = null;
    private CityMasterModel.CityMaster selected_ToCity = null;
    private ImageView vp_image;

    private String from_CityCode;
    private String to_CityCode;
    Button submitPage;

    public void AddExpense() {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View PopupView = inflater.inflate(R.layout.add_travel_expense_view, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            dialog.setContentView(PopupView);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.show();
            TextInputEditText et_date = PopupView.findViewById(R.id.et_date);
            Chip chip_close_dialog = PopupView.findViewById(R.id.chip_close_icon);
            AutoCompleteTextView from_city = PopupView.findViewById(R.id.from_city);
            AutoCompleteTextView to_city = PopupView.findViewById(R.id.to_city);
            TextInputEditText et_departure = PopupView.findViewById(R.id.et_departure);
            TextInputEditText et_arrival = PopupView.findViewById(R.id.et_arrival);
            TextInputEditText et_fare = PopupView.findViewById(R.id.et_fare);
            AutoCompleteTextView mode_of_travel_drop = PopupView.findViewById(R.id.mode_of_travel_drop);
            TextInputEditText et_loading_in_any = PopupView.findViewById(R.id.et_loading_in_any);
            TextInputEditText et_distance_km = PopupView.findViewById(R.id.et_distance_km);
            TextInputEditText et_fuel_vehicle_expance = PopupView.findViewById(R.id.et_fuel_vehicle_expance);
//            TextInputEditText et_daily_express = PopupView.findViewById(R.id.et_daily_express);
            TextInputEditText et_vehicle_repairing = PopupView.findViewById(R.id.et_vehicle_repairing);
            TextInputEditText et_local_convance = PopupView.findViewById(R.id.et_local_convance);
            TextInputEditText et_other_expenses = PopupView.findViewById(R.id.et_other_expenses);
            TextView tv_totalApplyExpenceAmmount = PopupView.findViewById(R.id.tv_totalApplyExpenceAmmount);
            TextView tv_travel_modelrate = PopupView.findViewById(R.id.tv_travel_modelrate);
            TextInputEditText ed_food_expences = PopupView.findViewById(R.id.ed_food_expences);
            TextInputEditText ed_pring_stationary = PopupView.findViewById(R.id.ed_pring_stationary);
            TextInputEditText ed_teliphone_expences = PopupView.findViewById(R.id.ed_teliphone_expences);
            TextInputEditText et_toll_tax_expense = PopupView.findViewById(R.id.et_toll_tax_expense);
            TextInputEditText et_courier_and_parcel = PopupView.findViewById(R.id.et_courier_and_parcel);
            ProgressBar insert_line_loading = PopupView.findViewById(R.id.insert_line_loading);
            chip_close_dialog.setOnClickListener(v -> {
                dialog.dismiss();
            });

//            TextView tv_maximum_Lodging = PopupView.findViewById(R.id.tv_maximum_Lodging);
//            TextView tv_full_da_amount = PopupView.findViewById(R.id.tv_full_da_amount);
//            TextView tv_half_da_amount = PopupView.findViewById(R.id.tv_half_da_amount);
//            TextView tv_time_diffrence = PopupView.findViewById(R.id.tv_time_diffrence);
            TextInputEditText et_lodging_cost = PopupView.findViewById(R.id.et_lodging_cost);
            TextInputEditText et_remarks = PopupView.findViewById(R.id.et_remarks);
            TextInputEditText et_from_km = PopupView.findViewById(R.id.et_from_km);
            TextInputEditText et_to_km = PopupView.findViewById(R.id.et_to_km);
            TextView tv_ttl_km = PopupView.findViewById(R.id.tv_ttl_km);
            TextView tv_rs_per_km = PopupView.findViewById(R.id.tv_rs_per_km);
            TextView tv_ttl_km_amnt = PopupView.findViewById(R.id.tv_ttl_km_amnt);
            LinearLayout linear_ttl_km = PopupView.findViewById(R.id.linear_ttl_km);
            LinearLayout linear_rs_per_km = PopupView.findViewById(R.id.linear_rs_per_km);
            LinearLayout linear_ttl_km_amt = PopupView.findViewById(R.id.linear_ttl_km_amt);
            LinearLayout linear_to_from_km = PopupView.findViewById(R.id.linear_to_from_km);
            vp_image = PopupView.findViewById(R.id.vp_image);
            clear_img = PopupView.findViewById(R.id.clear_img);
            Chip add_imge = PopupView.findViewById(R.id.add_imge);
            submitPage = PopupView.findViewById(R.id.submitPage);
            AutoCompleteTextView expense_type = PopupView.findViewById(R.id.expense_type);
            ProgressBar add_expence_content_loading = PopupView.findViewById(R.id.add_expence_content_loading);
            ItemAdapter expense_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.expense_type));
            expense_type.setAdapter(expense_adapter);

            getCityMaster(from_city, to_city);

            expense_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (expense_type.getText().toString().equalsIgnoreCase("Own Vehicle")) {
                        linear_to_from_km.setVisibility(View.VISIBLE);
                        linear_ttl_km.setVisibility(View.VISIBLE);
                        linear_ttl_km_amt.setVisibility(View.VISIBLE);
                        linear_rs_per_km.setVisibility(View.VISIBLE);
                        et_from_km.setText("");
                        et_to_km.setText("");
                        tv_ttl_km.setText("");
                        tv_ttl_km_amnt.setText("");
                    } else if (expense_type.getText().toString().equalsIgnoreCase("Company Vehicle")) {
                        linear_to_from_km.setVisibility(View.VISIBLE);
                        linear_ttl_km.setVisibility(View.VISIBLE);
                        linear_ttl_km_amt.setVisibility(View.GONE);
                        linear_rs_per_km.setVisibility(View.GONE);
                        et_from_km.setText("");
                        et_to_km.setText("");
                        tv_ttl_km.setText("");
                    }
                }
            });
            et_to_km.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    float from_km = Float.parseFloat(!et_from_km.getText().toString().equalsIgnoreCase("") ? et_from_km.getText().toString() : "0.0");
                    float to_km = Float.parseFloat(!et_to_km.getText().toString().equalsIgnoreCase("") ? et_to_km.getText().toString() : "0.0");
                    if (from_km > to_km) {
                        float differnce_input = (from_km - to_km);
                        tv_ttl_km.setText(String.valueOf(differnce_input));
                        tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                        tv_rs_per_km.setText("10");
                    } else if (from_km < to_km) {
                        float differnce_input = (to_km - from_km);
                        tv_ttl_km.setText(String.valueOf(differnce_input));
                        tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                        tv_rs_per_km.setText("10");
                    } else if (from_km == to_km) {
                        float differnce_input = (from_km - to_km);
                        tv_ttl_km.setText(String.valueOf(differnce_input));
                        tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                        tv_rs_per_km.setText("10");
                    }
                }
            });

            et_from_km.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!et_from_km.getText().toString().trim().equalsIgnoreCase("") && !et_to_km.getText().toString().trim().equalsIgnoreCase("")) {
                        float from_km = Float.parseFloat(et_from_km.getText().toString().trim());
                        float to_km = Float.parseFloat(et_to_km.getText().toString().trim());

                        if (from_km > to_km) {
                            float differnce_input = (from_km - to_km);
                            tv_ttl_km.setText(String.valueOf(differnce_input));
                            tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                            tv_rs_per_km.setText("10");
                        } else if (from_km < to_km) {
                            float differnce_input = (to_km - from_km);
                            tv_ttl_km.setText(String.valueOf(differnce_input));
                            tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                            tv_rs_per_km.setText("10");
                        } else if (from_km == to_km) {
                            float differnce_input = (to_km - from_km);
                            tv_ttl_km.setText(String.valueOf(differnce_input));
                            tv_ttl_km_amnt.setText(String.valueOf(differnce_input * 10));
                            tv_rs_per_km.setText("10");
                        }
                    }
                }
            });
            add_imge.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
            });

            et_date.setOnTouchListener((view1, motionEvent) -> {
                new CustomCurrentDatePicker(getActivity()).showDatePickerDialog(et_date);
                return true;
            });

          /*  from_city.setOnItemClickListener((adapterView, view, i, l) -> {
                selectedFromCity = fromcityList.get(i);
                from_city.setText(selectedFromCity.name);
                from_city.setError(null);
            });*/

            from_city.setOnItemClickListener((adapterView, view, i, l) -> {
                selected_FromCity = from_cityList.get(i);
                from_city.setText(selected_FromCity.City);
                from_city.setSelection(from_city.getText().length());
                from_CityCode = from_cityList.get(i).Code;
                from_city.setError(null);
            });


            to_city.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                to_CityCode = to_cityList.get(i).Code;
                if (to_CityCode.equals(from_CityCode)) {
                    Toast.makeText(getActivity(), "Can Not Select Same City.", Toast.LENGTH_SHORT).show();
                    to_city.setText("");
                } else if(!to_CityCode.equals(from_CityCode)){
                    selected_ToCity = to_cityList.get(i);
                    to_city.setText(selected_ToCity.City);
                    to_city.setSelection(to_city.getText().length());

                    //add_expence_content_loading.setVisibility(View.VISIBLE);
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<ModeOfTravelModel>> call = mAPIService.getModeOfTravel("getMode_of_travel", sessionManagement.getUserEmail(), selected_ToCity.City_Category, sessionManagement.getEmpGrade());

                    call.enqueue(new Callback<List<ModeOfTravelModel>>() {
                        @Override
                        public void onResponse(Call<List<ModeOfTravelModel>> call, Response<List<ModeOfTravelModel>> response) {
                            List<ModeOfTravelModel> modeOfTravelList = response.body();
                            if (modeOfTravelList != null && modeOfTravelList.size() > 0 && modeOfTravelList.get(0).condition) {
                                selectedModeOfTravelCost = modeOfTravelList.get(0);
//                            add_expence_content_loading.setVisibility(View.GONE);
                                if (selectedModeOfTravelCost != null) {
                                    ModeOftravelAdapter modeOftravelAdapter = new ModeOftravelAdapter(getActivity(), R.layout.drop_down_textview, modeOfTravelList);
                                    mode_of_travel_drop.setAdapter(modeOftravelAdapter);
//                                tv_maximum_Lodging.setText(Float.parseFloat(selectedModeOfTravelCost.lodging) != 0 ? String.valueOf(selectedModeOfTravelCost.lodging) : "0");
//                                tv_full_da_amount.setText(selectedModeOfTravelCost.da_full != 0 ? String.valueOf(selectedModeOfTravelCost.da_full) : "0");
//                                tv_half_da_amount.setText(selectedModeOfTravelCost.da_half != 0 ? String.valueOf(selectedModeOfTravelCost.da_half) : "0");
//                                tv_travel_modelrate.setText("Lodging " + selectedModeOfTravelCost.lodging + "And Half DA " + selectedModeOfTravelCost.da_half + " AND FULL DA " + selectedModeOfTravelCost.da_full);
                                    tv_travel_modelrate.setText("Lodging " + selectedModeOfTravelCost.lodging);
//                                to_city.setError(null);
                                } else {
                                    Toast.makeText(getActivity(), modeOfTravelList.get(0).message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                            add_expence_content_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), modeOfTravelList.size() > 0 ? "Data not found" : "Api error !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ModeOfTravelModel>> call, Throwable t) {
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_travel_mode", getActivity());
//                        add_expence_content_loading.setVisibility(View.GONE);
                        }
                    });

            /* PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
                try {
                    Mode_of_travel_master_Dao mode_of_travel_master_dao = pristineDatabase.mode_of_travel_master_dao();
                    selectedModeOfTravelCost = mode_of_travel_master_dao.fetchModeOfTravelNew(selectedToCity.class_of_city, "M1");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pristineDatabase.close();
                    pristineDatabase.destroyInstance();
                    if (selectedModeOfTravelCost != null) {
                        tv_maximum_Lodging.setText(Float.parseFloat(selectedModeOfTravelCost.lodging) != 0? String.valueOf(selectedModeOfTravelCost.lodging) : "0");
                        tv_full_da_amount.setText(selectedModeOfTravelCost.da_full != 0 ? String.valueOf(selectedModeOfTravelCost.da_full) : "0");
                        tv_half_da_amount.setText(selectedModeOfTravelCost.da_half != 0 ? String.valueOf(selectedModeOfTravelCost.da_half) : "0");
                        tv_travel_modelrate.setText("Lodging " + selectedModeOfTravelCost.lodging + "And Half DA " + selectedModeOfTravelCost.da_half+ " AND FULL DA " + selectedModeOfTravelCost.da_full);
                        to_city.setError(null);
                    }
                }*/

                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        Date date1 = format.parse(time1);
                        Date date2 = format.parse(time2);
                        float difference = Math.abs((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
                    /*if (difference >= 12) {
                        tv_time_diffrence.setText(difference + " >= 12 Full DA Apply");
                    } else if (difference >= 0) {
                        tv_time_diffrence.setText(difference + " < 12 Half DA Apply");
                    }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    to_city.setError(null);
                }

            });

            et_departure.setOnClickListener(view -> {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        (timePicker, hourOfDay, minute) -> {
                            et_departure.setText(hourOfDay + ":" + minute);
                            et_departure.setError(null);
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            });
            et_arrival.setOnClickListener(view -> {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        (timePicker, hourOfDay, minute) -> {
                            et_arrival.setText(hourOfDay + ":" + minute);
                            et_arrival.setError(null);
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            });

/*            getModeOfTravelMasterTable( mode_of_travel_drop, "", "M1");
            getModeOfTravelMasterTable(mode_of_travel_drop, selected_ToCity.City_Category, sessionManagement.getEmpGrade());*/


           /*   try {
                PristineDatabase db = PristineDatabase.getAppDatabase(getContext());
                Mode_of_travel_master_Dao mode_of_travel_master_dao = db.mode_of_travel_master_dao();
                List<String> mode_ofTravel = mode_of_travel_master_dao.getModeOfTravelDropDown(sessionManagement.getEmpGrade());
                db.close();
                db.destroyInstance();
                ModeOftravelAdapter modeOftravelAdapter = new ModeOftravelAdapter(getActivity(), R.layout.drop_down_textview, mode_ofTravel);
                mode_of_travel_drop.setAdapter(modeOftravelAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            tv_totalApplyExpenceAmmount.setText("0");

            et_fare.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = Math.abs((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));

                            /*if (difference >= 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference >= 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                        float lodging = 0.0f;
                        if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                            lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                        }

                       /* tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                daily_express + vehicle_repairing + local_convance + other_expenses +
                                lodging + appy_full_And_Half_DA));*/
                        tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                vehicle_repairing + local_convance + other_expenses +
                                lodging));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            et_loading_in_any.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                                Log.d("apply", String.valueOf(appy_full_And_Half_DA));
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                        float lodging = 0.0f;
                        if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                            lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                        }
                        tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                vehicle_repairing + local_convance + other_expenses +
                                lodging));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            et_arrival.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {
                        float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                        float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                        float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                        float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                        float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                        float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                        float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
                        float lodging_cost = et_lodging_cost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_lodging_cost.getText().toString());
                        float appy_full_And_Half_DA = 0;

                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = Math.abs((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
                          /*  if (difference >= 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                                tv_time_diffrence.setText(difference + " >= 12 Full DA Apply");
                            } else if (difference >= 0) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                                tv_time_diffrence.setText(difference + " < 12 Half DA Apply");
                            }*/
                        }

                        tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                vehicle_repairing + local_convance + other_expenses +
                                lodging_cost + appy_full_And_Half_DA));
                    } catch (Exception e) {
                    }
                }
            });

            et_departure.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                        float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                        float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                        float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                        float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                        float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                        float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
                        float lodging_cost = et_lodging_cost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_lodging_cost.getText().toString());
//                        float appy_full_And_Half_DA = 0;

                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = Math.abs((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
                            /*if (difference >= 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                                tv_time_diffrence.setText(difference + " >= 12 Full DA Apply");
                            } else if (difference >= 0) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                                tv_time_diffrence.setText(difference + " < 12 Half DA Apply");
                            }*/
                        }

                        tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                vehicle_repairing + local_convance + other_expenses +
                                lodging_cost));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            et_lodging_cost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float user_input_lodging_cost = et_lodging_cost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_lodging_cost.getText().toString());
                    try {
                        float lodging = 0.0f;
                        if (selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null) {
                            lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);

                            if (user_input_lodging_cost > lodging) {
                                et_lodging_cost.setText(String.valueOf(lodging));
                            }
                        }
                        float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                        float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                        float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                        float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                        float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                        float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                        float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
                        float lodging_cost = et_lodging_cost.getText().toString().equalsIgnoreCase("") ? 0 : Float.parseFloat(et_lodging_cost.getText().toString());
//                        float appy_full_And_Half_DA = 0;

                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = Math.abs((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
                           /* if (difference >= 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference >= 0) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }
                        tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                                vehicle_repairing + local_convance + other_expenses +
                                lodging_cost));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            et_fuel_vehicle_expance.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                           /* if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging));
//                     daily_express +
                }
            });
          /*  et_daily_express.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            daily_express + vehicle_repairing + local_convance + other_expenses +
                            lodging + appy_full_And_Half_DA));
                }
            });*/

            et_vehicle_repairing.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging));
                }
            });
            et_local_convance.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging));
                }
            });

            et_other_expenses.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging));
                }
            });

            ed_food_expences.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    float food_expense = Float.parseFloat(ed_food_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_food_expences.getText().toString().trim());
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging + food_expense));
                }
            });

            ed_pring_stationary.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    float printing_stationary_expnse = Float.parseFloat(ed_pring_stationary.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_pring_stationary.getText().toString().trim());
                    float food_expense = Float.parseFloat(ed_food_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_food_expences.getText().toString().trim());
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging + food_expense + printing_stationary_expnse));
                }

            });

            ed_teliphone_expences.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    float printing_stationary_expnse = Float.parseFloat(ed_pring_stationary.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_pring_stationary.getText().toString().trim());
                    float teliphn_expense = Float.parseFloat(ed_teliphone_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_teliphone_expences.getText().toString().trim());
                    float food_expense = Float.parseFloat(ed_food_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_food_expences.getText().toString().trim());
                    float fare = Float.parseFloat(et_fare.getText().toString().equalsIgnoreCase("") ? "0" : et_fare.getText().toString());
                    float loadging_in_any = Float.parseFloat(et_loading_in_any.getText().toString().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString());
                    float fuel_vehicle_expance = Float.parseFloat(et_fuel_vehicle_expance.getText().toString().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString());
//                    float daily_express = Float.parseFloat(et_daily_express.getText().toString().equalsIgnoreCase("") ? "0" : et_daily_express.getText().toString());
                    float vehicle_repairing = Float.parseFloat(et_vehicle_repairing.getText().toString().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString());
                    float local_convance = Float.parseFloat(et_local_convance.getText().toString().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString());
                    float other_expenses = Float.parseFloat(et_other_expenses.getText().toString().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString());
//                    float appy_full_And_Half_DA = 0;
                    try {
                        String time1 = et_departure.getText().toString();
                        String time2 = et_arrival.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        if (!time1.equalsIgnoreCase("") && !time2.equalsIgnoreCase("")) {
                            Date date1 = format.parse(time1);
                            Date date2 = format.parse(time2);
                            float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                            /*if (difference > 12) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_full;
                            } else if (difference > 8) {
                                appy_full_And_Half_DA = selectedModeOfTravelCost.da_half;
                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    float lodging = 0.0f;
                    if ((selectedModeOfTravelCost != null && selectedModeOfTravelCost.lodging != null)) {
                        lodging = Float.parseFloat(selectedModeOfTravelCost.lodging);
                    }
                    tv_totalApplyExpenceAmmount.setText(String.valueOf(fare + loadging_in_any + fuel_vehicle_expance +
                            vehicle_repairing + local_convance + other_expenses +
                            lodging + food_expense + printing_stationary_expnse + teliphn_expense));
                }
            });

            submitPage.setOnClickListener(view -> {
                if (et_date.getText().toString().equalsIgnoreCase("")) {
                    et_date.setError("Please Enter Date");
                    return;
                } else if (from_city.getText().toString().equalsIgnoreCase("")) {
                    from_city.setError("Please Select From City");
                    return;
                } else if (to_city.getText().toString().equalsIgnoreCase("")) {
                    to_city.setError("Please Select To City");
                    return;
                } else if (et_departure.getText().toString().equalsIgnoreCase("")) {
                    et_departure.setError("Please Enter Departure Time");
                    return;
                } else if (et_arrival.getText().toString().equalsIgnoreCase("")) {
                    et_arrival.setError("Please Enter Arrival Time");
                    return;
                } else if (et_fare.getText().toString().equalsIgnoreCase("")) {
                    et_fare.setError("Please Enter Fare");
                    return;
                } else if (mode_of_travel_drop.getText().toString().equalsIgnoreCase("")) {
                    mode_of_travel_drop.setError("Please Select Mode Of Travel");
                    return;
                }
                //todo bind submit
                String time1 = et_departure.getText().toString();
                String time2 = et_arrival.getText().toString();
                float da_full = 0, da_half = 0;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(time1);
                    date2 = format.parse(time2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                float difference = (date2.getTime() - date1.getTime()) / (1000 * 60);
                /*if (difference >= 12) {
                    da_full = selectedModeOfTravelCost.da_full;
                } else {
                    da_half = selectedModeOfTravelCost.da_half;
                }*/

                SyncTravelDetailModel.Travel_line_Expense travel_line_expense = new SyncTravelDetailModel().new Travel_line_Expense();
                travel_line_expense.travelcode = submitTravelData.travelcode;
                travel_line_expense.line_no = String.valueOf(line_expenseList.size() + 1);
                travel_line_expense.date = DateTimeUtilsCustome.splitDateInYYYMMDD(et_date.getText().toString().trim());
                travel_line_expense.from_loc = selected_FromCity.City.trim();
                travel_line_expense.to_loc = selected_ToCity.City.trim();
                travel_line_expense.departure = et_departure.getText().toString().trim();
                travel_line_expense.arrival = et_arrival.getText().toString().trim();
                travel_line_expense.fare = et_fare.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_fare.getText().toString().trim();
                travel_line_expense.mode_of_travel = mode_of_travel_drop.getText().toString().trim();
                travel_line_expense.loading_in_any = et_loading_in_any.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_loading_in_any.getText().toString().trim();
                travel_line_expense.distance_km = et_distance_km.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_distance_km.getText().toString().trim();
                travel_line_expense.fuel_vehicle_expance = et_fuel_vehicle_expance.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_fuel_vehicle_expance.getText().toString().trim();
                travel_line_expense.daily_express = "0";
                travel_line_expense.vehicle_repairing = et_vehicle_repairing.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_vehicle_repairing.getText().toString().trim();
                travel_line_expense.local_convance = et_local_convance.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_local_convance.getText().toString().trim();
                travel_line_expense.other_expenses = et_other_expenses.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_other_expenses.getText().toString().trim();
                travel_line_expense.total_amount_calulated = tv_totalApplyExpenceAmmount.getText().toString().trim();
                travel_line_expense.mod_city = selectedModeOfTravelCost.cities;
                travel_line_expense.mod_da_half = "";
                travel_line_expense.mode_da_full = "";
                travel_line_expense.mod_ope_max = String.valueOf(selectedModeOfTravelCost.ope_max);
                travel_line_expense.mod_lodging = String.valueOf(selectedModeOfTravelCost.lodging);
                travel_line_expense.user_grade = (selectedModeOfTravelCost.grade);
                travel_line_expense.expence_type = (expense_type.getText().toString());
                travel_line_expense.from_km = (et_from_km.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_from_km.getText().toString().trim());
                travel_line_expense.to_km = et_to_km.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_to_km.getText().toString().trim();
                travel_line_expense.total_km = tv_ttl_km.getText().toString().trim().equalsIgnoreCase("") ? "0" : tv_ttl_km.getText().toString().trim();
                travel_line_expense.total_km_amt = tv_ttl_km_amnt.getText().toString().trim().equalsIgnoreCase("") ? "0" : tv_ttl_km_amnt.getText().toString().trim();
                travel_line_expense.rupees_per_km = tv_rs_per_km.getText().toString().trim().equalsIgnoreCase("") ? "0" : tv_rs_per_km.getText().toString().trim();
                travel_line_expense.remarks = et_remarks.getText().toString().trim();
                travel_line_expense.food_expenses = ed_food_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_food_expences.getText().toString().trim();
                travel_line_expense.telephone_expenses = ed_teliphone_expences.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_teliphone_expences.getText().toString().trim();
                travel_line_expense.toll_tax_expense = et_toll_tax_expense.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_toll_tax_expense.getText().toString().trim();
                travel_line_expense.courier = et_courier_and_parcel.getText().toString().trim().equalsIgnoreCase("") ? "0" : et_courier_and_parcel.getText().toString().trim();
                travel_line_expense.printing_and_stationory = ed_pring_stationary.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_pring_stationary.getText().toString().trim();

                for (int r = 0; r < imageEncodList.size(); r++) {
                    SyncTravelDetailModel.Travel_Line_Attachments travel_line_attachments = new SyncTravelDetailModel().new Travel_Line_Attachments();
                    travel_line_attachments.attachment = imageEncodList.get(r);
                    travel_line_expense.travel_line_attachments.add(travel_line_attachments);
                }
                List<SyncTravelDetailModel.Travel_line_Expense> line_expenseList = new ArrayList<>();
                //call api......
                line_expenseList.add(travel_line_expense);
                submitLine(insert_line_loading, line_expenseList, dialog);
            });
            //You need to add the following line for this solution to work; thanks skayred
            PopupView.setFocusableInTouchMode(true);
            PopupView.requestFocus();
            PopupView.setOnKeyListener((view, keyCode, keyEvent) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    datedialog = false;
                    dialog.dismiss();
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletedTravelLines(int pos, String travelcode, String line_no) {
        if (!submitTravelData.STATUS.equalsIgnoreCase("INSERT EXPENSE") && !submitTravelData.STATUS.equalsIgnoreCase("APPROVED")) {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Confirm...")
                    .setMessage("Do you really want to delete this line?")
                    .setIcon(R.drawable.ic_baseline_delete_forever_24)
                    .setPositiveButton("Confirm", (dialogInterface, i1) -> {
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<ResponseModel>> call = mAPIService.deleteTravelLines(travelcode, line_no);
                        call.enqueue(new Callback<List<ResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                                List<ResponseModel> modeOfTravelList = response.body();
                                if (modeOfTravelList != null && modeOfTravelList.size() > 0 && modeOfTravelList.get(0).condition) {
                                    line_expenseList.remove(pos);
                                    travelExpenseListAdapter.notifyDataSetChanged();
                                    StaticMethods.showMDToast(getActivity(), modeOfTravelList.get(0).message, MDToast.TYPE_SUCCESS);
                                } else {
                                    Toast.makeText(getActivity(), modeOfTravelList.size() > 0 ? "Data not found" : "Api error !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_travel_mode", getActivity());
                            }
                        });

                    })
                    .setNegativeButton("Cancel", (dialogInterface, i1) -> {
                    })
                    .show();
        }
    }

    void bindLOcalDataBase() {
        travelExpenseListAdapter = new TravelExpenseListAdapter(getActivity(), line_expenseList);
        expense_List.setAdapter(travelExpenseListAdapter);
        travelExpenseListAdapter.setAddLineDeleteClickListner(this);
    }


    void getModeOfTravelMasterTable(AutoCompleteTextView ac_mode_of_travl, String class_of_city, String grade) {
//        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<ModeOfTravelModel>> call = mAPIService.getModeOfTravel("getMode_of_travel", sessionManagement.getUserEmail(), selected_ToCity.City_Category, sessionManagement.getEmpGrade());

        call.enqueue(new Callback<List<ModeOfTravelModel>>() {
            @Override
            public void onResponse(Call<List<ModeOfTravelModel>> call, Response<List<ModeOfTravelModel>> response) {
                List<ModeOfTravelModel> modeOfTravelList = response.body();
                if (modeOfTravelList != null && modeOfTravelList.size() > 0 && modeOfTravelList.get(0).condition) {
//                    content_loading.setVisibility(View.GONE);
                    ModeOftravelAdapter modeOftravelAdapter = new ModeOftravelAdapter(getActivity(), R.layout.drop_down_textview, modeOfTravelList);
                    ac_mode_of_travl.setAdapter(modeOftravelAdapter);
                } else {
//                    content_loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), modeOfTravelList.size() > 0 ? "Data not found" : "Api error !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModeOfTravelModel>> call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_travel_mode", getActivity());
//                content_loading.setVisibility(View.GONE);
            }
        });
    }

    /* private void insertModeOfTravelData(List<ModeOfTravelModel> modeOfTravelModelList) {
         PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
         try {
             Mode_of_travel_master_Dao mode_of_travel_master_dao = pristineDatabase.mode_of_travel_master_dao();
             for (int i = 0; i < modeOfTravelModelList.size(); i++) {
                 boolean isdataExist = mode_of_travel_master_dao.isDataExist(modeOfTravelModelList.get(i).id
                         , modeOfTravelModelList.get(i).grade, modeOfTravelModelList.get(i).cities);
                 if (isdataExist) {
                     mode_of_travel_master_dao.update(ModeOfTravelMasterTable.getModeOfTravelData(modeOfTravelModelList.get(i)));
                     Log.e("data_updated", "updated");
                 } else {
                     mode_of_travel_master_dao.insert(ModeOfTravelMasterTable.getModeOfTravelData(modeOfTravelModelList.get(i)));
                     Log.e("data_inserted", "insert");
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             pristineDatabase.close();
             pristineDatabase.destroyInstance();
         }
     }*/

    private List<String> imageEncodList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null) {
                imageEncodList = new ArrayList<String>();
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    String selectedFilePath = FilePath.getPath(getActivity(), mImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(selectedFilePath, options);
                    imageEncodList.add(selectedFilePath);
                    setImageBitmap(bm);
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            String selectedFilePath = imageEncodList.get(0);
            File file = new File(selectedFilePath);
            long file_size = file.length();
            long kb = file_size / 1024;
            long mb = kb / 1024;
            if (mb > 2) {
                clear_img.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "File size must be less than 2MB.", Toast.LENGTH_SHORT).show();
            } else if (imageEncodList.size() != 2) {
                vp_image.setImageBitmap(bitmap);
                clear_img.setVisibility(View.VISIBLE);
                clear_img.setOnClickListener(v -> {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle("Confirm...")
                            .setMessage("Do you really want to delete this Image?")
                            .setIcon(R.drawable.ic_baseline_delete_forever_24)
                            .setPositiveButton("Confirm", (dialogInterface, i1) -> {
                                vp_image.setImageBitmap(null);
                                imageEncodList.clear();
                                clear_img.setVisibility(View.GONE);
                            })
                            .setNegativeButton("Cancel", (dialogInterface, i1) -> {
                            }).show();
                });
            }
        }
    }

    private void checkBackFragmentData(boolean passBackFragmentData, TravelHeaderModel submitTravelData) {
        if (passBackFragmentData) {
            if (submitTravelData.travelcode != null && !submitTravelData.travelcode.equalsIgnoreCase("")) {
                travel_header_no.setText("(" + submitTravelData.travelcode + ")");
            }
            tv_from.setText(submitTravelData.from_loc);
            tv_to_loc.setText(submitTravelData.to_loc);
            tv_start_date.setText(submitTravelData.start_date);
            tv_end_date.setText(submitTravelData.end_date);
            tv_remrks.setText(submitTravelData.travel_reson);
//            tv_expnse_budget.setText(submitTravelData.expense_budget);
           /* if (submitTravelData.adavanced_tour_plan != null) {
                tv_addvance_tour_plan.setText(submitTravelData.adavanced_tour_plan);
            }*/

            if (submitTravelData.travel_line_expense != null && submitTravelData.travel_line_expense.size() > 0) {
                if (submitTravelData.travel_line_expense.get(0).line_no != null) {
                    line_expenseList = submitTravelData.travel_line_expense;
                    bindLOcalDataBase();
                } else {
                    expense_List.setAdapter(null);
                }
            }

            submitPage1.setOnClickListener(view -> {
                if (submitTravelData.STATUS.equalsIgnoreCase("CREATE REJECTED")) {
                    Snackbar.make(submitPage1, "You don't have Expense line So Go Back.", Snackbar.LENGTH_LONG).show();
                }

            });
        }
    }

    //todo final ta/da travel add in database....
    public void finalSubmit(String travelcode) {
        if (line_expenseList != null && line_expenseList.size() > 0 && line_expenseList.get(0).line_no != null) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.travelLineSubmit(travelcode);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseModel> modeOfTravelList = response.body();
                        if (modeOfTravelList != null && modeOfTravelList.size() > 0 && modeOfTravelList.get(0).condition) {
                            submitTravelData.STATUS = "INSERT EXPENSE";
                            getParentFragmentManager().popBackStack();
                            StaticMethods.showMDToast(getActivity(), modeOfTravelList.get(0).message, MDToast.TYPE_SUCCESS);
                        } else {
                            Toast.makeText(getActivity(), modeOfTravelList.size() > 0 ? "Data not found" : "Api error !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_travel_code", getActivity());
                }
            });

        }
    }

    private void getCityMaster(AutoCompleteTextView from_city, AutoCompleteTextView to_city) {
//        add_expence_content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<CityMasterModel> call = mAPIService.getCityMaster();
        call.enqueue(new Callback<CityMasterModel>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(Call<CityMasterModel> call, Response<CityMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        CityMasterModel cityMasterModels = response.body();
                        if (cityMasterModels != null && cityMasterModels.condition) {
                            from_cityList.clear();
                            to_cityList.clear();
                            from_cityList.addAll(cityMasterModels.data);
//                            to_cityList.addAll(cityMasterModels.data);
//                            from_cityList.addAll(cityMasterModels);
//                            to_cityList.addAll(cityMasterModels);
                            CityAdapter from_cityAdapter = new CityAdapter(getActivity(), R.layout.android_item_view, from_cityList);
                            from_city.setAdapter(from_cityAdapter);

                            from_cityList.remove(from_CityCode);
                            to_cityList.clear();
                            to_cityList.addAll(from_cityList);
                            CityAdapter to_cityAdapter = new CityAdapter(getActivity(), R.layout.android_item_view, to_cityList);
                            to_city.setAdapter(to_cityAdapter);
                        } else {
//                            add_expence_content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "City Master Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        add_expence_content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
//                    add_expence_content_loading.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "city_master_list", getActivity());
                } finally {
//                    add_expence_content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CityMasterModel> call, Throwable t) {
//                add_expence_content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "city_master_list", getActivity());
            }
        });
    }

    private void submitLine(ProgressBar insert_line_loading, List<SyncTravelDetailModel.Travel_line_Expense> temp_lineList, Dialog dialog) {
        try {
            boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
            if (networkUtil) {
                if (temp_lineList.size() > 0) {
                    insert_line_loading.setVisibility(View.VISIBLE);
                    JsonArray postedJason = new JsonArray();
                    for (int i = 0; i < temp_lineList.size(); i++) {
                        SyncTravelDetailModel.Travel_line_Expense data = temp_lineList.get(i);
                        JsonObject temp_json = new JsonObject();
                        temp_json.addProperty("travelcode", data.travelcode);
                        temp_json.addProperty("line_no", Integer.parseInt(data.line_no));
                        temp_json.addProperty("date", data.date);
                        temp_json.addProperty("from_loc", data.from_loc);
                        temp_json.addProperty("to_loc", data.to_loc);
                        temp_json.addProperty("departure", data.departure);
                        temp_json.addProperty("arrival", data.arrival);
                        temp_json.addProperty("fare", data.fare);
                        temp_json.addProperty("mode_of_travel", data.mode_of_travel);
                        temp_json.addProperty("loading_in_any", data.loading_in_any);
                        temp_json.addProperty("distance_km", data.distance_km);
                        temp_json.addProperty("fuel_vehicle_expance", data.fuel_vehicle_expance);
                        temp_json.addProperty("daily_express", data.daily_express);
                        temp_json.addProperty("vehicle_repairing", data.vehicle_repairing);
                        temp_json.addProperty("local_convance", data.local_convance);
                        temp_json.addProperty("other_expenses", data.other_expenses);
                        temp_json.addProperty("total_amount_calulated", data.total_amount_calulated);
                        temp_json.addProperty("mod_city", data.mod_city);
                        temp_json.addProperty("mod_lodging", data.mod_lodging);
                        temp_json.addProperty("mod_da_half", "0");
                        temp_json.addProperty("mode_da_full", "0");
                        temp_json.addProperty("mod_ope_max", data.mod_ope_max);
                        temp_json.addProperty("user_grade", data.user_grade);
                        temp_json.addProperty("expence_type", data.expence_type);
                        temp_json.addProperty("from_km", data.from_km);
                        temp_json.addProperty("to_km", data.to_km);
                        temp_json.addProperty("total_km", data.total_km);
                        temp_json.addProperty("total_km_amt", data.total_amount_calulated);
                        temp_json.addProperty("rupees_per_km", data.rupees_per_km);
                        temp_json.addProperty("remarks", data.remarks);
                        temp_json.addProperty("food_expenses", data.food_expenses);
                        temp_json.addProperty("printing_and_stationory", data.printing_and_stationory);
                        temp_json.addProperty("telephone_expenses", data.telephone_expenses);
                        temp_json.addProperty("toll_tax_expense", data.toll_tax_expense);
                        temp_json.addProperty("courier", data.courier);

                        JsonArray jsonArray = new JsonArray();

                        for (int file = 0; file < data.travel_line_attachments.size(); file++) {
                            Bitmap bm = BitmapFactory.decodeFile(data.travel_line_attachments.get(file).attachment);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            int streamLength = 100 * 1024;
                            byte[] bmpPicByteArray = baos.toByteArray();
                            streamLength = bmpPicByteArray.length;
                            Log.e("compress_size", String.valueOf(streamLength));
                            byte[] byteArrayImage = baos.toByteArray();
                            String imge_string = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                            jsonArray.add(imge_string);
                        }
                        temp_json.add("attachment12", jsonArray);
                        postedJason.add(temp_json);
                    }
                    // call insert api...........
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<SyncTravelDetailModel.Travel_line_Expense>> call = mAPIService.insertTravelExpenceResponseModel(postedJason);
                    call.enqueue(new Callback<List<SyncTravelDetailModel.Travel_line_Expense>>() {
                        @Override
                        public void onResponse(Call<List<SyncTravelDetailModel.Travel_line_Expense>> call, Response<List<SyncTravelDetailModel.Travel_line_Expense>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    insert_line_loading.setVisibility(View.GONE);
                                    List<SyncTravelDetailModel.Travel_line_Expense> server_response_list = response.body();
                                    if (server_response_list != null && server_response_list.size() > 0 && server_response_list.get(0).condition) {
                                        line_expenseList.clear();
                                        line_expenseList.addAll(server_response_list);
                                        bindLOcalDataBase();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), server_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        insert_line_loading.setVisibility(View.GONE);
                                        Snackbar.make(submitPage, server_response_list.size() > 0 ? server_response_list.get(0).message : "Record Not inserted.", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", view -> {
                                        }).show();
                                    }
                                } else {
                                    insert_line_loading.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                insert_line_loading.setVisibility(View.GONE);
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_travel_expence", getActivity());
                            } finally {
                                insert_line_loading.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SyncTravelDetailModel.Travel_line_Expense>> call, Throwable t) {
                            insert_line_loading.setVisibility(View.GONE);
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_travel_expence", getActivity());
                        }
                    });

                } else {
                    insert_line_loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Please add expence first", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("exception_submit_travel", e.getMessage());
        }
    }

    @Override
    public void onClick(int pos, SyncTravelDetailModel.Travel_line_Expense line_expense) {
        deletedTravelLines(pos, line_expense.travelcode, line_expense.line_no);
    }
}


