package com.example.pristineseed.ui.tfa_summary.createTFA;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.tfa.CreateTFADao;
import com.example.pristineseed.DataBaseRepository.tfa.TFA_Header_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.tfa.CreateTFAHeaderModel;
import com.example.pristineseed.model.tfa.FAInsertLineResponseModel;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.model.tfa.TFAResponseModel;
import com.example.pristineseed.model.travel.travel_view_header.TravelHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.StateMasterAdapter;
import com.example.pristineseed.ui.tfa_summary.addTFAExpanse.TFAddExpenceFragment;
import com.example.pristineseed.ui.travel.addExpanse.AddTravelExpanseFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTFASummaryFragment extends Fragment {

    private AutoCompleteTextView dropdown_district, dropdown_state;
    private TextInputEditText et_name, et_place, et_target_of_crop, et_joining_date, et_discnt_date, et_adhar_card, et_month_salary, et_bank_name,
            et_account_no, et_ifsc_code, et_mobile_no;
    private Button submitPage, next_btn;

    private Chip add_expence;

    private SessionManagement sessionManagement;
    private LinearLayout main_layout;
    private TFAHeaderModel tfaHeaderModel = null;
    private DistricMasterTable districMasterName = null;
    private List<StateMasterTable> stateMasterListName = null;
    private String district_code = "", state_code = "";
    private Button update_btn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_tfa_summary_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        setInitialViews(view);
        bindResponseWithLocalOrServer("InitialCall");

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                String flag = bundle.getString("flag", "");
                tfaHeaderModel = new Gson().fromJson(bundle.getString("passdata", ""), TFAHeaderModel.class);
                checkBackFragmentData(tfaHeaderModel, flag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        et_joining_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(et_joining_date);
            return true;
        });

        et_discnt_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(et_discnt_date);
            return true;
        });

        dropdown_district.setOnItemClickListener((parent, view1, position, id) -> {
            if(districMasterTableList!=null && districMasterTableList.size()>0) {
                districMasterTable = districMasterTableList.get(position);
                if (districMasterTable != null) {
                    district_code = districMasterTable.getCode();
                }
            }
        });

        dropdown_state.setOnItemClickListener((parent, view1, position, id) -> {
            districMasterTableList.clear();
            bindDistrict(stateMasterTableList.get(position).getCode());
            stateMasterTable = stateMasterTableList.get(position);
            if (stateMasterTable != null) {
                state_code = stateMasterTable.getCode();
                dropdown_state.setText(stateMasterTable.getName());
                dropdown_state.setSelection(dropdown_state.getText().length());
                dropdown_state.setError(null);
                dropdown_district.setText("");
            }
        });

        submitPage.setOnClickListener(v -> {
            getTfaHeader();
        });
    }


    private void setInitialViews(@NonNull View view) {
        dropdown_district = view.findViewById(R.id.dropdown_district);
        dropdown_state = view.findViewById(R.id.dropdown_state);
        et_name = view.findViewById(R.id.et_name);
        et_place = view.findViewById(R.id.et_place);
        et_target_of_crop = view.findViewById(R.id.et_target_of_crop);
        et_joining_date = view.findViewById(R.id.et_joining_date);
        et_discnt_date = view.findViewById(R.id.et_discnt_date);
        et_adhar_card = view.findViewById(R.id.et_adhar_card);
        et_month_salary = view.findViewById(R.id.et_month_salary);
        et_bank_name = view.findViewById(R.id.et_bank_name);
        et_account_no = view.findViewById(R.id.et_account_no);
        et_ifsc_code = view.findViewById(R.id.et_ifsc_code);
        et_mobile_no = view.findViewById(R.id.et_mobile_no);
        main_layout = view.findViewById(R.id.main_layout);

        submitPage = view.findViewById(R.id.submitPage);
        add_expence = view.findViewById(R.id.add_expence);
        next_btn = view.findViewById(R.id.next_btn);
        update_btn=view.findViewById(R.id.update_btn);

    }

    private void getTfaHeader() {
        if (et_name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please fill name of TFA", Toast.LENGTH_SHORT).show();
        } else if (dropdown_district.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select district", Toast.LENGTH_SHORT).show();
        } else if (dropdown_state.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
        } else if (et_discnt_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select discount date", Toast.LENGTH_SHORT).show();
        } else if (et_joining_date.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select joining date", Toast.LENGTH_SHORT).show();
        } else if (et_adhar_card.getText().toString().length() != 12) {
            Toast.makeText(getActivity(), "Please select correct aadhar no.", Toast.LENGTH_SHORT).show();
        } else {
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            JsonObject posted_Json = new JsonObject();
            posted_Json.addProperty("name", et_name.getText().toString());
            posted_Json.addProperty("place", et_place.getText().toString());
            posted_Json.addProperty("district", districMasterTable.getName() != null ? districMasterTable.getName() : "");
            posted_Json.addProperty("state", stateMasterTable.getName() != null ? stateMasterTable.getName() : "");
            posted_Json.addProperty("target_of_all_crop", et_target_of_crop.getText().toString());
            posted_Json.addProperty("date_of_joining", DateTimeUtilsCustome.getDateYYMMDD(et_joining_date.getText().toString()));
            posted_Json.addProperty("date_of_discontinue", DateTimeUtilsCustome.getDateYYMMDD(et_discnt_date.getText().toString()));
            posted_Json.addProperty("aadhaar_card", et_adhar_card.getText().toString());
            posted_Json.addProperty("month_salary", et_month_salary.getText().toString());
            posted_Json.addProperty("bank_name", et_bank_name.getText().toString());
            posted_Json.addProperty("account_no", et_account_no.getText().toString());
            posted_Json.addProperty("ifsc_code", et_ifsc_code.getText().toString());
            posted_Json.addProperty("mobile_no", et_mobile_no.getText().toString());
            posted_Json.addProperty("created_by", sessionManagement.getUserEmail());
            posted_Json.addProperty("approver_id_1", sessionManagement.getApprover_id());
            if (isNetwork) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.showLoadingDialog(getActivity());
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<TFAResponseModel>> call = mAPIService.getTfaHeader(posted_Json);
                call.enqueue(new Callback<List<TFAResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<TFAResponseModel>> call, Response<List<TFAResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                loadingDialog.hideDialog();
                                List<TFAResponseModel> createTFAModelList = response.body();
                                if (createTFAModelList.size() > 0 && createTFAModelList.get(0).condition) {
                                    bindAllOfflineInsertData(createTFAModelList.get(0).tfa_code, createTFAModelList.get(0).created_on);
                                    Toast.makeText(getActivity(), createTFAModelList.get(0).message, Toast.LENGTH_LONG).show();
                                } else {
                                    loadingDialog.hideDialog();
                                    Toast.makeText(getActivity(), createTFAModelList.size() > 0 ? createTFAModelList.get(0).message : "Api not responding.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                loadingDialog.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ".Error response.", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            loadingDialog.hideDialog();
                            e.printStackTrace();
                        } finally {
                            loadingDialog.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TFAResponseModel>> call, Throwable t) {
                        loadingDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_TFA_header", getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(),"Please check online connection",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void bindAllOfflineInsertData(String tfaCode, String created_on) {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = successmessaePopupView.findViewById(R.id.order_no);
            order_no.setText(tfaCode);

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
                    clearAllData();
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
        }
    }

    private void clearAllData() {
        dropdown_district.setText("");
        dropdown_district.clearFocus();
        dropdown_state.setText("");
        dropdown_state.clearFocus();
        et_name.setText("");
        et_name.clearFocus();
        et_place.setText("");
        et_place.clearFocus();
        et_target_of_crop.setText("");
        et_target_of_crop.clearFocus();
        et_joining_date.setText("");
        et_joining_date.clearFocus();
        et_discnt_date.setText("");
        et_discnt_date.clearFocus();
        et_adhar_card.setText("");
        et_adhar_card.clearFocus();
        et_month_salary.setText("");
        et_month_salary.clearFocus();
        et_bank_name.setText("");
        et_bank_name.clearFocus();
        et_account_no.setText("");
        et_account_no.clearFocus();
        et_ifsc_code.setText("");
        et_ifsc_code.clearFocus();
        et_mobile_no.setText("");
        et_mobile_no.clearFocus();
        main_layout.requestFocus(View.SCROLL_INDICATOR_TOP);
    }

    private List<DistricMasterTable> districMasterTableList = new ArrayList<>();
    private List<StateMasterTable> stateMasterTableList = new ArrayList<>();

    private void bindResponseWithLocalOrServer(String actionOfFlag) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            if (actionOfFlag.equalsIgnoreCase("InitialCall")){
                districMasterTableList = districMasterDao.getAllData();
                stateMasterTableList = stateMasterDao.getAllData();
             }
            else if(actionOfFlag.equalsIgnoreCase("passBackCall")){
                if (tfaHeaderModel != null) {
                    districMasterName = districMasterDao.getDistName(tfaHeaderModel.district);
                    stateMasterListName = stateMasterDao.getStateName(tfaHeaderModel.state);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            PristineDatabase.destroyInstance();
            bindDropDown();
        }
    }

    private DistricMasterTable districMasterTable = null;
    private StateMasterTable stateMasterTable = null;

    private void bindDropDown() {
        /*DistrictAdapter disctric_adapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
        dropdown_district.setAdapter(disctric_adapter);*/
        StateMasterAdapter state_adapter = new StateMasterAdapter(getActivity(), R.layout.drop_down_textview, stateMasterTableList);
        dropdown_state.setAdapter(state_adapter);

    }

    void bindDistrict(String state_code){
        districMasterTableList = getdistricMasterTableList(state_code);
        if (getdistricMasterTableList(state_code)!= null && getdistricMasterTableList(state_code).size()>0
                && districMasterTableList != null && districMasterTableList.size()>0) {
            DistrictAdapter disctric_adapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
            dropdown_district.setAdapter(disctric_adapter);
        }
        /*else {
            Toast.makeText(getActivity(), "No Record Found On "+state_code, Toast.LENGTH_SHORT).show();
        }*/
    }

    private List<DistricMasterTable> getdistricMasterTableList(String state_code) {

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            DistricMasterDao zoneMaterDao = pristineDatabase.districMasterDao();
            districMasterTableList = zoneMaterDao.fetch_byGeograficalStateCode(state_code);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }
        return districMasterTableList;

    }


    private void checkBackFragmentData(TFAHeaderModel submitTFAData, String flag) {
        if (flag.equalsIgnoreCase("Pending")) {
            next_btn.setVisibility(View.VISIBLE);
            submitPage.setVisibility(View.GONE);
            dropdown_district.setText(submitTFAData.district);
            dropdown_district.clearFocus();
            dropdown_district.setEnabled(false);
            dropdown_district.setFocusable(false);
            dropdown_district.setFocusableInTouchMode(false);
            dropdown_state.setText(submitTFAData.state);
            dropdown_state.clearFocus();
            dropdown_state.setEnabled(false);
            dropdown_state.setFocusable(false);
            dropdown_state.setFocusableInTouchMode(false);
            et_name.setText(submitTFAData.name);
            et_name.setEnabled(false);
            et_name.setFocusable(false);
            et_name.clearFocus();
            et_place.setText(submitTFAData.place);
            et_place.setFocusable(false);
            et_place.setEnabled(false);
            et_place.setFocusableInTouchMode(false);
            et_place.clearFocus();
            et_target_of_crop.setText(submitTFAData.target_of_all_crop);
            et_target_of_crop.setFocusable(false);
            et_target_of_crop.setEnabled(false);
            et_target_of_crop.clearFocus();
            et_joining_date.setText(submitTFAData.date_of_joining);
            et_joining_date.setEnabled(false);
            et_joining_date.setFocusable(false);
            et_joining_date.setFocusableInTouchMode(false);
            et_joining_date.clearFocus();
            et_discnt_date.setText(submitTFAData.date_of_discontinue);
            et_discnt_date.setEnabled(false);
            et_discnt_date.setFocusable(false);
            et_discnt_date.clearFocus();
            et_adhar_card.setText(submitTFAData.aadhaar_card);
            et_adhar_card.setEnabled(false);
            et_adhar_card.setFocusable(false);
            et_adhar_card.setFocusableInTouchMode(false);
            et_adhar_card.clearFocus();
            et_month_salary.setText(submitTFAData.month_salary);
            et_month_salary.setEnabled(false);
            et_month_salary.setFocusable(false);
            et_month_salary.setFocusableInTouchMode(false);
            et_month_salary.clearFocus();
            et_bank_name.setText(submitTFAData.bank_name);
            et_bank_name.setFocusable(false);
            et_bank_name.setEnabled(false);
            et_bank_name.setFocusableInTouchMode(false);
            et_bank_name.clearFocus();
            et_account_no.setText(submitTFAData.account_no);
            et_account_no.setEnabled(false);
            et_account_no.setFocusable(false);
            et_account_no.setFocusableInTouchMode(false);
            et_account_no.clearFocus();
            et_ifsc_code.setText(submitTFAData.ifsc_code);
            et_ifsc_code.setEnabled(false);
            et_ifsc_code.setFocusable(false);
            et_ifsc_code.setFocusableInTouchMode(false);
            et_ifsc_code.clearFocus();
            et_mobile_no.setText(submitTFAData.mobile_no);
            et_ifsc_code.setEnabled(false);
            et_ifsc_code.setFocusable(false);
            et_ifsc_code.setFocusableInTouchMode(false);
            et_mobile_no.clearFocus();
            next_btn.setOnClickListener(view -> {
                if (submitTFAData.status.equalsIgnoreCase("Reject")) {
                    Snackbar.make(next_btn, "You don't have Expense line So Go Back.", Snackbar.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", "insert_line");
                    bundle.putString("dataPass", new Gson().toJson(submitTFAData));
                    TFAddExpenceFragment tfAddExpenceFragment = new TFAddExpenceFragment();
                    tfAddExpenceFragment.setArguments(bundle);
                    StaticMethods.loadFragmentsWithBackStack(getActivity(), tfAddExpenceFragment, "Add_tfa_expense");
                }
            });
            if (submitTFAData.status.equalsIgnoreCase("Insert Expense")) {
                next_btn.setVisibility(View.GONE);
            }
        }

        else if (flag.equalsIgnoreCase("Reject")) {
            bindResponseWithLocalOrServer("passBackCall");
            update_btn.setVisibility(View.VISIBLE);
            submitPage.setVisibility(View.GONE);
            if (districMasterName != null) {
                district_code = districMasterName.getCode();
                dropdown_district.setText(districMasterName.getName());
            }
            if (stateMasterListName != null && stateMasterListName.size() > 0) {
                state_code = stateMasterListName.get(0).getCode();
                dropdown_state.setText(stateMasterListName.get(0).getName());
            }
            et_name.setText(submitTFAData.name);
            et_place.setText(submitTFAData.place);
            et_target_of_crop.setText(submitTFAData.target_of_all_crop);
            if (submitTFAData.date_of_joining != null) {
                et_joining_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(submitTFAData.date_of_joining));
            }
            et_adhar_card.setText(submitTFAData.aadhaar_card);
            et_month_salary.setText(submitTFAData.month_salary);
            et_bank_name.setText(submitTFAData.bank_name);
            et_account_no.setText(submitTFAData.account_no);
            et_ifsc_code.setText(submitTFAData.ifsc_code);
            et_mobile_no.setText(submitTFAData.mobile_no);
            next_btn.setVisibility(View.VISIBLE);
            submitPage.setVisibility(View.VISIBLE);
            if (submitTFAData.date_of_discontinue != null) {
                et_discnt_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(submitTFAData.date_of_discontinue));
            }
            update_btn.setOnClickListener(v -> {
            if (et_name.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please fill name of TFA", Toast.LENGTH_SHORT).show();
            } else if (dropdown_district.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select district", Toast.LENGTH_SHORT).show();
            } else if (dropdown_state.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
            } else if (et_discnt_date.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select discount date", Toast.LENGTH_SHORT).show();
            } else if (et_joining_date.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select joining date", Toast.LENGTH_SHORT).show();
            } else if (et_adhar_card.getText().toString().length() != 12) {
                Toast.makeText(getActivity(), "Please select correct aadhar no.", Toast.LENGTH_SHORT).show();
            } else {
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                JsonObject posted_Json = new JsonObject();
                posted_Json.addProperty("tfa_code",submitTFAData.tfa_code);
                posted_Json.addProperty("name", et_name.getText().toString());
                posted_Json.addProperty("place", et_place.getText().toString());
                posted_Json.addProperty("district", district_code != null ? district_code : "");
                posted_Json.addProperty("state", state_code != null ? state_code : "");
                posted_Json.addProperty("target_of_all_crop", et_target_of_crop.getText().toString());
                posted_Json.addProperty("date_of_joining", DateTimeUtilsCustome.getDateYYMMDD(et_joining_date.getText().toString()));
                posted_Json.addProperty("date_of_discontinue", DateTimeUtilsCustome.getDateYYMMDD(et_discnt_date.getText().toString()));
                posted_Json.addProperty("aadhaar_card", et_adhar_card.getText().toString());
                posted_Json.addProperty("month_salary", et_month_salary.getText().toString());
                posted_Json.addProperty("bank_name", et_bank_name.getText().toString());
                posted_Json.addProperty("account_no", et_account_no.getText().toString());
                posted_Json.addProperty("ifsc_code", et_ifsc_code.getText().toString());
                posted_Json.addProperty("mobile_no", et_mobile_no.getText().toString());
                posted_Json.addProperty("created_by", sessionManagement.getUserEmail());
                posted_Json.addProperty("approver_id_1", sessionManagement.getApprover_id());
                if (isNetwork) {
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.showLoadingDialog(getActivity());
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<FAInsertLineResponseModel>> call = mAPIService.updateTfaHeader(posted_Json);
                    call.enqueue(new Callback<List<FAInsertLineResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<FAInsertLineResponseModel>> call, Response<List<FAInsertLineResponseModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    loadingDialog.hideDialog();
                                    List<FAInsertLineResponseModel> createTFAModelList = response.body();
                                    if (createTFAModelList!=null && createTFAModelList.size() > 0 && createTFAModelList.get(0).condition) {
                                        Toast.makeText(getActivity(),createTFAModelList.get(0).message,Toast.LENGTH_SHORT).show();

                                     submitTFAData.tfa_code=createTFAModelList.get(0).tfa_code;
                                     submitTFAData.name=createTFAModelList.get(0).name;
                                     submitTFAData.place=createTFAModelList.get(0).place;
                                     submitTFAData.district=createTFAModelList.get(0).district;
                                     submitTFAData.state=createTFAModelList.get(0).state;
                                     submitTFAData.target_of_all_crop=createTFAModelList.get(0).target_of_all_crop;
                                     submitTFAData.date_of_joining=createTFAModelList.get(0).date_of_joining;
                                     submitTFAData.date_of_discontinue=createTFAModelList.get(0).date_of_discontinue;
                                     submitTFAData.aadhaar_card=createTFAModelList.get(0).aadhaar_card;
                                     submitTFAData.month_salary=createTFAModelList.get(0).month_salary;
                                     submitTFAData.bank_name=createTFAModelList.get(0).bank_name;
                                     submitTFAData.account_no=createTFAModelList.get(0).account_no;
                                     submitTFAData.ifsc_code=createTFAModelList.get(0).ifsc_code;
                                     submitTFAData.mobile_no=createTFAModelList.get(0).mobile_no;
                                     submitTFAData.status=createTFAModelList.get(0).status;
                                     submitTFAData.reason=createTFAModelList.get(0).reason;

                                    } else {
                                        loadingDialog.hideDialog();
                                        Toast.makeText(getActivity(), createTFAModelList.size() > 0 ? createTFAModelList.get(0).message : "Api not responding.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    loadingDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ".Error response.", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                loadingDialog.hideDialog();
                                e.printStackTrace();
                            } finally {
                                loadingDialog.hideDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<FAInsertLineResponseModel>> call, Throwable t) {
                            loadingDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Update_TFA_header", getActivity());
                        }
                    });
                }
            }
            });
            next_btn.setOnClickListener(view -> {
                if (submitTFAData.status.equalsIgnoreCase("Reject")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", "update_line");
                    bundle.putString("dataPass", new Gson().toJson(submitTFAData));
                    TFAddExpenceFragment tfAddExpenceFragment = new TFAddExpenceFragment();
                    tfAddExpenceFragment.setArguments(bundle);
                    StaticMethods.loadFragmentsWithBackStack(getActivity(), tfAddExpenceFragment, "Add_tfa_expense");
                }
            });
        }
    }
}
