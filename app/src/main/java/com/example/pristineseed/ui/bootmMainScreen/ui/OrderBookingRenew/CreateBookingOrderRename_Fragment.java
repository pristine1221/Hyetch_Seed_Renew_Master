package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.BackgroundTask;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.BookingResponseModel;
import com.example.pristineseed.model.BookingOrder.OrderBookingModel;
import com.example.pristineseed.model.BookingOrder.SalePlanLineModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.ItemVarietyAdapter;
import com.example.pristineseed.ui.adapter.order_book.OrderBookingAdapter;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderApproval.CityMasterAdapter;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderApproval.DistributorMasterAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookingOrderRename_Fragment extends Fragment {

    private ListView boooking_listview;
    private TextView no_record_found;
    private Chip add_book_order;
    private SessionManagement sessionManagement;
    private List<DistricMasterTable> districMasterTableList = null;
    private List<RoleMasterModel.Data> roleMasterTableList = null;
    private List<Hybrid_Item_Table> varity_list = new ArrayList<>();
    private List<CropMassterModel.Data> cropHytechMasterTableList = null;
    private SeasonMasterModel seasonMasterTable = null;
    private DistricMasterTable districMasterTable = null;
    private RoleMasterModel.Data roleMasterTable = null;
    private CropMassterModel.Data cropHytechMasterTable = null;
    private Hybrid_Item_Table hybrid_item_table = null;
    private String postedString;
    private String district_name = "", district_code = "", season_code = "", crop_name, role_name, city, distributor ,distributor_code = "" ,variety_name = "", crop_code = "", varity_code = "";
    private List<OrderBookingModel> orderBooking_list = new ArrayList<>();
    private OrderBookingAdapter orderBookingAdapter = null;
    private List<SeasonMasterModel> seasonMasterModelList = null;
    private List<RoleMasterModel.Data> distributorRoleModelTableList = null;
    private RoleMasterModel.Data distributorRoleModel = null;


    public static int no_series = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_booking_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getContext());
        boooking_listview = view.findViewById(R.id.order_booking_list);
        no_record_found = view.findViewById(R.id.no_data_found);
        add_book_order = view.findViewById(R.id.add_book_order);

        add_book_order.setOnClickListener(v -> {
            addOrderHeader("Insert", null);
        });

        boooking_listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (orderBooking_list != null && orderBooking_list.size() > 0) {
//                if (!orderBooking_list.get(position).status.equalsIgnoreCase("Approve") && !orderBooking_list.get(position).status.equalsIgnoreCase("Reject")) {

                    if (!orderBooking_list.get(position).status.equalsIgnoreCase("First Approve") && !orderBooking_list.get(position).status.equalsIgnoreCase("Final Approve")) {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle("Do you really want to delete this item ?")
                            .setMessage("Booking no : " + orderBooking_list.get(position).booking_no)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteBooking(orderBooking_list.get(position).booking_no, position);
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .show();

                } else {
                    Toast.makeText(getContext(), "You can't delete this line as status is approved", Toast.LENGTH_SHORT).show();
                }
            }

            return true;

        });

        boooking_listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (orderBooking_list != null && orderBooking_list.size() > 0) {
                if (orderBooking_list.get(position).status.equalsIgnoreCase("First Approve")) {
                    addOrderHeader("view_details", orderBooking_list.get(position));
                }
                else if(orderBooking_list.get(position).status.equalsIgnoreCase("Final Approve")){
                    addOrderHeader("view_details", orderBooking_list.get(position));
                }
                else if (orderBooking_list.get(position).status.equalsIgnoreCase("Reject")) {
                    addOrderHeader("update", orderBooking_list.get(position));
                }
                else if(orderBooking_list.get(position).status.equalsIgnoreCase("Pending")){
                    addOrderHeader("update", orderBooking_list.get(position));
                }
                else {
                    Toast.makeText(getContext(), "You can't update line as status is approved/Reject.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        getBookingList();
        super.onResume();
    }

    private MaterialCardView frame_layout_org_list;
    private AutoCompleteTextView ac_district, ac_city, ac_distributor;
    private TextInputLayout search_distributor_layout;

    private void addOrderHeader(String flag, OrderBookingModel updateOrderModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_order_booking_header_pop_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);
        ac_district = popupView.findViewById(R.id.ac_district);
        ac_city = popupView.findViewById(R.id.ac_city);
        ac_distributor = popupView.findViewById(R.id.ac_distributor);
        AutoCompleteTextView ac_crop_code = popupView.findViewById(R.id.ac_crop_code);
        AutoCompleteTextView ac_variety = popupView.findViewById(R.id.ac_variety);
        TextInputEditText ed_varty_pack_size = popupView.findViewById(R.id.ed_varty_pack_size);
        TextInputEditText ed_target = popupView.findViewById(R.id.ed_target);
        TextInputEditText ed_booking_qty = popupView.findViewById(R.id.ed_booking_qty);
        TextInputEditText ed_allotd_per = popupView.findViewById(R.id.ed_allotd_per);
        TextInputEditText ed_booking_alloted_qty = popupView.findViewById(R.id.ed_booking_alloted_qty);
        RadioGroup ed_no_posted = popupView.findViewById(R.id.posted_radio_grp);
        RadioButton poseted_yes = popupView.findViewById(R.id.posted_yes);
        RadioButton posted_no = popupView.findViewById(R.id.posted_no);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        MaterialButton submit_booking_header_btn = popupView.findViewById(R.id.submit_booking_header_btn);
        MaterialButton update_btn = popupView.findViewById(R.id.update_btn);
        MaterialProgressBar master_loading_progress = popupView.findViewById(R.id.master_loading_progress);
        frame_layout_org_list = popupView.findViewById(R.id.frame_layout_org_list);
        ProgressBar search_loading_item = popupView.findViewById(R.id.loading_item);
        RecyclerView lv_cust_dist_list = popupView.findViewById(R.id.lv_cust_dist_list);
        search_distributor_layout = popupView.findViewById(R.id.search_distributor_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);

        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });
        getSeasonMaster(master_loading_progress, ac_season);

        getCropMaster(master_loading_progress, ac_crop_code);

        ac_season.setOnItemClickListener((parent, view, position, id) -> {
            try {
                if (seasonMasterModelList != null && seasonMasterModelList.size() > 0) {
                    seasonMasterTable = seasonMasterModelList.get(position);
                    if (seasonMasterTable != null) {
                        if (seasonMasterTable.description != null) {
                            ac_season.setText(seasonMasterTable.description);
                        }
                        if (seasonMasterTable.season_Code != null) {
                            season_code = seasonMasterTable.season_Code;
                            getSalePlanLine(master_loading_progress, crop_code, varity_code, season_code, ed_target);
                        }
                    } else {
                        ac_season.setText("");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ac_season.setText("");
            }
        });
        new BackgraundThread(getActivity()).execute();

        ed_booking_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ed_booking_qty.getText().toString().trim().equalsIgnoreCase("") &&
                        !ed_allotd_per.getText().toString().trim().equalsIgnoreCase("")) {
                    float booking_qty = Float.parseFloat(ed_booking_qty.getText().toString().trim());
                    float alloted_per = Float.parseFloat(ed_allotd_per.getText().toString().trim());
                    float alloted_boooking_qty = booking_qty * alloted_per / 100;
                    if (!String.valueOf(alloted_boooking_qty).equalsIgnoreCase("")) {
                        ed_booking_alloted_qty.setText(String.valueOf(alloted_boooking_qty));
                    }
                }
            }
        });


        ed_allotd_per.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ed_booking_qty.getText().toString().trim().equalsIgnoreCase("") &&
                        !ed_allotd_per.getText().toString().trim().equalsIgnoreCase("")) {
                    float booking_qty = Float.parseFloat(ed_booking_qty.getText().toString().trim());
                    float alloted_per = Float.parseFloat(ed_allotd_per.getText().toString().trim());

                    float alloted_boooking_qty = booking_qty * alloted_per / 100;
                    if (!String.valueOf(alloted_boooking_qty).equalsIgnoreCase("")) {
                        ed_booking_alloted_qty.setText(String.valueOf(alloted_boooking_qty));
                    }
                }
            }
        });

        ac_district.setOnItemClickListener((parent, view, position, id) -> {
            districMasterTable = districMasterTableList.get(position);
            district_name = districMasterTableList.get(position).getName();
            district_code = districMasterTableList.get(position).getCode();
            ac_district.setText(district_name);
            ac_district.setSelection(ac_district.getText().length());

            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<RoleMasterModel> call = mAPIService.getCitySync("Distributor", district_code,sessionManagement.getSalePersonCode());
            call.enqueue(new Callback<RoleMasterModel>() {
                @Override
                public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            RoleMasterModel roleMasterModelResponse = response.body();
                            if (roleMasterModelResponse != null && roleMasterModelResponse.condition) {
                                List<RoleMasterModel.Data> rolemasterCityList = roleMasterModelResponse.data;
                                if (rolemasterCityList != null && rolemasterCityList.size() > 0) {
                                    roleMasterTableList = rolemasterCityList;
                                    CityMasterAdapter cityMasterAdapter = new CityMasterAdapter(getActivity(), R.layout.android_item_view, roleMasterTableList);
                                    ac_city.setAdapter(cityMasterAdapter);
                                } else {
                                    Toast.makeText(getActivity(), "City Not Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "No List Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), roleMasterTableList.get(0).message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                    //Log.e("error",t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        ac_city.setOnItemClickListener((parent, view, position, id) -> {
            roleMasterTable = roleMasterTableList.get(position);
            if (roleMasterTable != null) {
                city = roleMasterTableList.get(position).city;
//                distributor_code = districMasterTable.getCode();
                ac_city.setText(city);
            }
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<RoleMasterModel> call = mAPIService.getDistributorSync("Distributor", city, sessionManagement.getSalePersonCode());
            call.enqueue(new Callback<RoleMasterModel>() {
                @Override
                public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            RoleMasterModel roleMasterModelResponse = response.body();
                            if (roleMasterModelResponse != null && roleMasterModelResponse.condition) {
                                List<RoleMasterModel.Data> roleMasterDistributorList = roleMasterModelResponse.data;
                                if (roleMasterDistributorList != null && roleMasterDistributorList.size() > 0) {
                                    distributorRoleModelTableList = roleMasterDistributorList;
                                    DistributorMasterAdapter distributorMasterAdapter = new DistributorMasterAdapter(getActivity(), R.layout.android_item_view, distributorRoleModelTableList);
                                    ac_distributor.setAdapter(distributorMasterAdapter);
                                } else {
                                    Toast.makeText(getActivity(), "Distributor not Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "No List Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), distributorRoleModelTableList.get(0).message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                    //Log.e("error",t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        ac_distributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                distributorRoleModel = distributorRoleModelTableList.get(position);
                if (distributorRoleModel != null) {
                    distributor = distributorRoleModelTableList.get(position).name;
                    distributor_code = distributorRoleModelTableList.get(position).no;
                    ac_distributor.setText(distributor);
                }
            }
        });


        ac_crop_code.setOnItemClickListener((parent, view, position, id) -> {
            cropHytechMasterTable = cropHytechMasterTableList.get(position);
            if (cropHytechMasterTable != null) {
                crop_name = cropHytechMasterTableList.get(position).Description != null ? cropHytechMasterTableList.get(position).Description : "";
                ac_crop_code.setText(crop_name);
                crop_code = cropHytechMasterTable.Code != null ? cropHytechMasterTable.Code : "";
                getVarietyCode(cropHytechMasterTable.Code, ac_variety);
                getSalePlanLine(master_loading_progress, crop_code, varity_code, season_code, ed_target);
            }
        });

        ac_variety.setOnItemClickListener((parent, view, position, id) -> {
            if (varity_list != null && varity_list.size() > 0) {
                hybrid_item_table = varity_list.get(position);
                if (hybrid_item_table != null) {
                    variety_name = hybrid_item_table.getItem_Name() != null ? hybrid_item_table.getItem_Name() : "";
                    varity_code = hybrid_item_table.getNo() != null ? hybrid_item_table.getNo() : "";
                    getSalePlanLine(master_loading_progress, crop_code, varity_code, season_code, ed_target);
                } else {
                    ac_variety.setText("");
                }
            } else {
                ac_variety.setText("");
            }

        });

        ed_no_posted.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.posted_yes:
                    postedString = "1";
                    break;

                case R.id.posted_no:
                    postedString = "0";
                    break;
            }
        });

        if (flag.equalsIgnoreCase("insert")) {
            submit_booking_header_btn.setOnClickListener(v -> {
                if (ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("") || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter varity pack size", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("0.") || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase(".0")
                        || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("0")) {
                    Toast.makeText(getActivity(), "Please enter correct varity pack size", Toast.LENGTH_SHORT).show();
                    return;
                } /*else if (ed_target.getText().toString().trim().equalsIgnoreCase("") || ed_target.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter target", Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (ed_booking_qty.getText().toString().trim().equalsIgnoreCase("") || ed_booking_qty.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter booking qty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_booking_qty.getText().toString().trim().equalsIgnoreCase(".0") || ed_booking_qty.getText().toString().trim().equalsIgnoreCase("0.") ||
                        ed_booking_qty.getText().toString().trim().equalsIgnoreCase("0")) {
                    Toast.makeText(getActivity(), "Please enter correct qty.", Toast.LENGTH_SHORT).show();
                    return;
               /* } else if (ed_allotd_per.getText().toString().trim().equalsIgnoreCase("") || ed_allotd_per.getText().toString().trim().equalsIgnoreCase(".")) {
                    Toast.makeText(getActivity(), "Please enter allotted %", Toast.LENGTH_SHORT).show();
                    return;*/
                } else if (ed_no_posted.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Please select posted atleast one ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_season.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter season", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_variety.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter variety", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    OrderBookingModel orderBookingModel = new OrderBookingModel();
                    orderBookingModel.season_code = season_code != null ? season_code : "";
                    orderBookingModel.season_name = ac_season.getText().toString().trim();
                    orderBookingModel.district_code = districMasterTable.getCode() != null ? districMasterTable.getCode() : "";
                    orderBookingModel.district_name = districMasterTable.getName() != null ? districMasterTable.getName() : "";
                    orderBookingModel.distributor_code = distributor_code != null ? distributor_code : "";
                    orderBookingModel.distributor_name = ac_distributor.getText().toString().trim();
                    orderBookingModel.city = ac_city.getText().toString().trim();
                    orderBookingModel.crop_code = cropHytechMasterTable.Code != null ? cropHytechMasterTable.Code : "";
                    orderBookingModel.variety_code = hybrid_item_table.getNo() != null ? hybrid_item_table.getNo() : "";
                    orderBookingModel.variety_name = hybrid_item_table.getItem_Name() != null ? hybrid_item_table.getItem_Name() : "";
                    orderBookingModel.variety_package_size = String.valueOf(Float.parseFloat(ed_varty_pack_size.getText().toString().trim()));
                    orderBookingModel.target = ed_target.getText().toString().trim().equalsIgnoreCase("")? "0":ed_target.getText().toString().trim();
                    orderBookingModel.booking_qty = String.valueOf(Float.parseFloat(ed_booking_qty.getText().toString().trim()));
                    //  orderBookingModel.alotted_percent = String.valueOf(Float.parseFloat(ed_allotd_per.getText().toString().trim()));
                    no_series = no_series + 1;
                    orderBookingModel.no_series = String.valueOf(no_series);
                    orderBookingModel.posted = postedString;
                    orderBookingModel.approver_id = sessionManagement.getApprover_id();
                    orderBookingModel.created_by = sessionManagement.getUserEmail();
                    String jsonString = new Gson().toJson(orderBookingModel);
                    JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<BookingResponseModel>> call = mAPIService.inserBookingOrder(asJsonObject);
                        call.enqueue(new Callback<List<BookingResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<BookingResponseModel> insert_booking_response = response.body();
                                        if (insert_booking_response != null && insert_booking_response.size() > 0 && insert_booking_response.get(0).condition) {
                                            getBookingList();
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), insert_booking_response.get(0).message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), insert_booking_response != null && insert_booking_response.size() > 0 ? "No data found OB" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_booking_list", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_booking_list", getActivity());
                            }

                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        else if (flag.equalsIgnoreCase("update")) {
            update_btn.setVisibility(View.VISIBLE);
            submit_booking_header_btn.setVisibility(View.GONE);

            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                if (updateOrderModel.season_code != null && !updateOrderModel.season_code.equalsIgnoreCase("")) {
                    season_code = updateOrderModel.season_code;
                    ac_season.setEnabled(true);
                    ac_season.setText(updateOrderModel.season_name != null ? updateOrderModel.season_name: "") ;
                }
                if (updateOrderModel.district_code != null && !updateOrderModel.district_code.equalsIgnoreCase("")) {
                    district_code=updateOrderModel.district_code;
                    ac_district.setEnabled(true);
                    ac_district.setText(updateOrderModel.district_name);
                } else {
                    ac_district.setText("");
                    ac_district.setEnabled(true);
                }
                if (updateOrderModel.city != null && !updateOrderModel.city.equalsIgnoreCase("")) {
                    ac_city.setText(updateOrderModel.city != null ? updateOrderModel.city: "");
                    ac_city.setEnabled(true);
                } else {
                    ac_city.setEnabled(true);
                    ac_city.setText("");
                }
                if (updateOrderModel.distributor_code != null && !updateOrderModel.distributor_code.equalsIgnoreCase("")) {
                    distributor_code = updateOrderModel.distributor_code;
                    ac_distributor.setText(updateOrderModel.distributor_name);
                    ac_distributor.setEnabled(true);
                } else {
                    ac_distributor.setText("");
                    ac_distributor.setEnabled(true);
                }
                if (updateOrderModel.crop_code != null && !updateOrderModel.crop_code.equalsIgnoreCase("")) {
                    crop_code=updateOrderModel.crop_code;
                    ac_crop_code.setEnabled(true);
                    ac_crop_code.setText(crop_code);
                } else {
                    ac_crop_code.setText("");
                    ac_crop_code.setEnabled(true);
                }
                if (updateOrderModel.variety_code != null && !updateOrderModel.variety_code.equalsIgnoreCase("")) {
                    varity_code=updateOrderModel.variety_code;
                    ac_variety.setEnabled(true);
//                    ac_variety.setText(updateOrderModel.variety_name!=null? updateOrderModel.variety_name:"");
                    ac_variety.setText(updateOrderModel.variety_code!=null? updateOrderModel.variety_code:"");
                    getVarietyCode(crop_code, ac_variety);
                } else {
                    ac_variety.setText("");
                    ac_variety.setEnabled(true);
                }
                if(updateOrderModel.target!=null){
                    ed_target.setText(updateOrderModel.target);
                    ed_target.setEnabled(true);
                }

                BackgraundThread backgraundThread = new BackgraundThread(getActivity());
                backgraundThread.district_code = updateOrderModel.district_code;
                backgraundThread.variety_code = updateOrderModel.variety_code;
                backgraundThread.distributor_code = updateOrderModel.distributor_code;

                backgraundThread.execute();

                if (districMasterTable != null) {
                    district_name = districMasterTable.getName() != null ? districMasterTable.getName() : "";
                }
                if (hybrid_item_table != null) {
                    variety_name = hybrid_item_table.getItem_Name() != null ? hybrid_item_table.getItem_Name() : "";
                }

                ed_varty_pack_size.setText(updateOrderModel.variety_package_size);
                ed_varty_pack_size.setEnabled(true);
                ed_booking_qty.setText(updateOrderModel.booking_qty);
                ed_booking_qty.setEnabled(true);
                ed_allotd_per.setText(updateOrderModel.alotted_percent);
                ed_allotd_per.setEnabled(true);
                // ed_no_series.setText(updateOrderModel.no_series);
                if (updateOrderModel.posted != null) {
                    if (updateOrderModel.posted.equals("1")) {
                        postedString = updateOrderModel.posted;
                        poseted_yes.setChecked(true);
                        poseted_yes.setEnabled(true);
                    } else if (updateOrderModel.posted.equals("0")) {
                        postedString = updateOrderModel.posted;
                        posted_no.setChecked(true);
                        posted_no.setEnabled(true);
                    }
                }
                update_btn.setOnClickListener(v -> {
                    if (ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("") || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase(".")) {
                        Toast.makeText(getActivity(), "Please enter varity pack size", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("0.") || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase(".0")
                            || ed_varty_pack_size.getText().toString().trim().equalsIgnoreCase("0")) {
                        Toast.makeText(getActivity(), "Please enter correct varity pack size", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_target.getText().toString().trim().equalsIgnoreCase("") || ed_target.getText().toString().trim().equalsIgnoreCase(".")) {
                        Toast.makeText(getActivity(), "Please enter target", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_booking_qty.getText().toString().trim().equalsIgnoreCase("") || ed_booking_qty.getText().toString().trim().equalsIgnoreCase(".")) {
                        Toast.makeText(getActivity(), "Please enter booking qty", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_booking_qty.getText().toString().trim().equalsIgnoreCase(".0") || ed_booking_qty.getText().toString().trim().equalsIgnoreCase("0.") ||
                            ed_booking_qty.getText().toString().trim().equalsIgnoreCase("0")) {
                        Toast.makeText(getActivity(), "Please enter correct qty.", Toast.LENGTH_SHORT).show();
                        return;
                    } /*else if (ed_allotd_per.getText().toString().trim().equalsIgnoreCase("") || ed_allotd_per.getText().toString().trim().equalsIgnoreCase(".")) {
                        Toast.makeText(getActivity(), "Please enter allotted %", Toast.LENGTH_SHORT).show();
                        return;
                    }*/ else if (ed_no_posted.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getActivity(), "Please select posted atleast one ", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ac_season.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter season", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ac_variety.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter variety", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (hybrid_item_table == null || hybrid_item_table.getNo() == null || hybrid_item_table.getNo().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter variety", Toast.LENGTH_SHORT).show();
                    } else {
                        OrderBookingModel orderBookingModel = new OrderBookingModel();
                        try {
                            orderBookingModel.booking_no = updateOrderModel.booking_no;
                            orderBookingModel.season_code = season_code;
                            orderBookingModel.season_name = ac_season.getText().toString().trim();
                            orderBookingModel.district_code = district_code; //ac_district.getText().toString().trim()
                            orderBookingModel.city = ac_city.getText().toString().trim();
                            orderBookingModel.district_name = district_name;
                            orderBookingModel.distributor_code = distributor_code;
                            orderBookingModel.distributor_name = ac_distributor.getText().toString().trim();
                            orderBookingModel.crop_code = crop_code;
                            orderBookingModel.variety_code =varity_code;
                            orderBookingModel.variety_name = ac_variety.getText().toString().trim();
                            orderBookingModel.variety_package_size = String.valueOf(Float.parseFloat(ed_varty_pack_size.getText().toString().trim()));
                            orderBookingModel.target = String.valueOf(Float.parseFloat(ed_target.getText().toString().trim()));
                            orderBookingModel.booking_qty = String.valueOf(Float.parseFloat(ed_booking_qty.getText().toString().trim()));
                            orderBookingModel.no_series = updateOrderModel.no_series;
                            orderBookingModel.posted = postedString;
                            orderBookingModel.approver_id = sessionManagement.getApprover_id();
                            orderBookingModel.updated_by = sessionManagement.getUserEmail();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String jsonString = new Gson().toJson(orderBookingModel);
                        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<BookingResponseModel>> call = mAPIService.updateBookingOrder(asJsonObject);
                        call.enqueue(new Callback<List<BookingResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<BookingResponseModel> update_booking_response = response.body();
                                        if (update_booking_response != null && update_booking_response.size() > 0 && update_booking_response.get(0).condition) {
                                            getBookingList();
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), update_booking_response.get(0).message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), update_booking_response != null && update_booking_response.size() > 0 ? "No data found OB" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_booking_list", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_booking_list", getActivity());
                            }
                        });
                    }
                });
            } else if (!NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
            }
        }

        else if(flag.equalsIgnoreCase("view_details")){
            update_btn.setVisibility(View.GONE);
            submit_booking_header_btn.setVisibility(View.GONE);

            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                if (updateOrderModel.season_code != null && !updateOrderModel.season_code.equalsIgnoreCase("")) {
                    season_code = updateOrderModel.season_code;
                    ac_season.setText(updateOrderModel.season_name != null ? updateOrderModel.season_name : updateOrderModel.season_code);
                    ac_season.setEnabled(false);
                    ac_season.setFocusable(false);
                    ac_season.setDropDownHeight(0);
                }
                if (updateOrderModel.district_code != null && !updateOrderModel.district_code.equalsIgnoreCase("")) {
                    district_code = updateOrderModel.district_code;
                    ac_district.setText(updateOrderModel.district_name);
                    ac_district.setEnabled(false);
                    ac_district.setFocusable(false);
                    ac_district.setDropDownHeight(0);
                } else {
                    ac_district.setText("");
                    ac_district.setEnabled(false);
                }
                if (updateOrderModel.city != null && !updateOrderModel.city.equalsIgnoreCase("")) {
                    ac_city.setText(updateOrderModel.city != null ? updateOrderModel.city : "");
                    ac_city.setEnabled(false);
                    ac_city.setFocusable(false);
                    ac_city.setDropDownHeight(0);
                } else {
                    ac_city.setText("");
                    ac_city.setEnabled(false);
                }
                if (updateOrderModel.distributor_code != null && !updateOrderModel.distributor_code.equalsIgnoreCase("")) {
                    distributor_code = updateOrderModel.distributor_code;
                    ac_distributor.setText(updateOrderModel.distributor_name);
                    ac_distributor.setEnabled(false);
                    ac_distributor.setFocusable(false);
                    ac_distributor.setDropDownHeight(0);
                } else {
                    ac_distributor.setText("");
                    ac_distributor.setEnabled(false);
                }
                if (updateOrderModel.crop_code != null && !updateOrderModel.crop_code.equalsIgnoreCase("")) {
                    crop_code = updateOrderModel.crop_code;
                    ac_crop_code.setText(crop_code);
                    ac_crop_code.setEnabled(false);
                    ac_crop_code.setFocusable(false);
                    ac_crop_code.setDropDownHeight(0);
                } else {
                    ac_crop_code.setText("");
                    ac_crop_code.setEnabled(false);
                }
                if (updateOrderModel.variety_code != null && !updateOrderModel.variety_code.equalsIgnoreCase("")) {
                    varity_code = updateOrderModel.variety_code;
                    ac_variety.setText(updateOrderModel.variety_name != null ? updateOrderModel.variety_name : "");
                    ac_variety.setEnabled(false);
                    ac_variety.setFocusable(false);
                    ac_variety.setDropDownHeight(0);
                } else {
                    ac_variety.setText("");
                    ac_variety.setEnabled(false);
                }
                if (updateOrderModel.target != null) {
                    ed_target.setText(updateOrderModel.target);
                    ed_target.setEnabled(false);
                }

                BackgraundThread backgraundThread = new BackgraundThread(getActivity());
                backgraundThread.district_code = updateOrderModel.district_code;
                backgraundThread.variety_code = updateOrderModel.variety_code;
                backgraundThread.distributor_code = updateOrderModel.distributor_code;

                backgraundThread.execute();

                if (districMasterTable != null) {
                    district_name = districMasterTable.getName() != null ? districMasterTable.getName() : "";
                }
                if (hybrid_item_table != null) {
                    variety_name = hybrid_item_table.getItem_Name() != null ? hybrid_item_table.getItem_Name() : "";
                }

                ed_varty_pack_size.setText(updateOrderModel.variety_package_size);
                ed_varty_pack_size.setEnabled(false);
                ed_booking_qty.setText(updateOrderModel.booking_qty);
                ed_booking_qty.setEnabled(false);
                ed_allotd_per.setText(updateOrderModel.alotted_percent);
                ed_allotd_per.setEnabled(false);
                // ed_no_series.setText(updateOrderModel.no_series);
                if (updateOrderModel.posted != null) {
                    if (updateOrderModel.posted.equals("1")) {
                        postedString = updateOrderModel.posted;
                        poseted_yes.setChecked(true);
                        poseted_yes.setEnabled(false);
                        posted_no.setEnabled(false);
                    } else if (updateOrderModel.posted.equals("0")) {
                        postedString = updateOrderModel.posted;
                        posted_no.setChecked(true);
                        posted_no.setEnabled(false);
                        poseted_yes.setEnabled(false);
                    }
                }

            }
            else if (!NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
            }
        }
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
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            seasonMasterModelList = templantingList_season;
                            SeasonMasterAdapter seasonMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterModelList);
                            ac_season.setAdapter(seasonMasterAdapter);
                        } else {
                            master_loading_progress.setVisibility(View.GONE);
                            ac_season.setText("");
                            Toast.makeText(getActivity(), templantingList_season != null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
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

    private void getBookingList() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<OrderBookingModel>> call = mAPIService.getOrderBooking(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<OrderBookingModel>>() {
                @Override
                public void onResponse(Call<List<OrderBookingModel>> call, Response<List<OrderBookingModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<OrderBookingModel> orderbooking_list = response.body();
                            if (orderbooking_list != null && orderbooking_list.size() > 0 && orderbooking_list.get(0).condition) {
                                orderBooking_list = orderbooking_list;
                                binddataWithAadapter(orderBooking_list);
                            } else {
                                no_record_found.setVisibility(View.VISIBLE);
                                boooking_listview.setVisibility(View.GONE);
//                                Toast.makeText(getActivity(), orderbooking_list != null && orderbooking_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getOrderBookList", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<OrderBookingModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getOrderBookList", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void binddataWithAadapter(List<OrderBookingModel> orderbooking_list) {
        orderBookingAdapter = new OrderBookingAdapter(getActivity(), orderbooking_list);
        boooking_listview.setAdapter(orderBookingAdapter);
    }


    private void deleteBooking(String booking_no, int position) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingResponseModel>> call = mAPIService.deleteBooking(booking_no);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> delete_response = response.body();
                            if (delete_response != null && delete_response.size() > 0 && delete_response.get(0).condition) {
                                Toast.makeText(getActivity(), "Delete Successfully! "+orderBooking_list.get(position).booking_no, Toast.LENGTH_SHORT).show();
                                orderBooking_list.remove(position);
                                orderBookingAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getActivity(), delete_response != null && delete_response.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_order", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_order", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getVarietyCode(String crop_code, AutoCompleteTextView ac_variety) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            varity_list.clear();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            varity_list = hybridItemMasterDao.getVarietyItem("Hybrid", crop_code);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (varity_list != null && varity_list.size() > 0) {
              /*  HybridItemAliasNameAdapter hybridItemAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, varity_list);
                ac_variety.setAdapter(hybridItemAdapter);*/
                ItemVarietyAdapter varietyAdapter = new ItemVarietyAdapter(getActivity(), R.layout.android_item_view, varity_list);
                ac_variety.setAdapter(varietyAdapter);
            } else {
                ac_variety.setAdapter(null);
                ac_variety.setText("");
            }
        }
    }


    private List<RoleMasterModel.Data> tempRoleMasterList = new ArrayList<>();

    private void getCropMaster(MaterialProgressBar content_loading, AutoCompleteTextView ac_crop_code) {
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
                                ac_crop_code.setAdapter(cropHytechAdapter);
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
                }
            }

            @Override
            public void onFailure(Call<CropMassterModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });

    }

    public class BackgraundThread extends BackgroundTask {
        public Activity activity;
        private String district_code = "", variety_code = "", distributor_code = "";
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());

        public BackgraundThread(Activity activity) {
            super(activity);
            this.activity = activity;
        }
        @Override
        public void doInBackground() {
            try {
                DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
//                CropHytechMasterDao cropHytechMasterDao=pristineDatabase.cropHytechMasterDao();
                districMasterTableList = districMasterDao.getAllData();
//                cropHytechMasterTableList=cropHytechMasterDao.getAllData();
                districMasterTable = districMasterDao.getDistName(district_code);
                HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
                hybrid_item_table = hybridItemMasterDao.getHybridItemNameByCode(variety_code);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }

        @Override
        public void onPostExecute() {
            if (districMasterTableList != null && districMasterTableList.size() > 0) {
                DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.android_item_view, districMasterTableList);
                ac_district.setAdapter(districtAdapter);
            }
        }
    }

    private void getSalePlanLine(MaterialProgressBar content_loading, String crop_code, String varity_code, String season_code, TextInputEditText target_qty) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<SalePlanLineModel> call = mAPIService.getSalePlanModel(crop_code, varity_code, season_code);
        call.enqueue(new Callback<SalePlanLineModel>() {
            @Override
            public void onResponse(Call<SalePlanLineModel> call, Response<SalePlanLineModel> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        SalePlanLineModel cropMassterModel = response.body();
                        if (cropMassterModel != null && cropMassterModel.condition) {
                            List<SalePlanLineModel.Data> saleplainList = cropMassterModel.data;
                            if (saleplainList != null && saleplainList.size() > 0 && saleplainList.get(0).Sales_Plan_No != null) {
                                target_qty.setText(saleplainList.get(0).Sales_Plan_Quantity);
                            } else {
//                                StaticMethods.showMessage(getActivity(), "No Target Qty Found!", MDToast.TYPE_ERROR);
                                target_qty.setText("");
                                content_loading.setVisibility(View.GONE);
                            }
                        } else {
                            target_qty.setText("");
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Sale Plainline Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        target_qty.setText("");
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_sale_plan_line_model", getActivity());
                }
            }

            @Override
            public void onFailure(Call<SalePlanLineModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_sale_plan_line_model", getActivity());
            }
        });
    }
}
