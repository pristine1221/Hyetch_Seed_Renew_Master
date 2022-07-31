package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.HarvestingInspection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Germination_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.HarvestingInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.MaturityInspectionModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.File;
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

public class HarvestingInspectionFragment extends Fragment {

    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;

    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;
    private SessionManagement sessionManagement;
    private TextInputEditText ed_date_of_inspection, ed_moister_per, ed_remark, ed_pest_reamrk, ed_remmmdn_date,
            ed_actual_date, ed_sorting_grade, ed_desease_remark, ed_seed_setting,
            ed_seed_stng_per, ed_receipt_male, ed_receipt_female, ed_receipt_other, ac_crop_state,standing_acres,pld_acres,net_acres;

    private AutoCompleteTextView ac_crop_cond, ac_disease_inflaion, ac_disease, ac_pld_reason,ac_over_all_agronomy;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error, ed_item_name;

    private ImageView imageView;
    private FrameLayout close_dilog_bt;

    private Button complete_btn, save_record_btn;
    private Chip add_attachment;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip clear_image_btn;
    private String selected_file_path = "";
    private LinearLayout image_layout;
    private List<String> imageEncodList = new ArrayList<>();

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
        return inflater.inflate(R.layout.add_lot_wise_harvesting_inspection_field_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        insertHarvstingInspectionLine(new ArrayList<>());
    }

