package com.example.pristineseed.ui.bootmMainScreen.ui.DailyActivtiy;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.R;

import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityModel;

import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;

import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyActivty_AddLine_Fragment extends Fragment implements RoleMasterAdapter.OnItemClickListner {

    private DailyActivityModel dailyActivityModel = null;
    private AutoCompleteTextView filled_district_dropdown, ac_crop_varity, ac_check_crop_and_verity;
    private TextInputLayout text_input;
    private TextInputEditText ac_farmer_dealer;
    private Button submitPage;
    private TextInputEditText edit_village,
            edit_ajeet_crop_age, edit_ajeet_fruits_per, edit_ajeet_pest, edit_ajeet_disease,
            edit_check_crop_age, edit_check_fruits_per, edit_check_pest, edit_check_disease;

    private Context mcontext;
    private DistrictAdapter districtadapter;
    private List<RoleMasterModel.Data> roleMasterTableList = null;
    private List<CropMassterModel.Data> cropHytechMasterTableList = null;
    private String SelectedDistrict = "";
    private MaterialProgressBar content_loading;
    private String farmer_code = "";
    private MaterialCardView frame_layout_org_list;
    private ProgressBar loading_item;
    private RecyclerView lv_cust_dist_list;
    private TextInputLayout farmer_dealer_input_layout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_activity_addlines_fragments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    void initView(View view) {
        dailyActivityModel = new Gson().fromJson(getArguments().getString("dataPass", ""), DailyActivityModel.class);
        filled_district_dropdown = view.findViewById(R.id.filled_district_dropdown);
        submitPage = view.findViewById(R.id.submitPage);
        text_input = view.findViewById(R.id.text_input);

        ac_farmer_dealer = view.findViewById(R.id.ac_farmer_dealer);
        edit_village = view.findViewById(R.id.edit_village);

        ac_crop_varity = view.findViewById(R.id.ac_crop_varity);
        edit_ajeet_crop_age = view.findViewById(R.id.edit_ajeet_crop_age);
        edit_ajeet_fruits_per = view.findViewById(R.id.edit_ajeet_fruits_per);
        edit_ajeet_pest = view.findViewById(R.id.edit_ajeet_pest);
        edit_ajeet_disease = view.findViewById(R.id.edit_ajeet_disease);

        ac_check_crop_and_verity = view.findViewById(R.id.ac_check_crop_and_verity);
        edit_check_crop_age = view.findViewById(R.id.edit_check_crop_age);
        edit_check_fruits_per = view.findViewById(R.id.edit_check_fruits_per);
        edit_check_pest = view.findViewById(R.id.edit_check_pest);
        edit_check_disease = view.findViewById(R.id.edit_check_disease);
        content_loading = view.findViewById(R.id.content_loading);
        frame_layout_org_list = view.findViewById(R.id.frame_layout_org_list);
        lv_cust_dist_list = view.findViewById(R.id.lv_cust_dist_list);
        loading_item = view.findViewById(R.id.loading_item);
        farmer_dealer_input_layout = view.findViewById(R.id.farmer_dealer_input_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mcontext = getActivity();
        initView(view);
        bindDistrict();
//        eventOnUI();
        getCropMaster();


        ac_farmer_dealer.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                farmer_dealer_input_layout.setStartIconDrawable(null);
            } else {
                if (!ac_farmer_dealer.getText().toString().trim().equalsIgnoreCase("")) {
                    farmer_dealer_input_layout.setStartIconDrawable(null);
                } else {
                    farmer_dealer_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

        ac_farmer_dealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("")) {
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getRoleMasterData(s.toString());
                } else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ac_check_crop_and_verity.setOnItemClickListener((parent, view1, position, id) -> {
            ac_crop_varity.setText(cropHytechMasterTableList.get(position).Code);
            ac_check_crop_and_verity.setText(cropHytechMasterTableList.get(position).Description);
        });
        
        submitPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPage();
            }
        });

    }

    void bindDistrict() {
        try {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            List<DistricMasterTable> returnData = districMasterDao.getAllData();
            districtadapter = new DistrictAdapter(getContext(), R.layout.drop_down_textview, returnData);
            filled_district_dropdown.setAdapter(districtadapter);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("distric_table_exc", e.getMessage());
        }
    }

    void eventOnUI() {
        //todo if dropdown use this is autocomplete
        filled_district_dropdown.setOnClickListener(view -> {
            if (filled_district_dropdown.hasFocus()) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


        filled_district_dropdown.setOnItemClickListener((adapterView, view1, i, l) -> {
            SelectedDistrict = districtadapter.getItem(i).getCode();
            bindDistrict();
        });
    }

    void submitPage(){
        if (dailyActivityModel.addlines == null) {
            dailyActivityModel.addlines = new ArrayList<>();
        } else if (ac_farmer_dealer.getText().toString().equalsIgnoreCase("")) {   //toString().contentEquals("").
            Toast.makeText(getActivity(), "Please Enter Farmer Name.", Toast.LENGTH_SHORT).show();// ac_farmer_dealer.setError("Please Enter Farmer Name.");
            return;
        } else if (filled_district_dropdown.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please Select District.", Toast.LENGTH_SHORT).show();//filled_district_dropdown.setError("Please Select District.");
            return;
        } else if (edit_village.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please Enter Village.", Toast.LENGTH_SHORT).show();// edit_village.setError("Please Enter Village");
            return;
        } else {
            DailyActivityModel.DailyActivityLines postthedata = dailyActivityModel.new DailyActivityLines();
            postthedata.farmer_name = farmer_code != null ? farmer_code : "";
            postthedata.district = SelectedDistrict;
            postthedata.village = edit_village.getText().toString();
            postthedata.ajeet_crop_and_verity = ac_crop_varity.getText().toString();
            postthedata.ajeet_crop_age = edit_ajeet_crop_age.getText().toString();
            postthedata.ajeet_fruits_per = edit_ajeet_fruits_per.getText().toString();
            postthedata.ajeet_pest = edit_ajeet_pest.getText().toString();
            postthedata.ajeet_disease = edit_ajeet_disease.getText().toString();
            postthedata.check_crop_and_variety = ac_check_crop_and_verity.getText().toString();
            postthedata.check_crop_age = edit_check_crop_age.getText().toString();
            postthedata.check_fruits_per = edit_check_fruits_per.getText().toString();
            postthedata.check_pest = edit_check_pest.getText().toString();
            postthedata.check_disease = edit_check_disease.getText().toString();
            dailyActivityModel.addlines.add(postthedata);
            DailyActivityFragment dailyActivityFragment = new DailyActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putString("dataPass", new Gson().toJson(dailyActivityModel));
            dailyActivityFragment.setArguments(bundle);
            StaticMethods.loadFragmentsWithBackStack(getActivity(), dailyActivityFragment, "daily_activity_fragment");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getRoleMasterData(String filter_key) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributorSync("Farmer", filter_key,"");
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList != null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList = roleMasterModelList.data;
                            if (rolemasterList != null && rolemasterList.size() > 0 && rolemasterList.get(0).no != null) {
                                roleMasterTableList = rolemasterList;
                                setFarmerAdapter();
                            } else {
                                frame_layout_org_list.setVisibility(View.GONE);
                                lv_cust_dist_list.setAdapter(null);
                            }
                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getdaily_activity_farmer_master", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getdaily_activity_farmer_master", getActivity());
            }
        });
    }

    private void setFarmerAdapter() {
        RoleMasterAdapter roleMasterAdapter = new RoleMasterAdapter(getActivity(), roleMasterTableList);
        lv_cust_dist_list.setAdapter(roleMasterAdapter);
    }


 /*   private void getCropMaster(){
        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
        try{
            CropHytechMasterDao cropHytechMasterDao=pristineDatabase.cropHytechMasterDao();
            cropHytechMasterTableList=cropHytechMasterDao.getAllData();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            CropHytechAdapter cropHytechAdapter=new CropHytechAdapter(getActivity(),R.layout.item_view,cropHytechMasterTableList);
            ac_crop_varity.setAdapter(cropHytechAdapter);

            ac_check_crop_and_verity.setAdapter(cropHytechAdapter);
        }
    }*/

    void getCropMaster() {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<CropMassterModel> call = mAPIService.getCropMasterData();
        call.enqueue(new Callback<CropMassterModel>() {
            @Override
            public void onResponse(Call<CropMassterModel> call, Response<CropMassterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        CropMassterModel cropMassterModel = response.body();
                        if (cropMassterModel != null && cropMassterModel.condition) {
                            List<CropMassterModel.Data> crop_master_list = cropMassterModel.data;
                            if (crop_master_list != null && crop_master_list.size() > 0) {
                                cropHytechMasterTableList = crop_master_list;
                                CropHytechAdapter cropHytechAdapter = new CropHytechAdapter(getActivity(), R.layout.android_item_view, cropHytechMasterTableList);
                                ac_crop_varity.setAdapter(cropHytechAdapter);
                                ac_check_crop_and_verity.setAdapter(cropHytechAdapter);
                            }
                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Crop Master Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_crop_master", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CropMassterModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        if (roleMasterTableList != null && roleMasterTableList.size() > 0) {
            frame_layout_org_list.setVisibility(View.GONE);
            farmer_dealer_input_layout.setStartIconDrawable(null);
            ac_farmer_dealer.setText(roleMasterTableList.get(pos).name);
            farmer_code = roleMasterTableList.get(pos).no;
        } else {
            farmer_dealer_input_layout.setStartIconDrawable(null);
            frame_layout_org_list.setVisibility(View.GONE);
        }
    }
}
