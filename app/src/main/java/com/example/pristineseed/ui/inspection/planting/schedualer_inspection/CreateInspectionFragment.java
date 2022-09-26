package com.example.pristineseed.ui.inspection.planting.schedualer_inspection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nickin2InspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nicking2InspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspInsertDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostFloweringDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostfloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionHeaderDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.SeedlingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.Seedling_InspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;

import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.Germination_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.HarvestingInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.MaturityInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Nicking2InspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Nicking_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.PostFloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Qc_Inspection_Model;
import com.example.pristineseed.model.scheduler_inspection.SeedLing_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.Vegitative_InspectionModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler.MenuMainPageFragment;
import com.example.pristineseed.ui.inspection.planting.qc_inspection.QCInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.FlowringInspection.FloweringInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.HarvestingInspection.HarvestingInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.MaturityInspection.MaturityInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.Nicking2Inspection.Nicking2InspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.Nicking2Inspection.NickingInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.PostFloweringInspection.PostFloweringInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.SeedlingInspection.Seedling_InspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.vegetativeInspection.Vegitative_Inspection_Fragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateInspectionFragment extends Fragment {
    private SessionManagement sessionManagement;
    private Scheduler_Header_Table inspectionHeader;
    private SchedulerInspectionLineTable inspectionline = null;
    private String next_plan_action, varity_alias_name;
    private NickingInspectionTable nickingInspectionTable = null;

    private String check_status = "Pending";
    private int pldMarked=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_germination_inspection_fragment, container, false);
    }

    private Chip germination_insp, flowering_insp, vegitative_insp, seedling_insp, nicking_insp, nicking2_insp, post_flowering_insp,
            maturity_insp, harvesting_insp, qc_insp;

    private TextView tv_header_title_text, tv_scheduler_no, tv_user_id, tv_season, tv_season_name, tv_production_center, tv_p_center_name,
            tv_date, tv_production_lot_no, tv_arrival_no, tv_item_name, tv_grower_name, tv_varity_code, tv_crop_code, tv_crop_type, tv_grower_city,
            tv_taluka_madal, tv_classof_seed, inspection_status_text;
    public static String scheduler_no = "", production_lot_no = "", arrival_plan_no = "";

    BroadcastReceiver passValueByShedulerBrodcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            try {
//                Bundle bundle = intent.getExtras();
//                if (bundle != null) {
//                    scheduler_no = bundle.getString("scheduler_no", "");
//                    arrival_plan_no = bundle.getString("arrival_plan_no", "");
//                    production_lot_no = bundle.getString("production_lot_no", "");
//                    boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
//                    if (network) {
//                        getProductionLotInspectionDetail();
//                    } else {
//                        getDataInspectionFromLocal();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    };
    Activity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        activity = getActivity();

        //getActivity().registerReceiver(passValueByShedulerBrodcastReceiver, new IntentFilter("passValueBySheduler"));
        try {
            if (getArguments() != null) {
                String json = getArguments().getString("pass_data", "");
                SchedulerBundleModel jsonObject = new Gson().fromJson(json, SchedulerBundleModel.class);
                arrival_plan_no = jsonObject.arrival_plan_no;
                scheduler_no = jsonObject.scheduler_no;
                production_lot_no = jsonObject.production_lot_no;
                boolean network = NetworkUtil.getConnectivityStatusBoolean(getActivity());
                if (network) {
                    getProductionLotInspectionDetail();
                } else {
                    getDataInspectionFromLocal();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        intiView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //getActivity().unregisterReceiver(passValueByShedulerBrodcastReceiver);
    }

    @Override
    public void onResume() {
        //getActivity().registerReceiver(passValueByShedulerBrodcastReceiver, new IntentFilter("passValueBySheduler"));
        if (inspectionline == null) {
            //checkPldMarkedArea();
            //checkPldMarkArea(contain_pld_status);
            showInspectionOnUI();

//            if(sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("QC Inspection")){
//
//                SchedulerBundleModel passmodel=new SchedulerBundleModel();
//                passmodel.arrival_plan_no=arrival_plan_no;
//                passmodel.production_lot_no = production_lot_no;
//                passmodel.scheduler_no = scheduler_no;
//
//                StaticMethods.LoadMenuChidData(sessionManagement, getActivity(), "FSP&HSP", "Create Inspection", "pass_data", new Gson().toJson(passmodel));
//            }
//            else if(sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("Normal Inspection")) {
//                SchedulerBundleModel passmodel=new SchedulerBundleModel();
//                passmodel.arrival_plan_no=arrival_plan_no;
//                passmodel.production_lot_no = production_lot_no;
//                passmodel.scheduler_no = scheduler_no;
//
//                StaticMethods.LoadMenuChidData(sessionManagement, getActivity(), "FSP&HSP", "Create Inspection", "pass_data", new Gson().toJson(passmodel));
//                //MenuMainPageFragment.viewPager.setCurrentItem(1);
//            }
            //MenuMainPageFragment.viewPager.setCurrentItem(1);
            getDataInspectionFromLocal();
        } else {
           // checkPldMarkedArea();
            showInspectionOnUI();
            getDataInspectionFromLocal();
        }
        super.onResume();
    }

    private void intiView(View view) {
        checkPldMarkedArea();
        tv_scheduler_no = view.findViewById(R.id.schduler_no);
        tv_user_id = view.findViewById(R.id.user_id);
        tv_season = view.findViewById(R.id.season);
        tv_season_name = view.findViewById(R.id.season_name);
        tv_production_center = view.findViewById(R.id.prod_center);
        tv_p_center_name = view.findViewById(R.id.p_center_name);
        tv_date = view.findViewById(R.id.date);
        tv_production_lot_no = view.findViewById(R.id.production_lot_no);
        tv_arrival_no = view.findViewById(R.id.arrival_no);
        tv_item_name = view.findViewById(R.id.item_name);
        tv_grower_name = view.findViewById(R.id.grower_name);
        tv_varity_code = view.findViewById(R.id.varity_code);
        tv_crop_code = view.findViewById(R.id.crop_code);
        tv_crop_type = view.findViewById(R.id.crop_type);
        tv_grower_city = view.findViewById(R.id.grower_city);
        tv_taluka_madal = view.findViewById(R.id.tv_gwer_tluka_mndl);
        tv_classof_seed = view.findViewById(R.id.item_classfeed);
        germination_insp = view.findViewById(R.id.germination_insp);
        flowering_insp = view.findViewById(R.id.flowering_insp);
        vegitative_insp = view.findViewById(R.id.vegitative_insp);
        seedling_insp = view.findViewById(R.id.seedling_insp);
        nicking_insp = view.findViewById(R.id.nicking_insp);
        nicking2_insp = view.findViewById(R.id.nicking2_insp);
        post_flowering_insp = view.findViewById(R.id.post_flowering_insp);
        maturity_insp = view.findViewById(R.id.maturity_insp);
        harvesting_insp = view.findViewById(R.id.harvesting_insp);
        qc_insp = view.findViewById(R.id.qc_insp);
        inspection_status_text = view.findViewById(R.id.inspection_status);
        tv_header_title_text = view.findViewById(R.id.tv_header_title);

        germination_insp.setOnClickListener(v -> {
            try {

                    Bundle bundle = new Bundle();
                    bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                    bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                    bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                    bundle.putString("production_lot_no", inspectionline.getProduction_lot_no() != null ? inspectionline.getProduction_lot_no() : "");
                    GerminationInspectionFragment inspectionOneFragment = new GerminationInspectionFragment();
                    inspectionOneFragment.setArguments(bundle);
                    StaticMethods.loadFragmentsWithBackStack(getActivity(), inspectionOneFragment, "Germination_inspection1");

            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        seedling_insp.setOnClickListener(v -> {
            try {

                    if (inspectionline.getInspection_1() > 0 || inspectionline.getIns1_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        Seedling_InspectionFragment inspectionTwoFragment = new Seedling_InspectionFragment();
                        inspectionTwoFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), inspectionTwoFragment, "Seedling_inspection2");
                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        vegitative_insp.setOnClickListener(v -> {
            try {
                if (pldMarked > 0 && inspectionline.getInspection_3() <= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (inspectionline.getInspection_2() > 0 || inspectionline.getIns2_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        Vegitative_Inspection_Fragment vegitative_inspection_fragment = new Vegitative_Inspection_Fragment();
                        vegitative_inspection_fragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), vegitative_inspection_fragment, "Vegitative_inspection3");
                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        nicking_insp.setOnClickListener(v -> {
            try {
                if (pldMarked > 0 && inspectionline.getInspection_4() <= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (inspectionline.getInspection_3() > 0 || inspectionline.getIns3_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        NickingInspectionFragment inspectionFragment = new NickingInspectionFragment();
                        inspectionFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), inspectionFragment, "Nicking_inspection4");

                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
               e.printStackTrace();
            }
        });

        nicking2_insp.setOnClickListener(v -> {
            try {
                if (pldMarked > 0 && inspectionline.getInspection_5() <= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (next_plan_action != null && !next_plan_action.equalsIgnoreCase("")) {
                        if (inspectionline.getInspection_4() > 0 || inspectionline.getIns4_sync_with_server() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                            bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                            bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                            bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                            Nicking2InspectionFragment nicking2InspectionFragment = new Nicking2InspectionFragment();
                            nicking2InspectionFragment.setArguments(bundle);
                            StaticMethods.loadFragmentsWithBackStack(getActivity(), nicking2InspectionFragment, "Nicking2_inspection5");
                        } else {
                            Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "You cant' perfrom this acction as next plan of action is blank . Please move to next inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        flowering_insp.setOnClickListener(v -> {
            try {
                if (pldMarked > 0 && inspectionline.getInspection_6()<= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (next_plan_action != null && !next_plan_action.equalsIgnoreCase("")) {
                        if (inspectionline.getInspection_5() > 0 || inspectionline.getIns5_sync_with_server() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                            bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                            bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                            bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                            FloweringInspectionFragment floweringInspectionFragment = new FloweringInspectionFragment();
                            floweringInspectionFragment.setArguments(bundle);
                            StaticMethods.loadFragmentsWithBackStack(getActivity(), floweringInspectionFragment, "Flowering_inspection6");
                        } else {
                            Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (inspectionline.getInspection_4() > 0 || inspectionline.getIns4_sync_with_server() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                            bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                            bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                            bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                            FloweringInspectionFragment floweringInspectionFragment = new FloweringInspectionFragment();
                            floweringInspectionFragment.setArguments(bundle);
                            StaticMethods.loadFragmentsWithBackStack(getActivity(), floweringInspectionFragment, "Flowering_inspection6");
                        } else {
                            Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        post_flowering_insp.setOnClickListener(v -> {
            try {
                if (pldMarked > 0 && inspectionline.getInspection_7() <= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (inspectionline.getInspection_6() > 0 || inspectionline.getIns6_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        PostFloweringInspectionFragment postfloweringInspectionFragment = new PostFloweringInspectionFragment();
                        postfloweringInspectionFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), postfloweringInspectionFragment, "Post_Flowering_inspection7");

                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        maturity_insp.setOnClickListener(v -> {
            try {

                if (pldMarked > 0 && inspectionline.getInspection_8() <= 0) {
                    MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                } else {
                    if (inspectionline.getInspection_7() > 0 || inspectionline.getIns7_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        MaturityInspectionFragment maturityInspectionFragment = new MaturityInspectionFragment();
                        maturityInspectionFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), maturityInspectionFragment, "Maturity_inspection8");
                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        harvesting_insp.setOnClickListener(v -> {
            if (pldMarked > 0 && inspectionline.getInspection_9() <= 0 ) {
                MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
            } else {
                try {


                    if (inspectionline.getInspection_8() > 0 || inspectionline.getIns8_sync_with_server() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        HarvestingInspectionFragment harvestingInspectionFragment = new HarvestingInspectionFragment();
                        harvestingInspectionFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), harvestingInspectionFragment, "harvesting_inspection9");
                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        qc_insp.setOnClickListener(v -> {

            if (pldMarked > 0 && inspectionline.getInspection_qc() <= 0 ) {
                MDToast.makeText(getActivity(), "You can't perform any action as PLD area is marked.", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
            } else {
                try {
                    if (inspectionline.getInspection_9() >= 0 || inspectionline.getIns9_sync_with_server() >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("header_detail", new Gson().toJson(inspectionHeader));
                        bundle.putString("production_lot_no", inspectionline.getProduction_lot_no());
                        bundle.putString("scheduler_no", inspectionline != null ? inspectionline.getSchedule_no() : "");
                        bundle.putString("scheduler_line_detail", new Gson().toJson(inspectionline));
                        QCInspectionFragment qcInspectionFragment = new QCInspectionFragment();
                        qcInspectionFragment.setArguments(bundle);
                        StaticMethods.loadFragmentsWithBackStack(getActivity(), qcInspectionFragment, "qc_inspection");
                    } else {
                        Toast.makeText(getActivity(), "First complete previous inspection!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDataInspectionFromLocal() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            ScheduleInspectionHeaderDao scheduleInspectionHeaderDao = db.scheduleInspectionHeaderDao();
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            NickingInspInsertDao nickingInspInsertDao = db.nickingInspInsertDao();
            inspectionHeader = scheduleInspectionHeaderDao.getHeaderByScheduler_no(scheduler_no);
            inspectionline = scheduleInspectionLineDao.getAllDatabyLotNo(scheduler_no, production_lot_no);
            nickingInspectionTable = nickingInspInsertDao.nickingDataGet(production_lot_no);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
            if (inspectionline != null) {
                JsonObject json = new JsonParser().parse(new Gson().toJson(inspectionline)).getAsJsonObject();
                Log.e("local_data", json.toString());
                Log.e("local_header_data", new JsonParser().parse(new Gson().toJson(inspectionHeader)).getAsJsonObject().toString());
            }
        }
        try {
            tv_header_title_text.setText("Header Details" + "(" + inspectionHeader.getSchedule_no() + ")");
            tv_arrival_no.setText(inspectionline.getArrival_plan_no() != null ? inspectionline.getArrival_plan_no() : "");
            tv_scheduler_no.setText(inspectionHeader.getSchedule_no() != null ? inspectionHeader.getSchedule_no() : "");
            tv_user_id.setText(inspectionHeader.getUser_id());
            tv_season.setText(inspectionHeader.getSeason() + "(" + inspectionHeader.getSeason_name() + ")");
            tv_production_center.setText(inspectionHeader.getProduction_centre() + "(" + inspectionHeader.getProduction_centre_name() + ")");
            tv_date.setText(DateTimeUtilsCustome.getDate_Time(inspectionHeader.getDate()));
            tv_grower_name.setText(inspectionline.getGrower_owner());
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(activity);
            try {
                PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
                if (inspectionline != null) {
                    PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getVaityCodeAliasName(production_lot_no, inspectionline.getVariety_code());
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
            tv_crop_code.setText(inspectionline.getCrop_code());
            tv_crop_type.setText(inspectionline.getItem_crop_type());
            tv_grower_city.setText(inspectionline.getGrower_city());
            tv_production_lot_no.setText(inspectionline.getProduction_lot_no());
            tv_classof_seed.setText(inspectionline.getItem_class_of_seeds());
            if (nickingInspectionTable != null) {
                next_plan_action = nickingInspectionTable.getNext_plan_of_action();
            }
            Log.e("inspection_2", String.valueOf(inspectionline.getInspection_1() > 0 && inspectionline.getInspection_2() > 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearAllSelectededBackgraundFirst();
            //todo check online status of inspection...
            checkInspectionOnlineStatus();
            //todo check offline status of inspection...
            showInspectionOnUI();
        }

    }

    private void checkInspectionOnlineStatus() {
        if (inspectionline != null) {
            if (inspectionline.getInspection_1() > 0 || inspectionline.getIns1_sync_with_server() > 0) {//|| inspectionline.getIns1_sync_with_server() > 0
                inspection_status_text.setText("GER. Done");
                check_status = "GER. Done";
                setSlectedStyleBtn(germination_insp, "Online");

                if (inspectionline.getInspection_2() > 0 || inspectionline.getIns2_sync_with_server() > 0) {//|| inspectionline.getIns2_sync_with_server() > 0
                    inspection_status_text.setText("SED. Done");
                    check_status = "SED. Done";
                    setSlectedStyleBtn(seedling_insp, "Online");
                }

                if (inspectionline.getInspection_3() > 0 || inspectionline.getIns3_sync_with_server() > 0) {//|| inspectionline.getIns3_sync_with_server() > 0
                    inspection_status_text.setText("VEG. Done");
                    check_status = "VEG. Done";
                    setSlectedStyleBtn(vegitative_insp, "Online");
                }

                if (inspectionline.getInspection_4() > 0 || inspectionline.getIns4_sync_with_server() > 0) {// || inspectionline.getIns4_sync_with_server() > 0)
                    inspection_status_text.setText("NIK. Done");
                    check_status = "NIK. Done";
                    setSlectedStyleBtn(nicking_insp, "Online");
                }

                if ((inspectionline.getInspection_5() > 0) || inspectionline.getIns5_sync_with_server() > 0) {//|| inspectionline.getIns5_sync_with_server() > 0
                    inspection_status_text.setText("NIK2. Done");
                    check_status = "NIK2. Done";
                    setSlectedStyleBtn(nicking2_insp, "Online");
                }

                if (inspectionline.getInspection_6() > 0 || inspectionline.getIns6_sync_with_server() > 0) {//|| inspectionline.getIns6_sync_with_server() > 0
                    inspection_status_text.setText("FLWR. Done");
                    check_status = "FLWR. Done";
                    setSlectedStyleBtn(flowering_insp, "Online");
                }

                if (inspectionline.getInspection_7() > 0 || inspectionline.getIns7_sync_with_server() > 0) {// || inspectionline.getIns7_sync_with_server() > 0
                    inspection_status_text.setText("PostFLWR. Done");
                    check_status = "PostFLWR. Done";
                    setSlectedStyleBtn(post_flowering_insp, "Online");
                }
                if (inspectionline.getInspection_8() > 0 || inspectionline.getIns8_sync_with_server() > 0) {// || inspectionline.getIns8_sync_with_server() > 0
                    inspection_status_text.setText("MARTY. Done");
                    check_status = "MARTY. Done";
                    setSlectedStyleBtn(maturity_insp, "Online");
                }

                if (inspectionline.getInspection_9() > 0 || inspectionline.getIns9_sync_with_server() > 0) {//|| inspectionline.getIns9_sync_with_server() > 0
                    inspection_status_text.setText("HARVST. Done");
                    check_status = "HARVST. Done";
                    setSlectedStyleBtn(harvesting_insp, "Online");
                }

                if (inspectionline.getInspection_qc() > 0 || inspectionline.getInsqc_sync_with_server() > 0) {// || inspectionline.getInsqc_sync_with_server() > 0
                    inspection_status_text.setText("QC Done");
                    check_status = "QC Done";
                    setSlectedStyleBtn(qc_insp, "Online");
                } else {
                    inspection_status_text.setText(check_status);
                }
            } else {
                inspection_status_text.setText(check_status);
            }
        }

    }

    private void checkInspectionOfflineStatus() {
        if (inspectionline != null) {
            if (inspectionline.getIns1_sync_with_server() > 0 || inspectionline.getInspection_1() == 0) {//|| inspectionline.getInspection_1() == 0
                inspection_status_text.setText("GER. Done");
                check_status = "GER. Done";
                setSlectedStyleBtn(germination_insp, "Offline");
            }

            if (inspectionline.getIns2_sync_with_server() > 0 || inspectionline.getInspection_2() == 0) {//|| inspectionline.getInspection_2() == 0
                inspection_status_text.setText("SED. Done");
                check_status = "SED. Done";
                setSlectedStyleBtn(seedling_insp, "Offline");
            }

            if ((inspectionline.getIns3_sync_with_server() > 0 || inspectionline.getInspection_3() == 0)) {//|| inspectionline.getInspection_3() == 0
                inspection_status_text.setText("VEG. Done");
                check_status = "VEG. Done";
                setSlectedStyleBtn(vegitative_insp, "Offline");
            }

            if ((inspectionline.getIns4_sync_with_server() > 0 || inspectionline.getInspection_4() == 0)) {//|| inspectionline.getInspection_4() == 0
                inspection_status_text.setText("NIK. Done");
                check_status = "NIK. Done";
                setSlectedStyleBtn(nicking_insp, "Offline");
            }
            if ((inspectionline.getIns5_sync_with_server() > 0 || inspectionline.getInspection_5() == 0)) {//|| inspectionline.getInspection_5() == 0
                inspection_status_text.setText("NIK2. Done");
                check_status = "NIK2. Done";
                setSlectedStyleBtn(nicking2_insp, "Offline");
            }
            if ((inspectionline.getIns6_sync_with_server() > 0) || inspectionline.getInspection_6() == 0) {//|| inspectionline.getInspection_6() == 0
                inspection_status_text.setText("FLWR. Done");
                check_status = "FLWR. Done";
                setSlectedStyleBtn(flowering_insp, "Offline");
            }
            if ((inspectionline.getIns7_sync_with_server() > 0 || inspectionline.getInspection_7() == 0)) {// || inspectionline.getInspection_7() == 0
                inspection_status_text.setText("PostFLWR. Done");
                setSlectedStyleBtn(post_flowering_insp, "Offline");
            }
            if (inspectionline.getIns8_sync_with_server() > 0 || inspectionline.getInspection_8() == 0) {//||inspectionline.getInspection_8() == 0
                inspection_status_text.setText("MARTY. Done");
                setSlectedStyleBtn(maturity_insp, "Offline");
            }
            if (inspectionline.getIns9_sync_with_server() > 0 || inspectionline.getInspection_9() == 0) {// || inspectionline.getInspection_9() == 0
                inspection_status_text.setText("HARVST. Done");
                setSlectedStyleBtn(harvesting_insp, "Offline");
            }
            if (inspectionline.getInsqc_sync_with_server() > 0 || inspectionline.getInspection_qc() == 0) {//|| inspectionline.getInspection_qc() == 0
                inspection_status_text.setText("QC Done");
                setSlectedStyleBtn(qc_insp, "Offline");
            }

            inspection_status_text.setText(check_status);
        }
    }

    private void clearAllSelectededBackgraundFirst() {
        germination_insp.setChipBackgroundColorResource(R.color.gray4);
        germination_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        germination_insp.setChipStrokeColorResource(R.color.gray4);
        germination_insp.setChipIcon(null);

        seedling_insp.setChipBackgroundColorResource(R.color.gray4);
        seedling_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        seedling_insp.setChipStrokeColorResource(R.color.gray4);
        seedling_insp.setChipIcon(null);

        vegitative_insp.setChipBackgroundColorResource(R.color.gray4);
        vegitative_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        vegitative_insp.setChipStrokeColorResource(R.color.gray4);
        vegitative_insp.setChipIcon(null);

        nicking_insp.setChipBackgroundColorResource(R.color.gray4);
        nicking_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        nicking_insp.setChipStrokeColorResource(R.color.gray4);
        nicking_insp.setChipIcon(null);

        nicking2_insp.setChipBackgroundColorResource(R.color.gray4);
        nicking2_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        nicking2_insp.setChipStrokeColorResource(R.color.gray4);
        nicking2_insp.setChipIcon(null);

        flowering_insp.setChipBackgroundColorResource(R.color.gray4);
        flowering_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        flowering_insp.setChipStrokeColorResource(R.color.gray4);
        flowering_insp.setChipIcon(null);

        post_flowering_insp.setChipBackgroundColorResource(R.color.gray4);
        post_flowering_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        post_flowering_insp.setChipStrokeColorResource(R.color.gray4);
        post_flowering_insp.setChipIcon(null);

        maturity_insp.setChipBackgroundColorResource(R.color.gray4);
        maturity_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        maturity_insp.setChipStrokeColorResource(R.color.gray4);
        maturity_insp.setChipIcon(null);

        harvesting_insp.setChipBackgroundColorResource(R.color.gray4);
        harvesting_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        harvesting_insp.setChipStrokeColorResource(R.color.gray4);
        harvesting_insp.setChipIcon(null);

        qc_insp.setChipBackgroundColorResource(R.color.gray4);
        qc_insp.setTextColor(activity.getResources().getColor(R.color.gray1));
        qc_insp.setChipStrokeColorResource(R.color.gray4);
        qc_insp.setChipIcon(null);
    }

    private void getProductionLotInspectionDetail() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("production_lot_no", production_lot_no);
        Call<List<GerminationInspectionHeaderModel>> call = mAPIService.getScanInspction(jsonObject);
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        call.enqueue(new Callback<List<GerminationInspectionHeaderModel>>() {
            @Override
            public void onResponse(Call<List<GerminationInspectionHeaderModel>> call, Response<List<GerminationInspectionHeaderModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        int insp1 = 0, insp2 = 0, insp3 = 0, insp4 = 0, insp5 = 0, insp6 = 0, insp7 = 0, insp8 = 0, insp9 = 0, inspQc = 0;
                        String insp1_comp_on = null, insp2_comp_on = null, insp3_comp_on = null, insp4_comp_on = null, insp5_comp_on = null, insp6_comp_on = null, insp7_comp_on = null, insp8_comp_on = null, insp9_comp_on = null, insp_qc_comp_on = null;
                        List<GerminationInspectionHeaderModel> schedule_scan_lot_List = response.body();
                        if (schedule_scan_lot_List != null && schedule_scan_lot_List.size() > 0 && schedule_scan_lot_List.get(0).condition) {
                            insertGerminationInspectionLine(schedule_scan_lot_List.get(0).germination_ins1);
                            insertInspectionSeedlingLine(schedule_scan_lot_List.get(0).seedling_ins2);
                            insertVegitativeInspectionLine(schedule_scan_lot_List.get(0).vegitative_ins3);
                            bindNickingInspWithLocalData(schedule_scan_lot_List.get(0).nicking_ins4);
                            bindNicking2InspWithLocalData(schedule_scan_lot_List.get(0).nicking2_ins5);
                            bindFlowweringInspWithLocalData(schedule_scan_lot_List.get(0).flowering_ins6);
                            bindPostFlowweringInspWithLocalData(schedule_scan_lot_List.get(0).post_flowering_ins7);
                            bindPostMaturityInspWithLocalData(schedule_scan_lot_List.get(0).maturity_ins8);
                            bindHarvestingInspWithLocalData(schedule_scan_lot_List.get(0).harvesting_inspection9);
                            bindQCInspWithLocalData(schedule_scan_lot_List.get(0).inspection_qc1);
                            if (schedule_scan_lot_List.get(0).inspection_1 > 0) {
                                insp1 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins1_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_2 > 0) {
                                insp2 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins2_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_3 > 0) {
                                insp3 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins3_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_4 > 0) {
                                insp4 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins4_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_5 > 0) {
                                insp5 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins5_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_6 > 0) {
                                insp6 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins6_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_7 > 0) {
                                insp7 = 1;
                                insp1_comp_on = schedule_scan_lot_List.get(0).ins7_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_8 > 0) {
                                insp8 = 1;
                                insp8_comp_on = schedule_scan_lot_List.get(0).ins8_completed_on;
                            }

                            if (schedule_scan_lot_List.get(0).inspection_9 > 0) {
                                insp9 = 1;
                                insp9_comp_on = schedule_scan_lot_List.get(0).ins9_completed_on;
                            }
                            if (schedule_scan_lot_List.get(0).inspection_qc > 0) {
                                inspQc = 1;
                                insp_qc_comp_on = schedule_scan_lot_List.get(0).ins_qc_completed_on;
                            }

                            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(activity);
                            try {
                                ScheduleInspectionLineDao scheduleInspectionLineDao = pristineDatabase.scheduleInspectionLineDao();
                                scheduleInspectionLineDao.updateInspectionOnLine(insp1, insp2, insp3, insp4, insp5, insp6, insp7, insp8, insp9, inspQc, schedule_scan_lot_List.get(0).ins1_nav_sync, schedule_scan_lot_List.get(0).ins2_nav_sync,
                                        schedule_scan_lot_List.get(0).ins3_nav_sync, schedule_scan_lot_List.get(0).ins4_nav_sync, schedule_scan_lot_List.get(0).ins5_nav_sync,
                                        schedule_scan_lot_List.get(0).ins6_nav_sync, schedule_scan_lot_List.get(0).ins7_nav_sync, schedule_scan_lot_List.get(0).ins8_nav_sync, schedule_scan_lot_List.get(0).ins9_nav_sync, schedule_scan_lot_List.get(0).ins_qc_nav_sync, schedule_scan_lot_List.get(0).production_lot_no,
                                        insp1_comp_on, insp2_comp_on, insp3_comp_on, insp4_comp_on, insp5_comp_on, insp6_comp_on, insp7_comp_on, insp8_comp_on, insp9_comp_on, insp_qc_comp_on);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                pristineDatabase.close();
                                pristineDatabase.destroyInstance();
                                getDataInspectionFromLocal();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), schedule_scan_lot_List.size() > 0 ? "Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Log.e("exception database", e.getMessage() + "cause");
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Schedule_scan_lot_", getActivity());
                } finally {
                    getDataInspectionFromLocal();
                }
            }

            @Override
            public void onFailure(Call<List<GerminationInspectionHeaderModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "Schedule_scan_lot_", getActivity());
            }
        });
    }

    private void insertGerminationInspectionLine(List<Germination_InspectionLineModel> germination_ins1) {
        if (germination_ins1 != null && germination_ins1.size() > 0) {
            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
            try {
                GerminationInspectionDao germinationInspectionDao = db.germinationInspectionDao();
                for (int i = 0; i < germination_ins1.size(); i++) {
                    GerminationInspection1_Table germinationInspection1_table = GerminationInspection1_Table.insertGerminationInspection(germination_ins1.get(i));
                    germinationInspection1_table.setSync_with_api(1);
                    if (germinationInspectionDao.isDataExist(germination_ins1.get(i).production_lot_no) > 0) {
                        germinationInspectionDao.update(germinationInspection1_table);
                    } else {
                        germinationInspectionDao.insert(germinationInspection1_table);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
                db.destroyInstance();
            }
        }
    }

    private void insertInspectionSeedlingLine(List<SeedLing_InspectionLineModel> seedling_ins2List) {
        if (seedling_ins2List != null && seedling_ins2List.size() > 0) {
            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
            try {
                Seedling_InspectionDao seedling_inspection_dao = db.seedling_inspectionDao();
                for (int i = 0; i < seedling_ins2List.size(); i++) {
                    SeedlingInspectionTable seedlingInspectionTable = SeedlingInspectionTable.insertSeedlingInspection(seedling_ins2List.get(i));
                    seedlingInspectionTable.setSync_with_api_ins2(1);
                    if (seedling_inspection_dao.isDataExist(seedling_ins2List.get(0).production_lot_no) > 0) {
                        seedling_inspection_dao.update(seedlingInspectionTable);
                    } else {
                        seedling_inspection_dao.insert(seedlingInspectionTable);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
                db.destroyInstance();
            }
        }
    }

    private void insertVegitativeInspectionLine(List<Vegitative_InspectionModel> vegittvInspectionList) {
        if (vegittvInspectionList != null && vegittvInspectionList.size() > 0) {
            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
            try {
                VegitativeInspectionDao vegitativeInspectionDao = db.vegitativeInspectionDao();
                for (int i = 0; i < vegittvInspectionList.size(); i++) {
                    VegitativeInspectionTable vegitativeInspectionTable = VegitativeInspectionTable.insertVegitativeDataIntoLocal(vegittvInspectionList.get(i));
                    vegitativeInspectionTable.setSyncWith_Api(1);
                    if (vegitativeInspectionDao.isDataExist(vegittvInspectionList.get(i).production_lot_no) > 0) {
                        vegitativeInspectionDao.update(vegitativeInspectionTable);
                    } else {
                        vegitativeInspectionDao.insert(vegitativeInspectionTable);
                    }
                }
            } catch (Exception e) {
                Log.e("log_e", e.getMessage());
            } finally {
                db.close();
                db.destroyInstance();
            }
        }
    }

    private void bindNickingInspWithLocalData(List<Nicking_InspectionLineModel> tempNickingInspListModel) {
        if (tempNickingInspListModel != null && tempNickingInspListModel.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                NickingInspInsertDao nickingInspInsertDao = pristineDatabase.nickingInspInsertDao();
                if (tempNickingInspListModel.size() > 0) {
                    for (int i = 0; i < tempNickingInspListModel.size(); i++) {
                        NickingInspectionTable nickingInspInsertTable = NickingInspectionTable.insertNickingDataFromServer(tempNickingInspListModel.get(i));
                        nickingInspInsertTable.setSync_with_api_insp4(1);
                        int dataExist = nickingInspInsertDao.isDataExist(tempNickingInspListModel.get(0).production_lot_no);
                        if (dataExist > 0) {
                            nickingInspInsertDao.update(nickingInspInsertTable);
                        } else {
                            nickingInspInsertDao.insert(nickingInspInsertTable);
                        }
                    }
                    List<NickingInspectionTable> nickingInspectionTableList = nickingInspInsertDao.getInpection1DataByLotNo(production_lot_no);
                    next_plan_action = nickingInspectionTableList.get(0).getNext_plan_of_action();
                    if (next_plan_action != null && !next_plan_action.equalsIgnoreCase("")) {
                        nicking2_insp.setVisibility(View.VISIBLE);
                    } else {
                        nicking2_insp.setVisibility(View.GONE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindNicking2InspWithLocalData(List<Nicking2InspectionModel> tempNickingInspListModel) {
        if (tempNickingInspListModel != null && tempNickingInspListModel.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                Nickin2InspectionDao nicking2InspInsertDao = pristineDatabase.nickin2InspectionDao();
                if (tempNickingInspListModel.size() > 0) {
                    for (int i = 0; i < tempNickingInspListModel.size(); i++) {
                        Nicking2InspectionTable nicking2InspInsertTable = Nicking2InspectionTable.insertNicking2InsoectionData(tempNickingInspListModel.get(i));
                        nicking2InspInsertTable.setSyncWithApi5(1);
                        int dataExist = nicking2InspInsertDao.isDataExist(nicking2InspInsertTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            nicking2InspInsertDao.update(nicking2InspInsertTable);
                        } else {
                            nicking2InspInsertDao.insert(nicking2InspInsertTable);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindFlowweringInspWithLocalData(List<FloweringInspectionModel> tempFloweringInspListModelList) {
        if (tempFloweringInspListModelList != null && tempFloweringInspListModelList.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                FloweringInspectionDao floweringInspectionDao = pristineDatabase.floweringInspectionDao();
                if (tempFloweringInspListModelList.size() > 0) {
                    for (int i = 0; i < tempFloweringInspListModelList.size(); i++) {
                        FloweringInspectionTable floweringInspectionTable = FloweringInspectionTable.inertFloweringInspection(tempFloweringInspListModelList.get(i));
                        floweringInspectionTable.setSyncwith_api6(1);
                        int dataExist = floweringInspectionDao.isDataExist(floweringInspectionTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            floweringInspectionDao.update(floweringInspectionTable);
                        } else {
                            floweringInspectionDao.insert(floweringInspectionTable);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindPostFlowweringInspWithLocalData(List<PostFloweringInspectionModel> tempPostFloweringInspListModelList) {
        if (tempPostFloweringInspListModelList != null && tempPostFloweringInspListModelList.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                PostFloweringDao postfloweringInspectionDao = pristineDatabase.postFloweringDao();
                if (tempPostFloweringInspListModelList.size() > 0) {
                    for (int i = 0; i < tempPostFloweringInspListModelList.size(); i++) {
                        PostfloweringInspectionTable postfloweringInspectionTable = PostfloweringInspectionTable.inertPostFloweringInspection(tempPostFloweringInspListModelList.get(i));
                        postfloweringInspectionTable.setSynWithApi7(1);
                        int dataExist = postfloweringInspectionDao.isDataExist(postfloweringInspectionTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            postfloweringInspectionDao.update(postfloweringInspectionTable);
                        } else {
                            postfloweringInspectionDao.insert(postfloweringInspectionTable);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindPostMaturityInspWithLocalData(List<MaturityInspectionModel> tempMaturityInspListModelList) {
        if (tempMaturityInspListModelList != null && tempMaturityInspListModelList.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                MaturityInspectionDao maturityInspectionDao = pristineDatabase.maturityInspectionDao();
                if (tempMaturityInspListModelList.size() > 0) {
                    for (int i = 0; i < tempMaturityInspListModelList.size(); i++) {
                        MaturityInspectionTable maturityInspectionTable = MaturityInspectionTable.insertMatuirtyInspectionDataIntoLocal(tempMaturityInspListModelList.get(i));
                        maturityInspectionTable.setSyncWithApi8(1);
                        int dataExist = maturityInspectionDao.isDataExist(maturityInspectionTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            maturityInspectionDao.update(maturityInspectionTable);
                        } else {
                            maturityInspectionDao.insert(maturityInspectionTable);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindHarvestingInspWithLocalData(List<HarvestingInspectionModel> tempHarvstngInspListModelList) {
        if (tempHarvstngInspListModelList != null && tempHarvstngInspListModelList.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getContext());
            try {
                HarvestingInspectionDao harvestingInspectionDao = pristineDatabase.harvestingInspectionDao();
                if (tempHarvstngInspListModelList.size() > 0) {
                    for (int i = 0; i < tempHarvstngInspListModelList.size(); i++) {
                        HarvestingInspectionTable harvestingInspectionTable = HarvestingInspectionTable.insertHarvestingDataIntoLocal(tempHarvstngInspListModelList.get(i));
                        harvestingInspectionTable.setSynWithApi9(1);
                        int dataExist = harvestingInspectionDao.isDataExist(harvestingInspectionTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            harvestingInspectionDao.update(harvestingInspectionTable);
                        } else {
                            harvestingInspectionDao.insert(harvestingInspectionTable);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("log_e", e.getMessage());
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    private void bindQCInspWithLocalData(List<Qc_Inspection_Model> tempQcInspListModelList) {
        if (tempQcInspListModelList != null && tempQcInspListModelList.size() > 0) {
            PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(activity);
            try {
                QcInspectionDao qcInspectionDao = pristineDatabase.qcInspectionDao();
                if (tempQcInspListModelList.size() > 0) {
                    for (int i = 0; i < tempQcInspListModelList.size(); i++) {
                        QcInspectionTable qcInspectionTable = QcInspectionTable.insertOcDatafromIntoLocal(tempQcInspListModelList.get(i));
                        qcInspectionTable.setSyncwithQc(1);
                        int dataExist = qcInspectionDao.isDataExist(qcInspectionTable.getProduction_lot_no());
                        if (dataExist > 0) {
                            qcInspectionDao.update(qcInspectionTable);
                        } else {
                            qcInspectionDao.insert(qcInspectionTable);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }
    }

    void setSlectedStyleBtn(Chip materialButton, String flag) {
        inspection_status_text.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        if (flag.equalsIgnoreCase("Online")) {
            materialButton.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.chip_ripple)));
            materialButton.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            materialButton.setChipStrokeColorResource(R.color.colorPrimaryDark);
            materialButton.setChipStrokeWidth(2f);
        } else {
            materialButton.setChipBackgroundColorResource(R.color.chip_ripple);
            materialButton.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            materialButton.setChipStrokeColorResource(R.color.colorPrimaryDark);
            materialButton.setChipStrokeWidth(2f);
            materialButton.setChipIconResource(R.drawable.ic_baseline_pending_actions_24);
        }
    }

    private void showInspectionOnUI() {
        if (sessionManagement.getuser_app_inspection_type() != null) {
            if (sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("Normal Inspection")) {
                germination_insp.setVisibility(View.VISIBLE);
                seedling_insp.setVisibility(View.VISIBLE);
                vegitative_insp.setVisibility(View.VISIBLE);
                nicking_insp.setVisibility(View.VISIBLE);
                nicking2_insp.setVisibility(View.VISIBLE);
                flowering_insp.setVisibility(View.VISIBLE);
                post_flowering_insp.setVisibility(View.VISIBLE);
                maturity_insp.setVisibility(View.VISIBLE);
                harvesting_insp.setVisibility(View.VISIBLE);
                qc_insp.setVisibility(View.GONE);
            } else if (sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("QC Inspection")) {
                if (inspectionline != null) {
              /*  if (inspectionline != null && inspectionline.getIns1_nav_sync() > 0 && inspectionline.getIns2_nav_sync() > 0 && inspectionline.getIns3_nav_sync() > 0
                        && inspectionline.getIns4_nav_sync() > 0 && inspectionline.getIns5_nav_sync() > 0 && inspectionline.getIns6_nav_sync() > 0 && inspectionline.getIns7_nav_sync() > 0
                        && inspectionline.getIns8_nav_sync() > 0 && inspectionline.getIns9_nav_sync() > 0) {*/
                    germination_insp.setVisibility(View.GONE);
                    seedling_insp.setVisibility(View.GONE);
                    vegitative_insp.setVisibility(View.GONE);
                    nicking_insp.setVisibility(View.GONE);
                    nicking2_insp.setVisibility(View.GONE);
                    flowering_insp.setVisibility(View.GONE);
                    post_flowering_insp.setVisibility(View.GONE);
                    maturity_insp.setVisibility(View.GONE);
                    harvesting_insp.setVisibility(View.GONE);
                    qc_insp.setVisibility(View.VISIBLE);
                }
                //qc_insp.setVisibility(View.GONE);
            }
        }
    }


/*    void visibilityInspection() {
        germination_insp.setVisibility(View.VISIBLE);
        seedling_insp.setVisibility(View.VISIBLE);
        vegitative_insp.setVisibility(View.VISIBLE);
        nicking_insp.setVisibility(View.VISIBLE);
        nicking2_insp.setVisibility(View.VISIBLE);
        flowering_insp.setVisibility(View.VISIBLE);
        post_flowering_insp.setVisibility(View.VISIBLE);
        maturity_insp.setVisibility(View.VISIBLE);
        harvesting_insp.setVisibility(View.VISIBLE);
    }*/

   /* private void checkPldMarkArea() {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            if (production_lot_no != null) {
                contain_pld_status = plantingLineLotListDao.checkPldMark(production_lot_no);
                if (inspectionline != null) {
                    PlantingLineLotListTable plantingLineLotListTable = plantingLineLotListDao.getVaityCodeAliasName(production_lot_no, inspectionline.getVariety_code());
                    if (plantingLineLotListTable != null) {
                        varity_alias_name = plantingLineLotListTable.getAlias_Name();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

        }
    }*/

    //todo for check pld status.............................................................
    SchedulerInspectionLineTable schedulerInspectionLineTable;
    private int  checkPldMarkedArea() {
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionLineDao scheduleInspectionLineDao = pristineDatabase.scheduleInspectionLineDao();
                schedulerInspectionLineTable = scheduleInspectionLineDao.getAllDatabyLotNo(scheduler_no,production_lot_no);
                pldMarked = Integer.parseInt(schedulerInspectionLineTable.getPld_marked());
                return pldMarked;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();

        }
        return 0;
    }

}