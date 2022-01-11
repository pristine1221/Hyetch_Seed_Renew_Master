package com.example.pristineseed.ui.vendor.distribution_master;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.pristineseed.DataBaseRepository.distribution_master_table.Distribution_master_table;
import com.example.pristineseed.DataBaseRepository.distributor_dao.DistributorDao;
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
import com.example.pristineseed.model.GeoSetupModel.CountModel;
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
import com.example.pristineseed.ui.adapter.header_line.DistributionMasterAdapter;
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

public class DistributionMasterFragment extends Fragment {
    SessionManagement sessionManagement;
    NetworkInterface mAPIService;

    SwipeRefreshLayout swipe_refresh_layout;
    Chip add_newItem;
    ListView listview;
    TextView no_record_found;
    List<DistributorListModel> distribution_table_list = new ArrayList<>();

    private int init_page = 0;

    AutoCompleteTextView dropdown_District_Code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distributor, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        mAPIService = ApiUtils.getPristineAPIService();
        initView(view);
        add_newItem.setOnClickListener(view1 -> {
            AddNewItemPopup("insert", null);
        });

        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            getDistributorData(init_page);
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
        }

        swipe_refresh_layout.setOnRefreshListener(() -> {
            // To keep animation for 4 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(distribution_table_list!=null && distribution_table_list.size()>0) {
                        distribution_table_list.clear();
                    }
                    if (isNetwork) {
                        getDistributorData(init_page);
                        swipe_refresh_layout.setRefreshing(false);
                    } else {
                       bindData();
                        swipe_refresh_layout.setRefreshing(false);
                    }
                }
            }, 3000);
        });
        swipe_refresh_layout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(distribution_table_list!=null && distribution_table_list.size()>0) {
                    showDeletePopUP(distribution_table_list.get(pos).distributor_code, pos);
                }
                return true;
            }
        });
        listview.setOnItemClickListener((parent, view1, position, id) -> {
            if(distribution_table_list!=null && distribution_table_list.size()>0) {
                AddNewItemPopup("Update", distribution_table_list.get(position));
            }
        });
    }

    private void initView(View view) {
        listview = view.findViewById(R.id.listview);
        no_record_found = view.findViewById(R.id.no_data_found);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refresh_layout);
        add_newItem = view.findViewById(R.id.add_newItem);
    }

    private StateMasterTable stateMasterTable;
    private ZoneMasterTable zoneMasterTable;
    private DistricMasterTable districMasterTable;
    private TalukaMasterTable talukaMasterTable;
    private String state_code,taluka_code,disctrict_code,zone_code;
    List<BankMaserModel> bankMasterList=null;

    public void AddNewItemPopup(String flag, DistributorListModel distribution_master_model) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_distributor_popup_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        List<String> gst_type = Arrays.asList(CommonData.gst_type);
//        AutoCompleteTextView dropdown_Bussiness_Type = popupView.findViewById(R.id.dropdown_Bussiness_Type);
        AutoCompleteTextView state_list = popupView.findViewById(R.id.state_name);
        AutoCompleteTextView dropdown_Zone = popupView.findViewById(R.id.dropdown_Zone);
        TextView tv_dist_code=popupView.findViewById(R.id.tv_dist_code);
        TextInputLayout il_gs_no=popupView.findViewById(R.id.il_gs_no);
        MaterialProgressBar loading_bank_master=popupView.findViewById(R.id.p_Bar);

//        AutoCompleteTextView taluka_ = popupView.findViewById(R.id.taluka_);
        dropdown_District_Code=popupView.findViewById(R.id.dropdown_District_Code);

