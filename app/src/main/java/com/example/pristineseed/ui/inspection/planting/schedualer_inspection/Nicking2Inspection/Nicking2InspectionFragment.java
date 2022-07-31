package com.example.pristineseed.ui.inspection.planting.schedualer_inspection.Nicking2Inspection;

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
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nickin2InspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nicking2InspectionTable;
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
import com.example.pristineseed.model.scheduler_inspection.Nicking2InspectionModel;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Nicking2InspectionFragment extends Fragment {

    private Scheduler_Header_Table scheduler_header_table;
    private SchedulerInspectionLineTable schedulerInspectionLineTable;

    private TextView tv_date, tv_season, tv_season_name, tv_prod_cent_name,
            tv_farmer_name, village_address, tv_prod_lot_no, tv_crop_code, tv_varity_code, tv_sd_male, tv_sd_female, tv_org_name,
            tv_org_code, tv_item_prodGrp_code, tv_item_class_of_seed, tv_crop_type, posting_error;

    private TextInputEditText ed_date_of_insp, ed_Remarks, ed_date_of_action,
            ed_seed_stng_per, ed_receipt_male, ed_receipt_female, ed_receipt_other;

    private String scheduler_no = "", production_lot_no = "";
    private Button bt_complete, btn_save_record;
    private SessionManagement sessionManagement;
    private ImageView imageView;
    private AutoCompleteTextView ed_wther_remmndtn, ed_seed_setting;
    private FrameLayout backpress_img;
    private Chip add_attachment;
    private int PICK_IMAGE_SINGLE = 1;
    private Chip clear_image_btn;
    private LinearLayout image_layout;
    private String selected_file_path = "";
    private List<String> imageEncodList = new ArrayList<>();
    private TextInputEditText ed_actual_date;


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
        return inflater.inflate(R.layout.add_lot_wise_nicking2_inspection_field_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        insertNicking2InspectionLineIntoLocal(new ArrayList<>());

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
                        completeInspection();
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
        tv_farmer_name = view.findViewById(R.id.farmer_name);
        village_address = view.findViewById(R.id.tv_vill_);
        tv_prod_lot_no = view.findViewById(R.id.prod_lot);
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
        image_layout = view.findViewById(R.id.image_layout);

        bt_complete = view.findViewById(R.id.complete_btn);
        btn_save_record = view.findViewById(R.id.save_record);
        backpress_img = view.findViewById(R.id.close_dilog_bt);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        ed_receipt_male = view.findViewById(R.id.receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.receipt_no_other);

        //find input field.....
        ed_date_of_insp = view.findViewById(R.id.ed_date_of_insp);
        ed_date_of_action = view.findViewById(R.id.date_of_action);
        ed_date_of_insp = view.findViewById(R.id.date_of_inspection);
        ed_wther_remmndtn = view.findViewById(R.id.wther_remmndtn);
        ed_Remarks = view.findViewById(R.id.tv_Remarks);
        ed_actual_date = view.findViewById(R.id.ed_actual_date);
        //set header data....
        tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
        tv_season.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
        tv_prod_cent_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
        backpress_img.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        try {
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
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
                tv_sd_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(""));
            }
            if (schedulerInspectionLineTable.getSowing_date_male() != null) {
                tv_sd_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            } else {
                tv_sd_female.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ItemAdapter whthr_recomd_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.wthr_recmmnd));
        ed_wther_remmndtn.setAdapter(whthr_recomd_adapter);

        ItemAdapter seed_setting_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.seed_setting));
        ed_seed_setting.setAdapter(seed_setting_adapter);

        ed_date_of_insp.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_insp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

        ed_date_of_action.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_date_of_action);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

        ed_actual_date.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_actual_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    private List<Nicking2InspectionTable> nicking2InspectionTable;
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void insertNicking2InspectionLineIntoLocal(List<Nicking2InspectionModel> nicking2InspectionModelList) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = pristineDatabase.scheduleInspectionLineDao();
            Nickin2InspectionDao nickin2InspectionDao = pristineDatabase.nickin2InspectionDao();
            if (nicking2InspectionModelList != null && nicking2InspectionModelList.size() > 0) {
                for (int i = 0; i < nicking2InspectionModelList.size(); i++) {
                    Nicking2InspectionTable nicking2InspectionTable = Nicking2InspectionTable.insertNicking2InsoectionData(nicking2InspectionModelList.get(i));
                    if (nickin2InspectionDao.isDataExist(nicking2InspectionTable.getProduction_lot_no()) > 0) {
                        nickin2InspectionDao.update(nicking2InspectionTable);
                    } else {
                        nickin2InspectionDao.insert(nicking2InspectionTable);
                    }
                }
            }
            nicking2InspectionTable = nickin2InspectionDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if (nicking2InspectionTable != null) {
                if (scheduler_line_header_data.getIns5_sync_with_server() > 0 || scheduler_line_header_data.getInspection_5() > 0) {
                    if (scheduler_line_header_data.getInspection_5() > 0 && scheduler_line_header_data.getIns5_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp5_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp5_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    disableInsputField();

                } else {
                    bt_complete.setVisibility(View.VISIBLE);
                    btn_save_record.setVisibility(View.VISIBLE);
                }
                showRefelectedFieldData();
            } else {
                btn_save_record.setVisibility(View.VISIBLE);
                if (scheduler_line_header_data.getIns5_sync_with_server() > 0 || scheduler_line_header_data.getInspection_5() > 0) {
                    if (scheduler_line_header_data.getInspection_5() > 0 && scheduler_line_header_data.getIns5_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp5_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp5_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    bt_complete.setVisibility(View.GONE);
                    btn_save_record.setVisibility(View.GONE);
                    disableInsputField();
                }
            }

        }
    }

    private void disableInsputField() {
        ed_date_of_insp.setEnabled(false);
        ed_date_of_action.setEnabled(false);
        ed_wther_remmndtn.setEnabled(false);
        ed_Remarks.setEnabled(false);
        ed_date_of_insp.setFocusable(false);
        ed_date_of_action.setFocusable(false);
        ed_wther_remmndtn.setFocusable(false);
        ed_wther_remmndtn.setDropDownHeight(0);
        ed_actual_date.setEnabled(false);
        ed_Remarks.setFocusable(false);
        ed_seed_setting.setEnabled(false);
        ed_seed_setting.setFocusable(false);
        ed_seed_stng_per.setEnabled(false);
        ed_seed_stng_per.setFocusable(false);
        ed_receipt_male.setEnabled(false);
        ed_receipt_female.setEnabled(false);
        ed_receipt_other.setEnabled(false);
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


    private void showRefelectedFieldData() {
        if (nicking2InspectionTable != null && nicking2InspectionTable.size() > 0) {
            if (nicking2InspectionTable.get(0).getDate_of_inspection() != null) {
                ed_date_of_insp.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(nicking2InspectionTable.get(0).getDate_of_inspection()));
            } else {
                ed_date_of_insp.setText("");
            }
            if (nicking2InspectionTable.get(0).getDate_of_action() != null) {
                ed_date_of_action.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(nicking2InspectionTable.get(0).getDate_of_action()));
            } else {
                ed_date_of_action.setText("");
            }
            ed_wther_remmndtn.setText(nicking2InspectionTable.get(0).getWhether_recommendation_done());
            ed_actual_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(nicking2InspectionTable.get(0).getActual_date()));

            ed_Remarks.setText(nicking2InspectionTable.get(0).getRemarks());
            if (nicking2InspectionTable.get(0).getSeed_setting() != null) {
                ed_seed_setting.setText(nicking2InspectionTable.get(0).getSeed_setting());
            }

            try {
                if(nicking2InspectionTable.get(0).getAttachment()!=null){
                    String getImageId=nicking2InspectionTable.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    Toast.makeText(getActivity(), nicking2InspectionTable.get(0).getAttachment(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void saveRecord() {
        try {
            Nicking2InspectionModel nicking2InspectionModel = new Nicking2InspectionModel();
            nicking2InspectionModel.scheduler_no = scheduler_no;
            nicking2InspectionModel.arrival_plan_no = scheduler_line_header_data.getArrival_plan_no();
            nicking2InspectionModel.production_lot_no = production_lot_no;
            if (!ed_date_of_insp.getText().toString().trim().equalsIgnoreCase("")) {
                nicking2InspectionModel.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_of_insp.getText().toString().trim());
            } else {
                nicking2InspectionModel.date_of_inspection = "";
            }
            nicking2InspectionModel.whether_recommendation_done = ed_wther_remmndtn.getText().toString().trim();
            if (!ed_date_of_action.getText().toString().trim().equalsIgnoreCase("")) {
                nicking2InspectionModel.date_of_action = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date_of_action.getText().toString().trim());
            } else {
                nicking2InspectionModel.date_of_action = "";
            }
            nicking2InspectionModel.remarks = ed_Remarks.getText().toString().trim();
            nicking2InspectionModel.created_on = DateTimeUtilsCustome.getCurrentTime();
            nicking2InspectionModel.seed_setting = ed_seed_setting.getText().toString().trim();
            nicking2InspectionModel.male_reciept_no = "0";
            nicking2InspectionModel.female_reciept_no = "0";
            nicking2InspectionModel.other_reciept_no = "0";
            nicking2InspectionModel.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_actual_date.getText().toString().trim());

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                nicking2InspectionModel.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                nicking2InspectionModel.seed_setting_prcnt = "0.0";
            }

            if (selected_file_path != null && !selected_file_path.equalsIgnoreCase("")) {
                String base_64_image = StaticMethods.convertBase64(selected_file_path);
                if (base_64_image != null && !base_64_image.equalsIgnoreCase("")) {
                    nicking2InspectionModel.attachment = base_64_image;
                }
            } else {
                nicking2InspectionModel.attachment = "";
            }
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            String jsonString = new Gson().toJson(nicking2InspectionModel);
            JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            if (ed_Remarks.getText().toString().length() > 120) {
                Toast.makeText(getActivity(), "Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
            } else if (ed_date_of_action.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter the date of inspection.", Toast.LENGTH_SHORT).show();
            } else {
                if (isNetwork) {
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    Call<List<ResponseModel>> call = mAPIService.insertNicking2Inspection(asJsonObject);
                    LoadingDialog progressDialogLoading = new LoadingDialog();
                    progressDialogLoading.showLoadingDialog(getActivity());
                    ArrayList<Nicking2InspectionModel> nicking2InspectionModelArrayList = new ArrayList<>();
                    call.enqueue(new Callback<List<ResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialogLoading.hideDialog();
                                    List<ResponseModel> inserResponseList = response.body();
                                    if (inserResponseList != null && inserResponseList.size() > 0 && inserResponseList.get(0).condition) {
                                        nicking2InspectionModel.syncWithApi5 = 1;
                                        nicking2InspectionModel.attachment = selected_file_path;
                                        nicking2InspectionModelArrayList.add(nicking2InspectionModel);
                                        insertNicking2InspectionLineIntoLocal(nicking2InspectionModelArrayList);
                                        MDToast.makeText(getActivity(), inserResponseList.get(0).message, MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                                    } else {
                                        progressDialogLoading.hideDialog();
                                        MDToast.makeText(getActivity(), inserResponseList.size() > 0 ? inserResponseList.get(0).message : "Api not respoding.", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                                    }
                                } else {
                                    progressDialogLoading.hideDialog();
                                    MDToast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                                }
                            } catch (Exception e) {
                                progressDialogLoading.hideDialog();
                                //Log.e("exception database", e.getMessage() + "cause");
                                //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_nicking2", getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                            progressDialogLoading.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_nicking2", getActivity());
                        }
                    });
                } else {
                    ArrayList<Nicking2InspectionModel> nicking2InspectionModelArrayList = new ArrayList<>();
                    nicking2InspectionModel.syncWithApi5 = 0;
                    nicking2InspectionModel.attachment = selected_file_path;
                    nicking2InspectionModelArrayList.add(nicking2InspectionModel);
                    insertNicking2InspectionLineIntoLocal(nicking2InspectionModelArrayList);
                    MDToast.makeText(getActivity(), "insert Successful !", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void completeInspection() {
        boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (network) {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection V", nicking2InspectionTable.get(0).getProduction_lot_no(), nicking2InspectionTable.get(0).getScheduler_no());
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
                                    completeNicking2Inspection("Online", 1, 1, nicking2InspectionTable.get(0).getCreated_on(), 0, "");
                                    StaticMethods.showMDToast(getActivity(), completeResponseList.get(0).message + "//" + completeResponseList.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);

                                } else {
                                    completeNicking2Inspection("Online", 0, 1, nicking2InspectionTable.get(0).getCreated_on(), 0, completeResponseList.get(0).nav_message);
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
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_inspection5", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_inspection5", getActivity());
                }
            });
        } else {
            completeNicking2Inspection("Offline", 0, 0, nicking2InspectionTable.get(0).getCreated_on(), 1, "");
        }
    }

    private void completeNicking2Inspection(String flag, int nav_sync, int inspection5, String completed_on, int ins5_sync_with_server, String nav_sync_error) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteNicking2(nav_sync, inspection5, scheduler_no, nicking2InspectionTable.get(0).getProduction_lot_no(), completed_on, ins5_sync_with_server);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspNicking2(scheduler_no, nicking2InspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteNicking2(nav_sync, inspection5, scheduler_no, nicking2InspectionTable.get(0).getProduction_lot_no(), completed_on, ins5_sync_with_server);
                StaticMethods.showMDToast(getActivity(), "Inspection Completed !", MDToast.TYPE_SUCCESS);
            }
            insertNicking2InspectionLineIntoLocal(new ArrayList<>());

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
                Bitmap bm = BitmapFactory.decodeFile(selected_file_path, options);
                setBitmapImage(bm, this.selected_file_path);
            }
        } else {
            MDToast.makeText(getActivity(), "You haven't picked Image",
                    MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
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
