
package com.example.pristineseed.ui.vendor.fs_return_harvesting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
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
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;
import com.example.pristineseed.model.fs_return_harvesting.FsReturnResponseModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.FsioBsioSaleOrderNoModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingLotMFOAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.FSReturnHarvestingListAdapter;
import com.example.pristineseed.ui.adapter.header_line.FsReturnLineAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.FsioBsioSaleOrderNoAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
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


public class FSReturnHarvestingFragment extends Fragment implements FSReturnHarvestingListAdapter.OnListItemClickListener {
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<FSReturnHarvestingModel> fsharvestingList;
    private List<FsioBsioSaleOrderNoModel.Data> fsio_bsio_sale_list = null;
    private List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> fs_retrun_line_list = null;
    private FsReturnLineAdapter fsReturnLineAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    private Chip add_newItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fs_return_harvesting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        fsharvestingList = new ArrayList<>();
        listview = view.findViewById(R.id.listview);
        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            addUpdateHeader("insert_header", null);
        });

    }

    @Override
    public void onResume() {
        getFsRetrunHarvesting();
        super.onResume();
    }

    private void getFsRetrunHarvesting() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<FSReturnHarvestingModel>> call = mAPIService.getFsRetrunHarvestingData(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<FSReturnHarvestingModel>>() {
                @Override
                public void onResponse(Call<List<FSReturnHarvestingModel>> call, Response<List<FSReturnHarvestingModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<FSReturnHarvestingModel> fsReturnHarvestingModelList = response.body();
                            if (fsReturnHarvestingModelList != null && fsReturnHarvestingModelList.size() > 0 && fsReturnHarvestingModelList.get(0).condition) {
                                fsharvestingList = fsReturnHarvestingModelList;
                                bindDataWithAdapter(fsReturnHarvestingModelList);
                            } else {
                                Toast.makeText(getActivity(), fsReturnHarvestingModelList != null && fsReturnHarvestingModelList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Fs_return_harvesting", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<FSReturnHarvestingModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Fs_return_harvesting", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDataWithAdapter(List<FSReturnHarvestingModel> fsReturnHarvestingModelList) {
        FSReturnHarvestingListAdapter fsReturnHarvestingListAdapter = new FSReturnHarvestingListAdapter(getActivity(), fsReturnHarvestingModelList);
        listview.setAdapter(fsReturnHarvestingListAdapter);
        fsReturnHarvestingListAdapter.setOnListItemClickListener(this);
    }

    private UserLocationModel locationMasterTable = null;
    private SeasonMasterModel seasonMasterTable = null;
    private PlantingLotModel.Data female_lot_tble = null;
    private PlantingLotModel.Data male_lot_table = null;
    private List<PlantingLotModel.Data> planting_male_lotList = null;
    private List<PlantingLotModel.Data> planting_female_lotList = null;
    private List<UserLocationModel> locationMasterTableLis = null;
    private List<SeasonMasterModel> seasonMasterTableList = null;
    private List<PlantingLotModel.Data> planting_lot_master_tableList = null;
    private PlantingLotModel.Data planting_lot_bulk_master_table = null;
    private Planting_Lot_master_Table planting_bulk_lot_no = null;
    private List<CropMassterModel.Data> crop_list = null;
    private CropMassterModel.Data crop_table = null;
    private List<Hybrid_Item_Table> hybrid_item_list = null;
    private String location_code="",season_code="";

    public void addUpdateHeader(String flag, FSReturnHarvestingModel return_harvesting_model) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_fs_return_requirement_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        AutoCompleteTextView ac_loc = popupView.findViewById(R.id.ac_loc);
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);
        AutoCompleteTextView ac_doc_sale_no = popupView.findViewById(R.id.ac_doc_sale_no);
        Button update_btn = popupView.findViewById(R.id.update_btn);
        AutoCompleteTextView ac_doc_type = popupView.findViewById(R.id.ac_doc_type);
        MaterialProgressBar content_loading=popupView.findViewById(R.id.content_loading);
        List<String> doc_type_list = Arrays.asList(CommonData.doc_sub_type);
        ItemAdapter doc_typ_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, doc_type_list);
        ac_doc_type.setAdapter(doc_typ_adapter);
        getLocation(content_loading,ac_loc);
        getSeasonMaster(content_loading,ac_season);

        try {
            ac_doc_type.setOnItemClickListener((parent, view, position, id) -> {
                String doc_type = doc_type_list.get(position);
                getFsioBsioSaleOrderNo(content_loading,ac_doc_sale_no,doc_type);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

            ac_loc.setOnItemClickListener((parent, view, position, id) -> {
               UserLocationModel locationMasterTable = locationMasterTableLis.get(position);
                if(locationMasterTable!=null){
                 location_code=locationMasterTable.location_code;
                 ac_loc.setText(locationMasterTable.location_name);
                }
                else {
                    ac_loc.setText("");
                }
            });
            ac_season.setOnItemClickListener((parent, view, position, id) -> {
               SeasonMasterModel seasonMasterTable = seasonMasterTableList.get(position);
                if(seasonMasterTable!=null){
                     season_code=seasonMasterTable.season_Code;
                    ac_season.setText(seasonMasterTable.description);
                }

            });


        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);

        if (flag.equals("insert_header")) {
            filter_apply_bt.setOnClickListener(view -> {
                if (ac_loc.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select location.", Toast.LENGTH_SHORT).show();
                } else if (ac_doc_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select doc type.", Toast.LENGTH_SHORT).show();
                } else if (ac_doc_sale_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select fsio bsio sale order no.", Toast.LENGTH_SHORT).show();
                }
                    else {
                    try {
                        FSReturnHarvestingModel fsReturnHarvestingModel = new FSReturnHarvestingModel();
                        fsReturnHarvestingModel.location =location_code != null ?location_code : "";
                        fsReturnHarvestingModel.location_name = ac_loc.getText().toString().trim();
                        fsReturnHarvestingModel.season = season_code != null ? season_code : "";
                        fsReturnHarvestingModel.season_name=ac_season.getText().toString().trim();
                        fsReturnHarvestingModel.created_by = sessionManagement.getUserEmail();
                        fsReturnHarvestingModel.approver_id = sessionManagement.getApprover_id();
                        fsReturnHarvestingModel.fsioSalesOrderNo = ac_doc_sale_no.getText().toString().trim();
                        fsReturnHarvestingModel.doc_type = ac_doc_type.getText().toString().trim();

                        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                            LoadingDialog progressDialog = new LoadingDialog();
                            progressDialog.showLoadingDialog(getActivity());
                            String jsonString = new Gson().toJson(fsReturnHarvestingModel);
                            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                            Log.e("json_insert", asJsonObject.toString());
                            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                            Call<List<FsReturnResponseModel>> call = mAPIService.insertFsReturnHarvesting(asJsonObject);
                            call.enqueue(new Callback<List<FsReturnResponseModel>>() {
                                @Override
                                public void onResponse(Call<List<FsReturnResponseModel>> call, Response<List<FsReturnResponseModel>> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            progressDialog.hideDialog();
                                            List<FsReturnResponseModel> insert_response_list = response.body();
                                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                getFsRetrunHarvesting();
                                            } else {
                                                Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            progressDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_fs_return_header", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<FsReturnResponseModel>> call, Throwable t) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_fs_return_header", getActivity());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } else if (flag.equals("update_header")) {
            filter_apply_bt.setVisibility(View.GONE);
            update_btn.setVisibility(View.VISIBLE);
            update_btn.setText("Update Header");

            ac_doc_type.setText(return_harvesting_model.doc_type);
            if(return_harvesting_model.location!=null){
                ac_loc.setText(return_harvesting_model.location_name);
                location_code=locationMasterTable.location_code;

            }
            else {
                ac_loc.setText("");
            }

            if(return_harvesting_model.season!=null){
                ac_season.setText(return_harvesting_model.season_name);
                season_code=seasonMasterTable.season_Code;
            }


            update_btn.setOnClickListener(v -> {

                if (ac_loc.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select location.", Toast.LENGTH_SHORT).show();
                } else if (ac_doc_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select doc type.", Toast.LENGTH_SHORT).show();
                } else if (ac_doc_sale_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select fsio bsio sale order no.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        FSReturnHarvestingModel fsReturnHarvestingModel = new FSReturnHarvestingModel();
                        fsReturnHarvestingModel.location = location_code != null ? location_code : "";
                        fsReturnHarvestingModel.location_name=ac_loc.getText().toString().trim();
                        fsReturnHarvestingModel.season = season_code != null ? season_code : "";
                        fsReturnHarvestingModel.season_name=ac_season.getText().toString().trim();
                        fsReturnHarvestingModel.created_by = sessionManagement.getUserEmail();
                        fsReturnHarvestingModel.approver_id = sessionManagement.getApprover_id();
                        fsReturnHarvestingModel.fsioSalesOrderNo = ac_doc_sale_no.getText().toString().trim();
                        fsReturnHarvestingModel.fs_return_code = return_harvesting_model.fs_return_code;

                        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                            LoadingDialog progressDialog = new LoadingDialog();
                            progressDialog.showLoadingDialog(getActivity());
                            String jsonString = new Gson().toJson(fsReturnHarvestingModel);
                            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                            Log.e("json_insert", asJsonObject.toString());
                            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                            Call<List<FsReturnResponseModel>> call = mAPIService.insertFsReturnHarvesting(asJsonObject);
                            call.enqueue(new Callback<List<FsReturnResponseModel>>() {
                                @Override
                                public void onResponse(Call<List<FsReturnResponseModel>> call, Response<List<FsReturnResponseModel>> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            progressDialog.hideDialog();
                                            List<FsReturnResponseModel> insert_response_list = response.body();
                                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                getFsRetrunHarvesting();
                                            } else {
                                                Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            progressDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_fs_return_header", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<FsReturnResponseModel>> call, Throwable t) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_fs_return_header", getActivity());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

        }
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    @Override
    public void OnItemCLickListn(int pos) {
        FSReturnHarvestingModel fsReturnHarvestingModel = fsharvestingList.get(pos);
        FSReturnHarvestingModel.FSReturnHarvestingLineModel fsReturnHarvestingLineModel = fsReturnHarvestingModel.fs_return_line.get(pos);
        if (fsReturnHarvestingModel != null) {

            addFsRetrunLine(fsReturnHarvestingModel, "insert_line", fsReturnHarvestingLineModel);
        }
    }

    private void getHybridName(String crop_code, AutoCompleteTextView ac_hybrid_name) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            hybrid_item_list.clear();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            hybrid_item_list = hybridItemMasterDao.getVarietyItem("Hybrid", crop_code);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (hybrid_item_list != null && hybrid_item_list.size() > 0) {
                HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.item_view, hybrid_item_list);
                ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter);
                }
            else {
                ac_hybrid_name.setAdapter(null);
            }
        }
    }

    private String crop_code = "", crop_string = "",hybrid_code="",hybrid_string="";
    private CropHytechMasterTable cropHytechMasterTable = null;
    private  Hybrid_Item_Table hybrid_item_table=null;

    private void addFsRetrunLine(FSReturnHarvestingModel fsReturnHarvestingModel, String flagOfAction, FSReturnHarvestingModel.FSReturnHarvestingLineModel fs_return_line) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.fs_retrun_header_line_details_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        TextView tv_fs_retrurn_code = popupView.findViewById(R.id.tv_fs_retrurn_code);
        TextView tv_fs_loc = popupView.findViewById(R.id.tv_fs_loc);
        TextView tv_fs_doc_sale_no = popupView.findViewById(R.id.tv_fs_doc_sale_no);
        TextView fs_return_code_title = popupView.findViewById(R.id.fs_return_code_title);

        Chip chip_add_line = popupView.findViewById(R.id.chip_add_line);
        ListView fs_return_line_listview = popupView.findViewById(R.id.fs_return_line_listview);
        MaterialButton complete_fs_return_btn = popupView.findViewById(R.id.complete_fs_return_btn);
        TextView tv_header_update=popupView.findViewById(R.id.tv_header_update);
        ImageView back_button = popupView.findViewById(R.id.back_button);

        back_button.setOnClickListener(v -> {
            dialog.dismiss();
        });

        tv_fs_retrurn_code.setText(fsReturnHarvestingModel.fs_return_code);
        tv_fs_loc.setText(fsReturnHarvestingModel.location);
        tv_fs_doc_sale_no.setText(fsReturnHarvestingModel.fsioSalesOrderNo);
        fs_return_code_title.setText("(" + fsReturnHarvestingModel.fs_return_code + ")");
        if(fsReturnHarvestingModel.status.equalsIgnoreCase("Approve")){
            chip_add_line.setVisibility(View.GONE);
            tv_header_update.setVisibility(View.GONE);
            complete_fs_return_btn.setVisibility(View.GONE);
        }

        fs_retrun_line_list=fsReturnHarvestingModel.fs_return_line;
        if (fs_retrun_line_list != null && fs_retrun_line_list.size() > 0 && fs_retrun_line_list.get(0).line_no != null) {
               bindDataWithAdapter(fs_retrun_line_list, fs_return_line_listview);
        }

        tv_header_update.setOnClickListener(v -> {
            dialog.dismiss();
            if(fsReturnHarvestingModel.status.equalsIgnoreCase("Reject")) {
                addUpdateHeader("update_header", fsReturnHarvestingModel);
            }
            else  if(fsReturnHarvestingModel.status.equalsIgnoreCase("Pending") || fsReturnHarvestingModel.status.equalsIgnoreCase("Approved")){
                Toast.makeText(getActivity(), "You can't update header as status is Approved.", Toast.LENGTH_SHORT).show();
            }

        });

        fs_return_line_listview.setOnItemClickListener((parent, view, position, id) -> {
            try {
                FSReturnHarvestingModel.FSReturnHarvestingLineModel update_fs_retrun_line = fs_retrun_line_list.get(position);
                if(fsReturnHarvestingModel!=null && fsReturnHarvestingModel.status.equalsIgnoreCase("Reject") || fsReturnHarvestingModel.status.equalsIgnoreCase("Pending")) {
                    fs_line_add_update_pop_up(fsReturnHarvestingModel, "updateLine", update_fs_retrun_line, fs_return_line_listview);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        chip_add_line.setOnClickListener(v -> {
            fs_line_add_update_pop_up(fsReturnHarvestingModel, "insert_line", fs_return_line, fs_return_line_listview);

        });

        fs_return_line_listview.setOnItemLongClickListener((parent, view, position, id) -> {
            if(fs_retrun_line_list!=null && fs_retrun_line_list.size()>0 && fs_retrun_line_list.get(position).fs_return_code!=null) {
                if (fsReturnHarvestingModel != null && fsReturnHarvestingModel.status.equalsIgnoreCase("Reject") || fsReturnHarvestingModel.status.equalsIgnoreCase("Pending")) {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setMessage("Do You Really want to delete this Code : " + fs_retrun_line_list.get(position).line_no)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                                        LoadingDialog progressDialog = new LoadingDialog();
                                        progressDialog.showLoadingDialog(getActivity());

                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("fs_return_code", fs_retrun_line_list.get(position).fs_return_code);
                                        jsonObject.addProperty("line_no", fs_retrun_line_list.get(position).line_no);

                                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                                        Call<List<ResponseModel>> call = mAPIService.deleteFsReturnLine(jsonObject);
                                        call.enqueue(new Callback<List<ResponseModel>>() {
                                            @Override
                                            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                                                try {
                                                    if (response.isSuccessful()) {
                                                        progressDialog.hideDialog();
                                                        List<ResponseModel> insert_response_list = response.body();
                                                        if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                            //todo bind Adapter......
                                                            fs_retrun_line_list.remove(position);
                                                            fsReturnLineAdapter.notifyDataSetChanged();
                                                            Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                            dialogInterface.dismiss();
                                                        } else {
                                                            Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        progressDialog.hideDialog();
                                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception e) {
                                                    progressDialog.hideDialog();
                                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_fs_retrun_line", getActivity());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                                                progressDialog.hideDialog();
                                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_fs_retrun_line", getActivity());
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                } else {
                    Toast.makeText(getActivity(), "You can't delete the line as status is Approved.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "FS return code is blank.", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        complete_fs_return_btn.setOnClickListener(v -> {
            if(fs_retrun_line_list!=null && fs_retrun_line_list.size()>0 && fs_retrun_line_list.get(0).fs_return_code!=null) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setMessage("Do You Really want to Complete this Code : " + fs_retrun_line_list.get(0).fs_return_code)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                                    LoadingDialog progressDialog = new LoadingDialog();
                                    progressDialog.showLoadingDialog(getActivity());
                                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                                    Call<List<ResponseModel>> call = mAPIService.compelteFsReturn(fsReturnHarvestingModel.fs_return_code!=null?fsReturnHarvestingModel.fs_return_code:"");
                                    call.enqueue(new Callback<List<ResponseModel>>() {
                                        @Override
                                        public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                                            try {
                                                if (response.isSuccessful()) {
                                                    progressDialog.hideDialog();
                                                    List<ResponseModel> insert_response_list = response.body();
                                                    if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                        //todo bind Adapter......
                                                        chip_add_line.setVisibility(View.GONE);
                                                        Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                        dialogInterface.dismiss();
                                                        dialog.dismiss();
                                                        getFsRetrunHarvesting();
                                                    } else {
                                                        Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    progressDialog.hideDialog();
                                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                progressDialog.hideDialog();
                                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_fs_retrun", getActivity());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                                            progressDialog.hideDialog();
                                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_fs_retrun", getActivity());
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
            else {
                Toast.makeText(getActivity(), "FS return code is blank.", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void fs_line_add_update_pop_up(FSReturnHarvestingModel fsReturnHarvestingModel, String flagOfAction,
                                           FSReturnHarvestingModel.FSReturnHarvestingLineModel fs_return_line,
                                           ListView fs_return_line_listview) {
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View popupView1 = inflater1.inflate(R.layout.add_fs_retrun_line_layout, null);
        Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setContentView(popupView1);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog1.show();
        AutoCompleteTextView ac_crop_name = popupView1.findViewById(R.id.ac_crop_name);
        AutoCompleteTextView ac_hybrid_name = popupView1.findViewById(R.id.ac_hybrid_name);
        AutoCompleteTextView ac_female_lot_no = popupView1.findViewById(R.id.ac_female_lot_no);
        TextInputEditText no_of_bags_female = popupView1.findViewById(R.id.no_of_bags_female);
        TextInputEditText ed_female_pack_size = popupView1.findViewById(R.id.ed_pack_size);
        TextInputEditText feamle_qty = popupView1.findViewById(R.id.feamle_qty);
        AutoCompleteTextView ac_male_lot = popupView1.findViewById(R.id.ac_male_lot);

        TextInputEditText no_of_male_bags = popupView1.findViewById(R.id.no_of_male_bags);
        TextInputEditText pack_size_male = popupView1.findViewById(R.id.pack_size_male);
        TextInputEditText qty_male = popupView1.findViewById(R.id.qty_male);

        AutoCompleteTextView male_bulk_lotno = popupView1.findViewById(R.id.male_bulk_lotno);
        TextInputEditText ed_dispatch_date = popupView1.findViewById(R.id.ed_dispatch_date);

        TextInputEditText ed_remarks = popupView1.findViewById(R.id.ed_remarks);
        AutoCompleteTextView ac_physical_cond = popupView1.findViewById(R.id.ac_physical_cond);
        ImageView close_dilog_bt = popupView1.findViewById(R.id.close_dilog_bt);
        MaterialProgressBar content_loading=popupView1.findViewById(R.id.content_loading);


        Button save_line_btn = popupView1.findViewById(R.id.filter_apply_bt);
        Button update_btn = popupView1.findViewById(R.id.update_btn);

        close_dilog_bt.setOnClickListener(v1 -> {
            dialog1.dismiss();
        });

        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.item_view, Arrays.asList(CommonData.crop_condition));
        ac_physical_cond.setAdapter(itemAdapter);

        ed_dispatch_date.setOnTouchListener((v0, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_dispatch_date);
            return true;
        });

        getPlantingLotList(content_loading,ac_male_lot,ac_female_lot_no,male_bulk_lotno,fsReturnHarvestingModel.doc_type,"Male");
        getPlantingLotList(content_loading,ac_male_lot,ac_female_lot_no,male_bulk_lotno,fsReturnHarvestingModel.doc_type,"Female");
        getPlantingLotList(content_loading,ac_male_lot,ac_female_lot_no,male_bulk_lotno,fsReturnHarvestingModel.doc_type,"Other");
        getCropMaster(content_loading,ac_crop_name);

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
           // CropHytechMasterDao cropMasterDao = pristineDatabase.cropHytechMasterDao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            //Planting_lot_Dao lot_dao = pristineDatabase.planting_lot_dao();
           // crop_list = cropMasterDao.getAllData();
            hybrid_item_list = hybridItemMasterDao.getHybridItem();
           /*  planting_male_lotList = lot_dao.getPlantingLotListOfMale_byDocumentType(fsReturnHarvestingModel.doc_type);
            planting_female_lotList = lot_dao.getPlantingLotListOfFemale(fsReturnHarvestingModel.doc_type);
            planting_lot_master_tableList = lot_dao.getPlantingLotListOfMaleBulk("FSIO", "BSIO");*/
            // cropHytechMasterTable = cropMasterDao.getCropByCodeName(fs_return_line.crop);
             hybrid_item_table=hybridItemMasterDao.getHybridName(fs_return_line.hybrid);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }
           /* if (planting_female_lotList != null && planting_female_lotList.size() > 0) {
                PlantingLotMFOAdapter plantingLotMFOAdapter = new PlantingLotMFOAdapter(getActivity(), R.layout.item_view, planting_female_lotList);
                ac_female_lot_no.setAdapter(plantingLotMFOAdapter);
            }
            if (planting_male_lotList != null && planting_male_lotList.size() > 0) {
                PlantingLotMFOAdapter plantingLotMFOAdapter = new PlantingLotMFOAdapter(getActivity(), R.layout.item_view, planting_male_lotList);
                ac_male_lot.setAdapter(plantingLotMFOAdapter);
            }

            if (planting_lot_master_tableList != null && planting_lot_master_tableList.size() > 0) {
                PlantingLotMFOAdapter plantingLotMFOAdapter = new PlantingLotMFOAdapter(getActivity(), R.layout.item_view, planting_lot_master_tableList);
                male_bulk_lotno.setAdapter(plantingLotMFOAdapter);
            }


*/

            ac_female_lot_no.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    female_lot_tble = planting_female_lotList.get(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            ac_male_lot.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    male_lot_table = planting_male_lotList.get(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            male_bulk_lotno.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    planting_lot_bulk_master_table = planting_lot_master_tableList.get(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

           /* if (crop_list != null && crop_list.size() > 0) {
                CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.item_view, crop_list);
                ac_crop_name.setAdapter(cropAdapter);
            }
*/
            ac_crop_name.setOnItemClickListener((parent, view, position, id) -> {
                crop_table = crop_list.get(position);
                if (crop_table != null) {
                    crop_code = crop_table.Code;
                    crop_string = crop_table.Description;
                    ac_crop_name.setText(crop_string);
                    getHybridName(crop_table.Code, ac_hybrid_name);
                }
            });

            ac_hybrid_name.setOnItemClickListener((parent, view, position, id) -> {
                if(hybrid_item_list!=null && hybrid_item_list.size()>0){
                 Hybrid_Item_Table  hybrid_item_table= hybrid_item_list.get(position);
                 if(hybrid_item_table!=null){
                     hybrid_code=hybrid_item_table.getNo();
                     hybrid_string=hybrid_item_table.getAlias_Name();
                     ac_hybrid_name.setText(hybrid_code);
                 }
                }

            });


        if (flagOfAction.equalsIgnoreCase("insert_line")) {
            save_line_btn.setVisibility(View.VISIBLE);
            update_btn.setVisibility(View.GONE);

            save_line_btn.setOnClickListener(v1 -> {
                if (ac_crop_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter crop name.", Toast.LENGTH_SHORT).show();
                } else if (no_of_bags_female.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter no of bag.", Toast.LENGTH_SHORT).show();
                } else if (no_of_bags_female.getText().toString().trim().equalsIgnoreCase(".") ||
                        no_of_bags_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                        no_of_bags_female.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                } else if (ed_female_pack_size.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter female pack size", Toast.LENGTH_SHORT).show();
                } else if (ed_female_pack_size.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_female_pack_size.getText().toString().trim().equalsIgnoreCase(".0") ||
                        ed_female_pack_size.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                } else if (feamle_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter female qty", Toast.LENGTH_SHORT).show();
                } else if (feamle_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                        feamle_qty.getText().toString().trim().equalsIgnoreCase(".0") ||
                        feamle_qty.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct female qty", Toast.LENGTH_SHORT).show();
                } else if (no_of_male_bags.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter no of male bags", Toast.LENGTH_SHORT).show();
                } else if (no_of_male_bags.getText().toString().trim().equalsIgnoreCase(".") ||
                        no_of_male_bags.getText().toString().trim().equalsIgnoreCase(".0") ||
                        no_of_male_bags.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct input of male bags.", Toast.LENGTH_SHORT).show();
                } else if (pack_size_male.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male pack size", Toast.LENGTH_SHORT).show();
                } else if (pack_size_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        pack_size_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        pack_size_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male pack size", Toast.LENGTH_SHORT).show();
                } else if (qty_male.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter male qty", Toast.LENGTH_SHORT).show();
                } else if (qty_male.getText().toString().trim().equalsIgnoreCase(".") ||
                        qty_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                        qty_male.getText().toString().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct male qty", Toast.LENGTH_SHORT).show();
                } else if (ac_male_lot.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select male bulk lot no.", Toast.LENGTH_SHORT).show();
                }
                else  if(hybrid_code.equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Hybrid no. is blank.", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        FSReturnHarvestingModel.FSReturnHarvestingLineModel fsReturnHarvestingLineModel = new FSReturnHarvestingModel().new FSReturnHarvestingLineModel();
                        fsReturnHarvestingLineModel.fs_return_code = fsReturnHarvestingModel.fs_return_code;
                        fsReturnHarvestingLineModel.crop = crop_code != null ? crop_code : "";
                        fsReturnHarvestingLineModel.crop_name=ac_crop_name.getText().toString().trim();
                        fsReturnHarvestingLineModel.hybrid = hybrid_code;
                        fsReturnHarvestingLineModel.hybrid_name=ac_hybrid_name.getText().toString().trim();
                        fsReturnHarvestingLineModel.female_lot_no = female_lot_tble != null ? female_lot_tble.Lot_No : "";
                        fsReturnHarvestingLineModel.female_no_of_bags = no_of_bags_female.getText().toString().trim();
                        fsReturnHarvestingLineModel.female_pack_size = ed_female_pack_size.getText().toString().trim();
                        fsReturnHarvestingLineModel.female_quantity = feamle_qty.getText().toString().trim();
                        fsReturnHarvestingLineModel.male_lot_no = male_lot_table != null ? male_lot_table.Lot_No : "";
                        fsReturnHarvestingLineModel.male_no_of_bags = no_of_male_bags.getText().toString().trim();
                        fsReturnHarvestingLineModel.male_pack_size = pack_size_male.getText().toString().trim();
                        fsReturnHarvestingLineModel.male_quantity = qty_male.getText().toString().trim();
                        fsReturnHarvestingLineModel.male_bulk_lot_no = male_bulk_lotno.getText().toString().trim();
                        fsReturnHarvestingLineModel.dispatch_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_dispatch_date.getText().toString().trim());
                        fsReturnHarvestingLineModel.physical_condition = ac_physical_cond.getText().toString().trim();
                        fsReturnHarvestingLineModel.remarks = ed_remarks.getText().toString().trim();
                        fsReturnHarvestingLineModel.created_by = sessionManagement.getUserEmail();
                        fsReturnHarvestingLineModel.approver_id = sessionManagement.getApprover_id();

                        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                            LoadingDialog progressDialog = new LoadingDialog();
                            progressDialog.showLoadingDialog(getActivity());
                            String jsonString = new Gson().toJson(fsReturnHarvestingLineModel);
                            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                            Log.e("json_insert", asJsonObject.toString());
                            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                            Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call = mAPIService.insert_Update_fs_return_line(asJsonObject);
                            call.enqueue(new Callback<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>>() {
                                @Override
                                public void onResponse(Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call, Response<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            progressDialog.hideDialog();
                                            List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> insert_response_list = response.body();
                                            if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                fs_retrun_line_list = insert_response_list;
                                                //todo bind Adapter......
                                                bindDataWithAdapter(fs_retrun_line_list, fs_return_line_listview);
                                                Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                dialog1.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            progressDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_fs_retrun_line", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call, Throwable t) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_fs_retrun_line", getActivity());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            if (flagOfAction.equalsIgnoreCase("updateLine")) {
                save_line_btn.setVisibility(View.GONE);
                update_btn.setVisibility(View.VISIBLE);

                if (fs_return_line.crop != null) {
                    ac_crop_name.setText(fs_return_line.crop_name);
                    crop_code=fs_return_line.crop;
                } else {
                    ac_crop_name.setText("");
                }

                if(hybrid_item_table!=null){
                    ac_hybrid_name.setText(hybrid_item_table.getNo());
                    hybrid_code=hybrid_item_table.getNo();
                }
                else {
                    ac_hybrid_name.setText("");
                }

                ed_remarks.setText(fs_return_line.remarks);
                ac_physical_cond.setText(fs_return_line.physical_condition);
                ac_female_lot_no.setText(fs_return_line.female_lot_no);
                ac_male_lot.setText(fs_return_line.male_lot_no);

                ed_female_pack_size.setText(fs_return_line.female_pack_size);
                no_of_bags_female.setText(fs_return_line.female_no_of_bags);
                feamle_qty.setText(fs_return_line.female_quantity);
                qty_male.setText(fs_return_line.male_quantity);
                no_of_male_bags.setText(fs_return_line.male_no_of_bags);
                pack_size_male.setText(fs_return_line.male_pack_size);
                male_bulk_lotno.setText(fs_return_line.male_bulk_lot_no);

                if(fs_return_line.dispatch_date!=null){
                  ed_dispatch_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(fs_return_line.dispatch_date));
                }

                update_btn.setOnClickListener(v1 -> {

                    if (no_of_bags_female.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter no of bag.", Toast.LENGTH_SHORT).show();
                    } else if (no_of_bags_female.getText().toString().trim().equalsIgnoreCase(".") ||
                            no_of_bags_female.getText().toString().trim().equalsIgnoreCase(".0") ||
                            no_of_bags_female.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                    } else if (ed_female_pack_size.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter female pack size", Toast.LENGTH_SHORT).show();
                    } else if (ed_female_pack_size.getText().toString().trim().equalsIgnoreCase(".") ||
                            ed_female_pack_size.getText().toString().trim().equalsIgnoreCase(".0") ||
                            ed_female_pack_size.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                    } else if (feamle_qty.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter female qty", Toast.LENGTH_SHORT).show();
                    } else if (feamle_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                            feamle_qty.getText().toString().trim().equalsIgnoreCase(".0") ||
                            feamle_qty.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct female qty", Toast.LENGTH_SHORT).show();
                    } else if (no_of_male_bags.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter no of male bags", Toast.LENGTH_SHORT).show();
                    } else if (no_of_male_bags.getText().toString().trim().equalsIgnoreCase(".") ||
                            no_of_male_bags.getText().toString().trim().equalsIgnoreCase(".0") ||
                            no_of_male_bags.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct input of male bags.", Toast.LENGTH_SHORT).show();
                    } else if (pack_size_male.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter male pack size", Toast.LENGTH_SHORT).show();
                    } else if (pack_size_male.getText().toString().trim().equalsIgnoreCase(".") ||
                            pack_size_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                            pack_size_male.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct male pack size", Toast.LENGTH_SHORT).show();
                    } else if (qty_male.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter male qty", Toast.LENGTH_SHORT).show();
                    } else if (qty_male.getText().toString().trim().equalsIgnoreCase(".") ||
                            qty_male.getText().toString().trim().equalsIgnoreCase(".0") ||
                            qty_male.getText().toString().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct male qty", Toast.LENGTH_SHORT).show();
                    } else if (ac_male_lot.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please select male bulk lot no.", Toast.LENGTH_SHORT).show();
                    }
                    else  if(hybrid_code.equalsIgnoreCase("")){
                        Toast.makeText(getActivity(), "Hybrid no. is blank.", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            FSReturnHarvestingModel.FSReturnHarvestingLineModel fsReturnHarvestingLineModel = new FSReturnHarvestingModel().new FSReturnHarvestingLineModel();
                            fsReturnHarvestingLineModel.fs_return_code = fsReturnHarvestingModel.fs_return_code;
                            fsReturnHarvestingLineModel.line_no = fs_return_line.line_no;
                            fsReturnHarvestingLineModel.crop = crop_code != null ? crop_code : "";
                            fsReturnHarvestingLineModel.crop_name=ac_crop_name.getText().toString().trim();
                            fsReturnHarvestingLineModel.hybrid = hybrid_code;
                            fsReturnHarvestingLineModel.hybrid_name=ac_hybrid_name.getText().toString().trim();
                            fsReturnHarvestingLineModel.female_lot_no = female_lot_tble != null ? female_lot_tble.Lot_No : "";
                            fsReturnHarvestingLineModel.female_no_of_bags = no_of_bags_female.getText().toString().trim();
                            fsReturnHarvestingLineModel.female_pack_size = ed_female_pack_size.getText().toString().trim();
                            fsReturnHarvestingLineModel.female_quantity = feamle_qty.getText().toString().trim();
                            fsReturnHarvestingLineModel.male_lot_no = male_lot_table != null ? male_lot_table.Lot_No : "";
                            fsReturnHarvestingLineModel.male_no_of_bags = no_of_male_bags.getText().toString().trim();
                            fsReturnHarvestingLineModel.male_pack_size = pack_size_male.getText().toString().trim();
                            fsReturnHarvestingLineModel.male_quantity = qty_male.getText().toString().trim();
                            fsReturnHarvestingLineModel.male_bulk_lot_no = male_bulk_lotno.getText().toString().trim();
                            fsReturnHarvestingLineModel.dispatch_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_dispatch_date.getText().toString().trim());
                            fsReturnHarvestingLineModel.physical_condition = ac_physical_cond.getText().toString().trim();
                            fsReturnHarvestingLineModel.remarks = ed_remarks.getText().toString().trim();
                            fsReturnHarvestingLineModel.created_by = sessionManagement.getUserEmail();
                            fsReturnHarvestingLineModel.approver_id = sessionManagement.getApprover_id();

                            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                                LoadingDialog progressDialog = new LoadingDialog();
                                progressDialog.showLoadingDialog(getActivity());
                                String jsonString = new Gson().toJson(fsReturnHarvestingLineModel);
                                JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                                Log.e("json_insert", asJsonObject.toString());
                                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                                Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call = mAPIService.insert_Update_fs_return_line(asJsonObject);
                                call.enqueue(new Callback<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>>() {
                                    @Override
                                    public void onResponse(Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call, Response<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> response) {
                                        try {
                                            if (response.isSuccessful()) {
                                                progressDialog.hideDialog();
                                                List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> insert_response_list = response.body();
                                                if (insert_response_list != null && insert_response_list.size() > 0 && insert_response_list.get(0).condition) {
                                                    fs_retrun_line_list = insert_response_list;
                                                    //todo bind Adapter......
                                                    bindDataWithAdapter(fs_retrun_line_list, fs_return_line_listview);
                                                    dialog1.dismiss();
                                                    Toast.makeText(getActivity(), insert_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), insert_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                progressDialog.hideDialog();
                                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            progressDialog.hideDialog();
                                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_fs_retrun_line", getActivity());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> call, Throwable t) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_fs_retrun_line", getActivity());
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Please wait for online connection. ", Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void bindDataWithAdapter(List<FSReturnHarvestingModel.FSReturnHarvestingLineModel> insert_response_list, ListView fs_return_line_listview) {
        fsReturnLineAdapter = new FsReturnLineAdapter(getActivity(), insert_response_list);
        fs_return_line_listview.setAdapter(fsReturnLineAdapter);
    }


    private void getLocation(ProgressBar content_loading, AutoCompleteTextView ac_location){
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
                            locationMasterTableLis=user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, locationMasterTableLis);
                            ac_location.setAdapter(locationMasterAdapter);
                        }
                        else {
                            ac_location.setAdapter(null);
                        }
                    }
                    else {
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

    private void getSeasonMaster(ProgressBar content_loading,AutoCompleteTextView ac_season_code) {
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
                        if (templantingList_season!=null && templantingList_season.size() > 0) {
                            seasonMasterTableList=templantingList_season;
                            SeasonMasterAdapter locationMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterTableList);
                            ac_season_code.setAdapter(locationMasterAdapter);
                        } else {
                            content_loading.setVisibility(View.GONE);
                            ac_season_code.setText("");
                            ac_season_code.setAdapter(null);
                            Toast.makeText(getActivity(), templantingList_season!=null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
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

    private void getPlantingLotList(MaterialProgressBar loading_content, AutoCompleteTextView ac_male,AutoCompleteTextView ac_female,
                                    AutoCompleteTextView ac_bluk,  String sub_type,String flag) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData("",sub_type,flag);
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                if (response.isSuccessful()) {
                    loading_content.setVisibility(View.GONE);
                    PlantingLotModel plantingLotModel = response.body();
                    if (plantingLotModel!=null && plantingLotModel.condition) {
                        List<PlantingLotModel.Data> planting_lot_list= plantingLotModel.data;
                        if(planting_lot_list!=null && planting_lot_list.size()>0) {
                             planting_male_lotList = planting_lot_list;
                             planting_female_lotList = planting_lot_list;
                             planting_lot_master_tableList =planting_lot_list;

                            if(planting_lot_master_tableList!=null && planting_lot_master_tableList.size()>0){
                                PlantingLotMFOAdapter plantingLotMFOAdapter=new PlantingLotMFOAdapter(getActivity(),R.layout.item_view,planting_male_lotList);
                                ac_male.setAdapter(plantingLotMFOAdapter);
                            }
                            else {
                                ac_male.setAdapter(null);
                            }

                            if(planting_lot_master_tableList!=null && planting_lot_master_tableList.size()>0){
                                PlantingLotMFOAdapter plantingLotMFOAdapter=new PlantingLotMFOAdapter(getActivity(),R.layout.item_view,planting_female_lotList);
                                ac_female.setAdapter(plantingLotMFOAdapter);
                            }
                            else {
                                ac_female.setAdapter(null);
                            }

                            if(planting_lot_master_tableList!=null && planting_lot_master_tableList.size()>0){
                                PlantingLotMFOAdapter plantingLotMFOAdapter=new PlantingLotMFOAdapter(getActivity(),R.layout.item_view,planting_lot_master_tableList);
                                ac_bluk.setAdapter(plantingLotMFOAdapter);
                            }
                            else {
                                ac_bluk.setAdapter(null);
                            }
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),  "Planting Lot List Record not found !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loading_content.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantingLotModel>call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_lot_list", getActivity());
            }
        });
    }

    private void getCropMaster(MaterialProgressBar content_loading, AutoCompleteTextView ac_crop){
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
                        if (cropMassterModel!=null && cropMassterModel.condition) {
                            List<CropMassterModel.Data> crop_master_list= cropMassterModel.data;
                            if(crop_master_list!=null && crop_master_list.size()>0) {
                                crop_list=crop_master_list;
                                if(crop_list.size()>0){
                                    CropHytechAdapter cropHytechAdapter=new CropHytechAdapter(getActivity(),R.layout.android_item_view, crop_list);
                                    ac_crop.setAdapter(cropHytechAdapter);
                                }
                                else {
                                    ac_crop.setAdapter(null);
                                }
                            }
                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  "Crop Master Record not found !", Toast.LENGTH_SHORT).show();
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
                }
            }

            @Override
            public void onFailure(Call<CropMassterModel>call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });
    }



    private void getFsioBsioSaleOrderNo(MaterialProgressBar content_loading, AutoCompleteTextView ac_sale_no,String doc_type){
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<FsioBsioSaleOrderNoModel> call = mAPIService.getFsioBsioSaleOrderNo(doc_type);
        call.enqueue(new Callback<FsioBsioSaleOrderNoModel>() {
            @Override
            public void onResponse(Call<FsioBsioSaleOrderNoModel> call, Response<FsioBsioSaleOrderNoModel> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        FsioBsioSaleOrderNoModel cropMassterModel = response.body();
                        if (cropMassterModel!=null && cropMassterModel.condition) {
                            List<FsioBsioSaleOrderNoModel.Data> crop_master_list= cropMassterModel.data;
                            if(crop_master_list!=null && crop_master_list.size()>0) {
                                     fsio_bsio_sale_list=crop_master_list;
                                    if (fsio_bsio_sale_list != null && fsio_bsio_sale_list.size() > 0) {
                                        FsioBsioSaleOrderNoAdapter fsioBsioSaleOrderNoAdapter = new FsioBsioSaleOrderNoAdapter(getActivity(), R.layout.android_item_view, fsio_bsio_sale_list);
                                        ac_sale_no.setAdapter(fsioBsioSaleOrderNoAdapter);
                                     }
                                   else {
                                    ac_sale_no.setAdapter(null);
                                  }
                               }
                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  "Sale Order Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_fsio_bsio_saleorder_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<FsioBsioSaleOrderNoModel>call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_fsio_bsio_saleorder_master", getActivity());
            }
        });
    }
}


