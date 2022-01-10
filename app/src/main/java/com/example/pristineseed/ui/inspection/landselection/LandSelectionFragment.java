package com.example.pristineseed.ui.inspection.landselection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.landselection.LandSelectionDeleteModel;
import com.example.pristineseed.model.landselection.LandSelectionInsertModel;
import com.example.pristineseed.model.landselection.LandSelectionModel;
import com.example.pristineseed.model.landselection.LandSelectionUpdateModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.land_selection.LandSelectionListAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandSelectionFragment extends Fragment {

    private int page_init=0;
    private String zone_string="",season_string="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_land_selection, container, false);
    }

    SwipeRefreshLayout swipe_refresh_layout;
    Chip add_newItem;
    private SessionManagement sessionManagement;
    private ListView listview;
    private List<LandSelectionModel> landSelection_master_List=null;
    private LandSelectionListAdapter landSelectionListAdapter=null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        listview = view.findViewById(R.id.listview);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        add_newItem = view.findViewById(R.id.add_newItem);

        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
        if(isNetwork){
            getLandSelectionData(page_init);
        }

        add_newItem.setOnClickListener(view1 -> {
             AddNewItemPopup("Insert",null);
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletePopup(landSelection_master_List.get(position).land_selection_code,position);
                return true;
            }
        });

        listview.setOnItemClickListener((parent, view1, position, id) -> {

            if(landSelection_master_List!=null && landSelection_master_List.size()>0) {
                LandSelectionModel selectionModel = landSelection_master_List.get(position);
                AddNewItemPopup("Update",selectionModel);
            }
        });


        //todo go to infinite list....
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount)) {
                   // page_init++;
                   // Log.e("page",String.valueOf(page_init));
                }
            }
        });

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Stop animation (This will be after 3 seconds)
                    landSelection_master_List.clear();
                    if(isNetwork) {
                        getLandSelectionData(page_init);
                        swipe_refresh_layout.setRefreshing(false);
                    }
                }
            }, 3000); // Delay in millis
        });
        swipe_refresh_layout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

    }

    private ZoneMasterTable zoneMasterTable=null;
    private List<SeasonMasterModel> seasonMasterTableList=null;
    private SeasonMasterModel seasonMasterTable=null;
    private String zone_code="",season_code="",previous_crop_code="";
    private String previous_crop_string="";
    private List<ZoneMasterTable> zoneMasterTableList=null;
    private List<CropMassterModel.Data> hytechCropMasterTableList =null;

    public void AddNewItemPopup(String flag, LandSelectionModel landSelectionModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_land_selection_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        //todo Text input edit text
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);
        TextInputEditText edt_location = popupView.findViewById(R.id.ed_location);
        TextInputEditText edt_village = popupView.findViewById(R.id.ed_village);
        TextInputEditText edt_block_name = popupView.findViewById(R.id.ed_block_name);
        TextInputEditText edt_acreage = popupView.findViewById(R.id.ed_average);
        TextInputEditText edt_remark = popupView.findViewById(R.id.ed_remarks);
        AutoCompleteTextView ac_history = popupView.findViewById(R.id.ac_history);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        Button update_btn = popupView.findViewById(R.id.update_btn);
        TextInputEditText ed_farmer_father=popupView.findViewById(R.id.ed_farmer_father);
        TextInputEditText ed_farmer_name=popupView.findViewById(R.id.ed_farmer_name);
        AutoCompleteTextView ac_previous=popupView.findViewById(R.id.ac_previous);
        MaterialProgressBar content_loading=popupView.findViewById(R.id.content_loading);
        getSeasonMaster(content_loading,ac_season);
        AutoCompleteTextView ac_history_prevs_crop = popupView.findViewById(R.id.ac_history_crop);
        ItemAdapter history_adapter=new ItemAdapter(getActivity(),R.layout.android_item_view, Arrays.asList(CommonData.option_history));
        ac_history.setAdapter(history_adapter);
        ItemAdapter ac_prvs_history_adapter=new ItemAdapter(getActivity(),R.layout.android_item_view,Arrays.asList(CommonData.history_on_prvs_crop));
        ac_history_prevs_crop.setAdapter(ac_prvs_history_adapter);
        getCropMaster(content_loading,ac_previous);

        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
        try{
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
           // CropHytechMasterDao cropHytechMasterDao=pristineDatabase.cropHytechMasterDao();
             zoneMasterTableList = zoneMaterDao.getAllData();
            // hytechCropMasterTableList =cropHytechMasterDao.getAllData();

             if(landSelectionModel!=null) {
                 if (landSelectionModel.zone != null) {
                     ZoneMasterTable zoneMasterTable = zoneMaterDao.getZoneNameBycode(landSelectionModel.zone);
                     if (zoneMasterTable != null) {
                         zone_string = zoneMasterTable.getDescription();
                         zone_code = zoneMasterTable.getCode();
                     }
                 }
                /* if(landSelectionModel.previous_crop!=null){
                     CropHytechMasterTable cropHytechMasterTable=cropHytechMasterDao.getCropByCodeName(landSelectionModel.previous_crop);
                     if(cropHytechMasterTable!=null){
                         previous_crop_string=cropHytechMasterTable.getDescription();
                         previous_crop_code=cropHytechMasterTable.getCode();
                     }
                 }*/
             }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            /*if(hytechCropMasterTableList !=null && hytechCropMasterTableList.size()>0){
                CropHytechAdapter cropHytechAdapter=new CropHytechAdapter(getActivity(),R.layout.android_item_view, hytechCropMasterTableList);
                ac_previous.setAdapter(cropHytechAdapter);
            }
            else {
                ac_previous.setAdapter(null);
            }*/
        }

        AutoCompleteTextView dropdown_city_zone = popupView.findViewById(R.id.drop_dwn_zone);
        ZoneMasterAdapter stateAdapter = new ZoneMasterAdapter(getActivity(), R.layout.drop_down_textview, zoneMasterTableList);
        dropdown_city_zone.setAdapter(stateAdapter);

        ac_previous.setOnItemClickListener((parent, view, position, id) -> {
            if(hytechCropMasterTableList!=null && hytechCropMasterTableList.size()>0) {
                previous_crop_string = hytechCropMasterTableList.get(position).Description;
                ac_previous.setText(previous_crop_string);
                previous_crop_code = hytechCropMasterTableList.get(position).Code;
            }
        });

        ac_season.setOnItemClickListener((parent, view, position, id) -> {
            seasonMasterTable=seasonMasterTableList.get(position);
             if(seasonMasterTable!=null){
                ac_season.setText(seasonMasterTable.description);
                 season_code=seasonMasterTable.season_Code;
            }
            else {
                ac_season.setText("");
            }
        });

        dropdown_city_zone.setOnItemClickListener((parent, view, position, id) -> {
            zoneMasterTable = zoneMasterTableList.get(position);
            if(zoneMasterTable!=null){
                zone_code=zoneMasterTableList.get(position).getCode();
                dropdown_city_zone.setText(zoneMasterTable.getDescription());
            }

        });
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });
        //todo api hit for insert data
        if (flag.equalsIgnoreCase("Insert")) {
            filter_apply_bt.setOnClickListener(view -> {
                  if (ac_season.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter season !", Toast.LENGTH_SHORT).show();
//                    ac_season.requestFocus();
                    return;
                    } else if (ac_history_prevs_crop.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter history previous crop", Toast.LENGTH_SHORT).show();
                    return;
                    } else if (edt_village.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(getActivity(), "Please enter village", Toast.LENGTH_SHORT).show();
                    return;
                    }
                   else if (dropdown_city_zone.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select zone", Toast.LENGTH_SHORT).show();
                    return;
                    }
                   else if(ed_farmer_name.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter farmer name.", Toast.LENGTH_SHORT).show();
                    return;
                    }
                    else if(ed_farmer_father.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter farmer father name.", Toast.LENGTH_SHORT).show();
                    return;
                    }
                   else {
                    LandSelectionModel landSelectionGetModel = new LandSelectionModel();
                    try {
                        landSelectionGetModel.season = season_code != null ? season_code : "";
                        landSelectionGetModel.season_name=ac_season.getText().toString().trim();
                        landSelectionGetModel.location = edt_location.getText().toString();
                        landSelectionGetModel.village = edt_village.getText().toString();
                        landSelectionGetModel.block_name = edt_block_name.getText().toString();
                        landSelectionGetModel.history_on_previous_crop = ac_history_prevs_crop.getText().toString();
                        landSelectionGetModel.acreage = edt_acreage.getText().toString();
                        landSelectionGetModel.remark = edt_remark.getText().toString();
                        landSelectionGetModel.zone = zone_code != null ? zone_code : "";
                        landSelectionGetModel.zone_name=dropdown_city_zone.getText().toString();
                        landSelectionGetModel.history = "";
                        landSelectionGetModel.created_by = sessionManagement.getUserEmail();
                        landSelectionGetModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                        landSelectionGetModel.farmer_name=ed_farmer_father.getText().toString().trim();
                        landSelectionGetModel.farmer_father_name=ed_farmer_father.getText().toString().trim();
                        landSelectionGetModel.previous_crop=previous_crop_code!=null?previous_crop_code:"";
                        landSelectionGetModel.crop_name=ac_previous.getText().toString().trim();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
                    if (isNetwork) {
                        JsonObject postedJsonInsert = JsonParser.parseString(new Gson().toJson(landSelectionGetModel)).getAsJsonObject();
                        LoadingDialog loadingDialog = new LoadingDialog();
                        loadingDialog.showLoadingDialog(getActivity());
                        NetworkInterface  mApiServices = ApiUtils.getPristineAPIService();
                        Call<List<LandSelectionInsertModel>> landSelectionInsert = mApiServices.postLandSelectionInsert(postedJsonInsert);

                        landSelectionInsert.enqueue(new Callback<List<LandSelectionInsertModel>>() {
                            @Override
                            public void onResponse(Call<List<LandSelectionInsertModel>> call, Response<List<LandSelectionInsertModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        List<LandSelectionInsertModel> tempLandInsertModel = response.body();
                                        if (tempLandInsertModel!=null && tempLandInsertModel.size() > 0 && tempLandInsertModel.get(0).condition) {
                                            dialog.dismiss();
                                            getLandSelectionData(page_init);
                                        } else {
                                            Toast.makeText(getActivity(), tempLandInsertModel.size() > 0 ? tempLandInsertModel.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        loadingDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                   } catch (Exception e) {
                                    e.printStackTrace();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Insert_Land_Selection ", getActivity());
                                } finally {
                                    loadingDialog.hideDialog();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<LandSelectionInsertModel>> call, Throwable t) {
                                loadingDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert_Land_Selection", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

       else if(flag.equalsIgnoreCase("Update")){
            update_btn.setVisibility(View.VISIBLE);
            filter_apply_bt.setVisibility(View.GONE);
            //todo get offline data from database and sync it
            edt_location.setText(landSelectionModel.location);
            edt_village.setText(landSelectionModel.village);
            edt_block_name.setText(landSelectionModel.block_name);
            ac_history_prevs_crop.setText(landSelectionModel.history_on_previous_crop);
            if(landSelectionModel.acreage!=null) {
                edt_acreage.setText(landSelectionModel.acreage);
            }
            edt_remark.setText(landSelectionModel.remark);
            ac_history.setText(landSelectionModel.history);
            ed_farmer_name.setText(landSelectionModel.farmer_name);
            ed_farmer_name.setSelection(ed_farmer_name.getText().length());
            ed_farmer_father.setText(landSelectionModel.farmer_father_name);

            if(landSelectionModel.previous_crop!=null){
                ac_previous.setText(landSelectionModel.previous_crop);
                previous_crop_code=landSelectionModel.previous_crop;
            }

           if(landSelectionModel.season != null && !landSelectionModel.season.equalsIgnoreCase("")){
               ac_season.setText(landSelectionModel.season);
               season_code = landSelectionModel.season;
           }

            if(landSelectionModel.zone!=null && !landSelectionModel.zone.equalsIgnoreCase("")){
                dropdown_city_zone.setText(zone_string);
            }
            update_btn.setOnClickListener(v -> {
                LandSelectionModel landSelectionGetModel = new LandSelectionModel();
                try {
                    landSelectionGetModel.land_selection_code = landSelectionModel.land_selection_code;
                    landSelectionGetModel.season = season_code;
                    landSelectionGetModel.season_name=ac_season.getText().toString().trim();
                    landSelectionGetModel.location = edt_location.getText().toString();
                    landSelectionGetModel.village = edt_village.getText().toString();
                    landSelectionGetModel.block_name = edt_block_name.getText().toString();
                    landSelectionGetModel.history_on_previous_crop = ac_history_prevs_crop.getText().toString();
                    landSelectionGetModel.acreage = edt_acreage.getText().toString();
                    landSelectionGetModel.remark = edt_remark.getText().toString();
                    landSelectionGetModel.zone = zone_code;
                    landSelectionGetModel.zone_name=dropdown_city_zone.getText().toString().trim();
                    landSelectionGetModel.history = ac_history.getText().toString();
                    landSelectionGetModel.updated_by = sessionManagement.getUserEmail();
                    landSelectionGetModel.updated_on = DateTimeUtilsCustome.splitDateInYYYMMDD(DateTimeUtilsCustome.getCurrentDateBY_());
                    landSelectionGetModel.farmer_name=landSelectionModel.farmer_name;
                    landSelectionGetModel.farmer_father_name=landSelectionModel.farmer_father_name;
                    landSelectionGetModel.previous_crop=previous_crop_code!=null?previous_crop_code:"";
                    landSelectionGetModel.crop_name=ac_previous.getText().toString().trim();

                }catch (Exception e){
                    e.printStackTrace();
                }
                boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
                //todo Post Api hit for update data
                if(isNetwork){
                    JsonObject postedJsonUpdate = new JsonParser().parse(new Gson().toJson(landSelectionGetModel)).getAsJsonObject();
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.showLoadingDialog(getActivity());
                    NetworkInterface  mApiServices = ApiUtils.getPristineAPIService();
                    Call<List<LandSelectionUpdateModel>> updateLandSelection = mApiServices.postLandSelectionUpdate(postedJsonUpdate);
                    updateLandSelection.enqueue(new Callback<List<LandSelectionUpdateModel>>() {
                        @Override
                        public void onResponse(Call<List<LandSelectionUpdateModel>> call, Response<List<LandSelectionUpdateModel>> response) {
                            try{
                                if(response.isSuccessful()){
                                    loadingDialog.hideDialog();
                                    List<LandSelectionUpdateModel> tempUpdate = response.body();
                                    if(tempUpdate!=null && tempUpdate.size()>0 && tempUpdate.get(0).condition){
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), tempUpdate.get(0).message, Toast.LENGTH_SHORT).show();
                                        getLandSelectionData(page_init);
                                    }else {
                                        Toast.makeText(getActivity(), tempUpdate.size() > 0 ? tempUpdate.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    loadingDialog.hideDialog();
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                loadingDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Update_Land_selection", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<LandSelectionUpdateModel>> call, Throwable t) {
                            loadingDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Update_Land_selection", getActivity());
                        }
                    });
                }else {
                  Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void bindLandSelectionDataWithLocal(List<LandSelectionModel> landSelectionGetModelslist) {
        landSelectionListAdapter = new LandSelectionListAdapter(getActivity(), landSelectionGetModelslist);
        listview.setAdapter(landSelectionListAdapter);
    }

    private void getLandSelectionData(int page_init) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(getActivity());
        JsonObject postedjson = new JsonObject();
        postedjson.addProperty("query_parameter","");
        postedjson.addProperty("RowsPerPage","500");
        postedjson.addProperty("PageNumber",page_init);
        postedjson.addProperty("created_by",sessionManagement.getUserEmail());
        NetworkInterface  mApiServices = ApiUtils.getPristineAPIService();
        Call<List<LandSelectionModel>> landSelectionGet = mApiServices.postLandSelectionGet(postedjson);
        landSelectionGet.enqueue(new Callback<List<LandSelectionModel>>() {
            @Override
            public void onResponse(Call<List<LandSelectionModel>> call, Response<List<LandSelectionModel>> response) {
                try{
                    if(response.isSuccessful()){
                        loadingDialog.hideDialog();
                        List<LandSelectionModel> tempLandSelectionModel = response.body();
                        if(tempLandSelectionModel!=null && tempLandSelectionModel.size()>0 && tempLandSelectionModel.get(0).condition){
                            landSelection_master_List=tempLandSelectionModel;
                            bindLandSelectionDataWithLocal(landSelection_master_List);
                        }else {
                            Toast.makeText(getActivity(), tempLandSelectionModel.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        loadingDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_land_selection", getActivity());
                }
            }
            @Override
            public void onFailure(Call<List<LandSelectionModel>> call, Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_land_selection", getActivity());
            }
        });
    }

    public void SuccessMessage(String event_code) {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = (TextView) successmessaePopupView.findViewById(R.id.order_no);
            TextView mesage_confirm = successmessaePopupView.findViewById(R.id.message_header);

            order_no.setText(event_code);

            ImageView succesessImg = (ImageView) successmessaePopupView.findViewById(R.id.succesessImg);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.success_animation);
            succesessImg.startAnimation(animation);

            dialog.setContentView(successmessaePopupView);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.show();
            Button goBackFromItem = successmessaePopupView.findViewById(R.id.goBackFromItem);
            goBackFromItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkUtil.getConnectivityStatusBoolean(getContext())) {
                        getLandSelectionData(page_init);
                        dialog.dismiss();
                    }
                    // dialog.dismiss();
                }
            });
            //You need to add the following line for this solution to work; thanks skayred
            successmessaePopupView.setFocusableInTouchMode(true);
            successmessaePopupView.requestFocus();
            successmessaePopupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deletePopup(String land_selection_code,int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setIcon(R.drawable.ic_baseline_delete_forever_24);
        builder.setTitle("Do you really want to delete this item"+"("+land_selection_code+")" +"?");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("land_selection_code",land_selection_code);
                        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
                        if(isNetwork){
                            LoadingDialog loadingDialog = new LoadingDialog();
                            loadingDialog.showLoadingDialog(getActivity());
                            NetworkInterface  mApiServices = ApiUtils.getPristineAPIService();
                            Call<List<LandSelectionDeleteModel>> deleteLandSelection = mApiServices.postLandSelectionDelete(jsonObject);
                            deleteLandSelection.enqueue(new Callback<List<LandSelectionDeleteModel>>() {
                                @Override
                                public void onResponse(Call<List<LandSelectionDeleteModel>> call, Response<List<LandSelectionDeleteModel>> response) {
                                    try{
                                        if(response.isSuccessful()){
                                            loadingDialog.hideDialog();
                                            List<LandSelectionDeleteModel> tempDelete = response.body();
                                            if(tempDelete!=null && tempDelete.size()>0&& tempDelete.get(0).condition){
                                                        landSelection_master_List.remove(position);
                                                        Toast.makeText(getActivity(), tempDelete.get(0).message, Toast.LENGTH_SHORT).show();
                                                    }
                                                else {
                                                Toast.makeText(getActivity(), tempDelete.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            loadingDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }

                                    }catch (Exception e){
                                        loadingDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Land_Selection_Delete", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<LandSelectionDeleteModel>> call, Throwable t) {
                                    loadingDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Land_Selection_Delete", getActivity());
                                }
                            });
                        }
                        else {
                            Toast.makeText(getActivity(),"Please wait for internet connection!",Toast.LENGTH_SHORT).show();
                        }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }

    private void getSeasonMaster(ProgressBar master_loading_progress, AutoCompleteTextView ac_season) {
        master_loading_progress.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        master_loading_progress.setVisibility(View.GONE);
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season!=null && templantingList_season.size() > 0) {
                            seasonMasterTableList = templantingList_season;
                            SeasonMasterAdapter seasonMasterAdapter=new SeasonMasterAdapter(getActivity(),R.layout.android_item_view, seasonMasterTableList);
                            ac_season.setAdapter(seasonMasterAdapter);
                        } else {
                            master_loading_progress.setVisibility(View.GONE);
                            ac_season.setText("");
                            ac_season.setAdapter(null);
                            Toast.makeText(getActivity(), templantingList_season!=null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        master_loading_progress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    master_loading_progress.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_season", getActivity());
                } finally {
                    master_loading_progress.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                master_loading_progress.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_season", getActivity());
            }
        });
    }

    private void getCropMaster(MaterialProgressBar content_loading,AutoCompleteTextView ac_crop){
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
                                hytechCropMasterTableList=crop_master_list;
                                if(hytechCropMasterTableList !=null && hytechCropMasterTableList.size()>0){
                                    CropHytechAdapter cropHytechAdapter=new CropHytechAdapter(getActivity(),R.layout.android_item_view, hytechCropMasterTableList);
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
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CropMassterModel>call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });
    }
}
