package com.example.pristineseed.ui.vendor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.Breeder.BreederInsertVistorModel;
import com.example.pristineseed.model.Breeder.BreederListVisitorModel;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingLotMFOAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.BreederVisitListAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotNumberAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BreederVisitorFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SwipeRefreshLayout swipe_refresh_layout;
    private Chip add_newItem;
    private NetworkInterface mAPIService;
    private boolean isNetwork;
    private SessionManagement sessionManagement;
    private ListView listview;
    private int init_page = 0;
    private List<BreederListVisitorModel> breederVisitorList = new ArrayList<>();
    private List<PlantingLotModel.Data> planting_lot_master_tableList = null;
    private PlantingLotModel.Data planting_lot_master_table = null;
    private List<PlantingLineLotListTable> production_lot_list = new ArrayList<>();
    RecyclerView rv_search_lot_num_list;
    MaterialCardView frame_layout_lot_num_card_view;
    TextInputLayout ac_lot_number_layout;
    TextInputEditText ac_lot_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeder_visit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAPIService = ApiUtils.getPristineAPIService();
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        initView(view);

        isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());

        if (isNetwork) {
            getBreederVisitorList(init_page);
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup();
        });

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Stop animation (This will be after 3 seconds)
                    if (breederVisitorList != null && breederVisitorList.size() > 0) {
                        breederVisitorList.clear();
                    }
                    if (isNetwork) {
                        getBreederVisitorList(init_page);
                        swipe_refresh_layout.setRefreshing(true);
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

    private void initView(View view) {
        listview = view.findViewById(R.id.listview);
        add_newItem = view.findViewById(R.id.add_newItem);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private List<ZoneMasterTable> zoneMasterTableList = null;
    private List<Hybrid_Item_Table> hybrid_item_list = null;
    private String zone_code = "";
    private String hybrid_name = "";
    private String hybrid_code = "", season_code = "";
    private List<SeasonMasterModel> season_master_list = null;

    @SuppressLint("ClickableViewAccessibility")
    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_breeder_visit_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);

        ac_lot_number_layout = popupView.findViewById(R.id.ac_lot_number_layout);
        ac_lot_no = popupView.findViewById(R.id.ac_lot_no);
//        AutoCompleteTextView ac_lot_number = popupView.findViewById(R.id.ac_lot_number);
        TextInputEditText ed_breeder_name = popupView.findViewById(R.id.breeder_name);
        rv_search_lot_num_list = popupView.findViewById(R.id.rv_search_lot_num_list);
        frame_layout_lot_num_card_view = popupView.findViewById(R.id.frame_layout_lot_num_card_view);
        TextInputEditText ed_breeder_commt = popupView.findViewById(R.id.breeder_comment);
        TextInputEditText ed_visit_date = popupView.findViewById(R.id.visit_date);
        Button clear_data_btn = popupView.findViewById(R.id.clear_data);
        TextView title_text_ = popupView.findViewById(R.id.title_text_);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        AutoCompleteTextView ac_zone = popupView.findViewById(R.id.ac_zone);
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);
        AutoCompleteTextView ac_hybrid = popupView.findViewById(R.id.ac_hybrid);
        TextInputEditText ed_receipt_no=popupView.findViewById(R.id.ed_receipt_no);
        LinearLayoutManager search_lot_num_manager;

        search_lot_num_manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search_lot_num_list.setLayoutManager(search_lot_num_manager);

//        getPlantingLineListLot(rv_search_lot_num_list,"");

        ed_visit_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_visit_date);
                v.performClick();
                return v.onTouchEvent(event);
                // return true;
            }
        });
        getSeasonMaster(content_loading, ac_season);

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            zoneMasterTableList = zoneMaterDao.getAllData();
            hybrid_item_list = hybridItemMasterDao.getHybridItemLot();
