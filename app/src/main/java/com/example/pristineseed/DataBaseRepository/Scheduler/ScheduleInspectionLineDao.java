package com.example.pristineseed.DataBaseRepository.Scheduler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;

import java.util.List;

@Dao
public interface ScheduleInspectionLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SchedulerInspectionLineTable scheduleInspectionLineTables);

    @Insert
    void insertList(List<SchedulerInspectionLineTable> scheduleInspectionLineTables);

    @Query("SELECT * FROM scheduler_line_table WHERE schedule_no=:schedule_no")
    List<SchedulerInspectionLineTable> getAllData(String schedule_no);

    @Query("SELECT * FROM scheduler_line_table WHERE production_lot_no =:production_lot_no")
    SchedulerInspectionLineTable getDatabyLotNo(String production_lot_no);

    @Query("SELECT * FROM scheduler_line_table WHERE schedule_no=:schedule_no and production_lot_no=:production_lot_no")
    SchedulerInspectionLineTable getAllDatabyLotNo(String schedule_no,String production_lot_no);

    @Query("SELECT * FROM scheduler_line_table WHERE schedule_no=:schedule_no and production_lot_no=:production_lot_no")
    SchedulerInspectionLineTable getAllDatabyLotNoGermination(String schedule_no,String production_lot_no);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SchedulerInspectionLineTable scheduleInspectionLineTable);

    @Query("delete from scheduler_line_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(production_lot_no) FROM scheduler_line_table WHERE schedule_no=:schedule_no and production_lot_no=:production_lot_no")
    int isDataExist(String schedule_no,String production_lot_no); //change arrival_plan_no to schedule_no


    @Query("SELECT COUNT(production_lot_no) FROM scheduler_line_table WHERE schedule_no=:scheduler_no and production_lot_no=:production_lot_no")
    boolean isDataExistOnComplete(String scheduler_no,String production_lot_no); //change arrival_plan_no to schedule_no


    // for complete  germination inspection...
    @Query("update scheduler_line_table set ins1_nav_sync=:ins1_nav_sync,inspection_1=:inspection_1,ins1_completed_on=:ins1_completed_on,ins1_sync_with_server=:ins1_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteGermination(int ins1_nav_sync, int inspection_1,String schedule_no,String production_lot_no,String ins1_completed_on,int ins1_sync_with_server);


    @Query("update scheduler_line_table set inspection_1=:inspection_1,nav_error_insp1_mesage=:nav_error_insp1_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspGermination(String scheduler_no,String production_lot_no,int inspection_1,String nav_error_insp1_mesage);



    //for complete vegitative inspection....
    @Query("update scheduler_line_table set ins3_nav_sync=:ins3_nav_sync , inspection_3=:inspection_3,ins3_completed_on=:ins3_completed_on,ins3_sync_with_server=:ins3_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteVegitative(int ins3_nav_sync, int inspection_3,String schedule_no,String production_lot_no,String ins3_completed_on,int ins3_sync_with_server);

    @Query("update scheduler_line_table set inspection_3=:inspection_3,nav_error_insp3_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspVegitative(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_3);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN vegitative_inpsection_table  vg  on  sl.production_lot_no=vg.production_lot_no and vg.syncWith_Api=1 and sl.inspection_3=0 and sl.ins3_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineVegitative();

    //for complete nicking inspection.............
    @Query("update scheduler_line_table set ins4_nav_sync=:ins4_nav_sync,inspection_4=:inspection_4,ins4_completed_on=:ins4_completed_on,ins4_sync_with_server=:ins4_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteNicking(int ins4_nav_sync, int inspection_4,String schedule_no,String production_lot_no,String ins4_completed_on,int ins4_sync_with_server);

    @Query("update scheduler_line_table set inspection_4=:inspection_4,nav_error_insp5_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspNicking(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_4);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN nicking_inspection_insert_table  ni  on  sl.production_lot_no=ni.production_lot_no and ni.sync_with_api_insp4=1 and sl.inspection_4=0 and sl.ins4_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineNicking();

    //todo for complete nicking2 inspection.....

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN nicking2_inspection_table  ni  on  sl.production_lot_no=ni.production_lot_no and ni.syncWithApi5=1 and sl.inspection_5=0 and sl.ins5_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineNicking2();

    @Query("update scheduler_line_table set ins5_nav_sync=:ins5_nav_sync,inspection_5=:inspection_5,ins5_completed_on=:ins5_completed_on,ins5_sync_with_server=:ins5_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteNicking2(int ins5_nav_sync, int inspection_5,String schedule_no,String production_lot_no,String ins5_completed_on,int ins5_sync_with_server);

    @Query("update scheduler_line_table set inspection_5=:inspection_5,nav_error_insp5_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspNicking2(String scheduler_no,String production_lot_no,String nav_mesage ,int inspection_5);

    //todo for complete seedling inspedction....
    @Query("update scheduler_line_table set ins2_nav_sync=:ins2_nav_sync,inspection_2=:inspection_2,ins2_completed_on=:ins2_completed_on,ins2_sync_with_server=:ins2_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteSeedling(int ins2_nav_sync, int inspection_2,String schedule_no,String production_lot_no,String ins2_completed_on,int ins2_sync_with_server);

    @Query("update scheduler_line_table set inspection_2=:inspection_2,nav_error_insp2_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderSeedling(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_2);


    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN seedling_inpection_table  si  on  sl.production_lot_no=si.production_lot_no and si.sync_with_api_ins2=1 and sl.inspection_2=0 and sl.ins2_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineSeedling();

    //todo for complete flowering inspection.............

    @Query("update scheduler_line_table set ins6_nav_sync=:ins6_nav_sync,inspection_6=:inspection_6,ins6_completed_on=:ins6_completed_on,ins6_sync_with_server=:ins6_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteFlowering(int ins6_nav_sync, int inspection_6,String schedule_no,String production_lot_no,String ins6_completed_on,int ins6_sync_with_server);

    @Query("update scheduler_line_table set inspection_6=:inspection_6,nav_error_insp6_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderFlowering(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_6);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN flowering_inspection_table  si  on  sl.production_lot_no=si.production_lot_no and si.syncwith_api6=1 and sl.inspection_6=0 and sl.ins6_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineFlowering();

    //todo for complete postFlowering....

    @Query("update scheduler_line_table set ins7_nav_sync=:ins7_nav_sync,inspection_7=:inspection_7,ins7_completed_on=:ins7_completed_on,ins7_sync_with_server=:ins7_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompletePostFlowering(int ins7_nav_sync, int inspection_7,String schedule_no,String production_lot_no,String ins7_completed_on,int ins7_sync_with_server);


    @Query("update scheduler_line_table set inspection_7=:inspection_7,nav_error_insp7_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
   int updateServerErrorDuringCompleteHeaderInspPostFlowering(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_7);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN post_flowering_inspection_table  si  on  sl.production_lot_no=si.production_lot_no and si.synWithApi7=1 and sl.inspection_7=0 and sl.ins7_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLinePostFlowering();

    //todo for complete Maturity Inspection....


    @Query("update scheduler_line_table set ins8_nav_sync=:ins8_nav_sync,inspection_8=:inspection_8,ins8_completed_on=:ins8_completed_on,ins8_sync_with_server=:ins8_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteMaturity(int ins8_nav_sync, int inspection_8,String schedule_no,String production_lot_no,String ins8_completed_on,int ins8_sync_with_server);


    @Query("update scheduler_line_table set inspection_8=:inspection_8,nav_error_insp8_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspMaturity(String scheduler_no,String production_lot_no,String nav_mesage,int inspection_8);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN maturity_inspection_table  si  on  sl.production_lot_no=si.production_lot_no and si.syncWithApi8=1 and sl.inspection_8=0 and sl.ins8_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineMaturityInsp();

    //todo for complete Harvesting Inspection.....

    @Query("update scheduler_line_table set ins9_nav_sync=:ins9_nav_sync,inspection_9=:inspection_9,ins9_completed_on=:ins9_completed_on,ins9_sync_with_server=:ins9_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteHarvesting(int ins9_nav_sync,int inspection_9,String schedule_no,String production_lot_no,String ins9_completed_on,int ins9_sync_with_server);


    @Query("update scheduler_line_table set inspection_9=:inspection_9,nav_error_insp9_message=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspHarvesting(String scheduler_no,String production_lot_no,int inspection_9,String nav_mesage);


    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN harvesting_inspection_table  si  on  sl.production_lot_no=si.production_lot_no and si.synWithApi9=1 and sl.inspection_9=0 and sl.ins9_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineHarvestingInsp();
    //todo for complete Qc inspection.....

    @Query("update scheduler_line_table set ins_qc_nav_sync=:ins_qc_nav_sync,inspection_qc=:inspection_qc,ins_qc_completed_on=:ins_qc_completed_on,Insqc_sync_with_server=:Insqc_sync_with_server where production_lot_no=:production_lot_no and schedule_no=:schedule_no")
    int updateOnServerCompleteQc(int ins_qc_nav_sync,int inspection_qc,String schedule_no,String production_lot_no,String ins_qc_completed_on,int Insqc_sync_with_server);


    @Query("update scheduler_line_table set inspection_qc=:inspection_qc,nav_error_inspqc_mesage=:nav_mesage where production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    int updateServerErrorDuringCompleteHeaderInspQc(String scheduler_no,String production_lot_no,int inspection_qc,String nav_mesage);

    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN qc_inspection_table  si  on  sl.production_lot_no=si.production_lot_no and si.syncwithQc=1 and sl.inspection_qc=0 and sl.insqc_sync_with_server=1" )
    List<SchedulerInspectionLineTable> getUnpostedCompleteLineQcInsp();

    //todo for germination .......
    @Query("SELECT * FROM scheduler_line_table sl INNER JOIN germination_inspection_table  gi  on  sl.production_lot_no=gi.production_lot_no and gi.sync_with_api=1 and sl.inspection_1=0 and sl.ins1_sync_with_server=1")
    List<SchedulerInspectionLineTable> getUnpostedCompleteLine();

    @Query("update scheduler_line_table set fsio_no=:fsio_no,organizer_code=:organizer_code,organizer_name=:organizer_name,grower_owner=:grower_owner,grower_land_owner_name=:grower_land_owner_name,grower_village=:grower_village ," +
            "grower_taluka_mandal=:grower_taluka_mandal,grower_district=:grower_district,grower_city=:grower_city,supervisor_name=:supervisor_name,crop_code=:crop_code, variety_code=:variety_code,item_product_group_code=:item_product_group_code,"+
            "item_class_of_seeds=:item_class_of_seeds,item_crop_type=:item_crop_type,item_name=:item_name,revised_yield_raw=:revised_yield_raw,land_lease=:land_lease,unit_of_measure_code=:unit_of_measure_code,sowing_date_male=:sowing_date_male,sowing_date_female=:sowing_date_female,"+
            "sowing_date_other=:sowing_date_other,pld_marked=:pld_marked WHERE production_lot_no=:production_lot and schedule_no=:scheduler_no")

    int updateSchedulerLine(String fsio_no,String organizer_code, String organizer_name, String grower_owner, String grower_land_owner_name, String grower_village, String grower_taluka_mandal,
           String grower_district, String grower_city, String supervisor_name, String crop_code, String variety_code, String item_product_group_code,
           String item_class_of_seeds, String item_crop_type, String item_name, String revised_yield_raw, String land_lease, String unit_of_measure_code,
           String sowing_date_male, String sowing_date_female, String sowing_date_other,String pld_marked,String production_lot,String scheduler_no);


    @Query("update scheduler_line_table set inspection_1=:inspection1,inspection_2=:inspection2,inspection_3=:inspection3,inspection_4=:inspection4,inspection_5=:inspection5,inspection_6=:inspection6,inspection_7=:inspection7," +
            "inspection_8=:inspection8,inspection_9=:inspection9,inspection_qc=:insepction_qc,"+
            "ins1_nav_sync=:nav_sync1,ins2_nav_sync=:nav_sync2,ins3_nav_sync=:nav_sync3,ins4_nav_sync=:nav_sync4,ins5_nav_sync=:nav_sync5,ins6_nav_sync=:nav_sync6,ins7_nav_sync=:nav_sync7," +
            "ins8_nav_sync=:nav_sync8,insp9_nav_sync=:nav_sync9,ins_qc_nav_sync=:nav_sync_qc,"+
            "ins1_completed_on=:insp1_comp_on,ins2_completed_on=:insp2_comp_on,ins3_completed_on=:insp3_comp_on,ins4_completed_on=:insp4_comp_on,ins5_completed_on=:insp5_comp_on,ins6_completed_on=:insp6_comp_on,ins7_completed_on=:insp7_comp_on," +
            "ins8_completed_on=:insp8_comp_on,ins9_completed_on=:insp9_comp_on,ins_qc_completed_on=:insp_qc_comp_on"+
            " WHERE production_lot_no=:production_lot_no")
    int updateInspectionOnLine(int inspection1,int inspection2,int inspection3,int inspection4,int inspection5,int inspection6,int inspection7,int inspection8,int inspection9,int insepction_qc,
                               int nav_sync1,int nav_sync2,int nav_sync3,int nav_sync4,int nav_sync5,int nav_sync6,int nav_sync7,
                               int nav_sync8,int nav_sync9,int nav_sync_qc,String production_lot_no,
                               String insp1_comp_on,String insp2_comp_on,String insp3_comp_on,String insp4_comp_on,String insp5_comp_on,
                               String insp6_comp_on,String insp7_comp_on,String insp8_comp_on,String insp9_comp_on,String insp_qc_comp_on);


    //todo for germination complete update

    @Query("SELECT COUNT(production_lot_no) FROM scheduler_line_table WHERE schedule_no=:scheduler_no and production_lot_no=:production_lot_no")
    int  isDataExistOfflineStatusOnSchedulerLine(String scheduler_no,String production_lot_no);


    @Query("SELECT * FROM  scheduler_line_table WHERE ins1_sync_with_server=:ins1_sync_with_server  and ins2_sync_with_server=:ins2_sync_with_server and ins3_sync_with_server=:ins3_sync_with_server and ins4_sync_with_server=:ins4_sync_with_server and ins5_sync_with_server=:ins5_sync_with_server and ins6_sync_with_server=:ins6_sync_with_server and ins7_sync_with_server=:ins7_sync_with_server and ins8_sync_with_server=:ins8_sync_with_server and ins9_sync_with_server=:ins9_sync_with_server and  production_lot_no=:production_lot_no and schedule_no=:scheduler_no")
    SchedulerInspectionLineTable getAllLocalStatus(String scheduler_no,String production_lot_no,int ins1_sync_with_server,int ins2_sync_with_server,int ins3_sync_with_server, int ins4_sync_with_server,int ins5_sync_with_server,int ins6_sync_with_server,int ins7_sync_with_server,int ins8_sync_with_server,int ins9_sync_with_server);



@Query("SELECT (SELECT count(1) FROM scheduler_line_table sl where sl.ins1_sync_with_server=1 and sl.inspection_1=0) as ins1_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins2_sync_with_server=1 and sl.inspection_2=0) as ins2_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins3_sync_with_server=1 and sl.inspection_3=0) as ins3_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins4_sync_with_server=1 and sl.inspection_4=0) as ins4_offline ," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins5_sync_with_server=1 and sl.inspection_5=0) as ins5_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins6_sync_with_server=1 and sl.inspection_6=0) as ins6_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins7_sync_with_server=1 and sl.inspection_7=0) as ins7_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins8_sync_with_server=1 and sl.inspection_8=0) as ins8_offline," +
        "(SELECT count(1) from scheduler_line_table sl where sl.ins9_sync_with_server=1 and sl.inspection_9=0) as ins9_offline ," +
        "(SELECT count(1) from scheduler_line_table sl where sl.Insqc_sync_with_server=1 and sl.inspection_qc=0) as insqc_offline")
        List<OfflineStatusUpdateModel> getTableUpdate();

}
