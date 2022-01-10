package com.example.pristineseed.ui.vendor;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
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
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.distributor_master.DistributorListModel;
import com.example.pristineseed.model.distributor_master.InsertDistributorModel;
import com.example.pristineseed.model.item.BankMaserModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.StateMasterAdapter;
import com.example.pristineseed.ui.adapter.TalukaAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.header_line.CustomerListAdapter;
import com.example.pristineseed.ui.adapter.item.BankMasterAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerFragment extends Fragment {
    private SessionManagement sessionManagement;
    private NetworkInterface mAPIService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   private SwipeRefreshLayout swipe_refresh_layout;
    private Chip add_newItem;
    private ListView listview;

    private CustomerListAdapter customer_adapter;
    int init_page=0;

     private List<DistributorListModel> customerList;
     private String zone_string="",state_string="",district_string="",taluka_string="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        mAPIService = ApiUtils.getPristineAPIService();

        initView(view);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("insert",null);
        });

        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            getDistributorData(init_page);
        }
        else {
            Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
        }

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(customerList!=null && customerList.size()>0) {
                        customerList.clear();
                        }
                        if (isNetwork) {
                            getDistributorData(init_page);
                            swipe_refresh_layout.setRefreshing(false);
                        }
                }
            }, 3000); // Delay in millis
        });

        swipe_refresh_layout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if(customerList!=null && customerList.size()>0) {
                AddNewItemPopup("update", customerList.get(position));
            }
        });

        listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            showDeletePopUP(customerList.get(position).distributor_code,position);
            return true;
        });

    }

    private void initView(View view) {
        listview = view.findViewById(R.id.listview);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        add_newItem = view.findViewById(R.id.add_newItem);
    }

    private StateMasterTable stateMasterTable;
    private ZoneMasterTable zoneMasterTable;
    private DistricMasterTable districMasterTable;
    private TalukaMasterTable talukaMasterTable;
    private String state_code="",zone_code="",district_code="",taluka_code="";
    private List<BankMaserModel.Data> bankMasterTableList=null;
    private    List<StateMasterTable> stateMasterList=null;
    private List<ZoneMasterTable> zoneMasterTableList=null;
    private List<DistricMasterTable>districMasterTableList=null;
    private List<TalukaMasterTable> talukaMasterList=null;
    private AutoCompleteTextView dropdown_District_Code;

    public void AddNewItemPopup(String tag, DistributorListModel distributorListModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_customer_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        AutoCompleteTextView state_text_view = popupView.findViewById(R.id.cust_state);
        TextInputEditText dropdown_Bussiness_Type = popupView.findViewById(R.id.drop_down_bank_type);

        dropdown_District_Code = popupView.findViewById(R.id.dropdown_District_Code);

        AutoCompleteTextView dropdown_Zone = popupView.findViewById(R.id.dropdown_Zone);

        TextInputEditText dropdown_taluka = popupView.findViewById(R.id.taluka_drop_down);
        TextInputLayout il_gst_no=popupView.findViewById(R.id.il_gst_no);

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        TextInputEditText ed_cust_name=popupView.findViewById(R.id.cust_name);
        TextInputEditText ed_cust_doa=popupView.findViewById(R.id.cust_doa);
        List<String> forwardby_list=Arrays.asList(CommonData.forward_by);
        ArrayAdapter<String> forward_by_adapter=new ArrayAdapter<String>(getActivity(), R.layout.drop_down_textview,forwardby_list);
        AutoCompleteTextView ed_cust_forward=popupView.findViewById(R.id.forward_by);
        ed_cust_forward.setAdapter(forward_by_adapter);
        TextInputEditText ed_cust_phone=popupView.findViewById(R.id.cust_phone);
        TextInputEditText ed_cust_mobile_no=popupView.findViewById(R.id.cust_mobile_no);
        TextInputEditText ed_cust_address=popupView.findViewById(R.id.cust_address);
        TextInputEditText ed_gst_no=popupView.findViewById(R.id.gstine);
        TextInputEditText ed_pan_no=popupView.findViewById(R.id.pan_aadhar);
        TextInputEditText ed_aadhar_no=popupView.findViewById(R.id.aadhar_no);
        TextInputEditText ed_sd_=popupView.findViewById(R.id.ed_sd_);
        TextInputEditText ed_sd_cheq_no=popupView.findViewById(R.id.sd_cheq_no);
        TextInputEditText ed_s_cheque=popupView.findViewById(R.id.s_cheque);
        AutoCompleteTextView ac_gst_type=popupView.findViewById(R.id.gst_type);
        TextInputEditText ed_gst_seed_lincence=popupView.findViewById(R.id.gst_seed_lincence);
        TextInputEditText ed_seed_validity=popupView.findViewById(R.id.seed_validity);
        TextInputEditText ed_file_no=popupView.findViewById(R.id.file_no);
        AutoCompleteTextView status=popupView.findViewById(R.id.status);
        TextInputEditText ed_code=popupView.findViewById(R.id.code);
        TextInputEditText ed_remarks=popupView.findViewById(R.id.remarks);
        Button clear_data_btn=popupView.findViewById(R.id.clear_data);
        Button update_btn=popupView.findViewById(R.id.update_btn);
        TextInputEditText ed_place=popupView.findViewById(R.id.place_);
        TextInputEditText ed_contact=popupView.findViewById(R.id.contact_no_);
        TextInputEditText ed_post_code=popupView.findViewById(R.id.post_code);
        TextInputEditText ed_total_land=popupView.findViewById(R.id.total_land);
        TextView title_txt=popupView.findViewById(R.id.title_txt);
        List<String> gst_type_array=Arrays.asList(CommonData.gst_type);
        MaterialProgressBar loading_bank_master=popupView.findViewById(R.id.p_Bar);
//        getBankMasterData(loading_bank_master,dropdown_Bussiness_Type);

        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
        try {
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
            talukaMasterList = talukaMasterDao.getAllData();
            zoneMasterTableList = zoneMaterDao.getAllData();
            stateMasterList = stateMasterDao.getAllData();
            districMasterTableList = districMasterDao.getAllData();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }

        List<String> status_list=Arrays.asList(CommonData.status);
        if(stateMasterList!=null && stateMasterList.size()>0) {
            StateMasterAdapter gstRegTypeAdapter = new StateMasterAdapter(getActivity(), R.layout.drop_down_textview, stateMasterList);
            state_text_view.setAdapter(gstRegTypeAdapter);}


        if(zoneMasterTableList!=null && zoneMasterTableList.size()>0) {
            ZoneMasterAdapter zoneAdapter = new ZoneMasterAdapter(getActivity(), R.layout.drop_down_textview, zoneMasterTableList);
            dropdown_Zone.setAdapter(zoneAdapter); }

      /*  if(talukaMasterList!=null && talukaMasterList.size()>0) {
            TalukaAdapter taluka_adpter = new TalukaAdapter(getActivity(), R.layout.drop_down_textview, talukaMasterList);
            dropdown_taluka.setAdapter(taluka_adpter);
        }*/

        dropdown_Zone.setOnItemClickListener((parent, view, position, id) -> {
            zoneMasterTable = zoneMasterTableList.get(position);
            if(zoneMasterTable!=null) {
                zone_code = zoneMasterTableList.get(position).getCode();
                dropdown_Zone.setText(zoneMasterTable.getDescription());
            }
        });
        state_text_view.setOnItemClickListener((adapterView, view, i, l) -> {
            districMasterTableList.clear();
            bindDistrict(stateMasterList.get(i).getCode());
            stateMasterTable = stateMasterList.get(i);
            if(stateMasterTable!=null) {
                state_code = stateMasterList.get(i).getCode();
                state_text_view.setText(stateMasterTable.getName());
                state_text_view.setSelection(state_text_view.getText().length());
                state_text_view.setError(null);
                dropdown_District_Code.setText("");
            }
        });
        dropdown_District_Code.setOnItemClickListener((parent, view, position, id) -> {
            districMasterTable=districMasterTableList.get(position);
            if(districMasterTable!=null) {
                district_code = districMasterTableList.get(position).getCode();
                dropdown_District_Code.setText(districMasterTable.getName());
            }
        });
       /* dropdown_taluka.setOnItemClickListener((parent, view, position, id) -> {
            talukaMasterTable=talukaMasterList.get(position);
            if(talukaMasterTable!=null) {
                taluka_code = talukaMasterList.get(position).getCode();
                dropdown_taluka.setText(talukaMasterTable.getDescription());
            }
        });
*/
        ItemAdapter itemAdapter=new ItemAdapter(getActivity(),R.layout.drop_down_textview,status_list);
        status.setAdapter(itemAdapter);

        ArrayAdapter<String> gst_type_adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, gst_type_array);
        ac_gst_type.setAdapter(gst_type_adapter);


        ac_gst_type.setOnItemClickListener((parent, view, position, id) -> {
            if(gst_type_array.get(position).equalsIgnoreCase("Unregistered")){
                il_gst_no.setVisibility(View.GONE);
                ed_gst_no.setVisibility(View.GONE);}
            else {
                il_gst_no.setVisibility(View.VISIBLE);
                ed_gst_no.setVisibility(View.VISIBLE);}

        });
        clear_data_btn.setOnClickListener(v -> {

            clearAllField( ed_cust_name, ed_cust_doa,  ed_cust_forward,
                    ed_contact,  ed_cust_phone,  ed_cust_mobile_no,
                    ed_cust_address,  ed_pan_no,  ed_sd_,  ed_place,
                     ed_post_code,  ed_s_cheque,  ed_sd_cheq_no,
                    ed_gst_seed_lincence,  ed_seed_validity,  ed_file_no,  title_txt,
                    ed_aadhar_no,  ed_gst_no,  ac_gst_type);
        });

        ed_cust_doa.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_cust_doa);
            return  true;
        });

        ed_seed_validity.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_seed_validity);
            return  true;
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
        if(tag.equalsIgnoreCase("update")){
            update_btn.setVisibility(View.VISIBLE);
            filter_apply_bt.setVisibility(View.GONE);
            clear_data_btn.setVisibility(View.GONE);
            try {
                ed_cust_mobile_no.setText(distributorListModel.mobile != null ? distributorListModel.mobile : "");
                ed_cust_address.setText(distributorListModel.address != null ? distributorListModel.address : "");
                ed_pan_no.setText(distributorListModel.pan_no != null ? distributorListModel.pan_no : "");
                ed_sd_.setText(String.valueOf(distributorListModel.security_deposit));
                ed_s_cheque.setText(distributorListModel.security_deposit_chqs != null ? distributorListModel.security_deposit_chqs : "");
                ed_gst_seed_lincence.setText(distributorListModel.seed_license != null ? distributorListModel.seed_license: "");
                if(distributorListModel.validity != null && !distributorListModel.validity.equalsIgnoreCase("")) {
                    ed_seed_validity.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(distributorListModel.validity));
                }
                ed_file_no.setText(distributorListModel.file_no != null ? distributorListModel.file_no : "");
                status.setText(distributorListModel.status != null ? distributorListModel.status : "");
                ed_code.setText(distributorListModel.code != null ? distributorListModel.code : "");
                ed_remarks.setText(distributorListModel.remarks != null ? distributorListModel.remarks : "");
                ed_cust_phone.setText(distributorListModel.phone_no != null ? distributorListModel.phone_no : "");
                ed_cust_forward.setText(distributorListModel.forward_by != null ? distributorListModel.forward_by : "");
                ed_cust_name.setText(distributorListModel.distributor_name != null ? distributorListModel.distributor_name : "");
                ed_cust_name.setSelection(ed_cust_name.getText().length());
                if(distributorListModel.date_of_joining!= null  && !distributorListModel.date_of_joining.equalsIgnoreCase("")) {
                    ed_cust_doa.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(distributorListModel.date_of_joining));
                }
                dropdown_Bussiness_Type.setText(distributorListModel.bank != null ? distributorListModel.bank : "");
                ed_place.setText(distributorListModel.place!=null?distributorListModel.place:"");
                ed_gst_no.setText(distributorListModel.gst_number!=null?distributorListModel.gst_number:"");
                if(distributorListModel.gst_type!=null && !distributorListModel.gst_type.equalsIgnoreCase("")) {
                    if(distributorListModel.gst_type.equalsIgnoreCase("Unregistered")) {
                        ed_gst_no.setVisibility(View.GONE);
                        ed_gst_no.setVisibility(View.GONE);
                    }
                    ed_gst_no.setVisibility(View.VISIBLE);
                    ed_gst_no.setVisibility(View.VISIBLE);
                    ac_gst_type.setText(distributorListModel.gst_type);
                    }

                ed_aadhar_no.setText(distributorListModel.aadhaar_no);
                ed_total_land.setText(distributorListModel.total_land);
                ed_sd_cheq_no.setText(distributorListModel.security_deposit_chq_no);
                ed_contact.setText(distributorListModel.contact!=null?distributorListModel.contact:"");
                ed_post_code.setText(String.valueOf(distributorListModel.postcode!=null?distributorListModel.postcode:"0"));
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (distributorListModel.state != null && !distributorListModel.state.equalsIgnoreCase("")) {
                state_text_view.setText(distributorListModel.state_name);
                state_code=distributorListModel.state;
            }
            if (distributorListModel.taluka!= null && !distributorListModel.taluka.equalsIgnoreCase("")) {
                dropdown_taluka.setText(distributorListModel.taluka);
//                taluka_code=distributorListModel.taluka;
            }
            if (distributorListModel.district != null && !distributorListModel.district.equalsIgnoreCase("")) {
                dropdown_District_Code.setText(distributorListModel.district_name);
                district_code=distributorListModel.district;

            }
            if (distributorListModel.zone != null && !distributorListModel.zone.equalsIgnoreCase("")) {
                dropdown_Zone.setText(distributorListModel.zone_name);
            }
                update_btn.setOnClickListener(v -> {
                  if (ed_cust_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_mobile_no.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_address.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter address !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pan_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter pan no. !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_sd_.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter sd !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_sd_cheq_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter sd cheque !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_gst_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter gst type !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_gst_seed_lincence.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter seed licence !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_seed_validity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter validity !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_file_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter field no. !", Toast.LENGTH_SHORT).show();
                    return;
                } /*else if (status.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter status !", Toast.LENGTH_SHORT).show();
                    return;
                } */else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter cust. name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_forward.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter forward by !", Toast.LENGTH_SHORT).show();
                    return;}
                else if (ed_cust_doa.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter date of joining !", Toast.LENGTH_SHORT).show();
                    return; }
                else if(ed_seed_validity.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please enter seed validity !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                      DistributorListModel distributorListModel_new = new DistributorListModel();
                    try {
                        distributorListModel_new.distributor_code = distributorListModel.distributor_code;
                        distributorListModel_new.distributor_name = ed_cust_name.getText().toString().trim();
                        distributorListModel_new.date_of_joining = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_cust_doa.getText().toString().trim());
                        distributorListModel_new.forward_by = ed_cust_forward.getText().toString().trim();
                        distributorListModel_new.contact = ed_contact.getText().toString().trim();
                        distributorListModel_new.phone_no = ed_cust_phone.getText().toString().trim();
                        distributorListModel_new.mobile = ed_cust_mobile_no.getText().toString().trim();
                        distributorListModel_new.address = ed_cust_address.getText().toString().trim();
                        distributorListModel_new.place = ed_place.getText().toString().trim();
                        distributorListModel_new.postcode = ed_post_code.getText().toString().trim();
                        distributorListModel_new.state = state_code;
                        distributorListModel_new.state_name=state_text_view.getText().toString().trim();
                        distributorListModel_new.zone = zone_code;
                        distributorListModel_new.zone_name=dropdown_Zone.getText().toString().trim();
                        distributorListModel_new.district = district_code;
                        distributorListModel_new.district_name=dropdown_District_Code.getText().toString().trim();
//                        distributorListModel_new.taluka = taluka_code;
                        distributorListModel_new.taluka=dropdown_taluka.getText().toString().trim();
                        distributorListModel_new.gst_type = ac_gst_type.getText().toString().trim();
                        distributorListModel_new.pan_no = ed_pan_no.getText().toString().trim();
                        distributorListModel_new.aadhaar_no = ed_aadhar_no.getText().toString().trim();
                        distributorListModel_new.total_land = ed_total_land.getText().toString().trim();
                        distributorListModel_new.security_deposit = StaticMethods.convertValueToInt(ed_sd_.getText().toString().trim());
                        distributorListModel_new.security_deposit_chq_no = ed_s_cheque.getText().toString().trim();
                        distributorListModel_new.security_deposit_chqs = ed_s_cheque.getText().toString().trim();
                        distributorListModel_new.gst_number = ed_gst_no.getText().toString();
                        distributorListModel_new.bank = dropdown_Bussiness_Type.getText().toString().trim();
                        distributorListModel_new.seed_license = ed_gst_seed_lincence.getText().toString().trim();
                        distributorListModel_new.validity = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_seed_validity.getText().toString().trim());
                        distributorListModel_new.file_no = ed_file_no.getText().toString().trim();
//                        distributorListModel_new.status = status.getText().toString();
                        distributorListModel_new.code = "0";
                        distributorListModel_new.remarks = ed_remarks.getText().toString().trim();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                      boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                      if (isNetwork) {
                          JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(distributorListModel_new)).getAsJsonObject();
                          Log.e("json_update", jsonObject.toString());
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
                                          if (insertDistributorModelList!=null && insertDistributorModelList.size() > 0 && insertDistributorModelList.get(0).condition) {
                                                getDistributorData(init_page);
                                                dialog.dismiss();
                                              Toast.makeText(getActivity(), insertDistributorModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                          } else {
                                              Toast.makeText(getActivity(), insertDistributorModelList.size() > 0 ? insertDistributorModelList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                          }
                                      } else {
                                          progressDialog.hideDialog();
                                          Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                      }
                                  } catch (Exception e) {
                                      progressDialog.hideDialog();
                                      ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_Customer", getActivity());
                                  }
                              }

                              @Override
                              public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                  progressDialog.hideDialog();
                                  ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_Customer", getActivity());
                              }
                          });
                      } else {
                          //todo offline task...........
                        Toast.makeText(getActivity(),"Please wait for internet connection", Toast.LENGTH_SHORT).show();
                          dialog.dismiss();
                      }
                  }
                });


        }

        if(tag.equalsIgnoreCase("insert")) {
            filter_apply_bt.setOnClickListener(view -> {
                if (ed_cust_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_mobile_no.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_address.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter address !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pan_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter pan no. !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_sd_.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter sd !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_sd_cheq_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter sd cheque !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_gst_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter gst type !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_gst_seed_lincence.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter seed licence !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_seed_validity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter validity !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_file_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter field no. !", Toast.LENGTH_SHORT).show();
                    return;
                }/* else if (status.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter status !", Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter cust. name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_forward.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter forward by !", Toast.LENGTH_SHORT).show();
                    return;}
                else if (ed_cust_doa.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter date of joining !", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ed_seed_validity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter seed validity !", Toast.LENGTH_SHORT).show();
                    return;
                }   else if(ed_aadhar_no.getText().toString().length()>12){
                    Toast.makeText(getActivity(), "Aadhar card length must be 12 in characters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ed_pan_no.getText().toString().length()>10) {
                    Toast.makeText(getActivity(), "Pan card length must be 10 in characters.", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    // call submit api...
                    DistributorListModel distributorListModel1 = new DistributorListModel();
                    try {
                        distributorListModel1.distributor_name = ed_cust_name.getText().toString().trim();
                        distributorListModel1.date_of_joining = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_cust_doa.getText().toString().trim());
                        distributorListModel1.forward_by = ed_cust_forward.getText().toString().trim();
                        distributorListModel1.contact = ed_contact.getText().toString().trim();
                        distributorListModel1.phone_no = ed_cust_phone.getText().toString().trim();
                        distributorListModel1.mobile = ed_cust_mobile_no.getText().toString().trim();
                        distributorListModel1.address = ed_cust_address.getText().toString().trim();
                        distributorListModel1.postcode = ed_post_code.getText().toString().trim();
                        distributorListModel1.place = ed_place.getText().toString().trim();
                        distributorListModel1.state = state_code;
                        distributorListModel1.state_name=state_text_view.getText().toString().trim();
                        distributorListModel1.zone = zone_code;
                        distributorListModel1.zone_name=dropdown_Zone.getText().toString().trim();
                        distributorListModel1.district = district_code;
                        distributorListModel1.district_name=dropdown_District_Code.getText().toString().trim();
//                        distributorListModel1.taluka =taluka_code;
                        distributorListModel1.taluka=dropdown_taluka.getText().toString().trim();
                        distributorListModel1.gst_type = ac_gst_type.getText().toString().trim();
                        distributorListModel1.pan_no = ed_pan_no.getText().toString().trim();
                        distributorListModel1.aadhaar_no = ed_aadhar_no.getText().toString().trim();
                        distributorListModel1.total_land = ed_total_land.getText().toString().trim();
                        distributorListModel1.security_deposit = StaticMethods.convertValueToInt(ed_sd_.getText().toString().trim());
                        distributorListModel1.security_deposit_chq_no = ed_s_cheque.getText().toString().trim();
                        distributorListModel1.security_deposit_chqs = ed_s_cheque.getText().toString().trim();
                        distributorListModel1.gst_number = ed_gst_no.getText().toString().trim();
                        distributorListModel1.bank = dropdown_Bussiness_Type.getText().toString().trim();
                        distributorListModel1.seed_license = ed_gst_seed_lincence.getText().toString().trim();
                        distributorListModel1.validity = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_seed_validity.getText().toString().trim());
                        distributorListModel1.file_no = ed_file_no.getText().toString().trim();
//                        distributorListModel1.status = status.getText().toString();
                        distributorListModel1.code = "0";
                        distributorListModel1.remarks = ed_remarks.getText().toString().trim();
                        distributorListModel1.created_by = sessionManagement.getUserEmail();
                        distributorListModel1.created_on = DateTimeUtilsCustome.getCurrentTime();
                        distributorListModel1.isType = "Customer";
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(distributorListModel1)).getAsJsonObject();
                        Log.e("json_insert",jsonObject.toString());
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        Call<List<InsertDistributorModel>> call = mAPIService.insertDistributorCustomerFarmer(jsonObject);
                        call.enqueue(new Callback<List<InsertDistributorModel>>() {
                            @Override
                            public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<InsertDistributorModel> insertDistributorModelList = response.body();
                                        if (insertDistributorModelList!=null && insertDistributorModelList.size() > 0 && insertDistributorModelList.get(0).condition) {
                                            Toast.makeText(getActivity(), insertDistributorModelList.get(0).message, Toast.LENGTH_SHORT).show();
                                            getDistributorData(init_page);
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), insertDistributorModelList.size() > 0 ? insertDistributorModelList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Insert customer", getActivity());
                                }
                            }
                            @Override
                            public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert customer", getActivity());
                            }
                        });
                    } else {
                        //todo offline task.............
                    }
                }
            });
        }
    }

    void getDistributorData(int page_no) {
        LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.showLoadingDialog(getActivity());
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("created_by",sessionManagement.getUserEmail());
        jsonObject.addProperty("isType","Customer");
        jsonObject.addProperty("query_parameter","");
        jsonObject.addProperty("RowsPerPage","500");
        jsonObject.addProperty("PageNumber",page_no);

        Call<List<DistributorListModel>> call = mAPIService.getDistributorCustomerFarmerList(jsonObject);
        call.enqueue(new Callback<List<DistributorListModel>>() {
            @Override
            public void onResponse(Call<List<DistributorListModel>> call, Response<List<DistributorListModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialog.hideDialog();
                        List<DistributorListModel> distributionList = response.body();
                        if (distributionList!=null && distributionList.size() > 0 && distributionList.get(0).condition) {
                            customerList=distributionList;
                            bindDataWithAdapter(customerList);
                        } else {
                            Toast.makeText(getActivity(), distributionList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_customer", getActivity());
                }
            }
            @Override
            public void onFailure(Call<List<DistributorListModel>> call, Throwable t) {
                progressDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_customer", getActivity());
            }
        });
    }

    private void clearAllField(
            TextInputEditText ed_cust_name, TextInputEditText ed_doa, AutoCompleteTextView ed_forward,
            TextInputEditText ed_contact_per, TextInputEditText ed_phone_no, TextInputEditText ed_mobile_no,
            TextInputEditText ed_address, TextInputEditText ed_pan_aadhar, TextInputEditText ed_sd, TextInputEditText ed_place_,
            TextInputEditText ed_post_code, TextInputEditText ed_sd_chque, TextInputEditText ed_s_cheque,
            TextInputEditText ed_seed_licence, TextInputEditText ed_validity, TextInputEditText ed_file_no, TextView title_text,
            TextInputEditText ed_aadhar_, TextInputEditText ed_gst_number, AutoCompleteTextView ac_gst_type) {
        ed_cust_name.setText("");
        ed_contact_per.setText("");
        ed_address.setText("");
        ed_post_code.setText("");
        ed_seed_licence.setText("");
        ed_aadhar_.setText("");
        ed_doa.setText("");
        ed_phone_no.setText("");
        ed_pan_aadhar.setText("");
        ed_sd_chque.setText("");
        ed_validity.setText("");
        ed_gst_number.setText("");
        ed_forward.setText("");
        ed_mobile_no.setText("");
        ed_sd.setText("");
        ed_s_cheque.setText("");
        ed_file_no.setText("");
        ac_gst_type.setText("");
        ed_place_.setText("");
        title_text.requestFocus(View.SCROLL_INDICATOR_TOP);
    }

    void bindDataWithAdapter(List<DistributorListModel> distributorListModelList) {
                if (distributorListModelList!=null &&distributorListModelList.size() > 0) {
                    customer_adapter = new CustomerListAdapter(getActivity(), distributorListModelList);
                    listview.setAdapter(customer_adapter);
                }
    }

    void showDeletePopUP(String distributor_code,int position){
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
        TextView tv_distributor_code=PopupView.findViewById(R.id.dist_code);
        tv_distributor_code.setText(distributor_code);
        Button cancel_btn=PopupView.findViewById(R.id.cancel_btn);
        Button ok_btn=PopupView.findViewById(R.id.ok_btn);
        cancel_btn.setOnClickListener( v -> {
            dialog.dismiss();
        });
        ok_btn.setOnClickListener(v -> {
            deleteLine(distributor_code,position,dialog);
        });
    }

    private void deleteLine(String distributor_code,int position,Dialog dialog){
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if(isNetwork) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("distributor_code", distributor_code);
            jsonObject.addProperty("isType","Customer");
            jsonObject.addProperty("nav_code","");
            Call<List<ResponseModel>> call = mAPIService.deleteDistributor_customer_farmer(jsonObject);
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<ResponseModel> distributionList = response.body();
                            if (distributionList!=null && distributionList.size() > 0 && distributionList.get(0).condition) {
                                        customerList.remove(position);
                                        customer_adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), distributionList.get(0).message, Toast.LENGTH_SHORT).show();
                                     }
                            else {
                                Toast.makeText(getActivity(), distributionList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete customer", getActivity());
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete customer", getActivity());
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Please wait for online",Toast.LENGTH_SHORT).show();
            //todo offline task...........
        }
    }

    void bindDistrict(String state_code){
        districMasterTableList = getdistricMasterTableList(state_code);
        if (getdistricMasterTableList(state_code)!= null && getdistricMasterTableList(state_code).size()>0
                && districMasterTableList != null && districMasterTableList.size()>0) {
            DistrictAdapter DistrictAdapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
            dropdown_District_Code.setAdapter(DistrictAdapter);
        }
        else {
            Toast.makeText(getActivity(), "No Record Found On "+state_code, Toast.LENGTH_SHORT).show();
        }
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
                            loading_bank_master.setVisibility(View.GONE);
                            List<BankMaserModel.Data> bankMasterList = bankMaserModel.data;
                            if (bankMasterList != null && bankMasterList.size() > 0) {
                                try {
                                    bankMasterTableList=bankMasterList;
                                    BankMasterAdapter bankMasterAdapter=new BankMasterAdapter(getActivity(),R.layout.android_item_view,bankMasterList);
                                    ac_bank_master.setAdapter(bankMasterAdapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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

}