//        getBankMasterData(loading_bank_master,dropdown_Bussiness_Type);

        List<ZoneMasterTable> zoneMasterTableList = getZoneList();
        if(getZoneList()!=null && getZoneList().size()>0) {
            ZoneMasterAdapter zoneAdapter = new ZoneMasterAdapter(getActivity(), R.layout.drop_down_textview, zoneMasterTableList);
            dropdown_Zone.setAdapter(zoneAdapter);
        }
        dropdown_Zone.setOnItemClickListener((parent, view, position, id) -> {
            zoneMasterTable = zoneMasterTableList.get(position);
            if(zoneMasterTable!=null){
                zone_code=zoneMasterTableList.get(position).getCode();
                dropdown_Zone.setText(zoneMasterTable.getDescription());
               }
            else {
                dropdown_Zone.setText("");
            }
        });

        List<StateMasterTable> getStateList = getStateList();
        if(getStateList!=null && getStateList.size()>0) {
            StateMasterAdapter stateAdapter = new StateMasterAdapter(getActivity(), R.layout.drop_down_textview, getStateList);
            state_list.setAdapter(stateAdapter);
        }

       /* state_list.setOnItemClickListener((adapterView, view, i, l) -> {
            stateMasterTable = getStateList.get(i);
            if(stateMasterTable!=null) {
                state_list.setText(stateMasterTable.getName());
                state_code = getStateList.get(i).getCode();
            }
            else {
                state_list.setText("");
            }
        });*/

          state_list.setOnItemClickListener((adapterView, view, i, l) -> {
              districMasterTableList.clear();
              bindDistrict(stateMasterList.get(i).getCode());
              stateMasterTable = stateMasterList.get(i);
              state_list.setText(stateMasterTable.getName());
              state_code = stateMasterList.get(i).getCode();
              state_list.setError(null);
              dropdown_District_Code.setText("");
        });

    /*    List<DistricMasterTable> districMasterTableList = getdistricMasterTableList();
        if(getdistricMasterTableList()!=null && getdistricMasterTableList().size()>0) {
            DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
            dropdown_District_Code.setAdapter(districtAdapter);
        }*/

        dropdown_District_Code.setOnItemClickListener((parent, view, position, id) -> {
            districMasterTable = districMasterTableList.get(position);
            if (districMasterTable != null){
                dropdown_District_Code.setText(districMasterTable.getName());
                dropdown_District_Code.setSelection(dropdown_District_Code.getText().length());
               disctrict_code = districMasterTableList.get(position).getCode();
              }
            else {
                dropdown_District_Code.setText("");
            }
        });

       /* List<TalukaMasterTable> talukaMasterTableList = gettalukaMasterList();
        if(talukaMasterTableList!=null &&talukaMasterTableList.size()>0) {
            TalukaAdapter talukaAdapter = new TalukaAdapter(getActivity(), R.layout.drop_down_textview, talukaMasterTableList);
            taluka_.setAdapter(talukaAdapter);
        }

        taluka_.setOnItemClickListener((parent, view, position, id) -> {
            talukaMasterTable=talukaMasterTableList.get(position);
            if(talukaMasterTable!=null) {

                taluka_.setText(talukaMasterTable.getDescription());
                taluka_.setSelection(taluka_.getText().length());
                taluka_code = talukaMasterTableList.get(position).getCode();
            }
            else {
                taluka_.setText("");
            }
        });*/

        Button update_btn = popupView.findViewById(R.id.update_btn);
        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        TextInputEditText ed_cust_name = popupView.findViewById(R.id.cust_name);
        TextInputEditText ed_doa = popupView.findViewById(R.id.tv_doa);
        AutoCompleteTextView ac_froward_by = popupView.findViewById(R.id.forward_by);
        TextInputEditText ed_contact_per = popupView.findViewById(R.id.contact_person);
        TextInputEditText ed_phone_no = popupView.findViewById(R.id.phone_no);
        TextInputEditText ed_mobile_no = popupView.findViewById(R.id.mobile_no);
        TextInputEditText ed_address = popupView.findViewById(R.id.address);
        TextInputEditText ed_pan_aadhar = popupView.findViewById(R.id.pan_number);
        TextInputEditText ed_sd = popupView.findViewById(R.id.securit_deposite);
        TextInputEditText ed_place_ = popupView.findViewById(R.id.place_);

        TextInputEditText dropdown_Bussiness_Type = popupView.findViewById(R.id.dropdown_Bussiness_Type);
        TextInputEditText taluka_ = popupView.findViewById(R.id.taluka_);

        TextInputEditText ed_post_code = popupView.findViewById(R.id.post_code);

        TextInputEditText ed_sd_chque_no = popupView.findViewById(R.id.sd_cheque_no);
        TextInputEditText ed_s_cheque = popupView.findViewById(R.id.security_chqs);
        TextInputEditText ed_seed_licence = popupView.findViewById(R.id.seed_licence);

        TextInputEditText ed_validity = popupView.findViewById(R.id.validity);
        TextInputEditText ed_file_no = popupView.findViewById(R.id.file_no);
        LinearLayout main_layout = popupView.findViewById(R.id.main_layout);

        TextInputEditText ed_aadhar_ = popupView.findViewById(R.id.aadhar_no);
        TextInputEditText ed_gst_number = popupView.findViewById(R.id.gstine);
        AutoCompleteTextView ac_gst_type = popupView.findViewById(R.id.gst_type);
        ArrayAdapter<String> gst_adapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_textview, gst_type);
        ac_gst_type.setAdapter(gst_adapter);

        ac_gst_type.setOnItemClickListener((parent, view, position, id) -> {
            if(gst_type.get(position).equalsIgnoreCase("Unregistered")){
                il_gs_no.setVisibility(View.GONE);
                ed_gst_number.setVisibility(View.GONE);
            }else {
                il_gs_no.setVisibility(View.VISIBLE);
                ed_gst_number.setVisibility(View.VISIBLE);
            }
        });

        TextInputEditText ed_total_land = popupView.findViewById(R.id.total_land);
        Button clear_data_btn = popupView.findViewById(R.id.clear_data);
        List<String> forwardby_list=Arrays.asList(CommonData.forward_by);
        ArrayAdapter<String> forward_by_adapter=new ArrayAdapter<String>(getActivity(), R.layout.drop_down_textview,forwardby_list);
        ac_froward_by.setAdapter(forward_by_adapter);


        clear_data_btn.setOnClickListener(v -> {
            clearAllField(ed_cust_name, ed_doa, ac_froward_by, ed_contact_per, ed_phone_no, ed_mobile_no, ed_address, ed_pan_aadhar, ed_sd, ed_place_,
                    ed_post_code, ed_sd_chque_no, ed_s_cheque, ed_seed_licence, ed_validity, ed_file_no, main_layout, ed_aadhar_, ed_gst_number , ac_gst_type,dropdown_Bussiness_Type);
        });

        ed_doa.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_doa);
            return true;
        });
        ed_validity.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_validity);
            return true;
        });

        //todo status comment...16-12-2021..