    private void initView(View view) {

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
        ed_item_name = view.findViewById(R.id.item_name);
        posting_error = view.findViewById(R.id.posting_error);
        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);
        image_layout = view.findViewById(R.id.image_layout);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);

        //initlize input fields.......

        ac_crop_cond = view.findViewById(R.id.ac_crop_codn);
        ac_crop_state = view.findViewById(R.id.ac_crop_stage);

        ac_disease = view.findViewById(R.id.ac_disease);
        ac_disease_inflaion = view.findViewById(R.id.ac_disease_inflaion);
        ac_pld_reason = view.findViewById(R.id.ac_pld_reason);
        standing_acres = view.findViewById(R.id.standing_acres);
        pld_acres = view.findViewById(R.id.pld_acres);
        net_acres = view.findViewById(R.id.net_acres);

        ac_over_all_agronomy = view.findViewById(R.id.ac_over_all_agronomy);
        ed_moister_per = view.findViewById(R.id.ed_moister_per);

        //ed_date_of_inspection = view.findViewById(R.id.date_of_inspection);
        ed_remmmdn_date = view.findViewById(R.id.ed_recmnd_date);
        ed_actual_date = view.findViewById(R.id.actual_date);
        ed_sorting_grade = view.findViewById(R.id.sorting_grading);

        ed_remark = view.findViewById(R.id.remark_);
        ed_pest_reamrk = view.findViewById(R.id.pest_remark);
        ed_desease_remark = view.findViewById(R.id.deasease_rmrk);
        complete_btn = view.findViewById(R.id.complt_btn);
        save_record_btn = view.findViewById(R.id.save_btn);
        close_dilog_bt.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });
        //set header detail...

        try {
            tv_prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_center.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_name() + "(" + schedulerInspectionLineTable.getOrganizer_code() + ")");
            tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
            tv_crop_type.setText(schedulerInspectionLineTable.getItem_crop_type());
            village_address.setText("Vill-" + schedulerInspectionLineTable.getGrower_village() + "," + schedulerInspectionLineTable.getGrower_city() + "," + schedulerInspectionLineTable.getGrower_district());

            if (schedulerInspectionLineTable.getSowing_date_male() != null) {
                tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_male.setText("");
            }
            if (schedulerInspectionLineTable.getSowing_date_female() != null) {
                tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_female()));
            } else {
                tv_sd_female.setText("");
            }
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo for net acres................
        pld_acres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equalsIgnoreCase("")){
                    double i=Double.parseDouble(standing_acres.getText().toString());
                    double i1=Double.parseDouble(pld_acres.getText().toString());
                    net_acres.setText(String.valueOf(i-i1));
                }
                else {
                    MDToast.makeText(getActivity(),"pld acres can't blank or greater than standing acres",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       /* ed_date_of_inspection.setOnTouchListener((view1, motionEvent) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_inspection);
            } catch (Exception e) {
                Log.e("exc", e.getMessage());
            }
            return true;
        });
*/
        ed_actual_date.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            } catch (Exception e) {
                Log.e("exc", e.getMessage());
            }
            return true;
        });

        //todo new changes auto select recommended date by recommneded days.
        ed_remmmdn_date.setText(getFemaleSowingDate());

        standing_acres.setText(getStandingAcres());

        ItemAdapter ac_crop_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.desease));
        ac_disease.setAdapter(ac_crop_adapter);

        ItemAdapter germination_arrayAdapater = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.desease_infalation));
        ac_disease_inflaion.setAdapter(germination_arrayAdapater);

        ItemAdapter ac_pld_reason_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.pld));
        ac_pld_reason.setAdapter(ac_pld_reason_adapter);

        ac_crop_state.setText("Harvesting");
        ItemAdapter ac_crop_cond_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.crop_condition));
        ac_crop_cond.setAdapter(ac_crop_cond_adapter);

        ItemAdapter ac_over_all_agronomy_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.over_all_agronomy));
        ac_over_all_agronomy.setAdapter(ac_over_all_agronomy_adapter);

        save_record_btn.setOnClickListener(v -> {
            saveRecord();
        });

        complete_btn.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (harvestingInspectionTableList != null && harvestingInspectionTableList.size() > 0) {
                            completeGermination();
                        } else {
                            Toast.makeText(getActivity(), "First insert line.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        add_attachment.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }


    private List<HarvestingInspectionTable> harvestingInspectionTableList = new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertHarvstingInspectionLine(List<HarvestingInspectionModel> schedule_scan_lot_list) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            HarvestingInspectionDao harvestingInspectionDao = db.harvestingInspectionDao();
            if (schedule_scan_lot_list != null && schedule_scan_lot_list.size() > 0) {
                for (int i = 0; i < schedule_scan_lot_list.size(); i++) {
                    HarvestingInspectionTable harvestingInspectionTable = HarvestingInspectionTable.insertHarvestingDataIntoLocal(schedule_scan_lot_list.get(i));
                    if (harvestingInspectionDao.isDataExist(harvestingInspectionTable.getProduction_lot_no()) > 0) {
                        harvestingInspectionDao.update(harvestingInspectionTable);
                    } else {
                        harvestingInspectionDao.insert(harvestingInspectionTable);
                    }
                }
            }
            harvestingInspectionTableList = harvestingInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
        } finally {
            db.close();
            db.destroyInstance();
            if (harvestingInspectionTableList != null) {
                if (scheduler_line_header_data.getIns9_sync_with_server() > 0 || scheduler_line_header_data.getInspection_9() > 0) {
                    if (scheduler_line_header_data.getInspection_9() > 0 && scheduler_line_header_data.getInsp9_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp9_message() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp9_message());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    save_record_btn.setVisibility(View.GONE);
                    complete_btn.setVisibility(View.GONE);
                    // disable all input field when insp1 is completed or syncWithServer is 1.
                    disableAllInputField();

                } else {
                    save_record_btn.setVisibility(View.VISIBLE);
                    complete_btn.setVisibility(View.VISIBLE);
                }

                showInputFieldDetail();
            } else {
                save_record_btn.setVisibility(View.VISIBLE);
                if (scheduler_line_header_data.getIns9_sync_with_server() > 0 || scheduler_line_header_data.getInspection_9() > 0) {
                    if (scheduler_line_header_data.getInspection_9() > 0 && scheduler_line_header_data.getInsp9_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp9_message() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp1_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    save_record_btn.setVisibility(View.GONE);
                    complete_btn.setVisibility(View.GONE);
                    // Disable all input field when insp1 is completed or syncWithServer is 1.
                    disableAllInputField();
                }
            }
        }
    }

    private void showInputFieldDetail() {
        if (harvestingInspectionTableList != null && harvestingInspectionTableList.size() > 0) {
            ac_crop_cond.setText(harvestingInspectionTableList.get(0).getCrop_condition());
            ac_crop_state.setText(harvestingInspectionTableList.get(0).getCrop_stage());
            ac_over_all_agronomy.setText(harvestingInspectionTableList.get(0).getOverall_agronomy());
            ed_moister_per.setText(harvestingInspectionTableList.get(0).getMoisture_per());
            if (harvestingInspectionTableList.get(0).getDate_of_inspection() != null) {
                ed_date_of_inspection.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(harvestingInspectionTableList.get(0).getDate_of_inspection()));
            }
            try {
                if(harvestingInspectionTableList.get(0).getRecommended_date()!=null){
                    ed_remmmdn_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(harvestingInspectionTableList.get(0).getRecommended_date()));
                }
                else {
                    ed_remmmdn_date.setText(getFemaleSowingDate());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(harvestingInspectionTableList.get(0).getActual_date()));

            ed_sorting_grade.setText(harvestingInspectionTableList.get(0).getSorting_grading());
            ed_remark.setText(harvestingInspectionTableList.get(0).getRemarks());
            ed_pest_reamrk.setText(harvestingInspectionTableList.get(0).getPest_remarks());
            ed_desease_remark.setText(harvestingInspectionTableList.get(0).getDiseases_remarks());
            //todo new fields added......................................
            ac_disease_inflaion.setText(harvestingInspectionTableList.get(0).getDisease_infestation_level());
            ac_disease.setText(harvestingInspectionTableList.get(0).getDiseases());

            standing_acres.setText(harvestingInspectionTableList.get(0).getStanding_acres());
            pld_acres.setText(harvestingInspectionTableList.get(0).getPld_acre());
            net_acres.setText(harvestingInspectionTableList.get(0).getNet_acre());
            ac_pld_reason.setText(harvestingInspectionTableList.get(0).getPld_reason());

            try {
                if(harvestingInspectionTableList.get(0).getAttachment()!=null){
                    String getImageId=harvestingInspectionTableList.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    Toast.makeText(getActivity(), harvestingInspectionTableList.get(0).getAttachment(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void disableAllInputField() {
        ac_crop_cond.setEnabled(false);
        ac_crop_cond.setDropDownHeight(0);
        ac_crop_cond.setFocusable(false);
        ac_crop_state.setEnabled(false);
        ac_crop_state.setFocusable(false);
        ac_crop_state.setFocusableInTouchMode(false);

        ac_disease.setEnabled(false);
        ac_disease.setDropDownHeight(0);
        ac_disease.setFocusable(false);
        ac_disease.setFocusableInTouchMode(false);

        ac_disease_inflaion.setEnabled(false);
        ac_disease_inflaion.setDropDownHeight(0);
        ac_disease_inflaion.setFocusable(false);
        ac_disease_inflaion.setFocusableInTouchMode(false);

        ac_pld_reason.setEnabled(false);
        ac_pld_reason.setDropDownHeight(0);
        ac_pld_reason.setFocusable(false);
        ac_pld_reason.setFocusableInTouchMode(false);

        standing_acres.setEnabled(false);
        standing_acres.setFocusable(false);
        standing_acres.setFocusableInTouchMode(false);

        pld_acres.setEnabled(false);
        pld_acres.setFocusable(false);
        pld_acres.setFocusableInTouchMode(false);

        net_acres.setEnabled(false);
        net_acres.setFocusable(false);
        net_acres.setFocusableInTouchMode(false);


        ac_over_all_agronomy.setEnabled(false);
        ac_over_all_agronomy.setDropDownHeight(0);
        ac_over_all_agronomy.setFocusable(false);
        ac_over_all_agronomy.setFocusableInTouchMode(false);
        ed_moister_per.setEnabled(false);
        ed_moister_per.setFocusable(false);
        ed_moister_per.setFocusableInTouchMode(false);
        ed_date_of_inspection.setEnabled(false);
        ed_date_of_inspection.setFocusable(false);
        ed_remmmdn_date.setEnabled(false);
        ed_remmmdn_date.setFocusable(false);
        ed_actual_date.setEnabled(false);
        ed_actual_date.setFocusable(false);
        ed_sorting_grade.setEnabled(false);
        ed_sorting_grade.setFocusable(false);
        ed_remark.setEnabled(false);
        ed_remark.setFocusable(false);
        ed_pest_reamrk.setEnabled(false);
        ed_pest_reamrk.setFocusable(false);
        ed_desease_remark.setEnabled(false);
        ed_desease_remark.setFocusable(false);
        ed_seed_setting.setEnabled(false);
        ed_seed_stng_per.setEnabled(false);
        ed_seed_setting.setFocusable(false);
        ed_seed_stng_per.setFocusable(false);
        ed_receipt_male.setEnabled(false);
        ed_receipt_male.setFocusable(false);
        ed_receipt_female.setEnabled(false);
        ed_receipt_female.setFocusable(false);
        ed_receipt_other.setEnabled(false);
        ed_receipt_other.setFocusable(false);
        disableImageBtn();
    }


    private void disableImageBtn() {
        add_attachment.setEnabled(false);
        clear_image_btn.setVisibility(View.GONE);
        add_attachment.setChecked(false);
        add_attachment.setChipStrokeColor(getResources().getColorStateList(R.color.gray1));
        add_attachment.setText("Uploaded");
        add_attachment.setTextColor(getResources().getColor(R.color.gray1));
        add_attachment.setChipIconResource(R.drawable.default_img);
    }

    private void saveRecord() {
        HarvestingInspectionModel harvestingInspectionModel = new HarvestingInspectionModel();
        try {
            harvestingInspectionModel.scheduler_no = scheduler_no;
            harvestingInspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            harvestingInspectionModel.production_lot_no = production_lot_no;
            harvestingInspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_of_inspection.getText().toString().trim());
            harvestingInspectionModel.crop_condition = ac_crop_cond.getText().toString().trim();
            harvestingInspectionModel.crop_stage = ac_crop_state.getText().toString().trim();
            harvestingInspectionModel.sorting_grading = ed_sorting_grade.getText().toString().trim();
            harvestingInspectionModel.overall_agronomy = ac_over_all_agronomy.getText().toString().trim();
            harvestingInspectionModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            harvestingInspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            //harvestingInspectionModel.diseases = ac_disease.getText().toString().trim();
            harvestingInspectionModel.pest_remarks = ed_pest_reamrk.getText().toString().trim();
            harvestingInspectionModel.diseases_remarks = ed_desease_remark.getText().toString().trim();
            harvestingInspectionModel.remarks = ed_remark.getText().toString().trim();
            harvestingInspectionModel.male_reciept_no = "0";
            harvestingInspectionModel.female_reciept_no = "0";
            harvestingInspectionModel.other_reciept_no = "0";
            harvestingInspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                harvestingInspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                harvestingInspectionModel.seed_setting_prcnt = "0.0";
            }

            if (!ed_moister_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_moister_per.getText().toString().trim()));
                harvestingInspectionModel.moisture_per = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                harvestingInspectionModel.moisture_per = "0.0";
            }


            //todo new fields added.................................
            harvestingInspectionModel.diseases = ac_disease.getText().toString().trim();

            harvestingInspectionModel.disease_infestation_level = ac_disease_inflaion.getText().toString().trim();

            harvestingInspectionModel.standing_acres = standing_acres.getText().toString().trim();

            if (!pld_acres.getText().toString().trim().equalsIgnoreCase("")) {
                String pldAcres = pld_acres.getText().toString().trim();
                harvestingInspectionModel.pld_acre = !pldAcres.equalsIgnoreCase("") ? pldAcres : "0.0";
            } else {
                harvestingInspectionModel.pld_acre = "0.0";
            }

            harvestingInspectionModel.pld_reason = ac_pld_reason.getText().toString().trim();

            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            harvestingInspectionModel.attachment = base_64_image != null ? base_64_image : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        String jsonString = new Gson().toJson(harvestingInspectionModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("json_harvesting", asJsonObject.toString());

        if (ed_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ed_desease_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Disease Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_cond.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop condn. ", Toast.LENGTH_SHORT).show();

        } else if (ac_crop_state.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage. ", Toast.LENGTH_SHORT).show();

        } else if (ed_actual_date.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter actual date. ", Toast.LENGTH_SHORT).show();
        } else {
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.insertHarvestingInsection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<HarvestingInspectionModel> maturityInspectionModelArrayList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    harvestingInspectionModel.synWithApi9 = 1;
                                    harvestingInspectionModel.attachment = selected_file_path;
                                    maturityInspectionModelArrayList.add(harvestingInspectionModel);
                                    insertHarvstingInspectionLine(maturityInspectionModelArrayList);
                                    StaticMethods.showMDToast(getActivity(), inserResponseList.get(0).message, MDToast.TYPE_SUCCESS);
                                } else {
                                    progressDialogLoading.hideDialog();
                                    StaticMethods.showMDToast(getActivity(), inserResponseList.size() > 0 ? inserResponseList.get(0).message : "Api not respoding.", MDToast.TYPE_ERROR);
                                }

                            } else {
                                progressDialogLoading.hideDialog();
                                StaticMethods.showMDToast(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                            }

                        } catch (Exception e) {
                            progressDialogLoading.hideDialog();
                            Log.e("exception database", e.getMessage() + "cause");
                            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_flowering", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_flowering", getActivity());
                    }
                });
            } else {
                ArrayList<HarvestingInspectionModel> maturityInspectionModelArrayList = new ArrayList<>();
                harvestingInspectionModel.synWithApi9 = 0;
                harvestingInspectionModel.attachment = selected_file_path;
                maturityInspectionModelArrayList.add(harvestingInspectionModel);
                insertHarvstingInspectionLine(maturityInspectionModelArrayList);
                StaticMethods.showMDToast(getActivity(), "insert Successful !", MDToast.TYPE_SUCCESS);
            }
        }
    }

    private void completeGermination() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection IX", harvestingInspectionTableList.get(0).getProduction_lot_no(), harvestingInspectionTableList.get(0).getScheduler_no());
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<CompleteGerminationInspectionModel>>() {
                @Override
                public void onResponse(Call<List<CompleteGerminationInspectionModel>> call, Response<List<CompleteGerminationInspectionModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<CompleteGerminationInspectionModel> completeResponseList = response.body();
                            if (completeResponseList != null && completeResponseList.size() > 0 && completeResponseList.get(0).condition) {
                                if (completeResponseList.get(0).nav_condition != 0) {
                                    completeOnlineOfflineInspection("Online", 1, 1, harvestingInspectionTableList.get(0).getCreated_on(), 0, "");
                                    StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);

                                } else {
                                    completeOnlineOfflineInspection("Online", 0, 1, harvestingInspectionTableList.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
                                    StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_ERROR);
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                StaticMethods.showMDToast(getActivity(), completeResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", MDToast.TYPE_ERROR);
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            StaticMethods.showMDToast(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_inspection", getActivity());
                    } finally {
                        progressDialogLoading.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_inspection", getActivity());
                }
            });
        } else {
            //todo offline...........
            completeOnlineOfflineInspection("Offline", 0, 0, harvestingInspectionTableList.get(0).getCreated_on(), 1, "");
            StaticMethods.showMDToast(getActivity(), "Completed Successful!", MDToast.TYPE_SUCCESS);
        }
    }


    private void completeOnlineOfflineInspection(String flag, int nav_sync, int inspection9, String completed_on, int ins9_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteHarvesting(nav_sync, inspection9, scheduler_no, harvestingInspectionTableList.get(0).getProduction_lot_no(), completed_on, ins9_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspHarvesting(scheduler_no, harvestingInspectionTableList.get(0).getProduction_lot_no(), 1, nav_sync_error);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteHarvesting(0, inspection9, scheduler_no, harvestingInspectionTableList.get(0).getProduction_lot_no(), completed_on, ins9_sync_with_server);
            }
            insertHarvstingInspectionLine(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
        }
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
                setBitmapImage(bm, this.selected_file_path);
            }
        } else {
            Toast.makeText(getActivity(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
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
                imageEncodList.add(selected_file_path);
                clear_image_btn.setVisibility(View.VISIBLE);
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
                ca.add(Calendar.DAY_OF_YEAR, 110);

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

    //todo for get standing acres from germination table.................
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

    private void HitShowImageApi(String getImageId) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<ResponseBody> call = mAPIService.getImageInspection(getImageId);
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        image_layout.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        Glide.with(getActivity())
                                .load(ApiUtils.BASE_URL+"/api/Inspection/Get_Image?id="+getImageId) // image url
                                .placeholder(R.drawable.noimage1) // any placeholder to load at start
                                .into(imageView);
                    } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_germination", getActivity());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_germination", getActivity());
            }
        });
    }
}
