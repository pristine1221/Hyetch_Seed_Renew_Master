package com.example.pristineseed.ui.vendor;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.collection.InsertCollectionResponse;
import com.example.pristineseed.model.home.CollectionList;
import com.example.pristineseed.model.item.BankAccountModel;
import com.example.pristineseed.model.item.BankMaserModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.header_line.CollectionListAdapter;
import com.example.pristineseed.ui.adapter.item.BankMasterAdapter;
import com.example.pristineseed.ui.adapter.item.DepositeBankAccountAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pristineseed.common_data.CommonData.paymentType;


public class    CollectionFragment extends Fragment implements RoleMasterAdapter.OnItemClickListner {
   private SessionManagement sessionManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

  private   SwipeRefreshLayout swipe_refresh_layout;
   private Chip add_newItem;
   private ListView listview;
   private CollectionListAdapter collectionListAdapter;
   private List<BankAccountModel.Data> bankAccountModelList;
    NetworkInterface mAPIService;
    private List<CollectionList> collection_table_list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
         mAPIService = ApiUtils.getPristineAPIService();
         listview = view.findViewById(R.id.listview);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        add_newItem = view.findViewById(R.id.add_newItem);
        boolean isNetwork= NetworkUtil.getConnectivityStatusBoolean(getActivity());
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup();
        });

        if(isNetwork) {
            callGetCollectionData();}
        else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }
        getBankAccountList();

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(collection_table_list!=null && collection_table_list.size()>0){
                        collection_table_list.clear();
                    }
                    if(isNetwork) {
                        //collection_table_list.clear();
                        callGetCollectionData();
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
    private List<RoleMasterModel.Data> roleMasterTableList=null;
   private   RoleMasterModel.Data roleMasterTable=null;
   private  RelativeLayout frame_layout_org_list;
   private  RecyclerView lv_cust_dist_list;
   private TextInputLayout party_input_layout;
   private TextInputEditText ac_party_name;
   private   RoleMasterAdapter roleMasterAdapter=null;

    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_collection_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                dialog.getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        }
        dialog.show();
        TextInputEditText et_date =popupView.findViewById(R.id.et_date);
        TextInputEditText et_date_of_receipt=popupView.findViewById(R.id.et_date_of_receipt);

        AutoCompleteTextView payment_type_auto_text=popupView.findViewById(R.id.payment_type);
         ac_party_name=popupView.findViewById(R.id.ac_party_name);
        TextInputEditText ed_cheq_address=popupView.findViewById(R.id.chq_address);
        TextInputEditText ed_drwn_bank=popupView.findViewById(R.id.drawn_bank);
        AutoCompleteTextView ac_deposite_bank=popupView.findViewById(R.id.ac_deposite_bank);
        TextInputEditText ed_deposite_at=popupView.findViewById(R.id.deposite_at);
        AutoCompleteTextView ac_bank=popupView.findViewById(R.id.ac_bank);
        TextInputEditText ed_remark=popupView.findViewById(R.id.remark);
        TextInputEditText ed_amount=popupView.findViewById(R.id.ed_amount);
        TextInputEditText ac_place =popupView.findViewById(R.id.ac_place);
        MaterialProgressBar bank_loading_progress=popupView.findViewById(R.id.bank_loading_progress);
         frame_layout_org_list=popupView.findViewById(R.id.frame_layout_org_list);
         ProgressBar search_loading_item=popupView.findViewById(R.id.loading_item);
         lv_cust_dist_list=popupView.findViewById(R.id.lv_cust_dist_list);
         party_input_layout=popupView.findViewById(R.id.party_input_layout);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);

        et_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(et_date);
            return  true;
        });

        et_date_of_receipt.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(et_date_of_receipt);
            return  true;
        });


        ac_party_name.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                party_input_layout.setStartIconDrawable(null);
            }else {
                if(!ac_party_name.getText().toString().trim().equalsIgnoreCase("")){
                    party_input_layout.setStartIconDrawable(null);
                }
                else {
                    party_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });


        ac_party_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_party_name.setSelection(ac_party_name.getText().toString().length());
                if(!s.toString().equals("")) {
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getRoleMasterData(search_loading_item, lv_cust_dist_list, s.toString());
                }
                else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getBankMasterData(bank_loading_progress,ac_bank);
        bindBankAccountData(ac_deposite_bank);

        List<String> payment_type = Arrays.asList(paymentType);
        ArrayAdapter<String> payemnt_type_adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.android_item_view, R.id.text_view, payment_type);
        payment_type_auto_text.setAdapter(payemnt_type_adapter);
        payment_type_auto_text.setAdapter(payemnt_type_adapter);

     /*   lv_cust_dist_list.setOnItemClickListener((parent, view, position, id) -> {
            try {
                frame_layout_org_list.setVisibility(View.GONE);
                if (roleMasterTableList != null && roleMasterTableList.size() > 0) {
                    roleMasterTable = roleMasterTableList.get(position);
                    if (roleMasterTable != null) {
                        ac_party_name.setText(roleMasterTable.name);
                        frame_layout_org_list.setVisibility(View.GONE);
                     } else {
                        ac_party_name.setText("");
                        frame_layout_org_list.setVisibility(View.GONE);
                    }
                }
              }catch (Exception e){
                e.printStackTrace();
            }

        });*/

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        filter_apply_bt.setOnClickListener(view -> {
            if(et_date.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter date!",Toast.LENGTH_SHORT).show();
            }
            else if(ac_party_name.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter party name!",Toast.LENGTH_SHORT).show();
            }
            else if(ed_cheq_address.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter cheq rtgs no name!",Toast.LENGTH_SHORT).show();
            }
            else if(ac_deposite_bank.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter deposite bank !",Toast.LENGTH_SHORT).show();
            }
            else if(ed_deposite_at.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter deposite at !",Toast.LENGTH_SHORT).show();
            }
            else if(payment_type_auto_text.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter payment type !",Toast.LENGTH_SHORT).show();
            }

            else if(ac_bank.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter bank!",Toast.LENGTH_SHORT).show();
            }

            else if(et_date_of_receipt.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter date of receipt!",Toast.LENGTH_SHORT).show();
            }
            else if(ed_remark.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter remark!",Toast.LENGTH_SHORT).show();
            }
            else if(ed_drwn_bank.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getActivity(),"Please enter drawn bank!",Toast.LENGTH_SHORT).show();
            }
            else if(ed_amount.getText().toString().trim().equals("")){
                Toast.makeText(getActivity(),"Please enter amount !",Toast.LENGTH_SHORT).show();
            }
            else if(ed_amount.getText().toString().trim().equalsIgnoreCase(".")||
                    ed_amount.getText().toString().trim().equalsIgnoreCase(".0")|| ed_amount.getText().toString().trim().equalsIgnoreCase("0.")){

                Toast.makeText(getActivity(),"Please enter correct input",Toast.LENGTH_SHORT).show();
            }
            else {
                boolean isNetwork= NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if(isNetwork) {
                    LoadingDialog progressDialog=new LoadingDialog();
                    progressDialog.showLoadingDialog(getActivity());
                    JsonObject jsonObject = new JsonObject();
                    try {
                        jsonObject.addProperty("date", DateTimeUtilsCustome.splitDateInYYYMMDD(et_date.getText().toString().trim()));
                        jsonObject.addProperty("party_name", roleMasterTable.name!=null?roleMasterTable.name:"");
                        jsonObject.addProperty("place", ac_place.getText().toString().trim());
                        jsonObject.addProperty("chq_dd_rtgs_no", ed_cheq_address.getText().toString().trim());
                        jsonObject.addProperty("drawn_on_bank_name", ed_drwn_bank.getText().toString());
                        jsonObject.addProperty("deposited_bank", ac_deposite_bank.getText().toString().trim());
                        jsonObject.addProperty("deposited_at", ed_deposite_at.getText().toString().trim());
                        jsonObject.addProperty("payment_type", payment_type_auto_text.getText().toString().trim());
                        jsonObject.addProperty("bank", ac_bank.getText().toString().trim() );
                        jsonObject.addProperty("date_of_receipt", DateTimeUtilsCustome.splitDateInYYYMMDD(et_date_of_receipt.getText().toString().trim()));
                        jsonObject.addProperty("remark", ed_remark.getText().toString().trim());
                        jsonObject.addProperty("email", sessionManagement.getUserEmail());
                        jsonObject.addProperty("created_on", DateTimeUtilsCustome.splitDateInYYYMMDD(DateTimeUtilsCustome.getCurrentDateBY_()));
                        jsonObject.addProperty("amount", Float.parseFloat(ed_amount.getText().toString().trim()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Call<List<InsertCollectionResponse>> call = mAPIService.insertCollection(jsonObject);
                    call.enqueue(new Callback<List<InsertCollectionResponse>>() {
                        @Override
                        public void onResponse(Call<List<InsertCollectionResponse>> call, Response<List<InsertCollectionResponse>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialog.hideDialog();
                                    List<InsertCollectionResponse> collectionLists = response.body();
                                    if (collectionLists!=null && collectionLists.size() > 0 && collectionLists.get(0).condition) {
                                        if(collectionLists.get(0).collection_code!=null && !collectionLists.get(0).collection_code.equals("")){
                                        for(int i=0;i<collectionLists.size();i++) {
                                            SuccessMessage(collectionLists.get(0).collection_code);
                                            Toast.makeText(getActivity(), collectionLists.get(0).message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        }
                                    } else if(collectionLists==null || collectionLists.size()>0) {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), collectionLists.get(0).message + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Collection", getActivity());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<InsertCollectionResponse>> call, Throwable t) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Collection", getActivity());
                        }
                    });
                }
                else {
                  Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    public void SuccessMessage(String event_code) {
        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = successmessaePopupView.findViewById(R.id.order_no);
            order_no.setText(event_code);
            ImageView succesessImg = successmessaePopupView.findViewById(R.id.succesessImg);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.success_animation);
            succesessImg.startAnimation(animation);

            dialog.setContentView(successmessaePopupView);
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
            dialog.show();
            Button goBackFromItem = successmessaePopupView.findViewById(R.id.goBackFromItem);
            goBackFromItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isNetwork= NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if(isNetwork) {
                        callGetCollectionData();
                        dialog.dismiss();
                    }
                    else {
                        Toast.makeText(getActivity(),"Please wait for online..",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            successmessaePopupView.setFocusableInTouchMode(true);
            successmessaePopupView.requestFocus();
            successmessaePopupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private void callGetCollectionData(){
        LoadingDialog progressDialog=new LoadingDialog();
        progressDialog.showLoadingDialog(getActivity());
        Call<List<CollectionList>> call = mAPIService.getCollectionData(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<CollectionList>>() {
            @Override
            public void onResponse(Call<List<CollectionList>> call, Response<List<CollectionList>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialog.hideDialog();
                           List<CollectionList> collectionLists = response.body();
                        if (collectionLists.size()>0 && collectionLists.get(0).isCondition()) {
                            collection_table_list=collectionLists;
                            bindDataWithLocal(collection_table_list);
                        } else {
                            Toast.makeText(getActivity(), collectionLists.size()>0?"No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        progressDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Collection", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<CollectionList>> call, Throwable t) {
                progressDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Collection", getActivity());
            }
        });
    }
    void bindDataWithLocal(List<CollectionList> collectionLists ){
        if (collectionLists!=null && collectionLists.size() > 0) {
            collectionListAdapter = new CollectionListAdapter(getActivity(), collectionLists);
            listview.setAdapter(collectionListAdapter);
        }
    }
        private void getBankAccountList() {
            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                LoadingDialog progressDialog = new LoadingDialog();
                progressDialog.showLoadingDialog(getActivity());
                Call<BankAccountModel> call = mAPIService.getBankAccountList();
                call.enqueue(new Callback<BankAccountModel>() {
                    @Override
                    public void onResponse(Call<BankAccountModel> call, Response<BankAccountModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialog.hideDialog();
                                BankAccountModel bankAccountModel = response.body();
                                if (bankAccountModel!=null && bankAccountModel.condition) {
                                    List<BankAccountModel.Data> bankAccountList=bankAccountModel.data;
                                    if(bankAccountList!=null && bankAccountList.size()>0){
                                        bankAccountModelList= bankAccountList;
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "No data found" + response.code(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                progressDialog.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_bank_master", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<BankAccountModel> call, Throwable t) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_bank_master", getActivity());
                    }
                });

            }
            else {
                Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
            }
        }

        private void bindBankAccountData(AutoCompleteTextView ac_deposite_bank){
        if(bankAccountModelList!=null && bankAccountModelList.size()>0){
            DepositeBankAccountAdapter depositeBankAccountAdapter=new DepositeBankAccountAdapter(getActivity(),R.layout.android_item_view,bankAccountModelList);
            ac_deposite_bank.setAdapter(depositeBankAccountAdapter);
            }
        }

    private void getBankMasterData(ProgressBar loading_bank_master, AutoCompleteTextView ac_bank_master){
        loading_bank_master.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<BankMaserModel>call = mAPIService.getBankMasterData();
        call.enqueue(new Callback<BankMaserModel>() {
            @Override
            public void onResponse(Call<BankMaserModel> call, Response<BankMaserModel> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_bank_master.setVisibility(View.GONE);
                        BankMaserModel bankMaserModel = response.body();
                        if (bankMaserModel != null) {
                            List<BankMaserModel.Data> bankMasterList = bankMaserModel.data;
                            if (bankMasterList != null && bankMasterList.size() > 0) {
                                    BankMasterAdapter bankMasterAdapter=new BankMasterAdapter(getActivity(),R.layout.android_item_view,bankMasterList);
                                    ac_bank_master.setAdapter(bankMasterAdapter);
                            } else {
                                loading_bank_master.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Banking Master Record not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loading_bank_master.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    loading_bank_master.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_bank_master", getActivity());
                }
            }
            @Override
            public void onFailure(Call<BankMaserModel>call, Throwable t) {
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_bank_master", getActivity());
            }
        });
    }

    private void getRoleMasterData(ProgressBar hytech_role_master_loading, RecyclerView party_listview, String filter_key){
        hytech_role_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel>call = mAPIService.getDistributor("Distributor",filter_key, sessionManagement.getSalePersonCode());
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        hytech_role_master_loading.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList!=null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList= roleMasterModelList.data;
                            if(rolemasterList!=null && rolemasterList.size()>0) {
                                roleMasterTableList=rolemasterList;
                                setAdapter(party_listview);
                            }
                            else {
                               frame_layout_org_list.setVisibility(View.GONE);
                                hytech_role_master_loading.setVisibility(View.GONE);
                            }
                        } else {
                            frame_layout_org_list.setVisibility(View.GONE);
                            hytech_role_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        frame_layout_org_list.setVisibility(View.GONE);
                        hytech_role_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    hytech_role_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_party_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel>call, Throwable t) {
                hytech_role_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_party_master", getActivity());
            }
        });
    }

    private void setAdapter(RecyclerView party_listview) {
        roleMasterAdapter = new RoleMasterAdapter(getActivity(), roleMasterTableList);
        party_listview.setAdapter(roleMasterAdapter);
        roleMasterAdapter.setOnClick(this);
    }

    @Override
    public void onItemClick(int pos) {
        try {
            frame_layout_org_list.setVisibility(View.GONE);
            if (roleMasterTableList != null && roleMasterTableList.size() > 0) {
                roleMasterTable = roleMasterTableList.get(pos);
                if (roleMasterTable != null) {
                    ac_party_name.setText(roleMasterTable.name);
                    frame_layout_org_list.setVisibility(View.GONE);
                } else {
                    ac_party_name.setText("");
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}