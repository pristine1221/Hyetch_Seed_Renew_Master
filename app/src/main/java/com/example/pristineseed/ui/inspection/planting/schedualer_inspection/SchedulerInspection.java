
package com.example.pristineseed.ui.inspection.planting.schedualer_inspection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.scheduler_inspection.SchedulerAdapter;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerInspection extends Fragment {
    SessionManagement sessionManagement;
    ExpandableListView expandableListView;
    List<SchedulerModel> scheduleInspectionModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedualer_inspection_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            getAllsecheduleInspectionData();
        } else {
            getDataFromLocal();
        }

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
            parent.setItemChecked(index, true);
            selectedchildPosition = index;
            expandableListView.setSelector(R.color.chip_ripple);
//            CreateInspectionFragment createInspectionFragment = new CreateInspectionFragment();
//            createInspectionFragment.production_lot_no = scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).production_lot_no;
//            createInspectionFragment.scheduler_no = scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).schedule_no;
//            createInspectionFragment.arrival_plan_no = scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).arrival_plan_no;
//            Intent brodcastIntent = new Intent("passValueBySheduler");
//            brodcastIntent.putExtra("arrival_plan_no",createInspectionFragment.arrival_plan_no);
//            brodcastIntent.putExtra("scheduler_no",createInspectionFragment.scheduler_no);
//            brodcastIntent.putExtra("production_lot_no",createInspectionFragment.production_lot_no);
//            getActivity().sendBroadcast(brodcastIntent);
//            MenuMainPageFragment.viewPager.setCurrentItem(2);

            try {
                SchedulerBundleModel passmodel=new SchedulerBundleModel();
                passmodel.arrival_plan_no=scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).arrival_plan_no;
                passmodel.production_lot_no = scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).production_lot_no;
                passmodel.scheduler_no = scheduleInspectionModelList.get(groupPosition).scheduler_lines.get(childPosition).schedule_no;
                StaticMethods.LoadMenuChidData(sessionManagement, getActivity(), "FSP&HSP", "Create Inspection", "pass_data", new Gson().toJson(passmodel));
            } catch (Exception e) {
                StaticMethods.showMessage(getActivity(), e.getMessage(), MDToast.TYPE_ERROR);
            }

          /*  if(sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("QC Inspection")){
                MenuMainPageFragment.viewPager.setCurrentItem(2);
              }
            else if(sessionManagement.getuser_app_inspection_type().equalsIgnoreCase("Normal Inspection")) {
                MenuMainPageFragment.viewPager.setCurrentItem(2);
            }*/

            return false;
        });
    }

    public int selectedchildPosition = 0;

    private void initView(View view) {
        sessionManagement = new SessionManagement(getActivity());
        expandableListView = view.findViewById(R.id.scheduler_inspection);
        expandiblelistbind();
    }


    public void expandiblelistbind() {
        SchedulerAdapter expandableListAdapter = new SchedulerAdapter(this.getActivity(), scheduleInspectionModelList);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            for (int g = 0; g < expandableListAdapter.getGroupCount(); g++) {
                if (g != groupPosition) {
                    expandableListView.collapseGroup(g);
                }
            }
        });
    }

    private void getAllsecheduleInspectionData() {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<SchedulerModel>> call = mAPIService.getSchedulerInspection(sessionManagement.getUserEmail(), sessionManagement.getuser_app_inspection_type());
            LoadingDialog progressDialogLoading = new LoadingDialog();
            progressDialogLoading.showLoadingDialog(getActivity());
            call.enqueue(new Callback<List<SchedulerModel>>() {
                @Override
                public void onResponse(Call<List<SchedulerModel>> call, Response<List<SchedulerModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialogLoading.hideDialog();
                            List<SchedulerModel> tempscheduleInspectionModelList = response.body();
                            if (tempscheduleInspectionModelList != null && tempscheduleInspectionModelList.size() > 0 && tempscheduleInspectionModelList.get(0).condition) {
                                scheduleInspectionModelList = tempscheduleInspectionModelList;
                                expandiblelistbind();
                                InsertInspectionShedulerHeaderLine(scheduleInspectionModelList);
                            } else {
                                progressDialogLoading.hideDialog();
                            }
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialogLoading.hideDialog();
                        Log.e("exception database", e.getMessage() + "cause");
                        //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_inspection_fragment_", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<SchedulerModel>> call, Throwable t) {
                    progressDialogLoading.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_inspection_fragment_", getActivity());
                }
            });
        }

    private void InsertInspectionShedulerHeaderLine(List<SchedulerModel> scheduleInspectionModelList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionHeaderDao scheduleInspectionHeaderDao = db.scheduleInspectionHeaderDao();
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            for (int i = 0; i < scheduleInspectionModelList.size(); i++) {
                Scheduler_Header_Table scheduler_header_table = Scheduler_Header_Table.getThisTypeOfData(scheduleInspectionModelList.get(i));
                if (scheduleInspectionHeaderDao.isDataExist(scheduler_header_table.getSchedule_no())>0) {

                    scheduleInspectionHeaderDao.updateSchedulerHeader(scheduler_header_table.getSeason(), scheduler_header_table.getSeason_name(),
                            scheduler_header_table.getProduction_centre(), scheduler_header_table.getProduction_centre_name(), scheduler_header_table.getDate(),
                            scheduler_header_table.getStatus(), scheduler_header_table.getSchedule_no(),scheduler_header_table.getUser_type());
                }
                else {
                    scheduleInspectionHeaderDao.insert(scheduler_header_table);
                }
                for (int j = 0; j < scheduleInspectionModelList.get(i).scheduler_lines.size(); j++) {
                    int count = scheduleInspectionLineDao.isDataExist(scheduleInspectionModelList.get(i).scheduler_lines.get(j).schedule_no, scheduleInspectionModelList.get(i).scheduler_lines.get(j).production_lot_no);
                    if (count <= 0) {
                        scheduleInspectionLineDao.insert(SchedulerInspectionLineTable.getLineDataFormate(scheduleInspectionModelList.get(i).scheduler_lines.get(j)));
                    }
                    else {
                        SchedulerInspectionLineTable schedulerInspectionLineTable = SchedulerInspectionLineTable.getLineDataFormate(scheduleInspectionModelList.get(i).scheduler_lines.get(j));
                        if (schedulerInspectionLineTable.getIns1_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_1() > 0 && schedulerInspectionLineTable.getIns1_nav_sync() > 0) {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspGermination(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), 1, "");

                            if (schedulerInspectionLineTable.getIns2_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_2() > 0 && schedulerInspectionLineTable.getIns2_nav_sync() > 0) {
                                scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderSeedling(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                if (schedulerInspectionLineTable.getIns3_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_3() > 0 && schedulerInspectionLineTable.getIns3_nav_sync() > 0) {
                                    scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspVegitative(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                    if (schedulerInspectionLineTable.getIns4_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_4() > 0 && schedulerInspectionLineTable.getIns4_nav_sync() > 0) {
                                        scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                        if (schedulerInspectionLineTable.getIns5_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_5() > 0 && schedulerInspectionLineTable.getIns5_nav_sync() > 0) {
                                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking2(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                            if (schedulerInspectionLineTable.getIns6_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_6() > 0 && schedulerInspectionLineTable.getIns6_nav_sync() > 0) {
                                                scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderFlowering(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                                if (schedulerInspectionLineTable.getIns7_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_7() > 0 && schedulerInspectionLineTable.getIns7_nav_sync() > 0) {
                                                    scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspPostFlowering(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                                    if (schedulerInspectionLineTable.getIns8_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_8() > 0 && schedulerInspectionLineTable.getIns8_nav_sync() > 0) {
                                                        scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspMaturity(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), "", 1);

                                                        if (schedulerInspectionLineTable.getIns9_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_9() > 0 && schedulerInspectionLineTable.getIns9_nav_sync() > 0) {
                                                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspHarvesting(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), 1, "");

                                                            if (schedulerInspectionLineTable.getInsqc_sync_with_server() > 0 && schedulerInspectionLineTable.getInspection_qc() > 0 && schedulerInspectionLineTable.getIns_qc_nav_sync() > 0) {
                                                                scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspQc(schedulerInspectionLineTable.getSchedule_no(), schedulerInspectionLineTable.getProduction_lot_no(), 1, "");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                          }
                                      }
                                   }
                                 }
                                }

                                scheduleInspectionLineDao.updateSchedulerLine(schedulerInspectionLineTable.getFsio_no(), schedulerInspectionLineTable.getOrganizer_code(), schedulerInspectionLineTable.getOrganizer_name(),
                                schedulerInspectionLineTable.getGrower_owner(), schedulerInspectionLineTable.getGrower_land_owner_name(),
                                schedulerInspectionLineTable.getGrower_village(), schedulerInspectionLineTable.getGrower_taluka_mandal(), schedulerInspectionLineTable.getGrower_district(),
                                schedulerInspectionLineTable.getGrower_city(), schedulerInspectionLineTable.getSupervisor_name(), schedulerInspectionLineTable.getCrop_code(),
                                schedulerInspectionLineTable.getVariety_code(), schedulerInspectionLineTable.getItem_product_group_code(), schedulerInspectionLineTable.getItem_class_of_seeds(),
                                schedulerInspectionLineTable.getItem_crop_type(), schedulerInspectionLineTable.getItem_name(), schedulerInspectionLineTable.getRevised_yield_raw(),
                                schedulerInspectionLineTable.getLand_lease(), schedulerInspectionLineTable.getUnit_of_measure_code(), schedulerInspectionLineTable.getSowing_date_male(),
                                schedulerInspectionLineTable.getSowing_date_female(), schedulerInspectionLineTable.getSowing_date_other(),schedulerInspectionLineTable.getPld_marked(), schedulerInspectionLineTable.getProduction_lot_no(),
                                schedulerInspectionLineTable.getSchedule_no());
                                //update scheduler column
                    }

                }
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync_Schedule_Inspection_Insert", getActivity());
        } finally {
            db.close();
            db.destroyInstance();
        }
    }

    private void getDataFromLocal() {
        scheduleInspectionModelList.clear();
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            ScheduleInspectionHeaderDao scheduleInspectionHeaderDao = db.scheduleInspectionHeaderDao();
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            List<Scheduler_Header_Table> scheduelInspectionHeaderTablesList = scheduleInspectionHeaderDao.user_typegetAllData(sessionManagement.getUserEmail().toUpperCase(),sessionManagement.getuser_app_inspection_type());
            for (int k = 0; k < scheduelInspectionHeaderTablesList.size(); k++) {
                SchedulerModel scheduleInspectionModel = Scheduler_Header_Table.getServerFormat(scheduelInspectionHeaderTablesList.get(k));
                //todo bind scheduler Line
                List<SchedulerInspectionLineTable> scheduleInspectionLineTablesList = scheduleInspectionLineDao.getAllData(scheduleInspectionModel.schedule_no);
                for (int i = 0; i < scheduleInspectionLineTablesList.size(); i++) {
                    scheduleInspectionModel.scheduler_lines.add(SchedulerInspectionLineTable.getServerTypeFormate(scheduleInspectionModel, scheduleInspectionLineTablesList.get(i)));
                }
                scheduleInspectionModelList.add(scheduleInspectionModel);
            }

        } catch (Exception e) {
            Log.e("msg", e.getMessage());
            e.printStackTrace();

        } finally {
            db.close();
            db.destroyInstance();
            expandiblelistbind();
        }
    }

}
