package com.example.pristineseed.DataBaseRepository.Scheduler;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;

@Entity(tableName = "scheduler_line_table", primaryKeys = {"schedule_no", "production_lot_no"})//,primaryKeys = {"production_lot_no"}
public class SchedulerInspectionLineTable {
 /*   public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int android_id;*/

    @NonNull
   @ColumnInfo(name = "schedule_no")
     private String schedule_no;

    @ColumnInfo(name = "arrival_plan_no")
    private String arrival_plan_no;

    public String getSchedule_no() {
        return schedule_no;
    }

    public void setSchedule_no(String schedule_no) {
        this.schedule_no = schedule_no;
    }

    public String getArrival_plan_no() {
        return arrival_plan_no;
    }

    public void setArrival_plan_no(String arrival_plan_no) {
        this.arrival_plan_no = arrival_plan_no;
    }

    public String getProduction_lot_no() {
        return production_lot_no;
    }

    public void setProduction_lot_no(String production_lot_no) {
        this.production_lot_no = production_lot_no;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getFsio_no() {
        return fsio_no;
    }

    public void setFsio_no(String fsio_no) {
        this.fsio_no = fsio_no;
    }

    public String getOrganizer_code() {
        return organizer_code;
    }

    public void setOrganizer_code(String organizer_code) {
        this.organizer_code = organizer_code;
    }

    public String getOrganizer_name() {
        return organizer_name;
    }

    public void setOrganizer_name(String organizer_name) {
        this.organizer_name = organizer_name;
    }

    public String getGrower_owner() {
        return grower_owner;
    }

    public void setGrower_owner(String grower_owner) {
        this.grower_owner = grower_owner;
    }

    public String getGrower_land_owner_name() {
        return grower_land_owner_name;
    }

    public void setGrower_land_owner_name(String grower_land_owner_name) {
        this.grower_land_owner_name = grower_land_owner_name;
    }

    public String getGrower_village() {
        return grower_village;
    }

    public void setGrower_village(String grower_village) {
        this.grower_village = grower_village;
    }

    public String getGrower_taluka_mandal() {
        return grower_taluka_mandal;
    }

    public void setGrower_taluka_mandal(String grower_taluka_mandal) {
        this.grower_taluka_mandal = grower_taluka_mandal;
    }

    public String getGrower_district() {
        return grower_district;
    }

    public void setGrower_district(String grower_district) {
        this.grower_district = grower_district;
    }

    public String getGrower_city() {
        return grower_city;
    }

    public void setGrower_city(String grower_city) {
        this.grower_city = grower_city;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public void setSupervisor_name(String supervisor_name) {
        this.supervisor_name = supervisor_name;
    }

    public String getCrop_code() {
        return crop_code;
    }

    public void setCrop_code(String crop_code) {
        this.crop_code = crop_code;
    }

    public String getVariety_code() {
        return variety_code;
    }

    public void setVariety_code(String variety_code) {
        this.variety_code = variety_code;
    }

    public String getItem_product_group_code() {
        return item_product_group_code;
    }

    public void setItem_product_group_code(String item_product_group_code) {
        this.item_product_group_code = item_product_group_code;
    }

    public String getItem_class_of_seeds() {
        return item_class_of_seeds;
    }

    public void setItem_class_of_seeds(String item_class_of_seeds) {
        this.item_class_of_seeds = item_class_of_seeds;
    }

    public String getItem_crop_type() {
        return item_crop_type;
    }

    public void setItem_crop_type(String item_crop_type) {
        this.item_crop_type = item_crop_type;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getRevised_yield_raw() {
        return revised_yield_raw;
    }

    public void setRevised_yield_raw(String revised_yield_raw) {
        this.revised_yield_raw = revised_yield_raw;
    }

    public String getLand_lease() {
        return land_lease;
    }

    public void setLand_lease(String land_lease) {
        this.land_lease = land_lease;
    }

    public String getUnit_of_measure_code() {
        return unit_of_measure_code;
    }

    public void setUnit_of_measure_code(String unit_of_measure_code) {
        this.unit_of_measure_code = unit_of_measure_code;
    }

    public String getSowing_date_male() {
        return sowing_date_male;
    }

    public void setSowing_date_male(String sowing_date_male) {
        this.sowing_date_male = sowing_date_male;
    }

    public String getSowing_date_female() {
        return sowing_date_female;
    }

    public void setSowing_date_female(String sowing_date_female) {
        this.sowing_date_female = sowing_date_female;
    }

    public String getSowing_date_other() {
        return sowing_date_other;
    }

    public void setSowing_date_other(String sowing_date_other) {
        this.sowing_date_other = sowing_date_other;
    }

    public String getPld_marked() {
        return pld_marked;
    }

    public void setPld_marked(String pld_marked) {
        this.pld_marked = pld_marked;
    }

    public int getInspection_1() {
        return inspection_1;
    }

    public void setInspection_1(int inspection_1) {
        this.inspection_1 = inspection_1;
    }

    public String getIns1_completed_on() {
        return ins1_completed_on;
    }

    public void setIns1_completed_on(String ins1_completed_on) {
        this.ins1_completed_on = ins1_completed_on;
    }

    public int getIns1_nav_sync() {
        return ins1_nav_sync;
    }

    public void setIns1_nav_sync(int ins1_nav_sync) {
        this.ins1_nav_sync = ins1_nav_sync;
    }

    public int getInspection_2() {
        return inspection_2;
    }

    public void setInspection_2(int inspection_2) {
        this.inspection_2 = inspection_2;
    }

    public String getIns2_completed_on() {
        return ins2_completed_on;
    }

    public void setIns2_completed_on(String ins2_completed_on) {
        this.ins2_completed_on = ins2_completed_on;
    }

    public int getIns2_nav_sync() {
        return ins2_nav_sync;
    }

    public void setIns2_nav_sync(int ins2_nav_sync) {
        this.ins2_nav_sync = ins2_nav_sync;
    }

    public int getInspection_3() {
        return inspection_3;
    }

    public void setInspection_3(int inspection_3) {
        this.inspection_3 = inspection_3;
    }

    public String getIns3_completed_on() {
        return ins3_completed_on;
    }

    public void setIns3_completed_on(String ins3_completed_on) {
        this.ins3_completed_on = ins3_completed_on;
    }

    public int getIns3_nav_sync() {
        return ins3_nav_sync;
    }

    public void setIns3_nav_sync(int ins3_nav_sync) {
        this.ins3_nav_sync = ins3_nav_sync;
    }

    public int getInspection_4() {
        return inspection_4;
    }

    public void setInspection_4(int inspection_4) {
        this.inspection_4 = inspection_4;
    }

    public String getIns4_completed_on() {
        return ins4_completed_on;
    }

    public void setIns4_completed_on(String ins4_completed_on) {
        this.ins4_completed_on = ins4_completed_on;
    }

    public int getIns4_nav_sync() {
        return ins4_nav_sync;
    }

    public void setIns4_nav_sync(int ins4_nav_sync) {
        this.ins4_nav_sync = ins4_nav_sync;
    }

    public int getInspection_5() {
        return inspection_5;
    }

    public void setInspection_5(int inspection_5) {
        this.inspection_5 = inspection_5;
    }

    public String getIns5_completed_on() {
        return ins5_completed_on;
    }

    public void setIns5_completed_on(String ins5_completed_on) {
        this.ins5_completed_on = ins5_completed_on;
    }

    public int getIns5_nav_sync() {
        return ins5_nav_sync;
    }

    public void setIns5_nav_sync(int ins5_nav_sync) {
        this.ins5_nav_sync = ins5_nav_sync;
    }

    public int getInspection_6() {
        return inspection_6;
    }

    public void setInspection_6(int inspection_6) {
        this.inspection_6 = inspection_6;
    }

    public String getIns6_completed_on() {
        return ins6_completed_on;
    }

    public void setIns6_completed_on(String ins6_completed_on) {
        this.ins6_completed_on = ins6_completed_on;
    }

    public int getIns6_nav_sync() {
        return ins6_nav_sync;
    }

    public void setIns6_nav_sync(int ins6_nav_sync) {
        this.ins6_nav_sync = ins6_nav_sync;
    }

    public int getInspection_7() {
        return inspection_7;
    }

    public void setInspection_7(int inspection_7) {
        this.inspection_7 = inspection_7;
    }

    public String getIns7_completed_on() {
        return ins7_completed_on;
    }

    public void setIns7_completed_on(String ins7_completed_on) {
        this.ins7_completed_on = ins7_completed_on;
    }

    public int getIns7_nav_sync() {
        return ins7_nav_sync;
    }

    public void setIns7_nav_sync(int ins7_nav_sync) {
        this.ins7_nav_sync = ins7_nav_sync;
    }

    public int getInspection_8() {
        return inspection_8;
    }

    public void setInspection_8(int inspection_8) {
        this.inspection_8 = inspection_8;
    }

    public String getIns8_completed_on() {
        return ins8_completed_on;
    }

    public void setIns8_completed_on(String ins8_completed_on) {
        this.ins8_completed_on = ins8_completed_on;
    }

    public int getIns8_nav_sync() {
        return ins8_nav_sync;
    }

    public void setIns8_nav_sync(int ins8_nav_sync) {
        this.ins8_nav_sync = ins8_nav_sync;
    }

    public int getInspection_qc() {
        return inspection_qc;
    }

    public void setInspection_qc(int inspection_qc) {
        this.inspection_qc = inspection_qc;
    }

    public String getIns_qc_completed_on() {
        return ins_qc_completed_on;
    }

    public void setIns_qc_completed_on(String ins_qc_completed_on) {
        this.ins_qc_completed_on = ins_qc_completed_on;
    }

    public int getIns_qc_nav_sync() {
        return ins_qc_nav_sync;
    }

    public void setIns_qc_nav_sync(int ins_qc_nav_sync) {
        this.ins_qc_nav_sync = ins_qc_nav_sync;
    }

    @NonNull
    @ColumnInfo(name = "production_lot_no")
    private String production_lot_no;
    @ColumnInfo(name = "line_no")
    private String line_no;
    @ColumnInfo(name = "fsio_no")
    private String fsio_no;
    @ColumnInfo(name = "organizer_code")
    private String organizer_code;
    @ColumnInfo(name = "organizer_name")
    private String organizer_name;
    @ColumnInfo(name = "grower_owner")
    private String grower_owner;
    @ColumnInfo(name = "grower_land_owner_name")
    private String grower_land_owner_name;
    @ColumnInfo(name = "grower_village")
    private String grower_village;
    @ColumnInfo(name = "grower_taluka_mandal")
    private String grower_taluka_mandal;
    @ColumnInfo(name = "grower_district")
    private String grower_district;
    @ColumnInfo(name = "grower_city")
    private String grower_city;
    @ColumnInfo(name = "supervisor_name")
    private String supervisor_name;
    @ColumnInfo(name = "crop_code")
    private String crop_code;
    @ColumnInfo(name = "variety_code")
    private String variety_code;
    @ColumnInfo(name = "item_product_group_code")
    private String item_product_group_code;
    @ColumnInfo(name = "item_class_of_seeds")
    private String item_class_of_seeds;
    @ColumnInfo(name = "item_crop_type")
    private String item_crop_type;
    @ColumnInfo(name = "item_name")
    private String item_name;
    @ColumnInfo(name = "revised_yield_raw")
    private String revised_yield_raw;
    @ColumnInfo(name = "land_lease")
    private String land_lease;
    @ColumnInfo(name = "unit_of_measure_code")
    private String unit_of_measure_code;
    @ColumnInfo(name = "sowing_date_male")
    private String sowing_date_male;
    @ColumnInfo(name = "sowing_date_female")
    private String sowing_date_female;
    @ColumnInfo(name = "sowing_date_other")
    private String sowing_date_other;

    @ColumnInfo(name = "pld_marked")
    private String pld_marked;

    @ColumnInfo(name = "inspection_1")
    private int inspection_1;
    @ColumnInfo(name = "ins1_completed_on")
    private String ins1_completed_on;
    @ColumnInfo(name = "ins1_nav_sync")
    private int ins1_nav_sync;
    @ColumnInfo(name = "inspection_2")
    private int inspection_2;
    @ColumnInfo(name = "ins2_completed_on")
    private String ins2_completed_on;
    @ColumnInfo(name = "ins2_nav_sync")
    private int ins2_nav_sync;
    @ColumnInfo(name = "inspection_3")
    private int inspection_3;
    @ColumnInfo(name = "ins3_completed_on")
    private String ins3_completed_on;
    @ColumnInfo(name = "ins3_nav_sync")
    private int ins3_nav_sync;
    @ColumnInfo(name = "inspection_4")
    private int inspection_4;
    @ColumnInfo(name = "ins4_completed_on")
    private String ins4_completed_on;
    @ColumnInfo(name = "ins4_nav_sync")
    private int ins4_nav_sync;
    @ColumnInfo(name = "inspection_5")
    private int inspection_5;
    @ColumnInfo(name = "ins5_completed_on")
    private String ins5_completed_on;
    @ColumnInfo(name = "ins5_nav_sync")
    private int ins5_nav_sync;
    @ColumnInfo(name = "inspection_6")
    private int inspection_6;
    @ColumnInfo(name = "ins6_completed_on")
    private String ins6_completed_on;
    @ColumnInfo(name = "ins6_nav_sync")
    private int ins6_nav_sync;
    @ColumnInfo(name = "inspection_7")
    private int inspection_7;
    @ColumnInfo(name = "ins7_completed_on")
    private String ins7_completed_on;
    @ColumnInfo(name = "ins7_nav_sync")
    private int ins7_nav_sync;
    @ColumnInfo(name = "inspection_8")
    private int inspection_8;
    @ColumnInfo(name = "ins8_completed_on")
    private String ins8_completed_on;
    @ColumnInfo(name = "ins8_nav_sync")
    private int ins8_nav_sync;
    @ColumnInfo(name = "inspection_qc")
    private int inspection_qc;

    @ColumnInfo(name = "inspection_9")
    private int inspection_9;

    @ColumnInfo(name = "ins_qc_completed_on")
    private String ins_qc_completed_on;
    @ColumnInfo(name = "ins_qc_nav_sync")
    private int ins_qc_nav_sync;


    public int getInsp9_nav_sync() {
        return insp9_nav_sync;
    }

    public void setInsp9_nav_sync(int insp9_nav_sync) {
        this.insp9_nav_sync = insp9_nav_sync;
    }

    public String getNav_error_insp9_message() {
        return nav_error_insp9_message;
    }

    public void setNav_error_insp9_message(String nav_error_insp9_message) {
        this.nav_error_insp9_message = nav_error_insp9_message;
    }

    @ColumnInfo(name = "insp9_nav_sync")
    private int insp9_nav_sync;

    @ColumnInfo(name = "nav_error_insp9_message")
    private String nav_error_insp9_message;

    public String getNav_error_insp1_mesage() {
        return nav_error_insp1_mesage;
    }

    public void setNav_error_insp1_mesage(String nav_error_insp1_mesage) {
        this.nav_error_insp1_mesage = nav_error_insp1_mesage;
    }

    @ColumnInfo(name = "nav_error_insp1_mesage")
    private String nav_error_insp1_mesage;

    public int getIns1_sync_with_server() {
        return ins1_sync_with_server;
    }

    public void setIns1_sync_with_server(int ins1_sync_with_server) {
        this.ins1_sync_with_server = ins1_sync_with_server;
    }

    @ColumnInfo(name = "ins1_sync_with_server")
    private int ins1_sync_with_server;

    public int getIns3_sync_with_server() {
        return ins3_sync_with_server;
    }

    public void setIns3_sync_with_server(int ins3_sync_with_server) {
        this.ins3_sync_with_server = ins3_sync_with_server;
    }

    @ColumnInfo(name = "ins3_sync_with_server")
    private int ins3_sync_with_server;

    @ColumnInfo(name = "ins2_sync_with_server")
    private int ins2_sync_with_server;

    @ColumnInfo(name = "ins4_sync_with_server")
    private int ins4_sync_with_server;

    @ColumnInfo(name = "ins5_sync_with_server")
    private int ins5_sync_with_server;

 /*   public String getNav_error_insp1_mesage() {
        return nav_error_insp1_mesage;
    }

    public void setNav_error_insp1_mesage(String nav_error_insp1_mesage) {
        this.nav_error_insp1_mesage = nav_error_insp1_mesage;
    }*/

    public int getIns2_sync_with_server() {
        return ins2_sync_with_server;
    }

    public void setIns2_sync_with_server(int ins2_sync_with_server) {
        this.ins2_sync_with_server = ins2_sync_with_server;
    }

    public int getIns4_sync_with_server() {
        return ins4_sync_with_server;
    }

    public void setIns4_sync_with_server(int ins4_sync_with_server) {
        this.ins4_sync_with_server = ins4_sync_with_server;
    }

    public int getIns5_sync_with_server() {
        return ins5_sync_with_server;
    }

    public void setIns5_sync_with_server(int ins5_sync_with_server) {
        this.ins5_sync_with_server = ins5_sync_with_server;
    }

    public String getNav_error_insp2_mesage() {
        return nav_error_insp2_mesage;
    }

    public void setNav_error_insp2_mesage(String nav_error_insp2_mesage) {
        this.nav_error_insp2_mesage = nav_error_insp2_mesage;
    }

    public String getNav_error_insp3_mesage() {
        return nav_error_insp3_mesage;
    }

    public void setNav_error_insp3_mesage(String nav_error_insp3_mesage) {
        this.nav_error_insp3_mesage = nav_error_insp3_mesage;
    }

    public String getNav_error_insp4_mesage() {
        return nav_error_insp4_mesage;
    }

    public void setNav_error_insp4_mesage(String nav_error_insp4_mesage) {
        this.nav_error_insp4_mesage = nav_error_insp4_mesage;
    }

    public String getNav_error_insp5_mesage() {
        return nav_error_insp5_mesage;
    }

    public void setNav_error_insp5_mesage(String nav_error_insp5_mesage) {
        this.nav_error_insp5_mesage = nav_error_insp5_mesage;
    }

    @ColumnInfo(name = "nav_error_insp2_mesage")
    private String nav_error_insp2_mesage;

    @ColumnInfo(name = "nav_error_insp3_mesage")
    private String nav_error_insp3_mesage;

    @ColumnInfo(name = "nav_error_insp4_mesage")
    private String nav_error_insp4_mesage;


    @ColumnInfo(name = "nav_error_insp5_mesage")
    private String nav_error_insp5_mesage;


    public String getNav_error_insp6_mesage() {
        return nav_error_insp6_mesage;
    }

    public void setNav_error_insp6_mesage(String nav_error_insp6_mesage) {
        this.nav_error_insp6_mesage = nav_error_insp6_mesage;
    }

    public int getIns6_sync_with_server() {
        return ins6_sync_with_server;
    }

    public void setIns6_sync_with_server(int ins6_sync_with_server) {
        this.ins6_sync_with_server = ins6_sync_with_server;
    }

    @ColumnInfo(name = "nav_error_insp6_mesage")
    private String nav_error_insp6_mesage;

    @ColumnInfo(name = "ins6_sync_with_server")
    private int ins6_sync_with_server;

    @ColumnInfo(name = "ins7_sync_with_server")
    private int ins7_sync_with_server;

    public int getIns7_sync_with_server() {
        return ins7_sync_with_server;
    }

    public void setIns7_sync_with_server(int ins7_sync_with_server) {
        this.ins7_sync_with_server = ins7_sync_with_server;
    }

    public String getNav_error_insp7_mesage() {
        return nav_error_insp7_mesage;
    }

    public void setNav_error_insp7_mesage(String nav_error_insp7_mesage) {
        this.nav_error_insp7_mesage = nav_error_insp7_mesage;
    }

    public int getIns8_sync_with_server() {
        return ins8_sync_with_server;
    }

    public void setIns8_sync_with_server(int ins8_sync_with_server) {
        this.ins8_sync_with_server = ins8_sync_with_server;
    }

    public String getNav_error_insp8_mesage() {
        return nav_error_insp8_mesage;
    }

    public void setNav_error_insp8_mesage(String nav_error_insp8_mesage) {
        this.nav_error_insp8_mesage = nav_error_insp8_mesage;
    }

    @ColumnInfo(name = "nav_error_insp7_mesage")
    private String nav_error_insp7_mesage;

    @ColumnInfo(name = "ins8_sync_with_server")
    private int ins8_sync_with_server;

    @ColumnInfo(name = "nav_error_insp8_mesage")
    private String nav_error_insp8_mesage;

    public int getIns9_sync_with_server() {
        return ins9_sync_with_server;
    }

    public void setIns9_sync_with_server(int ins9_sync_with_server) {
        this.ins9_sync_with_server = ins9_sync_with_server;
    }

    @ColumnInfo(name = "ins9_sync_with_server")
    private int ins9_sync_with_server;


    public int getInspection_9() {
        return inspection_9;
    }

    public void setInspection_9(int inspection_9) {
        this.inspection_9 = inspection_9;
    }

    public String getIns9_completed_on() {
        return ins9_completed_on;
    }

    public void setIns9_completed_on(String ins9_completed_on) {
        this.ins9_completed_on = ins9_completed_on;
    }

    public int getIns9_nav_sync() {
        return ins9_nav_sync;
    }

    public void setIns9_nav_sync(int ins9_nav_sync) {
        this.ins9_nav_sync = ins9_nav_sync;
    }

    @ColumnInfo(name = "ins9_completed_on")
    public String ins9_completed_on;
    @ColumnInfo(name = "ins9_nav_sync")
    public int ins9_nav_sync;

    public int getInsqc_sync_with_server() {
        return Insqc_sync_with_server;
    }

    public void setInsqc_sync_with_server(int insqc_sync_with_server) {
        Insqc_sync_with_server = insqc_sync_with_server;
    }

    @ColumnInfo(name = "Insqc_sync_with_server")
    public int Insqc_sync_with_server;


    public String getNav_error_inspqc_mesage() {
        return nav_error_inspqc_mesage;
    }

    public void setNav_error_inspqc_mesage(String nav_error_inspqc_mesage) {
        this.nav_error_inspqc_mesage = nav_error_inspqc_mesage;
    }

    @ColumnInfo(name = "nav_error_inspqc_mesage")
    private String nav_error_inspqc_mesage;


    public static SchedulerInspectionLineTable getLineDataFormate(SchedulerModel.SchedulerLines passmodel) {
        SchedulerInspectionLineTable scheduleInspectionLineTable = new SchedulerInspectionLineTable();

        scheduleInspectionLineTable.setSchedule_no(passmodel.schedule_no);
        scheduleInspectionLineTable.setArrival_plan_no(passmodel.arrival_plan_no);
        scheduleInspectionLineTable.setProduction_lot_no(passmodel.production_lot_no);
        scheduleInspectionLineTable.setLine_no(passmodel.line_no);
        scheduleInspectionLineTable.setFsio_no(passmodel.fsio_no);
        scheduleInspectionLineTable.setOrganizer_code(passmodel.organizer_code);
        scheduleInspectionLineTable.setOrganizer_name(passmodel.organizer_name);
        scheduleInspectionLineTable.setGrower_owner(passmodel.grower_owner);
        scheduleInspectionLineTable.setGrower_land_owner_name(passmodel.grower_land_owner_name);

        scheduleInspectionLineTable.setGrower_village(passmodel.grower_village);

        scheduleInspectionLineTable.setGrower_taluka_mandal(passmodel.grower_taluka_mandal);

        scheduleInspectionLineTable.setGrower_district(passmodel.grower_district);
        scheduleInspectionLineTable.setGrower_city(passmodel.grower_city);

        scheduleInspectionLineTable.setSupervisor_name(passmodel.supervisor_name);
        scheduleInspectionLineTable.setCrop_code(passmodel.crop_code);
        scheduleInspectionLineTable.setVariety_code(passmodel.variety_code);
        scheduleInspectionLineTable.setItem_product_group_code(passmodel.item_product_group_code);
        scheduleInspectionLineTable.setItem_class_of_seeds(passmodel.item_class_of_seeds);
        scheduleInspectionLineTable.setItem_crop_type(passmodel.item_crop_type);
        scheduleInspectionLineTable.setItem_name(passmodel.item_name);
        scheduleInspectionLineTable.setRevised_yield_raw(passmodel.revised_yield_raw);
        scheduleInspectionLineTable.setLand_lease(passmodel.land_lease);
        scheduleInspectionLineTable.setSowing_date_male( DateTimeUtilsCustome.getDateDDMMYYY(passmodel.sowing_date_male));
        scheduleInspectionLineTable.setSowing_date_female( DateTimeUtilsCustome.getDateDDMMYYY(passmodel.sowing_date_female));
        scheduleInspectionLineTable.setSowing_date_other( DateTimeUtilsCustome.getDateDDMMYYY(passmodel.sowing_date_other));
        scheduleInspectionLineTable.setUnit_of_measure_code(passmodel.unit_of_measure_code);
        scheduleInspectionLineTable.setPld_marked(passmodel.pld_marked);
        scheduleInspectionLineTable.setInspection_1(passmodel.inspection_1);
        scheduleInspectionLineTable.setIns1_completed_on(passmodel.ins1_completed_on);
        scheduleInspectionLineTable.setIns1_nav_sync(passmodel.ins1_nav_sync);
        scheduleInspectionLineTable.setInspection_2(passmodel.inspection_2);
        scheduleInspectionLineTable.setIns2_completed_on(passmodel.ins2_completed_on);
        scheduleInspectionLineTable.setIns2_nav_sync(passmodel.ins2_nav_sync);
        scheduleInspectionLineTable.setInspection_3(passmodel.inspection_3);
        scheduleInspectionLineTable.setIns3_completed_on(passmodel.ins3_completed_on);
        scheduleInspectionLineTable.setIns3_nav_sync(passmodel.ins3_nav_sync);
        scheduleInspectionLineTable.setInspection_4(passmodel.inspection_4);
        scheduleInspectionLineTable.setIns4_completed_on(passmodel.ins4_completed_on);
        scheduleInspectionLineTable.setIns4_nav_sync(passmodel.ins4_nav_sync);
        scheduleInspectionLineTable.setInspection_5(passmodel.inspection_5);
        scheduleInspectionLineTable.setIns5_completed_on(passmodel.ins5_completed_on);
        scheduleInspectionLineTable.setIns5_nav_sync(passmodel.ins5_nav_sync);
        scheduleInspectionLineTable.setInspection_6(passmodel.inspection_6);
        scheduleInspectionLineTable.setIns6_completed_on(passmodel.ins6_completed_on);
        scheduleInspectionLineTable.setIns6_nav_sync(passmodel.ins6_nav_sync);
        scheduleInspectionLineTable.setInspection_7(passmodel.inspection_7);
        scheduleInspectionLineTable.setIns7_completed_on(passmodel.ins7_completed_on);
        scheduleInspectionLineTable.setIns_qc_nav_sync(passmodel.ins7_nav_sync);
        scheduleInspectionLineTable.setInspection_8(passmodel.inspection_8);
        scheduleInspectionLineTable.setIns8_completed_on(passmodel.ins8_completed_on);
        scheduleInspectionLineTable.setIns8_nav_sync(passmodel.ins8_nav_sync);
        scheduleInspectionLineTable.setInspection_qc(passmodel.inspection_qc);
        scheduleInspectionLineTable.setIns_qc_completed_on(passmodel.ins_qc_completed_on);
        scheduleInspectionLineTable.setIns_qc_nav_sync(passmodel.ins_qc_nav_sync);
        scheduleInspectionLineTable.setInspection_9(passmodel.inspection_9);
        scheduleInspectionLineTable.setIns9_completed_on(passmodel.ins9_completed_on);
        scheduleInspectionLineTable.setIns9_nav_sync(passmodel.ins9_nav_sync);
        scheduleInspectionLineTable.setIns1_sync_with_server(passmodel.ins1_sync_with_server);
        scheduleInspectionLineTable.setIns2_sync_with_server(passmodel.ins2_sync_with_server);
        scheduleInspectionLineTable.setIns3_sync_with_server(passmodel.ins3_sync_with_server);
        scheduleInspectionLineTable.setIns4_sync_with_server(passmodel.ins4_sync_with_server);
        scheduleInspectionLineTable.setIns5_sync_with_server(passmodel.ins5_sync_with_server);
        scheduleInspectionLineTable.setIns6_sync_with_server(passmodel.ins6_sync_with_server);
        scheduleInspectionLineTable.setIns7_sync_with_server(passmodel.ins7_sync_with_server);
        scheduleInspectionLineTable.setIns8_sync_with_server(passmodel.ins8_sync_with_server);
        scheduleInspectionLineTable.setIns9_sync_with_server(passmodel.ins9_sync_with_server);

        return scheduleInspectionLineTable;
    }

    public static SchedulerModel.SchedulerLines getServerTypeFormate(SchedulerModel scheduleInspectionModel, SchedulerInspectionLineTable passData) {
        SchedulerModel.SchedulerLines scheduleInspectionLine = scheduleInspectionModel.new SchedulerLines();
        scheduleInspectionLine.schedule_no=passData.getSchedule_no();
        scheduleInspectionLine.arrival_plan_no=passData.getArrival_plan_no() ;
        scheduleInspectionLine.production_lot_no=passData.getProduction_lot_no() ;
        scheduleInspectionLine.line_no=passData.getLine_no() ;
        scheduleInspectionLine.fsio_no=passData.getFsio_no();
        scheduleInspectionLine.organizer_code=passData.getOrganizer_code();
        scheduleInspectionLine.organizer_name=passData.getOrganizer_name();
        scheduleInspectionLine.grower_owner=passData.getGrower_owner();
        scheduleInspectionLine.grower_land_owner_name=passData.getGrower_land_owner_name();
        scheduleInspectionLine.grower_village=passData.getGrower_village();
        scheduleInspectionLine.grower_taluka_mandal=passData.getGrower_taluka_mandal();
        scheduleInspectionLine.grower_district=passData.getGrower_district();
        scheduleInspectionLine.grower_city=passData.getGrower_city();
        scheduleInspectionLine.supervisor_name=passData.getSupervisor_name();
        scheduleInspectionLine.crop_code=passData.getCrop_code();
        scheduleInspectionLine.variety_code=passData.getVariety_code();
        scheduleInspectionLine.item_product_group_code=passData.getItem_product_group_code();
        scheduleInspectionLine.item_class_of_seeds=passData.getItem_class_of_seeds();
        scheduleInspectionLine.item_crop_type=passData.getItem_crop_type();
        scheduleInspectionLine.item_name=passData.getItem_name();
        scheduleInspectionLine.revised_yield_raw=passData.getRevised_yield_raw();
        scheduleInspectionLine.land_lease=passData.getLand_lease();
        scheduleInspectionLine.unit_of_measure_code=passData.getUnit_of_measure_code();
        scheduleInspectionLine.sowing_date_male=passData.getSowing_date_male();
        scheduleInspectionLine.sowing_date_female=passData.getSowing_date_female();
        scheduleInspectionLine.sowing_date_other=passData.getSowing_date_other();
        scheduleInspectionLine.pld_marked=passData.getPld_marked();
        scheduleInspectionLine.inspection_1=passData.getInspection_1();
        scheduleInspectionLine.ins1_completed_on=passData.getIns1_completed_on();
        scheduleInspectionLine.ins1_nav_sync=passData.getIns1_nav_sync();
        scheduleInspectionLine.inspection_2=passData.getInspection_2();
        scheduleInspectionLine.ins2_completed_on=passData.getIns2_completed_on();
        scheduleInspectionLine.ins2_nav_sync=passData.getIns2_nav_sync();
        scheduleInspectionLine.inspection_3=passData.getInspection_3();
        scheduleInspectionLine.ins3_completed_on=passData.getIns3_completed_on();
        scheduleInspectionLine.ins3_nav_sync=passData.getIns3_nav_sync();
        scheduleInspectionLine.inspection_4=passData.getInspection_4();
        scheduleInspectionLine.ins4_completed_on=passData.getIns4_completed_on();
        scheduleInspectionLine.ins4_nav_sync=passData.getIns4_nav_sync();
        scheduleInspectionLine.inspection_5=passData.getInspection_5();
        scheduleInspectionLine.ins5_completed_on=passData.getIns5_completed_on();
        scheduleInspectionLine.ins5_nav_sync=passData.getIns5_nav_sync();
        scheduleInspectionLine.inspection_6=passData.getInspection_6();
        scheduleInspectionLine.ins6_completed_on=passData.getIns6_completed_on();
        scheduleInspectionLine.ins6_nav_sync=passData.getIns6_nav_sync();
        scheduleInspectionLine.inspection_7=passData.getInspection_7();
        scheduleInspectionLine.ins7_nav_sync=passData.getIns7_nav_sync();
        scheduleInspectionLine.ins7_completed_on=passData.getIns7_completed_on();
        scheduleInspectionLine.inspection_8=passData.getInspection_8();
        scheduleInspectionLine.ins8_completed_on=passData.getIns8_completed_on();
        scheduleInspectionLine.ins8_nav_sync=passData.getIns8_nav_sync();
        scheduleInspectionLine.inspection_9=passData.getInspection_9();
        scheduleInspectionLine.ins9_completed_on=passData.getIns9_completed_on();
        scheduleInspectionLine.ins9_nav_sync=passData.getIns9_nav_sync();
        scheduleInspectionLine.inspection_qc=passData.getInspection_qc();
        scheduleInspectionLine.ins_qc_nav_sync=passData.getIns_qc_nav_sync();
        scheduleInspectionLine.ins_qc_completed_on=passData.getIns_qc_completed_on();
       scheduleInspectionLine.ins1_sync_with_server=passData.getIns1_sync_with_server();
       scheduleInspectionLine.ins2_sync_with_server=passData.getIns2_sync_with_server();
       scheduleInspectionLine.ins3_sync_with_server=passData.getIns3_sync_with_server();
       scheduleInspectionLine.ins4_sync_with_server=passData.getIns4_sync_with_server();
       scheduleInspectionLine.ins5_sync_with_server=passData.getIns5_sync_with_server();
       scheduleInspectionLine.ins6_sync_with_server=passData.getIns6_sync_with_server();
       scheduleInspectionLine.ins7_sync_with_server=passData.getIns7_sync_with_server();
       scheduleInspectionLine.ins8_sync_with_server=passData.getIns8_sync_with_server();
       scheduleInspectionLine.ins9_sync_with_server=passData.getIns9_sync_with_server();

        return scheduleInspectionLine;
    }

}
