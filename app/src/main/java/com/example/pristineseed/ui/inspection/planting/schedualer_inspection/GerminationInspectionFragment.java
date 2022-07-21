package com.example.pristineseed.ui.inspection.planting.schedualer_inspection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspectionDao;
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

public class
GerminationInspectionFragment extends Fragment {
    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center, tv_farmer_name, village_address,
            tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name, ed_item_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error, img_notify_msg;

    private AutoCompleteTextView ac_germination_per, ac_crop_cond, ed_recmd_ds_fertz;
    private TextInputEditText recmd_ds_ftz_bgs, ed_basal_dos_bags, ed_remark_ftz,
            ed_receipt_male, ed_receipt_female, ed_receipt_other, ed_basal_dos, ed_date_of_insp, ed_seed_setting,
            seed_setting_per, ac_crop_stage, ed_recommded_date,ac_sowing_acres;

    private Button bt_cmplt, bt_save_rcrd, btn_update_record;
    private String scheduler_no = "", production_lot_no = "";
    private SessionManagement sessionManagement;
    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;
    private ImageView germinationImageView;
    private FrameLayout close_dilog_bt;
    private Chip clear_image_btn;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip add_image_btn;
    private String selected_file_path = "";
    private LinearLayout germinationImage_layout;
    private TextInputEditText ed_acutal_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            scheduler_header_table = new Gson().fromJson(bundle.getString("header_detail", ""), Scheduler_Header_Table.class);
            scheduler_no = bundle.getString("scheduler_no", "");
            production_lot_no = bundle.getString("production_lot_no", "");
            schedulerInspectionLineTable = new Gson().fromJson(bundle.getString("scheduler_line_detail", ""), SchedulerInspectionLineTable.class);
            Log.e("header_detail", new Gson().toJson(scheduler_header_table));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_germination_inspection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        intiView(view);
        insertGerminationInspectionLine(new ArrayList<>());

    }

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
        posting_error = view.findViewById(R.id.posting_error);

        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);
