package com.example.pristineseed.ui.vendor;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.TspModel.TrialSeedModel;
import com.example.pristineseed.model.TspModel.TspResponseModel;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;
import com.example.pristineseed.model.home.OrderList;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.PM_TSP_ListAdapter;
import com.example.pristineseed.ui.adapter.item.AreaAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PearlMilletTrialSeedProductionFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Chip add_newItem;
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<TrialSeedModel> trialSeedModelList;
    private List<UserLocationModel> locationMasterTableList = null;
    private List<Hybrid_Item_Table> hybrid_item_tableList = null;
    private List<ZoneMasterTable> zoneMasterTableList = null;
    private List<AreaMasterTable> areaMasterTableList = null;
    private String location_code = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pm_tsp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getContext());
        listview = view.findViewById(R.id.listview);
        add_newItem = view.findViewById(R.id.add_newItem);
        getTrialSeedProduction();
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup();
        });
    }


    private void getTrialSeedProduction() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<TrialSeedModel>> call = mAPIService.getTspList(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<TrialSeedModel>>() {
                @Override
                public void onResponse(Call<List<TrialSeedModel>> call, Response<List<TrialSeedModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<TrialSeedModel> trialSeedGetModelList = response.body();
                            if (trialSeedGetModelList != null && trialSeedGetModelList.size() > 0 && trialSeedGetModelList.get(0).condition) {
                                trialSeedModelList = trialSeedGetModelList;
                                bindDataWithAdapter(trialSeedModelList);
                            } else {
                                Toast.makeText(getActivity(), trialSeedModelList != null && trialSeedModelList.size() > 0 ? "No data found POG" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getTsp_list", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<TrialSeedModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getTsp_list", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDataWithAdapter(List<TrialSeedModel> trialSeedModelList) {
        PM_TSP_ListAdapter approvalAdapter = new PM_TSP_ListAdapter(getActivity(), trialSeedModelList);
        listview.setAdapter(approvalAdapter);
    }

    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_pm_tsp_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        TextInputEditText staggered_follwd, staggered_recmmdntn, date_of_sowing, ed_row_ratio_femle_followed,
                ed_row_ratio_male_followed, row_ratio_female_recmmdtn, row_ratio_male_recmmdtn, seed_rate_female_followed, seed_rate_male_fllowd,
                seed_rate_female_recmmndation, seed_rate_male_recmmndation, ed_fromSwoing, ed_to_sowing, ed_pollen_load,
                shedding_days, ed_tassel_traits, ed_stigma_respcty, ed_silk_recepty,
                ed_desease_servty_female, desese_servty_male, insect_pest_inicident_female, insect_pest_incident_male,
                from_maturty, to_maturity, ed_to_potential_yield, ed_area_of_advantage, area_of_concern, ed_challange, ed_produciblty_of_hybrid,
                ed_key_notes, ed_sowing_detail_female, ed_sowing_detail_male, ed_sowing_detail_male1;
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        AutoCompleteTextView ac_tsp_type, ac_location, ac_zone, ac_area, ac_type_of_soil, ac_uniform_silking, ac_sensitivity_low_tempr,
                ac_hybrid;
        TextView tv_unit_chnage = popupView.findViewById(R.id.tv_unit_chnage);
        ac_hybrid = popupView.findViewById(R.id.ac_hybrid);
        staggered_follwd = popupView.findViewById(R.id.staggered_follwd);
        staggered_recmmdntn = popupView.findViewById(R.id.staggered_recmmdntn);
        date_of_sowing = popupView.findViewById(R.id.date_of_sowing);
        ed_row_ratio_femle_followed = popupView.findViewById(R.id.ed_row_ratio_femle_followed);
        ed_row_ratio_male_followed = popupView.findViewById(R.id.ed_row_ratio_male_followed);
        row_ratio_female_recmmdtn = popupView.findViewById(R.id.row_ratio_female_recmmdtn);
        row_ratio_male_recmmdtn = popupView.findViewById(R.id.row_ratio_male_recmmdtn);
        seed_rate_female_followed = popupView.findViewById(R.id.seed_rate_female_followed);
        seed_rate_male_fllowd = popupView.findViewById(R.id.seed_rate_male_fllowd);
        seed_rate_female_recmmndation = popupView.findViewById(R.id.seed_rate_female_recmmndation);
        seed_rate_male_recmmndation = popupView.findViewById(R.id.seed_rate_male_recmmndation);
        ed_fromSwoing = popupView.findViewById(R.id.ed_fromSwoing);
        ed_to_sowing = popupView.findViewById(R.id.ed_to_sowing);
        ed_pollen_load = popupView.findViewById(R.id.ed_pollen_load);
        shedding_days = popupView.findViewById(R.id.shedding_days);
        ed_tassel_traits = popupView.findViewById(R.id.ed_tassel_traits);
        ed_stigma_respcty = popupView.findViewById(R.id.ed_stigma_respcty);
        ac_sensitivity_low_tempr = popupView.findViewById(R.id.ac_sensitivity_low_tempr);
        ed_silk_recepty = popupView.findViewById(R.id.ed_silk_recepty);
        ac_uniform_silking = popupView.findViewById(R.id.ac_uniform_silking);
        ed_desease_servty_female = popupView.findViewById(R.id.ed_desease_servty_female);
        desese_servty_male = popupView.findViewById(R.id.desese_servty_male);
        insect_pest_inicident_female = popupView.findViewById(R.id.insect_pest_inicident_female);
        insect_pest_incident_male = popupView.findViewById(R.id.insect_pest_incident_male);
        from_maturty = popupView.findViewById(R.id.from_maturty);
        to_maturity = popupView.findViewById(R.id.to_maturity);
        ed_to_potential_yield = popupView.findViewById(R.id.ed_to_potential_yield);
        ed_area_of_advantage = popupView.findViewById(R.id.ed_area_of_advantage);
        area_of_concern = popupView.findViewById(R.id.area_of_concern);
        ed_challange = popupView.findViewById(R.id.ed_challange);
        ed_produciblty_of_hybrid = popupView.findViewById(R.id.ed_produciblty_of_hybrid);
        ed_key_notes = popupView.findViewById(R.id.ed_key_notes);
        ac_tsp_type = popupView.findViewById(R.id.ac_tsp_type);
        ac_location = popupView.findViewById(R.id.ac_location);
        ac_zone = popupView.findViewById(R.id.ac_zone);
        ac_area = popupView.findViewById(R.id.ac_area);
        ac_type_of_soil = popupView.findViewById(R.id.ac_type_of_soil);
        ed_sowing_detail_female = popupView.findViewById(R.id.ed_sowing_detail_female);
        ed_sowing_detail_male = popupView.findViewById(R.id.ed_sowing_detail_male);
        ed_sowing_detail_male1 = popupView.findViewById(R.id.ed_sowing_detail_male1);
        TextInputEditText ed_from_potnntl_seed_yield = popupView.findViewById(R.id.ed_from_potnntl_seed_yield);

        TextInputLayout in_silk_receptivity = popupView.findViewById(R.id.in_silk_receptivity);
        TextInputLayout in_senstvty_low_tmpr = popupView.findViewById(R.id.in_senstvty_low_tmpr);
        TextInputLayout in_stigma_receptivity = popupView.findViewById(R.id.in_stigma_receptivity);
        TextInputLayout in_tassel_traits = popupView.findViewById(R.id.in_tassel_traits);
        TextInputLayout in_challenge = popupView.findViewById(R.id.in_challenge);
        TextInputLayout in_area_of_concern = popupView.findViewById(R.id.in_area_of_concern);
        TextInputLayout in_date_of_sowing_female = popupView.findViewById(R.id.in_date_of_sowing_female);
        TextInputLayout in_date_of_sowing_male = popupView.findViewById(R.id.in_date_of_sowing_male);
        TextInputLayout in_date_of_sowing_male1 = popupView.findViewById(R.id.in_date_of_sowing_male1);
        TextView tv_potential_seed_yld = popupView.findViewById(R.id.tv_potential_seed_yld);
        TextInputLayout in_uniform_silking = popupView.findViewById(R.id.in_uniform_silking);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading_progress);

        ed_sowing_detail_female.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_detail_female);
            return true;
        });

        ed_sowing_detail_male.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_detail_male);
            return true;
        });

        ed_sowing_detail_male1.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_detail_male1);
            return true;
        });

        date_of_sowing.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(date_of_sowing);
            return true;
        });

        ed_fromSwoing.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_fromSwoing);
            return true;
        });

        ed_to_sowing.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_to_sowing);
            return true;
        });

        getLocation(content_loading, ac_location);
        ac_location.setOnItemClickListener((parent, view, position, id) -> {
            if (locationMasterTableList != null && locationMasterTableList.size() > 0) {
                UserLocationModel userLocationModel = locationMasterTableList.get(position);
                if (userLocationModel != null) {
                    ac_location.setText(userLocationModel.location_name);
                    location_code = userLocationModel.location_code;
                } else {
                    ac_location.setText("");
                }
            }
        });

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            AreaDao areaDao = pristineDatabase.areaDao();
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            zoneMasterTableList = zoneMaterDao.getAllData();
            areaMasterTableList = areaDao.getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                ZoneMasterAdapter zoneMasterAdapter = new ZoneMasterAdapter(getActivity(), R.layout.android_item_view, zoneMasterTableList);
                ac_zone.setAdapter(zoneMasterAdapter);
            }
            if (areaMasterTableList != null && areaMasterTableList.size() > 0) {
                AreaAdapter areaAdapter = new AreaAdapter(getActivity(), R.layout.android_item_view, areaMasterTableList);
                ac_area.setAdapter(areaAdapter);
            }
        }

        List<String> tsp_type_list = Arrays.asList(CommonData.tsp_type);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, tsp_type_list);
        ac_tsp_type.setAdapter(itemAdapter);

        ItemAdapter itemAdapter1 = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.soil_type));
        ac_type_of_soil.setAdapter(itemAdapter1);

        ItemAdapter itemAdapter2 = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.uniform_of_silking));
        ac_uniform_silking.setAdapter(itemAdapter2);

        ItemAdapter itemAdapter3 = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.sensitive_tempr));
        ac_sensitivity_low_tempr.setAdapter(itemAdapter3);

        ac_tsp_type.setOnItemClickListener((parent, view, position, id) -> {
            String tsp_type = tsp_type_list.get(position);
            getHybridItem(ac_hybrid, tsp_type);

            if (tsp_type.equalsIgnoreCase("CORN")) {
                in_tassel_traits.setVisibility(View.VISIBLE);
                in_silk_receptivity.setVisibility(View.VISIBLE);
                in_uniform_silking.setVisibility(View.VISIBLE);
                in_stigma_receptivity.setVisibility(View.GONE);
                in_senstvty_low_tmpr.setVisibility(View.GONE);
                in_area_of_concern.setVisibility(View.GONE);
                in_challenge.setVisibility(View.VISIBLE);
                in_date_of_sowing_male1.setVisibility(View.VISIBLE);
                in_date_of_sowing_female.setVisibility(View.VISIBLE);
                in_date_of_sowing_male.setVisibility(View.VISIBLE);
                tv_potential_seed_yld.setText("kg/acr");
                tv_unit_chnage.setText("Population/acr");
            } else if (tsp_type.equalsIgnoreCase("BAJRA")) {
                tv_unit_chnage.setText("Kg/ac");
                tv_potential_seed_yld.setText("kg/acr");
                in_tassel_traits.setVisibility(View.GONE);
                in_silk_receptivity.setVisibility(View.GONE);
                in_uniform_silking.setVisibility(View.GONE);
                in_stigma_receptivity.setVisibility(View.VISIBLE);
                in_senstvty_low_tmpr.setVisibility(View.VISIBLE);
                in_area_of_concern.setVisibility(View.VISIBLE);
                in_date_of_sowing_male1.setVisibility(View.VISIBLE);
                in_date_of_sowing_female.setVisibility(View.VISIBLE);
                in_date_of_sowing_male.setVisibility(View.VISIBLE);
                in_challenge.setVisibility(View.GONE);

            } else if (tsp_type.equalsIgnoreCase("SORGHUM")) {
                tv_unit_chnage.setText("Kg/ac");
                tv_potential_seed_yld.setText("Qtl/acr");
                in_tassel_traits.setVisibility(View.GONE);
                in_uniform_silking.setVisibility(View.GONE);
                in_silk_receptivity.setVisibility(View.GONE);
                in_stigma_receptivity.setVisibility(View.VISIBLE);
                in_senstvty_low_tmpr.setVisibility(View.VISIBLE);
                in_area_of_concern.setVisibility(View.VISIBLE);
                in_date_of_sowing_male1.setVisibility(View.GONE);
                in_date_of_sowing_female.setVisibility(View.VISIBLE);
                in_date_of_sowing_male.setVisibility(View.GONE);
                in_challenge.setVisibility(View.GONE);
            }
        });

        filter_apply_bt.setOnClickListener(view -> {
            if (ac_tsp_type.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select first tsp type", Toast.LENGTH_SHORT).show();
            } else if (ac_location.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select location.", Toast.LENGTH_SHORT).show();
            } else if (ac_zone.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select zone.", Toast.LENGTH_SHORT).show();
            } else if (ac_type_of_soil.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select soil type", Toast.LENGTH_SHORT).show();
            } else if (date_of_sowing.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select date of sowing", Toast.LENGTH_SHORT).show();
            } else {
                TrialSeedModel trialSeedModel = new TrialSeedModel();
                trialSeedModel.tsp_type = ac_tsp_type.getText().toString().trim();
                trialSeedModel.hybrid = ac_hybrid.getText().toString().trim();
                trialSeedModel.location = location_code;
                trialSeedModel.zone = ac_zone.getText().toString().trim();
                trialSeedModel.area = ac_area.getText().toString().trim();
                trialSeedModel.staggering_followed = staggered_follwd.getText().toString().trim();
                trialSeedModel.staggering_recommended = staggered_recmmdntn.getText().toString().trim();
                trialSeedModel.type_of_soil = ac_type_of_soil.getText().toString().trim();
                trialSeedModel.date_of_sowing = DateTimeUtilsCustome.splitDateInYYYMMDD(date_of_sowing.getText().toString().trim());
                trialSeedModel.sowing_detail_male = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_detail_male.getText().toString().trim());
                trialSeedModel.sowing_detail_female = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_detail_female.getText().toString().trim());
                trialSeedModel.sowing_detail_other = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_detail_male1.getText().toString().trim());
                trialSeedModel.row_ratio_female_recommended = row_ratio_female_recmmdtn.getText().toString().trim();
                trialSeedModel.row_ratio_female_followed = ed_row_ratio_femle_followed.getText().toString().trim();
                trialSeedModel.row_ratio_male_recommended = row_ratio_male_recmmdtn.getText().toString().trim();
                trialSeedModel.row_ratio_male_followed = ed_row_ratio_male_followed.getText().toString().trim();
                trialSeedModel.seed_rate_female_recommended = seed_rate_female_recmmndation.getText().toString().trim();
                trialSeedModel.seed_rate_female_followed = seed_rate_female_followed.getText().toString().trim();
                trialSeedModel.seed_rate_male_recommended = seed_rate_male_recmmndation.getText().toString().trim();
                trialSeedModel.seed_rate_male_followed = seed_rate_male_fllowd.getText().toString().trim();
                trialSeedModel.sowing_window_from = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_fromSwoing.getText().toString().trim());
                trialSeedModel.sowing_window_to = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_to_sowing.getText().toString().trim());
                trialSeedModel.male_parent_pollen_load = ed_pollen_load.getText().toString().trim();
                trialSeedModel.male_parent_shedding_days = shedding_days.getText().toString().trim();
                trialSeedModel.male_parent_tassel_trait = ed_tassel_traits.getText().toString().trim();
                trialSeedModel.female_parent_stigma_receptivity = ed_stigma_respcty.getText().toString().trim();
                trialSeedModel.female_parent_senstivity_to_low_tempr = ac_sensitivity_low_tempr.getText().toString().trim();
                trialSeedModel.female_parent_silk_receptivity = ed_silk_recepty.getText().toString().trim();
                trialSeedModel.female_parent_uniformity_of_silking = ac_uniform_silking.getText().toString().trim();
                trialSeedModel.diseases_and_severity_female = ed_desease_servty_female.getText().toString().trim();
                trialSeedModel.diseases_and_severity_male = desese_servty_male.getText().toString().trim();
                trialSeedModel.insectpest_incidence_female = insect_pest_inicident_female.getText().toString().trim();
                trialSeedModel.insectpest_incidence_male = insect_pest_incident_male.getText().toString().trim();
                trialSeedModel.maturity_from = from_maturty.getText().toString().trim();
                trialSeedModel.maturity_to = to_maturity.getText().toString().trim();
                trialSeedModel.potential_seed_yield_from = ed_from_potnntl_seed_yield.getText().toString().trim();
                trialSeedModel.potential_seed_yield_to = ed_to_potential_yield.getText().toString().trim();
                trialSeedModel.areas_of_advantage = ed_area_of_advantage.getText().toString().trim();
                trialSeedModel.challenges = ed_challange.getText().toString().trim();
                trialSeedModel.areas_of_concern = area_of_concern.getText().toString().trim();
                trialSeedModel.produciblity_of_hybrid = ed_produciblty_of_hybrid.getText().toString().trim();
                trialSeedModel.key_notes = ed_key_notes.getText().toString().trim();
                trialSeedModel.created_by = sessionManagement.getUserEmail();
                String jsonString = new Gson().toJson(trialSeedModel);
                JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (isNetwork) {
                    LoadingDialog progressDialog = new LoadingDialog();
                    progressDialog.showLoadingDialog(getActivity());
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<TspResponseModel>> call = mAPIService.insertTrialSeedProd(asJsonObject);
                    call.enqueue(new Callback<List<TspResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<TspResponseModel>> call, Response<List<TspResponseModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialog.hideDialog();
                                    List<TspResponseModel> trialSeedGetModelList = response.body();
                                    if (trialSeedGetModelList != null && trialSeedGetModelList.size() > 0 && trialSeedGetModelList.get(0).condition) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), trialSeedGetModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                        getTrialSeedProduction();
                                    } else {
                                        Toast.makeText(getActivity(), trialSeedModelList != null && trialSeedModelList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_tsp_", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TspResponseModel>> call, Throwable t) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_tsp_", getActivity());
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void getHybridItem(AutoCompleteTextView ac_hybrid, String crop) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            hybrid_item_tableList.clear();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            hybrid_item_tableList = hybridItemMasterDao.getVarietyItemDeatilsUserwise(sessionManagement.getUserEmail().toLowerCase(), crop, "Seeds");  //todo TSP
//            hybrid_item_tableList = hybridItemMasterDao.getVarietyItem("Hybrid", crop);
//            hybrid_item_tableList = hybridItemMasterDao.getHybridItem();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (hybrid_item_tableList != null && hybrid_item_tableList.size() > 0) {
                HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid.setAdapter(hybridItemAliasNameAdapter);
            }else {
                ac_hybrid.setAdapter(null);
                ac_hybrid.setText("");
            }
        }
    }

    private void getLocation(ProgressBar content_loading, AutoCompleteTextView ac_location) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<UserLocationModel>>() {
            @Override
            public void onResponse(Call<List<UserLocationModel>> call, Response<List<UserLocationModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            locationMasterTableList = user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, locationMasterTableList);
                            ac_location.setAdapter(locationMasterAdapter);
                        } else {
                            ac_location.setAdapter(null);
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "user_location_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UserLocationModel>> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "user_location_master", getActivity());
            }
        });
    }
}