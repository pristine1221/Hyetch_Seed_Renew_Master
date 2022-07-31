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
import android.widget.ArrayAdapter;
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

import com.bumptech.glide.Glide;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspInsertDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspectionTable;
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
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.Nicking_InspectionLineModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.header_line.ListAdapter;
import com.google.android.material.button.MaterialButton;
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

public class NickingInspectionFragment extends Fragment {
    private Button complete_btn, save_record_btn;

    private TextView tv_season_name, tv_date, tv_item_no, tv_arrno_season_code, tv_prod_cnt_name, tv_prod_center, tv_farmer_name, tv_item_name, tv_org_name, tv_org_code, tv_crop_code, tv_variety_code, tv_item_prodGrp_code,
            tv_sowing_male, tv_fs_female, tv_village, item_class_of_seed, posting_error, prod_lot_no;
    public String production_lot_no = "", scheduler_no = "";
    private Scheduler_Header_Table scheduler_header_table = null;
    private SchedulerInspectionLineTable schedulerInspectionLineTable = null;
    private AutoCompleteTextView dropdown_crop_condition, edit_Status_of_Female, edit_Status_of_Male;
    private TextInputEditText edit_next_plan_of_action, edit_remark, edt_date_of_inspection, ed_seed_setting,
            ed_seed_stng_per, ed_receipt_male, ed_receipt_female, ed_receipt_other, dropdown_crop_stage, ed_recommeded_date;

    private ImageView imageView;
    private FrameLayout backpress_frag;
    private LinearLayout image_layout;
    private Chip add_attachment;
    private int PICK_IMAGE_SINGLE = 1;

