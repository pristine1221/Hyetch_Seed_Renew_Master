package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.vegetativeInspection;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;
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
import com.example.pristineseed.model.scheduler_inspection.Vegitative_InspectionModel;
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

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Vegitative_Inspection_Fragment extends Fragment {

    SessionManagement sessionManagement;
    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error;


    private Button bt_complete, btn_save_record;
    private AutoCompleteTextView ac_vigore_, ac_pest, ac_crop_codn, ac_desease, ac_isolation, ac_top_dressing_bags, ac_top_dressing;
    private TextInputEditText ed_recommended_date, ed_actual_date, ed_isolation_time, ed_iso_distance,
            ed_other_tps, ed_pest_remark, ed_remarks, ed_desease_remark, ed_date_of_insp, ed_seed_setting, seed_setting_per, ed_receipt_male, ed_receipt_female,
            ed_receipt_other, ed_grain_remark, ac_crop_stage;

    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;
    private TextInputLayout tv_iso_dis_show, tv_iso_time_show, tv_iso_grain_show;

    private ImageView imageView;
    private FrameLayout close_dilog_bt;
    private Chip clear_image_btn;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip add_image_btn;
    private String selected_file_path = "";
    private String scheduler_no = "", production_lot_no = "";
    private LinearLayout image_layout;
    private List<String> imageEncodList = new ArrayList<>();
    private String iso_empty_value;

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
        return inflater.inflate(R.layout.fragment_vegetative_inspection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        insertVegitativeInspectionLine(new ArrayList<>());

        btn_save_record.setOnClickListener(v -> {
            saveRecord();
        });
        bt_complete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    completeInspection();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        add_image_btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {
        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);
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
        posting_error = view.findViewById(R.id.posting_error);
        ed_date_of_insp = view.findViewById(R.id.ed_date_of_insp);

        ac_desease = view.findViewById(R.id.ac_desease);
        ed_recommended_date = view.findViewById(R.id.tv_recommended_date);
        ed_actual_date = view.findViewById(R.id.tv_actual_date);
        ed_isolation_time = view.findViewById(R.id.ed_iso_tym);
        ed_iso_distance = view.findViewById(R.id.ed_iso_dist);
        ac_top_dressing = view.findViewById(R.id.ac_top_dressing);
        ac_top_dressing_bags = view.findViewById(R.id.ac_top_dressing_bags);
        ac_vigore_ = view.findViewById(R.id.ac_vigore);
        ac_pest = view.findViewById(R.id.dropdown_pest_diseases);
        ac_crop_codn = view.findViewById(R.id.crop_condn);
        ac_crop_stage = view.findViewById(R.id.crop_stage);
        bt_complete = view.findViewById(R.id.complete_btn);
        btn_save_record = view.findViewById(R.id.save_record_btn);
        ed_other_tps = view.findViewById(R.id.ed_other_tps);
        ed_pest_remark = view.findViewById(R.id.ed_pest_remark);
        ed_remarks = view.findViewById(R.id.ed_remarks);
        ac_isolation = view.findViewById(R.id.ac_isolation);
        tv_iso_dis_show = view.findViewById(R.id.iso_dis_show);
        tv_iso_time_show = view.findViewById(R.id.iso_time_show);
        tv_iso_grain_show = view.findViewById(R.id.iso_grain_show);
        ed_desease_remark = view.findViewById(R.id.ed_desease_remark);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);
        ed_grain_remark = view.findViewById(R.id.grain_remark);

        image_layout = view.findViewById(R.id.image_layout);
        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        seed_setting_per = view.findViewById(R.id.seed_setting_per);

        imageView = view.findViewById(R.id.image_view);
        add_image_btn = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);

        close_dilog_bt.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        ed_date_of_insp.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });
        //globalDate();

        ed_actual_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            return true;
        });

        ed_isolation_time.setOnTouchListener((v, event) -> {
            new CustomTimePicker(getActivity()).showDialog(ed_isolation_time);
            return true;
        });

        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);
        List<String> desease_list = Arrays.asList(CommonData.desease);
        List<String> vigore = Arrays.asList(CommonData.vigor);
        List<String> isolation_List = Arrays.asList(CommonData.isolation_);

        ItemAdapter cropAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_codn.setAdapter(cropAdapter);
        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, desease_list);
        ac_desease.setAdapter(pest_adapter);
        ItemAdapter vigore_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, vigore);
        ac_vigore_.setAdapter(vigore_adapter);
        ac_crop_stage.setText("Vegetative");
        ItemAdapter pest_adpter = new ItemAdapter(getActivity(), R.layout.android_item_view, desease_list);
        ac_pest.setAdapter(pest_adpter);

        ItemAdapter topdress_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.first_top_dressing));
        ac_top_dressing.setAdapter(topdress_adapter);
        ItemAdapter top_dressing_bags = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.first_top_dressing_bags));
        ac_top_dressing_bags.setAdapter(top_dressing_bags);

        ItemAdapter iso_lation_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, isolation_List);
        ac_isolation.setAdapter(iso_lation_adapter);

        ac_isolation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    iso_empty_value = "";
                    ac_isolation.setText(iso_empty_value);
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

        try {
            //set header data....
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
            tv_org_code.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");

            // set scheduler line data....
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            village_address.setText(schedulerInspectionLineTable.getGrower_village() + "," +
                    schedulerInspectionLineTable.getGrower_district() + ","
                    + schedulerInspectionLineTable.getGrower_city() + ","
                    + schedulerInspectionLineTable.getGrower_taluka_mandal());

            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
            ed_recommended_date.setText(getFemaleSowingDate());

            tv_prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
            tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                if (schedulerInspectionLineTable != null) {
                    PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getVaityCodeAliasName(production_lot_no, schedulerInspectionLineTable.getVariety_code());
                    if (plantingLineLotListTable != null) {
                        tv_varity_code.setText(plantingLineLotListTable.getAlias_Name());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
            tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    List<VegitativeInspectionTable> vegitativeInspectionTable = new ArrayList<>();
    SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertVegitativeInspectionLine(List<Vegitative_InspectionModel> vegittvInspectionList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            VegitativeInspectionDao vegitativeInspectionDao = db.vegitativeInspectionDao();
            if (vegittvInspectionList != null && vegittvInspectionList.size() > 0) {
                for (int i = 0; i < vegittvInspectionList.size(); i++) {
                    VegitativeInspectionTable vegitativeInspectionTable = VegitativeInspectionTable.insertVegitativeDataIntoLocal(vegittvInspectionList.get(0));
                    if (vegitativeInspectionDao.isDataExist(vegitativeInspectionTable.getProduction_lot_no()) > 0) {
                        vegitativeInspectionDao.update(vegitativeInspectionTable);
                    } else if (vegitativeInspectionTable.getProduction_lot_no() != null || vegitativeInspectionTable.getScheduler_no() != null) {
                        vegitativeInspectionDao.insert(vegitativeInspectionTable);
                    }
                }
            }
            vegitativeInspectionTable = vegitativeInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
            Log.e("log_e", e.getMessage());
        } finally {
            db.close();
            db.destroyInstance();
            if (vegitativeInspectionTable != null) {
                if (scheduler_line_header_data.getIns3_sync_with_server() > 0 || scheduler_line_header_data.getInspection_3() > 0) {
                    if (scheduler_line_header_data.getInspection_3() > 0 && scheduler_line_header_data.getIns3_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp3_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp3_mesage());
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
                if (scheduler_line_header_data.getIns3_sync_with_server() > 0 || scheduler_line_header_data.getInspection_3() > 0) {
                    if (scheduler_line_header_data.getInspection_3() > 0 && scheduler_line_header_data.getIns3_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp3_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp3_mesage());
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
        ac_desease.setEnabled(false);
        ac_desease.setDropDownHeight(0);
        ed_recommended_date.setEnabled(false);
        ed_actual_date.setEnabled(false);
        ed_actual_date.setFocusable(false);
        ac_isolation.setEnabled(false);
        ac_isolation.setDropDownHeight(0);
        ac_isolation.setFocusableInTouchMode(false);
        ac_isolation.setFocusable(false);
        ed_isolation_time.setEnabled(false);
        ed_iso_distance.setEnabled(false);
        ac_top_dressing.setEnabled(false);
        ac_top_dressing.setDropDownHeight(0);
        ac_top_dressing.setFocusableInTouchMode(false);
        ac_top_dressing_bags.setEnabled(false);
        ac_top_dressing_bags.setDropDownHeight(0);
        ac_top_dressing_bags.setFocusableInTouchMode(false);
        ed_other_tps.setEnabled(false);
        ed_pest_remark.setEnabled(false);
        ed_remarks.setEnabled(false);
        ac_crop_codn.setEnabled(false);
        ac_crop_codn.setDropDownHeight(0);
        ac_pest.setEnabled(false);
        ac_pest.setDropDownHeight(0);
        ac_vigore_.setEnabled(false);
        ac_vigore_.setDropDownHeight(0);
        ac_vigore_.setFocusable(false);
        ac_crop_stage.setEnabled(false);
        ed_date_of_insp.setEnabled(false);
        ed_recommended_date.setFocusable(false);
        ed_isolation_time.setFocusable(false);
        ac_crop_codn.setFocusable(false);
        ac_pest.setFocusable(false);
        ac_crop_stage.setFocusable(false);
        ac_crop_stage.setFocusable(false);
        ed_date_of_insp.setFocusable(false);
        ed_desease_remark.setEnabled(false);
        ac_desease.setFocusable(false);
        ac_desease.setFocusableInTouchMode(false);
        ac_pest.setFocusableInTouchMode(false);
        ac_crop_codn.setFocusableInTouchMode(false);
        ac_crop_stage.setFocusableInTouchMode(false);
        ed_desease_remark.setFocusable(false);
        ed_desease_remark.setEnabled(false);

        ed_receipt_male.setEnabled(false);
        ed_receipt_male.setFocusable(false);
        ed_receipt_female.setEnabled(false);
        ed_receipt_female.setFocusable(false);
        ed_receipt_other.setEnabled(false);
        ed_receipt_other.setFocusable(false);

        disableImageBtn();

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

    private void showRefelectedFieldData() {
        try {
            if (vegitativeInspectionTable != null && vegitativeInspectionTable.size() > 0) {
                ac_desease.setText(vegitativeInspectionTable.get(0).getDiseases());
                //  if (vegitativeInspectionTable.get(0).getRecommended_date() != null) {

                ed_recommended_date.setText(getFemaleSowingDate());
                //   } else {
                // ed_recommended_date.setText("");
                //  }
                if (vegitativeInspectionTable.get(0).getActual_date() != null) {
                    ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(vegitativeInspectionTable.get(0).getActual_date()));
                } else {
                    ed_actual_date.setText("");
                }
                ac_isolation.setText(vegitativeInspectionTable.get(0).getIsolation());

                if (vegitativeInspectionTable.get(0).getIsolation() != null) {
                    if (vegitativeInspectionTable.get(0).getIsolation().equalsIgnoreCase("Distance")) {
                        tv_iso_dis_show.setVisibility(View.VISIBLE);
                        ed_iso_distance.setText(vegitativeInspectionTable.get(0).getIsolation_distance());
                    }
                    if (vegitativeInspectionTable.get(0).getIsolation().equalsIgnoreCase("Time")) {
                        tv_iso_time_show.setVisibility(View.VISIBLE);
                        ed_isolation_time.setText(vegitativeInspectionTable.get(0).getIsolation_time());
                    }
                    if (vegitativeInspectionTable.get(0).getIsolation().equalsIgnoreCase("Grain")) {
                        tv_iso_grain_show.setVisibility(View.VISIBLE);
                        ed_grain_remark.setText(vegitativeInspectionTable.get(0).getGrain_remarks());
                    }
                }
                ac_top_dressing.setText(vegitativeInspectionTable.get(0).getFirst_top_dressing());
                ac_top_dressing_bags.setText(vegitativeInspectionTable.get(0).getFirst_top_dressing_bags());
                ed_other_tps.setText(vegitativeInspectionTable.get(0).getOther_types());
                ed_pest_remark.setText(vegitativeInspectionTable.get(0).getPest_remarks());
                //ed_remarks.setText(vegitativeInspectionTable.get(0).getDiseases_remarks());
                ed_desease_remark.setText(vegitativeInspectionTable.get(0).getDiseases_remarks());
                ac_crop_codn.setText(vegitativeInspectionTable.get(0).getCrop_condition());
                ac_pest.setText(vegitativeInspectionTable.get(0).getPest());
                ac_vigore_.setText(vegitativeInspectionTable.get(0).getVigor());
                ac_crop_stage.setText(vegitativeInspectionTable.get(0).getCrop_stage());
                if (vegitativeInspectionTable.get(0).getDate_of_inspection() != null) {
                    ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(vegitativeInspectionTable.get(0).getDate_of_inspection()));
                } else {
                    ed_date_of_insp.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecord() {
        Vegitative_InspectionModel vegitative_inspectionModel = new Vegitative_InspectionModel();
        try {
            vegitative_inspectionModel.scheduler_no = scheduler_no;
            vegitative_inspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            vegitative_inspectionModel.production_lot_no = production_lot_no;
            vegitative_inspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            vegitative_inspectionModel.vigor = ac_vigore_.getText().toString().trim();
            vegitative_inspectionModel.pest = ac_pest.getText().toString().trim();
            vegitative_inspectionModel.diseases = ac_desease.getText().toString().trim();
            vegitative_inspectionModel.pest_remarks = ed_pest_remark.getText().toString().trim();
            vegitative_inspectionModel.diseases_remarks = ed_desease_remark.getText().toString().trim();
            vegitative_inspectionModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_recommended_date.getText().toString().trim());
            vegitative_inspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            vegitative_inspectionModel.isolation = ac_isolation.getText().toString().trim();
            vegitative_inspectionModel.isolation_time = ed_isolation_time.getText().toString().trim();
            vegitative_inspectionModel.crop_condition = ac_crop_codn.getText().toString().trim();
            vegitative_inspectionModel.crop_stage = ac_crop_stage.getText().toString().trim();
            vegitative_inspectionModel.other_types = ed_other_tps.getText().toString().trim();
            vegitative_inspectionModel.first_top_dressing = ac_top_dressing.getText().toString().trim();
            vegitative_inspectionModel.first_top_dressing_bags = ac_top_dressing_bags.getText().toString().trim();
            vegitative_inspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();
            vegitative_inspectionModel.male_reciept_no = "0";
            vegitative_inspectionModel.female_reciept_no = "0";
            vegitative_inspectionModel.other_reciept_no = "0";
            vegitative_inspectionModel.grain_remarks = ed_grain_remark.getText().toString().trim();

            if (!seed_setting_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(seed_setting_per.getText().toString().trim()));
                vegitative_inspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                vegitative_inspectionModel.seed_setting_prcnt = "0.0";
            }
            if (!ed_iso_distance.getText().toString().trim().equalsIgnoreCase("")) {
                String iso_ddis = String.valueOf(Float.parseFloat(ed_iso_distance.getText().toString().trim()));
                vegitative_inspectionModel.isolation_distance = !iso_ddis.equalsIgnoreCase("") ? iso_ddis : "0.0";
            } else {
                vegitative_inspectionModel.isolation_distance = "0.0";
            }

            if (ac_isolation.getText().toString().equalsIgnoreCase("-")) {
                ac_isolation.setText(iso_empty_value);
            }

            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            vegitative_inspectionModel.attachment = base_64_image != null ? base_64_image : "";

        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        String jsonString = new Gson().toJson(vegitative_inspectionModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("jsonObject", String.valueOf(asJsonObject));
        if (ed_pest_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Pest  Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ed_desease_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Disease Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_codn.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop condn.", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_stage.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage.", Toast.LENGTH_SHORT).show();
        } else if (ac_isolation.getText().toString().equalsIgnoreCase("-")) {
            Toast.makeText(getActivity(), "invalid input - !", Toast.LENGTH_SHORT).show();
        } else {
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.insertVegitativeInspection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<Vegitative_InspectionModel> vegitativeInspectionList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    vegitative_inspectionModel.syncWithApi = 1;
                                    vegitative_inspectionModel.attachment = selected_file_path;
                                    vegitativeInspectionList.add(vegitative_inspectionModel);
                                    insertVegitativeInspectionLine(vegitativeInspectionList);
                                    StaticMethods.showMDToast(getActivity(), inserResponseList.get(0).message, MDToast.TYPE_SUCCESS);
                                } else {
                                    progressDialogLoading.hideDialog();
                                    StaticMethods.showMDToast(getActivity(), inserResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", MDToast.TYPE_ERROR);
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                StaticMethods.showMDToast(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                            }

                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_vegitative", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_vegitative", getActivity());
                    }
                });
            } else {
                ArrayList<Vegitative_InspectionModel> vegitativeInspectionList = new ArrayList<>();
                vegitative_inspectionModel.syncWithApi = 0;
                vegitative_inspectionModel.attachment = selected_file_path;
                vegitativeInspectionList.add(vegitative_inspectionModel);
                insertVegitativeInspectionLine(vegitativeInspectionList);
                Toast.makeText(getActivity(), "insert Successful!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void completeInspection() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            VegitativeInspectionDao vegitativeInspectionDao = pristineDatabase.vegitativeInspectionDao();
            VegitativeInspectionTable vegitativeInspectionTable = vegitativeInspectionDao.getCreatedOn(production_lot_no);
            if (network) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection III", vegitativeInspectionTable.getProduction_lot_no(), vegitativeInspectionTable.getScheduler_no());
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
                                        completeVegitativeInspection("Online", 1, 1, vegitativeInspectionTable.getCreated_on(), 0, "");
                                        StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);

                                    } else {
                                        completeVegitativeInspection("Online", 0, 1, vegitativeInspectionTable.getCreated_on(), 0, completeResponseList.get(0).nav_message);
                                        StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_ERROR);
                                    }

                                } else {
                                    progressDialogLoading.hideDialog();
                                    Toast.makeText(getActivity(), completeResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_inspection", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_inspection", getActivity());
                    }
                });
            } else {
                completeVegitativeInspection("Offline", 0, 0, vegitativeInspectionTable.getCreated_on(), 1, "");

            }
        } catch (Exception e) {
            Log.e("vegi_cmp", e.getMessage());
        }

    }


    private void completeVegitativeInspection(String flag, int nav_sync, int inspection3, String completed_on, int ins3_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
        if (flag.equalsIgnoreCase("Online")) {
            inspection_oneHeaderDao.updateOnServerCompleteVegitative(nav_sync, inspection3, scheduler_no, vegitativeInspectionTable.get(0).getProduction_lot_no(), completed_on, ins3_sync_with_server);
            inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspVegitative(scheduler_no, vegitativeInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
        } else {
            inspection_oneHeaderDao.updateOnServerCompleteVegitative(0, inspection3, scheduler_no, vegitativeInspectionTable.get(0).getProduction_lot_no(), completed_on, ins3_sync_with_server);
            MDToast mdToast = MDToast.makeText(getActivity(), "Completed Successful!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
            mdToast.show();
        }
        db.close();
        db.destroyInstance();
        insertVegitativeInspectionLine(new ArrayList<>());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_SINGLE && resultCode == RESULT_OK && null != data) {
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    selected_file_path = FilePath.getPath(getActivity(), mImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(selected_file_path, options);
                    setBitmapImage(bm, this.selected_file_path);
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        } finally {

        }
    }

    private void setBitmapImage(Bitmap img_file, String selectedFilePath) {
        if (img_file != null) {
            File file = new File(selectedFilePath);
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;
            if (fileSizeInMB > 2) {
                clear_image_btn.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                image_layout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "File size must be less than 2MB.", Toast.LENGTH_SHORT).show();
            } else if (imageEncodList.size() != 2) {
                clear_image_btn.setVisibility(View.VISIBLE);
                imageEncodList.add(selected_file_path);
                imageView.setVisibility(View.VISIBLE);
                image_layout.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(img_file);
                clear_image_btn.setOnClickListener(v -> {
                    imageView.setImageBitmap(null);
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


    private String getFemaleSowingDate() {

        if (production_lot_no != null) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getFemaleSowingDate(production_lot_no);

                String date = plantingLineLotListTable.getSowing_Date_Female();
                String date_sub_string = date.substring(0, 10);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date steepingdate = formatter.parse(date_sub_string);
                Calendar ca = Calendar.getInstance();
                ca.setTime(steepingdate);
                ca.add(Calendar.DAY_OF_MONTH, 30);

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String newDate = dateFormat.format(ca.getTime());

                String[] parts = newDate.split("-");
                String dd = parts[0];
                String yy = parts[1];
                String mm = parts[2];
                Log.e("origional_date", yy + "-" + mm + "-" + dd);

                return newDate;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
        return "";

    }


}