//        AutoCompleteTextView ac_status = popupView.findViewById(R.id.status);
        TextInputEditText ed_code = popupView.findViewById(R.id.code_);

        /*ItemAdapter itemAdapter=new ItemAdapter(getActivity(),R.layout.android_item_view,Arrays.asList(CommonData.status));
        ac_status.setAdapter(itemAdapter);*/

        TextInputEditText ed_remarks = popupView.findViewById(R.id.remarks);
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
        if (flag.equalsIgnoreCase("Update")){
            update_btn.setVisibility(View.VISIBLE);
            filter_apply_bt.setVisibility(View.GONE);
            clear_data_btn.setVisibility(View.GONE);
            tv_dist_code.setVisibility(View.VISIBLE);
            tv_dist_code.setText(" ( "+distribution_master_model.distributor_code+" )" );
            main_layout.requestFocus();

            try {
                ed_mobile_no.setText(distribution_master_model.mobile);
                ed_address.setText(distribution_master_model.address);
                ed_pan_aadhar.setText(distribution_master_model.pan_no);
                ed_aadhar_.setText(distribution_master_model.aadhaar_no);
                ed_sd.setText(String.valueOf(distribution_master_model.security_deposit));
                ed_sd_chque_no.setText(distribution_master_model.security_deposit_chq_no);
                ed_s_cheque.setText(distribution_master_model.security_deposit_chqs);
                ed_seed_licence.setText(distribution_master_model.seed_license);
                if(distribution_master_model.validity!=null && !distribution_master_model.validity.equalsIgnoreCase("")) {
                    ed_validity.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(distribution_master_model.validity));
                }
                ed_file_no.setText(distribution_master_model.file_no);
//                ac_status.setText(distribution_master_model.status);
                ed_code.setText(distribution_master_model.code);
                ed_remarks.setText(distribution_master_model.remarks);
                ed_phone_no.setText(distribution_master_model.phone_no);
                ed_contact_per.setText(distribution_master_model.contact);
                ac_froward_by.setText(distribution_master_model.forward_by);
                ed_cust_name.setText(distribution_master_model.distributor_name);
                ed_cust_name.setSelection(ed_cust_name.getText().length());
                if(distribution_master_model.date_of_joining!=null && !distribution_master_model.date_of_joining.equalsIgnoreCase("")) {
                    ed_doa.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(distribution_master_model.date_of_joining));
                }
                dropdown_Bussiness_Type.setText(distribution_master_model.bank);
                ed_post_code.setText(distribution_master_model.pincode);

                if(distribution_master_model.taluka != null && !distribution_master_model.taluka.equalsIgnoreCase("")){
                    taluka_.setText(distribution_master_model.taluka);
                    taluka_.setSelection(taluka_.getText().length());
                }
