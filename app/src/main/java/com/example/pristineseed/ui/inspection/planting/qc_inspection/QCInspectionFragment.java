package com.example.pristineseed.ui.inspection.planting.qc_inspection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.home.OrderList;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Germination_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.Qc_Inspection_Model;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.header_line.ListAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class QCInspectionFragment extends Fragment {
    private TextInputEditText ed_grain_remarks, ed_date_of_insp, ed_date_of_field_updated, ed_actual_date_qc,
            ed_iso_tym, ed_seed_setting,
            ed_seed_stng_per, ed_grain_remark, ac_crop_stage;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error, tv_item_name;

    private ImageView imageView;
    private FrameLayout back_press_img;
    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;

    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;
    private AutoCompleteTextView ac_crop, ac_flag_status, ed_off_type_parent_male, off_type_female_parent, pollen_shedd_tssl, ed_nick_per, ac_crop_condtion,
            ed_border_rws, ed_wind_direction, ed_male_stand, ac_lot_recommend, ed_distance;

    private Chip add_attachment;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip clear_image_btn;
    private String selected_file_path = "";
    private AutoCompleteTextView ac_isolation;
    private TextInputLayout tv_iso_dis_show, tv_iso_time_show, tv_iso_grain_show;
    private LinearLayout image_layout;
    private String iso_empty_value = "";

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
        return inflater.inflate(R.layout.fragment_qc_inspection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inivitView(view);
        insertQCInspectionLine(new ArrayList<>());
    }

    private void inivitView(View view) {
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
        ac_isolation = view.findViewById(R.id.ac_isolation);
        ed_grain_remarks = view.findViewById(R.id.ed_grain_remark);
        tv_iso_dis_show = view.findViewById(R.id.iso_dis_show);
        tv_iso_time_show = view.findViewById(R.id.iso_time_show);
        tv_iso_grain_show = view.findViewById(R.id.iso_grain_show);
        ed_date_of_field_updated = view.findViewById(R.id.ed_date_field_updated);
        ac_crop_condtion = view.findViewById(R.id.ac_crop_condition);
        ac_crop_stage = view.findViewById(R.id.ac_crop_stage);
        ac_lot_recommend = view.findViewById(R.id.ac_lot_recommend);

        ed_grain_remark = view.findViewById(R.id.grain_remark);
        image_layout = view.findViewById(R.id.image_layout);

        bt_complete = view.findViewById(R.id.btn_cmplt);
        btn_save_record = view.findViewById(R.id.btn_save_record);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        ed_grain_remark = view.findViewById(R.id.grain_remark);

        back_press_img.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        // set scheduler line data....
        try {
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_season.setText(scheduler_header_table.getSeason());
            tv_season_name.setText(scheduler_header_table.getSeason_name());
            tv_prod_center.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_owner());
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
            if (schedulerInspectionLineTable.getSowing_date_male() != null && !schedulerInspectionLineTable.getSowing_date_male().equalsIgnoreCase("")) {
                tv_sd_male.setText(DateTimeUtilsCustome.getDateMMMDDYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_male.setText("");
            }

            if (schedulerInspectionLineTable.getSowing_date_male() != null && !schedulerInspectionLineTable.getSowing_date_male().equalsIgnoreCase("")) {
                tv_sd_female.setText(DateTimeUtilsCustome.getDateMMMDDYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_female.setText("");
            }
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //input fields......
        ed_off_type_parent_male = view.findViewById(R.id.ed_off_type_parent_male);
        off_type_female_parent = view.findViewById(R.id.off_type_female_parent);
        pollen_shedd_tssl = view.findViewById(R.id.pollen_shedd_tssl);
        ed_distance = view.findViewById(R.id.ed_distance);
        ed_date_of_insp = view.findViewById(R.id.ed_date_of_insp);
        ed_actual_date_qc = view.findViewById(R.id.ed_actual_date_qc);
        ed_iso_tym = view.findViewById(R.id.ed_iso_tym);
        ed_nick_per = view.findViewById(R.id.ed_nick_per);
        ed_border_rws = view.findViewById(R.id.ed_border_rws);
        ed_wind_direction = view.findViewById(R.id.ed_wind_direction);
        ed_male_stand = view.findViewById(R.id.ed_male_stand);
        ac_flag_status = view.findViewById(R.id.ac_flag_status);
        ac_crop = view.findViewById(R.id.ac_crop);

        List<String> isoList = Arrays.asList(CommonData.isolation_);

        ItemAdapter crop_condition_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.crop_condition));
        ac_crop_condtion.setAdapter(crop_condition_adapter);
        ac_crop_stage.setText("QC");
        ItemAdapter ac_crop_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.crop_));
        ac_crop.setAdapter(ac_crop_adapter);
        ItemAdapter ac_flag_status_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.flag_status));
        ac_flag_status.setAdapter(ac_flag_status_adapter);
        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, isoList);
        ac_isolation.setAdapter(iso_adapter);

        ItemAdapter off_type_female = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.off_type_male_female));
        off_type_female_parent.setAdapter(off_type_female);
        ItemAdapter off_type_male = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.off_type_male_female));
        ed_off_type_parent_male.setAdapter(off_type_male);

        ItemAdapter lot_recommnd_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.lot_recommnd));
        ac_lot_recommend.setAdapter(lot_recommnd_adapter);

        ItemAdapter pollen_shddr = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.pollen_shd));
        pollen_shedd_tssl.setAdapter(pollen_shddr);

        ItemAdapter iso_dis_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.iso_dis));
        ed_distance.setAdapter(iso_dis_adapter);

        ItemAdapter nick_per_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.nick_per));
        ed_nick_per.setAdapter(nick_per_adapter);

        ItemAdapter border_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.border_rows));
        ed_border_rws.setAdapter(border_adapter);

        ItemAdapter wind_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.wind_dir));
        ed_wind_direction.setAdapter(wind_adapter);

        ItemAdapter malestand_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.male_stand));
        ed_male_stand.setAdapter(malestand_adapter);

        ed_date_of_insp.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });

        ed_actual_date_qc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date_qc);
                return true;
            }
        });

        ed_date_of_field_updated.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_field_updated);
            return true;
        });

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
                    try {
                        if (qcInspectionTableList != null && qcInspectionTableList.size() > 0) {
                            completeQc();
                        } else {
                            Toast.makeText(getActivity(), "First insert line", Toast.LENGTH_SHORT).show();
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
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }

    private List<QcInspectionTable> qcInspectionTableList = null;
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertQCInspectionLine(List<Qc_Inspection_Model> qc_inspection_modelList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            QcInspectionDao qcInspectionDao = db.qcInspectionDao();
            if (qc_inspection_modelList != null && qc_inspection_modelList.size() > 0) {
                for (int i = 0; i < qc_inspection_modelList.size(); i++) {
                    QcInspectionTable qcInspectionTable = QcInspectionTable.insertOcDatafromIntoLocal(qc_inspection_modelList.get(i));
                    if (qcInspectionDao.isDataExist(qcInspectionTable.getProduction_lot_no()) > 0) {
                        qcInspectionDao.update(qcInspectionTable);
                    } else if (qcInspectionTable != null && qcInspectionTable.getProduction_lot_no() != null && qcInspectionTable.getScheduler_no() != null) {
                        qcInspectionDao.insert(qcInspectionTable);
                    }
                }
            }

            qcInspectionTableList = qcInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
            Log.e("log_e", e.getMessage());
        } finally {
            db.close();
            db.destroyInstance();
            if (qcInspectionTableList != null) {
                if (scheduler_line_header_data.getInsqc_sync_with_server() > 0 || scheduler_line_header_data.getInspection_qc() > 0) {
                    if (scheduler_line_header_data.getInspection_qc() > 0 && scheduler_line_header_data.getIns_qc_nav_sync() == 0 && scheduler_line_header_data.getNav_error_inspqc_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_inspqc_mesage());
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
                if (scheduler_line_header_data.getInsqc_sync_with_server() > 0 || scheduler_line_header_data.getInspection_qc() > 0) {
                    if (scheduler_line_header_data.getInspection_qc() > 0 && scheduler_line_header_data.getIns_qc_nav_sync() == 0 && scheduler_line_header_data.getNav_error_inspqc_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_inspqc_mesage());
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
            ed_off_type_parent_male.setEnabled(false);
            ed_off_type_parent_male.setDropDownHeight(0);
            off_type_female_parent.setEnabled(false);
            off_type_female_parent.setDropDownHeight(0);
            pollen_shedd_tssl.setEnabled(false);
            pollen_shedd_tssl.setDropDownHeight(0);
            ed_distance.setEnabled(false);
            ed_date_of_insp.setEnabled(false);
            ed_date_of_insp.setFocusable(false);
            ed_actual_date_qc.setEnabled(false);
            ed_actual_date_qc.setFocusable(false);
            ed_date_of_field_updated.setFocusable(false);
            ac_crop_condtion.setEnabled(false);
            ac_crop_condtion.setDropDownHeight(0);
            ac_crop_stage.setEnabled(false);
            ac_crop_condtion.setFocusableInTouchMode(false);
            ac_crop_stage.setFocusableInTouchMode(false);
            ac_lot_recommend.setEnabled(false);
            ac_lot_recommend.setDropDownHeight(0);
            ed_date_of_field_updated.setEnabled(false);
            ed_iso_tym.setEnabled(false);
            ed_nick_per.setEnabled(false);
            ed_nick_per.setDropDownHeight(0);
            ed_nick_per.setFocusableInTouchMode(false);
            ed_border_rws.setEnabled(false);
            ed_border_rws.setDropDownHeight(0);
            ed_wind_direction.setEnabled(false);
            ed_wind_direction.setDropDownHeight(0);
            ed_male_stand.setEnabled(false);
            ed_male_stand.setDropDownHeight(0);
            ac_flag_status.setEnabled(false);
            ac_flag_status.setDropDownHeight(0);
            ac_crop.setText(qcInspectionTableList.get(0).getCrop());
            off_type_female_parent.setEnabled(false);
            off_type_female_parent.setFocusableInTouchMode(false);
            ed_off_type_parent_male.setEnabled(false);
            ed_off_type_parent_male.setFocusableInTouchMode(false);

            ed_date_of_insp.setOnTouchListener(null);

            ed_actual_date_qc.setOnTouchListener(null);

            ed_date_of_field_updated.setOnTouchListener(null);
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
        try {
            if (qcInspectionTableList != null && qcInspectionTableList.size() > 0) {
                ed_off_type_parent_male.setText(qcInspectionTableList.get(0).getOff_type_per_female_parent());
                off_type_female_parent.setText(qcInspectionTableList.get(0).getOff_type_per_female_parent());
                pollen_shedd_tssl.setText(qcInspectionTableList.get(0).getPollen_shedders_or_shedding_tassel());
                ed_distance.setText(qcInspectionTableList.get(0).getIsolation_distance());
                ac_crop_condtion.setText(qcInspectionTableList.get(0).crop_condition);
                ac_crop_stage.setText("QC");//qcInspectionTableList.get(0).crop_stage
                ac_lot_recommend.setText(qcInspectionTableList.get(0).lot_recommend);
                ed_iso_tym.setText(qcInspectionTableList.get(0).getIsolation_time());
                ed_nick_per.setText(qcInspectionTableList.get(0).getNick_per());
                ed_border_rws.setText(qcInspectionTableList.get(0).getBorder_rows());
                ed_wind_direction.setText(qcInspectionTableList.get(0).getWind_direction());
                ed_male_stand.setText(qcInspectionTableList.get(0).getMale_stand());
                ac_flag_status.setText(qcInspectionTableList.get(0).getFlag_status());
                ac_crop.setText(qcInspectionTableList.get(0).getCrop());
                off_type_female_parent.setText(qcInspectionTableList.get(0).getOff_type_per_female_parent());
                ed_off_type_parent_male.setText(qcInspectionTableList.get(0).getOff_type_per_male_parent());
                pollen_shedd_tssl.setText(qcInspectionTableList.get(0).getPollen_shedders_or_shedding_tassel());
                ed_nick_per.setText(qcInspectionTableList.get(0).getNick_per());
                ed_iso_tym.setText(qcInspectionTableList.get(0).getIsolation_time());
                if (qcInspectionTableList.get(0).getDate_of_inspection() != null) {
                    ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(qcInspectionTableList.get(0).getDate_of_inspection()));
                }
                if (qcInspectionTableList.get(0).getDate_of_field_updated() != null) {
                    ed_date_of_field_updated.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(qcInspectionTableList.get(0).getDate_of_field_updated()));
                }
                if (qcInspectionTableList.get(0).getDate_of_actual_date() != null) {
                    ed_actual_date_qc.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(qcInspectionTableList.get(0).getDate_of_actual_date()));
                }
                try {
                    String file_attachment = qcInspectionTableList.get(0).getAttachment();

                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecord() {
        Qc_Inspection_Model qc_inspection_model = new Qc_Inspection_Model();
        try {
            qc_inspection_model.scheduler_no = scheduler_no;
            qc_inspection_model.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            qc_inspection_model.production_lot_no = production_lot_no;
            qc_inspection_model.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_of_insp.getText().toString().trim());
            qc_inspection_model.date_of_field_updated = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_of_field_updated.getText().toString());
            qc_inspection_model.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date_qc.getText().toString());
            qc_inspection_model.crop_stage = ac_crop_stage.getText().toString().trim();
            qc_inspection_model.crop_condition = ac_crop_condtion.getText().toString().trim();
            qc_inspection_model.lot_recommend = ac_lot_recommend.getText().toString().trim();
            qc_inspection_model.crop = ac_crop.getText().toString().trim();
            qc_inspection_model.off_type_per_male_parent = ed_off_type_parent_male.getText().toString().trim();
            qc_inspection_model.off_type_per_female_parent = off_type_female_parent.getText().toString().trim();
            qc_inspection_model.pollen_shedders_or_shedding_tassel = pollen_shedd_tssl.getText().toString().trim();
            qc_inspection_model.flag_status = ac_flag_status.getText().toString().trim();
            qc_inspection_model.isolation_time = ed_iso_tym.getText().toString().trim();
            qc_inspection_model.nick_per = ed_nick_per.getText().toString().trim();
            qc_inspection_model.border_rows = ed_border_rws.getText().toString().trim();
            qc_inspection_model.wind_direction = ed_wind_direction.getText().toString().trim();
            qc_inspection_model.male_stand = ed_male_stand.getText().toString().trim();
            qc_inspection_model.seed_setting = ed_seed_setting.getText().toString().trim();
            qc_inspection_model.grain_remarks = ed_grain_remarks.getText().toString().trim();
            qc_inspection_model.isolation_distance = ed_distance.getText().toString();
            qc_inspection_model.male_reciept_no = "0";
            qc_inspection_model.female_reciept_no = "0";
            qc_inspection_model.other_reciept_no = "0";

            if (ac_isolation.getText().toString().equalsIgnoreCase("-")) {
                ac_isolation.setText(iso_empty_value);
            }

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                qc_inspection_model.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                qc_inspection_model.seed_setting_prcnt = "0.0";
            }
            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            qc_inspection_model.attachment = base_64_image != null ? base_64_image : "";

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ac_crop_stage.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage.", Toast.LENGTH_SHORT).show();
        } else if (ac_crop_condtion.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please select crop condition.", Toast.LENGTH_SHORT).show();
        } else if (ac_isolation.getText().toString().trim().equalsIgnoreCase("-")) {
            Toast.makeText(getActivity(), "Invalid input (-) !", Toast.LENGTH_SHORT).show();
        } else {
            String jsonString = new Gson().toJson(qc_inspection_model);
            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            Log.e("jsonObject", String.valueOf(asJsonObject));
            InsertQCInspection(qc_inspection_model, asJsonObject);
        }
    }

    private void InsertQCInspection(Qc_Inspection_Model qc_inspection_model, JsonObject asJsonObject) {
        try {
            boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            if (network) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.insertQCInsection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<Qc_Inspection_Model> qc_inspection_modelArrayList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    qc_inspection_model.syncwithQc = 1;
                                    qc_inspection_model.attachment = selected_file_path;
                                    qc_inspection_modelArrayList.add(qc_inspection_model);
                                    insertQCInspectionLine(qc_inspection_modelArrayList);
                                    Toast.makeText(getActivity(), inserResponseList.get(0).message, Toast.LENGTH_SHORT).show();
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
                            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_qc_inspection", getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        progressDialogLoading.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_qc_inspection", getActivity());
                    }
                });
            } else {
                ArrayList<Qc_Inspection_Model> qc_inspection_modelArrayList = new ArrayList<>();
                qc_inspection_model.syncwithQc = 0;
                qc_inspection_model.attachment = selected_file_path;
                qc_inspection_modelArrayList.add(qc_inspection_model);
                insertQCInspectionLine(qc_inspection_modelArrayList);
                Toast.makeText(getActivity(), "Insert Successful !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void completeQc() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection QC", scheduler_line_header_data.getProduction_lot_no(), scheduler_line_header_data.getSchedule_no());
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
                                    completeOnlineOfflineInspection("Online", 1, 1, qcInspectionTableList.get(0).getCreated_on(), 0, "");
                                    Toast toast = Toast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 25);
                                    toast.show();
                                } else {
                                    completeOnlineOfflineInspection("Online", 0, 1, qcInspectionTableList.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
                                    Toast.makeText(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", Toast.LENGTH_SHORT).show();
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
            //todo offline...........
            completeOnlineOfflineInspection("Offline", 0, 0, qcInspectionTableList.get(0).getCreated_on(), 1, "");
            Toast.makeText(getActivity(), "Completed Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private void completeOnlineOfflineInspection(String flag, int nav_sync, int inspectionqc, String completed_on, int insqc_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteQc(nav_sync, inspectionqc, scheduler_no, qcInspectionTableList.get(0).getProduction_lot_no(), completed_on, insqc_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspQc(scheduler_no, qcInspectionTableList.get(0).getProduction_lot_no(), 1, nav_sync_error);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteQc(0, inspectionqc, scheduler_no, qcInspectionTableList.get(0).getProduction_lot_no(), completed_on, insqc_sync_with_server);
            }
            insertQCInspectionLine(new ArrayList<>());
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

}