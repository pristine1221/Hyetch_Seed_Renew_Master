package com.example.pristineseed.ui.vendor.farmar_mst;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.distributor_master.DistributorListModel;
import com.example.pristineseed.model.distributor_master.InsertDistributorModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.StateMasterAdapter;
import com.example.pristineseed.ui.adapter.TalukaAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.farmer_adapter.FarmarMasterAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.ItemVarietyAdapter;
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

public class FarmarMasterFragment extends Fragment {
    SwipeRefreshLayout swipe_refresh_layout;
    Chip add_newItem;
    ListView listview_farmer;
    TextView no_record_found;

    SessionManagement sessionManagement;
    int init_page = 0;
    //private String crop_name="" , zone_string="",state_string="",district_string="",taluka_string="";

    private List<DistributorListModel> get_farmeer_master_list = null;
    private FarmarMasterAdapter farmarMasterAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmar_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        listview_farmer = view.findViewById(R.id.lv_farmer_list);
        no_record_found = view.findViewById(R.id.no_data_found);

        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);

        add_newItem = view.findViewById(R.id.add_newItem);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("Insert", null);
        });
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            getFarmerData(init_page);
        } else {
            bindFarmerDataWithLocal(new ArrayList<>());
        }

        listview_farmer.setOnItemClickListener((parent, view1, position, id) -> {
            try {
                if (get_farmeer_master_list != null && get_farmeer_master_list.size() > 0) {
                    AddNewItemPopup("Update", get_farmeer_master_list.get(position));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        listview_farmer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeletePopUP(get_farmeer_master_list.get(position).distributor_code, position);
                return true;
            }
        });

        //todo go to infinite list
        listview_farmer.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount)) {
                    //list
                    for (int i = totalItemCount; i < get_farmer_list.size() + init_page; i++) {
                    }
                }
            }
        });


        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (get_farmeer_master_list != null && get_farmer_list.size() > 0) {
                        get_farmeer_master_list.clear();
                    }
                    if (isNetwork) {
                        getFarmerData(init_page);
                        swipe_refresh_layout.setRefreshing(false);
                    }
                    // Stop animation (This will be after 3 seconds)
                }

            }, 3000); // todo Delay in millis
        });
        swipe_refresh_layout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
    }

    private StateMasterTable stateMasterTable;
    private ZoneMasterTable zoneMasterTable;
    private DistricMasterTable districMasterTable;
    private TalukaMasterTable talukaMasterTable;
    private CropMassterModel.Data cropMasterTable = null;
    private Hybrid_Item_Table hybrid_item_table = null;
    private List<StateMasterTable> stateMasterList = null;
    private List<ZoneMasterTable> zoneMasterTableList = null;
    private List<DistricMasterTable> districMasterTableList = null;
    private List<TalukaMasterTable> talukaMasterList = null;
    private List<Hybrid_Item_Table> hybrid_list = null;
    private String state_code = "", zone_code = "", district_code = "", taluka_code = "", crop_code = "", hybrid_item_code = "", hybrid_alias_name = "";
    private List<CropMassterModel.Data> cropMasterTableArrayList = null;
    private AutoCompleteTextView drpdwn_district;

    public void AddNewItemPopup(String flag, DistributorListModel farmer_master_model) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupViewFarmer = inflater.inflate(R.layout.add_farmardata_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupViewFarmer);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        //todo textInput edit text value for farmer fragement
        TextInputEditText ed_farmer_name = popupViewFarmer.findViewById(R.id.ed_farmer_name);
        TextInputEditText ed_mobile_no = popupViewFarmer.findViewById(R.id.ed_mobile_no);

        TextInputEditText ed_aadhar_card = popupViewFarmer.findViewById(R.id.ed_aadhar_card);
        TextInputEditText ed_total_land = popupViewFarmer.findViewById(R.id.ed_total_land);
        TextInputEditText ed_pan_card = popupViewFarmer.findViewById(R.id.pan_card);
        TextView title_text = popupViewFarmer.findViewById(R.id.title_text);

        TextInputEditText drp_dwn_taluka = popupViewFarmer.findViewById(R.id.drp_dwn_taluka);
        AutoCompleteTextView drop_down_zone = popupViewFarmer.findViewById(R.id.drop_down_zone);
        AutoCompleteTextView drpdwn_state = popupViewFarmer.findViewById(R.id.drpdwn_state);
        drpdwn_district = popupViewFarmer.findViewById(R.id.drpdwn_district);
        //todo close  image
        ImageView close_dilog_bt = popupViewFarmer.findViewById(R.id.close_dilog_bt);
        //todo submit button and update
        Button clear_data = popupViewFarmer.findViewById(R.id.clear_data);
        Button filter_apply_bt = popupViewFarmer.findViewById(R.id.filter_apply_bt);
        Button update_btn = popupViewFarmer.findViewById(R.id.update_btn);
        AutoCompleteTextView ac_crop_dropdwn = popupViewFarmer.findViewById(R.id.crop_);
        AutoCompleteTextView ac_hybrid = popupViewFarmer.findViewById(R.id.ac_hybrid);
        MaterialProgressBar content_loading = popupViewFarmer.findViewById(R.id.content_loading);

        getCropMaster(content_loading, ac_crop_dropdwn);

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            //  CropHytechMasterDao cropMasterDao = pristineDatabase.cropHytechMasterDao();
            // cropMasterTableArrayList = cropMasterDao.getAllData();
            zoneMasterTableList = zoneMaterDao.getAllData();
            stateMasterList = stateMasterDao.getAllData();
            districMasterTableList = districMasterDao.getAllData();
            talukaMasterList = talukaMasterDao.getAllData();
