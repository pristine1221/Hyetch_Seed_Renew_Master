package com.example.pristineseed.ui.vendor.POG;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.POG.POGModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.RoleMasterModelNew;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.ProductOnGroundListAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.ItemVarietyAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterC_DAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductOnGroundFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Chip add_newItem;
    private ListView listview;
    private SessionManagement sessionManagement;
    private List<POGModel> pogModelList;
    private String hybrid_code = "", crop_code = "", zonecode = "", customer_code = "", season_code = "", hybrid_string = "";
    // private  List<CropHytechMasterTable> cropHytech_name_list=null;
    private Hybrid_Item_Table hybrid_name = null;
    private ZoneMasterTable zoneMaster_name = null;
    private RoleMasterModelNew roleMaster_name = null;
    private SeasonMasterTable seasonMasterName = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_on_ground, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.listview);
        add_newItem = view.findViewById(R.id.add_newItem);
        pogModelList = new ArrayList<>();
        sessionManagement = new SessionManagement(getActivity());
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("Insert", null);
        });

        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (pogModelList != null && pogModelList.size() > 0) {
                POGModel pogModel = pogModelList.get(position);
                if (!pogModel.status.equalsIgnoreCase("Approve")) {
                    AddNewItemPopup("Update", pogModel);
                } else {
                    Toast.makeText(getActivity(), "You can't update item as status is approved", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onResume() {
        getPOGList();
        super.onResume();
    }

    private void binddataWithAadapter(List<POGModel> pog_response_list) {
        ProductOnGroundListAdapter approvalAdapter = new ProductOnGroundListAdapter(getActivity(), pog_response_list);
        listview.setAdapter(approvalAdapter);
    }

    private void getPOGList() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<POGModel>> call = mAPIService.getProductionOnGrd(sessionManagement.getUserEmail());
            call.enqueue(new Callback<List<POGModel>>() {
                @Override
                public void onResponse(Call<List<POGModel>> call, Response<List<POGModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<POGModel> pog_response_list = response.body();
                            if (pog_response_list != null && pog_response_list.size() > 0 && pog_response_list.get(0).condition) {
                                pogModelList = pog_response_list;
                                binddataWithAadapter(pog_response_list);
                            } else {
                                Toast.makeText(getActivity(), pog_response_list != null && pog_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_pog", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<POGModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_pog", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ZoneMasterTable> zoneMasterTableList = null;
    private ZoneMasterTable zoneMasterTable = null;
    private List<RoleMasterModel.Data> roleMasterTableList_gl = new ArrayList<>();
    private RoleMasterModel.Data roleMasterTable = null;
    private List<SeasonMasterModel> seasonMasterTableList = null;
    private SeasonMasterModel seasonMasterTable = null;
    private List<CropMassterModel.Data> cropMasterTableList = null;
    private CropMassterModel.Data crop_table = null;
    private List<Hybrid_Item_Table> hybrid_item_tableList = null;
    private TextInputLayout search_cust_dist_layout;
    private MaterialCardView frame_layout_org_list;
    private AutoCompleteTextView ac_customer=null;
    private String customer_name = "";
    RoleMasterC_DAdapter masterCDAdapter;
    public void AddNewItemPopup(String flag, POGModel pogModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_product_on_ground_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        AutoCompleteTextView dropdown_zone = popupView.findViewById(R.id.dropdown_zone);
        AutoCompleteTextView ac_crop = popupView.findViewById(R.id.ac_crop);
        ac_customer = popupView.findViewById(R.id.ac_customer);
        AutoCompleteTextView ac_season = popupView.findViewById(R.id.ac_season);

        TextInputEditText ed_employee_name = popupView.findViewById(R.id.ed_employee_name);
        AutoCompleteTextView ac_hybrid_name = popupView.findViewById(R.id.ac_hybrid_name);
        TextInputEditText ed_pog_qty = popupView.findViewById(R.id.ed_pog_qty);

        TextInputEditText ed_date = popupView.findViewById(R.id.ed_date);
        TextInputEditText ed_remarks = popupView.findViewById(R.id.ed_remarks);

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        Button udpate_pog = popupView.findViewById(R.id.udpate_pog);
        TextView update_pog_title = popupView.findViewById(R.id.update_pog_title);
        TextView add_pog_title = popupView.findViewById(R.id.add_pog_title);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        RecyclerView lv_cust_dist_list=popupView.findViewById(R.id.lv_cust_dist_list);
        ProgressBar loading_search_item=popupView.findViewById(R.id.loading_item);
         search_cust_dist_layout=popupView.findViewById(R.id.search_cust_dist_layout);
         frame_layout_org_list=popupView.findViewById(R.id.frame_layout_org_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);

        ed_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date);
            return true;
        });

   /*     ac_customer.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus && ac_customer.isCursorVisible()){
                search_cust_dist_layout.setStartIconDrawable(null);
            }else {
                if(!ac_customer.getText().toString().trim().equalsIgnoreCase("")){
                    search_cust_dist_layout.setStartIconDrawable(null);
                }
                else {
                    search_cust_dist_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

          ac_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equalsIgnoreCase("")) {
                    ac_customer.setSelection(s.toString().length());
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getRoleMasterData(loading_search_item, lv_cust_dist_list,s.toString(),frame_layout_org_list);
                 }
                else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        getSeasonMaster(content_loading, ac_season);
        getCropMaster(content_loading, ac_crop);

            masterCDAdapter = new RoleMasterC_DAdapter(getActivity(), R.layout.android_item_view, roleMasterTableList_gl);
            ac_customer.setAdapter(masterCDAdapter);


        ac_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null && !s.toString().equalsIgnoreCase("")){
                    getRoleMasterC_DData(content_loading, ac_customer, s.toString());
                }

            }
        });
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            ZoneMaterDao zoneMaterDaop = pristineDatabase.zoneMaterDao();
            //CropHytechMasterDao cropMasterDao=pristineDatabase.cropHytechMasterDao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            zoneMasterTableList = zoneMaterDaop.getAllData();
            //cropMasterTableList=cropMasterDao.getAllData();
            hybrid_item_tableList = hybridItemMasterDao.getHybridItem();
           /* if(pogModel.crop!=null) {
                cropHytech_name_list = cropMasterDao.getCropName(pogModel.crop);
            }*/
           /* if(pogModel.hybrid!=null) {
                hybrid_name=hybridItemMasterDao.getHybridName(pogModel.hybrid);
            }
           */
            if (pogModel.zone != null) {
                zoneMaster_name = zoneMaterDaop.getZoneNameBycode(pogModel.zone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                ZoneMasterAdapter plantingLotMFOAdapter = new ZoneMasterAdapter(getActivity(), R.layout.android_item_view, zoneMasterTableList);
                dropdown_zone.setAdapter(plantingLotMFOAdapter);
            }
            dropdown_zone.setOnItemClickListener((parent, view, position, id) -> {
                zoneMasterTable = zoneMasterTableList.get(position);
                  if(zoneMasterTable!=null) {
                    zonecode = zoneMasterTable.getCode();
                    dropdown_zone.setText(zoneMasterTable.getDescription());
                }
            });

            ac_season.setOnItemClickListener((parent, view, position, id) -> {
                seasonMasterTable = seasonMasterTableList.get(position);
                ac_season.setText(seasonMasterTable.description);
                season_code = seasonMasterTable.season_Code;
            });

            ac_crop.setOnItemClickListener((parent, view, position, id) -> {
                crop_table = cropMasterTableList.get(position);
                crop_code = crop_table.Code;
                getHybridName(crop_table.Code, ac_hybrid_name);
            });
        }

        ac_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(roleMasterTableList_gl != null && roleMasterTableList_gl.size()>0) {
                        roleMasterTable = roleMasterTableList_gl.get(position);
                        if (roleMasterTable != null) {
                            ac_customer.setText(roleMasterTable.Search_Name);
                            ac_customer.setSelection(ac_customer.getText().length());
                            customer_name = roleMasterTable.Search_Name;
                            customer_code = roleMasterTable.no;

                        } else {
                            ac_customer.setText("");
                        }
                    } else {
                        ac_customer.setText("");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ac_hybrid_name.setOnItemClickListener((parent, view, position, id) -> {
            if (hybrid_item_tableList != null && hybrid_item_tableList.size() > 0) {
                Hybrid_Item_Table hybrid_item_table = hybrid_item_tableList.get(position);
                if (hybrid_item_table != null) {
                    ac_hybrid_name.setText(hybrid_item_table.getNo());
                    ac_hybrid_name.setSelection(ac_hybrid_name.getText().length());
                    hybrid_code = hybrid_item_table.getNo();
                    hybrid_string = hybrid_item_table.getNo();//todo hybrid_item_table.getAlias_Name() removed from hybrid name(04-01-2022).
                } else {
                    ac_hybrid_name.setText("");
                }
            } else {
                ac_hybrid_name.setText("");
            }

        });
        if (flag.equalsIgnoreCase("Insert")) {
            filter_apply_bt.setOnClickListener(view -> {
                if (ed_pog_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter qty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pog_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pog_qty.getText().toString().trim().equalsIgnoreCase(".0") || ed_pog_qty.getText().toString().trim().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct Qty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(roleMasterTable==null || roleMasterTable.no==null){
                    Toast.makeText(getActivity(), "Please enter valid customer no.", Toast.LENGTH_SHORT).show();
                } else{
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        POGModel pogModel1 = new POGModel();
                        pogModel1.zone = zoneMasterTable.getCode() != null ? zoneMasterTable.getCode() : "";
                        pogModel1.emp_name = ed_employee_name.getText().toString().trim();
                        pogModel1.season = season_code != null ? season_code : "";
                        pogModel1.season_name = ac_season.getText().toString().trim();
                        pogModel1.crop = crop_table.Code != null ? crop_table.Code : "";
                        pogModel1.crop_name = ac_crop.getText().toString().trim();
                        pogModel1.hybrid = hybrid_code != null ? hybrid_code : "";
                        pogModel1.hybrid_name = hybrid_string;
                        pogModel1.customer_or_distributor = roleMasterTable.no != null ? roleMasterTable.no : "";
                        pogModel1.customer_or_distributor_name= ac_customer.getText().toString().trim();
                        pogModel1.Search_Name= roleMasterTable.Search_Name != null ? roleMasterTable.Search_Name : "";
                        pogModel1.pog_qty = ed_pog_qty.getText().toString().trim();
                        pogModel1.date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                        pogModel1.remarks = ed_remarks.getText().toString().trim();
                        pogModel1.created_by = sessionManagement.getUserEmail();
                        pogModel1.approver_id = sessionManagement.getApprover_id();
                        String jsonString = new Gson().toJson(pogModel1);
                        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        Call<List<ResponseModel>> call = mAPIService.insertPog(asJsonObject);
                        call.enqueue(new Callback<List<ResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<ResponseModel> pog_response_list = response.body();
                                        if (pog_response_list != null && pog_response_list.size() > 0 && pog_response_list.get(0).condition) {
                                            getPOGList();
                                            Toast.makeText(getActivity(), pog_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), pog_response_list != null && pog_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_pog", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_pog", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (flag.equalsIgnoreCase("Update")) {
            udpate_pog.setVisibility(View.VISIBLE);
            filter_apply_bt.setVisibility(View.GONE);
            update_pog_title.setVisibility(View.VISIBLE);
            add_pog_title.setVisibility(View.GONE);
            update_pog_title.setText("Update POG" + "(" + pogModel.pog_code + ")");
            ac_season.setText(pogModel.season_name);
            season_code = pogModel.season;
            ed_employee_name.setText(pogModel.emp_name);

            if (pogModel.crop != null) {
                crop_code = pogModel.crop;
                ac_crop.setText(pogModel.crop_name);
            } else {
                ac_crop.setText("");
            }
            if (pogModel.hybrid != null) {
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
                /*HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter); //todo change in hybrid field set hybrid code(Item_No) instead of alias name on (04-01-2022). */
                        ItemVarietyAdapter varietyAdapter = new ItemVarietyAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                        ac_hybrid_name.setAdapter(varietyAdapter);
                    } else {
                        ac_hybrid_name.setAdapter(null);
                        ac_hybrid_name.setText("");
                    }
                }

                ac_hybrid_name.setText(pogModel.hybrid);
                ac_hybrid_name.setSelection(ac_hybrid_name.getText().length());
                hybrid_code = pogModel.hybrid;
                hybrid_string = pogModel.hybrid_name;
            } else {
                ac_hybrid_name.setText("");
            }
            if (zoneMaster_name != null) {
                zonecode = zoneMaster_name.getCode();
                dropdown_zone.setText(zoneMaster_name.getDescription());
            } else {
                dropdown_zone.setText("");
            }

            if (pogModel.season != null) {
                ac_season.setText(pogModel.season_name);
                season_code = pogModel.season;
            }

            ed_pog_qty.setText(pogModel.pog_qty);

            if (pogModel.date != null) {
                ed_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(pogModel.date));
            }
            ed_remarks.setText(pogModel.remarks);

            if (pogModel.customer_or_distributor != null) {  //todo pogModel.customer_or_distributor != null
                search_cust_dist_layout.setStartIconDrawable(null);
                ac_customer.setText(pogModel.customer_or_distributor_name);
                customer_name = pogModel.customer_or_distributor_name;
                ac_customer.setSelection(ac_customer.getText().length());
                frame_layout_org_list.setVisibility(View.GONE);
                customer_code = pogModel.customer_or_distributor;
            }

            udpate_pog.setOnClickListener(view -> {
                if (ed_pog_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter qty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pog_qty.getText().toString().trim().equalsIgnoreCase(".") ||
                        ed_pog_qty.getText().toString().trim().equalsIgnoreCase(".0") || ed_pog_qty.getText().toString().trim().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(), "Please enter correct Qty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_employee_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter employee name", Toast.LENGTH_SHORT).show();
                }
                    else {
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        POGModel pogModel1 = new POGModel();
                        try {
                            pogModel1.pog_code = pogModel.pog_code;
                            pogModel1.zone = zonecode != null ? zonecode : "";
                            pogModel1.emp_name = ed_employee_name.getText().toString().trim();
                            pogModel1.season = season_code != null ? season_code : "";
                            pogModel1.season_name = ac_season.getText().toString().trim();
                            pogModel1.crop = crop_code != null ? crop_code : "";
                            pogModel1.crop_name = ac_crop.getText().toString().trim();
                            pogModel1.hybrid = hybrid_code != null ? hybrid_code : "";
                            pogModel1.hybrid_name = hybrid_string;
                            pogModel1.customer_or_distributor = customer_code != null ? customer_code : "";
                            pogModel1.customer_or_distributor_name=ac_customer.getText().toString().trim();
                            pogModel1.Search_Name= customer_name != null ?customer_name : "";
                            pogModel1.pog_qty = ed_pog_qty.getText().toString().trim();
                            pogModel1.date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                            pogModel1.remarks = ed_remarks.getText().toString().trim();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String jsonString = new Gson().toJson(pogModel1);
                        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        Call<List<ResponseModel>> call = mAPIService.updatePog(asJsonObject);
                        call.enqueue(new Callback<List<ResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<ResponseModel> pog_response_list = response.body();
                                        if (pog_response_list != null && pog_response_list.size() > 0 && pog_response_list.get(0).condition) {
                                            getPOGList();
                                            Toast.makeText(getActivity(), pog_response_list.get(0).message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), pog_response_list != null && pog_response_list.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_pog", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_pog", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
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
                /*HybridItemAliasNameAdapter hybridItemAliasNameAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid_name.setAdapter(hybridItemAliasNameAdapter); //todo change in hybrid field set hybrid code(Item_No) instead of alias name on (04-01-2022). */
                ItemVarietyAdapter varietyAdapter = new ItemVarietyAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
                ac_hybrid_name.setAdapter(varietyAdapter);
            } else {
                ac_hybrid_name.setAdapter(null);
                ac_hybrid_name.setText("");
            }
        }
    }


    private void getRoleMasterC_DData(ProgressBar loading_content, AutoCompleteTextView ac_customer, String search_name){
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributor("C_D",search_name,"");
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if(response.isSuccessful()){
                        loading_content.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModel = response.body();
                        if(roleMasterModel != null && roleMasterModel.condition){
                           List<RoleMasterModel.Data > roleMasterList = roleMasterModel.data;
                           if(roleMasterList != null && roleMasterList.size()>0){
                               roleMasterTableList_gl.clear();
                               for(int i=0;i<roleMasterList.size();i++){
                                   if(i>10)
                                       break;
                                   roleMasterTableList_gl.add(roleMasterList.get(i));
                               }
                              // roleMasterTableList_gl = roleMasterList;
                               masterCDAdapter.notifyDataSetChanged();
                           }else {
                               frame_layout_org_list.setVisibility(View.GONE);
                           }
                        }else {
                            frame_layout_org_list.setVisibility(View.GONE);
                            loading_content.setVisibility(View.GONE);
                         //   Toast.makeText(getActivity(), "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        frame_layout_org_list.setVisibility(View.GONE);
                        loading_content.setVisibility(View.GONE);
                     //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    loading_content.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_customer_no", getActivity());
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_customer_no", getActivity());
            }
        });
    }


    private void getRoleMasterData(ProgressBar loading_content, RecyclerView lv_cust_dist_list,String fliter_key,MaterialCardView frame_layout_org_list) {  //todo ProgressBar loading_content, RecyclerView lv_cust_dist_list,String fliter_key,MaterialCardView frame_layout_org_list
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributor("C_D",fliter_key,"");
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_content.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList != null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList = roleMasterModelList.data;
                            if (rolemasterList != null && rolemasterList.size() > 0) {
                                roleMasterTableList_gl = rolemasterList;
                                setAdapter(lv_cust_dist_list);
                                Toast.makeText(getActivity(), "customer fetch successful !", Toast.LENGTH_SHORT).show();
                            } else {
                                frame_layout_org_list.setVisibility(View.GONE);
                                lv_cust_dist_list.setAdapter(null);
                            }
                        } else {
                            frame_layout_org_list.setVisibility(View.GONE);
                            loading_content.setVisibility(View.GONE);
                            lv_cust_dist_list.setAdapter(null);
                            Toast.makeText(getActivity(), "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        frame_layout_org_list.setVisibility(View.GONE);
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_customer_no", getActivity());
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_customer_no", getActivity());
            }
        });
    }

    private void setAdapter(RecyclerView lv_cust_dist_list){
        if (roleMasterTableList_gl != null && roleMasterTableList_gl.size()>0) {
            RoleMasterAdapter roleMasterAdapter = new RoleMasterAdapter(getActivity(), roleMasterTableList_gl);
            lv_cust_dist_list.setAdapter(roleMasterAdapter);
//            roleMasterAdapter.setOnClick(this);
        }else {
            RoleMasterAdapter roleMasterAdapter = new RoleMasterAdapter(getActivity(), new ArrayList<>());
            lv_cust_dist_list.setAdapter(roleMasterAdapter);
//            roleMasterAdapter.setOnClick(this);
            frame_layout_org_list.setVisibility(View.GONE);
        }
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

  /*  @Override
    public void onItemClick(int pos) {
        try {
            frame_layout_org_list.setVisibility(View.GONE);
            roleMasterTable = roleMasterTableList.get(pos);
            if (roleMasterTable != null) {
                ac_customer.setText(roleMasterTable.name);
                frame_layout_org_list.setVisibility(View.GONE);
                customer_code = roleMasterTable.no;

            } else {
                frame_layout_org_list.setVisibility(View.GONE);
                ac_customer.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    */
}