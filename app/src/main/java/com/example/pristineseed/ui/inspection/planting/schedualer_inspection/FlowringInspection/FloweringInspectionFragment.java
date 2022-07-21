package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.FlowringInspection;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
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
import com.example.pristineseed.global.CustomTimePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FloweringInspectionFragment extends Fragment {
    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center, tv_farmer_name, village_address, tv_prod_lot_no,
            tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name, tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed,
            tv_crop_type, posting_error, tv_item_name;

    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;
    private SessionManagement sessionManagement;

    private TextInputEditText ed_other_types, ed_date_of_insp, ed_pollen_shedder, ed_remnd_date, ed_actual_date,
            ed_top_dressing, ed_top_dressing_bags, ed_iso_dist, ed_iso_tym, ed_pest_remark, ed_deease_remark, ed_polln_shed_pr, ed_seed_stng_per,
            ed_seed_setting, ed_grain_remark, ac_crop_stage;
    private TextInputLayout tv_iso_dis_show, tv_iso_time_show, tv_iso_grain_show;

    private AutoCompleteTextView ac_pest, ac_desease, ac_crop_condn, ac_isolation;

    private Chip add_attachment;
    private int PICK_IMAGE_SINGLE = 1;
    private ImageView imageView;
    private FrameLayout back_press_img;
    private Chip clear_image_btn;
    private String selected_file_path = "";
    private LinearLayout image_layout;
    private String iso_empty_value;
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
        return inflater.inflate(R.layout.add_lot_wise_flowering_inspection_field_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        insertFloweringInspectionLine(new ArrayList<>());
        btn_save_record.setOnClickListener(v -> {
            saveFloweringInspRecord();
        });
        bt_complete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (floweringInspectionTable != null && floweringInspectionTable.size() > 0) {
                            completeFloweringInspection();
                        } else {
                            StaticMethods.showMDToast(getActivity(), "First insert line.", MDToast.TYPE_ERROR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        add_attachment.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }

    private void initView(View view) {
        tv_date = view.findViewById(R.id.tv_date);
        tv_season = view.findViewById(R.id.tv_season);
        tv_season_name = view.findViewById(R.id.season_name);
        tv_prod_cent_name = view.findViewById(R.id.prod_cent_name);
        tv_prod_center = view.findViewById(R.id.prod_center);
        tv_farmer_name = view.findViewById(R.id.farmer_name);
        village_address = view.findViewById(R.id.vill_address);
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
        back_press_img = view.findViewById(R.id.close_dilog_bt);
        tv_item_name = view.findViewById(R.id.item_name);
        bt_complete = view.findViewById(R.id.btn_cmplt);
        btn_save_record = view.findViewById(R.id.btn_save_record);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        tv_iso_dis_show = view.findViewById(R.id.iso_dis_show);
        tv_iso_time_show = view.findViewById(R.id.iso_time_show);
        tv_iso_grain_show = view.findViewById(R.id.iso_grain_show);
        ed_grain_remark = view.findViewById(R.id.grain_remark);
        image_layout = view.findViewById(R.id.image_layout1);

        //find input field.....
        ed_date_of_insp = view.findViewById(R.id.ed_date_of_insp);
        ed_pollen_shedder = view.findViewById(R.id.pollen_shedder);
        ed_other_types = view.findViewById(R.id.other_types);
        ac_pest = view.findViewById(R.id.ac_pest);
        ac_desease = view.findViewById(R.id.ac_desease);
        ed_remnd_date = view.findViewById(R.id.ed_remnd_date);
        ed_actual_date = view.findViewById(R.id.ed_actual_date);
        ac_isolation = view.findViewById(R.id.ac_isolation);
        ac_crop_stage = view.findViewById(R.id.crop_stage);
        ed_top_dressing = view.findViewById(R.id.ed_top_dressing);
        ed_top_dressing_bags = view.findViewById(R.id.ed_top_dressing_bags);
        ed_iso_dist = view.findViewById(R.id.ed_iso_dist);
        ed_iso_tym = view.findViewById(R.id.ed_iso_tym);
        ed_pest_remark = view.findViewById(R.id.pest_remark);
        ac_crop_condn = view.findViewById(R.id.crop_condn);
        ed_deease_remark = view.findViewById(R.id.deease_remark);
        ed_polln_shed_pr = view.findViewById(R.id.ed_polln_shed_pr);

        back_press_img.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        try {
            //set header data....
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
            // set scheduler line data....
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            village_address.setText(schedulerInspectionLineTable.getGrower_village() + "," +
                    schedulerInspectionLineTable.getGrower_district() + ","
                    + schedulerInspectionLineTable.getGrower_city() + ","
                    + schedulerInspectionLineTable.getGrower_taluka_mandal());

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

            if (schedulerInspectionLineTable.getSowing_date_male() != null) {
                tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_male.setText("");
            }
            if (schedulerInspectionLineTable.getSowing_date_male() != null) {
                tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_female.setText("");
            }
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_name() + "(" + schedulerInspectionLineTable.getOrganizer_code() + ")");
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> isoList = Arrays.asList(CommonData.isolation_);
        List<String> deaseasList = Arrays.asList(CommonData.desease);

        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);

        ItemAdapter desease_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_desease.setAdapter(desease_adapter);

        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_pest.setAdapter(pest_adapter);
        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, isoList);
        ac_isolation.setAdapter(iso_adapter);

        ItemAdapter cropAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_condn.setAdapter(cropAdapter);

        ac_crop_stage.setText("Flowering");

        ac_isolation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    iso_empty_value = "";
                    ac_isolation.setText(iso_empty_value);
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

        ed_actual_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            return true;
        });

        ed_date_of_insp.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });

        ed_remnd_date.setText(getFemaleSowingDate());

        ed_iso_tym.setOnTouchListener((v, motionEvent) -> {
            new CustomTimePicker(getActivity()).showDialog(ed_iso_tym);
            return true;
        });

        ed_seed_setting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_seed_stng_per.setVisibility(View.VISIBLE);
            }
        });

        add_attachment.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }

    private List<FloweringInspectionTable> floweringInspectionTable = new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertFloweringInspectionLine(List<FloweringInspectionModel> floweringInspectionModelList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            FloweringInspectionDao floweringInspectionDao = db.floweringInspectionDao();
            if (floweringInspectionModelList != null && floweringInspectionModelList.size() > 0) {
                for (int i = 0; i < floweringInspectionModelList.size(); i++) {
                    FloweringInspectionTable floweringInspectionTable = FloweringInspectionTable.inertFloweringInspection(floweringInspectionModelList.get(0));
                    if (floweringInspectionDao.isDataExist(floweringInspectionTable.getProduction_lot_no()) > 0) {
                        floweringInspectionDao.update(floweringInspectionTable);
                    } else if (floweringInspectionTable.getProduction_lot_no() != null || floweringInspectionTable.getScheduler_no() != null) {
                        floweringInspectionDao.insert(floweringInspectionTable);
                    }
                }
            }

            floweringInspectionTable = floweringInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
            Log.e("log_e", e.getMessage());
        } finally {
            db.close();
            db.destroyInstance();
            if (floweringInspectionTable != null) {
                if (scheduler_line_header_data.getIns6_sync_with_server() > 0 || scheduler_line_header_data.getInspection_6() > 0) {
                    if (scheduler_line_header_data.getInspection_6() > 0 && scheduler_line_header_data.getIns6_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp6_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp6_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    diableAllInputField();
                } else {
                    bt_complete.setVisibility(View.VISIBLE);
                    btn_save_record.setVisibility(View.VISIBLE);
                }
                showRefelectedFieldData();
            } else {
                btn_save_record.setVisibility(View.VISIBLE);
                if (scheduler_line_header_data.getIns6_sync_with_server() > 0 || scheduler_line_header_data.getInspection_6() > 0) {
                    if (scheduler_line_header_data.getInspection_6() > 0 && scheduler_line_header_data.getIns6_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp6_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp6_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    diableAllInputField();
                }
            }

        }
    }

    private void diableAllInputField() {
        try {
            ed_date_of_insp.setEnabled(false);
            ed_date_of_insp.setFocusable(false);
            ed_pollen_shedder.setFocusable(false);
            ed_pollen_shedder.setEnabled(false);
            ed_other_types.setEnabled(false);
            ac_pest.setEnabled(false);
            ac_pest.setDropDownHeight(0);
            ac_pest.setFocusableInTouchMode(false);
            ac_pest.setFocusable(false);
            ac_desease.setEnabled(false);
            ac_desease.setDropDownHeight(0);
            ac_desease.setFocusable(false);
            ed_remnd_date.setEnabled(false);
            ed_remnd_date.setFocusableInTouchMode(false);
            ed_actual_date.setEnabled(false);
            ed_actual_date.setFocusable(false);
            ac_isolation.setEnabled(false);
            ac_isolation.setDropDownHeight(0);
            ac_isolation.setFocusableInTouchMode(false);
            ac_isolation.setFocusable(false);
            ac_crop_stage.setEnabled(false);
            ac_crop_stage.setFocusableInTouchMode(false);
            ac_crop_stage.setFocusable(false);
            ed_top_dressing.setEnabled(false);
            ed_top_dressing.setFocusable(false);
            ed_top_dressing_bags.setEnabled(false);
            ed_iso_dist.setEnabled(false);
            ed_iso_tym.setEnabled(false);
            ed_pest_remark.setEnabled(false);
            ac_crop_condn.setEnabled(false);
            ac_crop_condn.setDropDownHeight(0);
            ac_crop_condn.setFocusableInTouchMode(false);
            ed_deease_remark.setEnabled(false);
            ed_polln_shed_pr.setEnabled(false);

            add_attachment.setEnabled(false);
            ed_seed_setting.setEnabled(false);
            ed_seed_setting.setFocusable(false);
            ed_seed_stng_per.setEnabled(false);
            ed_seed_stng_per.setFocusable(false);

            disableImageBtn();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void showRefelectedFieldData() {
        if (floweringInspectionTable != null && floweringInspectionTable.size() > 0) {
            try {
                if (floweringInspectionTable.get(0).getDate_of_inspection() != null) {
                    ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_of_inspection()));
                }
                ed_pollen_shedder.setText(floweringInspectionTable.get(0).getPollen_shedders());
                ed_other_types.setText(floweringInspectionTable.get(0).getOther_types());
                ac_pest.setText(floweringInspectionTable.get(0).getPest());
                ac_desease.setText(floweringInspectionTable.get(0).getDiseases());
                if (floweringInspectionTable.get(0).getIsolation() != null) {
                    ac_isolation.setText(floweringInspectionTable.get(0).getIsolation());
                    if (floweringInspectionTable.get(0).getIsolation().equalsIgnoreCase("Distance")) {
                        tv_iso_dis_show.setVisibility(View.VISIBLE);
                        ed_iso_dist.setText(floweringInspectionTable.get(0).getIsolation_distance());
                    }
                    if (floweringInspectionTable.get(0).getIsolation().equalsIgnoreCase("Time")) {
                        tv_iso_time_show.setVisibility(View.VISIBLE);
                        ed_iso_tym.setText(floweringInspectionTable.get(0).getIsolation_time());
                    }
                    if (floweringInspectionTable.get(0).getIsolation().equalsIgnoreCase("Grain")) {
                        tv_iso_grain_show.setVisibility(View.VISIBLE);
                        ed_grain_remark.setText(floweringInspectionTable.get(0).getGrain_remarks());
                    }
                }
                ac_crop_stage.setText(floweringInspectionTable.get(0).getCrop_stage());
                ed_top_dressing.setText(floweringInspectionTable.get(0).getSecond_top_dressing());
                ed_top_dressing_bags.setText(String.valueOf(floweringInspectionTable.get(0).getSecond_top_dressing_bags()));
                ed_pest_remark.setText(floweringInspectionTable.get(0).getPest_remarks());
                ac_crop_condn.setText(floweringInspectionTable.get(0).getCrop_condition());
                ed_deease_remark.setText(floweringInspectionTable.get(0).getDiseases_remarks());
                ed_polln_shed_pr.setText(String.valueOf(floweringInspectionTable.get(0).getPollen_shedding_plants_per()));
                if (floweringInspectionTable.get(0).getRecommended_date() != null) {
                    //ed_remnd_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getRecommended_date()));
                }
                if (floweringInspectionTable.get(0).getActual_date() != null) {
                    ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getActual_date()));
                }
                if(floweringInspectionTable.get(0).getAttachment()!=null){
                    String getImageId=floweringInspectionTable.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    Toast.makeText(getActivity(), floweringInspectionTable.get(0).getAttachment(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFloweringInspRecord() {
        FloweringInspectionModel floweringInspectionModel = new FloweringInspectionModel();
        try {
            floweringInspectionModel.scheduler_no = scheduler_no;
            floweringInspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            floweringInspectionModel.production_lot_no = production_lot_no;
            floweringInspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            floweringInspectionModel.crop_condition = ac_crop_condn.getText().toString().trim();
            floweringInspectionModel.crop_stage = ac_crop_stage.getText().toString().trim();
            floweringInspectionModel.pollen_shedders = ed_pollen_shedder.getText().toString().trim();
            floweringInspectionModel.other_types = ed_other_types.getText().toString().trim();
            floweringInspectionModel.pollen_shedding_plants_per = ed_polln_shed_pr.getText().toString().trim();
            floweringInspectionModel.pest = ac_pest.getText().toString().trim();
            floweringInspectionModel.diseases = ac_desease.getText().toString().trim();
            floweringInspectionModel.pest_remarks = ed_pest_remark.getText().toString().trim();
            floweringInspectionModel.diseases_remarks = ed_deease_remark.getText().toString().trim();
            floweringInspectionModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_remnd_date.getText().toString().trim());
            floweringInspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            floweringInspectionModel.isolation = ac_isolation.getText().toString().trim();
            floweringInspectionModel.isolation_time = ed_iso_tym.getText().toString().trim();
            floweringInspectionModel.second_top_dressing = ed_top_dressing.getText().toString().trim();
            floweringInspectionModel.second_top_dressing_bags = "";//Integer.parseInt(ed_top_dressing_bags.getText().toString().trim());
            floweringInspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();
            floweringInspectionModel.male_reciept_no = "0";
            floweringInspectionModel.female_reciept_no = "0";
            floweringInspectionModel.other_reciept_no = "0";
            floweringInspectionModel.grain_remarks = ed_grain_remark.getText().toString().trim();

            if (ac_isolation.getText().toString().equalsIgnoreCase("-")) {
                ac_isolation.setText(iso_empty_value);
            }

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                floweringInspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                floweringInspectionModel.seed_setting_prcnt = "0.0";
            }

            if (!ed_iso_dist.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_iso_dist.getText().toString().trim()));
                floweringInspectionModel.isolation_distance = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                floweringInspectionModel.isolation_distance = "0.0";
            }

            if (!ed_polln_shed_pr.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_polln_shed_pr.getText().toString().trim()));
                floweringInspectionModel.pollen_shedding_plants_per = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                floweringInspectionModel.pollen_shedding_plants_per = "0.0";
            }
            if (selected_file_path != null && !selected_file_path.equalsIgnoreCase("")) {
                String base_64_image = StaticMethods.convertBase64(selected_file_path);
                if (base_64_image != null && !base_64_image.equalsIgnoreCase("")) {
                    floweringInspectionModel.attachment = base_64_image;
                }
            } else {
                floweringInspectionModel.attachment = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        String jsonString = new Gson().toJson(floweringInspectionModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("json_flowering", asJsonObject.toString());

        if (ed_pest_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Pest Remark  value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ed_deease_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Disease Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_condn.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop condn. ", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_stage.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage. ", Toast.LENGTH_SHORT).show();
        } else if (ed_actual_date.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter actual date. ", Toast.LENGTH_SHORT).show();
        } else if (ac_isolation.getText().toString().trim().equalsIgnoreCase("-")) {
            Toast.makeText(getActivity(), "Invalid input (-) ! ", Toast.LENGTH_SHORT).show();
        } else if (ac_isolation.getText().toString().trim().equalsIgnoreCase("-")) {
            Toast.makeText(getActivity(), "Invalid input (-) ! ", Toast.LENGTH_SHORT).show();
        } else {
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.insertFloweringInsection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<FloweringInspectionModel> flowering_InspectionList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    floweringInspectionModel.syncwith_api6 = 1;
                                    floweringInspectionModel.attachment = selected_file_path;
                                    flowering_InspectionList.add(floweringInspectionModel);
                                    insertFloweringInspectionLine(flowering_InspectionList);
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
                ArrayList<FloweringInspectionModel> flowering_InspectionList = new ArrayList<>();
                floweringInspectionModel.syncwith_api6 = 0;
                floweringInspectionModel.attachment = selected_file_path;
                flowering_InspectionList.add(floweringInspectionModel);
                insertFloweringInspectionLine(flowering_InspectionList);
                StaticMethods.showMDToast(getActivity(), "insert Successful!", MDToast.TYPE_SUCCESS);
            }
        }
    }

    private void completeFloweringInspection() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VI", production_lot_no, scheduler_no);
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
                                    completeFloweringInspection("Online", 1, 1, floweringInspectionTable.get(0).getCreated_on(), 0, "");
                                    StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);
                                } else {
                                    completeFloweringInspection("Online", 0, 1, floweringInspectionTable.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
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
                    }
                }

                @Override
                public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_inspection", getActivity());
                }
            });
        } else {
            completeFloweringInspection("Offline", 0, 0, floweringInspectionTable.get(0).getCreated_on(), 1, "");
        }
    }

    private void completeFloweringInspection(String flag, int nav_sync, int inspection6, String completed_on, int ins6_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteFlowering(nav_sync, inspection6, scheduler_no, floweringInspectionTable.get(0).getProduction_lot_no(), completed_on, ins6_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderFlowering(scheduler_no, floweringInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteFlowering(0, inspection6, scheduler_no, floweringInspectionTable.get(0).getProduction_lot_no(), completed_on, ins6_sync_with_server);
                StaticMethods.showMDToast(getActivity(), " Complete successful !", MDToast.TYPE_SUCCESS);
            }
            insertFloweringInspectionLine(new ArrayList<>());
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
        try {
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
                imageView.setVisibility(View.VISIBLE);
                imageEncodList.add(selected_file_path);
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
                ca.add(Calendar.DAY_OF_YEAR, 65);

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
                        image_layout.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        Glide.with(getActivity())
                                .load("https://hytechdev.pristinefulfil.com/api/Inspection/Get_Image?id="+getImageId) // image url
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
