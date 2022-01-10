package com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.scheduler_inspection.Nicking2InspectionModel;

@Entity(tableName = "nicking2_inspection_table")
public class Nicking2InspectionTable {
    private int android_id;

    @ColumnInfo(name = "scheduler_no")
    private String scheduler_no;

    @NonNull
    @ColumnInfo(name = "arrival_plan_no")
   private String arrival_plan_no;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "production_lot_no")
   private String production_lot_no;
    @ColumnInfo(name = "date_of_inspection")
   private String date_of_inspection;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getScheduler_no() {
        return scheduler_no;
    }

    public void setScheduler_no(String scheduler_no) {
        this.scheduler_no = scheduler_no;
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

    public String getDate_of_inspection() {
        return date_of_inspection;
    }

    public void setDate_of_inspection(String date_of_inspection) {
        this.date_of_inspection = date_of_inspection;
    }

    public String getWhether_recommendation_done() {
        return whether_recommendation_done;
    }

    public void setWhether_recommendation_done(String whether_recommendation_done) {
        this.whether_recommendation_done = whether_recommendation_done;
    }

    public String getDate_of_action() {
        return date_of_action;
    }

    public void setDate_of_action(String date_of_action) {
        this.date_of_action = date_of_action;
    }


    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    @ColumnInfo(name = "whether_recommendation_done")
   private String whether_recommendation_done;
    @ColumnInfo(name = "date_of_action")
   private String date_of_action;

    @ColumnInfo(name = "created_on")
   private String created_on;

    public int getSyncWithApi5() {
        return syncWithApi5;
    }

    public void setSyncWithApi5(int syncWithApi5) {
        this.syncWithApi5 = syncWithApi5;
    }

    @ColumnInfo(name = "syncWithApi5")
    private int syncWithApi5;


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ColumnInfo(name = "remarks")
    public String remarks;



    @ColumnInfo(name = "attachment")
   private String attachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSeed_setting() {
        return seed_setting;
    }

    public void setSeed_setting(String seed_setting) {
        this.seed_setting = seed_setting;
    }

    public String getSeed_setting_prcnt() {
        return seed_setting_prcnt;
    }

    public void setSeed_setting_prcnt(String seed_setting_prcnt) {
        this.seed_setting_prcnt = seed_setting_prcnt;
    }

    @ColumnInfo(name = "seed_setting")
   private String seed_setting ;


    @ColumnInfo(name = "seed_setting_prcnt")
   private String seed_setting_prcnt;


    @ColumnInfo(name = "male_reciept_no")
    private String male_reciept_no;

    public String getMale_reciept_no() {
        return male_reciept_no;
    }

    public void setMale_reciept_no(String male_reciept_no) {
        this.male_reciept_no = male_reciept_no;
    }

    public String getFemale_reciept_no() {
        return female_reciept_no;
    }

    public void setFemale_reciept_no(String female_reciept_no) {
        this.female_reciept_no = female_reciept_no;
    }

    public String getOther_reciept_no() {
        return other_reciept_no;
    }

    public void setOther_reciept_no(String other_reciept_no) {
        this.other_reciept_no = other_reciept_no;
    }

    @ColumnInfo(name = "female_reciept_no")
    private String female_reciept_no;
    @ColumnInfo(name = "other_reciept_no")
    private String other_reciept_no;

    public String getActual_date() {
        return actual_date;
    }

    public void setActual_date(String actual_date) {
        this.actual_date = actual_date;
    }

    @ColumnInfo(name = "actual_date")
    private String actual_date;

    public static  Nicking2InspectionTable insertNicking2InsoectionData(Nicking2InspectionModel nicking2InspectionModel){

        Nicking2InspectionTable nicking2InspectionTable=new Nicking2InspectionTable();
       nicking2InspectionTable.scheduler_no=nicking2InspectionModel.scheduler_no;
       nicking2InspectionTable.arrival_plan_no=nicking2InspectionModel.arrival_plan_no;
       nicking2InspectionTable.production_lot_no=nicking2InspectionModel.production_lot_no;
       nicking2InspectionTable.date_of_inspection=nicking2InspectionModel.date_of_inspection;
       nicking2InspectionTable.whether_recommendation_done=nicking2InspectionModel.whether_recommendation_done;
       nicking2InspectionTable.date_of_action=nicking2InspectionModel.date_of_action;
       nicking2InspectionTable.remarks=nicking2InspectionModel.remarks;
       nicking2InspectionTable.created_on=nicking2InspectionModel.created_on;
       nicking2InspectionTable.seed_setting=nicking2InspectionModel.seed_setting;
       nicking2InspectionTable.seed_setting_prcnt=nicking2InspectionModel.seed_setting_prcnt;
       nicking2InspectionTable.attachment=nicking2InspectionModel.attachment;

        nicking2InspectionTable.male_reciept_no=nicking2InspectionModel.male_reciept_no;
        nicking2InspectionTable.female_reciept_no=nicking2InspectionModel.female_reciept_no;
        nicking2InspectionTable.other_reciept_no=nicking2InspectionModel.other_reciept_no;
        nicking2InspectionTable.actual_date=nicking2InspectionModel.actual_date;


        return nicking2InspectionTable;

    }

}
