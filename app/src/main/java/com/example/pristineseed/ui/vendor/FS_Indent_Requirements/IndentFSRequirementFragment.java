package com.example.pristineseed.ui.vendor.FS_Indent_Requirements;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.fs_indent_requirement.FsIndentRequirementsModel;
import com.example.pristineseed.model.fs_return_harvesting.FsReturnResponseModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.IndentFSRequirementListAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IndentFSRequirementFragment extends Fragment implements IndentFSRequirementListAdapter.OnListItemClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Chip add_newItem;
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<FsIndentRequirementsModel> fsIndentRequirementsModelList;
    private String hybrid_code = "", alias_name = "", location_code = "", crop_code = "", season_code = "";
    private Hybrid_Item_Table hybrid_item_Name = null;
    private SeasonMasterTable seasonMasterName = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indent_fs_requirement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.listview);
        sessionManagement = new SessionManagement(getActivity());
        fsIndentRequirementsModelList = new ArrayList<>();

        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("Insert", null);
        });

        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (fsIndentRequirementsModelList != null && fsIndentRequirementsModelList.size() > 0 && fsIndentRequirementsModelList.get(position).status.equalsIgnoreCase("Reject") || fsIndentRequirementsModelList.get(position).status.equalsIgnoreCase("Pending")) {
                AddNewItemPopup("UpdateView", fsIndentRequirementsModelList.get(position));
            } else {
                Toast.makeText(getActivity(), "You can't update line as status is approved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        getFsIndentRequirements();
        super.onResume();
    }

    private void getFsIndentRequirements() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<FsIndentRequirementsModel>> call = mAPIService.getFsIndentRequirements(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<FsIndentRequirementsModel>>() {
                @Override
                public void onResponse(Call<List<FsIndentRequirementsModel>> call, Response<List<FsIndentRequirementsModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<FsIndentRequirementsModel> fsIndentReqList = response.body();
                            if (fsIndentReqList != null && fsIndentReqList.size() > 0 && fsIndentReqList.get(0).condition) {
                                fsIndentRequirementsModelList = fsIndentReqList;
                                bindDataWithAdapter(fsIndentReqList);
                            } else {
                                Toast.makeText(getActivity(), fsIndentReqList != null && fsIndentReqList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Fs_indent_harvesting", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<FsIndentRequirementsModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Fs_indent_harvesting", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDataWithAdapter(List<FsIndentRequirementsModel> fsIndentReqList) {
        IndentFSRequirementListAdapter approvalAdapter = new IndentFSRequirementListAdapter(getActivity(), fsIndentReqList);
        listview.setAdapter(approvalAdapter);
        approvalAdapter.setOnListItemClickListener(this);
    }


    private UserLocationModel locationMasterTable = null;
    private SeasonMasterModel seasonMasterTable = null;
    private List<UserLocationModel> locationMasterTableLis = null;
    private List<SeasonMasterModel> seasonMasterTableList = null;
    private List<CropMassterModel.Data> cropMasterTableList = null;
    private CropMassterModel.Data cropMasterTable = null;
    private List<Hybrid_Item_Table> hybrid_item_tableList = null;


    public void AddNewItemPopup(String flag, FsIndentRequirementsModel fsIndentRequirementsModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_indent_fs_requirement_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        AutoCompleteTextView ac_location = popupView.findViewById(R.id.ac_loc);
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);
        AutoCompleteTextView ac_crop_name = popupView.findViewById(R.id.ac_crop_name);
        AutoCompleteTextView ac_hybrid_name = popupView.findViewById(R.id.ac_hybrid_name);
        TextInputEditText ed_no_of_bag_female = popupView.findViewById(R.id.ed_no_of_bag_female);
        TextInputEditText ed_pac_size_female = popupView.findViewById(R.id.ed_pac_size_female);
        TextInputEditText no_of_bags_male = popupView.findViewById(R.id.no_of_bags_male);
        TextInputEditText ed_pack_size_male = popupView.findViewById(R.id.ed_pack_size_male);
        TextInputEditText ed_male_bulk_qty = popupView.findViewById(R.id.ed_male_bulk_qty);
        TextInputEditText ed_req_by_date = popupView.findViewById(R.id.ed_req_by_date);
        TextInputEditText ed_remarks = popupView.findViewById(R.id.ed_remarks);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        TextView tv_alias_name = popupView.findViewById(R.id.tv_alias_name);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        TextInputEditText ed_male_acreage = popupView.findViewById(R.id.ed_male_acreage);
        TextInputEditText ed_female_acreage = popupView.findViewById(R.id.ed_female_acreage);

        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        Button update_btn = popupView.findViewById(R.id.update_btn);
        ed_req_by_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_req_by_date);
            return true;
        });
        getLocation(content_loading, ac_location);
        getSeasonMaster(content_loading, ac_season);
        getCropMaster(content_loading, ac_crop_name);

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            // CropHytechMasterDao cropMasterDao = pristineDatabase.cropHytechMasterDao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            //cropMasterTableList = cropMasterDao.getAllData();
            hybrid_item_tableList = hybridItemMasterDao.getHybridItem();

         /*   if(fsIndentRequirementsModel!=null) {
                if (fsIndentRequirementsModel.location != null) {
                     cropHytechMasterName = cropMasterDao.getCropByCodeName(fsIndentRequirementsModel.crop);
                }
                if (fsIndentRequirementsModel.hybrid != null) {
                     hybrid_item_Name = hybridItemMasterDao.getHybridName(fsIndentRequirementsModel.hybrid);
                }
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (seasonMasterTableList != null && seasonMasterTableList.size() > 0) {
                SeasonMasterAdapter seasonMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.item_view, seasonMasterTableList);
                ac_season.setAdapter(seasonMasterAdapter);
            }
            ac_location.setOnItemClickListener((parent, view, position, id) -> {
                locationMasterTable = locationMasterTableLis.get(position);
                if (locationMasterTable != null) {
                    ac_location.setText(locationMasterTable.location_name);
                    location_code = locationMasterTable.location_code;
                } else {
                    ac_location.setText("");
                }
            });
            ac_season.setOnItemClickListener((parent, view, position, id) -> {
                seasonMasterTable = seasonMasterTableList.get(position);
                season_code = seasonMasterTable.season_Code;
                ac_season.setText(seasonMasterTable.description);
            });

            /*if (cropMasterTableList != null && cropMasterTableList.size() > 0) {
                CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.item_view, cropMasterTableList);
                ac_crop_name.setAdapter(cropAdapter);
            }*/
            ac_crop_name.setOnItemClickListener((parent, view, position, id) -> {
                cropMasterTable = cropMasterTableList.get(position);
                crop_code = cropMasterTable.Code;
                getHybridName(cropMasterTable.Code, ac_hybrid_name);
            });

            ac_hybrid_name.setOnItemClickListener((parent, view, position, id) -> {
                if (hybrid_item_tableList != null && hybrid_item_tableList.size() > 0) {
                    Hybrid_Item_Table hybrid_item_table = hybrid_item_tableList.get(position);
                    if (hybrid_item_table != null) {
                        ac_hybrid_name.setText(hybrid_item_table.getNo());
                        hybrid_code = hybrid_item_table.getNo();
                        alias_name = hybrid_item_table.getAlias_Name();
                    } else {
                        ac_hybrid_name.setText("");
                    }
                } else {
                    ac_hybrid_name.setText("");
                }
            });
        }

        if (flag.equals("Insert")) {
            filter_apply_bt.setOnClickListener(view -> {
                /*   *//*if (ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter no of bag.", Toast.LENGTH_SHORT).show();
                } else if (ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_no_of_bag_female.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                }*//*  if (ed_pac_size_female.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter female pack size", Toast.LENGTH_SHORT).show();
                } else if (ed_pac_size_female.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pac_size_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_pac_size_female.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct female pack size", Toast.LENGTH_SHORT).show();
             *//*   } else if (no_of_bags_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        no_of_bags_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        no_of_bags_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct no. input of male bags.", Toast.LENGTH_SHORT).show();*//*
                } else if (ed_pack_size_male.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male pack size", Toast.LENGTH_SHORT).show();
                } else if (ed_pack_size_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pack_size_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_pack_size_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male pack size", Toast.LENGTH_SHORT).show();


                }*/
                if (ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male bulk qty", Toast.LENGTH_SHORT).show();
                } else if (ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_male_bulk_qty.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male qty", Toast.LENGTH_SHORT).show();
                } else if (ed_female_acreage.getText().toString().trim().equalsIgnoreCase("")) {

                    Toast.makeText(getActivity(), "Please enter female acreage !", Toast.LENGTH_SHORT).show();
                } else if (ed_male_acreage.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male acreage !", Toast.LENGTH_SHORT).show();
                } else if (ac_location.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter location.", Toast.LENGTH_SHORT).show();
                } else if (ac_season.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter season.", Toast.LENGTH_SHORT).show();
                } else {
                    FsIndentRequirementsModel fsIndentRequirementmodel = new FsIndentRequirementsModel();

                    fsIndentRequirementmodel.location = locationMasterTable != null ? locationMasterTable.location_code : "";
                    fsIndentRequirementmodel.location_name = ac_location.getText().toString().trim();
                    fsIndentRequirementmodel.season = season_code != null ? season_code : "";
                    fsIndentRequirementmodel.season_name = ac_season.getText().toString().trim();
                    fsIndentRequirementmodel.crop = cropMasterTable.Code != null ? cropMasterTable.Code : "";
                    fsIndentRequirementmodel.crop_name = ac_crop_name.getText().toString().trim();
                    fsIndentRequirementmodel.hybrid = hybrid_code != null ? hybrid_code : "";
                    fsIndentRequirementmodel.hybrid_name = alias_name;
                    fsIndentRequirementmodel.female_no_of_bags = "0";
                    fsIndentRequirementmodel.female_pack_size = "0";
                    fsIndentRequirementmodel.male_no_of_bags = "0";
                    fsIndentRequirementmodel.male_pack_size = "0";
                    fsIndentRequirementmodel.male_bulk_qty = ed_male_bulk_qty.getText().toString().trim();
                    fsIndentRequirementmodel.required_by_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_req_by_date.getText().toString().trim());
                    fsIndentRequirementmodel.remarks = ed_remarks.getText().toString().trim();
                    fsIndentRequirementmodel.created_by = sessionManagement.getUserEmail();
                    fsIndentRequirementmodel.approver_id = sessionManagement.getApprover_id();
                    fsIndentRequirementmodel.female_acreage = ed_female_acreage.getText().toString().trim();
                    fsIndentRequirementmodel.male_acreage = ed_male_acreage.getText().toString().trim();

                    if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        String jsonString = new Gson().toJson(fsIndentRequirementmodel);
                        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        Log.e("json_insert", asJsonObject.toString());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<FsReturnResponseModel>> call = mAPIService.insertFsIndentRequirements(asJsonObject);
                        call.enqueue(new Callback<List<FsReturnResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<FsReturnResponseModel>> call, Response<List<FsReturnResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<FsReturnResponseModel> insert_indent_list = response.body();
                                        if (insert_indent_list != null && insert_indent_list.size() > 0 && insert_indent_list.get(0).condition) {
                                            Toast.makeText(getActivity(), insert_indent_list.get(0).message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            getFsIndentRequirements();
                                        } else {
                                            Toast.makeText(getActivity(), insert_indent_list != null && insert_indent_list.size() > 0 ? insert_indent_list.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_indent_fs_requirement", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<FsReturnResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_indent_fs_requirement", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }
            });

        } else if (flag.equals("UpdateView")) {
            filter_apply_bt.setVisibility(View.GONE);
            update_btn.setVisibility(View.VISIBLE);

            ac_location.setText(fsIndentRequirementsModel.location_name);
            location_code = fsIndentRequirementsModel.location;


            if (fsIndentRequirementsModel.season != null) {
                season_code = fsIndentRequirementsModel.season;
                ac_season.setText(fsIndentRequirementsModel.season_name);
            }
            if (fsIndentRequirementsModel.hybrid != null) {
                hybrid_code = fsIndentRequirementsModel.hybrid;
                alias_name = fsIndentRequirementsModel.hybrid_name;
                ac_hybrid_name.setText(hybrid_code);
            }


            if (fsIndentRequirementsModel.crop != null) {
                crop_code = fsIndentRequirementsModel.crop;
                ac_crop_name.setText(fsIndentRequirementsModel.crop_name);
            }

            ed_no_of_bag_female.setText(fsIndentRequirementsModel.female_no_of_bags);
            ed_pac_size_female.setText(fsIndentRequirementsModel.female_pack_size);
            no_of_bags_male.setText(fsIndentRequirementsModel.male_no_of_bags);
            ed_pack_size_male.setText(fsIndentRequirementsModel.male_pack_size);
            ed_remarks.setText(fsIndentRequirementsModel.remarks);
            ed_male_bulk_qty.setText(fsIndentRequirementsModel.male_bulk_qty);
            ed_male_acreage.setText(fsIndentRequirementsModel.male_acreage);
            ed_female_acreage.setText(fsIndentRequirementsModel.female_acreage);

            if (fsIndentRequirementsModel.required_by_date != null) {
                ed_req_by_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(fsIndentRequirementsModel.required_by_date));
            }
            update_btn.setOnClickListener(v -> {
                /*if (ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter no of bag.", Toast.LENGTH_SHORT).show();
                } else if (ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_no_of_bag_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_no_of_bag_female.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                } else if (ed_pac_size_female.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter female pack size", Toast.LENGTH_SHORT).show();
                } else if (ed_pac_size_female.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pac_size_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_pac_size_female.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct female pack size", Toast.LENGTH_SHORT).show();
                } else if (no_of_bags_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        no_of_bags_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        no_of_bags_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct no. input of male bags.", Toast.LENGTH_SHORT).show();
                } else if (ed_pack_size_male.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male pack size", Toast.LENGTH_SHORT).show();
                } else if (ed_pack_size_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pack_size_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_pack_size_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male pack size", Toast.LENGTH_SHORT).show();*/
                if (ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male bulk qty", Toast.LENGTH_SHORT).show();
                } else if (ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_male_bulk_qty.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_male_bulk_qty.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male qty", Toast.LENGTH_SHORT).show();
                } else if (ac_location.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter location.", Toast.LENGTH_SHORT).show();
                } else if (ac_season.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter season.", Toast.LENGTH_SHORT).show();
                } else {
                    FsIndentRequirementsModel fsIndentRequirementmodel = new FsIndentRequirementsModel();
                    try {
                        fsIndentRequirementmodel.location = location_code != null ? location_code : "";
                        fsIndentRequirementmodel.fs_req_code= fsIndentRequirementsModel.fs_req_code;
                        fsIndentRequirementmodel.location_name = ac_location.getText().toString().trim();
                        fsIndentRequirementmodel.season = season_code != null ? season_code : "";
                        fsIndentRequirementmodel.season_name = ac_season.getText().toString().trim();
                        fsIndentRequirementmodel.crop = crop_code;
                        fsIndentRequirementmodel.crop_name = ac_crop_name.getText().toString().trim();
                        fsIndentRequirementmodel.hybrid = ac_hybrid_name.getText().toString().trim();
                        fsIndentRequirementmodel.hybrid_name = alias_name;
                        fsIndentRequirementmodel.female_no_of_bags = ed_no_of_bag_female.getText().toString().trim();
                        fsIndentRequirementmodel.female_pack_size = ed_pac_size_female.getText().toString().trim();
                        fsIndentRequirementmodel.male_no_of_bags = no_of_bags_male.getText().toString().trim();
                        fsIndentRequirementmodel.male_pack_size = ed_pack_size_male.getText().toString().trim();
                        fsIndentRequirementmodel.male_bulk_qty = ed_male_bulk_qty.getText().toString().trim();
                        fsIndentRequirementmodel.required_by_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_req_by_date.getText().toString().trim());
                        fsIndentRequirementmodel.remarks = ed_remarks.getText().toString().trim();
                        fsIndentRequirementmodel.approver_id = sessionManagement.getApprover_id();
                        fsIndentRequirementmodel.female_acreage = ed_female_acreage.getText().toString().trim();
                        fsIndentRequirementmodel.male_acreage = ed_male_acreage.getText().toString().trim();
                        fsIndentRequirementmodel.created_by = sessionManagement.getUserEmail();
                        fsIndentRequirementmodel.updated_by = sessionManagement.getUserEmail();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        String jsonString = new Gson().toJson(fsIndentRequirementmodel);
                        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<FsReturnResponseModel>> call = mAPIService.updateFsIndentRequirements(asJsonObject);
                        call.enqueue(new Callback<List<FsReturnResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<FsReturnResponseModel>> call, Response<List<FsReturnResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<FsReturnResponseModel> insert_indent_list = response.body();
                                        if (insert_indent_list != null && insert_indent_list.size() > 0 && insert_indent_list.get(0).condition) {
                                            Toast.makeText(getActivity(), insert_indent_list.get(0).message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            getFsIndentRequirements();
                                        } else {
                                            Toast.makeText(getActivity(), insert_indent_list != null && insert_indent_list.size() > 0 ? insert_indent_list.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_indent_fs_requirement", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<FsReturnResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_indent_fs_requirement", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void OnItemCLickListn(int pos) {
        FsIndentRequirementsModel fsIndentRequirementsModel = fsIndentRequirementsModelList.get(pos);
        AddNewItemPopup("UpdateView", fsIndentRequirementsModel);
    }

    private void getHybridName(String crop_code, AutoCompleteTextView ac_hybrid_name) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            hybrid_item_tableList.clear();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            hybrid_item_tableList = hybridItemMasterDao.getVarietyItem("Hybrid", crop_code);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if (hybrid_item_tableList != null && hybrid_item_tableList.size() > 0) {
                HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter);
            } else {
                ac_hybrid_name.setAdapter(null);
                ac_hybrid_name.setText("");
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
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            content_loading.setVisibility(View.GONE);
                            locationMasterTableLis = user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, locationMasterTableLis);
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


    private void getSeasonMaster(ProgressBar content_loading, AutoCompleteTextView ac_season_code) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            seasonMasterTableList = templantingList_season;
                            SeasonMasterAdapter locationMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterTableList);
                            ac_season_code.setAdapter(locationMasterAdapter);
                        } else {
                            content_loading.setVisibility(View.GONE);
                            ac_season_code.setText("");
                            ac_season_code.setAdapter(null);
                            Toast.makeText(getActivity(), templantingList_season != null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_season", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_season", getActivity());
            }
        });
    }


    private void getCropMaster(MaterialProgressBar content_loading, AutoCompleteTextView ac_crop) {
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
                                cropMasterTableList = crop_master_list;
                                if (cropMasterTableList.size() > 0) {
                                    CropHytechAdapter cropHytechAdapter = new CropHytechAdapter(getActivity(), R.layout.android_item_view, cropMasterTableList);
                                    ac_crop.setAdapter(cropHytechAdapter);
                                } else {
                                    ac_crop.setAdapter(null);
                                }
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
}