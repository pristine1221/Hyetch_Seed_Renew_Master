package com.example.pristineseed.ui.vendor.Dealer_master;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.Dealer.DealerMasterModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.distributor_master.InsertDistributorModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.StateMasterAdapter;
import com.example.pristineseed.ui.adapter.TalukaAdapter;
import com.example.pristineseed.ui.adapter.header_line.DealerListAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
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

public class DealerMaster_Fragment extends Fragment implements RoleMasterAdapter.OnItemClickListner {

    private ListView dealer_listview;
    private TextView no_record_found;
    private SessionManagement sessionManagement;
    private int page_no = 0;
    private List<DealerMasterModel> dealerlist;
    private DealerListAdapter dealerListAdapter;
    private Chip add_newItem;
    private List<TalukaMasterTable> talukaMasterTableList = null;
    private List<StateMasterTable> stateMasterTableList = null;
    private List<DistricMasterTable> districMasterTableList = null;
    private List<CropMassterModel.Data> cropMasterTableList = null;
    private CropMassterModel.Data cropMasterTable = null;
    private TalukaMasterTable talukaMasterTable = null;
    private StateMasterTable stateMasterTable = null;
    private DistricMasterTable districMasterTable = null;
    private int row_per_page=10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dealer_master_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        dealerlist = new ArrayList<>();
        initView(view);

        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            getDealerrMasterList();
        } else if (!NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
            return;
        }
        add_newItem.setOnClickListener(v -> {
            AddNewItemPopup("Insert", null);

        });
        dealer_listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (dealerlist != null && dealerlist.size() > 0) {
                AddNewItemPopup("Update", dealerlist.get(position));
            }
        });

        dealer_listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (dealerlist != null && dealerlist.size() > 0) {
                showDeletePopUP(dealerlist.get(position).distributor_code, position);
            }
            return true;
        });

        dealer_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if(firstVisibleItem + visibleItemCount == totalItemCount){
                      //  page_no++;
                  }
               // getDealerrMasterList();
            }
        });
    }

    private void initView(View view) {
        dealer_listview = view.findViewById(R.id.dealer_listview);
        no_record_found = view.findViewById(R.id.no_data_found);
        add_newItem = view.findViewById(R.id.add_newItem);
    }

    private void getDealerrMasterList() {
        LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.showLoadingDialog(getActivity());
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
        jsonObject.addProperty("isType", "Dealer");
        jsonObject.addProperty("query_parameter", "");
        jsonObject.addProperty("RowsPerPage", 200);
        jsonObject.addProperty("PageNumber", page_no);
        Call<List<DealerMasterModel>> call = mAPIService.getDealerMasterList(jsonObject);
        call.enqueue(new Callback<List<DealerMasterModel>>() {
            @Override
            public void onResponse(Call<List<DealerMasterModel>> call, Response<List<DealerMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialog.hideDialog();
                        List<DealerMasterModel> dealer_list_response = response.body();
                        if (dealer_list_response!=null && dealer_list_response.size() > 0 && dealer_list_response.get(0).condition) {
                            if (dealer_list_response.get(0).distributor_code != null) {
                                dealerlist = dealer_list_response;
                                Toast.makeText(getActivity(), "Data fetch successfully.", Toast.LENGTH_SHORT).show();
                                bindDataWithLocal(dealer_list_response);
                            }
                        } else {
                            no_record_found.setVisibility(View.VISIBLE);
                            dealer_listview.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), dealer_list_response.size() > 0 && dealer_list_response.get(0).condition ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.hideDialog();
//                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_dealer_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<DealerMasterModel>> call, Throwable t) {
                progressDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_dealer_master", getActivity());
            }
        });

    }

    private void bindDataWithLocal(List<DealerMasterModel> distributionList) {
        dealerListAdapter = new DealerListAdapter(getActivity(), distributionList);
        dealer_listview.setAdapter(dealerListAdapter);
    }


    private  List<RoleMasterModel.Data> roleMasterTableList=null;
    private RoleMasterModel.Data roleMasterTable=null;
    private String state_code, taluka_code, disctrict_code, crop_code,customer_code;
    private TextInputLayout customer_layout=null;
    private RecyclerView lv_cust_dist_list=null;
    private MaterialCardView frame_layout_org_list=null;
    private TextInputEditText ac_customer=null;
    private AutoCompleteTextView ac_district;


    public void AddNewItemPopup(String flag, DealerMasterModel distributorListModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_dealer_pop_up_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        ac_customer = popupView.findViewById(R.id.ac_customer);
        Button update_btn = popupView.findViewById(R.id.update_btn);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        TextInputEditText ed_dealer_namae = popupView.findViewById(R.id.dealer_name);
        TextInputEditText ed_place = popupView.findViewById(R.id.ed_place);
        TextInputEditText ed_phone = popupView.findViewById(R.id.ed_phone);
        TextInputEditText ed_email = popupView.findViewById(R.id.ed_email);
        TextInputEditText ac_taluka = popupView.findViewById(R.id.ac_taluka);
        AutoCompleteTextView ac_crop = popupView.findViewById(R.id.ac_crop);
        ac_district = popupView.findViewById(R.id.ac_district);
        AutoCompleteTextView ac_state = popupView.findViewById(R.id.ac_state);
        TextInputEditText ed_turnover = popupView.findViewById(R.id.ed_annual_turnover);
        TextInputEditText ed_remarks = popupView.findViewById(R.id.remarks);
        TextView tv_deal_code=popupView.findViewById(R.id.tv_deal_code);
        MaterialProgressBar content_loading=popupView.findViewById(R.id.p_Bar);
         frame_layout_org_list=popupView.findViewById(R.id.frame_layout_org_list);
         customer_layout=popupView.findViewById(R.id.customer_layout);
        ProgressBar loading_search_item=popupView.findViewById(R.id.loading_item);
         lv_cust_dist_list=popupView.findViewById(R.id.lv_cust_dist_list);
         TextInputLayout taluka_input_layout = popupView.findViewById(R.id.taluka_input_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);

        getCropMaster(content_loading,ac_crop);
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
           // CropHytechMasterDao cropMasterDao = pristineDatabase.cropHytechMasterDao();
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            talukaMasterTableList = talukaMasterDao.getAllData();
            stateMasterTableList = stateMasterDao.getAllData();
            districMasterTableList = districMasterDao.getAllData();
          //  crop  MasterTableList = cropMasterDao.getAllData();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
           /* if (talukaMasterTableList != null && talukaMasterTableList.size() > 0) {
                TalukaAdapter talukaAdapter = new TalukaAdapter(getActivity(), R.layout.android_item_view, talukaMasterTableList);
                ac_taluka.setAdapter(talukaAdapter);
            }*/

          /*  if (cropMasterTableList != null && cropMasterTableList.size() > 0) {
                CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.item_view, cropMasterTableList);
                ac_crop.setAdapter(cropAdapter);
            }*/
        }

        ac_customer.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                customer_layout.setStartIconDrawable(null);
            }else {
                if(!ac_customer.getText().toString().trim().equalsIgnoreCase("")){
                    customer_layout.setStartIconDrawable(null); }
                else {
                    customer_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

        ac_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ac_customer.setSelection(s.toString().length());
                if(!s.toString().equalsIgnoreCase("")){
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getRoleMasterData(loading_search_item,s.toString(),frame_layout_org_list);
                   }
                else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }
        });

        ac_crop.setOnItemClickListener((parent, view, position, id) -> {
            cropMasterTable = cropMasterTableList.get(position);
            if(cropMasterTable!=null) {
                crop_code = cropMasterTableList.get(position).Code;
                ac_crop.setText(cropMasterTable.Description);
            }
            else {
                ac_crop.setText("");
            }

        });

   /*     ac_taluka.setOnItemClickListener((parent, view, position, id) -> {
            talukaMasterTable = talukaMasterTableList.get(position);
            if(talukaMasterTable!=null) {
                taluka_code = talukaMasterTableList.get(position).getCode();
                ac_taluka.setText(talukaMasterTable.getDescription());
            }
            else {
                ac_taluka.setText("");
            }
        });
*/

        if (stateMasterTableList != null && stateMasterTableList.size() > 0) {
            StateMasterAdapter stateAdapter = new StateMasterAdapter(getActivity(), R.layout.android_item_view, stateMasterTableList);
            ac_state.setAdapter(stateAdapter);
        }

       /* ac_state.setOnItemClickListener((parent, view, position, id) -> {
            stateMasterTable = stateMasterTableList.get(position);
            if(stateMasterTable!=null) {
                state_code = stateMasterTableList.get(position).getCode();
                ac_state.setText(stateMasterTable.getName());
            }
        });*/
         ac_state.setOnItemClickListener((parent, view, position, id) -> {
             districMasterTableList.clear();
             bindDistrict(stateMasterTableList.get(position).getCode());
            stateMasterTable = stateMasterTableList.get(position);
            if(stateMasterTable!=null) {
                state_code = stateMasterTable.getCode();
                ac_state.setText(stateMasterTable.getName());
                ac_state.setSelection(ac_state.getText().length());
                ac_state.setError(null);
                ac_district.setText("");
            }
        });

        /*if (districMasterTableList != null && talukaMasterTableList.size() > 0) {
            DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.android_item_view, districMasterTableList);
            ac_district.setAdapter(districtAdapter);
        }*/

        ac_district.setOnItemClickListener((parent, view, position, id) -> {
            districMasterTable = districMasterTableList.get(position);
            if(districMasterTable!=null) {
                disctrict_code = districMasterTableList.get(position).getCode();
                ac_district.setText(districMasterTable.getName());
            }
        });

        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (flag.equalsIgnoreCase("Update")) {
            update_btn.setVisibility(View.VISIBLE);
            filter_apply_bt.setVisibility(View.GONE);
            tv_deal_code.setVisibility(View.VISIBLE);
            tv_deal_code.setText("( "+distributorListModel.distributor_code+" )");
            // main_layout.requestFocus();
            try {
                ed_email.setText(distributorListModel.email);
                ed_phone.setText(distributorListModel.phone_no);
                ed_place.setText(distributorListModel.place);
                ed_dealer_namae.setText(distributorListModel.customer);
                ed_remarks.setText(distributorListModel.remarks);
                if(distributorListModel.distributor_code!=null) {
                    ac_customer.setText(distributorListModel.distributor_name);
                    customer_layout.setStartIconDrawable(null);
                    frame_layout_org_list.setVisibility(View.GONE);
                }
                ed_turnover.setText(distributorListModel.annual_turnover);

                if (distributorListModel.state != null && !distributorListModel.state.equalsIgnoreCase("")) {
                    ac_state.setText(distributorListModel.state_name);
                    ac_state.setSelection(ac_state.getText().length());
                   state_code= distributorListModel.state;
                }
                if (distributorListModel.taluka != null && !distributorListModel.taluka.equalsIgnoreCase("")) {
                    ac_taluka.setText(distributorListModel.taluka);
//                    taluka_code=distributorListModel.taluka_name;
                }

                if (distributorListModel.district != null && !distributorListModel.district.equalsIgnoreCase("")) {
                    ac_district.setText(distributorListModel.district_name);
                    disctrict_code=distributorListModel.district_name;
                }

                if (distributorListModel.crop != null && !distributorListModel.crop.equalsIgnoreCase("")) {
                    ac_crop.setText(distributorListModel.crop_name);
                    crop_code=distributorListModel.crop_name;
                }

                update_btn.setOnClickListener(v -> {
                    if (ed_dealer_namae.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter dealer name.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_phone.getText().toString().trim().length() != 10) {
                        Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ed_email.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter email !", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (ed_place.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter Pan no. !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ac_crop.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter crop !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (distributorListModel.distributor_code == null && distributorListModel.distributor_code.equals("")) {
                        Toast.makeText(getActivity(), "Please enter customer no. !", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(ed_turnover.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please enter anuual turnover", Toast.LENGTH_SHORT).show();

                    }else if(ed_turnover.getText().toString().trim().equalsIgnoreCase(".") || ed_turnover.getText().toString().trim().equalsIgnoreCase(".0")
                            ||ed_turnover.getText().toString().trim().equalsIgnoreCase("0.")) {
                        Toast.makeText(getActivity(), "Please enter correct input", Toast.LENGTH_SHORT).show();
                    }else {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("distributor_code", distributorListModel.distributor_code);
                        jsonObject.addProperty("distributor_name", ac_customer.getText().toString().trim());
                        jsonObject.addProperty("customer", ed_dealer_namae.getText().toString().trim());
                        jsonObject.addProperty("email", ed_email.getText().toString().trim());
                        jsonObject.addProperty("phone_no", ed_phone.getText().toString().trim());
                        jsonObject.addProperty("place", ed_place.getText().toString().trim());
                        /*jsonObject.addProperty("taluka",taluka_code!=null?taluka_code:"");
                        jsonObject.addProperty("taluka_name",ac_taluka.getText().toString().trim());*/
                        jsonObject.addProperty("taluka",ac_taluka.getText().toString().trim());
                        jsonObject.addProperty("state", state_code!=null?state_code:"");
                        jsonObject.addProperty("state_name",ac_state.getText().toString().trim());
                        jsonObject.addProperty("district", disctrict_code!=null?disctrict_code:"");
                        jsonObject.addProperty("district_name",ac_district.getText().toString().trim());
                        jsonObject.addProperty("crop", crop_code);
                        jsonObject.addProperty("crop_name", ac_crop.getText().toString().trim());
                        jsonObject.addProperty("annual_turnover", ed_turnover.getText().toString().trim());
                        jsonObject.addProperty("remarks", ed_remarks.getText().toString().trim());
                        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                        if (isNetwork) {
                            Log.e("json_first", jsonObject.toString());
                            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                            LoadingDialog progressDialog = new LoadingDialog();
                            progressDialog.showLoadingDialog(getActivity());
                            Call<List<InsertDistributorModel>> call = mAPIService.updateDistributorCustomerFarmer(jsonObject);
                            call.enqueue(new Callback<List<InsertDistributorModel>>() {
                                @Override
                                public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            progressDialog.hideDialog();
                                            List<InsertDistributorModel> insertDistributorModelList = response.body();
                                            if (insertDistributorModelList.size() > 0 && insertDistributorModelList.get(0).condition) {
                                                getDealerrMasterList();
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), insertDistributorModelList.get(0).message + insertDistributorModelList.get(0).distributor_code, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), insertDistributorModelList.size() > 0 ? insertDistributorModelList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            progressDialog.hideDialog();
                                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Update_dealer", getActivity());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Update_dealer", getActivity());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (flag.equalsIgnoreCase("insert")) {
            update_btn.setVisibility(View.GONE);
            filter_apply_bt.setVisibility(View.VISIBLE);
            tv_deal_code.setVisibility(View.GONE);

            filter_apply_bt.setOnClickListener(view -> {
                if (ed_dealer_namae.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter dealer name.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_phone.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter email !", Toast.LENGTH_SHORT).show();
                    return;

                } else if (ed_place.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter Pan no. !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_crop.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter crop !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (roleMasterTable.no == null && roleMasterTable.no.equals("")) {
                    Toast.makeText(getActivity(), "Please enter customer no. !", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ed_turnover.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter anuual turnover", Toast.LENGTH_SHORT).show();

                }else if(ed_turnover.getText().toString().trim().equalsIgnoreCase(".") || ed_turnover.getText().toString().trim().equalsIgnoreCase(".0")
                    ||ed_turnover.getText().toString().trim().equalsIgnoreCase("0.")) {
                    Toast.makeText(getActivity(),"Please enter correct input",Toast.LENGTH_SHORT).show();

                } else {
                    // call submit api...
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("distributor_code",  roleMasterTable.no!=null?roleMasterTable.no:"");
                        jsonObject.addProperty("distributor_name", ac_customer.getText().toString().trim());
                        jsonObject.addProperty("customer", ed_dealer_namae.getText().toString().trim());
                        jsonObject.addProperty("email", ed_email.getText().toString().trim());
                        jsonObject.addProperty("phone_no", ed_phone.getText().toString().trim());
                        jsonObject.addProperty("place", ed_place.getText().toString().trim());
                        /*jsonObject.addProperty("taluka", taluka_code);
                        jsonObject.addProperty("taluka_name",ac_taluka.getText().toString().trim());*/
                        jsonObject.addProperty("taluka",ac_taluka.getText().toString().trim());
                        jsonObject.addProperty("state", state_code);
                        jsonObject.addProperty("state_name",ac_state.getText().toString().trim());
                        jsonObject.addProperty("district", disctrict_code);
                        jsonObject.addProperty("district_name",ac_district.getText().toString().trim());
                        jsonObject.addProperty("crop",crop_code);
                        jsonObject.addProperty("crop_name",ac_crop.getText().toString().trim());
                        jsonObject.addProperty("annual_turnover", ed_turnover.getText().toString().trim());
                        jsonObject.addProperty("remarks", ed_remarks.getText().toString().trim());
                        jsonObject.addProperty("created_by", sessionManagement.getUserEmail());
                        jsonObject.addProperty("created_on", DateTimeUtilsCustome.getCurrentTime());
                        jsonObject.addProperty("isType", "Dealer");

                        Log.e("json_first", jsonObject.toString());
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<InsertDistributorModel>> call = mAPIService.insertDistributorCustomerFarmer(jsonObject);
                        call.enqueue(new Callback<List<InsertDistributorModel>>() {
                            @Override
                            public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<InsertDistributorModel> insertDistributorModelList = response.body();
                                        if (insertDistributorModelList!=null && insertDistributorModelList.size() > 0 && insertDistributorModelList.get(0).condition) {
                                            dialog.dismiss();
                                            getDealerrMasterList();
                                            Toast.makeText(getActivity(), insertDistributorModelList.get(0).message + insertDistributorModelList.get(0).distributor_code, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), insertDistributorModelList==null || insertDistributorModelList.size() > 0 ? insertDistributorModelList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Insert_Dealer", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert_Dealer", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for online connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    void showDeletePopUP(String distributor_code, int position) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View PopupView = inflater.inflate(R.layout.delete_dialog_pop_up, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.setContentView(PopupView);
        dialog.setCancelable(false);
        dialog.show();
        TextView tv_distributor_code = PopupView.findViewById(R.id.dist_code);
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
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("distributor_code", distributor_code);
            jsonObject.addProperty("isType", "Dealer");
            jsonObject.addProperty("nav_code", "");
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.deleteDistributor_customer_farmer(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<ResponseModel> distributionList = response.body();
                            if (distributionList!=null && distributionList.size() > 0 && distributionList.get(0).condition) {
                                dealerlist.remove(position);
                                dealerListAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(getActivity(), distributionList.get(0).message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), distributionList.size() > 0 ? "No data found Dealer" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_dealer", getActivity());
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_dealer", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for online", Toast.LENGTH_SHORT).show();
            //todo offline task...........
        }
    }


    private void getRoleMasterData(ProgressBar role_master_loading,String filter_key,MaterialCardView cust_list_layout ){
        role_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributor("Distributor",filter_key, sessionManagement.getSalePersonCode());
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        role_master_loading.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList!=null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList= roleMasterModelList.data;
                            if(rolemasterList!=null && rolemasterList.size()>0) {
                                roleMasterTableList=rolemasterList;
                                setAdapter();
                               }
                             else {
                                role_master_loading.setVisibility(View.GONE);
                                cust_list_layout.setVisibility(View.GONE);
                            }
                        } else {
                            cust_list_layout.setVisibility(View.GONE);
                            role_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        cust_list_layout.setVisibility(View.GONE);
                        role_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    role_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_dealer_role_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel>call, Throwable t) {
                role_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_dealer_role_master", getActivity());
            }
        });
    }

    private void setAdapter(){
        RoleMasterAdapter roleMasterAdapter=new RoleMasterAdapter(getActivity(),roleMasterTableList);
        lv_cust_dist_list.setAdapter(roleMasterAdapter);
        roleMasterAdapter.setOnClick(this);
    }

    void bindDistrict(String state_code){
        districMasterTableList = getdistricMasterTableList(state_code);
        if (getdistricMasterTableList(state_code)!= null && getdistricMasterTableList(state_code).size()>0
                && districMasterTableList != null && districMasterTableList.size()>0) {
            DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.android_item_view, districMasterTableList);
            ac_district.setAdapter(districtAdapter);
        }
       /* else {
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
                                cropMasterTableList=crop_master_list;
                                if(cropMasterTableList.size()>0){
                                    CropHytechAdapter cropHytechAdapter=new CropHytechAdapter(getActivity(),R.layout.android_item_view, cropMasterTableList);
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

    @Override
    public void onItemClick(int pos) {
        frame_layout_org_list.setVisibility(View.GONE);
        roleMasterTable = roleMasterTableList.get(pos);
         if(roleMasterTable!=null) {
            frame_layout_org_list.setVisibility(View.GONE);
            ac_customer.setText(roleMasterTable.name);
            customer_code = roleMasterTable.no;
         }
        else {
            frame_layout_org_list.setVisibility(View.GONE);
            ac_customer.setText("");
        }
    }
}
