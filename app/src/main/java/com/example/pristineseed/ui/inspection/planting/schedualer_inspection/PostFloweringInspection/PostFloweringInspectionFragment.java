package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.PostFloweringInspection;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
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
import com.example.pristineseed.global.CustomTimePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.FilePath;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class PostFloweringInspectionFragment extends Fragment {

    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name, tv_prod_center,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error, tv_item_name;


    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;
    private TextInputEditText ed_pollen_shedd, ed_ohter_type, ed_pollen_shed_plants, ed_pollen_shedd_per, ed_pest_remark, ed_desease_remark,
            ed_date_of_insp, recmnd_date, actual_date, ed_seed_stng_per, ed_receipt_male, ed_receipt_female, ed_receipt_other,ac_crop_stg;

    private AutoCompleteTextView ac_pest, ac_desease, ac_crop_cond,ed_seed_setting;
    private SessionManagement sessionManagement;
    private ImageView imageView;
    private FrameLayout back_press_img;

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
        return inflater.inflate(R.layout.post__flowering_inspection_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        insertPostFloweringInspectionLine(new ArrayList<>());

        btn_save_record.setOnClickListener(v -> {
            savePostFloweringInspRecord();
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
                        if(postfloweringInspectionTable!=null && postfloweringInspectionTable.size()>0) {
                            completePostFloweringInspection();
                        }
                        else {
                            Toast.makeText(getActivity(),"First insert line.",Toast.LENGTH_SHORT).show();
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
        back_press_img = view.findViewById(R.id.close_dilog_bt);
        posting_error = view.findViewById(R.id.posting_error);
        tv_item_name = view.findViewById(R.id.item_name);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);
        image_layout = view.findViewById(R.id.image_layout);

        back_press_img.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        bt_complete = view.findViewById(R.id.complete_btn);
        btn_save_record = view.findViewById(R.id.save_btn);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        try {
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_season.setText(scheduler_header_table.getSeason()+"("+scheduler_header_table.getSeason_name()+")");
            tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre_name()+"("+scheduler_header_table.getProduction_centre()+")");
        // set scheduler line data....
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() +"("+schedulerInspectionLineTable.getGrower_owner()+")");
            village_address.setText(schedulerInspectionLineTable.getGrower_village() +
                    schedulerInspectionLineTable.getGrower_district()
                    + schedulerInspectionLineTable.getGrower_city()
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
            if(schedulerInspectionLineTable.getSowing_date_male()!=null) {
                tv_sd_male.setText((DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male())));
            }
            else {
                tv_sd_male.setText("");
            }
            if(schedulerInspectionLineTable.getSowing_date_male()!=null){
                tv_sd_female.setText((DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male())));
            }
            else {
                tv_sd_female.setText("");
            }
            tv_org_code.setText(schedulerInspectionLineTable.getOrganizer_code()+"("+schedulerInspectionLineTable.getOrganizer_name()+")");
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());

        }catch (Exception e){
            e.printStackTrace();
        }
        //find input field.....
        ed_date_of_insp = view.findViewById(R.id.date_of_insp);
        ed_pollen_shedd = view.findViewById(R.id.pollen_shedd);
        ed_ohter_type = view.findViewById(R.id.ohter_type);
        ed_pollen_shed_plants = view.findViewById(R.id.pollen_shed_plants);
        ed_pollen_shedd_per = view.findViewById(R.id.pollen_shedd_per);
        ed_pest_remark = view.findViewById(R.id.pest_remark);
        ed_desease_remark = view.findViewById(R.id.ed_desease_remark);

        recmnd_date = view.findViewById(R.id.recmnd_date);
        actual_date = view.findViewById(R.id.actual_date);

        ac_pest = view.findViewById(R.id.ac_pest);
        ac_desease = view.findViewById(R.id.ac_desease);
        ac_crop_cond = view.findViewById(R.id.ac_crop_cond);
        ac_crop_stg = view.findViewById(R.id.ac_crop_stg);


        actual_date.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(actual_date);
            return true;
        });

        ed_date_of_insp.setOnTouchListener((view1, motionEvent) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            return true;
        });

        //todo new changes auto select recommended date by recommneded days.
        recmnd_date.setText(getFemaleSowingDate());

        List<String> deaseasList = Arrays.asList(CommonData.desease);
        List<String> crop_condn = Arrays.asList(CommonData.crop_condition);
        ItemAdapter desease_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_desease.setAdapter(desease_adapter);

        ItemAdapter pest_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, deaseasList);
        ac_pest.setAdapter(pest_adapter);
        ItemAdapter iso_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, crop_condn);
        ac_crop_cond.setAdapter(iso_adapter);
        ac_crop_stg.setText("Post Flowering");

        ItemAdapter seed_setting_item_adapter=new ItemAdapter(getActivity(),R.layout.android_item_view,Arrays.asList(CommonData.seed_setting));
        ed_seed_setting.setAdapter(seed_setting_item_adapter);
    }


    private List<PostfloweringInspectionTable> postfloweringInspectionTable;
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertPostFloweringInspectionLine(List<PostFloweringInspectionModel> post_flowering_ins7List) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            PostFloweringDao postloweringInspectionDao = db.postFloweringDao();
            if (post_flowering_ins7List != null && post_flowering_ins7List.size() > 0) {
                for (int i = 0; i < post_flowering_ins7List.size(); i++) {
                    PostfloweringInspectionTable postfloweringInspectionTable = PostfloweringInspectionTable.inertPostFloweringInspection(post_flowering_ins7List.get(i));
                    if (postloweringInspectionDao.isDataExist(postfloweringInspectionTable.getProduction_lot_no())>0) {
                        postloweringInspectionDao.update(postfloweringInspectionTable);
                    } else if (postfloweringInspectionTable.getProduction_lot_no() != null || postfloweringInspectionTable.getScheduler_no() != null) {
                        postloweringInspectionDao.insert(postfloweringInspectionTable);
                    }
                }
            }
            postfloweringInspectionTable = postloweringInspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);
        } catch (Exception e) {
            Log.e("log_e", e.getMessage());
        } finally {
            db.close();
            db.destroyInstance();
            if (postfloweringInspectionTable != null) {
                if (scheduler_line_header_data.getIns7_sync_with_server() > 0 || scheduler_line_header_data.getInspection_7() > 0) {
                    if (scheduler_line_header_data.getInspection_7() > 0 && scheduler_line_header_data.getIns7_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp7_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp7_mesage());
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
                if (scheduler_line_header_data.getIns7_sync_with_server() > 0 || scheduler_line_header_data.getInspection_7() > 0) {
                    if (scheduler_line_header_data.getInspection_7() > 0 && scheduler_line_header_data.getIns7_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp7_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp7_mesage());
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
        if (postfloweringInspectionTable != null && postfloweringInspectionTable.size() > 0) {
            ed_ohter_type.setText(postfloweringInspectionTable.get(0).getOther_types());
            ed_pest_remark.setText(postfloweringInspectionTable.get(0).getPest_remarks());
            ed_desease_remark.setText(postfloweringInspectionTable.get(0).getDiseases_remarks());
            ac_pest.setText(postfloweringInspectionTable.get(0).getPest());
            ac_desease.setText(postfloweringInspectionTable.get(0).getDiseases());
            ac_crop_cond.setText(postfloweringInspectionTable.get(0).getCrop_condition());
            ac_crop_stg.setText(postfloweringInspectionTable.get(0).getCrop_stage());
            ed_seed_setting.setText(postfloweringInspectionTable.get(0).getSeed_setting());
            ed_seed_stng_per.setText(postfloweringInspectionTable.get(0).getSeed_setting_prcnt());
            if(postfloweringInspectionTable.get(0).getDate_of_inspection()!=null) {
                ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(postfloweringInspectionTable.get(0).getDate_of_inspection()));
            }

            if(postfloweringInspectionTable.get(0).getRecommended_date()!=null){
                recmnd_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(postfloweringInspectionTable.get(0).getRecommended_date()));
            }
            if(postfloweringInspectionTable.get(0).getActual_date()!=null) {
                actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(postfloweringInspectionTable.get(0).getActual_date()));
            }
            try {
                String file_attachment = postfloweringInspectionTable.get(0).getAttachment();

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void diableAllInputField() {
        ed_date_of_insp.setEnabled(false);
        ed_pollen_shedd.setEnabled(false);
        ed_pollen_shed_plants.setEnabled(false);
        ed_pollen_shedd_per.setEnabled(false);
        ed_ohter_type.setEnabled(false);
        ed_pest_remark.setEnabled(false);
        ed_desease_remark.setEnabled(false);
        ed_date_of_insp.setEnabled(false);
        ed_date_of_insp.setFocusable(false);
        recmnd_date.setEnabled(false);
        recmnd_date.setFocusable(false);
        actual_date.setEnabled(false);
        actual_date.setFocusable(false);
        ac_pest.setEnabled(false);
        ac_pest.setDropDownHeight(0);
        ac_pest.setFocusable(false);
        ac_pest.setFocusableInTouchMode(false);
        ac_desease.setEnabled(false);
        ac_desease.setDropDownHeight(0);
        ac_desease.setFocusable(false);
        ac_desease.setFocusableInTouchMode(false);
        ac_crop_cond.setEnabled(false);
        ac_crop_cond.setDropDownHeight(0);
        ac_crop_cond.setFocusableInTouchMode(false);
        ac_crop_cond.setFocusable(false);
        ac_crop_stg.setEnabled(false);
        ac_crop_stg.setFocusableInTouchMode(false);
        ac_crop_stg.setFocusable(false);
        ed_desease_remark.setEnabled(false);
        ed_desease_remark.setFocusable(false);
        ed_seed_setting.setEnabled(false);
        ed_seed_setting.setDropDownHeight(0);
        ed_seed_setting.setFocusable(false);
        ed_pollen_shedd_per.setEnabled(false);
        ed_pollen_shedd_per.setFocusable(false);
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

    private void savePostFloweringInspRecord() {
        PostFloweringInspectionModel postfloweringInspectionModel = new PostFloweringInspectionModel();
        try {
            postfloweringInspectionModel.scheduler_no = scheduler_no;
            postfloweringInspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            postfloweringInspectionModel.production_lot_no = production_lot_no;
            postfloweringInspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(actual_date.getText().toString().trim());
            postfloweringInspectionModel.crop_condition = ac_crop_cond.getText().toString().trim();
            postfloweringInspectionModel.crop_stage = ac_crop_stg.getText().toString().trim();
            postfloweringInspectionModel.other_types = ed_ohter_type.getText().toString().trim();
            postfloweringInspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();
            postfloweringInspectionModel.pest = ac_pest.getText().toString().trim();
            postfloweringInspectionModel.diseases = ac_desease.getText().toString().trim();
            postfloweringInspectionModel.pest_remarks = ed_pest_remark.getText().toString().trim();
            postfloweringInspectionModel.diseases_remarks = ed_desease_remark.getText().toString().trim();
            postfloweringInspectionModel.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(recmnd_date.getText().toString().trim());
            postfloweringInspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(actual_date.getText().toString().trim());
            postfloweringInspectionModel.created_on = DateTimeUtilsCustome.getCurrentTime();
            postfloweringInspectionModel.male_reciept_no ="0";
            postfloweringInspectionModel.female_reciept_no ="0";
            postfloweringInspectionModel.other_reciept_no ="0";

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                postfloweringInspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                postfloweringInspectionModel.seed_setting_prcnt = "0.0";
            }

            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            postfloweringInspectionModel.attachment = base_64_image != null ? base_64_image : "";
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        String jsonString = new Gson().toJson(postfloweringInspectionModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        Log.e("json_flering", asJsonObject.toString());
        if(ed_pest_remark.getText().toString().length()>120){
            Toast.makeText(getActivity(),"Pest Remark value should be less than 120 characters in length ",Toast.LENGTH_SHORT).show();
        }
        else if(ed_desease_remark.getText().toString().length()>120){
            Toast.makeText(getActivity(),"Disease Remark value should be less than 120 characters in length ",Toast.LENGTH_SHORT).show();
        }
        else if(ac_crop_cond.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please enter crop condn. ",Toast.LENGTH_SHORT).show();
        }
        else if(ac_crop_stg.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please enter crop stage. ",Toast.LENGTH_SHORT).show();
        }
        else if(actual_date.getText().toString().trim().trim().equalsIgnoreCase("")){
            Toast.makeText(getActivity(),"Please enter acutal date. ",Toast.LENGTH_SHORT).show();
        }
        else {
            if (isNetwork) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> call = mAPIService.insertPostFloweringInsection(asJsonObject);
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                ArrayList<PostFloweringInspectionModel> postflowering_InspectionList = new ArrayList<>();
                call.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<ResponseModel> inserResponseList = response.body();
                                if (inserResponseList!=null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                    postfloweringInspectionModel.synWithApi7 = 1;
                                    postfloweringInspectionModel.attachment = selected_file_path;
                                    postflowering_InspectionList.add(postfloweringInspectionModel);
                                    insertPostFloweringInspectionLine(postflowering_InspectionList);
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
                ArrayList<PostFloweringInspectionModel> postflowering_InspectionList = new ArrayList<>();
                postfloweringInspectionModel.synWithApi7 = 0;
                postfloweringInspectionModel.attachment = selected_file_path;
                postflowering_InspectionList.add(postfloweringInspectionModel);
                insertPostFloweringInspectionLine(postflowering_InspectionList);
                StaticMethods.showMDToast(getActivity(), "insert Successful !", MDToast.TYPE_SUCCESS);
            }
        }

    }

    private void completePostFloweringInspection() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        try {
            if (network) {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VII", postfloweringInspectionTable.get(0).getProduction_lot_no(), postfloweringInspectionTable.get(0).getScheduler_no());
                LoadingDialog progressDialogLoading = new LoadingDialog();
                progressDialogLoading.showLoadingDialog(getActivity());
                call.enqueue(new Callback<List<CompleteGerminationInspectionModel>>() {
                    @Override
                    public void onResponse(Call<List<CompleteGerminationInspectionModel>> call, Response<List<CompleteGerminationInspectionModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                progressDialogLoading.hideDialog();
                                List<CompleteGerminationInspectionModel> completeResponseList = response.body();
                                if (completeResponseList!=null && completeResponseList.size() > 0 && completeResponseList.get(0).condition) {
                                    if (completeResponseList.get(0).nav_condition != 0) {
                                        completeOnlineOfflineNickingData("Online", 1, 1, postfloweringInspectionTable.get(0).getCreated_on(), 0, "");
                                        StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);
                                    } else {
                                        completeOnlineOfflineNickingData("Online", 0, 1, postfloweringInspectionTable.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
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
                completeOnlineOfflineNickingData("Offline", 0, 0, postfloweringInspectionTable.get(0).getCreated_on(), 1, "");
                StaticMethods.showMDToast(getActivity(),"Successful Complete !",MDToast.TYPE_SUCCESS);
            }
        } catch (Exception e) {
            Log.e("vegi_cmp", e.getMessage());
        }

    }

    private void completeOnlineOfflineNickingData(
            String flag, int nav_sync, int inspection7, String completed_on, int ins7_sync_with_server, String nav_sync_error) {
            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompletePostFlowering(nav_sync, inspection7, scheduler_no, postfloweringInspectionTable.get(0).getProduction_lot_no(), completed_on, ins7_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspPostFlowering(scheduler_no, postfloweringInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);

            } else {
                inspection_oneHeaderDao.updateOnServerCompletePostFlowering(nav_sync, inspection7, scheduler_no, postfloweringInspectionTable.get(0).getProduction_lot_no(), completed_on, ins7_sync_with_server);//nav_message
            }
            insertPostFloweringInspectionLine(new ArrayList<>());
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
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
                Bitmap bm = BitmapFactory.decodeFile(selected_file_path, options);
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




    private String getFemaleSowingDate(){

        if(production_lot_no!=null) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getFemaleSowingDate(production_lot_no);

                String date=plantingLineLotListTable.getSowing_Date_Female();
                String date_sub_string=date.substring(0,10);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date steepingdate = formatter.parse(date_sub_string);
                Calendar ca = Calendar.getInstance();
                ca.setTime(steepingdate);
                ca.add(Calendar.DAY_OF_YEAR, 80);

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
}