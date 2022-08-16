package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.SeedlingInspection;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.math.BigDecimal;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.SeedlingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.Seedling_InspectionDao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.CustomTimePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.SeedLing_InspectionLineModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Seedling_InspectionFragment extends Fragment {
    private String scheduler_no = "", production_lot_no = "";
    private SessionManagement sessionManagement;
    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;
    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error, tv_item_name;
    private TextInputLayout tv_iso_dis_show, tv_iso_time_show, tv_iso_grain_show,ac_pld_reason_layout;
    private Button bt_complete, btn_save_record;
    private AutoCompleteTextView ac_vigore_, ac_crop_codn, ac_diseases, ac_pest, ac_pld_reason,ed_isolation,ac_pest_insfestation,ac_diseases_insfestation;
    private TextInputEditText ed_recommended_date, ed_actual_date, ed_isolation_time, ed_iso_distance,
            ed_Remarks, ed_deasease_remark, ed_date_of_insp, ed_seed_setting, ed_grain_remark,
            seed_setting_per, ed_receipt_male, ed_receipt_female, ed_receipt_other,ac_crop_stage,ed_standing_acres,ed_pld_acres,ed_net_acres;
    private ImageView setImageView;
    private FrameLayout close_dilog_bt;

    private Chip clear_image_btn;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip add_image_btn;
    private String selected_file_path = "";
    private List<PlantingLineLotListTable> plantingMaleList = null;
    private LinearLayout image_layout;
    private PlantingLineLotListTable plantingLostParentTable = null;
    private List<String> imageEncodList = new ArrayList<>();
    private String iso_empty_value="";

    private List<GerminationInspection1_Table> germination_inspection_table = new ArrayList<>();/////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            scheduler_header_table = new Gson().fromJson(bundle.getString("header_detail", ""), Scheduler_Header_Table.class);
            schedulerInspectionLineTable = new Gson().fromJson(bundle.getString("scheduler_line_detail", ""), SchedulerInspectionLineTable.class);
            scheduler_no = bundle.getString("scheduler_no", "");
            production_lot_no = bundle.getString("production_lot_no", "");

            Log.e("header_detail", new Gson().toJson(scheduler_header_table));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_lot_wise_seedling_inspection_field_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        intiView(view);
        insertSeedlingInspectionLine(new ArrayList<>());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void intiView(View view) {
        tv_date = view.findViewById(R.id.tv_date);
        tv_season = view.findViewById(R.id.tv_season);
        tv_season_name = view.findViewById(R.id.season_name);
        tv_prod_cent_name = view.findViewById(R.id.prod_cent_name);
        tv_prod_center = view.findViewById(R.id.prod_center);
        tv_farmer_name = view.findViewById(R.id.farmer_name);
        village_address = view.findViewById(R.id.village_address);
        tv_prod_lot_no = view.findViewById(R.id.prod_lot_no);
        tv_crop_code = view.findViewById(R.id.crop_code);
        tv_varity_code = view.findViewById(R.id.variety_code);
        tv_sd_male = view.findViewById(R.id.tv_sd_male);
        tv_sd_female = view.findViewById(R.id.tv_sd_female);
        tv_org_name = view.findViewById(R.id.org_name);
        tv_org_code = view.findViewById(R.id.org_code);
        tv_item_prodGrp_code = view.findViewById(R.id.item_prodGrp_code);
        tv_item_class_of_seed = view.findViewById(R.id.item_class_of_seed);
        tv_crop_type = view.findViewById(R.id.crop_type);
        image_layout = view.findViewById(R.id.image_layout);
        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        ac_pest = view.findViewById(R.id.ac_pest);
        ac_diseases = view.findViewById(R.id.ac_diseases);
        ac_pld_reason_layout = view.findViewById(R.id.ac_pld_reason_layout);
        ac_pld_reason = view.findViewById(R.id.ac_pld_reason);
        ac_pest_insfestation = view.findViewById(R.id.ac_pest_insfestation);
        ac_diseases_insfestation = view.findViewById(R.id.ac_diseases_insfestation);
        ed_recommended_date = view.findViewById(R.id.ed_recommended_date);
        ed_actual_date = view.findViewById(R.id.ed_actual_date);
        ed_isolation = view.findViewById(R.id.ed_isolation);
        ed_isolation_time = view.findViewById(R.id.ed_iso_time);
        ed_iso_distance = view.findViewById(R.id.ed_iso_distance);
        ed_Remarks = view.findViewById(R.id.ed_pestRemark);
        ed_deasease_remark = view.findViewById(R.id.ed_deasease_remark);
        ac_vigore_ = view.findViewById(R.id.ac_vigore);

        ac_crop_codn = view.findViewById(R.id.ac_crop_codn);
        ac_crop_stage = view.findViewById(R.id.ac_crop_stage);
        bt_complete = view.findViewById(R.id.complete_btn);
        btn_save_record = view.findViewById(R.id.save_record);
        posting_error = view.findViewById(R.id.posting_error);
        ed_date_of_insp = view.findViewById(R.id.date_of_inspection);
        tv_item_name = view.findViewById(R.id.item_name);
        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        seed_setting_per = view.findViewById(R.id.seed_setting_per);
        setImageView = view.findViewById(R.id.image_view);
        add_image_btn = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        tv_iso_dis_show = view.findViewById(R.id.iso_dis_show);
        tv_iso_time_show = view.findViewById(R.id.iso_time_show);
        tv_iso_grain_show = view.findViewById(R.id.iso_grain_show);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);
        ed_grain_remark = view.findViewById(R.id.grain_remark);
        ed_standing_acres = view.findViewById(R.id.ed_standing_acres);
        ed_pld_acres = view.findViewById(R.id.ed_pld_acres);
        ed_net_acres = view.findViewById(R.id.ed_net_acres);
        //todo for net acres................
        ed_pld_acres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equalsIgnoreCase("")){
                    try {
                        double i=Double.parseDouble(ed_standing_acres.getText().toString());
                        double i1=Double.parseDouble(ed_pld_acres.getText().toString());
                        ed_net_acres.setText(String.valueOf(i-i1));
                        if(!ed_pld_acres.getText().toString().equalsIgnoreCase("") && i1>0)
                            ac_pld_reason_layout.setVisibility(View.VISIBLE);

                        else
                            ac_pld_reason_layout.setVisibility(View.GONE);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
              else {
                  MDToast.makeText(getActivity(),"pld acres can't blank or greater than standing acres",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
              }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);
        List<String> isoList = Arrays.asList(CommonData.isolation_);
        List<String> pest = Arrays.asList(CommonData.pest);
        List<String> deasease = Arrays.asList(CommonData.desease);
        List<String> pld = Arrays.asList(CommonData.pld);
        List<String> pest_inflation = Arrays.asList(CommonData.pest_infalation);
        List<String> desease_inflation = Arrays.asList(CommonData.desease_infalation);
        List<String> vigore = Arrays.asList(CommonData.vigor);

        ItemAdapter cropAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_codn.setAdapter(cropAdapter);
        ItemAdapter vigore_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, vigore);
        ac_vigore_.setAdapter(vigore_adapter);
        ac_crop_stage.setText("Seedling");
        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pest);
        ac_pest.setAdapter(pest_adapter);
        ItemAdapter desease_adpter = new ItemAdapter(getActivity(), R.layout.android_item_view, deasease);
        ac_diseases.setAdapter(desease_adpter);
        ItemAdapter pld_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pld);
        ac_pld_reason.setAdapter(pld_adapter);

        ItemAdapter ac_pest_inflation_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pest_inflation);
        ac_pest_insfestation.setAdapter(ac_pest_inflation_adapter);

        ItemAdapter ac_desease_inflation_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, desease_inflation);
        ac_diseases_insfestation.setAdapter(ac_desease_inflation_adapter);

        //set header data....
        tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
        tv_season.setText(scheduler_header_table.getSeason()+"("+scheduler_header_table.getSeason_name()+")");
        tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre()+"("+scheduler_header_table.getProduction_centre_name()+")");

        // set scheduler line data....
        tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name()+"("+schedulerInspectionLineTable.getGrower_owner()+")");
        village_address.setText(schedulerInspectionLineTable.getGrower_village() + "," +
                schedulerInspectionLineTable.getGrower_district() + ","
                + schedulerInspectionLineTable.getGrower_city() + ","
                + schedulerInspectionLineTable.getGrower_taluka_mandal());

        tv_prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
        tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
        try{
            PlantingLineLotListDao plantingLineLotListDao=pristineDatabase.plantingLineLotListDao();
            if(schedulerInspectionLineTable!=null){
                PlantingLineLotListTable plantingLineLotListTable=plantingLineLotListDao.getVaityCodeAliasName(production_lot_no,schedulerInspectionLineTable.getVariety_code());
                if(plantingLineLotListTable!=null) {
                    tv_varity_code.setText(plantingLineLotListTable.getAlias_Name());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }


        tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
        tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_female()));
        tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code()+"("+schedulerInspectionLineTable.getOrganizer_name()+")");
        tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
        tv_crop_type.setText(schedulerInspectionLineTable.getItem_crop_type());
        tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());

        ed_recommended_date.setText(getFemaleSowingDate());

        ed_standing_acres.setText(getStandingAcres());

        ed_actual_date.setOnTouchListener((v, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            return true;
        });
        ed_date_of_insp.setOnTouchListener((v, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });

        ed_isolation_time.setOnTouchListener((v, motionEvent) -> {
            new CustomTimePicker(getActivity()).showDialog(ed_isolation_time);
            return true;
        });

        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, isoList);
        ed_isolation.setAdapter(iso_adapter);

        ed_isolation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                     iso_empty_value="";
                    ed_isolation.setText(iso_empty_value);
                    tv_iso_dis_show.setVisibility(View.GONE);
                    tv_iso_grain_show.setVisibility(View.GONE);
                    tv_iso_time_show.setVisibility(View.GONE);
                  }
                if (position == 1) {
                    tv_iso_dis_show.setVisibility(View.VISIBLE);
                    tv_iso_grain_show.setVisibility(View.GONE);
                    tv_iso_time_show.setVisibility(View.GONE);

                } else if (position == 2) {
                    tv_iso_grain_show.setVisibility(View.VISIBLE);
                    tv_iso_dis_show.setVisibility(View.GONE);
                    tv_iso_time_show.setVisibility(View.GONE);

                } else if (position == 3) {
                    tv_iso_time_show.setVisibility(View.VISIBLE);
                    tv_iso_grain_show.setVisibility(View.GONE);
                    tv_iso_dis_show.setVisibility(View.GONE);
                }
            }
        });


        bt_complete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    completeSeedling();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        btn_save_record.setOnClickListener(v -> {
            insertRecord();
        });

        add_image_btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }

    private List<SeedlingInspectionTable> seedlingInspectionTable = new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertSeedlingInspectionLine(List<SeedLing_InspectionLineModel> schedule_scan_lot_list) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            Seedling_InspectionDao seedling_inspection_dao = db.seedling_inspectionDao();
            if (schedule_scan_lot_list != null && schedule_scan_lot_list.size() > 0) {
                for (int i = 0; i < schedule_scan_lot_list.size(); i++) {
                    SeedlingInspectionTable seedlingInspectionTable = SeedlingInspectionTable.insertSeedlingInspection(schedule_scan_lot_list.get(i));
                    if (seedling_inspection_dao.isDataExist(seedlingInspectionTable.getProduction_lot_no())>0) {
                        seedling_inspection_dao.update(seedlingInspectionTable);
                    } else {
                        seedling_inspection_dao.insert(seedlingInspectionTable);
                    }
                }
            }
            seedlingInspectionTable = seedling_inspection_dao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
            if (seedlingInspectionTable != null) {
                if (scheduler_line_header_data.getIns2_sync_with_server() > 0 || scheduler_line_header_data.getInspection_2() > 0) {
                    if (scheduler_line_header_data.getInspection_2() > 0 && scheduler_line_header_data.getIns2_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp2_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp2_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    disableAllInputField();

                } else {
                    bt_complete.setVisibility(View.VISIBLE);
                    btn_save_record.setVisibility(View.VISIBLE);
                }
                showRefelectedFieldData();

            } else {
                btn_save_record.setVisibility(View.VISIBLE);
                if (scheduler_line_header_data.getIns2_sync_with_server() > 0 || scheduler_line_header_data.getInspection_2() > 0) {
                    if (scheduler_line_header_data.getInspection_2() > 0 && scheduler_line_header_data.getIns2_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp2_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp2_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    disableAllInputField();

                }
            }
        }
    }

    private void disableAllInputField() {
        ed_isolation.setEnabled(false);
        ed_isolation.setDropDownHeight(0);
        ed_isolation_time.setEnabled(false);
        ed_iso_distance.setEnabled(false);
        ed_Remarks.setEnabled(false);
        ed_deasease_remark.setEnabled(false);
        ac_vigore_.setEnabled(false);
        ac_vigore_.setDropDownHeight(0);
        ac_crop_codn.setEnabled(false);
        ac_crop_codn.setDropDownHeight(0);
        ac_crop_stage.setEnabled(false);
        ed_date_of_insp.setEnabled(false);
        ed_date_of_insp.setFocusable(false);
        ed_actual_date.setEnabled(false);
        ed_actual_date.setFocusable(false);
        ed_standing_acres.setEnabled(false);
        ed_standing_acres.setFocusable(false);
        ed_recommended_date.setEnabled(false);
        ed_recommended_date.setFocusable(false);
        ac_crop_codn.setFocusableInTouchMode(false);
        ed_recommended_date.setFocusableInTouchMode(false);
        ed_actual_date.setFocusableInTouchMode(false);
        ac_vigore_.setFocusableInTouchMode(false);

        ac_pest.setEnabled(false);
        ac_pest.setDropDownHeight(0);
        ac_pest.setFocusableInTouchMode(false);
        ac_pest.setFocusable(false);

        ac_diseases.setEnabled(false);
        ac_diseases.setDropDownHeight(0);
        ac_diseases.setFocusableInTouchMode(false);
        ac_diseases.setFocusable(false);

        ac_pest_insfestation.setEnabled(false);
        ac_pest_insfestation.setDropDownHeight(0);
        ac_pest_insfestation.setFocusableInTouchMode(false);
        ac_pest_insfestation.setFocusable(false);

        ac_diseases_insfestation.setEnabled(false);
        ac_diseases_insfestation.setDropDownHeight(0);
        ac_diseases_insfestation.setFocusableInTouchMode(false);
        ac_diseases_insfestation.setFocusable(false);

        disableImageBtn();

        ed_date_of_insp.setOnTouchListener(null);

        ed_recommended_date.setOnTouchListener(null);

        ed_pld_acres.setEnabled(false);
        ed_pld_acres.setFocusable(false);

        ed_net_acres.setEnabled(false);
        ed_net_acres.setFocusable(false);

        ac_pld_reason.setEnabled(false);
        ac_pld_reason.setDropDownHeight(0);
        ac_pld_reason.setFocusable(false);
        ac_pld_reason.setFocusableInTouchMode(false);

        ed_actual_date.setOnTouchListener(null);

        ac_crop_stage.setFocusable(false);
        ac_crop_codn.setFocusable(false);
    }

    private void disableImageBtn() {
        add_image_btn.setEnabled(false);
        clear_image_btn.setVisibility(View.GONE);
        add_image_btn.setChecked(false);
        add_image_btn.setChipStrokeColor(getResources().getColorStateList(R.color.gray1));
        add_image_btn.setText("Uploaded");
        add_image_btn.setTextColor(getResources().getColor(R.color.gray1));
        add_image_btn.setChipIconResource(R.drawable.default_img);
    }

    private void insertRecord() {
        SeedLing_InspectionLineModel seedLing_inspectionLineModel = new SeedLing_InspectionLineModel();
        try {
            seedLing_inspectionLineModel.scheduler_no = scheduler_no;
            seedLing_inspectionLineModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            seedLing_inspectionLineModel.production_lot_no = production_lot_no;
            seedLing_inspectionLineModel.crop_condition = ac_crop_codn.getText().toString().trim();
            seedLing_inspectionLineModel.crop_stage = ac_crop_stage.getText().toString().trim();
            seedLing_inspectionLineModel.date_of_inspection =DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            seedLing_inspectionLineModel.vigor = ac_vigore_.getText().toString().trim();
            seedLing_inspectionLineModel.pest = ac_pest.getText().toString().trim();
            seedLing_inspectionLineModel.diseases = ac_diseases.getText().toString().trim();
            seedLing_inspectionLineModel.pest_infestation_level = ac_pest_insfestation.getText().toString().trim();
            seedLing_inspectionLineModel.disease_infestation_level = ac_diseases_insfestation.getText().toString().trim();
            seedLing_inspectionLineModel.pest_remarks = ed_Remarks.getText().toString().trim();
            seedLing_inspectionLineModel.diseases_remarks = ed_deasease_remark.getText().toString().trim();
            seedLing_inspectionLineModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_recommended_date.getText().toString().trim());
            seedLing_inspectionLineModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            seedLing_inspectionLineModel.isolation = ed_isolation.getText().toString().trim();
            seedLing_inspectionLineModel.isolation_time = ed_isolation_time.getText().toString().trim();
            seedLing_inspectionLineModel.created_on = DateTimeUtilsCustome.getCurrentTime();
            seedLing_inspectionLineModel.seed_setting = ed_seed_setting.getText().toString().trim();
            seedLing_inspectionLineModel.male_reciept_no = "0";
            seedLing_inspectionLineModel.female_reciept_no = "0";
            seedLing_inspectionLineModel.other_reciept_no = "0";
            seedLing_inspectionLineModel.grain_remarks = ed_grain_remark.getText().toString().trim();
            seedLing_inspectionLineModel.standing_acres = ed_standing_acres.getText().toString().trim();
            if(!ed_pld_acres.getText().toString().trim().equalsIgnoreCase("") && ed_pld_acres.getText().toString().length()>0 ){
                seedLing_inspectionLineModel.pld_acre = ed_pld_acres.getText().toString().trim();
            }
            else {
                MDToast.makeText(getActivity(),"pld acres can't blank or greater than standing acres",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                seedLing_inspectionLineModel.pld_acre="0.0";

            }
            seedLing_inspectionLineModel.net_acre = ed_net_acres.getText().toString().trim();
            if(!ac_pld_reason.getText().toString().equalsIgnoreCase("")) {
                seedLing_inspectionLineModel.pld_reason = ac_pld_reason.getText().toString().trim();
            }
            else {
                seedLing_inspectionLineModel.pld_reason =" ";
            }

            if (!seed_setting_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(seed_setting_per.getText().toString().trim()));
                seedLing_inspectionLineModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                seedLing_inspectionLineModel.seed_setting_prcnt = "0.0";
            }

            if (!ed_iso_distance.getText().toString().trim().equalsIgnoreCase("")) {
                String iso_ddis = String.valueOf(Float.parseFloat(ed_iso_distance.getText().toString().trim()));
                seedLing_inspectionLineModel.isolation_distance = !iso_ddis.equalsIgnoreCase("") ? iso_ddis : "0.0";
            } else {
                seedLing_inspectionLineModel.isolation_distance = "0.0";
            }

            if(ed_isolation.getText().toString().equalsIgnoreCase("-")){
                ed_isolation.setText(iso_empty_value);
            }

            String base64_image = StaticMethods.convertBase64(selected_file_path);
            seedLing_inspectionLineModel.attachment = base64_image != null ? base64_image : "";
        }catch (Exception e){
            e.printStackTrace();
        }
        if(ed_Remarks.getText().toString().length()>120){
            MDToast.makeText(getActivity(),"Pest  Remark value should be less than 120 characters in length ",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
        else if(ed_deasease_remark.getText().toString().length()>120 ){
            MDToast.makeText(getActivity(),"Disease Remark value should be less than 120 characters in length ",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
        else
        if(ac_crop_codn.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please enter crop condn.",Toast.LENGTH_SHORT).show();
        }
        else if(ac_crop_stage.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please enter crop stage.",Toast.LENGTH_SHORT).show();
        }
        else if(ed_isolation.getText().toString().equalsIgnoreCase("-")){
            Toast.makeText(getActivity(),"invalid input - !",Toast.LENGTH_SHORT).show();
        }
        else {
            String jsonString = new Gson().toJson(seedLing_inspectionLineModel);
            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            InsertSeedling(seedLing_inspectionLineModel, asJsonObject);
        }

    }

    private void showRefelectedFieldData() {
        try {
            if (seedlingInspectionTable != null && seedlingInspectionTable.size() > 0) {
                ed_isolation.setText(seedlingInspectionTable.get(0).getIsolation());
                if(seedlingInspectionTable.get(0).getIsolation()!=null){
                    if(seedlingInspectionTable.get(0).getIsolation().equalsIgnoreCase("Distance")){
                        tv_iso_dis_show.setVisibility(View.VISIBLE);
                        ed_iso_distance.setText(seedlingInspectionTable.get(0).getIsolation_distance());
                    }
                     if(seedlingInspectionTable.get(0).getIsolation().equalsIgnoreCase("Grain")){
                        tv_iso_grain_show.setVisibility(View.VISIBLE);
                        ed_grain_remark.setText(seedlingInspectionTable.get(0).getGrain_remarks());
                    }
                     if(seedlingInspectionTable.get(0).getIsolation().equalsIgnoreCase("Time")){
                        tv_iso_time_show.setVisibility(View.VISIBLE);
                        ed_isolation_time.setText(seedlingInspectionTable.get(0).getIsolation_time());
                    }
                     if(seedlingInspectionTable.get(0).getStanding_acres()!=null){
                         ed_standing_acres.setText(seedlingInspectionTable.get(0).getStanding_acres());
                     }

                }
                ed_Remarks.setText(seedlingInspectionTable.get(0).getPest_remarks());
                ed_deasease_remark.setText(seedlingInspectionTable.get(0).getDiseases_remarks());
                ac_vigore_.setText(seedlingInspectionTable.get(0).getVigor());
                ac_crop_codn.setText(seedlingInspectionTable.get(0).getCrop_condition());
                ac_crop_stage.setText(seedlingInspectionTable.get(0).getCrop_stage());
                ac_pest.setText(seedlingInspectionTable.get(0).getPest());
                ac_diseases.setText(seedlingInspectionTable.get(0).getDiseases());
                ac_pest_insfestation.setText(seedlingInspectionTable.get(0).getPest_infestation_level());
                ac_diseases_insfestation.setText(seedlingInspectionTable.get(0).getDisease_infestation_level());
                ac_pld_reason.setText(seedlingInspectionTable.get(0).getPld_reason());
                ed_pld_acres.setText(seedlingInspectionTable.get(0).getPld_acres());
                ed_net_acres.setText(seedlingInspectionTable.get(0).getNet_acres());


                if(seedlingInspectionTable.get(0).getPld_reason().length()>0) {
                    try {
                        ac_pld_reason.setText(seedlingInspectionTable.get(0).getPld_reason());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    ac_pld_reason_layout.setVisibility(View.GONE);
                }



                if(seedlingInspectionTable.get(0).getDate_of_inspection()!=null && !seedlingInspectionTable.get(0).getDate_of_inspection().equalsIgnoreCase("")) {
                    ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(seedlingInspectionTable.get(0).getDate_of_inspection()));
                    ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(seedlingInspectionTable.get(0).getActual_date()));
                }
                if(seedlingInspectionTable.get(0).getRecommended_date()!=null && !seedlingInspectionTable.get(0).getRecommended_date().equalsIgnoreCase("")) {
                    ed_recommended_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(seedlingInspectionTable.get(0).getRecommended_date()));
                }
                if(seedlingInspectionTable.get(0).getAttachment()!=null) {
                    image_layout.setVisibility(View.VISIBLE);
                    setImageView.setVisibility(View.VISIBLE);
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(getActivity()).clearDiskCache();
                            }
                        }).start();
                        String file_attachment = seedlingInspectionTable.get(0).getAttachment();

                        try {
                            byte[] decodedString = Base64.decode(file_attachment, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Glide.with(getActivity())
                                    .asBitmap()
                                    .load(decodedByte)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .listener(new RequestListener<Bitmap>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                            Glide.with(getActivity())
                                                    .load(ApiUtils.BASE_URL + "/api/Inspection/Get_Image?id="+file_attachment) // image urlApiUtils.BASE_URL + "/api/Inspection/Get_Image?id=" +
                                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                    .skipMemoryCache(true)
                                                    .placeholder(R.drawable.noimage1)
                                                    .into(setImageView);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .placeholder(R.drawable.noimage1)
                                    .into(setImageView);
                        }
                        catch (Exception e){
                            Glide.with(getActivity())
                                    .load(ApiUtils.BASE_URL + "/api/Inspection/Get_Image?id="+file_attachment) // image urlApiUtils.BASE_URL + "/api/Inspection/Get_Image?id=" +
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.noimage1)
                                    // any placeholder to load at start
                                    .into(setImageView);
                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                else {
                    MDToast.makeText(getActivity(), "no image", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }
        } catch (Exception e) {
            Log.e("exc", e.getMessage());
        }
    }

    private void InsertSeedling(SeedLing_InspectionLineModel seedLing_inspectionLineModel, JsonObject asJsonObject) {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.insertSeedlingInspection(asJsonObject);
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            ArrayList<SeedLing_InspectionLineModel> seedlingInspectionArrayList = new ArrayList<>();
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<ResponseModel> inserResponseList = response.body();
                            if (inserResponseList!=null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                seedLing_inspectionLineModel.sync_with_api_ins2 = 1;
                               seedLing_inspectionLineModel.attachment = inserResponseList.get(0).attachment;
                                seedlingInspectionArrayList.add(seedLing_inspectionLineModel);
                                insertSeedlingInspectionLine(seedlingInspectionArrayList);
                                MDToast.makeText(getActivity(), inserResponseList.get(0).message, MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                            } else {
                                progressDialogLoading.hideDialog();
                                MDToast.makeText(getActivity(), inserResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                            }

                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_seedling", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_seedling", getActivity());
                }
            });

        } else {
            ArrayList<SeedLing_InspectionLineModel> seedlingInspectionArrayList = new ArrayList<>();
            seedLing_inspectionLineModel.sync_with_api_ins2 = 0;
            seedLing_inspectionLineModel.attachment = selected_file_path;
            seedlingInspectionArrayList.add(seedLing_inspectionLineModel);
            insertSeedlingInspectionLine(seedlingInspectionArrayList);
            MDToast.makeText(getActivity(), "Insert Successful!", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
        }
    }

    private void completeSeedling() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection II", seedlingInspectionTable.get(0).getProduction_lot_no(), seedlingInspectionTable.get(0).getScheduler_no());
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<CompleteGerminationInspectionModel>>() {
                @Override
                public void onResponse(Call<List<CompleteGerminationInspectionModel>> call, Response<List<CompleteGerminationInspectionModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<CompleteGerminationInspectionModel> completeResponseList = response.body();
                            if (completeResponseList.size() > 0 && completeResponseList.get(0).condition) {
                                if (completeResponseList.get(0).nav_condition != 0) {
                                    completeSeedlingInspection("Online", 1, 1, seedlingInspectionTable.get(0).getCreated_on(), 0, "");
                                    MDToast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                                } else {
                                    completeSeedlingInspection("Online", 0, 1, seedlingInspectionTable.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
                                    MDToast toast=MDToast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                                    toast.setGravity(Gravity.BOTTOM,0,25);
                                    toast.show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                MDToast.makeText(getActivity(), completeResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                            }

                        } else {
                            progressDialogLoading.hideDialog();
                            MDToast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                        }

                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_inspection2", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_inspection2", getActivity());
                }
            });
        } else {
            //todo offline...........
            completeSeedlingInspection("Offline", 0, 0, seedlingInspectionTable.get(0).getCreated_on(), 1, "");
            MDToast.makeText(getActivity(), "Completed Successful !", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
        }
    }

    private void completeSeedlingInspection(String flag, int nav_sync, int inspection2, String completed_on, int ins2_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
        if (flag.equalsIgnoreCase("Online")) {
            inspection_oneHeaderDao.updateOnServerCompleteSeedling(nav_sync, inspection2, scheduler_no, seedlingInspectionTable.get(0).getProduction_lot_no(), completed_on, ins2_sync_with_server);
            inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderSeedling(scheduler_no, seedlingInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
        } else {
            int compResult=inspection_oneHeaderDao.updateOnServerCompleteSeedling(0, inspection2, scheduler_no, seedlingInspectionTable.get(0).getProduction_lot_no(), completed_on, ins2_sync_with_server);
        if(compResult>0){
            Log.e("comp2",String.valueOf(compResult));
        }
        }
        insertSeedlingInspectionLine(new ArrayList<>());
        db.close();
        db.destroyInstance();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_SINGLE && resultCode == RESULT_OK && null != data) {
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    selected_file_path = FilePath.getPath(getActivity(), mImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(this.selected_file_path, options);
                    setBitmapImage(bm,this.selected_file_path);
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
    }

    private void setBitmapImage(Bitmap img_file,String selected_file_path) {
        if (img_file != null) {
            File file = new File(selected_file_path);
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;
            if (fileSizeInMB > 2) {
                clear_image_btn.setVisibility(View.GONE);
                setImageView.setVisibility(View.GONE);
                image_layout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "File size must be less than 2MB.", Toast.LENGTH_SHORT).show();
            } else if (imageEncodList.size() != 2) {
                clear_image_btn.setVisibility(View.VISIBLE);
                imageEncodList.add(selected_file_path);
                setImageView.setVisibility(View.VISIBLE);
                image_layout.setVisibility(View.VISIBLE);
                setImageView.setImageBitmap(img_file);
                clear_image_btn.setOnClickListener(v -> {
                    setImageView.setImageBitmap(null);
                    imageEncodList.clear();
                    image_layout.setVisibility(View.GONE);
                    clear_image_btn.setVisibility(View.GONE);
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //todo for get recommended date  from planting table
    PlantingLineLotListTable plantingLineLotListTable;
    private String getFemaleSowingDate(){

        if(production_lot_no!=null) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                plantingLineLotListTable = plantingLineLotListDao.getFemaleSowingDate(production_lot_no);

                String date=plantingLineLotListTable.getSowing_Date_Female();
                String date_sub_string=date.substring(0,10);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date steepingdate = formatter.parse(date_sub_string);
                Calendar ca = Calendar.getInstance();
                ca.setTime(steepingdate);
                ca.add(Calendar.DATE, 25);

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String newDate = dateFormat.format(ca.getTime());

                String[] parts = newDate.split("-");
                String dd = parts[0];
                String yy = parts[1];
                String mm=parts[2];
                Log.e("origional_date",yy+"-"+mm+"-"+dd);
                return newDate;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
        return "";
    }

    //todo for get standing acres from germination table
    GerminationInspection1_Table germinationInspection1_table;
    private String getStandingAcres(){

        if(production_lot_no!=null) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                GerminationInspectionDao germinationInspectionDao = pristineDatabase.germinationInspectionDao();
                germinationInspection1_table = germinationInspectionDao.getGerminationStandingAcres(production_lot_no);
                String standing_acres =germinationInspection1_table.getStanding_acres();
                return standing_acres;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
        return "";
    }

}