/*    if (distribution_master_model.taluka != null && !distribution_master_model.taluka.equalsIgnoreCase("")) {
                    taluka_.setText(distribution_master_model.taluka_name);
                    taluka_.setSelection(taluka_.getText().length());
                    taluka_code=distribution_master_model.taluka;
                }*/

                if(distribution_master_model.gst_type!=null && !distribution_master_model.gst_type.equalsIgnoreCase("")) {
                    if (distribution_master_model.gst_type.equalsIgnoreCase("Unregistered")) {
                        il_gs_no.setVisibility(View.GONE);
                        ed_gst_number.setVisibility(View.GONE);
                    } else {
                        il_gs_no.setVisibility(View.VISIBLE);
                        ed_gst_number.setVisibility(View.VISIBLE);
                    }
                    ac_gst_type.setText(distribution_master_model.gst_type);
                }

                ed_total_land.setText(distribution_master_model.total_land);
                ed_gst_number.setText(distribution_master_model.gst_number);
                ed_total_land.setText(distribution_master_model.total_land);
                ed_place_.setText(distribution_master_model.place);

                if (distribution_master_model.state_name != null && !distribution_master_model.state_name.equalsIgnoreCase("")) {
                    state_list.setText(distribution_master_model.state_name);
                    state_code=distribution_master_model.state;
                }

                if (distribution_master_model.district!= null && !distribution_master_model.district.equalsIgnoreCase("")) {
                    dropdown_District_Code.setText(distribution_master_model.district_name);
                    dropdown_District_Code.setSelection(dropdown_District_Code.getText().length());
                    disctrict_code=distribution_master_model.district;
                }

                if (distribution_master_model.zone != null && !distribution_master_model.zone.equalsIgnoreCase("")) {
                    dropdown_Zone.setText(distribution_master_model.zone_name);
                    zone_code=distribution_master_model.zone;
                }
                if(distribution_master_model.postcode!=null){
                    ed_post_code.setText(distribution_master_model.postcode);
                }

                update_btn.setOnClickListener(v -> {
                     if (ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_mobile_no.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_address.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter address !", Toast.LENGTH_SHORT).show();
                    return;

                } else if (ed_pan_aadhar.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter Pan no. !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter customer name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_seed_licence.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter seed licence !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_validity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter validity !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_file_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter file no !", Toast.LENGTH_SHORT).show();
                    return;
                }/* else if (ac_status.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter status !", Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter cust. name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_froward_by.getText().toString().equalsIgnoreCase("")) {
                         Toast.makeText(getActivity(), "Please enter forward by !", Toast.LENGTH_SHORT).show();
                         return;
                     }

                     else if(ed_doa.getText().toString().trim().equalsIgnoreCase("")){
                         Toast.makeText(getActivity(), "Please enter date of joining!", Toast.LENGTH_SHORT).show();
                         return;
                     }

                     else if(ed_validity.getText().toString().trim().equalsIgnoreCase("")){
                         Toast.makeText(getActivity(), "Please enter date of joining !", Toast.LENGTH_SHORT).show();
                         return;
                     }
                     else if(ed_aadhar_.getText().toString().length()>12){
                         Toast.makeText(getActivity(), "Aadhar card length must be 12 in characters.", Toast.LENGTH_SHORT).show();
                         return;
                     }
                     else if(ed_pan_aadhar.getText().toString().length()>10){
                         Toast.makeText(getActivity(), "Pan card length must be 10 in characters.", Toast.LENGTH_SHORT).show();
                         return;
                     }
                     else {
                         DistributorListModel distributorListModel = new DistributorListModel();
                         distributorListModel.distributor_code = distribution_master_model.distributor_code;
                         distributorListModel.distributor_name = ed_cust_name.getText().toString().trim();
                         distributorListModel.date_of_joining = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_doa.getText().toString().trim());
                         distributorListModel.forward_by = ac_froward_by.getText().toString().trim();
                         distributorListModel.contact = ed_contact_per.getText().toString().trim();
                         distributorListModel.phone_no = ed_phone_no.getText().toString().trim();
                         distributorListModel.mobile = ed_mobile_no.getText().toString().trim();
                         distributorListModel.address = ed_address.getText().toString().trim();
                         distributorListModel.place = ed_place_.getText().toString().trim();
                         distributorListModel.postcode = ed_post_code.getText().toString().trim();
                         distributorListModel.state = state_code!=null?state_code:"";
                         distributorListModel.state_name=state_list.getText().toString().trim();
                         distributorListModel.zone = zone_code!=null?zone_code:"";
                         distributorListModel.zone_name=dropdown_Zone.getText().toString().trim();
                         distributorListModel.district = disctrict_code!=null?disctrict_code:"";
                         distributorListModel.district_name=dropdown_District_Code.getText().toString().trim();
//                         distributorListModel.taluka = taluka_code!=null?taluka_code:"";
//                         distributorListModel.taluka_name=taluka_.getText().toString().trim();
                         distributorListModel.taluka=taluka_.getText().toString().trim();
                         distributorListModel.gst_number = ed_gst_number.getText().toString().trim();
                         distributorListModel.pan_no = ed_pan_aadhar.getText().toString().trim();
                         distributorListModel.aadhaar_no = ed_aadhar_.getText().toString().trim();
                         distributorListModel.security_deposit = StaticMethods.convertValueToInt(ed_sd.getText().toString().trim());
                         distributorListModel.security_deposit_chq_no = ed_sd_chque_no.getText().toString().trim();
                         distributorListModel.security_deposit_chqs = ed_s_cheque.getText().toString().trim();
                         distributorListModel.bank = dropdown_Bussiness_Type.getText().toString().trim();
                         distributorListModel.seed_license = ed_seed_licence.getText().toString().trim();
                         distributorListModel.validity = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_validity.getText().toString().trim());
                         distributorListModel.file_no = ed_file_no.getText().toString().trim();
//                         distributorListModel.status = ac_status.getText().toString();
                         distributorListModel.code = "123";
                         distributorListModel.remarks = ed_remarks.getText().toString().trim();
                         distributorListModel.created_by = sessionManagement.getUserEmail();
                         distributorListModel.created_on = DateTimeUtilsCustome.getCurrentDateBY_();
                         distributorListModel.isType = "Distributor";
                         distributorListModel.total_land = ed_total_land.getText().toString().trim();
                         distributorListModel.gst_type = ac_gst_type.getText().toString().trim();
                         boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                         if (isNetwork) {
                             String jsonString = new Gson().toJson(distributorListModel);
                             JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                             Log.e("json_first", asJsonObject.toString());
                             LoadingDialog progressDialog = new LoadingDialog();
                             progressDialog.showLoadingDialog(getActivity());
                             Call<List<InsertDistributorModel>> call = mAPIService.updateDistributorCustomerFarmer(asJsonObject);
                             call.enqueue(new Callback<List<InsertDistributorModel>>() {
                                 @Override
                                 public void onResponse(Call<List<InsertDistributorModel>> call, Response<List<InsertDistributorModel>> response) {
                                     try {
                                         if (response.isSuccessful()) {
                                             progressDialog.hideDialog();
                                             List<InsertDistributorModel> insertDistributorModelList = response.body();
                                             if (insertDistributorModelList!=null && insertDistributorModelList.size() > 0 && insertDistributorModelList.get(0).condition) {
                                                  getDistributorData(init_page);
                                                 Toast.makeText(getActivity(), insertDistributorModelList.get(0).message + insertDistributorModelList.get(0).distributor_code, Toast.LENGTH_SHORT).show();
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
                                         ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Update Distributor", getActivity());
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                     progressDialog.hideDialog();
                                     ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Update Distributor", getActivity());
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
        if (flag.equalsIgnoreCase("insert")){
            update_btn.setVisibility(View.GONE);
            filter_apply_bt.setVisibility(View.VISIBLE);
            clear_data_btn.setVisibility(View.VISIBLE);
            tv_dist_code.setVisibility(View.GONE);

            filter_apply_bt.setOnClickListener(view -> {
                if (ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_mobile_no.getText().toString().trim().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter correct mobile no.!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_address.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter address !", Toast.LENGTH_SHORT).show();
                    return;

                } else if (ed_pan_aadhar.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter Pan no. !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter customer name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_seed_licence.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter seed licence !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_validity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter validity !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_file_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter file no !", Toast.LENGTH_SHORT).show();
                    return;
                } /*else if (ac_status.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter status !", Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (ed_cust_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter cust. name !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_froward_by.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter forward by !", Toast.LENGTH_SHORT).show();
                    return;
                     }
                       else if(ed_doa.getText().toString().trim().equalsIgnoreCase("")){
                        Toast.makeText(getActivity(), "Please enter date of joining!", Toast.LENGTH_SHORT).show();
                        return;
                        }

                    else if(ed_validity.getText().toString().trim().equalsIgnoreCase("")){
                        Toast.makeText(getActivity(), "Please enter date of joining !", Toast.LENGTH_SHORT).show();
                        return;
                } else {
                    // call submit api...
                    DistributorListModel distributorListModel = new DistributorListModel();
                    distributorListModel.distributor_name = ed_cust_name.getText().toString().trim();
                    distributorListModel.date_of_joining =DateTimeUtilsCustome.splitDateInYYYMMDD(ed_doa.getText().toString().trim());
                    distributorListModel.forward_by = ac_froward_by.getText().toString().trim();
                    distributorListModel.contact = ed_contact_per.getText().toString().trim();
                    distributorListModel.phone_no = ed_phone_no.getText().toString().trim();
                    distributorListModel.mobile = ed_mobile_no.getText().toString().trim();
                    distributorListModel.address = ed_address.getText().toString().trim();
                    distributorListModel.place = ed_place_.getText().toString().trim();
                    distributorListModel.postcode =ed_post_code.getText().toString().trim();
                    distributorListModel.state = state_code;
                    distributorListModel.state_name=state_list.getText().toString().trim();
                    distributorListModel.zone =zone_code;
                    distributorListModel.zone_name=dropdown_Zone.getText().toString().trim();
                    distributorListModel.district = disctrict_code;
                    distributorListModel.district_name=dropdown_District_Code.getText().toString();
//                    distributorListModel.taluka = taluka_code;
                    distributorListModel.taluka = taluka_.getText().toString().trim();
//                    distributorListModel.taluka_name=taluka_.getText().toString().trim();
                    distributorListModel.gst_type = ac_gst_type.getText().toString().trim();
                    distributorListModel.pan_no = ed_pan_aadhar.getText().toString().trim();
                    distributorListModel.aadhaar_no = ed_aadhar_.getText().toString().trim();
                    distributorListModel.total_land = ed_total_land.getText().toString().trim();
                    distributorListModel.security_deposit = StaticMethods.convertValueToInt(ed_sd.getText().toString().trim());
                    distributorListModel.security_deposit_chq_no = ed_s_cheque.getText().toString().trim();
                    distributorListModel.security_deposit_chqs = ed_s_cheque.getText().toString().trim();
                    distributorListModel.gst_number = ed_gst_number.getText().toString().trim();
                    distributorListModel.bank = dropdown_Bussiness_Type.getText().toString().trim();
                    distributorListModel.seed_license = ed_seed_licence.getText().toString().trim();
                    distributorListModel.validity = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_validity.getText().toString().trim());
                    distributorListModel.file_no = ed_file_no.getText().toString().trim();
//                    distributorListModel.status = ac_status.getText().toString();
                    distributorListModel.code = "123";
                    distributorListModel.remarks = ed_remarks.getText().toString().trim();
                    distributorListModel.created_by = sessionManagement.getUserEmail();
                    distributorListModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                    distributorListModel.isType = "Distributor";
                    boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                    if (isNetwork) {
                        JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(distributorListModel)).getAsJsonObject();
                        Log.e("json_first", jsonObject.toString());
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        Call<List<InsertDistributorModel>> call = mAPIService.insertDistributorCustomerFarmer(jsonObject);
                        // List<DistributorListModel> distributorListModels = new ArrayList<>();
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
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Insert_Distributor", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<InsertDistributorModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert_Distributor", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please wait for internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void clearAllField(
            TextInputEditText ed_cust_name, TextInputEditText ed_doa, AutoCompleteTextView ed_forward,
            TextInputEditText ed_contact_per, TextInputEditText ed_phone_no, TextInputEditText ed_mobile_no,
            TextInputEditText ed_address, TextInputEditText ed_pan_aadhar, TextInputEditText ed_sd, TextInputEditText ed_place_,
            TextInputEditText ed_post_code, TextInputEditText ed_sd_chque, TextInputEditText ed_s_cheque,
            TextInputEditText ed_seed_licence, TextInputEditText ed_validity, TextInputEditText ed_file_no, LinearLayout main_layout,
            TextInputEditText ed_aadhar_, TextInputEditText ed_gst_number, AutoCompleteTextView ac_gst_type, TextInputEditText dropdown_Bussiness_Type) {

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
        dropdown_Bussiness_Type.setText("");
        main_layout.requestFocus(View.SCROLL_INDICATOR_TOP);
    }

    void getDistributorData(int page_no) {
        LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.showLoadingDialog(getActivity());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("created_by",sessionManagement.getUserEmail());
        jsonObject.addProperty("isType", "Distributor");
        jsonObject.addProperty("query_parameter", "");
        jsonObject.addProperty("RowsPerPage", "200");
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
                            if(distributionList.get(0).distributor_code!=null) {
                                distribution_table_list=distributionList;
                                bindData();
                            }
                            else {
                                listview.setAdapter(null);
                            }
                        } else {
                            no_record_found.setVisibility(View.VISIBLE);
                            swipe_refresh_layout.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), distributionList.size() > 0 && distributionList.get(0).condition ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        progressDialog.hideDialog();
//                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Distributor", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<DistributorListModel>> call, Throwable t) {
                progressDialog.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Distributor", getActivity());
            }
        });
    }

    DistributionMasterAdapter distributionMasterAdapter = null;

    void bindData() {
        if (distribution_table_list!=null && distribution_table_list.size() > 0) {
            distributionMasterAdapter = new DistributionMasterAdapter(getActivity(), this.distribution_table_list);
            listview.setAdapter(distributionMasterAdapter);
            distributionMasterAdapter.notifyDataSetChanged();
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
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            if (isNetwork) {
                LoadingDialog progressDialog = new LoadingDialog();
                progressDialog.showLoadingDialog(getActivity());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("distributor_code", distributor_code);
                jsonObject.addProperty("isType","Distributor");
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
                                    try {
                                        distribution_table_list.remove(position);
                                        distributionMasterAdapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), distributionList.get(0).message, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                   }
                                 else {
                                    Toast.makeText(getActivity(), distributionList.size() > 0 ? "No data found Distributor" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                progressDialog.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete distributor", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete distributor", getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(),"Please wait for online connection.",Toast.LENGTH_SHORT).show();
            }

        });

    }

    List<StateMasterTable> stateMasterList = new ArrayList<>();
    private List<StateMasterTable> getStateList() {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            // CollectionTable collectionTable=new CollectionTable();
            stateMasterList = stateMasterDao.getAllData();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }
        return stateMasterList;
    }

    private List<ZoneMasterTable> getZoneList() {
        List<ZoneMasterTable> zoneMasterTableList = new ArrayList<>();
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            // CollectionTable collectionTable=new CollectionTable();
            zoneMasterTableList = zoneMaterDao.getAllData();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }
        return zoneMasterTableList;
    }

    void bindDistrict(String state_code){
        List<DistricMasterTable> districMasterTableList = getdistricMasterTableList(state_code);
        if(getdistricMasterTableList(state_code)!=null && getdistricMasterTableList(state_code).size()>0 && districMasterTableList != null
        && districMasterTableList.size()>0) {
            DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districMasterTableList);
            dropdown_District_Code.setAdapter(districtAdapter);
        }
       /* else {
            Toast.makeText(getActivity(), "No Data Found On "+state_code, Toast.LENGTH_SHORT).show();
        }*/
    }
    List<DistricMasterTable> districMasterTableList = new ArrayList<>();

    private List<DistricMasterTable> getdistricMasterTableList(String state_code) {

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            DistricMasterDao zoneMaterDao = pristineDatabase.districMasterDao();
            // CollectionTable collectionTable=new CollectionTable();
//            districMasterTableList = zoneMaterDao.getAllData();

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


    private List<TalukaMasterTable> gettalukaMasterList() {
        List<TalukaMasterTable> talukaMasterList = new ArrayList<>();
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
            talukaMasterList = talukaMasterDao.getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }
        return talukaMasterList;
    }



    private void getBankMasterData(ProgressBar loading_bank_master,AutoCompleteTextView ac_bank_master){
        loading_bank_master.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<BankMaserModel>call = mAPIService.getBankMasterData();
        call.enqueue(new Callback<BankMaserModel>() {
            @Override
            public void onResponse(Call<BankMaserModel> call, Response<BankMaserModel> response) {
                try {
                    if (response.isSuccessful()) {
                        BankMaserModel bankMaserModel = response.body();
                        if (bankMaserModel != null) {
                            loading_bank_master.setVisibility(View.GONE);
                            Log.e("sucess", "success");
                            List<BankMaserModel.Data> bankMasterList = bankMaserModel.data;
                            if (bankMasterList != null && bankMasterList.size() > 0) {
                                try {
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

  /*  private int deleteLineFromLocal(int pos,PristineDatabase pristineDatabase) {
        DistributorDao distributorDao=pristineDatabase.distributorDao();
        return distributorDao.deleteFromLocal(distribution_table_list.get(pos).getDistributor_code());
    }*/
}