    private Chip clear_image_btn;
    private String selected_file_path = "";
    private TextInputEditText ed_acutal_date;
    private List<String> imageEncodList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            scheduler_header_table = new Gson().fromJson(bundle.getString("header_detail", ""), Scheduler_Header_Table.class);
            schedulerInspectionLineTable = new Gson().fromJson(bundle.getString("scheduler_line_detail", ""), SchedulerInspectionLineTable.class);
            production_lot_no = bundle.getString("production_lot_no", "");
            scheduler_no = bundle.getString("scheduler_no", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nicking_inspection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        edt_date_of_inspection.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(edt_date_of_inspection);
            return true;
        });

        ItemAdapter crop_condition_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.crop_condition));
        dropdown_crop_condition.setAdapter(crop_condition_adapter);

        dropdown_crop_stage.setText("Nicking");

        ItemAdapter status_Female = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.status_male_female));
        edit_Status_of_Female.setAdapter(status_Female);

        ItemAdapter status_male = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.status_male_female));
        edit_Status_of_Male.setAdapter(status_male);

        bindNickingInspWithLocalData(new ArrayList<>());
        save_record_btn.setOnClickListener(v -> {
            saveNickingInspData();

        });
        complete_btn.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            builder.setIcon(R.drawable.ic_baseline_verified_user_24);
            builder.setTitle("Do you really want to complete this ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    completeNicking();
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
        tv_date = view.findViewById(R.id.tv_date_);
        tv_arrno_season_code = view.findViewById(R.id.tv_arrno_season_code);
        tv_season_name = view.findViewById(R.id.tv_season_name);
        tv_prod_cnt_name = view.findViewById(R.id.tv_prod_cnt_name);
        tv_prod_center = view.findViewById(R.id.tv_prod_center);
        tv_farmer_name = view.findViewById(R.id.tv_farmer_name);
        tv_item_no = view.findViewById(R.id.tv_item_no);
        tv_item_name = view.findViewById(R.id.tv_item_name);
        tv_org_name = view.findViewById(R.id.tv_org_name);
        tv_org_code = view.findViewById(R.id.tv_org_code);
        tv_crop_code = view.findViewById(R.id.tv_crop_code);
        tv_variety_code = view.findViewById(R.id.tv_variety_code);
        tv_item_prodGrp_code = view.findViewById(R.id.tv_item_prodGrp_code);
        tv_sowing_male = view.findViewById(R.id.tv_sowing_male);
        item_class_of_seed = view.findViewById(R.id.item_class_of_seed);
        tv_village = view.findViewById(R.id.tv_village);
        prod_lot_no = view.findViewById(R.id.prod_lot_no);
        backpress_frag = view.findViewById(R.id.close_dilog_bt);
        image_layout = view.findViewById(R.id.image_layout);
        tv_fs_female = view.findViewById(R.id.tv_sowing_female);
        edit_remark = view.findViewById(R.id.edit_remark);
        edit_next_plan_of_action = view.findViewById(R.id.edit_next_plan_of_action);
        edt_date_of_inspection = view.findViewById(R.id.edt_date_of_inspection);

        edit_Status_of_Female = view.findViewById(R.id.edit_Status_of_Female);
        edit_Status_of_Male = view.findViewById(R.id.edit_Status_of_Male);
        dropdown_crop_condition = view.findViewById(R.id.dropdown_crop_condition);
        dropdown_crop_stage = view.findViewById(R.id.dropdown_crop_stage);
        save_record_btn = view.findViewById(R.id.save_record_btn);
        complete_btn = view.findViewById(R.id.complete_btn);
        posting_error = view.findViewById(R.id.posting_error);

        ed_seed_setting = view.findViewById(R.id.ed_seed_setting);
        ed_seed_stng_per = view.findViewById(R.id.ed_seed_stng_per);

        imageView = view.findViewById(R.id.image_view);
        add_attachment = view.findViewById(R.id.add_attachment);
        clear_image_btn = view.findViewById(R.id.clear_img);
        ed_receipt_male = view.findViewById(R.id.ni_receipt_no_male);
        ed_receipt_female = view.findViewById(R.id.ni_receipt_no_female);
        ed_receipt_other = view.findViewById(R.id.ni_receipt_no_other);
        ed_recommeded_date = view.findViewById(R.id.ed_recommeded_date);
        ed_acutal_date = view.findViewById(R.id.ed_acutal_date);
        backpress_frag.setOnClickListener(v -> {
            getFragmentManager().popBackStack();

        });

        try {
            tv_date.setText(DateTimeUtilsCustome.getDateMMMDDYYYYSlsh1(scheduler_header_table.getDate()));
            tv_arrno_season_code.setText(scheduler_header_table.getSeason() + "(" + scheduler_header_table.getSeason_name() + ")");
            tv_prod_cnt_name.setText(scheduler_header_table.getProduction_centre() + "(" + scheduler_header_table.getProduction_centre_name() + ")");
            prod_lot_no.setText(schedulerInspectionLineTable.getProduction_lot_no());
            if (schedulerInspectionLineTable.getSowing_date_female() != null) {
                tv_fs_female.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_female()));
            } else {
                tv_fs_female.setText("");
            }
            tv_farmer_name.setText(schedulerInspectionLineTable.getGrower_land_owner_name() + "(" + schedulerInspectionLineTable.getGrower_owner() + ")");
            tv_org_name.setText(schedulerInspectionLineTable.getOrganizer_code() + "(" + schedulerInspectionLineTable.getOrganizer_name() + ")");
            tv_crop_code.setText(schedulerInspectionLineTable.getCrop_code());
            ed_recommeded_date.setText(getFemaleSowingDate());
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                if (schedulerInspectionLineTable != null) {
                    PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getVaityCodeAliasName(production_lot_no, schedulerInspectionLineTable.getVariety_code());
                    if (plantingLineLotListTable != null) {
                        tv_variety_code.setText(plantingLineLotListTable.getAlias_Name());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
            tv_item_prodGrp_code.setText(schedulerInspectionLineTable.getItem_product_group_code());
            tv_sowing_male.setText(DateTimeUtilsCustome.splitDateDDDMMYYYY(schedulerInspectionLineTable.getSowing_date_male()));
            item_class_of_seed.setText(schedulerInspectionLineTable.getItem_class_of_seeds());
            tv_village.setText("Vill-" + schedulerInspectionLineTable.getGrower_village() + "," + schedulerInspectionLineTable.getGrower_city() + "," + schedulerInspectionLineTable.getGrower_district());

        } catch (Exception e) {
            e.printStackTrace();
        }

        ed_acutal_date.setOnTouchListener((v, event) -> {
            try {
                new CustomDatePicker(getActivity()).showDatePickerDialog(ed_acutal_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
    }


    private List<NickingInspectionTable> nickingInspectionTable = new ArrayList<>();
    private SchedulerInspectionLineTable scheduler_line_header_data = null;

    private void bindNickingInspWithLocalData(List<Nicking_InspectionLineModel> tempNickingInspListModel) {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = pristineDatabase.scheduleInspectionLineDao();
            NickingInspInsertDao nickingInspInsertDao = pristineDatabase.nickingInspInsertDao();
            if (tempNickingInspListModel != null && tempNickingInspListModel.size() > 0) {
                for (int i = 0; i < tempNickingInspListModel.size(); i++) {
                    NickingInspectionTable nickingInspInsertTable = NickingInspectionTable.insertNickingDataFromServer(tempNickingInspListModel.get(i));
                    int dataExist = nickingInspInsertDao.isDataExist(tempNickingInspListModel.get(i).production_lot_no);
                    if (dataExist > 0) {
                        nickingInspInsertDao.update(nickingInspInsertTable);
                    } else {
                        nickingInspInsertDao.insert(nickingInspInsertTable);
                    }
                }
            }
            nickingInspectionTable = nickingInspInsertDao.getInpection1DataByLotNo(production_lot_no);
            scheduler_line_header_data = scheduleInspectionLineDao.getAllDatabyLotNoGermination(scheduler_header_table.getSchedule_no(), production_lot_no);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

            if (nickingInspectionTable != null) {
                if (scheduler_line_header_data.getIns4_sync_with_server() > 0 || scheduler_line_header_data.getInspection_4() > 0) {
                    if (scheduler_line_header_data.getInspection_4() > 0 && scheduler_line_header_data.getIns4_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp4_mesage() != null &&
                            !scheduler_line_header_data.getNav_error_insp1_mesage().equalsIgnoreCase("")) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp4_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    save_record_btn.setVisibility(View.GONE);
                    complete_btn.setVisibility(View.GONE);
                    disableInputField();

                } else {
                    complete_btn.setVisibility(View.VISIBLE);
                    save_record_btn.setVisibility(View.VISIBLE);
                }
                showReflectedFieldData();
            } else {
                save_record_btn.setVisibility(View.VISIBLE);
                if (scheduler_line_header_data.getIns4_sync_with_server() > 0 || scheduler_line_header_data.getInspection_4() > 0) {
                    if (scheduler_line_header_data.getInspection_4() > 0 && scheduler_line_header_data.getIns4_nav_sync() == 0 && scheduler_line_header_data.getNav_error_insp4_mesage() != null) {
                        posting_error.setText("Post Error :" + scheduler_line_header_data.getNav_error_insp4_mesage());
                        posting_error.setVisibility(View.VISIBLE);
                    } else {
                        posting_error.setVisibility(View.GONE);
                    }
                    save_record_btn.setVisibility(View.GONE);
                    complete_btn.setVisibility(View.GONE);
                    disableInputField();
                }
            }
        }
    }

    private void disableInputField() {
        edit_Status_of_Female.setEnabled(false);
        edit_Status_of_Female.setDropDownHeight(0);
        edit_Status_of_Male.setEnabled(false);
        edit_Status_of_Male.setDropDownHeight(0);
        dropdown_crop_condition.setEnabled(false);
        dropdown_crop_condition.setDropDownHeight(0);
        dropdown_crop_condition.setFocusable(false);
        dropdown_crop_stage.setEnabled(false);
        dropdown_crop_stage.setFocusable(false);
        edit_next_plan_of_action.setEnabled(false);
        edt_date_of_inspection.setEnabled(false);
        ed_acutal_date.setEnabled(false);
        edt_date_of_inspection.setFocusable(false);
        edit_remark.setEnabled(false);
        edit_remark.setFocusable(false);

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


    private void showReflectedFieldData() {
        try {
            edit_Status_of_Female.setText(nickingInspectionTable.get(0).getStatus_of_female() != null ? nickingInspectionTable.get(0).getStatus_of_female() : "");
            edit_Status_of_Male.setText(nickingInspectionTable.get(0).getStatus_of_male() != null ? nickingInspectionTable.get(0).getStatus_of_male() : "");
            dropdown_crop_condition.setText(nickingInspectionTable.get(0).getCrop_condition() != null ? nickingInspectionTable.get(0).getCrop_condition() : "");
            dropdown_crop_stage.setText(nickingInspectionTable.get(0).getCrop_stage() != null ? nickingInspectionTable.get(0).getCrop_stage() : "");
            edit_next_plan_of_action.setText(nickingInspectionTable.get(0).getNext_plan_of_action() != null ? nickingInspectionTable.get(0).getNext_plan_of_action() : "");
            edit_remark.setText(nickingInspectionTable.get(0).getRemarks() != null ? nickingInspectionTable.get(0).getRemarks() : "");
            ed_recommeded_date.setText(getFemaleSowingDate());
            ed_acutal_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(nickingInspectionTable.get(0).getActual_date()));
            if (nickingInspectionTable.get(0).getDate_of_inspection() != null) {
                edt_date_of_inspection.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(nickingInspectionTable.get(0).getDate_of_inspection()));
            } else {
                edt_date_of_inspection.setText("");
            }
            try {
                if(nickingInspectionTable.get(0).getAttachment()!=null){
                    String getImageId=nickingInspectionTable.get(0).getAttachment();
                    HitShowImageApi(getImageId );
                }
                else {
                    MDToast.makeText(getActivity(), nickingInspectionTable.get(0).getAttachment(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNickingInspData() {
        Nicking_InspectionLineModel tempNickingInspList = new Nicking_InspectionLineModel();
        try {
            tempNickingInspList.scheduler_no = scheduler_no;
            tempNickingInspList.arrival_plan_no = schedulerInspectionLineTable.getArrival_plan_no();
            tempNickingInspList.production_lot_no = production_lot_no;
            tempNickingInspList.date_of_inspection = DateTimeUtilsCustome.splitDateInYYYMMDD(edt_date_of_inspection.getText().toString());
            tempNickingInspList.crop_condition = dropdown_crop_condition.getText().toString();
            tempNickingInspList.crop_stage = dropdown_crop_stage.getText().toString();
            tempNickingInspList.status_of_female = edit_Status_of_Female.getText().toString().trim();
            tempNickingInspList.status_of_male = edit_Status_of_Male.getText().toString().trim();
            tempNickingInspList.next_plan_of_action = edit_next_plan_of_action.getText().toString().trim();
            tempNickingInspList.remarks = edit_remark.getText().toString().trim();
            tempNickingInspList.male_reciept_no = "0";
            tempNickingInspList.female_reciept_no = "0";
            tempNickingInspList.other_reciept_no = "0";
            tempNickingInspList.seed_setting = ed_seed_setting.getText().toString().trim();
            tempNickingInspList.recommended_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_recommeded_date.getText().toString().trim());
            tempNickingInspList.actual_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_acutal_date.getText().toString().trim());

            if (!ed_seed_stng_per.getText().toString().trim().equalsIgnoreCase("")) {
                String seed_per = String.valueOf(Float.parseFloat(ed_seed_stng_per.getText().toString().trim()));
                tempNickingInspList.seed_setting_prcnt = !seed_per.equalsIgnoreCase("") ? seed_per : "0.0";
            } else {
                tempNickingInspList.seed_setting_prcnt = "0.0";
            }
            String base_64_image = StaticMethods.convertBase64(selected_file_path);
            tempNickingInspList.attachment = base_64_image != null ? base_64_image : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonString = new Gson().toJson(tempNickingInspList);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        if (edit_remark.getText().toString().length() > 120) {
            Toast.makeText(getActivity(), "Remark value should be less than 120 characters in length ", Toast.LENGTH_SHORT).show();
        } else if (dropdown_crop_condition.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop crod. ", Toast.LENGTH_SHORT).show();
        } else if (dropdown_crop_stage.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter crop stage. ", Toast.LENGTH_SHORT).show();
        } else if (edt_date_of_inspection.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter date of inspection. ", Toast.LENGTH_SHORT).show();
        } else {
            insertNickingData(tempNickingInspList, asJsonObject);
        }
    }

    private void insertNickingData(Nicking_InspectionLineModel tempNickingInspList, JsonObject asJsonObject) {
        try {
            boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
            if (isNetwork) {
                NetworkInterface mApiService = ApiUtils.getPristineAPIService();
                Call<List<ResponseModel>> insertNickingInspection = mApiService.insertNickingInspection(asJsonObject);
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.showLoadingDialog(getActivity());
                List<Nicking_InspectionLineModel> temp_insert_Nicking_list = new ArrayList<>();
                insertNickingInspection.enqueue(new Callback<List<ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                loadingDialog.hideDialog();
                                List<ResponseModel> tempInsert = response.body();
                                if (tempInsert != null && tempInsert.size() > 0 && tempInsert.get(0).condition) {
                                    tempNickingInspList.sync_with_api_insp4 = 1;
                                    tempNickingInspList.attachment = selected_file_path;
                                    temp_insert_Nicking_list.add(tempNickingInspList);
                                    bindNickingInspWithLocalData(temp_insert_Nicking_list);
                                    StaticMethods.showMDToast(getActivity(), tempInsert.get(0).message, MDToast.TYPE_SUCCESS);
                                } else {
                                    StaticMethods.showMDToast(getActivity(), tempInsert.size() > 0 ? tempInsert.get(0).message : ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                                }
                            } else {
                                loadingDialog.hideDialog();
                                StaticMethods.showMDToast(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                            }
                        } catch (Exception e) {
                            loadingDialog.hideDialog();
                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Insert_Nicking", getActivity());
                        } finally {
                            loadingDialog.hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                        loadingDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Insert_Nicking", getActivity());
                    }
                });
            } else {
                tempNickingInspList.sync_with_api_insp4 = 0;
                tempNickingInspList.attachment = selected_file_path;
                ArrayList<Nicking_InspectionLineModel> nicking_InspectionLineModelArrayList = new ArrayList<>();
                nicking_InspectionLineModelArrayList.add(tempNickingInspList);
                bindNickingInspWithLocalData(nicking_InspectionLineModelArrayList);
                StaticMethods.showMDToast(getActivity(), "insert successful.", MDToast.TYPE_SUCCESS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void completeNicking() {
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getContext());
        if (isNetwork) {
            NetworkInterface mApiService = ApiUtils.getPristineAPIService();
            Call<List<CompleteGerminationInspectionModel>> call = mApiService.completeInspection("Inspection IV", nickingInspectionTable.get(0).getProduction_lot_no(), nickingInspectionTable.get(0).getScheduler_no());
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<CompleteGerminationInspectionModel>>() {
                @Override
                public void onResponse(Call<List<CompleteGerminationInspectionModel>> call, Response<List<CompleteGerminationInspectionModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            loadingDialog.hideDialog();
                            List<CompleteGerminationInspectionModel> completeInspectionModels = response.body();
                            if (completeInspectionModels != null && completeInspectionModels.size() > 0 && completeInspectionModels.get(0).condition) {
                                if (completeInspectionModels.get(0).nav_condition != 0) {
                                    completeOnlineOfflineNickingData("Online", 1, 0, nickingInspectionTable.get(0).getCreated_on(), "", 1);
                                    StaticMethods.showMDToast(getActivity(), completeInspectionModels.get(0).message + "//" + completeInspectionModels.get(0).nav_message + "//", MDToast.TYPE_SUCCESS);
                                } else {
                                    completeOnlineOfflineNickingData("Online", 0, 0, nickingInspectionTable.get(0).getCreated_on(), completeInspectionModels.get(0).nav_message, 1);
                                    StaticMethods.showMDToast(getActivity(), completeInspectionModels.get(0).message + "//" + completeInspectionModels.get(0).nav_message + "//", MDToast.TYPE_ERROR);
                                }
                            } else {
                                loadingDialog.hideDialog();
                                StaticMethods.showMDToast(getActivity(), completeInspectionModels.size() > 0 ? "Record not found !" : "Api not responding.", MDToast.TYPE_ERROR);
                            }
                        } else {
                            loadingDialog.hideDialog();
                            StaticMethods.showMDToast(getActivity(), response.message() + ". Error Code:" + response.code(), MDToast.TYPE_ERROR);
                        }

                    } catch (Exception e) {
                        loadingDialog.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "complete_Nicking", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<CompleteGerminationInspectionModel>> call, Throwable t) {
                    loadingDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "complete_Nicking", getActivity());
                }
            });
        } else {
            //todo offline...........
            completeOnlineOfflineNickingData("Offline", 0, 1, nickingInspectionTable.get(0).getCreated_on(), "", 0);
            StaticMethods.showMDToast(getActivity(), "Complete Successfully !", MDToast.TYPE_SUCCESS);
        }
    }

    private void completeOnlineOfflineNickingData(
            String flag, int nav_sync4, int sync_with_insp4, String completed_on, String nav_sync_error, int inspection4) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao inspection_oneHeaderDao = db.scheduleInspectionLineDao();
            if (flag.equalsIgnoreCase("Online")) {
                inspection_oneHeaderDao.updateOnServerCompleteNicking(nav_sync4, inspection4, scheduler_no, nickingInspectionTable.get(0).getProduction_lot_no(), completed_on, sync_with_insp4);
                inspection_oneHeaderDao.updateServerErrorDuringCompleteHeaderInspNicking(scheduler_no, nickingInspectionTable.get(0).getProduction_lot_no(), nav_sync_error, 1);
            } else {
                inspection_oneHeaderDao.updateOnServerCompleteNicking(nav_sync4, inspection4, scheduler_no, nickingInspectionTable.get(0).getProduction_lot_no(), completed_on, sync_with_insp4);//nav_message
            }
            bindNickingInspWithLocalData(new ArrayList<>());
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
                ca.add(Calendar.DAY_OF_YEAR, 55);

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