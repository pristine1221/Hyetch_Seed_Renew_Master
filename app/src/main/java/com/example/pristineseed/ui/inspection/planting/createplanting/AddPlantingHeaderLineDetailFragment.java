package com.example.pristineseed.ui.inspection.planting.createplanting;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.BackgroundTask;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UOMDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UomTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingHeaderDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_fsio_bsio_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Dao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Table;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedFarmerMasterDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Seed_Farmer_master_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.GeoSetupModel.CountModel;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderResponseModel;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.model.item.UnitOfMeasureModel;
import com.example.pristineseed.model.reportModel.LotsDueInspectionModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingFsioBsioAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingLineAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingLotMFOAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.ZoneMasterAdapter;
import com.example.pristineseed.ui.adapter.farmer_adapter.FarmerVillageAdapter;
import com.example.pristineseed.ui.adapter.item.FarmerListAdapter;
import com.example.pristineseed.ui.adapter.item.HybridItemAliasNameAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.example.pristineseed.ui.adapter.item.OrganizerAdapter;
import com.example.pristineseed.ui.adapter.item.UnitOfMeasureAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlantingHeaderLineDetailFragment extends Fragment implements OrganizerAdapter.OnItemClickListner {
    private SessionManagement sessionManagement;
    private AutoCompleteTextView dropdown_sesion_code, drop_down_parent, ac_select_loc, ac_type, ac_stage_code, ac_doc_sub_type;
    private TextInputEditText edit_date, ed_total_sowing_area, ed_date_of_harvest, ed_total_land_acres;
    private Button create_header_btn;
    private TextView tv_palnting_no, tv_date, tv_season_code, tv_stage_code, tv_parent_type, tv_header_no, tv_doc_subtype,
            tv_land_acr, tv_sowing_acr_type, tv_org_name, tv_org_code;
    private LinearLayout header_create_section;
    private LinearLayout planting_detail_header_section;
    private ListView listview;
    private Chip chip_add_line_planting, delete_header_chip, complete_header;
    private LinearLayout back_button_go_topreviousPage;
    private LinearLayout line_cardview;
    private int line_no = 0;
    private String planting_code = "";
    private String view_flag;
    private PlantingLineAdapter plantingLineAdapter = null;
    private List<PlantingHeaderModel.PlantingLine> plantingLineTableList = new ArrayList<>();

    private PlantingLotModel.Data plantingMasterTable = null;
    private PlantingLotModel.Data plantingMaleTable = null;
    private PlantingLotModel.Data plantingFemaleTable = null;

    private List<PlantingLotModel.Data> plantingMaleList = new ArrayList<>();
    private List<PlantingLotModel.Data> plantingLotFemaleList = new ArrayList<>();
    private List<PlantingLotModel.Data> plantingLotOtherList = new ArrayList<>();
    //  private List<Hybrid_Item_Table> hybrid_item_tableList = null;
    private List<CropHytechMasterTable> cropMasterTableList1 = null;
    private List<Hybrid_Item_Table> varietyList = null;
    private List<SeasonMasterModel> seasonMasterTableList = null;
    private List<UserLocationModel> locationMasterTableList = null;
    private List<Planting_Lot_master_Table> planting_lot_master_List = null;
    private OrganizerModel.Data organizer_master_table = null;

    private PlantingHeaderModel plantingHeaderModel = null;

    private TextInputEditText ed_supervsr_name, ed_grower_code, ed_recept_num, ed_address_,
            ed_parent_male, ed_receiptno_female, ed_receptno_male, ed_parent_female, ed_sowing_female, ed_sowing_male,
            ed_expected_yield, ed_sowing_date_other, revised_yield, ed_sowing_area, ed_land_lease, ed_item_clsof_seed,
            item_crop_type, ed_item_name, item_prod_grp_cd, ed_male_qty, ed_female_qty, ed_other_receipt_no,
            ed_parent_other, ed_other_qty, dropdown_city, ac_state, ac_taluka, ac_crop_code, ac_organizer_name;

    AutoCompleteTextView ac_doc_no, ac_parent_female_lot, ac_parent_male_lot, ac_parent_other_lot, ac_alias_name, ac_variety_code, ac_grwer_name, unit_of_msrd_cd, ed_vill_name, ac_zone_;

    TextInputLayout recpt_male, recpt_female, recpt_other;
    private RadioGroup radio_group_land_lease;
    private AppCompatRadioButton radio_1, radio_2;
    private String postedString;
    private MaterialProgressBar loading_content;
    private static int line_series;
    private UserLocationModel locationMasterTable = null;
    private List<UnitOfMeasureModel.UnitOfMeasureModelList> uomTableList = null;
    private DispatchFarmerModel.Data farmer_grower_model = null;
    private List<DispatchFarmerModel.Data> farmer_grower_list = null;

    private SeasonMasterModel seasonMasterTable = null;
    private List<ZoneMasterTable> zoneMasterTableList = null;
    private String zone_code = "";
    private MaterialCardView frame_layout_org_list;
    private ProgressBar search_loading_item;
    private RecyclerView lv_cust_dist_list;
    private TextInputLayout organser_input_layout, unit_of_msrd_cd_layout;


    private List<DispatchFarmerModel.Data> village_list = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (getArguments() != null) {
            view_flag = bundle.getString("view_flag", "");
            plantingHeaderModel = new Gson().fromJson(bundle.getString("planting_list"), PlantingHeaderModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_planting_header_detail_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        getUserLocation();
        getPlantingHeaderData();
        getSeasonMaster();


        ac_organizer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    organser_input_layout.setStartIconDrawable(null);
                } else {
                    if (!ac_organizer_name.getText().toString().trim().equalsIgnoreCase("")) {
                        organser_input_layout.setStartIconDrawable(null);
                    } else {
                        organser_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                    }
                }
            }
        });

        ac_organizer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_organizer_name.setSelection(s.toString().length());
                if (!s.toString().equalsIgnoreCase("")) {
                    frame_layout_org_list.setVisibility(View.VISIBLE);
                    getOrganizerList(s.toString());
                } else {
                    frame_layout_org_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_date.setOnTouchListener((view1, motionEvent) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(edit_date);
            } catch (Exception e) {
                Log.e("exc", e.getMessage());
            }
            return true;
        });

        ed_date_of_harvest.setOnTouchListener((view1, motionEvent) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_harvest);
            } catch (Exception e) {
                Log.e("exc", e.getMessage());
            }
            return true;
        });

        ac_select_loc.setOnItemClickListener((parent, view1, position, id) -> {
            locationMasterTable = locationMasterTableList.get(position);
            if (locationMasterTable != null) {
                ac_select_loc.setText(locationMasterTable.location_name);
            } else {
                ac_select_loc.setText("");
            }
        });

        back_button_go_topreviousPage.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
        ArrayAdapter<String> parent_type_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.asList(CommonData.parent_type));
        drop_down_parent.setAdapter(parent_type_adapter);

        ArrayAdapter<String> crop_stage_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.asList(CommonData.stage_code));
        ac_stage_code.setAdapter(crop_stage_adapter);
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.asList(CommonData.type));
        ac_type.setAdapter(type_adapter);

        ItemAdapter sub_type_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.doc_sub_type));
        ac_doc_sub_type.setAdapter(sub_type_adapter);

        create_header_btn.setOnClickListener(v -> {
            callSubmitPlantingheader();
        });

        dropdown_sesion_code.setOnItemClickListener((parent, view1, position, id) -> {
            if (seasonMasterTableList != null && seasonMasterTableList.size() > 0) {
                seasonMasterTable = seasonMasterTableList.get(position);
                if (seasonMasterTable != null) {
                    dropdown_sesion_code.setText(seasonMasterTable.description);
                }
            }
        });

        chip_add_line_planting.setOnClickListener(v -> {
            if (plantingHeaderModel != null) {
                AddNewItemPopup(plantingHeaderModel.Document_SubType, "", "insert", null);
            } else {
                Toast.makeText(getActivity(), "Header is empty !", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        delete_header_chip.setOnClickListener(v -> {
            if (plantingLineTableList != null && plantingLineTableList.size() > 0) {
                Toast.makeText(getActivity(), "You can't delete header as lines exist ! ", Toast.LENGTH_SHORT).show();
                return;
            } else if (plantingHeaderModel != null) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Header" + "(" + plantingHeaderModel.code + ")")
                        .setMessage("Do you want to delete this header ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteHeader(plantingHeaderModel.code);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });


        listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (plantingHeaderModel == null) {
                Toast.makeText(getActivity(), "Header is blank", Toast.LENGTH_SHORT).show();
            }

           else if (plantingHeaderModel.nav_sync!=null && plantingHeaderModel.nav_sync.equals("1")) {
                Toast.makeText(getActivity(), "You can't delete line as status completed.", Toast.LENGTH_SHORT).show();
            }
           else {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Header Line" + "(" + plantingLineTableList.get(position).code + ")")
                        .setMessage("Do you want to delete this Line ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletePlantingLine(plantingLineTableList.get(position).code, plantingLineTableList.get(position).line_no, position);
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
            return true;
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (plantingLineTableList != null && plantingLineTableList.size() > 0) {
                    //todo getting model from particular index from list....
                    PlantingHeaderModel.PlantingLine plantingLineModel = plantingLineTableList.get(position);

                    if (plantingLineModel != null) {
                        AddNewItemPopup("", "", "view", plantingLineModel);
                    }
                }
            }
        });


        complete_header.setOnClickListener(v -> {
            if (plantingHeaderModel.code != null && !plantingHeaderModel.code.equalsIgnoreCase("")) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Planting" + "(" + plantingHeaderModel.code + ")")
                        .setMessage("Do you really want to complete this planting ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                completePlanting(plantingHeaderModel.code);
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            } else {
                Toast.makeText(getActivity(), "Planting code can't be null.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        dropdown_sesion_code = view.findViewById(R.id.dropdown_sesion_code);
        edit_date = view.findViewById(R.id.ed_date);
        ac_organizer_name = view.findViewById(R.id.ac_org_name);
        drop_down_parent = view.findViewById(R.id.drop_down_parent);
        ac_select_loc = view.findViewById(R.id.ac_select_loc);
        ed_date_of_harvest = view.findViewById(R.id.ed_date_of_harvest);
        ed_total_sowing_area = view.findViewById(R.id.ed_total_sowing_area);
        ed_total_land_acres = view.findViewById(R.id.ed_total_land_acres);
        ac_doc_sub_type = view.findViewById(R.id.ac_doc_sub_type);
        ac_stage_code = view.findViewById(R.id.ac_stage_code);
        create_header_btn = view.findViewById(R.id.create_header_btn);
        header_create_section = view.findViewById(R.id.header_create_section);
        planting_detail_header_section = view.findViewById(R.id.planting_detail_header_section);
        ac_type = view.findViewById(R.id.ac_type);
        loading_content = view.findViewById(R.id.content_loading);
        //header section input field....
        tv_palnting_no = view.findViewById(R.id.tv_palnting_no);
        tv_date = view.findViewById(R.id.tv_date);
        tv_season_code = view.findViewById(R.id.tv_season_code);
        tv_parent_type = view.findViewById(R.id.tv_parent_type);
        tv_header_no = view.findViewById(R.id.tv_header_no);
        tv_doc_subtype = view.findViewById(R.id.tv_doc_subtype);
        tv_land_acr = view.findViewById(R.id.land_acr);
        tv_sowing_acr_type = view.findViewById(R.id.tv_sowing_acr_type);
        tv_stage_code = view.findViewById(R.id.tv_stage_code);
        tv_org_name = view.findViewById(R.id.tv_org_name);
        tv_org_code = view.findViewById(R.id.tv_org_code);
        // create planting line...
        listview = view.findViewById(R.id.line_listview);
        chip_add_line_planting = view.findViewById(R.id.chip_add_line_planting);
        delete_header_chip = view.findViewById(R.id.delete_header);
        back_button_go_topreviousPage = view.findViewById(R.id.back_button_go_topreviousPage);
        line_cardview = view.findViewById(R.id.line_cardview);
        complete_header = view.findViewById(R.id.complete_header);
        frame_layout_org_list = view.findViewById(R.id.frame_layout_org_list);
        search_loading_item = view.findViewById(R.id.loading_item);
        lv_cust_dist_list = view.findViewById(R.id.lv_cust_dist_list);
        ac_organizer_name = view.findViewById(R.id.ac_org_name);
        organser_input_layout = view.findViewById(R.id.organser_input_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);
        unit_of_msrd_cd_layout = view.findViewById(R.id.unit_of_msrd_cd_layout);
    }

    private void getPlantingHeaderData() {
        if (view_flag != null) {
            if (view_flag.equalsIgnoreCase("view_header_line")) {
                if (plantingHeaderModel != null) {
                    if (plantingHeaderModel.status > 0) {
                        header_create_section.setVisibility(View.GONE);
                        planting_detail_header_section.setVisibility(View.VISIBLE);
                        line_cardview.setVisibility(View.VISIBLE);
                        chip_add_line_planting.setVisibility(View.GONE);
                        delete_header_chip.setVisibility(View.GONE);
                        complete_header.setVisibility(View.GONE);
                    } else {
                        header_create_section.setVisibility(View.GONE);
                        line_cardview.setVisibility(View.VISIBLE);
                        chip_add_line_planting.setVisibility(View.VISIBLE);
                        delete_header_chip.setVisibility(View.VISIBLE);
                        planting_detail_header_section.setVisibility(View.VISIBLE);
                        complete_header.setVisibility(View.VISIBLE);
                    }
                    try {
                        setHeaderDetailData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    plantingLineTableList = plantingHeaderModel.pl;
                    setLineAdapter();
                } else {
                    // header_create_section.setVisibility(View.VISIBLE);
                    line_cardview.setVisibility(View.GONE);
                    planting_detail_header_section.setVisibility(View.GONE);
                    delete_header_chip.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setLineAdapter() {
        if (this.plantingLineTableList != null && this.plantingLineTableList.size() > 0 && this.plantingLineTableList.get(0).line_no != null) {
            plantingLineAdapter = new PlantingLineAdapter(getActivity(), this.plantingLineTableList);
            listview.setAdapter(plantingLineAdapter);
        } else {
            listview.setAdapter(null);
        }
    }


    private void getSeasonMaster() {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_content.setVisibility(View.GONE);
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            seasonMasterTableList = templantingList_season;
                            SeasonMasterAdapter seasonMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterTableList);
                            dropdown_sesion_code.setAdapter(seasonMasterAdapter);
                        } else {
                            loading_content.setVisibility(View.GONE);
                            dropdown_sesion_code.setText("");
                            dropdown_sesion_code.setAdapter(null);
                            Toast.makeText(getActivity(), templantingList_season != null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_season", getActivity());
                } finally {
                    loading_content.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_season", getActivity());
            }
        });
    }


    private List<OrganizerModel.Data> organizer_master_tableList = null;

    private void getOrganizerList(String filter_key) {
        search_loading_item.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<OrganizerModel> call = mAPIService.getOrgnizerData("O_D", filter_key);
        call.enqueue(new Callback<OrganizerModel>() {
            @Override
            public void onResponse(Call<OrganizerModel> call, Response<OrganizerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        search_loading_item.setVisibility(View.GONE);
                        OrganizerModel organizerModel = response.body();
                        if (organizerModel != null && organizerModel.condition) {
                            List<OrganizerModel.Data> organizerList = organizerModel.data;
                            if (organizerList != null && organizerList.size() > 0) {
                                organizer_master_tableList = organizerList;
                                setOrganiserAdapter();
                            } else {
                                lv_cust_dist_list.setAdapter(null);
                                search_loading_item.setVisibility(View.GONE);
                                frame_layout_org_list.setVisibility(View.GONE);
                            }
                        } else {
                            search_loading_item.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Organizer Record not found !", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        search_loading_item.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    search_loading_item.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "organizer_list", getActivity());
                }
            }

            @Override
            public void onFailure(Call<OrganizerModel> call, Throwable t) {
                search_loading_item.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "organizer_list", getActivity());
            }
        });
    }

    private void setOrganiserAdapter() {
        OrganizerAdapter organizerAdapter = new OrganizerAdapter(getActivity(), organizer_master_tableList);
        lv_cust_dist_list.setAdapter(organizerAdapter);
        organizerAdapter.setClickListner(this);
    }

    private void callSubmitPlantingheader() {
        if (ac_doc_sub_type.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select doc. type", Toast.LENGTH_SHORT).show();
        } else if (ac_select_loc.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Pleasse select location", Toast.LENGTH_SHORT).show();

        } else if (ac_stage_code.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select stage code", Toast.LENGTH_SHORT).show();
        } else if (edit_date.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select date.", Toast.LENGTH_SHORT).show();
        } else if (ed_date_of_harvest.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select date of harvests.", Toast.LENGTH_SHORT).show();
        } else if (dropdown_sesion_code.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select season.", Toast.LENGTH_SHORT).show();
        } else if (ac_organizer_name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select organizer.", Toast.LENGTH_SHORT).show();
        } else if (organizer_master_table == null || organizer_master_table.No == null) {
            Toast.makeText(getActivity(), "Please select organiser", Toast.LENGTH_SHORT).show();
        } else {

            PlantingHeaderModel plantingHeaderModel1 = new PlantingHeaderModel();
            plantingHeaderModel1.production_centre_loc = locationMasterTable.location_code;
            plantingHeaderModel1.date = DateTimeUtilsCustome.splitDateInMMDDYYYYDash(edit_date.getText().toString().trim());
            plantingHeaderModel1.date_of_harvest = DateTimeUtilsCustome.splitDateInMMDDYYYYDash(ed_date_of_harvest.getText().toString().trim());
            plantingHeaderModel1.season_code = seasonMasterTable.season_Code != null ? seasonMasterTable.season_Code : "";
            plantingHeaderModel1.season_name = dropdown_sesion_code.getText().toString().trim();
            plantingHeaderModel1.type = ac_type.getText().toString().trim();
            plantingHeaderModel1.stage_code = ac_stage_code.getText().toString().trim();
            plantingHeaderModel1.total_sowing_area_in_acres = ed_total_sowing_area.getText().toString().trim() != null ? ed_total_sowing_area.getText().toString().trim() : "0";
            plantingHeaderModel1.total_land_in_acres = ed_total_land_acres.getText().toString().trim() != null ? ed_total_land_acres.getText().toString().trim() : "0";
            plantingHeaderModel1.parent_type = drop_down_parent.getText().toString().trim();//ac_type.getText().toString().trim();
            plantingHeaderModel1.Document_SubType = ac_doc_sub_type.getText().toString().trim();
            plantingHeaderModel1.created_by = sessionManagement.getUserEmail();
            plantingHeaderModel1.organizer_code = organizer_master_table.No;
            plantingHeaderModel1.organizer_name = organizer_master_table.Name != null && !organizer_master_table.Name.equalsIgnoreCase("") ? organizer_master_table.Name : "";

            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            if (isNetwork) {
                String jsonString = new Gson().toJson(plantingHeaderModel1);
                JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<PlantingHeaderModel>> call = mAPIService.insertPlantingHeader(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                //List<PlantingHeaderModel> plantingHeaderModelList = new ArrayList<>();
                call.enqueue(new Callback<List<PlantingHeaderModel>>() {
                    @Override
                    public void onResponse(Call<List<PlantingHeaderModel>> call, Response<List<PlantingHeaderModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<PlantingHeaderModel> header_response_modelList = response.body();
                                if (header_response_modelList != null && header_response_modelList.size() > 0 && header_response_modelList.get(0).condition) {
                                    // plantingHeaderModel.code = header_response_modelList.get(0).planting_code;
                                    plantingHeaderModel = header_response_modelList.get(0);

                                    //plantingHeaderModel.add_online_offlie_header = 0;
                                    //plantingHeaderModelList.add(plantingHeaderModel);
                                    bindPlantingHeaderData();
                                    Toast.makeText(getActivity(), "Planting code :" + header_response_modelList.get(0).code + "," + header_response_modelList.get(0).message, Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), header_response_modelList.size() > 0 ? header_response_modelList.get(0).message : "Api not respoding.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "header_response_model", getActivity());
                        } finally {
                            progressDialogLoading.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PlantingHeaderModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "header_response_model", getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please wait for internet connection. ", Toast.LENGTH_SHORT).show();
              /*  List<PlantingHeaderModel> plantingHeaderModelList = new ArrayList<>();
                plantingHeaderModel.code = "0";
                plantingHeaderModel.add_online_offlie_header = 1;
                plantingHeaderModelList.add(plantingHeaderModel);*/
                //bindPlantingHeaderData(plantingHeaderModelList);
                //offline network calling...
            }
        }
    }


    private void bindPlantingHeaderData() {
      /*  PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        PlantingHeaderDao plantingHeaderDao = pristineDatabase.plantingHeaderDao();
        try {
            PlantingDetailHeaderTable plantingDetailHeaderTable = new PlantingDetailHeaderTable();
            plantingDetailHeaderTable.setProduction_centre_loc(plantingHeaderModelList.get(0).production_centre_loc);
            plantingDetailHeaderTable.setCode(plantingHeaderModelList.get(0).code);
            plantingDetailHeaderTable.setDate(plantingHeaderModelList.get(0).date);
            plantingDetailHeaderTable.setDate_of_harvest(plantingHeaderModelList.get(0).date_of_harvest);
            plantingDetailHeaderTable.setSeason_code(plantingHeaderModelList.get(0).season_name);
            plantingDetailHeaderTable.setType(plantingHeaderModelList.get(0).type);
            plantingDetailHeaderTable.setStage_code(plantingHeaderModelList.get(0).stage_code);
            plantingDetailHeaderTable.setTotal_sowing_area_in_acres(plantingHeaderModelList.get(0).total_sowing_area_in_acres);
            plantingDetailHeaderTable.setTotal_land_in_acres(plantingHeaderModelList.get(0).total_land_in_acres);
            plantingDetailHeaderTable.setParent_type(plantingHeaderModelList.get(0).parent_type);
            plantingDetailHeaderTable.setDocument_SubType(plantingHeaderModelList.get(0).Document_SubType);
            plantingDetailHeaderTable.setCreated_by(plantingHeaderModelList.get(0).created_by);
            plantingDetailHeaderTable.setOrganizer_name(plantingHeaderModelList.get(0).organizer_name);
            plantingDetailHeaderTable.setOrganizer_code(plantingHeaderModelList.get(0).organizer_code);

            if (plantingHeaderDao.isDataExist(plantingDetailHeaderTable.getCode())) {
                plantingHeaderDao.update(plantingDetailHeaderTable);
            } else {
                plantingHeaderDao.insert(plantingDetailHeaderTable);
            }
           // plantingHeaderModel = plantingHeaderDao.getHeaderByPlantingno(plantingDetailHeaderTable.getCode());

            Log.e("planting_list", new Gson().toJson(plantingHeaderModel));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();*/
        try {
            if (plantingHeaderModel != null) {
                header_create_section.setVisibility(View.GONE);
                planting_detail_header_section.setVisibility(View.VISIBLE);
                delete_header_chip.setVisibility(View.VISIBLE);
                line_cardview.setVisibility(View.VISIBLE);
                setHeaderDetailData();
                if (plantingLineTableList != null && plantingLineTableList.size() > 0) {
                    complete_header.setVisibility(View.VISIBLE);
                }
            } else {
                header_create_section.setVisibility(View.VISIBLE);
                line_cardview.setVisibility(View.GONE);
                complete_header.setVisibility(View.GONE);
                planting_detail_header_section.setVisibility(View.GONE);
                delete_header_chip.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // }
    }

    private void setHeaderDetailData() throws Exception {
        tv_header_no.setText("(" + plantingHeaderModel.code + ")");
        tv_palnting_no.setText(plantingHeaderModel.code);
        tv_date.setText(plantingHeaderModel.date);
        tv_season_code.setText(plantingHeaderModel.season_code);
        tv_doc_subtype.setText(plantingHeaderModel.Document_SubType);
        tv_parent_type.setText(plantingHeaderModel.parent_type);
        tv_stage_code.setText(plantingHeaderModel.stage_code);
        tv_org_code.setText(plantingHeaderModel.organizer_code);
        tv_org_name.setText(plantingHeaderModel.organizer_name);

        if (plantingLineTableList != null && plantingLineTableList.size() > 0) {
            double total_sowing_area = 0.0, grant_total = 0.0;
            for (int i = 0; i < plantingLineTableList.size(); i++) {
                if (plantingLineTableList.get(i).sowing_area_In_acres != null && !plantingLineTableList.get(i).sowing_area_In_acres.equalsIgnoreCase("")) {
                    total_sowing_area = Double.parseDouble((plantingLineTableList.get(i).sowing_area_In_acres));
                    grant_total = grant_total + total_sowing_area;
                }
            }
            tv_sowing_acr_type.setText(String.valueOf(grant_total));
            Log.e("sowing_area", String.valueOf(grant_total));
        } else {
            tv_sowing_acr_type.setText("0");
        }
    }

    private PlantingFsio_bsio_model planting_doc_table = null;
    private List<PlantingFsio_bsio_model> plantingFsioList = null;

    private void getAcDocNo(ProgressBar content_loading, String doc_sub_type, String org_no) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingFsio_bsio_model>> call = mAPIService.getPlantingFsioBsioData(doc_sub_type, org_no);
        call.enqueue(new Callback<List<PlantingFsio_bsio_model>>() {
            @Override
            public void onResponse(Call<List<PlantingFsio_bsio_model>> call, Response<List<PlantingFsio_bsio_model>> response) {
                try {
                    if (response.isSuccessful()) {
                        content_loading.setVisibility(View.GONE);
                        List<PlantingFsio_bsio_model> templantingList_fsio = response.body();
                        if (templantingList_fsio != null && templantingList_fsio.size() > 0 && templantingList_fsio.get(0).condition) {
                            plantingFsioList = templantingList_fsio;
                            PlantingFsioBsioAdapter plantingFsioBsioAdapter = new PlantingFsioBsioAdapter(getActivity(), R.layout.android_item_view, templantingList_fsio);
                            ac_doc_no.setAdapter(plantingFsioBsioAdapter);

                        } else {
                            content_loading.setVisibility(View.GONE);
                            ac_doc_no.setAdapter(null);
                            // Toast.makeText(getActivity(), templantingList_fsio==null && templantingList_fsio.size() > 0 && templantingList_fsio.get(0).No==null ? "Planting Fsio/Bsio Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_fsio_bsio_fragment_", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PlantingFsio_bsio_model>> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_fsio_bsio_fragment_", getActivity());
            }
        });
    }


    public void AddNewItemPopup(String Doucment_subtype, String organiser_code, String flag, PlantingHeaderModel.PlantingLine plantingLineModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_planting_line_lot_no_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        ac_grwer_name = popupView.findViewById(R.id.ac_grwer_name);
        ed_supervsr_name = popupView.findViewById(R.id.ed_supervsr_name);
        ed_grower_code = popupView.findViewById(R.id.gr_owner_code);

        ac_doc_no = popupView.findViewById(R.id.ac_doc_no);
        ed_vill_name = popupView.findViewById(R.id.ed_vill_name);
        ac_taluka = popupView.findViewById(R.id.ac_taluka);
        ac_state = popupView.findViewById(R.id.dropdown_state);
        dropdown_city = popupView.findViewById(R.id.dropdown_city);
        ed_address_ = popupView.findViewById(R.id.ed_address_);
        ed_parent_male = popupView.findViewById(R.id.ed_parent_male);
        ac_parent_female_lot = popupView.findViewById(R.id.ac_parent_female_lot);
        ed_receiptno_female = popupView.findViewById(R.id.ed_receiptno_female);
        ed_receptno_male = popupView.findViewById(R.id.ed_receptno_male);
        ed_parent_female = popupView.findViewById(R.id.ed_parent_female);
        recpt_female = popupView.findViewById(R.id.recpt_female);
        recpt_male = popupView.findViewById(R.id.recpt_male);
        recpt_other = popupView.findViewById(R.id.recpt_other);

        ed_sowing_female = popupView.findViewById(R.id.ed_sowing_female);
        ed_sowing_male = popupView.findViewById(R.id.ed_sowingdate_male);

        ed_expected_yield = popupView.findViewById(R.id.ed_expected_yield);
        ed_sowing_date_other = popupView.findViewById(R.id.ed_sowing_date_other);
        revised_yield = popupView.findViewById(R.id.revised_yield);
        ed_sowing_area = popupView.findViewById(R.id.ed_sowing_area);
        unit_of_msrd_cd = popupView.findViewById(R.id.unit_of_msrd_cd);
        // ed_land_lease = popupView.findViewById(R.id.ed_land_lease);
        radio_group_land_lease = popupView.findViewById(R.id.groupradio_land_lease);
        radio_1 = popupView.findViewById(R.id.radia_id1);
        radio_2 = popupView.findViewById(R.id.radia_id2);

        ed_item_clsof_seed = popupView.findViewById(R.id.ed_item_clsof_seed);
        item_crop_type = popupView.findViewById(R.id.item_crop_type);
        ed_item_name = popupView.findViewById(R.id.ed_item_name);
        item_prod_grp_cd = popupView.findViewById(R.id.item_prod_grp_cd);
        ac_crop_code = popupView.findViewById(R.id.ac_crop_code);
        ac_parent_male_lot = popupView.findViewById(R.id.ac_parent_male_lot);
        ac_variety_code = popupView.findViewById(R.id.ac_variety_code);

        ed_male_qty = popupView.findViewById(R.id.ed_qty_male);
        ed_female_qty = popupView.findViewById(R.id.ed_qty_female);
        ed_other_receipt_no = popupView.findViewById(R.id.ed_other_receipt_no);
        ac_parent_other_lot = popupView.findViewById(R.id.ac_parent_other_lot);
        ed_parent_other = popupView.findViewById(R.id.ed_parent_others);
        ed_other_qty = popupView.findViewById(R.id.ed_other_qty);
        ac_zone_ = popupView.findViewById(R.id.ac_zone);
        ac_alias_name = popupView.findViewById(R.id.ac_alias_name);
        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        Button filter_apply_bt = popupView.findViewById(R.id.submit_line_btn);
        ac_grwer_name.setSelection(ac_grwer_name.getText().length());
        ed_vill_name.setSelection(ed_vill_name.getText().length());
        getAcDocNo(content_loading, Doucment_subtype, organiser_code);
        getUnitOfMeasureData(content_loading);
        getFarmerAndGrower_master(content_loading, "", "");

        ac_grwer_name.setOnItemClickListener((parent, view, position, id) -> {
            farmer_grower_model = farmer_grower_list.get(position);
            ac_grwer_name.setText(farmer_grower_model.Name);
            if (farmer_grower_model != null) {
                ac_grwer_name.setText(farmer_grower_model.Name);
                loading_content.setVisibility(View.VISIBLE);
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList("");
                call.enqueue(new Callback<DispatchFarmerModel>() {
                    @Override
                    public void onResponse(Call<DispatchFarmerModel> call, Response<DispatchFarmerModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                DispatchFarmerModel farmerMasterModel = response.body();
                                if (farmerMasterModel != null && farmerMasterModel.condition) {
                                    List<DispatchFarmerModel.Data> farmermaster_list = farmerMasterModel.data;
                                    if (farmermaster_list != null && farmermaster_list.size() > 0) {
                                        farmer_grower_list = farmermaster_list;
                                        //getFarmerGrowerDertail(farmer_grower_model.No);
                                        //ac_grwer_name.setText(farmer_grower_list.get(0).Name);
                                        // if (role_master_table != null && role_master_table.getName() != null && role_master_table.getZone_Name() != null && role_master_table.getState_Code() != null && role_master_table.getCity() != null && role_master_table.getNo() != null) {
                                        ac_state.setText(farmer_grower_list.get(0).State_Code != null ? farmer_grower_list.get(0).State_Code : "");
                                        ac_taluka.setText(farmer_grower_list.get(0).Territory_Name != null ? farmer_grower_list.get(0).Territory_Name : "");
                                        dropdown_city.setText(farmer_grower_list.get(0).City != null ? farmer_grower_list.get(0).City : "");
                                        ed_address_.setText(farmer_grower_list.get(0).Address != null ? farmer_grower_list.get(0).Address : "");
                                        ed_grower_code.setText(farmer_grower_list.get(0).No != null ? farmer_grower_list.get(0).No : "");
                                    }
                                } else {
                                    loading_content.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                loading_content.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            loading_content.setVisibility(View.GONE);
                            Log.e("exception database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", getActivity());
                        } finally {
                            loading_content.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<DispatchFarmerModel> call, Throwable t) {
                        loading_content.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
                    }
                });
            }
        });
        ed_vill_name.setOnItemClickListener((parent, view, position, id) -> {
            if (village_list != null && village_list.size() > 0) {
                ed_vill_name.setText(village_list.get(position).Village);
                getFarmerAndGrower_master(content_loading, "Village", village_list.get(position).Village);
            }
        });

        ac_doc_no.setOnItemClickListener((parent, view, position, id) -> {
            try {
                planting_doc_table = plantingFsioList.get(position);
                if (planting_doc_table != null) {
                    runbackgraundTask(planting_doc_table.Child_Seed_Type, planting_doc_table.Crop_Code);
                    // getProductGrpCode(planting_doc_table.Child_Seed_Type, planting_doc_table.Crop_Code);
                    if (planting_doc_table.Document_SubType.equalsIgnoreCase("FSIO")) {
                        ed_item_clsof_seed.setEnabled(false);
                        ed_item_name.setVisibility(View.VISIBLE);
                        recpt_male.setVisibility(View.VISIBLE);
                        recpt_female.setVisibility(View.VISIBLE);
                        recpt_other.setVisibility(View.VISIBLE);
                        ed_item_clsof_seed.setText(planting_doc_table.Child_Seed_Type);
                        ed_item_name.setText(planting_doc_table.Child_Seed_Name);
                        item_crop_type.setText(planting_doc_table.Child_Seed_Type);
                        ac_variety_code.setText(planting_doc_table.Child_Seed);
                        ac_crop_code.setText(planting_doc_table.Crop_Code);
                        ac_parent_male_lot.setText("");
                        ed_parent_male.setText("");
                        ed_male_qty.setText("");
                        ac_parent_female_lot.setText("");
                        ed_parent_female.setText("");
                        ed_female_qty.setText("");
                        ac_parent_other_lot.setText("");
                        ed_parent_other.setText("");
                        ed_other_qty.setText("");
                        getPlantingLotListMale(content_loading, planting_doc_table.No,"FSIO", "Male");
                        getPlantingLotListFemale(content_loading,  planting_doc_table.No,"FSIO", "Female");

                    } else if (planting_doc_table.Document_SubType.equalsIgnoreCase("BSIO")) {
                        ed_item_name.setVisibility(View.VISIBLE);
                        ed_item_clsof_seed.setEnabled(true);
                        recpt_male.setVisibility(View.GONE);
                        recpt_female.setVisibility(View.GONE);
                        recpt_other.setVisibility(View.GONE);
                        ed_item_clsof_seed.setText(planting_doc_table.Child_Seed_Type);
                        ed_item_name.setText(planting_doc_table.Child_Seed_Name);
                        item_crop_type.setText(planting_doc_table.Child_Seed_Type);
                        ac_variety_code.setText(planting_doc_table.Child_Seed);
                        ac_crop_code.setText(planting_doc_table.Crop_Code);
                        getPlantingLotListMale(content_loading,  planting_doc_table.No,"BSIO", "Male");
                        getPlantingLotListFemale(content_loading, planting_doc_table.No,"BSIO",  "Female");

                    }

                    getPlantingLotListOther(content_loading, "FSIO", planting_doc_table.No, "Male");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        runbackgraundTask("", "");

        ac_zone_.setOnItemClickListener((parent, view, position, id) -> {
            if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                ac_zone_.setText(zoneMasterTableList.get(position).getDescription());
                zone_code = zoneMasterTableList.get(position).getCode();
            }
        });

        radio_group_land_lease.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radia_id1:
                    postedString = "1";
                    break;

                case R.id.radia_id2:
                    postedString = "0";
                    break;
            }
        });
        ac_parent_male_lot.setOnItemClickListener((parent, view, position, id) -> {
            try {
                plantingMaleTable = plantingMaleList.get(position);
                ac_parent_male_lot.setText(plantingMaleTable.Lot_No);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (plantingMaleTable != null) {
                    ed_parent_male.setText(plantingMaleTable.Lot_No);
                    ed_male_qty.setText(plantingMaleTable.Quantity);
                    ed_receptno_male.setText(plantingMaleTable.Receipt_No != null ? plantingMaleTable.Receipt_No : "");
                    ed_male_qty.setEnabled(true);
                }
            }
        });

        ac_parent_female_lot.setOnItemClickListener((parent, view, position, id) -> {
            try {
                plantingFemaleTable = plantingLotFemaleList.get(position);
                ac_parent_female_lot.setText(plantingFemaleTable.Lot_No);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (plantingFemaleTable != null) {
                    ed_parent_female.setText(plantingFemaleTable.Lot_No);
                    ed_female_qty.setText(plantingFemaleTable.Quantity);
                    ed_receiptno_female.setText(plantingFemaleTable.Receipt_No != null ? plantingFemaleTable.Receipt_No : "");
                    ed_female_qty.setEnabled(true);
                }
            }
        });

        ac_parent_other_lot.setOnItemClickListener((parent, view, position, id) -> {
            try {
                plantingMasterTable = plantingLotOtherList.get(position);
                ac_parent_other_lot.setText(plantingMasterTable.Lot_No);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (plantingMasterTable != null) {
                    if (plantingMasterTable.Lot_No != null && !plantingMasterTable.Lot_No.equalsIgnoreCase("")) {
                        ed_parent_male.setText(plantingMasterTable.Lot_No);
                    }
                    ed_other_qty.setText(plantingMasterTable.Quantity);
                    ed_receptno_male.setText(plantingMasterTable.Receipt_No != null ? plantingMasterTable.Receipt_No : "");
                    ed_other_qty.setEnabled(true);
                }
            }
        });

       /* if (hybrid_item_tableList != null && hybrid_item_tableList.size() > 0) {
            HybridItemAliasNameAdapter hybridItemAdapter = new HybridItemAliasNameAdapter(getActivity(), R.layout.android_item_view, hybrid_item_tableList);
            ac_alias_name.setAdapter(hybridItemAdapter);
        }*/

        ed_sowing_female.setOnTouchListener((view, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_female);
            return true;
        });

        ed_sowing_male.setOnTouchListener((view, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_male);
            return true;
        });

        ed_sowing_date_other.setOnTouchListener((view, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_sowing_date_other);
            return true;
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (flag.equals("insert")) {
            filter_apply_bt.setOnClickListener(v -> {
                if (ac_grwer_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select grower name", Toast.LENGTH_SHORT).show();
                } else {
                    PlantingHeaderModel.PlantingLine plantingLine = new PlantingHeaderModel().new PlantingLine();
                    plantingLine.code = plantingHeaderModel.code;
                    line_series = line_series + 1;
                    plantingLine.line_no = String.valueOf(line_series);
                    plantingLine.doc_no = planting_doc_table.No;
                    plantingLine.parent_male = ed_parent_male.getText().toString().trim();
                    plantingLine.parent_male_lot = ac_parent_male_lot.getText().toString().trim();
                    plantingLine.parent_female = ed_parent_female.getText().toString().trim();
                    plantingLine.parent_female_lot = ac_parent_female_lot.getText().toString().trim();
                    plantingLine.reciept_no_male = ed_receptno_male.getText().toString().trim();
                    plantingLine.reciept_no_female = ed_receiptno_female.getText().toString().trim();
                    plantingLine.grower_owner_code = ed_grower_code.getText().toString().trim();
                    plantingLine.grower_land_owner_name = ac_grwer_name.getText().toString().trim();
                    plantingLine.supervisor_name = ed_supervsr_name.getText().toString().trim() != null ? ed_supervsr_name.getText().toString().trim() : "";
                    plantingLine.crop_code = ac_crop_code.getText().toString().trim();
                    plantingLine.variety_code = ac_variety_code.getText().toString().trim();
                    plantingLine.item_product_group_code = item_prod_grp_cd.getText().toString().trim() != null ? item_prod_grp_cd.getText().toString().trim() : "";
                    plantingLine.item_class_of_seeds = ed_item_clsof_seed.getText().toString().trim();
                    if (Doucment_subtype.equalsIgnoreCase("FSIO")) {
                        plantingLine.item_name = ed_item_name.getText().toString().trim();
                    } else if (Doucment_subtype.equalsIgnoreCase("BSIO")) {
                        plantingLine.item_name = ed_item_name.getText().toString().trim() != null ? ed_item_name.getText().toString().trim() : "";
                    }
                    plantingLine.item_crop_type = item_crop_type.getText().toString().trim();
                    plantingLine.revised_yield_raw = revised_yield.getText().toString().trim();
                    plantingLine.land_lease = radio_group_land_lease.getCheckedRadioButtonId() != -1 ? postedString : "0";//ed_land_lease.getText().toString().trim();
                    plantingLine.unit_of_measure_code = unit_of_msrd_cd.getText().toString().trim();

                    if (ed_sowing_male.getText().toString().trim().equalsIgnoreCase("")) {
                        plantingLine.sowing_date_male = "";
                    } else {
                        plantingLine.sowing_date_male = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_male.getText().toString().trim());
                    }
                    if (ed_sowing_female.getText().toString().trim().equalsIgnoreCase("")) {
                        plantingLine.sowing_date_female = "";
                    } else {
                        plantingLine.sowing_date_female = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_female.getText().toString().trim());
                    }
                    if (ed_sowing_date_other.getText().toString().trim().equalsIgnoreCase("")) {
                        plantingLine.sowing_date_other = "";
                    } else {
                        plantingLine.sowing_date_other = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_sowing_date_other.getText().toString().trim());
                    }
                    if (ed_expected_yield.getText().toString().trim().equalsIgnoreCase(".") ||
                            ed_expected_yield.getText().toString().trim().equalsIgnoreCase(".0")
                            || ed_expected_yield.getText().toString().trim().equalsIgnoreCase("0.")) {
                        Toast.makeText(getContext(), "Wrong value in expected yield", Toast.LENGTH_LONG).show();
                    } else {
                        if (!ed_expected_yield.getText().toString().trim().equalsIgnoreCase("")) {
                            plantingLine.expected_yield = String.valueOf(Float.parseFloat(ed_expected_yield.getText().toString().trim()));
                        } else {
                            plantingLine.expected_yield = "0.0";
                        }
                    }
                    plantingLine.village_name = ed_vill_name.getText().toString().trim();
                    plantingLine.address = ed_address_.getText().toString().trim();
                    plantingLine.zone = zone_code;
                    plantingLine.taluka = ac_taluka.getText().toString().trim();
                    plantingLine.state = ac_state.getText().toString().trim();
                    plantingLine.city = dropdown_city.getText().toString().trim();
                    plantingLine.quantity_male = !ed_male_qty.getText().toString().trim().equalsIgnoreCase("") ? ed_male_qty.getText().toString().trim() : "0.0";
                    plantingLine.quantity_female = !ed_female_qty.getText().toString().trim().equalsIgnoreCase("") ? ed_female_qty.getText().toString().trim() : "0.0";
                    plantingLine.quantity_other = !ed_other_qty.getText().toString().trim().equalsIgnoreCase("") ? ed_other_qty.getText().toString().trim() : "0.0";
                    if (ed_sowing_area.getText().toString().equalsIgnoreCase(".") || ed_sowing_area.getText().toString().equalsIgnoreCase("0")) {
                        Toast.makeText(getContext(), "Wrong Values In sowing area", Toast.LENGTH_LONG).show();
                    } else {
                        if (!ed_sowing_area.getText().toString().equalsIgnoreCase("")) {
                            String sowing_acr_value = String.valueOf(Float.parseFloat(ed_sowing_area.getText().toString().trim()));
                            plantingLine.sowing_area_In_acres = !sowing_acr_value.equalsIgnoreCase("") ? sowing_acr_value : "0.0";
                        } else {
                            plantingLine.sowing_area_In_acres = "0.0";
                        }
                    }
                    plantingLine.parent_other = ed_parent_other.getText().toString().trim();
                    plantingLine.parent_other_lot = ac_parent_other_lot.getText().toString().trim();
                    plantingLine.reciept_no_other = ed_other_receipt_no.getText().toString().trim();
                    plantingLine.InspectionI = "0";
                    plantingLine.InspectionII = "0";
                    plantingLine.InspectionIII = "0";
                    plantingLine.InspectionIV = "0";
                    plantingLine.InspectionV = "0";
                    plantingLine.InspectionVI = "0";
                    plantingLine.InspectionVII = "0";
                    plantingLine.InspectionVIII = "0";
                    plantingLine.InspectionIX = "0";
                    plantingLine.InspectionQC = "0";
                    plantingLine.created_by = sessionManagement.getUserEmail();
                    insertPlantingLine(plantingLine, dialog);
                }
            });
        } else if (flag.equals("view")) {
            filter_apply_bt.setVisibility(View.GONE);
//            PlantingHeaderModel.PlantingLine plantingLine = new PlantingHeaderModel().new PlantingLine();
            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                if(radio_1.isChecked()) {
                    radio_1.setChecked(true);
                    radio_1.setEnabled(false);
                    radio_1.setFocusable(false);
                    radio_1.setFocusableInTouchMode(false);
                    radio_1.clearFocus();
                }
                else {
                    radio_1.setChecked(false);
                    radio_1.setEnabled(false);
                    radio_1.setFocusable(false);
                    radio_1.setFocusableInTouchMode(false);
                    radio_1.clearFocus();
                }

                if(radio_2.isChecked()) {
                    radio_2.setChecked(true);
                    radio_2.setEnabled(false);
                    radio_2.setFocusable(false);
                    radio_2.setFocusableInTouchMode(false);
                    radio_2.clearFocus();
                }
                else {
                    radio_2.setChecked(false);
                    radio_2.setEnabled(false);
                    radio_2.setFocusable(false);
                    radio_2.setFocusableInTouchMode(false);
                    radio_2.clearFocus();
                }

              /*  if(radio_1 != null){
                    radio_1.setChecked(true);
                    radio_2.setChecked(false);
                }
                else {
                    radio_1.setChecked(false);
                    radio_2.setChecked(false);
                }*/

                if (plantingLineModel.doc_no != null && !plantingLineModel.doc_no.equalsIgnoreCase("")) {
                    ac_doc_no.setText(plantingLineModel.doc_no);
                    ac_doc_no.clearFocus();
                    ac_doc_no.setFocusableInTouchMode(false);
                    ac_doc_no.setEnabled(false);
                    ac_doc_no.setFocusable(false);
                } else {
                    ac_doc_no.setText("");
                    ac_doc_no.setFocusableInTouchMode(false);
                    ac_doc_no.setEnabled(false);
                    ac_doc_no.setFocusable(false);
                }

                if(plantingLineModel.crop_code != null && !plantingLineModel.crop_code.equalsIgnoreCase("")){
                    ac_crop_code.setText(plantingLineModel.crop_code);
                    ac_crop_code.setFocusableInTouchMode(false);
                    ac_crop_code.clearFocus();
                    ac_crop_code.setFocusable(false);
                    ac_crop_code.setEnabled(false);
                }
                else {
                    ac_crop_code.setText("");
                    ac_crop_code.setFocusableInTouchMode(false);
                    ac_crop_code.setFocusable(false);
                    ac_crop_code.setEnabled(false);
                }

                if(plantingLineModel.item_product_group_code != null && !plantingLineModel.item_product_group_code.equalsIgnoreCase("")){
                    item_prod_grp_cd.setText(plantingLineModel.item_product_group_code);
                    item_prod_grp_cd.setFocusableInTouchMode(false);
                    item_prod_grp_cd.clearFocus();
                    item_prod_grp_cd.setFocusable(false);
                    item_prod_grp_cd.setEnabled(false);
                }
                else {
                    item_prod_grp_cd.setText("");
                    item_prod_grp_cd.setFocusableInTouchMode(false);
                    item_prod_grp_cd.setFocusable(false);
                    item_prod_grp_cd.setEnabled(false);
                }

                if(plantingLineModel.item_class_of_seeds != null && !plantingLineModel.item_class_of_seeds.equalsIgnoreCase("")){
                    ed_item_clsof_seed.setText(plantingLineModel.item_class_of_seeds);
                    ed_item_clsof_seed.setFocusableInTouchMode(false);
                    ed_item_clsof_seed.clearFocus();
                    ed_item_clsof_seed.setFocusable(false);
                    ed_item_clsof_seed.setEnabled(false);
                }
                else {
                    ed_item_clsof_seed.setText("");
                    ed_item_clsof_seed.setFocusableInTouchMode(false);
                    ed_item_clsof_seed.setFocusable(false);
                    ed_item_clsof_seed.setEnabled(false);
                }

                if(plantingLineModel.item_crop_type != null && !plantingLineModel.item_crop_type.equalsIgnoreCase("")){
                    item_crop_type.setText(plantingLineModel.item_crop_type);
                    item_crop_type.setFocusableInTouchMode(false);
                    item_crop_type.clearFocus();
                    item_crop_type.setFocusable(false);
                    item_crop_type.setEnabled(false);
                }
                else {
                    item_crop_type.setText("");
                    item_crop_type.setFocusableInTouchMode(false);
                    item_crop_type.setFocusable(false);
                    item_crop_type.setEnabled(false);
                }

                if(plantingLineModel.item_name != null && !plantingLineModel.item_name.equalsIgnoreCase("")){
                    ed_item_name.setText(plantingLineModel.item_name);
                    ed_item_name.setFocusableInTouchMode(false);
                    ed_item_name.clearFocus();
                    ed_item_name.setFocusable(false);
                    ed_item_name.setEnabled(false);
                }
                else {
                    ed_item_name.setText("");
                    ed_item_name.setFocusableInTouchMode(false);
                    ed_item_name.setFocusable(false);
                    ed_item_name.setEnabled(false);
                }

                if(plantingLineModel.expected_yield != null && !plantingLineModel.expected_yield.equalsIgnoreCase("")){
                    ed_expected_yield.setText(plantingLineModel.expected_yield);
                    ed_expected_yield.setFocusableInTouchMode(false);
                    ed_expected_yield.clearFocus();
                    ed_expected_yield.setFocusable(false);
                    ed_expected_yield.setEnabled(false);
                }
                else {
                    ed_expected_yield.setText("");
                    ed_expected_yield.setFocusableInTouchMode(false);
                    ed_expected_yield.setFocusable(false);
                    ed_expected_yield.setEnabled(false);
                }

                if(plantingLineModel.unit_of_measure_code != null && !plantingLineModel.unit_of_measure_code.equalsIgnoreCase("")){
                    unit_of_msrd_cd.setText(plantingLineModel.unit_of_measure_code);
                    unit_of_msrd_cd.clearFocus();
                    unit_of_msrd_cd.setDropDownHeight(0);
                    unit_of_msrd_cd.setFocusableInTouchMode(false);
                    unit_of_msrd_cd.setFocusable(false);
                    unit_of_msrd_cd.setEnabled(false);
                }
                else {
                    unit_of_msrd_cd.setText("");
                    unit_of_msrd_cd.setDropDownHeight(0);
                    unit_of_msrd_cd.setFocusableInTouchMode(false);
                    unit_of_msrd_cd.setFocusable(false);
                    unit_of_msrd_cd.setEnabled(false);
                }

                if(plantingLineModel.sowing_area_In_acres != null && !plantingLineModel.sowing_area_In_acres.equalsIgnoreCase("")){
                    ed_sowing_area.setText(plantingLineModel.sowing_area_In_acres);
                    ed_sowing_area.setFocusableInTouchMode(false);
                    ed_sowing_area.clearFocus();
                    ed_sowing_area.setFocusable(false);
                    ed_sowing_area.setEnabled(false);
                }
                else {
                    ed_sowing_area.setText("");
                    ed_sowing_area.setFocusableInTouchMode(false);
                    ed_sowing_area.setFocusable(false);
                    ed_sowing_area.setEnabled(false);
                }

                if(plantingLineModel.zone != null && !plantingLineModel.zone.equalsIgnoreCase("")){
                    ac_zone_.setText(plantingLineModel.zone);
                    ac_zone_.setDropDownHeight(0);
                    ac_zone_.setFocusableInTouchMode(false);
                    ac_zone_.clearFocus();
                    ac_zone_.setFocusable(false);
                    ac_zone_.setEnabled(false);
                }
                else {
                    ac_zone_.setText("");
                    ac_zone_.setDropDownHeight(0);
                    ac_zone_.setFocusableInTouchMode(false);
                    ac_zone_.setFocusable(false);
                    ac_zone_.setEnabled(false);
                }

                if(plantingLineModel.village_name != null && !plantingLineModel.village_name.equalsIgnoreCase("")){
                    ed_vill_name.setText(plantingLineModel.village_name);
                    ed_vill_name.setDropDownHeight(0);
                    ed_vill_name.clearFocus();
                    ed_vill_name.setFocusableInTouchMode(false);
                    ed_vill_name.setFocusable(false);
                    ed_vill_name.setEnabled(false);
                }
                else {
                    ed_vill_name.setText("");
                    ed_vill_name.setDropDownHeight(0);
                    ed_vill_name.setFocusable(false);
                    ed_vill_name.setEnabled(false);
                }

                if(plantingLineModel.taluka != null && !plantingLineModel.taluka.equalsIgnoreCase("")){
                    ac_taluka.setText(plantingLineModel.taluka);
                    ac_taluka.setFocusableInTouchMode(false);
                    ac_taluka.clearFocus();
                    ac_taluka.setFocusable(false);
                    ac_taluka.setEnabled(false);
                }
                else {
                    ac_taluka.setText("");
                    ac_taluka.setFocusableInTouchMode(false);
                    ac_taluka.setFocusable(false);
                    ac_taluka.setEnabled(false);
                }

                if(plantingLineModel.state != null && !plantingLineModel.state.equalsIgnoreCase("")){
                    ac_state.setText(plantingLineModel.state);
                    ac_state.setFocusableInTouchMode(false);
                    ac_state.clearFocus();
                    ac_state.setFocusable(false);
                    ac_state.setEnabled(false);
                }
                else {
                    ac_state.setText("");
                    ac_state.setFocusableInTouchMode(false);
                    ac_state.setFocusable(false);
                    ac_state.setEnabled(false);
                }

                if(plantingLineModel.city != null && !plantingLineModel.city.equalsIgnoreCase("")){
                    dropdown_city.setText(plantingLineModel.city);
                    dropdown_city.setFocusableInTouchMode(false);
                    dropdown_city.clearFocus();
                    dropdown_city.setFocusable(false);
                    dropdown_city.setEnabled(false);
                }
                else {
                    dropdown_city.setText("");
                    dropdown_city.setFocusableInTouchMode(false);
                    dropdown_city.setFocusable(false);
                    dropdown_city.setEnabled(false);
                }

                if(plantingLineModel.address != null && !plantingLineModel.address.equalsIgnoreCase("")){
                    ed_address_.setText(plantingLineModel.address);
                    ed_address_.setFocusableInTouchMode(false);
                    ed_address_.clearFocus();
                    ed_address_.setFocusable(false);
                    ed_address_.setEnabled(false);
                }
                else {
                    ed_address_.setText("");
                    ed_address_.setFocusableInTouchMode(false);
                    ed_address_.setFocusable(false);
                    ed_address_.setEnabled(false);
                }

                if(plantingLineModel.grower_land_owner_name != null && !plantingLineModel.grower_land_owner_name.equalsIgnoreCase("")){
                    ac_grwer_name.setText(plantingLineModel.grower_land_owner_name);
                    ac_grwer_name.setDropDownHeight(0);
                    ac_grwer_name.clearFocus();
                    ac_grwer_name.setFocusableInTouchMode(false);
                    ac_grwer_name.setFocusable(false);
                    ac_grwer_name.setEnabled(false);
                }
                else {
                    ac_grwer_name.setText("");
                    ac_grwer_name.setDropDownHeight(0);
                    ac_grwer_name.setFocusableInTouchMode(false);
                    ac_grwer_name.setFocusable(false);
                    ac_grwer_name.setEnabled(false);
                }

                if(plantingLineModel.grower_owner_code != null && !plantingLineModel.grower_owner_code.equalsIgnoreCase("")){
                    ed_grower_code.setText(plantingLineModel.grower_owner_code);
                    ed_grower_code.setFocusableInTouchMode(false);
                    ed_grower_code.clearFocus();
                    ed_grower_code.setFocusable(false);
                    ed_grower_code.setEnabled(false);
                }
                else {
                    ed_grower_code.setText("");
                    ed_grower_code.setFocusableInTouchMode(false);
                    ed_grower_code.setFocusable(false);
                    ed_grower_code.setEnabled(false);
                }

                if(plantingLineModel.supervisor_name != null && !plantingLineModel.supervisor_name.equalsIgnoreCase("")){
                    ed_supervsr_name.setText(plantingLineModel.supervisor_name);
                    ed_supervsr_name.setFocusableInTouchMode(false);
                    ed_supervsr_name.clearFocus();
                    ed_supervsr_name.setFocusable(false);
                    ed_supervsr_name.setEnabled(false);
                }
                else {
                    ed_supervsr_name.setText("");
                    ed_supervsr_name.setFocusableInTouchMode(false);
                    ed_supervsr_name.setFocusable(false);
                    ed_supervsr_name.setEnabled(false);
                }

                if(plantingLineModel.sowing_date_male != null && !plantingLineModel.sowing_date_male.equalsIgnoreCase("")){
                    ed_sowing_male.setText(plantingLineModel.sowing_date_male);
                    ed_sowing_male.setFocusableInTouchMode(false);
                    ed_sowing_male.clearFocus();
                    ed_sowing_male.setFocusable(false);
                    ed_sowing_male.setEnabled(false);
                }
                else {
                    ed_sowing_male.setText("");
                    ed_sowing_male.setFocusableInTouchMode(false);
                    ed_sowing_male.setFocusable(false);
                    ed_sowing_male.setEnabled(false);
                }

                if(plantingLineModel.parent_male_lot != null && !plantingLineModel.parent_male_lot.equalsIgnoreCase("")){
                    ac_parent_male_lot.setText(plantingLineModel.parent_male_lot);
                    ac_parent_male_lot.setDropDownHeight(0);
                    ac_parent_male_lot.setFocusableInTouchMode(false);
                    ac_parent_male_lot.clearFocus();
                    ac_parent_male_lot.setFocusable(false);
                    ac_parent_male_lot.setEnabled(false);
                }
                else {
                    ac_parent_male_lot.setText("");
                    ac_parent_male_lot.setDropDownHeight(0);
                    ac_parent_male_lot.setFocusableInTouchMode(false);
                    ac_parent_male_lot.setFocusable(false);
                    ac_parent_male_lot.setEnabled(false);
                }

                if(plantingLineModel.parent_male != null && !plantingLineModel.parent_male.equalsIgnoreCase("")){
                    ed_parent_male.setText(plantingLineModel.parent_male);
                    ed_parent_male.setFocusableInTouchMode(false);
                    ed_parent_male.clearFocus();
                    ed_parent_male.setFocusable(false);
                    ed_parent_male.setEnabled(false);
                }
                else {
                    ed_parent_male.setText("");
                    ed_parent_male.setFocusableInTouchMode(false);
                    ed_parent_male.setFocusable(false);
                    ed_parent_male.setEnabled(false);
                }

                if(plantingLineModel.quantity_male != null && !plantingLineModel.quantity_male.equalsIgnoreCase("")){
                    ed_male_qty.setText(plantingLineModel.quantity_male);
                    ed_male_qty.setFocusableInTouchMode(false);
                    ed_male_qty.clearFocus();
                    ed_male_qty.setFocusable(false);
                    ed_male_qty.setEnabled(false);
                }
                else {
                    ed_male_qty.setText("");
                    ed_male_qty.setFocusableInTouchMode(false);
                    ed_male_qty.setFocusable(false);
                    ed_male_qty.setEnabled(false);
                }
                //todo

                if(plantingLineModel.sowing_date_female != null && !plantingLineModel.sowing_date_female.equalsIgnoreCase("")){
                    ed_sowing_female.setText(plantingLineModel.sowing_date_female);
                    ed_sowing_female.setFocusableInTouchMode(false);
                    ed_sowing_female.clearFocus();
                    ed_sowing_female.setFocusable(false);
                    ed_sowing_female.setEnabled(false);
                }
                else {
                    ed_sowing_female.setText("");
                    ed_sowing_female.setFocusableInTouchMode(false);
                    ed_sowing_female.setFocusable(false);
                    ed_sowing_female.setEnabled(false);
                }

                if(plantingLineModel.parent_female_lot != null && !plantingLineModel.parent_female_lot.equalsIgnoreCase("")){
                    ac_parent_female_lot.setText(plantingLineModel.parent_female_lot);
                    ac_parent_female_lot.setDropDownHeight(0);
                    ac_parent_female_lot.clearFocus();
                    ac_parent_female_lot.setFocusableInTouchMode(false);
                    ac_parent_female_lot.setFocusable(false);
                    ac_parent_female_lot.setEnabled(false);
                }
                else {
                    ac_parent_female_lot.setText("");
                    ac_parent_female_lot.setDropDownHeight(0);
                    ac_parent_female_lot.setFocusableInTouchMode(false);
                    ac_parent_female_lot.setFocusable(false);
                    ac_parent_female_lot.setEnabled(false);
                }

                if(plantingLineModel.parent_female != null && !plantingLineModel.parent_female.equalsIgnoreCase("")){
                    ed_parent_female.setText(plantingLineModel.parent_female);
                    ed_parent_female.setFocusableInTouchMode(false);
                    ed_parent_female.clearFocus();
                    ed_parent_female.setFocusable(false);
                    ed_parent_female.setEnabled(false);
                }
                else {
                    ed_parent_female.setText("");
                    ed_parent_female.setFocusableInTouchMode(false);
                    ed_parent_female.setFocusable(false);
                    ed_parent_female.setEnabled(false);
                }

                if(plantingLineModel.quantity_female != null && !plantingLineModel.quantity_female.equalsIgnoreCase("")){
                    ed_female_qty.setText(plantingLineModel.quantity_female);
                    ed_female_qty.setFocusableInTouchMode(false);
                    ed_female_qty.clearFocus();
                    ed_female_qty.setFocusable(false);
                    ed_female_qty.setEnabled(false);
                }
                else {
                    ed_female_qty.setText("");
                    ed_female_qty.setFocusableInTouchMode(false);
                    ed_female_qty.setFocusable(false);
                    ed_female_qty.setEnabled(false);
                }

                if(plantingLineModel.sowing_date_other != null && !plantingLineModel.sowing_date_other.equalsIgnoreCase("")){
                    ed_sowing_date_other.setText(plantingLineModel.sowing_date_other);
                    ed_sowing_date_other.setFocusableInTouchMode(false);
                    ed_sowing_date_other.clearFocus();
                    ed_sowing_date_other.setFocusable(false);
                    ed_sowing_date_other.setEnabled(false);
                }
                else {
                    ed_sowing_date_other.setText("");
                    ed_sowing_date_other.setFocusableInTouchMode(false);
                    ed_sowing_date_other.setFocusable(false);
                    ed_sowing_date_other.setEnabled(false);
                }

                if(plantingLineModel.parent_other_lot != null && !plantingLineModel.parent_other_lot.equalsIgnoreCase("")){
                    ac_parent_other_lot.setText(plantingLineModel.parent_other_lot);
                    ac_parent_other_lot.setDropDownHeight(0);
                    ac_parent_other_lot.clearFocus();
                    ac_parent_other_lot.setFocusableInTouchMode(false);
                    ac_parent_other_lot.setFocusable(false);
                    ac_parent_other_lot.setEnabled(false);
                }
                else {
                    ac_parent_other_lot.setText("");
                    ac_parent_other_lot.setDropDownHeight(0);
                    ac_parent_other_lot.setFocusableInTouchMode(false);
                    ac_parent_other_lot.setFocusable(false);
                    ac_parent_other_lot.setEnabled(false);
                }

                if(plantingLineModel.revised_yield_raw != null && !plantingLineModel.revised_yield_raw.equalsIgnoreCase("")){
                    revised_yield.setText(plantingLineModel.revised_yield_raw);
                    revised_yield.setFocusableInTouchMode(false);
                    revised_yield.clearFocus();
                    revised_yield.setFocusable(false);
                    revised_yield.setEnabled(false);
                }
                else {
                    revised_yield.setText("");
                    revised_yield.setFocusableInTouchMode(false);
                    revised_yield.setFocusable(false);
                    revised_yield.setEnabled(false);
                }

                if(plantingLineModel.quantity_other != null && !plantingLineModel.quantity_other.equalsIgnoreCase("")){
                    ed_other_qty.setText(plantingLineModel.quantity_other);
                    ed_other_qty.setFocusableInTouchMode(false);
                    ed_other_qty.clearFocus();
                    ed_other_qty.setFocusable(false);
                    ed_other_qty.setEnabled(false);
                }
                else {
                    ed_other_qty.setText("");
                    ed_other_qty.setFocusableInTouchMode(false);
                    ed_other_qty.setFocusable(false);
                    ed_other_qty.setEnabled(false);
                }

            }
        }

    }

    private Hybrid_Item_Table hybrid_item_table_grp_code = null;

    private void getProductGrpCode(String child_seed_type, String crop_code) {

        if (child_seed_type != null && !child_seed_type.equalsIgnoreCase("") && crop_code != null && !crop_code.equalsIgnoreCase("")) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
                hybrid_item_table_grp_code = hybridItemMasterDao.getDocProductGrpCode(sessionManagement.getUserEmail().toLowerCase(), crop_code, child_seed_type);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
                if (hybrid_item_table_grp_code != null) {
                    item_prod_grp_cd.setText(hybrid_item_table_grp_code.getItem_Product_Group_Code());
                }
            }
        }
    }

    private void insertPlantingLine(PlantingHeaderModel.PlantingLine plantingLine, Dialog dialog) {
        String jsonString = new Gson().toJson(plantingLine);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<PlantingHeaderModel.PlantingLine>> call = mAPIService.insertPlantingLine(asJsonObject);
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            // ArrayList<PlantingHeaderModel.PlantingLine> plantingList = new ArrayList<>();
            call.enqueue(new Callback<List<PlantingHeaderModel.PlantingLine>>() {
                @Override
                public void onResponse(Call<List<PlantingHeaderModel.PlantingLine>> call, Response<List<PlantingHeaderModel.PlantingLine>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<PlantingHeaderModel.PlantingLine> insertLine_list = response.body();
                            if (insertLine_list != null && insertLine_list.size() > 0 && insertLine_list.get(0).condition) {
                                plantingLineTableList = insertLine_list;
                               /* plantingLine.send_to_server_line = 1;
                                plantingLine.code = insertLine_list.get(0).planting_code;
                                plantingLine.created_on = insertLine_list.get(0).created_on;*/
                                setLineAdapter();
                                bindPlantingHeaderData();
                                dialog.dismiss();
                                // plantingList.add(plantingLine);
                                // bindInsertLineData(dialog);
                                Toast.makeText(getActivity(), insertLine_list.get(0).message, Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), insertLine_list.size() > 0 ? insertLine_list.get(0).message : "Api not respoding.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + "Error (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Location_master", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<PlantingHeaderModel.PlantingLine>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Location_master", getActivity());
                }
            });
        } else {
            ArrayList<PlantingHeaderModel.PlantingLine> plantingList = new ArrayList<>();
            plantingLine.send_to_server_line = 0;
            plantingList.add(plantingLine);
            bindInsertLineData(plantingList, dialog);
        }
    }

    private void bindInsertLineData(List<PlantingHeaderModel.PlantingLine> insertLine_list, Dialog dialog) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineTable plantingLineTable = new PlantingLineTable();
            PlantingLineDao plantingLineDao = pristineDatabase.plantingLineDao();
            for (int i = 0; i < insertLine_list.size(); i++) {
                plantingLineTable.setCode(insertLine_list.get(i).code);
                plantingLineTable.setLine_no(insertLine_list.get(i).line_no);
                plantingLineTable.setDoc_no(insertLine_list.get(i).doc_no);
                plantingLineTable.setParent_male(insertLine_list.get(i).parent_male);
                plantingLineTable.setParent_male_lot(insertLine_list.get(i).parent_male_lot);
                plantingLineTable.setParent_female(insertLine_list.get(i).parent_female);
                plantingLineTable.setParent_female_lot(insertLine_list.get(i).parent_female_lot);
                plantingLineTable.setReciept_no_male(insertLine_list.get(i).reciept_no_male);
                plantingLineTable.setReciept_no_female(insertLine_list.get(i).reciept_no_female);
                plantingLineTable.setGrower_owner_code(insertLine_list.get(i).grower_owner_code);
                plantingLineTable.setGrower_land_owner_name(insertLine_list.get(i).grower_land_owner_name);
                plantingLineTable.setSupervisor_name(insertLine_list.get(i).supervisor_name);
                plantingLineTable.setCrop_code(insertLine_list.get(i).crop_code);
                plantingLineTable.setVariety_code(insertLine_list.get(i).variety_code);
                plantingLineTable.setItem_product_group_code(insertLine_list.get(i).item_product_group_code);
                plantingLineTable.setItem_class_of_seeds(insertLine_list.get(i).item_class_of_seeds);
                plantingLineTable.setItem_crop_type(insertLine_list.get(i).item_crop_type);
                plantingLineTable.setItem_name(insertLine_list.get(i).item_name);
                plantingLineTable.setExpected_yield(insertLine_list.get(i).expected_yield);
                plantingLineTable.setRevised_yield_raw(insertLine_list.get(i).revised_yield_raw);
                plantingLineTable.setLand_lease(insertLine_list.get(i).land_lease);
                plantingLineTable.setUnit_of_measure_code(insertLine_list.get(i).unit_of_measure_code);
                plantingLineTable.setSowing_date_male(insertLine_list.get(i).sowing_date_male);
                plantingLineTable.setSowing_date_female(insertLine_list.get(i).sowing_date_female);

                plantingLineTable.setSowing_date_other(insertLine_list.get(i).sowing_date_other);
                plantingLineTable.setSowing_area_In_acres(insertLine_list.get(i).sowing_area_In_acres);
                plantingLineTable.setVillage_name(insertLine_list.get(i).village_name);

                plantingLineTable.setTaluka(insertLine_list.get(i).taluka);
                plantingLineTable.setState(insertLine_list.get(i).state);
                plantingLineTable.setCity(insertLine_list.get(i).city);

                plantingLineTable.setReciept_no_other(insertLine_list.get(i).reciept_no_other);
                plantingLineTable.setQuantity_female(insertLine_list.get(i).quantity_female);
                plantingLineTable.setQuantity_male(insertLine_list.get(i).quantity_male);
                plantingLineTable.setQuantity_other(insertLine_list.get(i).quantity_other);
                plantingLineTable.setParent_other(insertLine_list.get(i).parent_other);
                plantingLineTable.setParent_other_lot(insertLine_list.get(i).parent_other_lot);

                plantingLineTable.setAddress(insertLine_list.get(i).address);
                plantingLineTable.setInspectionI("0");
                plantingLineTable.setInspectionII("0");
                plantingLineTable.setInspectionIII("0");
                plantingLineTable.setInspectionIV("0");
                plantingLineTable.setInspectionV("0");
                plantingLineTable.setInspectionVI("0");
                plantingLineTable.setInspectionVII("0");
                plantingLineTable.setInspectionVIII("0");
                plantingLineTable.setInspectionIX("0");
                plantingLineTable.setInspectionQC("0");
                plantingLineTable.setCreated_by(sessionManagement.getUserEmail());
                plantingLineTable.setPoduction_lot_no(insertLine_list.get(i).production_lot_no);
                plantingLineTable.setDelete_planting_line(insertLine_list.get(i).delete_planting_line);

               /* if(plantingLineDao.isDataExist(plantingLineTable.getCode())>0){
                    plantingLineDao.update(plantingLineTable);
                }*/
                //  else {
                plantingLineDao.insert(plantingLineTable);
                //  }
            }
            //plantingLineTableList = plantingLineDao.getAllDatabyPlantingCode(plantingLineTable.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            dialog.dismiss();
            // plantingLineAdapter = new PlantingLineAdapter(getActivity(), plantingLineTableList);
            listview.setAdapter(plantingLineAdapter);
            bindPlantingHeaderData();
        }
    }

    private void getFarmerAndGrower_master(MaterialProgressBar content_loading, String action_flag, String village_name) {
        content_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList(village_name);
        call.enqueue(new Callback<DispatchFarmerModel>() {
            @Override
            public void onResponse(Call<DispatchFarmerModel> call, Response<DispatchFarmerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        DispatchFarmerModel farmerMasterModel = response.body();
                        if (farmerMasterModel != null && farmerMasterModel.condition) {
                            List<DispatchFarmerModel.Data> farmermaster_list = farmerMasterModel.data;
                            if (farmermaster_list != null && farmermaster_list.size() > 0) {

                                Collections.sort(farmermaster_list, new Comparator<DispatchFarmerModel.Data>() {
                                    @Override
                                    public int compare(DispatchFarmerModel.Data o1, DispatchFarmerModel.Data o2) {
                                        return o1.Village.compareTo(o2.Village);
                                    }
                                });

                                for (int i = 0; i < farmermaster_list.size(); i++) {
                                    for (int j = i + 1; j < farmermaster_list.size(); j++) {
                                        if (farmermaster_list.get(i).Village.equals(farmermaster_list.get(j).Village)) {
                                            farmermaster_list.remove(j);
                                            j--;
                                        }
                                    }
                                }

                                if (action_flag.equalsIgnoreCase("")) {
                                    village_list = farmermaster_list;
                                    FarmerVillageAdapter village_adapter = new FarmerVillageAdapter(getActivity(), R.layout.android_item_view, village_list);
                                    ed_vill_name.setThreshold(1);
                                    ed_vill_name.setAdapter(village_adapter);
                                } else if (action_flag.equalsIgnoreCase("Village")) {
                                    farmer_grower_list = farmermaster_list;
                                    FarmerListAdapter roleMasterAdapter = new FarmerListAdapter(getActivity(), R.layout.android_item_view, farmer_grower_list);
                                    ac_grwer_name.setThreshold(1);
                                    ac_grwer_name.setAdapter(roleMasterAdapter);
                                }
                            }

                        } else {
                            content_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        content_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    content_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", getActivity());
                } finally {
                    content_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DispatchFarmerModel> call, Throwable t) {
                content_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
            }
        });
    }


    private void getUnitOfMeasureData(MaterialProgressBar loading_content) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitOfMeasureModel> call = mAPIService.getUnitsOfMeasureList();
        call.enqueue(new Callback<UnitOfMeasureModel>() {
            @Override
            public void onResponse(Call<UnitOfMeasureModel> call, Response<UnitOfMeasureModel> response) {
                if (response.isSuccessful()) {
                    UnitOfMeasureModel unitOfMeasureModelList = response.body();
                    if (unitOfMeasureModelList != null) {
                        List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList = unitOfMeasureModelList.data;
                        if (uomList != null && uomList.size() > 0) {
                            uomTableList = uomList;
                            UnitOfMeasureAdapter uomAdapter = new UnitOfMeasureAdapter(getActivity(), R.layout.android_item_view, uomTableList);
                            unit_of_msrd_cd.setAdapter(uomAdapter);

                        } else {
                            loading_content.setVisibility(View.GONE);
                            unit_of_msrd_cd.setAdapter(null);
                            Toast.makeText(getActivity(), "Unit of measure record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UnitOfMeasureModel> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_uom_master", getActivity());
            }
        });
    }

    private void getUserLocation() {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<UserLocationModel>>() {
            @Override
            public void onResponse(Call<List<UserLocationModel>> call, Response<List<UserLocationModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            locationMasterTableList = user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, locationMasterTableList);
                            ac_select_loc.setAdapter(locationMasterAdapter);
                        } else {
                            ac_select_loc.setAdapter(null);
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "user_location_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UserLocationModel>> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "user_location_master", getActivity());
            }
        });
    }

    private void deleteHeader(String code) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        // PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            // PlantingHeaderDao plantingHeaderDao = pristineDatabase.plantingHeaderDao();
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.deletePlantingHeader(code);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> deleteResponseList = response.body();
                                if (deleteResponseList != null && deleteResponseList.size() > 0 && deleteResponseList.get(0).condition) {
                                    // int remove = plantingHeaderDao.deleteRecordHedaer(plantingDetailHeaderTableList.get(0).getCode());
                                    // if (remove > 0) {
                                    //  plantingDetailHeaderTableList.clear();
                                    plantingHeaderModel = null;
                                    bindPlantingHeaderData();
                                    clearPlantingHeaderData();
                                    Toast.makeText(getActivity(), "delete data successful!", Toast.LENGTH_SHORT).show();
                                }
                               /* } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), deleteResponseList.size() > 0 ? "No data found" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                                }*/
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete Planting", getActivity());
                        } finally {
                            progressDialogLoading.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete Planting", getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please wait for interrnet connection.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }*/
    }

    private void clearPlantingHeaderData() {
        tv_header_no.setText("");
        ac_select_loc.setText("");
        edit_date.setText("");
        ed_date_of_harvest.setText("");
        dropdown_sesion_code.setText("");
        ac_type.setText("");
        ac_stage_code.setText("");
        drop_down_parent.setText("");
        ac_doc_sub_type.setText("");
        ac_organizer_name.setText("");
    }

    private void deletePlantingLine(String code, String planting_line_no, int pos) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.deletePlantingLines(code, planting_line_no);
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<ResponseModel> deleteResponseList = response.body();
                            if (deleteResponseList != null && deleteResponseList.size() > 0 && deleteResponseList.get(0).condition) {
                                //  PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
                                // PlantingLineDao plantingLineDao = pristineDatabase.plantingLineDao();
                                // try {
                                //   int remove = plantingLineDao.deletePlantingLine(code, planting_line_no);
                                // int remove=  deleteLineFromLocal(pos,pristineDatabase);
                                //  if (remove > 0) {
                                plantingLineTableList.remove(pos);
                                plantingLineAdapter.notifyDataSetChanged();
                                setHeaderDetailData();
                                bindPlantingHeaderData();
                                // bindInsertLineData(new ArrayList<>(), null);
                                Toast.makeText(getActivity(), "delete line successful!", Toast.LENGTH_SHORT).show();
                                //  }
                              /*  } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    pristineDatabase.close();
                                    pristineDatabase.destroyInstance();
                                }*/

                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), deleteResponseList.size() > 0 ? "No data found" : "Api not responding.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception_database", e.getMessage() + "cause");
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_line_Planting", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_line_Planting", getActivity());
                }
            });
        } else {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            PlantingLineDao plantingLineDao = pristineDatabase.plantingLineDao();
            try {
                PlantingHeaderModel.PlantingLine plantingLineModel = new PlantingHeaderModel().new PlantingLine();
                ArrayList<PlantingHeaderModel.PlantingLine> plantingLineArrayList = new ArrayList<>();
                int remove = plantingLineDao.deletePlantingLine(code, planting_line_no);
                // int remove = deleteLineFromLocal(pos,pristineDatabase);
                if (remove > 0) {
                    plantingLineTableList.remove(planting_line_no);
                    // plantingLineTableList.get(pos).setDelete_planting_line();=1;
                    plantingLineArrayList.add(plantingLineModel);
                    Toast.makeText(getActivity(), "delete line successful!", Toast.LENGTH_SHORT).show();
                    bindInsertLineData(plantingLineArrayList, null);
                }

            } catch (Exception e) {
                e.getLocalizedMessage();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void completePlanting(String planting_code) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        // PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.completePlantingHeaderLines(planting_code);//planting_code
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> completePlantingList = response.body();
                                if (completePlantingList != null && completePlantingList.size() > 0 && completePlantingList.get(0).condition) {
                                    plantingHeaderModel.status = 1;
                                    complete_header.setVisibility(View.GONE);
                                    chip_add_line_planting.setVisibility(View.GONE);
                                    delete_header_chip.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), completePlantingList.get(0).message, Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), completePlantingList.size() > 0 ? completePlantingList.get(0).message : "Api Error !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception_database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_line_Planting", getActivity());
                        } finally {
                            progressDialogLoading.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_line_Planting", getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please wait for internet connection. ", Toast.LENGTH_SHORT).show();
                //  dispatchCompleteOnlineOffline("Offline");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           /* pristineDatabase.close();
            pristineDatabase.destroyInstance();*/
        }
    }

    private void dispatchCompleteOnlineOffline(String onlineOffline) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingHeaderDao plantingHeaderDao = pristineDatabase.plantingHeaderDao();
            if (onlineOffline.equalsIgnoreCase("Online")) {
                plantingHeaderDao.updateCompletePlantingStatus(1, 0, plantingLineTableList.get(0).code);
            } else {
                plantingHeaderDao.updateCompletePlantingStatus(0, 1, plantingLineTableList.get(0).code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            getPlantingHeaderData();
            bindPlantingHeaderData();
        }
    }

    private void getPlantingLotListMale(MaterialProgressBar loading_content, String doc_no, String flag, String gender_flag) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData(doc_no, flag, gender_flag);
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                if (response.isSuccessful()) {
                    loading_content.setVisibility(View.GONE);
                    PlantingLotModel plantingLotModel = response.body();
                    if (plantingLotModel != null && plantingLotModel.condition) {
                        List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                        if (planting_lot_list != null && planting_lot_list.size() > 0) {
                            plantingMaleList.clear();
                            plantingMaleList.addAll(planting_lot_list);
                            if (plantingMaleList != null && plantingMaleList.size() > 0) {
                                PlantingLotMFOAdapter plantingLotMFOAdapter1 = new PlantingLotMFOAdapter(getActivity(), R.layout.android_item_view, plantingMaleList);
                                ac_parent_male_lot.setAdapter(plantingLotMFOAdapter1);
                            } else {
                                ac_parent_male_lot.setAdapter(null);
                            }
                        }
                    } else {
                        ac_parent_male_lot.setAdapter(null);
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


    private void getPlantingLotListFemale(MaterialProgressBar loading_content,String doc_no, String flag,  String gender_flag) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData(doc_no, flag, gender_flag);
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                if (response.isSuccessful()) {
                    loading_content.setVisibility(View.GONE);
                    PlantingLotModel plantingLotModel = response.body();
                    if (plantingLotModel != null && plantingLotModel.condition) {
                        List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                        if (planting_lot_list != null && planting_lot_list.size() > 0) {
                            plantingLotFemaleList.clear();
                            plantingLotFemaleList.addAll(planting_lot_list);
                            if (plantingLotFemaleList != null && plantingLotFemaleList.size() > 0) {
                                PlantingLotMFOAdapter plantingLotMFOAdapter1 = new PlantingLotMFOAdapter(getActivity(), R.layout.android_item_view, plantingLotFemaleList);
                                ac_parent_female_lot.setAdapter(plantingLotMFOAdapter1);
                            } else {
                                ac_parent_female_lot.setAdapter(null);
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


    private void getPlantingLotListOther(MaterialProgressBar loading_content, String flag, String doc_no, String gender_flag) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData(doc_no, flag, gender_flag);
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                if (response.isSuccessful()) {
                    loading_content.setVisibility(View.GONE);
                    PlantingLotModel plantingLotModel = response.body();
                    if (plantingLotModel != null && plantingLotModel.condition) {
                        List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                        if (planting_lot_list != null && planting_lot_list.size() > 0) {
                            //plantingLotOtherList=planting_lot_list;
                            plantingLotOtherList.clear();
                            plantingLotOtherList.addAll(planting_lot_list);
                            if (plantingLotOtherList != null && plantingLotOtherList.size() > 0) {
                                PlantingLotMFOAdapter plantingLotMFOAdapter1 = new PlantingLotMFOAdapter(getActivity(), R.layout.android_item_view, plantingLotOtherList);
                                ac_parent_other_lot.setAdapter(plantingLotMFOAdapter1);
                            } else {
                                ac_parent_other_lot.setAdapter(null);
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


    private void attachFragment() {
        Fragment currentFragment = getParentFragmentManager().findFragmentByTag("add_planting_header_fragment");
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    private void runbackgraundTask(String child_seed_type, String crop_code) {
        new BackgroundTask(getActivity()) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());

            @Override
            public void doInBackground() {
                try {
                    ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
                    zoneMasterTableList = zoneMaterDao.getAllData();

                    if (child_seed_type != null && !child_seed_type.equalsIgnoreCase("") && crop_code != null && !crop_code.equalsIgnoreCase("")) {
                        HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
                        hybrid_item_table_grp_code = hybridItemMasterDao.getDocProductGrpCode(sessionManagement.getUserEmail().toLowerCase(), crop_code, child_seed_type);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pristineDatabase.close();
                    pristineDatabase.destroyInstance();
                }
            }

            @Override
            public void onPostExecute() {
                //hear is result part same
                //UI Thread(update your UI widget)
                if (zoneMasterTableList != null && zoneMasterTableList.size() > 0) {
                    ZoneMasterAdapter zoneMasterAdapter = new ZoneMasterAdapter(getActivity(), R.layout.android_item_view, zoneMasterTableList);
                    ac_zone_.setAdapter(zoneMasterAdapter);
                }

                if (hybrid_item_table_grp_code != null) {
                    item_prod_grp_cd.setText(hybrid_item_table_grp_code.getItem_Product_Group_Code());
                }

            }
        }.execute();
    }

    @Override
    public void onItemClick(int pos) {
        frame_layout_org_list.setVisibility(View.GONE);
        organizer_master_table = organizer_master_tableList.get(pos);
        if (organizer_master_table != null) {
            ac_organizer_name.setText(organizer_master_tableList.get(pos).Name);
            frame_layout_org_list.setVisibility(View.GONE);
        } else {
            frame_layout_org_list.setVisibility(View.VISIBLE);
        }
    }
}