//            hybrid_item_list.clear();
//            hybrid_item_list.addAll(hybridItemMasterDao.getHybridItemLot());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                ZoneMasterAdapter zoneMasterAdapter = new ZoneMasterAdapter(getActivity(), R.layout.android_item_view, zoneMasterTableList);
                ac_zone.setAdapter(zoneMasterAdapter);
            }

            if (hybrid_item_list != null && hybrid_item_list.size() > 0 ) {
                HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_list);
                ac_hybrid.setAdapter(hybridItemAliasNameAdapter);
            }
        }

        ac_zone.setOnItemClickListener((parent, view, position, id) -> {
            if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                ac_zone.setText(zoneMasterTableList.get(position).getDescription());
                ac_zone.setSelection(ac_zone.getText().length());
                zone_code = zoneMasterTableList.get(position).getCode();
            } else {
                ac_zone.setText("");
            }

        });

        ac_hybrid.setOnItemClickListener((parent, view, position, id) -> {
            if (hybrid_item_list != null && hybrid_item_list.size() > 0) {
                hybrid_code = hybrid_item_list.get(position).getNo();
                hybrid_name = hybrid_item_list.get(position).getAlias_Name();
                ac_hybrid.setText(hybrid_name);
                ac_hybrid.setSelection(ac_hybrid.getText().length());
//                getPlantingLotList(content_loading, ac_lot_number, zone_code, season_code, hybrid_code);
            }
        });

        ac_season.setOnItemClickListener((parent, view, position, id) -> {
            if (season_master_list != null && season_master_list.size() > 0) {
                ac_season.setText(season_master_list.get(position).description);
                ac_season.setSelection(ac_season.getText().length());
                season_code = season_master_list.get(position).season_Code;
            } else {
                ac_season.setText("");
            }
        });


      /*  ac_lot_number.setOnItemClickListener((parent, view, position, id) -> {
            planting_lot_master_table = planting_lot_master_tableList.get(position);
        });*/

        clear_data_btn.setOnClickListener(v -> {
            clearAllField(ac_lot_no, ed_breeder_name, ed_breeder_commt, ed_visit_date, title_text_);
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        ed_receipt_no.setText("");

        filter_apply_bt.setOnClickListener(view -> {

            if (ac_lot_no.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter lot no !", Toast.LENGTH_SHORT).show();
            } else if (ed_breeder_name.getText().toString().trim().equalsIgnoreCase("")) {

                Toast.makeText(getActivity(), "Please enter breeder name !", Toast.LENGTH_SHORT).show();
            } else if (ed_breeder_commt.getText().toString().trim().equalsIgnoreCase("")) {

                Toast.makeText(getActivity(), "Please write comment !", Toast.LENGTH_SHORT).show();
            } else if (ed_visit_date.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select date !", Toast.LENGTH_SHORT).show();
            } else {
                insertBreederVisitor(dialog, ac_lot_no.getText().toString().trim(), ed_breeder_name.getText().toString().trim(), ed_breeder_commt.getText().toString().trim(),
                    ed_visit_date.getText().toString().trim()
                        , ac_zone, ac_season, ed_receipt_no);
                //     DateTimeUtilsCustome.splitDateInYYYMMDD(DateTimeUtilsCustome.getCurrentDateBY_())

            }

        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
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
                            season_master_list = templantingList_season;
                            SeasonMasterAdapter locationMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, season_master_list);
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

    private void clearAllField(TextInputEditText ed_lot_number, TextInputEditText ed_breeder_name, TextInputEditText ed_breeder_commt,
                               TextInputEditText ed_visit_date, TextView title_text_) {
        ed_breeder_commt.setText("");
        ed_lot_number.setText("");
        ed_breeder_name.setText("");
        ed_visit_date.setText("");
        title_text_.requestFocus();
    }

    private void insertBreederVisitor(Dialog dialog, String lot_no, String visitor_name, String comment, String visitor_date,
                                      AutoCompleteTextView ac_zone, AutoCompleteTextView ac_season, TextInputEditText ed_receipt_no) {
        if (isNetwork) {
            LoadingDialog loadingDialog = new LoadingDialog();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lot_no",lot_no );// planting_lot_master_table.Lot_No != null ? planting_lot_master_table.Lot_No : ""
            jsonObject.addProperty("visitor_name", visitor_name);
            jsonObject.addProperty("comment", comment);
            jsonObject.addProperty("date_of_visit", visitor_date);
            jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
//            jsonObject.addProperty("created_on", created_on); //String created_on
            jsonObject.addProperty("season_code", season_code);
            jsonObject.addProperty("season_name", ac_season.getText().toString().trim());
            jsonObject.addProperty("zone_code", zone_code);
            jsonObject.addProperty("zone_name", ac_zone.getText().toString().trim());
            jsonObject.addProperty("alias_code", hybrid_code);
            jsonObject.addProperty("alias_name", hybrid_name);
            jsonObject.addProperty("receipt_no",ed_receipt_no.getText().toString().trim() == null ? "0" :"");
            jsonObject.addProperty("document_no", "" );

            loadingDialog.showLoadingDialog(getActivity());
            Call<List<BreederInsertVistorModel>> call = mAPIService.insertBreederVisitor(jsonObject);
            call.enqueue(new Callback<List<BreederInsertVistorModel>>() {
                @Override
                public void onResponse(Call<List<BreederInsertVistorModel>> call, Response<List<BreederInsertVistorModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loadingDialog.hideDialog();
                            List<BreederInsertVistorModel> breederInsertVistorModelList = response.body();
                            if (breederInsertVistorModelList != null && breederInsertVistorModelList.size() > 0 && breederInsertVistorModelList.get(0).condition) {
                                if (breederInsertVistorModelList.get(0).visitor_code != null && !breederInsertVistorModelList.get(0).visitor_code.equals("")) {
                                    SuccessMessage(breederInsertVistorModelList.get(0).visitor_code);
                                    getBreederVisitorList(init_page);
                                    dialog.dismiss();
                                }
                            } else {
                                Toast.makeText(getActivity(), breederInsertVistorModelList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loadingDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        loadingDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insertBreederVisitor", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BreederInsertVistorModel>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insertBreederVisitor", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void SuccessMessage(String event_code) {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = (TextView) successmessaePopupView.findViewById(R.id.order_no);
            TextView mesage_confirm = successmessaePopupView.findViewById(R.id.message_header);
            //mesage_confirm.setText(messgae);
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
                    if (isNetwork) {
                        getBreederVisitorList(init_page);
                        dialog.dismiss();
                    }
                }
            });
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

    private void getBreederVisitorList(int init_page) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(getActivity());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
        jsonObject.addProperty("query_parameter", "");
        jsonObject.addProperty("RowsPerPage", "500");
        jsonObject.addProperty("PageNumber", init_page);

        Call<List<BreederListVisitorModel>> call = mAPIService.getBreederList(jsonObject);
        call.enqueue(new Callback<List<BreederListVisitorModel>>() {
            @Override
            public void onResponse(Call<List<BreederListVisitorModel>> call, Response<List<BreederListVisitorModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        loadingDialog.hideDialog();
                        List<BreederListVisitorModel> breederListVisitorModelList = response.body();
                        if (breederListVisitorModelList != null && breederListVisitorModelList.size() > 0 && breederListVisitorModelList.get(0).condition) {
                            breederVisitorList = breederListVisitorModelList;
                            bindLocalDataWithView(breederVisitorList);
                        } else {
                            Toast.makeText(getActivity(), breederListVisitorModelList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "BreederVisitList", getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<BreederListVisitorModel>> call, Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "BreederVisitList", getActivity());
            }
        });
    }


    private void bindLocalDataWithView(List<BreederListVisitorModel> breederListVisitorModelList) {
        BreederVisitListAdapter approvalAdapter = new BreederVisitListAdapter(getActivity(), breederListVisitorModelList);
        listview.setAdapter(approvalAdapter);
    }


    private void getPlantingLotList(MaterialProgressBar loading_content, AutoCompleteTextView ac_lot_number, String zone_code, String season_code, String hybrid_code) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotListForBreeder(season_code, zone_code, hybrid_code);
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                if (response.isSuccessful()) {
                    loading_content.setVisibility(View.GONE);
                    PlantingLotModel plantingLotModel = response.body();
                    if (plantingLotModel != null && plantingLotModel.condition) {
                        List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                        if (planting_lot_list != null && planting_lot_list.size() > 0) {
                            planting_lot_master_tableList = planting_lot_list;
                            if (planting_lot_master_tableList != null && planting_lot_master_tableList.size() > 0) {
                                PlantingLotMFOAdapter plantingLotMFOAdapter = new PlantingLotMFOAdapter(getActivity(), R.layout.android_item_view, planting_lot_master_tableList);
                                ac_lot_number.setAdapter(plantingLotMFOAdapter);
                            } else {
                                ac_lot_number.setAdapter(null);
                            }
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Planting Lot List Record not found !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loading_content.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                }
               }

            @Override
            public void onFailure(Call<PlantingLotModel> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_lot_list", getActivity());
            }
        });

    }

}