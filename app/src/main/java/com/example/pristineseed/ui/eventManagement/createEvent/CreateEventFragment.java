package com.example.pristineseed.ui.eventManagement.createEvent;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagemantTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterTable;
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
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;

import com.example.pristineseed.global.StaticMethods;

import com.example.pristineseed.model.GeoSetupModel.CountModel;
import com.example.pristineseed.model.event_managment_model.EventManagemantModel;
import com.example.pristineseed.model.event_managment_model.EventTypeMasterModel;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.travel.travel_view_header.EventCreateResponseModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;

import com.example.pristineseed.ui.adapter.DistrictAdapter;
import com.example.pristineseed.ui.adapter.StateMasterAdapter;
import com.example.pristineseed.ui.adapter.TalukaAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.event_adapterr.EventTypeAdapter;
import com.example.pristineseed.ui.adapter.item.CropHytechAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAdapter;

import com.example.pristineseed.ui.eventManagement.addExpanse.AddEventExpenceFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment  extends Fragment {
 private AutoCompleteTextView dropdown_event_type, dropdown_crop, dropdown_variety, dropdown_state, dropdown_district,
         dropdown_taluka,ac_zone;
 private ChipGroup selected_no_odvillageCovered_chipgroup;
 private ImageView add_village_name_button;
 private AppCompatEditText et_event_desc, et_village, et_farmer_name, et_farmer_contact_no,
         et_expected_farmers,
         et_expected_dealers,
            et_expected_distributers, et_village_covered_name;
  private Button submitPage, add_eventExpense;
  private SessionManagement sessionManagement;
  private boolean passBackFragmentData = false;

  private EventTypeMasterTable selectedEventType;
  private CropMassterModel.Data selectedCrop=null;
  private Hybrid_Item_Table selectedCropItem=null;
  private StateMasterTable selectedState=null;
  private DistricMasterTable selecteddistrict=null;
  private TalukaMasterTable selectedTaluka=null;
  private ZoneMasterTable zoneMasterTable=null;

  private LinearLayout parent_request_focus;

  private MaterialProgressBar content_loading;

  private TextInputEditText et_event_date, et_Actual_farmers, et_Actual_dealers, et_Actual_distributers;
  private LinearLayout create_approval_section,create_event_title_layout;
  private TextView title_text;
  private String state_code, district_code;

  public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
        if (networkUtil) {
            bindDataEventTypeMaster();
            bindControllEvent();
        }
        checkBackFragmentData();
        bindZone();
        bindTaluka(district_code);
        bindDistrict(state_code);
        bindState();
        bindCrop();
    }

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        dropdown_event_type = view.findViewById(R.id.dropdown_event_type);
        dropdown_crop = view.findViewById(R.id.dropdown_crop);
        dropdown_variety = view.findViewById(R.id.dropdown_variety);
        dropdown_state = view.findViewById(R.id.dropdown_state);
        dropdown_district = view.findViewById(R.id.dropdown_district);
        dropdown_taluka = view.findViewById(R.id.dropdown_taluka);
        et_village_covered_name = view.findViewById(R.id.et_village_covered_name);
        selected_no_odvillageCovered_chipgroup = view.findViewById(R.id.selected_no_odvillageCovered_chipgroup);
        add_village_name_button = view.findViewById(R.id.add_village_name_button);
        ac_zone=view.findViewById(R.id.drop_down_zone);

        et_event_desc = view.findViewById(R.id.et_event_desc);
        et_event_date = view.findViewById(R.id.et_event_date);

       // et_village = view.findViewById(R.id.et_village);
        et_farmer_name = view.findViewById(R.id.et_farmer_name);

        et_farmer_contact_no = view.findViewById(R.id.et_farmer_contact_no);
        et_expected_farmers = view.findViewById(R.id.et_expected_farmers);
        et_expected_dealers = view.findViewById(R.id.et_expected_dealers);
        et_expected_distributers = view.findViewById(R.id.et_expected_distributers);
        parent_request_focus=view.findViewById(R.id.parent_layout);

        create_approval_section = view.findViewById(R.id.create_approval_section);
        et_Actual_farmers=view.findViewById(R.id.et_Actual_farmers);
        et_Actual_dealers=view.findViewById(R.id.et_Actual_dealers);
        et_Actual_distributers=view.findViewById(R.id.et_Actual_distributers);
        create_event_title_layout=view.findViewById(R.id.create_event_title_layout);

        submitPage = view.findViewById(R.id.submitPage);
        add_eventExpense = view.findViewById(R.id.add_eventExpense);
        title_text=view.findViewById(R.id.title_text);
        content_loading=view.findViewById(R.id.content_loading);
    }
    private boolean approve_view_display = false;
    private SyncEventDetailModel submiteEventData = null;
    void checkBackFragmentData() {
        try {
            passBackFragmentData = getArguments().getBoolean("flag", false);
            submiteEventData = new Gson().fromJson(getArguments().getString("passdata", ""), SyncEventDetailModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (passBackFragmentData) {
                title_text.setVisibility(View.VISIBLE);
                et_event_desc.setEnabled(false);
                et_event_date.setEnabled(false);
                dropdown_event_type.setEnabled(false);
                dropdown_crop.setEnabled(false);
                dropdown_variety.setEnabled(false);
                dropdown_state.setEnabled(false);
                dropdown_district.setEnabled(false);
                dropdown_taluka.setEnabled(false);
                et_village.setEnabled(false);
                et_farmer_name.setEnabled(false);
                et_farmer_contact_no.setEnabled(false);
                et_expected_farmers.setEnabled(false);
                et_expected_dealers.setEnabled(false);
                et_expected_distributers.setEnabled(false);
                et_village_covered_name.setEnabled(false);
                add_village_name_button.setVisibility(View.GONE);

                et_event_desc.setText(submiteEventData.event_desc);
                if(submiteEventData.event_date!=null) {
                    et_event_date.setText(DateTimeUtilsCustome.getDateOnlyChange(submiteEventData.event_date));
                }
                dropdown_event_type.setText(submiteEventData.event_type);
                dropdown_crop.setText(submiteEventData.crop);
                dropdown_variety.setText(submiteEventData.crop_hybrid);
                dropdown_state.setText(submiteEventData.state);
                dropdown_district.setText(submiteEventData.district);
                dropdown_taluka.setText(submiteEventData.taluka);
                ac_zone.setText(submiteEventData.zone);
                et_village.setText(submiteEventData.village);
                et_farmer_name.setText(submiteEventData.farmer_name);
                et_farmer_contact_no.setText(submiteEventData.farmer_mobile_no);
                et_expected_farmers.setText(submiteEventData.expected_farmers);
                et_expected_dealers.setText(submiteEventData.expected_dealers);
                et_expected_distributers.setText(submiteEventData.expected_distributer);
                String[] arrayVillage = submiteEventData.event_cover_villages.split(",");
                if (VillagesCoveredNameList != null && !VillagesCoveredNameList.isEmpty())
                    VillagesCoveredNameList.clear();
                for (int i = 0; i < arrayVillage.length; i++) {
                    VillagesCoveredNameList.add(arrayVillage[i]);
                }
                bindVillageCoverdNameListNo();
                add_eventExpense.setVisibility(View.VISIBLE);
                submitPage.setVisibility(View.GONE);
                add_eventExpense.setOnClickListener(view -> {
                    if (submiteEventData.status.equalsIgnoreCase("CREATE REJECTED")) {
                        Snackbar.make(add_eventExpense, "You don't have Expense line So Go Back.", Snackbar.LENGTH_LONG).show();
                    }
                   else if (submiteEventData.status.equalsIgnoreCase("CREATE APPROVED")) {
                            if (et_Actual_farmers.getText().toString().equalsIgnoreCase("")) {
                                Snackbar.make(add_eventExpense, "Please Enter Actual Farmers.", Snackbar.LENGTH_LONG).show();
                                return;
                            } else if (et_Actual_distributers.getText().toString().equalsIgnoreCase("")) {
                                Snackbar.make(add_eventExpense, "Please Enter Actual Distributers.", Snackbar.LENGTH_LONG).show();
                                return;
                            } else if (et_Actual_dealers.getText().toString().equalsIgnoreCase("")) {
                                Snackbar.make(add_eventExpense, "Please Enter Actual Dealers.", Snackbar.LENGTH_LONG).show();
                                return;
                            }
                            try {
                                submiteEventData.actual_dealers=et_Actual_dealers.getText().toString();
                                submiteEventData.actual_distributers=et_Actual_distributers.getText().toString();
                                submiteEventData.actual_farmers=et_Actual_farmers.getText().toString();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("dataPass", new Gson().toJson(submiteEventData));
                        AddEventExpenceFragment addEventExpenceFragment=new AddEventExpenceFragment();
                        addEventExpenceFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(),addEventExpenceFragment,"Add_event_expence");

                });

                if (submiteEventData.status.equalsIgnoreCase("PENDING")) {
                    add_eventExpense.setVisibility(View.GONE);
                    create_approval_section.setVisibility(View.GONE);
                } else if (submiteEventData.status.equalsIgnoreCase("CREATE REJECTED")) {
                    create_approval_section.setVisibility(View.GONE);
                } else if (submiteEventData.status.equalsIgnoreCase("CREATE APPROVED")) {
                    create_approval_section.setVisibility(View.VISIBLE);
                    et_Actual_dealers.setText(submiteEventData.actual_dealers);
                    et_Actual_distributers.setText(submiteEventData.actual_distributers);
                    et_Actual_farmers.setText(submiteEventData.actual_farmers);
                } else {
                    create_approval_section.setVisibility(View.VISIBLE);
                    et_Actual_dealers.setEnabled(false);
                    et_Actual_distributers.setEnabled(false);
                    et_Actual_farmers.setEnabled(false);
                    et_Actual_dealers.setText(submiteEventData.actual_dealers);
                    et_Actual_distributers.setText(submiteEventData.actual_distributers);
                    et_Actual_farmers.setText(submiteEventData.actual_farmers);
                    //todo disable all input field of this section;
                }
                if (approve_view_display) {
                    create_event_title_layout.setVisibility(View.VISIBLE);
                    title_text.setText("View Event " + submiteEventData.event_code);
                    if (submiteEventData.status.equalsIgnoreCase("PENDING")) {
                        add_eventExpense.setVisibility(View.GONE);
                        create_event_title_layout.setVisibility(View.GONE);
                    }
                    else if (submiteEventData.status.equalsIgnoreCase("CREATE APPROVED")) {
                        add_eventExpense.setVisibility(View.GONE);
                        create_event_title_layout.setVisibility(View.GONE);
                    }else{
                        add_eventExpense.setVisibility(View.VISIBLE);
                        create_event_title_layout.setVisibility(View.GONE);
                    }
                    submitPage.setVisibility(View.GONE);
                } else
                    create_event_title_layout.setVisibility(View.VISIBLE);
                    title_text.setText("Update Event " +"( "+ submiteEventData.event_code+" )");

                if (submiteEventData.status.equalsIgnoreCase("PENDING")) {
                    add_eventExpense.setVisibility(View.GONE);
                }
            } else {
                    et_event_date.setOnClickListener(view1 -> {
                        try {
                            new CustomDatePicker(getActivity()).showDatePickerDialog(et_event_date);
                        } catch (Exception e) {
                            Log.e("exc", e.getMessage());
                        }
                    });
                add_village_name_button.setVisibility(View.VISIBLE);
                bindEventType();
                bindCrop();
                bindState();
                bindControllEvent();
            }
        }
    }

   private String event_cover_villages = "";


    void bindControllEvent() {
        bindDistrict(state_code);
        bindTaluka(district_code);
        dropdown_event_type.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectedEventType = eventTypeList.get(i);
            dropdown_event_type.setError(null);
        });
        dropdown_crop.setOnItemClickListener((adapterView, view1, i, l) -> {
            cropItemList.clear();
            selectedCropItem = null;
            bindCropItem(cropList.get(i).Code);
                selectedCrop = cropList.get(i);
                dropdown_crop.setError(null);
        });
        dropdown_variety.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectedCropItem = cropItemList.get(i);
            dropdown_variety.setError(null);
        });
        /*dropdown_state.setOnItemClickListener((adapterView, view1, i, l) -> {
                selectedState = statelist.get(i);
                dropdown_state.setError(null);

        });*/
        dropdown_state.setOnItemClickListener((adapterView, view1, i, l) -> {
            districtList.clear();
            bindDistrict(statelist.get(i).getCode());
            selectedState = statelist.get(i);
            if(selectedState != null){
                state_code = selectedState.getCode();
                dropdown_state.setText(selectedState.getName());
                dropdown_state.setSelection(dropdown_state.getText().length());
                dropdown_state.setError(null);
                dropdown_district.setText("");
            }
        });
        dropdown_district.setOnItemClickListener((adapterView, view1, i, l) -> {
                talukaList.clear();
                bindTaluka(districtList.get(i).getCode());
                selecteddistrict = districtList.get(i);
                if(selecteddistrict != null){
                    district_code = selecteddistrict.getCode();
                    dropdown_district.setText(selecteddistrict.getName());
                    dropdown_district.setSelection(dropdown_district.getText().length());
                    dropdown_district.setError(null);
                    dropdown_taluka.setText("");
                }
        });
        dropdown_taluka.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectedTaluka = talukaList.get(i);
        });

        ac_zone.setOnItemClickListener((parent, view, position, id) -> {
            zoneMasterTable = zoneMasterTableList.get(position);
        });

        add_village_name_button.setOnClickListener(view1 -> {
            if (!et_village_covered_name.getText().toString().equalsIgnoreCase("")){
                VillagesCoveredNameList.add(et_village_covered_name.getText().toString());
                bindVillageCoverdNameListNo();
            et_village_covered_name.setText("");
            et_village_covered_name.setError(null);
            et_village_covered_name.clearFocus();
        }else {
                et_village_covered_name.setError("Please enter village name");
                return;
            }
        });
        submitPage.setOnClickListener(view -> {
            try {
                if (et_event_desc.getText().toString().equalsIgnoreCase("")) {
                    et_event_desc.setError("Please Enter Event Discription.");
                    return;
                } else if (et_event_date.getText().toString().equalsIgnoreCase("")) {
                    et_event_date.setError("Please Enter Event Date.");
                    return;
                } else if (selectedEventType == null || !selectedEventType.getEvent_type().equalsIgnoreCase(dropdown_event_type.getText().toString())) {
                    dropdown_event_type.setError("Please Select Event Type.");
                    return;
                } else if (selectedCrop == null || selectedCrop.Code==null || selectedCrop.Code.equalsIgnoreCase("")) {
                    //!selectedCrop.getDescription().equalsIgnoreCase(dropdown_crop.getText().toString())
                    dropdown_crop.setError("Please Select Crop.");
                    return;
                } else if (selectedCropItem == null || selectedCropItem.getNo()==null) {
                    //!selectedCropItem.getDescription().equalsIgnoreCase(dropdown_variety.getText().toString())
                    dropdown_variety.setError("Please Select Variety.");
                    return;
                } else if (selectedState == null || selectedState.getName().equalsIgnoreCase("") || selectedState.getName()==null) {
                    //!selectedState.getName().equalsIgnoreCase(dropdown_state.getText().toString();
                    dropdown_state.setError("Please Select State.");
                    return;
                } else if (selecteddistrict == null ||selecteddistrict.getName().equalsIgnoreCase("")||selecteddistrict.getName()==null) {
                    // !selecteddistrict.getName().equalsIgnoreCase(dropdown_district.getText().toString())
                    dropdown_district.setError("Please Select District.");
                    return;
                } else if (selectedTaluka == null ||selectedTaluka.getDescription().equalsIgnoreCase("")
                ||selectedTaluka.getCode()==null) {
                    dropdown_taluka.setError("Please Select Taluka.");
                    return;

                }else if(zoneMasterTable==null && zoneMasterTable.getCode()==null || zoneMasterTable.getCode().equals("")){
                    ac_zone.setError("Please select zone.");
                    return;
                } else if (et_village.getText().toString().equalsIgnoreCase("")) {
                    et_village.setError("Please Enter Village Name.");
                    return;
                } else if (et_farmer_name.getText().toString().equalsIgnoreCase("")) {
                    et_farmer_name.setError("Please Enter Farmer Name.");
                    return;
                } else if (et_farmer_contact_no.getText().toString().equalsIgnoreCase("")) {
                    et_farmer_contact_no.setError("Please Enter Contact no.");
                    return;
                } else if (et_expected_farmers.getText().toString().equalsIgnoreCase("")) {
                    et_expected_farmers.setError("Please Enter Expected Farmers.");
                    return;
                } else if (et_expected_dealers.getText().toString().equalsIgnoreCase("")) {
                    et_expected_dealers.setError("Please Enter Expected Dealers.");
                    return;
                } else if (et_expected_distributers.getText().toString().equalsIgnoreCase("")) {
                    et_expected_distributers.setError("Please Enter Expected Distributers.");
                    return;
                } else if (VillagesCoveredNameList == null || VillagesCoveredNameList.size() == 0) {
                    et_village_covered_name.setError("Please Enter Village Covered Name.");
                    return;
                }
                for (int i = 0; i < VillagesCoveredNameList.size(); i++) {
                    event_cover_villages += VillagesCoveredNameList.get(i) + (i == VillagesCoveredNameList.size() - 1 ? "" : ",");
                }
                boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
                if (networkUtil) {
                    JsonObject postedJson = new JsonObject();
                    postedJson.addProperty("email", sessionManagement.getUserEmail());
                    postedJson.addProperty("event_desc", et_event_desc.getText().toString());
                    postedJson.addProperty("event_date", DateTimeUtilsCustome.splitDateInYYYMMDD(et_event_date.getText().toString()));
                    postedJson.addProperty("event_type", selectedEventType.getEvent_type());
                    postedJson.addProperty("event_budget", selectedEventType.getRate());
                    postedJson.addProperty("crop", selectedCrop.Code);
                    postedJson.addProperty("crop_hybrid", selectedCropItem.getNo());
                    postedJson.addProperty("zone",zoneMasterTable.getDescription());
                    postedJson.addProperty("state", selectedState.getCode()); //it is only keep state code integer value not string value;
                    postedJson.addProperty("district", selecteddistrict.getName());
                    postedJson.addProperty("village", et_village.getText().toString());
                    postedJson.addProperty("taluka", selectedTaluka.getDescription());
                    postedJson.addProperty("farmer_name", et_farmer_name.getText().toString());
                    postedJson.addProperty("farmer_mobile_no", et_farmer_contact_no.getText().toString());
                    postedJson.addProperty("expected_farmers", et_expected_farmers.getText().toString());
                    postedJson.addProperty("expected_dealers", et_expected_dealers.getText().toString());
                    postedJson.addProperty("expected_distributer", et_expected_distributers.getText().toString());
                    postedJson.addProperty("event_cover_villages", event_cover_villages);
                    postedJson.addProperty("approver_email", sessionManagement.getApprover_id());
                    createEventSubmitApiCall(postedJson);
                } else {
                    Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void createEventSubmitApiCall(JsonObject jsonObject) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        if(getActivity()!=null) {
            progressDialogLoading.showLoadingDialog(getActivity());
            Call<List<EventCreateResponseModel>> call = mAPIService.createEventSubmit(jsonObject);
            call.enqueue(new Callback<List<EventCreateResponseModel>>() {
                @Override
                public void onResponse(Call<List<EventCreateResponseModel>> call, Response<List<EventCreateResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<EventCreateResponseModel> tempdata = response.body();
                            if (tempdata!=null && tempdata.size() > 0 && tempdata.get(0).condition) {
                                successMessage(tempdata.get(0).event_code);
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), tempdata.size() > 0 ? "Result Not found !" : "Api not responding.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "create_event_submit", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<EventCreateResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "create_event_submit", getActivity());
                }
            });
        }
    }

    private List<String> VillagesCoveredNameList = new ArrayList<>();

    private void bindVillageCoverdNameListNo() {
        selected_no_odvillageCovered_chipgroup.removeAllViews();
        for (int index = 0; index < VillagesCoveredNameList.size(); index++) {
            final String tagName = VillagesCoveredNameList.get(index);
            final Chip chip = new Chip(getActivity());
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.product_sans_regular);
            chip.setTypeface(typeface);
            chip.setChipIconResource(R.drawable.farmer);
            chip.setCloseIconResource(R.drawable.circle_close);
            if (passBackFragmentData)
                chip.setCloseIconEnabled(false);
            else
                chip.setCloseIconEnabled(true);
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(view -> {
                VillagesCoveredNameList.remove(tagName);
                selected_no_odvillageCovered_chipgroup.removeView(chip);
            });
            selected_no_odvillageCovered_chipgroup.addView(chip);
        }
    }

   private List<CropMassterModel.Data> cropList = new ArrayList<>();

    void bindCrop() {
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
                                    cropList=crop_master_list;
                                    CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.drop_down_textview, cropList);
                                    dropdown_crop.setAdapter(cropAdapter);
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

/*

        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            CropHytechMasterDao cropMaster_dao = pristineDatabase.cropHytechMasterDao();
            cropList = cropMaster_dao.getAllData();
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if(cropList!=null && cropList.size()>0) {
                CropHytechAdapter cropAdapter = new CropHytechAdapter(getActivity(), R.layout.drop_down_textview, cropList);
                dropdown_crop.setAdapter(cropAdapter);
            }
        }
*/

    }

   private List<Hybrid_Item_Table> cropItemList = new ArrayList<>();

    void bindCropItem(String cropcode) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            HybridItemMasterDao crop_item_master_dao = pristineDatabase.hybridItemMasterDao();
            cropItemList =crop_item_master_dao.getHybridItemForEvent(cropcode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if(cropItemList!=null && cropItemList.size()>0) {
                HybridItemAdapter cropAdapter = new HybridItemAdapter(getActivity(), R.layout.drop_down_textview, cropItemList);
                dropdown_variety.setAdapter(cropAdapter);
            }
        }
    }

    private List<StateMasterTable> statelist = new ArrayList<>();

    void bindState() {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            statelist = stateMasterDao.getAllData();
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if(statelist!=null && statelist.size()>0) {
                StateMasterAdapter stateAdapter = new StateMasterAdapter(getActivity(), R.layout.drop_down_textview, statelist);
                dropdown_state.setAdapter(stateAdapter);
            }
        }
    }

   private List<DistricMasterTable> districtList = new ArrayList<>();
    private List<ZoneMasterTable> zoneMasterTableList=new ArrayList<>();

    void bindDistrict(String state_code) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
