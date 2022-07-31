package com.example.pristineseed.DataSyncingBackgraundProcess;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.GeoghraphicalTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.GeographicDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RegionMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RegionMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RoleMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RoleMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ShipToAddressDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ShipToAddressDataTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UOMDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UomTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_fsio_bsio_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
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
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Dao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Table;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedFarmerMasterDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Seed_Farmer_master_Table;
import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.City_master_Dao;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;
import com.example.pristineseed.model.GeoSetupModel.AreaMasterModel;
import com.example.pristineseed.model.GeoSetupModel.CountModel;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.GeoSetupModel.DistrictMaster;
import com.example.pristineseed.model.GeoSetupModel.GeoGraphicData;
import com.example.pristineseed.model.GeoSetupModel.GeographicModel;
import com.example.pristineseed.model.GeoSetupModel.RegionMaster;
import com.example.pristineseed.model.GeoSetupModel.StateMaster;
import com.example.pristineseed.model.GeoSetupModel.TalukaMaster;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.GeoSetupModel.ZoneMaster;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.exceptions.ExceptionModel;
import com.example.pristineseed.model.home.CollectionList;
import com.example.pristineseed.model.item.BankMaserModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.FsioBsioSaleOrderNoModel;
import com.example.pristineseed.model.item.HybridItemMasterModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.model.item.PlantingProdcutionLotModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.item.UnitOfMeasureModel;
import com.example.pristineseed.model.item.UnitPriceModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.FloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Germination_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.HarvestingInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.MaturityInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Nicking2InspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Nicking_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.PostFloweringInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.Qc_Inspection_Model;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;
import com.example.pristineseed.model.scheduler_inspection.SeedLing_InspectionLineModel;
import com.example.pristineseed.model.scheduler_inspection.Vegitative_InspectionModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.sql_lite_process.dao.ExceptionDao;
import com.example.pristineseed.sql_lite_process.model.ExceptionTableModel;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgruandSyncing_process extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private SessionManagement sessionManagement;
    private static int Schedulertimercounter = 0;
    private String lastSync;

    public BackgruandSyncing_process(Activity activity) {
        this.activity = activity;
        sessionManagement = new SessionManagement(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        //NotificationCall("syncing");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //todo get Inspection Scheduler who exist on server
        try {
           // NotificationCall("syncing");
             getAllsecheduleInspectionData();
            //todo server sync data....
            syncDataGerminationInspection();
            syncDataSeedlingInspection();
            syncDataVegitativeInspection();
            synDataNickingInspection();
            synDataNicking2Inspection();
            synDataFloweringInspection();
            synDataPostFloweringInspection();
            synDataPostMaturityInspection();
            synDataPostHarvestingInspection();
            synDataPostQcInspection();
            exceptionSendToServer();

            if (Schedulertimercounter >= 60000 || Schedulertimercounter == 0) {
                Schedulertimercounter = 0;

                getAllsecheduleInspectionData();
                getHybridItemMasterData();
                getGeoServerData();
                getPlantingLineListLot();

                /*getCityMaster();
                getPlantingLotList();

                getCropMasterData();
                getShipmentToAddressData();

                getFsioBsioSaleOrderNo();
                getUserLocationMasterData();
                getPlantingFsio_BsioORGData();

                getSelectSeason();
                getOrganizerCode();
               getFarmermaster();

                getRoleMasterData("Dealer");
                getRoleMasterData("Customer");
                getRoleMasterData("Farmer");
                getRoleMasterData("Distributor");
                getRoleMasterData("Prod Distributor");
                getUnitOfMeasureData();
                getBookingUnitOfPriceData();
                getBankMasterData();*/

            }
            Thread.sleep(5000);
            Schedulertimercounter += 5000;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // Log.e("time", String.valueOf(Schedulertimercounter));
        }
        return null;
    }

    private void getAllsecheduleInspectionData() {
        try {
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<SchedulerModel>> call = mAPIService.getSchedulerInspection(sessionManagement.getUserEmail(), sessionManagement.getuser_app_inspection_type());
            List<SchedulerModel> scheduleInspectionModelList = call.execute().body();
            if (scheduleInspectionModelList.size() > 0 && scheduleInspectionModelList.get(0).condition) {
                InsertInspectionShedulerHeaderLine(scheduleInspectionModelList);
            }
        } catch (Exception e) {
        }
    }

    private void InsertInspectionShedulerHeaderLine(List<SchedulerModel> scheduleInspectionModelList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            ScheduleInspectionHeaderDao scheduleInspectionHeaderDao = db.scheduleInspectionHeaderDao();
            ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
            for (int i = 0; i < scheduleInspectionModelList.size(); i++) {
                scheduleInspectionHeaderDao.insert(Scheduler_Header_Table.getThisTypeOfData(scheduleInspectionModelList.get(i)));
                for (int j = 0; j < scheduleInspectionModelList.get(i).scheduler_lines.size(); j++) {
                    SchedulerModel.SchedulerLines inserted_pmodel = scheduleInspectionModelList.get(i).scheduler_lines.get(j);
                    int isDataExist = scheduleInspectionLineDao.isDataExistOfflineStatusOnSchedulerLine(inserted_pmodel.schedule_no, inserted_pmodel.production_lot_no);
                    if (isDataExist > 0) {
                        SchedulerInspectionLineTable modeldata = scheduleInspectionLineDao.getAllLocalStatus(inserted_pmodel.schedule_no, inserted_pmodel.production_lot_no, 1,1,1,1,1,1,1,1,1);
                        if (modeldata != null && modeldata.getProduction_lot_no() != null) {
                            inserted_pmodel.ins1_sync_with_server = modeldata.getIns1_sync_with_server();
                            inserted_pmodel.ins2_sync_with_server=modeldata.getIns2_sync_with_server();
                            inserted_pmodel.ins3_sync_with_server=modeldata.getIns3_sync_with_server();
                            inserted_pmodel.ins4_sync_with_server=modeldata.getIns4_sync_with_server();
                            inserted_pmodel.ins5_sync_with_server=modeldata.getIns5_sync_with_server();
                            inserted_pmodel.ins6_sync_with_server=modeldata.getIns6_sync_with_server();
                            inserted_pmodel.ins7_sync_with_server=modeldata.getIns7_sync_with_server();
                            inserted_pmodel.ins8_sync_with_server=modeldata.getIns8_sync_with_server();
                            inserted_pmodel.ins9_sync_with_server=modeldata.getIns9_sync_with_server();
                        }
                    }
                    scheduleInspectionLineDao.insert(SchedulerInspectionLineTable.getLineDataFormate(inserted_pmodel));
                }
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Inspection Insert", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("Insert Sheduler", "Success");
        }
    }

    private void syncDataGerminationInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                GerminationInspectionDao germinationInspectionDao = db.germinationInspectionDao();
                List<GerminationInspection1_Table> insert_germination_pending_line = germinationInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Germination_InspectionLineModel germination_inspectionLineModel = new Germination_InspectionLineModel();
                for (int i = 0; i < insert_germination_pending_line.size(); i++) {
                    germination_inspectionLineModel.scheduler_no = insert_germination_pending_line.get(i).getScheduler_no();
                    germination_inspectionLineModel.production_lot_no = insert_germination_pending_line.get(i).getProduction_lot_no();
                    germination_inspectionLineModel.arrival_plan_no = insert_germination_pending_line.get(i).getArrival_plan_no();
                    germination_inspectionLineModel.crop_condition = insert_germination_pending_line.get(i).getCrop_condition();
                    germination_inspectionLineModel.crop_stage = insert_germination_pending_line.get(i).getCrop_stage();
                    germination_inspectionLineModel.germination_per = insert_germination_pending_line.get(i).getGermination_per();
                    germination_inspectionLineModel.recommended_dose_of_fertilizer = insert_germination_pending_line.get(i).getRecommended_dose_of_fertilizer();
                    germination_inspectionLineModel.recommended_dose_of_fertilizer_in_bags = insert_germination_pending_line.get(i).getRecommended_dose_of_fertilizer_in_bags();
                    germination_inspectionLineModel.basal_dose = insert_germination_pending_line.get(i).getBasal_dose();
                    germination_inspectionLineModel.basal_dose_bags = insert_germination_pending_line.get(i).getBasal_dose_bags();
                    germination_inspectionLineModel.remark_for_fertilizer = insert_germination_pending_line.get(i).getRemark_for_fertilizer();
                    germination_inspectionLineModel.created_by = sessionManagement.getUserEmail();
                    germination_inspectionLineModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                    germination_inspectionLineModel.date_of_inspection = DateTimeUtilsCustome.splitDateInMMDDYYYYslsh(insert_germination_pending_line.get(i).getDate_of_inspection());
                    germination_inspectionLineModel.seed_setting = insert_germination_pending_line.get(i).getSeed_setting();
                    germination_inspectionLineModel.seed_setting_prcnt = insert_germination_pending_line.get(i).getSeed_setting_prcnt();
                    String attachment = insert_germination_pending_line.get(i).getAttachment();
                    germination_inspectionLineModel.male_reciept_no = insert_germination_pending_line.get(i).getMale_reciept_no();
                    germination_inspectionLineModel.female_reciept_no = insert_germination_pending_line.get(i).getFemale_reciept_no();
                    germination_inspectionLineModel.other_reciept_no = insert_germination_pending_line.get(i).getOther_reciept_no();
                    germination_inspectionLineModel.recommended_date=insert_germination_pending_line.get(i).getRecommended_date();
                    germination_inspectionLineModel.actual_date=insert_germination_pending_line.get(i).getActual_date();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        germination_inspectionLineModel.attachment = base64_string;
                    } else {
                        germination_inspectionLineModel.attachment = "";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(germination_inspectionLineModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertGermination(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp != null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        insert_germination_pending_line.get(i).setSync_with_api(1);
                        germinationInspectionDao.update(insert_germination_pending_line.get(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("Insert Germination:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteGerminationList = scheduleInspectionLineDao.getUnpostedCompleteLine();
                for (int i = 0; i < pendingCompleteGerminationList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection I", pendingCompleteGerminationList.get(i).getProduction_lot_no(), pendingCompleteGerminationList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst != null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteGermination(1, 1, pendingCompleteGerminationList.get(0).getSchedule_no().toLowerCase(), pendingCompleteGerminationList.get(0).getProduction_lot_no(),
                                    pendingCompleteGerminationList.get(0).getIns1_completed_on(),1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspGermination(pendingCompleteGerminationList.get(0).getSchedule_no(),
                                    pendingCompleteGerminationList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspGermination(pendingCompleteGerminationList.get(0).getSchedule_no(),
                                    pendingCompleteGerminationList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("Germ. Insp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Germination Insert", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("Germ. Insp. Complete", " Success");
        }
    }

    private void syncDataSeedlingInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                Seedling_InspectionDao seedling_inspectionDao = db.seedling_inspectionDao();
                List<SeedlingInspectionTable> insert_seedling_pending_line = seedling_inspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                SeedLing_InspectionLineModel seedLing_inspectionLineModel = new SeedLing_InspectionLineModel();
                for (int i = 0; i < insert_seedling_pending_line.size(); i++) {
                    seedLing_inspectionLineModel.scheduler_no = insert_seedling_pending_line.get(i).getScheduler_no();
                    seedLing_inspectionLineModel.arrival_plan_no = insert_seedling_pending_line.get(i).getArrival_plan_no();
                    seedLing_inspectionLineModel.production_lot_no = insert_seedling_pending_line.get(i).getProduction_lot_no();
                    seedLing_inspectionLineModel.crop_condition = insert_seedling_pending_line.get(i).getCrop_condition();
                    seedLing_inspectionLineModel.crop_stage = insert_seedling_pending_line.get(i).getCrop_stage();
                    if(insert_seedling_pending_line.get(i).getDate_of_inspection()!=null){
                        seedLing_inspectionLineModel.date_of_inspection = insert_seedling_pending_line.get(i).getDate_of_inspection();
                    }else {
                        seedLing_inspectionLineModel.date_of_inspection = "";
                    }
                    seedLing_inspectionLineModel.vigor = insert_seedling_pending_line.get(i).getVigor();
                    seedLing_inspectionLineModel.pest = insert_seedling_pending_line.get(i).getPest();
                    seedLing_inspectionLineModel.diseases = insert_seedling_pending_line.get(i).getDiseases();
                    seedLing_inspectionLineModel.pest_remarks = insert_seedling_pending_line.get(i).getPest_remarks();
                    seedLing_inspectionLineModel.diseases_remarks = insert_seedling_pending_line.get(i).getDiseases_remarks();
                    if(insert_seedling_pending_line.get(i).getRecommended_date()!=null){
                        seedLing_inspectionLineModel.recommended_date = insert_seedling_pending_line.get(i).getRecommended_date();
                    }
                    else {
                        seedLing_inspectionLineModel.recommended_date = "";
                    }

                    if(insert_seedling_pending_line.get(i).getActual_date()!=null){
                        seedLing_inspectionLineModel.actual_date = insert_seedling_pending_line.get(i).getActual_date();
                    }
                    else {
                        seedLing_inspectionLineModel.actual_date="";
                    }

                    seedLing_inspectionLineModel.isolation = insert_seedling_pending_line.get(i).getIsolation();
                    seedLing_inspectionLineModel.isolation_distance = insert_seedling_pending_line.get(i).getIsolation_distance();
                    seedLing_inspectionLineModel.isolation_time = insert_seedling_pending_line.get(i).getIsolation_time();
                    seedLing_inspectionLineModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                    seedLing_inspectionLineModel.seed_setting = insert_seedling_pending_line.get(i).getSeed_setting();
                    seedLing_inspectionLineModel.seed_setting_prcnt = insert_seedling_pending_line.get(i).getSeed_setting_prcnt();
                    seedLing_inspectionLineModel.male_reciept_no = insert_seedling_pending_line.get(i).getMale_reciept_no();
                    seedLing_inspectionLineModel.female_reciept_no = insert_seedling_pending_line.get(i).getFemale_reciept_no();
                    seedLing_inspectionLineModel.other_reciept_no = insert_seedling_pending_line.get(i).getOther_reciept_no();
                    seedLing_inspectionLineModel.grain_remarks = insert_seedling_pending_line.get(i).getGrain_remarks();
                    String attachment = insert_seedling_pending_line.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        seedLing_inspectionLineModel.attachment = base64_string;
                    } else {
                        seedLing_inspectionLineModel.attachment = "";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(seedLing_inspectionLineModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertSeedlingInspection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        insert_seedling_pending_line.get(i).setSync_with_api_ins2(1);
                        seedling_inspectionDao.update(insert_seedling_pending_line.get(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
               // Log.e("Insert Seedling:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteSeedlingList = scheduleInspectionLineDao.getUnpostedCompleteLineSeedling();
                for (int i = 0; i < pendingCompleteSeedlingList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection II", pendingCompleteSeedlingList.get(i).getProduction_lot_no(), pendingCompleteSeedlingList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteSeedling(1, 1, pendingCompleteSeedlingList.get(0).getSchedule_no(), pendingCompleteSeedlingList.get(0).getProduction_lot_no(),
                                    pendingCompleteSeedlingList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderSeedling(pendingCompleteSeedlingList.get(0).getSchedule_no(),
                                    pendingCompleteSeedlingList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderSeedling(pendingCompleteSeedlingList.get(0).getSchedule_no(),
                                    pendingCompleteSeedlingList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("Seed. Insp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Seedling Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("Seed. Insp. Complete", " Success");
        }

    }

    private void syncDataVegitativeInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                VegitativeInspectionDao vegitativeInspectionDao = db.vegitativeInspectionDao();
                List<VegitativeInspectionTable> insert_vegitative_pending_line = vegitativeInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Vegitative_InspectionModel vegitative_inspectionModel = new Vegitative_InspectionModel();
                for (int i = 0; i < insert_vegitative_pending_line.size(); i++) {
                    vegitative_inspectionModel.scheduler_no = insert_vegitative_pending_line.get(i).getScheduler_no();
                    vegitative_inspectionModel.arrival_plan_no = insert_vegitative_pending_line.get(i).getArrival_plan_no();
                    vegitative_inspectionModel.production_lot_no = insert_vegitative_pending_line.get(i).getProduction_lot_no();

                    if(insert_vegitative_pending_line.get(i).getDate_of_inspection()!=null){
                        vegitative_inspectionModel.date_of_inspection = insert_vegitative_pending_line.get(i).getDate_of_inspection();
                    }
                    else {
                        vegitative_inspectionModel.date_of_inspection="";
                    }
                    vegitative_inspectionModel.vigor = insert_vegitative_pending_line.get(i).getVigor();
                    vegitative_inspectionModel.pest = insert_vegitative_pending_line.get(i).getPest();
                    vegitative_inspectionModel.diseases = insert_vegitative_pending_line.get(i).getDiseases();
                    vegitative_inspectionModel.pest_remarks = insert_vegitative_pending_line.get(i).getPest_remarks();
                    vegitative_inspectionModel.diseases_remarks = insert_vegitative_pending_line.get(i).getDiseases_remarks();
                    if(insert_vegitative_pending_line.get(i).getRecommended_date()!=null){
                        vegitative_inspectionModel.recommended_date =insert_vegitative_pending_line.get(i).getRecommended_date();
                     }
                    else {
                        vegitative_inspectionModel.recommended_date="";
                    }
                    if(insert_vegitative_pending_line.get(i).getActual_date()!=null){
                        vegitative_inspectionModel.actual_date = insert_vegitative_pending_line.get(i).getActual_date();
                    }
                    else {
                        vegitative_inspectionModel.actual_date="";
                    }
                    vegitative_inspectionModel.isolation = insert_vegitative_pending_line.get(i).getIsolation();
                    vegitative_inspectionModel.isolation_distance = insert_vegitative_pending_line.get(i).getIsolation_distance();
                    vegitative_inspectionModel.isolation_time = insert_vegitative_pending_line.get(i).getIsolation_time();
                    vegitative_inspectionModel.crop_condition = insert_vegitative_pending_line.get(i).getCrop_condition();
                    vegitative_inspectionModel.crop_stage = insert_vegitative_pending_line.get(i).getCrop_stage();
                    vegitative_inspectionModel.other_types = insert_vegitative_pending_line.get(i).getOther_types();
                    vegitative_inspectionModel.first_top_dressing = insert_vegitative_pending_line.get(i).getFirst_top_dressing();
                    vegitative_inspectionModel.first_top_dressing_bags = insert_vegitative_pending_line.get(i).getFirst_top_dressing_bags();
                    vegitative_inspectionModel.grain_remarks = insert_vegitative_pending_line.get(i).getGrain_remarks();
                    vegitative_inspectionModel.male_reciept_no = "0";
                    vegitative_inspectionModel.female_reciept_no = "0";
                    vegitative_inspectionModel.other_reciept_no ="0";
                    vegitative_inspectionModel.seed_setting = insert_vegitative_pending_line.get(i).getSeed_setting();
                    vegitative_inspectionModel.seed_setting_prcnt="0.0";

                    String attachment = insert_vegitative_pending_line.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        vegitative_inspectionModel.attachment = base64_string;
                    }
                    else {
                        vegitative_inspectionModel.attachment="";
                    }

                    JsonObject json = new JsonParser().parse(new Gson().toJson(vegitative_inspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertVegitativeInspection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        insert_vegitative_pending_line.get(i).setSyncWith_Api(1);
                        vegitativeInspectionDao.update(insert_vegitative_pending_line.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteVegitativeList = scheduleInspectionLineDao.getUnpostedCompleteLineVegitative();
                for (int i = 0; i < pendingCompleteVegitativeList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection III", pendingCompleteVegitativeList.get(i).getProduction_lot_no(), pendingCompleteVegitativeList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteVegitative(1, 1, pendingCompleteVegitativeList.get(0).getSchedule_no(), pendingCompleteVegitativeList.get(0).getProduction_lot_no(),
                                    pendingCompleteVegitativeList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspVegitative(pendingCompleteVegitativeList.get(0).getSchedule_no(),
                                    pendingCompleteVegitativeList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspVegitative(pendingCompleteVegitativeList.get(0).getSchedule_no(),
                                    pendingCompleteVegitativeList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Veg. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("Veg. Insp. Complete", " Success");
        }

    }

    private void synDataNickingInspection() {

        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                NickingInspInsertDao nickingInspInsertDao = db.nickingInspInsertDao();
                List<NickingInspectionTable> insert_nikcing_pending_lineList = nickingInspInsertDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Nicking_InspectionLineModel nicking_inspectionLineModel = new Nicking_InspectionLineModel();
                for (int i = 0; i < insert_nikcing_pending_lineList.size(); i++) {
                    nicking_inspectionLineModel.scheduler_no = insert_nikcing_pending_lineList.get(i).getScheduler_no();
                    nicking_inspectionLineModel.arrival_plan_no = insert_nikcing_pending_lineList.get(i).getArrival_plan_no();
                    nicking_inspectionLineModel.production_lot_no = insert_nikcing_pending_lineList.get(i).getProduction_lot_no();
                    if(insert_nikcing_pending_lineList.get(i).getDate_of_inspection()!=null){
                        nicking_inspectionLineModel.date_of_inspection =insert_nikcing_pending_lineList.get(i).getDate_of_inspection();
                    }else {
                        nicking_inspectionLineModel.date_of_inspection ="";
                    }
                    nicking_inspectionLineModel.crop_condition = insert_nikcing_pending_lineList.get(i).getCrop_condition();
                    nicking_inspectionLineModel.crop_stage = insert_nikcing_pending_lineList.get(i).getCrop_stage();
                    nicking_inspectionLineModel.status_of_female = insert_nikcing_pending_lineList.get(i).getStatus_of_female();
                    nicking_inspectionLineModel.status_of_male = insert_nikcing_pending_lineList.get(i).getStatus_of_male();
                    nicking_inspectionLineModel.next_plan_of_action = insert_nikcing_pending_lineList.get(i).getNext_plan_of_action();
                    nicking_inspectionLineModel.remarks = insert_nikcing_pending_lineList.get(i).getRemarks();
                    nicking_inspectionLineModel.seed_setting = insert_nikcing_pending_lineList.get(i).getSeed_setting();
                    nicking_inspectionLineModel.seed_setting_prcnt = insert_nikcing_pending_lineList.get(i).getSeed_setting_prcnt();
                    nicking_inspectionLineModel.male_reciept_no ="0";
                    nicking_inspectionLineModel.female_reciept_no = "0";
                    nicking_inspectionLineModel.other_reciept_no = "0";
                    nicking_inspectionLineModel.actual_date=insert_nikcing_pending_lineList.get(i).getActual_date();
                    String attachment = insert_nikcing_pending_lineList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        nicking_inspectionLineModel.attachment = base64_string;
                    }
                    else {
                        nicking_inspectionLineModel.attachment="";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(nicking_inspectionLineModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertNickingInspection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        insert_nikcing_pending_lineList.get(i).setSync_with_api_insp4(1);
                        nickingInspInsertDao.update(insert_nikcing_pending_lineList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteNickingList = scheduleInspectionLineDao.getUnpostedCompleteLineNicking();
                for (int i = 0; i < pendingCompleteNickingList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection IV", pendingCompleteNickingList.get(i).getProduction_lot_no(), pendingCompleteNickingList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteNicking(1, 1, pendingCompleteNickingList.get(0).getSchedule_no(), pendingCompleteNickingList.get(0).getProduction_lot_no(),
                                    pendingCompleteNickingList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking(pendingCompleteNickingList.get(0).getSchedule_no(),
                                    pendingCompleteNickingList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking(pendingCompleteNickingList.get(0).getSchedule_no(),
                                    pendingCompleteNickingList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Nick. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("Nick. Insp. Complete", " Success");
        }

    }

    private void synDataNicking2Inspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                Nickin2InspectionDao nickin2InspectionDao = db.nickin2InspectionDao();
                List<Nicking2InspectionTable> insert_nikcing2_pending_lineList = nickin2InspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Nicking2InspectionModel nicking2InspectionModel = new Nicking2InspectionModel();
                for (int i = 0; i < insert_nikcing2_pending_lineList.size(); i++) {
                    nicking2InspectionModel.scheduler_no = insert_nikcing2_pending_lineList.get(i).getScheduler_no();
                    nicking2InspectionModel.arrival_plan_no = insert_nikcing2_pending_lineList.get(i).getArrival_plan_no();
                    nicking2InspectionModel.production_lot_no = insert_nikcing2_pending_lineList.get(i).getProduction_lot_no();
                    if(insert_nikcing2_pending_lineList.get(i).getDate_of_inspection()!=null){
                        nicking2InspectionModel.date_of_inspection =insert_nikcing2_pending_lineList.get(i).getDate_of_inspection();
                    }
                    else {
                        nicking2InspectionModel.date_of_inspection="";
                    }
                    nicking2InspectionModel.whether_recommendation_done = insert_nikcing2_pending_lineList.get(i).getWhether_recommendation_done();
                    nicking2InspectionModel.date_of_action = insert_nikcing2_pending_lineList.get(i).getDate_of_action();
                    nicking2InspectionModel.created_on = insert_nikcing2_pending_lineList.get(i).getCreated_on();
                    nicking2InspectionModel.seed_setting = insert_nikcing2_pending_lineList.get(i).getSeed_setting();
                    nicking2InspectionModel.seed_setting_prcnt = insert_nikcing2_pending_lineList.get(i).getSeed_setting_prcnt();
                    nicking2InspectionModel.remarks = insert_nikcing2_pending_lineList.get(i).getRemarks();

                    nicking2InspectionModel.male_reciept_no = "0";
                    nicking2InspectionModel.female_reciept_no ="0";
                    nicking2InspectionModel.other_reciept_no = "0";
                    nicking2InspectionModel.actual_date=insert_nikcing2_pending_lineList.get(i).getActual_date();
                    String attachment = insert_nikcing2_pending_lineList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        nicking2InspectionModel.attachment = base64_string;
                    }
                    else {
                        nicking2InspectionModel.attachment="";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(nicking2InspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertNicking2Inspection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        insert_nikcing2_pending_lineList.get(i).setSyncWithApi5(1);
                        nickin2InspectionDao.update(insert_nikcing2_pending_lineList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteNicking2List = scheduleInspectionLineDao.getUnpostedCompleteLineNicking2();
                for (int i = 0; i < pendingCompleteNicking2List.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection V", pendingCompleteNicking2List.get(i).getProduction_lot_no(), pendingCompleteNicking2List.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteNicking2(1, 1, pendingCompleteNicking2List.get(0).getSchedule_no(), pendingCompleteNicking2List.get(0).getProduction_lot_no(),
                                    pendingCompleteNicking2List.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking2(pendingCompleteNicking2List.get(0).getSchedule_no(),
                                    pendingCompleteNicking2List.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspNicking2(pendingCompleteNicking2List.get(0).getSchedule_no(),
                                    pendingCompleteNicking2List.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("Nick2. Insp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule Nick2. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
           // Log.e("Nick2. Insp. Complete", " Success");
        }

    }

    private void synDataFloweringInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                FloweringInspectionDao floweringInspectionDao = db.floweringInspectionDao();
                List<FloweringInspectionTable> floweringInspectionTableList = floweringInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                FloweringInspectionModel floweringInspectionModel = new FloweringInspectionModel();
                for (int i = 0; i < floweringInspectionTableList.size(); i++) {

                    floweringInspectionModel.scheduler_no = floweringInspectionTableList.get(i).getScheduler_no();
                    floweringInspectionModel.arrival_plan_no = floweringInspectionTableList.get(i).getArrival_plan_no();
                    floweringInspectionModel.production_lot_no = floweringInspectionTableList.get(i).getProduction_lot_no();
                    if(floweringInspectionTableList.get(i).getDate_of_inspection()!=null){
                        floweringInspectionModel.date_of_inspection = floweringInspectionTableList.get(i).getDate_of_inspection();
                    }
                    else {
                        floweringInspectionModel.date_of_inspection="";
                    }
                    floweringInspectionModel.crop_condition = floweringInspectionTableList.get(i).getCrop_condition();
                    floweringInspectionModel.crop_stage = floweringInspectionTableList.get(i).getCrop_stage();
                    floweringInspectionModel.pollen_shedders = floweringInspectionTableList.get(i).getPollen_shedders();
                    floweringInspectionModel.other_types = floweringInspectionTableList.get(i).getOther_types();
                    floweringInspectionModel.pollen_shedding_plants_per = floweringInspectionTableList.get(i).getPollen_shedding_plants_per();
                    floweringInspectionModel.pest = floweringInspectionTableList.get(i).getPest();
                    floweringInspectionModel.diseases = floweringInspectionTableList.get(i).getDiseases();
                    floweringInspectionModel.pest_remarks = floweringInspectionTableList.get(i).getPest_remarks();
                    floweringInspectionModel.diseases_remarks = floweringInspectionTableList.get(i).getDiseases_remarks();
                    floweringInspectionModel.recommended_date = floweringInspectionTableList.get(i).getRecommended_date();
                    floweringInspectionModel.actual_date = floweringInspectionTableList.get(i).getActual_date();
                    floweringInspectionModel.isolation = floweringInspectionTableList.get(i).getIsolation();
                    floweringInspectionModel.isolation_distance = floweringInspectionTableList.get(i).getIsolation_distance();
                    floweringInspectionModel.isolation_time = floweringInspectionTableList.get(i).getIsolation_time();
                    floweringInspectionModel.second_top_dressing = floweringInspectionTableList.get(i).getSecond_top_dressing();
                    floweringInspectionModel.second_top_dressing_bags = floweringInspectionTableList.get(i).getSecond_top_dressing_bags();
                    floweringInspectionModel.seed_setting = floweringInspectionTableList.get(i).getSeed_setting();
                    floweringInspectionModel.seed_setting_prcnt = floweringInspectionTableList.get(i).getSeed_setting_prcnt();
                    floweringInspectionModel.grain_remarks = floweringInspectionTableList.get(i).getGrain_remarks();

                    floweringInspectionModel.male_reciept_no = "0";
                    floweringInspectionModel.female_reciept_no = "0";
                    floweringInspectionModel.other_reciept_no = "0";

                    String attachment = floweringInspectionTableList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        floweringInspectionModel.attachment = base64_string;
                    }else {
                        floweringInspectionModel.attachment="";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(floweringInspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertFloweringInsection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        floweringInspectionTableList.get(i).setSyncwith_api6(1);
                        floweringInspectionDao.update(floweringInspectionTableList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteFloweringList = scheduleInspectionLineDao.getUnpostedCompleteLineFlowering();
                for (int i = 0; i < pendingCompleteFloweringList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VI", pendingCompleteFloweringList.get(i).getProduction_lot_no(), pendingCompleteFloweringList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteFlowering(1, 1, pendingCompleteFloweringList.get(0).getSchedule_no(), pendingCompleteFloweringList.get(0).getProduction_lot_no(),
                                    pendingCompleteFloweringList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderFlowering(pendingCompleteFloweringList.get(0).getSchedule_no(),
                                    pendingCompleteFloweringList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderFlowering(pendingCompleteFloweringList.get(0).getSchedule_no(),
                                    pendingCompleteFloweringList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("FlwringInsp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule FlwringInsp. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
           // Log.e("FlwringInsp. Complete", " Success");
        }

    }

    private void synDataPostFloweringInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                PostFloweringDao postfloweringInspectionDao = db.postFloweringDao();
                List<PostfloweringInspectionTable> postfloweringInspectionTableList = postfloweringInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                PostFloweringInspectionModel postfloweringInspectionModel = new PostFloweringInspectionModel();
                for (int i = 0; i < postfloweringInspectionTableList.size(); i++) {

                    postfloweringInspectionModel.scheduler_no = postfloweringInspectionTableList.get(i).getScheduler_no();
                    postfloweringInspectionModel.arrival_plan_no = postfloweringInspectionTableList.get(i).getArrival_plan_no();
                    postfloweringInspectionModel.production_lot_no = postfloweringInspectionTableList.get(i).getProduction_lot_no();
                    if((postfloweringInspectionTableList.get(i).getDate_of_inspection()!=null)){
                        postfloweringInspectionModel.date_of_inspection = postfloweringInspectionTableList.get(i).getDate_of_inspection();
                    }
                    else {
                        postfloweringInspectionModel.date_of_inspection="";
                    }
                    postfloweringInspectionModel.crop_condition = postfloweringInspectionTableList.get(i).getCrop_condition();
                    postfloweringInspectionModel.crop_stage = postfloweringInspectionTableList.get(i).getCrop_stage();
                    postfloweringInspectionModel.other_types = postfloweringInspectionTableList.get(i).getOther_types();
                    postfloweringInspectionModel.pest = postfloweringInspectionTableList.get(i).getPest();
                    postfloweringInspectionModel.diseases = postfloweringInspectionTableList.get(i).getDiseases();
                    postfloweringInspectionModel.pest_remarks = postfloweringInspectionTableList.get(i).getPest_remarks();
                    postfloweringInspectionModel.diseases_remarks = postfloweringInspectionTableList.get(i).getDiseases_remarks();
                    if(postfloweringInspectionTableList.get(i).getRecommended_date()!=null){
                        postfloweringInspectionModel.recommended_date = postfloweringInspectionTableList.get(i).getRecommended_date();
                    }
                    else {
                        postfloweringInspectionModel.recommended_date="";
                    }
                    if(postfloweringInspectionTableList.get(i).getActual_date()!=null){
                        postfloweringInspectionModel.actual_date = postfloweringInspectionTableList.get(i).getActual_date();
                    }
                    else {
                        postfloweringInspectionModel.actual_date="";
                    }
                    postfloweringInspectionModel.seed_setting = postfloweringInspectionTableList.get(i).getSeed_setting();
                    postfloweringInspectionModel.seed_setting_prcnt = postfloweringInspectionTableList.get(i).getSeed_setting_prcnt();
                    postfloweringInspectionModel.created_on = DateTimeUtilsCustome.getCurrentTime();
                    postfloweringInspectionModel.male_reciept_no = postfloweringInspectionTableList.get(i).getMale_reciept_no();
                    postfloweringInspectionModel.female_reciept_no = postfloweringInspectionTableList.get(i).getFemale_reciept_no();
                    postfloweringInspectionModel.other_reciept_no = postfloweringInspectionTableList.get(i).getOther_reciept_no();

                    String attachment = postfloweringInspectionTableList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        postfloweringInspectionModel.attachment = base64_string;
                    }
                    else {
                        postfloweringInspectionModel.attachment="";
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(postfloweringInspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertPostFloweringInsection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        postfloweringInspectionTableList.get(i).setSynWithApi7(1);
                        postfloweringInspectionDao.update(postfloweringInspectionTableList.get(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("Insert pstflwring:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteFloweringList = scheduleInspectionLineDao.getUnpostedCompleteLinePostFlowering();
                for (int i = 0; i < pendingCompleteFloweringList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VII", pendingCompleteFloweringList.get(i).getProduction_lot_no(), pendingCompleteFloweringList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompletePostFlowering(1, 1, pendingCompleteFloweringList.get(0).getSchedule_no(), pendingCompleteFloweringList.get(0).getProduction_lot_no(),
                                    pendingCompleteFloweringList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspPostFlowering(pendingCompleteFloweringList.get(0).getSchedule_no(),
                                    pendingCompleteFloweringList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspPostFlowering(pendingCompleteFloweringList.get(0).getSchedule_no(),
                                    pendingCompleteFloweringList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               // Log.e("pstflwring. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule pstflwring. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("pstflwring. Complete", " Success");
        }

    }

    private void synDataPostMaturityInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                MaturityInspectionDao maturityInspectionDao = db.maturityInspectionDao();
                List<MaturityInspectionTable> maturityInspectionTableList = maturityInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                MaturityInspectionModel maturityInspectionModel = new MaturityInspectionModel();
                for (int i = 0; i < maturityInspectionTableList.size(); i++) {
                    maturityInspectionModel.scheduler_no = maturityInspectionTableList.get(i).getScheduler_no();
                    maturityInspectionModel.arrival_plan_no = maturityInspectionTableList.get(i).getArrival_plan_no();
                    maturityInspectionModel.production_lot_no = maturityInspectionTableList.get(i).getProduction_lot_no();

                    if(maturityInspectionTableList.get(i).getDate_of_inspection()!=null){
                        maturityInspectionModel.date_of_inspection = maturityInspectionTableList.get(i).getDate_of_inspection();
                    }
                    else {
                        maturityInspectionModel.date_of_inspection="";
                    }
                    maturityInspectionModel.crop_condition = maturityInspectionTableList.get(i).getCrop_condition();
                    maturityInspectionModel.crop_stage = maturityInspectionTableList.get(i).getCrop_stage();
                    maturityInspectionModel.yield_estimation_in_kgs = maturityInspectionTableList.get(i).getYield_estimation_in_kgs();
                    maturityInspectionModel.abiotic_stress = maturityInspectionTableList.get(i).getAbiotic_stress();
                    maturityInspectionModel.remarks = maturityInspectionTableList.get(i).getRemarks();
                    maturityInspectionModel.pest = maturityInspectionTableList.get(i).getPest();
                    maturityInspectionModel.diseases = maturityInspectionTableList.get(i).getDiseases();
                    maturityInspectionModel.diseases_remarks = maturityInspectionTableList.get(i).getDiseases_remarks();
                    if(maturityInspectionTableList.get(i).getRecommended_date()!=null){
                        maturityInspectionModel.recommended_date = maturityInspectionTableList.get(i).getRecommended_date();
                    }
                    else {
                        maturityInspectionModel.recommended_date="";
                    }
                    if(maturityInspectionTableList.get(i).getActual_date()!=null){

                        maturityInspectionModel.actual_date = maturityInspectionTableList.get(i).getActual_date();
                    }else {
                        maturityInspectionModel.actual_date="";
                    }
                    maturityInspectionModel.seed_setting = maturityInspectionTableList.get(i).getSeed_setting();
                    maturityInspectionModel.seed_setting_prcnt = maturityInspectionTableList.get(i).getSeed_setting_prcnt();
                    maturityInspectionModel.male_reciept_no = "0";
                    maturityInspectionModel.female_reciept_no ="0";
                    maturityInspectionModel.other_reciept_no = "0";

                    String attachment = maturityInspectionTableList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        maturityInspectionModel.attachment = base64_string;
                    }

                    JsonObject json = new JsonParser().parse(new Gson().toJson(maturityInspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertMaturityInsection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        maturityInspectionTableList.get(i).setSyncWithApi8(1);
                        maturityInspectionDao.update(maturityInspectionTableList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // Log.e("Insert maturty:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteMatuirtyList = scheduleInspectionLineDao.getUnpostedCompleteLineMaturityInsp();
                for (int i = 0; i < pendingCompleteMatuirtyList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection VIII", pendingCompleteMatuirtyList.get(i).getProduction_lot_no(), pendingCompleteMatuirtyList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteMaturity(1, 1, pendingCompleteMatuirtyList.get(0).getSchedule_no(), pendingCompleteMatuirtyList.get(0).getProduction_lot_no(),
                                    pendingCompleteMatuirtyList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspMaturity(pendingCompleteMatuirtyList.get(0).getSchedule_no(),
                                    pendingCompleteMatuirtyList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspMaturity(pendingCompleteMatuirtyList.get(0).getSchedule_no(),
                                    pendingCompleteMatuirtyList.get(0).getProduction_lot_no(), responseLst.get(0).nav_message, 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Log.e("maturtyInsp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule maturtyInsp. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("maturtyInsp. Complete", " Success");
        }

    }

    private void synDataPostHarvestingInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                HarvestingInspectionDao harvestingInspectionDao = db.harvestingInspectionDao();
                List<HarvestingInspectionTable> harvestingInspectionTableList = harvestingInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                HarvestingInspectionModel harvestingInspectionModel = new HarvestingInspectionModel();
                for (int i = 0; i < harvestingInspectionTableList.size(); i++) {

                    harvestingInspectionModel.scheduler_no = harvestingInspectionTableList.get(i).getScheduler_no();
                    harvestingInspectionModel.arrival_plan_no = harvestingInspectionTableList.get(i).getArrival_plan_no();
                    harvestingInspectionModel.production_lot_no = harvestingInspectionTableList.get(i).getProduction_lot_no();
                    if(harvestingInspectionTableList.get(i).getDate_of_inspection()!=null){
                        harvestingInspectionModel.date_of_inspection = harvestingInspectionTableList.get(i).getDate_of_inspection();
                    }
                    else {
                        harvestingInspectionModel.date_of_inspection="";
                    }
                    harvestingInspectionModel.crop_condition = harvestingInspectionTableList.get(i).getCrop_condition();
                    harvestingInspectionModel.crop_stage = harvestingInspectionTableList.get(i).getCrop_stage();
                    harvestingInspectionModel.moisture_per = harvestingInspectionTableList.get(i).getMoisture_per();
                    harvestingInspectionModel.sorting_grading = harvestingInspectionTableList.get(i).getSorting_grading();
                    harvestingInspectionModel.overall_agronomy = harvestingInspectionTableList.get(i).getOverall_agronomy();
                    harvestingInspectionModel.actual_date = harvestingInspectionTableList.get(i).getActual_date();
                    harvestingInspectionModel.diseases = harvestingInspectionTableList.get(i).getDiseases();
                    harvestingInspectionModel.pest_remarks = harvestingInspectionTableList.get(i).getDiseases_remarks();
                    if(harvestingInspectionTableList.get(i).getRecommended_date()!=null){
                        harvestingInspectionModel.recommended_date= harvestingInspectionTableList.get(i).getRecommended_date();
                    }
                    else {
                        harvestingInspectionModel.recommended_date="";
                    }
                    if(harvestingInspectionTableList.get(i).getActual_date()!=null){
                        harvestingInspectionModel.actual_date=harvestingInspectionTableList.get(i).getActual_date();
                    }
                    else {
                        harvestingInspectionModel.actual_date="";
                    }
                    harvestingInspectionModel.diseases_remarks = harvestingInspectionTableList.get(i).getDiseases_remarks();
                    harvestingInspectionModel.remarks = harvestingInspectionTableList.get(i).getRemarks();
                    harvestingInspectionModel.seed_setting = harvestingInspectionTableList.get(i).getSeed_setting();
                    harvestingInspectionModel.seed_setting_prcnt = harvestingInspectionTableList.get(i).getSeed_setting_prcnt();

                    harvestingInspectionModel.male_reciept_no = "0";
                    harvestingInspectionModel.female_reciept_no = "0";
                    harvestingInspectionModel.other_reciept_no = "0";

                    String attachment = harvestingInspectionTableList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        if (base64_string != null && base64_string.equalsIgnoreCase("")) {
                            harvestingInspectionModel.attachment = base64_string;
                        }
                        else {
                            harvestingInspectionModel.attachment="";
                        }
                    }
                    JsonObject json = new JsonParser().parse(new Gson().toJson(harvestingInspectionModel)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertHarvestingInsection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp!=null && server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        harvestingInspectionTableList.get(i).setSynWithApi9(1);
                        harvestingInspectionDao.update(harvestingInspectionTableList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // Log.e("Insert harvstng:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteHarvestingList = scheduleInspectionLineDao.getUnpostedCompleteLineHarvestingInsp();
                for (int i = 0; i < pendingCompleteHarvestingList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection IX", pendingCompleteHarvestingList.get(i).getProduction_lot_no(), pendingCompleteHarvestingList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst!=null && responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteHarvesting(1, 1, pendingCompleteHarvestingList.get(0).getSchedule_no(), pendingCompleteHarvestingList.get(0).getProduction_lot_no(),
                                    pendingCompleteHarvestingList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspHarvesting(pendingCompleteHarvestingList.get(0).getSchedule_no(),
                                    pendingCompleteHarvestingList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspHarvesting(pendingCompleteHarvestingList.get(0).getSchedule_no(),
                                    pendingCompleteHarvestingList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("harvstng. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule harvstng. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            //Log.e("harvstng. Complete", " Success");
        }

    }

    private void synDataPostQcInspection() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            //todo first step is insert unposted line
            try {
                QcInspectionDao qcInspectionDao = db.qcInspectionDao();
                List<QcInspectionTable> qcInspectionTableList = qcInspectionDao.getSavedLineIntoLocal();
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                Qc_Inspection_Model qc_inspection_model = new Qc_Inspection_Model();
                for (int i = 0; i < qcInspectionTableList.size(); i++) {
                    qc_inspection_model.scheduler_no = qcInspectionTableList.get(i).getScheduler_no();
                    qc_inspection_model.arrival_plan_no = qcInspectionTableList.get(i).getArrival_plan_no();
                    qc_inspection_model.production_lot_no = qcInspectionTableList.get(i).getProduction_lot_no();
                    if(qcInspectionTableList.get(i).getDate_of_inspection()!=null){
                        qc_inspection_model.date_of_inspection = qcInspectionTableList.get(i).getDate_of_inspection();
                    }else {
                        qc_inspection_model.date_of_inspection="";
                    }
                    if(qcInspectionTableList.get(i).getDate_of_actual_date()!=null){
                        qc_inspection_model.actual_date = qcInspectionTableList.get(i).getDate_of_actual_date();
                    }else {
                        qc_inspection_model.actual_date="";
                    }
                    qc_inspection_model.crop = qcInspectionTableList.get(i).getCrop();
                    qc_inspection_model.off_type_per_male_parent = qcInspectionTableList.get(i).getOff_type_per_male_parent();
                    qc_inspection_model.off_type_per_female_parent = qcInspectionTableList.get(i).getOff_type_per_female_parent();
                    qc_inspection_model.flag_status = qcInspectionTableList.get(i).getFlag_status();
                    qc_inspection_model.isolation_distance = qcInspectionTableList.get(i).getIsolation_distance();
                    qc_inspection_model.isolation_time = qcInspectionTableList.get(i).getIsolation_time();
                    qc_inspection_model.nick_per = qcInspectionTableList.get(i).getNick_per();
                    qc_inspection_model.border_rows = qcInspectionTableList.get(i).getBorder_rows();
                    qc_inspection_model.wind_direction = qcInspectionTableList.get(i).getWind_direction();
                    qc_inspection_model.male_stand = qcInspectionTableList.get(i).getMale_stand();
                    qc_inspection_model.seed_setting = qcInspectionTableList.get(i).getSeed_setting();
                    qc_inspection_model.seed_setting_prcnt = qcInspectionTableList.get(i).getSeed_setting_prcnt();
                    qc_inspection_model.grain_remarks = qcInspectionTableList.get(i).getGrain_remarks();

                    qc_inspection_model.male_reciept_no = qcInspectionTableList.get(i).getMale_reciept_no();
                    qc_inspection_model.female_reciept_no = qcInspectionTableList.get(i).getFemale_reciept_no();
                    qc_inspection_model.other_reciept_no = qcInspectionTableList.get(i).getOther_reciept_no();
                    if(qcInspectionTableList.get(i).getDate_of_field_updated()!=null){
                        qc_inspection_model.date_of_field_updated = qcInspectionTableList.get(i).getDate_of_field_updated();
                    }
                    else {
                        qc_inspection_model.date_of_field_updated="";
                    }
                    qc_inspection_model.crop_condition = qcInspectionTableList.get(i).getCrop_condition();
                    qc_inspection_model.crop_stage = qcInspectionTableList.get(i).getCrop_stage();
                    qc_inspection_model.lot_recommend = qcInspectionTableList.get(i).getLot_recommend();


                    String attachment = qcInspectionTableList.get(i).getAttachment();
                    if (attachment != null && !attachment.equalsIgnoreCase("")) {
                        String base64_string = StaticMethods.covertBase64File(attachment);
                        qc_inspection_model.attachment = base64_string;
                    }
                    else {
                        qc_inspection_model.attachment="";
                    }

                    JsonObject json = new JsonParser().parse(new Gson().toJson(qc_inspection_model)).getAsJsonObject();
                    Call<List<ResponseModel>> call = mAPIService.insertQCInsection(json);
                    List<ResponseModel> server_insertline_resp = call.execute().body();
                    if (server_insertline_resp.size() > 0 && server_insertline_resp.get(0).condition) {
                        qcInspectionTableList.get(i).setSyncwithQc(1);
                        qcInspectionDao.update(qcInspectionTableList.get(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // Log.e("Insert Qc:", "Line Sync Success");
            }
            //todo third step complete Line..
            try {
                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                ScheduleInspectionLineDao scheduleInspectionLineDao = db.scheduleInspectionLineDao();
                List<SchedulerInspectionLineTable> pendingCompleteQcList = scheduleInspectionLineDao.getUnpostedCompleteLineQcInsp();
                for (int i = 0; i < pendingCompleteQcList.size(); i++) {
                    Call<List<CompleteGerminationInspectionModel>> call = mAPIService.completeInspection("Inspection X", pendingCompleteQcList.get(i).getProduction_lot_no(), pendingCompleteQcList.get(i).getSchedule_no());
                    List<CompleteGerminationInspectionModel> responseLst = call.execute().body();
                    if (responseLst.size() > 0 && responseLst.get(0).condition) {
                        if (responseLst.get(0).nav_condition != 0) {
                            scheduleInspectionLineDao.updateOnServerCompleteQc(1, 1, pendingCompleteQcList.get(0).getSchedule_no(), pendingCompleteQcList.get(0).getProduction_lot_no(),
                                    pendingCompleteQcList.get(0).getIns1_completed_on(), 1);

                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspQc(pendingCompleteQcList.get(0).getSchedule_no(),
                                    pendingCompleteQcList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        } else {
                            scheduleInspectionLineDao.updateServerErrorDuringCompleteHeaderInspQc(pendingCompleteQcList.get(0).getSchedule_no(),
                                    pendingCompleteQcList.get(0).getProduction_lot_no(), 1, responseLst.get(0).nav_message);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Log.e("QcInsp. Complete :", "Complete Sync Success");
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Sync Schedule QcInsp. Complete", activity);
        } finally {
            db.close();
            db.destroyInstance();
            // Log.e("QcInsp. Complete", " Success");
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
        //NotificationCall("completed syncing");
    }

    //todo send Exception to server
    private void exceptionSendToServer() {
        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
        try {
            ExceptionDao exceptionTable = db.exceptionDao();
            List<ExceptionTableModel> exception_list = exceptionTable.getAllException();
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            for (int i = 0; i < exception_list.size(); i++) {
                JsonObject hashMap = new JsonObject();
                hashMap.addProperty("Exception", exception_list.get(i).getMyException());
                hashMap.addProperty("ExceptionType", exception_list.get(i).getExceptionType());
                hashMap.addProperty("lineNo", exception_list.get(i).getLineNo());
                hashMap.addProperty("fragment", exception_list.get(i).getFragment());
                hashMap.addProperty("method", exception_list.get(i).getMethod());
                Call<List<ExceptionModel>> call = mAPIService.Exception(hashMap);
                Response<List<ExceptionModel>> response = call.execute();
                //Log.e("Result", response.body().get(0).message);
            }
            if (exception_list.size() > 0) {
                int a = exceptionTable.deleteAllRecord();
//                    Log.e("Delte",a+" ");
            }
        } catch (Exception e) {
        } finally {
            db.close();
            db.destroyInstance();
        }
    }

    private void getGeoServerData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        if (sessionManagement.getLastSync() == null || sessionManagement.getLastSync().equalsIgnoreCase("")) {
            lastSync = "2020-02-27T00:00:42.387";
        } else {
            lastSync = sessionManagement.getLastSync();
            try {
                sessionManagement.setLastSync(DateTimeUtilsCustome.getCurrentTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Call<GeoGraphicData> call = mAPIService.getGeographical(lastSync);
        try {
            GeoGraphicData geoGraphicData = call.execute().body();
            try {
                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                try {
                    if (geoGraphicData != null) {
                        // insert data into database..................
                        insertGeographicalSetUp(geoGraphicData.geographical_Setup, db);
                        setStateMasterData(geoGraphicData.state_master, db);
                        setzoneMasterData(geoGraphicData.zone_master, db);
                        setDistricMasterData(geoGraphicData.district_master, db);
                        setRegionMasterData(geoGraphicData.region_master, db);
                        setTalukaMaster(geoGraphicData.taluka_master, db);
                        setArea(geoGraphicData.area_master, db);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                    db.destroyInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "sync_data_exception", activity);
        }
    }


    private int insertGeographicalSetUp(List<GeographicModel> geoGraphicData, PristineDatabase db) {
        GeographicDao geographicDao = db.geographicDao();
        geographicDao.deleteAllRecord();
        List<GeoghraphicalTable> geoghraphicalTableList = new ArrayList<>();
        try {
            for (int i = 0; i < geoGraphicData.size(); i++) {
                GeoghraphicalTable geoghraphicalTable = new GeoghraphicalTable();
                geoghraphicalTable.setId(geoGraphicData.get(i).id);
                geoghraphicalTable.setZone(geoGraphicData.get(i).zone);
                geoghraphicalTable.setState(geoGraphicData.get(i).state);
                geoghraphicalTable.setRegion(geoGraphicData.get(i).region);
                geoghraphicalTable.setDistrict(geoGraphicData.get(i).district);
                geoghraphicalTable.setTaluka(geoGraphicData.get(i).taluka);
                geoghraphicalTable.setManager(geoGraphicData.get(i).managers);
                geoghraphicalTable.setUpdated_on(geoGraphicData.get(i).updated_on);
                geoghraphicalTable.setActive(geoGraphicData.get(i).active);
                geoghraphicalTableList.add(geoghraphicalTable);
            }
            geographicDao.insert(geoghraphicalTableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return geographicDao.getRowCount();

    }


    private int setzoneMasterData(List<ZoneMaster> zoneMasters, PristineDatabase db) {
        ZoneMaterDao zoneMaterDao = db.zoneMaterDao();
        List<ZoneMasterTable> zoneMasterTableList = new ArrayList<>();
        for (int i = 0; i < zoneMasters.size(); i++) {
            ZoneMasterTable zoneMasterTable = new ZoneMasterTable();
            zoneMasterTable.setCode(zoneMasters.get(i).code);
            zoneMasterTable.setDescription(zoneMasters.get(i).description);
            zoneMasterTable.setActive(zoneMasters.get(i).active);
            zoneMasterTable.setUpdated_on(zoneMasters.get(i).updated_on);
            zoneMasterTableList.add(zoneMasterTable);
        }
        zoneMaterDao.insert(zoneMasterTableList);
        return zoneMaterDao.getRowCount();
    }

    private int setStateMasterData(List<StateMaster> stateMasters, PristineDatabase db) {
        StateMasterDao stateMasterDao = db.stateMasterDao();
        List<StateMasterTable> stateMasterTableList = new ArrayList<>();
        for (int i = 0; i < stateMasters.size(); i++) {
            StateMasterTable stateMasterTable = new StateMasterTable();
            stateMasterTable.setCode(stateMasters.get(i).code);
            stateMasterTable.setName(stateMasters.get(i).name);
            stateMasterTable.setActive(stateMasters.get(i).active);
            stateMasterTable.setUpdated_on(stateMasters.get(i).updated_on);
            stateMasterTableList.add(stateMasterTable);
        }
        stateMasterDao.insert(stateMasterTableList);
        return stateMasterDao.getRowCount();
    }


    private int setRegionMasterData(List<RegionMaster> regionMasters, PristineDatabase db) {
        RegionMasterDao regionMasterDao = db.regionMasterDao();
        List<RegionMasterTable> regionMasterTableList = new ArrayList<>();

        for (int i = 0; i < regionMasters.size(); i++) {
            RegionMasterTable regionMasterTable = new RegionMasterTable();
            regionMasterTable.setCode(regionMasters.get(i).code);
            regionMasterTable.setName(regionMasters.get(i).name);
            regionMasterTable.setActive(regionMasters.get(i).active);
            regionMasterTable.setUpdated_on(regionMasters.get(i).updated_on);
            regionMasterTable.setRegional_head(regionMasters.get(i).regional_head);
            regionMasterTable.setRegional_head_email_id(regionMasters.get(i).regional_head_email_id);
            regionMasterTable.setRegional_head_emp_code(regionMasters.get(i).regional_head_emp_code);
            regionMasterTable.setRegional_manager(regionMasters.get(i).regional_manager);
            regionMasterTable.setRegional_manager_email_id(regionMasters.get(i).regional_manager_email_id);
            regionMasterTable.setRegional_manager_emp_code(regionMasters.get(i).regional_manager_emp_code);
            regionMasterTable.setRegional_manager_mobile(regionMasters.get(i).regional_manager_mobile);
            regionMasterTableList.add(regionMasterTable);
        }
        regionMasterDao.insert(regionMasterTableList);
        return regionMasterDao.getRowCount();
    }

    private int setDistricMasterData(List<DistrictMaster> districtMasters, PristineDatabase db) {
        DistricMasterDao districMasterDao = db.districMasterDao();

        List<DistricMasterTable> districMasterTableList = new ArrayList<>();
        for (int i = 0; i < districtMasters.size(); i++) {
            DistricMasterTable districMasterTable = new DistricMasterTable();
            districMasterTable.setCode(districtMasters.get(i).code);
            districMasterTable.setName(districtMasters.get(i).name);
            districMasterTable.setActive(districtMasters.get(i).active);
            districMasterTable.setUpdated_on(districtMasters.get(i).updated_on);
            districMasterTable.setClass_of_city(districtMasters.get(i).class_of_city);
            districMasterTableList.add(districMasterTable);
        }
        districMasterDao.insert(districMasterTableList);
        //Log.e("list", new Gson().toJson(districMasterTableList));
        return districMasterDao.getRowCount();
    }


    private int setTalukaMaster(List<TalukaMaster> talukaMasters, PristineDatabase db) {
        TalukaMasterDao talukaMasterDao = db.talukaMasterDao();
        List<TalukaMasterTable> talukaMasterList = new ArrayList<>();

        for (int i = 0; i < talukaMasters.size(); i++) {
            TalukaMasterTable talukaMasterTable = new TalukaMasterTable();
            talukaMasterTable.setCode(talukaMasters.get(i).code);
            talukaMasterTable.setDescription(talukaMasters.get(i).description);
            talukaMasterTable.setUpdated_on(talukaMasters.get(i).updated_on);
            talukaMasterTable.setActive(talukaMasters.get(i).active);
            talukaMasterList.add(talukaMasterTable);
        }
        talukaMasterDao.insert(talukaMasterList);
        return talukaMasterDao.getRowCount();
    }

    private int setArea(List<AreaMasterModel> areaMasterList, PristineDatabase pristineDatabase) {
        AreaDao areaDao = pristineDatabase.areaDao();
        List<AreaMasterTable> areaMasterTableList = new ArrayList<>();
        for (int i = 0; i < areaMasterList.size(); i++) {
            AreaMasterTable areaMasterTable = new AreaMasterTable();
            areaMasterTable.setArea_name(areaMasterList.get(i).area_name);
            areaMasterTable.setArea_code(areaMasterList.get(i).area_code);
            areaMasterTable.setUpdated_on(areaMasterList.get(i).updated_on);
            areaMasterTableList.add(areaMasterTable);
        }
        areaDao.insert(areaMasterTableList);
        return areaDao.getRowCount();
    }

    private void getUserLocationMasterData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        try {
            List<UserLocationModel> user_locastion_masterlist = call.execute().body();
            if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                try {
                    UserLocationMasterDao userLocationMasterDao = db.userLocationMasterDao();
                    userLocationMasterDao.deleteAllRecord();
                    List<LocationMasterTable> userLocationTableList = new ArrayList<>();
                    for (int i = 0; i < user_locastion_masterlist.size(); i++) {
                        if (user_locastion_masterlist.get(i).location_code != null) {
                            LocationMasterTable locationMasterTable = new LocationMasterTable();
                            locationMasterTable.setEmail_id(user_locastion_masterlist.get(i).email_id);
                            locationMasterTable.setLocation_code(user_locastion_masterlist.get(i).location_code);
                            locationMasterTable.setLocation_name(user_locastion_masterlist.get(i).location_name);
                            userLocationTableList.add(locationMasterTable);
                        }
                    }
                    userLocationMasterDao.insert(userLocationTableList);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                    db.destroyInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // Log.e("location_master", "success");
        }
    }


    private void getPlantingFsio_BsioORGData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingFsio_bsio_model>> call = mAPIService.getPlantingFsioBsioData("FSIO","");
        try {
            List<PlantingFsio_bsio_model> templantingList_fsio = call.execute().body();
            if (templantingList_fsio.size() > 0 && templantingList_fsio.get(0).condition) {
                PristineDatabase pristinedb = PristineDatabase.getAppDatabase(activity);
                try {
                    //Log.e("sucess", "success");
                 bindPlantingFsio_data_IntoLocal(templantingList_fsio, pristinedb);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pristinedb.close();
                    pristinedb.destroyInstance();
                }
            }
        } catch (Exception e) {
            //Log.e("exception database", e.getMessage() + "cause");
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_fsio_bsio_fragment_", activity);
        } finally {
           // Log.e("planting_fsio_bsio", "Done");
        }
    }


    private int bindPlantingFsio_data_IntoLocal(List<PlantingFsio_bsio_model> templantingList_fsio, PristineDatabase pristineDatabase) {
        Planting_fsio_bsio_Dao planting_fsio_bsio_dao = pristineDatabase.planting_fsio_bsio_dao();
        List<PlantingFSIO_BSIO_Table> plantingFSIOBsioTableList = new ArrayList<>();
        for (int i = 0; i < templantingList_fsio.size(); i++) {
            PlantingFSIO_BSIO_Table plantingFSIO_bsio_table = PlantingFSIO_BSIO_Table.getPlanting_fsio_bsio_data(templantingList_fsio.get(i));
            if (plantingFSIO_bsio_table.getNo() != null) {
                plantingFSIOBsioTableList.add(plantingFSIO_bsio_table);
            }
        }
        planting_fsio_bsio_dao.insertList(plantingFSIOBsioTableList);
        return planting_fsio_bsio_dao.getRowCount();
    }


    private void getSelectSeason() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        try {
            List<SeasonMasterModel> templantingList_season = call.execute().body();
            if (templantingList_season!=null && templantingList_season.size() > 0) {
                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                try {
                    bindSeaonListIntoLocal(templantingList_season, db);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                    db.destroyInstance();
                }
            }
        } catch (Exception e) {
            //Log.e("exception database", e.getMessage() + "cause");
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_season_", activity);
        } finally {
           // Log.e("season", "done");
        }
    }

    private void bindSeaonListIntoLocal(List<SeasonMasterModel> templantingList_season, PristineDatabase pristineDatabase) {
        SeasonDao seasonDao = pristineDatabase.seasonDao();
        for (int i = 0; i < templantingList_season.size(); i++) {
            if (templantingList_season.get(i).sync_To_App.equalsIgnoreCase("True")) {
                SeasonMasterTable seasonMasterTable = SeasonMasterTable.getSeasonMasterTable(templantingList_season.get(i));
                if (seasonDao.isDataExist(seasonMasterTable.getSeason_Code()) > 0) {
                    seasonDao.update(seasonMasterTable);
                } else {
                    seasonDao.insert(seasonMasterTable);
                }
            }
        }
    }

    private void getOrganizerCode() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<OrganizerModel> call = mAPIService.getOrgnizerData("","");
        try {
            OrganizerModel organizerModel = call.execute().body();
            if (organizerModel != null && organizerModel.condition) {
                List<OrganizerModel.Data> organizerList = organizerModel.data;
                if (organizerList != null && organizerList.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        bindOrganizerList(organizerList, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "organizer_list", activity);
        } finally {
            Log.e("organizer_list", "done");
        }
    }

    private void bindOrganizerList(List<OrganizerModel.Data> orginer_list, PristineDatabase
            pristineDatabase) {
        Organizer_master_Dao organizer_master_dao = pristineDatabase.organizer_master_dao();
        for (int i = 0; i < orginer_list.size(); i++) {
            Organizer_master_Table organizer_master_table = Organizer_master_Table.insertOrganiserData(orginer_list.get(i));
            if (organizer_master_dao.isDataExist(orginer_list.get(i).No) > 0) {
                organizer_master_dao.update(organizer_master_table);
            } else {
                organizer_master_dao.insert(organizer_master_table);
            }
        }
    }


    private void getFarmermaster() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList("");
        try {
            DispatchFarmerModel farmerMasterModel = call.execute().body();
            if (farmerMasterModel != null && farmerMasterModel.condition) {
                List<DispatchFarmerModel.Data> farmermaster_list = farmerMasterModel.data;
                if (farmermaster_list != null && farmermaster_list.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        // insert data into database..................
                        bindFarmerList(farmermaster_list, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", activity);
        } finally {
            Log.e("farmer_list", "done");
        }
    }

    private int bindFarmerList(List<DispatchFarmerModel.Data> farmermaster_list, PristineDatabase pristineDatabase) {
        SeedFarmerMasterDao seedFarmerMasterDao = pristineDatabase.seedFarmerMasterDao();
        for (int i = 0; i < farmermaster_list.size(); i++) {
            Seed_Farmer_master_Table seed_farmer_master_table = Seed_Farmer_master_Table.insertFarmerMasterTable(farmermaster_list.get(i));
            if (seedFarmerMasterDao.isDataExist(farmermaster_list.get(i).No.toLowerCase()) > 0) {
                seedFarmerMasterDao.update(seed_farmer_master_table);
            } else {
                seedFarmerMasterDao.insert(seed_farmer_master_table);
            }
        }
        return seedFarmerMasterDao.getRowCount();
    }


    private void getCityMaster() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<CityMasterModel>> call = mAPIService.getCityMasterModel("getCityMaster");
        try {
            List<CityMasterModel> cityMasterModels = call.execute().body();
            if (cityMasterModels != null && cityMasterModels.size() > 0 && cityMasterModels.get(0).condition) {
                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                try {
                    // insert data into database..................
                  bindCityMasterList(cityMasterModels, db);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                    db.destroyInstance();
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "city_master_list", activity);
        } finally {
            Log.e("city_master_list", "done");
        }
    }

    private void bindCityMasterList(List<CityMasterModel> cityMasterModelList, PristineDatabase pristineDatabase) {
        City_master_Dao city_master_dao = pristineDatabase.city_master_dao();
        for (int i = 0; i < cityMasterModelList.size(); i++) {
            if (cityMasterModelList.get(i).code != null && !cityMasterModelList.get(i).code.equalsIgnoreCase("")) {
                CityMasterTable cityMasterTable = new CityMasterTable();
                cityMasterTable.setClass_of_city(cityMasterModelList.get(i).class_of_city);
                cityMasterTable.setCode(cityMasterModelList.get(i).code);
                cityMasterTable.setCountry_region_code(cityMasterModelList.get(i).country_region_code);
                if (city_master_dao.isDataExist(cityMasterTable.getCode()) > 0) {
                    city_master_dao.update(cityMasterTable);
                } else {
                    city_master_dao.insert(cityMasterTable);
                }
            }
        }
    }

    private void getPlantingLotList() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData("","","");
        try {
            PlantingLotModel plantingLotModel = call.execute().body();
            if (plantingLotModel != null && plantingLotModel.condition) {
                List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                if (planting_lot_list != null && planting_lot_list.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        bindPlantingLotList(planting_lot_list, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_lot_list", activity);
        } finally {
            Log.e("planting_lot_list", "done");
        }
    }

    private void bindPlantingLotList(List<PlantingLotModel.Data> planting_lot_list, PristineDatabase pristineDatabase) {
        Planting_lot_Dao planting_lot_dao = pristineDatabase.planting_lot_dao();
        planting_lot_dao.deleteAllRecord();
        for (int i = 0; i < planting_lot_list.size(); i++) {
            Planting_Lot_master_Table planting_lot_master_table = new Planting_Lot_master_Table();
            planting_lot_master_table.setLot_No(planting_lot_list.get(i).Lot_No);
            planting_lot_master_table.setDocument_No(planting_lot_list.get(i).Document_No);
            planting_lot_master_table.setDocument_SubType(planting_lot_list.get(i).Document_SubType);
            planting_lot_master_table.setMale_Female_Other(planting_lot_list.get(i).Male_Female_Other);
            planting_lot_dao.insert(planting_lot_master_table);
        }
    }


    private void getHybridItemMasterData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<HybridItemMasterModel> call = mAPIService.getHybridItemMaster(sessionManagement.getUserEmail());
        try {
            HybridItemMasterModel hybridItemMasterModel = call.execute().body();
            if (hybridItemMasterModel != null && hybridItemMasterModel.condition) {
                List<HybridItemMasterModel.Data> hybrid_item_list = hybridItemMasterModel.data;
                if (hybrid_item_list != null && hybrid_item_list.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        bindHybridItemList(hybrid_item_list, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "hybrid_item_list", activity);
        } finally {
            Log.e("hybrid_item_list", "done");
        }
    }

    private void bindHybridItemList(List<HybridItemMasterModel.Data> hybrid_item_list, PristineDatabase db) {
        HybridItemMasterDao hybridItemMasterDao = db.hybridItemMasterDao();
        hybridItemMasterDao.deleteAllRecord();
        for (int i = 0; i < hybrid_item_list.size(); i++) {
            Hybrid_Item_Table hybrid_item_table = Hybrid_Item_Table.insertHybridItem(hybrid_item_list.get(i));
            hybridItemMasterDao.insert(hybrid_item_table);
        }
    }


    private void getCropMasterData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<CropMassterModel> call = mAPIService.getCropMasterData();

        try {
            CropMassterModel cropMassterModel = call.execute().body();
            try {
                if (cropMassterModel != null && cropMassterModel.condition) {
                    List<CropMassterModel.Data> crop_master_list = cropMassterModel.data;
                    if (crop_master_list != null && crop_master_list.size() > 0) {
                        PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                        try {
                         bindCropMasterData(crop_master_list, db);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            db.close();
                            db.destroyInstance();
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("exception database", e.getMessage() + "cause");
                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_crop_master", activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.e("hybrid_item_crop_list", "done");
        }
    }


    private void bindCropMasterData(List<CropMassterModel.Data> cropmaster_model_list, PristineDatabase db) {
        CropHytechMasterDao cropHytechMasterDao = db.cropHytechMasterDao();
        cropHytechMasterDao.deleteAllRecord();
        for (int i = 0; i < cropmaster_model_list.size(); i++) {
            CropHytechMasterTable cropHytechMasterTable = new CropHytechMasterTable();
            cropHytechMasterTable.setCode(cropmaster_model_list.get(i).Code);
            cropHytechMasterTable.setDescription(cropmaster_model_list.get(i).Description);
            cropHytechMasterDao.insert(cropHytechMasterTable);
        }
    }


    private void getRoleMasterData(String cust_type) {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributorSync(cust_type,"","");
        try {
            RoleMasterModel roleMasterModelList = call.execute().body();
            if (roleMasterModelList != null && roleMasterModelList.condition) {
                List<RoleMasterModel.Data> roleMasterList = roleMasterModelList.data;
                if (roleMasterList != null && roleMasterList.size() > 0) {
                    CountModel countmodel = new CountModel();
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                      bindRoleMasteta(roleMasterList, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();

                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_crop_master", activity);
        } finally {
            Log.e("role_master", "done");
        }
    }

    private void bindRoleMasteta(List<RoleMasterModel.Data> roleMasterModelList, PristineDatabase db) {
        RoleMasterDao roleMasterDao = db.roleMasterDao();
        roleMasterDao.deleteAllRecord();
        for (int i = 0; i < roleMasterModelList.size(); i++) {
            RoleMasterTable roleMasterTable = RoleMasterTable.insertRoleMasterData(roleMasterModelList.get(i));
            roleMasterDao.insert(roleMasterTable);
        }
    }


    private void getBankMasterData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<BankMaserModel> call = mAPIService.getBankMasterData();
        try {
            BankMaserModel bankMaserModel = call.execute().body();
            if (bankMaserModel != null) {
                List<BankMaserModel.Data> bankMasterList = bankMaserModel.data;
                if (bankMasterList != null && bankMasterList.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                      bindBankMasterData(bankMasterList, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("exception database", e.getMessage() + "cause");
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_bank_master", activity);
        } finally {
            Log.e("bank_master_data", "done");
        }
    }


    private void bindBankMasterData(List<BankMaserModel.Data> bankMasterList, PristineDatabase db) {
        BankMasterDao bankMasterDao = db.bankMasterDao();
        bankMasterDao.deleteAllRecord();
        for (int i = 0; i < bankMasterList.size(); i++) {
            BankMasterTable bankMasterTable = BankMasterTable.insertBankMasterData(bankMasterList.get(i));
            bankMasterDao.insert(bankMasterTable);
        }
    }


    private void getUnitOfMeasureData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitOfMeasureModel> call = mAPIService.getUnitsOfMeasureList();
        try {
            UnitOfMeasureModel unitOfMeasureModelList = call.execute().body();
            if (unitOfMeasureModelList != null) {
                List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList = unitOfMeasureModelList.data;
                if (uomList != null && uomList.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                      bindUOMData(uomList, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.e("unit_of_measure_code", "done");
        }
    }


    private void getBookingUnitOfPriceData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitPriceModel> call = mAPIService.getunitPriceModelList();
        try {
            UnitPriceModel unitOfMeasureModel = call.execute().body();
            if (unitOfMeasureModel != null) {
                List<UnitPriceModel.Data> unitPricelist = unitOfMeasureModel.data;
                if (unitPricelist != null && unitPricelist.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        bindBookingUnitPriceData(unitPricelist, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_booking_unit_price", activity);
        } finally {
            Log.e("booking_unit_price_list", "done");
        }
    }


    private void bindBookingUnitPriceData(List<UnitPriceModel.Data> unitPricelist, PristineDatabase db) {
        BookingUnitPriceDao bookingUnitPriceDao = db.bookingUnitPriceDao();
        bookingUnitPriceDao.deleteAllRecord();
        for (int i = 0; i < unitPricelist.size(); i++) {
            BookingUnitPriceTable bookingUnitPriceTable = BookingUnitPriceTable.getBookingUnitPrice(unitPricelist.get(i));
            bookingUnitPriceDao.insert(bookingUnitPriceTable);
        }
    }

    private void  bindUOMData(List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList, PristineDatabase db) {
        UOMDao uomDao = db.uomDao();
        uomDao.deleteAllRecord();
        for (int i = 0; i < uomList.size(); i++) {
            UomTable uomTable = UomTable.insertUOMData(uomList.get(i));
            uomDao.insert(uomTable);
        }
    }

    private void getShipmentToAddressData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<ShipToAddressModel> call = mAPIService.getShipToAddressData("","");
        try {
            ShipToAddressModel shipToAddressModel = call.execute().body();
            if (shipToAddressModel != null && shipToAddressModel.condition) {
                List<ShipToAddressModel.Data> ship_address_list = shipToAddressModel.data;
                if (ship_address_list != null && ship_address_list.size() > 0) {
                    PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                    try {
                        bindShipAdressData(ship_address_list, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_ship_details", activity);
        } finally {
            //Log.e("ship_address_details", "done");
        }
    }


    private void  bindShipAdressData(List<ShipToAddressModel.Data> ship_address_list, PristineDatabase db) {
        ShipToAddressDao shipToAddressDao = db.shipToAddressDao();
        shipToAddressDao.deleteAllRecord();
        for (int i = 0; i < ship_address_list.size(); i++) {
            ShipToAddressDataTable shipToAddressDataTable = ShipToAddressDataTable.insertShipToAddressData(ship_address_list.get(i));
            shipToAddressDao.insert(shipToAddressDataTable);
        }
    }


    private void getPlantingLineListLot() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingProdcutionLotModel>> call = mAPIService.getPlantingLineListData();
        try {
            List<PlantingProdcutionLotModel> planting_lot_list_data = call.execute().body();
            if (planting_lot_list_data != null && planting_lot_list_data.size() > 0) {
                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                try {
                     bindPlantingLineLotData(planting_lot_list_data, db);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                    db.destroyInstance();
                }
            }
        } catch (Exception e) {
            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getPlanting_line_lotList", activity);
        } finally {
            //Log.e("planting_line_lotlist", "done");
        }
    }


    private void bindPlantingLineLotData(List<PlantingProdcutionLotModel> planting_line_lot_list_, PristineDatabase db) {
        PlantingLineLotListDao plantingLineLotListDao = db.plantingLineLotListDao();
        plantingLineLotListDao.deleteAllRecord();
        for (int i = 0; i < planting_line_lot_list_.size(); i++) {
            PlantingLineLotListTable plantingLostParentTable = PlantingLineLotListTable.bindPLantingLotDetail(planting_line_lot_list_.get(i));
            plantingLineLotListDao.insert(plantingLostParentTable);
        }
    }

    private void getFsioBsioSaleOrderNo(){
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<FsioBsioSaleOrderNoModel>call = mAPIService.getFsioBsioSaleOrderNo("");
              try{
             FsioBsioSaleOrderNoModel fsioBsioSaleOrderNoModel = call.execute().body();
                        if (fsioBsioSaleOrderNoModel != null && fsioBsioSaleOrderNoModel.condition) {
                            List<FsioBsioSaleOrderNoModel.Data> fsio_bsio_sale_order_no_list= fsioBsioSaleOrderNoModel.data;
                            if(fsio_bsio_sale_order_no_list!=null && fsio_bsio_sale_order_no_list.size()>0) {
                                PristineDatabase db = PristineDatabase.getAppDatabase(activity);
                                try {
                                    FsioBsioSaleOrderNoDao fsioBsioSaleOrderNoDao= db.fsioBsioSaleOrderNoDao();
                                    fsioBsioSaleOrderNoDao.deleteAllRecord();
                                    for(int i=0;i<fsio_bsio_sale_order_no_list.size();i++){
                                        FsioBsioSaleOrderNoTable fsioBsioSaleOrderNoTable = FsioBsioSaleOrderNoTable.insertFsioBsioSaleOrderNoData(fsio_bsio_sale_order_no_list.get(i));
                                        fsioBsioSaleOrderNoDao.insert(fsioBsioSaleOrderNoTable);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                }
                            }
                    }
                } catch (Exception e) {
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "fsio_bsio_sale_order_no_list",activity);
                }
              finally {
                 // Log.e("fsio_sale_list","sync_done");
              }
              }

    // declaring variables

   /* void NotificationCall(String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity.getApplicationContext())
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(message)
                        .setContentText("This is a sync notification");

        Intent notificationIntent = new Intent(activity.getApplicationContext(), BottomMainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) (this.activity.getSystemService(Context.NOTIFICATION_SERVICE));
        manager.notify(0, builder.build());
    }*/


}