//            hybrid_list = hybridItemMasterDao.getHybridAliasItem(); //todo changed hybrid code (Item_No) instead of alias name...
            hybrid_list = hybridItemMasterDao.getHybridItem();

/*            if(farmer_master_model!=null) {
                if(farmer_master_model.zone!=null) {
                    ZoneMasterTable zoneMasterTable = zoneMaterDao.getZoneNameBycode(farmer_master_model.zone);
                    if (zoneMasterTable != null) {
                        zone_string = zoneMasterTable.getDescription();
                        zone_code=zoneMasterTable.getCode();
                    }
                }

                if(farmer_master_model.state!=null) {
                    List<StateMasterTable> stateMasterTableList = stateMasterDao.getStateName(farmer_master_model.state);
                    if (stateMasterTableList != null && stateMasterTableList.size() > 0) {
                         state_string = stateMasterTableList.get(0).getName();
                         state_code=stateMasterTableList.get(0).getCode();
                    }
                }

                if(farmer_master_model.district!=null){
                 DistricMasterTable districtMasterTable = districMasterDao.getDistName(farmer_master_model.district);
                    if (districtMasterTable != null) {
                         district_string = districtMasterTable.getName();
                         district_code=districtMasterTable.getCode();
                    }
                }
                if(farmer_master_model.taluka!=null){
                    List<TalukaMasterTable> talukaMasterTable = talukaMasterDao.getTalukaName(farmer_master_model.taluka);
                    if (talukaMasterTable != null && talukaMasterTable.size()>0) {
                        taluka_string = talukaMasterTable.get(0).getDescription();
                        taluka_code=talukaMasterTable.get(0).getCode();
                    }
                }

                if(farmer_master_model.hybrid!=null){
                    Hybrid_Item_Table hybrid_item_table = hybridItemMasterDao.getHybridName(farmer_master_model.hybrid);
                    if (hybrid_item_table != null) {
                        hybrid_alias_name = hybrid_item_table.getAlias_Name();
                        hybrid_item_code=hybrid_item_table.getNo();
                    }
                }
                if(farmer_master_model.crop!=null){
                    CropHytechMasterTable cropHytechMasterTable = cropMasterDao.getCropByCodeName(farmer_master_model.crop);
                    if (cropHytechMasterTable != null) {
                        crop_name = cropHytechMasterTable.getDescription();
                        crop_code=cropHytechMasterTable.getCode();
                    }
                }

            }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }

        if (stateMasterList != null && stateMasterList.size() > 0) {
            StateMasterAdapter geoStateAdapter = new StateMasterAdapter(getActivity(), R.layout.drop_down_textview, stateMasterList);
            drpdwn_state.setAdapter(geoStateAdapter);
        }

        if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
            ZoneMasterAdapter zoneMasterAdapter = new ZoneMasterAdapter(getActivity(), R.layout.drop_down_textview, zoneMasterTableList);
            drop_down_zone.setAdapter(zoneMasterAdapter);
        }


       /* if (talukaMasterList != null && talukaMasterList.size() > 0) {
            TalukaAdapter talukaAdapter = new TalukaAdapter(getActivity(), R.layout.drop_down_textview, talukaMasterList);
            drp_dwn_taluka.setAdapter(talukaAdapter);
        }
*/

        if (cropMasterTableArrayList != null && cropMasterTableArrayList.size() > 0) {
            CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.drop_down_textview, cropMasterTableArrayList);
            ac_crop_dropdwn.setAdapter(cropAdapter);
        }

        ac_crop_dropdwn.setOnItemClickListener((parent, view, position, id) -> {
            cropMasterTable = cropMasterTableArrayList.get(position);
            if (cropMasterTable != null) {
                ac_crop_dropdwn.setText(cropMasterTable.Description);
                crop_code = cropMasterTableArrayList.get(position).Code;
                getHybridName(cropMasterTable.Code, ac_hybrid);
            } else {
                ac_crop_dropdwn.setText("");
            }
        });


        ac_hybrid.setOnItemClickListener((parent, view, position, id) -> {
            if (hybrid_list != null && hybrid_list.size() > 0) {
                hybrid_item_table = hybrid_list.get(position);
                if (hybrid_item_table != null) {
                    hybrid_item_table = hybrid_list.get(position);
                    hybrid_item_code = hybrid_list.get(position).getNo();
                    ac_hybrid.setText(hybrid_item_table.getNo());
                    ac_hybrid.setSelection(ac_hybrid.getText().length());
                } else {
                    ac_hybrid.setText("");
                }
            } else {
                ac_hybrid.setText("");
            }
        });

        drop_down_zone.setOnItemClickListener((parent, view, position, id) -> {
            zoneMasterTable = zoneMasterTableList.get(position);
            if (zoneMasterTable != null) {
                zone_code = zoneMasterTableList.get(position).getCode();
                drop_down_zone.setText(zoneMasterTable.getDescription());
            }
        });

        drpdwn_state.setOnItemClickListener((adapterView, view, i, l) -> {
            districMasterTableList.clear();
            bindDistrict(stateMasterList.get(i).getCode());
            stateMasterTable = stateMasterList.get(i);
            if (stateMasterTable != null) {
                state_code = stateMasterList.get(i).getCode();
                drpdwn_state.setText(stateMasterTable.getName());
                drpdwn_state.setSelection(drpdwn_state.getText().length());
                drpdwn_state.setError(null);
                drpdwn_district.setText("");
            }
        });

        drpdwn_district.setOnItemClickListener((parent, view, position, id) -> {
            districMasterTable = districMasterTableList.get(position);
            if (districMasterTable != null) {
                district_code = districMasterTableList.get(position).getCode();
                drpdwn_district.setText(districMasterTable.getName());
            }
        });

       /* drp_dwn_taluka.setOnItemClickListener((parent, view, position, id) -> {
            talukaMasterTable = talukaMasterList.get(position);
            if (talukaMasterTable != null) {
                taluka_code = talukaMasterList.get(position).getCode();
                drp_dwn_taluka.setText(talukaMasterTable.getDescription());
            }
        });*/

        clear_data.setOnClickListener(v -> {
            clearAllField(ed_farmer_name, ed_mobile_no,
                    ed_aadhar_card, ed_total_land, ac_crop_dropdwn,
                    ac_hybrid, ed_pan_card, title_text);

        });

        if (flag.equalsIgnoreCase("Insert")) {
            filter_apply_bt.setOnClickListener(view -> {
                try {
                    if (ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_mobile_no.getText().toString().trim().length() != 10) {
                        Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_aadhar_card.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter aadhar no. !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (drop_down_zone.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter zone !", Toast.LENGTH_SHORT).show();
                    } else if (ed_farmer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter farmer name.", Toast.LENGTH_SHORT).show();
                    } else if (drpdwn_state.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter state !", Toast.LENGTH_SHORT).show();
                    } else if (drpdwn_district.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter district !", Toast.LENGTH_SHORT).show();
                    } else if (drp_dwn_taluka.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter taluka !", Toast.LENGTH_SHORT).show();
                    } else if (ed_aadhar_card.getText().toString().length() > 12) {
                        Toast.makeText(getActivity(), "Aadhar card length must be 12 in characters.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_pan_card.getText().toString().length() > 10) {
                        Toast.makeText(getActivity(), "Pan card length must be 10 in characters.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //todo call Farmer list data for submit
                        DistributorListModel tempFarmerList = new DistributorListModel();
                        tempFarmerList.distributor_name = ed_farmer_name.getText().toString().trim();
                        tempFarmerList.mobile = ed_mobile_no.getText().toString().trim();
                        tempFarmerList.state = state_code;
                        tempFarmerList.state_name = drpdwn_state.getText().toString().trim();
                        tempFarmerList.zone = zone_code;
                        tempFarmerList.zone_name = drop_down_zone.getText().toString().trim();
                        tempFarmerList.district = district_code;
                        tempFarmerList.district_name = drpdwn_district.getText().toString().trim();
//                        tempFarmerList.taluka = taluka_code;
                        tempFarmerList.taluka = drp_dwn_taluka.getText().toString().trim();
                        tempFarmerList.pan_no = ed_pan_card.getText().toString().trim();
                        tempFarmerList.aadhaar_no = ed_aadhar_card.getText().toString().trim();
                        tempFarmerList.total_land = ed_total_land.getText().toString().trim();
                        tempFarmerList.crop = crop_code;
                        tempFarmerList.crop_name = ac_crop_dropdwn.getText().toString().trim();
                        tempFarmerList.hybrid = hybrid_item_code != null ? hybrid_item_code : "";
                        tempFarmerList.hybrid_name = ac_hybrid.getText().toString().trim();
                        tempFarmerList.created_by = sessionManagement.getUserEmail();
                        tempFarmerList.created_on = DateTimeUtilsCustome.getCurrentTime();
                        tempFarmerList.isType = "Farmer";
                        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                        if (isNetwork) {
                            JsonObject json_object_insert = new JsonParser().parse(new Gson().toJson(tempFarmerList)).getAsJsonObject();
                            LoadingDialog loadingDialog = new LoadingDialog();
                            loadingDialog.showLoadingDialog(getActivity());
                            NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                            Call<List<InsertDistributorModel>> insertFarmer = mApiService.insertDistributorCustomerFarmer(json_object_insert);
                            insertFarmer.enqueue(new Callback<List<InsertDistributorModel>>() {
                                @Override
                                public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                    if (response.isSuccessful()) {
                                        loadingDialog.hideDialog();
                                        List<InsertDistributorModel> tempInsert = response.body();
                                        if (tempInsert != null && tempInsert.size() > 0 && tempInsert.get(0).condition) {
                                            getFarmerData(init_page);
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), tempInsert.get(0).message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), tempInsert.size() > 0 ? tempInsert.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        loadingDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                    loadingDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert_farmer", getActivity());
                                }
                            });
                        } else {
                            //todo offline task...................
                            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


        if (flag.equalsIgnoreCase("Update")) {
            update_btn.setVisibility(View.VISIBLE);
            clear_data.setVisibility(View.GONE);
            filter_apply_bt.setVisibility(View.GONE);
            try {
                //todo get offline data from database and sync it
                ed_farmer_name.setText(farmer_master_model.distributor_name);
                ed_farmer_name.setSelection(ed_farmer_name.getText().length());
                ed_mobile_no.setText(farmer_master_model.mobile);
                ed_total_land.setText(farmer_master_model.total_land);
                ed_aadhar_card.setText(farmer_master_model.aadhaar_no);
                ed_pan_card.setText(farmer_master_model.pan_no);

                if (farmer_master_model.state != null && !farmer_master_model.state.equalsIgnoreCase("")) {
                    drpdwn_state.setText(farmer_master_model.state_name);
                    state_code = farmer_master_model.state;
                } else {
                    drpdwn_state.setText("");
                }
                if (farmer_master_model.taluka != null && !farmer_master_model.taluka.equalsIgnoreCase("")) {
                    drp_dwn_taluka.setText(farmer_master_model.taluka);
//                    taluka_code = farmer_master_model.taluka;
                } else {
                    drp_dwn_taluka.setText("");
                }

                if (farmer_master_model.district != null && !farmer_master_model.district.equalsIgnoreCase("")) {
                    drpdwn_district.setText(farmer_master_model.district_name);
                    district_code = farmer_master_model.district;
                } else {
                    drpdwn_district.setText("");
                }
                if (farmer_master_model.zone != null && !farmer_master_model.zone.equalsIgnoreCase("")) {
                    drop_down_zone.setText(farmer_master_model.zone_name);
                    zone_code = farmer_master_model.zone;
                } else {
                    drop_down_zone.setText("");
                }

                if (farmer_master_model.crop != null && !farmer_master_model.crop.equalsIgnoreCase("")) {
                    ac_crop_dropdwn.setText(farmer_master_model.crop_name);
                    crop_code = farmer_master_model.crop;
                } else {
                    ac_crop_dropdwn.setText("");
                }

                if (farmer_master_model.hybrid != null && !farmer_master_model.hybrid.equalsIgnoreCase("")) {
                    try {
                        hybrid_list.clear();
                        HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
                        hybrid_list = hybridItemMasterDao.getVarietyItem("Hybrid", crop_code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        pristineDatabase.close();
                        pristineDatabase.destroyInstance();
                        if (hybrid_list != null && hybrid_list.size() > 0) {
                /*HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter); //todo change in hybrid field set hybrid code(Item_No) instead of alias name on (04-01-2022). */
                            ItemVarietyAdapter varietyAdapter = new ItemVarietyAdapter(getActivity(), R.layout.android_item_view, hybrid_list);
                            ac_hybrid.setAdapter(varietyAdapter);
                        } else {
                            ac_hybrid.setAdapter(null);
                            ac_hybrid.setText("");
                        }
                    }

                    ac_hybrid.setText(farmer_master_model.hybrid_name);
                    ac_hybrid.setSelection(ac_hybrid.getText().length());
                    hybrid_item_code = farmer_master_model.hybrid;
                } else {
                    ac_hybrid.setText("");
                }

                update_btn.setOnClickListener(v -> {
                    if (ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_mobile_no.getText().toString().trim().length() != 10) {
                        Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_aadhar_card.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter aadhar no. !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (drop_down_zone.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter zone !", Toast.LENGTH_SHORT).show();
                    } else if (ed_farmer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter farmer name.", Toast.LENGTH_SHORT).show();
                    } else if (drpdwn_state.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter state !", Toast.LENGTH_SHORT).show();
                    } else if (drpdwn_district.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter district !", Toast.LENGTH_SHORT).show();
                    } else if (drp_dwn_taluka.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter taluka !", Toast.LENGTH_SHORT).show();
                    } else {
                        DistributorListModel tempFarmerList = new DistributorListModel();
                        tempFarmerList.distributor_code = farmer_master_model.distributor_code;
                        tempFarmerList.distributor_name = ed_farmer_name.getText().toString().trim();
                        tempFarmerList.mobile = ed_mobile_no.getText().toString();
                        tempFarmerList.state = state_code;
                        tempFarmerList.state_name = drpdwn_state.getText().toString().trim();
                        tempFarmerList.zone = zone_code;
                        tempFarmerList.zone_name = drop_down_zone.getText().toString().trim();
                        tempFarmerList.district = district_code;
                        tempFarmerList.district_name = drpdwn_district.getText().toString().trim();
//                        tempFarmerList.taluka = taluka_code;
                        tempFarmerList.taluka = drp_dwn_taluka.getText().toString().trim();
                        tempFarmerList.pan_no = ed_pan_card.getText().toString();
                        tempFarmerList.aadhaar_no = ed_aadhar_card.getText().toString();
                        tempFarmerList.total_land = ed_total_land.getText().toString();
                        tempFarmerList.crop = crop_code;
                        tempFarmerList.crop_name = ac_crop_dropdwn.getText().toString().trim();
                        tempFarmerList.hybrid = hybrid_item_code;
                        tempFarmerList.hybrid_name = ac_hybrid.getText().toString().trim();
                        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                        //todo Post Api hit for update data
                        if (isNetwork) {
                            JsonObject postedJsonUpdate = new JsonParser().parse(new Gson().toJson(tempFarmerList)).getAsJsonObject();
                            LoadingDialog loadingDialog = new LoadingDialog();
                            loadingDialog.showLoadingDialog(getActivity());
                            NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                            Call<List<InsertDistributorModel>> updateFarmer = mApiService.updateDistributorCustomerFarmer(postedJsonUpdate);
                            updateFarmer.enqueue(new Callback<List<InsertDistributorModel>>() {
                                @Override
                                public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            loadingDialog.hideDialog();
                                            List<InsertDistributorModel> tempUpdate = response.body();
                                            if (tempUpdate.size() > 0 && tempUpdate.get(0).condition) {
                                                getFarmerData(init_page);
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), tempUpdate.get(0).message, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), tempUpdate.size() > 0 ? tempUpdate.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            loadingDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        loadingDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Update_farmer", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                    loadingDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Update_farmer", getActivity());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    private void getHybridName(String crop_code, AutoCompleteTextView ac_hybrid_name) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            hybrid_list.clear();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            hybrid_list = hybridItemMasterDao.getVarietyItem("Hybrid", crop_code);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (hybrid_list != null && hybrid_list.size() > 0) {
            /*HybridItemAliasNameAdapter hybridItemAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_list);
            ac_hybrid_name.setAdapter(hybridItemAdapter);*/ //todo changes in hybrid field set item_no, instead of alias name..(04-01-2022)
                ItemVarietyAdapter varietyAdapter = new ItemVarietyAdapter(getActivity(), R.layout.android_item_view, hybrid_list);
                ac_hybrid_name.setAdapter(varietyAdapter);
            }
            else {
                ac_hybrid_name.setAdapter(null);
                ac_hybrid_name.setText("");
            }
        }

    }

    private void clearAllField(
            TextInputEditText ed_farmer_name, TextInputEditText ed_mobile_no,
            TextInputEditText ed_aadhar_card, TextInputEditText ed_total_land, AutoCompleteTextView ed_crop,
            AutoCompleteTextView ed_hybrid, TextInputEditText ed_pan_card, TextView title_text) {
        ed_farmer_name.setText("");
        ed_mobile_no.setText("");
        ed_aadhar_card.setText("");
        ed_total_land.setText("");
        ed_crop.setText("");
        ed_hybrid.setText("");
        ed_pan_card.setText("");

        title_text.requestFocus(View.SCROLL_INDICATOR_TOP);
    }


    List<DistributorListModel> get_farmer_list = new ArrayList<>();

    private void getFarmerData(int page_no) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(getActivity());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
        jsonObject.addProperty("isType", "Farmer");
        jsonObject.addProperty("query_parameter", "");
        jsonObject.addProperty("RowsPerPage", "500");
        jsonObject.addProperty("PageNumber", page_no);
        NetworkInterface mApiService = ApiUtils.getPristineAPIService();
        Call<List<DistributorListModel>> getFarmer = mApiService.getDistributorCustomerFarmerList(jsonObject);
        getFarmer.enqueue(new Callback<List<DistributorListModel>>() {
            @Override
            public void onResponse(Call<List<DistributorListModel>> call, Response<List<DistributorListModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        loadingDialog.hideDialog();
                        List<DistributorListModel> farmermasterList = response.body();
                        if (farmermasterList.size() > 0 && farmermasterList.get(0).condition && farmermasterList.get(0).distributor_code != null) {
                            get_farmeer_master_list = farmermasterList;
                            bindFarmerDataWithLocal(get_farmeer_master_list);
                        } else {
                            no_record_found.setVisibility(View.VISIBLE);
                            swipe_refresh_layout.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), get_farmer_list.size() > 0 ? "No data found Farmer" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getFarmer_", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<DistributorListModel>> call, Throwable t) {
                loadingDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getFarmer_", getActivity());
            }
        });

    }

    private void bindFarmerDataWithLocal(List<DistributorListModel> tempFarmerListModel) {
        if (tempFarmerListModel.get(0).distributor_code != null) {
            if (get_farmeer_master_list != null && get_farmeer_master_list.size() > 0) {
                farmarMasterAdapter = new FarmarMasterAdapter(getActivity(), get_farmeer_master_list);
                listview_farmer.setAdapter(farmarMasterAdapter);
            }
        }
    }

    void bindDistrict(String state_code){
        districMasterTableList = getdistricMasterTableList(state_code);
        if (getdistricMasterTableList(state_code)!= null && getdistricMasterTableList(state_code).size()>0
                && districMasterTableList != null && districMasterTableList.size()>0) {
            DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
            drpdwn_district.setAdapter(districtAdapter);
        }
      /*  else {
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


    void showDeletePopUP(String distributor_code, int position) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View PopupView = inflater.inflate(R.layout.delete_dialog_pop_up, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.setContentView(PopupView);
        dialog.setCancelable(false);
        dialog.show();
        TextView tv_distributor_code = PopupView.findViewById(R.id.dist_code);
        TextView tv_title = PopupView.findViewById(R.id.title_text);
        tv_title.setText("Do you really want to delete this item ?");
        tv_distributor_code.setText(distributor_code);
        Button cancel_btn = PopupView.findViewById(R.id.cancel_btn);
        Button ok_btn = PopupView.findViewById(R.id.ok_btn);
        cancel_btn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        ok_btn.setOnClickListener(v -> {
            deleteLine(distributor_code, position, dialog);
        });
    }

    private void deleteLine(String distributor_code, int position, Dialog dialog) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("distributor_code", distributor_code);
            jsonObject.addProperty("isType", "Farmer");
            jsonObject.addProperty("nav_code", "");
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mApiService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> callDelete = mApiService.deleteDistributor_customer_farmer(jsonObject);
            callDelete.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<ResponseModel> farmerDeleteModelList = response.body();
                            if (farmerDeleteModelList.size() > 0 && farmerDeleteModelList.get(0).condition) {
                                get_farmeer_master_list.remove(position);
                                farmarMasterAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(getActivity(), farmerDeleteModelList.get(0).message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), farmerDeleteModelList.size() > 0 ? "No data found Farmer" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Farmer_delete", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Farmer_delete", getActivity());
                }
            });
        }
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
                                cropMasterTableArrayList = crop_master_list;
                                if (cropMasterTableArrayList.size() > 0) {
                                    CropHytechAdapter cropHytechAdapter = new CropHytechAdapter(getActivity(), R.layout.android_item_view, cropMasterTableArrayList);
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