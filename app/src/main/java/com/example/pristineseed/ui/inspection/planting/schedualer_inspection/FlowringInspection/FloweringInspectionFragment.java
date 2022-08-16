package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.FlowringInspection;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
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
import com.example.pristineseed.global.CustomTimePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.MinMAXFilter;
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
import java.text.ParseException;
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
            ed_seed_setting, ed_grain_remark, ac_crop_stage,ed_target_date_of_dataselling,ed_actual_date_dataselling,ed_net_deviation_days,ed_date_first_pass,
            ed_standing_acres,ed_pld_acres,ed_net_acres,ed_silk_first_pass,ed_silk_final_pass,ed_male_sheduling_first,ed_male_sheduling_second,ed_date_second_pass,ed_silk_second_pass,ed_date_final_pass,ed_male_sheduling_final,ed_date_first_roughing,
            ed_off_type_roughing_1,ed_no_off_types_roughing1,ed_no_off_types_roughing2,ed_no_off_types_roughing3,ed_date_second_roughing,ed_date_third_roughing,ed_off_type_roughing_2,ed_off_type_roughing_3;
    private TextInputLayout tv_iso_dis_show, tv_iso_time_show, tv_iso_grain_show,ac_pld_reason_layout;

    private AutoCompleteTextView ac_pest, ac_desease, ac_crop_condn, ac_isolation,ac_pld_reason,ac_male_female_roughing1,ac_male_female_roughing2,ac_male_female_roughing3,ac_pest_insfestation,ac_diseases_insfestation;

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
        ac_pest_insfestation = view.findViewById(R.id.ac_pest_insfestation);
        ac_diseases_insfestation = view.findViewById(R.id.ac_diseases_insfestation);

        ac_male_female_roughing1 = view.findViewById(R.id.ac_male_female_roughing1);
        ac_male_female_roughing2 = view.findViewById(R.id.ac_male_female_roughing2);
        ac_male_female_roughing3 = view.findViewById(R.id.ac_male_female_roughing3);

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
        // todo new fields initialization.......................
        ed_standing_acres=view.findViewById(R.id.ed_standing_acres);
        ed_pld_acres=view.findViewById(R.id.ed_pld_acres);
        ed_net_acres=view.findViewById(R.id.ed_net_acres);
        ac_pld_reason=view.findViewById(R.id.ac_pld_reason);
        ac_pld_reason_layout=view.findViewById(R.id.ac_pld_reason_layout);

        ed_silk_first_pass=view.findViewById(R.id.ed_silk_first_pass);
        ed_silk_second_pass=view.findViewById(R.id.ed_silk_second_pass);
        ed_silk_final_pass=view.findViewById(R.id.ed_silk_final_pass);

        ed_male_sheduling_first=view.findViewById(R.id.ed_male_sheduling_first);
        ed_male_sheduling_second=view.findViewById(R.id.ed_male_sheduling_second);
        ed_male_sheduling_final=view.findViewById(R.id.ed_male_sheduling_final);

        ed_target_date_of_dataselling=view.findViewById(R.id.ed_target_date_of_dataselling);
        ed_actual_date_dataselling=view.findViewById(R.id.ed_actual_date_dataselling);


        ed_date_first_pass=view.findViewById(R.id.ed_date_first_pass);
        ed_date_second_pass=view.findViewById(R.id.ed_date_second_pass);
        ed_date_final_pass=view.findViewById(R.id.ed_date_final_pass);

        ed_net_deviation_days=view.findViewById(R.id.ed_net_deviation_days);


        ed_date_first_roughing=view.findViewById(R.id.ed_date_first_roughing);
        ed_date_second_roughing=view.findViewById(R.id.ed_date_second_roughing);
        ed_date_third_roughing=view.findViewById(R.id.ed_date_third_roughing);

        ed_off_type_roughing_1=view.findViewById(R.id.ed_off_type_roughing_1);
        ed_off_type_roughing_2=view.findViewById(R.id.ed_off_type_roughing_2);
        ed_off_type_roughing_3=view.findViewById(R.id.ed_off_type_roughing_3);

        ed_no_off_types_roughing1=view.findViewById(R.id.ed_no_off_types_roughing1);
        ed_no_off_types_roughing2=view.findViewById(R.id.ed_no_off_types_roughing2);
        ed_no_off_types_roughing3=view.findViewById(R.id.ed_no_off_types_roughing3);

        //todo  Assigning filters
       /* ed_polln_shed_pr.setFilters( new InputFilter[]{ new MinMAXFilter( "1" , "99" )}) ;
        //ed_polln_shed_pr.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100")}) ;
        ed_silk_first_pass.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
        ed_silk_second_pass.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
        ed_silk_final_pass.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
        ed_male_sheduling_first.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
        ed_male_sheduling_second.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
        ed_male_sheduling_final.setFilters( new InputFilter[]{ new MinMAXFilter( "0" , "100" )}) ;
*/



        back_press_img.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });


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
            public void afterTextChanged(Editable editable) {

            }
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
        List<String> pestList = Arrays.asList(CommonData.pest);
        List<String> pest_inflation = Arrays.asList(CommonData.pest_infalation);
        List<String> desease_inflation = Arrays.asList(CommonData.desease_infalation);
        List<String> pldReasonList = Arrays.asList(CommonData.pld);
        List<String> male_female = Arrays.asList(CommonData.male_female);

        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);

        ItemAdapter desease_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_desease.setAdapter(desease_adapter);

        ItemAdapter ac_pest_inflation_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pest_inflation);
        ac_pest_insfestation.setAdapter(ac_pest_inflation_adapter);

        ItemAdapter ac_desease_inflation_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, desease_inflation);
        ac_diseases_insfestation.setAdapter(ac_desease_inflation_adapter);

        ItemAdapter pld_reason_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pldReasonList);
        ac_pld_reason.setAdapter(pld_reason_adapter);

        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, pestList);
        ac_pest.setAdapter(pest_adapter);
        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, isoList);
        ac_isolation.setAdapter(iso_adapter);

        ItemAdapter cropAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_condn.setAdapter(cropAdapter);

        ItemAdapter male_female_adapter1 = new ItemAdapter(getActivity(), R.layout.android_item_view, male_female);
        ac_male_female_roughing1.setAdapter(male_female_adapter1);

        ItemAdapter male_female_adapter2 = new ItemAdapter(getActivity(), R.layout.android_item_view, male_female);
        ac_male_female_roughing2.setAdapter(male_female_adapter2);

        ItemAdapter male_female_adapter3 = new ItemAdapter(getActivity(), R.layout.android_item_view, male_female);
        ac_male_female_roughing3.setAdapter(male_female_adapter3);

        ac_crop_stage.setText("Flowering");

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

        ed_actual_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            return true;
        });

        ed_target_date_of_dataselling.setOnTouchListener((view2, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_target_date_of_dataselling);
            return true;
        });

        ed_actual_date_dataselling.setOnTouchListener((view3, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date_dataselling);
            return true;
        });

        //todo for calculate for net deviation days...........................
        ed_actual_date_dataselling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateNoOfDays(ed_target_date_of_dataselling.getText().toString(), ed_actual_date_dataselling.getText().toString(), ed_net_deviation_days);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_date_first_pass.setOnTouchListener((view4, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_first_pass);
            return true;
        });

        ed_date_second_pass.setOnTouchListener((view5, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_second_pass);
            return true;
        });

        ed_date_final_pass.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_final_pass);
            return true;
        });

        ed_date_of_insp.setOnTouchListener((view6, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });

        ed_date_first_roughing.setOnTouchListener((view7, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_first_roughing);
            return true;
        });

        ed_date_second_roughing.setOnTouchListener((view8, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_second_roughing);
            return true;
        });

        ed_date_third_roughing.setOnTouchListener((view9, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_third_roughing);
            return true;
        });

        //todo for show recommended date from local database................
        ed_remnd_date.setText(getFemaleSowingDate());
        ed_standing_acres.setText(getStandingAcres());

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

            ac_pest_insfestation.setEnabled(false);
            ac_pest_insfestation.setDropDownHeight(0);
            ac_pest_insfestation.setFocusable(false);
            ac_pest_insfestation.setFocusableInTouchMode(false);

            ac_diseases_insfestation.setEnabled(false);
            ac_diseases_insfestation.setDropDownHeight(0);
            ac_diseases_insfestation.setFocusable(false);
            ac_diseases_insfestation.setFocusableInTouchMode(false);

            ac_male_female_roughing1.setEnabled(false);
            ac_male_female_roughing1.setDropDownHeight(0);
            ac_male_female_roughing1.setFocusable(false);
            ac_male_female_roughing1.setFocusableInTouchMode(false);

            ac_male_female_roughing2.setEnabled(false);
            ac_male_female_roughing2.setDropDownHeight(0);
            ac_male_female_roughing2.setFocusable(false);
            ac_male_female_roughing2.setFocusableInTouchMode(false);

            ac_male_female_roughing3.setEnabled(false);
            ac_male_female_roughing3.setDropDownHeight(0);
            ac_male_female_roughing3.setFocusable(false);
            ac_male_female_roughing3.setFocusableInTouchMode(false);

            ed_target_date_of_dataselling.setEnabled(false);
            ed_target_date_of_dataselling.setFocusableInTouchMode(false);

            ed_actual_date_dataselling.setEnabled(false);
            ed_actual_date_dataselling.setFocusableInTouchMode(false);

            ed_net_deviation_days.setEnabled(false);
            ed_net_deviation_days.setFocusableInTouchMode(false);

            ed_date_first_pass.setEnabled(false);
            ed_date_first_pass.setFocusableInTouchMode(false);

            ed_date_second_pass.setEnabled(false);
            ed_date_second_pass.setFocusableInTouchMode(false);

            ed_date_final_pass.setEnabled(false);
            ed_date_final_pass.setFocusable(false);
            ed_date_final_pass.setFocusableInTouchMode(false);

            ed_silk_first_pass.setEnabled(false);
            ed_silk_first_pass.setFocusable(false);
            ed_silk_first_pass.setFocusableInTouchMode(false);//

            ed_silk_second_pass.setEnabled(false);
            ed_silk_second_pass.setFocusable(false);
            ed_silk_second_pass.setFocusableInTouchMode(false);

            ed_silk_final_pass.setEnabled(false);
            ed_silk_final_pass.setFocusable(false);
            ed_silk_final_pass.setFocusableInTouchMode(false);

            ed_male_sheduling_first.setEnabled(false);
            ed_male_sheduling_first.setFocusable(false);
            ed_male_sheduling_first.setFocusableInTouchMode(false);

            ed_male_sheduling_second.setEnabled(false);
            ed_male_sheduling_second.setFocusable(false);
            ed_male_sheduling_second.setFocusableInTouchMode(false);

            ed_male_sheduling_final.setEnabled(false);
            ed_male_sheduling_final.setFocusable(false);
            ed_male_sheduling_final.setFocusableInTouchMode(false);

            ed_date_first_roughing.setEnabled(false);
            ed_date_first_roughing.setFocusable(false);
            ed_date_first_roughing.setFocusableInTouchMode(false);

            ed_date_second_roughing.setEnabled(false);
            ed_date_second_roughing.setFocusable(false);
            ed_date_second_roughing.setFocusableInTouchMode(false);

            ed_date_third_roughing.setEnabled(false);
            ed_date_third_roughing.setFocusable(false);
            ed_date_third_roughing.setFocusableInTouchMode(false);

            ed_no_off_types_roughing1.setEnabled(false);
            ed_no_off_types_roughing1.setFocusable(false);
            ed_no_off_types_roughing1.setFocusableInTouchMode(false);

            ed_no_off_types_roughing2.setEnabled(false);
            ed_no_off_types_roughing2.setFocusable(false);
            ed_no_off_types_roughing2.setFocusableInTouchMode(false);

            ed_no_off_types_roughing3.setEnabled(false);
            ed_no_off_types_roughing3.setFocusable(false);
            ed_no_off_types_roughing3.setFocusableInTouchMode(false);

            ed_off_type_roughing_1.setEnabled(false);
            ed_off_type_roughing_1.setFocusable(false);
            ed_off_type_roughing_1.setFocusableInTouchMode(false);

            ed_off_type_roughing_2.setEnabled(false);
            ed_off_type_roughing_2.setFocusable(false);
            ed_off_type_roughing_2.setFocusableInTouchMode(false);

            ed_off_type_roughing_3.setEnabled(false);
            ed_off_type_roughing_3.setFocusable(false);
            ed_off_type_roughing_3.setFocusableInTouchMode(false);

            ed_standing_acres.setEnabled(false);
            ed_standing_acres.setFocusable(false);
            ed_standing_acres.setFocusableInTouchMode(false);

            ed_pld_acres.setEnabled(false);
            ed_pld_acres.setFocusable(false);
            ed_pld_acres.setFocusableInTouchMode(false);

            ed_net_acres.setEnabled(false);
            ed_net_acres.setFocusable(false);
            ed_net_acres.setFocusableInTouchMode(false);

            ac_pld_reason.setEnabled(false);
            ac_pld_reason.setDropDownHeight(0);
            ac_pld_reason.setFocusable(false);
            ac_pld_reason.setFocusableInTouchMode(false);


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
                ed_top_dressing_bags.setText(floweringInspectionTable.get(0).getSecond_top_dressing_bags());
                ed_pest_remark.setText(floweringInspectionTable.get(0).getPest_remarks());
                ac_crop_condn.setText(floweringInspectionTable.get(0).getCrop_condition());
                ed_deease_remark.setText(floweringInspectionTable.get(0).getDiseases_remarks());

                ed_polln_shed_pr.setText(floweringInspectionTable.get(0).getPollen_shedding_plants_per());

                if (floweringInspectionTable.get(0).getRecommended_date() != null) {
                    ed_remnd_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getRecommended_date()));
                }
                if (floweringInspectionTable.get(0).getActual_date() != null) {
                    ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getActual_date()));
                }

                ac_pest_insfestation.setText(floweringInspectionTable.get(0).getPest_infestation_level());
                ac_diseases_insfestation.setText(floweringInspectionTable.get(0).getDisease_infestation_level());
                ed_standing_acres.setText(floweringInspectionTable.get(0).getStanding_acres());
                ed_pld_acres.setText(floweringInspectionTable.get(0).getPld_acre());
                ed_net_acres.setText(floweringInspectionTable.get(0).getNet_acre());


                if(floweringInspectionTable.get(0).getPld_reason().length()>0) {
                    try {
                        ac_pld_reason.setText(floweringInspectionTable.get(0).getPld_reason());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    ac_pld_reason_layout.setVisibility(View.GONE);
                }

               ed_target_date_of_dataselling.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getTarget_date_of_detasseling()));
               ed_actual_date_dataselling.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getActual_date_of_detasseling()));

               ed_net_deviation_days.setText(floweringInspectionTable.get(0).getNet_deviation_days());

               ed_date_first_pass.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_1st_pass()));
               ed_date_second_pass.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_2nd_pass()));
               ed_date_final_pass.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_final_pass()));

               ed_silk_first_pass.setText(floweringInspectionTable.get(0).getPrcnt_of_silk_1st_pass());
               ed_silk_second_pass.setText(floweringInspectionTable.get(0).getPrcnt_of_silk_2nd_pass());
               ed_silk_final_pass.setText(floweringInspectionTable.get(0).getPrcnt_of_Silk_final_pass());

               ed_male_sheduling_first.setText(floweringInspectionTable.get(0).getPrcnt_of_male_shedding_1st_pass());
               ed_male_sheduling_second.setText(floweringInspectionTable.get(0).getPrcnt_of_male_shedding_2nd_pass());
               ed_male_sheduling_final.setText(floweringInspectionTable.get(0).getPrcnt_of_Male_Shedding_final_pass());

               ed_date_first_roughing.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_1st_Roughing()));
               ed_date_second_roughing.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_Roughing_2()));
               ed_date_third_roughing.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(floweringInspectionTable.get(0).getDate_Roughing_3()));

               ed_off_type_roughing_1.setText(floweringInspectionTable.get(0).getType_of_Offtype_Roughing_1());
               ed_off_type_roughing_2.setText(floweringInspectionTable.get(0).getType_of_Offtype_Roughing_2());
               ed_off_type_roughing_3.setText(floweringInspectionTable.get(0).getType_of_Offtype_Roughing_3());

               ac_male_female_roughing1.setText(floweringInspectionTable.get(0).getIn_Male_or_Female_Roughing_1());
               ac_male_female_roughing2.setText(floweringInspectionTable.get(0).getIn_Male_or_Female_Roughing_2());
               ac_male_female_roughing3.setText(floweringInspectionTable.get(0).getIn_Male_or_Female_Roughing_3());

               ed_no_off_types_roughing1.setText(floweringInspectionTable.get(0).getNo_of_Off_types_Roughing_1());
               ed_no_off_types_roughing2.setText(floweringInspectionTable.get(0).getNo_of_Off_types_Roughing_2());
               ed_no_off_types_roughing3.setText(floweringInspectionTable.get(0).getNo_of_Off_types_Roughing_3());


                if(floweringInspectionTable.get(0).getAttachment()!=null) {
                    image_layout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(getActivity()).clearDiskCache();
                            }
                        }).start();
                        String file_attachment = floweringInspectionTable.get(0).getAttachment();

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
                                                    // any placeholder to load at start
                                                    .into(imageView);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .placeholder(R.drawable.noimage1)
                                    .into(imageView);
                        }
                        catch (Exception e){
                            Glide.with(getActivity())
                                    .load(ApiUtils.BASE_URL + "/api/Inspection/Get_Image?id="+file_attachment) // image urlApiUtils.BASE_URL + "/api/Inspection/Get_Image?id=" +
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.noimage1)
                                    // any placeholder to load at start
                                    .into(imageView);
                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                else {
                    MDToast.makeText(getActivity(), "no image", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
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


            //todo new fields added.................................
            floweringInspectionModel.pest_infestation_level = ac_pest_insfestation.getText().toString().trim();
            floweringInspectionModel.disease_infestation_level = ac_diseases_insfestation.getText().toString().trim();

            floweringInspectionModel.standing_acres = ed_standing_acres.getText().toString().trim();
            if (!ed_pld_acres.getText().toString().trim().equalsIgnoreCase("")) {
                String pldAcres = String.valueOf(Float.parseFloat(ed_pld_acres.getText().toString().trim()));
                floweringInspectionModel.pld_acre = !pldAcres.equalsIgnoreCase("") ? pldAcres : "0.0";
            } else {
                floweringInspectionModel.pld_acre = "0.0";
            }

            floweringInspectionModel.pld_reason = ac_pld_reason.getText().toString().trim();

            floweringInspectionModel.prcnt_of_silk_1st_pass = ed_silk_first_pass.getText().toString().trim();
            floweringInspectionModel.prcnt_of_silk_2nd_pass = ed_silk_second_pass.getText().toString().trim();
            floweringInspectionModel.prcnt_of_Silk_final_pass = ed_silk_final_pass.getText().toString().trim();

            floweringInspectionModel.prcnt_of_male_shedding_1st_pass = ed_male_sheduling_first.getText().toString().trim();
            floweringInspectionModel.prcnt_of_male_shedding_2nd_pass = ed_male_sheduling_second.getText().toString().trim();
            floweringInspectionModel.prcnt_of_Male_Shedding_final_pass = ed_male_sheduling_final.getText().toString().trim();

            floweringInspectionModel.target_date_of_detasseling = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_target_date_of_dataselling.getText().toString().trim());
            floweringInspectionModel.actual_date_of_detasseling = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date_dataselling.getText().toString().trim());



            floweringInspectionModel.date_1st_pass = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_first_pass.getText().toString().trim());
            floweringInspectionModel.date_2nd_pass = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_second_pass.getText().toString().trim());
            floweringInspectionModel.Date_final_pass = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_final_pass.getText().toString().trim());

            floweringInspectionModel.net_deviation_days = ed_net_deviation_days.getText().toString().trim();

            floweringInspectionModel.Date_1st_Roughing = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_first_roughing.getText().toString().trim());
            floweringInspectionModel.Date_Roughing_2 = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_second_roughing.getText().toString().trim());
            floweringInspectionModel.Date_Roughing_3 = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_third_roughing.getText().toString().trim());

            floweringInspectionModel.Type_of_Offtype_Roughing_1 = ed_off_type_roughing_1.getText().toString().trim();
            floweringInspectionModel.Type_of_Offtype_Roughing_2 = ed_off_type_roughing_2.getText().toString().trim();
            floweringInspectionModel.Type_of_Offtype_Roughing_3 = ed_off_type_roughing_3.getText().toString().trim();

            floweringInspectionModel.no_of_Off_types_Roughing_1 = ed_no_off_types_roughing1.getText().toString().trim();
            floweringInspectionModel.no_of_Off_types_Roughing_2 = ed_no_off_types_roughing2.getText().toString().trim();
            floweringInspectionModel.no_of_Off_types_Roughing_3 = ed_no_off_types_roughing3.getText().toString().trim();

            floweringInspectionModel.In_Male_or_Female_Roughing_1 = ac_male_female_roughing1.getText().toString().trim();
            floweringInspectionModel.In_Male_or_Female_Roughing_2 = ac_male_female_roughing2.getText().toString().trim();
            floweringInspectionModel.In_Male_or_Female_Roughing_3 = ac_male_female_roughing3.getText().toString().trim();

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
                                    floweringInspectionModel.attachment = inserResponseList.get(0).attachment;
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

    //todo for get date local database.................
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
                        Glide.with(getActivity())//"https://hytechdev.pristinefulfil.com
                                .load(ApiUtils.BASE_URL+"/api/Inspection/Get_Image?id="+getImageId) // image url
                                .placeholder(R.drawable.noimage1) // any placeholder to load at start
                                .into(imageView);
                    } else {
                        progressDialogLoading.hideDialog();
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

    //todo for net deviation target days.............................
    void calculateNoOfDays(String target_date, String actual_date, TextInputEditText et_calulate_days) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = myFormat.parse(target_date);
            Date date2 = myFormat.parse(actual_date);
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = (hours / 24) + 1;

            et_calulate_days.setText(String.valueOf(days));
            Log.e("days", String.valueOf(days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