/*
        close_dilog_bt.setOnTouchListener((v, event) -> {
            getFragmentManager().popBackStack();
            return true;
        });*/
        close_dilog_bt.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });


        ac_germination_per = view.findViewById(R.id.dropdown_germination_per);
        ed_basal_dos = view.findViewById(R.id.basal_dos);
        // btn_update_record = view.findViewById(R.id.update_record);
        ed_date_of_insp = view.findViewById(R.id.date_of_insp);
        ed_recmd_ds_fertz = view.findViewById(R.id.recmd_ds_fertz);
        recmd_ds_ftz_bgs = view.findViewById(R.id.recmd_ds_ftz_bgs);
        ed_basal_dos_bags = view.findViewById(R.id.basal_dos_bags);
        ed_remark_ftz = view.findViewById(R.id.remark_ftz);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);

        bt_cmplt = view.findViewById(R.id.complete);
        bt_save_rcrd = view.findViewById(R.id.sav_record);
        ed_item_name = view.findViewById(R.id.item_name);
        ac_crop_cond = view.findViewById(R.id.ac_crop_codn);
        ac_crop_stage = view.findViewById(R.id.ac_crop_stage);
        ac_sowing_acres=view.findViewById(R.id.ac_sowing_acres);
        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        seed_setting_per = view.findViewById(R.id.seed_setting_per);

        add_image_btn = view.findViewById(R.id.add_attachment);
        germinationImageView = view.findViewById(R.id.image_view);
        clear_image_btn = view.findViewById(R.id.clear_img);
        img_notify_msg = view.findViewById(R.id.img_notify_msg);
        germinationImage_layout = view.findViewById(R.id.germinationImage_layout);
        ed_recommded_date = view.findViewById(R.id.ed_recommended_date);
        ed_acutal_date = view.findViewById(R.id.ed_acutal_date);

        //set header detail...
        try {
            tv_prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));

            tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");

            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");
            tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
            tv_crop_type.setText(schedulerInspectionLineTable.getItem_crop_type());

            village_address.setText("Vill-" + schedulerInspectionLineTable.getGrower_village() + "," + schedulerInspectionLineTable.getGrower_city() + "," + schedulerInspectionLineTable.getGrower_district());

            //schedulerInspectionLineTable.getOrganizer_name() + "," +

            tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_female()));
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());

            ed_recommded_date.setText(getFemaleSowingDate());//plantingLineLotListTable
            ac_sowing_acres.setText(plantingLineLotListTable.getSowing_Area_In_Acres());

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
        add_image_btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });

        ed_date_of_insp.setOnTouchListener((view1, motionEvent) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            } catch (Exception e) {
            }
            return true;
        });
        ed_acutal_date.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_acutal_date);
            } catch (Exception e) {

            }
            return true;
        });

        List<String> germination_per = Arrays.asList(CommonData.germination_per);
        ItemAdapter germination_arrayAdapater = new ItemAdapter(getActivity(), R.layout.android_item_view, germination_per);
        ac_germination_per.setAdapter(germination_arrayAdapater);
        ItemAdapter ac_crop_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.crop_condition));
        ac_crop_cond.setAdapter(ac_crop_adapter);
        ac_crop_stage.setText("Germination");

        ItemAdapter recmd_ds_fertz_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.first_top_dressing));
        ed_recmd_ds_fertz.setAdapter(recmd_ds_fertz_adapter);
        //todo recent changes.....
      /*  ItemAdapter recmdation_dose_of_fertilizer_adapter=new ItemAdapter(getActivity(),R.layout.android_item_view,Arrays.asList(CommonData.recommend_dose_of_fertilizer));
        ed_recmd_ds_fertz.setAdapter(recmdation_dose_of_fertilizer_adapter);*/

        bt_save_rcrd.setOnClickListener(v -> {
            saveRecord();
        });

        bt_cmplt.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        completeGermination();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }

    private List<GerminationInspection1_Table> germination_inspection_table = new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertGerminationInspectionLine(List<Germination_InspectionLineModel> schedule_scan_lot_list) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            GerminationInspectionDao germinationInspectionDao = db.germinationInspectionDao();
            if (schedule_scan_lot_list != null && schedule_scan_lot_list.size() > 0) {
                for (int i = 0; i < schedule_scan_lot_list.size(); i++) {
                    GerminationInspection1_Table germinationInspection1_table = GerminationInspection1_Table.insertGerminationInspection(schedule_scan_lot_list.get(i));
                    if (germinationInspectionDao.isDataExist(germinationInspection1_table.getProduction_lot_no()) > 0) {
                        germinationInspectionDao.update(germinationInspection1_table);
                    } else {
                        germinationInspectionDao.insert(germinationInspection1_table);
                    }
                }
            }
            germination_inspection_table = germinationInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
            if (germination_inspection_table != null) {
                if (scheduler_line_header_data.getIns1_sync_with_server() > 0
                        || scheduler_line_header_data.getInspection_1() > 0) {
                    if (scheduler_line_header_data.getInspection_1() > 0 && scheduler_line_header_data.getIns1_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp1_mesage() != null && !scheduler_line_header_data.getNav_error_insp1_mesage().equalsIgnoreCase("")) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp1_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_save_rcrd.setVisibility(View.GONE);
                    bt_cmplt.setVisibility(View.GONE);
                    // disable all input field when insp1 is completed or syncWithServer is 1.
                    disableAllInputField();

                } else {
                    bt_save_rcrd.setVisibility(View.VISIBLE);
                    bt_cmplt.setVisibility(View.VISIBLE);
                }
                showInputFieldDetail();
            } else {
                bt_save_rcrd.setVisibility(View.VISIBLE);
                add_image_btn.setEnabled(true);
                if (scheduler_line_header_data.getIns1_sync_with_server() > 0 || scheduler_line_header_data.getInspection_1() > 0) {
                    if (scheduler_line_header_data.getInspection_1() > 0 && scheduler_line_header_data.getIns1_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp1_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp1_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_save_rcrd.setVisibility(View.GONE);
                    bt_cmplt.setVisibility(View.GONE);
                    // Disable all input field when insp1 is completed or syncWithServer is 1.
                    disableAllInputField();
                }
            }
        }
    }

    private void disableAllInputField() {
        ac_germination_per.setEnabled(false);
        ac_germination_per.setDropDownHeight(0);
        ed_basal_dos.setEnabled(false);
        ed_basal_dos_bags.setEnabled(false);
        ed_recmd_ds_fertz.setEnabled(false);
        ed_recmd_ds_fertz.setDropDownHeight(0);
        recmd_ds_ftz_bgs.setEnabled(false);
        ed_remark_ftz.setEnabled(false);
        ed_receipt_male.setEnabled(false);
        ed_receipt_female.setEnabled(false);
        ed_receipt_other.setEnabled(false);
        ed_date_of_insp.setEnabled(false);
        ac_crop_stage.setEnabled(false);
        ac_sowing_acres.setEnabled(false);
        ac_crop_cond.setEnabled(false);
        ac_crop_cond.setDropDownHeight(0);
        ac_crop_cond.setFocusableInTouchMode(false);
        ac_crop_stage.setFocusableInTouchMode(false);
        ac_sowing_acres.setFocusable(false);
        ac_germination_per.setFocusableInTouchMode(false);
        ac_germination_per.setDropDownHeight(0);
        ed_acutal_date.setEnabled(false);
        ac_germination_per.setFocusable(false);
        disableImageBtn();
        ed_date_of_insp.setOnTouchListener(null);
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

    private void showInputFieldDetail() {
        if (germination_inspection_table != null && germination_inspection_table.size() > 0) {
            try {
                ac_germination_per.setText(germination_inspection_table.get(0).getGermination_per());
                ed_basal_dos.setText(germination_inspection_table.get(0).getBasal_dose());
                ed_basal_dos_bags.setText(germination_inspection_table.get(0).getBasal_dose_bags());
                ed_recmd_ds_fertz.setText(germination_inspection_table.get(0).getRecommended_dose_of_fertilizer());
                recmd_ds_ftz_bgs.setText(germination_inspection_table.get(0).getRecommended_dose_of_fertilizer_in_bags());
                ed_remark_ftz.setText(germination_inspection_table.get(0).getRemark_for_fertilizer());
                ed_acutal_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDD(germination_inspection_table.get(0).getActual_date()));
                ed_recommded_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDD(germination_inspection_table.get(0).getRecommended_date()));
                try {
                    if (germination_inspection_table.get(0).getDate_of_inspection() != null && !germination_inspection_table.get(0).getDate_of_inspection().equalsIgnoreCase("")) {
                        ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDD(germination_inspection_table.get(0).getDate_of_inspection()));
                    } else {
                        ed_date_of_insp.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ac_crop_stage.setText(germination_inspection_table.get(0).getCrop_stage());
                //ac_sowing_acres.setText(germination_inspection_table.get(0).);
                ac_crop_cond.setText(germination_inspection_table.get(0).getCrop_condition());
                if(germination_inspection_table.get(0).getAttachment()!=null){
                    String getImageId=germination_inspection_table.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    Toast.makeText(getActivity(), germination_inspection_table.get(0).getAttachment(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                //throw  e;
            }
        }
    }

    private void saveRecord() {
        Germination_InspectionLineModel germination_inspectionLineModel = new Germination_InspectionLineModel();
        try {
            germination_inspectionLineModel.scheduler_no = scheduler_no;
            germination_inspectionLineModel.production_lot_no = production_lot_no;
            germination_inspectionLineModel.arrival_plan_no = schedulerInspectionLineTable.getArrival_plan_no();
            germination_inspectionLineModel.crop_condition = ac_crop_cond.getText().toString().trim();
            germination_inspectionLineModel.crop_stage = ac_crop_stage.getText().toString().trim();
            germination_inspectionLineModel.germination_per = ac_germination_per.getText().toString().trim();
            germination_inspectionLineModel.recommended_dose_of_fertilizer = ed_recmd_ds_fertz.getText().toString().trim();
            germination_inspectionLineModel.basal_dose = ed_basal_dos.getText().toString().trim();
            germination_inspectionLineModel.remark_for_fertilizer = ed_remark_ftz.getText().toString().trim();
            germination_inspectionLineModel.male_reciept_no = "0";
            germination_inspectionLineModel.created_by = sessionManagement.getUserEmail();
            germination_inspectionLineModel.created_on = DateTimeUtilsCustome.getCurrentTime();
            germination_inspectionLineModel.seed_setting = ed_seed_setting.getText().toString().trim();
            germination_inspectionLineModel.female_reciept_no = "0";
            germination_inspectionLineModel.other_reciept_no = "0";
            germination_inspectionLineModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_recommded_date.getText().toString().trim());
            germination_inspectionLineModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_acutal_date.getText().toString().trim());

            if (selected_file_path != null && !selected_file_path.equalsIgnoreCase("")) {
                String base_64_image = StaticMethods.convertBase64(selected_file_path);
                if (base_64_image != null && !base_64_image.equalsIgnoreCase("")) {
                    germination_inspectionLineModel.attachment = base_64_image;
                }
            } else {
                germination_inspectionLineModel.attachment = "";
            }
            if (!seed_setting_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(seed_setting_per.getText().toString().trim()));
                germination_inspectionLineModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                germination_inspectionLineModel.seed_setting_prcnt = "0.0";
            }
            if (!recmd_ds_ftz_bgs.getText().toString().trim().equalsIgnoreCase("")) {
                String bas_ds_bags = String.valueOf(Float.parseFloat(recmd_ds_ftz_bgs.getText().toString().trim()));
                germination_inspectionLineModel.recommended_dose_of_fertilizer_in_bags = !bas_ds_bags.equalsIgnoreCase("") ? bas_ds_bags : "0.0";
            } else {
                germination_inspectionLineModel.recommended_dose_of_fertilizer_in_bags = "0.0";
            }
            if (!ed_basal_dos_bags.getText().toString().trim().equalsIgnoreCase("")) {
                String recmnd_ds_bags = String.valueOf(Float.parseFloat(ed_basal_dos_bags.getText().toString().trim()));
                germination_inspectionLineModel.basal_dose_bags = !recmnd_ds_bags.equalsIgnoreCase("") ? recmnd_ds_bags : "0.0";
            } else {
                germination_inspectionLineModel.basal_dose_bags = "0.0";
            }
            Log.e("insp", ed_date_of_insp.getText().toString().trim());
            germination_inspectionLineModel.date_of_inspection = DateTimeUtilsCustome.getCurrentTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonString = new Gson().toJson(germination_inspectionLineModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("jsonObject", String.valueOf(asJsonObject));

        if (ed_remark_ftz.getText().toString().length() > 150) {
            Toast.makeText(getActivity(), "Remark length must be < 150", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_cond.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop condn.", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_stage.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage.", Toast.LENGTH_SHORT).show();
        } else {
            InsertGermination(germination_inspectionLineModel, asJsonObject);
        }
    }

    private void InsertGermination(Germination_InspectionLineModel germination_inspectionLineModel, JsonObject asJsonObject) {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<ResponseModel>> call = mAPIService.insertGermination(asJsonObject);
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            ArrayList<Germination_InspectionLineModel> germinationInspectionModelArrayList = new ArrayList<>();
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<ResponseModel> inserResponseList = response.body();
                            if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                germination_inspectionLineModel.sync_with_api = 1;
                                germination_inspectionLineModel.attachment = selected_file_path;
                                germinationInspectionModelArrayList.add(germination_inspectionLineModel);
                                insertGerminationInspectionLine(germinationInspectionModelArrayList);
                                MDToast mdToast = MDToast.makeText(getActivity(), inserResponseList.get(0).message, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
                                mdToast.show();

                            } else {
                                progressDialogLoading.hideDialog();
                                Toast.makeText(getActivity(), inserResponseList.size() > 0 ? inserResponseList.get(0).message : "Api not respoding.", Toast.LENGTH_SHORT).show();
                            }
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
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_germination", getActivity());
                }
            });
        } else {
            ArrayList<Germination_InspectionLineModel> germinationInspectionModelArrayList = new ArrayList<>();
            germination_inspectionLineModel.sync_with_api = 0;
            germination_inspectionLineModel.attachment = selected_file_path;
            germinationInspectionModelArrayList.add(germination_inspectionLineModel);
            insertGerminationInspectionLine(germinationInspectionModelArrayList);
            Toast.makeText(getActivity(), "Insert Successful !", Toast.LENGTH_SHORT).show();
        }
    }

    private void completeGermination() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection I", germination_inspection_table.get(0).getProduction_lot_no(), germination_inspection_table.get(0).getScheduler_no());
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
                                    completeOnlineOfflineInspection("Online", 1, 1, germination_inspection_table.get(0).getCreated_on(), 0, "");
                                    MDToast mdToast = MDToast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                    mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
                                    mdToast.show();
                                } else {
                                    completeOnlineOfflineInspection("Online", 0, 1, germination_inspection_table.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
                                    MDToast mdToast = MDToast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                                    mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
                                    mdToast.show();
                                }
                            } else {
                                progressDialogLoading.hideDialog();
                                MDToast mdToast = MDToast.makeText(getActivity(), completeResponseList.size() > 0 ? "Record not found !" : "Api not respoding.", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                                mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
                                mdToast.show();
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
            //todo offline...........
            completeOnlineOfflineInspection("Offline", 0, 0, germination_inspection_table.get(0).getCreated_on(), 1, "");
            MDToast mdToast = MDToast.makeText(getActivity(), "Completed Successful!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
            mdToast.show();

        }
    }

    private void completeOnlineOfflineInspection(String flag, int nav_sync, int inspection1, String completed_on, int ins1_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteGermination(nav_sync, inspection1, scheduler_no.toLowerCase(), germination_inspection_table.get(0).getProduction_lot_no(), completed_on, ins1_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspGermination(scheduler_no, germination_inspection_table.get(0).getProduction_lot_no(), 1, nav_sync_error);
            } else {
                int comple_result = inspection_oneHeaderDao.updateOnServerCompleteGermination(nav_sync, inspection1, scheduler_no, germination_inspection_table.get(0).getProduction_lot_no(), completed_on, ins1_sync_with_server);
                if (comple_result > 0) {
                    Log.e("comp", String.valueOf(comple_result));
                }
            }
            insertGerminationInspectionLine(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
        }
    }

    private List<String> imageEncodList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_SINGLE && resultCode == RESULT_OK && null != data) {
            if (data.getData() != null) {
                Uri mImageUri = data.getData();
                selected_file_path = FilePath.getPath(getActivity(), mImageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                try {
                    Bitmap bm = BitmapFactory.decodeFile(selected_file_path, options);
                    setBitmapImage(bm, this.selected_file_path);
                } catch (Exception e) {
                    Log.e("image_crash", e.getMessage());
                }
            }
        } else {
            Toast.makeText(getActivity(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setBitmapImage(Bitmap img_file, String selected_file_path) {
        if (img_file != null) {
            File file = new File(selected_file_path);
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;
            if (fileSizeInMB > 2) {
                clear_image_btn.setVisibility(View.GONE);
                germinationImageView.setVisibility(View.GONE);
                germinationImage_layout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "File size must be less than 2MB.", Toast.LENGTH_SHORT).show();
            } else if (imageEncodList.size() != 2) {
                imageEncodList.add(selected_file_path);
                germinationImage_layout.setVisibility(View.VISIBLE);
                clear_image_btn.setVisibility(View.VISIBLE);
                germinationImageView.setVisibility(View.VISIBLE);
                germinationImageView.setImageBitmap(img_file);
                clear_image_btn.setOnClickListener(v -> {
                    germinationImageView.setImageBitmap(null);
                    imageEncodList.clear();
                    germinationImage_layout.setVisibility(View.GONE);
                    clear_image_btn.setVisibility(View.GONE);
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    PlantingLineLotListTable plantingLineLotListTable;
    private String getFemaleSowingDate() {
        if (production_lot_no != null) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                plantingLineLotListTable = plantingLineLotListDao.getFemaleSowingDate(production_lot_no);
               // PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getFemaleSowingDate(production_lot_no);
                String date = plantingLineLotListTable.getSowing_Date_Female();
                String date_sub_string = date.substring(0, 10);
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //Signature by Arjun sir/ Abhit
                Date steepingdate = formatter.parse(date_sub_string);
                Calendar ca = Calendar.getInstance();
                ca.setTime(steepingdate);
                ca.add(Calendar.DAY_OF_YEAR, 15);
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
                        germinationImage_layout.setVisibility(View.VISIBLE);
                        germinationImageView.setVisibility(View.VISIBLE);
                        Glide.with(getActivity())
                                .load("https://hytechdev.pristinefulfil.com/api/Inspection/Get_Image?id="+getImageId) // image url
                                .placeholder(R.drawable.noimage1) // any placeholder to load at start
                                .into(germinationImageView);
                    } else {
                        progressDialogLoading.hideDialog();
                        //Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
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