//            districtList = districMasterDao.getAllData();
            districtList = districMasterDao.fetch_byGeograficalStateCode(state_code);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if(districtList!=null && districtList.size()>0) {
                DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(), R.layout.drop_down_textview, districtList);
                dropdown_district.setAdapter(districtAdapter);
            }
            /*else {
                Toast.makeText(getActivity(), "No Records Found On "+state_code , Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private void bindZone(){
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            zoneMasterTableList = zoneMaterDao.getAllData();
        } catch (Exception e) {
       e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if(zoneMasterTableList!=null && zoneMasterTableList.size()>0) {
                ZoneMasterAdapter zoneMasterAdapter = new ZoneMasterAdapter(getActivity(), R.layout.drop_down_textview, zoneMasterTableList);
                ac_zone.setAdapter(zoneMasterAdapter);
            }
        }
    }

    private List<TalukaMasterTable> talukaList = new ArrayList<>();

    void bindTaluka(String district_code) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
//            talukaList = talukaMasterDao.getAllData();
            talukaList = talukaMasterDao.fetchBydistrictNo(district_code);
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if(talukaList!=null && talukaList.size()>0) {
                TalukaAdapter districtAdapter = new TalukaAdapter(getActivity(), R.layout.drop_down_textview, talukaList);
                dropdown_taluka.setAdapter(districtAdapter);
            }
            else {
                Toast.makeText(getActivity(), "No Records Found On "+district_code, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void bindDataEventTypeMaster() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<EventTypeMasterModel>> call = mAPIService.getEventTypeData("GetEventType");
        call.enqueue(new Callback<List<EventTypeMasterModel>>() {
            @Override
            public void onResponse(Call<List<EventTypeMasterModel>> call, Response<List<EventTypeMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<EventTypeMasterModel> tempdata = response.body();
                        if (tempdata.size() > 0 && tempdata.get(0).condition) {
                            saveEventTypeDataLocal(tempdata);
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), tempdata.size() > 0 ? "Result Not found !" : "Api not responding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Event_type_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<EventTypeMasterModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Event_type_master", getActivity());
            }
        });
    }

    private List<EventTypeMasterTable> eventTypeList = new ArrayList<>();

    private void saveEventTypeDataLocal(List<EventTypeMasterModel> tempdata) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            EventTypeMasterDao eventTypeMasterDao = pristineDatabase.eventTypeMasterDao();
            for (int i = 0; i < tempdata.size(); i++) {
                EventTypeMasterTable eventTypeMasterTable = EventTypeMasterTable.getEventTypeData(tempdata.get(i));

                if (eventTypeMasterDao.isDataExist(eventTypeMasterTable.getId())) {
                    eventTypeMasterDao.update(eventTypeMasterTable);
                } else {
                    eventTypeMasterDao.insert(eventTypeMasterTable);
                }
            }
        } catch (Exception e) {
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            bindEventType();
        }
    }

    private void bindEventType() {
        try {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            EventTypeMasterDao eventTypeMasterDao = pristineDatabase.eventTypeMasterDao();
            eventTypeList = eventTypeMasterDao.fetchParent();
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EventTypeAdapter fruitAdapter = new EventTypeAdapter(getActivity(), R.layout.drop_down_textview, eventTypeList);
            dropdown_event_type.setAdapter(fruitAdapter);
        }
    }

    private void successMessage(String event_code) {

        try {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View successmessaePopupView = inflater.inflate(R.layout.success_message_popup, null);
            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
            TextView order_no = (TextView) successmessaePopupView.findViewById(R.id.order_no);
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
                    clearAllInputField();
                    dialog.dismiss();
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
        }
    }

    void clearAllInputField(){
      //  parent_request_focus.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
      //  parent_request_focus.setFocusableInTouchMode(true);
        dropdown_event_type.setText("");
        dropdown_crop.setText("");
        dropdown_variety.setText("");
        dropdown_state.setText("");
        dropdown_district.setText("");
        dropdown_taluka.setText("");
        et_village_covered_name.setText("");
        ac_zone.setText("");
        selected_no_odvillageCovered_chipgroup.removeAllViews();

        et_event_desc.setText("");
        et_event_date.setText("");
        et_event_date.setText("");

        et_village.setText("");
        et_farmer_name.setText("");
        et_farmer_contact_no.setText("");
        et_expected_farmers.setText("");
        et_expected_dealers.setText("");
        et_expected_distributers.setText("");

        et_village.clearFocus();
        et_farmer_name.clearFocus();
        et_farmer_contact_no.clearFocus();
        et_expected_dealers.clearFocus();
        et_expected_distributers.clearFocus();
        et_event_date.clearFocus();
        et_event_date.clearFocus();
        et_event_desc.clearFocus();
        dropdown_variety.clearFocus();
    }

}
