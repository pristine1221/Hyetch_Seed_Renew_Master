package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.MaturityInspection;

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

import com.bumptech.glide.Glide;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostFloweringDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostfloweringInspectionTable;
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
import com.example.pristineseed.model.scheduler_inspection.MaturityInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.PostFloweringInspectionModel;
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

public class MaturityInspectionFragment extends Fragment {
    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;

    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;
    private SessionManagement sessionManagement;

    private TextInputEditText ed_date_of_inspection, ed_yield_est, ed_pest_remark, ed_desease_remark, ed_remmmdn_date, ac_crop_state,
            ed_actual_date,
            ed_seed_stng_per, ed_receipt_male, ed_receipt_female, ed_receipt_other,standing_acres,pld_acres,net_acres;

    private AutoCompleteTextView ac_crop_cond, ac_pest, ac_desease, ed_abiotic_streess, ed_seed_setting,ac_pld_reason;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error;

    private ImageView imageView;
    private FrameLayout close_dilog_bt;
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
        return inflater.inflate(R.layout.add_lot_wise_maturity_inspection_field_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        insertMaturitiyInspectionLine(new ArrayList<>());

        btn_save_record.setOnClickListener(v -> {
            saveMaturityInspRecord();
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
                        if (maturityInspectionTable != null && maturityInspectionTable.size() > 0) {
                            completeFloweringInspection();
                        } else {
                            Toast.makeText(getActivity(), "first insert line", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
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
        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);
        image_layout = view.findViewById(R.id.image_layout);
        standing_acres = view.findViewById(R.id.standing_acres);
        pld_acres = view.findViewById(R.id.pld_acres);
        net_acres = view.findViewById(R.id.net_acres);

        //input field initlize .....

        ed_date_of_inspection = view.findViewById(R.id.ed_date_of_insp);
        ac_crop_cond = view.findViewById(R.id.crop_cond);
        ac_crop_state = view.findViewById(R.id.crop_stage);
        ac_pest = view.findViewById(R.id.ac_pest);
        ac_desease = view.findViewById(R.id.ac_desease);
        ac_pld_reason=view.findViewById(R.id.ac_pld_reason);
        ed_yield_est = view.findViewById(R.id.yeld_estimate);
        ed_abiotic_streess = view.findViewById(R.id.abitoic_stress);
        ed_pest_remark = view.findViewById(R.id.ed_remarks);
        ed_desease_remark = view.findViewById(R.id.ed_deses_remark);
        ed_remmmdn_date = view.findViewById(R.id.recomm_date);
        ed_actual_date = view.findViewById(R.id.actual_date);

        close_dilog_bt.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        bt_complete = view.findViewById(R.id.btn_cmplt);
        btn_save_record = view.findViewById(R.id.btn_save_record);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);

        try {
            if (scheduler_header_table.getDate() != null) {
                tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            } else {
                tv_date.setText("");
            }
            tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");

            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            village_address.setText(schedulerInspectionLineTable.getGrower_village() + "(" + schedulerInspectionLineTable.getVariety_code() + ")"
                    + schedulerInspectionLineTable.getGrower_city() + "," + schedulerInspectionLineTable.getGrower_taluka_mandal());
            tv_prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
            tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
            ed_remmmdn_date.setText(getFemaleSowingDate());

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
                tv_sd_male.setText((DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male())));
            } else {
                tv_sd_male.setText("");
            }

            if (schedulerInspectionLineTable.getSowing_date_female() != null) {
                tv_sd_female.setText((DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_female())));
            } else {
                tv_sd_female.setText("");
            }
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ed_actual_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            return true;
        });

        ed_date_of_inspection.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_inspection);
            return true;
        });
        //todo new changes auto select recommended date by recommneded days.

        List<String> deaseasList = Arrays.asList(CommonData.desease);
        List<String> abioticStressList = Arrays.asList(CommonData.abiotic_stress);

        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);
        ItemAdapter desease_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_desease.setAdapter(desease_adapter);

        ItemAdapter abioticStress_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, abioticStressList);
        ed_abiotic_streess.setAdapter(abioticStress_adapter);

        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_pest.setAdapter(pest_adapter);
        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_cond.setAdapter(iso_adapter);

        ac_crop_state.setText("Maturity");

        ItemAdapter seed_setting_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.seed_setting));
        ed_seed_setting.setAdapter(seed_setting_adapter);

        add_attachment.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
        });
    }


    private List<MaturityInspectionTable> maturityInspectionTable=new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertMaturitiyInspectionLine(ArrayList<MaturityInspectionModel> maturityInspectionModelArrayList) {

        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            MaturityInspectionDao maturityInspectionDao = db.maturityInspectionDao();
            if (maturityInspectionModelArrayList != null && maturityInspectionModelArrayList.size() > 0) {
                for (int i = 0; i < maturityInspectionModelArrayList.size(); i++) {
                    MaturityInspectionTable maturityInspectionTable = MaturityInspectionTable.insertMatuirtyInspectionDataIntoLocal(maturityInspectionModelArrayList.get(i));
                    if (maturityInspectionDao.isDataExist(maturityInspectionTable.getProduction_lot_no()) > 0) {
                        maturityInspectionDao.update(maturityInspectionTable);
                    } else if (maturityInspectionTable.getProduction_lot_no() != null || maturityInspectionTable.getScheduler_no() != null) {
                        maturityInspectionDao.insert(maturityInspectionTable);
                    }
                }
            }

            maturityInspectionTable = maturityInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);
        } catch (Exception e) {
            Log.e("log_e", e.getMessage());
        } finally {
            db.close();
            db.destroyInstance();
            if (maturityInspectionTable != null) {
                if (scheduler_line_header_data.getIns8_sync_with_server() > 0 || scheduler_line_header_data.getInspection_8() > 0) {
                    if (scheduler_line_header_data.getInspection_8() > 0 && scheduler_line_header_data.getIns8_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp8_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp8_mesage());
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
                if (scheduler_line_header_data.getIns8_sync_with_server() > 0 || scheduler_line_header_data.getInspection_8() > 0) {
                    if (scheduler_line_header_data.getInspection_8() > 0 && scheduler_line_header_data.getIns8_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp8_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp8_mesage());
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

    private void showRefelectedFieldData() {
        if (maturityInspectionTable != null && maturityInspectionTable.size() > 0) {
            if (maturityInspectionTable.get(0).getDate_of_inspection() != null) {
                ed_date_of_inspection.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(maturityInspectionTable.get(0).getDate_of_inspection()));
            }
            ac_crop_cond.setText(maturityInspectionTable.get(0).getCrop_condition());
            ac_crop_state.setText(maturityInspectionTable.get(0).getCrop_stage());
            ac_pest.setText(maturityInspectionTable.get(0).getPest());
            ac_desease.setText(maturityInspectionTable.get(0).getDiseases());
            ed_yield_est.setText(maturityInspectionTable.get(0).getYield_estimation_in_kgs());
            ed_abiotic_streess.setText(maturityInspectionTable.get(0).getAbiotic_stress());
            ed_pest_remark.setText(maturityInspectionTable.get(0).getPest_remarks());
            ed_desease_remark.setText(maturityInspectionTable.get(0).getDiseases_remarks());

            if(maturityInspectionTable.get(0).getRecommended_date()!=null){
                ed_remmmdn_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(maturityInspectionTable.get(0).getRecommended_date()));
            }
            if (maturityInspectionTable.get(0).getActual_date() != null) {
                ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(maturityInspectionTable.get(0).getActual_date()));
            }

            ed_seed_setting.setText(maturityInspectionTable.get(0).getSeed_setting());
            ed_seed_stng_per.setText(maturityInspectionTable.get(0).getSeed_setting_prcnt());
            try {
                if(maturityInspectionTable.get(0).getAttachment()!=null){
                    String getImageId=maturityInspectionTable.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    Toast.makeText(getActivity(), maturityInspectionTable.get(0).getAttachment(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void diableAllInputField() {
        ed_date_of_inspection.setEnabled(false);
        ac_crop_cond.setEnabled(false);
        ac_crop_cond.setDropDownHeight(0);
        ac_crop_cond.setFocusableInTouchMode(false);
        ac_crop_state.setEnabled(false);
        ac_crop_state.setFocusableInTouchMode(false);
        ac_pest.setEnabled(false);
        ac_pest.setDropDownHeight(0);
        ac_pest.setFocusableInTouchMode(false);
        ac_desease.setEnabled(false);
        ac_desease.setDropDownHeight(0);
        ac_desease.setFocusableInTouchMode(false);
        ed_yield_est.setEnabled(false);
        ed_abiotic_streess.setEnabled(false);
        ed_abiotic_streess.setDropDownHeight(0);
        ed_pest_remark.setEnabled(false);
        ed_desease_remark.setEnabled(false);
        ed_remmmdn_date.setEnabled(false);
        ed_remmmdn_date.setFocusable(false);
        ed_actual_date.setEnabled(false);
        ed_actual_date.setFocusable(false);
        ed_seed_setting.setEnabled(false);
        ed_seed_setting.setDropDownHeight(0);
        ed_seed_stng_per.setEnabled(false);
        ed_seed_stng_per.setFocusable(false);
        ed_seed_setting.setFocusable(false);
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


    private void saveMaturityInspRecord() {
        MaturityInspectionModel maturityInspectionModel = new MaturityInspectionModel();
        try {
            maturityInspectionModel.scheduler_no = scheduler_no;
            maturityInspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            maturityInspectionModel.production_lot_no = production_lot_no;
            maturityInspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            maturityInspectionModel.crop_condition = ac_crop_cond.getText().toString().trim();
            maturityInspectionModel.crop_stage = ac_crop_state.getText().toString().trim();
            maturityInspectionModel.abiotic_stress = ed_abiotic_streess.getText().toString().trim();
            maturityInspectionModel.remarks = ed_pest_remark.getText().toString().trim();
            maturityInspectionModel.pest = ac_pest.getText().toString().trim();
            maturityInspectionModel.pest_remarks = ed_pest_remark.getText().toString().trim();
            maturityInspectionModel.diseases = ac_pest.getText().toString().trim();
            maturityInspectionModel.diseases_remarks = ed_desease_remark.getText().toString().trim();
            maturityInspectionModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_remmmdn_date.getText().toString().trim());
            maturityInspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());
            maturityInspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();
            maturityInspectionModel.male_reciept_no = "0";
            maturityInspectionModel.female_reciept_no = "0";
            maturityInspectionModel.other_reciept_no = "0";
            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                maturityInspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                maturityInspectionModel.seed_setting_prcnt = "0.0";
            }
            if (!ed_yield_est.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_yield_est.getText().toString().trim()));
                maturityInspectionModel.yield_estimation_in_kgs = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                maturityInspectionModel.yield_estimation_in_kgs = "0.0";
            }

            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            maturityInspectionModel.attachment = base_64_image != null ? base_64_image : "";

        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        String jsonString = new Gson().toJson(maturityInspectionModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("json_flering", asJsonObject.toString());

        if (ed_pest_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Pest Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
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
                Call<List<ResponseModel>> call = mAPIService.insertMaturityInsection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<MaturityInspectionModel> maturityInspectionModelArrayList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    maturityInspectionModel.syncWithApi8 = 1;
                                    maturityInspectionModel.attachment = selected_file_path;
                                    maturityInspectionModelArrayList.add(maturityInspectionModel);
                                    insertMaturitiyInspectionLine(maturityInspectionModelArrayList);
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
                ArrayList<MaturityInspectionModel> maturityInspectionModelArrayList = new ArrayList<>();
                maturityInspectionModel.syncWithApi8 = 0;
                maturityInspectionModel.attachment = selected_file_path;
                maturityInspectionModelArrayList.add(maturityInspectionModel);
                insertMaturitiyInspectionLine(maturityInspectionModelArrayList);
                StaticMethods.showMDToast(getActivity(), "insert Successful !", MDToast.TYPE_SUCCESS);
            }
        }
    }

    private void completeFloweringInspection() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VIII", production_lot_no, scheduler_no);
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
                                    completeMaturityOnLineOfflineInspection("Online", 1, 1, maturityInspectionTable.get(0).getCreated_on(), 0, "");
                                    StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);
                                } else {
                                    completeMaturityOnLineOfflineInspection("Online", 0, 1, maturityInspectionTable.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
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
                        //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
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
            completeMaturityOnLineOfflineInspection("Offline", 0, 0, maturityInspectionTable.get(0).getCreated_on(), 1, "");
        }
    }

    private void completeMaturityOnLineOfflineInspection(String flag, int nav_sync, int inspection8, String completed_on, int ins8_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteMaturity(nav_sync, inspection8, scheduler_no, maturityInspectionTable.get(0).getProduction_lot_no(), completed_on, ins8_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspMaturity(scheduler_no, maturityInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteMaturity(0, inspection8, scheduler_no, maturityInspectionTable.get(0).getProduction_lot_no(), completed_on, ins8_sync_with_server);
                StaticMethods.showMDToast(getActivity(), "Inspection Completed !", MDToast.TYPE_SUCCESS);
            }
            insertMaturitiyInspectionLine(new ArrayList<>());
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
                ca.add(Calendar.DAY_OF_YEAR, 100);

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


